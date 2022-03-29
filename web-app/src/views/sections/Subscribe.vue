<template>
  <v-container
    class="page-subscribe"
  >
    <!-- eslint-disable -->
    <div class="page-row">
      <h1 
      v-if='subscriptionExists & referralLink'
      align='center'
      style="color: green"
      class="">You already have an existing subscriptions, so promo cannot be applied</span>
      </h1>
      <h1 
      v-if='referralLink & !subscriptionExists'
      align='center'
      
      style="color: green; font-size: 42px;"
      class=""><span style="color: black">{{referring_user}}</span> has referred you to <span style="color: black">Honely!</span></span>
      </h1>
      <h1 
      v-if='referralLink & !subscriptionExists'
      align='center'
      style="color: green"
      class="">Sign up to one of our paid plans to get additional <span style="color: black">Smart Searches</span> for the first month.
      </h1>
      <br>
      <p v-if='!referralLink' class="copy-bold">Whether you&#8217;re a real estate agent helping clients buy that perfect home, or an investor keeping an eye on specific properties or zip codes, keep your finger on the markets pulse with one of our subscription plans.</p>
    </div>
    <div class="container-plans">
        <table class="table-plans">
            <tr>
                <td colspan="3" class="no-borders">&nbsp;</td>
                <td class="highlight-dark">Most popular</td>
                <td colspan="2" class="no-borders">&nbsp;</td>
            </tr>
            <tr>
                <td width="20%" class="no-borders">&nbsp;</td>
                <td width="16%"><span class="plan-name">Free</span></td>
                <td width="16%"><span class="plan-name">Starter</span></td>
                <td width="16%" class="highlight-light"><span class="plan-name">Pro</span></td>
                <td width="16%"><span class="plan-name">Enterprise</span></td>
                <td width="16%"><span class="plan-name">Custom solutions</span></td>
            </tr>
            <tr>
                <td class="no-borders">&nbsp;</td>
                <td valign="top">
                    <div class="plan-wrapper">
                        <span class="plan-cost">{{ getPlanCostFree }}</span>
                        <span class="plan-duration">per month</span>
                        <button v-if="isLogin && checkCurrentPlan === 'FREE'" class="btns" disabled>Current</button>
                        <button v-else-if="isLogin && checkCurrentPlan !== 'FREE'" class="btns" disabled>Sign up</button>
                        <button v-else class="btns" @click="displayLoginOverlay('create-account')">Sign up</button>
                    </div>
                </td>
                <td valign="top">
                    <div class="plan-wrapper">
                        <span class="plan-cost">${{ getPlanCostStarter }}</span>
                        <span class="plan-duration">per month</span>
                        <button v-if="isLogin && checkCurrentPlan === 'STARTER'" class="btns" disabled>Current</button>
                        <button v-else class="btns" @click="doPayment('starter')">Subscribe</button>
                    </div>
                </td>
                <td valign="top" class="highlight-light">
                    <div class="plan-wrapper">
                        <span class="plan-cost">${{ getPlanCostPro }}</span>
                        <span class="plan-duration">per month</span>
                        <button v-if="isLogin && checkCurrentPlan === 'PRO'" class="btns" disabled>Current</button>
                        <button v-else class="btns highlight-dark" @click="doPayment('pro')">Subscribe</button>
                    </div>
                </td>
                <td valign="top">
                    <div class="plan-wrapper">
                        <span class="plan-cost">${{ getPlanCostEnterprise }}</span>
                        <span class="plan-duration">per month</span>
                        <button v-if="isLogin && checkCurrentPlan === 'ENTERPRISE'" class="btns" disabled>Current</button>
                        <button v-else class="btns" @click="doPayment('enterprise')">Subscribe</button>
                    </div>
                </td>
                <td rowspan="9" valign="top">
                    <div class="plan-wrapper">
                        <p class="copy-sm-text">Contact us to discuss our custom real estate data and anylytics solutions.</p>
                        <button class="btns" @click="toContactPg()">Contact us</button>
                    </div>
                </td>
            </tr>
            <tr>
                <td colspan="5" class="divider">Features</td>
            </tr>
            <tr>
                <td>Property / map search</td>
                <td>
                  <span v-if="getMapSearchFree" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
                <td>
                  <span v-if="getMapSearchStarter" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
                <td class="highlight-light">
                  <span v-if="getMapSearchPro" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
                <td>
                  <span v-if="getMapSearchEnterprise" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
            </tr>
            <tr>
                <td>Property valuation</td>
                <td>
                  <span v-if="getValuationFree" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
                <td>
                  <span v-if="getValuationStarter" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
                <td class="highlight-light">
                  <span v-if="getValuationPro" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
                <td>
                  <span v-if="getValuationEnterprise" class="checkmark">&#10004;</span>
                  <span v-else class="crossmark">&#10007;</span>
                </td>
            </tr>
            <tr>
                <td>Property forecasts</td>
                <td>{{ getForecastCountsFree }} </td>
                <td>{{ getForecastCountsStarter }} <span v-if='referralLink' style="color: green">+5 (Bonus)</span></td>
                <td class="highlight-light">{{ getForecastCountsPro }} <span v-if='referralLink' style="color: green">+10 (Bonus)</span></td>
                <td>{{ getForecastCountsEnterprise }} <span v-if='referralLink' style="color: green">+25 (Bonus)</span></td>
            </tr>
            <tr>
                <td>Zip code statistics</td>
                <td>{{ getZipStatsFree }} </td>
                <td>{{ getZipStatsStarter }} <span v-if='referralLink' style="color: green">+5 (Bonus)</span></td>
                <td class="highlight-light">{{ getZipStatsPro }} <span v-if='referralLink' style="color: green">+10 (Bonus)</span></td>
                <td>{{ getZipStatsEnterprise }} <span v-if='referralLink' style="color: green">+25 (Bonus)</span></td>
            </tr>
            <tr>
                <td>Migration forecasts</td>
                <td>{{ getMigrationForecastFree }} </td>
                <td>{{ getMigrationForecastStarter }} <span v-if='referralLink' style="color: green">+5 (Bonus)</span></td>
                <td class="highlight-light">{{ getMigrationForecastPro }} <span v-if='referralLink' style="color: green">+10 (Bonus)</span></td>
                <td>{{ getMigrationForecastEnterprise }} <span v-if='referralLink' style="color: green">+25 (Bonus)</span></td>
            </tr>
            <tr>
                <td>Honely competitive score</td>
                <td>{{ getCompetitiveScoreFree }}</td>
                <td>{{ getCompetitiveScoreStarter }} <span v-if='referralLink' style="color: green">+5 (Bonus)</span></td>
                <td class="highlight-light">{{ getCompetitiveScorePro }} <span v-if='referralLink' style="color: green">+10 (Bonus)</span></td>
                <td>{{ getCompetitiveScoreEnterprise }} <span v-if='referralLink' style="color: green">+25 (Bonus)</span></td>
            </tr>
            <tr>
                <td>Branded property reports</td>
                <td>{{ getReportsFree }} </td>
                <td>{{ getReportsStarter }} <span v-if='referralLink' style="color: green">+5 (Bonus)</span></td>
                <td class="highlight-light">{{ getReportsPro }} <span v-if='referralLink' style="color: green">+10 (Bonus)</span></td>
                <td>{{ getReportsEnterprise }} <span v-if='referralLink' style="color: green">+25 (Bonus)</span></td>
            </tr>
        </table>
    </div>
    <div class="page-row flex-2cols">
        <div class="cols">
            <p class="copy-bold">Want to share property forecasts with clients and colleagues?</p>
            <p>We provide downloadable co-branded reports with all of our subscription tiers so that users can export and share pdfs. As a paid user, you&#8217;ll not only be able to download property reports, you&#8217;ll get to display the name of your firm/brokerage as well as personal contact details.</p>
            <p>Our co-branded property reports are extensive and feature every single data point you see when searching a property or running a property forecast in our site.</p>
        </div>
        <div class="cols">
        </div>
    </div>
    <!-- eslint-disable -->

    <login-overlay
        ref="loginOverlay"
        :show-overlay="showLoginOverlay"
        :display-tab="overlayTab"
        @hideLoginOverlay="hideLoginOverlay"
    />
    <stripe-checkout
      ref="checkoutRef"
      :customerEmail="customerEmail"
      mode="subscription"
      :pk="publishableKey"
      :line-items="lineItems"
      :success-url="successURL"
      :cancel-url="cancelURL"
      @loading="v => loading = v"
    />
  </v-container>
