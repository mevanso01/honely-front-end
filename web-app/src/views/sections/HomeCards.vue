<template>
  <v-theme-provider dark>
    <base-section
      id="home-cards"
      space="40"
    >
      <v-container>
        <v-row
          justify="start"
          class="border-me-not"
        >
          <v-col
            class="intro-text"
            cols="12"
            sm="10"
            md="8"
          >
            <h1
              class="mb-1"
            >
              Homes in your area
            </h1>
            <p>
              You can search for homes that meet your requirements but will also grow in value. Filter by location and specification but also by forecasted growth rates.
            </p>
          </v-col>
        </v-row>
        <br>
        <!-- <p
          align="center"
          v-if="!geolocationEnable"
        >
          Please give location access to see listings in your area. You may have to reload your page
        </p> -->
        <!-- <v-btn
                align="center"
                v-if="!geolocationEnable"
                color="primary"
                @click="reloadPage()"
              >
                Enable Location
              </v-btn> -->
        <!-- <p  v-if="!geolocationEnable">Please give location access to see locations in your area</p> -->
        <v-row
          v-if="firstThreeListings"
          justify="center"
          class="border-me-not"
          style="min-height: 387px"
        >
          <v-col
            v-for="item in firstThreeListings"
            :key="item.id"
            class="intro-text"
            cols="12"
            sm="8"
            md="4"
            style="min-height: 387px"
          >
            <property-listings-card :item="item" />
          </v-col>
        </v-row>
        <v-row
          v-else
          justify="center"
          class="border-me-not"
          style="min-height: 387px"
        >
          <v-col
            class="intro-text"
            cols="12"
            sm="8"
            md="4"
            style="min-height: 387px"
          >
            <p class="homeCardsReloadRequest">Please refresh the page</p>
            <div
            id="homecardsLoadingSpinner"
            >
            <circle-spin :loading='true'></circle-spin>
            </div>
          </v-col>
        </v-row>
        <v-row
          justify="center"
          class="border-me-not centeritems"
          align-content="center"
        >
          <v-col
            v-if="geolocationEnable && latitude && longitude"
            cols="12"
            align-self="center"
          >
            <base-btn
              :block="$vuetify.breakpoint.smAndDown"
              :to="{ name: 'Listings',  query: {lat: latitude, long: longitude}, params: {lat: latitude, long: longitude}}"
              class="btn-home-pg-view-more"
            >
              <i class="fas fa-paper-plane"></i>
              <span>View More</span>
              <!--              <v-icon-->
              <!--                class="icon-left-space mdi-rotate-90  mdi-20px icon-green"-->
              <!--              >-->
              <!--                {{ 'mdi-play' }}-->
              <!--              </v-icon>-->
            </base-btn>
          </v-col>
          <v-col
            v-else
            cols="12"
            align-self="center"
          >
            <base-btn
              :block="$vuetify.breakpoint.smAndDown"
              :to="{ name: 'Listings' }"
              class="btn-home-pg-view-more"
            >
              <i class="fas fa-paper-plane"></i>
              <span>View More</span>
              <!--              <v-icon-->
              <!--                class="icon-left-space mdi-rotate-90  mdi-20px icon-green"-->
              <!--              >-->
              <!--                {{ 'mdi-play' }}-->
              <!--              </v-icon>-->
            </base-btn>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  // import { mapState } from 'vuex'
  import axios from 'axios'
  import Vue from 'vue'
  import VueGeolocation from 'vue-browser-geolocation'
  Vue.use(VueGeolocation)
  export default {
    name: 'SectionContactUs',
    components: {
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
    },
    // computed: {
    //   ...mapState('listings', {
    //     listings: 'all',
    //   }),
    // },

    data: () => ({
      /* eslint-disable */
      listings: null,
      firstThreeListings: null,
      loading: false,
      geolocationEnable : false,
      latitude: null,
      longitude: null,
    }),

    mounted () {
      /* eslint-disable */ 
      // this.load()
      this.locatorButtonPressed()
    },

    methods: {
      /* eslint-disable */ 
      locatorButtonPressed () {
      // var latitude,longitude
          this.loading = true
          this.$getLocation()
            .then(coordinates => {
              // console.log(coordinates);
              // navigator.geolocation.getCurrentPosition(
              // position => {
              this.latitude = coordinates.lat
              this.longitude = coordinates.lng
              // console.log(position.coords.latitude)
              // console.log(position.coords.longitude)
              this.geolocationEnable = true
              this.load()
                },
                error => {
                  this.load('','')
                  console.log(error)
                  if (this.$useDevVariables) {
                  // eslint-disable-next-line
                  this.listings = [{"fips":"36061","apn":"00396-002017 05","unit_type":"APT","full_address":"626 E 14 ST APT 17 NEW YORK NY 10009 3334","longitude":-73.976781,"latitude":40.728591,"property_value":420000,"year_built":"1920.0","beds_count":null,"baths":null,"total_area_sq_ft":"65585.0","parking_spaces_count":null},{"fips":"36061","apn":"00558-1131","unit_type":"APT","full_address":"115 4 AVE APT 4J NEW YORK NY 10003 4908","longitude":-73.989573,"latitude":40.732812,"property_value":1856000,"year_built":null,"beds_count":null,"baths":null,"total_area_sq_ft":"1132.0","parking_spaces_count":null},{"fips":"36061","apn":"00576-00162G","unit_type":"APT","full_address":"42 W 13 ST APT 2G NEW YORK NY 10011 7921","longitude":-73.9964,"latitude":40.736027,"property_value":1764000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"31540.0","parking_spaces_count":null},{"fips":"36061","apn":"00693-1115","unit_type":"APT","full_address":"551 W 21 ST APT 5B NEW YORK NY 10011 2895","longitude":-74.007302,"latitude":40.747579,"property_value":2509000,"year_built":null,"beds_count":null,"baths":null,"total_area_sq_ft":null,"parking_spaces_count":null},{"fips":"36061","apn":"00826-1063","unit_type":"APT","full_address":"1107 BROADWAY APT 10A NEW YORK NY 10010 0012","longitude":-73.989581,"latitude":40.742717,"property_value":3410000,"year_built":"1915.0","beds_count":null,"baths":null,"total_area_sq_ft":"2205.0","parking_spaces_count":null},{"fips":"36061","apn":"00846-00642E","unit_type":"#","full_address":"18 E 18 ST # 2E NEW YORK NY 10003 1910","longitude":-73.99078100000001,"latitude":40.737858,"property_value":2716000,"year_built":"1900.0","beds_count":null,"baths":null,"total_area_sq_ft":"36400.0","parking_spaces_count":null},{"fips":"36061","apn":"00892-002510A","unit_type":"#","full_address":"273 LEXINGTON AVE # 10A NEW YORK NY 10016 3540","longitude":-73.978376,"latitude":40.74775800000001,"property_value":626000,"year_built":"1966.0","beds_count":null,"baths":null,"total_area_sq_ft":"129964.0","parking_spaces_count":null},{"fips":"36061","apn":"00893-004121H","unit_type":"#","full_address":"560 3 AVE # 21H NEW YORK NY 10016 3117","longitude":-73.976864,"latitude":40.748094,"property_value":699000,"year_built":"1975.0","beds_count":null,"baths":null,"total_area_sq_ft":"329706.0","parking_spaces_count":null},{"fips":"36061","apn":"01047-1468","unit_type":"APT","full_address":"353 W 56 ST APT 2C NEW YORK NY 10019 3773","longitude":-73.985076,"latitude":40.767173,"property_value":603000,"year_built":"1931.0","beds_count":null,"baths":null,"total_area_sq_ft":"534.0","parking_spaces_count":null},{"fips":"36061","apn":"01066-00372O","unit_type":"APT","full_address":"408 W 57 ST APT 2O NEW YORK NY 10019 3018","longitude":-73.986621,"latitude":40.767949,"property_value":457000,"year_built":"1957.0","beds_count":null,"baths":null,"total_area_sq_ft":"104966.0","parking_spaces_count":null},{"fips":"36061","apn":"01124-00058D","unit_type":"APT","full_address":"59 W 71 ST APT 8D NEW YORK NY 10023 4115","longitude":-73.978514,"latitude":40.776651,"property_value":998000,"year_built":"1924.0","beds_count":null,"baths":null,"total_area_sq_ft":"54976.0","parking_spaces_count":null},{"fips":"36061","apn":"01147-10036G","unit_type":"#","full_address":"161 W 75 ST # 6G NEW YORK NY 10023 1801","longitude":-73.979637,"latitude":40.780676,"property_value":1381000,"year_built":"1925.0","beds_count":null,"baths":null,"total_area_sq_ft":"181976.0","parking_spaces_count":null},{"fips":"36061","apn":"01242-0062 02","unit_type":"#","full_address":"706 W END AVE # SUPER1A NEW YORK NY 10025 6496","longitude":-73.973569,"latitude":40.794203,"property_value":574000,"year_built":"1928.0","beds_count":null,"baths":null,"total_area_sq_ft":"99494.0","parking_spaces_count":null},{"fips":"36061","apn":"01343-100211A","unit_type":"APT","full_address":"319 E 50 ST APT 11A NEW YORK NY 10022 7948","longitude":-73.967511,"latitude":40.754762,"property_value":524000,"year_built":"1931.0","beds_count":null,"baths":null,"total_area_sq_ft":"79062.0","parking_spaces_count":null},{"fips":"36061","apn":"01407-003314E","unit_type":"#","full_address":"1250 3 AVE # 14E NEW YORK NY 10021 ","longitude":-73.960649,"latitude":40.770244,"property_value":831000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"326054.0","parking_spaces_count":null},{"fips":"36061","apn":"01487-1303","unit_type":"APT","full_address":"530 E 76 ST APT 18H NEW YORK NY 10021 3159","longitude":-73.950085,"latitude":40.768188,"property_value":1511000,"year_built":"1986.0","beds_count":null,"baths":null,"total_area_sq_ft":"1007.0","parking_spaces_count":null},{"fips":"36061","apn":"01492-00612B","unit_type":"APT","full_address":"18 E 81 ST APT 2B NEW YORK NY 10028 0232","longitude":-73.96185799999998,"latitude":40.777682,"property_value":928000,"year_built":"1930.0","beds_count":null,"baths":null,"total_area_sq_ft":"13050.0","parking_spaces_count":null},{"fips":"36061","apn":"01527-1023","unit_type":"APT","full_address":"1441 3 AVE APT 9C NEW YORK NY 10028 1976","longitude":-73.955859,"latitude":40.775694,"property_value":1481000,"year_built":"1984.0","beds_count":null,"baths":null,"total_area_sq_ft":"1220.0","parking_spaces_count":null},{"fips":"36061","apn":"01551-00443B","unit_type":"APT","full_address":"312 E 89 ST APT 3B NEW YORK NY 10128 5095","longitude":-73.949572,"latitude":40.779235,"property_value":337000,"year_built":"1960.0","beds_count":null,"baths":null,"total_area_sq_ft":"9165.0","parking_spaces_count":null},{"fips":"36061","apn":"01566-00142C","unit_type":"#","full_address":"431 E 86 ST # 2C NEW YORK NY 10028 ","longitude":-73.94789200000002,"latitude":40.776591,"property_value":1173000,"year_built":"1910.0","beds_count":null,"baths":null,"total_area_sq_ft":"33500.0","parking_spaces_count":null}]
                  this.totalResults = this.listings.length
                  this.setMarkers()
                }
                  // this.load()
                },
            )
      },
      reloadPage () {
        this.locatorButtonPressed()
      },
      load () {
          // fetch('https://api.honely.com/search/listings?ipConfig=yes')
          if (this.latitude && this.longitude) {
            fetch('https://api.honely.com/lookup/listings?ipConfig=yes&lat=' + this.latitude+ '&long=' + this.longitude)
            .then(response => response.json())
            .then((data) => {
              this.listings = data.rows
              // this.listings = data
              this.firstThreeListings = []
              for (let i = 0; i < 3; i++) {
                if (this.listings[i]) {
                  this.firstThreeListings.push(this.listings[i])
                }
              }
              this.totalResults = data.total

              this.loading = false
            })
            .catch((error) => {
              console.warn(error)
              return null
            })
          } else {
            fetch('https://api.honely.com/lookup/listings?ipConfig=yes')
            .then(response => response.json())
            .then((data) => {
              this.listings = data.rows
              // this.listings = data
              this.firstThreeListings = []
              for (let i = 0; i < 3; i++) {
                if (this.listings[i]) {
                  this.firstThreeListings.push(this.listings[i])
                }
              }
              this.totalResults = data.total

              this.loading = false
            })
            .catch((error) => {
              console.warn(error)
              return null
            })
          } 
      },
    },
  }
</script>
