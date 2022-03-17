<template>
  <div>
    <!-- <v-container
    v-bind:class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
    id="forecast-blocker-1"
    class="forecast-container ma-0 pa-0"
    fluid
    style="height: 100%;"
  > -->
    <v-container
      id="forecast-blocker-1"
      class="forecast-container ma-0 pa-0"
      fluid
      style="height: 100%;"
    >
      <!-- render blocked overlay -->
      <!-- <div
      v-if="isLogin === false"
      class="container-overlay"
    <v-container
      id="forecast-blocker-1"
      :class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
      class="forecast-container ma-0 pa-0"
      fluid
      style="height: 100%;"
    >
      <div
        v-if="isLogin === false"
        class="container-overlay"
      >
        <div class="overlay-wrapper">
          <p>Please create an account to access 3 free smart searches</p>
          <v-btn
            class="forecastcard-signup"
            color="white"
            elevation="3"
            text
            @click="displayLoginOverlay('create-account')"
          >
            <span>
              Sign Up
            </span>
          </v-btn>
          <p>Already have an account? <a @click="displayLoginOverlay('sign-in')">Log In</a> instead.</p>
        </div>
      </div>
    </v-container>
  </div> -->

      <!-- <div
      v-if="isLogin && isOverLimit"
      class="container-overlay"
    >
      <div class="overlay-wrapper">
        <p>Please upgrade your account to access more statistics</p>
        <v-btn
        class="forecastcard-signup"
        color="white"
        elevation="3"
        text
        @click="toSubscribe"

      <div
        v-if="isLogin && isOverLimit"
        class="container-overlay"
      >
        <div class="overlay-wrapper">
          <p>Please upgrade your account to access more statistics</p>
          <v-btn
            class="forecastcard-signup"
            color="white"
            elevation="3"
            text
            @click="toSubscribe"
          >
            <span>
              Upgrade
            </span>
          </v-btn>
        </div>
      </div>
    </div> -->

      <!-- <div
      v-if="isLogin && !isOverLimit && isOverLimitEnterprise"
      class="container-overlay"
    >
      <div class="overlay-wrapper">
        <p>Please contact us to discuss our custom real estate data and analytics solutions</p>
        <v-btn
        class="forecastcard-signup"
        color="white"
        elevation="3"
        text
        @click="toContactPg()"

      <div
        v-if="isLogin && !isOverLimit && isOverLimitEnterprise"
        class="container-overlay"
      >
        <div class="overlay-wrapper">
          <p>Please contact us to discuss our custom real estate data and analytics solutions</p>
          <v-btn
            class="forecastcard-signup"
            color="white"
            elevation="3"
            text
            @click="toContactPg()"
          >
            <span>
              Contact Us
            </span>
          </v-btn>
        </div>
      </div>
    </div> -->
      <!-- /render blocked overlay -->
      <!-- <login-overlay
      ref="loginOverlay"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
    /> -->
      <!-- /render blocked overlay -->

      <!-- vx: elevation="7" for shadow -->
      <v-card
        v-if="forecast"
        height="100%"
        rounded="10px"
        class="forecast-card mx-auto text-left pb-5"
        style="min-height: 415px; height: 100%; box-shadow: none"
      >
        <v-card-title
          class="text-h5"
          style="word-break: normal;"
        >
          Property Value Forecast:
          <span>
            <v-tooltip
              content-class="custom-tooltip"
              bottom
              max-width="300px"
            >
              <template #activator="{ on, attrs }">
                <i
                  :class="'pt-1 mb-5 mdi band-header-icon mdi-information'"
                  aria-hidden="true"
                  style="float:right;color: black; font-size: 20px"
                  v-bind="attrs"
                  v-on="on"
                />
              </template>
              <span>The Honely Forecast is the most accurate prediction available of a home’s future value. Our AI/ML models consider a multitude of factors such as property features, tax and sale information, macroeconomic indicators, and much more.<br><br>Our models have been back tested and verified by a third party to ensure integrity and prevent cherry picking.</span>
            </v-tooltip>
          </span>
        </v-card-title>
        <v-card-subtitle
          class="mt-1 pb-0"
        >
          Time Frame
        </v-card-subtitle>
        <v-card-text>
          <v-slider
            v-model="forecastYearIndex"
            :tick-labels="tickLabels"
            :max="3"
            step="1"
            ticks="always"
            tick-size="3"
            track-color="#cccccc"
          />
        </v-card-text>
        <div
          v-if="forecastDataShowCheck()"
        >
          <v-card-subtitle
            class="mt-1 pb-0 forecast-data-header py-0"
          >
          <!-- In The Next -->
          <span style="color: emerald; font-size: 17px">
            {{ forecastYear }} Forecast    >>
          </span>
        </v-card-subtitle>
          <!-- Parent class -->
          <v-card
            class="forecast-data-bar d-flex flex-row justify-space-between mx-4 my-2"
            outlined
            rounded="0"
            elevation="0"
            style="padding:13px"
          >
            <!-- %Change text -->
            <v-card
              class="percentChangeText"
              sm="6"
              elevation="0"
            >
              <span
                class="card-content-indicator"
                style="font-size: 17px"
                >
                % Change
              </span>
            </v-card>
            <!-- Value expected to text:  -->
            <v-card
              class="valueChangedText"
              sm="6"
              elevation="0"
            >
              <b>Value expected to:</b>
              <span
                v-if="isNaN(futurePercentageChange)"
                class="pl-1"
                style="font-size: 17px"
              >
                N/A
              </span>
              <span
                v-if="futurePercentageChange > 0"
                class="pl-1"
                style="font-size: 17px"
              >
                increase {{ futurePercentageChange }}%
                <i
                  class="mdi mdi-arrow-up"
                  aria-hidden="true"
                />
              </span>
              <span
                v-if="futurePercentageChange <= 0"
                class="pl-1 value-lower"
                style="font-size: 17px"
              >
                decrease {{ futurePercentageChange }}%
                <i
                  class="mdi down"
                  aria-hidden="true"
                />
              </span>
            </v-card>
          </v-card>
          <v-card
            class="forecast-data-bar d-flex flex-row justify-space-between mx-4 my-2 mb-1"
            outlined
            rounded="0"
            elevation="0"
            style="padding:13px"
          >
            <v-card
              sm="6"
              elevation="0"
            >
              <span
                class="card-content-indicator"
                style="font-size: 17px"
                >
                Honely Forecast
              </span>
            </v-card>
            <v-card
              sm="6"
              elevation="0"
            >
              <b>Property value:</b>
              <span
                v-if="isNaN(futureValueChange)"
                class="pl-1"
                style="font-size: 17px"
              >
                N/A
              </span>
              <span
                v-if="futureValueChange > 0"
                class="pl-1"
                style="font-size: 17px"
              >
                {{ formatPrice(futureValueChangeDollars) }}
                <!-- <i
                class="mdi mdi-arrow-up"
                aria-hidden="true"
              /> -->
              </span>
              <span
                v-if="futureValueChange <= 0"
                class="pl-1 value-lower"
                style="font-size: 17px"
              >
                {{ formatPrice(futureValueChangeDollars) }}
                <!-- <i
                class="mdi down"
                aria-hidden="true"
              /> -->
              </span>
            </v-card>
          </v-card>
        </div>
        <div
          class="contact-agent-form-button"
          v-else
        >
          <contact-agent-form
            :forecast="forecast"
            :user="user"
            :yearForecast="forecastYearIndex"
          >
          </contact-agent-form>
        </div>
        <!-- <v-card
        class="forecast-data-footer  px-2 pt-0 mx-4 mt-0"
        elevation="0"
      >
        <v-card-text
          sm="12"
          class="text-right pa-0"
        >
          Home value is currently:
          <span v-if="forecast">
            {{ formatPrice(forecast.property_forecast.valuation) }}
          </span>
        </v-card-text>
      </v-card>

      <v-card-subtitle
        class="mt-5 pb-0 forecast-data-header py-0"
      >
        In The Past
        <span>
          {{ forecastYear }}
        </span>
        Years
        <i
          class="mdi mdi-chevron-double-left"
          aria-hidden="true"
        />
      </v-card-subtitle>
      <v-card
        class="forecast-data-bar d-flex flex-row justify-space-between pa-2 mx-4 my-2"
        outlined
        rounded="0"
        elevation="0"
      >
        <v-card
          sm="6"
          elevation="0"
        >
          <span>
            % Change
          </span>
        </v-card>
        <v-card
          sm="6"
          elevation="0"
        >
          Home value has:
          <span
            v-if="pastPercentageChange > 0"
            class="pl-1"
          >
            increased {{ pastPercentageChange }}%
            <i
              class="mdi mdi-arrow-up"
              aria-hidden="true"
            />
          </span>
          <span
            v-if="pastPercentageChange <= 0"
            class="pl-1"
          >
            decreased {{ pastPercentageChange }}%
            <i
              class="mdi mdi-arrow-up"
              aria-hidden="true"
            />
          </span>
        </v-card>
      </v-card> -->
        <!-- <v-card-subtitle
        class="mt-1 pb-0 forecast-data-header py-0"
      >
        In The Next
        <span>
          3
        </span>
        months
        <i
          class="mdi mdi-chevron-double-right"
          aria-hidden="true"
        />
      </v-card-subtitle>
      <v-card
        class="forecast-data-bar d-flex flex-row justify-space-between pa-2 mx-4 my-2"
        outlined
        rounded="0"
        elevation="0"
      >
        <v-card
          sm="6"
          elevation="0"
        >
          <span>
            % Change
          </span>
        </v-card>
        <v-card
          sm="6"
          elevation="0"
        >
          Home value will:
          <span
            v-if="futurePercentageChange > 0"
            class="pl-1"
          > -->
        <!-- increase {{ futurePercentageChange }}% -->
        <!-- increase {{ '--' }}%
            <i
              class="mdi mdi-arrow-up"
              aria-hidden="true"
            />
          </span>
          <span
            v-if="futurePercentageChange <= 0"
            class="pl-1 value-lower"
          > -->
        <!-- decrease {{ futurePercentageChange }}% -->
        <!-- decrease {{ '--' }}%
            <i
              class="mdi down"
              aria-hidden="true"
            />
          </span>
        </v-card>
      </v-card> -->

        <!-- <v-card
        class="forecast-data-bar d-flex flex-row justify-space-between pa-2 mx-4 my-2 mb-1"
        outlined
        rounded="0"
        elevation="0"
      >
        <v-card
          sm="6"
          elevation="0"
        >
          <span>
            Honely Forecast
          </span>
        </v-card>
        <v-card
          sm="6"
          elevation="0"
        >
          The home value will be:
          <span
            v-if="futureValueChange > 0"
            class="pl-1"
          >
            {{ '--' }} -->
        <!-- <i
              class="mdi mdi-arrow-up"
              aria-hidden="true"
            /> -->
        <!-- </span>
          <span
            v-if="futureValueChange <= 0"
            class="pl-1 value-lower"
          >
            {{ '--' }} -->
        <!-- <i
              class="mdi down"
              aria-hidden="true"
            /> -->
        <!-- </span>
        </v-card>
      </v-card> -->
      </v-card>
    </v-container>
    <login-overlay
      ref="loginOverlay"
      style="text-align: left;"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
    />
  </div>
