<template>
  <v-overlay
    light
    opacity="0"
    :value="showPricingFilter"
    class="pricing-filter"
  >
    <v-card
      v-click-outside="hideOverlay"
      width="300"
      light
    >
      <v-card-text>
        <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="12"
            style="padding-bottom:55px"
          >
            <!-- <h4>
              Forecasted Growth
            </h4> -->
            <!-- <v-row> -->
              <!-- <v-col> -->
                <h4>
              Price Range ($)
            </h4>
            <v-row
            style="height:60px"
            >
              <v-col
              :md="5"
              >
                <v-text-field
                  v-model="priceMin"
                  name="priceMin"
                  placeholder="Min"
                  @focus="activateLeft"
                  @keypress="isPriceMinNumber($event)"
                  :maxlength="10"
                  @change="filterPriceMaxItems"
                />
              </v-col>
              <v-col
              :md="2"
              style="margin-left:0px;padding-left:0px;margin-top:20px;"
              >
              <span
              style="margin-left:50%;"
              >-</span>
              </v-col>
              <v-col
              :md="5"
              >
                <v-text-field
                  v-model="priceMax"
                  name="priceMax"
                  placeholder="Max"
                  @focus="deactivateLeft"
                  @keypress="isPriceMaxNumber($event)"
                  :maxlength="10"
                  @change="filterPriceMinItems"
                />
              </v-col>
            </v-row>
            <v-row>
                <v-col
                :md="7"
                >
                <div
                v-if="isLeftActive"
                >
                <p
                v-for="item in priceMinItemsFiltered"
                :key="item"
                @click="setPriceMin(item)"
                >
                <a>{{ item }}</a>
                </p>
                <!-- <p>$0+</p>
                <p>$100,000+</p>
                <p>$200,000+</p>
                <p>$300,000+</p>
                <p>$400,000+</p>
                <p>$500,000+</p>
                <p>$600,000+</p>
                <p>$700,000+</p>
                <p>$800,000+</p> -->
                </div>
                </v-col>
                <v-col
                :md="5"
                >
                <div
                v-if="!isLeftActive"
                >
                <p
                v-for="item in priceMaxItemsFiltered"
                :key="item"
                @click="setPriceMax(item)"
                >
                <a>{{ item }}</a>
                </p>
                <!-- <p>$500,000</p>
                <p>$600,000</p>
                <p>$700,000</p>
                <p>$800,000</p>
                <p>$900,000</p>
                <p>$1M</p>
                <p>$1.25M</p>
                <p>$1.5M</p>
                <p>$1.75M</p>
                <p>Any Price</p> -->
                </div>
                </v-col>
            </v-row>
                <!-- <v-select
                  v-model="priceValue"
                  :items="priceItems"
                  label="($)"
                /> -->
              <!-- </v-col> -->
              <!-- <v-col
                cols="12"
                class="pt-5"
              >
                <v-btn-toggle
                  v-model="percentValue"
                  mandatory
                >
                  <v-btn
                    v-for="item in percentItems"
                    :key="item.value"
                    text
                    class="black-text"
                  >
                    {{ item.name }}
                  </v-btn>
                </v-btn-toggle>
              </v-col> -->
            <!-- </v-row> -->
            <!-- <v-row> -->
              <!-- <v-col> -->
                <!-- <h4
                style="margin-top:20px;"
                >
              Forecasted value
            </h4>
                <v-select
                  v-model="dollarValue"
                  :items="dollarItems"
                  label="($)"
                /> -->
              <!-- </v-col> -->
            <!-- </v-row> -->
            <!-- <h4
              class="pt-5"
            >
              Forecasted Dollar Amount
            </h4> -->
            <!-- <v-row>
              <v-col>
                <h4
              class="pt-5"
            >
              Forecasted Dollar Amount
            </h4>
                <v-select
                  v-model="dollarValue"
                  :items="dollarItems"
                  label="($)"
                />
              </v-col> -->
              <!-- <v-col
                cols="12"
                class="pt-5"
              >
                <v-btn-toggle
                  v-model="dollarValue"
                  mandatory
                >
                  <v-btn
                    v-for="item in dollarItems"
                    :key="item"
                    text
                  >
                    {{ item }}
                  </v-btn>
                </v-btn-toggle>
                <v-slider
                  v-model="yearIndex"
                  :tick-labels="tickLabels"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="3"
                  track-color="#cccccc"
                  class="pt-5"
                />
              </v-col> -->
            <!-- </v-row> -->
            <!-- <v-row>
              <v-col>
              <h4
              style="margin-top:20px;"
              >
              Timeframe
            </h4>
              <v-select
                  v-model="yearValue"
                  :items="timeItems"
                  label="(years)"
                /> -->
              <!-- <v-slider
                  v-model="yearIndex"
                  :tick-labels="tickLabels"
                  :max="2"
                  step="1"
                  ticks="always"
                  tick-size="3"
                  track-color="#cccccc"
                  class="pt-5"
                /> -->
                <!-- </v-col>
            </v-row> -->
            <!-- <v-row>
            <v-col
            :md="6"
            > -->

              <v-btn
                class="pricing-reset-button"
                :absolute="true"
                :left="true"
                @click="resetValues"
                style="margin-top:10px; margin-bottom: 5px"
              >
                Reset
              </v-btn>
            <!-- </v-col>
            <v-col
            :md="6"
            > -->
              <v-btn
                color="primary"
                @click="load"
                class="pricing-apply-button"
                :absolute="true"
                :right="true"
                :disabled="isDisabled"
                style="margin-top:10px; margin-bottom: 5px"
              >
                Apply
              </v-btn>
            <!-- </v-row> -->
          <!-- </v-col> -->
          <!-- <v-col
            cols="12"
          > -->
            <!-- <v-row
              align="center"
              justify="end"
            >
              <v-btn
                color="primary"
                @click="load"
              >
                Apply
              </v-btn>
            </v-row> -->
          <!-- </v-col> -->
        <!-- </v-row> -->
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>

  export default {
    name: 'PricingFilter',
    props: {
      showPricingFilter: Boolean,
      // forecastYear: Number,
      // ForecastingValue: Number,
    },

    // watch: {
    //   forecastPercent (val) {
    //     this.percentValue = val
    //   },
    //   forecastDollar (val) {
    //     this.dollarValue = val
    //   },
    //   forecastYear (val) {
    //     this.yearIndex = val
    //   },
    // },

    data: () => ({
      priceValue: 0,
      priceMin: null,
      priceMax: null,
      isLeftActive: true,
      isDisabled: false,
      priceMinItems: ['$0+', '$100,000+', '$200,000+', '$300,000+', '$400,000+', '$500,000+', '$600,000+', '$700,000+', '$800,000+'],
      priceMaxItems: ['$500,000', '$600,000', '$700,000', '$800,000', '$900,000', '$1M', '$1.25M', '$1.5M', '$1.75M', 'Any Price'],
      priceMinItemsFiltered: ['$0+', '$100,000+', '$200,000+', '$300,000+', '$400,000+', '$500,000+', '$600,000+', '$700,000+', '$800,000+'],
      priceMaxItemsFiltered: ['$500,000', '$600,000', '$700,000', '$800,000', '$900,000', '$1M', '$1.25M', '$1.5M', '$1.75M', 'Any Price'],
    }),
    watch: {
      priceMin: function () {
        this.disableCheck()
      },
      priceMax: function () {
        this.disableCheck()
      },
    },
    methods: {
      setPriceMin (val) {
        this.priceMin = this.formatPriceItem(val)
        this.filterPriceMaxItems()
      },
      setPriceMax (val) {
        if (val === 'Any Price') {
          this.priceMax = val
        } else {
          this.priceMax = this.formatPriceItem(val)
        }
        this.filterPriceMinItems()
      },
      filterPriceMinItems () {
        this.priceMinItemsFiltered = []
        for (var x = 0; x < this.priceMinItems.length; x++) {
          if (this.priceMax === 'Any Price' || this.priceMax === null || this.priceMax === '' || (parseInt(this.formatPriceItem(this.priceMinItems[x])) < parseInt(this.priceMax))) {
            this.priceMinItemsFiltered.push(this.priceMinItems[x])
          }
        }
      },
      filterPriceMaxItems () {
        this.priceMaxItemsFiltered = []
        for (var x = 0; x < this.priceMaxItems.length; x++) {
          if ((this.priceMin === null) || (this.priceMin === '') || (this.priceMaxItems[x] === 'Any Price') || (parseInt(this.formatPriceItem(this.priceMaxItems[x])) > parseInt(this.priceMin))) {
            this.priceMaxItemsFiltered.push(this.priceMaxItems[x])
          }
        }
      },
      formatPriceItem (val) {
        if (val.includes('M')) {
          var ans = val.replace('$', '')
          ans = ans.replace('M', '')
          return (parseFloat(ans) * 1000000)
        } else {
          return val.replace(/[^0-9]/g, '')
        }
      },
      isPriceMinNumber: function (evt) {
        // evt = (evt) ? evt : window.event
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) || ((this.priceMin === null || this.priceMin === '') && charCode === 48)) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      isPriceMaxNumber: function (evt) {
        // evt = (evt) ? evt : window.event
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) || ((this.priceMax === null || this.priceMax === '') && charCode === 48)) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      disableCheck () {
        if (this.priceMin === null || this.priceMin === '' || this.priceMax === null || this.priceMax === '' || this.priceMax === 'Any Price') {
          this.isDisabled = false
        } else if (parseInt(this.priceMin) <= parseInt(this.priceMax)) {
          this.isDisabled = false
        } else {
          this.isDisabled = true
        }
      },
      activateLeft () {
        this.isLeftActive = true
      },
      deactivateLeft () {
        this.isLeftActive = false
      },
      resetValues () {
        // if (this.percentValue !== 'Custom') {
        //   this.percentValue = 0
        // }
        this.priceValue = 0
        // this.dollarValue = 0
        // this.yearValue = '1 year'
        this.priceMin = null
        this.priceMax = null
        this.priceMinItemsFiltered = ['$0+', '$100,000+', '$200,000+', '$300,000+', '$400,000+', '$500,000+', '$600,000+', '$700,000+', '$800,000+']
        this.priceMaxItemsFiltered = ['$500,000', '$600,000', '$700,000', '$800,000', '$900,000', '$1M', '$1.25M', '$1.5M', '$1.75M', 'Any Price']
      },
      hideOverlay (a) {
        // this.$emit('hideForecastingFilterOverlay')
        if (a.target.className.indexOf('v-list-item') === -1) {
          this.$emit('hidePricingFilterOverlay')
        }
      },
      load () {
        // this.$emit('setForecastedGrowthPercent', this.percentItems[this.percentValue].value)
        // this.$emit('setForecastedGrowthDollar', this.dollarItems[this.dollarValue])
        // this.$emit('setForecastedYear', this.yearIndex + 1)
        var toEmit = ''
        if (this.priceMin === null || this.priceMin === '') {
          toEmit = '0'
        } else {
          toEmit = this.priceMin
        }
        if (this.priceMax !== null && this.priceMax !== '' && this.priceMax !== 'Any Price') {
          toEmit = toEmit + '-' + this.priceMax
        }
        this.$emit('setPriceRange', toEmit)
        // if (this.percentValue === 'Custom') {
        //   var toEmit = ''
        //   if (this.percentMin === null) {
        //     toEmit = '0%'
        //   } else {
        //     toEmit = this.percentMin + '%'
        //   }
        //   if (this.percentMax === null) {
        //     toEmit = toEmit + '-100%'
        //   } else {
        //     toEmit = toEmit + '-' + this.percentMax + '%'
        //   }
        //   this.$emit('setForecastedGrowthPercent', toEmit)
        // } else {
        //   if (this.percentValue === '30%+') {
        //     this.$emit('setForecastedGrowthPercent', '30%')
        //   } else {
        //     this.$emit('setForecastedGrowthPercent', this.percentValue)
        //   }
        // }
        // if (this.dollarValue === '2m+') {
        //   this.$emit('setForecastedGrowthDollar', '2m')
        // } else {
        //   this.$emit('setForecastedGrowthDollar', this.dollarValue)
        // }
        // this.$emit('setForecastedYear', this.yearValue.substring(0, 1))
        // this.hideOverlay()
        this.$emit('hidePricingFilterOverlay')
        this.$emit('load')
      },
    },
  }
</script>

<style lang="sass">
  .pricing-filter
    .v-overlay__content
      width: 100%
      height: 100%

      .v-card
        position: absolute
        top: 140px
        left: 10px
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
            /* color: black */
            font-weight: 600

        .v-btn-toggle:not(.v-btn-toggle--group)
          background: white
          color: rgba(0, 0, 0, 0.5)

        .theme--light.v-btn-toggle .v-btn.v-btn--active
          background-color: $color-emerald

          span
            color: white

        .theme--light.v-btn-toggle:not(.v-btn-toggle--group) .v-btn.v-btn
          border-color: $color-emerald

  #search-listings
    .pricing-filter
      .v-btn
        color: black

  @media (max-width: 960px)
    .pricing-filter
      .v-overlay__content
        .v-card
          left: 15px

</style>
<style scoped>
.pricing-apply-button {
  color: white !important;
}
.pricing-reset-button {
  color: #06a550 !important;
  border: solid #06a550;
  border-width: 1px;
}
.price-range-hyphen {
  margin-top:60%;
  margin-left:40%;
  font-size:20px;
}
p {
    margin-bottom: 2px
}
</style>
