<template>
  <!-- eslint-disable -->
  <div class="section bg-secondary">
    <section-loader
      :loading="loading"
      :noBackground="true"
    />
    <div class="home-locals-container">
      <p class="feature-text text-exlarge">Homes in your area</p>
      <p class="feature-text text-small">You can search for homes that meet your requirements but will also grow in value. Filter by location and specification but also by forecasted growth rates.</p>
      <!-- home-local-properties -->
      <div class="home-local-properties">
        <div v-if="topProperties && topProperties.length > 0" class="home-local-property-list">
          <property-block
            v-for="(property, i) in topProperties"
            :property-data="property"
            :comparable="false"
            :compact="false"
            :key="i"
          />
        </div>
        <div v-else class="home-local-property-msg">
          No nearby properties, please check your browser location settings.
        </div>
        <div v-if="topProperties" class="home-local-property-more">
          <p><a :href="getMoreListingsLink">See more results</a></p>
        </div>
      </div>
      <!-- /home-local-properties -->
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import Vue from 'vue'
  import VueGeolocation from 'vue-browser-geolocation'

  Vue.use(VueGeolocation)

  export default {
    name: 'HomeLoals',
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
      PropertyBlock: () => import('@/components/PropertyBlock'),
    },
    data: () => ({
      loading: false,
      properties: [],
      topProperties: [],
      geolocationEnable: false,
      latitude: null,
      longitude: null,
    }),
    computed: {
      getMoreListingsLink () {
        if (this.latitude && this.longitude) {
          return '/listings?lat=' + this.latitude + '&long=' + this.longitude
        } else {
          return '/listings'
        }
      },
    },
    mounted () {
      this.locatorButtonPressed()
    },
    methods: {
      locatorButtonPressed () {
        this.loading = true
        this.$getLocation()
          .then(coordinates => {
                  this.latitude = coordinates.lat
                  this.longitude = coordinates.lng
                  this.geolocationEnable = true
                  this.load()
                },
                error => {
                  this.load('', '')
                  console.log(error)
                },
          )
      },
      reloadPage () {
        this.locatorButtonPressed()
      },
      load () {
        this.loading = true
        let apiURL = 'https://listhub.honely.com/locate/listhub_listings?ipConfig=yes'
        if (this.latitude && this.longitude) {
          apiURL += '&lat=' + this.latitude + '&long=' + this.longitude
        }
        // console.log(apiURL)
        // fetching the local properties
        fetch(apiURL)
          .then(response => response.json())
          .then((data) => {
            let listhubProperties = []
            if (data.rows) {
              listhubProperties = data.rows
            }
            let faProperties = []
            if (data.first_american) {
              faProperties = data.first_american
            }
            // add both properties to the properties
            for (let i = 0; i < listhubProperties.length; i++) {
              this.properties.push(listhubProperties[i])
            }
            for (let i = 0; i < faProperties.length; i++) {
              this.properties.push(faProperties[i])
            }
            // assign top properties
            if (this.properties && this.properties.length > 0) {
              if (this.properties.length >= 3) {
                for (let i = 0; i < 3; i++) {
                  this.topProperties.push(this.properties[i])
                }
              } else {
                for (let i = 0; i < this.properties.length; i++) {
                  this.topProperties.push(this.properties[i])
                }
              }
            }
            this.totalResults = data.total
            this.loading = false
          })
          .catch((error) => {
            this.loading = false
            console.warn(error)
            return null
          })
      },
    },
  }
</script>
