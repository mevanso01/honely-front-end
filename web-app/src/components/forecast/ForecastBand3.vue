<template>
  <div
    id="forecast-band-3"
    class="mt-0 py-6"
  >
    <v-container>
      <v-row
        justify="center"
      >
        <v-col
          sm="12"
          :class="{'align-center text-center px-2': $vuetify.breakpoint.smAndDown, 'align-center text-center px-8': $vuetify.breakpoint.mdAndUp}"
        >
          <base-heading
            class="mt-0 forecast-band-header pb-1"
            title=""
            weight="medium"
            size="text-h4"
            space="1"
          >
            <span v-if="forecast && forecast.property_forecast">
              <!-- 3. -->
              <i
                class="mdi mdi-home-map-marker"
                aria-hidden="true"
              />
            </span>
            <span v-if="forecast && !forecast.property_forecast">
              <!--  2. -->
              <i
                class="mdi mdi-home-map-marker"
                aria-hidden="true"
              />
            </span>
            Moving Trends
            <sup>
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
                      v-bind="attrs"
                      style="color: black; font-size: 20px"
                      v-on="on"
                    />
                  </template>
                  <span>Based on comprehensive moving data, we’re able to show you the rate at which people are moving in or out of a zip code. This can be used to help dictate a buyers or sellers market.</span>
                </v-tooltip>
              </span>
            </sup>
          </base-heading>
          <v-row
            justify="start"
            class="forecast-section-below-heading"
          >
            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
              <!-- <v-card
                v-bind:class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
                id="forecast-blocker-4"
                max-width="600"
                elevation="7"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              > -->
              <v-card
                id="forecast-blocker-4"
                max-width="600"
                elevation="7"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              >
                <!-- render blocked overlay -->
                <!-- <div
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
      >
        <span>
          Upgrade
        </span>
      </v-btn>
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
      >
        <span>
          Contact Us
        </span>
      </v-btn>
                </div>
              </div> -->
                <!-- /render blocked overlay -->
                <v-card-title
                  class="text-h5 same-size-span"
                >
                  Moving Trends In:
                  <span>
                    {{ forecast.zipcode }}
                  </span>
                </v-card-title>
                <!-- <v-card
                  class="forecast-data-bar d-flex flex-row justify-space-between pa-2 mx-4 my-2"
                  outlined
                  rounded="0"
                  elevation="0"
                >
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span style="color: black">
                      Total State Rank:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      style= "color: green; font-size: 20px;"
                    >
                      #{{ forecast.moving_trends.total_state_rank }}
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
                    <span style="color: black">
                    Total Country Rank:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      style= "color: green; font-size: 20px;"
                    >
                      #{{ forecast.moving_trends.total_country_rank }}
                    </span>
                  </v-card>
                </v-card> -->
                <br>
                <v-card-subtitle
                  class="mt-1 pb-0"
                >
                  Time Frame
                </v-card-subtitle>
                <v-card-text>
                  <v-slider
                    v-model="movingYearIndex"
                    :tick-labels="tickLabels"
                    :max="4"
                    step="1"
                    ticks="always"
                    tick-size="3"
                    track-color="#cccccc"
                  />
                </v-card-text>
                <v-card-subtitle
                  class="mt-1 pb-0 forecast-data-header py-0"
                >
                  <span>
                    {{ movingYear }}
                  </span>
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
                    <span
                      :class="{'value-lower': moveInPercentageChangeForecast <= 0 && moveInPercentageChangeForecast!=null}"
                    >
                      Move-In Rate:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      v-if="moveInPercentageChangeForecast ==null"
                      class="pl-1"
                    >
                      null
                      <!-- <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      /> -->
                    </span>
                    <span
                      v-if="moveInPercentageChangeForecast !=null && moveInPercentageChangeForecast > 0"
                      class="pl-1"
                    >
                      {{ moveInPercentageChangeForecast }}%
                      <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      />
                    </span>
                    <!-- <span
                      v-if="moveInPercentageChangeForecast <= 0"
                      class="pl-1 value-lower"
                    >
                      {{ moveInPercentageChangeForecast }}%
                      <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      />
                    </span> -->
                  </v-card>
                </v-card>

                <v-card
                  class="forecast-data-bar d-flex flex-row justify-space-between pa-2 mx-4 my-2 mb-1"
                  outlined
                  rounded="0"
                  elevation="0"
                >
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      :class="{'value-lower': moveOutPercentageChangeForecast == null || moveOutPercentageChangeForecast >= 0 }"
                    >
                      Move-Out Rate:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      v-if="moveOutPercentageChangeForecast < 0"
                      class="pl-1"
                    >
                      {{ moveOutPercentageChangeForecast }}%
                      <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      />
                    </span>
                    <span
                      v-if="moveOutPercentageChangeForecast == null"
                      class="pl-1 value-lower"
                    >
                      null
                      <!-- <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      /> -->
                    </span>
                    <span
                      v-if="moveOutPercentageChangeForecast != null && moveOutPercentageChangeForecast >= 0"
                      class="pl-1 value-lower"
                    >
                      {{ moveOutPercentageChangeForecast }}%
                      <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      />
                    </span>
                    <!-- <span
                      v-if="moveOutPercentageChangeForecast != null || moveOutPercentageChangeForecast >= 0"
                      class="pl-1 value-lower"
                    >
                      {{ moveOutPercentageChangeForecast }}%
                      <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      />
                    </span> -->
                  </v-card>
                </v-card>
                <br>
                <!-- vx: add net in here.. -->
                <v-card
                  class="forecast-data-bar d-flex flex-row justify-space-between pa-2 mx-4 my-2 mb-1"
                  outlined
                  rounded="0"
                  elevation="0"
                >
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      :class="{'value-lower': netIn == null || netIn <= 0 }"
                    >
                      Overall Moving Trend:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      v-if="netIn == null"
                      class="pl-1 value-lower"
                    >
                      null
                      <!-- <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      /> -->
                    </span>
                    <span
                      v-if="netIn != null && netIn > 0"
                      class="pl-1"
                    >
                      {{ netIn.toFixed(2) }}%
                      <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      />
                    </span>
                    <span
                      v-if="netIn != null && netIn <= 0"
                      class="pl-1 value-lower"
                    >
                      {{ (netIn.toFixed(2)).substring(1) }}%
                      <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      />
                    </span>
                  </v-card>
                </v-card>

                <!-- <v-card-subtitle
                  class="mt-5 pb-0 forecast-data-header py-0"
                >
                  In The Past
                  <span>
                    {{ movingYear }}
                  </span>
                  Years
                  <i
                    class="mdi mdi-chevron-double-left"
                    aria-hidden="true"
                  />
                </v-card-subtitle> -->
                <br>
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
                    <!-- <span
                      :class="{'value-lower': moveInRankingsState <= 0 }"
                    > -->
                    <span style="color: black">
                      Statewide Desirability Rank : (out of {{ forecast.neighborhood.total_state_rank }})
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      style="color: green; font-size: 20px;"
                    >
                      #{{ desirabilityRankState }}
                      <!-- <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      /> -->
                    </span>
                    <!-- <span
                      v-if="moveInRankingsState <= 0"
                      class="pl-1 value-lower"
                    >
                      {{ moveInRankingsState }}
                      <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      />
                    </span> -->
                  </v-card>
                </v-card>
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
                    <!-- <span
                      :class="{'value-lower': moveOutRankingsState <= 0 }"
                    > -->
                    <span style="color: black">
                       Nationwide Desirability Rank: (out of {{ forecast.neighborhood.total_national_rank }})
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      style="color: green; font-size: 20px;"
                    >
                      #{{ desirabilityRankCountry }}
                      <!-- <i
                        class="mdi mdi-arrow-up"
                        aria-hidden="true"
                      /> -->
                    </span>
                    <!-- <span
                      v-if="moveOutRankingsState <= 0"
                      class="pl-1 value-lower"
                    >
                      {{ moveOutRankingsState }}
                      <i
                        class="mdi mdi-arrow-down"
                        aria-hidden="true"
                      />
                    </span> -->
                  </v-card>
                </v-card>
                <!-- <br>
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
                    <span style="color: black">
                    Move-In Ranking Country:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      style= "color: green; font-size: 20px;"
                    >
                      #{{ moveInRankingsCountry }}
                    </span>
                  </v-card>
                </v-card>
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
                    <span style="color: black">
                      Move-Out Rankings Country:
                    </span>
                  </v-card>
                  <v-card
                    sm="6"
                    elevation="0"
                  >
                    <span
                      style= "color: red; font-size: 20px;"
                    >
                      #{{ moveOutRankingsCountry }}
                    </span>
                  </v-card>
                </v-card> -->
              </v-card>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
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

  export default {
    name: 'ForecastBand3',

    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },

    props: {
      forecast: Object,
      user: Object,
    },

    data: () => ({
      item: {},
      tickLabels: [
        // '1 year',
        // '2 years',
        // '3 years',
        'Current',
        '3 months',
        '1 year',
        '2 years',
        '3 years',
      ],
      movingYearIndex: 0,
      showLoginOverlay: false,
      overlayTab: 'sign-up',
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
      movingYear () {
        if (this.movingYearIndex === 0) {
          return 'Current:'
        } if (this.movingYearIndex === 1) {
          return 'Next 3 months from now:'
        } if (this.movingYearIndex === 2) {
          return 'Next 1 year from now:'
        } if (this.movingYearIndex === 3) {
          return 'Next 2 years from now:'
        } else {
          return 'Next 3 years from now:'
        }
      },
      futurePercentageChange () {
        // if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.percentage_change_forecasts[this.movingYearIndex]) {
        //   return parseFloat(this.forecast.moving_trends.percentage_change_forecasts[this.movingYearIndex].change)
        // } else {
        //   return 0
        // }
        return 0
      },
      futurePercentageComparisonChange () {
        // if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.percentage_change_comparison_forecasts[this.movingYearIndex]) {
        //   return parseFloat(this.forecast.moving_trends.percentage_change_comparison_forecasts[this.movingYearIndex].change)
        // } else {
        //   return 0
        // }
        return 0
      },
      pastPercentageChange () {
        // if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.past_percentage_changes[this.movingYearIndex]) {
        //   return parseFloat(this.forecast.moving_trends.past_percentage_changes[this.movingYearIndex].change)
        // } else {
        //   return 0
        // }
        return 0
      },
      pastPercentageComparisonChange () {
        // if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.past_percentage_changes_comparison[this.movingYearIndex]) {
        //   return parseFloat(this.forecast.moving_trends.past_percentage_changes_comparison[this.movingYearIndex].rank)
        // } else {
        //   return 0
        // }
        return 0
      },
      moveInPercentageChangeForecast () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_in_percentage_change_forecast) {
        //       return this.forecast.moving_trends.move_in_percentage_change_forecast[this.movingYearIndex].change
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_in_percentage_change_forecast) {
          return this.forecast.moving_trends.move_in_percentage_change_forecast[this.movingYearIndex].change
        } else {
          return 0
        }
      },
      moveOutPercentageChangeForecast () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_out_percentage_change_forecast) {
        //       return this.forecast.moving_trends.move_out_percentage_change_forecast[this.movingYearIndex].change
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_out_percentage_change_forecast) {
          return this.forecast.moving_trends.move_out_percentage_change_forecast[this.movingYearIndex].change
        } else {
          return 0
        }
      },
      netIn () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.net_in) {
        //       return this.forecast.moving_trends.net_in[this.movingYearIndex].change
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.net_in) {
          return this.forecast.moving_trends.net_in[this.movingYearIndex].change
        } else {
          return 0
        }
      },
      desirabilityRankState () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.state_rankings) {
        //       // console.log('vx: moveInRankingsState returns', this.forecast.moving_trends.move_in_rankings_state[this.movingYearIndex].rank)
        //       return this.forecast.moving_trends.state_rankings[this.movingYearIndex].rank
        //       // return Math.floor(Math.random() * 961)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.state_rankings) {
          // console.log('vx: moveInRankingsState returns', this.forecast.moving_trends.move_in_rankings_state[this.movingYearIndex].rank)
          return this.forecast.moving_trends.state_rankings[this.movingYearIndex].rank
          // return Math.floor(Math.random() * 961)
        } else {
          return 0
        }
      },
      desirabilityRankCountry () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.country_rankings) {
        //       // console.log('vx: moveInRankingsCountry returns', this.forecast.moving_trends.move_in_rankings_country[this.movingYearIndex].rank)
        //       return this.forecast.moving_trends.country_rankings[this.movingYearIndex].rank
        //       // return 10000 + Math.floor(Math.random() * 20000)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.country_rankings) {
          // console.log('vx: moveInRankingsCountry returns', this.forecast.moving_trends.move_in_rankings_country[this.movingYearIndex].rank)
          return this.forecast.moving_trends.country_rankings[this.movingYearIndex].rank
          // return 10000 + Math.floor(Math.random() * 20000)
        } else {
          return 0
        }
      },
      // moveOutRankingsState () {
      //   if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_out_rankings_state) {
      //     console.log('vx: moveOutRankingsState returns', this.forecast.moving_trends.move_out_rankings_state[this.movingYearIndex].rank)
      //     return this.forecast.moving_trends.move_out_rankings_state[this.movingYearIndex].rank
      //     // return Math.floor(Math.random() * 961)
      //   } else {
      //     return 0
      //   }
      // },
      // moveOutRankingsCountry () {
      //   if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_out_rankings_country) {
      //     console.log('vx: moveOutRankingsCountry returns', this.forecast.moving_trends.move_out_rankings_country[this.movingYearIndex].rank)
      //     return this.forecast.moving_trends.move_out_rankings_country[this.movingYearIndex].rank
      //     // return 10000 + Math.floor(Math.random() * 20000)
      //   } else {
      //     return 0
      //   }
      // },
      // movingYear () {
      //   return this.movingYearIndex
      // },
      // valueZone () {
      //   if (this.forecast) {
      //     return [
      //       parseFloat(this.forecast.neighborhood.past_percentage_change[0].change),
      //       parseFloat(this.forecast.neighborhood.past_percentage_change[2].change),
      //       parseFloat(this.forecast.neighborhood.percentage_change_forecasts[1].change),
      //     ]
      //   } else {
      //     return []
      //   }
      // },
      // valueProperty () {
      //   if (this.forecast) {
      //     return [
      //       // TODO: this is wrong but past changes are not available
      //       // parseFloat(this.forecast.property_forecast.percentage_change_forecasts[0].change),
      //       // parseFloat(this.forecast.property_forecast.percentage_change_forecasts[1].change),
      //       // parseFloat(this.forecast.property_forecast.percentage_change_forecasts[2].change),
      //     ]
      //   } else {
      //     return []
      //   }
      // },
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
      // default
    },
    methods: {
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
      toSubscribe () {
        window.location.href = '/subscribe'
      },
    },
  }
</script>
