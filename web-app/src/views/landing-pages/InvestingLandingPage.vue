<template>
  <!-- eslint-disable -->
  <div class="landing-page-container no-footer-container">
    <div class="landing-page-hero">
      <!-- <p class="hero-text">Find the future value of any property</p> -->
      <p class="landing-page-text" >Investing in Real Estate?</p>
      <div class="landing-page-search-container">
        <honely-search
          :listings="false"
          :standard="false"
          :searchButton="true"
        />
        <div class="landing-page-copy">
          <p>Find the future value of potential investments and much more.</p>
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
    name: 'BuyingLandingPage',
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
