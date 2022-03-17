<template>
  <!-- eslint-disable -->
  <div class="landing-page-container no-footer-container">
    <div class="landing-page-hero">
      <!-- <p class="hero-text">Find the future value of any property</p> -->
      <p class="landing-page-text">Looking to Refinance?</p>
      <div class="landing-page-search-container">
        <honely-search
          :listings="false"
          :standard="false"
          :searchButton="true"
        />
        <div class="landing-page-copy">
          <p>Honely helps time the refi of your property perfectly.</p>
        </div>
      </div>
      <div>
        <img class="landing-page-bg-img" src="~@/assets/site_images/graphics/art_home_background.png" alt="Advanced insights, rankings, forecasts">
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'RefinancingLandingPage',
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
