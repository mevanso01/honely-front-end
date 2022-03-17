<template>
  <v-overlay
    light
    opacity="0"
    :value="showDetailsFilter"
    class="details-filter"
  >
    <v-card
      v-click-outside="hideOverlay"
      width="300"
      height="225"
      light
    >
      <v-card-text>
        <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="12"
            class="pb-1"
          >
            <h4>
              Property Attributes Must Include:
            </h4>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="6"
              >
                <v-checkbox
                  v-model="parkingSpotVal"
                  label="Parking Spot"
                  color="#00a650"
                  value="Parking Spot"
                  hide-details
                />
                <v-checkbox
                  v-model="garageVal"
                  label="Garage"
                  color="#00a650"
                  value="Garage"
                  hide-details
                />
                <v-checkbox
                  v-model="foreclosedVal"
                  label="Foreclosed"
                  color="#00a650"
                  value="Foreclosed"
                  hide-details
                />
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="6"
              >
                <v-checkbox
                  v-model="poolVal"
                  label="Pool"
                  color="#00a650"
                  value="Pool"
                  hide-details
                />
                <v-checkbox
                  v-model="basementVal"
                  label="Basement"
                  color="#00a650"
                  value="Basement"
                  hide-details
                />
              </v-col>
            </v-row>
          </v-col>
          <v-col
            cols="12"
          >
            <v-row
              align="center"
              justify="end"
            >
              <v-btn
                color="primary"
                @click="load"
              >
                Apply
              </v-btn>
            </v-row>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>

  export default {
    name: 'DetailsFilter',
    props: {
      showDetailsFilter: Boolean,
      garage: String,
      parkingSpot: String,
      foreclosed: String,
      pool: String,
      basement: String,
      // DetailsValue: Number,
    },

    data: () => ({
      DetailsItems: [1, 2, 3, 4, 5, 6],
      DetailsValue: 0,
      parkingSpotVal: null,
      garageVal: null,
      foreclosedVal: null,
      poolVal: null,
      basementVal: null,
    }),
    computed: {
      selectedItems () {
        var ans = []
        if (this.parkingSpotVal === 'Parking Spot') {
          ans.push(this.parkingSpotVal)
        }
        if (this.garageVal === 'Garage') {
          ans.push(this.garageVal)
        }
        if (this.foreclosedVal === 'Foreclosed') {
          ans.push(this.foreclosedVal)
        }
        if (this.poolVal === 'Pool') {
          ans.push(this.poolVal)
        }
        if (this.basementVal === 'Basement') {
          ans.push(this.basementVal)
        }
        return ans
      },
    },
    watch: {
      garage (val) {
        this.garageVal = val
      },
      parkingSpot (val) {
        this.parkingSpotVal = val
      },
      foreclosed (val) {
        this.foreclosedVal = val
      },
      pool (val) {
        this.poolVal = val
      },
      basement (val) {
        this.basementVal = val
      },
    },

    methods: {
      hideOverlay () {
        this.$emit('hideDetailsFilterOverlay')
      },
      load () {
        // const propertyDetails = []
        // if (this.parkingSpotVal) {
        //   propertyDetails.push('Parking Spot')
        // }
        // if (this.garageVal) {
        //   propertyDetails.push('garageVal')
        // }
        // if (this.foreclosedVal) {
        //   propertyDetails.push('foreclosedVal')
        // }
        // if (this.poolVal) {
        //   propertyDetails.push('poolVal')
        // }
        // if (this.basementVal) {
        //   propertyDetails.push('basementVal')
        // }
        //
        // this.$emit('setPropertyDetails', propertyDetails)
        this.$emit('setPropertyDetailsParkingSpot', this.parkingSpotVal)
        this.$emit('setPropertyDetailsGarage', this.garageVal)
        this.$emit('setPropertyDetailsForeclosed', this.foreclosedVal)
        this.$emit('setPropertyDetailsPool', this.poolVal)
        this.$emit('setPropertyDetailsBasement', this.basementVal)
        this.$emit('setPropertyDetails', this.selectedItems)
        this.hideOverlay()
        this.$emit('load')
      },
    },
  }
</script>

<style lang="sass">
  .details-filter
    .v-overlay__content
      width: 100%
      height: 100%

      .v-card
        position: absolute
        top: 140px
        left: 480px
        border: 1px solid green

        h4
          color: black
          font-family: $heading-font-family
          font-weight: 500

        .v-input
          .v-label
            font-size: 12px

        .v-input--is-label-active
          .v-label
            color: black
            font-weight: 600

  @media (max-width: 960px)
    .details-filter
      .v-overlay__content
        .v-card
          left: 15px
          height: auto !important
    /* col.pb-0
      padding-bottom: 0 !important */

</style>
