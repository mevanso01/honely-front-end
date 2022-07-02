<template>
  <!-- eslint-disable -->
  <div class="bg-white">
    <div class="section-wrapper">
      <!-- page-section -->
      <div class="flex-wrapper page-section flex-align-center partner">
        <div class="section-copy">
          <h2 class="page-sub-title">
            Realtors, Brokerages, Lenders, &amp; Other Service Providers
          </h2>
          <p>
            Brokerages of all sizes, and other service providers, leverage our predictive analytics to capture leads and demonstrate a unique advantage over the competition. We&rsquo;ve built an affordable, effective lead gen tool so agents can procure leads and drive traffic to their own sites.
          </p>
          <a
            href="https://calendly.com/honelyb2b/leadgen"
            class="theme-links"
          >
            Schedule a call
          </a>
        </div>
        <div class="section-img">
          <img :src="require('@/assets/site_images/graphics/partner-connect-1.png')" />
        </div>
      </div>
      <!-- /page-section -->

      <!-- page-section -->
      <div class="flex-wrapper page-section flex-align-center partner">
        <div class="section-img">
          <img :src="require('@/assets/site_images/graphics/partner-connect-2.png')" />
        </div>
        <div class="section-copy">
          <h2 class="page-sub-title">
            Leverage our technology, data, or leads for your business.
          </h2>
          <p>
            We offer out-of-the-box and custom data and analytics solutions for businesses.
          </p>
          <p>
            Real estate investors, financial institutions, and brands look to Honely to power critical real estate and business decisions with our state-of-the-art forecasting and valuation technology.
          </p>
          <p>Contact us today to schedule a meeting with our team to discuss leveraging our real estate forecast and valuation APIs, as well as custom data solutions we can build for your organization, and much more.</p>
        </div>
      </div>
      <!-- /page-section -->
    </div>
    <!-- header-placeholder -->
    <div class="header-placeholder"></div>
    <login-popup
      :show="showLogin"
      @toggleShow="toggleLoginPopupShow"
    />
  </div>
  <!-- eslint-enable -->
</template>
<script>
  export default {
    name: 'SectionPartnerConnect',
    components: {
      LoginPopup: () => import('@/components/login_popup/Index'),
    },
    data: () => ({
      expand: false,
      showLogin: false,
    }),
    methods: {
      showLoginPopup () {
        this.showLogin = true
      },
      toggleLoginPopupShow (value) {
        this.showLogin = value
      },
      // goToLeadSubscriptions () {
      //   if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
      //     window.location.href = '/leads-subscriptions'
      //   } else {
      //     this.showLoginPopup()
      //   }
      // },
    },
  }
</script>
