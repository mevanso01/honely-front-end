<template>
  <!-- eslint-disable -->
  <div class="pg-listings">
    <!-- listing-filters -->
    <listing-filters-row @filterListings="applyFiltersToListings" />
    <!-- /listing-filters -->

    <!-- listings-section - -->
    <div class="listings-section">
      <!-- listings-container -->
      <div class="listings-container">
        <section-loader
          :loading="loading"
        />
        <div v-if="listings && listings.length > 0" class="listings-property-list">
          <property-block
            v-for="(property, i) in listings"
            :property-data="property"
            :comparable="false"
            :compact="true"
            :key="i"
          />
        </div>
        <div v-else>
          <p class="msg-system">No listings available, please check your browser location settings or try a different zip code/address.</p>
        </div>
        <!-- pagination -->
        <div v-if="totalPages > 1" class="listings-pagination">
          <v-pagination
            :length="totalPages"
            v-model="page"
            circle
            total-visible="10"
            @input="changePage"
          >
          </v-pagination>
        </div>
        <!-- /pagination -->
      </div>
      <!-- /listings-container -->

      <!-- listings-map -->
      <div class="listings-map" id="listings-map">
      </div>
      <!-- /listings-map -->
    </div>
    <!-- /listings-section -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import Scriptjs from 'scriptjs'
  import { mapGetters } from 'vuex'
  import { bus } from '../../main'

  /* global google */

  export default {
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
      ListingFiltersRow: () => import('@/components/listing/ListingFiltersRow'),
      PropertyBlock: () => import('@/components/PropertyBlock'),
    },
    data: () => ({
      loading: false,
      user: null,
      isSavedSearch: false,
      savedSearchCount: null,
      page: 1,
      ready: false,
      showListings: true,
      query: '',
      center: { lat: 51.093048, lng: 6.842120 },
      markers: [
      ],
      order: '',
      sortOptions: [
        { order: 'newest.desc', name: 'Newest' },
        { order: 'price.desc', name: 'Price (high to low)' },
        { order: 'price.asc', name: 'Price (low to high)' },
        { order: 'bedrooms.desc', name: 'Bedrooms' },
        { order: 'bathrooms.desc', name: 'Bathrooms' },
        { order: 'square_feet.desc', name: 'Square Feet' },
      ],
      filterSqftMin: null,
      filterSqftMax: null,
      filterLotSizeMin: null,
      filterLotSizeMax: null,
      filterBedsMin: 'No Minimum',
      filterBedsMax: 'No Maximum',
      filterBathsMin: 'No Minimum',
      filterBathsMax: 'No Maximum',
      filterPriceMin: null,
      filterPriceMax: null,
      filterHomeTypeLand: null,
      filterHomeTypeApart: null,
      filterHomeTypeMultiFamily: null,
      filterHomeTypeSingleFamily: null,
      filterHomeTypeOthers: null,
      filterPropertyParking: null,
      filterPropertyGarage: null,
      filterPropertyForeclosed: null,
      filterPropertyPool: null,
      filterPropertyBasement: null,
      filterForecastedGrowthPercent: 0,
      filterForecastedGrowthValue: 0,
      filterForecastedYear: 0,
      filterPriceRange: 0,
      filterHomeTypes: [],
      filterPropertyAttributes: [],
      map: null,
      marker: null,
      infoWindow: null,
      // center around the Upper West Side, NYC
      DEFAULT_LAT: 40.780,
      DEFAULT_LNG: -73.974,
      geocoder: null,
      savedSearchId: null,
      lat: null,
      long: null,
    }),

    computed: {
      ...mapGetters('listings', ['allListings', 'totalResults', 'noAddressEntries', 'listingsError', 'computedZip']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      sortBy: function () {
        return this.order.split('.')[0]
      },
      sortOrder: function () {
        return this.order.split('.')[1]
      },
      listings: function () {
        return this.$store.getters['listings/allListings']
      },
      computed_zip: function () {
        return this.$store.getters['listings/computedZip']
      },
      totalPages: function () {
        var totalListings = this.$store.getters['listings/totalResults']
        // console.log('total listing: ' + totalListings)

        if (totalListings <= 0 || totalListings === 1 || totalListings === 20) {
          return 1
        } else if (totalListings > 600) {
          return 30
        } else {
          return Number(parseInt(totalListings / 20) + 1)
        }
      },
      isSingleListing () {
        const total = this.$store.getters['listings/totalResults']
        const filterCounts = document.getElementsByClassName('ma-2').length
        if (total) {
          if (total === 1 && filterCounts === 0) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
    },

    watch: {
      $route (to, from) {
        this.lat = this.$route.query.lat
        this.long = this.$route.query.long
        this.query = this.$route.query.address
        this.page = this.$route.query.offset + 1
        this.filterSqftMin = null
        this.filterSqftMax = null
        this.filterLotSizeMin = null
        this.filterLotSizeMax = null
        this.filterBedsMin = 'No Minimum'
        this.filterBedsMax = 'No Maximum'
        this.filterBathsMin = 'No Minimum'
        this.filterBathsMax = 'No Maximum'
        this.filterPriceMin = ''
        this.filterPriceMax = ''
        this.filterPriceRange = 0
        this.filterForecastedGrowthPercent = 0
        this.filterForecastedGrowthValue = 0
        this.filterForecastedYear = 1
        this.filterHomeTypes = []
        this.filterPropertyAttributes = []
        this.load()
      },
    },
    mounted () {
      Scriptjs('https://maps.googleapis.com/maps/api/js?key=' + this.$mapsKey, () => {
        this.initMap()
      })
      if (this.$store.getters['auth/loggedIn']) {
        this.$store.dispatch('listings/getFavorites')
      }
      // console.log(this.listings)
    },
    created () {
      this.lat = this.$route.query.lat
      this.long = this.$route.query.long
      this.query = this.$route.query.address
    },
    methods: {
      onPageChange (value) {
        console.log('vx: onPageChange and therefore load being called. this.page being set to ', value)
        this.page = value
        console.log('vx: this.query from onpagechange', this.query)
        this.load()
      },
      initMap () {
        this.map = new google.maps.Map(document.getElementById('listings-map'), {
          center: {
            lat: this.DEFAULT_LAT,
            lng: this.DEFAULT_LNG,
          },
        })
        this.infoWindow = new google.maps.InfoWindow({
          content: '',
        })
        const me = this
        google.maps.event.addListener(me.map, 'zoom_changed', function () {
          google.maps.event.addListener(me.map, 'bounds_changed', function (event) {
            if (this.getZoom() > 15) {
              this.setZoom(15)
              this.initialZoom = false
            }
          })
        })
        this.ready = true
        if (this.query) {
          this.geoCodeAddress(this.query)
        }
        this.load()
      },
      applyFiltersToListings (filtersApplied) {
        // console.log(filtersApplied)
        // updating filer data in root
        console.log('[INFO] Applying filters to listings......')
        if (filtersApplied) {
          if (filtersApplied.price_range) {
            this.filterPriceRange = filtersApplied.price_range.value
          } else {
            this.filterPriceRange = 0
          }
          if (filtersApplied.forecast_percent) {
            this.filterForecastedGrowthPercent = filtersApplied.forecast_percent.value
          } else {
            this.filterForecastedGrowthPercent = 0
          }
          if (filtersApplied.forecast_value) {
            this.filterForecastedGrowthValue = filtersApplied.forecast_value.value
          } else {
            this.filterForecastedGrowthValue = 0
          }
          if (filtersApplied.forecast_timeframe) {
            this.filterForecastedYear = filtersApplied.forecast_timeframe.value
          } else {
            this.filterForecastedYear = 0
          }
          if (filtersApplied.hometypes) {
            this.filterHomeTypes = filtersApplied.hometypes.value
          } else {
            this.filterHomeTypes = []
          }
          if (filtersApplied.beds_min) {
            this.filterBedsMin = filtersApplied.beds_min.value
          } else {
            this.filterBedsMin = 'No Minimum'
          }
          if (filtersApplied.beds_max) {
            this.filterBedsMax = filtersApplied.beds_max.value
          } else {
            this.filterBedsMax = 'No Maximum'
          }
          if (filtersApplied.baths_min) {
            this.filterBathsMin = filtersApplied.baths_min.value
          } else {
            this.filterBathsMin = 'No Minimum'
          }
          if (filtersApplied.baths_max) {
            this.filterBathsMax = filtersApplied.baths_max.value
          } else {
            this.filterBathsMax = 'No Maximum'
          }
          if (filtersApplied.attributes) {
            this.filterPropertyAttributes = filtersApplied.attributes.value
          } else {
            this.filterPropertyAttributes = []
          }
          this.load()
        }
      },
      load () {
        if (this.$useDevVariables) {
          // eslint-disable-next-line
          this.listings = [{"fips":"36061","apn":"00396-002017 05","unit_type":"APT","full_address":"626 E 14 ST APT 17 NEW YORK NY 10009 3334","longitude":-73.976781,"latitude":40.728591,"property_value":420000,"year_built":"1920.0","beds_count":null,"baths":null,"total_area_sq_ft":"65585.0","parking_spaces_count":null},{"fips":"36061","apn":"00558-1131","unit_type":"APT","full_address":"115 4 AVE APT 4J NEW YORK NY 10003 4908","longitude":-73.989573,"latitude":40.732812,"property_value":1856000,"year_built":null,"beds_count":null,"baths":null,"total_area_sq_ft":"1132.0","parking_spaces_count":null},{"fips":"36061","apn":"00576-00162G","unit_type":"APT","full_address":"42 W 13 ST APT 2G NEW YORK NY 10011 7921","longitude":-73.9964,"latitude":40.736027,"property_value":1764000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"31540.0","parking_spaces_count":null},{"fips":"36061","apn":"00693-1115","unit_type":"APT","full_address":"551 W 21 ST APT 5B NEW YORK NY 10011 2895","longitude":-74.007302,"latitude":40.747579,"property_value":2509000,"year_built":null,"beds_count":null,"baths":null,"total_area_sq_ft":null,"parking_spaces_count":null},{"fips":"36061","apn":"00826-1063","unit_type":"APT","full_address":"1107 BROADWAY APT 10A NEW YORK NY 10010 0012","longitude":-73.989581,"latitude":40.742717,"property_value":3410000,"year_built":"1915.0","beds_count":null,"baths":null,"total_area_sq_ft":"2205.0","parking_spaces_count":null},{"fips":"36061","apn":"00846-00642E","unit_type":"#","full_address":"18 E 18 ST # 2E NEW YORK NY 10003 1910","longitude":-73.99078100000001,"latitude":40.737858,"property_value":2716000,"year_built":"1900.0","beds_count":null,"baths":null,"total_area_sq_ft":"36400.0","parking_spaces_count":null},{"fips":"36061","apn":"00892-002510A","unit_type":"#","full_address":"273 LEXINGTON AVE # 10A NEW YORK NY 10016 3540","longitude":-73.978376,"latitude":40.74775800000001,"property_value":626000,"year_built":"1966.0","beds_count":null,"baths":null,"total_area_sq_ft":"129964.0","parking_spaces_count":null},{"fips":"36061","apn":"00893-004121H","unit_type":"#","full_address":"560 3 AVE # 21H NEW YORK NY 10016 3117","longitude":-73.976864,"latitude":40.748094,"property_value":699000,"year_built":"1975.0","beds_count":null,"baths":null,"total_area_sq_ft":"329706.0","parking_spaces_count":null},{"fips":"36061","apn":"01047-1468","unit_type":"APT","full_address":"353 W 56 ST APT 2C NEW YORK NY 10019 3773","longitude":-73.985076,"latitude":40.767173,"property_value":603000,"year_built":"1931.0","beds_count":null,"baths":null,"total_area_sq_ft":"534.0","parking_spaces_count":null},{"fips":"36061","apn":"01066-00372O","unit_type":"APT","full_address":"408 W 57 ST APT 2O NEW YORK NY 10019 3018","longitude":-73.986621,"latitude":40.767949,"property_value":457000,"year_built":"1957.0","beds_count":null,"baths":null,"total_area_sq_ft":"104966.0","parking_spaces_count":null},{"fips":"36061","apn":"01124-00058D","unit_type":"APT","full_address":"59 W 71 ST APT 8D NEW YORK NY 10023 4115","longitude":-73.978514,"latitude":40.776651,"property_value":998000,"year_built":"1924.0","beds_count":null,"baths":null,"total_area_sq_ft":"54976.0","parking_spaces_count":null},{"fips":"36061","apn":"01147-10036G","unit_type":"#","full_address":"161 W 75 ST # 6G NEW YORK NY 10023 1801","longitude":-73.979637,"latitude":40.780676,"property_value":1381000,"year_built":"1925.0","beds_count":null,"baths":null,"total_area_sq_ft":"181976.0","parking_spaces_count":null},{"fips":"36061","apn":"01242-0062 02","unit_type":"#","full_address":"706 W END AVE # SUPER1A NEW YORK NY 10025 6496","longitude":-73.973569,"latitude":40.794203,"property_value":574000,"year_built":"1928.0","beds_count":null,"baths":null,"total_area_sq_ft":"99494.0","parking_spaces_count":null},{"fips":"36061","apn":"01343-100211A","unit_type":"APT","full_address":"319 E 50 ST APT 11A NEW YORK NY 10022 7948","longitude":-73.967511,"latitude":40.754762,"property_value":524000,"year_built":"1931.0","beds_count":null,"baths":null,"total_area_sq_ft":"79062.0","parking_spaces_count":null},{"fips":"36061","apn":"01407-003314E","unit_type":"#","full_address":"1250 3 AVE # 14E NEW YORK NY 10021 ","longitude":-73.960649,"latitude":40.770244,"property_value":831000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"326054.0","parking_spaces_count":null},{"fips":"36061","apn":"01487-1303","unit_type":"APT","full_address":"530 E 76 ST APT 18H NEW YORK NY 10021 3159","longitude":-73.950085,"latitude":40.768188,"property_value":1511000,"year_built":"1986.0","beds_count":null,"baths":null,"total_area_sq_ft":"1007.0","parking_spaces_count":null},{"fips":"36061","apn":"01492-00612B","unit_type":"APT","full_address":"18 E 81 ST APT 2B NEW YORK NY 10028 0232","longitude":-73.96185799999998,"latitude":40.777682,"property_value":928000,"year_built":"1930.0","beds_count":null,"baths":null,"total_area_sq_ft":"13050.0","parking_spaces_count":null},{"fips":"36061","apn":"01527-1023","unit_type":"APT","full_address":"1441 3 AVE APT 9C NEW YORK NY 10028 1976","longitude":-73.955859,"latitude":40.775694,"property_value":1481000,"year_built":"1984.0","beds_count":null,"baths":null,"total_area_sq_ft":"1220.0","parking_spaces_count":null},{"fips":"36061","apn":"01551-00443B","unit_type":"APT","full_address":"312 E 89 ST APT 3B NEW YORK NY 10128 5095","longitude":-73.949572,"latitude":40.779235,"property_value":337000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"9165.0","parking_spaces_count":null},{"fips":"36061","apn":"01566-00142C","unit_type":"#","full_address":"431 E 86 ST # 2C NEW YORK NY 10028 ","longitude":-73.94789200000002,"latitude":40.776591,"property_value":1173000,"year_built":"1910.0","beds_count":null,"baths":null,"total_area_sq_ft":"33500.0","parking_spaces_count":null}]
          this.totalResults = this.listings.length
          this.setMarkers()
        } else {
          this.loading = true
          this.$store.dispatch('listings/getListings', {
            query: this.query,
            computed_zip: this.computed_zip,
            offset: this.page - 1,
            sortBy: this.sortBy,
            sortOrder: this.sortOrder,
            squareFeetMin: this.filterSqftMin,
            squareFeetMax: this.filterSqftMax,
            lotSizeMin: this.filterLotSizeMin,
            lotSizeMax: this.filterLotSizeMax,
            numBedroomsMin: this.filterBedsMin,
            numBedroomsMax: this.filterBedsMax,
            numBathroomsMin: this.filterBathsMin,
            numBathroomsMax: this.filterBedsMax,
            priceMin: this.filterPriceMin,
            priceMax: this.filterPriceMax,
            forecastedGrowthPercent: this.filterForecastedGrowthPercent,
            forecastedGrowthDollar: this.filterForecastedGrowthValue,
            forecastedYear: this.forecastedYearValue,
            priceRange: this.filterPriceRange,
            homeTypes: this.filterHomeTypes,
            propertyDetails: this.filterPropertyAttributes,
            lat: this.lat,
            long: this.long,
          })
            .then(() => {
              this.loading = false
              /* console.log('query: ' + this.query)
              console.log('computed_zip: ' + this.computed_zip)
              console.log('offset: ' + this.page)
              console.log('sortBy: ' + this.sortBy)
              console.log('sortOrder: ' + this.sortOrder)
              console.log('squareFeetMin: ' + this.filterSqftMin)
              console.log('squareFeeMax: ' + this.filterSqftMax)
              console.log('lotSizeMin: ' + this.filterLotSizeMin)
              console.log('lotSizeMax: ' + this.filterLotSizeMax)
              console.log('numBedroomsMin: ' + this.filterBedsMin)
              console.log('numBedroomsMax: ' + this.filterBedsMax)
              console.log('numBathroomsMin: ' + this.filterBathsMin)
              console.log('numBathroomsMax: ' + this.filterBathsMax)
              console.log('priceMin: ' + this.filterPriceMin)
              console.log('priceMax: ' + this.filterPriceMax)
              console.log('forecastedGrowthPercent: ' + this.filterForecastedGrowthPercent)
              console.log('forecastGrowthDollar: ' + this.filterForecastedGrowthValue)
              console.log('forecastedYear: ' + this.filterForecastedYear)
              console.log('priceRange: ' + this.filterPriceRange)
              console.log('homeTypes: ' + this.filterHomeTypes)
              console.log('propertyDetails: ' + this.filterPropertyAttributes)
              console.log('lat: ' + this.lat)
              console.log('long: ' + this.long) */
              // console.log(this.listings)
              this.loading = false
              if (this.listings.length > 0) {
                this.setMarkers()
              } else {
                this.geoCodeAddress(this.query)
              }
            })
          if (this.$useDevVariables) {
            this.user = {
              user_id: null,
              first_name: 'Sample',
              last_name: 'Sample',
              email: null,
              phone_number: null,
              lookup_count: null,
              membership_type: null,
            }
          } else {
            if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
              axios.get('https://api.honely.com/lookup/user_profile', {
                params: {
                  email: this.$store.getters['auth/cognitoUser'].attributes.email,
                },
              }).then((response) => {
                this.user = response.data
                this.getSavedSearches()
              }).catch((error) => {
                console.log('[ERROR] Failed to fetch user data', error)
                console.log(error.response.data.errorMessage)
              })
            }
          }
        }
      },
      setMarkers () {
        this.markers.forEach(function (marker) {
          marker.setMap(null)
        })
        this.markers = []
        this.listings.forEach(function (listing) {
          listing.additional_images = null
          this.addMarker(parseFloat(listing.latitude), parseFloat(listing.longitude), listing)
        }, this)

        const me = this
        setTimeout(function () {
          var bounds = new google.maps.LatLngBounds()
          me.markers.forEach(function (marker) {
            bounds.extend(marker.getPosition())
          })
          me.map.fitBounds(bounds)
        }, 500)
      },
      onAddressChange (value) {
        this.query = value
        this.geoCodeAddress(value)
        this.load()
      },
      geoCodeAddress (address, price = null, addMarker = false) {
        var me = this
        if (!this.geocoder) {
          this.geocoder = new google.maps.Geocoder()
        }
        this.geocoder.geocode({ address: address }, function (results, status) {
          if (status === 'OK') {
            me.map.setCenter(results[0].geometry.location)
          } else {
            console.log(address + 'Geocode was not successful for the following reason: ' + status)
          }
        })
      },
      addMarker (latitude, longitude, listing) {
        if (this.ready) {
          if (latitude && longitude) {
            const map = this.map

            const marker = new google.maps.Marker({
              position: { lat: latitude, lng: longitude },
              label: '',
              map: map,
            })

            let contentString = '<a style="text-decoration: none" href= "/listing/' + listing.property_id + '"><div class="card-pricing">'
            if (listing.full_address) {
              contentString += '<div  class="addressinfo">' + listing.full_address + '</div>'
            }
            if (listing.appraisal) {
              contentString += '<span class="priceline pl-valuation">$' + this.formatPrice(listing.appraisal) + ' <span class="pricecat">Honely Estimated Value</span></span>'
            }

            contentString += '</div></a>'
            var me = this

            if (this.$vuetify.breakpoint.xs) {
              marker.addListener('click', () => {
                me.infoWindow.setContent(contentString)
                me.infoWindow.open({
                  anchor: marker,
                  map,
                  shouldFocus: false,
                })
              })
            } else {
              marker.addListener('mouseover', () => {
                me.infoWindow.setContent(contentString)

                me.infoWindow.open({
                  anchor: marker,
                  map,
                  shouldFocus: false,
                })
              })
            }
            this.markers.push(marker)
          }
        } else {
          const me = this
          setTimeout(function () {
            me.addMarker(latitude, longitude, listing)
          }, 500)
        }
      },
      changePage (toPage) {
        this.page = toPage
        this.load()
      },
      getSavedSearches () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          axios.get('https://api.honely.com/lookup/saved_searches?user_id=' + this.user.user_id)
            .then((response) => {
              const row = response.data.find(item => item.search === this.query)
              if (row) {
                this.isSavedSearch = true
              } else {
                this.isSavedSearch = false
              }
              this.savedSearchCount = response.data.length
            })
            .catch((error) => {
              console.log('Error fetching saved searches: ', error)
            })
        }
      },
      onToggleSearch () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          if (this.isSavedSearch) {
            this.removeSaveSearch()
          } else {
            if (this.savedSearchCount < 20) {
              this.saveSearch()
            } else {
              bus.$emit('showError', { message: 'You have reached the maximum limit of saved searches (20).' })
            }
          }
        } else {
          bus.$emit('showError', { message: 'Please log in or create an account to save this search' })
        }
      },
      saveSearch () {
        const params = {
          user_id: this.user.user_id,
          search: this.query,
        }
        axios.post('https://api.honely.com/lookup/insert_searches', params)
          .then(() => {
            this.isSavedSearch = true
            this.savedSearchCount = this.savedSearchCount + 1
          })
          .catch(err => {
            console.log('Error inserting saved search', err)
          })
      },
      removeSaveSearch () {
        const params = {
          user_id: this.user.user_id,
          search: this.query,
        }
        axios.delete('https://api.honely.com/lookup/delete_searches', { data: params })
          .then(() => {
            this.isSavedSearch = false
            this.savedSearchCount = this.savedSearchCount - 1
          })
          .catch((err) => {
            console.log('Error deleting saved search', err)
          })
      },

      formatPrice (value) {
        if (value) {
          value = value.toString().split('.')[0]
          value = parseInt(value)
          value = (value % 100 >= 50) ? Math.ceil(value / 100) * 100 : Math.floor(value / 100) * 100
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
      removeFilter (item) {
        if (item.indexOf('Min Beds') !== -1) {
          this.numBedroomsMin = 'No Minimum'
        }
        if (item.indexOf('Max Beds') !== -1) {
          this.numBedroomsMax = 'No Maximum'
        }
        if (item.indexOf('Min Baths') !== -1) {
          this.numBathroomsMin = 'No Minimum'
        }
        if (item.indexOf('Max Baths') !== -1) {
          this.numBathroomsMax = 'No Maximum'
        }
        if (item === 'Single Family') {
          this.homeTypeSingleFamily = null
        }
        if (item === 'Multi Family') {
          this.homeTypeMultiFamily = null
        }
        if (item === 'Land') {
          this.homeTypeLand = null
        }
        if (item === 'Apartment') {
          this.homeTypeApartment = null
        }
        if (item === 'Others') {
          this.homeTypeOthers = null
        }
        if (item === 'Parking Spot') {
          this.propertyDetailsParkingSpot = null
        }
        if (item === 'Garage') {
          this.propertyDetailsGarage = null
        }
        if (item === 'Foreclosed') {
          this.propertyDetailsForeclosed = null
        }
        if (item === 'Pool') {
          this.propertyDetailsPool = null
        }
        if (item === 'Basement') {
          this.propertyDetailsBasement = null
        }
        if (item.indexOf('yr % growth') !== -1) {
          this.forecastedGrowthPercent = 0
        }
        if (item.indexOf('yr $ growth') !== -1) {
          this.forecastedGrowthDollar = 0
        }
        if (item.indexOf('Valuation') !== -1) {
          this.priceRange = 0
        }
        this.load()
      },
    },
  }
</script>
