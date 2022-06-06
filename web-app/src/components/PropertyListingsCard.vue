<template>
  <!-- eslint-disable -->
  <v-card class="mx-auto home-card" light style="min-height: 415px; height: 100%">
    <router-link
      style="text-decoration: none; color: inherit;"
      :to="{ name: 'ListingId', params: { property_id: item.property_id }, query: { address: query }}"
    >
      <v-img
        v-if="img !== placeHolder"
        id="propertyImg"
        :src="img"
        height="200px"
      />
      <v-img
        v-if="img === placeHolder"
        id="propertyImg"
        :src="img"
        height="200px"
        contain
      />

      <div class="homecontainer">
        <v-card-title class="card-pricing pa-2">
          <!-- <template v-if="((item.for_sale === 'Active') || (item.for_sale === 'For Sale') || (item.for_sale === 'PENDING') || (item.for_sale === 'Contingent')) && (item.list_price)">
          ${{ formatPrice(item.list_price) }}
          <span class="pricecat">
            list
          </span><br>
        </template> -->
          <!-- <template v-else-if="item.appraisal">
          ${{ formatPrice(item.appraisal) }}
          <span class="pricecat">
            appraisal
          </span><br>
        </template>
        <template v-else>
          <br>
        </template> -->

          <span class="price2">
            <!-- ${{item}} -->
            <span v-if="item.appraisal">${{ formatPrice(item.appraisal) }}</span>
            <span v-else style="display: inline-block;">
              No Valuation
              <v-tooltip
                  content-class="custom-tooltip"
                  bottom
                  max-width="300px"
              >
                <template #activator="{ on, attrs }">
                  <i
                    :class="'pt-1 mb-5 mdi band-header-icon mdi-information'"
                    aria-hidden="true"
                    style="float:right;color:black;font-size:13px;"
                    v-bind="attrs"
                    v-on="on"
                  />
                </template>
                  <span>Our proprietary automated valuation model provides the fairest value estimate on the market and quickly adjusts to unusual market conditions.<br><br>Our models have been back tested and verified by a third party to ensure integrity and prevent cherry picking.</span>
              </v-tooltip>
            </span>
            <br>
            <span class="pricecat">
              <span v-if="item.appraisal">Honely Estimated Value</span>
              <span v-if="item.appraisal">
                <v-tooltip
                  content-class="custom-tooltip"
                  bottom
                  max-width="300px"
                >
                  <template #activator="{ on, attrs }">
                    <i
                      :class="'pt-1 mb-5 mdi band-header-icon mdi-information'"
                      aria-hidden="true"
                      style="float:right;color:black;font-size:13px;"
                      v-bind="attrs"
                      v-on="on"
                    />
                  </template>
                  <span>Our proprietary automated valuation model provides the fairest value estimate on the market and quickly adjusts to unusual market conditions.<br><br>Our models have been back tested and verified by a third party to ensure integrity and prevent cherry picking.</span>
                </v-tooltip>
              </span>
            </span>
          </span>
        </v-card-title>

        <v-card-subtitle class="card-attributes mt-0  pa-2 font-weight-regular">
          <!-- <template v-if="item.beds_count"> -->
          <template>
            <b style="color: black;">{{ formatCount(item.beds_count) }} bds</b>
            <span>•</span>
          </template>
          <!-- <template v-if="item.baths"> -->
          <template>
            <b style="color: black;">{{ formatCount(item.baths) }} ba</b>
            <span>•</span>
          </template>
          <template v-if="item.total_area_sq_ft">
            <b style="color: black;">{{ formatCount(item.total_area_sq_ft) }}&nbsp;sqft</b>
          </template>
          <br>
          <b
          :style="confScoreStyles()"
          v-if="item.confidence_score"
          >
            <!-- Confidence Score: <span style="color: green; font-size: 16px">{{ item.confidence_score }}</span> -->
            Confidence Score: {{ item.confidence_score }}
          </b>
          <span v-if="item.confidence_score">
                <v-tooltip
                  content-class="custom-tooltip"
                  bottom
                  max-width="300px"
                >
                  <template #activator="{ on, attrs }">
                    <i
                      :class="'pt-1 mb-5 mdi band-header-icon mdi-information'"
                      aria-hidden="true"
                      style="float:right;color:black;font-size:13px;"
                      v-bind="attrs"
                      v-on="on"
                    />
                  </template>
                  <span>This score from 1 to 10 gives you an idea of how confident we are that the valuation we’re displaying is as close as possible to the truest value of the home.</span>
                </v-tooltip>
              </span>
        </v-card-subtitle>
      </div>
      <!-- <span class="price2 pricecat">
      Honely Value Estimate
    </span> -->
      <v-card-text class="addressinfo">
        <span style="color: black; font-size: 15px;">
          <b>{{ item.full_address }}</b>
        </span>
        <span v-if="item.realtor">
          {{ item.realtor }}
        </span>
        <!-- <template v-if="item.comparable_score">
        <br><br>
        <span><b>Comparable Score:</b></span> <span>{{ giveInt(item.comparable_score) }}</span>
      </template> -->
        <template v-if="item.comparable_score" style="margin-bottom: 20 px;">
          <br>
          <span style="float: left; margin-top: 10px; margin-right: 10px; color: black; font-size: 18px;">
            <b>Comparable Score:</b>
          </span>
          <span v-if="item.comparable_score >=75" class="circle-green">
            {{ giveInt(item.comparable_score) }}
          </span>
          <span v-if="item.comparable_score <75 && item.comparable_score >=50" class="circle-amber">
            {{ giveInt(item.comparable_score) }}
          </span>
          <span v-if="item.comparable_score <50 && item.comparable_score >0" class="circle-red">
            {{ giveInt(item.comparable_score) }}
          </span>
          <br>
        <br>
        </template>
        <!-- <span> -->
        <template>
          <div style="margin-top:4px">
            <span v-if="item && item.property_type" style="float: left; margin-right: 10px">
              Home type: {{ item.ptype }}
            </span>
            <br>
            <span v-if="item.for_sale && item.for_sale !== 'Fail'" style="float: left; margin-right: 10px">
              Status: <span style="color: #07871c; display: inline">{{ item.for_sale }}</span>
            </span>
            <span v-if="item.for_sale && item.for_sale === 'Fail'" style="float: left; margin-right: 10px">
              Status: <span style="color: #e74c3c; display: inline">Unknown</span>
            </span>
            <span v-if="!item.for_sale" style="float: left; margin-right: 10px">
              Status: <span style="color: #e74c3c; display: inline">Unknown</span>
            </span>
            <span v-if="item.fainitiallistdate " style="margin-right: 10px">
              Property posted on: {{ item.fainitiallistdate }}
            </span>
            <span v-if="!item.fainitiallistdate " style="margin-right: 10px">
              Property posted on: Date Unavailable
            </span>
          </div>
        </template>
        <!-- </span> -->
        <p class="home-card-link-button">
          <!-- <router-link :to="{ name: 'Listing', params: { fips: item.fips, apn: item.apn }, query: { address: query }}"> -->
          <div class="view-property-contact-agent-buttons">
            <router-link :to="{ name: 'ListingId', params: { property_id: item.property_id }, query: { address: query }}">
            <button class="btn-view-details">
              <i class="fas fa-home" />
              View Property Details
            </button>
          </router-link>
          <!-- <v-dialog v-model="dialog" persistent max-width="600px">
            <template #activator="{ on, attrs }">
              <div v-bind="attrs" v-on="on">
                <v-hover>
                  <template #default="{ hover }">
                    <button class="btn-view-details contact-agent" @click.prevent="">
                      <i class="mdi mdi-account-wrench" aria-hidden="true"/>
                      Contact Agent
                  </button>
                  </template>
                </v-hover>
              </div>
            </template>
            <template #default="dialog">
              <v-card class="contact-agent-form-card">
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
                  <i class="fa fa-times-thin fa-2x" style="color: black;"/>
                </button>
                <v-card-title>
                  <span class="provider-modal-heading text-h5">Contact Agent</span>
                </v-card-title>
                <v-form v-model="valid" :lazy-validation="true">
                  <v-row>
                      <v-col md='12'>
                        <v-text-field
                        v-model="firstName"
                        placeholder="First Name"
                        style="max-width:70%; margin-left: 25px"
                        :maxlength="30"
                        />
                      </v-col>
                  </v-row>
                  <v-row>
                      <v-col md='12'>
                        <v-text-field
                        v-model="lastName"
                        placeholder = "Last Name"
                        style="max-width:70%; margin-left: 25px"
                        :maxlength="30"
                        />
                      </v-col>
                  </v-row>
                  <v-row>
                      <v-col md='12'>
                        <v-text-field
                        v-model="email"
                        :rules="emailRules"
                        :maxlength="50"
                        placeholder="Email Address"
                        style="max-width:70%; margin-left: 25px"
                        />
                      </v-col>
                  </v-row>
                  <v-row>
                    <v-col md='12'>
                      <v-text-field
                      v-model="phone"
                      @keypress="isNumber($event)"
                      :rules="phoneRules"
                      :maxlength="10"
                      placeholder="Phone"
                      style="max-width:70%; margin-left: 25px"
                      />
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col md='12'>
                      <v-text-field
                      v-model="message"
                      :maxlength="50"
                      placeholder="Message"
                      style="max-width:70%; margin-left: 25px"
                      />
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col md='12'>
                      <input type="checkbox" id="contactLendersCheckBox" v-model="contactLendersChecked">
                      <label for="contactLendersCheckBox">Contact Lenders</label>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col md='12'>
                      <input type="checkbox" id="contactGeneralContractorsCheckBox" v-model="contactGeneralContractorsChecked">
                      <label for="contactGeneralContractorsCheckBox">Contact General Contractors</label>
                    </v-col>
                  </v-row>
                  <v-row>
                    <v-col md='12'>
                    </v-col>
                  </v-row>
                  <v-row align= "center" justify= "center">
                    <v-col md='12' v-if="!leadFormErrorMessage">
                      <v-btn
                      @click="submitLead(agent.agent_email, searchedAddress)"
                      :disabled="!valid"
                      color= "#06A550"
                      style="margin-left: 25px; margin-bottom: 25px"
                      class="submit-button"
                      >
                        Submit
                      </v-btn>
                    </v-col>
                    <v-col md='12' v-if="leadFormErrorMessage">
                      <br><p style="color: red; margin-left: 25px; margin-bottom: 25px;">{{ leadFormErrorMessage }}</p>
                    </v-col>
                  </v-row>
                </v-form>
              </v-card>
            </template>
          </v-dialog> -->
          </div>
          <router-link v-if="showEdit" :to="{ name: 'EditListing', params: { fips: item.fips, apn: item.apn }}">
            <v-btn text plain class="mx-3 pa-0">
              Edit Listing
              <i class="mdi mdi-play" aria-hidden="true"/>
            </v-btn>
          </router-link>

        <!--        <v-btn-->
        <!--          text-->
        <!--          color="#000000"-->
        <!--          plain-->
        <!--          class="ma-0 pa-0"-->
        <!--          @click="showAlert('View more like this');"-->
        <!--        >-->
        <!--          View more like this-->
        <!--          <i-->
        <!--            class="mdi mdi-play"-->
        <!--            aria-hidden="true"-->
        <!--          />-->
        <!--        </v-btn>-->
        </p>
      </v-card-text>
      <v-card-actions class="card-buttons">
        <v-btn
          v-if="item.additional_images"
          icon
          absolute
          plain
          class="camera-icon"
          @click="showAlert('View photos')"
        >
          <v-icon>{{ 'mdi-camera' }}</v-icon>
        </v-btn>
        <v-btn
          icon
          absolute
          plain
          class="heart-icon"
          @click.prevent="toggleFavorite(item)"
        >
          <template v-if="!isFavorite(item)">
            <v-icon>mdi-heart-outline</v-icon>
          </template>
          <template v-else>
            <v-icon>mdi-heart</v-icon>
          </template>
        </v-btn>
      </v-card-actions>
    </router-link>
  </v-card>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'
  import { bus } from '../main'
  import * as constants from './base64constants'

  export default {
    name: 'PropertyListingsCard',

    props: {
      item: Object,
      query: String,
      showEdit: Boolean,
    },
    data () {
      return {
        user: null,
        img: this.getImgBaseUrl(),
        placeHolder: constants.placeHolder,
        dialog: false,
        valid: false,
        firstName: null,
        lastName: null,
        email: null,
        emailRules: [
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        phone: null,
        phoneRules: [
          v => v.length === 10 || 'Phone number must contain 10 digits',
        ],
        message: '',
        leadFormErrorMessage: '',
        contactLendersChecked: false,
        contactGeneralContractorsChecked: false,
      }
    },

    computed: {
      ...mapGetters('listings', ['favoriteListings']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
    },
    updated () {
      this.getImage(this.item)
    },
    mounted () {
      this.getImage()
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.$store.getters['auth/cognitoUser'].attributes.email,
          },
        }).then((response) => {
          this.user = response.data
          this.prepopulateFields()
        }).catch((error) => {
          console.log('[ERROR] Failed to fetch user data', error)
          console.log(error.response.data.errorMessage)
        })
      }
    },
    methods: {
      confScoreStyles () {
        if (parseFloat(this.item.confidence_score) > 7.5) {
          return 'color: white; background: green; padding: 2px 4px 2px 4px; border-radius: 5px;'
        } else if (parseFloat(this.item.confidence_score) > 5) {
          // return 'color: white; background: #FFBF00; padding: 2px 4px 2px 4px; border-radius: 5px;'
          return 'color: white; background: #E67E22; padding: 2px 4px 2px 4px; border-radius: 5px;'
          // return 'color: #FFBF00; padding: 2px 4px 2px 4px; border-radius: 5px;'
        } else {
          return 'color: white; background: red; padding: 2px 4px 2px 4px; border-radius: 5px;'
        }
      },
      submitLead (email, currAddress) {
        // Can be Implemented Once we figure out how to send agent lead to a random agent
        // if (!this.firstName || !this.lastName || !this.email || !this.phone) {
        //   this.leadFormErrorMessage = 'Please enter all the fields!'
        //   setTimeout(() => {
        //     this.leadFormErrorMessage = ''
        //   }, 2000)
        // } else {
        //   const params = {
        //     first_name: this.firstName,
        //     last_name: this.lastName,
        //     user_email: this.email,
        //     phone_number: this.phone,
        //     agent_email: email,
        //     address: currAddress,
        //     tier: '1',
        //   }
        //   axios.post('https://api.honely.com/lookup-test/leads_tier_notification', params)
        //     .then(() => {
        //       this.leadFormErrorMessage = 'Form Submitted Successfully!'
        //       setTimeout(() => {
        //         this.leadFormErrorMessage = ''
        //       }, 2000)
        //     })
        // }
      },
      toggleDialog () {
        this.dialog = !this.dialog
      },
      validate () {
        this.$refs.form.validate()
      },
      isNumber: function (evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      getImage: function (item) {
        const toDataURL = url => fetch(url)
          .then(response => response.blob())
          .then(blob => new Promise((resolve, reject) => {
            const reader = new FileReader()
            reader.onloadend = () => resolve(reader.result)
            reader.onerror = reject
            reader.readAsDataURL(blob)
          }))
        toDataURL(this.getImgBaseUrl())
          .then(dataUrl => {
            if (dataUrl === constants.noImage) {
              this.img = constants.placeHolder
              return
            }
            this.img = this.getImgBaseUrl()
          })
      },
      getImgBaseUrl () {
        if (this.item.tempImages) {
          return '/apartment_temp_images/' + this.item.tempImages[0]
        } else {
          return 'https://maps.googleapis.com/maps/api/streetview?size=600x400&location=' + this.item.latitude + '' +
            ',' + this.item.longitude + '+&fov=90&source=outdoor&key=' + this.$mapsKey
        }
      },
      isFavorite: function (listing) {
        const exists = this.favoriteListings.find(function (x) {
          return x.fips === listing.fips && x.apn === listing.apn
        })
        // console.log('vx: exists from card isFavorite', exists)
        return exists
      },
      toggleFavorite: function (listing) {
        // if (this.$store.getters['auth/loggedIn']) {
        //   store.dispatch('listings/toggleFavorite', listing)
        // } else {
        //   bus.$emit('showError', { message: 'Please log in or create an account to add this to your favorites' })
        // }
        // console.log('vx: auth/isCognitoUserLoggedIn in togglefav (Listing.vue) is', this.$store.getters['auth/isCognitoUserLoggedIn'])
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          if (!(!this.isFavorite(listing) && this.favoriteListings.length === 5)) {
            const payload = {
              property_id: listing.property_id,
              apn: listing.apn,
              fips: listing.fips,
              user_id: this.user.user_id,
              address: listing.full_address,
              appraisal: listing.appraisal,
            }
            // console.log('vx: payload inside toggleFavorite is', payload)
            if (!this.isFavorite(listing)) {
              // vx: Tier 2: params = ["first_name", "last_name", "user_email", "phone_number", "saved_address", "zip_code", "tier"]
              const paramsTier2 = {
                first_name: this.user.first_name,
                last_name: this.user.last_name,
                user_email: this.user.email,
                phone_number: this.user.phone_number,
                address: listing.address.full_address,
                zip_code: listing.address.zip_code,
                tier: '2',
              }
              // axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier2)
            }
            this.$store.dispatch('listings/toggleFavorite', payload)
          } else {
            bus.$emit('showError', { message: 'Cannot have more than 5 favorite listings!' })
          }
        } else {
          bus.$emit('showError', { message: 'Please log in or create an account to add this to your favorites' })
        }
      },
      showAlert: function (theAlert = 'Button Clicked') {
        alert(theAlert)
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
      formatCount (value) {
        if (!parseInt(value)) {
          return '--'
        } else {
          return String(parseInt(value))
        }
      },
      giveInt (value) {
        return value.toString().split('.')[0]
      },
      prepopulateFields () {
        this.firstName = this.user.first_name
        this.lastName = this.user.last_name
        this.email = this.user.email
        this.phone = this.user.phone
      },
    },
  }
