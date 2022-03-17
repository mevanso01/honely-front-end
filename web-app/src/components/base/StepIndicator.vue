<template>
  <!-- eslint-disable -->
  <div class="step-indicator-wrapper">
    <span
      v-for="index in steps"
      :key="index"
      :class="'step-indicator-badge' + (index <= value ? ' active' : '') + (size ? ` badge-${size}` : '')">
    </span>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    props: {
      steps: {
        type: Number,
        required: true,
        validator (value) {
          return value > 0
        },
      },
      value: {
        type: Number,
      },
      size: {
        type: String,
        default: 'md',
        validator (value) {
          return ['sm', 'md', 'lg'].includes(value)
        },
      },
    },
    methods: {
    },
  }
</script>

<style scoped>
</style>
