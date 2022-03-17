<template>
  <!-- eslint-disable -->
  <div class="buyer-score-container">
    <section-loader
      :loading="loading"
    />
    <div class="buyer-score-data">
      <div class="buyer-score-estimate">
        <label>Honely Estimate</label>
        <p class="estimate-value">{{ getHonelyEstimate }}</p>
      </div>
      <div class="buyer-score-offer">
        <label>Enter Potential Offer</label>
        <input type="text" :placeholder="getHonelyEstimatePlaceholder" name="buyer-score-offer" @keyup="getBuyerScore" :disabled="!getHonelyEstimatePlaceholder">
        <div class="buyer-score-message">{{ message }}</div>
      </div>
    </div>
    <div class="buyer-score-chart">
      <div class="buyer-score-chart-heading">
        Buyer's Score
      </div>
      <div class="chart-wrapper">
        <apexchart
          v-if="score"
          width="100%"
          height="330px"
          :options="buyerScoreChartOptions"
          :series="buyerScoreChartData"
        />
      </div>
      <div class="chart-data-label">{{ reading }}</div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'BuyerScoreBlock',
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
    },
    props: {
      forecast: Object,
    },
    data () {
      return {
        score: [0],
        reading: '--',
        timer: null,
        message: '',
        loading: false,
      }
    },
    computed: {
      isActiveProperty () {
        let status = false
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.property_status) {
            if (this.forecast.property_forecast.property_status === 'Active') {
              status = true
            }
          }
        }
        return status
      },
      getListingPrice () {
        let price = null
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.list_price) {
            price = this.formatNumber(this.forecast.property_forecast.list_price, true)
          }
        }
        return price
      },
      getHonelyEstimate () {
        let estimate = '--'
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.appraisal) {
            estimate = this.formatNumber(this.forecast.property_forecast.appraisal, true)
          }
        }
        return estimate
      },
      getHonelyEstimatePlaceholder () {
        let estimate = null
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.appraisal) {
            estimate = this.formatNumber(this.forecast.property_forecast.appraisal, true)
          }
        }
        return estimate
      },
      buyerScoreChartOptions () {
        return {
          chart: {
            type: 'radialBar',
            offsetY: -10,
            sparkline: {
              enabled: true,
            },
          },
          plotOptions: {
            radialBar: {
              startAngle: -90,
              endAngle: 90,
              hollow: {
                margin: 0,
                size: '70%',
                background: '#ffffff',
              },
              track: {
                background: '#24cb43',
                dropShadow: {
                  enabled: false,
                },
              },
              dataLabels: {
                name: {
                  show: false,
                },
                show: true,
                value: {
                  show: true,
                  offsetY: 10,
                  fontSize: '90px',
                  fontWeight: '600',
                  fontFamily: 'Poppins',
                  formatter: function (val, opts) {
                    return val
                  },
                },
              },
            },
          },
          grid: {
            padding: {
              top: -10,
            },
          },
          fill: {
            colors: ['#ff7d1f'],
            type: 'solid',
            gradient: {
              shade: 'none',
            },
          },
          stroke: {
            lineCap: 'round',
          },
        }
      },
      buyerScoreChartData () {
        let data = []
        if (this.score) {
          data = [this.score]
        }
        return data
      },
    },
    watch: {
      forecast: function () {
        this.getInitialBuyerScore()
      },
    },
    mounted () {
      this.reading = '--'
      this.score = [0]
      this.timer = null
      this.getInitialBuyerScore()
    },
    methods: {
      formatNumber (num, currency) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            num = Math.round(num / 100) * 100
            if (currency) {
              return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              return num
            }
          } else if (typeof (num) === 'string') {
            num = Math.round(parseFloat(num) / 100) * 100
            if (currency) {
              return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
            } else {
              return num
            }
          }
        } else {
          return ''
        }
      },
      formatCurrencyInput (num) {
        let result = ''
        if (num) {
          if (!isNaN(num)) {
            result = parseFloat(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          }
        }
        return result
      },
      getScoreReading (score) {
        let reading = '--'
        if (score) {
          if (this.score <= 50) {
            reading = 'Poor'
          } else if (this.score > 50 && this.score <= 60) {
            reading = 'Average'
          } else if (this.score > 60 && this.score <= 75) {
            reading = 'Solid'
          } else if (this.score > 75) {
            reading = 'Great'
          }
        }
        return reading
      },
      getInitialBuyerScore () {
        if (this.forecast && this.forecast.property_forecast && this.forecast.property_forecast.property_id && this.forecast.property_forecast.appraisal) {
          this.loading = true
          axios.get('https://api.honely.com/lookup/competitive_score', {
            params: {
              property_id: this.forecast.property_forecast.property_id,
              listing_price: this.forecast.property_forecast.appraisal,
            },
          }).then((response) => {
            this.loading = false
            if (response.data) {
              this.score = [parseFloat(response.data.competitive_score).toFixed(0)]
              this.reading = this.getScoreReading(this.score)
            }
          }).catch((error) => {
            this.loading = false
            console.log('[ERROR] Failed to fetch buyer score data')
            console.log(error)
          })
        } else {
          this.loading = false
          this.score = [0]
          this.reading = '--'
        }
      },
      getBuyerScore (event) {
        if (event.keyCode !== 37 && event.keyCode !== 38 && event.keyCode !== 39 && event.keyCode !== 40 && event.keyCode !== 13) {
          // console.log(event.target.value)
          // format number to currency
          const offer = event.target.value.trim().replaceAll(',', '').replace('$', '')
          event.target.value = this.formatCurrencyInput(offer)

          if (offer.length >= 4) {
            this.loading = true
            this.message = ''
            // point to root
            const self = this
            // clear timer
            clearTimeout(this.timer)
            // create new timer
            this.timer = setTimeout(function () {
              if (self.forecast && self.forecast.property_forecast && self.forecast.property_forecast.property_id) {
                axios.get('https://api.honely.com/lookup/competitive_score', {
                  params: {
                    property_id: self.forecast.property_forecast.property_id,
                    listing_price: offer,
                  },
                }).then((response) => {
                  self.loading = false
                  if (response.data) {
                    self.score = [parseFloat(response.data.competitive_score).toFixed(0)]
                    self.reading = self.getScoreReading(self.score)
                  }
                }).catch((error) => {
                  self.loading = false
                  console.log('[ERROR] Failed to fetch buyer score data')
                  console.log(error)
                })
              } else {
                self.loading = false
                self.score = [0]
                self.reading = '--'
              }
            }, 500)
          } else {
            this.loading = false
            if (offer !== '') {
              this.message = 'Please enter valid offer'
            } else {
              this.message = ''
            }
          }
        }
      },
    },
  }
</script>
