<template>
  <div
    id="forecast-band-1"
    class="mt-0 py-6"
  >
    <v-container
      v-if="forecast"
    >
      <v-row
        v-for="item in listings"
        :key="item.id"
        justify="center"
      >
        <v-col
          sm="12"
          :class="{'align-center text-center px-2': $vuetify.breakpoint.smAndDown, 'align-center text-center px-8': $vuetify.breakpoint.mdAndUp}"
        >
          <base-heading
            class="mt-0 forecast-band-header pb-1 forecast-heading"
            title=""
            weight="medium"
            size="text-h4"
            space="1"
          >
            <div>
              <span>
                <!-- 1. -->
                <i
                  class="mdi mdi-home"
                  aria-hidden="true"
                />
              </span>
              Honely Property Value Forecast
            </div>
            <!-- <div
              v-if="isLogin && !isOverLimit && !isOverLimitEnterprise && isForecastable"
              class="report-btn-container"
            >
              <button
                id="btn_showReportFormHeading"
                @click="showReportForm"
              >
                Generate Report
              </button>
            </div> -->
            <div
              class="report-btn-container"
            >
              <button
                id="btn_showReportFormHeading"
                @click="showReportForm"
              >
                Generate Report
              </button>
              <!-- <v-col
              v-if="showUserTypePrompt"
              class="provider-CTA text-end px-8 pt-0"
            > -->
              <v-dialog
                v-model="dialog"
                persistent
                max-width="600px"
              >
                <!-- <template #activator="{ on, attrs }">
                  <v-btn
                    id="btn_showReportFormHeading2"
                    color="#07871c"
                    elevation="2"
                    large
                    v-bind="attrs"
                    v-on="on"
                  >
                    Generate Report
                  </v-btn>
                </template> -->
                <template #default="dialog">
                  <v-card>
                    <button
                      class="btn"
                      style="position: absolute;
                      top: -15px;
                      right: -5px;
                      border: none;
                      outline: none;
                      padding: 15px 15px;
                      color: white;"
                      @click="toggleDialog"
                    >
                      <i
                        class="fa fa-times-thin fa-2x"
                        style="color: black;"
                      />
                    </button>
                    <v-card-title>
                      <span class="user-type-modal-heading text-h5">Info Required</span>
                    </v-card-title>
                    <!-- <v-card-subtitle>
                      <p>In order to generate the report please let us know what type of user you are:</p>
                    </v-card-subtitle> -->
                    <v-card-text>
                      <!-- In order to generate the report please pick what best describes you: -->
                      <v-container>
                        <v-row>
                          <v-col
                            cols="12"
                            md="12"
                            sm="6"
                          >
                            <v-select
                              v-model="userType"
                              :items="['Agent/Broker', 'Home Buyer', 'Home Seller', 'Property Investor', 'Lender', 'General Contractor', 'Just browsing', 'Service provider']"
                              :rules="userTypeRules"
                              label="Pick what best describes you*"
                              required
                            />
                          </v-col>
                        </v-row>
                        <v-row
                        v-if="userType === 'Service provider'"
                        >
                          <v-col
                            cols="12"
                            md="12"
                            sm="6"
                          >
                            <v-select
                              v-model="serviceProviderType"
                              :items="['Appraiser', 'Attorney', 'Gardening service', 'Insurance (Homeowner, Title, etc.)', 'Home inspector', 'Locksmith']"
                              :rules="serviceProviderTypeRules"
                              label="Service provider type*"
                              required
                            />
                          </v-col>
                        </v-row>
                      </v-container>
                      <small style="margin-left: 10px">*indicates required field</small>
                    </v-card-text>
                    <span
                      v-if="isFailure"
                      style="float:left; margin-left:30px; color:red; "
                    >
                      Error in adding your information. Please try again.
                    </span>
                    <span
                      v-if="isSuccess"
                      style="float:left; margin-left:30px; color: #06A550"
                    >Information added successfully!
                    </span>
                    <v-card-actions>
                      <v-spacer />
                      <v-btn
                        color="blue darken-1"
                        text
                        :disabled="(!userType) || (userType === 'Service provider' && !serviceProviderType)"
                        @click="submitUserType"
                      >
                        Submit
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </template>
              </v-dialog>
            <!-- </v-col> -->
            </div>
          </base-heading>
          <base-heading
            class="mt-0 forecast-band-subheader pb-1"
            title=""
            weight="medium"
            size="text-h6"
          >
            <span>
              Property Found:
            </span>
            {{ forecast.property_forecast.address }}
          </base-heading>
          <v-row
            justify="center"
          >
            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
              <property-listings-card
                :item="item"
              />
              <!-- <v-card
                :key="item.id"
                :item="item"
                elevation="0"
                class="current-value-card mx-auto text-left mt-6"
              >
                <v-card-title>
                  This property is valued
                  <span
                    v-if="propertyValueComparedToZipCode > 0"
                    class="value-higher my-3"
                  >
                    <i
                      class="mdi mdi-arrow-up"
                      aria-hidden="true"
                    />
                    {{ propertyValueComparedToZipCode }}
                    % higher
                  </span>
                  <span
                    v-else
                    class="value-lower my-3"
                  >
                    <i
                      class="mdi mdi-arrow-down"
                      aria-hidden="true"
                    />
                    {{ propertyValueComparedToZipCode }}
                    % lower
                  </span>
                  compared to the typical property in zipcode
                  {{ forecast.zipcode }}
                </v-card-title>
              </v-card> -->
            </v-col>

            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
              <forecast-card
                :displayaddress="forecast.property_forecast.address"
                :forecast="forecast"
                :user="user"
              />
            </v-col>
          </v-row>
          <v-row
            v-if="item.appraisal && $vuetify.breakpoint.mdAndUp"
            justify="start"
          >
            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
              <v-card
                :key="item.id"
                :item="item"
                elevation="0"
                class="current-value-card mx-auto text-left mt-6"
                style="border"
              >
                <v-card-title style="word-break: normal;">
                  This property is valued at<br>
                  <span
                    v-if="propertyValueComparedToZipCode > 0"
                    class="value-higher my-3"
                  >
                    <i
                      class="mdi mdi-arrow-up"
                      aria-hidden="true"
                    />
                    {{ propertyValueComparedToZipCode }}
                    % above
                  </span>
                  <span
                    v-if="propertyValueComparedToZipCode == 0"
                    class="same-as"
                  >
                    same as
                  </span>
                  <span
                    v-if="propertyValueComparedToZipCode < 0"
                    class="value-lower my-3"
                  >
                    <i
                      class="mdi mdi-arrow-down"
                      aria-hidden="true"
                    />
                    {{ propertyValueComparedToZipCode }}
                    % below
                  </span>
                  the average property value in zipcode
                  {{ forecast.zipcode }}:
                  <span
                  style="color: #07871c; display: inline;"
                  v-if="$vuetify.breakpoint.name === 'md'"
                  >
                  {{ formatPrice(forecast.property_forecast.average_zip_code_value) }}
                  </span>
                  <!-- <div
                  v-if="$vuetify.breakpoint.name === 'lg'"
                  style="color: #07871c;"
                  > -->
                    <span
                     v-if="$vuetify.breakpoint.lgAndUp"
                     style="color: #07871c; margin-top: 12px; margin-bottom: 12px;"
                    >{{ formatPrice(forecast.property_forecast.average_zip_code_value) }}</span>
                  <!-- </div> -->
                  <!-- <span>{{ $vuetify.breakpoint.name }}</span> -->
                </v-card-title>
              </v-card>
            </v-col>
            <v-col
              sm="6"
              class="align-center text-center px-8"
            >
            <v-card
                :key="item.id"
                :item="item"
                elevation="0"
                class="current-value-card mx-auto text-left mt-6"
                style="min-height: 195px;"
              >
              <br>
                <v-card-title style="text-align: center; word-break: normal;">
                  To correct any errors or missing property data, click below.
                </v-card-title>
                <br>
                <v-dialog
                v-model="dialog"
                persistent
                max-width="999px"
              >
                <template #activator="{ on, attrs }">
                  <v-btn
                    class="provider-network-btn white--text"
                    color="#07871c"
                    elevation="2"
                    large
                    v-bind="attrs"
                    v-on="on"
                    :width="$vuetify.breakpoint.name === 'md'?'40%':'30%'"
                    :style="$vuetify.breakpoint.name === 'md'?'margin-left:30%;':'margin-left:35%'"
                  >
                    Enter New Data!
                  </v-btn>
                </template>
                <template #default="dialog">
                  <br><br>
                  <v-card>
                    <button
                      class="btn"
                      style="position: absolute;
                  top: -15px;
                  right: -5px;
                  border: none;
                  outline: none;
                  padding: 15px 15px;
                  color: white;"
                      @click="toggleDialog"
                    >
                      <i
                        class="fa fa-times-thin fa-2x"
                        style="color: black;"
                      />
                    </button>
                    <honely-calculator-3
                      v-if="forecast && forecast.property_forecast"
                      :forecast="forecast"
                      :user="user"
                      :property="property"
                      :walkscore="walkscore"
                      :schools="schools"
                      :option-lists="optionLists"
                      :county-options="countyOptions"
                      :county-options-multi="countyOptionsMulti"
                    />
                  </v-card>
                </template>
              </v-dialog>
              </v-card>
            </v-col>
          </v-row>
          <v-row
            v-if="item.appraisal && $vuetify.breakpoint.smAndDown"
            justify="start"
          >
            <v-col
              sm="12"
              class="align-center text-center px-8"
            >
              <v-card
                :key="item.id"
                :item="item"
                elevation="0"
                class="current-value-card mx-auto text-left mt-6"
              >
                <v-card-title style="word-break: normal;">
                  This property is valued at
                  <span
                    v-if="propertyValueComparedToZipCode > 0"
                    class="value-higher my-3"
                    style="word-break: normal;"
                  >
                    <i
                      class="mdi mdi-arrow-up"
                      aria-hidden="true"
                    />
                    {{ propertyValueComparedToZipCode }}
                    % above
                  </span>
                  <span
                    v-if="propertyValueComparedToZipCode == 0"
                    class="same-as"
                  >
                    same as
                  </span>
                  <span
                    v-if="propertyValueComparedToZipCode < 0"
                    class="value-lower my-3"
                    style="word-break: normal;"
                  >
                    <i
                      class="mdi mdi-arrow-down"
                      aria-hidden="true"
                    />
                    {{ propertyValueComparedToZipCode }}
                    % below
                  </span>
                  the average property value in zipcode
                  {{ forecast.zipcode }}: <span style="color: #07871c; margin-top: 12px; margin-bottom: 12px;">{{ formatPrice(forecast.property_forecast.average_zip_code_value) }}</span>
                </v-card-title>
              </v-card>
            </v-col>
          </v-row>
          <v-row
            v-if="item.appraisal && $vuetify.breakpoint.smAndDown"
            justify="start"
          >
            <v-col
              sm="12"
              class="align-center text-center px-8"
            >
            <v-card
                :key="item.id"
                :item="item"
                elevation="0"
                class="current-value-card mx-auto text-left mt-6"
                style="min-height: 136px;"
              >
                <v-card-title style="text-align: center;">
                  <p style="margin-bottom: 0px; word-break: normal;">To correct any errors or missing property data, click below.</p>
                </v-card-title>
                <v-dialog
                v-model="dialog"
                persistent
                max-width="999px"
              >
                <template #activator="{ on, attrs }">
                  <v-btn
                    class="provider-network-btn white--text"
                    color="#07871c"
                    elevation="2"
                    large
                    v-bind="attrs"
                    v-on="on"
                    width="50%"
                    style="margin-left:25%"
                  >
                    Enter New Data!
                  </v-btn>
                </template>
                <template #default="dialog">
                  <br><br>
                  <v-card>
                    <button
                      class="btn"
                      style="position: absolute;
                  top: -15px;
                  right: -5px;
                  border: none;
                  outline: none;
                  padding: 15px 15px;
                  color: white;"
                      @click="toggleDialog"
                    >
                      <i
                        class="fa fa-times-thin fa-2x"
                        style="color: black;"
                      />
                    </button>
                    <honely-calculator-3
                      v-if="forecast && forecast.property_forecast"
                      :forecast="forecast"
                      :user="user"
                      :property="property"
                      :walkscore="walkscore"
                      :schools="schools"
                      :option-lists="optionLists"
                      :county-options="countyOptions"
                      :county-options-multi="countyOptionsMulti"
                    />
                  </v-card>
                </template>
              </v-dialog>
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
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'ForecastBand1',

    components: {
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
      ForecastCard: () => import('@/components/ForecastCard'),
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      HonelyCalculator3: () => import('@/components/forecast/HonelyCalculator3'),
    },

    props: {
      forecast: Object,
      listings: Array,
      user: Object,
      property: Object,
      walkscore: Object,
      schools: Array,
      optionLists: Object,
      countyOptions: Object,
      countyOptionsMulti: Object,
      /*
      :property="property"
        :walkscore="walkscore"
        :schools="schools"
        :option-lists="optionLists"
        :county-options="countyOptions"
        :county-options-multi="countyOptionsMulti"
      */
    },

    data: () => ({
      user_type: null,
      isSuccess: false,
      isFailure: false,
      userType: null,
      serviceProviderType: null,
      dialog: false,
      showLoginOverlay: false,
      overlayTab: 'sign-up',
      userTypeRules: [
        v => !!v || 'Please choose an option',
      ],
      serviceProviderTypeRules: [
        v => !!v || 'Please choose an option',
      ],
    }),

    mounted () {
      // Any test functions
    },

    computed: {
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
      isForecastable () {
        // console.log(this.forecast)
        if (this.forecast.property_forecast.appraisal && this.forecast.property_forecast.appraisal != null) {
          return true
        } else {
          return false
        }
      },
      propertyValueComparedToZipCode: function () {
        // console.log(this.user)
        if (this.forecast.property_forecast.property_valued_compared_to_zipcode) {
          return parseFloat(this.forecast.property_forecast.property_valued_compared_to_zipcode)
        } else {
          return 0
        }
      },
    },
    // mounted: function () {
    //   console.log('vx: user profile', this.user)
    // },
    watch: {
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },
    methods: {
      formatPrice (value) {
        // let val = (value/1).toFixed(2).replace('.', ',')
        if (value) {
          value = value.toString().split('.')[0]
          // value = parseInt(value)
          // value = (value % 100 >= 50) ? Math.ceil(value / 100) * 100 : Math.floor(value / 100) * 100
          return '$' + value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
      submitUserType () {
        const params = {
          user_type: this.userType,
          user_sub_type: this.serviceProviderType,
          email: this.user.email,
        }
        axios.post('https://api.honely.com/lookup/add_user_type', params)
          .then(() => {
            this.isSuccess = true
            this.user_type = this.userType
            setTimeout(() => {
              this.toggleDialog()
            }, 2000)
          }).catch(() => {
            this.isFailure = true
          })
      },
      toggleDialog () {
        this.dialog = !this.dialog
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      showReportForm () {
        if (!this.user) {
          this.displayLoginOverlay('create-account')
        } else {
          if (this.user.user_type === 'NONE' && this.user_type === null) {
            this.toggleDialog()
          } else {
            // alert('test')
            document.getElementById('report-form-overlay').classList.add('active')
            // fix Apex chart disappear when series updates bug
            window.dispatchEvent(new Event('resize'))
          }
        }
      },
    },
  }
</script>
