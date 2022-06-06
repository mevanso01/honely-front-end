<template>
  <!-- eslint-disable -->
  <div class="forecast-section section-wrapper">
    <!-- forecast-heading-wrapper -->
    <div class="forecast-heading-super-wrapper">
    <div class="forecast-heading-wrapper">
      <div class="forecast-section-title">Honely Property Value Forecast</div>
      <p class="forecast-address">Property Found: <span class="mdi mdi-map-marker"></span> {{ getAddress1}} {{ getAddress2}}</p>
    </div>
    <div v-if="!isCognitoUserLoggedIn || !subscriptionFlag">
      <button class="bg-primary forecast-subscription-btn" @click="goToSubscriptionPage" >Subscribe for $9.99 a month for unlimited access to Forecast Data</button>
    </div>
    </div>
    <!-- /forecast-heading-wrapper -->

    <!-- forecast-content-wrapper -->
    <div class="forecast-content-wrapper flex-wrapper">
      <!-- forecast-property-img -->
      <div class="forecast-property-img-wrapper">
        <div
          class="forecast-property-img"
          v-bind:style="[ validImage ? { background: 'url(' + image + ')' } : { background: ''}]"
        >
          <div class="property-fav-btn" @click="toggleFavorite(property)">
            <span v-if="isFavorite" class="mdi mdi-heart"></span>
            <span v-else class="mdi mdi-heart-outline"></span>
          </div>
        </div>
        <p><span class="mdi mdi-map-marker"></span> {{ getAddress1}} {{ getAddress2}}</p>
      </div>
      <!-- /forecast-property-img -->

      <!-- forecast-property-data -->
      <div class="forecast-data">
        <p>Honely Estimate Value</p>
        <p class="forecast-value">{{ getHonelyEstimate }}</p>
        <p class="forecast-value-notes">Estimated range ({{ getHonelyEstimateRange }})</p>
        <!-- honely-forecast-container -->
        <div class="honely-forecast-container">
          <div class="honely-forecast-tabs">
            <span class="forecast-tab" @click="displayPropertyForecastData(0)">Current</span>
            <span class="forecast-tab active" @click="displayPropertyForecastData(1)">3 Month</span>
            <span class="forecast-tab" @click="displayPropertyForecastData(2)">1 Year</span>
            <span class="forecast-tab" @click="displayPropertyForecastData(3)">2 Year</span>
            <span class="forecast-tab" @click="displayPropertyForecastData(4)">3 Year</span>
          </div>
          <div class="honely-forecast-tab-content">
            <div class="honely-forecast-tab-content-title">{{ activeForecastTimeframeText }}</div>
            <div class="honely-forecast-data-row">
              <p>
                Value expected to: 
                <span class="forecast-status" :class="activeForecastStatus">{{ activeForecastPercent }}%</span>
                <span v-if="(activeForecastStatus === 'increase')" class="mdi mdi-chevron-up forecast-status-arrow up"></span>
                <span v-else-if="(activeForecastStatus === 'decrease')" class="mdi mdi-chevron-down forecast-status-arrow down"></span>
              </p>
            </div>
            <div class="honely-forecast-data-row">
              <p>Property value expected: <span class="forecast-status" :class="activeForecastStatus">{{ activeForecastValue }}</span></p>
            </div>
          </div>
        </div>
        <!-- /honely-forecast-container -->
      </div>
      <!-- /forecast-property-data -->
    </div>
    <!-- /forecast-content-wrapper -->

    <!-- honely-forecast-property-data -->
    <div class="honely-forecast-property-data">
      <div class="property-data">
        <span>{{ getPropertyBeds }}</span>|
        <span>{{ getPropertyBaths }}</span>| 
        <span>{{ getPropertySqft }}</span>
      </div>
      <div class="property-options">
        <div class="property-options-item" :class="subscriptionFlag?'':'honely-forecast-not-subscribed'" @click="showForecastReportModal">
          <span class="mdi mdi-file-chart"></span>
          <p>Generate Report</p>
        </div>
        <div class="property-options-item" @click="showHonelyCalculatorModal">
          <span class="mdi mdi-calculator"></span>
          <p>Enter most up-to-date property information to calculate most accurate estimate</p>
        </div>
      </div>
      <div class="property-link-wrapper">
        <router-link
          :to="{ name: 'ListingId', params: { search_id: forecast.property_forecast.property_id }}"
          class="property-link-btn"
          >
            View Property Details
        </router-link>
      </div>
    </div>
    <!-- /honely-forecast-property-data -->

    <!-- honely-property-zip-data -->
    <div class="honely-property-zip-data">
      <div class="forecast-section-title">Property Relative to Zip Code: {{ getZipcode }}</div>
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
      <div class="honely-property-zip-data-charts" :class="!isCognitoUserLoggedIn || !subscriptionFlag?'blocked':''" >
        <div class="chart-container">
          <p class="chart-tilte">
            <span class="mdi mdi-chart-bar"></span>
            Subject Property v. Similar Sized Properties
          </p>
          <p>The average {{ getAvgHomesizeRange }} sqft property value: <span class="forecast-value">{{ getAvgValueByHomesize }}</span></p>
          <p>This property is valued at: <span class="forecast-value">{{ getAvgPercentByHomesize }}</span></p>
          <div class="chart-wrapper">
            <apexchart
              width="100%"
              height="50px"
              :options="chartOptions"
              :series="propertyPercentBySizeData"
            />
          </div>
        </div>
        <div class="chart-container">
          <p class="chart-tilte">
            <span class="mdi mdi-chart-bar"></span>
            Price per SQft vs. Average Property
          </p>
          <p>The average price per SQft: <span class="forecast-value">{{ getAvgValuePerSqftInZip }}</span></p>
          <p>This property's Price Per SQft: <span class="forecast-value">{{ getPropertyPercentPerSqftInZip }}</span></p>
          <div class="chart-wrapper">
            <apexchart
              width="100%"
              height="50px"
              :options="chartOptions"
              :series="propertyPricePerSqftData"
            />
          </div>
        </div>
        <div class="chart-container">
          <p class="chart-tilte">
            <span class="mdi mdi-crosshairs-gps"></span>
            Subject Property v. Properties
          </p>
          <p>The average property value: <span class="forecast-value">{{ getAvgPropertyValueByZip }}</span></p>
          <p>This property is valued at: <span class="forecast-value">{{ getPropertyPercentageByZip }}</span></p>
          <div class="chart-wrapper">
            <apexchart
              width="100%"
              height="50px"
              :options="chartOptions"
              :series="propertyPercentByZipData"
            />
          </div>
        </div>
        <div class="chart-container">
          <p class="chart-tilte">
            <span class="mdi mdi-chart-bar"></span>
            Subject Property v. {{ getPropertyBeds }} Properties
          </p>
          <p>The average {{ getPropertyBeds }} property value: <span class="forecast-value">{{ getAvgPropertyValueByBeds }}</span></p>
          <p>This property is valued at: <span class="forecast-value">{{ getPropertyPercentByBeds }}</span></p>
          <div class="chart-wrapper">
            <apexchart
              width="100%"
              height="50px"
              :options="chartOptions"
              :series="propertyPercentByBedsData"
            />
          </div>
        </div>
      </div>
    </div>
    </div>
    <!-- /honely-property-zip-data -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapGetters } from 'vuex'

  export default {
    name: 'HonelyForeast',
    props: {
      forecast: Object,
      property: Object,
      propertyZipData: Object,
      subscriptionFlag: Boolean,
    },
    data () {
      return {
        // blocked: true,
        image: null,
        validImage: false,
        activeForecastStatus: '',
        activeForecastTimeframe: 1,
        activeForecastTimeframeText: '3 Month Honely Forecast',
        activeForecastPercent: 0,
        activeForecastValue: 0,
        forecastPercentageChange: [],
        forecastValueChange: [],
        forecastTimeframe: [
          'Current',
          '3 Month Honely Forecast',
          '1 Year Honely Forecast',
          '2 Years Honely Forecast',
          '3 Years Honely Forecast',
        ],
      }
    },
    computed: {
      ...mapGetters('listings', ['favoriteListings']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      isFavorite () {
        let found = false
        if (this.property) {
          if (this.favoriteListings && this.favoriteListings.length > 0) {
            for (let i = 0; i < this.favoriteListings.length; i++) {
              if (this.favoriteListings[i].property_id === this.property.address.property_id ||
                this.favoriteListings[i].apn === this.property.address.apn) {
                found = true
                break
              }
            }
          }
        }
        return found
      },
      getAddress1 () {
        if (this.property) {
          if (this.property.address.full_address) {
            if (this.property.address.city && this.property.address.state && this.property.address.zip_code) {
              const toRemove = this.capitalize(this.property.address.city) + ' ' + this.property.address.state + ' ' + this.property.address.zip_code
              return this.property.address.full_address.replace(toRemove, '').trim() + ', '
            } else {
              return this.property.address.full_address
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
        if (this.property) {
          if (this.property.address.city) {
            address2 += this.capitalize(this.property.address.city) + ' '
          }
          if (this.property.address.state) {
            address2 += this.property.address.state + ' '
          }
          if (this.property.address.zip_code) {
            address2 += this.property.address.zip_code
          }
        }
        return address2
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
      isOnSale () {
        if (this.property) {
          if (this.forecast && this.forecast.property_forecast) {
            if (this.forecast.property_forecast.property_status) {
              if (this.property_forecast.property_forecast.property_status === 'Active' || this.property_forecast.property_forecast.property_status === 'Sale') {
                return true
              } else {
                return false
              }
            } else {
              return false
            }
          } else {
            return false
          }
        } else {
          return false
        }
      },
      getListPrice () {
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.list_price) {
            return this.formatNumber(this.forecast.property_forecast.list_price, true)
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getHonelyEstimate () {
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.appraisal) {
            return this.formatNumber(this.forecast.property_forecast.appraisal, true)
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getHonelyEstimateRange () {
        let range = ''
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.appraisal_low) {
            range += this.formatNumber(this.forecast.property_forecast.appraisal_low, true) + ' - '
          }
          if (this.forecast.property_forecast.appraisal_high) {
            range += this.formatNumber(this.forecast.property_forecast.appraisal_high, true)
          }
        }
        return range
      },
      getPropertyBeds () {
        let fact = '-- bds'
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.beds_count) {
            fact = this.forecast.property_forecast.beds_count + ' bds'
          }
        }
        return fact
      },
      getPropertyBaths () {
        let fact = '-- ba'
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.baths) {
            fact = this.forecast.property_forecast.baths + ' ba'
          }
        }
        return fact
      },
      getPropertySqft () {
        let fact = '-- sqft'
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.total_area_sq_ft) {
            fact = this.formatNumber(this.forecast.property_forecast.total_area_sq_ft, false) + ' sqft'
          }
        }
        return fact
      },
      chartOptions () {
        return {
          chart: {
            type: 'bar',
            toolbar: {
                show: false,
            },
            sparkline: {
              enabled: true,
            },
          },
          chartOptions: {
            padding: {
              left: 0,
              right: 0,
            },
          },
          colors: ['#24cb43', '#ff7d1f'],
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
            enabled: false,
          },
          stroke: {
            show: false,
          },
          xaxis: {
            show: false,
            categories: ['Zip', 'Property'],
            labels: {
                show: false,
            },
            axisTicks: {
                show: false,
            },
            axisBorder: {
                show: false,
            },
          },
          yaxis: {
            show: false,
            labels: {
                show: false,
            },
            axisBorder: {
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
      getAvgHomesizeRange () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.properties_limit_range_in_sqft) {
            if (this.propertyZipData.properties_limit_range_in_sqft.lower_limit) {
              data = this.propertyZipData.properties_limit_range_in_sqft.lower_limit
            } else {
              data = '--'
            }
            if (this.propertyZipData.properties_limit_range_in_sqft.upper_limit) {
              data += '-' + this.propertyZipData.properties_limit_range_in_sqft.upper_limit
            } else {
              data = '--'
            }
          }
        }
        return data
      },
      getAvgValueByHomesize () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.average_property_value_by_homesize) {
            data = this.formatNumber(this.propertyZipData.average_property_value_by_homesize, true)
          }
        }
        return data
      },
      getAvgPercentByHomesize () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.average_percentage_by_homesize) {
            data = this.propertyZipData.average_percentage_by_homesize + '%'
            if (parseFloat(this.propertyZipData.average_percentage_by_homesize) > 0) {
              data += ' Above'
            } else if (parseFloat(this.propertyZipData.average_percentage_by_homesize) < 0) {
              data += ' Below'
            }
          }
        }
        return data
      },
      getAvgPropertyValueByZip () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.average_properties_value_by_zipcode) {
            data = this.formatNumber(this.propertyZipData.average_properties_value_by_zipcode, true)
          }
        }
        return data
      },
      getPropertyPercentageByZip () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.property_percentage_by_zipcode) {
            data = this.propertyZipData.property_percentage_by_zipcode + '%'
            if (parseFloat(this.propertyZipData.property_percentage_by_zipcode) > 0) {
              data += ' Above'
            } else if (parseFloat(this.propertyZipData.property_percentage_by_zipcode) < 0) {
              data += ' Below'
            }
          }
        }
        return data
      },
      getAvgValuePerSqftInZip () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.average_value_per_sqft_in_zipcode) {
            data = this.formatNumber(this.propertyZipData.average_value_per_sqft_in_zipcode, true)
          }
        }
        return data
      },
      getPropertyPercentPerSqftInZip () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.property_percentage_per_sqft) {
            data = this.propertyZipData.property_percentage_per_sqft + '%'
            if (parseFloat(this.propertyZipData.property_percentage_per_sqft ) > 0) {
              data += ' Above'
            } else if (parseFloat(this.propertyZipData.property_percentage_per_sqft ) < 0) {
              data += ' Below'
            }
          }
        }
        return data
      },
      getAvgPropertyValueByBeds () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.average_propertise_value_by_bedroom) {
            data = this.formatNumber(this.propertyZipData.average_propertise_value_by_bedroom, true)
          }
        }
        return data
      },
      getPropertyPercentByBeds () {
        let data = '--'
        if (this.propertyZipData) {
          if (this.propertyZipData.property_percentage_by_bedroom) {
            data = this.propertyZipData.property_percentage_by_bedroom + '%'
            if (parseFloat(this.propertyZipData.property_percentage_by_bedroom ) > 0) {
              data += ' Above'
            } else if (parseFloat(this.propertyZipData.property_percentage_by_bedroom ) < 0) {
              data += ' Below'
            }
          }
        }
        return data
      },
      propertyPercentBySizeData () {
        let data = []
        if (this.propertyZipData) {
          if (this.propertyZipData.average_percentage_by_homesize) {
            data = [
              {
                name: 'Property v. Size',
                data: [100, 100 + parseFloat(this.propertyZipData.average_percentage_by_homesize)],
              }
            ]
          }
        }
        return data
      },
      propertyPricePerSqftData () {
        let data = []
        if (this.propertyZipData) {
          if (this.propertyZipData.property_percentage_per_sqft) {
            data = [
              {
                name: 'Price per SQFT',
                data: [100, 100 + parseFloat(this.propertyZipData.property_percentage_per_sqft)],
              }
            ]
          }
        }
        return data
      },
      propertyPercentByZipData () {
        let data = []
        if (this.propertyZipData) {
          if (this.propertyZipData.property_percentage_by_zipcode) {
            data = [
              {
                name: 'Property v. Properties',
                data: [100, 100 + parseFloat(this.propertyZipData.property_percentage_by_zipcode)],
              }
            ]
          }
        }
        return data
      },
      propertyPercentByBedsData () {
        let data = []
        if (this.propertyZipData) {
          if (this.propertyZipData.property_percentage_by_bedroom) {
            data = [
              {
                name: 'Property v. Bedrooms',
                data: [100, 100 + parseFloat(this.propertyZipData.property_percentage_by_bedroom)],
              }
            ]
          }
        }
        return data
      },
    },
    updated () {
    },
    watch: {
      forecast: function () {
        // do something
      }
    },
    mounted () {
      // console.log(this.forecast)
      // console.log(this.propertyZipData)
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.$store.getters['auth/cognitoUser'].attributes.email,
          },
        }).then((response) => {
          this.user = response.data
          // this.prepopulateFields()
        }).catch((error) => {
          console.log('[ERROR] Failed to fetch user data', error)
          console.log(error.response.data.errorMessage)
        })
      }
      if (this.forecast && this.forecast.property_forecast) {
        this.activeForecastTimeframe = 1
        this.activeForecastTimeframeText = this.forecastTimeframe[1]
        this.forecastPercentageChange = this.forecast.property_forecast.percentage_change_forecasts
        this.forecastValueChange = this.forecast.property_forecast.value_change_forecasts
        this.activeForecastStatus = this.getForecastStatus(this.forecastPercentageChange[this.activeForecastTimeframe].change)
        this.activeForecastPercent = this.activeForecastStatus + ' ' + this.getForcastPercent(this.forecastPercentageChange[this.activeForecastTimeframe].change)
        this.activeForecastValue = this.formatNumber(this.forecastValueChange[this.activeForecastTimeframe].change, true)
      }
      this.checkImage()
    },
    created () {
    },
    methods: {
      goToSubscriptionPage () {
        window.location.href = '/smart-data-subscription'
      },
      checkImage () {
        const self = this
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.latitude && this.forecast.property_forecast.longitude) {
            const url = 'https://maps.googleapis.com/maps/api/streetview?size=600x400&location=' + this.forecast.property_forecast.latitude + ',' + this.forecast.property_forecast.longitude + '&radius=100&return_error_code=true&source=outdoor&key=' + this.$mapsKey
            // console.log(url)
            fetch(url, {
              method: 'GET',
              headers: {
              },
            }).then(function (response) {
              // console.log(response)
              if (!response.ok) {
                throw Error(response.status)
              }
              return response.blob
            }).then(function (blob) {
              self.image = url
              self.validImage = true
            }).catch((err) => {
              self.validImage = true
              self.image = '/site_images/listing_default_image.png'
              console.log('[ERROR] Google image API failed =>', err)
            })
          }
        }
      },
      getForecastStatus (value) {
        let style = ''
        if (value) {
          if (!isNaN(value)) {
            if (parseFloat(value) > 0) {
              style =  'increase'
            } else if (parseFloat(value) < 0) {
              style = 'decrease'
            }
          } 
        }
        return style
      },
      getForcastPercent (value) {
        let data = '--'
        if (value) {
          data = value
        }
        return data
      },
      getForecastStatusArrowIcon () {
        let icon = ''
        if (this.activeForecastStatus === 'increase') {
          icon = '<span class="mdi mdi-chevron-up forecast-status-up"></span>'
        } else if (this.activeForecastStatus === 'decrease') {
          icon = '<span class="mdi mdi-chevron-down forecast-status-down"></span>'
        }
        return icon
      },
      displayPropertyForecastData (timeframe) {
        if (this.forecastPercentageChange && this.forecastValueChange) {
          this.activeForecastStatus = this.getForecastStatus(this.forecastPercentageChange[timeframe].change)
          this.activeForecastPercent = this.activeForecastStatus + ' ' + this.getForcastPercent(this.forecastPercentageChange[timeframe].change)
          if (timeframe === 0) {
            this.activeForecastValue = this.getHonelyEstimate
          } else {
            this.activeForecastValue = this.formatNumber(this.forecastValueChange[timeframe].change, true)
          }
          
          this.activeForecastTimeframeText = this.forecastTimeframe[timeframe]
        }
        const tabs = document.getElementsByClassName('forecast-tab')
        if (tabs) {
          for (let i = 0; i < tabs.length; i++) {
            if (i !== timeframe) {
              tabs[i].classList.remove('active')
            } else {
              tabs[i].classList.add('active')
            }
          }
        }
      },
      toggleFavorite: function (listing) {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          if (!(!this.isFavorite && this.favoriteListings.length === 5)) {
            const payload = {
              property_id: listing.address.property_id,
              apn: listing.address.apn,
              fips: listing.address.fips,
              user_id: this.user.user_id,
              address: listing.address.full_address,
              appraisal: listing.valuation.appraisal,
            }
            if (!this.isFavorite) {
              const paramsTier2 = {
                first_name: this.user.first_name,
                last_name: this.user.last_name,
                user_email: this.user.email,
                phone_number: this.user.phone_number,
                address: listing.address.full_address,
                zip_code: listing.address.zip_code,
                tier: '2',
              }
              // axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier2)
            }
            this.$store.dispatch('listings/toggleFavorite', payload)
          } else {
            this.$alert('Cannot have more than 5 favorite listings.')
          }
        } else {
          this.$alert('Please log in or create an account to add this to your favorites.')
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
      formatNumber (num, currency) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            num = Math.round(num / 100) * 100
            if (currency) {
              return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              return num
            }
          } else if (typeof (num) === 'string') {
            num = Math.round(parseFloat(num) / 100) * 100
            if (currency) {
              return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              return num
            }
          }
        } else {
          return '--'
        }
      },
      showForecastReportModal () {
        if (this.subscriptionFlag) {
          document.getElementById('report-form-overlay').classList.add('active')
          window.dispatchEvent(new Event('resize'))
        } else {
          window.location.href = '/smart-data-subscription'
        }
      },
      showHonelyCalculatorModal () {
        document.getElementById('calculator-form-overlay').classList.add('active')
      },
    },
  }
</script>
