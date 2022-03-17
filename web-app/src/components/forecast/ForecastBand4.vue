<template>
  <div
    id="forecast-band-4"
    class="mt-0 py-6"
  >
    <!-- eslint-disable -->
    <v-container>
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
              <!--7.-->
              <i
                class="mdi mdi-calculator"
                aria-hidden="true"
              />
            </span>
            <span v-if="forecast && !forecast.property_forecast">
              <!-- 4. -->
              <i
                class="mdi mdi-calculator"
                aria-hidden="true"
              />
            </span>
            Property Investment Calculator
          </base-heading>
          <v-row
            justify="start"
            class="forecast-section-below-heading"
          >
            <v-col
              sm="12"
              class="align-center text-center px-2"
            >
              <!-- <v-card
                :class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
                max-width="1200"
                elevation="0"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              > -->
              <v-card
                elevation="0"
                rounded="10px"
                class="forecast-card mx-auto text-left pb-5"
              >
                <!-- render blocked overlay -->
                <!-- <div
                  v-if="isLogin === false"
                  class="container-overlay"
                >
                  <div class="overlay-wrapper">
                    <p>Please create an account to access 3 free smart searchess</p>
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
                    <p>Please upgrade your account to access more statistics</p>
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
                
                <!-- investment calculator form -->
                <div class="investment-cal-form">
                  <!-- investment-cal-form-left -->
                  <div class="investment-cal-form-left">
                    <!-- investment-cal-section -->
                    <div class="investment-cal-section">
                      <p class="section-heading">Financing Information</p>
                      <div class="row-flex-2cols">
                        <div class="input-col">
                          <label>Home Value</label>
                          <input type="text" name="inv-home-value" id="inv-home-value" :value="getHomeValue" @keyup="doUpdateResult(true, 'inv-home-value')">
                          <p class="error-msg" id='msg-home-value'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Down Payment</label>
                          <div class="input-number-custom">
                            <span class="minus" @click="doIncrementDecrement('inv-down-payment', false, 1, 0, 100, false, true, null)"><i class="fas fa-minus"></i></span>
                            <input type="text" name="inv-down-payment" id="inv-down-payment" value='20%' @keyup="doUpdateResult(false, 'inv-down-payment')">
                            <span class="plus" @click="doIncrementDecrement('inv-down-payment', true, 1, 0, 100, false, true, null)"><i class="fas fa-plus"></i></span>
                          </div>
                          <p class="error-msg" id='msg-down-payment'>&nbsp;</p>
                        </div>
                      </div>
                      <div class="investment-payment">
                        <p class="result-sm">Your monthly payment</p>
                        <p class="result-large" id="result_monthly_payment">--</p>
                        <p class="result-sm"><span id="investment-term">15</span> years fixed loan term</p>
                      </div>
                      <div class="row-flex-3cols">
                        <div class="input-col">
                          <label>Interest Rate</label>
                          <div class="input-number-custom">
                            <span class="minus" @click="doIncrementDecrement('inv-interest', false, 0.1, 0, 100, false, true, null)"><i class="fas fa-minus"></i></span>
                            <input type="text" name="inv-interest" id="inv-interest" value='3%' @keyup="doUpdateResult(false, 'inv-interest')">
                            <span class="plus" @click="doIncrementDecrement('inv-interest', true, 0.1, 0, 100, false, true, null)"><i class="fas fa-plus"></i></span>
                          </div>
                          <p class="error-msg" id='msg-interest'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Loan Term</label>
                          <div class="input-number-custom">
                            <span class="minus" @click="doIncrementDecrement('inv-terms', false, 1, 5, 45, false, false, 'Years')"><i class="fas fa-minus"></i></span>
                            <input type="text" name="inv-terms" id="inv-terms" value='15 Years' @keyup="doUpdateResult(false, 'inv-terms')">
                            <span class="plus" @click="doIncrementDecrement('inv-terms', true, 1, 5, 45, false, false, 'Years')"><i class="fas fa-plus"></i></span>
                          </div>
                          <p class="error-msg" id='msg-terms'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Closing Cost</label>
                          <input type="text" name="inv-exp-closing" id="inv-exp-closing" placeholder="$0" @keyup="doUpdateResult(true, 'inv-exp-closing')">
                          <p class="error-msg" id='msg-exp-closing'>&nbsp;</p>
                        </div>
                      </div>
                    </div>
                    <!-- /investment-cal-section -->

                    <!-- investment-cal-section -->
                    <div class="investment-cal-section">
                      <p class="section-heading">Monthly Expenses</p>
                      <div class="row-flex-3cols">
                        <div class="input-col">
                          <label>Monthly Mortgage*</label>
                          <input type="text" name="inv-exp-mortgage" id="inv-exp-mortgage" placeholder="$0" readonly>
                          <p class="label-msg">* principal &amp; interest</p>
                        </div>
                        <div class="input-col">
                          <label>Maintenance</label>
                          <input type="text" name="inv-exp-maintenance" id="inv-exp-maintenance" placeholder="$0" @keyup="doUpdateResult(true, 'inv-exp-maintenance')">
                          <p class="error-msg" id='msg-exp-maintenance'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Property Tax</label>
                          <input type="text" name="inv-exp-tax" id="inv-exp-tax" placeholder="$0" @keyup="doUpdateResult(true, 'inv-exp-tax')">
                          <p class="error-msg" id='msg-exp-tax'>&nbsp;</p>
                        </div>
                      </div>
                      <div class="row-flex-3cols">
                        <div class="input-col">
                          <label>Insurance</label>
                          <input type="text" name="inv-exp-insurance" id="inv-exp-insurance" placeholder="$0" @keyup="doUpdateResult(true, 'inv-exp-insurance')">
                          <p class="error-msg" id='msg-exp-insurance'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>HOA Fees</label>
                          <input type="text" name="inv-exp-hoa" id="inv-exp-hoa" placeholder="$0" @keyup="doUpdateResult(true, 'inv-exp-hoa')">
                          <p class="error-msg" id='msg-exp-hoa'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Utilities</label>
                          <input type="text" name="inv-exp-util" id="inv-exp-util" placeholder="$0" @keyup="doUpdateResult(true, 'inv-exp-util')">
                          <p class="error-msg" id='msg-exp-util'>&nbsp;</p>
                        </div>
                      </div>
                    </div>
                    <!-- /investment-cal-section -->

                    <!-- investment-cal-section -->
                    <div class="investment-cal-section">
                      <p class="section-heading">Monthly Income Streams</p>
                      <div class="row-flex-3cols">
                        <div class="input-col">
                          <label>Rental Income</label>
                          <input type="text" name="inv-income-rental" id="inv-income-rental" placeholder="$0" @keyup="doUpdateResult(true, 'inv-income-rental')">
                          <p class="error-msg" id='msg-income-rental'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Misc. Income</label>
                          <input type="text" name="inv-income-misc" id="inv-income-misc" placeholder="$0" @keyup="doUpdateResult(true, 'inv-income-misc')">
                          <p class="error-msg" id='msg-income-misc'>&nbsp;</p>
                        </div>
                        <div class="input-col">
                          <label>Laundry Income</label>
                          <input type="text" name="inv-income-laundry" id="inv-income-laundry" placeholder="$0" @keyup="doUpdateResult(true, 'inv-income-laundry')">
                          <p class="error-msg" id='msg-income-laundry'>&nbsp;</p>
                        </div>
                      </div>
                    </div>
                    <!-- /investment-cal-section -->
                  </div>
                  <!-- /investment-cal-form-left -->

                  <!-- investment-cal-form-right -->
                  <div class="investment-cal-form-right">
                    <div class="investment-result-container">
                      <div class="investment-result-summary">
                        <!-- investment-result-summary-top -->
                        <div class="investment-result-summary-top">
                          <p class="section-heading">Result</p>
                          <p class="label-text">Loan Amount</p>
                          <p class="result-text" id="result_loan_amt">$0</p>
                        </div>
                        <!-- /investment-result-summary-top -->

                        <!-- investment-result-summary-bottom -->
                        <div class="investment-result-summary-bottom">
                          <p class="label-text">Net Operating Income</p>
                          <p class="result-text" id="result_noi">$0</p>
                          <p class="label-text">Total Expenses</p>
                          <p class="result-text" id="result_total_exp">$0</p>
                          <p class="label-text">
                            Capitalization Rate*
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
                              <span>The capitalization rate is the rate of return on a real estate investment property based on the income that the property is expected to generate.</span>
                            </v-tooltip>
                          </p>
                          <p class="result-text" id="result_cap_rate">0%</p>
                          <p class="label-text">
                            Cash on Cash Return*
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
                              <span>Cash-on-cash return, often used in real estate transactions, calculates the cash income earned on the cash invested in a property.</span>
                            </v-tooltip>
                          </p>
                          <p class="result-text" id="result_cash_return">0%</p>
                          <p class="label-text">
                            Return on Investment*
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
                              <span>Return on investment (ROI) is a performance measure used to evaluate the efficiency of an investment or compare the efficiency of several investments.</span>
                            </v-tooltip>
                          </p>
                          <p class="result-text" id="result_inv_return">0%</p>
                          <div class="result-notes">
                            <p>* Rates are annualized</p>
                          </div>
                        </div>
                        <!-- /investment-result-summary-bottom -->
                      </div>
                      <button id="btn_inv_reset" @click="doResetForm">Reset</button>
                    </div>
                  </div>
                  <!-- /investment-cal-form-right -->
                </div>
                <!-- /investment calculator form -->
              </v-card>
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
    <login-overlay
      ref="loginOverlay"
      style="text-align: left;"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'ForecastBand4',
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },
    props: {
      forecast: Object,
      user: Object,
      initialValue: String,
    },

    data: () => ({
      showLoginOverlay: false,
      overlayTab: 'sign-up',
    }),
    computed: {
      isLogin () {
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
          if (this.user.blocked === 'YES') {
            return true
          } else {
            return false
          }
        } else {
          return true
        }
      },
      getHomeValue () {
        if (this.forecast) {
          if (this.forecast.property_forecast.appraisal) {
            return this.formatCurrency(this.forecast.property_forecast.appraisal)
          } else {
            return '$200,000'
          }
        } else {
          return '$200,000'
        }
      },
    },
    watch: {
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
      forecast: function () {
        this.doResetForm()
      },
    },
    mounted () {
      // this.homeValue = this.formatHomeValue(this.forecast.property_forecast.appraisal)
    },
    methods: {
      doIncrementDecrement (input, increament, step, min, max, currency, percent, custom) {
        const toChange = document.getElementById(input)
        let changed = false

        if (toChange) {
          // console.log(toChange.value)
          if (toChange.value !== null && toChange.value !== '') {
            let temp = toChange.value.trim().replaceAll('%', '').replaceAll(',', '').replace('$', '')
            if (custom && custom !== null) {
              temp = temp.replaceAll(custom, '').replaceAll(' ', '')
            }

            if (!isNaN(temp)) {
              if (increament) {
                // console.log('temp: ' + temp + ' | max: ' + max + ' | cal result: ' + parseFloat(parseFloat(temp) + parseFloat(step)))

                if (parseFloat(temp) >= parseFloat(min) && parseFloat(temp) < parseFloat(max) && (parseFloat(parseFloat(temp) + parseFloat(step)) <= parseFloat(max))) {
                  toChange.value = parseFloat(temp) + parseFloat(step)
                  changed = true
                }
              } else {
                if (parseFloat(temp) >= parseFloat(min) && parseFloat(temp) < parseFloat(max) && (parseFloat(parseFloat(temp) - parseFloat(step)) >= parseFloat(min))) {
                  toChange.value = parseFloat(temp) - parseFloat(step)
                  changed = true
                }
              }

              if (changed) {
                if (currency) {
                  toChange.value = this.formatCurrency(toChange.value)
                }

                if (percent) {
                  if (input === 'inv-interest') {
                    toChange.value = parseFloat(toChange.value).toFixed(2) + '%'
                  } else {
                    toChange.value = parseFloat(toChange.value).toFixed(0) + '%'
                  }
                }

                if (custom && custom !== null) {
                  toChange.value = toChange.value + ' ' + custom
                }

                this.doInvestmentCalculation()
              }
            }
          }
        }
      },
      formatCurrency (num) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          } else if (typeof (num) === 'string') {
            return parseInt(num).toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          }
        } else {
          return '$0'
        }
      },
      doUpdateResult (currency, input) {
        // console.log('[INFO] updating....')
        const toChange = document.getElementById(input)
        if (toChange) {
          const temp = this.cleanUpInputValue(toChange.value)
          const msg = input.replace('inv-', 'msg-')
          if (temp && temp !== null && temp !== '' && !isNaN(temp)) {
            if (currency) {
              toChange.value = this.formatCurrency(temp)
            }
            toChange.classList.remove('error-input')
            this.updateErrorMsg(msg, true)
            // update results
            this.doInvestmentCalculation()
          } else {
            toChange.classList.add('error-input')
            this.updateErrorMsg(msg, false)
          }
        }
      },
      updateErrorMsg (input, valid) {
        if (valid) {
          document.getElementById(input).innerHTML = '&nbsp;'
        } else {
          document.getElementById(input).innerHTML = 'Enter a valid number'
        }
      },
      cleanUpInputValue (value) {
        if (value) {
          if (value !== null && value !== '') {
            value = value.trim().replaceAll('$', '').replaceAll(',', '').replaceAll(' ', '').replaceAll('%', '').replace('Years', '').replace('years', '')
            return value
          }
        } else {
          return null
        }
      },
      doResetForm () {
        console.log('[INFO] Resetting form......')
        document.getElementById('inv-home-value').value = this.getHomeValue
        document.getElementById('inv-down-payment').value = '20%'
        document.getElementById('inv-interest').value = '3%'
        document.getElementById('inv-terms').value = '15 Years'
        document.getElementById('inv-exp-closing').value = ''
        document.getElementById('inv-income-rental').value = ''
        document.getElementById('inv-income-misc').value = ''
        document.getElementById('inv-income-laundry').value = ''
        document.getElementById('inv-exp-insurance').value = ''
        document.getElementById('inv-exp-hoa').value = ''
        document.getElementById('inv-exp-util').value = ''
        document.getElementById('inv-exp-tax').value = ''
        document.getElementById('inv-exp-maintenance').value = ''
        document.getElementById('inv-exp-mortgage').value = ''
        document.getElementById('result_monthly_payment').innerHTML = '--'
        document.getElementById('investment-term').innerHTML = '15'
        document.getElementById('result_total_exp').innerHTML = '$0'
        document.getElementById('result_noi').innerHTML = '$0'
        document.getElementById('result_cap_rate').innerHTML = '0%'
        document.getElementById('result_cash_return').innerHTML = '0%'
        document.getElementById('result_inv_return').innerHTML = '0%'
        document.getElementById('result_loan_amt').innerHTML = '$0'
      },
      doMonthlyMortgage (loan, rate, terms) {
        if (loan && rate && terms) {
          return parseFloat(loan) * (parseFloat(rate) * Math.pow(1 + parseFloat(rate), parseFloat(terms))) / (Math.pow(1 + parseFloat(rate), parseFloat(terms)) - 1)
        } else {
          return 0
        }
      },
      doInvestmentCalculation () {
        let homeValue = document.getElementById('inv-home-value')
        let downPayment = document.getElementById('inv-down-payment')
        let interest = document.getElementById('inv-interest')
        let terms = document.getElementById('inv-terms')
        let closing = document.getElementById('inv-exp-closing')
        let rental = document.getElementById('inv-income-rental')
        let misc = document.getElementById('inv-income-misc')
        let laundry = document.getElementById('inv-income-laundry')
        let insurance = document.getElementById('inv-exp-insurance')
        let hoa = document.getElementById('inv-exp-hoa')
        let util = document.getElementById('inv-exp-util')
        let tax = document.getElementById('inv-exp-tax')
        let maintenance = document.getElementById('inv-exp-maintenance')
        const mortgage = document.getElementById('inv-exp-mortgage')

        // calcualted values
        let downPaymentAmt = 0
        let loanAmt = 0
        let interestRate = 0
        let termMonths = 0
        let mortgagePayment = 0
        let totalExp = 0
        let monthlyExp = 0
        let incomeStream = 0
        let netOpIncome = 0
        let capRate = 0
        let cashReturn = 0
        let investReturn = 0

        if (homeValue) {
          homeValue = this.cleanUpInputValue(homeValue.value)
          if (homeValue == null || isNaN(homeValue)) {
            homeValue = 0
          }
        } else {
          homeValue = 0
        }
        if (downPayment) {
          downPayment = this.cleanUpInputValue(downPayment.value)
          if (downPayment == null || isNaN(downPayment)) {
            downPayment = 0
          }
        } else {
          downPayment = 0
        }
        if (interest) {
          interest = this.cleanUpInputValue(interest.value)
          if (interest == null || isNaN(interest)) {
            interest = 0
          }
        } else {
          interest = 0
        }
        if (terms) {
          terms = this.cleanUpInputValue(terms.value)
          if (terms == null || isNaN(terms)) {
            terms = 0
          }
        } else {
          terms = 0
        }
        if (closing) {
          closing = this.cleanUpInputValue(closing.value)
          if (closing == null || isNaN(closing)) {
            closing = 0
          }
        } else {
          closing = 0
        }
        if (rental) {
          rental = this.cleanUpInputValue(rental.value)
          if (rental == null || isNaN(rental)) {
            rental = 0
          }
        } else {
          rental = 0
        }
        if (misc) {
          misc = this.cleanUpInputValue(misc.value)
          if (misc == null || isNaN(misc)) {
            misc = 0
          }
        } else {
          misc = 0
        }
        if (laundry) {
          laundry = this.cleanUpInputValue(laundry.value)
          if (laundry == null || isNaN(laundry)) {
            laundry = 0
          }
        } else {
          laundry = 0
        }
        if (insurance) {
          insurance = this.cleanUpInputValue(insurance.value)
          if (insurance == null || isNaN(insurance)) {
            insurance = 0
          }
        } else {
          insurance = 0
        }
        if (hoa) {
          hoa = this.cleanUpInputValue(hoa.value)
          if (hoa == null || isNaN(hoa)) {
            hoa = 0
          }
        } else {
          hoa = 0
        }
        if (util) {
          util = this.cleanUpInputValue(util.value)
          if (util == null || isNaN(util)) {
            util = 0
          }
        } else {
          util = 0
        }
        if (tax) {
          tax = this.cleanUpInputValue(tax.value)
          if (tax == null || isNaN(tax)) {
            tax = 0
          }
        } else {
          tax = 0
        }
        if (maintenance) {
          maintenance = this.cleanUpInputValue(maintenance.value)
          if (maintenance == null || isNaN(maintenance)) {
            maintenance = 0
          }
        } else {
          maintenance = 0
        }

        if (!isNaN(homeValue) && !isNaN(downPayment) && !isNaN(interest) && !isNaN(terms)) {
          downPaymentAmt = parseFloat(homeValue) * (parseFloat(downPayment) / 100)
          loanAmt = parseFloat(homeValue) - parseFloat(downPaymentAmt)
          interestRate = parseFloat(interest) / 100 / 12
          termMonths = parseFloat(terms) * 12

          mortgagePayment = parseFloat(this.doMonthlyMortgage(loanAmt, interestRate, termMonths)).toFixed(0)
          monthlyExp = parseFloat(parseFloat(insurance) + parseFloat(hoa) + parseFloat(util) + parseFloat(tax) + parseFloat(maintenance)).toFixed(0)
          totalExp = parseFloat(parseFloat(monthlyExp) + parseFloat(mortgagePayment)).toFixed(0)
          incomeStream = parseFloat(parseFloat(rental) + parseFloat(misc) + parseFloat(laundry)).toFixed(0)
          netOpIncome = parseFloat(incomeStream) - parseFloat(monthlyExp)
          capRate = parseFloat(parseFloat((parseFloat(incomeStream) - parseFloat(monthlyExp)) / parseFloat(homeValue)) * 12).toFixed(4)
          cashReturn = parseFloat(parseFloat(parseFloat(parseFloat(incomeStream) - parseFloat(totalExp)) * 12 / (parseFloat(downPaymentAmt) + parseFloat(closing)))).toFixed(4)
          investReturn = parseFloat(parseFloat(parseFloat(incomeStream) - parseFloat(totalExp)) * 12 / parseFloat(homeValue)).toFixed(4)

          // developer notes
          // console.log('down payment: ' + downPaymentAmt)
          // console.log('loan: ' + loanAmt)
          // console.log('interest: ' + interestRate)
          // console.log('terms: ' + termMonths)
          // console.log('mortgage: ' + mortgagePayment)
          // console.log('monthly exp: ' + monthlyExp)
          // console.log('total exp: ' + totalExp)
          // console.log('income stream: ' + incomeStream)
          // console.log('net opt income: ' + netOpIncome)
          // console.log('cap rate: ' + capRate)
          // console.log('cash return: ' + cashReturn)
          // console.log('invest return: ' + investReturn)
          // set output
          mortgage.value = this.formatCurrency(mortgagePayment)

          // update results
          document.getElementById('result_monthly_payment').innerHTML = this.formatCurrency(totalExp)
          document.getElementById('investment-term').innerHTML = terms
          document.getElementById('result_total_exp').innerHTML = this.formatCurrency(totalExp)
          document.getElementById('result_noi').innerHTML = this.formatCurrency(netOpIncome)
          document.getElementById('result_cap_rate').innerHTML = parseFloat(parseFloat(capRate) * 100).toFixed(2) + '%'
          document.getElementById('result_cash_return').innerHTML = parseFloat(parseFloat(cashReturn) * 100).toFixed(2) + '%'
          document.getElementById('result_inv_return').innerHTML = parseFloat(parseFloat(investReturn) * 100).toFixed(2) + '%'
          document.getElementById('result_loan_amt').innerHTML = this.formatCurrency(loanAmt)
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
    },
  }