</template>

<script>
  import {
    mapGetters,
  } from 'vuex'
  export default {
    name: 'ForecastCard',

    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      ContactAgentForm: () => import('@/components/base/ContactAgentForm'),
    },
    props: {
      displayaddress: {
        type: String,
        default: '21 Macopin Ave',
      },
      forecast: Object,
      user: Object,
    },

    data: () => ({
      tickLabels: [
        '3 months',
        '1 year',
        '2 years',
        '3 years',
      ],
      forecastYearIndex: 0,
      address: 'Helping you invest wisely',
      propertyData: [
        {
          id: 1,
          address: '9 Norwood Lane',
          price1: '$175,000',
          price2: '$192,500',
          price3: '$212,350',
        },
      ],
      showLoginOverlay: false,
      overlayTab: 'sign-up',
    }),

    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser', 'leadDetails', 'leadZipCodeList']),
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
      forecastYear () {
        if (this.forecastYearIndex === 0) {
          return '3 Months'
        } else if (this.forecastYearIndex === 1) {
          return this.forecastYearIndex + ' Year'
        } else {
          return this.forecastYearIndex + ' Years'
        }
      },
      futurePercentageChange () {
        // console.log(this.user)
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast) {
        //         return parseFloat(this.forecast.property_forecast.percentage_change_forecasts[this.forecastYearIndex].change)
        //       } else {
        //         return 0
        //       }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast) {
          return parseFloat(this.forecast.property_forecast.percentage_change_forecasts[this.forecastYearIndex].change)
        } else {
          return 0
        }
      },
      futureValueChange () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast) {
        //       return parseFloat(this.forecast.property_forecast.value_change_forecasts[this.forecastYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast) {
          return parseFloat(this.forecast.property_forecast.value_change_forecasts[this.forecastYearIndex].change)
        } else {
          return 0
        }
      },
      futureValueChangeDollars () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast) {
        //       return this.forecast.property_forecast.value_change_forecasts[this.forecastYearIndex].change
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast) {
          return this.forecast.property_forecast.value_change_forecasts[this.forecastYearIndex].change
        } else {
          return 0
        }
      },
      pastPercentageChange () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast) {
        //       return parseFloat(this.forecast.property_forecast.past_percentage_changes[this.forecastYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast) {
          return parseFloat(this.forecast.property_forecast.past_percentage_changes[this.forecastYearIndex].change)
        } else {
          return 0
        }
      },
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

    mounted: function () {
      // console.log(this.user)
      // console.log('vx: this.user from mounted of forecastcard', this.user)
    },

    methods: {
      forecastDataShowCheck () {
        if (this.forecastYearIndex === 0) {
          return true
        } else {
          /*
          leadDetails
          if user not logged in:
            if zipcode in zipcodelist:
              return true
          else:
            if serviceprovider:
              return true
            else:
              if zipcode in zipcodelist:
                return true
          */
          const leadZipCodeList = this.$store.getters['auth/leadZipCodeList']
          if (!this.user) {
            if (leadZipCodeList !== null && leadZipCodeList.includes(this.forecast.zipcode)) {
              return true
            }
          } else {
            // if (this.user.user_type === 'AGENT/BROKER' || this.user.user_type === 'LENDER' || this.user.user_type === 'GENERAL CONTRACTOR') {
            //   return true
            // } else {
            //   if (leadZipCodeList !== null && leadZipCodeList.includes(this.forecast.zipcode)) {
            //     return true
            //   }
            // }
            return true
          }
        }
        return false
      },
      toContactPg () {
        window.location.href = '/contact'
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      formatPrice (value) {
        // let val = (value/1).toFixed(2).replace('.', ',')
        if (value) {
          value = value.toString().split('.')[0]
          // value = parseInt(value)
          // value = (value % 100 >= 50) ? Math.ceil(value / 100) * 100 : Math.floor(value / 100) * 100
          return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
      toSubscribe () {
        window.location.href = '/subscribe'
      },
    },
  }
</script>

<style lang="sass">
  .forecast-container
    .forecast-card
      border: 1px solid $color-emerald !important

      .v-card__title
        color: $color-orange

        span
          color: $color-gray-shadow
          font-size: 17px
          line-height: 20px
          font-weight: 600
          padding-left: 10px

      .v-card__subtitle
        font-weight: 600
        color: $color-gray-dk

      .v-card__subtitle.forecast-data-header
        font-size: 15px
        font-family: $heading-font-family
        letter-spacing: .03em

        span, i
          font-size: 25px
          color: black
          padding: 0 3px

      .v-slider__tick-label
        font-size: 12px
        font-weight: 600

      .forecast-data-bar
        background-color: $color-gray-vlt
        border-color: $color-gray-mdlt

        .v-card
          background-color: inherit
          font-size: 14px

          span
            color: $color-emerald
            font-weight: 800

          .card-content-indicator
            color: black !important
            font-weight: 600 !important

          span.value-lower
            color: $color-squash

    .forecast-data-footer
      .v-card__text
        span
          font-weight: 800
          padding: 0 4px

</style>

<style>
#forecast-blocker-1 {
  position: relative;
}
.blocked .forecast-card {
  filter: blur(3px);
}
.container-overlay {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(240, 240, 240, 0.5);
  border-radius: 4px;
  z-index: 1;
}
.overlay-wrapper {
  color: #ffffff;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
  height: 100%;
}
.overlay-wrapper p {
  margin: 0;
  width: 100%;
  color: #000000;
  padding: 10px;
  font-weight: bold;
  font-size: 17px;
}
.overlay-wrapper button {
  color: #ffffff;
  background: #3c75fa;
  padding: 10px;
  min-width: 115px;
  margin: 1rem 0 1rem 0;
  border: 1px solid #3063db;
  border-radius: 3px;
}
.overlay-wrapper button:hover {
  background: #3063db;
}
.forecastcard-signup {
  background-color: #08871c !important;
  border-color: #08871c !important;
}
.custom-tooltip {
  opacity: 1!important;
}
.parentClass{
  display: block !important;
}
.v-card .percentChangeText{
  white-space: nowrap;
}
.contact-agent-form-button{
  text-align: center;
  margin-top: 10%;
}
.contact-agent-form-button button{
  box-shadow: 0 8px 16px 0 rgba(0,0,0,0.2), 0 4px 10px 0 rgba(0,0,0,0.19);
}
@media only screen and (max-width: 1265px){
  .v-application .d-flex.forecast-data-bar{
    display: block !important;
  }
}

@media only screen and (max-width: 960px){
  .v-application .d-flex.forecast-data-bar {
    display: flex !important;
  }
}

@media only screen and (max-width: 500px){
  .v-application .d-flex.forecast-data-bar {
    display: block !important;
  }
}
</style>
