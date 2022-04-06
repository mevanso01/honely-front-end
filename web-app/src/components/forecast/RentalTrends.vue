<template>
  <!-- eslint-disable -->
  <div class="forecast-section section-wrapper">
    <!-- forecast-heading-wrapper -->
    <div class="forecast-heading-wrapper">
      <div class="forecast-section-title">Rental Trends</div>
      <p class="forecast-address">Forecast for: <span class="mdi mdi-map-marker"></span> {{ getFullAddress }}</p>
    </div>
    <!-- /forecast-heading-wrapper -->
    
    <!-- forecast-rental-container -->
    <div class="honely-property-position-relative">
      <!-- <div v-if="!isCognitoUserLoggedIn" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Message prompting to login</p>
          <button class="bg-primary">Sign In</button>
        </div>
      </div> -->
      <div v-if="!isCognitoUserLoggedIn || !subscriptionFlag" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Please subscribe to view all of our statistics</p>
          <button class="bg-primary" @click="goToSubscriptionPage">Subscribe</button>
        </div>
      </div>
    <div class="forecast-rental-container" :class="!isCognitoUserLoggedIn || !subscriptionFlag?'blocked':''" >
      <div class="forecat-rental-data forecast-wrapper">
        <div class="forecast-wrapper-heading">
          Stats
        </div>
        <div class="forecast-wrapper-inner">
          <table class="table-data">
            <tr>
              <th width="25%">&nbsp;</th>
              <th width="25%" class="heading-bg">Zip Code</th>
              <th width="25%" class="heading-bg">Metro</th>
              <th width="25%" class="heading-bg">State</th>
            </tr>
            <tr>
              <td class="spacer">&nbsp;</td>
              <td class="spacer">&nbsp;</td>
              <td class="spacer">&nbsp;</td>
              <td class="spacer">&nbsp;</td>
            </tr>
            <tr>
              <td>Median Rent</td>
              <td v-if="rentalZip && rentalZip.median_rent">{{ formatNumber(rentalZip.median_rent, true, false) }}</td>
              <td v-else>--</td>
              <td v-if="rentalMetro && rentalMetro.median_rent">{{ formatNumber(rentalMetro.median_rent, true, false) }}</td>
              <td v-else>--</td>
              <td v-if="rentalState && rentalState.median_rent">{{ formatNumber(rentalState.median_rent, true, false) }}</td>
              <td v-else>--</td>
            </tr>
            <tr>
              <td>Population</td>
              <td v-if="rentalZip && rentalZip.population">{{ formatNumber(rentalZip.population, false, true) }}</td>
              <td v-else>--</td>
              <td v-if="rentalMetro && rentalMetro.population">{{ formatNumber(rentalMetro.population, false, true) }}</td>
              <td v-else>--</td>
              <td v-if="rentalState && rentalState.population">{{ formatNumber(rentalState.population, false, true) }}</td>
              <td v-else>--</td>
            </tr>
            <tr>
              <td>Cash Flow Ratio</td>
              <td v-if="rentalZip && rentalZip.cash_flow">{{ formatNumber(rentalZip.cash_flow, false, false) }}%</td>
              <td v-else>--</td>
              <td v-if="rentalMetro && rentalMetro.cash_flow">{{ formatNumber(rentalMetro.cash_flow, false, false) }}%</td>
              <td v-else>--</td>
              <td v-if="rentalState && rentalState.cash_flow">{{ formatNumber(rentalState.cash_flow, false, false) }}%</td>
              <td v-else>--</td>
            </tr>
          </table>
        </div>
      </div>
      <div class="forecast-wrapper">
        <div class="forecast-wrapper-heading">
          Average Yearly Rental Growth
        </div>
        <div class="forecast-wrapper-inner">
          <div class="chart-wrapper">
            <apexchart
              width="100%"
              height="250px"
              :options="rentalGrowthChartOption"
              :series="rentalGrowthChartData"
            />
          </div>
        </div>
      </div>
    </div>
    </div>
    <!-- /forecast-rental-container -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import { mapGetters } from 'vuex'
  export default {
    name: 'RentalTrends',
    components: {
    },
    props: {
      forecast: Object,
      rentalTrends: Object,
      subscriptionFlag: Boolean,
    },
    data () {
      return {
        // blocked: true,
        rentalZip: null,
        rentalMetro: null,
        rentalState: null,
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
      getState () {
        if (this.forecast) {
          if (this.forecast.state) {
            return this.forecast.state
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
      rentalGrowthChartOption () {
        const currentYear = new Date().getFullYear()
        const categories = [currentYear, currentYear + 2, currentYear + 4]

        return {
          chart: {
            type: 'line',
            toolbar: {
              show: false,
            },
          },
          colors: ['#1f2937', '#24cb43', '#ff7d1f'],
          dataLabels: {
            enabled: false,
          },
          stroke: {
            curve: 'straight',
          },
          xaxis: {
            categories: categories,
            labels: {
              show: true,
            },
            axisBorder: {
              show: true,
              color: '#36404e',
            },
          },
          yaxis: {
            labels: {
              show: true,
            },
            axisBorder: {
              show: true,
              color: '#36404e',
            },
          },
          tooltip: {
            enabled: true,
            y: {
              formatter: function (value, opts) {
                return value + '%'
              },
            },
          },
          grid: {
            show: false,
          },
          legend: {
            horizontalAlign: 'center',
          },
        }
      },
      rentalGrowthChartData () {
        const data = []
        if (this.rentalZip && this.rentalMetro && this.rentalState) {
          if (this.rentalZip.cagr1y && this.rentalZip.cagr3y && this.rentalZip.cagr5y) {
            data.push(
              {
                name: 'Zip code: ' + this.getZipcode,
                data: [this.rentalZip.cagr1y, this.rentalZip.cagr3y, this.rentalZip.cagr5y],
              },
            )
          }
          if (this.rentalMetro.cagr1y && this.rentalMetro.cagr3y && this.rentalMetro.cagr5y) {
            data.push(
              {
                name: 'Metro',
                data: [this.rentalMetro.cagr1y, this.rentalMetro.cagr3y, this.rentalMetro.cagr5y],
              },
            )
          }
          if (this.rentalState.cagr1y && this.rentalState.cagr3y && this.rentalState.cagr5y) {
            data.push(
              {
                name: 'State: ' + this.getState,
                data: [this.rentalState.cagr1y, this.rentalState.cagr3y, this.rentalState.cagr5y],
              },
            )
          }
        }
        return data
      },
    },
    watch: {
      rentalTrends: function () {
        if (this.rentalTrends) {
          this.rentalZip = this.rentalTrends.zip
          this.rentalMetro = this.rentalTrends.metro
          this.rentalState = this.rentalTrends.state
        }
      },
    },
    mounted () {
      if (this.rentalTrends) {
        this.rentalZip = this.rentalTrends.zip
        this.rentalMetro = this.rentalTrends.metro
        this.rentalState = this.rentalTrends.state
      }
    },
    methods: {
      goToSubscriptionPage () {
        window.location.href = '/smart-data-subscription'
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
      formatNumber (num, currency, comma) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            if (currency) {
              // num = Math.round(num / 100) * 100
              return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              if (comma) {
                return num.toLocaleString('en-US')
              } else {
                return num
              }
            }
          } else if (typeof (num) === 'string') {
            if (currency) {
              // num = Math.round(parseFloat(num) / 100) * 100
              return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              if (comma) {
                return parseFloat(num).toLocaleString('en-US')
              } else {
                return num
              }
            }
          }
        } else {
          return '--'
        }
      },
    },
  }
</script>
