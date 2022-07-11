<template>
  <!-- eslint-disable -->
  <div class="pg-static">
    <div v-if="!isCognitoUserLoggedIn" class="section section-wrapper">
        <p>Please login to view this page</p>
    </div>
    <div v-else class="section section-wrapper">
      <!-- title -->
      <h2 class="feature-text text-large user-sub-main-title" >Honely.com Subscription</h2>
      <!-- contents of the page -->
      <div v-if="mountedApisLoaded" class="static-body feature-text text-exsmall user-sub-body" >
        <!-- non-subscribed portion -->
        <div v-if="!subscriptionFlag || !forecastAccess" class="user-sub-body text-small">
          <p><i class="fas fa-arrow-right user-sub-tick" />Before purchasing, investing in, or selling a home you should have an unbiased, analytical overview of not only the home itself but of the surrounding area and the specific housing market you’re interested in.</p>
          <p><i class="fas fa-arrow-right user-sub-tick" />A $14.99 subscription to Honely.com includes a number of forward-looking statistics and data points that are crucial for understanding a property’s future, along with its surrounding neighborhood and the specific housing market that it lies in.</p>
          <fore-cast-subscription />
          <!-- <button class="bg-primary" @click="doUserSubscriptionPayment">Subscribe now</button> -->
        </div>
        <!-- subscribed portion -->
        <div v-if="subscriptionFlag && forecastAccess" class="text-small user-sub-subscription-details" >
          <h2>
            Your Subscription Details:
            <v-tooltip
            content-class="custom-tooltip"
            bottom
            max-width="300px"
            >
            <template #activator="{ on, attrs }">
                <i
                class="fas fa-info-circle user-sub-info-icon"
                v-bind="attrs"
                v-on="on"
                />
            </template>
            <span>Please call (561) 607-5051 for any questions regarding your subscription or for cancellation.</span>
            </v-tooltip>
          </h2>
          <div class="user-sub-list-tick user-sub-subscription-details">
          <ul>
          <li><i class="fas fa-arrow-right user-sub-tick" />Current Pay Cycle Start Date: <span class="newsitem-date mdi mdi-calendar-month-outline user-sub-color-primary" style="display: inline-block;">{{ start_date.substring(0,16) }}</span></li>
          <li><i class="fas fa-arrow-right user-sub-tick" />Current Pay Cycle Renewal Date: <span class="newsitem-date mdi mdi-calendar-month-outline user-sub-color-primary" style="display: inline-block;">{{ end_date.substring(0,16) }}</span></li>
          </ul>
          <!-- <button @click="cancelUserSubscription">Cancel Subscription</button> -->
          </div>
        </div>
        <br>
        <!-- common portion -->
        <div class="user-sub-body">
            <div class="user-sub-features-heading-box">
          <h2 class="user-sub-color-secondary">Included in the subscription:</h2>
            </div>
          <div class="user-sub-feature-list list-ordered user-sub-list-tick user-sub-features-box" >
            <br>
          <ul>
            <li><i class="fas fa-check user-sub-tick" />15 Honely Pro property searches each month</li>
            <li><i class="fas fa-check user-sub-tick" />Ability to generate and download comprehensive report on any home (includes all data points that we offer)</li>
            <li><i class="fas fa-check user-sub-tick" />
                Neighborhood statistics
                <div>
                <ul class="user-sub-list-normal">
                    <li>% change of home values in a given zip code up to 3 years in the future</li>
                    <li>Neighborhood growth rates and desirability ratings</li>
                    <li>Neighborhood rankings</li>
                    <li>Avg. rental income, total listings, properties sold last month, median days on market, school ratings, median listing price, etc.</li>
                </ul>
                </div>
            </li>
            <li><i class="fas fa-check user-sub-tick" />Moving trends (net move-in rate)</li>
            <li><i class="fas fa-check user-sub-tick" />Rental trends</li>
            <li><i class="fas fa-check user-sub-tick" />
                Interactive tools
                <ul class="user-sub-list-normal">
                    <li>Buyer score</li>
                    <li>Investment/planning calculator</li>
                </ul>
            </li>
          </ul>
          <br>
          </div>
        </div>
      </div>
      <stripe-checkout
      ref="checkoutRef"
      :customer-email="cognitoUser.attributes.email"
      mode="subscription"
      :pk="publishableKey"
      :line-items="lineItems"
      :success-url="successURL"
      :cancel-url="cancelURL"
      @loading="v => loading = v"
    />
    </div>
    <div class="header-placeholder"></div>
    <login-popup
      :show="showLogin"
      @toggleShow="toggleLoginPopupShow"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import { StripeCheckout } from '@vue-stripe/vue-stripe'
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'
  export default {
    props: {
      searchQuery: String,
    },
    components: {
      StripeCheckout,
      LoginPopup: () => import('@/components/login_popup/Index'),
      StripeCardForm: () => import('@/components/forecastDataSubscription/StripeCardForm'),
      ForeCastSubscription: () => import('@/components/forecastDataSubscription/ForecastSubscription'),
    },
    data () {
      return {
        userId: null,
        lineItems: [],
        successURL: '',
        publishableKey: 'pk_live_51Jf6iUKnYAv7HjWUdWqgDur9RCxcCQ0LIQJ4UjLbN3ZaagOe2ysT0r44F4CUqkNqOMj1ODml3lnY2A7t1eqmv80q00wet01Ymy',
        cancelURL: 'https://www.honely.com/',
        showLogin: false,
        subscriptionFlag: false,
        mountedApisLoaded: false,
        subscription_id: null,
        start_date: null,
        end_date: null,
        forecastAccess: this.subscriptionMode?.forecastAccess ?? true,
        forecastApiLoaded: false
      }
    },
    computed: {
       ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      ...mapGetters('listings', ['subscriptionMode']),
      oneTimePayment () {
        return this.subscriptionFlag && !this.forecastAccess && this.forecastApiLoaded
      }
    },
    watch: {
      oneTimePayment: function () {
        if (this.oneTimePayment) {
          this.$store.dispatch('listings/setSubscriptionMode', { ...this.subscriptionMode, price: 100 })
        }
      }
    },
    mounted () {
      if (this.subscriptionMode?.searchQuery) {
        this.getEstimatedValue(this.subscriptionMode?.searchQuery)
      }
      if (this.isCognitoUserLoggedIn) {
        axios.get('https://api.honely.com/lookup-test/user_profile?email=' + this.cognitoUser.attributes.email)
          .then((response) => {
            this.userId = response.data.user_id
          })
        axios.get('https://api.honely.com/lookup-test/fetch-user-subscription?email=' + this.cognitoUser.attributes.email)
          .then((response) => {
            if (response.data.subscriopions.length > 0) {
              this.subscriptionFlag = this.subscriptionStatusCheck(response.data.subscriopions[0].end_date)
              if (this.subscriptionFlag) {
                this.userId = response.data.subscriopions[0].user_id
                this.subscription_id = response.data.subscriopions[0].subscription_id
                this.start_date = response.data.subscriopions[0].start_date
                this.end_date = response.data.subscriopions[0].end_date
              }
            }
            this.mountedApisLoaded = true
          })
      } else {
        this.showLogin = true
      }
    },
    methods: {
      subscriptionStatusCheck (x) {
        var month = x.substring(0, 16).split(' ')[2]
        var monthNumMapping = {
          Jan: '01',
          Feb: '02',
          Mar: '03',
          Apr: '04',
          May: '05',
          Jun: '06',
          Jul: '07',
          Aug: '08',
          Sep: '09',
          Oct: '10',
          Nov: '11',
          Dec: '12',
        }
        // console.log(monthNumMapping[month])
        var monthNum = monthNumMapping[month]
        var day = x.substring(0, 16).split(' ')[1]
        var year = x.substring(0, 16).split(' ')[3]
        var date = new Date(monthNum + '/' + day + '/' + year + ' 00:00:00')
        var milliseconds = date.getTime()
        console.log(milliseconds)
        console.log(Date.now())
        return (Date.now() < milliseconds)
      },
      toggleLoginPopupShow (value) {
        this.showLogin = value
        // if (!value) {
        //   window.location.href='/'
        // }
      },
      doUserSubscriptionPayment () {
        this.lineItems = [
          {
            price: 'price_1KhEgjKnYAv7HjWU5KwGRN3U',
            quantity: 1,
          },
        ]
        this.successURL = 'https://www.honely.com/paymentSuccess?user_id=' + this.userId + '&price_id=price_1KhEgjKnYAv7HjWU5KwGRN3U&email=' + this.cognitoUser.attributes.email + '&sub_type=user'
        this.$refs.checkoutRef.redirectToCheckout()
      },
      cancelUserSubscription () {
        axios.get('https://api.honely.com/lookup-test/cancel-user-subscription?subscription_id=' + this.subscription_id)
          .then(() => {
            location.reload()
          })
      },
      getEstimatedValue (searchQuery) {
        this.forecastApiLoaded = false
        const requestOptions = {
          headers: {
            Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
          },
          params: {
            address: searchQuery,
          },
        }
        axios.get('https://api.honely.com/searches/dev/forecast', requestOptions).then((response) => {
          if (response) {
            this.forecastAccess = response.data.access
            this.forecastApiLoaded = true
            if (this.forecastAccess) {
              const redirectUrl = this.subscriptionMode.successURL
              this.$store.dispatch('listings/resetSubscription')
              window.location.href = redirectUrl
            }
          }
        })
        .catch((error) => {
          this.forecastApiLoaded = true
          console.log(error)
        })
      },
    },
  }
</script>
