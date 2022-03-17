<template>
  <!-- eslint-disable -->
  <div v-if="loading" class="page-loader">
    <div class="page-loader-wrapper">
      <span class='mdi mdi-loading mdi-spin spin outter'></span>
      <span class='mdi mdi-home inner'></span>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'PageLoader',
    props: {
      loading: Boolean,
    },
  }
</script>
