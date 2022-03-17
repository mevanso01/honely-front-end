<template>
  <div
    id="forecast-band-2"
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
              <!-- 2. -->
              <i
                class="mdi mdi-home-group"
                aria-hidden="true"
              />
            </span>
            <span v-if="forecast && !forecast.property_forecast">
              <!-- 1. -->
              <i
                class="mdi mdi-home-group"
                aria-hidden="true"
              />
            </span>
            Neighborhood at a Glance
          </base-heading>
          <v-row
            justify="center"
            class="forecast-section-below-heading"
          >
            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
              <!-- <v-card
                id="forecast-blocker-2"
                :class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
                max-width="600"
                elevation="7"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              > -->
              <v-card
                id="forecast-blocker-2"
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
                    <p>Please upgrade your account to access more statistic</p>
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
                  Forecasts For:
                  <span v-if="forecast">
                    {{ forecast.zipcode }}
                  </span>
                </v-card-title>
                <div>
                  <v-card-subtitle
                    class="mt-1 pb-0"
                  >
                    Time Frame
                  </v-card-subtitle>
                  <v-card-text>
                    <v-slider
                      v-model="timeframeYearIndex"
                      :tick-labels="tickLabels"
                      :max="3"
                      step="1"
                      ticks="always"
                      tick-size="3"
                      track-color="#cccccc"
                    />
                  </v-card-text>
                </div>
                <v-card
                  class="mx-4"
                  elevation="0"
                  height="66%"
                >
                  <v-tabs
                    color="#07871c"
                    left
                    @change="onShowBarChart"
                  >
                    <v-tab>Value Forecast</v-tab>
                    <!-- <v-tab>Line Graph</v-tab> -->
                    <v-tab-item
                      height="100%"
                    >
                      <v-container
                        fluid
                      >
                        <v-row class="forecast-chart-row-2cols">
                          <v-col
                            class="mt-3 ml-4"
                          >
                            <!--                            <v-img-->
                            <!--                              :src="require('@/assets/temp-bar-graph.png')"-->
                            <!--                            />-->
                            <!-- <apexchart
                              v-if="user"
                              width="90%"
                              height="168%"
                              type="bar"
                              :options="chartOptions"
                              :series="chartSeries"
                            /> -->
                            <apexchart
                              width="90%"
                              height="168%"
                              type="bar"
                              :options="chartOptions"
                              :series="chartSeries"
                            />
                          </v-col>
                          <v-col
                            class="mt-3"
                          >
                            <!-- <v-card
                              elevation="0"
                              class="forecast-card-content mx-auto text-left pa-0 ma-0 "
                            >
                              <v-card-subtitle
                                class="text-subtitle-2 orange-subtitle mt-1 pb-0 pl-0"
                              >
                                Past
                              </v-card-subtitle>
                              <v-simple-table
                                class="forecast-text-table"
                              >
                                <tbody>
                                  <tr>
                                    <td>
                                      Home values in
                                    </td>
                                    <td v-if="forecast">
                                      {{ forecast.zipcode }}
                                    </td>
                                  </tr>
                                  <tr v-if="pastPercentageChange > 0">
                                    <td>
                                      Have increased
                                    </td>
                                    <td
                                      class="ranking-copy"
                                    >
                                      {{ pastPercentageChange }}%
                                      <i
                                        class="mdi mdi-arrow-up"
                                        aria-hidden="true"
                                      />
                                    </td>
                                  </tr>
                                  <tr v-if="pastPercentageChange <= 0">
                                    <td>
                                      Have decreased
                                    </td>
                                    <td
                                      class="value-lower"
                                    >
                                      {{ pastPercentageChange }}%
                                      <i
                                        class="mdi mdi-arrow-down"
                                        aria-hidden="true"
                                      />
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      Over the past
                                    </td>
                                    <td>
                                      {{ tickValue }}
                                    </td>
                                  </tr>
                                </tbody>
                              </v-simple-table>
                            </v-card> -->
                            <v-card
                              elevation="0"
                              class="forecast-card-content mx-auto pa-0 ma-0 mt-5"
                            >
                              <!-- <v-card-subtitle
                                class="text-subtitle-2 orange-subtitle mt-1 pb-0 pl-0"
                              >
                                Future
                              </v-card-subtitle> -->
                              <v-simple-table
                                class="forecast-text-table"
                              >
                                <tbody>
                                  <tr>
                                    <td>
                                      <b style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important">Home values in:</b>
                                    </td>
                                    <td
                                    v-if="forecast"
                                    style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important"
                                    >
                                      <b>{{ forecast.zipcode }}</b>
                                    </td>
                                    <span>
                                      <v-tooltip
                                        content-class="custom-tooltip"
                                        bottom
                                        max-width="300px"
                                      >
                                        <template #activator="{ on, attrs }">
                                          <i
                                            :class="'pt-1 mb-5 mdi band-header-iconmdi-information'"
                                            aria-hidden="true"
                                            style="float:right;color:black;font-size:18px"
                                            v-bind="attrs"
                                            v-on="on"
                                          />
                                        </template>
                                        <span>Discover whether or not you’re investing in a good area. This data and the accompanying graph shows the rate at which home values will increase or decrease in your desired zip code, as well as for the surrounding 10 zip codes, and the state.</span>
                                      </v-tooltip>
                                    </span>
                                  </tr>
                                  <tr v-if="futurePercentageChange > 0">
                                    <td>
                                      <b style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important">Will increase:</b>
                                    </td>
                                    <td
                                    style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important"
                                    >
                                      <b>{{ futurePercentageChange }}%</b>
                                      <i
                                        class="mdi mdi-arrow-up"
                                        aria-hidden="true"
                                      />
                                    </td>
                                  </tr>
                                  <tr v-if="futurePercentageChange <= 0">
                                    <td>
                                      <b style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important">Will decrease:</b>
                                    </td>
                                    <td
                                      class="value-lower"
                                      style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important"
                                    >
                                      <b>{{ futurePercentageChange }}%</b>
                                      <i
                                        class="mdi mdi-arrow-down"
                                        aria-hidden="true"
                                      />
                                    </td>
                                  </tr>
                                  <tr>
                                    <td>
                                      <b style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important">Over the next:</b>
                                    </td>
                                    <td
                                    style="font-family: 'Avant Garde Gothic Md', 'lato', sans-serif !important"
                                    >
                                      <b>{{ tickValue }}</b>
                                    </td>
                                  </tr>
                                </tbody>
                              </v-simple-table>
                            </v-card>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-tab-item>
                    <!-- vx: line graph area -->
                    <!-- <v-tab-item>
                      <v-container
                        fluid
                      >
                        <v-row>
                          <v-col
                            class="mt-3"
                          >
                            <v-card
                              elevation="0"
                              class="forecast-card-content graph-card text-left mx-auto  pa-0 ma-0"
                            >
                              <v-card-subtitle
                                class="text-subtitle-2 mt-1 pb-0"
                              >
                                <i
                                  class="mdi mdi-rectangle"
                                  aria-hidden="true"
                                />
                                Zip Code Avg. Home Values
                                <i
                                  class="mdi mdi-rectangle pl-3 icon-home-color"
                                  aria-hidden="true"
                                />
                                This Property Home Value
                              </v-card-subtitle>
                              <v-sparkline
                                :labels="labels[timeframeYearIndex]"
                                :value="valueZone"
                                color="#444444"
                                line-width="2"
                                padding="16"
                              />
                              <div
                                class="overlay-graph"
                              >
                                <v-sparkline
                                  :value="valueProperty"
                                  color="#32bebb"
                                  line-width="2"
                                  padding="16"
                                />
                              </div>
                            </v-card>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-tab-item> -->

                    <v-spacer />
                  </v-tabs>
                </v-card>
              </v-card>
            </v-col>

            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
              <!-- <v-card
                id="forecast-blocker-3"
                :class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
                max-width="600"
                min-height="640"
                max-height="640"
                elevation="7"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              > -->
              <v-card
                id="forecast-blocker-3"
                max-width="600"
                min-height="640"
                max-height="765"
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
                    <p>Please create an account to access 3 free smart searchess</p>
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
                  Rankings For:
                  <span v-if="forecast">
                    {{ forecast.zipcode }}
                  </span>
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
                      <span> See how your zip code shapes up against other zips in your state and the nation as a whole.</span>
                    </v-tooltip>
                  </span>
                </v-card-title>
                <div v-if="!rankGraphsActive">
                  <v-card-subtitle
                    class="mt-1 pb-0"
                  >
                    Time Frame
                  </v-card-subtitle>
                  <v-card-text>
                    <v-slider
                      v-model="rankingYearIndex"
                      :tick-labels="tickLabels"
                      :max="3"
                      step="1"
                      ticks="always"
                      tick-size="3"
                      track-color="#cccccc"
                    />
                  </v-card-text>
                </div>
                <v-card
                  class="mx-4"
                  elevation="0"
                >
                  <v-tabs
                    color="#07871c"
                    left
                  >
                    <v-tab @click="setRankGraphsActiveToFalse()">
                      Rankings
                    </v-tab>
                    <v-tab @click="dummy()">
                      Charts
                    </v-tab>

                    <v-tab-item>
                      <v-container
                        fluid
                      >
                        <v-row>
                          <v-col>
                            <v-card
                              elevation="0"
                              class="forecast-card-content mx-auto text-left pa-0 ma-0 "
                            >
                              <v-card-subtitle
                                v-if="forecast && forecast.neighborhood"
                                class="mt-0 pb-5 orange-subtitle text-start"
                              >
                                Rankings in the State of {{ forecast.state }} (out of {{ forecast.neighborhood.total_state_rank }})
                              </v-card-subtitle>

                              <div class="slider-container d-flex flex-column">
                                <div
                                  class="row-container"
                                >
                                  <div
                                    v-if="forecast && forecast.neighborhood"
                                    class="pa-0"
                                  >
                                    <!-- {{ getPercentile(futureRankings,forecast.neighborhood.total_state_rank) }}th<span style="font-size: 14px">  percentile</span>  -->
                                    <div class="slider-row">
                                      <span class="slider-label label-low">Best</span>
                                      <span class="slider-label label-high">Worst</span>
                                      <input
                                        id="myRange"
                                        class="slider"
                                        type="range"
                                        min="1"
                                        :max="forecast.neighborhood.total_state_rank"
                                        :value="futureRankings"
                                        disabled
                                      >
                                    </div>
                                  </div>
                                  <div
                                    v-if="forecast && forecast.neighborhood"
                                    class="ranking-copy"
                                  >
                                    Growth Rate <span style="font-size: 18px; color: #e67e22;">#{{ futureRankings }}</span>
                                  </div>
                                </div>
                                <div
                                  class="row-container"
                                >
                                  <div
                                    v-if="forecast && forecast.neighborhood"
                                    class="pa-0 pl-0"
                                  >
                                    <!-- {{ getPercentile(forecast.neighborhood.avg_value_state_ranking_forecasts[rankingYearIndex].change,forecast.neighborhood.total_state_rank) }}th <span style="font-size: 14px">percentile </span> -->
                                    <div class="slider-row">
                                      <span class="slider-label label-low">Highest</span>
                                      <span class="slider-label label-high">Lowest</span>
                                      <input
                                        id="myRange"
                                        class="slider"
                                        type="range"
                                        min="1"
                                        :max="forecast.neighborhood.total_state_rank"
                                        :value="forecast.neighborhood.avg_value_state_ranking_forecasts[rankingYearIndex].change"
                                        disabled
                                      >
                                    </div>
                                  </div>
                                  <div
                                    class="ranking-copy"
                                  >
                                    Avg. Property Value
                                    <span style="font-size: 18px; color: #e67e22;">#{{ forecast.neighborhood.avg_value_state_ranking_forecasts[rankingYearIndex].change }}</span>
                                  </div>
                                </div>
                              </div>

                              <v-card-subtitle
                                v-if="forecast && forecast.neighborhood"
                                class="mt-0 pb-5 orange-subtitle text-start"
                              >
                                National Rankings (out of {{ forecast.neighborhood.total_national_rank }})
                              </v-card-subtitle>
                              <div class="slider-container d-flex flex-column">
                                <div
                                  class="row-container"
                                >
                                  <div
                                    v-if="forecast && forecast.neighborhood"
                                    class="pa-0 pl-1"
                                  >
                                    <!-- {{ getPercentile(forecast.neighborhood.zipcode_growth_national_ranking_forecasts[rankingYearIndex].change,forecast.neighborhood.total_national_rank) }}th <span style="font-size: 14px">percentile</span> -->
                                    <div class="slider-row">
                                      <span class="slider-label label-low">Best</span>
                                      <span class="slider-label label-high">Worst</span>
                                      <input
                                        id="myRange"
                                        class="slider"
                                        type="range"
                                        min="1"
                                        :max="forecast.neighborhood.total_national_rank"
                                        :value="forecast.neighborhood.zipcode_growth_national_ranking_forecasts[rankingYearIndex].change"
                                        disabled
                                      >
                                    </div>
                                  </div>
                                  <div
                                    class="ranking-copy"
                                  >
                                    Growth Rate
                                    <span style="font-size: 18px; color: #e67e22;">  #{{ forecast.neighborhood.zipcode_growth_national_ranking_forecasts[rankingYearIndex].change }}</span>
                                  </div>
                                </div>
                                <div
                                  v-if="forecast && forecast.neighborhood"
                                  class="row-container"
                                >
                                  <div
                                    class="pa-0 pl-1"
                                  >
                                    <!-- {{ getPercentile(forecast.neighborhood.avg_value_national_ranking_forecasts[rankingYearIndex].change,forecast.neighborhood.total_national_rank) }}th <span style="font-size: 14px">percentile</span> -->
                                    <div class="slider-row">
                                      <span class="slider-label label-low">Highest</span>
                                      <span class="slider-label label-high">Lowest</span>
                                      <input
                                        id="myRange"
                                        class="slider"
                                        type="range"
                                        min="1"
                                        :max="forecast.neighborhood.total_national_rank"
                                        :value="forecast.neighborhood.avg_value_national_ranking_forecasts[rankingYearIndex].change"
                                        disabled
                                      >
                                    </div>
                                  </div>
                                  <div
                                    class="ranking-copy"
                                  >
                                    Avg. Property Value
                                    <span style="font-size: 18px; color: #e67e22;">#{{ forecast.neighborhood.avg_value_national_ranking_forecasts[rankingYearIndex].change }}</span>
                                  </div>
                                </div>
                              </div>
                            </v-card>
                          </v-col>
                        </v-row>
                      </v-container>
                    </v-tab-item>
                    <v-tab-item>
                      <v-tabs>
                        <v-tab @click="dummy()">
                          State
                        </v-tab>
                        <v-tab
                          @click="dummy2()"
                        >
                          National
                        </v-tab>
                        <v-tab-item>
                          <v-container
                            fluid
                          >
                            <v-row>
                              <v-col
                                class="mt-3"
                              >
                                <v-card
                                  elevation="0"
                                  class="forecast-card-content mx-auto text-left pa-0 ma-0 "
                                >
                                  <figure class="highcharts-figure">
                                    <div
                                      id="container"
                                      style="background-color: #fafafa"
                                    />
                                  </figure>
                                </v-card>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-tab-item>
                        <v-tab-item>
                          <v-container
                            fluid
                          >
                            <v-row>
                              <v-col
                                class="mt-3"
                              >
                                <v-card
                                  elevation="0"
                                  class="forecast-card-content mx-auto text-left pa-0 ma-0 "
                                >
                                  <figure class="highcharts-figure">
                                    <div
                                      id="container2"
                                      style="background-color: #fafafa"
                                    />
                                  </figure>
                                </v-card>
                              </v-col>
                            </v-row>
                          </v-container>
                        </v-tab-item>
                      </v-tabs>
                    </v-tab-item>
                    <v-spacer />
                  </v-tabs>
                </v-card>
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
  /* eslint-disable */
  
  
  // var nationalZipCodeRankings = []
  // var nationalPropertyValueRankings = []
  export default {
    name: 'ForecastBand2',

    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },

    props: {
      forecast: Object,
      user: Object,
      stateZipCodeRankings: Array,
      nationalZipCodeRankings: Array,
      statePropertyValueRankings: Array,
      nationalPropertyValueRankings: Array,
    },

    watch: {
      forecast: function() {
        //console.log('Prop changed')
        if(this.rankGraphsActive) {
          if(this.stateGraphActive) {
            this.dummy()
          } else {
            this.dummy2()
          }
        }
      },
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },

    data: () => ({
      // item: {},
      rankGraphsActive: false,
      stateGraphActive: false,
      tickLabels: [
        '3 months',
        '1 year',
        '2 years',
        '3 years',
      ],
      timeframeYearIndex: 0,
      labels: [
        ['-3 Months', 'Current', '+3 Months'],
        ['-1 Years', 'Current', '+1 Years'],
        ['-2 Years', 'Current', '+2 Years'],
        ['-3 Years', 'Current', '+3 Years'],
      ],
      rankingYearIndex: 0,
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
      chartOptions () {
        // console.log(this.forecast)
        return {
          plotOptions: {
            bar: {
              distributed: true,
              dataLabels: {
                position: 'top',
              },
            },
          },
          xaxis: {
            // categories: ['Past', 'Future'],
            categories: [this.forecast.zipcode, 'Surrounding 10 zip codes', 'State'],
            labels: {
              show: false,
            },
          },
          yaxis: {
            tickAmount: 6,
            min: Math.min(0.15, this.futurePercentageChange, this.surroundingsPercentageChange, this.statePercentageChange) - 0.15,
            max: Math.max(this.futurePercentageChange, this.surroundingsPercentageChange, this.statePercentageChange) + 0.15,
          },
          legend: {
            horizontalAlign: 'left',
          },
          chart: {
            toolbar: {
              show: false,
            },
          },
          colors: [
            '#018c45', '#FFA500', '#f5da0f',
          ],
          dataLabels: {
            // position: 'bottom',
            offsetY: -20,
            // textAnchor: 'bottom',
            style: {
              colors: ['#434544'],
            },
            formatter: function (val, opts) {
              return val + '%'
            },
          },
          annotations: {
            yaxis: [{
              y: 0
            }]
          }
        }
      },
      chartSeries () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return [{
        //       name: 'Percent Change',
        //       data: [],
        //     }]
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return [{
        //       name: 'Percent Change',
        //       data: [],
        //     }]
        //   } else {
        //     if (this.forecast && this.futurePercentageChange && this.surroundingsPercentageChange && this.statePercentageChange) {
        //       return [{
        //         name: 'Percent Change',
        //         // data: [this.pastPercentageChange, this.futurePercentageChange],
        //         data: [this.futurePercentageChange, this.surroundingsPercentageChange, this.statePercentageChange],
        //       }]
        //     } else {
        //       return [{
        //         name: 'Percent Change',
        //         data: [],
        //       }]
        //     }
        //   }
        // } else {
        //   return [{
        //     name: 'Percent Change',
        //     data: [],
        //   }]
        // }
        if (this.forecast && this.futurePercentageChange && this.surroundingsPercentageChange && this.statePercentageChange) {
          return [{
            name: 'Percent Change',
            // data: [this.pastPercentageChange, this.futurePercentageChange],
            data: [this.futurePercentageChange, this.surroundingsPercentageChange, this.statePercentageChange],
          }]
        } else {
          return [{
            name: 'Percent Change',
            data: [],
          }]
        }
      },
      futurePercentageChange () {
        // console.log("in band 2, login: " + this.isLogin)
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.neighborhood.percentage_change_forecasts) {
        //       return parseFloat(this.forecast.neighborhood.percentage_change_forecasts[this.timeframeYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.neighborhood.percentage_change_forecasts) {
          return parseFloat(this.forecast.neighborhood.percentage_change_forecasts[this.timeframeYearIndex].change)
        } else {
          return 0
        }
      },
      surroundingsPercentageChange () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.surrounding_zipcode) {
        //       return parseFloat(this.forecast.surrounding_zipcode.percentage_change_forecasts[this.timeframeYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.surrounding_zipcode) {
          return parseFloat(this.forecast.surrounding_zipcode.percentage_change_forecasts[this.timeframeYearIndex].change)
        } else {
          return 0
        }
      },
      statePercentageChange () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.state_statistics) {
        //       return parseFloat(this.forecast.state_statistics.percentage_change_forecasts[this.timeframeYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.state_statistics) {
          return parseFloat(this.forecast.state_statistics.percentage_change_forecasts[this.timeframeYearIndex].change)
        } else {
          return 0
        }
      },
      futureRankings () {
        // if (this.isLogin) {
        //   if (this.isOverLimit && !this.isOverLimitEnterprise) {
        //     return 0
        //   } else if (!this.isOverLimit && this.isOverLimitEnterprise) {
        //     return 0
        //   } else {
        //     if (this.forecast && this.forecast.neighborhood) {
        //       return parseFloat(this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[this.rankingYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.neighborhood) {
          return parseFloat(this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[this.rankingYearIndex].change)
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
        //     if (this.forecast && this.forecast.neighborhood) {
        //       return parseFloat(this.forecast.neighborhood.past_percentage_change[this.timeframeYearIndex].change)
        //     } else {
        //       return 0
        //     }
        //   }
        // } else {
        //   return 0
        // }
        if (this.forecast && this.forecast.neighborhood) {
          return parseFloat(this.forecast.neighborhood.past_percentage_change[this.timeframeYearIndex].change)
        } else {
          return 0
        }
      },
      tickValue () {
        return this.tickLabels[this.timeframeYearIndex]
      },
      valueZone () {
        if (this.forecast && this.forecast.neighborhood) {
          const returnVal = []
          // if (this.forecast.neighborhood.past_percentage_change[this.timeframeYearIndex].change) {
          //   returnVal.push(parseFloat(this.forecast.neighborhood.past_percentage_change[this.timeframeYearIndex].change))
          // } else {
          //   returnVal.push(0)
          // }
          returnVal.push(0)
          if (this.forecast.neighborhood.percentage_change_forecasts[this.timeframeYearIndex].change) {
            returnVal.push(parseFloat(this.forecast.neighborhood.percentage_change_forecasts[this.timeframeYearIndex].change))
          } else {
            returnVal.push(0)
          }

          return returnVal
        } else {
          return []
        }
      },
      valueProperty () {
        if (this.forecast && this.forecast.property_forecast) {
          // TODO: Need past value changes for 3 years
          return [
            parseFloat(this.forecast.property_forecast.value_change_forecasts[0].change),
            parseFloat(this.forecast.property_forecast.value_change_forecasts[1].change),
            parseFloat(this.forecast.property_forecast.value_change_forecasts[2].change),
            parseFloat(this.forecast.property_forecast.value_change_forecasts[3].change),
          ]
        } else {
          return []
        }
      },
    },

    mounted: function () {
      // console.log('vx: this.user from mounted of Forecastband2', this.user)
    },

    methods: {
      getPercentile(rank, total) {
        return Math.round(((total-rank) / total )* 100)
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
      setRankGraphsActiveToFalse () {
        setTimeout(()=>{this.rankGraphsActive=false},100)
        // this.rankGraphsActive=false
      },
      dummy () {
        this.rankGraphsActive=true
        this.stateGraphActive=true
        /* console.log('vx: props data', this.stateZipCodeRankings)
        console.log('vx: props data', this.statePropertyValueRankings)
        console.log('vx: props data', this.nationalZipCodeRankings)
        console.log('vx: props data', this.nationalPropertyValueRankings)
        console.log('vx: this.stateSeries', this.stateSeries) */
        // document.getElementById('container').innerHTML = 'Rendering graph...'
        // forecast.neighborhood.zipcode_growth_national_ranking_forecasts[this.rankingYearIndex].change
        var stateZipCodeRankings = []
        stateZipCodeRankings = this.stateZipCodeRankings.slice()
        var statePropertyValueRankings = []
        statePropertyValueRankings = this.statePropertyValueRankings.slice()
        // for(var x=0; x< 4; x++) {
        //   // console.log('vx: this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[x].change', this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[x].change)
        //   stateZipCodeRankings.push(parseInt(this.stateZipCodeRankings))
        //   // console.log('vx: this.forecast.neighborhood.avg_value_state_ranking_forecasts[x].change', this.forecast.neighborhood.avg_value_state_ranking_forecasts[x].change)
        //   statePropertyValueRankings.push(parseInt(this.forecast.neighborhood.avg_value_state_ranking_forecasts[x].change))
        //   // nationalZipCodeRankings.push(parseInt(this.forecast.neighborhood.zipcode_growth_national_ranking_forecasts[x].change))
        //   // nationalPropertyValueRankings.push(parseInt(this.forecast.neighborhood.avg_value_national_ranking_forecasts[x].change))
        // }
        // console.log('vx: stateZipCodeRankings', stateZipCodeRankings)
        // console.log('vx: statePropertyValueRankings', statePropertyValueRankings)
        setTimeout(function () {
        Highcharts.chart('container', {
          chart: {
            height:390,
            backgroundColor: '#fafafa',
            // width:452
          },
    title: {
        // text: 'Rankings in the State of '+ this.forecast.state + ' (out of ' + this.forecast.neighborhood.total_state_rank + ')'
        text: null
    },

    // subtitle: {
    //     text: 'Source: thesolarfoundation.com'
    // },

    yAxis: [{
        title: {
            text: 'Growth Rank',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        },
        reversed: true,
        allowDecimals: false,
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        },
    },
    {
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        },
        title: {
            text: 'Avg Property Value Rank',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        },
        reversed: true,
        allowDecimals: false,
        opposite: true,
    }],

    xAxis: {
        title: {
            text: 'Time Period',
        },
        categories: ['3 months', '1 year', '2 years', '3 years']
    },

    legend: {
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom'
    },
    navigation: {
      buttonOptions: {
        enabled: false
      }
    },

    plotOptions: {
        series: {
            label: {
                connectorAllowed: false
            },
            // pointStart: 2010
        }
    },
    credits: {
      enabled: false
    },
    series: [{
        name: 'Growth Rank',
        data: stateZipCodeRankings,
    }, {
        name: 'Avg Property Value Rank',
        data: statePropertyValueRankings,
        yAxis: 1
    }/*, {
        name: 'Sales & Distribution',
        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
    }, {
        name: 'Project Development',
        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
    }, {
        name: 'Other',
        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
    }*/],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom'
                }
            }
        }]
    }

});},1)
      },
      dummy2 () {
        this.rankGraphsActive=true
        this.stateGraphActive=false
        // document.getElementById('container').innerHTML = 'Rendering graph...'
        // forecast.neighborhood.zipcode_growth_national_ranking_forecasts[this.rankingYearIndex].change
  //       var nationalZipCodeRankings = []
  // var nationalPropertyValueRankings = []
  //       for(var x=0; x< 4; x++) {
  //         nationalZipCodeRankings.push(parseInt(this.forecast.neighborhood.zipcode_growth_national_ranking_forecasts[x].change))
  //         nationalPropertyValueRankings.push(parseInt(this.forecast.neighborhood.avg_value_national_ranking_forecasts[x].change))
  //       }
        var nationalZipCodeRankings = []
        nationalZipCodeRankings = this.nationalZipCodeRankings.slice()
        var nationalPropertyValueRankings = []
        nationalPropertyValueRankings = this.nationalPropertyValueRankings.slice()
        setTimeout(function () {
        Highcharts.chart('container2', {
          chart: {
            height:390,
            backgroundColor: '#fafafa',
            // width:452
          },
    title: {
        // text: 'Rankings in the State of '+ this.forecast.state + ' (out of ' + this.forecast.neighborhood.total_state_rank + ')'
        text: null
    },

    // subtitle: {
    //     text: 'Source: thesolarfoundation.com'
    // },

    yAxis: [{
        title: {
            text: 'Growth Rank',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        },
        reversed: true,
        allowDecimals: false,
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[0]
            }
        },
    },
    {
        labels: {
            format: '{value}',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        },
        title: {
            text: 'Avg Property Value Rank',
            style: {
                color: Highcharts.getOptions().colors[1]
            }
        },
        reversed: true,
        allowDecimals: false,
        opposite: true,
    }],

    xAxis: {
        title: {
            text: 'Time Period',
        },
        categories: ['3 months', '1 year', '2 years', '3 years']
    },

    legend: {
        layout: 'horizontal',
        align: 'center',
        verticalAlign: 'bottom'
    },

    plotOptions: {
        series: {
            label: {
                connectorAllowed: false
            },
            // pointStart: 2010
        }
    },
    credits: {
      enabled: false
    },
    navigation: {
      buttonOptions: {
        enabled: false
      }
    },

    series: [{
        name: 'Growth Rank',
        data: nationalZipCodeRankings,
    }, {
        name: 'Avg Property Value Rank',
        data: nationalPropertyValueRankings,
        yAxis: 1
    }/*, {
        name: 'Sales & Distribution',
        data: [11744, 17722, 16005, 19771, 20185, 24377, 32147, 39387]
    }, {
        name: 'Project Development',
        data: [null, null, 7988, 12169, 15112, 22452, 34400, 34227]
    }, {
        name: 'Other',
        data: [12908, 5948, 8105, 11248, 8989, 11816, 18274, 18111]
    }*/],

    responsive: {
        rules: [{
            condition: {
                maxWidth: 500
            },
            chartOptions: {
                legend: {
                    layout: 'horizontal',
                    align: 'center',
                    verticalAlign: 'bottom'
                }
            }
        }]
    }

});},1)
      },
      onShowBarChart (tab) {
        if (tab === 0) {
          const originalValue = this.timeframeYearIndex
          this.timeframeYearIndex = 0

          // console.log('pre-setting')
          const me = this
          setTimeout(function () {
            // console.log('setting to original: ' + originalValue)
            me.timeframeYearIndex = originalValue
          }, 200)
        }
      },
      toSubscribe () {
        window.location.href = '/subscribe'
      },
    },
  }
</script>

