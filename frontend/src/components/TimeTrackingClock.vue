<template>
  <div id="clock">{{ time }}</div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { LocalDateTime, DateTimeFormatter } from '@js-joda/core'
const time = ref('')

function startTime() {
  const d = LocalDateTime.now()
  time.value = d.format(DateTimeFormatter.ofPattern('HH:mm:ss')).toString()
  setTimeout(startTime, 1000)
}

onMounted(() => {
  startTime()
})
</script>
