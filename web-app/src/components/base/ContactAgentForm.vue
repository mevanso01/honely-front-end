<template>
  <!-- eslint-disable -->
  <v-dialog v-model="dialog" persistent max-width="500px">
    <template #activator="{ on, attrs }">
      <div v-bind="attrs" v-on="on">
        <template>
          <!-- Agent Card UI -->
          <div v-if="agent && agent!=null">
            <agent-card
             :agent="agent"
            >
            </agent-card>
          </div>
          <div v-else>
            <!-- <button class="contact-agent-button" @click.prevent="">
              <i class="mdi mdi-account-wrench" aria-hidden="true"/>
                {{getCardTitle()}}
           </button> -->
          </div>
         </template>
      </div>
    </template>
    <!-- Pop up -->
    <template #default="dialog">
      <div class="contact-agent-form-card">        
        <div class="dialog-header-wrapper">
          <div class="dialog-title-wrapper">
            <p class="dialog-heading">Get the <span>Future Value</span> of this Property!</p>
            <p class="amount-txt" v-if="estimatedValueLoaded && estimatedValue">${{ formatValue(estimatedValue) }}</p>
            <p v-if="estimatedValueLoaded && estimatedValue">Current Value Estimate for:</p>
            <p v-if="estimatedValueLoaded && estimatedValue">{{ address }}</p>
          </div>
          <div class="btn-close" @click="toggleDialog">
            <i class="fa fa-times"/>
          </div>
        </div>
        <form 
          :class="`form-contact` + (estimatedValueLoaded && estimatedValue ? ' mt-4' : ' mt-5')" 
          v-model="valid" 
          :lazy-validation="true">          
          <label class="control-label">Name</label>          
          <div class="input-container mt-2">
            <v-text-field
              outlined
              shaped
              dense
              label=""
              prepend-inner-icon="mdi-account"
              class="contact-agent-form-control"
              v-model="firstName"
              :maxlength="30"              
              placeholder="e.g Jonathan Shah"
             ></v-text-field>
          </div>                 
          <label>Email</label>
          <div class="input-container mt-2">
            <v-text-field
              outlined
              shaped
              dense
              label=""
              prepend-inner-icon="mdi-email"
              class="contact-agent-form-control"
              :rules="emailRules"
              :maxlength="50"
              v-model="email"            
              placeholder="Email"
             ></v-text-field>
          </div>            
          <label>Phone number (optional)</label>
           <div class="input-container mt-2">
            <v-text-field
              outlined
              shaped
              dense
              label=""
              placeholder="e.g 555-555-5555"
              prepend-inner-icon="mdi-phone"
              class="contact-agent-form-control"      
              @keypress="isNumber($event)"
              :rules="phoneRules"
              :maxlength="10"
              v-model="phone"
            ></v-text-field>
          </div>          
          <label v-if="agent">Message</label>
          <div v-if="agent" class="input-container partner-mt-2">
            <textarea
              rows="5"
              type="text"                            
              placeholder=""
              class="form-control form-control-lg name"              
              v-model="message"
            ></textarea>
          </div>
          
          <div v-if="!($route.name === 'Buying Landing Page' || $route.name === 'Selling Landing Page' || $route.name === 'Refinancing Landing Page' || $route.name === 'Investing Landing Page')">
          <div class="text-h5 mt-0 mb-2">Select Your Purpose</div>
          <div class="purposes-box">
            <div class="btn-purpose" v-for="item in Object.keys(purposes)" @click="selectPurpose(item)">
              <div :class="`content-box ${purposes[item].value ? 'active' : ''}`">
                {{ purposes[item].label }}
              </div>
            </div>
          </div>
          </div>
          <p v-if="leadFormErrorMessage" style="color: red; margin-bottom: 0px;">{{ leadFormErrorMessage }}</p>
          <div class="d-flex flex-column justify-center mt-5">
            <div class="d-flex justify-center">
              <v-btn
                v-if="!leadGenerating"
                @click="agent ? submitLead(agent.agent_id, agent.agent_email, searchedAddress, searchedZipcode, agent.user_type) : submitRandomLead()"
                :disabled="valid"
                color= "#06A550"
                class="btn-submit px-10 py-5"
              >
                Get Free Forecast Report
              </v-btn>
              <div
                id="leadGeneratingLoadingSpinner"
                v-if="leadGenerating"
                >
                  <circle-spin :loading='true'></circle-spin>
              </div>
            </div>
            <div class="d-flex justify-center mt-5 mb-5">              
              <div
                v-if="!leadGenerating"
                class="px-15 py-4 btn-login"
                :disabled="valid"   
                @click="loginDialog"             
              >
                Log in instead
              </div>
            </div>
            <!-- <div class="d-flex justify-center">
              <span class="btn-login mt-2">Log In Instead</span>
            </div> -->
          </div>
        </form>
      </div>
      <login-popup
        :show="showLogin"
        @toggleShow="toggleLoginPopupShow"
      />
    </template>    
  </v-dialog>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapGetters } from 'vuex'
  import { REQUIRED_EMAIL_RULE, REQUIRED_PHONE_RULE } from '@/utils/validators'
  export default {
    name: 'ContactAgentForm',
    components: {
      AgentCard: () => import('@/components/base/AgentCard'),
      LoginPopup: () => import('@/components/login_popup/Index'),
    },
    props: {
      forecast: Object,
      agent: Object,
      user: Object,
      yearForecast: Number,
      searchQuery: String,
    },
    mounted () {
      console.log('vx: here are the props')
      console.log('vx: forecast', this.forecast)
      console.log('vx: agent', this.agent)
      console.log('vx: user', this.user)
      console.log('vx: yearForecast', this.yearForecast)
      console.log('vx: searchQuery', this.searchQuery)
      if (this.user && this.user.first_name) this.firstName = this.user.first_name
      if (this.user && this.user.email) this.email = this.user.email
      if (this.user && this.user.phone) this.phone = this.user.phone
      const leadDetails = this.$store.getters['auth/selectedHomeOwnerTypes']
      if (!this.user && leadDetails !== null) {
        this.firstName = leadDetails.firstName
        this.lastName = leadDetails.lastName
        this.email = leadDetails.email
        this.phone = leadDetails.phone
        this.purposes.buying.value = leadDetails.isBuying
        this.purposes.selling.value = leadDetails.isSelling
        this.purposes.refinancing.value = leadDetails.isRefinancing
        this.purposes.browsing.value = leadDetails.isJustBrowsing
      }
      this.getEstimatedValue()
    },
    data: () => ({
      leadGenerating: false,
      address: null,
      estimatedValue: null,
      estimatedValueLoaded: false,
      valid: false,
      firstName: null,
      lastName: null,
      email: null,
      dialog: true,
      showLogin: false,
      emailRules: [
        REQUIRED_EMAIL_RULE(),
      ],
      phone: null,
      phoneRules: [
        // v => (v != null && v.length === 10) || 'Phone number must contain 10 digits',
        // v => /^\d{10}$/.test(v) || v === null || v === '' || 'Phone number must contain 10 digits'
        REQUIRED_PHONE_RULE(),
      ],
      message: '',
      contactLendersChecked: false,
      contactGeneralContractorsChecked: false,
      leadFormErrorMessage: '',
      purposes: {
        buying: { label: 'Buying', value: false },
        selling: { label: 'Selling', value: false },
        refinancing: { label: 'Refinancing', value: false },
        browsing: { label: 'Just Browsing', value: false },
      },
    }),
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser', 'leadDetails', 'leadZipCodeList']),
      searchedAddress () {
        if (this.forecast && this.forecast.property_forecast) {
          return this.forecast.property_forecast.address
        } else if (this.forecast) {
          return this.forecast.zipcode
        } else {
          return ''
        }
      },
      searchedZipcode () {
        if (this.forecast) {
          return this.forecast.zipcode
        } else {
          return ''
        }
      },
      isBuying () {
        return this.purposes.buying.value
      },
      isSelling () {
        return this.purposes.selling.value
      },
      isRefinancing () {
        return this.purposes.refinancing.value
      },
      isJustBrowsing () {
        return this.purposes.browsing.value
      },
    },
    methods: {
      formatValue (value) {
        if (value) {
          value = value.toString().split(',')[0]
          value = parseInt(value)
          value = (value % 100 >= 50) ? Math.ceil(value / 100) * 100 : Math.floor(value / 100) * 100
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
      getEstimatedValue () {
        axios.get('https://api.honely.com/lookup/forecast', {
          params: {
            address: this.searchQuery,
          },
        }).then((response) => {
          // console.log('response', response)
          if (response) {
            this.estimatedValue = response.data.property_forecast?.appraisal
            this.address = response.data.property_forecast?.address
            this.estimatedValueLoaded = true
          }
        })
          .catch((error) => {
            this.estimatedValueLoaded = true
            console.log(error)
          })
      },
      getCardTitle () {
        var cardTitle = 'Access a Full Forecast Report Now!'
        // if (this.yearForecast !== 1) cardTitle += 's'
        // cardTitle += ' Forecast'
        if (this.agent) {
          cardTitle = 'Contact Service Provider'
        }
        return cardTitle
      },
      submitLead (agentId, email, currAddress, zipcode, userType, agentPhoneNumber) {
        // console.log(agentId, email, currAddress, zipcode, userType)
        if (!this.firstName || !this.lastName || !this.email || !this.phone || (!this.isBuying && !this.isSelling && !this.isRefinancing && !this.isJustBrowsing)) {
          this.leadFormErrorMessage = 'Please enter all the fields!'
          setTimeout(() => {
            this.leadFormErrorMessage = ''
          }, 2000)
        } else {
          const params = {
            first_name: this.firstName,
            last_name: this.lastName,
            user_email: this.email,
            phone_number: this.phone,
            agent_email: email,
            address: currAddress,
            tier: '1',
            agent_id: agentId,
            message: this.message,
            // vx: agent_phone_number and user_type
            agent_phone_number: agentPhoneNumber,
            user_type: userType,
          }
          axios.post('https://api.honely.com/lookup-test/leads_tier_notification', params)
            .then(() => {
              // this.leadFormErrorMessage = 'Form Submitted Successfully!'
              this.toggleDialog()
              setTimeout(() => {
                this.leadFormErrorMessage = ''
              }, 2000)
              const paramsTier3 = {
                first_name: this.firstName,
                last_name: this.lastName,
                user_email: this.email,
                phone_number: this.phone,
                user_type: userType,
                address: currAddress,
                tier: '3',
                zip_code: zipcode,
                link_id: String(Date.now()),
              }
              axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier3)
            })
            .catch(() => {
              this.leadFormErrorMessage = 'Something went wrong. Please refresh the page and try again.'
            })
        }
      },
      submitRandomLead () {
        // TO BE IMPLEMENTED
        if (!this.firstName || !this.email) {
          this.leadFormErrorMessage = 'Please enter all the fields!'
          setTimeout(() => {
            this.leadFormErrorMessage = ''
          }, 2000)
        } else if (!(this.$route.name === 'Buying Landing Page' || this.$route.name === 'Selling Landing Page' || this.$route.name === 'Refinancing Landing Page' || this.$route.name === 'Investing Landing Page') && (!this.isBuying && !this.isSelling && !this.isRefinancing && !this.isJustBrowsing)) {
          this.leadFormErrorMessage = 'Please enter all the fields!'
          setTimeout(() => {
            this.leadFormErrorMessage = ''
          }, 2000)
        } else {
          gtag('event', 'conversion', {'send_to': 'AW-10805687182/RY9SCNrA06UDEI7nxqAo',
            'value': 1.0,
            'currency': 'USD'
          })
          this.leadGenerating = true
          this.$store.dispatch('auth/setLeadDetails', null)
          var leadDetails = {
            firstName: this.firstName,
            lastName: this.lastName,
            email: this.email,
            phone: this.phone,
            isBuying: this.isBuying,
            isSelling: this.isSelling,
            isRefinancing: this.isRefinancing,
            isJustBrowsing: this.isJustBrowsing,
          }
          this.$store.dispatch('auth/setLeadDetails', leadDetails)
          var leadZipCodeList = this.$store.getters['auth/leadZipCodeList']
          this.$store.dispatch('auth/setLeadZipCodeList', null)
          if (leadZipCodeList === null) {
            leadZipCodeList = []
          }
          if (this.forecast) {
            leadZipCodeList.push(this.forecast.zipcode)
          }
          if (this.searchQuery) {
            leadZipCodeList.push(this.searchQuery.substring(this.searchQuery.length - 5, this.searchQuery.length))
          }
          this.$store.dispatch('auth/setLeadZipCodeList', leadZipCodeList)
          var tier2Targets = []
          // if (this.isJustBrowsing || this.isRefinancing) {
          //   tier2Targets.push('LENDER')
          // }
          // if (this.isBuying || this.isSelling) {
          //   tier2Targets.push('AGENT/BROKER')
          // }
          // if (this.isSelling) {
          //   tier2Targets.push('GENERAL CONTRACTOR')
          // }
          if (this.$route.name === 'Buying Landing Page' || this.$route.name === 'Investing Landing Page' || this.isBuying || this.$route.name === 'Selling Landing Page' || this.isSelling || this.isJustBrowsing) {
            tier2Targets.push('AGENT/BROKER')
          }
          if (this.$route.name === 'Buying Landing Page' || this.$route.name === 'Investing Landing Page' || this.isBuying || this.$route.name === 'Refinancing Landing Page' || this.isRefinancing) {
            tier2Targets.push('LENDER')
          }
          var addressParam = null
          var zipCodeParam = null
          if (this.forecast) {
            addressParam = this.forecast.zipcode
            zipCodeParam = this.forecast.zipcode
          }
          if (this.searchQuery) {
            addressParam = this.searchQuery
            zipCodeParam = this.searchQuery.substring(this.searchQuery.length - 5, this.searchQuery.length)
          }
          var messageParam = ''
          var endUserTypes = []
          if (this.$route.name === 'Buying Landing Page' || this.$route.name === 'Selling Landing Page' || this.$route.name === 'Refinancing Landing Page' || this.$route.name === 'Investing Landing Page') {
            if (this.$route.name === 'Buying Landing Page') endUserTypes.push('BUYING')
            if (this.$route.name === 'Selling Landing Page') endUserTypes.push('SELLING')
            if (this.$route.name === 'Refinancing Landing Page') endUserTypes.push('REFINANCER')
            if (this.$route.name === 'Investing Landing Page') endUserTypes.push('BUYING')
          } else {
            if (this.isBuying) {
              endUserTypes.push('BUYING')
            }
            if (this.isSelling) {
              endUserTypes.push('SELLING')
            }
            if (this.isRefinancing) {
              endUserTypes.push('REFINANCING')
            }
            if (this.isJustBrowsing) {
              endUserTypes.push('JUST BROWSING')
            }
          }
          messageParam = endUserTypes.join(',')
          const paramsTier2 = {
            first_name: this.firstName,
            last_name: this.lastName,
            user_email: this.email,
            phone_number: this.phone,
            address: addressParam,
            zip_code: zipCodeParam,
            message: messageParam,
            user_types: tier2Targets,
            tier: '2.1',
            link_id: String(Date.now()),
          }
          axios.post('https://api.honely.com/lookup-test/bad_word_check', paramsTier2)
          axios.get('https://api.honely.com/lookup-test/is_open_window?zip_code=' + zipCodeParam)
            .then((response) => {
              console.log('vx: content for the queue', paramsTier2)
              var AWS = require('aws-sdk')
              AWS.config.update({
                accessKeyId: 'AKIAQUKOD44IIRAEIOOS',
                secretAccessKey: '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',
                region: 'us-east-1',
              })
              // Set the region
              // Create an SQS service object
              var sqs = new AWS.SQS({ apiVersion: '2012-11-05' })
              var params = {
                // Remove DelaySeconds parameter and value for FIFO queues
                DelaySeconds: 1,
                MessageBody: JSON.stringify(paramsTier2),
                QueueUrl: response.data.target_url,
              }
              const searchAddr = this.searchQuery
              sqs.sendMessage(params, function (err, data) {
                if (err) {
                  console.log('Error', err)
                  // if (searchAddr) {
                  //   this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
                  // }
                } else {
                  console.log('Success', data.MessageId)
                  // if (searchAddr) {
                  //   this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
                  // }
                }
              })
              setTimeout(() => {
                if (searchAddr) {
                  if (this.$route.path.startsWith('/forecast')) {
                    this.dialog = !this.dialog
                  } else {
                    this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
                  }
                }
              }, 2000)
              // if (response.data.is_open_window) {
              //   axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier2)
              //     .then(() => {
              //       if (this.searchQuery) {
              //         this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
              //       }
              //     })
              //     .catch((error) => {
              //       if (error.response.status === 404) {
              //         this.dialog = false
              //         if (this.searchQuery) {
              //           this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
              //         }
              //       } else {
              //         this.leadFormErrorMessage = 'Something went wrong. Please refresh the page and try again.'
              //       }
              //     })
              // } else {
              //   console.log('vx: content for the queue', paramsTier2)
              //   var AWS = require('aws-sdk')
              //   AWS.config.update({
              //     accessKeyId: 'AKIAQUKOD44IIRAEIOOS',
              //     secretAccessKey: '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',
              //     region: 'us-east-1',
              //   })
              //   // Set the region
              //   // Create an SQS service object
              //   var sqs = new AWS.SQS({ apiVersion: '2012-11-05' })
              //   var params = {
              //     // Remove DelaySeconds parameter and value for FIFO queues
              //     DelaySeconds: 1,
              //     MessageBody: JSON.stringify(paramsTier2),
              //     QueueUrl: response.data.target_url,
              //   }
              //   const searchAddr = this.searchQuery
              //   sqs.sendMessage(params, function (err, data) {
              //     if (err) {
              //       console.log('Error', err)
              //       // if (searchAddr) {
              //       //   this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
              //       // }
              //     } else {
              //       console.log('Success', data.MessageId)
              //       // if (searchAddr) {
              //       //   this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
              //       // }
              //     }
              //   })
              //   setTimeout(() => {
              //     if (searchAddr) {
              //       this.$router.push({ name: 'Smart Search', query: { address: this.searchQuery } })
              //     }
              //   }, 2000)
              // }
            })
        }
      },
      toggleDialog () {
        if (this.$route.name === 'Buying Landing Page' || this.$route.name === 'Selling Landing Page' || this.$route.name === 'Refinancing Landing Page' || this.$route.name === 'Investing Landing Page') {
          this.dialog = !this.dialog
          if (this.$route.name === 'Buying Landing Page') window.location.href = '/buying'
          else if (this.$route.name === 'Selling Landing Page') window.location.href = '/selling'
          else if (this.$route.name === 'Refinancing Landing Page') window.location.href = '/refinancing'
          else if (this.$route.name === 'Investing Landing Page') window.location.href = '/investing'
          return
        }
        window.location.href = '/'
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
      selectPurpose (item) {
        this.purposes[item].value = !this.purposes[item].value
        // const selected = this.purposes[item].value = !this.purposes[item].value
        // if (selected) {
        // for (const key in this.purposes) {
        // if (key !== item) {
        //   this.purposes[key].value = false
        // }
        // }
        // }
      },
      loginDialog () {
        this.dialog = !this.dialog
        this.showLogin = !this.showLogin
      },
      toggleLoginPopupShow (value) {
        this.showLogin = value
        window.location.href = '/'
      },
    },
  }
</script>
<style scoped>
#leadGeneratingLoadingSpinner {
  min-height: 40px;
}
#leadGeneratingLoadingSpinner >>> .sk-fading-circle {
  margin: 0px auto;
  position: absolute;
  left: 46%;
}
</style>
