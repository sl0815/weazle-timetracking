<template>
  {{ formattedDate }}
</template>

<script setup lang="ts">
const today = new Date()

const userLocale = navigator.language || 'de-DE'
const formattedDate = new Intl.DateTimeFormat(userLocale, {
  day: '2-digit',
  month: '2-digit',
  year: 'numeric',
}).format(today)
</script>
