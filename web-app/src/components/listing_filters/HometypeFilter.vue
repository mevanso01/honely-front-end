<template>
  <v-overlay
    light
    opacity="0"
    :value="showHometypeFilter"
    class="hometype-filter"
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
              Home Type
            </h4>
            <v-row>
              <v-col
                cols="12"
                sm="6"
                md="6"
              >
                <v-checkbox
                  v-model="singleFamilyVal"
                  label="Single Family"
                  color="#00a650"
                  value="SFR"
                  hide-details
                />
                <v-checkbox
                  v-model="apartmentVal"
                  label="Apartment"
                  color="#00a650"
                  value="APT"
                  hide-details
                />
                <v-checkbox
                  v-model="othersVal"
                  label="Others"
                  color="#00a650"
                  value="OTHERS"
                  hide-details
                />
              </v-col>
              <v-col
                cols="12"
                sm="6"
                md="6"
              >
                <v-checkbox
                  v-model="multiFamilyVal"
                  label="Multi-Family"
                  color="#00a650"
                  value="MFR"
                  hide-details
                />
                <v-checkbox
                  v-model="landVal"
                  label="Land"
                  color="#00a650"
                  value="LAND"
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
    name: 'HometypeFilter',
    props: {
      showHometypeFilter: Boolean,
      land: String,
      apartment: String,
      multiFamily: String,
      singleFamily: String,
      others: String,
      // HometypeValue: Number,
    },

    data: () => ({
      HometypeItems: [1, 2, 3, 4, 5, 6],
      // HometypeValue: 0,
      // landVal: null,
      // apartmentVal: null,
      // multiFamilyVal: null,
      // singleFamilyVal: null,
      // others: null,
      landVal: 'LAND',
      apartmentVal: 'APT',
      multiFamilyVal: 'MFR',
      singleFamilyVal: 'SFR',
      othersVal: 'OTHERS',
    }),
    computed: {
      selectedItems () {
        var ans = []
        if (this.landVal === 'LAND') {
          ans.push(this.landVal)
        }
        if (this.apartmentVal === 'APT') {
          ans.push(this.apartmentVal)
        }
        if (this.multiFamilyVal === 'MFR') {
          ans.push(this.multiFamilyVal)
        }
        if (this.singleFamilyVal === 'SFR') {
          ans.push(this.singleFamilyVal)
        }
        if (this.othersVal === 'OTHERS') {
          ans.push(this.othersVal)
        }
        return ans
      },
    },
    watch: {
      land (val) {
        this.landVal = val
      },
      apartment (val) {
        this.apartmentVal = val
      },
      multiFamily (val) {
        this.multiFamilyVal = val
      },
      singleFamily (val) {
        this.singleFamilyVal = val
      },
      others (val) {
        this.othersVal = val
      },
    },

    methods: {
      hideOverlay () {
        this.$emit('hideHometypeFilterOverlay')
      },
      load () {
        this.$emit('setHomeTypeSingleFamily', this.singleFamilyVal)
        this.$emit('setHomeTypeMultiFamily', this.multiFamilyVal)
        this.$emit('setHomeTypeApartment', this.apartmentVal)
        this.$emit('setHomeTypes', this.selectedItems)
        this.$emit('setHomeTypeLand', this.landVal)
        this.$emit('setHomeTypeOthers', this.othersVal)

        this.hideOverlay()
        this.$emit('load')
      },
    },
  }
</script>

<style lang="sass">
  .hometype-filter
    .v-overlay__content
      width: 100%
      height: 100%

      .v-card
        position: absolute
        top: 140px
        left: 365px
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
    .hometype-filter
      .v-overlay__content
        .v-card
          left: 15px
          height: auto !important

    /* col.pb-0
      padding-bottom: 0 !important */

</style>
