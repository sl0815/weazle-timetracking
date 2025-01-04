<template>
  <button class="text-white font-bold py-2 px-4 rounded-full">
    {{ title }}
  </button>
</template>

<script setup lang="ts">
defineProps(['title'])
</script>
