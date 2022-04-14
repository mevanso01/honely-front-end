<template>
  <!-- eslint-disable -->
  <div class="pg-listing">
    <page-loader :loading="loading" />
    <div class="section-wrapper">
      <!-- listing-container -->
      <div class="listing-container">
        <!-- listing-container-left-col -->
        <div class="listing-container-left-col">
          <!-- image wrapper (listhub) -->
          <div
            v-if="isListHub && getListhubPhotoCount > 0"
            class="listing-image-wrapper"
          >
            <v-carousel
              class="listing-carousel"
              height="auto"
              :show-arrows-on-hover="false"
              :hide-delimiters="true"
              :hide-delimiter-background="true"
            >
              <v-carousel-item
                v-for="i in getListhubPhotoCount"
                :key="i"
              >
                <div class="listing-carousel-bg" :style="'background-image: url(' + getListhubPhotoURL(i) + ')'"></div>
                <!-- <img :src="getListhubPhotoURL(i)" /> -->
              </v-carousel-item>
            </v-carousel>
          </div>
          <!-- /image wrapper (listhub) -->

          <!-- image wrapper (non-listhub) -->
          <div
            v-else
            class="listing-image-wrapper"
            v-bind:style="[ validImage ? { backgroundImage: 'url(' + image + ')' } : { background: ''}]"
          >
            <div class="listing-action-row">
              <div v-if="isActiveListing" class="listing-value">
                <p>{{ getPropertyStatus }}</p>
                <p>{{ getPropertyValue }}</p>
              </div>
              <div class="listing-action property-fav-btn" @click="toggleFavorite(property)">
                <span v-if="isFavorite" class="mdi mdi-heart"></span>
                <span v-else class="mdi mdi-heart-outline"></span>
              </div>
            </div>
          </div>
          <!-- /image wrapper (non-listhub) -->
          <div class="listing-map" id="listing-map"></div>
        </div>
        <!-- /listing-container-left-col -->

        <!-- listing-container-right-col -->
        <div class="listing-container-right-col">
          <!-- listing-data-basic -->
          <div class="listing-data-basic">
            <div class="listing-fact">
              <p><span class="mdi mdi-map-marker"></span> {{ getAddress1 }}</p>
              <p>{{ getPropertyBeds }} bds | {{ getPropertyBaths }} ba | {{ getPropertySqft }} sqft</p>
              <div v-if="isListHub" class="listing-value">
                <p><span>Listing Status: </span>{{ getPropertyStatus }}</p>
                <p><span>List Price:</span><span class="list-price"> {{ getPropertyValue }}</span></p>
              </div>
            </div>
            <div class="listing-extra">
              <p>Honely Estimate</p>
              <p class="text-large">{{ getHonelyEstimate }}</p>
              <a
                v-if="property"
                :href="'/forecast/' + property.property_id"
                class="forecast-links"
                >
                  Find Future Value
              </a>
            </div>
          </div>
          <!-- /listing-data-basic -->

          <!-- listing-data-detail -->
          <div class="listing-data-detail">
            <!-- listing-data-detail-tabs-->
            <div class="listing-data-detail-tabs-wrapper">
              <ul class="listing-data-detail-tabs" id="listing-data-detail-tabs">
                <li class="active" @click="displayListingTab(0)">Overview</li>
                <li @click="displayListingTab(1)">Property Info</li>
                <li @click="displayListingTab(2)">Sales &amp; Tax History</li>
                <li @click="displayListingTab(3)">Schools</li>
                <li @click="displayListingTab(4)">Neighborhood</li>
              </ul>
            </div>
            <!-- /listing-data-detail-tabs -->

            <!-- listing-data-detail-tab-content -->
            <div class="listing-data-detail-tab-content-wrapper">
              <!-- overview -->
              <div class="listing-data-detail-tab-content active">
                <table class="table-data">
                  <tr>
                    <td width="50%">Beds</td>
                    <td width="50%">{{ getPropertyBeds }}</td>
                  </tr>
                  <tr>
                    <td>Baths</td>
                    <td>{{ getPropertyBaths }}</td>
                  </tr>
                  <tr>
                    <td>SQft</td>
                    <td>{{ getPropertySqft }}</td>
                  </tr>
                  <tr>
                    <td>Stories</td>
                    <td v-if="property">{{ getPropertyData(property.structure.stories) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Style</td>
                    <td v-if="property">{{ getPropertyData(property.structure.architecture_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Year Built</td>
                    <td v-if="property">{{ getPropertyData(property.structure.year_built) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>County code</td>
                    <td v-if="property">{{ getPropertyData(property.address.fips) }}</td>
                    <td v-else>--</td>
                  </tr>
                </table>
              </div>
              <!-- /overview -->

              <!-- property-info -->
              <div class="listing-data-detail-tab-content">
                <table class="table-data">
                  <tr>
                    <td width="50%"># of Beds</td>
                    <td width="50%">{{ getPropertyBeds }}</td>
                  </tr>
                  <tr>
                    <td># of Full Baths</td>
                    <td>{{ getPropertyBaths }}</td>
                  </tr>
                  <tr>
                    <td># of Partial Baths</td>
                    <td v-if="property">{{ getPropertyData(property.structure.partial_baths_count) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Stories</td>
                    <td v-if="property">{{ getPropertyData(property.structure.stories) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Lot Size</td>
                    <td v-if="property">{{ getPropertySqft }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Parking Type</td>
                    <td v-if="property">{{ getPropertyData(property.structure.parking_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Parking Spaces Count</td>
                    <td v-if="property">{{ getPropertyData(property.structure.parking_spaces_count) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Pool</td>
                    <td v-if="property">{{ getPropertyData(property.structure.pool_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Heating</td>
                    <td v-if="property">{{ getPropertyData(property.structure.heating_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Cooling</td>
                    <td v-if="property">{{ getPropertyData(property.structure.air_conditioning_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Year Built</td>
                    <td v-if="property">{{ getPropertyData(property.structure.year_built) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Units Count</td>
                    <td v-if="property">{{ getPropertyData(property.structure.units_count) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Architecture Type</td>
                    <td v-if="property">{{ getPropertyData(property.structure.architecture_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Contruction</td>
                    <td v-if="property">{{ getPropertyData(property.structure.construction_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Exterior Walls</td>
                    <td v-if="property">{{ getPropertyData(property.structure.exterior_wall_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Interior Walls</td>
                    <td v-if="property">{{ getPropertyData(property.structure.interior_wall_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Flooring</td>
                    <td v-if="property">{{ getPropertyData(property.structure.flooring_types) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Water Type</td>
                    <td v-if="property">{{ getPropertyData(property.structure.water_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                  <tr>
                    <td>Sewer Type</td>
                    <td v-if="property">{{ getPropertyData(property.structure.sewer_type) }}</td>
                    <td v-else>--</td>
                  </tr>
                </table>
              </div>
              <!-- /property-info -->

              <!-- sale + tax -->
              <div class="listing-data-detail-tab-content">
                <div class="listing-data-row">
                  <p>Tax History</p>
                  <table v-if="getPropertyTaxHistory && getPropertyTaxHistory.length" class="table-data no-row-bg">
                    <tr>
                      <th width="50%">Year</th>
                      <th width="50%">Property Tax</th>
                    </tr>
                    <tr v-for="(item, i) in getPropertyTaxHistory">
                      <td>{{ item.year }}</td>
                      <td>${{ item.property_tax }}</td>
                    </tr>
                  </table>
                  <table v-else class="table-data no-row-bg">
                    <tr>
                      <td>No tax history available</td>
                    </tr>
                  </table>
                </div>
                <div class="listing-data-row">
                  <p>Sale History</p>
                  <table v-if="getPropertySaleHistory && getPropertySaleHistory.length > 0" class="table-data no-row-bg">
                    <tr>
                      <th width="50%">Date</th>
                      <th width="50%">Price</th>
                    </tr>
                    <tr v-for="(item, i) in getPropertySaleHistory">
                      <td>{{ item.date }}</td>
                      <td>${{ item.price }}</td>
                    </tr>
                  </table>
                  <table v-else class="table-data no-row-bg">
                    <tr>
                      <td>No sale history available</td>
                    </tr>
                  </table>
                </div>
              </div>
              <!-- /sale + tax -->

              <!-- schools -->
              <div class="listing-data-detail-tab-content">
                <table v-if="schools" class="table-data no-row-bg">
                  <tr>
                    <th width="15%">Rating</th>
                    <th width="65%">Name</th>
                    <th width="20%">Distance</th>
                  </tr>
                  <tr v-for="(item, i) in schools">
                    <td><span class="rating">{{ getRating(item.rating) }}</span></td>
                    <td>
                      <span class="name">{{ item.name }}</span>
                      <span class="description">{{ item.type }} | {{ item.level_short }}</span>
                    </td>
                    <td>{{ item.distance }} mi</td>
                  </tr>
                </table>
                <table v-else class="table-data no-row-bg">
                  <tr>
                    <td>No school data available</td>
                  </tr>
                </table>
              </div>
              <!-- /schools -->

              <!-- neighborhood -->
              <div class="listing-data-detail-tab-content">
                <table v-if="walkscore" class="table-data no-row-bg">
                  <tr>
                    <th width="15%">Rating</th>
                    <th width="85%">Category</th>
                  </tr>
                 <tr>
                    <td><span class="rating">{{ getRating(walkscore.walkscore) }}</span></td>
                    <td>
                      <span class="name">Walk Score</span>
                      <span class="description">{{ walkscore.description }}</span>
                    </td>
                  </tr>
                  <tr v-if="walkscore.bike">
                    <td><span class="rating">{{ getRating(walkscore.bike.score) }}</span></td>
                    <td>
                      <span class="name">Bike Score</span>
                      <span class="description">{{ walkscore.bike.description }}</span>
                    </td>
                  </tr>
                  <tr v-if="walkscore.transit">
                    <td><span class="rating">{{ getRating(walkscore.transit.score) }}</span></td>
                    <td>
                      <span class="name">Transit Score</span>
                      <span class="description">{{ walkscore.transit.description }}</span>
                      <span class="description">{{ walkscore.transit.summary }}</span>
                    </td>
                  </tr>
                </table>
                <table v-else class="table-data no-row-bg">
                  <tr>
                    <td>No data available</td>
                  </tr>
                </table>
              </div>
              <!-- /neighborhood -->
            </div>
            <!-- /listing-data-detail-tab-content -->
          </div>
          <!-- /listing-data-detail -->
        </div>
        <!-- /listing-container-right-col -->
      </div>
      <!-- /listing-container -->

      <!-- agent-form-container -->
      <div
        v-if="isListHub && getListAgent && getBrokerInfo"
        class="listhub-agent-form-container"
      >
        <div class="section-heading">Questions about this property?</div>
        <p>Get immediate information from a knowledgeable buyer's agent.</p>
        <div class="listhub-agent-form-wrapper">
          <div class="listhub-agent-form">
            <div class="form-row">
              <input type="text" name="listhub-form-name" id="listhub-form-name" placeholder="Your Name">
              <p class="input-msg"></p>
            </div>
            <div class="form-row">
              <input type="text" name="listhub-form-phone" id="listhub-form-phone" placeholder="Phone">
              <p class="input-msg"></p>
            </div>
            <div class="form-row">
              <input type="text" name="listhub-form-email" id="listhub-form-email" placeholder="Email">
              <p class="input-msg"></p>
            </div>
            <div class="form-row">
              <textarea name="listhub-form-message" id="listhub-form-message" placeholder="Message"></textarea>
              <p class="input-msg"></p>
            </div>
            <div>
              <button class="bg-primary" @click="submitListhubLead">Contact Now</button>
              <div class="response-msg" :class="formMessageClass">{{ formMessage }}</div>
            </div>
            <p class="input-msg-default">By proceeding, you consent to receive calls, text and voicemails at the number you provided (may be recorded and may be autodialed and use pre-recorded and artificial voices), and email, from HousesFor.Sale, Opcity, realtor.com and their network of service providers about your inquiry and other hone related matters. Msg/data rates may apply. This consent applies even if you are on a do not call list can is not a contain of any purchase.</p>
          </div>
          <div class="listhub-agent-contact">
            <div class="list-agent-picture">
              <img src="~@/assets/site_images/graphics/list_agent_default.png" />
            </div>
            <div class="list-agent-info">
              <p class="list-agent-name">{{ getListhubData(true, this.getListAgent.agent_full_name) }}</p>
              <p class="list-agent-company">{{ getListhubData(true, this.getListAgent.office_name) }}</p>
              <p class="list-agent-phone">{{ Math.trunc(getListhubData(true, this.getListAgent.office_phone)) }}</p>
            </div>
          </div>
        </div>
        <!-- listhub tracking -->
        <img :src="listhubTrackerUrl" class="img-tracker">
        <img v-if="agentEmailSent" :src="listhubTrackerUrl2" class="img-tracker">
        <!-- /listhub tracking -->
      </div>
      <!-- /agent-form-container -->

      <!-- broker-container -->
      <div
        v-if="isListHub && getListAgent && getBrokerInfo"
        class="listhub-broker-container"
      >
        <div class="section-heading">Broker Details</div>
        <div class="listhub-broker-wrapper">
          <p>Listing Provided By:</p>
          <p>
            <span>Brokerage name:</span>
            {{ getListhubData(false, this.getBrokerInfo.broker_name) }}
          </p>
          <p>
            <span>Phone:</span>
            {{ getListhubData(false, this.getBrokerInfo.brokerage_phone) }}
          </p>
          <p>
            <span>Email:</span>
            {{ getListhubData(false, this.getBrokerInfo.brokerage_email) }}
          </p>
          <p>
            <span>Broker name:</span>
            {{ getListhubData(true, this.getListAgent.office_name) }}
          </p>
          <p class="listhub-disclaimer">
            {{ getListhubData(true, this.getListAgent.disclaimer) }}
          </p>
        </div>
      </div>
      <!-- /broker-container -->

      <!-- comparable-properties-container -->
      <div class="comparable-properties-container">
        <div class="section-heading" v-if="properties && properties.length > 0">Comparable Properties</div>
        <div class="comparable-properties">
          <property-block
            v-for="property in properties"
            :property-data="property"
            :comparable="true"
            :compact="false"
            :key="property.property_id"
          />
        </div>
      </div>
      <!-- /comparable-properties-container -->
    </div>
  </div>
  <!-- /eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import Scriptjs from 'scriptjs'
  import { mapGetters, mapState } from 'vuex'

  /* global google */

  export default {
    components: {
      PageLoader: () => import('@/components/PageLoader'),
      PropertyBlock: () => import('@/components/PropertyBlock'),
    },
    prop: {
    },
    data () {
      return {
        loading: false,
        search_id: null,
        user: null,
        canSponsorListing: false,
        fips: null,
        apn: null,
        property_id: null,
        query: null,
        mapsKey: 'AIzaSyClIFG-ONBwyXrn4_kaA4yMYHGpZD5EEko',
        property: null,
        listingkey: null,
        isListHub: true,
        image: '',
        validImage: false,
        properties: [],
        schools: [],
        walkscore: null,
        forecast: null,
        mySponsoredListings: [],
        errorMessage: null,
        errorAddress: null,
        formMessage: '',
        formMessageClass: 'status-ok',
        agentEmailSent: false,
      }
    },
    computed: {
      ...mapState('auth', ['currentUser']),
      ...mapGetters('listings', ['favoriteListings']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      listhubTrackerUrl: function () {
        if (this.listingkey) {
          return 'https://tracking.listhub.net/tracker?mp=' + encodeURIComponent('M-5383') + '&ev=' + encodeURIComponent('DETAIL_PAGE_VIEWED') + '&et=' + encodeURIComponent(Date.now()) + '&v=' + encodeURIComponent('3') + '&ep=' + encodeURIComponent(window.location.href) + '&lkey=' + encodeURIComponent(this.property.address.listing_key) + '&clid=' + encodeURIComponent('123456') + '&ua=' + encodeURIComponent(navigator.userAgent)
        } else {
          return ''
        }
      },
      listhubTrackerUrl2: function () {
        if (this.listingkey) {
          return 'https://tracking.listhub.net/tracker?mp=' + encodeURIComponent('M-5383') + '&ev=' + encodeURIComponent('AGENT_OFFICE_EMAIL_SENT') + '&et=' + encodeURIComponent(Date.now()) + '&v=' + encodeURIComponent('3') + '&ep=' + encodeURIComponent(window.location.href) + '&lkey=' + encodeURIComponent(this.property.address.listing_key) + '&clid=' + encodeURIComponent('123456') + '&ua=' + encodeURIComponent(navigator.userAgent)
        } else {
          return ''
        }
      },
      getListhubPhotoCount () {
        let count = 0
        if (this.property && this.property.address) {
          if (this.property.address.photos_count) {
            if (parseInt(this.property.address.photos_count) > 5) {
              count = 5
            } else {
              count = parseInt(this.property.address.photos_count)
            }
          }
        }
        return count
      },
      isFavorite () {
        let found = false
        if (this.property) {
          // console.log(this.$store.getters['listings/favoriteListings'])
          if (this.$store.getters['listings/favoriteListings'] && this.$store.getters['listings/favoriteListings'].length > 0) {
            for (let i = 0; i < this.$store.getters['listings/favoriteListings'].length; i++) {
              if (this.$store.getters['listings/favoriteListings'][i].property_id === this.property.property_id) {
                found = true
                break
              }
            }
          }
        }
        return found
      },
      isActiveListing () {
        let flag = false
        if (this.property && this.property.status) {
          if (this.property.status === 'For Sale' || this.property.status === 'Active') {
            if (this.property.valuation) {
              if (this.property.valuation.list_price) {
                flag = true
              }
            }
          }
        }
        return flag
      },
      getPropertyStatus () {
        if (this.property && this.property.status) {
          if (this.property.status === 'For Sale' || this.property.status === 'Active') {
            return this.property.status
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getPropertyValue () {
        let value = ''
        if (this.property) {
          if (this.property.valuation) {
            if (this.property.status) {
              if (this.property.status === 'For Sale' || this.property.status === 'Active') {
                if (this.property.valuation.list_price) {
                  value = this.formatNumber(this.property.valuation.list_price, true)
                }
              }
            }
          }
        }
        return value
      },
      getHonelyEstimate () {
        let value = '--'
        if (this.property && this.property.valuation) {
          if (this.property.valuation.appraisal) {
            value = this.formatNumber(this.property.valuation.appraisal, true)
          }
        }
        return value
      },
      getAddress1 () {
        if (this.property && this.property.address) {
          if (this.property.address.full_address) {
            if (this.property.address.city && this.property.address.state && this.property.address.zip_code) {
              const toRemove = this.capitalize(this.property.address.city) + ' ' + this.property.address.state + ' ' + this.property.address.zip_code
              return this.property.address.full_address.replace(toRemove, '')
            } else {
              return this.property.address.full_address
            }
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getAddress2 () {
        let address2 = ''
        if (this.property && this.property.address) {
          if (this.property.address.city) {
            address2 += this.capitalize(this.property.address.city) + ' '
          }
          if (this.property.address.state) {
            address2 += this.property.address.state + ' '
          }
          if (this.property.address.zip_code) {
            address2 += this.property.address.zip_code
          }
        }
        return address2
      },
      getPropertyBeds () {
        let fact = '--'
        if (this.property && this.property.structure) {
          if (this.property.structure.beds_count) {
            fact = this.property.structure.beds_count
          }
        }
        return fact
      },
      getPropertyBaths () {
        let fact = '--'
        if (this.property && this.property.structure) {
          if (this.property.structure.baths) {
            fact = this.property.structure.baths
          }
        }
        return fact
      },
      getPropertySqft () {
        let fact = '--'
        if (this.property && this.property.structure) {
          if (this.property.structure.total_area_sq_ft) {
            fact = this.formatNumber(this.property.structure.total_area_sq_ft, false)
          }
        }
        return fact
      },
      getPropertyTaxHistory () {
        const history = []
        if (this.property && this.property.tax) {
          for (let i = 0; i < this.property.tax.length; i++) {
            history.push(this.property.tax[i])
          }
        }
        return history
      },
      getPropertySaleHistory () {
        const history = []
        if (this.property && this.property.sale_history) {
          for (let i = 0; i < this.property.sale_history.length; i++) {
            history.push(this.property.sale_history[i])
          }
        }
        return history
      },
      getListAgent () {
        let agent = null
        if (this.isListHub) {
          if (this.property && this.property.agent_information) {
            agent = this.property.agent_information
          }
        }
        return agent
      },
      getBrokerInfo () {
        let broker = null
        if (this.isListHub) {
          if (this.property && this.property.broker_information) {
            broker = this.property.broker_information
          }
        }
        return broker
      },
    },
    watch: {
      $route (to, from) {
        this.fips = this.$route.params.fips
        this.apn = this.$route.params.apn
        this.search_id = this.$route.params.search_id
        if (this.search_id) {
          // if (isNaN(this.search_id)) {
          //   this.listingkey = this.search_id
          //   this.property_id = null
          // } else {
          //   this.listingkey = null
          //   this.property_id = this.search_id
          // }
          // if (this.listingkey) {
          //   this.isListHub = true
          // } else {
          //   this.isListHub = false
          // }
        }
        if (this.query !== null) {
          this.query = this.$route.query.address
        } else {
          const currentAddress = this.getURLParameterByName('address', window.location.href)
          if (currentAddress !== null && currentAddress !== 'null') {
            this.query = currentAddress
          } else {
            this.query = ''
          }
        }
        this.load()
        this.agentEmailSent = false
      },
    },
    updated () {
    },
    mounted () {
      // console.log(navigator.userAgent)
      this.fips = this.$route.params.fips
      this.apn = this.$route.params.apn
      this.search_id = this.$route.params.search_id
      // if (this.search_id) {
      //   if (isNaN(this.search_id)) {
      //     this.listingkey = this.search_id
      //     this.property_id = null
      //   } else {
      //     this.listingkey = null
      //     this.property_id = this.search_id
      //   }
      // }
      // if (this.listingkey) {
      //   this.isListHub = true
      // } else {
      //   this.isListHub = false
      // }
      this.address = this.$route.query.address
      this.load()
    },
    created () {
      this.fips = this.$route.params.fips
      this.apn = this.$route.params.apn
      this.property_id = this.$route.params.property_id
      if (this.query !== null) {
        this.query = this.$route.query.address
      } else {
        const currentAddress = this.getURLParameterByName('address', window.location.href)
        if (currentAddress !== null && currentAddress !== 'null') {
          this.query = currentAddress
        } else {
          this.query = ''
        }
      }
    },
    methods: {
      /* eslint-disable */
      getURLParameterByName (name, url) {
        name = name.replace(/[\[\]]/g, '\\$&')
        var regex = new RegExp('[?&]' + name + '(=([^&#]*)|&|#|$)')
        const results = regex.exec(url)
        if (!results) return null
        if (!results[2]) return ''
        return decodeURIComponent(results[2].replace(/\+/g, ' '))
      },
      /* eslint-enable */
      load () {
        const self = this
        this.loading = true
        this.getUserProfile()
        // get property data
        setTimeout(function () {
          // console.log('[DEV] Getting property data......')
          self.getProperty()
        }, 500)
      },
      getUserProfile () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          axios.get('https://api.honely.com/lookup-test/user_profile', {
            params: {
              email: this.$store.getters['auth/cognitoUser'].attributes.email,
            },
          }).then((response) => {
            this.user = response.data
          }).catch((error) => {
            console.log('[ERROR] Failed to fetch user data', error)
            console.log(error.response.data.errorMessage)
          })
        }
      },
      getProperty () {
        let userId = null
        if (this.user && this.user.user_id) {
          userId = this.user.user_id
        }
        if (this.search_id) {
          axios.get('https://api.honely.com/searches/listing', {
            params: {
              search_id: this.search_id,
              user_id: userId,
            },
          }).then((response) => {
            const self = this
            // console.log(response.data)
            this.property = response.data
            if (!this.property_id) {
              this.property_id = this.property.property_id
            }
            if (this.property.listing_key) {
              this.isListHub = true
            } else {
              this.isListHub = false
            }
            //     this.listingkey = this.search_id
            //     this.property_id = null
            this.listingkey = this.property.listing_key
            this.property_id = this.property.property_id
            this.loading = false
            this.checkImage()
            this.getSchoolData()
            this.getWalkScore()
            this.getRelatedListings()
            // load map
            Scriptjs('https://maps.googleapis.com/maps/api/js?key=' + this.$mapsKey, () => {
              self.initMap()
            })
          }).catch((error) => {
            console.log('[ERROR] Failed to fetch listing data......')
            this.errorMessage = error.response.data.errorMessage
            this.errorAddress = error.response.data.address
            this.loading = false
          })
        }
      },
      checkImage () {
        const self = this
        if (this.property) {
          const url = 'https://maps.googleapis.com/maps/api/streetview?size=600x400&location=' + this.property.address.latitude + ',' + this.property.address.longitude + '&radius=100&return_error_code=true&source=outdoor&key=' + this.$mapsKey
          // console.log(url)
          fetch(url, {
            method: 'GET',
            headers: {
            },
          }).then(function (response) {
            // console.log(response)
            if (!response.ok) {
              throw Error(response.status)
            }
            return response.blob
          }).then(function (blob) {
            self.image = url
            self.validImage = true
          }).catch((err) => {
            console.log('[ERROR] Google image API failed =>', err)
            self.validImage = true
            self.image = '/site_images/listing_default_image.png'
          })
        }
      },
      initMap () {
        this.map = new google.maps.Map(document.getElementById('listing-map'), {
          center: {
            lat: parseFloat(this.property.address.latitude),
            lng: parseFloat(this.property.address.longitude),
          },
          zoom: 15,
        })
        const me = this
        google.maps.event.addListener(me.map, 'zoom_changed', function () {
          google.maps.event.addListener(me.map, 'bounds_changed', function (event) {
            if (me.map.zoom > 15) {
              me.map.setZoom(15)
              me.map.initialZoom = false
            }
          })
        })
        const marker = new google.maps.Marker({
          position: { lat: parseFloat(this.property.address.latitude), lng: parseFloat(this.property.address.longitude) },
          map: this.map,
        })
        var bounds = new google.maps.LatLngBounds()
        bounds.extend(marker.getPosition())
        this.map.fitBounds(bounds)
      },
      getListhubPhotoURL (index) {
        let url = ''
        if (this.listingkey && this.getListhubPhotoCount > 0) {
          url = 'https://listhub-property-images.s3.amazonaws.com/' + this.listingkey + '_' + index + '.jpg'
        }
        return url
      },
      getSchoolData () {
        if (this.property.address.latitude && this.property.address.longitude) {
          axios.get('https://api.honely.com/lookup/great_schools', {
            params: {
              lat: this.property.address.latitude,
              lon: this.property.address.longitude,
            },
          }).then((response) => {
            // console.log(response.data)
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
      getWalkScore () {
        if (this.property.address.street_name &&
          this.property.address.street_suffix &&
          this.property.address.city &&
          this.property.address.state &&
          this.property.address.zip_code &&
          this.property.address.latitude &&
          this.property.address.longitude) {
          axios.get('https://api.honely.com/lookup/walk_score', {
            params: {
              address: this.property.address.street_name + this.property.address.street_suffix + ',' + this.property.address.city + ',' + this.property.address.state + this.property.address.zip_code,
              lat: this.property.address.latitude,
              lon: this.property.address.longitude,
            },
          }).then((response) => {
            // console.log(response.data)
            this.walkscore = response.data
          })
        }
      },
      getForecast () {
        if (this.property_id) {
          axios.get('https://api.honely.com/searches/forecast', {
            params: {
              property_id: this.property_id,
            },
          }).then((response) => {
            this.forecast = response.data
          })
        }
      },
      getRelatedListings () {
        axios.get('https://api.honely.com/lookup/comparable_homes?property_id=' + this.property.property_id, {
          params: {
          },
        }).then((response) => {
          // console.log(response.data)
          this.properties = []
          response.data.rows.forEach(function (row) {
            if (this.properties.length < 3 && (row.apn !== this.apn || row.fips !== this.fips)) {
              row.appraisal = row.current_value
              row.beds_count = row.bedrooms
              row.baths = row.bathrooms
              row.latitude = row.situslatitude
              row.longitude = row.situslongitude
              const tempImages = this.$store.getters['listings/tempImages'](row.fips + '/' + row.apn)
              if (tempImages) {
                row.tempImages = tempImages
              }
              this.properties.push(row)
            }
          }, this)
        })
      },
      getMySponsoredListings () {
        axios
          .get('/api/v1/my-sponsored-listings', {
            headers: {
              Authorization: 'Bearer ' + this.currentUser,
            },
          })
          .then((response) => {
            const data = response.data
            this.mySponsoredListings = data.rows
          })
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
          return '--'
        }
      },
      capitalize (string) {
        if (string) {
          let result = ''
          const strings = string.split(' ')
          for (let i = 0; i < strings.length; i++) {
            result += strings[i].charAt(0).toUpperCase() + strings[i].slice(1).toLowerCase()
            if (i !== strings.length - 1) {
              result += ' '
            }
          }
          return result
        } else {
          return string
        }
      },
      getPropertyData (value) {
        if (this.property) {
          if (value) {
            return value
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getListhubData (agent, value) {
        let data = '--'
        if (agent) {
          if (this.getListAgent) {
            if (value) {
              data = value
            }
          }
        } else {
          if (this.getBrokerInfo) {
            if (value) {
              data = value
            }
          }
        }
        return data
      },
      schoolDescription (school) {
        const description = []
        if (school.type) {
          description.push(school.type.charAt(0).toUpperCase() + school.type.slice(1))
        }
        if (school.level) {
          description.push(school.level)
        }
        return description.join(' | ')
      },
      getRating (rating) {
        if (rating) {
          return rating
        } else {
          return 'N/R'
        }
      },
      sponsorListing () {
        if (this.fips && this.apn) {
          axios
            .post('/api/v1/sponsor-listing', {
                    fips: this.fips,
                    apn: this.apn,
                  },
                  {
                    headers: {
                      Authorization: 'Bearer ' + this.currentUser,
                    },
                  },
            ).then(() => {
              this.load()
            })
        }
      },
      toggleFavorite: function (listing) {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          if (!(!this.isFavorite && this.favoriteListings.length === 5)) {
            const payload = {
              property_id: listing.property_id,
              apn: listing.address.apn,
              fips: listing.address.fips,
              user_id: this.user.user_id,
              address: listing.address.full_address,
              appraisal: listing.valuation.appraisal,
            }
            if (!this.isFavorite) {
              const paramsTier2 = {
                first_name: this.user.first_name,
                last_name: this.user.last_name,
                user_email: this.user.email,
                phone_number: this.user.phone_number,
                address: listing.address.full_address,
                zip_code: listing.address.zip_code,
                tier: '2',
              }
              axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier2)
            }
            this.$store.dispatch('listings/toggleFavorite', payload)
          } else {
            this.$alert('Cannot have more than 5 favorite listings.')
          }
        } else {
          this.$alert('Please log in or create an account to add this to your favorites.')
        }
      },
      displayListingTab (index) {
        if (index >= 0) {
          const tabs = document.getElementById('listing-data-detail-tabs')
          const tabContent = document.getElementsByClassName('listing-data-detail-tab-content')
          if (tabs) {
            const tabItems = tabs.childNodes
            if (tabItems) {
              for (let i = 0; i < tabItems.length; i++) {
                if (index === i) {
                  tabItems[i].classList.add('active')
                  tabContent[i].classList.add('active')
                } else {
                  tabItems[i].classList.remove('active')
                  tabContent[i].classList.remove('active')
                }
              }
            }
          }
        }
      },
      submitListhubLead () {
        let name = document.getElementById('listhub-form-name')
        let phone = document.getElementById('listhub-form-phone')
        let email = document.getElementById('listhub-form-email')
        let message = document.getElementById('listhub-form-message')

        if (name) {
          name = name.value.trim()
        }
        if (phone) {
          phone = phone.value.trim()
        }
        if (email) {
          email = email.value.trim()
        }
        if (message) {
          message = message.value.trim()
        }

        // validate inputs
        if (name === '') {
          this.toggleInputError('listhub-form-name', true, 'Please enter your name')
          return
        } else {
          this.toggleInputError('listhub-form-name', false, null)
        }
        if (phone === '') {
          this.toggleInputError('listhub-form-phone', true, 'Please enter your phone')
          return
        } else {
          this.toggleInputError('listhub-form-phone', false, null)
        }
        if (email === '') {
          this.toggleInputError('listhub-form-email', true, 'Please enter a valid email')
          return
        } else {
          if (this.validateEmail(email)) {
            this.toggleInputError('listhub-form-email', false, null)
          } else {
            this.toggleInputError('listhub-form-email', true, 'Please enter a valid email')
            return
          }
        }
        if (message === '') {
          this.toggleInputError('listhub-form-message', true, 'Please enter the message')
          return
        } else {
          this.toggleInputError('listhub-form-message', false, null)
        }

        // continue submitting the form
        const params = {
          name: name,
          message: message,
          email: email,
          phone_number: phone,
          agent_email: this.getListAgent.agent_email,
          // agent_email: 'chenxin.lin@allocaterite.com',
        }
        axios.post('https://api.honely.com/lookup-test/agent_contact', params)
          .then(() => {
            this.agentEmailSent = true
            this.$alert('Submitted successfully')
            // this.formMessage = 'Submitted successfully'
            // this.formMessageClass = 'status-ok'
            this.clearLeadForm()
          })
          .catch((error) => {
            this.$alert('Something went wrong, please try again later')
            // this.formMessage = 'Something went wrong, please try again later'
            // this.formMessageClass = 'status-error'
            console.log(error)
          })
      },
      validateEmail (email) {
        // eslint-disable-next-line
        if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(email)) {
          return true
        } else {
          return false
        }
      },
      toggleInputError (id, error, message) {
        const input = document.getElementById(id)
        if (input) {
          if (error) {
            input.classList.add('error')
            input.nextElementSibling.innerHTML = message
          } else {
            input.classList.remove('error')
            input.nextElementSibling.innerHTML = ''
          }
        }
      },
      clearLeadForm () {
        document.getElementById('listhub-form-name').value = ''
        document.getElementById('listhub-form-phone').value = ''
        document.getElementById('listhub-form-email').value = ''
        document.getElementById('listhub-form-message').value = ''
      },
    },
  }
</script>
