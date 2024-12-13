package org.weazle.timetracking.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.weazle.timetracking.domain.entity.WorkdayEntity;

import java.util.UUID;

@Repository
public interface WorkdayRepository extends JpaRepository<WorkdayEntity, UUID> {
}
