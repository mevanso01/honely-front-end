<template>
  <div
    id="forecast-band-6"
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
            class="mt-0 forecast-band-header pb-1 flex-heading"
            title=""
            weight="medium"
            size="text-h4"
            space="1"
          >
            <div class="heading-text">
              <span v-if="forecast && forecast.property_forecast">
                <!-- 6. -->
                <i
                  class="mdi mdi-home-switch"
                  aria-hidden="true"
                />
              </span>
              Comparable Properties
            </div>
            <div class="comparable-score-reference-container">
              <div class="reference-label-row">
                <span>Low</span>
                <span>Below Avg.</span>
                <span>Average</span>
                <span>Above Avg.</span>
                <span>High</span>
              </div>
              <div class="reference-bar-row">
                &nbsp;
              </div>
              <div class="reference-label-row-below">
                <span>0</span>
                <span>25</span>
                <span>50</span>
                <span>75</span>
                <span>100</span>
              </div>
            </div>
          </base-heading>
          <v-row class="forecast-section-below-heading">
            <v-col
              cols="12"
              class="white-buttons"
            >
              <!-- <v-row
                v-if="listings.length > 0"
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
                    Comparable Homes In The Area
                  </h1>
                </v-col>
              </v-row> -->

              <v-row
                justify="center"
                class="border-me-not"
              >
                <v-col
                  v-for="item in listings"
                  :key="item.property_id"
                  class="intro-text"
                  cols="12"
                  sm="8"
                  md="4"
                >
                  <property-listings-card
                    :item="item"
                  />
                </v-col>
              </v-row>
              <v-row
                justify="center"
                class="border-me-not centeritems"
                align-content="center"
              >
                <v-col
                  cols="12"
                  align-self="center"
                />
              </v-row>
            </v-col>
            <v-col
              cols="12"
              align-self="center"
            >
              <button
                v-if= "!isExpanded"
                :block="$vuetify.breakpoint.smAndDown"
                class="btn-comparable-view"
                @click="toggleExpanded"
              >
                <i class="fas fa-chevron-down" />
              </button>
              <!-- <base-btn
              v-if= "!isExpanded"
              :block="$vuetify.breakpoint.smAndDown"
              @click="toggleExpanded"
              class="btn-comparable-view"
            >
              <i class="fas fa-chevron-down"></i>
            </base-btn>
            <base-btn
              v-if= "isExpanded"
              :block="$vuetify.breakpoint.smAndDown"
              @click="toggleExpanded"
              class="btn-comparable-view"
            >
              <i class="fas fa-chevron-up"></i>
            </base-btn> -->
              <button
                v-if= "isExpanded"
                :block="$vuetify.breakpoint.smAndDown"
                class="btn-comparable-view"
                @click="toggleExpanded"
              >
                <i class="fas fa-chevron-up" />
              </button>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </div>
</template>

<script>
  /* eslint-disable */
  import { mapGetters } from 'vuex'

  export default {
    name: 'ForecastBand6',

    props: {
      forecast: Object,
    },

    components: {
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
    },

    watch: {
      forecast: function() {
        // console.log('Prop changed')
        this.getRelatedListings()
      }
    },

    data: () => ({
      listings: null,
      isExpanded: false,
    }),

    computed: {
        ...mapGetters('listings', ['favoriteListings']),
    },

    mounted: function () {
      this.getRelatedListings()
    },

    updated: function () {
    //   this.getRelatedListings()
    },

    methods: {
      toggleExpanded () {
          this.isExpanded = !this.isExpanded
          this.getRelatedListings ()
      },
      getRelatedListings () {
        // axios.get('https://api.honely.com/search/listings', {
            if (!this.isExpanded) {
                axios.get('https://api.honely.com/lookup/comparable_homes?property_id=' + this.forecast.property_forecast.property_id)
        .then((response) => {
          this.listings = []

          response.data.rows.forEach(function (row) {
            if (this.listings.length < 3 && (row.apn !== this.apn || row.fips !== this.fips)) {
              row.appraisal = row.current_value
              row.beds_count = row.bedrooms
              row.baths = row.bathrooms
              row.latitude = row.situslatitude
              row.longitude = row.situslongitude
              const tempImages = this.$store.getters['listings/tempImages'](row.fips + '/' + row.apn)
              if (tempImages) {
                row.tempImages = tempImages
              }
              this.listings.push(row)
            }
          }, this)
        })
            }
            if (this.isExpanded) {
                axios.get('https://api.honely.com/lookup/comparable_homes?property_id=' + this.forecast.property_forecast.property_id)
        .then((response) => {
          this.listings = []

          response.data.rows.forEach(function (row) {
            if (this.listings.length < 9 && (row.apn !== this.apn || row.fips !== this.fips)) {
              row.appraisal = row.current_value
              row.beds_count = row.bedrooms
              row.baths = row.bathrooms
              row.latitude = row.situslatitude
              row.longitude = row.situslongitude
              const tempImages = this.$store.getters['listings/tempImages'](row.fips + '/' + row.apn)
              if (tempImages) {
                row.tempImages = tempImages
              }
              this.listings.push(row)
            }
          }, this)
        })
            }
        // axios.get('https://api.honely.com/lookup/comparable_homes?property_id=' + this.forecast.property_forecast.property_id)
        // .then((response) => {
        //   this.listings = []

        //   response.data.rows.forEach(function (row) {
        //     if (this.listings.length < 3 && (row.apn !== this.apn || row.fips !== this.fips)) {
        //       row.appraisal = row.current_value
        //       row.beds_count = row.bedrooms
        //       row.baths = row.bathrooms
        //       row.latitude = row.situslatitude
        //       row.longitude = row.situslongitude
        //       const tempImages = this.$store.getters['listings/tempImages'](row.fips + '/' + row.apn)
        //       if (tempImages) {
        //         row.tempImages = tempImages
        //       }
        //       this.listings.push(row)
        //     }
        //   }, this)
        // })
      },
    },
  }
</script>

<style>
.btn-comparable-view {
  background-color: transparent !important;
  color: #00a650;
  padding: 0;
}
.btn-comparable-view span {

}
.btn-comparable-view i {
  font-size: 27px;
}
.btn-comparable-view:hover {
  color: #e67e22;
}
h1.flex-heading {
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
}
.comparable-score-reference-container {
  width: 40%;
  min-width: 300px;
  background: #ffffff;
  padding: 3px 15px;
  border-radius: 10px;
  margin-bottom: 15px;
  margin-left: 10px;
}
@media (max-width: 800px) {
  .comparable-score-reference-container {
    width: 95%;
    margin-top: 10px;
    margin-left: 0;
  }
}
@media (max-width: 600px) {
  .comparable-score-reference-container {
    width: 100%;
  }
}
</style>