</template>

<script>
  import { StripeCheckout } from '@vue-stripe/vue-stripe'
  import { mapGetters } from 'vuex'
  import axios from 'axios'

  export default {
    name: 'SectionSubscribe',
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      StripeCheckout,
    },
    data () {
      // this.publishableKey = 'pk_test_51Jf6iUKnYAv7HjWU4iktr1OvlsX9aSTiUJAF16nsG2lJIwEkS2k6yOorjuFk7VHQ5bjExBa0V7WP9fVCuvGnora100CiipkNxY'
      this.publishableKey = 'pk_live_51Jf6iUKnYAv7HjWUdWqgDur9RCxcCQ0LIQJ4UjLbN3ZaagOe2ysT0r44F4CUqkNqOMj1ODml3lnY2A7t1eqmv80q00wet01Ymy'
      return {
        showLoginOverlay: false,
        selectedProduct: null,
        overlayTab: 'sign-up',
        planFree: null,
        planStarter: null,
        planPro: null,
        planEnterprise: null,
        customerEmail: null,
        referralLinkParameter: null,
        referralLink: false,
        currentPlan: null,
        subscriptionExists: false,
        lineItems: [],
        successURL: 'https://www.honely.com/paymentSuccess',
        cancelURL: 'https://www.honely.com/subscribe',
        // successURL: 'http://localhost:8080/paymentSuccess',
        // cancelURL: 'http://localhost:8080/paymentFailed',
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      isLogin () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          return this.$store.getters['auth/isCognitoUserLoggedIn']
        } else {
          return false
        }
      },
      getPlanCostFree () {
        if (this.planFree) {
          if (this.planFree.plan_cost === 0) {
            return 'FREE'
          } else {
            return '$' + this.planFree.plan_cost
          }
        } else {
          return 'FREE'
        }
      },
      getMapSearchFree () {
        if (this.planFree) {
          return this.planFree.property_map_search
        } else {
          return false
        }
      },
      getValuationFree () {
        if (this.planFree) {
          return this.planFree.add_property_valuation
        } else {
          return false
        }
      },
      getForecastCountsFree () {
        if (this.planFree) {
          return this.planFree.property_forecast
        } else {
          return 0
        }
      },
      getZipStatsFree () {
        if (this.planFree) {
          return this.planFree.zip_code_statistics
        } else {
          return 0
        }
      },
      getMigrationForecastFree () {
        if (this.planFree) {
          return this.planFree.migration_forecast
        } else {
          return 0
        }
      },
      getCompetitiveScoreFree () {
        if (this.planFree) {
          return this.planFree.competitive_score
        } else {
          return 0
        }
      },
      getReportsFree () {
        if (this.planFree) {
          return this.planFree.property_reports
        } else {
          return 0
        }
      },
      getPlanCostStarter () {
        if (this.planStarter) {
          return this.planStarter.plan_cost
        } else {
          return 0
        }
      },
      getMapSearchStarter () {
        if (this.planStarter) {
          return this.planStarter.property_map_search
        } else {
          return true
        }
      },
      getValuationStarter () {
        if (this.planStarter) {
          return this.planStarter.add_property_valuation
        } else {
          return true
        }
      },
      getForecastCountsStarter () {
        if (this.planStarter) {
          return this.planStarter.property_forecast
        } else {
          return 0
        }
      },
      getZipStatsStarter () {
        if (this.planStarter) {
          return this.planStarter.zip_code_statistics
        } else {
          return 0
        }
      },
      getMigrationForecastStarter () {
        if (this.planStarter) {
          return this.planStarter.migration_forecast
        } else {
          return 0
        }
      },
      getCompetitiveScoreStarter () {
        if (this.planStarter) {
          return this.planStarter.competitive_score
        } else {
          return 0
        }
      },
      getReportsStarter () {
        if (this.planStarter) {
          return this.planStarter.property_reports
        } else {
          return 0
        }
      },
      getPlanCostPro () {
        if (this.planPro) {
          return this.planPro.plan_cost
        } else {
          return 0
        }
      },
      getMapSearchPro () {
        if (this.planPro) {
          return this.planStarter.property_map_search
        } else {
          return true
        }
      },
      getValuationPro () {
        if (this.planPro) {
          return this.planPro.add_property_valuation
        } else {
          return true
        }
      },
      getForecastCountsPro () {
        if (this.planPro) {
          return this.planPro.property_forecast
        } else {
          return 0
        }
      },
      getZipStatsPro () {
        if (this.planPro) {
          return this.planPro.zip_code_statistics
        } else {
          return 0
        }
      },
      getMigrationForecastPro () {
        if (this.planPro) {
          return this.planPro.migration_forecast
        } else {
          return 0
        }
      },
      getCompetitiveScorePro () {
        if (this.planPro) {
          return this.planPro.competitive_score
        } else {
          return 0
        }
      },
      getReportsPro () {
        if (this.planPro) {
          return this.planPro.property_reports
        } else {
          return 0
        }
      },
      getPlanCostEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.plan_cost
        } else {
          return 0
        }
      },
      getMapSearchEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.property_map_search
        } else {
          return true
        }
      },
      getValuationEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.add_property_valuation
        } else {
          return true
        }
      },
      getForecastCountsEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.property_forecast
        } else {
          return 0
        }
      },
      getZipStatsEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.zip_code_statistics
        } else {
          return 0
        }
      },
      getMigrationForecastEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.migration_forecast
        } else {
          return 0
        }
      },
      getCompetitiveScoreEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.competitive_score
        } else {
          return 0
        }
      },
      getReportsEnterprise () {
        if (this.planEnterprise) {
          return this.planEnterprise.property_reports
        } else {
          return 0
        }
      },
      checkCurrentPlan () {
        return this.currentPlan
      },
    },
    created () {
      this.getData()
      this.getCurrentPlan()
      this.load()
    },
    watch: {
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },
    methods: {
      /* eslint-disable */ 
      async getData () {
        // load subscription plans
        fetch('https://api.honely.com/lookup/subscription_plans')
          .then(response => response.json())
          .then(data => {
            // console.log(data)
            if (data.plans != null && data.plans.length >= 4) {
              this.planFree = data.plans[0]
              this.planStarter = data.plans[1]
              this.planPro = data.plans[2]
              this.planEnterprise = data.plans[3]
            } else {
              console.log('[ERROR] Needed plan count 4 but only returns ' + data.plans.length)
            }
          }).catch(error => {
            console.log('[ERROR] subscription plans fetch error.')
            console.log(error)
          })
      },
      async getCurrentPlan () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          axios.get('https://api.honely.com/lookup-test/user_profile', {
            params: {
              email: this.$store.getters['auth/cognitoUser'].attributes.email,
            },
          }).then((response) => {
            // console.log(response.data)
            this.currentPlan = response.data.membership_type
            // console.log(this.currentPlan)
          }).catch((error) => {
            console.log('[ERROR] Failed to fetch user data')
            console.log(error.response.data.errorMessage)
          })
        }
      },
      load () {

        this.referralLinkParameter = this.$route.query.code;
        this.referring_user = this.$route.query.name;
        var currentPlanType = ""
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          axios.get('https://api.honely.com/lookup-test/user_profile', {
              params: {
                email: this.$store.getters['auth/cognitoUser'].attributes.email,
              },
            }).then((response) => {
              // console.log(response.data)
              currentPlanType = response.data.membership_type
              // console.log('zz',this.currentPlan)
              if (this.$store.getters['auth/isCognitoUserLoggedIn'] && (currentPlanType === 'STARTER' || currentPlanType === 'PRO' || currentPlanType === 'ENTERPRISE')) {

                  this.subscriptionExists = true
                }
              else{
                this.subscriptionExists = false
                if (this.referralLinkParameter != null) {
                  this.referralLink = true
                  
                  this.successURL = this.successURL + '?referralCode=' + this.referralLinkParameter
                  // console.log('this.successURL', this.successURL)
                } else {
                  this.successURL = this.successURL + "?"
                }
                // console.log(this.subscriptionExists)
              }
            }).catch((error) => {
              console.log('[ERROR] Failed to fetch user data')
              console.log(error.response.data.errorMessage)
            })
        }
        else{
          this.subscriptionExists = false
          if (this.referralLinkParameter != null) {
            this.referralLink = true
            
            this.successURL = this.successURL + '?referralCode=' + this.referralLinkParameter
            // console.log('this.successURL', this.successURL)
          } else {
            this.successURL = this.successURL + "?"
          }
        }

        // console.log('zz2',currentPlanType)
        
        // TO DO
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      toContactPg () {
        window.location.href = '/contact'
      },
      doPayment (product) {
        // console.log(product)
        // console.log(this.$store.getters['auth/isCognitoUserLoggedIn'])

        if (product === 'starter') {
          this.successURL = this.successURL + '&type=starter'
          this.lineItems = [
            {
              price: 'price_1Jn2jHKnYAv7HjWURvvMeQF4',
              quantity: 1,
            },
          ]
        } else if (product === 'pro') {
          this.successURL = this.successURL + '&type=pro'
          this.lineItems = [
            {
              price: 'price_1Jn2jaKnYAv7HjWUSDlgII1w',
              quantity: 1,
            },
          ]
        } else if (product === 'enterprise') {
          this.successURL = this.successURL + '&type=enterprise'
          this.lineItems = [
            {
              price: 'price_1JjtLtKnYAv7HjWUtoHTywut',
              quantity: 1,
            },
          ]
        } else {
          this.lineItems = []
        }

        // check user login status
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          this.customerEmail = this.$store.getters['auth/cognitoUser'].attributes.email

          // console.log(this.lineItems)
          // console.log(this.customerEmail)

          // if all condition is met, direct to payment page
          if (this.lineItems.length > 0 && this.customerEmail != null) {
            this.$refs.checkoutRef.redirectToCheckout()
          }
        } else {
          console.log('[ERROR] User is not log in')
          var elem = this.$els
          this.displayLoginOverlay('create-account')
          // elem.click()
          // this.$alert('Please login to subscribe.')
        }
      },
    },
  }
