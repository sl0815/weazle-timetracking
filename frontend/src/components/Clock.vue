<script setup>
import { onMounted } from 'vue'
function startTime() {
  const today = new Date()
  const h = today.getHours()
  let m = today.getMinutes()
  let s = today.getSeconds()
  m = checkTime(m)
  s = checkTime(s)
  document.getElementById('clock').innerHTML = h + ':' + m + ':' + s
  setTimeout(startTime, 1000)
}

function checkTime(i) {
  if (i < 10) {
    i = '0' + i
  } // add zero in front of numbers < 10
  return i
}

onMounted(() => {
  startTime()
})
</script>

<template>
  <div id="clock"></div>
</template>
