<template>
  {{ today }}
</template>

<script setup lang="ts">
const today = new Date().toLocaleDateString()
</script>
