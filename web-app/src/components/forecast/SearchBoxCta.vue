<template>
  <v-theme-provider light>
    <base-section
      id="search-box-cta"
      class="white-band"
      space="5"
    >
      <v-container>
        <v-row
          class="border-me-not"
        >
          <v-col
            class="text-center"
            cols="12"
          >
            <div
              class="text-h5 mb-0"
            >
              {{ heading }}
            </div>
            <div
              class="subtitle mb-6"
            >
             <!-- <div
              class="subtitle mb-6"
              style="margin-bottom: 10px !important"
            > -->
              {{ subheading }}
            </div>
            <!-- <p style="font-size: 16px; font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important">Average Value in zipcode: <span style="color: #07871c">${{ forecast.zip_code_listing_statistics.days_on_market_mean }}</span><br>
            Mean days on zipcode: <span style="color: #07871c">{{ forecast.zip_code_listing_statistics.days_on_market_mean }}</span></p> -->
            <!-- render report button -->
            <!-- <div
              v-if="isLogin"
              class="counter-container"
            >
              <p>Remaining lookups: <span>{{ getLookUpCount }}</span></p>
              <a href="/subscribe"><i class="far fa-arrow-alt-circle-up"></i> Upgrade Account</a>
            </div> -->
            <!-- <div
              v-if="isLogin && !isOverLimit && !isOverLimitEnterprise && forecast.property_forecast"
              class="report-btn-container"
            >
              <button
                id="btn_showReportForm"
                @click="showReportForm"
              >
                Generate Report
              </button>
            </div> -->
            <!-- /render report button -->
            <div
            v-if="forecast"
            >
              <button
                id="btn_showReportForm"
                @click="redirectToListings"
              >
                View Listings In {{ forecast.zipcode }}
              </button>
            </div>
            <div
              class="divider-with-icon"
            >
              <v-divider
                class="mt-8"
              />
              <i
                class="mdi mdi-chevron-down"
                aria-hidden="true"
              />
            </div>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'SearchBoxCta',
    props: {
      forecast: Object,
      user: Object,
    },

    data: () => ({
      heading: 'Smart Search Results',
    }),

    computed: {
      isLogin () {
        // console.log(this.user)
        if (this.user) {
          if (this.user.membership_type === null) {
            return false
          } else {
            return true
          }
        } else {
          return false
        }
      },
      isOverLimit () {
        if (this.user.lookup_count !== null) {
          // if (this.user.membership_type === 'FREE' && this.user.lookup_count > 3) {
          //   return true
          // } else if (this.user.membership_type === 'STARTER' && this.user.lookup_count > 10) {
          //   return true
          // } else if (this.user.membership_type === 'PRO' && this.user.lookup_count > 25) {
          //   return true
          // } else {
          //   return false
          // }
          if (this.user.blocked === 'YES') {
            return true
          } else {
            return false
          }
        } else {
          return true
        }
      },
      isOverLimitEnterprise () {
        if (this.user.lookup_count !== null) {
          // if (this.user.membership_type === 'ENTERPRISE' && this.user.lookup_count > 100) {
          //   return true
          // } else {
          //   return false
          // }
          if (this.user.blocked === 'YES') {
            return true
          } else {
            return false
          }
        } else {
          return true
        }
      },
      getLookUpCount () {
        if (this.user) {
          if (this.user.lookup_count) {
            return this.user.lookup_count - 1
          } else {
            return 0
          }
        } else {
          return 0
        }
      },
      subheading () {
        if (this.forecast) {
          if (this.forecast.property_forecast) {
            return 'for: ' + this.forecast.property_forecast.address
          } else {
            return 'for: ' + this.forecast.zipcode
          }
        } else {
          return 'for: [ address searched ]'
        }
      },
    },
    methods: {
      redirectToListings () {
        this.$router.push({ name: 'Listings', query: { address: this.forecast.zipcode, offset: 0 } })
      },
      showReportForm () {
        // alert('test')
        document.getElementById('report-form-overlay').classList.add('active')
      },
    },
  }
</script>

<style lang="sass">
#search-box-cta
  .divider-with-icon
    font-size: 30px
    position: relative

    i
      background-color: #fafafa
      color: $color-gray-lt
      position: absolute
      width: 34px
      height: 20px
      top:-7px
      left: calc(50% - 17px)
      line-height: .6

  .text-h5
    font-weight: 400

  .subtitle
    font-size: 20px
    font-weight: 200

  .featured-search-form

    .featured-search-form-row
      border: 1px solid $color-emerald
      background-color: white
      width: 438px
      box-shadow: 1px 1px 14px #666666
      height: 35px
      overflow: hidden
      position: relative

      span
        display: inline-block
        color: black

      .search-btn
        width: 35px
        height: 35px
        background-color: $color-emerald
        display: block
        position: absolute
        top: 0
        right: 0
        color: white
        font-size: 30px
        padding: 0
        line-height: 35px !important

        i
          display: block
          width: 100%
          text-align: right
          font-size: 25px
          padding-right: 3px

      #searchstring
        padding: 3px 10px
        width: 420px
        color: $color-gray-mddk

  ::placeholder
    color: $color-gray-lt

  :-ms-input-placeholder  /* Internet Explorer 10-11 */
    color: $color-gray-lt

  input:focus, textarea:focus, select:focus, button:focus
    outline: none !important
</style>

<style>
#btn_showReportForm {
  background: #07871c;
  color: #ffffff;
  padding: 8px 15px;
  border-radius: 3px;
  border: 1px solid #07871c;
  -webkit-transition: all 0.30s ease-in-out;
  -moz-transition: all 0.30s ease-in-out;
  -ms-transition: all 0.30s ease-in-out;
  -o-transition: all 0.30s ease-in-out;
}
#btn_showReportForm:hover {
  background: #ffffff;
  color: #07871c;
}
.counter-container {
  text-align: center;
}
.counter-container p {
  margin-bottom: 0 !important;
}
.counter-container p span {
  font-weight: 600;
  color: #e67e22;
}
.counter-container a {
  display: block;
  padding: 12px;
  color: #ffffff;
  background: #07871c;
  text-decoration: none;
  width: 170px;
  margin: 8px auto 0 auto;
  line-height: 100%;
  border: 1px solid #07871c;
  border-radius: 3px;
}
.counter-container a:hover {
  background: #ffffff;
  color: #07871c;
}
</style>
