<script setup lang="ts">
import TimeTrackingClock from './TimeTrackingClock.vue'
import TimeTrackingButton from './TimeTrackingButton.vue'
import DateDisplay from './DateDisplay.vue'
</script>

<template>
  <div class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
    <div class="border p-2.5 rounded-lg border-slate-100 bg-slate-100">
      <h1 class="font-mono text-3xl text-center">
        <TimeTrackingClock />
      </h1>
      <p class="text-center text-gray-400">
        <DateDisplay />
      </p>
      <div class="ml-1 mt-5">
        <TimeTrackingButton title="Kommen" class="bg-blue-500 hover:bg-blue-700" />
        <TimeTrackingButton title="Gehen" class="bg-red-500 hover:bg-red-700" />
      </div>
    </div>
  </div>
</template>
