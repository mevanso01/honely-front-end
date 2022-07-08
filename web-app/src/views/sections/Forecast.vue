<template>
  <!-- eslint-disable -->
  <div class="page pg-forecast">
    <page-loader :loading="loading" />
    <!-- forecast-carousel-wrapper -->
    <div v-if="forecast" class="forecast-carousel-wrapper">
      <!-- <div>
          <ul class="forecast-header-menu" id="forecast-header-menu">
            <li id="btn-mobile-menu-close">
              <span class="mdi mdi-close forecast-menu-close-button" @click="toggleMobileMenu"></span>
            </li>
            <li
              v-for="i in controllers"
              @click="moveCarousel(i-1)"
            >
              {{ menu_items[i - 1] }}
            </li>
          </ul>
        </div> -->
      <div class="forecast-carousel-controller">
        <!-- <span class="mdi mdi-menu forecast-mobile-hamburger" id="btn-mobile-menu-smart"  @click="toggleMobileMenu"></span> -->
        <ul v-if="controllers > 1" class="carousel-controller">
          <li
            v-for="i in controllers"
            class="carousel-nav"
            @click="moveCarousel(i-1)"
            :class="{active : i == 1}"
          >
          <span class="carousel-menu-item">{{ menu_items[i - 1] }}</span>
          </li>
        </ul>
      </div>
      <div v-if="controllers > 1" class="forecast-carousel-controller forecast-dropdown-container" v-click-outside="() => toggleDropDown(false)">
        <div class="forecast-nav-dropdown-curr-val" id="forecast-nav-dropdown-curr-val" @click="() => toggleDropDown(true)">
          Property Value Forecast<span class="mdi mdi-chevron-down forecast-nav-dropdown-arrow"></span>
        </div>
        <div class="forecast-nav-dropdown-container" v-if="dropdownFlag">
          <ul>
            <li v-for="i in controllers" @click="moveCarousel(i-1)">{{ menu_items[i - 1] }}</li>
          </ul>
        </div>
      </div>
      <div class="forecast-carousel-content">
        <div v-if="isProperty" class="carousel-item bg-white active">
          <honely-forecast
            :forecast="forecast"
            :property="property"
            :property-zip-data="propertyZipData"
            :subscriptionFlag="subscriptionFlag"
            :defaultPaymethod="defaultPaymethod"
          />
        </div>
        <div v-if="forecast" class="carousel-item bg-default" :class="{active : !isProperty}">
          <neighborhood
            :forecast="forecast"
            :subscriptionFlag="subscriptionFlag"
            :defaultPaymethod="defaultPaymethod"
          />
        </div>
        <!-- <div v-if="isProperty" class="carousel-item bg-white">
          <rental-trends
            :forecast="forecast"
            :rental-trends="rentalTrendData"
            :subscriptionFlag="subscriptionFlag"
          />
        </div> -->
        <div v-if="isProperty" class="carousel-item bg-white">
          <buyer-score
            :forecast="forecast"
            :subscriptionFlag="subscriptionFlag"
          />
        </div>
        <div v-if="isProperty" class="carousel-item bg-white">
          <investment-calculator
            :forecast="forecast"
            :subscriptionFlag="subscriptionFlag"
          />
        </div>
      </div>
    </div>
    <!-- /forecast-carousel-wrapper -->
    <!-- <div v-else class="section-wrapper forecast-no-data">
      No data available, please check the address or zip code.
    </div> -->

    <!-- service providers -->
    <div class="forecast-services-wrapper" v-if="forecast && forecast.property_forecast && forecast.property_forecast.property_id">
      <services
        :service-providers="serviceProviders"
        :forecast="forecast"
        :user="user"
      />
    </div>
    <!-- /service providers -->

    <!-- report-form -->
    <report-form
      v-if="isProperty"
      :forecast="forecast"
      :user="user"
      :property="property"
      :walkscore="walkscore"
      :schools="schools"
      :rental-trends="rentalTrendData"
      :option-lists="optionLists"
      :report_counter="report_counter"
    />
    <!-- /report-form -->

    <!-- honely-calculator -->
    <honely-calculator
      :forecast="forecast"
      :property="property"
      :option-lists="optionLists"
      :county-options="countyOptions"
      :county-options-multi="countyOptionsMulti"
    />
    <!-- /honely-calcualtor -->

    <!-- lead-contact-form -->
    <contact-agent-form
      :searchQuery="searchAddress"
      v-if="leadFormFlag"
    />
    <!-- /lead-contact-form -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapGetters } from 'vuex'
  import { bus } from '../../main'

  export default {
    name: 'Forecast',
    components: {
      PageLoader: () => import('@/components/PageLoader'),
      HonelyForecast: () => import('@/components/forecast/HonelyForecast'),
      Neighborhood: () => import('@/components/forecast/Neighborhood'),
      RentalTrends: () => import('@/components/forecast/RentalTrends'),
      BuyerScore: () => import('@/components/forecast/BuyerScore'),
      ComparableProperties: () => import('@/components/forecast/ComparableProperties'),
      InvestmentCalculator: () => import('@/components/forecast/InvestmentCalculator'),
      Services: () => import('@/components/forecast/Services'),
      ReportForm: () => import('@/components/forecast/ReportForm'),
      HonelyCalculator: () => import('@/components/forecast/HonelyCalculator'),
      ContactAgentForm: () => import('@/components/base/ContactAgentForm'),
    },
    data: () => ({
      dropdownFlag: false,
      menu_items: ['Property Value Forecast', 'Neighborhood at a Glance', 'Compare Deals', 'Investment Calculators'],
      report_counter: null,
      loading: false,
      user: null,
      userId: '',
      subscriptionFlag : false,
      fips: null,
      apn: null,
      propertyId: null,
      address: null,
      forecast: null,
      property: null,
      propertyZipData: null,
      controllers: 0,
      rentalTrendData: null,
      competitive_score: 0,
      error: null,
      initialValue: '0',  
      walkscore: null,
      schools: [],
      optionLists: null,
      countyOptions: null,
      countyOptionsMulti: null,
      serviceProviders: null,
      leadFormFlag: false,
      searchAddress: null,
      devData: {
        agents: [
          {
            agent_id: 'AGHT3480',
            first_name: 'Jeff',
            last_name: 'Jefferies',
            phone_number: '+15515805592',
            agent_email: 'rohan@honely.com',
            home_url: 'www.honely.com',
            user_type: 'AGENT/BROKER',
            zip_code_count: 3,
            start_date: null,
            end_date: null,
            renews: null,
            customer_id: null,
            subscription_id: null,
            trialused: null,
            subscribed_zip_codes: '33128|33130|33132',
            free_subscription_count: 3,
            company_name: 'None',
            license_number: '',
            business_address: null,
            company_type: null,
            zip_code_count_list: '2,0,0',
            free_subscription_count_list: '2,0,0',
            subscribed_zip_codes_list: '00006|07029,,',
            image_url: 'https://honely-files-public.s3.amazonaws.com/images/avatar/avatar_user_02.png',
          },
          {
            agent_id: 'AGHT3777',
            first_name: 'Chenxin',
            last_name: 'Lin',
            phone_number: '+11234567893',
            agent_email: 'chenxin.lin@allocaterite.com',
            home_url: 'www.honely.com',
            user_type: 'AGENT/BROKER',
            zip_code_count: 3,
            start_date: null,
            end_date: null,
            renews: null,
            customer_id: null,
            subscription_id: null,
            trialused: null,
            subscribed_zip_codes: '11385|33130|33132',
            free_subscription_count: 3,
            company_name: 'AllocateRite',
            license_number: '',
            business_address: null,
            company_type: null,
            zip_code_count_list: '2,0,0',
            free_subscription_count_list: '2,0,0',
            subscribed_zip_codes_list: '00006|07029,,',
            image_url: 'https://honely-files-public.s3.amazonaws.com/images/Lin.png',
          },
          {
            agent_id: 'AGHT3333',
            first_name: 'Philis',
            last_name: 'Lonnie',
            phone_number: '+11234567893',
            agent_email: 'philis.lonnie@test.com',
            home_url: 'www.honely.com',
            user_type: 'AGENT/BROKER',
            zip_code_count: 3,
            start_date: null,
            end_date: null,
            renews: null,
            customer_id: null,
            subscription_id: null,
            trialused: null,
            subscribed_zip_codes: '00006|33130|33132',
            free_subscription_count: 3,
            company_name: 'Amazing Home',
            license_number: '',
            business_address: null,
            company_type: null,
            zip_code_count_list: '2,0,0',
            free_subscription_count_list: '2,0,0',
            subscribed_zip_codes_list: '00006|07029,,',
            image_url: 'https://honely-files-public.s3.amazonaws.com/images/avatar/avatar_user_03.png',
          },
        ],
        general_contractors: null,
        lenders: null,
      },
      defaultPaymethod: {}
    }),
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      listings: function () {
        if (this.forecast && this.forecast.property_forecast) {
          return [{
            fips: this.forecast.property_forecast.fips,
            apn: this.forecast.property_forecast.apn,
            full_address: this.forecast.property_forecast.address,
            longitude: this.forecast.property_forecast.longitude,
            latitude: this.forecast.property_forecast.latitude,
            propery_value: this.forecast.property_forecast.valuation,
            beds_count: this.forecast.property_forecast.beds_count,
            baths: this.forecast.property_forecast.baths,
            total_area_sq_ft: this.forecast.property_forecast.total_area_sq_ft,
            for_sale: this.forecast.property_forecast.property_status,
            list_price: this.forecast.property_forecast.list_price,
            appraisal: this.forecast.property_forecast.appraisal,
            property_id: this.forecast.property_forecast.property_id,
            fainitiallistdate: this.forecast.property_forecast.posted_date,
            confidence_score: this.forecast.property_forecast.confidence_score, 
          }]
        } else {
          return []
        }
      },
      isProperty () {
        if (this.forecast && this.forecast.property_forecast) {
          return true
        } else {
          return false
        }
      }
    },
    watch: {
      $route (to, from) {
        this.fips = this.$route.params.fips
        this.apn = this.$route.params.apn
        this.propertyId = this.$route.params.property_id
        this.address = this.$route.query.address
        this.forecast
        this.property
        this.propertyZipData
      },
    },
    mounted () {
      this.fips = this.$route.params.fips
      this.apn = this.$route.params.apn
      this.propertyId = this.$route.params.property_id
      this.address = this.$route.query.address
      this.getUserData()
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        this.getPaymethods()
      }
      // this.getForecastData()
    },
    created () {
    },
    methods: {
      toggleDropDown(value) {
        this.dropdownFlag = value
      },
      toggleMobileMenu () {
        const menu = document.getElementById('forecast-header-menu')
        if (menu.classList.contains('active')) {
          menu.classList.remove('active')
        } else {
          menu.classList.add('active')
        }
      },
      closeMobileMenu () {
        const menu = document.getElementById('forecast-header-menu')
        if (menu.classList.contains('active')) {
          menu.classList.remove('active')
        }
      },
      subscriptionStatusCheck (x) {
        var month = x.substring(0, 16).split(' ')[2]
        var monthNumMapping = {
          Jan: '01',
          Feb: '02',
          Mar: '03',
          Apr: '04',
          May: '05',
          Jun: '06',
          Jul: '07',
          Aug: '08',
          Sep: '09',
          Oct: '10',
          Nov: '11',
          Dec: '12'
        }
        // console.log(monthNumMapping[month])
        var monthNum = monthNumMapping[month]
        var day = x.substring(0, 16).split(' ')[1]
        var year = x.substring(0, 16).split(' ')[3]
        var date = new Date(monthNum + '/' + day + '/' + year + ' 00:00:00')
        var milliseconds = date.getTime()
        // console.log(milliseconds)
        // console.log(Date.now())
        return (Date.now() < milliseconds)
      },
      getForecastData () {
        let params = {}
        let userId = ''
        // get user id
        if (this.user) {
          userId = this.user.user_id
        }
        // determine forecast params
        if (this.fips && this.apn) {
          // console.log('[DEV] forecast using fips and apn')
          params = {
            apn: this.apn,
            fipss: this.fips,
            user_id: userId,
          }
        } else if (this.propertyId) {
          // console.log('[DEV] forecast using property id')
          params = {
            property_id: this.propertyId,
            user_id: userId,
          }
        } else if (this.address) {
          // console.log('[DEV] forecast using address')
          params = {
            address: this.address,
            user_id: userId,
          }
        }
        // get forecast data ----------------------
        this.loading = true
        let requestOptions = {}
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          requestOptions = {
            headers: {
              Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
            },
            params: params,
          }
        } else {
          requestOptions = {
            params: params,
          }
        }
        axios.get('https://api.honely.com/searches/dev/forecast', requestOptions).then((response) => {
          this.loading = false
          console.log(response.data)
          this.forecast = response.data
          if (this.forecast.property_forecast) {
            this.controllers = 4
          } else {
            this.controllers = 1
          }
          // this.getServiceProviders()
          // check lead form
          if (this.address) {
            this.leadFormFlag = this.leadFormNeeded(this.address)
          } else {
            if (this.forecast) {
              this.searchAddress = this.forecast.zipcode
              this.leadFormFlag = this.leadFormNeeded(this.searchAddress)
            }
          }
          // get property data
          if (this.forecast.property_forecast && this.forecast.property_forecast.property_id) {
            this.getPropertyData(this.forecast.property_forecast.property_id)
            this.getPropertyZipData(this.forecast.property_forecast.property_id)
          }
        }).catch((error) => {
          this.loading = false
          console.log('[ERROR] Failed to fetch forecast data')
          console.log(error)
        })
      },
      getUserData () {
        if (!this.isCognitoUserLoggedIn) {
          this.getForecastData()
        }
        if (this.$store.getters['auth/userProfile']) {
          this.user = this.$store.getters['auth/userProfile']
          this.userId = this.user.user_id
          this.getForecastData()
        }
        if (this.isCognitoUserLoggedIn) {
          if (!this.user) {
            axios.get('https://api.honely.com/lookup-test/user_profile', {
              params: {
                email: this.cognitoUser.attributes.email,
              },
            }).then((response) => {
              // console.log(response.data)
              this.$store.dispatch('auth/setUserProfile', response.data)
              this.user = response.data
              this.userId = this.user.user_id
              this.getForecastData()
            }).catch((error) => {
              console.log('[ERROR] Failed to fetch user data')
              console.log(error.response.data.errorMessage)
            })
          }
          axios.get('https://api.honely.com/lookup-test/fetch-user-subscription?email=' + this.cognitoUser.attributes.email)
          .then((response) => {
            if (response.data.subscriopions.length > 0) {
              if (this.subscriptionStatusCheck(response.data.subscriopions[0].end_date)) {
                this.subscriptionFlag = true
                this.report_counter = response.data.subscriopions[0].report_counter
              }
            }
          })
        }
      },
      getPropertyData (propertyId) {
        if(propertyId) {
          axios.get('https://api.honely.com/lookup/listing?property_id=' + propertyId, {
            params: {
            },
          }).then((response) => {
            // console.log(response.data)
            this.property = response.data
            this.getSchoolData(this.property)
            this.getWalkScore(this.property)
            this.getPropertyOptionLists()
            // this.getRentalTrendData()
            this.getServiceProviders()
            this.getCountySupportedOptions()
          }).catch((error) => {
            console.log('[ERROR] Failed to fetch property data')
            console.log(error)
          })
        }
      },
      getPropertyZipData (propertyId) {
        if(propertyId) {
          axios.get('https://api.honely.com/searches/forecast/property_to_zipcode?property_id=' + propertyId + '&user_id=' + this.userId)
            .then((response) => {
            // console.log(response.data)
            if (response.data) {
              if (response.data.result_status === 'Success') {
                this.propertyZipData = response.data.result_data
              }
            }
          }).catch((error) => {
            console.log('[ERROR] Failed to fetch property to zip code data')
            console.log(error)
          })
        }
      },
      getSchoolData (property) {
        if (property.address.latitude && property.address.longitude) {
          axios.get('https://api.honely.com/lookup/great_schools', {
            params: {
              lat: property.address.latitude,
              lon: property.address.longitude,
            },
          }).then((response) => {
            this.schools = response.data.schools
            if (!Array.isArray(this.schools)) {
              this.schools = [this.schools]
            }
            this.schools = this.schools.splice(0, 10)
            this.schools.forEach(function (school) {
              school.distance = school.distance.toFixed(2)
              school.distanceText = 'mi'
            })
          })
        }
      },
      getWalkScore (property) {
        if (property.address.street_name && property.address.street_suffix && property.address.city && property.address.state && property.address.zip_code && property.address.latitude && property.address.longitude) {
          axios.get('https://api.honely.com/lookup/walk_score', {
            params: {
              address: property.address.street_name + property.address.street_suffix + ',' + property.address.city + ',' + property.address.state + property.address.zip_code,
              lat: property.address.latitude,
              lon: property.address.longitude,
            },
          }).then((response) => {
            this.walkscore = response.data
          })
        }
      },
      getPropertyOptionLists () {
        axios.get('https://api.honely.com/lookup/drop_down', {
          params: {
          },
        }).then((response) => {
          // console.log(response.data)
          if (response.data) {
            this.optionLists = response.data
          }
        })
      },
      getRentalTrendData () {
        if (this.forecast && this.forecast.property_forecast) {
          axios.get(`https://www.housingalerts.com/api/?key=gogAEOCeebByaHSVEID92JPVcCu5fmyU&resource_type=honely_dataset&zip=${this.forecast.zipcode}&fips=${this.forecast.property_forecast.fips}&state=${this.forecast.state}`,
          { 
            transformRequest: (data, headers) => {
              delete headers.common['Authorization'];
            },
          }).then((response) => {
            // console.log(response.data)
            if(response.data.status === "ok"){
                this.rentalTrendData = response.data.data
                // console.log(this.rentalTrendData)
              }
          }).catch((err) => {
              console.log(err)
          })
        }
      },
      getCountySupportedOptions () {
        // console.log(this.property)
        if (this.property) {
          const self = this
          const propertyId = this.property.address.property_id
          const fips = this.property.address.fips
          const zip = this.property.address.zip_code
          const yearBuilt = this.property.structure.year_built

          // reqquest body with default values
          const body = {
            basic_info: {
              property_id: propertyId,
              fips: fips,
              zip: zip,
              year_build: yearBuilt,
            },
            numerical: {
              sqft: '1200',
              num_beds: '3',
              num_baths: '1',
              num_partial_baths: '0',
              room_count: '6',
              parking_spaces: 0,
              plumbing_count: 3,
            },
            category: {
              stories: null,
              other_rooms: null,
              air_condition: null,
              heat_type: null,
              heat_fuel_type: null,
              pool_type: null,
              building_condition: null,
              architecture: null,
              construction: null,
              basement_type: null,
              roof_style: null,
              roof_material_type: null,
              exterior_walls: null,
              interior_walls: null,
              flooring: null,
              garage_type: null,
              water_type: null,
              sewer_type: null,
            },
          }

          const apiURL = 'https://api.honely.com/calculator/honely_calculator'
          // console.log(JSON.stringify(body))

          fetch(apiURL, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(body),
          }).then(function (response) {
            // console.log(response)
            return response.json()
          }).then(function (data) {
            // console.log(data)
            if (data) {
              self.countyOptions = data.supported_dropdowns_for_this_county
              self.countyOptionsMulti = data.supported_multi_dropdowns_for_this_county
            }
          }).catch((err) => {
            console.log('[ERROR] Getting county options failed =>', err)
          })
        }
      },
      getServiceProviders () {
        if (this.forecast) {
          axios.get('https://api.honely.com/lookup-test/service_providers?zip_code=' + this.forecast.zipcode)
            .then((response) => {
              // console.log(response.data)
              this.serviceProviders = response.data;
          })
        }
      },
      moveCarousel (target) {
        const carouselItems = document.getElementsByClassName('carousel-item')
        const carouselControllers = document.getElementsByClassName('carousel-nav')
        let current= 0
        // find the current carousel/controller
        if (carouselItems) {
          for (let i = 0; i < carouselItems.length; i++) {
            if (carouselItems[i].classList.contains('active')) {
              current = i
              carouselItems[i].classList.remove('active')
              carouselControllers[i].classList.remove('active')
              break;
            }
          }
          // console.log('current: '+ current + ' | target: ' + target)
          // make target active
          carouselItems[target].classList.add('active')
          carouselControllers[target].classList.add('active')
          // determine which direction to slide to
          if (target < current) {
            // move to right
            this.clearCarousel()
             carouselItems[current].classList.add('next')
          } else if (target > current) {
            // move to left
            this.clearCarousel()
            carouselItems[current].classList.add('prev')
          }
        }
        if (document.getElementById('forecast-nav-dropdown-curr-val')) {
          document.getElementById('forecast-nav-dropdown-curr-val').innerHTML = this.menu_items[target] + '<span class="mdi mdi-chevron-down forecast-nav-dropdown-arrow"></span>'
        }
        if (this.dropdownFlag) {
          this.toggleDropDown(false)
        }
      },
      clearCarousel () {
        const carouselItems = document.getElementsByClassName('carousel-item')
        if (carouselItems) {
          for (let i = 0; i < carouselItems.length; i++) {
            carouselItems[i].classList.remove('next')
            carouselItems[i].classList.remove('prev')
          }
        }
      },
      leadFormNeeded (value) {
        // return false
        let flag = true
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          flag = false
        } else {
          const zipInQuery = value.substring(value.length - 5, value.length)
          if (isNaN(zipInQuery)) {
            flag = false
          } else {
            const leadZipCodeList = this.$store.getters['auth/leadZipCodeList']
            if (leadZipCodeList !== null && leadZipCodeList.includes(zipInQuery)) {
              flag = false
            } else {
              flag = true
            }
          }
        }
        // console.log('flag: ' + flag)
        return flag
      },
      getPaymethods () {
        this.paymethodsLoading = true
        axios.get('https://api.honely.com/dev/payments/payment-methods', {
          headers: {
            Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
          }
        })
        .then(response => {
          this.defaultPaymethod = response.data.data.find(paymethod => paymethod.default) || {}
        }).catch(error => {
          console.log(error)
        })
      }
    },
  }
</script>

