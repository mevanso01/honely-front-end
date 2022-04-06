<template>
  <!-- eslint-disable -->
  <div v-if="newsItem" class="newsitem-container">
    <div class="newsitem-info">
      <p class="newsitem-date"><span class="mdi mdi-calendar-month-outline"></span> {{ newsItem.date }}</p>
      <p class="newsitem-title">{{ newsItem.headline }}</p>
      <div>
        <a :href="newsItem.link" class="btn-links">Read Article</a>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'InsightBlock',
    props: {
      newsItem: Object,
    },
    data () {
      return {
      }
    },
    computed: {
    },
    updated () {
    },
    mounted () {
    },
    methods: {
    },
  }
</script>
