<template>
  <!-- eslint-disable -->
  <v-dialog
    v-model="dialog"
    scrollable
    persistent
    max-width="750px"
    content-class="subscription-dialog-wrapper"
  >
    <template #default>
      <div class="subscription-dialog">
        <div class="subscription-dialog-header">
          <p>Get Honely Pro</p>
          <div class="logo-wrapper">
            <img
              src="site_images/logo_honely.png"
              class="header-logo"
              alt="Honely"
            />
            <span>PRO</span>
          </div>
        </div>
        <div class="subscription-dialog-content">
          <div class="subscription-slogan-wrapper">
            <p>Get Honely Pro for just $14.99 a month</p>
            <p>Honely Pro Members recieve 15 Honely Pro property searches each month. Members enjoy access to additional Honely Pro searches for just $1 each</p>
          </div>
          <div class="property-search-forecast-wrapper">
            <div class="free-container">
              <div class="bage">
                <span>Free</span>
              </div>
              <h3>Free Honely Property Search</h3>
              <p>Honely Free limited data Property Search</p>
              <ul>
                <li>Current Estimate</li>
                <li>1 Year Forecast</li>
              </ul>
            </div>
            <div class="pro-container">
              <div class="bage">
                <span>$2.99</span>
              </div>
              <h3>Honely Pro Property Search</h3>
              <p>Honely Pro Full Property Search</p>
              <ul>
                <li>Current Estimate</li>
                <li>1, 2 and 3 Year Forecasts</li>
                <li>Neighborhood Trends</li>
                <li>Buyers Scores</li>
                <li>Property Investment Calculator</li>
              </ul>
            </div>
          </div>
          <div class="subscription-save-container">
            <h3>Save with a subscription. Honely Pro members get 15 Pro searches each month.</h3>
            <p>Save on property searches with a Honely Pro Subscription. Gain access to our best tools at our best rates available</p>
          </div>
        </div>
        <div class="subscription-dialog-bottom">
          <div>
            <button class="btn" @click="goToSubscriptionPage(true)">Get a Honely Pro Subscription</button>
            <span>or</span>
            <button class="btn" @click="goToSubscriptionPage(false)">Purchase Report for $2.99</button>
          </div>
          <button class="continue-btn" @click="hideDialog">Continue with Free Search</button>
        </div>
      </div>
    </template>
  </v-dialog>
</template>

<script>
  /* eslint-disable */
  export default {
    props: {
      show: Boolean,
      propertyId: String || Number
    },
    computed: {
      dialog: {
        get () {
          return this.show
        },
        set (value) {
          this.$emit('toggleShow', value)
        }
      }
    },
    methods: {
      hideDialog () {
        this.dialog = false
      },
      goToSubscriptionPage (mode) {
        this.$store.dispatch('listings/setSubscriptionMode', {
          propertyId: this.propertyId,
          isPro: mode
        })
        this.$router.push('/smart-data-subscription')
      },
    }
  }
</script>