</script>

<style>
#forecast-band-4 {
  background-color: #eeeeee;
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
  text-align: center;
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
.investment-cal-form {
  width: 80%;
  padding: 1rem;
  display: flex;
  flex-wrap: wrap;
  margin: 0 auto;
  justify-content: space-between;
}
.investment-cal-form-left {
  width: 63%;
  padding: 2rem 2rem 1rem 2rem;
  background: #ffffff;
  border: 1px solid #07871c;
  box-shadow: 0px 3px 1px -2px rgb(0 0 0 / 20%), 0px 2px 2px 0px rgb(0 0 0 / 14%), 0px 1px 5px 0px rgb(0 0 0 / 12%);
}
.investment-cal-form-right {
  width: 32%;
}
.investment-cal-form p {
  line-height: 100%;
}
p.error-msg {
  display: block;
  margin-bottom: 0;
  font-size: 12px;
  padding-top: 2px;
  color: red;
  height: 14px;
}
p.label-msg {
  display: block;
  margin-bottom: 0;
  font-size: 12px;
  padding-top: 2px;
  height: 14px;
}
.investment-cal-section {
  width: 100%;
  padding-bottom: 1.5rem;
}
.investment-cal-section:last-of-type {
  padding-bottom: 10px;
}
.investment-cal-form .section-heading, .investment-result-container .section-heading {
  color: #e67e22;
  font-weight: bold;
  font-size: 22px;
}
.investment-cal-form label {
  font-weight: 600;
  font-size: 17px;
  display: block;
  line-height: 100%;
  padding-bottom: 5px;
  color: black;
}
.investment-cal-form input[type="text"],
.investment-cal-form input[type="number"],
.investment-cal-form select {
  display: block;
  width: 100%;
  font-size: 17px;
  padding: 7px 5px;
  border: 1px solid #cccccc;
  border-bottom-width: 2px;
  border-radius: 3px;
  background: #ffffff;
  outline: 0;
  -webkit-transition: all 0.40s ease-in-out;
  -moz-transition: all 0.40s ease-in-out;
  -ms-transition: all 0.40s ease-in-out;
  -o-transition: all 0.40s ease-in-out;
}
.investment-cal-form input[type="number"] {
  -webkit-appearance: textfield;
  -moz-appearance: textfield;
  appearance: textfield;
  border-radius: 0;
}
.investment-cal-form input[type=number]::-webkit-inner-spin-button,
.investment-cal-form input[type=number]::-webkit-outer-spin-button {
  -webkit-appearance: none;
}
.investment-cal-form input[type="text"]:focus,
.investment-cal-form input[type="number"]:focus,
.investment-cal-form select:focus {
  border-bottom-color: #07871c;
  border-bottom-width: 2px;
}
.investment-cal-form input[type="text"]:read-only {
  background: #eeeeee;
}
.row-flex-2cols, .row-flex-3cols {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  padding-bottom: 1.5rem;
}
.row-flex-2cols:last-of-type, .row-flex-3cols:last-of-type {
  padding-bottom: 0;
}
.row-flex-2cols div.input-col {
  width: 45%;
}
.row-flex-3cols div.input-col {
  width: 30%;
}
.input-number-custom {
  display: flex;
  align-items: center;
}
.input-number-custom span {
  display: block;
  text-align: center;
  line-height: 42px;
  height: 42px;
  padding: 0 12px;
  color: black;
  background: #ffffff;
  cursor: pointer;
  -webkit-transition: all 0.40s ease-in-out;
  -moz-transition: all 0.40s ease-in-out;
  -ms-transition: all 0.40s ease-in-out;
  -o-transition: all 0.40s ease-in-out;
  border: 1px solid #cccccc;
}
.input-number-custom span.minus {
  border-top-left-radius: 5px;
  border-bottom-left-radius: 5px;
}
.input-number-custom span.plus {
  border-top-right-radius: 5px;
  border-bottom-right-radius: 5px;
}
.input-number-custom span:hover {
  background: #07871c;
  color: #ffffff;
}
.input-number-custom input {
  text-align: center;
}
.investment-cal-form input.error-input, .investment-cal-form input.error-input:focus {
  border-color: red;
  border-bottom-color: red;
}
.investment-payment {
  text-align: center;
  padding-top: 1.5rem;
  padding-bottom: 3rem;
}
.investment-payment p.result-sm {
  font-weight: 600;
  margin-bottom: 5px;
}
.investment-payment p.result-large {
  font-size: 35px;
  font-weight: bold;
  margin-bottom: 5px;
}
.investment-result-container {
}
.investment-result-container p {
  font-size: 18px;
}
.investment-result-summary {
  background: #ffffff;
  padding: 2rem 2rem 1.5rem 2rem;
  margin-bottom: 2rem;
  border: 1px solid #07871c;
  box-shadow: 0px 3px 1px -2px rgb(0 0 0 / 20%), 0px 2px 2px 0px rgb(0 0 0 / 14%), 0px 1px 5px 0px rgb(0 0 0 / 12%);
}
.investment-result-summary-top {
  border-bottom: 1px solid #07871c;
  padding-bottom: 1rem;
}
.investment-result-summary-bottom {
  padding-top: 1.5rem;
}
.investment-result-container .label-text {
  font-weight: 600;
  color: black;
  margin-bottom: 7px;
}
.investment-result-container .result-text {
  margin-bottom: 1rem;
  font-weight: 600;
}
#btn_inv_reset {
  background: #07871c;
  color: #ffffff;
  width: 100%;
  font-size: 17px;
  padding: 5px;
  border: 1px solid #07871c;
  border-radius: 3px;
  outline: 0;
  -webkit-transition: all 0.40s ease-in-out;
  -moz-transition: all 0.40s ease-in-out;
  -ms-transition: all 0.40s ease-in-out;
  -o-transition: all 0.40s ease-in-out;
}
#btn_inv_reset:hover {
  background: #ffffff;
  color: #07871c;
}
.result-notes {
  padding-top: 10px;
  border-top: 1px solid #cccccc;
  color: #000000;
}
.result-notes p {
  font-size: 12px;
  margin-bottom: 5px;
}
@media only screen and (max-width: 1600px){
  .investment-cal-form {
    width: 100%;
  }
}
@media only screen and (max-width: 1300px){
  .row-flex-2cols div.input-col, .row-flex-3cols div.input-col {
    width: 48%;
  }
}
@media only screen and (max-width: 700px){
  .investment-cal-form {
    justify-content: center;
    padding: 0;
  }
  .investment-cal-form-left {
    width: 100%;
    padding: 1rem;
  }
  .investment-cal-form-right {
    width: 100%;
    margin-top: 1rem;
    padding: 0;
  }
  .investment-result-summary {
    padding: 1rem;
  }
  .row-flex-2cols div.input-col, .row-flex-3cols div.input-col {
    width: 99%;
    margin-bottom: 8px;
  }
  .input-number-custom span {
    line-height: 60px;
    height: 60px;
    width: 80px;
  }
  .input-number-custom input[type="text"] {
    height: 60px;
    font-size: 20px;
  }
}
</style>
