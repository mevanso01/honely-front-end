<template>
  <v-container
    id="search-listings"
    fluid
    class="mt-3"
  >
    <v-overlay :value="loading">
      <v-progress-circular
        indeterminate
        size="64"
      />
    </v-overlay>
    <v-row
      v-if="showListings"
    >
      <v-col
        cols="10"
        class="green-buttons"
      >
        <v-btn
          text
        >
          ForecastingX
        </v-btn>
        <v-btn
          text
        >
          Market Info
        </v-btn>
        <v-btn
          text
        >
          Bed & Bath
        </v-btn>
        <v-btn
          text
        >
          Home Type
        </v-btn>
        <v-btn
          text
        >
          Property Details
        </v-btn>
      </v-col>
      <v-spacer />
      <v-col
        cols="2"
        class="squash-buttons text-center"
      >
        <v-btn
          text
          elevation="3"
        >
          Save This Search
          <v-icon>
            {{ 'mdi-heart-outline' }}
          </v-icon>
        </v-btn>
      </v-col>
    </v-row>
    <v-row
      id="search-listings-content"
    >
      <v-col
        id="map-col"
        cols="8"
        class="d-flex"
      >
        <div id="map" />
      </v-col>
      <v-col
        cols="4"
        class="found-properties-column d-flex flex-column"
      >
        <v-row>
          <v-col
            cols="12"
            class="pb-0"
          >
            <div
              class="found-properties-header"
            >
              <h2>
                &ldquo;{{ query }}&rdquo;
                <br><span>{{ totalResults }} results</span>
              </h2>
              <v-select
                :items="sortOptions"
                label="Sort by"
              />
            </div>
          </v-col>
        </v-row>
        <v-row
          v-for="item in listings"
          :key="item.id"
          class="listings-item-row"
        >
          <v-col
            cols="12"
          >
            <property-listings-card :item="item" />
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import axios from 'axios'
  import SearchSuggestions from '@/components/SearchSuggestions'
  import Scriptjs from 'scriptjs'

  /* global google */

  export default {
    components: {
      SearchSuggestions,
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
    },

    data: () => ({
      loading: false,
      ready: false,
      showListings: true,
      listings: [],
      totalResults: '',
      query: '',
      center: { lat: 51.093048, lng: 6.842120 },
      markers: [
      ],
      sortOptions: ['Newest', 'Price (high to low)', 'Price (low to high)', 'Bedrooms', 'Bathrooms', 'Square Feet', 'Lot Size'],

      map: null,
      marker: null,

      // center around the Upper West Side, NYC
      DEFAULT_LAT: 40.780,
      DEFAULT_LNG: -73.974,

      mapsKey: 'AIzaSyClIFG-ONBwyXrn4_kaA4yMYHGpZD5EEko',

      geocoder: null,

    }),

    mounted () {
      Scriptjs('https://maps.googleapis.com/maps/api/js?key=' + this.mapsKey, () => {
        this.initMap()
      })
    },

    created () {
      this.query = this.$route.query.address
    },

    methods: {
      initMap () {
        // const me = this

        this.map = new google.maps.Map(document.getElementById('map'), {
          center: {
            lat: this.DEFAULT_LAT,
            lng: this.DEFAULT_LNG,
          },
          zoom: 10,
        })

        this.ready = true
        this.geoCodeAddress(this.query)
        this.load()
      },
      load () {
        if (this.query) {
          this.loading = true
          // axios.get('/api/v1/listings', {
          //   params: {
          //     address: this.query,
          //   },
          // }).then((response) => {
          //   this.listings = response.data.rows
          // eslint-disable-next-line
          this.listings = [{"fips":"36061","apn":"00396-002017 05","unit_type":"APT","full_address":"626 E 14 ST APT 17 NEW YORK NY 10009 3334","longitude":-73.976781,"latitude":40.728591,"property_value":420000,"year_built":"1920.0","beds_count":null,"baths":null,"total_area_sq_ft":"65585.0","parking_spaces_count":null},{"fips":"36061","apn":"00558-1131","unit_type":"APT","full_address":"115 4 AVE APT 4J NEW YORK NY 10003 4908","longitude":-73.989573,"latitude":40.732812,"property_value":1856000,"year_built":null,"beds_count":null,"baths":null,"total_area_sq_ft":"1132.0","parking_spaces_count":null},{"fips":"36061","apn":"00576-00162G","unit_type":"APT","full_address":"42 W 13 ST APT 2G NEW YORK NY 10011 7921","longitude":-73.9964,"latitude":40.736027,"property_value":1764000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"31540.0","parking_spaces_count":null},{"fips":"36061","apn":"00693-1115","unit_type":"APT","full_address":"551 W 21 ST APT 5B NEW YORK NY 10011 2895","longitude":-74.007302,"latitude":40.747579,"property_value":2509000,"year_built":null,"beds_count":null,"baths":null,"total_area_sq_ft":null,"parking_spaces_count":null},{"fips":"36061","apn":"00826-1063","unit_type":"APT","full_address":"1107 BROADWAY APT 10A NEW YORK NY 10010 0012","longitude":-73.989581,"latitude":40.742717,"property_value":3410000,"year_built":"1915.0","beds_count":null,"baths":null,"total_area_sq_ft":"2205.0","parking_spaces_count":null},{"fips":"36061","apn":"00846-00642E","unit_type":"#","full_address":"18 E 18 ST # 2E NEW YORK NY 10003 1910","longitude":-73.99078100000001,"latitude":40.737858,"property_value":2716000,"year_built":"1900.0","beds_count":null,"baths":null,"total_area_sq_ft":"36400.0","parking_spaces_count":null},{"fips":"36061","apn":"00892-002510A","unit_type":"#","full_address":"273 LEXINGTON AVE # 10A NEW YORK NY 10016 3540","longitude":-73.978376,"latitude":40.74775800000001,"property_value":626000,"year_built":"1966.0","beds_count":null,"baths":null,"total_area_sq_ft":"129964.0","parking_spaces_count":null},{"fips":"36061","apn":"00893-004121H","unit_type":"#","full_address":"560 3 AVE # 21H NEW YORK NY 10016 3117","longitude":-73.976864,"latitude":40.748094,"property_value":699000,"year_built":"1975.0","beds_count":null,"baths":null,"total_area_sq_ft":"329706.0","parking_spaces_count":null},{"fips":"36061","apn":"01047-1468","unit_type":"APT","full_address":"353 W 56 ST APT 2C NEW YORK NY 10019 3773","longitude":-73.985076,"latitude":40.767173,"property_value":603000,"year_built":"1931.0","beds_count":null,"baths":null,"total_area_sq_ft":"534.0","parking_spaces_count":null},{"fips":"36061","apn":"01066-00372O","unit_type":"APT","full_address":"408 W 57 ST APT 2O NEW YORK NY 10019 3018","longitude":-73.986621,"latitude":40.767949,"property_value":457000,"year_built":"1957.0","beds_count":null,"baths":null,"total_area_sq_ft":"104966.0","parking_spaces_count":null},{"fips":"36061","apn":"01124-00058D","unit_type":"APT","full_address":"59 W 71 ST APT 8D NEW YORK NY 10023 4115","longitude":-73.978514,"latitude":40.776651,"property_value":998000,"year_built":"1924.0","beds_count":null,"baths":null,"total_area_sq_ft":"54976.0","parking_spaces_count":null},{"fips":"36061","apn":"01147-10036G","unit_type":"#","full_address":"161 W 75 ST # 6G NEW YORK NY 10023 1801","longitude":-73.979637,"latitude":40.780676,"property_value":1381000,"year_built":"1925.0","beds_count":null,"baths":null,"total_area_sq_ft":"181976.0","parking_spaces_count":null},{"fips":"36061","apn":"01242-0062 02","unit_type":"#","full_address":"706 W END AVE # SUPER1A NEW YORK NY 10025 6496","longitude":-73.973569,"latitude":40.794203,"property_value":574000,"year_built":"1928.0","beds_count":null,"baths":null,"total_area_sq_ft":"99494.0","parking_spaces_count":null},{"fips":"36061","apn":"01343-100211A","unit_type":"APT","full_address":"319 E 50 ST APT 11A NEW YORK NY 10022 7948","longitude":-73.967511,"latitude":40.754762,"property_value":524000,"year_built":"1931.0","beds_count":null,"baths":null,"total_area_sq_ft":"79062.0","parking_spaces_count":null},{"fips":"36061","apn":"01407-003314E","unit_type":"#","full_address":"1250 3 AVE # 14E NEW YORK NY 10021 ","longitude":-73.960649,"latitude":40.770244,"property_value":831000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"326054.0","parking_spaces_count":null},{"fips":"36061","apn":"01487-1303","unit_type":"APT","full_address":"530 E 76 ST APT 18H NEW YORK NY 10021 3159","longitude":-73.950085,"latitude":40.768188,"property_value":1511000,"year_built":"1986.0","beds_count":null,"baths":null,"total_area_sq_ft":"1007.0","parking_spaces_count":null},{"fips":"36061","apn":"01492-00612B","unit_type":"APT","full_address":"18 E 81 ST APT 2B NEW YORK NY 10028 0232","longitude":-73.96185799999998,"latitude":40.777682,"property_value":928000,"year_built":"1930.0","beds_count":null,"baths":null,"total_area_sq_ft":"13050.0","parking_spaces_count":null},{"fips":"36061","apn":"01527-1023","unit_type":"APT","full_address":"1441 3 AVE APT 9C NEW YORK NY 10028 1976","longitude":-73.955859,"latitude":40.775694,"property_value":1481000,"year_built":"1984.0","beds_count":null,"baths":null,"total_area_sq_ft":"1220.0","parking_spaces_count":null},{"fips":"36061","apn":"01551-00443B","unit_type":"APT","full_address":"312 E 89 ST APT 3B NEW YORK NY 10128 5095","longitude":-73.949572,"latitude":40.779235,"property_value":337000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"9165.0","parking_spaces_count":null},{"fips":"36061","apn":"01566-00142C","unit_type":"#","full_address":"431 E 86 ST # 2C NEW YORK NY 10028 ","longitude":-73.94789200000002,"latitude":40.776591,"property_value":1173000,"year_built":"1910.0","beds_count":null,"baths":null,"total_area_sq_ft":"33500.0","parking_spaces_count":null}]
          this.totalResults = this.listings.length

          if (this.listings.length === 1) {
            this.showListings = false
            this.$router.push({ name: 'Listing', params: { fips: this.listings[0].fips, apn: this.listings[0].apn } })
          } else {
            this.markers.forEach(function (marker) {
              marker.setMap(null)
            })
            this.markers = []

            this.listings.forEach(function (listing) {
              listing.additional_images = null

              listing.image = 'https://maps.googleapis.com/maps/api/streetview?size=600x400&location=' + listing.latitude + '' +
                ',' + listing.longitude + '+&fov=90&source=outdoor&key=' + this.mapsKey

              this.addMarker(listing.latitude, listing.longitude, listing.price)
            }, this)

            const me = this

            setTimeout(function () {
              var bounds = new google.maps.LatLngBounds()
              me.markers.forEach(function (marker) {
                bounds.extend(marker.getPosition())
              })
              me.map.fitBounds(bounds)
            }, 500)
          }

          this.loading = false
          // })
        }
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
            alert(address + 'Geocode was not successful for the following reason: ' + status)
          }
        })
      },

      addMarker (latitude, longitude, price) {
        if (this.ready) {
          // console.log(latitude)
          // console.log(longitude)
          const marker = new google.maps.Marker({
            position: { lat: latitude, lng: longitude },
            label: price,
            map: this.map,
          })

          this.markers.push(marker)
        } else {
          const me = this
          setTimeout(function () {
            me.addMarker(latitude, longitude, price)
          }, 500)
        }
      },
    },

  }

</script>

<style lang="sass">
  #search-listings
    border-top: 4px solid $color-orange

    .v-btn
      color: white
      font-weight: 600
      letter-spacing: 0.04em

      .v-icon
        font-size: 1rem
        padding-left: 5px

    .green-buttons
      .v-btn
        background-color: $color-emerald
        margin-right: 6px

    .squash-buttons

      .v-btn
        background-color: $color-squash

    .found-properties-header
      max-width: 460px
      margin: 0 auto

      h2
        span
          display: inline-block
          font-size: 14px
          border-top: 1px solid $color-gray-vlt
          border-left: 1px solid $color-gray-vlt
          width: auto
          color: $color-gray-mddk
          padding: 0 10px

  #search-listings-content > .col
    border-top: 1px solid $color-gray-lt

  #map-col
    background-color: $color-gray-vlt
    padding:0

  #map
    width: 100%
    height: 90vh
    margin: 0
    padding: 0

  .found-properties-column
    overflow: scroll
    height: 90vh
    border-left: 1px solid $color-gray-lt
    box-shadow: 2px 2px 2px 1px rgba(0, 0, 0, 0.2)

  .listings-item-row

</style>