</script>

<style lang="sass">

.home-card.v-sheet.v-card
  border: 1px solid #07871c
  box-shadow: none

  .homecontainer
    display: flex
    justify-content: space-between

    .card-pricing
      display: block
      flex-basis: auto
      font-size: 23px
      font-weight: 600
      line-height: 21px
      text-align: left

      .pricecat
        font-size: 14px
        white-space: nowrap
        letter-spacing: 0
        margin-left: 3px

      .price2
        color: black
        margin-left: 2px

    .card-attributes
      display: block
      flex-basis: auto
      font-family: $body-font-family
      font-size: 13px
      line-height: 21px
      text-align: right
      color: #000000

      span
        font-size: 10px
        color: $color-gray-lt
        letter-spacing: 0

      b
        color: $color-squash

  .addressinfo
    padding: 0 0 0 12px
    color: black
    font-size: 14px
    line-height: 16px
    text-align: left

    span
      display: block
      font-family: $body-font-family
      color: $color-gray-mddk
      font-size: 12px

    .home-card-links
      z-index: 10

      .v-btn__content
        color: black
        letter-spacing: normal
        font-weight: 600

      .v-btn
        i
          color: $color-squash

      .v-btn:first-child
        margin-left: 0 !important

  .card-buttons
    padding: 4px

    .v-btn
      border-radius: 4px

    .camera-icon
      top: 158px
      left: 8px
      background-color: rgba(0,0,0,.5)
      color: rgba(255, 255, 255, .7)

    .camera-icon:hover
      color: #ffffff

    .heart-icon
      bottom: 5px
      right: 8px
      z-index: 3
      color: rgb(255,0,0) !important

    .heart-icon:hover
      background-color: transparent
      color: $color-squash

    .heart-icon.selected
      color: $color-squash

