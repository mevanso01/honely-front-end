<template>
  <v-overlay
    light
    opacity="0"
    :value="showBedsFilter"
    class="beds-filter"
  >
    <v-card
      v-click-outside="hideOverlay"
      width="400"
      height="250"
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
              Beds
            </h4>
            <v-row
              align="center"
              justify="center"
            >
              <v-col
                cols="5"
                class="pb-1"
              >
                <v-select
                  v-model="bedMinVal"
                  :items="BedsMinOptions"
                  label="Minimum #"
                />
              </v-col>
              <v-col
                cols="2"
                class="text-center pb-1"
              >
                to
              </v-col>
              <v-col
                cols="5"
                class="pb-1"
              >
                <v-select
                  v-model="bedMaxVal"
                  :items="BedsMaxOptions"
                  label="Maximum #"
                />
              </v-col>
            </v-row>
          </v-col>
          <v-col
            cols="12"
            class="pb-0"
          >
            <h4>
              Bathrooms
            </h4>
            <v-row
              align="center"
              justify="center"
            >
              <v-col
                cols="5"
                class="pb-0"
              >
                <v-select
                  v-model="bathMinVal"
                  :items="BathMinOptions"
                  label="Minimum #"
                />
              </v-col>
              <v-col
                cols="2"
                class="text-center pb-0"
              >
                to
              </v-col>
              <v-col
                cols="5"
                class="pb-0"
              >
                <v-select
                  v-model="bathMaxVal"
                  :items="BathMaxOptions"
                  label="Maximum #"
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
  /* eslint-disable */
  export default {
    name: 'BedsFilter',
    props: {
      showBedsFilter: Boolean,
      minBeds: String,
      maxBeds: String,
      minBaths: String,
      maxBaths: String,
      // BedsValue: Number,
    },

    data: () => ({
      BedsItems: [1, 2, 3, 4, 5, 6],
      BedsValue: 0,
      // BedsMinOptions: ['No Minimum', 'Studio', '1', '2', '3', '4'],
      BedsMinOptions: ['1', '2', '3', '4', '5', '6', 'No Minimum'],
      BedsMaxOptions: ['1', '2', '3', '4', '5', '6', 'No Maximum'],
      BathMinOptions: ['1', '1.5', '2', '2.5', '3', '3.5', '4', '4.5', '5', 'No Minimum'],
      BathMaxOptions: ['1', '1.5', '2', '2.5', '3', '3.5', '4', '4.5', '5', 'No Maximum'],
      // minBeds: 'No Minimum',
      // maxBeds: 'No Maximum',
      // minBaths: 'No Minimum',
      // maxBaths: 'No Maximum',
      bedMinVal: '',
      bedMaxVal: '',
      bathMinVal: '',
      bathMaxVal: '',
    }),
    computed: {
      // minimumBeds() {
      //   return this.minBeds;
      // },
      // maximumBeds() {
      //   return this.maxBeds;
      // },
      // minimumBaths() {
      //   return this.minBaths;
      // },
      // maximumBaths() {
      //   return this.maxBaths;
      // },
    },
    watch: {
      // minBeds (val) {
      //   this.bedMinVal = val
      // },
      // maxBeds (val) {
      //   this.bedMinVal = val
      // },
      // minBaths (val) {
      //   this.bathMinVal = val
      // },
      // maxBaths (val) {
      //   this.bathMaxVal = val
      // }
    },

    methods: {
      hideOverlay (a) {
        if (a.target.className.indexOf('v-list-item') === -1) {
          this.$emit('hideBedsFilterOverlay')
        }
      },
      // setMinBeds (value) {
      // },
      // setMaxBeds (value) {
      // },
      // setMinBaths (value) {
      // },
      // setMaxBaths (value) {
      // },
      // setLowerLimitForMaxBeds () {
      //   var theIndex = this.BedsMaxOptions.indexOf(this.minBeds)
      //   this.BedsMaxOptions = this.BedsMaxOptions.slice(theIndex + 1, this.BedsMaxOptions.length)
      // },
      // setUpperLimitForMinBeds () {
      //   var theIndex = this.BedsMinOptions.indexOf(this.maxBeds)
      //   this.BedsMinOptions = this.BedsMinOptions.slice(0, theIndex)
      // },
      // setLowerLimitForMaxBath () {
      //   var theIndex = this.BathMaxOptions.indexOf(this.minBaths)
      //   this.BathMaxOptions = this.BathMaxOptions.slice(theIndex + 1, this.BathMaxOptions.length)
      // },
      // setUpperLimitForMinBath () {
      //   var theIndex = this.BathMinOptions.indexOf(this.maxBaths)
      //   this.BathMinOptions = this.BathMinOptions.slice(0, theIndex)
      // },
      load () {
        this.$emit('setMinBeds', this.bedMinVal)
        this.$emit('setMaxBeds', this.bedMaxVal)
        this.$emit('setMinBaths', this.bathMinVal)
        this.$emit('setMaxBaths', this.bathMaxVal)
        this.$emit('hideBedsFilterOverlay')
        this.$emit('load')
      },
    },
  }
</script>

<style lang="sass">
  .beds-filter
    .v-overlay__content
      width: 100%
      height: 100%

      .v-card
        position: absolute
        top: 140px
        left: 250px
        border: 1px solid green

        h4
          color: black
          font-family: $heading-font-family
          font-weight: 500

  @media (max-width: 960px)
    .beds-filter
      .v-overlay__content
        .v-card
          left: 15px

    /* col.pb-0
      padding-bottom: 0 !important */

</style>
