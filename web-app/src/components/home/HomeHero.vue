<template>
  <!-- eslint-disable -->
  <div class="home-hero-container">
    <div class="home-hero">
      <!-- <p class="hero-text">Find the future value of any property</p> -->
      <p class="hero-text" >Find the current and <span class="color-primary">future</span> value of any property</p>
      <div class="home-hero-search-container">
        <honely-search
          :listings="false"
          :standard="false"
          :searchButton="true"
        />
        <div class="home-hero-copy">
          <p>Current price estimates and forecasts up to 3 years into the future on over 100 million properties</p>
        </div>
      </div>
      <div>
        <img class="home-bg-img" src="~@/assets/site_images/graphics/art_home_background.png" alt="Advanced insights, rankings, forecasts">
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'HomeHero',
    components: {
      HonelySearch: () => import('@/components/HonelySearch'),
    },
    props: {
    },
    data: () => ({
    }),
    computed: {
    },
    mounted () {
    },
  }
</script>
