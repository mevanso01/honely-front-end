<template>
  <!-- eslint-disable -->
  <div v-if="forecast" class="form-overlay" id="calculator-form-overlay">
    <!-- report-form-container -->
    <div class="forecast-form-container short">
      <!-- form-title-section -->
      <div class="forecast-form-title-bar">
        <span>Honely Calculator</span>
        <i class="fa fa-times-thin fa-2x" aria-hidden="true" @click="doCloseForm"></i>
      </div>
      <!-- /form-title-section -->
      
      <!-- form-section -->
      <div class="forecast-form-wrapper custom-scrollbar">
        <!-- loader -->
        <div class="section-loader-overlay manual withBackground" id="calculator-loader">
          <div class="section-loader-wrapper">
            <span class='mdi mdi-loading mdi-spin spin'></span>
          </div>
        </div>
        <!-- loader -->

        <!-- calculator form -->
        <div class="forecast-form">
          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-dollar-sign"></i> Honely Estimated Value</span></p>
            <div class="report-honely-value-container">
              <div class="report-honely-value">
                <p class="report-honely-value-label">Before</p>
                <p>{{ getForecastValue }}</p>
              </div>
              <div class="report-honely-value">
                <p class="report-honely-value-label">After *</p>
                <p id="forecast-result-after">--</p>
              </div>
            </div>
          </div>
          <!-- /form-section -->

          <!-- calculator-form-wrapper -->
          <div class="calculator-form-wrapper">
            <div class="calculator-form-col">
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
                <div class="form-row-flex-4cols">
                  <p class="wide">
                    <label>Other Rooms (Increase SQ Ft.)</label>
                    <select name="cal-other-rooms" id="cal-other-rooms" v-bind:disabled="checkSupportOptions(true, 'OtherRooms') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(true, 'OtherRooms', 'other_rooms')"
                        :value="item.id"
                        :selected="getOtherRooms==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Parking Spaces</label>
                    <input type="text" name="cal-parking-spaces" id="cal-parking-spaces" v-bind:value="getParkingSpaces">
                  </p>
                  <p>
                    <label>Plumbing Fixtures #</label>
                    <input type="text" name="cal-plumbing-count" id="cal-plumbing-count" v-bind:value="getPlumbingCount">
                  </p>
                </div>
                <!-- hide following data until further development -->
                <!-- <div class="form-row-flex-2cols">
                  <p>
                    <label>Parking type</label>
                    <input type="text" name="cal-parking-type" id="cal-parking-type" v-bind:value="getParkingType">
                  </p>
                  <p>
                    <label>Amenities</label>
                    <select name="cal-amenities" id="cal-amenities" v-bind:disabled="checkSupportOptions(true, 'Amenities') == false">
                      <option value="--">No</option>
                      <option
                        v-for="(item) in getDropdownOptions(true, 'Amenities', 'amenities')"
                        :value="item.id"
                        :selected="getAmenities==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Air Conditioning</label>
                    <select name="cal-ac-type" id="cal-ac-type" v-bind:disabled="checkSupportOptions(false, 'AirConditioningCode') == false">
                      <option value="--">No</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'AirConditioningCode', 'air_condition_code')"
                        :value="item.id"
                        :selected="getACType==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Heating</label>
                    <select name="cal-heat-type" id="cal-heat-type" v-bind:disabled="checkSupportOptions(false, 'HeatCode') == false">
                      <option value="--">No</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'HeatCode', 'heating_type')"
                        :value="item.id"
                        :selected="getHeatType==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Heating Fuel</label>
                    <select name="cal-heat-fuel-type" id="cal-heat-fuel-type" v-bind:disabled="checkSupportOptions(false, 'HeatingFuelTypeCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'HeatingFuelTypeCode', 'heating_fuel_code')"
                        :value="item.id"
                        :selected="getHeatFuelType==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Pool</label>
                    <select name="cal-pool" id="cal-pool" v-bind:disabled="checkSupportOptions(false, 'PoolCode') == false">
                      <option value="--">No</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'PoolCode', 'pool')"
                        :value="item.id"
                        :selected="getPool==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Most recent sale date (YYYY-MM-DD)</label>
                    <input type="text" name="cal-sale-date" id="cal-sale-date" v-bind:value="getMostRecentSaleDate">
                  </p>
                  <p>
                    <label>Most recent sale price ($)</label>
                    <input type="text" name="cal-sale-price" id="cal-sale-price" v-bind:value="getMostRecentSalePrice">
                  </p>
                </div> -->
              </div>
            </div>
            <!-- hide following data until further development -->
            <!-- <div class="calculator-form-col">
              <div class="form-section noborder-nomargin">
                <p class="form-section-title"><span><i class="fas fa-drafting-compass"></i> Construction Information</span></p>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Condition</label>
                    <select name="cal-condition" id="cal-condition" v-bind:disabled="checkSupportOptions(false, 'BuildingConditionCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'BuildingConditionCode', 'building_condition_code')"
                        :value="item.id"
                        :selected="getCondition==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Architecture Type</label>
                    <select name="cal-architecture" id="cal-architecture" v-bind:disabled="checkSupportOptions(false, 'StyleCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'StyleCode', 'style_code')"
                        :value="item.id"
                        :selected="getArchitecture==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Construction Type</label>
                    <select name="cal-construction" id="cal-construction" v-bind:disabled="checkSupportOptions(false, 'ConstructionTypeCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'ConstructionTypeCode', 'construction_type_code')"
                        :value="item.id"
                        :selected="getConstructionType==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Basement</label>
                    <select name="cal-basement-type" id="cal-basement-type" v-bind:disabled="checkSupportOptions(false, 'BasementCode') == false">
                      <option value="--">No</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'BasementCode', 'basement_code')"
                        :value="item.id"
                        :selected="getBasement==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Roof Style</label>
                    <select name="cal-roof-style" id="cal-roof-style" v-bind:disabled="checkSupportOptions(false, 'RoofTypeCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'RoofTypeCode', 'roof_type_code')"
                        :value="item.id"
                        :selected="getRoofStyle==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Roof Material</label>
                    <select name="cal-roof-material" id="cal-roof-material" v-bind:disabled="checkSupportOptions(false, 'RoofCoverCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'RoofCoverCode', 'roof_cover_code')"
                        :value="item.id"
                        :selected="getRoofMaterial==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Exterior Walls Type</label>
                    <select name="cal-exterior-walls" id="cal-exterior-walls" v-bind:disabled="checkSupportOptions(false, 'ExteriorWallsCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'ExteriorWallsCode', 'exterior_walls_code')"
                        :value="item.id"
                        :selected="getExteriorWalls==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Interior Walls Type</label>
                    <select name="cal-interior-walls" id="cal-interior-walls" v-bind:disabled="checkSupportOptions(false, 'InteriorWallsCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'InteriorWallsCode', 'interior_walls_code')"
                        :value="item.id"
                        :selected="getInteriorWalls==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Flooring</label>
                    <select name="cal-flooring" id="cal-flooring" v-bind:disabled="checkSupportOptions(false, 'FloorCoverCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'FloorCoverCode', 'floor_cover_code')"
                        :value="item.id"
                        :selected="getFlooring==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Garage</label>
                    <select name="cal-garage-type" id="cal-garage-type" v-bind:disabled="checkSupportOptions(false, 'Garage') == false">
                      <option value="--">No</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'Garage', 'garage_code')"
                        :value="item.id"
                        :selected="getGarage==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
                <div class="form-row-flex-2cols">
                  <p>
                    <label>Water</label>
                    <select name="cal-water-type" id="cal-water-type" v-bind:disabled="checkSupportOptions(false, 'WaterCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'WaterCode', 'water_code')"
                        :value="item.id"
                        :selected="getWaterType==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                  <p>
                    <label>Sewer</label>
                    <select name="cal-sewer-type" id="cal-sewer-type" v-bind:disabled="checkSupportOptions(false, 'SewerCode') == false">
                      <option value="--">--</option>
                      <option
                        v-for="(item) in getDropdownOptions(false, 'SewerCode', 'sewer_code')"
                        :value="item.id"
                        :selected="getSewerType==item.name"
                      >
                      {{item.name}}
                      </option>
                    </select>
                  </p>
                </div>
              </div>
            </div> -->
            <div class="form-action-row-mobile">
              <button @click="doHonelyCalculator" id="btn_doHonelyCalculator_mobile" v-bind:disabled="isButtonDisabled">Forecast</button>
            </div>
          </div>
          <!-- /calculator-form-wrapper -->
        </div>
        <!-- /calculator form -->
      </div>
      <!-- /form-section -->

      <!-- form-action-section -->
      <div class="form-action-row">
        <button @click="doHonelyCalculator" id="btn_doHonelyCalculator" v-bind:disabled="isButtonDisabled">Forecast</button>
      </div>
      <!-- /form-action-section -->
    </div>
    <!-- /report-form-container -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'HonelyCalculator',
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
    },
    props: {
      forecast: Object,
      property: Object,
      optionLists: Object,
      countyOptions: Object,
      countyOptionsMulti: Object,
    },

    data: () => ({
      loading: false,
      initialRate: 1,
    }),

    computed: {
      isButtonDisabled () {
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
      getOtherRooms () {
        if (this.property && this.property.structure) {
          if (this.property.structure.other_rooms && this.property.structure.other_rooms !== null) {
            return this.property.structure.other_rooms
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getParkingSpaces () {
        if (this.property && this.property.structure) {
          if (this.property.structure.parking_spaces_count && this.property.structure.parking_spaces_count !== null) {
            return this.property.structure.parking_spaces_count
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getPlumbingCount () {
        if (this.property && this.property.structure) {
          if (this.property.structure.plumbing_fixtures && this.property.structure.plumbing_fixtures !== null) {
            return this.property.structure.plumbing_fixtures
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getParkingType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.parking_type && this.property.structure.parking_type !== null) {
            return this.property.structure.parking_type
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getACType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.air_conditioning_type && this.property.structure.air_conditioning_type !== null) {
            return this.property.structure.air_conditioning_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getHeatType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.heating_type && this.property.structure.heating_type !== null) {
            return this.property.structure.heating_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getHeatFuelType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.heating_fuel_type && this.property.structure.heating_fuel_type !== null) {
            return this.property.structure.heating_fuel_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getPool () {
        if (this.property && this.property.structure) {
          if (this.property.structure.pool_type && this.property.structure.pool_type !== null) {
            return this.property.structure.pool_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getGarage () {
        if (this.property && this.property.structure) {
          if (this.property.structure.garage_type && this.property.structure.garage_type !== null) {
            return this.property.structure.garage_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getAmenities () {
        if (this.property && this.property.structure) {
          if (this.property.structure.amenities && this.property.structure.amenities !== null) {
            return this.property.structure.amenities
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getMostRecentSaleDate () {
        if (this.property && this.property.sale_history) {
          if (this.property.sale_history != null && this.property.sale_history.length > 0) {
            if (this.property.sale_history[0].date && this.property.sale_history[0].date != null) {
              return this.property.sale_history[0].date
            } else {
              return ''
            }
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getMostRecentSalePrice () {
        if (this.property && this.property.sale_history) {
          if (this.property.sale_history != null && this.property.sale_history.length > 0) {
            if (this.property.sale_history[0].price && this.property.sale_history[0].price != null) {
              return this.property.sale_history[0].price
            } else {
              return ''
            }
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getCondition () {
        if (this.property && this.property.structure) {
          if (this.property.structure.condition && this.property.structure.condition !== null) {
            return this.property.structure.condition
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getConstructionType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.construction_type && this.property.structure.construction_type !== null) {
            return this.property.structure.construction_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getArchitecture () {
        if (this.property && this.property.structure) {
          if (this.property.structure.architecture_type && this.property.structure.architecture_type !== null) {
            return this.property.structure.architecture_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getRoofStyle () {
        if (this.property && this.property.structure) {
          if (this.property.structure.roof_style_type && this.property.structure.roof_style_type !== null) {
            return this.property.structure.roof_style_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getRoofMaterial () {
        if (this.property && this.property.structure) {
          if (this.property.structure.roof_material_type && this.property.structure.roof_material_type !== null) {
            return this.property.structure.roof_material_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getExteriorWalls () {
        if (this.property && this.property.structure) {
          if (this.property.structure.exterior_wall_type && this.property.structure.exterior_wall_type !== null) {
            return this.property.structure.exterior_wall_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getInteriorWalls () {
        if (this.property && this.property.structure) {
          if (this.property.structure.interior_wall_type && this.property.structure.interior_wall_type !== null) {
            return this.property.structure.interior_wall_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getFlooring () {
        if (this.property && this.property.structure) {
          if (this.property.structure.flooring_types && this.property.structure.flooring_types !== null) {
            return this.property.structure.flooring_types
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getBasement () {
        if (this.property && this.property.structure) {
          if (this.property.structure.basement_type && this.property.structure.basement_type !== null) {
            return this.property.structure.basement_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getSewerType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.sewer_type && this.property.structure.sewer_type !== null) {
            return this.property.structure.sewer_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getWaterType () {
        if (this.property && this.property.structure) {
          if (this.property.structure.water_type && this.property.structure.water_type !== null) {
            return this.property.structure.water_type
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
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
    watch: {
      forecast: function () {
        // console.log('forecast changed')
        // reset calculated data
        this.doUpdateCalculatorResult(null)
        // update dropdown selected value
        this.doUpdateSelectedValues()
      },
      property: function () {
        this.doInitialEstimate()
        document.getElementById('forecast-result-after').innerHTML = '--'
      },
    },
    created () {
      // get supported drop-down options for selected county
    },
    mounted () {
      // default
      // this.doDev()
      // this.doUpdateSelectedValues()
      this.doInitialEstimate()
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
          /* this.doGetValueForOptionList('cal-ac-type', this.getAirConditionCodes, this.property.structure.air_conditioning_type)
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
          this.doGetValueForOptionList('cal-sewer-type', this.getSewerCodes, this.property.structure.sewer_type) */
        }
      },
      doUpdateCalculatorResult (data) {
        const toUpdate = document.getElementById('forecast-result-after')
        if (data && data != null) {
          if (toUpdate) {
            toUpdate.innerHTML = this.formatCurrency(this.doGetForecastResult(data))
          }
        } else {
          if (toUpdate) {
            toUpdate.innerHTML = '--'
          }
        }
      },
      toContactPg () {
        window.location.href = '/contact'
      },
      toSubscribe () {
        window.location.href = '/subscribe'
      },
      doDev () {
        console.log('>> current property ----------------')
        console.log(this.property)
        console.log('>> current forecast ----------------')
        console.log(this.forecast)
        console.log('>> current walkscore ----------------')
      },
      getToday () {
        const monthNames = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December']
        const today = new Date()
        const todayString = monthNames[today.getMonth()] + ' ' + today.getDate() + ', ' + today.getFullYear()
        return todayString
      },
      doShowForecastCalForm () {
        document.getElementById('calculator-form-overlay').classList.add('active')
      },
      doCloseForm () {
        document.getElementById('calculator-form-overlay').classList.remove('active')
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
            return parseFloat(this.forecast.property_forecast.appraisal) * (data.current_value / this.initialRate)
          } else {
            return null
          }
        } else {
          return null
        }
      },
      doConvertDropdownToCode (dropdown, option) {
        if (dropdown && option) {
          if (dropdown === 'stories') {
            const temp = option.replace('Stories', '').replace('Story', '').replace(' ', '')
            if (!isNaN(temp)) {
              return parseInt(parseFloat(temp) * 100)
            }
          } else {
            return null
          }
        } else {
          return null
        }
      },
      doInitialEstimate () {
        // TO DO WHEN API is ready
        // get all the inputs from user
        // console.log(this.property)
        if (this.property) {
          const propertyId = this.property.address.property_id
          const fips = this.property.address.fips
          let zip = this.property.address.zip_code
          let yearBuilt = this.property.structure.year_built
          let stories = this.property.structure.stories
          let sqft = this.property.structure.total_area_sq_ft
          let numBeds = this.property.structure.beds_count
          let numBaths = this.property.structure.baths
          let numPartialBaths = this.property.structure.partial_baths_count
          let roomCount = this.property.structure.rooms_count
          let parkingSpaces = this.property.structure.parking_spaces_count
          let plumbingCount = this.property.structure.plumbing_fixtures

          // check numerical inputs
          if (!this.validateNumericalInput(sqft)) {
            sqft = 0
          }
          if (!this.validateNumericalInput(numBeds)) {
            numBeds = 0
          }
          if (!this.validateNumericalInput(numBaths)) {
            numBaths = 0
          }
          if (!this.validateNumericalInput(numPartialBaths)) {
            numPartialBaths = 0
          }
          if (!this.validateNumericalInput(roomCount)) {
            roomCount = 0
          }
          if (!this.validateNumericalInput(parkingSpaces)) {
            parkingSpaces = 0
          }
          if (!this.validateNumericalInput(plumbingCount)) {
            plumbingCount = 0
          }

          // update null data
          if (zip === null || zip === '') {
            zip = null
          }
          if (yearBuilt !== null && yearBuilt !== '') {
            yearBuilt = yearBuilt.toString()
          }
          if (stories == null) {
            stories = 0
          } else {
            stories = this.doConvertDropdownToCode('stories', stories)
          }

          const body = {
            basic_info: {
              property_id: propertyId,
              fips: fips,
              zip: zip,
              year_build: yearBuilt,
            },
            numerical: {
              sqft: this.formatNumber(sqft),
              num_beds: numBeds,
              num_baths: numBaths,
              num_partial_baths: numPartialBaths,
              room_count: roomCount,
              parking_spaces: parkingSpaces,
              plumbing_count: plumbingCount,
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
          const self = this
          const apiURL = 'https://api.honely.com/calculator/honely_calculator'

          fetch(apiURL, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(body),
          }).then(function (response) {
            //  console.log(response)
            return response.json()
          }).then(function (data) {
            // console.log(data)
            if (data) {
              if (data.current_value) {
                self.initialRate = data.current_value
              }
            }
          }).catch((err) => {
            self.hideLoader()
            console.log('[ERROR] Honely calculator API failed =>', err)
          })
        }
      },
      doHonelyCalculator () {
        // TO DO WHEN API is ready
        // get all the inputs from user
        // console.log(this.property)
        if (this.property) {
          const propertyId = this.property.address.property_id
          const fips = this.property.address.fips
          let zip = this.property.address.zip_code
          let yearBuilt = this.property.structure.year_built
          // let stories = document.getElementById('stories').value
          let stories = this.property.structure.stories
          const sqft = document.getElementById('cal-sqft').value
          const numBeds = document.getElementById('cal-num-beds').value
          const numBaths = document.getElementById('cal-num-baths').value
          let numPartialBaths = document.getElementById('cal-num-partial-bath').value
          let roomCount = document.getElementById('cal-room-count').value
          let parkingSpaces = document.getElementById('cal-parking-spaces').value
          let plumbingCount = document.getElementById('cal-plumbing-count').value

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
            numPartialBaths = 0
          }
          if (!this.validateNumericalInput(roomCount)) {
            roomCount = 0
          }
          if (!this.validateNumericalInput(parkingSpaces)) {
            parkingSpaces = 0
          }
          if (!this.validateNumericalInput(plumbingCount)) {
            plumbingCount = 0
          }

          // update null data
          if (zip === null || zip === '') {
            zip = null
          }
          if (yearBuilt !== null || yearBuilt !== '') {
            yearBuilt = yearBuilt.toString()
          }
          if (stories === '--') {
            stories = 0
          } else {
            stories = this.doConvertDropdownToCode('stories', stories)
          }

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
              parking_spaces: parkingSpaces,
              plumbing_count: plumbingCount,
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
          // this.loading = true
          this.displayLoader()
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
            // self.loading = false
            self.hideLoder()
            // update component
            if (data) {
              // console.log(data.current_value)
              // document.getElementById('forecast-result-after').innerHTML = self.formatCurrency(self.doGetForecastResult(data))
              if (self.doGetForecastResult(data) && self.doGetForecastResult(data) != null) {
                self.doUpdateCalculatorResult(data)
              } else {
                self.doUpdateCalculatorResult(null)
              }
            }
            console.log('[INFO] Finished processing Honely calculator......')
          }).catch((err) => {
            // self.loading = false
            self.hideLoder()
            console.log('[ERROR] Honely calculator API failed =>', err)
          })
        }
      },
      displayLoader () {
        document.getElementById('calculator-loader').classList.add('active')
      },
      hideLoder () {
        document.getElementById('calculator-loader').classList.remove('active')
      },
    },
  }
</script>
