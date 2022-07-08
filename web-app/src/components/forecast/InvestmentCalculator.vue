<template>
  <!-- eslint-disable -->
  <div class="forecast-section section-wrapper">
    <!-- forecast-heading-wrapper -->
    <div class="forecast-heading-wrapper">
      <div class="forecast-section-title">Property Investment Calculators</div>
    </div>
    <!-- /forecast-heading-wrapper -->
    <!-- forecast-content-wrapper -->
    <v-tabs
      color="shades"
      left
      justify-tab-title="start"
    >
      <v-tab>Mortgage Info</v-tab>
      <v-tab @click="doCalculateRental">Rental Income & Expense</v-tab>
      <!-- <v-tab>Debt-to-Income Ratio</v-tab> -->
      <v-tab-item :key="1"
      >
        <div class="honely-property-position-relative">
          <!-- <div v-if="!isCognitoUserLoggedIn" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Message prompting to login</p>
          <button class="bg-primary">Sign In</button>
        </div>
      </div> -->
      <div v-if="!isCognitoUserLoggedIn || !forecastAccess" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Please subscribe to view all of our statistics</p>
          <div v-if="!isCognitoUserLoggedIn || (!forecastAccess && !subscriptionFlag)">
            <button class="bg-primary" @click="showSubscriptionPopup()">Subscribe</button>
          </div>
          <div v-else-if="subscriptionFlag && !forecastAccess">
            <button @click="showSingleSubscriptionPopup()" class="bg-primary">Purchase for $1.00</button>
          </div>
        </div>
      </div>
        <div class="flex-wrapper" :class="!isCognitoUserLoggedIn || !forecastAccess?'blocked':''" >
          <div class="flex-col-md-6">
            <label class="text-0">Home Value</label>
            <input id="homeValue" type="text" :placeholder="getHonelyEstimate" @keyup="getBuyerScore">
            <label class="text-0">Down Payment</label>
            <input id="downPayment" type="text" placeholder='0%'  @keyup="getPercent($event, 0)">
            <label class="text-0">Loan Term</label>
            <div class="honely-forecast-tabs">              
              <span class="year-neighborhood-tab active" @click="displayForecastData(0, 10)">10yr</span>
              <span class="year-neighborhood-tab" @click="displayForecastData(1, 15)">15yr</span>
              <span class="year-neighborhood-tab" @click="displayForecastData(2, 20)">20yr</span>
              <span class="year-neighborhood-tab" @click="displayForecastData(3, 30)">30yr</span>
            </div>
            <label class="text-0">Interest Rate</label>
            <input id="interest" type="text" placeholder='0%' @keyup="getPercent($event, 1)">
          </div>
          <div class="flex-col-md-6 info-right">
            <div class="green-board">
              <p class="text-0">Mortgage Payment</p>
              <p class="text-1">{{ monthlyMortgage }}</p>
              <v-btn
                rounded
                color="success" 
                class="btn-reset" 
                @click="doResetMortgage"
              >
                Reset
              </v-btn>
            </div>
          </div>
        </div>
        </div>
      </v-tab-item>
      <v-tab-item :key="2"
      >
        <div class="honely-property-position-relative">
        <!-- <div v-if="!isCognitoUserLoggedIn" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Message prompting to login</p>
          <button class="bg-primary">Sign In</button>
        </div>
      </div> -->
      <div v-if="!isCognitoUserLoggedIn || !subscriptionFlag" class="container-overlay">
        <div class="overlay-wrapper">
          <p>Please subscribe to view all of our statistics</p>
          <button class="bg-primary" @click="goToSubscriptionPage">Subscribe</button>
        </div>
      </div>
        <div class="flex-wrapper" :class="!isCognitoUserLoggedIn || !subscriptionFlag?'blocked':''">
          <div class="flex-col-md-4">
            <span class="warning-text">Monthly Expenses</span>
            <label class="text-0">Mortgage</label>
            <input type="text" :placeholder="monthlyMortgage" :value="monthlyMortgage" disabled />
            <label class="text-0">Maintainance</label>
            <input id="maintain" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'maintain')" />
            <label class="text-0">Insurance</label>
            <input id="insurance" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'insurance')" />
            <label class="text-0">Property Tax</label>
            <input id="tax" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'tax')" />
            <label class="text-0">Utilities</label>
            <input id="util" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'util')" />
            <label class="text-0">HOA Fees</label>
            <input id="hoa" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'hoa')" />

          </div>
          <div class="flex-col-md-4">
            <span class="warning-text">Monthly Income</span>
            <label class="text-0">Rental Income</label>
            <input id="rental" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'rental')" />
            <label class="text-0">Misc.Income</label>
            <input id="misc" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'misc')"/>
            <!-- <label class="text-0">Laundry Income</label>
            <input id="laundry" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'laundry')"> -->
            <span class="warning-text">One Time Expenses</span>
            <label class="text-0">Closing Costs</label>
            <input id="closing" type="text" placeholder="$0" @keyup="getIncomeScore($event, 'closing')">            
          </div>
          <div class="flex-col-md-4 info-right">
            <div class="green-board">
              <p class="text-0">Net Monthly Income</p>
              <p class="text-1">{{ netOpIncome }}</p>
              <p class="text-0">Total Expenses</p>
              <p class="text-1">{{ totalExp }}</p>
              <p class="text-0">Capitalization Rate</p>
              <p class="text-1">{{ capRate }}%</p>
              <p class="text-0">Cash on Cash Return</p>
              <p class="text-1">{{ cashReturn }}%</p>
              <p class="text-0">Return on Investment</p>
              <p class="text-1">{{ investReturn }}%</p>
              
              <v-btn
                rounded
                color="success" 
                class="btn-reset" 
                @click="doResetRental"
              >
                Reset
              </v-btn>
            </div>
          </div>
        </div>
        </div>
      </v-tab-item>
      <!-- <v-tab-item :key="3"
      >
        <div class="flex-wrapper">
          <div class="flex-col-md-6">
            <label class="text-0">Annual Income</label>
            <input id="annual" type="text" :placeholder="getHonelyEstimate" @keyup="getAnnual($event, 'annual')" />
            <span class="warning-text">Monthly Expenses</span>
            <label class="text-0">Credit Card Payment</label>
            <input id="credit" type="text" @keyup="getAnnual($event, 'credit')" />
            <label class="text-0">Car Loan Payment</label>
            <input id="carloan" type="text" @keyup="getAnnual($event, 'carloan')" />
            <label class="text-0">Student Loan Payments</label>
            <input id="studentloan" type="text" @keyup="getAnnual($event, 'studentloan')" />
            <label class="text-0">Other Loan or Debt Payments</label>
            <input id="otherloan" type="text" @keyup="getAnnual($event, 'otherloan')" />
          </div>
          <div class="flex-col-md-6 info-right">
            <div class="green-board">
              <p class="text-0">Debt to Income Ratio</p>
              <p class="text-2">{{ DTIrate }}%</p>
              <label class="text-0 py-10 mb-10">
                Your DTI ratio is 
                <label v-if="DTIrate < 35" >Good.</label>
                <label v-else-if="DTIrate > 34 && DTIrate < 56">Fair.</label>
                <label v-else-if="DTIrate > 54 && DTIrate < 76">not Good.</label>
                <label v-else-if="DTIrate > 75">Bad.</label>
                <br />Under 35% is ideal,
              </label>              
              <div class="sum-up mt-10">
                <h3 class="mt-8">Total Monthly</h3>
                <h3 class="mt-8">{{ totalAnnualExp }}</h3>
              </div>
              <div class="sum-up">
                <h3 class="mt-8">Mortgage Payment</h3>
                <h3 class="mt-8">{{ monthlyMortgage }}</h3>
              </div>
              <div class="sum-up">
                <h3 class="mt-8">Remaining Income</h3>
                <h3 class="mt-8">{{ remain }}</h3>
              </div>

              <div>
                <apexchart type="radialBar" height="250" :options="chartOptions" :series="series"></apexchart>
              </div>

              <v-btn
                rounded
                color="success" 
                class="btn-reset" 
                @click="doResetDTI"
              >
                Reset
              </v-btn>
            </div>
          </div>
        </div>
      </v-tab-item> -->
    </v-tabs>
    <!-- /forecast-content-wrapper -->
    <subscription-popup
      :show="showSubscription"
      :forecastAccess="forecastAccess"
      :zipCode="getZipcode"
      :propertyId="getPropertyId"
      @toggleShow="toggleSubscriptionShow"
    />
    <single-subscription-popup
      :show="showSingleSubscription"
      :forecastAccess="forecastAccess"
      :zipCode="getZipcode"
      :propertyId="getPropertyId"
      :defaultPaymethod="defaultPaymethod"
      @toggleShow="toggleSingleSubscriptionShow"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import VueApexCharts from 'vue-apexcharts'
  import VueNumberFormat from 'vue-number-format'
  import { REQUIRED_ONLY_FLOAT } from '@/utils/validators'
  import { mapGetters } from 'vuex'

  export default {
    name: 'InvestmentCalculator',
    components: {
      apexcharts: VueApexCharts,
      nformat: VueNumberFormat,
      SubscriptionPopup: () => import('@/components/forecast/SubscriptionPopup'),
      SingleSubscriptionPopup: () => import('@/components/forecast/SingleSubscriptionPopup'),
    },
    props: {
      forecast: Object,
      subscriptionFlag: Boolean,
      defaultPaymethod: Object,
    },
    data () {
      return {
        // blocked: true,
        homeValue: 0,
        downPayment: 0,
        loan: 10,
        interestRate: 0,
        monthlyMortgage: '$0',
        maintain: 0,
        insurance: 0,
        tax: 0,
        util: 0,
        hoa: 0,
        rental: 0,
        misc: 0,
        closing: 0,
        laundry: 0,
        netOpIncome: '$0',
        totalExp: '$0',
        capRate: 0,
        cashReturn: 0,
        investReturn: 0,
        annual: 0,
        credit: 0,
        carloan: 0,
        studentloan: 0,
        otherloan: 0,
        DTIrate: 0,
        totalAnnualExp: '$0',
        remain: '$0',
        series: [0, 0, 0],
        chartOptions: {
          chart: {
            height: 350,
            type: 'radialBar',
          },
          plotOptions: {
            radialBar: {
              dataLabels: {
                name: {
                  fontSize: '18px',
                },
                value: {
                  fontSize: '16px',
                },
              },
            },
          },
          labels: ['Total', 'Mortgage', 'Remaining'],
        },
        showSubscription: false,
        showSingleSubscription: false,
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      getHonelyEstimate () {
        let estimate = null
        if (this.forecast && this.forecast.property_forecast) {
          if (this.forecast.property_forecast.appraisal) {
            estimate = this.formatNumber(this.forecast.property_forecast.appraisal, true)
          }
        }
        return estimate
      },
      forecastAccess () {
        return this.forecast.access
      },
      getZipcode () {
        return this.forecast?.zipcode
      },
      getPropertyId () {
        return this.forecast?.property_forecast?.address?.property_id
      },
    },
    watch: {
    },
    mounted () {
    },
    methods: {
      goToSubscriptionPage () {
        window.location.href = '/smart-data-subscription'
      },
      displayForecastData (index, year) {
        if (index >= 0) {
          this.activeTimeframe = index
          const tabs = document.getElementsByClassName('year-neighborhood-tab')
          if (tabs) {
            for (let i = 0; i < tabs.length; i++) {
              if (i !== index) {
                tabs[i].classList.remove('active')
              } else {
                tabs[i].classList.add('active')
                this.loan = year
                this.doCalculate()
              }
            }
          }
        }
      },
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
      formatPercentInput (num) {
        let result = ''
        if (num) {
          if (REQUIRED_ONLY_FLOAT(num)) {
            result = num + '%'
          }
        }
        return result
      },
      formatToNumber (value) {
        let result = 0
        if (value) {
          result = value.trim().replaceAll(',', '').replace('$', '')
        }
        return result
      },
      getPercent (event, idx) {
        if (event.keyCode !== 37 && event.keyCode !== 38 && event.keyCode !== 39 && event.keyCode !== 40 && event.keyCode !== 13) {
          if (event.keyCode === 8) {
            const str = event.target.value.trim().replaceAll('%', '')
            event.target.value = str.substring(0, str.length - 1)
          }
          const offer = event.target.value.trim().replaceAll('%', '')
          if (idx === 0) { this.downPayment = offer } else { this.interestRate = offer }

          event.target.value = this.formatPercentInput(offer)
          this.doCalculate()
        }
      },
      getBuyerScore (event) {
        if (event.keyCode !== 37 && event.keyCode !== 38 && event.keyCode !== 39 && event.keyCode !== 40 && event.keyCode !== 13) {
          // console.log(event.target.value)
          // format number to currency
          const offer = this.formatToNumber(event.target.value)
          this.homeValue = offer
          event.target.value = this.formatCurrencyInput(offer)
          this.doCalculate()
        }
      },
      getIncomeScore (event, idx) {
        if (event.keyCode !== 37 && event.keyCode !== 38 && event.keyCode !== 39 && event.keyCode !== 40 && event.keyCode !== 13) {
          // console.log(event.target.value)
          // format number to currency
          const offer = this.formatToNumber(event.target.value)
          event.target.value = this.formatCurrencyInput(offer)

          switch (idx) {
            case 'maintain':
              if (offer === null || offer === '') {
                this.maintain = 0
              } else {
                this.maintain = offer
              }
              break
            case 'insurance':
              if (offer === null || offer === '') {
                this.insurance = 0
              } else {
                this.insurance = offer
              }
              break
            case 'tax':
              if (offer === null || offer === '') {
                this.tax = 0
              } else {
                this.tax = offer
              }
              break
            case 'util':
              if (offer === null || offer === '') {
                this.util = 0
              } else {
                this.util = offer
              }
              break
            case 'hoa':
              if (offer === null || offer === '') {
                this.hoa = 0
              } else {
                this.hoa = offer
              }
              break
            case 'rental':
              if (offer === null || offer === '') {
                this.rental = 0
              } else {
                this.rental = offer
              }
              break
            case 'misc':
              if (offer === null || offer === '') {
                this.misc = 0
              } else {
                this.misc = offer
              }
              break
            case 'closing':
              if (offer === null || offer === '') {
                this.closing = 0
              } else {
                this.closing = offer
              }
              break
            case 'laundry':
              if (offer === null || offer === '') {
                this.laundry = 0
              } else {
                this.laundry = offer
              }
              break
          }
          this.doCalculateRental()
        }
      },
      getAnnual (event, idx) {
        if (event.keyCode !== 37 && event.keyCode !== 38 && event.keyCode !== 39 && event.keyCode !== 40 && event.keyCode !== 13) {
          // console.log(event.target.value)
          // format number to currency
          const offer = this.formatToNumber(event.target.value)
          event.target.value = this.formatCurrencyInput(offer)

          switch (idx) {
            case 'annual':
              if (offer === null || offer === '') {
                this.annual = 0
              } else {
                this.annual = offer
              }
              break
            case 'credit':
              if (offer === null || offer === '') {
                this.credit = 0
              } else {
                this.credit = offer
              }
              break
            case 'carloan':
              if (offer === null || offer === '') {
                this.carloan = 0
              } else {
                this.carloan = offer
              }
              break
            case 'studentloan':
              if (offer === null || offer === '') {
                this.studentloan = 0
              } else {
                this.studentloan = offer
              }
              break
            case 'otherloan':
              if (offer === null || offer === '') {
                this.otherloan = 0
              } else {
                this.otherloan = offer
              }
              break
          }
          this.doCalculateAnnual()
        }
      },
      doCalculate () {
        if (!isNaN(this.homeValue) && !isNaN(this.interestRate) && !isNaN(this.loan)) {
          const loanAmt = parseFloat(this.homeValue) * (1 - parseFloat(this.downPayment) / 100)
          const interestRateAmt = parseFloat(this.interestRate) / 100 / 12
          const termMonths = parseFloat(this.loan) * 12

          this.monthlyMortgage = this.formatCurrencyInput(parseFloat(this.doMonthlyMortgage(loanAmt, interestRateAmt, termMonths)).toFixed(2))
        }
      },
      doMonthlyMortgage (loan, rate, terms) {
        if (loan && rate && terms) {
          return parseFloat(loan) * (parseFloat(rate) * Math.pow(1 + parseFloat(rate), parseFloat(terms))) / (Math.pow(1 + parseFloat(rate), parseFloat(terms)) - 1)
        } else {
          return 0
        }
      },
      doCalculateRental () {
        if (!isNaN(this.insurance) && !isNaN(this.hoa) && !isNaN(this.util) && !isNaN(this.tax) && !isNaN(this.maintain)) {
          const monthlyExp = parseFloat(parseFloat(this.insurance) + parseFloat(this.hoa) + parseFloat(this.util) + parseFloat(this.tax) + parseFloat(this.maintain)).toFixed(0)
          this.totalExp = this.formatCurrencyInput(parseFloat(parseFloat(monthlyExp) + parseFloat(this.formatToNumber(this.monthlyMortgage))).toFixed(0))
          const incomeStream = parseFloat(parseFloat(this.rental) + parseFloat(this.misc) + parseFloat(this.laundry)).toFixed(0)
          const downPaymentAmt = parseFloat(this.homeValue) * (parseFloat(this.downPayment) / 100)
          this.netOpIncome = this.formatCurrencyInput(parseFloat(parseFloat(incomeStream) - parseFloat(monthlyExp)).toFixed(0))
          const fCapRate = parseFloat(parseFloat((parseFloat(incomeStream) - parseFloat(monthlyExp)) / parseFloat(this.homeValue)) * 12).toFixed(4)
          this.capRate = !isNaN(parseFloat(parseFloat(fCapRate) * 100).toFixed(2)) && isFinite(parseFloat(parseFloat(fCapRate) * 100).toFixed(2)) ? parseFloat(parseFloat(fCapRate) * 100).toFixed(2) : 0
          const fCashReturn = parseFloat(parseFloat(parseFloat(parseFloat(incomeStream) - parseFloat(this.formatToNumber(this.totalExp))) * 12 / (parseFloat(downPaymentAmt) + parseFloat(this.closing)))).toFixed(4)
          this.cashReturn = !isNaN(parseFloat(parseFloat(fCashReturn) * 100).toFixed(2)) && isFinite(parseFloat(parseFloat(fCashReturn) * 100).toFixed(2)) ? parseFloat(parseFloat(fCashReturn) * 100).toFixed(2) : 0
          const fInvestReturn = parseFloat(parseFloat(parseFloat(incomeStream) - parseFloat(this.totalExp.trim().replaceAll(',', '').replace('$', ''))) * 12 / parseFloat(this.homeValue)).toFixed(4)
          this.investReturn = !isNaN(parseFloat(parseFloat(fInvestReturn) * 100).toFixed(2)) && isFinite(parseFloat(parseFloat(fInvestReturn) * 100).toFixed(2)) ? parseFloat(parseFloat(fInvestReturn) * 100).toFixed(2) : 0
        }
      },
      doResetMortgage () {
        document.getElementById('homeValue').value = ''
        document.getElementById('downPayment').value = ''
        const tabs = document.getElementsByClassName('year-neighborhood-tab')
        if (tabs) {
          for (let i = 0; i < tabs.length; i++) {
            tabs[0].classList.add('active')
            tabs[i].classList.remove('active')
          }
        }
        document.getElementById('interest').value = ''
        this.homeValue = 0
        this.downPayment = 0
        this.loan = 10
        this.interestRate = 0
        this.monthlyMortgage = '$0'
        this.doResetRental()
      },
      doResetRental () {
        this.maintain = 0
        this.insurance = 0
        this.tax = 0
        this.util = 0
        this.hoa = 0
        this.rental = 0
        this.misc = 0
        this.closing = 0
        this.laundry = 0
        this.netOpIncome = '$0'
        this.totalExp = '$0'
        this.capRate = 0
        this.cashReturn = 0
        this.investReturn = 0
        document.getElementById('maintain').value = ''
        document.getElementById('insurance').value = ''
        document.getElementById('tax').value = ''
        document.getElementById('util').value = ''
        document.getElementById('hoa').value = ''
        document.getElementById('rental').value = ''
        document.getElementById('misc').value = ''
        document.getElementById('closing').value = ''
      },
      doCalculateAnnual () {
        const totalAnnaulExp = parseFloat(this.credit) + parseFloat(this.carloan) + parseFloat(this.studentloan) + parseFloat(this.otherloan)
        const DTI = parseFloat(parseFloat(totalAnnaulExp) / parseFloat(this.formatToNumber(this.netOpIncome))).toFixed(2)
        this.DTIrate = !isNaN(DTI) && isFinite(DTI) ? DTI : 0
        this.totalAnnualExp = this.formatCurrencyInput(parseFloat(parseFloat(this.annual) / 12).toFixed(0))
        this.remain = this.formatCurrencyInput(parseFloat(this.formatToNumber(this.totalAnnualExp)) - parseFloat(this.formatToNumber(this.monthlyMortgage)))
        this.series = [
          parseFloat(this.annual / this.formatToNumber(this.totalAnnualExp)).toFixed(1),
          parseFloat(this.annual / this.formatToNumber(this.monthlyMortgage)).toFixed(1),
          parseFloat(this.annual / this.formatToNumber(this.remain)).toFixed(1)]
      },
      doResetDTI () {
        this.annual = 0
        this.credit = 0
        this.carloan = 0
        this.studentloan = 0
        this.otherloan = 0
        this.DTIrate = 0
        this.totalAnnualExp = '$0'
        this.remain = '$0'
        document.getElementById('annual').value = ''
        document.getElementById('credit').value = ''
        document.getElementById('carloan').value = ''
        document.getElementById('studentloan').value = ''
        document.getElementById('otherloan').value = ''
        this.series = [0, 0, 0]
      },
      showSubscriptionPopup () {
        this.showSubscription = true
      },
      showSingleSubscriptionPopup () {
        this.showSingleSubscription = true
      },
      toggleSubscriptionShow (value) {
        this.showSubscription = value
      },
      toggleSingleSubscriptionShow (value) {
        this.showSingleSubscription = value
      },
    },
  }
</script>
