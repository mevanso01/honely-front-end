<template>
  <!-- eslint-disable -->
  <div class="forecast-section section-wrapper">
    <!-- forecast-heading-wrapper -->
    <div class="forecast-heading-wrapper">
      <div class="forecast-section-title">Neighborhood at a Glance</div>
      <p class="forecast-address">Forecast for: <span class="mdi mdi-map-marker"></span> {{ getFullAddress }}</p>
    </div>
    <!-- /forecast-heading-wrapper -->

    <!-- forecast-tabs -->
    <div class="honely-forecast-tabs">
      <span class="neighborhood-tab" @click="displayForecastData(0)">Current</span>
      <span class="neighborhood-tab active" @click="displayForecastData(1)">3 Month</span>
      <span class="neighborhood-tab" @click="displayForecastData(2)">1 Year</span>
      <span class="neighborhood-tab" @click="displayForecastData(3)">2 Year</span>
      <span class="neighborhood-tab" @click="displayForecastData(4)">3 Year</span>
    </div>
    <!-- forecast-tabs -->

    <!-- forecast-neighborhood-data -->
    <div class="honely-property-position-relative">
      <!-- <div v-if="!isCognitoUserLoggedIn" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Message prompting to login</p>
          <button class="bg-primary">Sign In</button>
        </div>
      </div> -->
      <div v-if="!isCognitoUserLoggedIn || (!forecastAccess && !subscriptionFlag)" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Please subscribe to view all of our statistics</p>
          <button class="bg-primary" @click="showSubscriptionPopup()">Subscribe</button>
        </div>
      </div>
      <div v-else-if="subscriptionFlag && !forecastAccess" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Please purchase $1.00 to view this statistics</p>
          <button @click="showSingleSubscriptionPopup()" class="bg-primary">Purchase for $1.00</button>
        </div>
      </div>
    <div class="forecast-neighborhood-data" :class="!isCognitoUserLoggedIn || !forecastAccess?'blocked':''" >
      <!-- forecast-neighborhood-leftcol -->
      <div class="forecast-neighborhood-left">
        <!-- forecast-neighborhood-home-value -->
        <div class="forecast-neighborhood-home-value forecast-wrapper">
          <div class="forecast-wrapper-heading">
            Predicted Home Values
          </div>
          <div class="forecast-wrapper-inner">
            <div v-if="homeValuesChartData && homeValuesChartData.length > 0">
              <p class="forecast-value-big">{{ getForecastHomeValuePercent }}</p>
              <div class="chart-wrapper">
                <apexchart
                  v-if="homeValuesChartData && homeValuesChartData.length > 0"
                  width="100%"
                  height="140px"
                  :options="homeValuesChartOptions"
                  :series="homeValuesChartData"
                />
              </div>
            </div>
            <div v-else class="forecast-not-applicable">
              <span class="mdi mdi-home-circle"></span>
              <p>Not applicable</p>
            </div>
          </div>
        </div>
        <!-- /forecast-neighborhood-home-value -->

        <!-- forecast-neighborhood-rankings -->
        <div class="forecast-neighborhood-rankings forecast-wrapper">
          <div class="forecast-wrapper-heading">
            <span class="forecast-wrapper-heading-tab active" @click="displayTabContent(0)">Rankings</span>
            <span class="forecast-wrapper-heading-tab" @click="displayTabContent(1)">Charts</span>
          </div>
          <div class="forecast-wrapper-inner">
            <div class="forecast-tab-content active">
              <table class="table-data">
                <tr>
                  <th width="56%">&nbsp;</th>
                  <th width="22%" class="heading-bg">State</th>
                  <th width="22%" class="heading-bg">National</th>
                </tr>
                <tr>
                  <td class="spacer" colspan="3">&nbsp;</td>
                </tr>
                <tr>
                  <td>Growth Rate:</td>
                  <td v-if="neighborhoodData">{{ formatRanking(neighborhoodData.zipcode_growth_state_ranking_forecasts[activeTimeframe].change) }}</td>
                  <td v-else>--</td>
                  <td v-if="neighborhoodData">{{ formatRanking(neighborhoodData.zipcode_growth_national_ranking_forecasts[activeTimeframe].change) }}</td>
                  <td v-else>--</td>
                </tr>
                <tr>
                  <td>Avg. Property Value:</td>
                  <td v-if="neighborhoodData">{{ formatRanking(neighborhoodData.avg_value_state_ranking_forecasts[activeTimeframe].change) }}</td>
                  <td v-else>--</td>
                  <td v-if="neighborhoodData">{{ formatRanking(neighborhoodData.avg_value_national_ranking_forecasts[activeTimeframe].change) }}</td>
                  <td v-else>--</td>
                </tr>
                <tr>
                  <td>Desireability Rank:</td>
                  <td v-if="movingTrends">{{ formatRanking(movingTrends.state_rankings[activeTimeframe].rank) }}</td>
                  <td v-else>--</td>
                  <td v-if="movingTrends">{{ formatRanking(movingTrends.country_rankings[activeTimeframe].rank) }}</td>
                  <td v-else>--</td>
                </tr>
              </table>
            </div>
            <div class="forecast-tab-content">
              <div class="forecast-slider-wrapper">
                <label>Growth Rate:</label>
                <v-slider
                  v-if="neighborhoodData"
                  class="forecast-slider"
                  readonly
                  :value="neighborhoodData.zipcode_growth_state_ranking_forecasts[activeTimeframe].change"
                  min="0"
                  :max="neighborhoodData.total_state_rank"
                  color="rgba(36, 203, 67, 0.3)"
                  track-color="rgba(36, 203, 67, 0.3)"
                  thumb-color="#ff7d1f"
                ></v-slider>
                <div class="forecast-slider-legend">
                  <span>Best</span>
                  <span>Worst</span>
                </div>
              </div>
              <div class="forecast-slider-wrapper">
                <label>Avg. Property Value:</label>
                <v-slider
                  v-if="neighborhoodData"
                  class="forecast-slider"
                  readonly
                  :value="neighborhoodData.avg_value_state_ranking_forecasts[activeTimeframe].change"
                  min="0"
                  :max="neighborhoodData.total_state_rank"
                  color="rgba(36, 203, 67, 0.3)"
                  track-color="rgba(36, 203, 67, 0.3)"
                  thumb-color="#ff7d1f"
                ></v-slider>
                <div class="forecast-slider-legend">
                  <span>Best</span>
                  <span>Worst</span>
                </div>
              </div>
              <div class="forecast-slider-wrapper">
                <label>Desireability Rank:</label>
                <v-slider
                  v-if="movingTrends"
                  class="forecast-slider"
                  readonly
                  :value="movingTrends.state_rankings[activeTimeframe].rank"
                  min="0"
                  :max="neighborhoodData.total_state_rank"
                  color="rgba(36, 203, 67, 0.3)"
                  track-color="rgba(36, 203, 67, 0.3)"
                  thumb-color="#ff7d1f"
                ></v-slider>
                <div class="forecast-slider-legend">
                  <span>Best</span>
                  <span>Worst</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /forecast-neighborhood-rankings -->

        <!-- forecast-neighborhood-stats -->
        <div class="forecast-neighborhood-stats forecast-wrapper">
          <div class="forecast-wrapper-heading">
            <div class="data-col">&nbsp;</div>
            <div class="data-col">Zip Code</div>
            <div class="data-col">vs.</div>
            <div class="data-col">State</div>
          </div>
          <div class="forecast-wrapper-inner">
            <table class="table-data">
              <tr>
                <td class="spacer" colspan="4">&nbsp;</td>
              </tr>
              <tr>
                <td width="55%">Avg. Rental Income:</td>
                <td v-if="this.forecast" width="20%">{{ formatNumber(getStatsTableData(this.forecast.zip_code_listing_statistics.average_rental_income), true, false) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ formatNumber(getStatsTableData(this.forecast.state_statistics.average_rental_income), true, false) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Total Listings on Market:</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.zip_code_listing_statistics.total_listing_on_marker) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.state_statistics.total_listing_on_marker) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Properties Sold Last Month:</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.zip_code_listing_statistics.sold_properties_last_month) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.state_statistics.sold_properties_last_month) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Avg. Value per Sqft:</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.zip_code_listing_statistics.average_sqft) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.state_statistics.average_sqft) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Median Days on Market:</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.zip_code_listing_statistics.median_days_on_market) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.state_statistics.median_days_on_market) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Best Schools:</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.zip_code_listing_statistics.great_school_rating) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ getStatsTableData(this.forecast.state_statistics.great_school_rating) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Median Sold Price 1y:</td>
                <td v-if="this.forecast" width="20%">{{ formatNumber(getStatsTableData(this.forecast.zip_code_listing_statistics.median_sold_price), true, false) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ formatNumber(getStatsTableData(this.forecast.state_statistics.median_sold_price), true, false) }}</td>
                <td v-else width="20%">--</td>
              </tr>
              <tr>
                <td>Median Listing Price:</td>
                <td v-if="this.forecast" width="20%">{{ formatNumber(getStatsTableData(this.forecast.zip_code_listing_statistics.median_listings_price), true, false) }}</td>
                <td v-else width="20%">--</td>
                <td width="5%">&nbsp;</td>
                <td v-if="this.forecast" width="20%">{{ formatNumber(getStatsTableData(this.forecast.state_statistics.median_listings_price), true, false) }}</td>
                <td v-else width="20%">--</td>
              </tr>
            </table>
          </div>
        </div>
        <!-- /forecast-neighborhood-stats -->
      </div>
      <!-- /forecast-neighborhood-left -->

      <!-- forecast-neighborhood-leftcol -->
      <div class="forecast-neighborhood-right">
        <!-- forecast-neighborhood-moving-trends -->
        <div class="forecast-neighborhood-moving-trends forecast-wrapper">
          <div class="forecast-wrapper-heading">
            Moving Trends for {{ getZipcode }}
          </div>
          <div class="forecast-wrapper-inner">
            <p class="forecast-value-big">{{ getMovingTrendsNetIn }}</p>
          </div>
        </div>
        <!-- /forecast-neighborhood-moving-trends -->

        <!-- forecast-neighborhood-housing-price -->
        <div class="forecast-neighborhood-housing-price forecast-wrapper">
          <div class="forecast-wrapper-heading">
            Avg. Price Rank: {{ getCity }}
          </div>
          <div class="forecast-wrapper-inner">
            <div class="chart-wrapper">
              <apexchart
                width="100%"
                height="250px"
                :options="avgPriceChartOptions"
                :series="avgPriceChartData"
              />
            </div>
            <div class="forecast-chart-btns">
              <span class="forecast-chart-btn active" @click="displayChart(0)">State</span>
              <span class="forecast-chart-btn" @click="displayChart(1)">National</span>
            </div>
          </div>
        </div>
        <!-- /forecast-neighborhood-housing-price -->

        <!-- forecast-neighborhood-housing-price -->
        <div class="forecast-neighborhood-listings forecast-wrapper">
          <div class="forecast-wrapper-inner align-center">
            <p class="text-large">Browse Listings</p>
            <p>Find other homes in {{ getZipcode }} zip code.</p>
            <p><a @click="goToListings(getZipcode)" class="theme-links">Explore All Properties</a></p>
          </div>
        </div>
        <!-- /forecast-neighborhood-listings -->
      </div>
      <!-- /forecast-neighborhood-right -->
    </div>
    </div>
    <!-- /forecast-neighborhood-data -->
    <subscription-popup
      :show="showSubscription"
      :forecastAccess="forecastAccess"
      :zipCode="getZipcode"
      :propertyId="getPropertyId"
      @toggleShow="toggleSubscriptionShow"
    />
    <single-subscription-popup
      :show="showSingleSubscription"
      :forecastAccess="forecastAccess"
      :zipCode="getZipcode"
      :propertyId="getPropertyId"
      :defaultPaymethod="defaultPaymethod"
      @toggleShow="toggleSingleSubscriptionShow"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'Neighborhood',
    components: {
      SubscriptionPopup: () => import('@/components/forecast/SubscriptionPopup'),
      SingleSubscriptionPopup: () => import('@/components/forecast/SingleSubscriptionPopup'),
    },
    props: {
      forecast: Object,
      subscriptionFlag: Boolean,
      defaultPaymethod: Object,
    },
    data () {
      return {
        // blocked: true,
        neighborhoodData: null,
        surroundingZipData: null,
        stateData: null,
        movingTrends: null,
        activeTimeframe: 1,
        activeGrowthStateRank: '--',
        activeGrowthNationalRank: '--',
        activeValueStateRank: '--',
        activeValueNationalRank: '--',
        activeChartType: 0,
        showSubscription: false,
        showSingleSubscription: false,
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      isProperty () {
        if (this.forecast && this.forecast.property_forecast) {
          return true
        } else {
          return false
        }
      },
      getZipcode () {
        if (this.forecast) {
          if (this.forecast.zipcode) {
            return this.forecast.zipcode
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getPropertyId () {
        return this.forecast.property_forecast?.property_id
      },
      getCity () {
        if (this.forecast) {
          if (this.forecast.city) {
            return this.forecast.city
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getFullAddress () {
        if (this.isProperty) {
          return this.getAddress1.trim() + ' ' + this.getAddress2
        } else {
          if (this.forecast) {
            return this.forecast.zipcode
          } else {
            return '--'
          }
        }
      },
      getAddress1 () {
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.address) {
            if (this.forecast.city && this.forecast.state && this.forecast.zipcode) {
              const toRemove = this.capitalize(this.forecast.city) + ' ' + this.forecast.state + ' ' + this.forecast.zipcode
              return this.forecast.property_forecast.address.replace(toRemove, '').trim() + ', '
            } else {
              return this.forecast.property_forecast.address
            }
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getAddress2 () {
        let address2 = ''
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.city) {
            address2 += this.capitalize(this.forecast.city) + ' '
          }
          if (this.forecast.state) {
            address2 += this.forecast.state + ' '
          }
          if (this.forecast.zipcode) {
            address2 += this.forecast.zipcode
          }
        }
        return address2
      },
      getForecastHomeValuePercent () {
        let result = '--'
        if (this.neighborhoodData) {
          if (this.neighborhoodData.percentage_change_forecasts) {
            if (this.neighborhoodData.percentage_change_forecasts[this.activeTimeframe].change) {
              if (parseFloat(this.neighborhoodData.percentage_change_forecasts[this.activeTimeframe].change) > 0) {
                result = '+'
              } else {
                result = ''
              }
              result += this.neighborhoodData.percentage_change_forecasts[this.activeTimeframe].change + '%'
            }
          }
        }
        return result
      },
      getMovingTrendsNetIn () {
        let result = '--'
        if (this.movingTrends) {
          if (this.movingTrends.net_in) {
            if (parseFloat(this.movingTrends.net_in[this.activeTimeframe].change) > 0) {
              result = '+'
            } else {
              result = ''
            }
            result += this.movingTrends.net_in[this.activeTimeframe].change + '%'
          }
        }
        return result
      },
      homeValuesChartOptions () {
        return {
          chart: {
            type: 'bar',
            toolbar: {
              show: false,
            },
          },
          colors: ['#ff7d1f', '#ff735e', '#24cb43'],
          plotOptions: {
            bar: {
              distributed: true,
              horizontal: true,
              borderRadius: 5,
            },
          },
          dataLabels: {
            enabled: false,
          },
          tooltip: {
            enabled: true,
          },
          xaxis: {
            show: false,
            categories: [this.getZipcode, 'Region', 'State'],
            labels: {
              show: false,
            },
            axisBorder: {
              show: false,
            },
            axisTicks: {
              show: false,
            },
          },
          yaxis: {
            show: true,
            labels: {
              show: true,
              style: {
                colors: ['#5a6778'],
                fontSize: '15px',
              },
            },
            axisBorder: {
              show: false,
            },
            axisTicks: {
              show: false,
            },
          },
          grid: {
            show: false,
            padding: {
              top: 0,
              right: 0,
              bottom: 0,
              left: 0,
            },
          },
          legend: {
            show: false,
          },
        }
      },
      homeValuesChartData () {
        let data = []
        if (this.neighborhoodData && this.surroundingZipData && this.stateData) {
          if (this.neighborhoodData.percentage_change_forecasts[this.activeTimeframe] &&
            this.surroundingZipData.percentage_change_forecasts[this.activeTimeframe] &&
            this.stateData.percentage_change_forecasts[this.activeTimeframe]) {
            if (this.neighborhoodData.percentage_change_forecasts[this.activeTimeframe].change &&
              this.surroundingZipData.percentage_change_forecasts[this.activeTimeframe].change &&
              this.stateData.percentage_change_forecasts[this.activeTimeframe].change) {
              data = [
                {
                  name: 'Home Value',
                  data: [
                    this.neighborhoodData.percentage_change_forecasts[this.activeTimeframe].change,
                    this.surroundingZipData.percentage_change_forecasts[this.activeTimeframe].change,
                    this.stateData.percentage_change_forecasts[this.activeTimeframe].change,
                  ],
                },
              ]
            }
          }
        }
        return data
      },
      avgPriceChartOptions () {
        return {
          chart: {
            type: 'line',
            toolbar: {
              show: false,
            },
          },
          colors: ['#018c45'],
          dataLabels: {
            enabled: false,
          },
          stroke: {
            curve: 'smooth',
          },
          xaxis: {
            categories: ['CURRENT', '3 MONTH', '1 YEAR', '2 YEAR', '3 YEAR'],
            labels: {
              show: true,
              style: {
                fontSize: '11px',
              },
            },
          },
          yaxis: {
            reversed: true,
            labels: {
              show: true,
              style: {
                colors: ['#ffffff'],
              },
            },
          },
          tooltip: {
            enabled: true,
            y: {
              formatter: function (value, { series, seriesIndex, dataPointIndex, w }) {
              if (value === null) {
                return '--'
              } else {
                return parseInt(value)
              }
            },
            },
          },
          legend: {
            show: false,
          },
        }
      },
      avgPriceChartData () {
        let data = []
        const temp = []
        if (this.neighborhoodData) {
          if (this.neighborhoodData.avg_value_state_ranking_forecasts && this.neighborhoodData.avg_value_national_ranking_forecasts) {
            if (this.activeChartType === 0) {
              for (let i = 0; i < this.neighborhoodData.avg_value_state_ranking_forecasts.length; i++) {
                temp.push(this.neighborhoodData.avg_value_state_ranking_forecasts[i].change)
              }
            } else if (this.activeChartType === 1) {
              for (let i = 0; i < this.neighborhoodData.avg_value_national_ranking_forecasts.length; i++) {
                temp.push(this.neighborhoodData.avg_value_national_ranking_forecasts[i].change)
              }
            }
            if (temp) {
              data = [
                {
                  name: 'Avg. Price Rank',
                  data: temp,
                },
              ]
            }
          }
        }
        return data
      },
      forecastAccess () {
        return this.forecast.access
      },
    },
    watch: {
      forecast: function () {
        if (this.forecast.neighborhood) {
          this.neighborhoodData = this.forecast.neighborhood
        }
        if (this.forecast.surrounding_zipcode) {
          this.surroundingZipData = this.forecast.surrounding_zipcode
        }
        if (this.forecast.state_statistics) {
          this.stateData = this.forecast.state_statistics
        }
        if (this.forecast.moving_trends) {
          this.movingTrends = this.forecast.moving_trends
        }
      },
    },
    mounted () {
      // forecast data
      if (this.forecast) {
        if (this.forecast.neighborhood) {
          this.neighborhoodData = this.forecast.neighborhood
        }
        if (this.forecast.surrounding_zipcode) {
          this.surroundingZipData = this.forecast.surrounding_zipcode
        }
        if (this.forecast.state_statistics) {
          this.stateData = this.forecast.state_statistics
        }
        if (this.forecast.moving_trends) {
          this.movingTrends = this.forecast.moving_trends
        }
      }
    },
    methods: {
      goToListings (zipcode) {
        window.location.href = '/listings?address=' + zipcode + '&offset=0'
      },
      goToSubscriptionPage () {
        window.location.href = '/smart-data-subscription'
      },
      displayForecastData (time) {
        // console.log('[DEV] Displaying neighborhood data for time: ' + time)
        if (time >= 0) {
          this.activeTimeframe = time
          const tabs = document.getElementsByClassName('neighborhood-tab')
          if (tabs) {
            for (let i = 0; i < tabs.length; i++) {
              if (i !== time) {
                tabs[i].classList.remove('active')
              } else {
                tabs[i].classList.add('active')
              }
            }
          }
        }
      },
      displayTabContent (tab) {
        if (tab >= 0) {
          const tabs = document.getElementsByClassName('forecast-wrapper-heading-tab')
          const tabContent = document.getElementsByClassName('forecast-tab-content')
          if (tabs && tabContent) {
            for (let i = 0; i < tabs.length; i++) {
              if (i !== tab) {
                tabs[i].classList.remove('active')
                tabContent[i].classList.remove('active')
              } else {
                tabs[i].classList.add('active')
                tabContent[i].classList.add('active')
              }
            }
          }
        }
      },
      getStatsTableData (value) {
        // console.log(value)
        let data = '--'
        if (value === 0) {
          data = 0
        } else if (value) {
          data = value
        }
        return data
      },
      displayChart (type) {
        if (type >= 0) {
          const btns = document.getElementsByClassName('forecast-chart-btn')
          if (btns) {
            this.activeChartType = type
            for (let i = 0; i < btns.length; i++) {
              if (i !== type) {
                btns[i].classList.remove('active')
              } else {
                btns[i].classList.add('active')
              }
            }
          }
        }
      },
      capitalize (string) {
        if (string) {
          let result = ''
          const strings = string.split(' ')
          for (let i = 0; i < strings.length; i++) {
            result += strings[i].charAt(0).toUpperCase() + strings[i].slice(1).toLowerCase()
            if (i !== strings.length - 1) {
              result += ' '
            }
          }
          return result
        } else {
          return string
        }
      },
      formatNumber (num, currency, rounding) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            if (currency) {
              if (rounding) {
                num = Math.round(num / 100) * 100
              }
              return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              return num
            }
          } else if (typeof (num) === 'string') {
            if (isNaN(num)) {
              return '--'
            } else {
              if (currency) {
                if (rounding) {
                  num = Math.round(parseFloat(num) / 100) * 100
                }
                return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
              } else {
                return num
              }
            }
          }
        } else {
          return '--'
        }
      },
      formatRanking (num) {
        let result = '--'
        if (num) {
          result = '#' + num
        }
        return result
      },
      showSubscriptionPopup () {
        this.showSubscription = true
      },
      showSingleSubscriptionPopup () {
        this.showSingleSubscription = true
      },
      toggleSubscriptionShow (value) {
        this.showSubscription = value
      },
      toggleSingleSubscriptionShow (value) {
        this.showSingleSubscription = value
      },
    },
  }
</script>
