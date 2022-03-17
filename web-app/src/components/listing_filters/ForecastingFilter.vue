<template>
  <v-overlay
    light
    opacity="0"
    :value="showForecastingFilter"
    class="forecasting-filter"
  >
    <v-card
      v-click-outside="hideOverlay"
      width="350"
      :height="percentValue == 'Custom' ? '430':'380'"
      light
    >
      <v-card-text>
        <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="12"
          >
            <!-- <h4>
              Forecasted Growth
            </h4> -->
            <!-- <v-row> -->
              <!-- <v-col> -->
                <h4>
              Percentage increase in forecasted value
            </h4>
            <v-row
            v-if="percentValue === 'Custom'"
            style="height:80px"
            >
              <v-col
              :md="4"
              >
                <v-text-field
                  v-model="percentMin"
                  name="percentMin"
                  placeholder="Min"
                />
              </v-col>
              <v-col
              :md="3"
              style="margin-left:0px;padding-left:0px;margin-top:20px;"
              >
              <span
              style="margin-left:0px;"
              >%</span>
              <span
              style="margin-left:50%;"
              >-</span>
              </v-col>
              <v-col
              :md="4"
              >
                <v-text-field
                  v-model="percentMax"
                  name="percentMax"
                  placeholder="Max"
                />
              </v-col>
              <v-col
              :md="1"
              style="margin-left:0px;padding-left:0px;margin-top:20px;"
              >
              <p>%</p>
              </v-col>
            </v-row>
                <v-select
                  v-model="percentValue"
                  :items="percentItems"
                  label="(%)"
                />
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
                <h4
                style="margin-top:20px;"
                >
              Forecasted value
            </h4>
                <v-select
                  v-model="dollarValue"
                  :items="dollarItems"
                  label="($)"
                />
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
            <v-row>
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
                />
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
                </v-col>
            </v-row>
            <v-row
              align="center"
            >
              <v-btn
                class="forecasting-reset-button"
                :absolute="true"
                :left="true"
                @click="resetValues"
              >
                Reset
              </v-btn>
              <v-btn
                color="primary"
                @click="load"
                class="forecasting-apply-button"
                :absolute="true"
                :right="true"
              >
                Apply
              </v-btn>
            </v-row>
          </v-col>
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
        </v-row>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>

  export default {
    name: 'ForecastingFilter',
    props: {
      showForecastingFilter: Boolean,
      forecastYear: Number,
      // ForecastingValue: Number,
    },

    watch: {
      forecastPercent (val) {
        this.percentValue = val
      },
      forecastDollar (val) {
        this.dollarValue = val
      },
      forecastYear (val) {
        this.yearIndex = val
      },
    },

    data: () => ({
      percentValue: 0,
      percentMin: null,
      percentMax: null,
      dollarValue: 0,
      yearValue: '1 year',

      item: {},
      tickLabels: [
        '1 year',
        '2 years',
        '3 years',
      ],
      yearIndex: 0,
      percentItems: [
        // { name: 'Any', value: 'Any' },
        // { name: '0-5%', value: '0-5%' },
        // { name: '5%-10%', value: '5%-10%' },
        // { name: '10%-15%', value: '10%-15%' },
        // { name: '15%+', value: '15%' },
        'Any', '0%-10%', '10%-20%', '20%-30%', '30%+', 'Custom',
      ],
      dollarItems: ['Any', '100k-500k', '500k-1m', '1m-1.5m', '1.5m-2m', '2m+'],
      timeItems: ['1 year', '2 years', '3 years'],
    }),

    methods: {
      resetValues () {
        if (this.percentValue !== 'Custom') {
          this.percentValue = 0
        }
        this.dollarValue = 0
        this.yearValue = '1 year'
        this.percentMin = null
        this.percentMax = null
      },
      hideOverlay (a) {
        // this.$emit('hideForecastingFilterOverlay')
        if (a.target.className.indexOf('v-list-item') === -1) {
          this.$emit('hideForecastingFilterOverlay')
        }
      },
      load () {
        // this.$emit('setForecastedGrowthPercent', this.percentItems[this.percentValue].value)
        // this.$emit('setForecastedGrowthDollar', this.dollarItems[this.dollarValue])
        // this.$emit('setForecastedYear', this.yearIndex + 1)
        if (this.percentValue === 'Custom') {
          var toEmit = ''
          if (this.percentMin === null) {
            toEmit = '0%'
          } else {
            toEmit = this.percentMin + '%'
          }
          if (this.percentMax === null) {
            toEmit = toEmit + '-100%'
          } else {
            toEmit = toEmit + '-' + this.percentMax + '%'
          }
          this.$emit('setForecastedGrowthPercent', toEmit)
        } else {
          if (this.percentValue === '30%+') {
            this.$emit('setForecastedGrowthPercent', '30%')
          } else {
            this.$emit('setForecastedGrowthPercent', this.percentValue)
          }
        }
        if (this.dollarValue === '2m+') {
          this.$emit('setForecastedGrowthDollar', '2m')
        } else {
          this.$emit('setForecastedGrowthDollar', this.dollarValue)
        }
        this.$emit('setForecastedYear', this.yearValue.substring(0, 1))
        // this.hideOverlay()
        this.$emit('hideForecastingFilterOverlay')
        this.$emit('load')
      },
    },
  }
</script>

<style lang="sass">
  .forecasting-filter
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
    .forecasting-filter
      .v-btn
        color: black

  @media (max-width: 960px)
    .forecasting-filter
      .v-overlay__content
        .v-card
          left: 15px

</style>
<style scoped>
.forecasting-apply-button {
  color: white !important;
}
.forecasting-reset-button {
  color: #06a550 !important;
  border: solid #06a550;
  border-width: 1px;
}
.percent-range-hyphen {
  margin-top:60%;
  margin-left:40%;
  font-size:20px;
}
</style>
