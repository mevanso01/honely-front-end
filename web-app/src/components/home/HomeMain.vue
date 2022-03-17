<template>
  <!-- eslint-disable -->
  <div class="home-main">
    <!-- section -->
    <div class="section home-bg">
      <div class="section-wrapper flex-wrapper feature-text">
        <div class="section-col-left">
          <p class="home-text-heading-smaller">Get a free market report on your home.</p>
          <p>Both the value and quality of our insights go beyond what's currently reserved for institutions and large enterprises. Never before has the public been able to access investor-grade home value forecasts, relocation trends, zip code rankings, and much more.</p>
          <button class="bg-primary home-search-button" @click="showLoginPopup">Sign Up</button>
        </div>
        <div class="section-col-right">
          <!-- <p class="color-primary text-small align-center">Access advanced insights, rankings, forecasts and more.</p> -->
          <img src="~@/assets/site_images/graphics/art_home_02.png" alt="Advanced insights, rankings, forecasts">
        </div>
      </div>
    </div>
    <!-- /section -->

    <!-- section -->
    <div class="section home-bg">
      <div class="section-wrapper">
        <p class="home-text-heading">A Look Into The <span class="color-primary">Future</span></p>
        <div class="flex-wrapper">
          <div class="flex-col3">
            <div class="home-icon-holder">
              <i class="fa fa-chart-bar home-icon"/>
            </div>
            <p class="feature-text text-medium">Dive Deeper</p>
            <p class="copy80">We give you the market insights needed to analyze properties and neighborhoods primed for gowth.</p>
          </div>
          <div class="flex-col3">
            <div class="home-icon-holder">
              <i class="fa fa-user-plus home-icon"/>
            </div>
            <p class="feature-text text-medium">Get Connected</p>
            <p class="copy80">Leverage our network of service providers to help you with your real estate endeavors. </p>
          </div>
          <div class="flex-col3">
            <div class="home-icon-holder">
              <i class="fa fa-clock home-icon"/>
            </div>
            <p class="feature-text text-medium">Time it Right</p>
            <p class="copy80">Use indsutry leading predictive analytics to decide when and where to buy, sell, or refinance. </p>
          </div>
        </div>
      </div>
    </div>
    <!-- /section -->
    <login-popup
      :show="showLogin"
      @toggleShow="toggleLoginPopupShow"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'HomeMain',
    components: {
      LoginPopup: () => import('@/components/login_popup/Index'),
    },
    props: {
    },
    data: () => ({
      showLogin: false,
    }),
    computed: {
    },
    mounted () {
    },
    methods: {
      showLoginPopup () {
        this.showLogin = true
      },
      toggleLoginPopupShow (value) {
        this.showLogin = value
      },
    },
  }
</script>
