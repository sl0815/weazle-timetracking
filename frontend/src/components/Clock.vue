<script setup>
import { ref, onMounted } from 'vue'
const time = ref('')

function startTime() {
  const today = new Date()
  const h = today.getHours()
  let m = today.getMinutes()
  let s = today.getSeconds()
  m = checkTime(m)
  s = checkTime(s)
  time.value = h + ':' + m + ':' + s
  setTimeout(startTime, 1000)
}

function checkTime(i) {
  if (i < 10) {
    i = '0' + i
  }
  return i
}

onMounted(() => {
  startTime()
})
</script>

<template>
  <div id="clock">{{ time }}</div>
</template>
