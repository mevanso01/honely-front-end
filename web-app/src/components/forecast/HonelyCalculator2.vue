<template>
  <!-- eslint-disable -->
  <div
    id="forecast-band-8"
    class="mt-0 py-6"
  >
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
            <span>
              <i
                class="mdi mdi-chart-box"
                aria-hidden="true"
              />
            </span>
            Home Improvement Calculator
          </base-heading>
          <v-row
            justify="center"
            class="forecast-section-below-heading"
          >
            <!-- honely-calculator-section -->
            <!-- <div
              class="honely-calculator-section"
              v-bind:class="{blocked: isLogin === false || (isLogin && isOverLimit) || (isLogin && isOverLimitEnterprise)}"
            > -->
            <div
              class="honely-calculator-section"
            >
              <!-- <div class="honely-calculator-copy">
                <p class="feature-text">See what upgrades increase the property value by using our forecast calculator.</p>
                <button @click="doShowForecastCalForm">Honely Forecast Calculator</button>
              </div> -->
              <!-- calculator form -->
              <div class="honely-calculator-form">
                <!-- loader -->
                <div class="loader-overlay" id="loader-overlay-calculator">
                  <div class="loader-container">
                    <i class="fas fa-spinner fa-spin"></i>
                    <span>Loading...</span>
                  </div>
                </div>
                <!-- loader -->

                <!-- report-form-container -->
                <div class="calculator-form-container">
                  <!-- form-section -->
                  <div class="form-section">
                    <p class="form-feature-title">Honely Estimated Value</p>
                    <div class="forecast-calculator-results-row">
                      <div class="forecast-before">
                        <p class="forecast-label">Before</p>
                        <p class="forecast-result">{{ getForecastValue }}</p>
                      </div>
                      <div class="forecast-after">
                        <p class="forecast-label">After</p>
                        <p class="forecast-result" id="forecast-result-after">--</p>
                      </div>
                    </div>
                  </div>
                  <!-- /form-section -->

                  <!-- calculator-form-wrapper -->
                  <div class="calculator-form-wrapper">
                    <div class="calculator-form-col">
                      <!-- form-section -->
                      <div class="form-section noborder-nomargin">
                        <p class="form-section-title"><span><i class="fas fa-home"></i> Property Information</span></p>
                        <!-- <div class="form-row-flex-2cols">
                          <p>{{ getFullAddress }}</p>
                          <p><span class="font-bold">APN:</span> {{ getAPN }}</p>
                        </div> -->
                        <div class="form-row-flex-4cols">
                          <p>
                            <label>Year Built</label>
                            <input type="text" name="cal-year-built" id="cal-year-built" v-bind:value="getYearBuilt" readonly>
                          </p>
                          <p>
                            <label>County</label>
                            <input type="text" name="cal-county" id="cal-county" v-bind:value="getCounty" readonly>
                          </p>
                          <p>
                            <label>Stories</label>
                            <select name="cal-stories" id="cal-stories">
                              <option value="--">--</option>
                              <option
                                v-for="(item) in getStoriesCodes"
                                :value="item.id"
                                :selected="getStories==item.name"
                              >
                              {{item.name}}
                              </option>
                            </select>
                          </p>
                          <p>
                            <label>SQ Ft.</label>
                            <input type="text" name="cal-sqft" id="cal-sqft" v-bind:value="getSqft">
                          </p>
                        </div>
                        <div class="form-row-flex-4cols">
                          <p>
                            <label># of Bedrooms</label>
                            <input type="text" name="cal-num-beds" id="cal-num-beds" v-bind:value="getNumBeds">
                          </p>
                          <p>
                            <label># of Bathrooms</label>
                            <input type="text" name="cal-num-baths" id="cal-num-baths" v-bind:value="getNumBaths">
                          </p>
                          <p>
                            <label># of Partial Bathrooms</label>
                            <input type="text" name="cal-num-partial-bath" id="cal-num-partial-bath" v-bind:value="getNumPartialBaths">
                          </p>
                          <p>
                            <label>Room Count</label>
                            <input type="text" name="cal-room-count" id="cal-room-count" v-bind:value="getRoomCount">
                          </p>
                        </div>
                      </div>
                      <!-- /form-section -->
                    </div>
                  </div>
                  <!-- /calculator-form-wrapper -->
                  <!-- form-action-section -->
                  <div class="form-action-row">

                    <button
                      @click="doHonelyCalculator"
                      id="btn_doHonelyCalculator"
                      v-bind:disabled="isButtonDisabled"
                    >
                      Forecast
                    </button>
                  </div>
                  <!-- /form-action-section -->
                </div>
              </div>
              <!-- /calculator form -->

              <!-- render blocked overlay -->
              <!-- <div
                v-if="isLogin === false"
                class="container-overlay"
              >
                <div class="overlay-wrapper">
                  <p>Please create an account to access 3 free smart searches</p>
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
            </div>
            <!-- /honely-calculator-section -->
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
  <!-- eslint-enable -->
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'HonelyCalculator2',
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },

    props: {
      forecast: Object,
      user: Object,
      property: Object,
      walkscore: Object,
      schools: Array,
      optionLists: Object,
      countyOptions: Object,
      countyOptionsMulti: Object,
    },

    data: () => ({
      mapsKey: 'AIzaSyClIFG-ONBwyXrn4_kaA4yMYHGpZD5EEko',
      showLoginOverlay: false,
      overlayTab: 'sign-up',
    }),

    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
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
      isButtonDisabled () {
        // if (!this.isLogin || (this.isLogin && this.isOverLimit) || (this.isLogin && this.isOverLimitEnterprise)) {
        //   return true
        // } else {
        //   return false
        // }
        return false
      },
      // drop-down option codes ----------------------------------------------
      getWaterCodes () {
        if (this.optionLists && this.optionLists.water_code) {
          const options = []
          for (const key in this.optionLists.water_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.water_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getSewerCodes () {
        if (this.optionLists && this.optionLists.sewer_code) {
          const options = []
          for (const key in this.optionLists.sewer_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.sewer_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getStoriesCodes () {
        if (this.optionLists && this.optionLists.stories_code) {
          const options = []
          for (const key in this.optionLists.stories_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.stories_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getRoofCoverCodes () {
        if (this.optionLists && this.optionLists.roof_cover_code) {
          const options = []
          for (const key in this.optionLists.roof_cover_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.roof_cover_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getRoofTypeCodes () {
        if (this.optionLists && this.optionLists.roof_type_code) {
          const options = []
          for (const key in this.optionLists.roof_type_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.roof_type_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getInteriorWallsCodes () {
        if (this.optionLists && this.optionLists.interior_walls_code) {
          const options = []
          for (const key in this.optionLists.interior_walls_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.interior_walls_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getConstructionTypeCodes () {
        if (this.optionLists && this.optionLists.construction_type_code) {
          const options = []
          for (const key in this.optionLists.construction_type_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.construction_type_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getFloorCoverCodes () {
        if (this.optionLists && this.optionLists.floor_cover_code) {
          const options = []
          for (const key in this.optionLists.floor_cover_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.floor_cover_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getExteriorWallsCodes () {
        if (this.optionLists && this.optionLists.exterior_walls_code) {
          const options = []
          for (const key in this.optionLists.exterior_walls_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.exterior_walls_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getBuildingConditionCodes () {
        if (this.optionLists && this.optionLists.building_condition_code) {
          const options = []
          for (const key in this.optionLists.building_condition_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.building_condition_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getBasementCodes () {
        if (this.optionLists && this.optionLists.basement_code) {
          const options = []
          for (const key in this.optionLists.basement_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.basement_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getStyleCodes () {
        if (this.optionLists && this.optionLists.style_code) {
          const options = []
          for (const key in this.optionLists.style_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.style_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getAirConditionCodes () {
        if (this.optionLists && this.optionLists.air_condition_code) {
          const options = []
          for (const key in this.optionLists.air_condition_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.air_condition_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getHeatingFuelCodes () {
        if (this.optionLists && this.optionLists.heating_fuel_code) {
          const options = []
          for (const key in this.optionLists.heating_fuel_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.heating_fuel_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getGarageCodes () {
        if (this.optionLists && this.optionLists.garage_code) {
          const options = []
          for (const key in this.optionLists.garage_code) {
            options.push(
              {
                id: key,
                name: this.optionLists.garage_code[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getHeatingTypeCodes () {
        if (this.optionLists && this.optionLists.heating_type) {
          const options = []
          for (const key in this.optionLists.heating_type) {
            options.push(
              {
                id: key,
                name: this.optionLists.heating_type[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getOtherRoomCodes () {
        if (this.optionLists && this.optionLists.other_rooms) {
          const options = []
          for (const key in this.optionLists.other_rooms) {
            options.push(
              {
                id: key,
                name: this.optionLists.other_rooms[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getAmenitiesCodes () {
        if (this.optionLists && this.optionLists.amenities) {
          const options = []
          for (const key in this.optionLists.amenities) {
            options.push(
              {
                id: key,
                name: this.optionLists.amenities[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      getPoolCodes () {
        if (this.optionLists && this.optionLists.pool) {
          const options = []
          for (const key in this.optionLists.pool) {
            options.push(
              {
                id: key,
                name: this.optionLists.pool[key],
              },
            )
          }
          return options
        } else {
          return []
        }
      },
      // property values ----------------------------------------------
      getForecastValue () {
        // console.log(this.optionLists)
        // console.log(this.property)
        // console.log(this.forecast)
        if (this.forecast && this.forecast.property_forecast && this.forecast.property_forecast.appraisal) {
          if (this.forecast.property_forecast.appraisal != null) {
            return this.formatCurrency(this.forecast.property_forecast.appraisal)
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getAddress1 () {
        if (this.property && this.property.address) {
          return this.property.address.street_number + ' ' + this.property.address.street_name + ' ' + this.property.address.street_suffix
        } else {
          return ''
        }
      },
      getAddress2 () {
        if (this.property && this.property.address) {
          if (this.property.address.unit_type !== null && this.property.address.unit_number !== null) {
            return this.property.address.unit_type + ' ' + this.property.address.unit_number
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getCity () {
        if (this.property && this.property.address) {
          return this.property.address.city
        } else {
          return ''
        }
      },
      getState () {
        if (this.property && this.property.address) {
          return this.property.address.state
        } else {
          return ''
        }
      },
      getZip () {
        if (this.property && this.property.address) {
          return this.property.address.zip_code
        } else {
          return ''
        }
      },
      getFullAddress () {
        if (this.property && this.property.address) {
          return this.property.address.full_address
        } else {
          return ''
        }
      },
      getCounty () {
        if (this.property && this.property.address) {
          return this.property.address.fips
        } else {
          return ''
        }
      },
      getAPN () {
        if (this.property && this.property.address) {
          return this.property.address.apn
        } else {
          return ''
        }
      },
      getYearBuilt () {
        if (this.property && this.property.structure) {
          if (this.property.structure.year_built && this.property.structure.year_built !== null) {
            return this.property.structure.year_built
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getStories () {
        if (this.property && this.property.structure) {
          if (this.property.structure.stories && this.property.structure.stories != null) {
            return this.property.structure.stories
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getSqft () {
        if (this.property && this.property.structure) {
          if (this.property.structure.total_area_sq_ft && this.property.structure.total_area_sq_ft !== null) {
            return this.formatNumber(this.property.structure.total_area_sq_ft)
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getNumBeds () {
        if (this.property && this.property.structure) {
          if (this.property.structure.beds_count && this.property.structure.beds_count !== null) {
            return this.property.structure.beds_count
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getNumBaths () {
        if (this.property && this.property.structure) {
          if (this.property.structure.baths && this.property.structure.baths !== null) {
            return this.property.structure.baths
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getNumPartialBaths () {
        if (this.property && this.property.structure) {
          if (this.property.structure.partial_baths_count && this.property.structure.partial_baths_count !== null) {
            return this.property.structure.partial_baths_count
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getRoomCount () {
        if (this.property && this.property.structure) {
          if (this.property.structure.rooms_count && this.property.structure.rooms_count !== null) {
            return this.property.structure.rooms_count
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
    //   getOtherRooms () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.other_rooms && this.property.structure.other_rooms !== null) {
    //         return this.property.structure.other_rooms
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getParkingSpaces () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.parking_spaces_count && this.property.structure.parking_spaces_count !== null) {
    //         return this.property.structure.parking_spaces_count
    //       } else {
    //         return ''
    //       }
    //     } else {
    //       return ''
    //     }
    //   },
    //   getPlumbingCount () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.plumbing_fixtures && this.property.structure.plumbing_fixtures !== null) {
    //         return this.property.structure.plumbing_fixtures
    //       } else {
    //         return ''
    //       }
    //     } else {
    //       return ''
    //     }
    //   },
    //   getParkingType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.parking_type && this.property.structure.parking_type !== null) {
    //         return this.property.structure.parking_type
    //       } else {
    //         return ''
    //       }
    //     } else {
    //       return ''
    //     }
    //   },
    //   getACType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.air_conditioning_type && this.property.structure.air_conditioning_type !== null) {
    //         return this.property.structure.air_conditioning_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getHeatType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.heating_type && this.property.structure.heating_type !== null) {
    //         return this.property.structure.heating_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getHeatFuelType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.heating_fuel_type && this.property.structure.heating_fuel_type !== null) {
    //         return this.property.structure.heating_fuel_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getPool () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.pool_type && this.property.structure.pool_type !== null) {
    //         return this.property.structure.pool_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getGarage () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.garage_type && this.property.structure.garage_type !== null) {
    //         return this.property.structure.garage_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getAmenities () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.amenities && this.property.structure.amenities !== null) {
    //         return this.property.structure.amenities
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getMostRecentSaleDate () {
    //     if (this.property && this.property.sale_history) {
    //       if (this.property.sale_history != null && this.property.sale_history.length > 0) {
    //         if (this.property.sale_history[0].date && this.property.sale_history[0].date != null) {
    //           return this.property.sale_history[0].date
    //         } else {
    //           return ''
    //         }
    //       } else {
    //         return ''
    //       }
    //     } else {
    //       return ''
    //     }
    //   },
    //   getMostRecentSalePrice () {
    //     if (this.property && this.property.sale_history) {
    //       if (this.property.sale_history != null && this.property.sale_history.length > 0) {
    //         if (this.property.sale_history[0].price && this.property.sale_history[0].price != null) {
    //           return this.property.sale_history[0].price
    //         } else {
    //           return ''
    //         }
    //       } else {
    //         return ''
    //       }
    //     } else {
    //       return ''
    //     }
    //   },
    //   getCondition () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.condition && this.property.structure.condition !== null) {
    //         return this.property.structure.condition
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getConstructionType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.construction_type && this.property.structure.construction_type !== null) {
    //         return this.property.structure.construction_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getArchitecture () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.architecture_type && this.property.structure.architecture_type !== null) {
    //         return this.property.structure.architecture_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getRoofStyle () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.roof_style_type && this.property.structure.roof_style_type !== null) {
    //         return this.property.structure.roof_style_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getRoofMaterial () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.roof_material_type && this.property.structure.roof_material_type !== null) {
    //         return this.property.structure.roof_material_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getExteriorWalls () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.exterior_wall_type && this.property.structure.exterior_wall_type !== null) {
    //         return this.property.structure.exterior_wall_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getInteriorWalls () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.interior_wall_type && this.property.structure.interior_wall_type !== null) {
    //         return this.property.structure.interior_wall_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getFlooring () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.flooring_types && this.property.structure.flooring_types !== null) {
    //         return this.property.structure.flooring_types
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getBasement () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.basement_type && this.property.structure.basement_type !== null) {
    //         return this.property.structure.basement_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getSewerType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.sewer_type && this.property.structure.sewer_type !== null) {
    //         return this.property.structure.sewer_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    //   getWaterType () {
    //     if (this.property && this.property.structure) {
    //       if (this.property.structure.water_type && this.property.structure.water_type !== null) {
    //         return this.property.structure.water_type
    //       } else {
    //         return '--'
    //       }
    //     } else {
    //       return '--'
    //     }
    //   },
    // simplify drop-down options (NOT USED currently) ----------------------------------------------
    /* getOtherRoomsOptions () {
        const options = []
        if (this.countyOptionsMulti && this.optionLists) {
          if (this.countyOptionsMulti.OtherRooms) {
            for (let i = 0; i < this.countyOptionsMulti.OtherRooms.length; i++) {
              options.push(
                {
                  id: this.countyOptionsMulti.OtherRooms[i],
                  name: this.optionLists.other_rooms[this.countyOptionsMulti.OtherRooms[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getACOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.AirConditioningCode) {
            for (let i = 0; i < this.countyOptions.AirConditioningCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.AirConditioningCode[i],
                  name: this.optionLists.air_condition_code[this.countyOptions.AirConditioningCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getHeatingTypeOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.HeatCode) {
            for (let i = 0; i < this.countyOptions.HeatCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.HeatCode[i],
                  name: this.optionLists.heating_type[this.countyOptions.HeatCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getHeatingFuelTypeOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.HeatingFuelTypeCode) {
            for (let i = 0; i < this.countyOptions.HeatingFuelTypeCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.HeatingFuelTypeCode[i],
                  name: this.optionLists.heating_fuel_code[this.countyOptions.HeatingFuelTypeCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getPoolOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.PoolCode) {
            for (let i = 0; i < this.countyOptions.PoolCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.PoolCode[i],
                  name: this.optionLists.pool[this.countyOptions.PoolCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getAmenityOptions () {
        const options = []
        if (this.countyOptionsMulti && this.optionLists) {
          if (this.countyOptionsMulti.Amenities) {
            for (let i = 0; i < this.countyOptionsMulti.Amenities.length; i++) {
              options.push(
                {
                  id: this.countyOptionsMulti.Amenities[i],
                  name: this.optionLists.amenities[this.countyOptionsMulti.Amenities[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getBuildingConditionOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.BuildingConditionCode) {
            for (let i = 0; i < this.countyOptions.BuildingConditionCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.BuildingConditionCode[i],
                  name: this.optionLists.building_condition_code[this.countyOptions.BuildingConditionCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getStyleOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.StyleCode) {
            for (let i = 0; i < this.countyOptions.StyleCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.StyleCode[i],
                  name: this.optionLists.style_code[this.countyOptions.StyleCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getConstructionOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.ConstructionTypeCode) {
            for (let i = 0; i < this.countyOptions.ConstructionTypeCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.ConstructionTypeCode[i],
                  name: this.optionLists.construction_type_code[this.countyOptions.ConstructionTypeCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getBasementOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.BasementCode) {
            for (let i = 0; i < this.countyOptions.BasementCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.BasementCode[i],
                  name: this.optionLists.basement_code[this.countyOptions.BasementCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getRoofStyleOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.RoofTypeCode) {
            for (let i = 0; i < this.countyOptions.RoofTypeCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.RoofTypeCode[i],
                  name: this.optionLists.roof_type_code[this.countyOptions.RoofTypeCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getRoofMaterialOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.RoofCoverCode) {
            for (let i = 0; i < this.countyOptions.RoofCoverCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.RoofCoverCode[i],
                  name: this.optionLists.roof_cover_code[this.countyOptions.RoofCoverCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getExteriorWallOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.ExteriorWallsCode) {
            for (let i = 0; i < this.countyOptions.ExteriorWallsCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.ExteriorWallsCode[i],
                  name: this.optionLists.exterior_walls_code[this.countyOptions.ExteriorWallsCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getInteriorWallOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.InteriorWallsCode) {
            for (let i = 0; i < this.countyOptions.InteriorWallsCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.InteriorWallsCode[i],
                  name: this.optionLists.interior_walls_code[this.countyOptions.InteriorWallsCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getFloorCoverOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.FloorCoverCode) {
            for (let i = 0; i < this.countyOptions.FloorCoverCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.FloorCoverCode[i],
                  name: this.optionLists.floor_cover_code[this.countyOptions.FloorCoverCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getGarageOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.Garage) {
            for (let i = 0; i < this.countyOptions.Garage.length; i++) {
              options.push(
                {
                  id: this.countyOptions.Garage[i],
                  name: this.optionLists.garage_code[this.countyOptions.Garage[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getWaterOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.WaterCode) {
            for (let i = 0; i < this.countyOptions.WaterCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.WaterCode[i],
                  name: this.optionLists.water_code[this.countyOptions.WaterCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      getSewerOptions () {
        const options = []
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.SewerCode) {
            for (let i = 0; i < this.countyOptions.SewerCode.length; i++) {
              options.push(
                {
                  id: this.countyOptions.SewerCode[i],
                  name: this.optionLists.sewer_code[this.countyOptions.SewerCode[i]],
                },
              )
            }
            options.push(
              {
                id: '+',
                name: 'Other',
              },
            )
          }
        }
        return options
      },
      // validate drop-down supports (NOT USED currently) ----------------------------------------------
      checkSupportOtherRooms () {
        if (this.countyOptionsMulti && this.optionLists) {
          if (this.countyOptionsMulti.OtherRooms) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportAC () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.AirConditioningCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportHeatingType () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.HeatCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportHeatingFuelType () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.HeatingFuelTypeCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportPool () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.PoolCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportAmenities () {
        if (this.countyOptionsMulti && this.optionLists) {
          if (this.countyOptionsMulti.Amenities) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportBuildingCondition () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.BuildingConditionCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportStyle () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.StyleCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportConstruction () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.ConstructionTypeCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportBasement () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.BasementCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportRoofStyle () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.RoofTypeCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportRoofMaterial () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.RoofCoverCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportExteriorWalls () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.ExteriorWallsCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportInteriorWalls () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.InteriorWallsCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportFloorCover () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.FloorCoverCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportGarage () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.Garage) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportWater () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.WaterCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkSupportSewer () {
        if (this.countyOptions && this.optionLists) {
          if (this.countyOptions.SewerCode) {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      }, */
    },
    created () {
      // get supported drop-down options for selected county
    },
    watch: {
      forecast: function () {
        // console.log('forecast changed')
        // reset calculated data
        this.doUpdateCalcualtorResult(null)
        // update dropdown selected value
        this.doUpdateSelectedValues()
      },
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },
    mounted () {
      // default
      // this.doDev()
      // this.doUpdateSelectedValues()
    },
    methods: {
      checkSupportOptions (multi, key) {
        if (multi) {
          if (this.countyOptionsMulti && this.optionLists) {
            if (this.countyOptionsMulti[key]) {
              return true
            } else {
              return false
            }
          } else {
            return false
          }
        } else {
          if (this.countyOptions && this.optionLists) {
            if (this.countyOptions[key]) {
              if (this.countyOptions[key].length > 0) {
                return true
              } else {
                if (key === 'ConstructionTypeCode' || key === 'WaterCode' || key === 'SewerCode') {
                  return false
                } else {
                  return true
                }
              }
            } else {
              return false
            }
          } else {
            return false
          }
        }
      },
      getDropdownOptions (multi, supportKey, optionKey) {
        const options = []
        if (multi) {
          if (this.countyOptionsMulti && this.optionLists) {
            if (this.countyOptionsMulti[supportKey]) {
              for (let i = 0; i < this.countyOptionsMulti[supportKey].length; i++) {
                options.push(
                  {
                    id: this.countyOptionsMulti[supportKey][i],
                    name: this.optionLists[optionKey][this.countyOptionsMulti[supportKey][i]],
                  },
                )
              }
              // get default true option
              if (optionKey === 'amenities') {
                options.push(
                  {
                    id: '+',
                    name: 'Yes',
                  },
                )
              } else {
                options.push(
                  {
                    id: '+',
                    name: 'Other',
                  },
                )
              }
            }
          }
        } else {
          if (this.countyOptions && this.optionLists) {
            if (this.countyOptions[supportKey]) {
              for (let i = 0; i < this.countyOptions[supportKey].length; i++) {
                options.push(
                  {
                    id: this.countyOptions[supportKey][i],
                    name: this.optionLists[optionKey][this.countyOptions[supportKey][i]],
                  },
                )
              }
              // get default true option
              if (optionKey === 'air_condition_code' || optionKey === 'heating_type' || optionKey === 'pool' || optionKey === 'garage_code' || optionKey === 'basement_code') {
                options.push(
                  {
                    id: '+',
                    name: 'Yes',
                  },
                )
              } else {
                options.push(
                  {
                    id: '+',
                    name: 'Other',
                  },
                )
              }
            }
          }
        }
        return options
      },
      doGetValueForOptionList (dropdown, arr, value) {
        let id = '--'
        if (arr && value) {
          for (let i = 0; i < arr.length; i++) {
            if (arr[i].name === value) {
              id = arr[i].id
              break
            }
          }
        }
        // console.log(id)
        document.getElementById(dropdown).value = id
      },
      doUpdateSelectedValues () {
        if (this.property && this.property.structure) {
          this.doGetValueForOptionList('cal-stories', this.getStoriesCodes, this.property.structure.stories)
          this.doGetValueForOptionList('cal-other-rooms', this.getOtherRoomCodes, this.property.structure.other_rooms)
          this.doGetValueForOptionList('cal-ac-type', this.getAirConditionCodes, this.property.structure.air_conditioning_type)
          this.doGetValueForOptionList('cal-heat-type', this.getHeatingTypeCodes, this.property.structure.heating_type)
          this.doGetValueForOptionList('cal-heat-fuel-type', this.getHeatingFuelCodes, this.property.structure.heating_fuel_type)
          this.doGetValueForOptionList('cal-pool', this.getPoolCodes, this.property.structure.pool_type)
          this.doGetValueForOptionList('cal-amenities', this.getAmenitiesCodes, this.property.structure.amenities)
          this.doGetValueForOptionList('cal-condition', this.getBuildingConditionCodes, this.property.structure.condition)
          this.doGetValueForOptionList('cal-architecture', this.getStyleCodes, this.property.structure.architecture_type)
          this.doGetValueForOptionList('cal-construction', this.getConstructionTypeCodes, this.property.structure.construction_type)
          this.doGetValueForOptionList('cal-basement-type', this.getBasementCodes, this.property.structure.basement_type)
          this.doGetValueForOptionList('cal-roof-style', this.getRoofTypeCodes, this.property.structure.roof_style_type)
          this.doGetValueForOptionList('cal-roof-material', this.getRoofCoverCodes, this.property.structure.roof_material_type)
          this.doGetValueForOptionList('cal-exterior-walls', this.getExteriorWallsCodes, this.property.structure.exterior_wall_type)
          this.doGetValueForOptionList('cal-interior-walls', this.getInteriorWallsCodes, this.property.structure.interior_wall_type)
          this.doGetValueForOptionList('cal-flooring', this.getFloorCoverCodes, this.property.structure.flooring_types)
          this.doGetValueForOptionList('cal-garage-type', this.getGarageCodes, this.property.structure.garage_type)
          this.doGetValueForOptionList('cal-water-type', this.getWaterCodes, this.property.structure.water_type)
          this.doGetValueForOptionList('cal-sewer-type', this.getSewerCodes, this.property.structure.sewer_type)
        }
      },
      doUpdateCalcualtorResult (data) {
        if (data) {
          document.getElementById('forecast-result-after').innerHTML = this.formatCurrency(this.doGetForecastResult(data))
        } else {
          document.getElementById('forecast-result-after').innerHTML = '--'
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
      toSubscribe () {
        window.location.href = '/subscribe'
      },
      doDev () {
        console.log('>> current user ----------------')
        console.log(this.user)
        console.log('>> current property ----------------')
        console.log(this.property)
        console.log('>> current forecast ----------------')
        console.log(this.forecast)
        console.log('>> current walkscore ----------------')
        console.log(this.walkscore)
        console.log('>> school ----------------')
        console.log(this.schools)
      },
      getToday () {
        const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        const today = new Date()
        const todayString = monthNames[today.getMonth()] + ' ' + today.getDate() + ', ' + today.getFullYear()
        return todayString
      },
      doShowForecastCalForm () {
        document.getElementById('honely-calculator-overlay').classList.add('active')
      },
      doCloseForm () {
        document.getElementById('honely-calculator-overlay').classList.remove('active')
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
      formatNumber (num) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            return parseInt(num)
          } else if (typeof (num) === 'string') {
            return parseInt(num)
          }
        } else {
          return '0'
        }
      },
      displayLoader () {
        document.getElementById('loader-overlay-calculator').classList.add('active')
      },
      hideLoader () {
        document.getElementById('loader-overlay-calculator').classList.remove('active')
      },
      disableSubmitButton () {
        document.getElementById('btn_doHonelyCalculator').disabled = true
      },
      enableSubmitButton () {
        document.getElementById('btn_doHonelyCalculator').disabled = false
      },
      validateNumericalInput (value) {
        if (value) {
          value = value.trim()

          if (value !== '') {
            if (isNaN(value)) {
              return false
            } else {
              if (parseFloat(value) < 0) {
                return false
              } else {
                return true
              }
            }
          } else {
            return false
          }
        } else {
          return false
        }
      },
      doGetForecastResult (data) {
        if (data) {
          if (this.forecast.property_forecast.appraisal && data.current_value) {
            return parseFloat(this.forecast.property_forecast.appraisal) * data.current_value
          } else {
            return null
          }
        } else {
          return null
        }
      },
      doHonelyCalculator () {
        // TO DO WHEN API is ready
        // get all the inputs from user
        // console.log(this.property)
        const propertyId = this.property.address.property_id
        const fips = this.property.address.fips
        let apn = this.getAPN
        let address1 = this.getAddress1
        let address2 = this.getAddress2
        let city = this.getCity
        let state = this.getState
        let zip = this.getZip
        let county = this.getCounty
        let yearBuilt = this.getYearBuilt
        let stories = document.getElementById('cal-stories').value
        const sqft = document.getElementById('cal-sqft').value
        const numBeds = document.getElementById('cal-num-beds').value
        const numBaths = document.getElementById('cal-num-baths').value
        const numPartialBaths = document.getElementById('cal-num-partial-bath').value
        const roomCount = document.getElementById('cal-room-count').value
        // let otherRooms = document.getElementById('cal-other-rooms').value
        // let acType = document.getElementById('cal-ac-type').value
        // let parkingSpaces = document.getElementById('cal-parking-spaces').value
        // let plumbingCount = document.getElementById('cal-plumbing-count').value
        // const parkingType = document.getElementById('cal-parking-type').value
        // let heatType = document.getElementById('cal-heat-type').value
        // let heatFuelType = document.getElementById('cal-heat-fuel-type').value
        // let pool = document.getElementById('cal-pool').value
        // let amenities = document.getElementById('cal-amenities').value
        // let condition = document.getElementById('cal-condition').value
        // let architecture = document.getElementById('cal-architecture').value
        // let construction = document.getElementById('cal-construction').value
        // let basementType = document.getElementById('cal-basement-type').value
        // let roofStyle = document.getElementById('cal-roof-style').value
        // let roofMaterial = document.getElementById('cal-roof-material').value
        // let exteriorWalls = document.getElementById('cal-exterior-walls').value
        // let interiorWalls = document.getElementById('cal-interior-walls').value
        // let flooring = document.getElementById('cal-flooring').value
        // let garageType = document.getElementById('cal-garage-type').value
        // let waterType = document.getElementById('cal-water-type').value
        // let sewerType = document.getElementById('cal-sewer-type').value
        // const saleDate = document.getElementById('cal-sale-date').value
        // const salePrice = document.getElementById('cal-sale-price').value

        // check numerical inputs
        if (!this.validateNumericalInput(sqft)) {
          document.getElementById('cal-sqft').classList.add('error-input')
          alert('Please enter a valid number')
          return
        } else {
          document.getElementById('cal-sqft').classList.remove('error-input')
        }
        if (!this.validateNumericalInput(numBeds)) {
          document.getElementById('cal-num-beds').classList.add('error-input')
          alert('Please enter a valid number')
          return
        } else {
          document.getElementById('cal-num-beds').classList.remove('error-input')
        }
        if (!this.validateNumericalInput(numBaths)) {
          document.getElementById('cal-num-baths').classList.add('error-input')
          alert('Please enter a valid number')
          return
        } else {
          document.getElementById('cal-num-baths').classList.remove('error-input')
        }
        if (!this.validateNumericalInput(numPartialBaths)) {
          document.getElementById('cal-stories').classList.add('error-input')
          alert('Please enter a valid number')
          return
        } else {
          document.getElementById('cal-stories').classList.remove('error-input')
        }
        if (!this.validateNumericalInput(roomCount)) {
          document.getElementById('cal-room-count').classList.add('error-input')
          alert('Please enter a valid number')
          return
        } else {
          document.getElementById('cal-room-count').classList.remove('error-input')
        }
        // if (!this.validateNumericalInput(parkingSpaces)) {
        //   document.getElementById('cal-parking-spaces').classList.add('error-input')
        //   alert('Please enter a valid number')
        //   return
        // } else {
        //   document.getElementById('cal-parking-spaces').classList.remove('error-input')
        // }
        // if (!this.validateNumericalInput(plumbingCount)) {
        //   document.getElementById('cal-plumbing-count').classList.add('error-input')
        //   alert('Please enter a valid number')
        //   return
        // } else {
        //   document.getElementById('cal-plumbing-count').classList.remove('error-input')
        // }

        // update null data
        if (apn || apn === '') {
          apn = ' '
        }
        if (address1 === null || address1 === '') {
          address1 = ' '
        }
        if (address2 === null || address2 === '') {
          address2 = ' '
        }
        if (city === null || city === '') {
          city = ' '
        }
        if (state === null || state === '') {
          state = ' '
        }
        if (zip === null || zip === '') {
          zip = null
        }
        if (county === null || county === '') {
          county = ' '
        }
        if (yearBuilt === null || yearBuilt === '') {
          yearBuilt = ' '
        } else {
          yearBuilt = yearBuilt.toString()
        }
        if (stories === '--') {
          stories = null
        }
        // if (parkingSpaces === '') {
        //   parkingSpaces = null
        // }
        // if (plumbingCount === '') {
        //   plumbingCount = null
        // }
        // if (otherRooms === '--') {
        //   otherRooms = null
        // }
        // if (acType === '--') {
        //   acType = null
        // }
        // if (heatType === '--') {
        //   heatType = null
        // }
        // if (heatFuelType === '--') {
        //   heatFuelType = null
        // }
        // if (pool === '--') {
        //   pool = null
        // }
        // if (amenities === '--') {
        //   amenities = null
        // }
        // if (condition === '--') {
        //   condition = null
        // }
        // if (architecture === '--') {
        //   architecture = null
        // }
        // if (construction === '--') {
        //   construction = null
        // }
        // if (basementType === '--') {
        //   basementType = null
        // }
        // if (roofStyle === '--') {
        //   roofStyle = null
        // }
        // if (roofMaterial === '--') {
        //   roofMaterial = null
        // }
        // if (exteriorWalls === '--') {
        //   exteriorWalls = null
        // }
        // if (interiorWalls === '--') {
        //   interiorWalls = null
        // }
        // if (flooring === '--') {
        //   flooring = null
        // }
        // if (garageType === '--') {
        //   garageType = null
        // }
        // if (waterType === '--') {
        //   waterType = null
        // }
        // if (sewerType === '--') {
        //   sewerType = null
        // }

        const body = {
          basic_info: {
            property_id: propertyId,
            fips: fips,
            zip: zip,
            year_build: yearBuilt,
          },
          numerical: {
            sqft: sqft,
            num_beds: numBeds,
            num_baths: numBaths,
            num_partial_baths: numPartialBaths,
            room_count: roomCount,
            parking_spaces: null,
            plumbing_count: null,
          },
          category: {
            stories: stories,
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

        // console.log(JSON.stringify(body))

        // display loader and disable button
        this.displayLoader()
        this.disableSubmitButton()
        const self = this
        const apiURL = 'https://api.honely.com/calculator/honely_calculator'
        console.log('[INFO] Start Honely calculator......')

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
          self.hideLoader()
          self.enableSubmitButton()

          // update component
          if (data) {
            // console.log(data.current_value)
            // document.getElementById('forecast-result-after').innerHTML = self.formatCurrency(self.doGetForecastResult(data))
            if (self.doGetForecastResult(data) && self.doGetForecastResult(data) != null) {
              // self.calculated.value = self.formatCurrency(self.doGetForecastResult(data))
              self.doUpdateCalcualtorResult(data)
            } else {
              // self.calculated.value = '--'
              self.doUpdateCalcualtorResult(null)
            }
          }

          console.log('[INFO] Finished processing Honely calculator......')
        }).catch((err) => {
          self.hideLoader()
          self.enableSubmitButton()
          console.log('[ERROR] Honely calculator API failed =>', err)
        })
      },
    },
  }
</script>

<style>
#forecast-band-8 {
  /*background-image: url('~@/assets/background_polygons.gif');*/
}
#forecast-band-8 .forecast-band-header {
  color: black;
  border-bottom: 2px solid #07871c;
}
.honely-calculator-section {
  margin: 1.5rem 0;
  width: 97%;
  padding: 1.25rem;
  background: #ffffff;
  border: 1px solid #07871c;
  border-radius: 4px;
  box-shadow: 0px 4px 5px -2px rgb(0 0 0 / 20%), 0px 7px 10px 1px rgb(0 0 0 / 14%), 0px 2px 16px 1px rgb(0 0 0 / 12%);
  position: relative;
}
.honely-calculator-section.blocked .honely-calculator-form {
  filter: blur(3px);
}
.honely-calculator-copy {
  border-bottom: 1px solid #cccccc;
}
.feature-text {
  font-size: 25px;
  font-weight: bold;
}
.honely-calculator-section button {
  background-color: #07871c;
  color: #ffffff;
  padding: 10px;
  border: 1px solid #07871c;
  border-radius: 3px;
}
.honely-calculator-section button:hover {
  background: #ffffff;
  color: #07871c;
}
.report-form-overlay {
  display: none;
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(10, 10, 10, 0.5);
  border-radius: 4px;
  z-index: 999;
}
.report-form-overlay.active {
  display: block;
}
.report-form-container {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  width: 70%;
  height: 80vh;
  max-width: 900px;
  min-width: 300px;
  min-height: 300px;
  background: #ffffff;
  border-radius: 5px;
  z-index: 99;
}
.report-form-title-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 15px;
  background: #07871c;
  color: #ffffff;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}
.report-form-title-bar span {
  font-size: 18px;
  line-height: 100%;
}
.report-form-title-bar i {
  font-size: 30px;
  cursor: pointer;
}
.report-form-title-bar i:hover {
  color: #222222;
}
.fa-times-thin:before {
  content: '\00d7';
}
.report-form-wrapper {
  padding: 8px 20px;
  overflow-y: scroll;
  height: 84%;
}
.custom-scrollbar::-webkit-scrollbar {
    width: 10px;
}
.custom-scrollbar::-webkit-scrollbar-track {
    background: #e7e7e7;
}
.custom-scrollbar::-webkit-scrollbar-thumb {
    background: #bbbbbb;
}
.custom-scrollbar::-webkit-scrollbar-thumb:hover {
    background: #e67e22;
}
.color-secondary {
  color: #e67e22;
}
.font-bold {
  font-weight: 600;
}
.calculator-form-wrapper {
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
}
.calculator-form-col {
  width: 50%;
}
.calculator-form-container {
  width: 100%;
  text-align: left;
}
.calculator-form-container input[type="text"],
.calculator-form-container select,
.calculator-form-container textarea {
  display: block;
  width: 100%;
  padding: 5px;
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
.calculator-form-container input[type="text"].error-input {
  border-color: red;
}
.calculator-form-container input#agent-logo {
  width: 100%;
}
.calculator-form-container select {
  height: 37px;
}
.calculator-form-container input[type="text"]:focus,
.calculator-form-container select:focus,
.calculator-form-container textarea:focus {
  border-bottom-color: #07871c;
  border-bottom-width: 2px;
}
.calculator-form-container input:read-only {
  background: #efefef;
}
.calculator-form-container select:disabled {
  background: #efefef;
}
.calculator-form-container input:read-only:focus {
  border-bottom-color: #cccccc;
}
.calculator-form-container select {
  appearance: auto;
  -moz-appearance: auto;
  -webkit-appearance: auto;
}
.calculator-form-container label {
  display: block;
  font-weight: 600;
  margin-bottom: 2px;
}
.form-section {
  border-bottom: 1px solid #cccccc;
  padding-bottom: 1.5rem;
  margin-bottom: 1rem;
}
.form-section.noborder-nomargin {
  border-bottom: none;
  margin-bottom: 0;
}
.form-feature-title {
  font-size: 20px;
  font-weight: bold;
}
.forecast-calculator-results-row {
  max-width: 1000px;
  margin: 0 auto;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-content: center;
}
.forecast-before, .forecast-after {
  width: 47%;
  background: #f7f7f7;
  border-radius: 5px;
  padding: 10px 15px;
}
.forecast-label {
  font-size: 17px;
  font-weight: bold;
  text-transform: uppercase;
}
.forecast-before .forecast-label {
  color: #07871c;
  border-bottom: 1px solid #07871c;
}
.forecast-after .forecast-label {
  color: #e67e22;
  border-bottom: 1px solid #e67e22;
}
.forecast-result {
  text-align: center;
  font-size: 20px;
  font-weight: bold;
  margin-bottom: 5px !important;
}
.forecast-before .forecast-result {
  color: #07871c;
}
.forecast-after .forecast-result {

}
.form-section-title {
  font-weight: bold;
}
.form-section-title span {
  text-transform: uppercase;
}
.form-row {
  width: 100%;
}
.form-row.share-area {
  max-width: 98%;
  padding: 15px;
  margin-bottom: 1rem;
  border: 1px dashed #bbbbbb;
  border-radius: 5px;
  background: #efefef;
}
.form-row textarea {
  min-height: 100px;
}
.form-row-flex-2cols, .form-row-flex-3cols, .form-row-flex-4cols {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
}
.honely-calculator-section .form-row-flex-2cols p {
  width: 44%;
  margin-right: 2rem;
}
.form-row-flex-2cols p:last-of-type {
  margin-right: 0;
}
.honely-calculator-section .form-row-flex-3cols p {
  width: 30%;
  margin-right: 2rem;
}
.form-row-flex-3cols p:last-of-type {
  margin-right: 0;
}
.honely-calculator-section .form-row-flex-4cols p {
  width: 20%;
  margin-right: 2rem;
}
.form-row-flex-4cols p:last-of-type {
  margin-right: 0;
}
.form-row-flex-4cols p.wide {
  width: 30%;
}
.file-input-container {
  width: 98%;
  padding: 10px 15px;
  background: #f5f5f5;
  border: 1px dashed #bbbbbb;
  border-radius: 5px;
}
.file-input-label {
  cursor: pointer;
  font-weight: normal !important;
  border: 1px solid #bbbbbb;
  display: inline-block !important;
  padding: 5px 15px;
  border-radius: 3px;
}
/*#agent-logo {
  opacity: 0;
  position: absolute;
  z-index: -1;
}*/
.form-action-row {
  padding: 15px;
  text-align: center;
  border-top: 1px solid #bbbbbb;
}
.form-action-row-mobile {
  display: none;
  text-align: center;
}
.form-action-row button, .form-action-row-mobile button {
  background: #07871c;
  color: #ffffff;
  padding: 8px 15px;
  margin-right: 1rem;
  border-radius: 3px;
  border: 1px solid #07871c;
  -webkit-transition: all 0.30s ease-in-out;
  -moz-transition: all 0.30s ease-in-out;
  -ms-transition: all 0.30s ease-in-out;
  -o-transition: all 0.30s ease-in-out;
}
.form-action-row button:last-of-type,
.form-action-row-mobile button:last-of-type {
  margin-right: 0;
}
.form-action-row button:hover, .form-action-row-mobile button:hover {
  background: #ffffff;
  color: #07871c;
}
.form-action-row button:disabled, .form-action-row-mobile button:disabled {
  background: #bbbbbb;
  border: 1px solid #222222;
  color: #222222;
}
.form-action-row button:disabled:hover, .form-action-row-mobile button:disabled:hover {
  background: #bbbbbb;
  border: 1px solid #222222;
  color: #222222;
}
.charts-row {
  width: 100%;
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
}
.chart-container {
  width: 22%;
}
.chart-container p {
  font-weight: 600;
  text-align: center;
  border-top: 2px solid #07871c;
  padding-top: 5px;
  margin: 0 auto;
}
.loader-overlay {
  display: none;
  position: absolute;
  left: 50%;
  transform: translateX(-50%);
  top: 50%;
  background: rgba(20,20,20,0.7);
  padding: 1rem;
  border-radius: 5px;
  z-index: 999;
}
.loader-overlay.active {
  display: block;
}
.loader-container {
  color: #ffffff;
  font-size: 1rem;
  font-weight: normal;
}
.loader-container span {
  padding-left: 10px;
}
.loader-container i {
  font-size: 1rem;
}
.brokerage-logo-container {
  width: 100%;
  margin-bottom: 0 !important;
  border: 1px solid #bbbbbb;
  border-radius: 3px;
  padding: 10px;
}
.brokerage-logo-container img {
  display: block;
  max-width: 240px;
  max-height: 50px;
  margin: 0 auto;
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
@media (max-width: 1900px) {
  .honely-calculator-section {
    width: 80%;
    max-width: 1000px;
  }
  .calculator-form-col {
    width: 100%;
  }
  .honely-calculator-section .form-row-flex-4cols p {
    width: 22%;
  }
  .honely-calculator-section .form-row-flex-2cols p {
    width: 47%;
  }
}
@media (max-width: 1000px) {
  .honely-calculator-section {
    width: 90%;
  }
}
@media (max-width: 800px) {
  .honely-calculator-section .form-row-flex-3cols p {
    width: 45%;
  }
  .honely-calculator-section .form-row-flex-4cols p.wide,
  .honely-calculator-section .form-row-flex-4cols p,
  .honely-calculator-section .chart-container {
    width: 45%;
  }
}
@media (max-width: 600px) {
  .honely-calculator-section .form-row-flex-3cols p {
    width: 100%;
    margin-right: 0;
  }
  .honely-calculator-section .form-row-flex-2cols p {
    width: 100%;
    margin-right: 0;
  }
  .honely-calculator-section .form-row-flex-4cols p.wide,
  .honely-calculator-section .form-row-flex-4cols p,
  .honely-calculator-section .chart-container {
    width: 100%;
    margin-right: 0;
  }
  .form-row.margin-top-sm {
    margin-top: 1rem;
  }
  .file-input-container {
    width: 100%;
  }
  .form-action-row {
    display: none;
  }
  .form-action-row-mobile {
    display: block;
  }
  .form-action-row-mobile button {
    display: block;
    margin: 0 auto 10px auto;
  }
  .form-action-row-mobile button:last-of-type {
    margin: 0 auto;
  }
  .report-form-wrapper {
    height: 89%;
  }
  .forecast-calculator-results-row {
    flex-direction: column;
  }
  .forecast-before, .forecast-after {
    width: 100%;
  }
}
</style>
