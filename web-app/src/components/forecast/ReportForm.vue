<template>
  <!-- eslint-disable -->
  <div v-if="forecast" class="form-overlay" id="report-form-overlay">
    <!-- forecast-form-container -->
    <div class="forecast-form-container">
      <!-- form-title-section -->
      <div class="forecast-form-title-bar">
        <span>Property Report</span>
        <i class="fa fa-times-thin fa-2x" aria-hidden="true" @click="doCloseForm"></i>
      </div>
      <!-- /form-title-section -->

      <!-- form-section -->
      <div class="forecast-form-wrapper custom-scrollbar">
        <!-- loader -->
        <div class="section-loader-overlay manual withBackground" id="report-loader">
          <div class="section-loader-wrapper">
            <span class='mdi mdi-loading mdi-spin spin'></span>
          </div>
        </div>
        <!-- loader -->

        <div class="forecast-form">
          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-user-circle"></i> Agent Information</span></p>
            <div class="form-row-flex-3cols">
              <p>
                <label>Name</label>
                <input type="text" name="agent-name" id="agent-name" v-bind:value="getAgentName">
              </p>
              <p>
                <label>Email</label>
                <input type="text" name="agent-email" id="agent-email" v-bind:value="getAgentEmail">
              </p>
              <p>
                <label>Phone</label>
                <input type="text" name="agent-phone" id="agent-phone" v-bind:value="getAgentPhone">
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Brokerage Logo</label>
                <select name="brokerage-logo" id="brokerage-logo" @change="doShowBrokerageLogo">
                  <option value="--">Default</option>
                  <option value="ben-bay-realty">Ben Bay Realty</option>
                  <option value="berkshire-hathaway-homeservices">Berkshire Hathaway HomeServices</option>
                  <option value="brown-harris-stevens">Brown Harris Stevens</option>
                  <option value="century-21">Century 21</option>
                  <option value="christies-international-real-estate">Christie's International Real Estate</option>
                  <option value="coldwell-banker-real-estate">Coldwell Banker Real Estate</option>
                  <option value="compass">Compass</option>
                  <option value="corcoran-group">Corcoran Group</option>
                  <option value="core">Core</option>
                  <option value="douglas-elliman">Douglas Elliman</option>
                  <option value="elegran-real-estate-and-development">Elegran Real Estate and Development</option>
                  <option value="engel-volkers">Engel &amp; V&#214;lkers</option>
                  <option value="exit-realty">EXIT Realty</option>
                  <option value="exp-realty">eXp Realty</option>
                  <option value="fillmore-real-estate">Fillmore Real Estate</option>
                  <option value="halstead-real-estate">Halstead Real Estate</option>
                  <option value="homeservices-of-america">HomeServices of America</option>
                  <option value="keller-williams-nyc">Keller Williams NYC</option>
                  <option value="keller-williams-realty">Keller Williams Realty</option>
                  <option value="laffey">Laffey Real Estate</option>
                  <option value="leslie-j-garfield-co">Leslie J. Garfield &amp; Co.</option>
                  <option value="mns">MNS</option>
                  <option value="modern-spaces">Modern Spaces</option>
                  <option value="momentum-real-estate">Momentum Real Estate</option>
                  <option value="nest-seekers-international">Nest Seekers International</option>
                  <option value="oxford-property-group">Oxford Property Group</option>
                  <option value="r-new-york">R New York</option>
                  <option value="re-max">RE/MAX</option>
                  <option value="re-max-edge">RE/MAX Edge</option>
                  <option value="re-max-real-estate-professionals">RE/MAX Real Estate Professionals</option>
                  <option value="sothebys-international-realty">Sotheby's International Realty</option>
                  <option value="triplemint">Triplemint</option>
                  <option value="the-modlin-group">The Modlin Group</option>
                  <option value="warburg-realty">Warburg Realty</option>
                  <option value="weichert">Weichert</option>
                </select>
              </p>
              <p class="brokerage-logo-container">
                <img src="https://honely-files-public.s3.amazonaws.com/report/logo_honely_report_default.png" id="logo-display" alt="Report logo">
              </p>
            </div>
            <div class="form-row margin-top-sm">
              <label>Or use custom logo</label>
              <div class="file-input-container">
                <!-- <label for="agent-logo" class="file-input-label">Browse file...</label> -->
                <input type="file" name="agent-logo" id="agent-logo">
              </div>
            </div>
          </div>
          <!-- /form-section -->

          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-dollar-sign"></i> Honely Estimated Value</span></p>
            <div class="report-honely-value-container">
              <div class="report-honely-value">
                <p class="report-honely-value-label">Before</p>
                <p>{{ getHonelyAppraisal }}</p>
              </div>
              <div class="report-honely-value">
                <p class="report-honely-value-label">After *</p>
                <p id="report-honey-value-after">--</p>
              </div>
            </div>
          </div>
          <!-- /form-section -->

          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-home"></i> Property Information</span></p>
            <div class="form-row-flex-2cols">
              <p>{{ getFullAddress }}</p>
              <p><span class="font-bold">APN:</span> {{ getAPN }}</p>
            </div>
            <div class="form-row-flex-4cols">
              <p>
                <label>Year built</label>
                <input type="text" name="year-built" id="year-built" v-bind:value="getYearBuilt" readonly>
              </p>
              <p>
                <label>County</label>
                <input type="text" name="county" id="county" v-bind:value="getCounty" readonly>
              </p>
              <p>
                <label>Stories</label>
                <select name="stories" id="stories">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getStoriesCodes"
                    :value="item.name"
                    :selected="getStories==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>SQ Ft.</label>
                <input type="text" name="sqft" id="sqft" v-bind:value="getSqft">
              </p>
            </div>
            <div class="form-row-flex-4cols">
              <p>
                <label># of bedrooms</label>
                <input type="text" name="num-beds" id="num-beds" v-bind:value="getNumBeds">
              </p>
              <p>
                <label># of bathrooms</label>
                <input type="text" name="num-baths" id="num-baths" v-bind:value="getNumBaths">
              </p>
              <p>
                <label># of partial bathrooms</label>
                <input type="text" name="num-partial-bath" id="num-partial-bath" v-bind:value="getNumPartialBaths">
              </p>
              <p>
                <label>Room count</label>
                <input type="text" name="room-count" id="room-count" v-bind:value="getRoomCount">
              </p>
            </div>
            <div class="form-row-flex-4cols">
              <p class="wide">
                <label>Other Rooms</label>
                <select name="other-rooms" id="other-rooms">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getOtherRoomCodes"
                    :value="item.name"
                    :selected="getOtherRooms==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Parking spaces</label>
                <input type="text" name="parking-spaces" id="parking-spaces" v-bind:value="getParkingSpaces">
              </p>
              <p>
                <label>Plumbing Fixtures #</label>
                <input type="text" name="plumbing-count" id="plumbing-count" v-bind:value="getPlumbingCount">
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Parking type</label>
                <input type="text" name="parking-type" id="parking-type" v-bind:value="getParkingType">
              </p>
              <p>
                <label>Air conditioning type</label>
                <select name="ac-type" id="ac-type">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getAirConditionCodes"
                    :value="item.name"
                    :selected="getACType==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Heating type</label>
                <select name="heat-type" id="heat-type">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getHeatingTypeCodes"
                    :value="item.name"
                    :selected="getHeatType==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Heating fuel type</label>
                <select name="heat-fuel-type" id="heat-fuel-type">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getHeatingFuelCodes"
                    :value="item.name"
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
                <select name="pool" id="pool">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getPoolCodes"
                    :value="item.name"
                    :selected="getPool==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Amenities</label>
                <select name="amenities" id="amenities">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getAmenitiesCodes"
                    :value="item.name"
                    :selected="getAmenities==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Most recent sale date (YYYY-MM-DD)</label>
                <input type="text" name="sale-date" id="sale-date" v-bind:value="getMostRecentSaleDate">
              </p>
              <p>
                <label>Most recent sale price ($)</label>
                <input type="text" name="sale-price" id="sale-price" v-bind:value="getMostRecentSalePrice">
              </p>
            </div>
          </div>
          <!-- /form-section -->

          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-drafting-compass"></i> Construction Information</span></p>
            <div class="form-row-flex-2cols">
              <p>
                <label>Condition</label>
                <select name="condition" id="condition">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getBuildingConditionCodes"
                    :value="item.name"
                    :selected="getCondition==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Architecture type</label>
                <select name="architecture" id="architecture">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getStyleCodes"
                    :value="item.name"
                    :selected="getArchitecture==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Construction type</label>
                <select name="construction" id="construction">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getConstructionTypeCodes"
                    :value="item.name"
                    :selected="getConstructionType==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Basement type</label>
                <select name="basement-type" id="basement-type">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getBasementCodes"
                    :value="item.name"
                    :selected="getBasement==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Roof style</label>
                <select name="roof-style" id="roof-style">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getRoofTypeCodes"
                    :value="item.name"
                    :selected="getRoofStyle==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Roof material</label>
                <select name="roof-material" id="roof-material">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getRoofCoverCodes"
                    :value="item.name"
                    :selected="getRoofMaterial==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Exterior walls</label>
                <select name="exterior-walls" id="exterior-walls">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getExteriorWallsCodes"
                    :value="item.name"
                    :selected="getExteriorWalls==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Interior walls</label>
                <select name="interior-walls" id="interior-walls">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getInteriorWallsCodes"
                    :value="item.name"
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
                <select name="flooring" id="flooring">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getFloorCoverCodes"
                    :value="item.name"
                    :selected="getFlooring==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
              <p>
                <label>Water type</label>
                <select name="water-type" id="water-type">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getWaterCodes"
                    :value="item.name"
                    :selected="getWaterType==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
            <div class="form-row-flex-2cols">
              <p>
                <label>Sewer type</label>
                <select name="sewer-type" id="sewer-type">
                  <option value="--">--</option>
                  <option
                    v-for="(item) in getSewerCodes"
                    :value="item.name"
                    :selected="getSewerType==item.name"
                  >
                  {{item.name}}
                  </option>
                </select>
              </p>
            </div>
          </div>
          <!-- /form-section -->

          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-chart-line"></i> Neighborhood Forecast</span></p>
            <div class="charts-row">
              <div class="chart-container">
                <apexchart
                  ref="chart3month"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptions"
                  :series="chart3month"
                />
                <p>3 Months</p>
              </div>
              <div class="chart-container">
                <apexchart
                  ref="chart1year"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptions"
                  :series="chart1year"
                />
                <p>1 Year</p>
              </div>
              <div class="chart-container">
                <apexchart
                  ref="chart2years"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptions"
                  :series="chart2years"
                />
                <p>2 Years</p>
              </div>
              <div class="chart-container">
                <apexchart
                  ref="chart3years"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptions"
                  :series="chart3years"
                />
                <p>3 Years</p>
              </div>
            </div>
          </div>
          <!-- /form-section -->

          <!-- form-section -->
          <div class="form-section">
            <p class="form-section-title"><span><i class="fas fa-chart-line"></i> Rental Trends</span></p>
            <div class="charts-row">
              <div class="chart-container chart-col-25">
                <apexchart
                  ref="chartMedianRent"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptionsMedianRent"
                  :series="chartMedianRent"
                />
                <p>Media Rent</p>
              </div>
              <div class="chart-container chart-col-25">
                <apexchart
                  ref="chartCashFlow"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptionsCashFlow"
                  :series="chartCashFlow"
                />
                <p>Cash Flow</p>
              </div>
              <div class="chart-container chart-col-50">
                <apexchart
                  ref="chartRentalGrowth"
                  type="bar"
                  height="300"
                  width="100%"
                  :options="reportChartOptionsGrowth"
                  :series="chartRentalGrowth"
                />
                <p>Rental Growth</p>
              </div>
            </div>
          </div>
          <!-- /form-section -->

           <!-- form-section -->
          <div class="form-section share-area noborder-nomargin">
            <p class="form-section-title"><i class="fas fa-share-alt"></i> <span>Share Honely Report (Optional)</span></p>
            <div class="form-row">
              <p>
                <label>Emails (use commas " , " to separate multiple emails)</label>
                <div class="input-text-multi" id="share-emails-input-container">
                  <div class="input-text-multi-display" id="input-text-multi-display"></div>
                  <input type="text" name="share-emails-temp" id="share-emails-temp">
                  <input type="hidden" name="share-emails" id="share-emails">
                </div>
              </p>
              <p>
                <label>Message</label>
                <textarea name="share-message" id="share-message" placeholder="enter your message"></textarea>
              </p>
            </div>
          </div>
          <div>
          <p class="report-disclaimer">Honely provides the Honely AI, data, website and brand &amp; links &ldquo;as is,&rdquo; &ldquo;with all faults&rdquo; and &ldquo;as available.&rdquo; <br>* The Honely revaluation currenty takes into account only valid changes to inputs such as property size, number of bedrooms, and number of bathrooms.</p>
          </div>
          <!-- /form-section -->

          <!-- form-section -->
          <div class="form-action-row-mobile">
            <button @click="doUpdateHonelyEstimate" id="btn_doUpdateEstimate_mobile">Update Estimate</button>
            <button @click="doGenerateReport" id="btn_doShareReport_mobile">Share Report</button>
            <button @click="doDownloadReport" id="btn_doDownloadReport_mobile">Generate Report</button>
          </div>
          <!-- /form-section -->
        </div>
      </div>
      <!-- /form-section -->

      <!-- form-action-section -->
      <div class="form-action-row">
        <button @click="doUpdateHonelyEstimate" id="btn_doUpdateEstimate">Update Estimate</button>
        <button @click="doShareReport" id="btn_doShareReport">Share Report</button>
        <button @click="doDownloadReport" id="btn_doDownloadReport">Generate Report</button>
      </div>
      <!-- /form-action-section -->
    </div>
    <!-- /forecast-form-container -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'ReportForm',
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
    },
    props: {
      forecast: Object,
      user: Object,
      property: Object,
      walkscore: Object,
      schools: Array,
      optionLists: Object,
      rentalTrends: Object,
    },
    data: () => ({
      mapsKey: 'AIzaSyClIFG-ONBwyXrn4_kaA4yMYHGpZD5EEko',
      initialRate: 1,
      loading: false,
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
      getHonelyAppraisal () {
        // console.log(this.property)
        if (this.property && this.property.valuation.appraisal) {
          return this.formatCurrency(this.property.valuation.appraisal)
        } else {
          return '--'
        }
      },
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
      getAgentName () {
        if (this.user) {
          return this.user.first_name + ' ' + this.user.last_name
        } else {
          return ''
        }
      },
      getAgentEmail () {
        if (this.user) {
          return this.user.email
        } else {
          return ''
        }
      },
      getAgentPhone () {
        if (this.user) {
          return this.user.phone_number
        } else {
          return ''
        }
      },
      getCustomLogo () {
        return ''
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
      getChartHeight () {
        return 300 + 1
      },
      reportChartOptions () {
        return {
          plotOptions: {
            bar: {
              distributed: true,
              dataLabels: {
                position: 'top',
              },
            },
          },
          xaxis: {
            categories: [this.forecast.zipcode, 'Surrounding 10 zip codes', 'State'],
            labels: {
              show: false,
            },
          },
          legend: {
            horizontalAlign: 'left',
          },
          chart: {
            toolbar: {
              show: false,
            },
          },
          colors: [
            '#24cb43', '#ff7d1f', '#f5da0f',
          ],
          dataLabels: {
            offsetY: -20,
            style: {
              colors: ['#434544'],
            },
            formatter: function (val, opts) {
              return val + '%'
            },
          },
        }
      },
      chart3month () {
        if (this.forecast) {
          if (this.forecast.neighborhood.percentage_change_forecasts && this.forecast.surrounding_zipcode.percentage_change_forecasts && this.forecast.state_statistics.percentage_change_forecasts) {
            return [{
              name: 'Percent Change',
              data: [
                this.forecast.neighborhood.percentage_change_forecasts[1].change,
                this.forecast.surrounding_zipcode.percentage_change_forecasts[1].change,
                this.forecast.state_statistics.percentage_change_forecasts[1].change,
              ],
            }]
          } else {
            return [{
              name: 'Percent Change',
              data: [],
            }]
          }
        } else {
          return [{
            name: 'Percent Change',
            data: [],
          }]
        }
      },
      chart1year () {
        if (this.forecast) {
          if (this.forecast.neighborhood.percentage_change_forecasts && this.forecast.surrounding_zipcode.percentage_change_forecasts && this.forecast.state_statistics.percentage_change_forecasts) {
            return [{
              name: 'Percent Change',
              data: [
                this.forecast.neighborhood.percentage_change_forecasts[2].change,
                this.forecast.surrounding_zipcode.percentage_change_forecasts[2].change,
                this.forecast.state_statistics.percentage_change_forecasts[2].change,
              ],
            }]
          } else {
            return [{
              name: 'Percent Change',
              data: [],
            }]
          }
        } else {
          return [{
            name: 'Percent Change',
            data: [],
          }]
        }
      },
      chart2years () {
        if (this.forecast) {
          if (this.forecast.neighborhood.percentage_change_forecasts && this.forecast.surrounding_zipcode.percentage_change_forecasts && this.forecast.state_statistics.percentage_change_forecasts) {
            return [{
              name: 'Percent Change',
              data: [
                this.forecast.neighborhood.percentage_change_forecasts[3].change,
                this.forecast.surrounding_zipcode.percentage_change_forecasts[3].change,
                this.forecast.state_statistics.percentage_change_forecasts[3].change,
              ],
            }]
          } else {
            return [{
              name: 'Percent Change',
              data: [],
            }]
          }
        } else {
          return [{
            name: 'Percent Change',
            data: [],
          }]
        }
      },
      chart3years () {
        if (this.forecast) {
          if (this.forecast.neighborhood.percentage_change_forecasts && this.forecast.surrounding_zipcode.percentage_change_forecasts && this.forecast.state_statistics.percentage_change_forecasts) {
            return [{
              name: 'Percent Change',
              data: [
                this.forecast.neighborhood.percentage_change_forecasts[3].change,
                this.forecast.surrounding_zipcode.percentage_change_forecasts[3].change,
                this.forecast.state_statistics.percentage_change_forecasts[3].change,
              ],
            }]
          } else {
            return [{
              name: 'Percent Change',
              data: [],
            }]
          }
        } else {
          return [{
            name: 'Percent Change',
            data: [],
          }]
        }
      },
      reportChartOptionsMedianRent () {
        return {
          plotOptions: {
            bar: {
              distributed: true,
              dataLabels: {
                position: 'top',
              },
            },
          },
          xaxis: {
            categories: ['Zip', 'State', 'Metro'],
            labels: {
              show: false,
            },
          },
          legend: {
            horizontalAlign: 'left',
          },
          chart: {
            toolbar: {
              show: false,
            },
          },
          colors: [
            '#24cb43', '#ff7d1f', '#f5da0f',
          ],
          dataLabels: {
            offsetY: -20,
            style: {
              colors: ['#434544'],
            },
            formatter: function (val, opts) {
              return '$' + val
            },
          },
        }
      },
      reportChartOptionsCashFlow () {
        return {
          plotOptions: {
            bar: {
              distributed: true,
              dataLabels: {
                position: 'top',
              },
            },
          },
          xaxis: {
            categories: ['Zip', 'State', 'Metro'],
            labels: {
              show: false,
            },
          },
          legend: {
            horizontalAlign: 'left',
          },
          chart: {
            toolbar: {
              show: false,
            },
          },
          colors: [
            '#24cb43', '#ff7d1f', '#f5da0f',
          ],
          dataLabels: {
            offsetY: -20,
            style: {
              colors: ['#434544'],
            },
            formatter: function (val, opts) {
              return val + '%'
            },
          },
        }
      },
      reportChartOptionsGrowth () {
        return {
          plotOptions: {
            bar: {
              distributed: false,
              dataLabels: {
                position: 'top',
              },
            },
          },
          xaxis: {
            categories: ['1 Year', '3 Years', '5 Years'],
            labels: {
              show: true,
            },
          },
          legend: {
            horizontalAlign: 'left',
          },
          chart: {
            toolbar: {
              show: false,
            },
          },
          colors: [
            '#24cb43', '#ff7d1f', '#f5da0f',
          ],
          dataLabels: {
            offsetY: -20,
            style: {
              colors: ['#434544'],
            },
            formatter: function (val, opts) {
              return val + '%'
            },
          },
        }
      },
      validaterentalTrendsValues () {
        // console.log(this.rentalTrends)
        if (this.rentalTrends) {
          if (this.rentalTrends.zip && this.rentalTrends.state && this.rentalTrends.metro) {
            if (this.rentalTrends.zip.cagr1y && this.rentalTrends.zip.cagr3y && this.rentalTrends.zip.cagr5y && this.rentalTrends.zip.median_rent && this.rentalTrends.zip.cash_flow &&
              this.rentalTrends.state.cagr1y && this.rentalTrends.state.cagr3y && this.rentalTrends.state.cagr5y && this.rentalTrends.state.median_rent && this.rentalTrends.state.cash_flow &&
              this.rentalTrends.metro.cagr1y && this.rentalTrends.metro.cagr3y && this.rentalTrends.metro.cagr5y && this.rentalTrends.metro.median_rent && this.rentalTrends.metro.cash_flow) {
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
      chartMedianRent () {
        if (this.validaterentalTrendsValues) {
          return [{
            name: 'Median Rent',
            data: [
              this.rentalTrends.zip.median_rent,
              this.rentalTrends.state.median_rent,
              this.rentalTrends.metro.median_rent,
            ],
          }]
        } else {
          return [{
            name: 'Median Rent',
            data: [],
          }]
        }
      },
      chartCashFlow () {
        if (this.validaterentalTrendsValues) {
          return [{
            name: 'Cash Flow',
            data: [
              this.rentalTrends.zip.cash_flow,
              this.rentalTrends.state.cash_flow,
              this.rentalTrends.metro.cash_flow,
            ],
          }]
        } else {
          return [{
            name: 'Cash Flow',
            data: [],
          }]
        }
      },
      chartRentalGrowth () {
        if (this.validaterentalTrendsValues) {
          return [
            {
              name: 'Zip',
              data: [this.rentalTrends.zip.cagr1y, this.rentalTrends.zip.cagr3y, this.rentalTrends.zip.cagr5y],
            },
            {
              name: 'State',
              data: [this.rentalTrends.state.cagr1y, this.rentalTrends.state.cagr3y, this.rentalTrends.state.cagr5y],
            },
            {
              name: 'Metro',
              data: [this.rentalTrends.metro.cagr1y, this.rentalTrends.metro.cagr3y, this.rentalTrends.metro.cagr5y],
            },
          ]
        } else {
          return [
            {
              name: 'Zip',
              data: [],
            },
            {
              name: 'State',
              data: [],
            },
            {
              name: 'Metro',
              data: [],
            },
          ]
        }
      },
    },
    watch: {
      forecast: function () {
        // console.log('forecast changed')
        // console.log(this.forecast)
        // update dropdown selected value
        this.doResetForm()
      },
      property: function () {
        this.doInitialEstimate()
        document.getElementById('report-honey-value-after').innerHTML = '--'
      },
    },

    created () {
      // default
      // this.doDev()
    },
    mounted () {
      this.doInitialEstimate()
    },
    methods: {
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
      doCloseForm () {
        document.getElementById('report-form-overlay').classList.remove('active')
        document.getElementById('share-emails-temp').value = ''
        document.getElementById('share-emails').value = ''
        document.getElementById('share-message').value = ''
      },
      doShowBrokerageLogo () {
        const brokerageSelect = document.getElementById('brokerage-logo')
        if (brokerageSelect) {
          const selected = brokerageSelect.value
          const img = document.getElementById('logo-display')
          let logoURI = ''
          if (selected !== '--') {
            logoURI = 'https://honely-files-public.s3.amazonaws.com/report/brokerages/logo-' + selected + '.png'
          } else {
            // hide
            logoURI = 'https://honely-files-public.s3.amazonaws.com/report/logo_honely_report_default.png'
          }
          img.src = logoURI
        }
      },
      doTextConversionnHTML4 (text) {
        if (text !== null && text !== '') {
          // process HTML 4 entities conversion
          text = text.trim().replace('&Tab;', '&Tab;').replace('&NewLine;', '&NewLine;').replace('&excl;', '&excl;').replace('&quot;', '&quot;').replace('&num;', '&#35;').replace('&dollar;', '&#36;').replace('&percnt;', '&#37;')
            .replace('&amp;', '&#38;').replace('&', '&#38;').replace('&apos;', '&#39;').replace('&lpar;', '&#40;').replace('&rpar;', '&#41;').replace('&ast;', '&#42;').replace('&plus;', '&#43;').replace('&comma;', '&#44;')
            .replace('&period;', '&#46;').replace('&sol;', '&#47;').replace('/', '&#47;').replace('&lt;', '&#60;').replace('<', '&#60;').replace('&equals;', '&#61;').replace('=', '&#61;').replace('&gt;', '&#62;').replace('>', '&#62;')
            .replace('&quest;', '&#63;').replace('&commat;', '&#64;').replace('@', '&#64;').replace('&lsqb;', '&#91;').replace('[', '&#91;').replace('&bsol;', '&#92;').replace('\\', '&#92;').replace('&rsqb;', '&#93;')
            .replace(']', '&#93;').replace('&Hat;', '&#94;').replace('^', '&#94;').replace('&grave;', '&#96;').replace('`', '&#96;').replace('&lcub;', '&#123;').replace('{', '&#123;').replace('&verbar;', '&#124;').replace('|', '&#124;')
            .replace('&rcub;', '&#125;').replace('}', '&#125;').replace('&nbsp;', '&nbsp;').replace('&iexcl;', '&iexcl;').replace('¡', '&iexcl;').replace('&cent;', '&#162;').replace('¢', '&#162;').replace('&pound;', '&#163;')
            .replace('£', '&#163;').replace('&curren;', '&#164;').replace('¤', '&#164;').replace('&yen;', '&#165;').replace('¥', '&#165;').replace('&sect;', '&#167;').replace('§', '&#167;').replace('&copy;', '&#169;').replace('©', '&#169;')
            .replace('&reg;', '&#174;').replace('®', '&#174;').replace('&deg;', '&#176;').replace('°', '&#176;').replace('&acute;', '&#180;').replace('´', '&#180;').replace('&micro;', '&#181;').replace('µ', '&#181;').replace('&para;', '&#182;')
            .replace('¶', '&#182;').replace('&middot;', '&#183;').replace('·', '&#183;').replace('&iquest;', '&#191;').replace('¿', '&#191;').replace('&Agrave;', '&#192;').replace('À', '&#192;').replace('&Aacute;', '&#193;').replace('Á', '&#193;')
            .replace('&Acirc;', '&#194;').replace('Â', '&#194;').replace('&Atilde;', '&#195;').replace('Ã', '&#195;').replace('&Auml;', '&#196;').replace('Ä', '&#196;').replace('&Aring;', '&#197;').replace('Å', '&#197;').replace('&AElig;', '&#198')
            .replace('Æ', '&#198').replace('&Ccedil;', '&#199;').replace('Ç', '&#199;').replace('&Egrave;', '&#200;').replace('È', '&#200;').replace('&Eacute;', '&#201;').replace('É', '&#201;').replace('&Ecirc;', '&#202;').replace('Ê', '&#202;')
            .replace('Ë', '&#203;').replace('&Euml;', '&#203;').replace('Ì', '&#204;').replace('&Igrave;', '&#204;').replace('Í', '&#205;').replace('&Iacute;', '&#205;').replace('Î', '&#206;').replace('&Icirc;', '&#206;').replace('Ï', '&#207;').replace('&Iuml;', '&#207;')
            .replace('Ð', '&#208;').replace('&ETH;', '&#208;').replace('Ñ', '&#209;').replace('&Ntilde;', '&#209;').replace('Ò', '&#210;').replace('&Ograve;', '&#210;').replace('Ó', '&#211;').replace('&Oacute;', '&#211;').replace('Ô', '&#212;').replace('&Ocirc;', '&#212;')
            .replace('Õ', '&#213;').replace('&Otilde;', '&#213;').replace('Ö', '&#214;').replace('&Ouml;', '&#214;').replace('×', '&#215;').replace('&times;', '&#215;').replace('Ø', '&#216;').replace('&Oslash;', '&#216;').replace('Ù', '&#217;').replace('&Ugrave;', '&#217;')
            .replace('Ú', '&#218;').replace('&Uacute;', '&#218;').replace('Û', '&#219;').replace('&Ucirc;', '&#219;').replace('Ü', '&#220;').replace('&Uuml;', '&#220;').replace('Ý', '&#221;').replace('&Yacute;', '&#221;').replace('Þ', '&#222;').replace('&THORN;', '&#222;')
            .replace('ß', '&#223;').replace('&szlig;', '&#223;').replace('à', '&#224;').replace('&agrave;', '&#224;').replace('á', '&#225;').replace('&aacute;', '&#225;').replace('â', '&#226;').replace('&acirc;', '&#226;').replace('ã', '&#227;').replace('&atilde;', '&#227;')
            .replace('ä', '&#228;').replace('&auml;', '&#228;').replace('å', '&#229;').replace('&aring;', '&#229;').replace('æ', '&#230;').replace('&aelig;', '&#230;').replace('ç', '&#231;').replace('&ccedil;', '&#231;').replace('è', '&#232;').replace('&egrave;', '&#232;')
            .replace('é', '&#233;').replace('&eacute;', '&#233;').replace('ê', '&#234;').replace('&ecirc;', '&#234;').replace('ë', '&#235;').replace('&euml;', '&#235;').replace('ì', '&#236;').replace('&igrave;', '&#236;').replace('í', '&#237;').replace('&iacute;', '&#237;')
            .replace('î', '&#238;').replace('&icirc;', '&#238;').replace('ï', '&#239;').replace('&iuml;', '&#239;').replace('ð', '&#240;').replace('&eth;', '&#240;').replace('ñ', '&#241;').replace('&ntilde;', '&#241;').replace('ò', '&#242;').replace('&ograve;', '&#242;')
            .replace('ó', '&#243;').replace('&oacute;', '&#243;').replace('ô', '&#244;').replace('&ocirc;', '&#244;').replace('õ', '&#245;').replace('&otilde;', '&#245;').replace('ö', '&#246;').replace('&ouml;', '&#246;').replace('÷', '&#247;').replace('&divide; &div;', '&#247;')
            .replace('ø', '&#248;').replace('&oslash;', '&#248;').replace('ù', '&#249;').replace('&ugrave;', '&#249;').replace('ú', '&#250;').replace('&uacute;', '&#250;').replace('û', '&#251;').replace('&ucirc;', '&#251;').replace('ü', '&#252;').replace('&uuml;', '&#252;')
            .replace('ý', '&#253;').replace('&yacute;', '&#253;').replace('þ', '&#254;').replace('&thorn;', '&#254;').replace('ÿ', '&#255;').replace('&yuml;', '&#255;').replace('Ā', '&#256;').replace('&Amacr;', '&#256;').replace('ā', '&#257;').replace('&amacr;', '&#257;')
            .replace('Ă', '&#258;').replace('&Abreve;', '&#258;').replace('ă', '&#259;').replace('&abreve;', '&#259;').replace('Ą', '&#260;').replace('&Aogon;', '&#260;').replace('ą', '&#261;').replace('&aogon;', '&#261;').replace('Ć', '&#262;').replace('&Cacute;', '&#262;')
            .replace('ć', '&#263;').replace('&cacute;', '&#263;').replace('Ĉ', '&#264;').replace('&Ccirc;', '&#264;').replace('ĉ', '&#265;').replace('&ccirc;', '&#265;').replace('Ċ', '&#266;').replace('&Cdot;', '&#266;').replace('ċ', '&#267;').replace('&cdot;', '&#267;')
            .replace('Č', '&#268;').replace('&Ccaron;', '&#268;').replace('č', '&#269;').replace('&ccaron;', '&#269;').replace('Ď', '&#270;').replace('&Dcaron;', '&#270;').replace('ď', '&#271;').replace('&dcaron;', '&#271;').replace('Đ', '&#272;').replace('&Dstrok;', '&#272;')
            .replace('đ', '&#273;').replace('&dstrok;', '&#273;').replace('Ē', '&#274;').replace('&Emacr;', '&#274;').replace('ē', '&#275;').replace('&emacr;', '&#275;').replace('Ė', '&#278;').replace('&Edot;', '&#278;').replace('ė', '&#279;').replace('&edot;', '&#279;')
            .replace('Ę', '&#280;').replace('&Eogon;', '&#280;').replace('ę', '&#281;').replace('&eogon;', '&#281;').replace('Ě', '&#282;').replace('&Ecaron;', '&#282;').replace('ě', '&#283;').replace('&ecaron;', '&#283;').replace('Ĝ', '&#284;').replace('&Gcirc;', '&#284;')
            .replace('ĝ', '&#285;').replace('&gcirc;', '&#285;').replace('Ğ', '&#286;').replace('&Gbreve;', '&#286;').replace('ğ', '&#287;').replace('&gbreve;', '&#287;').replace('Ġ', '&#288;').replace('&Gdot;', '&#288;').replace('ġ', '&#289;').replace('&gdot;', '&#289;')
            .replace('Ģ', '&#290;').replace('&Gcedil;', '&#290;').replace('Ĥ', '&#292;').replace('&Hcirc;', '&#292;').replace('ĥ', '&#293;').replace('&hcirc;', '&#293;').replace('Ħ', '&#294;').replace('&Hstrok;', '&#294;').replace('ħ', '&#295;').replace('&hstrok;', '&#295;')
            .replace('Ĩ', '&#296;').replace('&Itilde;', '&#296;').replace('ĩ', '&#297;').replace('&itilde;', '&#297;').replace('Ī', '&#298;').replace('&Imacr;', '&#298;').replace('ī', '&#299;').replace('&imacr;', '&#299;').replace('Į', '&#302;').replace('&Iogon;', '&#302;')
            .replace('į', '&#303;').replace('&iogon;', '&#303;').replace('İ', '&#304;').replace('&Idot;', '&#304;').replace('ı', '&#305;').replace('&imath; &inodot;', '&#305;').replace('Ĳ', '&#306;').replace('&IJlig;', '&#306;').replace('ĳ', '&#307;').replace('&ijlig;', '&#307;')
            .replace('Ĵ', '&#308;').replace('&Jcirc;', '&#308;').replace('ĵ', '&#309;').replace('&jcirc;', '&#309;').replace('Ķ', '&#310;').replace('&Kcedil;', '&#310;').replace('ķ', '&#311;').replace('&kcedil;', '&#311;').replace('ĸ', '&#312;').replace('&kgreen;', '&#312;')
            .replace('Ĺ', '&#313;').replace('&Lacute;', '&#313;').replace('ĺ', '&#314;').replace('&lacute;', '&#314;').replace('Ļ', '&#315;').replace('&Lcedil;', '&#315;').replace('ļ', '&#316;').replace('&lcedil;', '&#316;').replace('Ľ', '&#317;').replace('&Lcaron;', '&#317;')
            .replace('ľ', '&#318;').replace('&lcaron;', '&#318;').replace('Ŀ', '&#319;').replace('&Lmidot;', '&#319;').replace('ŀ', '&#320;').replace('&lmidot;', '&#320;').replace('Ł', '&#321;').replace('&Lstrok;', '&#321;').replace('ł', '&#322;').replace('&lstrok;', '&#322;')
            .replace('Ń', '&#323;').replace('&Nacute;', '&#323;').replace('ń', '&#324;').replace('&nacute;', '&#324;').replace('Ņ', '&#325;').replace('&Ncedil;', '&#325;').replace('ņ', '&#326;').replace('&ncedil;', '&#326;').replace('Ň', '&#327;').replace('&Ncaron;', '&#327;')
            .replace('ň', '&#328;').replace('&ncaron;', '&#328;').replace('ŉ', '&#329;').replace('&napos;', '&#329;').replace('Ŋ', '&#330;').replace('&ENG;', '&#330;').replace('ŋ', '&#331;').replace('&eng;', '&#331;').replace('Ō', '&#332;').replace('&Omacr;', '&#332;')
            .replace('ō', '&#333;').replace('&omacr;', '&#333;').replace('Ő', '&#336;').replace('&Odblac;', '&#336;').replace('ő', '&#337;').replace('&odblac;', '&#337;').replace('Œ', '&#338;').replace('&OElig;', '&#338;').replace('œ', '&#339;').replace('&oelig;', '&#339;')
            .replace('Ŕ', '&#340;').replace('&Racute;', '&#340;').replace('ŕ', '&#341;').replace('&racute;', '&#341;').replace('Ŗ', '&#342;').replace('&Rcedil;', '&#342;').replace('ŗ', '&#343;').replace('&rcedil;', '&#343;').replace('Ř', '&#344;').replace('&Rcaron;', '&#344;')
            .replace('ř', '&#345;').replace('&rcaron;', '&#345;').replace('Ś', '&#346;').replace('&Sacute;', '&#346;').replace('ś', '&#347;').replace('&sacute;', '&#347;').replace('Ŝ', '&#348;').replace('&Scirc;', '&#348;').replace('ŝ', '&#349;').replace('&scirc;', '&#349;')
            .replace('Ş', '&#350;').replace('&Scedil;', '&#350;').replace('ş', '&#351;').replace('&scedil;', '&#351;').replace('Š', '&#352;').replace('&Scaron;', '&#352;').replace('š', '&#353;').replace('&scaron;', '&#353;').replace('Ţ', '&#354;').replace('&Tcedil;', '&#354;')
            .replace('ţ', '&#355;').replace('&tcedil;', '&#355;').replace('Ť', '&#356;').replace('&Tcaron;', '&#356;').replace('ť', '&#357;').replace('&tcaron;', '&#357;').replace('Ŧ', '&#358;').replace('&Tstrok;', '&#358;').replace('ŧ', '&#359;').replace('&tstrok;', '&#359;')
            .replace('Ũ', '&#360;').replace('&Utilde;', '&#360;').replace('ũ', '&#361;').replace('&utilde;', '&#361;').replace('Ū', '&#362;').replace('&Umacr;', '&#362;').replace('ū', '&#363;').replace('&umacr;', '&#363;').replace('Ŭ', '&#364;').replace('&Ubreve;', '&#364;')
            .replace('ŭ', '&#365;').replace('&ubreve;', '&#365;').replace('Ů', '&#366;').replace('&Uring;', '&#366;').replace('ů', '&#367;').replace('&uring;', '&#367;').replace('Ű', '&#368;').replace('&Udblac;', '&#368;').replace('ű', '&#369;').replace('&udblac;', '&#369;')
            .replace('Ų', '&#370;').replace('&Uogon;', '&#370;').replace('ų', '&#371;').replace('&uogon;', '&#371;').replace('Ŵ', '&#372;').replace('&Wcirc;', '&#372;').replace('ŵ', '&#373;').replace('&wcirc;', '&#373;').replace('Ŷ', '&#374;').replace('&Ycirc;', '&#374;')
            .replace('ŷ', '&#375;').replace('&ycirc;', '&#375;').replace('Ÿ', '&#376;').replace('&Yuml;', '&#376;').replace('Ź', '&#377;').replace('&Zacute;', '&#377;').replace('ź', '&#378;').replace('&zacute;', '&#378;').replace('Ż', '&#379;').replace('&Zdot;', '&#379;')
            .replace('ż', '&#380;').replace('&zdot;', '&#380;').replace('Ž', '&#381;').replace('&Zcaron;', '&#381;').replace('ž', '&#382;').replace('&zcaron;', '&#382;').replace('ƒ', '&#402;').replace('&fnof;', '&#402;').replace('Ƶ', '&#437;').replace('&imped;', '&#437;')
            .replace('ǵ', '&#501;').replace('&gacute;', '&#501;').replace('&lsquo;', '&#8216;').replace('‘', '&#8216;').replace('&rsquo;', '&#8217;').replace('’', '&#8217;').replace('&ldquo;', '&#8220;').replace('“', '&#8220;').replace('&rdquo;', '&#8221;').replace('”', '&#8221;')

          // console.log(text)
          return text
        } else {
          return ''
        }
      },
      doGetValueForOptionList (dropdown, arr, value) {
        let id = '--'
        if (arr && value) {
          for (let i = 0; i < arr.length; i++) {
            if (arr[i].name === value) {
              id = arr[i].name
              break
            }
          }
        }
        // console.log(dropdown + ' | ' + id)
        document.getElementById(dropdown).value = id
      },
      doResetForm () {
        if (document.getElementById('share-emails-temp')) {
          document.getElementById('share-emails-temp').value = ''
        }
        if (document.getElementById('share-emails')) {
          document.getElementById('share-emails').value = ''
        }
        if (document.getElementById('share-message')) {
          document.getElementById('share-message').value = ''
        }

        if (this.property && this.property.structure) {
          this.doGetValueForOptionList('stories', this.getStoriesCodes, this.property.structure.stories)
          this.doGetValueForOptionList('other-rooms', this.getOtherRoomCodes, this.property.structure.other_rooms)
          this.doGetValueForOptionList('ac-type', this.getAirConditionCodes, this.property.structure.air_conditioning_type)
          this.doGetValueForOptionList('heat-type', this.getHeatingTypeCodes, this.property.structure.heating_type)
          this.doGetValueForOptionList('heat-fuel-type', this.getHeatingFuelCodes, this.property.structure.heating_fuel_type)
          this.doGetValueForOptionList('pool', this.getPoolCodes, this.property.structure.pool_type)
          this.doGetValueForOptionList('amenities', this.getAmenitiesCodes, this.property.structure.amenities)
          this.doGetValueForOptionList('condition', this.getBuildingConditionCodes, this.property.structure.condition)
          this.doGetValueForOptionList('architecture', this.getStyleCodes, this.property.structure.architecture_type)
          this.doGetValueForOptionList('construction', this.getConstructionTypeCodes, this.property.structure.construction_type)
          this.doGetValueForOptionList('basement-type', this.getBasementCodes, this.property.structure.basement_type)
          this.doGetValueForOptionList('roof-style', this.getRoofTypeCodes, this.property.structure.roof_style_type)
          this.doGetValueForOptionList('roof-material', this.getRoofCoverCodes, this.property.structure.roof_material_type)
          this.doGetValueForOptionList('exterior-walls', this.getExteriorWallsCodes, this.property.structure.exterior_wall_type)
          this.doGetValueForOptionList('interior-walls', this.getInteriorWallsCodes, this.property.structure.interior_wall_type)
          this.doGetValueForOptionList('flooring', this.getFloorCoverCodes, this.property.structure.flooring_types)
          // this.doGetValueForOptionList('garage-type', this.getGarageCodes, this.property.structure.garage_type)
          this.doGetValueForOptionList('water-type', this.getWaterCodes, this.property.structure.water_type)
          this.doGetValueForOptionList('sewer-type', this.getSewerCodes, this.property.structure.sewer_type)
        }
      },
      doReportTextColorCode (num, before, after) {
        if (num) {
          if (num != null && num !== '') {
            if (!isNaN(num)) {
              const temp = parseFloat(num)
              if (temp >= 0) {
                if (before != null && after === null) {
                  return '<span style="color: #07871c;">' + before + num + '</span>'
                } else if (before === null && after !== null) {
                  return '<span style="color: #07871c;">' + num + after + '</span>'
                } else {
                  return '<span style="color: #07871c;">' + num + '</span>'
                }
              } else {
                if (before !== null && after === null) {
                  return '<span style="color: red;">' + before + num + '</span>'
                } else if (before === null && after !== null) {
                  return '<span style="color: red;">' + num + after + '</span>'
                } else {
                  return '<span style="color: red;">' + num + '</span>'
                }
              }
            } else {
              return num
            }
          } else {
            return num
          }
        } else {
          return num
        }
      },
      doShareReport () {
        let shareEmailsTemp = document.getElementById('share-emails-temp').value
        let shareEmails = document.getElementById('share-emails').value

        if (shareEmailsTemp) {
          shareEmailsTemp = shareEmailsTemp.trim().replace(' ', '')
        }

        if (shareEmails) {
          shareEmails = shareEmails.trim().replace(' ', '')
          if (shareEmails === '') {
            document.getElementById('share-emails-temp').classList.add('error-input')
            alert('Please enter at least one email address')
          } else {
            document.getElementById('share-emails-temp').classList.remove('error-input')
            this.doGenerateReport(true)
          }
        } else {
          if (shareEmailsTemp != null && shareEmailsTemp !== '') {
            const emails = shareEmailsTemp.split(',')
            let valid = true
            for (let i = 0; i < emails.length; i++) {
              if (!this.doValidateEmail(emails[i])) {
                console.log('not valid')
                valid = false
                document.getElementById('share-emails-temp').classList.remove('error-input')
                alert('Please enter valid email addresses')
                break
              }
            }
            if (valid) {
              shareEmails = shareEmailsTemp
              document.getElementById('share-emails').value = shareEmailsTemp
              document.getElementById('share-emails-temp').classList.remove('error-input')
              this.doGenerateReport(true)
            }
          } else {
            document.getElementById('share-emails-temp').classList.add('error-input')
            alert('Please enter at least one email address')
          }
          // document.getElementById('share-emails-temp').classList.add('error-input')
          // alert('Please enter at least one email address')
        }
      },
      doDownloadReport () {
        this.doGenerateReport(false)
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
      displayLoaderEstimate () {
        document.getElementById('loader-overlay-report').classList.add('active')
      },
      hideLoaderEstimate () {
        document.getElementById('loader-overlay-report').classList.remove('active')
      },
      doReportUpdate (input) {
        const changed = document.getElementById(input)

        if (changed) {
          if (!isNaN(changed.value)) {
            this.doUpdateHonelyEstimate()
          }
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
          if (yearBuilt !== null || yearBuilt !== '') {
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
            // console.log(response)
            return response.json()
          }).then(function (data) {
            // console.log(data)
            if (data) {
              if (data.current_value) {
                self.initialRate = data.current_value
              }
            }
          }).catch((err) => {
            console.log('[ERROR] Honely calculator API failed =>', err)
          })
        }
      },
      doUpdateHonelyEstimate () {
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
          const sqft = document.getElementById('sqft').value
          const numBeds = document.getElementById('num-beds').value
          const numBaths = document.getElementById('num-baths').value
          let numPartialBaths = document.getElementById('num-partial-bath').value
          let roomCount = document.getElementById('room-count').value
          let parkingSpaces = document.getElementById('parking-spaces').value
          let plumbingCount = document.getElementById('plumbing-count').value

          // check numerical inputs
          if (!this.validateNumericalInput(sqft)) {
            document.getElementById('sqft').classList.add('error-input')
            alert('Please enter a valid number')
            return
          } else {
            document.getElementById('sqft').classList.remove('error-input')
          }
          if (!this.validateNumericalInput(numBeds)) {
            document.getElementById('num-beds').classList.add('error-input')
            alert('Please enter a valid number')
            return
          } else {
            document.getElementById('num-beds').classList.remove('error-input')
          }
          if (!this.validateNumericalInput(numBaths)) {
            document.getElementById('num-baths').classList.add('error-input')
            alert('Please enter a valid number')
            return
          } else {
            document.getElementById('num-baths').classList.remove('error-input')
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
      doGetForecastResult (data) {
        if (data) {
          // console.log('inital: ' + this.initialRate)
          if (this.forecast.property_forecast.appraisal && data.current_value) {
            if (parseFloat(data.current_value) !== parseFloat(this.initialRate)) {
              return parseFloat(this.forecast.property_forecast.appraisal) * (parseFloat(data.current_value) / parseFloat(this.initialRate))
            } else {
              return parseFloat(this.forecast.property_forecast.appraisal)
            }
          } else {
            return null
          }
        } else {
          return null
        }
      },
      doUpdateCalculatorResult (data) {
        if (data) {
          document.getElementById('report-honey-value-after').innerHTML = this.formatCurrency(this.doGetForecastResult(data))
        } else {
          document.getElementById('report-honey-value-after').innerHTML = '--'
        }
      },
      doValidateEmail (email) {
        // eslint-disable-next-line
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
          return true
        } else {
          return false
        }
      },
      doGenerateReport (shareMode) {
        // constants
        const chartExportOptions = {
          width: '300',
        }
        const chartExportOptionsLarge = {
          width: '400',
        }

        const timeFrame3lvls = ['current', '3 months', '1 year', '2 years', '3 years']
        const timeFrame4lvls = ['present', '3 months', '1 year', '2 years', '3 years']

        let chart3monthImg = null
        let chart1yearImg = null
        let chart2yearsImg = null
        let chart3yearsImg = null
        let chartMedianRentImg = null
        let chartCashFlowImg = null
        let chartGrowthImg = null
        let customLogoData = null

        // read custom logo file
        const customLogoFile = document.getElementById('agent-logo').files[0]

        Promise.all([
          // get chart image data
          this.$refs.chart3month.chart.dataURI(chartExportOptions).then(uri => {
            // console.log(uri)
            chart3monthImg = uri.imgURI
          }),
          this.$refs.chart1year.chart.dataURI(chartExportOptions).then(uri => {
            // console.log(uri)
            chart1yearImg = uri.imgURI
          }),
          this.$refs.chart2years.chart.dataURI(chartExportOptions).then(uri => {
            // console.log(uri)
            chart2yearsImg = uri.imgURI
          }),
          this.$refs.chart3years.chart.dataURI(chartExportOptions).then(uri => {
            // console.log(uri)
            chart3yearsImg = uri.imgURI
          }),
          this.$refs.chartMedianRent.chart.dataURI(chartExportOptions).then(uri => {
            // console.log(uri)
            chartMedianRentImg = uri.imgURI
          }),
          this.$refs.chartCashFlow.chart.dataURI(chartExportOptions).then(uri => {
            // console.log(uri)
            chartCashFlowImg = uri.imgURI
          }),
          this.$refs.chartRentalGrowth.chart.dataURI(chartExportOptionsLarge).then(uri => {
            // console.log(uri)
            chartGrowthImg = uri.imgURI
          }),
          this.readFile(customLogoFile).then(data => {
            customLogoData = data
          }),
        ]).then(data => {
          // console.log(chart3monthImg)
          // console.log(chart1yearImg)
          // console.log(chart2yearsImg)
          // console.log(chart3yearsImg)
          // console.log(customLogoData)

          // get property image
          const propertyImg = 'https://maps.googleapis.com/maps/api/streetview?size=600x400&location=' + this.property.address.latitude + '' + ',' + this.property.address.longitude + '+&fov=90&source=outdoor&key=' + this.$mapsKey

          // get all the inputs from user
          const agentName = document.getElementById('agent-name').value
          const agentEmail = document.getElementById('agent-email').value
          const agentPhone = document.getElementById('agent-phone').value
          let shareEmails = document.getElementById('share-emails').value
          let shareMessage = document.getElementById('share-message').value
          let appraisal = this.formatCurrency(this.property.valuation.appraisal)
          const appraisalAfter = document.getElementById('report-honey-value-after').innerText
          let apn = this.getAPN
          let address1 = this.getAddress1
          let address2 = this.getAddress2
          let city = this.getCity
          let state = this.getState
          let zip = this.getZip
          let county = this.getCounty
          let yearBuilt = this.getYearBuilt
          const stories = document.getElementById('stories').value
          const sqft = document.getElementById('sqft').value
          const numBeds = document.getElementById('num-beds').value
          const numBaths = document.getElementById('num-baths').value
          const numPartialBaths = document.getElementById('num-partial-bath').value
          const roomCount = document.getElementById('room-count').value
          let otherRooms = document.getElementById('other-rooms').value
          let acType = document.getElementById('ac-type').value
          let parkingSpaces = document.getElementById('parking-spaces').value
          let plumbingCount = document.getElementById('plumbing-count').value
          let parkingType = document.getElementById('parking-type').value
          let heatType = document.getElementById('heat-type').value
          let heatFuelType = document.getElementById('heat-fuel-type').value
          let pool = document.getElementById('pool').value
          let amenities = document.getElementById('amenities').value
          let condition = document.getElementById('condition').value
          let architecture = document.getElementById('architecture').value
          let construction = document.getElementById('construction').value
          let basementType = document.getElementById('basement-type').value
          let roofStyle = document.getElementById('roof-style').value
          let roofMaterial = document.getElementById('roof-material').value
          let exteriorWalls = document.getElementById('exterior-walls').value
          let interiorWalls = document.getElementById('interior-walls').value
          let flooring = document.getElementById('flooring').value
          let waterType = document.getElementById('water-type').value
          let sewerType = document.getElementById('sewer-type').value
          const brokerageLogo = document.getElementById('brokerage-logo').value
          let saleDate = document.getElementById('sale-date').value
          let salePrice = document.getElementById('sale-price').value

          // update data to default -----------------------------
          if (appraisalAfter && appraisalAfter !== '' && appraisalAfter !== '--') {
            appraisal = appraisalAfter
          }
          // check custom logo, if 0, use default
          if (customLogoData === 0) {
            if (brokerageLogo !== '--') {
              customLogoData = 'https://honely-files-public.s3.amazonaws.com/report/brokerages/logo-' + brokerageLogo + '.png'
            } else {
              customLogoData = 'https://honely-files-public.s3.amazonaws.com/report/logo_honely_report_default.png'
            }
          }
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
            zip = ' '
          }
          if (county === null || county === '') {
            county = ' '
          }
          if (yearBuilt === null || yearBuilt === '') {
            yearBuilt = ' '
          } else {
            yearBuilt = yearBuilt.toString()
          }
          if (parkingSpaces === '') {
            parkingSpaces = ' '
          }
          if (plumbingCount === '') {
            plumbingCount = ' '
          }
          // update drop-down list default value
          if (otherRooms === '--') {
            otherRooms = ' '
          }
          if (acType === '--') {
            acType = ' '
          }
          if (parkingType && parkingType !== '') {
            parkingType = this.doTextConversionnHTML4(parkingType)
          } else {
            parkingType = ' '
          }
          if (heatType === '--') {
            heatType = ' '
          }
          if (heatFuelType === '--') {
            heatFuelType = ' '
          }
          if (pool === '--') {
            pool = ' '
          } else {
            pool = this.doTextConversionnHTML4(pool)
          }
          if (amenities === '--') {
            amenities = ' '
          }
          if (condition === '--') {
            condition = ' '
          }
          if (architecture === '--') {
            architecture = ' '
          }
          if (construction === '--') {
            construction = ' '
          }
          if (basementType === '--') {
            basementType = ' '
          }
          if (roofStyle === '--') {
            roofStyle = ' '
          }
          if (roofMaterial === '--') {
            roofMaterial = ' '
          } else {
            roofMaterial = this.doTextConversionnHTML4(roofMaterial)
          }
          if (exteriorWalls === '--') {
            exteriorWalls = ' '
          }
          if (interiorWalls === '--') {
            interiorWalls = ' '
          }
          if (flooring === '--') {
            flooring = ' '
          }
          if (waterType === '--') {
            waterType = ' '
          }
          if (sewerType === '--') {
            sewerType = ' '
          }

          // get additional data ---------------------------------
          // schools
          const schoolList = []
          if (this.schools) {
            if (this.schools.length > 3) {
              for (let i = 0; i < 3; i++) {
                let rating = ''
                if (this.schools[i].rating !== null) {
                  rating = this.schools[i].rating + '/10'
                } else {
                  rating = 'N/R'
                }
                schoolList.push([rating, this.schools[i].name, this.schools[i].type, this.schools[i].level_short, this.schools[i].distance + this.schools[i].distanceText])
              }
            } else {
              for (let i = 0; i < this.schools.length; i++) {
                let rating = ''
                if (this.schools[i].rating !== null) {
                  rating = this.schools[i].rating + '/10'
                } else {
                  rating = 'N/R'
                }
                schoolList.push([rating, this.schools[i].name, this.schools[i].type, this.schools[i].level_short, this.schools[i].distance + this.schools[i].distanceText])
              }
            }
          }

          // walkscore
          const walkscoreList = []
          if (this.walkscore) {
            if (this.walkscore.walkscore) {
              walkscoreList.push([this.walkscore.walkscore.toString(), 'Walk Score', this.walkscore.description])
            }
            if (this.walkscore.bike) {
              walkscoreList.push([this.walkscore.bike.score.toString(), 'Bike Score', this.walkscore.bike.description])
            }
            if (this.walkscore.transit) {
              walkscoreList.push([this.walkscore.transit.score.toString(), 'Transit Score', this.walkscore.transit.description])
            }
          }

          // honely forecast
          const honelyForecast = []
          if (this.forecast && this.forecast.property_forecast.percentage_change_forecasts && this.forecast.property_forecast.value_change_forecasts) {
            let change = ''
            for (let i = 1; i < this.forecast.property_forecast.percentage_change_forecasts.length; i++) {
              if (this.forecast.property_forecast.percentage_change_forecasts[i].change >= 0) {
                change = '<span style="color: #07871c;">increase ' + this.forecast.property_forecast.percentage_change_forecasts[i].change + '%</span>'
              } else {
                change = '<span style="color: red;">decrease ' + this.forecast.property_forecast.percentage_change_forecasts[i].change + '%</span>'
              }
              honelyForecast.push([timeFrame4lvls[i], change, this.formatCurrency(this.forecast.property_forecast.value_change_forecasts[i].change)])
            }
          }

          // home value change zip
          const homeValueChangeZip = []
          if (this.forecast && this.forecast.neighborhood.percentage_change_forecasts) {
            let change = ''
            for (let i = 1; i < this.forecast.neighborhood.percentage_change_forecasts.length; i++) {
              if (this.forecast.neighborhood.percentage_change_forecasts[i].change >= 0) {
                change = '<span style="color: #07871c;">increase ' + this.forecast.neighborhood.percentage_change_forecasts[i].change + '%</span>'
              } else {
                change = '<span style="color: red;">decrease ' + this.forecast.neighborhood.percentage_change_forecasts[i].change + '%</span>'
              }
              homeValueChangeZip.push([timeFrame3lvls[i], change])
            }
          }

          // migration trends
          const migrationTrends = []
          if (this.forecast && this.forecast.moving_trends && this.forecast.moving_trends.move_in_percentage_change_forecast && this.forecast.moving_trends.move_out_percentage_change_forecast && this.forecast.moving_trends.net_in && this.forecast.moving_trends.state_rankings && this.forecast.moving_trends.country_rankings) {
            let inRateChange = ''
            let outRateChange = ''
            let migrationChange = ''

            for (let i = 0; i < this.forecast.moving_trends.move_in_percentage_change_forecast.length; i++) {
              if (this.forecast.moving_trends.move_in_percentage_change_forecast[i].change >= 0) {
                inRateChange = '<span style="color: #07871c;">' + this.forecast.moving_trends.move_in_percentage_change_forecast[i].change + '%</span>'
              } else {
                inRateChange = '<span style="color: red;">' + this.forecast.moving_trends.move_in_percentage_change_forecast[i].change + '%</span>'
              }
              if (this.forecast.moving_trends.move_out_percentage_change_forecast[i].change >= 0) {
                outRateChange = '<span style="color: #07871c;">' + this.forecast.moving_trends.move_out_percentage_change_forecast[i].change + '%</span>'
              } else {
                outRateChange = '<span style="color: red;">' + this.forecast.moving_trends.move_out_percentage_change_forecast[i].change + '%</span>'
              }
              if (this.forecast.moving_trends.net_in[i].change >= 0) {
                migrationChange = '<span style="color: #07871c;">' + this.forecast.moving_trends.net_in[i].change + '%</span>'
              } else {
                migrationChange = '<span style="color: red;">' + this.forecast.moving_trends.net_in[i].change + '%</span>'
              }
              migrationTrends.push([timeFrame4lvls[i], inRateChange, outRateChange, migrationChange, '#' + this.forecast.moving_trends.state_rankings[i].rank, '#' + this.forecast.moving_trends.country_rankings[i].rank])
            }
          }

          // comparsion to zip
          let propertyValueForecast = ''
          if (this.forecast && this.forecast.property_forecast.property_valued_compared_to_zipcode) {
            if (this.forecast.property_forecast.property_valued_compared_to_zipcode >= 0) {
              propertyValueForecast = '<span style="color: #07871c;">' + this.forecast.property_forecast.property_valued_compared_to_zipcode + '% higher</span>'
            } else {
              propertyValueForecast = '<span style="color: red;">' + this.forecast.property_forecast.property_valued_compared_to_zipcode + '% lower</span>'
            }
          }

          // check property status, only show list price if active
          // console.log(this.property)
          let listPrice = ' '
          if (this.property && this.property.status && this.property.status !== null) {
            if (this.property.status.toLowerCase() === 'active' || this.property.status.toLowerCase() === 'for sale') {
              if (this.property.valuation.list_price && this.property.valuation.list_price !== null) {
                listPrice = '<p style="font-size: 16px;">List Price: ' + this.formatCurrency(this.property.valuation.list_price) + '</p>'
              }
            }
          }
          // console.log(listPrice)
          // sale history
          if (saleDate === '') {
            saleDate = ' '
          }

          if (salePrice === '') {
            salePrice = ' '
          }

          /* if (this.property.sale_history && this.property.sale_history.length > 0) {
            saleDate = this.property.sale_history[0].date
            salePrice = this.formatCurrency(this.property.sale_history[0].price)
          } */

          // get rental trend data
          const rentalTrendss = []
          // console.log(this.rentalTrends)

          if (this.rentalTrends) {
            // do zip codes
            if (this.rentalTrends.zip) {
              let medianRent = this.rentalTrends.zip.median_rent
              let population = this.rentalTrends.zip.population
              let cashFlow = this.rentalTrends.zip.cash_flow
              let cagr1y = this.rentalTrends.zip.cagr1y
              let cagr3y = this.rentalTrends.zip.cagr3y
              let cagr5y = this.rentalTrends.zip.cagr5y

              if (medianRent && medianRent != null) {
                medianRent = this.formatCurrency(medianRent)
              } else {
                medianRent = 'N/A'
              }
              if (population && population != null) {
                population = this.formatNumberWithComma(population)
              } else {
                population = 'N/A'
              }
              if (cashFlow && cashFlow != null) {
                cashFlow = this.doReportTextColorCode(cashFlow, null, '%')
              } else {
                cashFlow = 'N/A'
              }
              if (cagr1y && cagr1y != null) {
                cagr1y = this.doReportTextColorCode(cagr1y, null, '%')
              } else {
                cagr1y = 'N/A'
              }
              if (cagr3y && cagr3y != null) {
                cagr3y = this.doReportTextColorCode(cagr3y, null, '%')
              } else {
                cagr3y = 'N/A'
              }
              if (cagr5y && cagr5y != null) {
                cagr5y = this.doReportTextColorCode(cagr5y, null, '%')
              } else {
                cagr5y = 'N/A'
              }

              rentalTrendss.push(
                [
                  'Zip Code',
                  medianRent,
                  population,
                  cashFlow,
                  cagr1y,
                  cagr3y,
                  cagr5y,
                ],
              )
            } else {
              rentalTrendss.push(
                [
                  'Zip Code',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                ],
              )
            }

            // do state data
            if (this.rentalTrends.state) {
              let medianRent = this.rentalTrends.state.median_rent
              let population = this.rentalTrends.state.population
              let cashFlow = this.rentalTrends.state.cash_flow
              let cagr1y = this.rentalTrends.state.cagr1y
              let cagr3y = this.rentalTrends.state.cagr3y
              let cagr5y = this.rentalTrends.state.cagr5y

              if (medianRent && medianRent != null) {
                medianRent = this.formatCurrency(medianRent)
              } else {
                medianRent = 'N/A'
              }
              if (population && population != null) {
                population = this.formatNumberWithComma(population)
              } else {
                population = 'N/A'
              }
              if (cashFlow && cashFlow != null) {
                cashFlow = this.doReportTextColorCode(cashFlow, null, '%')
              } else {
                cashFlow = 'N/A'
              }
              if (cagr1y && cagr1y != null) {
                cagr1y = this.doReportTextColorCode(cagr1y, null, '%')
              } else {
                cagr1y = 'N/A'
              }
              if (cagr3y && cagr3y != null) {
                cagr3y = this.doReportTextColorCode(cagr3y, null, '%')
              } else {
                cagr3y = 'N/A'
              }
              if (cagr5y && cagr5y != null) {
                cagr5y = this.doReportTextColorCode(cagr5y, null, '%')
              } else {
                cagr5y = 'N/A'
              }

              rentalTrendss.push(
                [
                  'State',
                  medianRent,
                  population,
                  cashFlow,
                  cagr1y,
                  cagr3y,
                  cagr5y,
                ],
              )
            } else {
              rentalTrendss.push(
                [
                  'State',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                ],
              )
            }

            // do metro data
            if (this.rentalTrends.metro) {
              let medianRent = this.rentalTrends.metro.median_rent
              let population = this.rentalTrends.metro.population
              let cashFlow = this.rentalTrends.metro.cash_flow
              let cagr1y = this.rentalTrends.metro.cagr1y
              let cagr3y = this.rentalTrends.metro.cagr3y
              let cagr5y = this.rentalTrends.metro.cagr5y

              if (medianRent && medianRent != null) {
                medianRent = this.formatCurrency(medianRent)
              } else {
                medianRent = 'N/A'
              }
              if (population && population != null) {
                population = this.formatNumberWithComma(population)
              } else {
                population = 'N/A'
              }
              if (cashFlow && cashFlow != null) {
                cashFlow = this.doReportTextColorCode(cashFlow, null, '%')
              } else {
                cashFlow = 'N/A'
              }
              if (cagr1y && cagr1y != null) {
                cagr1y = this.doReportTextColorCode(cagr1y, null, '%')
              } else {
                cagr1y = 'N/A'
              }
              if (cagr3y && cagr3y != null) {
                cagr3y = this.doReportTextColorCode(cagr3y, null, '%')
              } else {
                cagr3y = 'N/A'
              }
              if (cagr5y && cagr5y != null) {
                cagr5y = this.doReportTextColorCode(cagr5y, null, '%')
              } else {
                cagr5y = 'N/A'
              }

              rentalTrendss.push(
                [
                  'Metro',
                  medianRent,
                  population,
                  cashFlow,
                  cagr1y,
                  cagr3y,
                  cagr5y,
                ],
              )
            } else {
              rentalTrendss.push(
                [
                  'Metro',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                  'N/A',
                ],
              )
            }
          } else {
            rentalTrendss.push(
              [
                'Zip Code',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
              ],
            )
            rentalTrendss.push(
              [
                'State',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
              ],
            )
            rentalTrendss.push(
              [
                'Metro',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
                'N/A',
              ],
            )
          }

          // prepare report request body
          const templateTest = 'template_honely_basic_test.html'
          const templateProd = 'template_honely_basic.html'

          const body = {
            template: templateProd,
            DATE: {
              type: 'text',
              data: this.getToday(),
            },
            PROPERTY_URL: {
              type: 'text',
              data: 'https://www.honely.com/forecast/' + this.property.address.property_id,
            },
            IMG_LOCATION: {
              type: 'image',
              data: propertyImg,
            },
            ADDRESS_1: {
              type: 'text',
              data: address1,
            },
            ADDRESS_2: {
              type: 'text',
              data: address2,
            },
            CITY: {
              type: 'text',
              data: city,
            },
            STATE: {
              type: 'text',
              data: state,
            },
            ZIP: {
              type: 'text',
              data: zip,
            },
            AGENT_NAME: {
              type: 'text',
              data: agentName,
            },
            EMAIL: {
              type: 'text',
              data: agentEmail,
            },
            PHONE: {
              type: 'text',
              data: agentPhone,
            },
            CUSTOM_LOGO: {
              type: 'text',
              data: customLogoData,
            },
            LIST_PRICE: {
              type: 'text',
              data: listPrice,
            },
            VALUATION_PRICE: {
              type: 'text',
              data: appraisal,
            },
            NUM_BEDS: {
              type: 'text',
              data: numBeds,
            },
            NUM_BATHS: {
              type: 'text',
              data: numBaths,
            },
            SQFT: {
              type: 'text',
              data: sqft,
            },
            STORIES: {
              type: 'text',
              data: stories,
            },
            STYLE: {
              type: 'text',
              data: architecture,
            },
            YEAR_BUILT: {
              type: 'text',
              data: yearBuilt,
            },
            COUNTY: {
              type: 'text',
              data: county,
            },
            APN: {
              type: 'text',
              data: apn,
            },
            SALE_DATE: {
              type: 'text',
              data: saleDate,
            },
            SALE_PRICE: {
              type: 'text',
              data: salePrice,
            },
            LAND: {
              type: 'text',
              data: this.formatCurrency(this.property.tax[0].land),
            },
            ADDITIONS: {
              type: 'text',
              data: this.formatCurrency(this.property.tax[0].additions),
            },
            ASSESSED_VALUE: {
              type: 'text',
              data: this.formatCurrency(this.property.tax[0].assessed_value),
            },
            TAX_YEAR: {
              type: 'text',
              data: this.property.tax[0].year.toString(),
            },
            PROPERTY_TAX: {
              type: 'text',
              data: this.formatCurrency(this.property.tax[0].property_tax),
            },
            NUM_PART_BATHS: {
              type: 'text',
              data: numPartialBaths,
            },
            ROOM_COUNT: {
              type: 'text',
              data: roomCount,
            },
            OTHER_ROOMS: {
              type: 'text',
              data: otherRooms,
            },
            AC_TYPE: {
              type: 'text',
              data: acType,
            },
            HEATING_TYPE: {
              type: 'text',
              data: heatType,
            },
            HEATING_FUEL: {
              type: 'text',
              data: heatFuelType,
            },
            AMENITIES: {
              type: 'text',
              data: amenities,
            },
            PARKING_TYPE: {
              type: 'text',
              data: parkingType,
            },
            PARKING_COUNT: {
              type: 'text',
              data: parkingSpaces,
            },
            PLUMBING_COUNT: {
              type: 'text',
              data: plumbingCount,
            },
            POOL: {
              type: 'text',
              data: pool,
            },
            SCHOOLS: {
              type: 'array',
              data: {
                row_class: null,
                array: schoolList,
              },
            },
            WALKSCORE: {
              type: 'array',
              data: {
                row_class: null,
                array: walkscoreList,
              },
            },
            UNIT_COUNT: {
              type: 'text',
              data: roomCount,
            },
            ARCHITECTURE_TYPE: {
              type: 'text',
              data: architecture,
            },
            CONDITION: {
              type: 'text',
              data: condition,
            },
            EXTERIOR_WALLS: {
              type: 'text',
              data: exteriorWalls,
            },
            SEWER_TYPE: {
              type: 'text',
              data: sewerType,
            },
            WATER_TYPE: {
              type: 'text',
              data: waterType,
            },
            CONSTRUCTION_TYPE: {
              type: 'text',
              data: construction,
            },
            ROOF_STYLE: {
              type: 'text',
              data: roofStyle,
            },
            ROOF_MATERIAL: {
              type: 'text',
              data: roofMaterial,
            },
            INTERIOR_WALLS: {
              type: 'text',
              data: interiorWalls,
            },
            FLOORING: {
              type: 'text',
              data: flooring,
            },
            BASEMENT_TYPE: {
              type: 'text',
              data: basementType,
            },
            PROPERTY_VALUE_FORECAST: {
              type: 'text',
              data: propertyValueForecast,
            },
            HONELY_FORECAST: {
              type: 'array',
              data: {
                row_class: null,
                array: honelyForecast,
              },
            },
            HOME_VALUE_CHANGE_ZIP: {
              type: 'array',
              data: {
                row_class: null,
                array: homeValueChangeZip,
              },
            },
            STATE_RANKING_TOTAL: {
              type: 'text',
              data: this.forecast.moving_trends.total_state_rank,
            },
            NATIONAL_RANKING_TOTAL: {
              type: 'text',
              data: this.forecast.moving_trends.total_country_rank,
            },
            MONTH3_ZIP_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[1].change,
            },
            MONTH3_ZIP_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_national_ranking_forecasts[1].change,
            },
            MONTH3_VALUE_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_state_ranking_forecasts[1].change,
            },
            MONTH3_VALUE_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_national_ranking_forecasts[1].change,
            },
            YEAR1_ZIP_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[2].change,
            },
            YEAR1_ZIP_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_national_ranking_forecasts[2].change,
            },
            YEAR1_VALUE_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_state_ranking_forecasts[2].change,
            },
            YEAR1_VALUE_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_national_ranking_forecasts[2].change,
            },
            YEAR2_ZIP_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[3].change,
            },
            YEAR2_ZIP_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_national_ranking_forecasts[3].change,
            },
            YEAR2_VALUE_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_state_ranking_forecasts[3].change,
            },
            YEAR2_VALUE_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_national_ranking_forecasts[3].change,
            },
            YEAR3_ZIP_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_state_ranking_forecasts[4].change,
            },
            YEAR3_ZIP_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.zipcode_growth_national_ranking_forecasts[4].change,
            },
            YEAR3_VALUE_STATE_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_state_ranking_forecasts[4].change,
            },
            YEAR3_VALUE_NATIONAL_RANK: {
              type: 'text',
              data: this.forecast.neighborhood.avg_value_national_ranking_forecasts[4].change,
            },
            CHART_3MONTH: {
              type: 'text',
              data: chart3monthImg,
            },
            CHART_1YEAR: {
              type: 'text',
              data: chart1yearImg,
            },
            CHART_2YEAR: {
              type: 'text',
              data: chart2yearsImg,
            },
            CHART_3YEAR: {
              type: 'text',
              data: chart3yearsImg,
            },
            CHART_RENT: {
              type: 'text',
              data: chartMedianRentImg,
            },
            CHART_CASHFLOW: {
              type: 'text',
              data: chartCashFlowImg,
            },
            CHART_GROWTH: {
              type: 'text',
              data: chartGrowthImg,
            },
            MIGRATION_STATE_RANK: {
              type: 'text',
              data: this.forecast.moving_trends.total_state_rank,
            },
            MIGRATION_COUNTRY_RANK: {
              type: 'text',
              data: this.forecast.moving_trends.total_country_rank,
            },
            MIGRATION_TRENDS: {
              type: 'array',
              data: {
                row_class: null,
                array: migrationTrends,
              },
            },
            RENTAL_TRENDS: {
              type: 'array',
              data: {
                row_class: null,
                array: rentalTrendss,
              },
            },
          }

          // add share properties if share mode is true
          if (shareMode) {
            if (shareEmails) {
              shareEmails = shareEmails.trim().replace(' ', '')
              if (shareEmails !== '') {
                if (shareMessage) {
                  shareMessage = this.doTextConversionnHTML4(shareMessage.trim())

                  if (shareMessage === '') {
                    shareMessage = ' '
                  }
                }
                body.share = {
                  sender: agentName,
                  to: shareEmails,
                  message: shareMessage,
                }
              } else {
                shareMode = false
              }
            } else {
              shareMode = false
            }
          }

          // console.log(JSON.stringify(body))

          // send request
          let prodUrl = ''
          let localUrl = ''

          if (shareMode) {
            prodUrl = 'https://api.honely.com/util/reports/pdf/share'
            localUrl = 'http://localhost:8080/reports/pdf/share'
          } else {
            prodUrl = 'https://api.honely.com/util/reports/pdf'
            localUrl = 'http://localhost:8080/reports/pdf'
          }

          // display loader and disable button
          // this.loading = true
          this.displayLoader()
          this.disableSubmitButton()
          const self = this

          console.log('[INFO] Start generating PDF report......')
          fetch(prodUrl, {
            method: 'POST',
            headers: {
              'Content-Type': 'application/json',
            },
            body: JSON.stringify(body),
          }).then(function (response) {
            // console.log(response)
            return response.blob()
          }).then(function (blob) {
            if (!shareMode) {
              var url = window.URL.createObjectURL(blob)
              var a = document.createElement('a')
              a.href = url
              a.download = 'honely_report.pdf'
              document.body.appendChild(a)
              a.click()
              a.remove()
            }
            // self.loading = false
            self.hideLoder()
            self.enableSubmitButton()
            console.log('[INFO] Finished generating PDF report......')
          }).catch((err) => {
            // self.loading = false
            self.hideLoder()
            self.enableSubmitButton()
            console.log('PDF Request Failed', err)
          })
        })
      },
      readFile (file) {
        if (file) {
          return new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.onload = res => {
              resolve(res.target.result)
            }
            reader.onerror = err => reject(err)
            reader.readAsDataURL(file)
          })
        } else {
          // create custom promise resolve if file does not exist
          return new Promise((resolve, reject) => {
            resolve(0)
          })
        }
      },
      formatCurrency (num) {
        // console.log(typeof (num))
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            num = Math.round(num / 100) * 100
            return num.toLocaleString('en-US', { style: 'currency', currency: 'USD', minimumFractionDigits: 0, maximumFractionDigits: 0 })
          } else if (typeof (num) === 'string') {
            num = Math.round(parseFloat(num) / 100) * 100
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
      formatNumberWithComma (num) {
        if (num && num !== null) {
          if (typeof (num) === 'number') {
            return parseInt(num).toLocaleString('en-US')
          } else if (typeof (num) === 'string') {
            return parseInt(num).toLocaleString('en-US')
          }
        } else {
          return '--'
        }
      },
      disableSubmitButton () {
        document.getElementById('btn_doShareReport').disabled = true
        document.getElementById('btn_doShareReport_mobile').disabled = true
        document.getElementById('btn_doDownloadReport').disabled = true
        document.getElementById('btn_doDownloadReport_mobile').disabled = true
        document.getElementById('btn_doUpdateEstimate').disabled = true
        document.getElementById('btn_doUpdateEstimate_mobile').disabled = true
      },
      enableSubmitButton () {
        document.getElementById('btn_doShareReport').disabled = false
        document.getElementById('btn_doShareReport_mobile').disabled = false
        document.getElementById('btn_doDownloadReport').disabled = false
        document.getElementById('btn_doDownloadReport_mobile').disabled = false
        document.getElementById('btn_doUpdateEstimate').disabled = false
        document.getElementById('btn_doUpdateEstimate_mobile').disabled = false
      },
      displayLoader () {
        document.getElementById('report-loader').classList.add('active')
      },
      hideLoder () {
        document.getElementById('report-loader').classList.remove('active')
      },
    },
  }
</script>
