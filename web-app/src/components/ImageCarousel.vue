<template>
  <v-carousel>
    <v-carousel-item
      v-for="(item, i) in items"
      :key="i"
    >
      <v-img
        :src="item.src"
        contain
        max-height="100%"
      />
    </v-carousel-item>
  </v-carousel>
</template>

<script>

  export default {
    props: {
      items: Array,
    },
    data () {
      return {
      }
    },
    created () {
    },
    methods: {
    },
  }
</script>
