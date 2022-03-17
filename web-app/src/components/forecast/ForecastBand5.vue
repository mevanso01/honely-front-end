<template>
  <div
    id="forecast-band-5"
    class="mt-0 py-6"
  >
    <!-- <v-container
      v-bind:class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
      id="forecast-blocker-5"
    > -->
    <v-container
      id="forecast-blocker-5"
    >
      <!-- render blocked overlay -->
      <!-- <div
        v-if="isLogin === false"
        class="container-overlay"
      >
        <div class="overlay-wrapper">
          <p>Please create an account to access competitive score</p>
          <v-btn
        class="forecastcard-signup"
        color="white"
        elevation="3"
        text
        @click="displayLoginOverlay('create-account')"
      >
        <span>
          Sign Up
        </span>
      </v-btn>
      <p>Already have an account? <a @click="displayLoginOverlay('sign-in')">Log In</a> instead.</p>
        </div>
      </div> -->

      <!-- <div
        v-if="isLogin && isOverLimit"
        class="container-overlay"
      >
        <div class="overlay-wrapper">
          <p>Please upgrade your account to access competitive score.</p>
          <v-btn
        class="forecastcard-signup"
        color="white"
        elevation="3"
        text
        @click="toSubscribe"
      >
        <span>
          Upgrade
        </span>
      </v-btn>
        </div>
      </div> -->

      <!-- <div
        v-if="isLogin && !isOverLimit && isOverLimitEnterprise"
        class="container-overlay"
      >
        <div class="overlay-wrapper">
          <p>Please contact us to discuss our custom real estate data and analytics solutions</p>
          <v-btn
        class="forecastcard-signup"
        color="white"
        elevation="3"
        text
        @click="toContactPg()"
      >
        <span>
          Contact Us
        </span>
      </v-btn>
        </div>
      </div> -->
      <!-- /render blocked overlay -->
      <v-row
        justify="center"
      >
        <v-col
          sm="12"
          :class="{'align-center text-center px-2': $vuetify.breakpoint.smAndDown, 'align-center text-center px-8': $vuetify.breakpoint.mdAndUp}"
        >
          <base-heading
            class="mt-0 forecast-band-header pb-1"
            title=""
            weight="medium"
            size="text-h4"
            space="1"
          >
            <span v-if="forecast && forecast.property_forecast">
              <!-- 4. -->
              <i
                class="mdi mdi-crown-circle"
                aria-hidden="true"
              />
            </span>
            Buyer Score
            <sup>
              <span>
                <v-tooltip
                  content-class='custom-tooltip'
                  bottom
                  max-width="300px"
                >
                  <template v-slot:activator="{ on, attrs }">
                    <i
                      :class="'pt-1 mb-5 mdi band-header-icon mdi-information'"
                      aria-hidden="true"
                      v-bind="attrs"
                      style="color: black; font-size: 20px"
                      v-on="on"
                    />
                  </template>
                  <span>This tool helps you make the right offer. The higher the score received, the better the offer is from a buyer's perspective. This tool is most effective when comparing multiple homes and deciding which offer to make. To do so, enter the bid price you’re willing to make on each prospective home, the highest score between those homes dictates that to be the best investment/bang for your buck.</span>
                </v-tooltip>
              </span>
            </sup>
          </base-heading>
          <!-- Property's Honely valuation is null -->
          <v-row
            v-if="this.forecast.property_forecast.appraisal === null"
            class="forecast-section-below-heading"
          >
            <v-col
              style="padding-top: 25px; font-size: 22px; color: #343634;"
            >
              Sorry! Buyer Score can not be computed for this property.
            </v-col>
          </v-row>
          <!-- Property is not active and has honely valuation -->
          <v-row
            v-if="!isPropertyActive && this.forecast.property_forecast.appraisal"
            justify="start"
            class="forecast-section-below-heading"
          >
            <v-col
              sm="12"
              class="align-center text-center px-8"
            >
              <v-card
                max-width="1200"
                elevation="0"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              >
                <!-- v-card-title
                  class="text-h5"
                >
                  Calculator
                  <span>
                    please enter all three values.
                  </span>
                </! -->
                <v-form
                  v-on:submit.prevent
                  class="mx-3 mortgage-calculator"
                >
                  <div class="row-2cols competitive-score-section">
                    <div class="col competitive-price">
                      <div class="col-row">
                        <label>The Honely estimate for this property is</label>
                        <!-- Property has honely valauation -->
                        <span v-if="this.forecast.property_forecast.appraisal">{{ getAppraisalWithCurrency }}</span>
                        <span v-else>Sorry! Buyer Score can not be computed for this property.</span>
                      </div>
                      <div
                        class="col-row"
                      >
                        <v-text-field
                          v-model="listPrice"
                          :rules="listPriceRules"
                          label="Enter Potential Offer"
                          hint="Enter numbers only, no commas. (Minimum 4 digits)"
                          placeholder="0"
                          required
                          v-on:keyup="getCompetitiveScore"
                        />
                      </div>
                    </div>
                    <div
                      class="col competitive-score"
                      id="competitive-score-wrapper"
                    >
                      <label>Buyer Score</label>
                      <span
                        v-if="competitiveScoreLoading"
                      >--</span>
                      <span
                        v-if="competitive_score==null && !competitiveScoreLoading"
                      >--</span>
                      <span
                        v-if="competitive_score >=75 && !competitiveScoreLoading"
                        style="color: green"
                      >{{ Math.round(competitive_score) }}</span>
                      <span
                        v-if="competitive_score <75 && competitive_score >=50 && !competitiveScoreLoading"
                        style="color: #FFBF00"
                      >{{ Math.round(competitive_score) }}</span>
                      <span
                        v-if="competitive_score <50 && competitive_score >0 && !competitiveScoreLoading"
                        style="color: red"
                      >{{ Math.round(competitive_score) }}</span>
                      <span
                        v-if="competitive_score == 0 && !competitiveScoreLoading"
                        style="color: red"
                      >{{ Math.round(competitive_score) }}</span>
                      <!-- competitive-score-reference-container -->
                      <div class="competitive-score-reference-container">
                        <div class="reference-label-row">
                          <span>Least</span>
                          <span>Average</span>
                          <span>Most</span>
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
                      <!-- /competitive-score-reference-container -->
                      <span
                        class="competitive-score-loader"
                        v-if="competitiveScoreLoading"
                      >
                        <circle-spin :loading="competitiveScoreLoading"></circle-spin>
                      </span>
                      <p class="competitive-score-notes"><strong>Buyer Score</strong> is a composite of the list price, Honely estimated value, and statistics of comparable properties.</p>
                    </div>
                  </div>
                </v-form>
              </v-card>
            </v-col>
          </v-row>
          <!-- Property is active -->
          <v-row
            v-if="isPropertyActive"
            class="forecast-section-below-heading"
          >
            <v-col
              sm="12"
              class="align-center text-center px-8"
            >
              <v-card
                max-width="1200"
                elevation="0"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              >
                <v-form
                  v-on:submit.prevent
                  class="mx-3 mortgage-calculator"
                >
                  <div class="row-2cols competitive-score-section">
                    <div class="col competitive-price">
                      <div class="col-row">
                        <label>Current List Price</label>
                        <!-- Property doesn't have list price but has honely valuation -->
                        <span v-if="!this.forecast.property_forecast.list_price && this.forecast.property_forecast.appraisal">${{ this.formatPrice(this.forecast.property_forecast.appraisal) }}</span>
                        <span v-else-if="rentCheck()">--</span>
                        <span v-else>${{ this.formatPrice(this.forecast.property_forecast.list_price) }}</span>
                      </div>
                      <div class="col-row">
                        <v-text-field
                          v-model="listPrice"
                          :rules="listPriceRules"
                          label="Enter Potential Offer"
                          hint="Enter numbers only, no commas. (Minimum 4 digits)"
                          placeholder="0"
                          required
                          v-on:keyup="getCompetitiveScore"
                        />
                      </div>
                    </div>
                    <div
                      class="col competitive-score"
                      id="competitive-score-wrapper"
                    >
                      <label>Buyer Score</label>
                      <span
                        v-if="competitiveScoreLoading"
                      >--</span>
                      <span
                        v-if="competitive_score==null && !competitiveScoreLoading"
                      >--</span>
                      <span
                        v-if="competitive_score >=75 && !competitiveScoreLoading"
                        style="color: green"
                      >{{ Math.round(competitive_score) }}</span>
                      <span
                        v-if="competitive_score <75 && competitive_score >=50 && !competitiveScoreLoading"
                        style="color: #FFBF00"
                      >{{ Math.round(competitive_score) }}</span>
                      <span
                        v-if="competitive_score <50 && competitive_score >= 0 && !competitiveScoreLoading"
                        style="color: red"
                      >{{ Math.round(competitive_score) }}</span>
                      <span
                        v-if="competitive_score == 0 && !competitiveScoreLoading"
                        style="color: red"
                      >{{ Math.round(competitive_score) }}</span>
                      <!-- competitive-score-reference-container -->
                      <div class="competitive-score-reference-container">
                        <div class="reference-label-row">
                          <span>Least</span>
                          <span>Average</span>
                          <span>Most</span>
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
                      <!-- /competitive-score-reference-container -->
                      <span
                        class="competitive-score-loader"
                        v-if="competitiveScoreLoading"
                      >
                        <circle-spin :loading="competitiveScoreLoading"></circle-spin>
                      </span>
                      <p class="competitive-score-notes"><strong>Buyer Score</strong> is a composite of the list price, Honely estimated value, and statistics of comparable properties.</p>
                    </div>
                  </div>
                </v-form>
              </v-card>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <login-overlay
      style="text-align: left;"
      ref="loginOverlay"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
  />
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'ForecastBand5',

    props: {
      forecast: Object,
      initialValue: String,
      user: Object,
    },

    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },

    data: () => ({
      downPayment: null,
      mortgageTerm: null,
      interestRate: null,
      homeValue: null,
      estimatedMonthlyPayment: '0.00',
      listPrice: null,
      competitive_score: null,
      timer: null,
      competitiveScoreLoading: false,
      listPriceRules: [
        v => (!v || (v.length === 0 || v.length >= 5)) || 'Please enter at least 4 digits',
      ],
      showLoginOverlay: false,
      overlayTab: 'sign-up',
      oldEnteredPrice: 0,
    }),

    computed: {
      homeValueComp: {
        // getter
        get: function () {
          if (this.homeValue) {
            return this.homeValue
          } else if (this.initialValue) {
            return this.initialValue
          } else {
            return null
          }
        },
        // setter
        set: function (newValue) {
          this.homeValue = newValue
        },
      },
      isLogin () {
        // console.log(this.user)
        if (this.user) {
          if (this.user.membership_type === null) {
            return false
          } else {
            return true
          }
        } else {
          return false
        }
      },
      isOverLimit () {
        if (this.user.lookup_count !== null) {
          // if (this.user.membership_type === 'FREE' && this.user.lookup_count > 3) {
          //   return true
          // } else if (this.user.membership_type === 'STARTER' && this.user.lookup_count > 10) {
          //   return true
          // } else if (this.user.membership_type === 'PRO' && this.user.lookup_count > 25) {
          //   return true
          // } else {
          //   return false
          // }
          if (this.user.blocked === 'YES') {
            return true
          } else {
            return false
          }
        } else {
          return true
        }
      },
      isOverLimitEnterprise () {
        if (this.user.lookup_count !== null) {
          // if (this.user.membership_type === 'ENTERPRISE' && this.user.lookup_count > 100) {
          //   return true
          // } else {
          //   return false
          // }
          if (this.user.blocked === 'YES') {
            return true
          } else {
            return false
          }
        } else {
          return true
        }
      },
      isPropertyActive () {
        if (this.forecast.property_forecast) {
          if (this.forecast.property_forecast.property_status && this.forecast.property_forecast.property_status !== null) {
            if (this.forecast.property_forecast.property_status === 'Active' || this.forecast.property_forecast.property_status === 'For Sale') {
              return true
            } else {
              return false
            }
          } else {
            return false
          }
        } else {
          return false
        }
      },

      getAppraisalWithCurrency () {
        if (this.forecast && this.forecast.property_forecast.appraisal) {
          var appraisal = this.forecast.property_forecast.appraisal.toString().split('.')[0]
          appraisal = parseInt(appraisal)
          appraisal = (appraisal % 100 >= 50) ? Math.ceil(appraisal / 100) * 100 : Math.floor(appraisal / 100) * 100
          return this.formatCurrency(appraisal)
        } else {
          return ''
        }
      },

      getAppraisal () {
        if (this.forecast && this.forecast.property_forecast.appraisal) {
          return this.formatNum(this.forecast.property_forecast.appraisal)
        } else {
          return ''
        }
      },
    },
    watch: {
      forecast: function () {
        // console.log('forecast changed......')
        this.listPrice = null
        this.getCompetitiveScoreForListPrice()
      },
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },

    updated () {
      // const x = this.getCompetitiveScore()
      // setTimeout(function(){ x() }, 500)
      // this.getCompetitiveScore()
    },
    mounted () {
      // console.log(this.forecast)
      this.getCompetitiveScoreForListPrice()
      /* window.setInterval(() => {
        this.setLoadingFalse()
        if (this.listPrice < 10000) {
          this.competitive_score = null
        }
      }, 3000) */
    },
    methods: {
      rentCheck () {
        var x = parseFloat(this.forecast.property_forecast.list_price) / parseFloat(this.forecast.property_forecast.appraisal)
        if (x < 0.15) {
          return true
        } else {
          return false
        }
      },
      formatPrice (value) {
        // let val = (value/1).toFixed(2).replace('.', ',')
        if (value) {
          value = value.toString().split('.')[0]
          value = parseInt(value)
          value = (value % 100 >= 50) ? Math.ceil(value / 100) * 100 : Math.floor(value / 100) * 100
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
      toContactPg () {
        window.location.href = '/contact'
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      setLoadingFalse () {
        this.competitiveScoreLoading = false
      },
      isNumber: function (evt) {
        // evt = (evt) ? evt : window.event
        if (!evt) {
          evt = window.event
        }
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 46) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      getCompetitiveScoreForListPrice () {
        // console.log(this.forecast)
        if (this.forecast && this.forecast.property_forecast.property_status) {
          if (this.forecast.property_forecast.property_status === 'active' || this.forecast.property_forecast.property_status === 'For Sale') {
            if (this.forecast.property_forecast.list_price) {
              this.competitiveScoreLoading = true
              axios.get('https://api.honely.com/lookup/competitive_score', {
                params: {
                  property_id: this.forecast.property_forecast.property_id,
                  listing_price: this.listPrice,
                },
              }).then((response) => {
                this.competitive_score = response.data.competitive_score
                this.competitiveScoreLoading = false
              })
            } else {
              this.competitive_score = null
            }
          } else {
            this.competitive_score = null
          }
        } else {
          this.competitive_score = null
        }
      },
      getCompetitiveScore () {
        // format price input to currency
        let listPriceNum = null
        if (this.listPrice) {
          listPriceNum = this.listPrice.trim().replace('$', '').replaceAll(',', '')
          if (!isNaN(listPriceNum)) {
            this.listPrice = this.formatCurrency(listPriceNum)
          } else {
            this.listPrice = null
            listPriceNum = null
          }
        }
        /* if (this.forecast.property_forecast.property_status === 'For Sale' && this.forecast.property_forecast.appraisal !== null) {
          // this.listPrice = null
        }
        if (this.forecast.property_forecast.property_id && !this.listPrice && this.forecast.property_forecast.property_status === 'For Sale' && this.forecast.property_forecast.appraisal !== null) {
          axios.get('https://api.honely.com/lookup/competitive_score', {
            params: {
              property_id: this.forecast.property_forecast.property_id,
            },
          }).then((response) => {
            console.log('vx: response.data.competitive_score in getCompetitiveScore method', response.data.competitive_score)
            this.competitive_score = response.data.competitive_score
            this.listPrice = null
          })
        } else if (this.forecast.property_forecast.property_id && this.listPrice) {
          axios.get('https://api.honely.com/lookup/competitive_score', {
            params: {
              property_id: this.forecast.property_forecast.property_id,
              listing_price: this.listPrice,
            },
          }).then((response) => {
            console.log('vx: response.data.competitive_score in getCompetitiveScore method', response.data.competitive_score)
            this.competitive_score = response.data.competitive_score
            // this.listPrice=null
          })
        } else {
          this.listPrice = null
          this.competitive_score = 0
        } */

        // console.log('old: ' + this.oldEnteredPrice)
        // console.log('entered: ' + this.listPrice)

        // note: only execute the api when list price exisits and larger than 5 and it's different from the previously entered
        // console.log(listPriceNum)
        if (listPriceNum && listPriceNum.length >= 4 && this.oldEnteredPrice !== listPriceNum) {
          clearTimeout(this.timer)
          this.oldEnteredPrice = listPriceNum
          this.competitiveScoreLoading = true

          // console.log(this.listPrice.length)
          this.timer = setTimeout(() => {
            if (listPriceNum && listPriceNum != null) {
              if (this.forecast.property_forecast.property_status === 'For Sale' && this.forecast.property_forecast.appraisal !== null) {
                // this.listPrice = null
              }
              if (this.forecast.property_forecast.property_id && !listPriceNum && this.forecast.property_forecast.property_status === 'For Sale' && this.forecast.property_forecast.appraisal !== null) {
                axios.get('https://api.honely.com/lookup/competitive_score', {
                  params: {
                    property_id: this.forecast.property_forecast.property_id,
                  },
                }).then((response) => {
                  // console.log('in for sell: ' + response.data.competitive_score)
                  // console.log('vx: response.data.competitive_score in getCompetitiveScore method', response.data.competitive_score)
                  // console.log('in 1: score -> ' + response.data.competitive_score)
                  this.competitive_score = response.data.competitive_score
                  this.competitiveScoreLoading = false
                  this.listPrice = null
                })
              } else if (this.forecast.property_forecast.property_id && listPriceNum) {
                axios.get('https://api.honely.com/lookup/competitive_score', {
                  params: {
                    property_id: this.forecast.property_forecast.property_id,
                    listing_price: listPriceNum,
                  },
                }).then((response) => {
                  // console.log(response.data)
                  // console.log('in not for sell: ' + response.data.competitive_score)
                  // console.log('vx: response.data.competitive_score in getCompetitiveScore method', response.data.competitive_score)
                  // console.log('in 2: score -> ' + response.data.competitive_score)
                  this.competitive_score = response.data.competitive_score
                  this.competitiveScoreLoading = false
                  // this.listPrice=null
                })
              } else {
                this.listPrice = null
                this.competitive_score = 0
                this.competitiveScoreLoading = false
              }
            }
          }, 500)
        }
      },
      toSubscribe () {
        window.location.href = '/subscribe'
      },
      formatCurrency (num) {
        // console.log(typeof (num))
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          } else if (typeof (num) === 'string') {
            return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          }
        } else {
          return ' '
        }
      },
      formatCurrencyForInput (num) {
        // console.log(typeof (num))
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          } else if (typeof (num) === 'string') {
            return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          }
        } else {
          return ''
        }
      },
      formatNum (num) {
        // console.log(typeof (num))
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            return parseFloat(num).toFixed(0)
          } else if (typeof (num) === 'string') {
            return parseInt(num)
          }
        } else {
          return ' '
        }
      },
    },
  }
