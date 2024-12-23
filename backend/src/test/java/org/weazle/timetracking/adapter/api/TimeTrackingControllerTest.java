package org.weazle.timetracking.adapter.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.annotation.PostConstruct;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.weazle.timetracking.adapter.api.model.TimeRecord;
import org.weazle.timetracking.adapter.api.model.TimeRecordType;
import org.weazle.timetracking.domain.entity.WorkdayEntity;
import org.weazle.timetracking.domain.repository.CalendarRepository;
import org.weazle.timetracking.domain.repository.WorkdayRepository;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.with;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles(value = "testcontainer")
@Transactional
public class TimeTrackingControllerTest {

    @LocalServerPort
    private Integer serverPort;

    @NonNull final WorkdayRepository workdayRepository;
    @NonNull final CalendarRepository calendarRepository;

    @Autowired
    public TimeTrackingControllerTest(
            @NonNull final WorkdayRepository workdayRepository,
            @NonNull final CalendarRepository calendarRepository) {
        this.workdayRepository = workdayRepository;
        this.calendarRepository = calendarRepository;
    }

    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:17-alpine"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @PostConstruct
    public void initRestAssured() {
        RestAssured.port = serverPort;
        RestAssured.urlEncodingEnabled = false;
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }



    @Test
    void testTheReturnValueForTodayMustMatchTheCurrentDate() {
        LocalDate today = LocalDate.now();

        given()
            .accept(ContentType.JSON)
        .when()
            .get("/api/today")
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            .body("actualDate", Matchers.equalTo(today.format(DateTimeFormatter.ISO_DATE)))
            .body("nameOfMonth", Matchers.any(String.class))
            .body("weekDay", Matchers.any(String.class))
            .body("isWeekday", Matchers.any(Boolean.class));
    }

    @Test
    void testItMustRecordTheTimeOnANewWorkDay() {
        ZonedDateTime now = ZonedDateTime.now();
        TimeRecord timeRecord = new TimeRecord(now, TimeRecordType.START_WORK);

        String workdayUUIDString = with()
            .body(timeRecord)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
        .when()
            .post("/api/track-time")
        .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            .body("id", Matchers.any(String.class))
            .body("currentState", Matchers.equalTo("PRESENT"))
        .extract().path("id");

        WorkdayEntity workday = workdayRepository.getReferenceById(UUID.fromString(workdayUUIDString));

        assertThat(workday).isNotNull();
        assertThat(workday.getTimeSlots()).hasSize(1);
    }

    @Test
    void testItMustRecordTimesForAnAlreadyExistingWorkday() {
        ZonedDateTime now = ZonedDateTime.now();
        TimeRecord startRecord = new TimeRecord(now, TimeRecordType.START_WORK);
        TimeRecord endRecord = new TimeRecord(now.plusHours(3), TimeRecordType.END_WORK);

        String workdayUUIDString = with()
                .body(startRecord)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .when()
                .post("/api/track-time")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(HttpStatus.OK.value())
                .body("id", Matchers.any(String.class))
                .body("currentState", Matchers.equalTo("PRESENT"))
                .extract().path("id");

        with()
            .body(endRecord)
            .contentType(MediaType.APPLICATION_JSON_VALUE)
            .when()
            .post("/api/track-time-for-workday/" + workdayUUIDString)
            .then()
            .contentType(ContentType.JSON)
            .statusCode(HttpStatus.OK.value())
            .body("id", Matchers.any(String.class))
            .body("currentState", Matchers.equalTo("ABSENT"))
            .extract().path("id");

        WorkdayEntity workdayAfterUpdate = workdayRepository.getReferenceById(UUID.fromString(workdayUUIDString));

        assertThat(workdayAfterUpdate).isNotNull();
        assertThat(workdayAfterUpdate.getTimeSlots()).hasSize(1);
    }
}