</script>

<style>
.swal2-container {
  font-family: "lato", "verdana", sans-serif;
}
.page-subscribe {
  max-width: 1200px;
  padding: 3rem 2rem;
}
.page-subscribe p {
  font-size: 18px;
}
.page-row {
  margin-bottom: 3rem;
}
.page-row:last-of-type {
  margin-bottom: 0;
}
.copy-bold {
  font-weight: bold;
}
.flex-2cols {
  display: flex;
  flex-wrap: wrap;
}
.flex-2cols .cols:first-of-type {
  width: 65%;
}
.flex-2cols .cols:nth-of-type(2) {
  width: 35%;
  display: flex;
  justify-content: center;
}
.container-plans {
  margin: 4rem 0 5rem 0;
  min-width: 300px;
  overflow-x: scroll;
}
.container-plans::-webkit-scrollbar, .container-plans::-webkit-scrollbar {
  display: none;
}
.container-plans {
  -ms-overflow-style: none;
  scrollbar-width: none;
}
.table-plans {
  width: 99%;
  min-width: 1100px;
  border-collapse: collapse;
}
.table-plans .plan-wrapper {
  padding: 1rem 0 1.5rem 0;
}
.table-plans .plan-wrapper .plan-cost {
  display: block;
  font-size: 35px;
  font-weight: bold;
}
.table-plans .plan-wrapper .plan-duration {
  display: block;
  margin-bottom: 1rem;
  font-size: 14px;
}
.table-plans .plan-wrapper .btns {
  background: #07871c;
  color: #ffffff;
  padding: 5px 15px;
  display: block;
  margin: 0 auto;
}
.table-plans .plan-wrapper .btns:disabled, .table-plans .plan-wrapper .btns:disabled:hover {
  background: #aaaaaa;
}
.table-plans .plan-wrapper .btns:hover {
  background: #2c2c2c;
}
.table-plans .plan-wrapper .btns.highlight-dark {
  background: #d65746;
}
.table-plans .plan-wrapper .btns.highlight-dark:hover {
  background: #2c2c2c;
}
.table-plans .plan-name {
  font-weight: bold;
}
.table-plans .checkmark {
  color: #07871c;
}
.table-plans .crossmark {
  color: red;
}
.table-plans td.highlight-dark {
  background: #d65746;
  color: #ffffff;
}
.table-plans td.highlight-light {
  background: #fcf2f2;
}
.table-plans td {
  padding: 5px 8px;
  text-align: center;
  border: 1px solid #bbbbbb;
}
.table-plans td:first-of-type {
  text-align: left;
  font-weight: bold;
  border-left-width: 2px;
}
.table-plans td.no-borders {
  border: none;
}
.table-plans td.divider {
  background: #ebebeb;
}
.table-plans td p.copy-sm-text {
  font-size: 14px;
  margin-bottom: 24px;
}
@media (max-width: 900px) {
.flex-2cols .cols:first-of-type {
  width: 100%;
}
.flex-2cols .cols:nth-of-type(2) {
  width: 100%;
}
}
</style>
