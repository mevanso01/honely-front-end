<template>
  <!-- eslint-disable -->
  <div class="forecast-section section-wrapper">
    <!-- forecast-heading-wrapper -->
    <div class="forecast-heading-wrapper">
      <div class="forecast-section-title">Compare Deals With Our Buyer's Score</div>
      <p class="forecast-address"><span class="mdi mdi-map-marker"></span> {{ getFullAddress }}</p>
    </div>
    <!-- /forecast-heading-wrapper -->

    <!-- forecast-buyer-score -->
    <div class="forecast-buyer-score">
      <buyer-score-block
        :forecast="forecast"
      />
    </div>
    <!-- /forecast-buyer-score -->

    <!-- forecast-buyer-score-listings -->
    <div v-if="properties && properties.length > 0" class="forecast-buyer-score-listings">
      <div class="buyer-score-property"
        v-for="(property, i) in properties"
      >
        <div class="buyer-score-property-action-row">
          <div class="buyer-score-property-address">
            <span class="mdi mdi-map-marker"></span> {{ getPropertyAddress(property) }}
          </div>
          <div class="buyer-score-property-action" @click="removeProperty(i)">
            <span class="mdi mdi-close"></span>
          </div>
        </div>
        <buyer-score-block
        :forecast="property"
        :key="i"
        />
      </div>
    </div>
    <!-- /forecast-buyer-score-listings -->

    <!-- forecast-buyer-score-search -->
    <div class="forecast-buyer-score-search">
      <section-loader
        :loading="loading"
        :noBackground="false"
      />
      <div class="forecast-buyer-score-search-wrapper">
        <div class="buyer-score-search-heading">Compare Property</div>
        <honely-search-simple @appendProperties="appendProperties" @loading="setLoading" />
      </div>
    </div>
    <!-- /forecast-buyer-score-search -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'BuyerScore',
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
      BuyerScoreBlock: () => import('@/components/forecast/BuyerScoreBlock'),
      HonelySearchSimple: () => import('@/components/HonelySearchSimple'),
    },
    props: {
      forecast: Object,
    },
    data () {
      return {
        loading: false,
        properties: [],
      }
    },
    computed: {
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
    },
    mounted () {
      this.properties = []
    },
    methods: {
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
      getPropertyAddress (forecast) {
        let address = '--'
        if (forecast && forecast.property_forecast) {
          if (forecast.property_forecast.address) {
            address = forecast.property_forecast.address
          }
        }
        return address
      },
      setLoading (loading) {
        this.loading = loading
      },
      appendProperties (forecast) {
        this.properties.push(forecast)
      },
      removeProperty (index) {
        if (index >= 0) {
          if (index < this.properties.length) {
            this.properties.splice(index, 1)
          }
        }
      },
    },
  }
</script>
