<template>
  <div class="pg-home">
    <home-hero />
    <home-main />
    <home-maps />
    <home-locals />
    <!-- <home-insights /> -->
  </div>
</template>

<script>
  export default {
    name: 'Home',
    props: [],
    components: {
      HomeHero: () => import('@/components/home/HomeHero'),
      HomeMain: () => import('@/components/home/HomeMain'),
      HomeMaps: () => import('@/components/home/HomeMaps'),
      HomeLocals: () => import('@/components/home/HomeLocals'),
      HomeInsights: () => import('@/components/home/HomeInsights'),
    },
    data: () => ({
    }),
    computed: {
    },
    mounted () {
    },
    methods: {
    },
  }
</script>
