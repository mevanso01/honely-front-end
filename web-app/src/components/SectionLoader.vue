<template>
  <!-- eslint-disable -->
  <div v-if="loading" class="section-loader-overlay" :class="{ withBackground :!noBackground }">
    <div class="section-loader-wrapper">
      <span class='mdi mdi-loading mdi-spin spin'></span>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'SectionLoader',
    props: {
      loading: Boolean,
      noBackground: Boolean,
    },
  }
</script>
