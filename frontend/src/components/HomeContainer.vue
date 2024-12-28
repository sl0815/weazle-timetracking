<script setup lang="ts">
import TimeTrackingClock from './TimeTrackingClock.vue'
</script>

<template>
  <div class="absolute top-1/2 left-1/2 -translate-x-1/2 -translate-y-1/2">
    <h1 class="font-mono text-3xl border p-2.5 rounded-lg border-slate-100 bg-slate-100">
      <TimeTrackingClock />
    </h1>
  </div>
</template>
