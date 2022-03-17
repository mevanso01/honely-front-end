<template>
  <div class="view-section">
    <component
      :is="`section-${section}`"
      v-for="section in sections"
      :key="section"
    />
  </div>
</template>

<script>
  export default {
    name: 'BaseView',
    props: {
      id: {
        type: String,
        default: 'view',
      },
    },
    data: () => ({ sections: [] }),
  }
</script>