</style>
<style scoped>
.home-card-link-button {
  margin-top: 1rem;
  margin-bottom: 0 !important;
}
.home-card-link-button a {
  text-decoration: none;
}
.btn-view-details {
  font-size: 14px;
  padding: 2px 10px;
  min-height: 50px;
  border: 1px solid #00a650;
  border-radius: 3px;;
}
#search-listings-content .btn-view-details, #forecast-band-6 .btn-view-details {
  padding: 5px 10px;
}
.btn-view-details:hover {
  border-color: #e67e22;
  color: #e67e22;
}
.btn-view-details i {
  margin-right: 5px;
}
.btn-view-details.contact-agent:hover {
  border-color: #e67e22;
  color: #e67e22;
}
.btn-view-details.contact-agent{
  color: #00a650;
  margin-left: 15px;
  margin-bottom: 20px;
}
.btn-view-details.contact-agent i{
  font-size: 18px;
}
.circle-green
    {
    width:40px;
    height:40px;
    border-radius:20px;
    font-size:18px !important;
    color:#fff !important;
    line-height:40px;
    text-align:center;
    background:#2a8c45;
    margin-right: 10px;
    float: right;
    }
.circle-amber
    {
    width:40px;
    height:40px;
    border-radius:20px;
    margin-right: 10px;
    float: right;
    font-size:18px !important;
    color:black !important;
    line-height:40px;
    text-align:center;
    background:#FFBF00
    }
.circle-red
    {
    width:40px;
    height:40px;
    border-radius:20px;
    font-size:18px !important;
    color:#fff !important;
    line-height:40px;
    text-align:center;
    background:red;
    margin-right: 10px;
    float: right;
    }
.view-property-contact-agent-buttons{
  display: flex;
}
/* #contactGeneralContractorsCheckBox, #contactLendersCheckBox{
  margin-left: 20px;
  margin-right: 10px;
} */

@media only screen and (max-height: 850px) {
  .contact-agent-form-card{
    position: absolute;
    top: 25%;
    left: 0;
    right: 0;
    margin-left: auto;
    margin-right: auto;
    width: 80%;
    height: 70%;
    overflow-x: hidden;
  }
}
</style>
<style>
.custom-tooltip {
    opacity: 1!important;
}
</style>
