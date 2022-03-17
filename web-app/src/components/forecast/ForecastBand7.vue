<template>
  <div
    id="forecast-band-7"
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
            @click="test"
          >
            <span v-if="forecast && forecast.property_forecast">
              <!-- 5. -->
              <i
                class="mdi mdi-home-city"
                aria-hidden="true"
              />
            </span>
            <span v-if="forecast && !forecast.property_forecast">
              <!--3. -->
              <i
                class="mdi mdi-home-city"
                aria-hidden="true"
              />
            </span>
            Rental Trends
          </base-heading>
          <v-row class="forecast-section-below-heading justify-center">
            <!-- <v-col
              cols="12"
              sm="8"
              class="white-buttons"
            >
             <button @click="test">See Data</button>
            </v-col> -->
            <v-col
              cols="12"
              class="white-buttons"
            >
              <!-- <v-card
                v-if="forecast"
                id="forecast-blocker-7"
                :class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
                height="100%"
                rounded="10px"
                class="forecast-card mx-0 text-left pb-5"
                style="box-shadow: none; border-radius: 4px; border: 1px solid #07871c; box-shadow: 0px   4px 5px -2px rgb(0 0 0 / 20%), 0px 7px 10px 1px rgb(0 0 0 / 14%), 0px 2px 16px 1px rgb(0   0 0 / 12%) !important;"
              > -->
              <v-card
                v-if="forecast"
                id="forecast-blocker-7"
                height="100%"
                rounded="10px"
                class="forecast-card mx-0 text-left pb-5"
                style="box-shadow: none; border-radius: 4px; border: 1px solid #07871c; box-shadow: 0px   4px 5px -2px rgb(0 0 0 / 20%), 0px 7px 10px 1px rgb(0 0 0 / 14%), 0px 2px 16px 1px rgb(0   0 0 / 12%) !important;"
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
                  <div
                    class="overlay-wrapper"
                  >
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
                <v-row class="justify-center">
                  <v-col
                    cols="12"
                  >
                    <v-card-text>
                      <div v-if="$vuetify.breakpoint.smAndDown">
                      <v-row
                        align="center"
                        justify="end"
                      >
                        <!-- <v-col>
                          <v-card-title
                          class="card-title text-start"
                          style="color: black; word-break: normal; font-weight: 800 !important;"
                          >
                            Current Rental Estimate: <div style="display: inline; color: green;"><b v-if="forecast.property_forecast.rental_estimate">{{ formatPrice(forecast.property_forecast.rental_estimate) }}</b><b v-if="!forecast.property_forecast.rental_estimate">N/A</b></div>
                          </v-card-title>
                        </v-col> -->
                        <!-- <v-col
                          cols="12"
                          lg="4"
                          md="5"
                          sm="12"
                        >
                          <v-card-subtitle
                            class="slider-header pb-2 text-center mr-3"
                          >
                            Time Frame
                          </v-card-subtitle>
                        </v-col> -->
                      </v-row>
                      <v-row
                        align="center"
                        justify="end"
                      >
                        <v-col
                          class="mr-3"
                          cols="12"
                          lg="4"
                          md="5"
                          sm="12"
                        >
                           <v-card-subtitle
                            class="slider-header pb-2 text-center mr-3"
                          >
                            Time Frame
                          </v-card-subtitle>
                          <v-slider
                            v-model="forecastYearIndex"
                            :tick-labels="tickLabels"
                            :max="2"
                            step="1"
                            ticks="always"
                            tick-size="2"
                            track-color="#cccccc"
                            @click="forecastYear"
                          />
                        </v-col>
                      </v-row>
                      </div>
                      <div v-else>
                        <v-row
                        align="center"
                        justify="end"
                      >
                        <!-- <v-card-title
                          md="7"
                          class="card-title text-start"
                          :style="$vuetify.breakpoint.name === 'xl'?'color: black; word-break: normal;padding-right:18%; font-size: 36px !important; font-weight: 800 !important;':'color: black; word-break: normal;padding-right:10%; font-size: 36px !important; font-weight: 800 !important;'"
                          >
                            Current Rental Estimate: <div style="display: inline; color: green;"><b
                            v-if="forecast.property_forecast.rental_estimate"
                            style="margin-left: 10px;"
                            >{{ formatPrice(forecast.property_forecast.rental_estimate) }}</b><b
                            v-if="!forecast.property_forecast.rental_estimate"
                            style="margin-left: 10px;"
                            >N/A</b></div>
                          </v-card-title> -->
                        <v-col
                          cols="12"
                          lg="4"
                          md="5"
                          sm="12"
                        >
                          <v-card-subtitle
                            class="slider-header pb-2 text-center mr-3"
                          >
                            Time Frame
                          </v-card-subtitle>
                        <!-- </v-col>
                      </v-row>
                      <v-row
                        align="center"
                        justify="end"
                      >
                        <v-col
                          class="mr-3"
                          cols="12"
                          lg="4"
                          md="5"
                          sm="12"
                        > -->
                          <v-slider
                            v-model="forecastYearIndex"
                            :tick-labels="tickLabels"
                            :max="2"
                            step="1"
                            ticks="always"
                            tick-size="2"
                            track-color="#cccccc"
                            @click="forecastYear"
                          />
                        </v-col>
                      </v-row>
                      </div>
                      <v-row class="px-5">
                        <v-col
                          cols="12"
                        >
                          <v-simple-table class="growth-rate-table">
                            <thead>
                              <tr class="px-0">
                                <th />
                                <th class="text-left growth-rate-header px-0">
                                  Median Rent
                                </th>
                                <th class="text-left growth-rate-header px-0">
                                  Population
                                </th>
                                <th class="text-left growth-rate-header px-0">
                                  Cash Flow Ratio
                                  <v-tooltip top>
                                    <template #activator="{ on }">
                                      <i
                                        class="mdi mdi-information"
                                        aria-hidden="true"
                                        v-on="on"
                                      />
                                    </template>
                                    <span>Median annualized rental income divided by the median property value in a specific area.</span>
                                  </v-tooltip>
                                </th>
                                <th class="text-left growth-rate-header px-0">
                                  Average Yearly Rental Growth
                                  <v-tooltip top>
                                    <template #activator="{ on }">
                                      <i
                                        class="mdi mdi-information"
                                        aria-hidden="true"
                                        v-on="on"
                                      />
                                    </template>
                                    <span>The compound annual growth rate (CAGR) is the rate of return that would be required for an investment to grow from its beginning balance to its ending one.</span>
                                  </v-tooltip>
                                </th>
                              </tr>
                            </thead>
                            <tbody>
                              <td
                                class="text-left level"
                              >
                                Zip Code
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.zip === null || rentalTrends.zip.median_rent === null ? "N/A" : formatPrice(rentalTrends.zip.median_rent) }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.zip === null || rentalTrends.zip.population === null ? "N/A" : formatPopulation(rentalTrends.zip.population) }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.zip === null || rentalTrends.zip.cash_flow === null ? "N/A" : rentalTrends.zip.cash_flow + "%" }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ year.zipYear === null ? "N/A" : year.zipYear + "%" }} <span class="growth-rate-header">per year</span>
                              </td>
                            </tbody>
                            <tbody>
                              <td
                                class="text-left level px-0"
                              >
                                Metro
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.metro === null || rentalTrends.metro.median_rent === null ? "N/A" : formatPrice(rentalTrends.metro.median_rent) }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.metro === null || rentalTrends.metro.population === null ? "N/A" : formatPopulation(rentalTrends.metro.population) }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.metro === null || rentalTrends.metro.cash_flow === null ? "N/A" : rentalTrends.metro.cash_flow + "%" }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ year.metroYear === null ? "N/A" : year.metroYear + "%" }} <span class="growth-rate-header">per year</span>
                              </td>
                            </tbody>
                            <tbody>
                              <td
                                class="text-left level"
                              >
                                State
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.state === null || rentalTrends.state.median_rent === null ? "N/A" : formatPrice(rentalTrends.state.median_rent) }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.state === null || rentalTrends.state.population === null ? "N/A" : formatPopulation(rentalTrends.state.population) }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ rentalTrends.state === null || rentalTrends.state.cash_flow === null ? "N/A" : rentalTrends.state.cash_flow + "%" }}
                              </td>
                              <td
                                class="text-left level-value py-2"
                              >
                                {{ year.stateYear === null ? "N/A" : year.stateYear + "%" }} <span class="growth-rate-header">per year</span>
                              </td>
                            </tbody>
                          </v-simple-table>
                        </v-col>
                      </v-row>
                    </v-card-text>
                  </v-col>
                </v-row>
                <v-row>
                  <v-col
                    class="pt-1 pb-0 d-flex justify-end"
                    cols="12"
                  >
                    <v-btn
                      class="affiliate-logo mr-1 pa-1"
                      depressed
                      link
                      x-large
                      href="https://www.housingalerts.com/honely-special/"
                      target="_blank"
                    >
                      Powered by
                      <v-img
                        src="../../assets/housing-alerts-affiliate.svg"
                        max-width="150px"
                        max-height="50px"
                      />
                    </v-btn>
                  </v-col>
                </v-row>
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
  import axios from 'axios'
  export default {
    name: 'ForecastBand7',
    components: {
        LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      },
    props: {
      forecast: Object,
      user: Object,
    },
    data: () => ({
        tickLabels: [
        'Past 1 year',
        'Past 3 years',
        'Past 5 years',
      ],
      labels: [
        ['-1 Years', 'Current', '+1 Years'],
        ['-3 Years', 'Current', '+3 Years'],
        ['-5 Years', 'Current', '+5 Years'],
      ],
      forecastYearIndex: 0,
       isLoading: false,
       rentalTrends: {
         zip: {},
         state: {},
         metro: {},
       },
       year: {
         zipYear: null,
         stateYear: null,
         metroYear: null,
       },
      showLoginOverlay: false,
      overlayTab: 'sign-up',
    }),
    computed: {
      isLogin () {
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
          if (this.user.blocked === 'YES') {
            return true
          } else {
            return false
          }
        } else {
          return true
        }
      },
    },
    watch: {
         forecast: function() {
          // console.log('Prop changed')
          this.getTrendData()
        },
        showLoginOverlay: function () {
          if (this.showLoginOverlay) {
            document.documentElement.style.overflow = 'hidden'
            return
          }
          document.documentElement.style.overflow = 'auto'
        },
      },
    mounted () {
        this.getTrendData()
    },
    methods: {
      async getTrendData () {
        if(this.forecast && this.forecast.hasOwnProperty('property_forecast') === true){
          axios.get(`https://www.housingalerts.com/api/?key=gogAEOCeebByaHSVEID92JPVcCu5fmyU&resource_type=honely_dataset&zip=${this.forecast.zipcode}&fips=${this.forecast.property_forecast.fips}&state=${this.forecast.state}`, {transformRequest: (data, headers) => {
          delete headers.common['Authorization'];
          },
          }).then((response) => {
            if(response.data.status === "ok"){
                this.rentalTrends = response.data.data
                this.isLoading = true
                this.forecastYear()
                // pass rental trend data to root
                this.$root.$emit('rentalTrendData', response.data.data)
              }
            else if (response.data.status === "error") {
              this.rentalTrends.zip = null
              this.rentalTrends.state = null
              this.rentalTrends.metro = null
              this.isLoading = false
              }
          }).catch((err) => {
              console.log(err)
          })
        } else {
              this.isLoading = false
              this.rentalTrends.zip = null
              this.rentalTrends.state = null
              this.rentalTrends.metro = null
        }
      },
      test () {
      console.log("ForecastData",this.forecast)
      console.log("Rental Trends",this.rentalTrends)
      },
      forecastYear () {
      var zipYear = null
      var stateYear = null
      var metroYear = null
      if (this.forecastYearIndex === 0) {
        try{
        zipYear = this.rentalTrends.zip.cagr1y
        }
        catch{
          console.log("Insufficient data for cagr1y")
        }
        try{
        stateYear = this.rentalTrends.state.cagr1y
        }
        catch{
          console.log("Insufficient data for cagr1y")
        }
        try{
        metroYear = this.rentalTrends.metro.cagr1y 
        }
        catch{
          console.log("Insufficient data for cagr1y")
        }
        this.year.zipYear = zipYear
        this.year.stateYear = stateYear
        this.year.metroYear = metroYear
        return [zipYear,stateYear,metroYear]
      } else if (this.forecastYearIndex === 1) {
        try{
        zipYear = this.rentalTrends.zip.cagr3y
        }
        catch{
          console.log("Insufficient data for cagr3y")
        }
        try{
        stateYear = this.rentalTrends.state.cagr3y
        }
        catch{
          console.log("Insufficient data for cagr3y")
        }
        try{
        metroYear = this.rentalTrends.metro.cagr3y 
        }
        catch{
          console.log("Insufficient data for cagr3y")
        }
        this.year.zipYear = zipYear
        this.year.stateYear = stateYear
        this.year.metroYear = metroYear
        return [zipYear,stateYear,metroYear]
      } else if (this.forecastYearIndex === 2) {
        try{
        zipYear = this.rentalTrends.zip.cagr5y
        }
        catch{
          console.log("Insufficient data for cagr5y")
        }
        try{
        stateYear = this.rentalTrends.state.cagr5y
        }
        catch{
          console.log("Insufficient data for cagr5y")
        }
        try{
        metroYear = this.rentalTrends.metro.cagr5y 
        }
        catch{
          console.log("Insufficient data for cagr5y")
        }
        this.year.zipYear = zipYear
        this.year.stateYear = stateYear
        this.year.metroYear = metroYear
        return [zipYear,stateYear,metroYear]
      }
      },
     formatPopulation(num) {
       if(!num){
         return
       } else{
         return num.toString().replace(/(\d)(?=(\d{3})+(?!\d))/g, '$1,')
        }
      },
     formatPrice (value) {
        if (value) {
          value = value.toString().split('.')[0]
          return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
      roundDecimal (value) {
        return Math.round(value * 1) / 1 + ".0%";
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
    },
  }
</script>