</script>
<style>
.group {
  display: flex;
  flex: 1;
  justify-content: space-around;
}
#competitive-score-wrapper {
  position: relative;
}
#forecast-blocker-5 {
  position: relative;
}
.blocked .forecast-card {
  filter: blur(3px);
}
.container-overlay {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(240, 240, 240, 0.5);
  border-radius: 4px;
  z-index: 1;
}
.overlay-wrapper {
  color: #ffffff;
  display: flex;
  flex-wrap: wrap;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  height: 100%;
}
.overlay-wrapper p {
  margin: 0;
  width: 100%;
  color: #000000;
  padding: 10px;
  font-weight: bold;
  font-size: 17px;
}
.overlay-wrapper button {
  color: #ffffff;
  background: #3c75fa;
  padding: 10px;
  min-width: 115px;
  margin: 1rem 0 1rem 0;
  border: 1px solid #3063db;
  border-radius: 3px;
}
.overlay-wrapper button:hover {
  background: #3063db;
}
.competitive-price .v-label {
  height: 24px !important;
}
.competitive-score-reference-container {
  width: 95%;
  padding: 10px 0;
  margin: 0 auto;
}
.reference-label-row {
  display: flex;
  justify-content: space-between;
  padding-top: 10px;
  padding-bottom: 7px;
}
.reference-label-row-below {
  display: flex;
  justify-content: space-between;
  padding-top: 7px;
  padding-bottom: 10px;
}
.reference-label-row span, .reference-label-row-below span {
  font-size: 12px;
  line-height: 100%;
}
.reference-bar-row {
  background: rgb(255,0,0);
  background: linear-gradient(90deg, rgba(255,0,0,1) 0%, rgba(255,191,0,1) 50%, rgba(7,135,28,1) 100%);
  height: 12px;
  border-radius: 5px;
}
span.competitive-score-loader {
  position: absolute;
  font-size: 1rem;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.custom-tooltip {
    opacity: 1!important;
}
.competitive-score-notes {
  font-size: 12px;
  text-align: left;
}
</style>
