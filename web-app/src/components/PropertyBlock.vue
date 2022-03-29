<template>
  <!-- eslint-disable -->
  <div v-if="propertyData" class="property-container" :class="{ compact : compact}">
    <!-- property-img -->

    <!-- property-img -->
    <div
      class="property-img"
      v-bind:style="[ validImage ? { background: 'url(' + image + ')' } : { background: ''}]"
    >
      <div v-if="compact" class="property-action-row">
        <p class="property-status-value">
          <span>
            {{ getAddress1 }}
            <br>
            {{ getAddress2 }}
          </span>
        </p>
        <div class="property-fav-btn" @click="toggleFavorite(propertyData)">
          <span v-if="isFavorite" class="mdi mdi-heart"></span>
          <span v-else class="mdi mdi-heart-outline"></span>
        </div>
      </div>
      <div v-else class="property-action-row">
        <p class="property-status-value">
          <span>{{ getSaleStatus }}</span>
          <span>{{ getListPrice }}</span>
        </p>
        <div class="property-fav-btn" @click="toggleFavorite(propertyData)">
          <span v-if="isFavorite" class="mdi mdi-heart"></span>
          <span v-else class="mdi mdi-heart-outline"></span>
        </div>
      </div>
    </div>
    <!-- /property-img -->

    <!-- property-info -->
    <div v-if="compact" class="property-info">
      <div class="property-info-compact">
        <div class="property-data">
          <p class="property-facts">{{ getPropertyFacts }}</p>
          <p>
            <router-link
              v-if="isListhub"
              :to="{ name: 'ListingId', params: { search_id: propertyData.listingkey }, query: { address: query }}"
              class="property-links"
              >
                View Property Details
            </router-link>
            <router-link
              v-else
              :to="{ name: 'ListingId', params: { search_id: propertyData.property_id }, query: { address: query }}"
              class="property-links"
              >
                View Property Details
            </router-link>
          </p>
        </div>
        <div v-if="isActiveListing" class="property-estimate">
          <label>List Price</label>
          <span>{{ getListPrice }}</span>
        </div>
        <div v-else class="property-estimate">
          <label>Honely Estimate</label>
          <span>{{ getHonelyEstimate }}</span>
        </div>
      </div>
    </div>
    <div v-else class="property-info">
      <div class="property-honely-data">
        <p v-if="isActiveListing">
          <label>List Price</label>
          <span>{{ getListPrice }}</span>
        </p>
        <p v-else>
          <label>Honely Estimate</label>
          <span>{{ getHonelyEstimate }}</span>
        </p>
        <div v-if="comparable" class="property-score">
          <label>Comparable</label>
          <span :class="getPropertyComparableScoreColorCode(getPropertyComparableScore)">{{ getPropertyComparableScore }}</span>
        </div>
        <!-- <p>
          <label>Confidence Score</label>
          <span>7.5</span>
        </p> -->
      </div>
      <div class="property-data">
        <p class="property-facts">{{ getPropertyFacts }}</p>
        <p class="property-address">{{ getPropertyAddress }}</p>
       <p>
         <router-link
          v-if="isListhub"
          :to="{ name: 'ListingId', params: { search_id: propertyData.listingkey }, query: { address: query }}"
          class="property-links"
          >
            View Property Details
          </router-link>
          <router-link
            v-else
            :to="{ name: 'ListingId', params: { search_id: propertyData.property_id }, query: { address: query }}"
            class="property-links"
          >
            View Property Details
          </router-link>
        </p>
      </div>
    </div>
    <!-- /property-info -->

    <!-- listhub-tracker -->
    <img v-if="isListhub" :src="listhubTrackerUrl" class="img-tracker">
    <!-- /listhub-tracker -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapGetters } from 'vuex'
  import { bus } from '../main'
  import * as constants from './base64constants'
  export default {
    name: 'PropertyBlock',
    props: {
      propertyData: Object,
      comparable: Boolean,
      compact: Boolean,
      item: Object,
      query: String,
      showEdit: Boolean,
    },
    data () {
      return {
        user: null,
        image: null,
        validImage: false,
      }
    },
    computed: {
      ...mapGetters('listings', ['favoriteListings']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      isListhub () {
        if (this.propertyData && this.propertyData.listingkey) {
          return true
        } else {
          return false
        }
      },
      listhubTrackerUrl: function () {
        if (this.propertyData && this.propertyData.listingkey) {
          return 'https://tracking.listhub.net/tracker?mp=' + encodeURIComponent('M-5383') + '&ev=' + encodeURIComponent('DETAIL_PAGE_VIEWED') + '&et=' + encodeURIComponent(Date.now()) + '&v=' + encodeURIComponent('3') + '&ep=' + encodeURIComponent(window.location.href) + '&lkey=' + encodeURIComponent(this.propertyData.listingkey) + '&clid=' + encodeURIComponent('123456') + '&ua=' + encodeURIComponent(navigator.userAgent)
        } else {
          return ''
        }
      },
      getPhotoCount () {
        let count = 0
        if (this.propertyData && this.propertyData.listingkey) {
          if (this.propertyData.photoscount) {
            count = parseInt(this.propertyData.photoscount)
          }
        }
        return count
      },
      isFavorite () {
        // console.log(this.propertyData)
        let found = false
        if (this.favoriteListings && this.favoriteListings.length > 0) {
          for (let i = 0; i < this.favoriteListings.length; i++) {
            if (this.favoriteListings[i].property_id && this.favoriteListings[i].property_id === this.propertyData.property_id) {
              found = true
              break
            } else if (this.favoriteListings[i].apn && this.favoriteListings[i].apn === this.propertyData.apn) {
              found = true
              break
            } else if (this.favoriteListings[i].address && this.favoriteListings[i].address === this.propertyData.full_address) {
              found = true
              break
            }
          }
        }
        return found
      },
      getAddress1 () {
        if (this.propertyData) {
          if (this.propertyData.full_address) {
            if (this.propertyData.city && this.propertyData.state && this.propertyData.zip_code) {
              const toRemove = this.capitalize(this.propertyData.city) + ' ' + this.propertyData.state + ' ' + this.propertyData.zip_code
              return this.propertyData.full_address.replace(toRemove, '')
            } else {
              return this.propertyData.full_address
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
        if (this.propertyData.city) {
          address2 += this.capitalize(this.propertyData.city) + ' '
        }
        if (this.propertyData.state) {
          address2 += this.propertyData.state + ' '
        }
        if (this.propertyData.zip_code) {
          address2 += this.propertyData.zip_code
        }
        return address2
      },
      getSaleStatus () {
        if (this.propertyData) {
          if (this.comparable) {
            if (this.propertyData.status) {
              if (this.propertyData.status === 'Active' || this.propertyData.status === 'For Sale') {
                return 'For Sale'
              } else {
                return ''
              }
            } else {
              return ''
            }
          } else {
            if (this.propertyData.for_sale && this.propertyData.for_sale === 'Active') {
              return 'For Sale'
            } else {
              return '--'
            }
          }
        } else {
          return '--'
        }
      },
      isActiveListing () {
        // console.log(this.propertyData)
        let flag = false
        if (this.comparable) {
          if (this.propertyData && this.propertyData.status) {
            if (this.propertyData.status === 'Active' || this.propertyData.status === 'For Sale') {
              if (this.propertyData.list_price) {
                flag = true
              }
            }
          }
        } else {
          if (this.propertyData && this.propertyData.for_sale) {
            if (this.propertyData.for_sale === 'Active') {
              if (this.propertyData.list_price) {
                flag = true
              }
            }
          }
        }
        
        return flag
      },
      getListPrice () {
        let data = '--'
        if (this.comparable) {
          data = ''
        }
        if (this.propertyData ) {
          if (this.comparable) {
            if (this.propertyData.status === 'Active' || this.propertyData.status === 'For Sale') {
              if (this.propertyData.currentlistingprice) {
                data = this.formatNumber(this.propertyData.currentlistingprice, true)
              }
            }
          } else {
            if (this.propertyData.for_sale && this.propertyData.for_sale === 'Active') {
              if (this.propertyData.list_price) {
                data = this.formatNumber(this.propertyData.list_price, true)
              }
            }
          }
        }
        return data
      },
      getHonelyEstimate () {
        if (this.propertyData) {
          if (this.propertyData.appraisal) {
            return this.formatNumber(this.propertyData.appraisal, true)
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getPropertyFacts () {
        if (this.propertyData) {
          let fact = ''
          if (this.propertyData.beds_count) {
            fact += this.propertyData.beds_count + ' bds'
          } else {
            fact += '-- bds'
          }
          if (this.propertyData.baths) {
            fact += ' | ' + this.propertyData.baths + ' ba'
          } else {
            fact += ' | -- ba'
          }
          if (!this.comparable) {
            if (this.propertyData.total_area_sq_ft) {
              fact += ' | ' + this.formatNumber(this.propertyData.total_area_sq_ft, false) + ' sqft'
            } else {
              fact += ' | -- sqft'
            }
          }
          return fact
        } else {
          if (!this.comparable) {
            return '-- bds | -- ba | -- sqft'
          } else {
            return '-- bds | -- ba'
          }
        }
      },
      getPropertyAddress () {
        if (this.propertyData) {
          if (this.propertyData.full_address) {
            return this.propertyData.full_address
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getPropertyComparableScore () {
        let score = '--'
        if (this.comparable && this.propertyData) {
          if (this.propertyData.comparable_score) {
            score = parseFloat(this.propertyData.comparable_score).toFixed(0)
          }
        }
        return score
      },
    },
    updated () {
      // default
    },
    watch: {
      propertyData: function () {
        this.checkImage()
      }
    },
    mounted () {
      // check image
      this.checkImage()
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.$store.getters['auth/cognitoUser'].attributes.email,
          },
        }).then((response) => {
          this.user = response.data
          // this.prepopulateFields()
        }).catch((error) => {
          console.log('[ERROR] Failed to fetch user data', error)
          console.log(error.response.data.errorMessage)
        })
      }
    },
    methods: {
      checkImage () {
        // console.log('getting images')
        const self = this
        if (this.propertyData) {
          const googlePicture = 'https://maps.googleapis.com/maps/api/streetview?size=600x400&location=' + this.propertyData.latitude + ',' + this.propertyData.longitude + '&radius=100&return_error_code=true&source=outdoor&key=' + this.$mapsKey
          let url = googlePicture
          // check if it's listhub property, if true, switch image url
          if (this.propertyData.listingkey && this.propertyData.photoscount) {
            if (this.propertyData.photoscount > 0) {
              url = 'https://listhub-property-images.s3.amazonaws.com/' + this.propertyData.listingkey + '_1.jpg'
            }
          }
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
            // double check google map api
            fetch(googlePicture, {
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
              self.image = googlePicture
              self.validImage = true
            }).catch((err) => {
              // double check google map api
              self.validImage = true
              self.image = '/site_images/listing_default_image.png'
              console.log('[ERROR] Google image API failed =>', err)
            })
            console.log('[ERROR] Google image API/listhub image failed =>', err)
          })
        }
      },
      capitalize(string) {
        if (string) {
          let result = ''
          const strings = string.split(' ')
          for (let i = 0; i < strings.length; i++) {
            result += strings[i].charAt(0).toUpperCase() + strings[i].slice(1).toLowerCase()
            if (i != strings.length - 1) {
              result += ' '
            }
          }
          return result
        } else {
          return string
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
          return '--'
        }
      },
      getPropertyComparableScoreColorCode (score) {
        let colorClass = ''
        if (score) {
          if (score >= 0 && score <= 20) {
            colorClass = 'level1'
          } else if (score >= 21 && score <= 40) {
            colorClass = 'level2'
          } else if (score >= 41 && score <= 60) {
            colorClass = 'level3'
          } else if (score >= 61 && score <= 80) {
            colorClass = 'level4'
          } else if (score >= 81) {
            colorClass = 'level5'
          }
        }
        return colorClass
      },
      toggleFavorite: function (listing) {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          if (!(!this.isFavorite && this.favoriteListings.length === 5)) {
            const payload = {
              property_id: listing.property_id,
              apn: listing.apn,
              fips: listing.fips,
              user_id: this.user.user_id,
              address: listing.full_address,
              appraisal: listing.appraisal,
            }
            if (!this.isFavorite) {
              const paramsTier2 = {
                first_name: this.user.first_name,
                last_name: this.user.last_name,
                user_email: this.user.email,
                phone_number: this.user.phone_number,
                address: listing.full_address,
                zip_code: listing.zip_code,
                tier: '2',
              }
              // axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier2)
            }
            this.$store.dispatch('listings/toggleFavorite', payload)
          } else {
            this.$alert('Cannot have more than 5 favorite listings.')
          }
        } else {
          this.$alert('Please log in or create an account to add this to your favorites.')
        }
      },
      formatPrice (value) {
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
