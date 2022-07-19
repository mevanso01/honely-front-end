<template>
  <!-- eslint-disable -->
  <v-dialog
    v-model="dialog"
    scrollable
    persistent
    max-width="1024px"
    content-class="subscription-dialog-wrapper"
  >
    <template #default>
      <div class="subscription-dialog">
        <div class="subscription-dialog-header">
          <div>
            <h2>Honely Pro Subscription</h2>
            <p>Select payment method</p>
          </div>
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
          <table class="subscription-table">
            <thead>
              <tr>
                <th></th>
                <th>Free Search</th>
                <th>Single Property Report</th>
                <th>Honely Pro Subscription</th>
              </tr>
            </thead>
            <tbody>
              <tr>
                <td class="name">Current Estimate</td>
                <td colspan="3"></td>
              </tr>
              <tr>
                <td class="name">1 Year Forecast</td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
               <tr>
                <td class="name">2 Year Forecast</td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
               <tr>
                <td class="name">3 Year Forecast</td>
                <td class="border-bottom"></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
               <tr>
                <td class="name">Neighberhood trends</td>
                <td class="border-bottom"></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
               <tr>
                <td class="name">Buyers Score</td>
                <td class="border-bottom"></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
              <tr>
                <td class="name">Property Investmaent Calculator</td>
                <td class="border-bottom"></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
              <tr>
                <td class="name primary">15 Reports Per month</td>
                <td class="border-bottom"></td>
                <td class="border-bottom"></td>
                <td class="border-bottom"><span class="mdi mdi-check"></span></td>
              </tr>
              <tr>
                <td></td>
                <td></td>
                <td class="price">
                  <p>$2.99 <span>per report</span></p>
                </td>
                <td class="price">
                  <p>$14.99 <span>per month</span></p>
                  <p class="extra">+ $1 per extra report</p>
                </td>
              </tr>
              <tr>
                <td></td>
                <td class="continue">
                  <button class="continue-btn" @click="onClickContinue()">Continue</button>
                </td>
                <td class="purchase">
                  <button class="bg-primary" @click="goToSubscriptionPage(299)" @disabled="!estimatedValueLoaded">Purchase</button>
                </td>
                <td class="purchase">
                  <button class="bg-primary" @click="goToSubscriptionPage(1499)" @disabled="!estimatedValueLoaded">Purchase</button>
                </td>
              </tr>
            </tbody>
          </table>
        </div>
        <login-popup
          :show="showLogin"
          :redirectPath="'/smart-data-subscription'"
          @toggleShow="toggleLoginPopupShow"
        />
      </div>
    </template>
  </v-dialog>
</template>

<script>
  import { mapGetters } from 'vuex'
  /* eslint-disable */
  export default {
    props: {
      show: Boolean,
      forecastAccess: Boolean,
      propertyId: String || Number,
      zipCode: String || Number,
      searchQuery: String
    },
    components: {
      LoginPopup: () => import('@/components/login_popup/Index'),
    },
    data () {
      return {
        showLogin: false,
        forecastPropertyId: this.propertyId || null,
        forecastZipcode: this.zipCode || null,
        estimatedValueLoaded: true
      }
    },
    computed: {
      dialog: {
        get () {
          return this.show
        },
        set (value) {
          this.$emit('toggleShow', value)
        }
      },
       ...mapGetters('auth', ['isCognitoUserLoggedIn']),
    },
    mounted () {
      if (!this.$store.getters['auth/isCognitoUserLoggedIn'] && this.searchQuery) {
        this.getEstimatedValue()
      }
    },
    methods: {
      hideDialog () {
        this.dialog = false
      },
      goToSubscriptionPage (price) {
        let successURL = window.location.href
        if (this.searchQuery) {
          successURL += "forecast?address=" + encodeURI(this.searchQuery)
        }
        this.$store.dispatch('listings/setSubscriptionMode', {
          propertyId: this.forecastPropertyId,
          zipCode: this.forecastZipcode,
          price: price,
          successURL: successURL,
          forecastAccess: this.forecastAccess || false,
          searchQuery: this.searchQuery || null
        })
        if (this.isCognitoUserLoggedIn) {
          this.$router.push('/smart-data-subscription')
        } else {
          this.showLogin = true
        }
      },
      toggleLoginPopupShow (value) {
        this.showLogin = value
      },
      getEstimatedValue () {
        this.estimatedValueLoaded = false
        const requestOptions = {
          params: {
            address: this.searchQuery,
          },
        }
        axios.get('https://api.honely.com/searches/dev/forecast', requestOptions).then((response) => {
          if (response) {
            this.forecastPropertyId = response.data.property_forecast?.property_id
            this.forecastZipcode = response.data?.zipcode
          }
        })
        .catch((error) => {
          this.estimatedValueLoaded = true
          console.log(error)
        })
      },
      onClickContinue () {
        if (this.searchQuery) {
          this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
        } else {
          this.hideDialog()
        }
      }
    },
  }
</script>