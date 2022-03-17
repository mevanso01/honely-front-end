<template>
  <!-- eslint-disable -->
  <div id="forecast-band-9" class="mt-0 py-6">
    <v-container>
      <v-row justify="center">
        <v-col
          sm="12"
          :class="{'align-center text-center px-2': $vuetify.breakpoint.smAndDown, 'align-center text-end px-8': $vuetify.breakpoint.mdAndUp}"
        >
          <base-heading
            class="mt-0 forecast-band-header pb-1"
            title=""
            weight="medium"
            size="text-h4"
            space="1"
          >
            <span>
              <!-- 6. -->
              <i class="mdi mdi-account-wrench" aria-hidden="true"/>
            </span>
            Service Providers In Your Area
          </base-heading>
        </v-col>
        <v-container>
          <v-row class="apply-text-row">
            <v-col class="px-8 pt-0 text-start">
              <span class="provider-copy">If you are a real estate agent, lender, or general contractor join our network for free leads and brand exposure.</span>
            </v-col>
            <v-col class="provider-CTA text-end px-8 pt-0">
              <v-btn
                class="provider-network-btn"
                color="#07871c"
                elevation="2"
                large
                @click="goToLeadSubscriptions()"
              >
                <i class="mdi mdi-account-network" aria-hidden="true"/>
                  Apply To Be Part Of Our Network
              </v-btn>
            </v-col>
          </v-row>
        </v-container>
      </v-row>

      <!-- Real Estate Heading -->
      <v-row class="justify-center" v-if="serviceProviders.agents !== null">
        <v-col class="d-flex justify-space-between align-center" cols="11">
          <h2 class="provider-heading">
            <i class="mdi mdi-sign-real-estate" aria-hidden="true"/>
            Real Estate Agents
          </h2>
        </v-col>
      </v-row>

      <!-- Real Estate Cards -->
      <v-container class="d-flex flex-wrap justify-center my-3" v-if="serviceProviders.agents !== null">
        <v-row class="d-flex flex-wrap pa-2 justify-center">
          <v-col
            v-for="agent in serviceProviders.agents"
            :key="agent.agent_id"
            class="d-flex flex-wrap pa-2 justify-center"
            cols="12"
            lg="4"
            md="6"
            sm="12"
          >
          <!-- Real Estate Agent Cards and Form -->
          <contact-agent-form
            :forecast="forecast"
            :agent="agent"
            :user="user">
          </contact-agent-form>
          </v-col>
        </v-row>
      </v-container>

      <!-- Text if real estate agents is missing -->
      <v-container style="display: none !important" class="d-flex flex-wrap justify-center my-3" v-else>
        <p>No Real Estate Agents In Your Area</p>
      </v-container>

      <!-- Financial Lenders Heading -->
      <v-row class="justify-center" v-if="serviceProviders.lenders !== null">
        <v-col cols="11">
          <h2 class="provider-heading">
            <i class="mdi mdi-handshake" aria-hidden="true"/>
              Financial Lenders
          </h2>
        </v-col>
      </v-row>

      <!-- Financial Lenders Cards -->
      <v-container class="d-flex flex-wrap justify-center my-3" v-if="serviceProviders.lenders !== null">
        <v-row class="d-flex flex-wrap pa-2 justify-center">
          <v-col
            v-for="agent in serviceProviders.lenders"
            :key="agent.agent_id"
            class="d-flex flex-wrap pa-2 justify-center"
            cols="12"
            lg="4"
            md="6"
            sm="12"
          >
          <contact-agent-form
            :forecast="forecast"
            :agent="agent"
            :user="user">
          </contact-agent-form>
          </v-col>
        </v-row>
      </v-container>

      <!-- Text if Financial Lenders do not exists -->
      <!-- <v-container style="display: none !important" class="d-flex flex-wrap justify-center my-3" v-else>
        <p>No Lenders In Your Area</p>
      </v-container> -->

      <!-- General Contractors Heading -->
      <v-row class="justify-center" v-if="serviceProviders.general_contractors !== null">
        <v-col cols="11">
          <h2 class="provider-heading">
            <i class="mdi mdi-hard-hat" aria-hidden="true"/>
              General Contractors
          </h2>
        </v-col>
      </v-row>

      <!-- General Contractors Cards -->
      <v-container class="d-flex flex-wrap justify-center my-3" v-if="serviceProviders.general_contractors !== null">
        <v-row class="d-flex flex-wrap justify-center">
          <v-col
            v-for="agent in serviceProviders.general_contractors"
            :key="agent.agent_id"
            class="d-flex flex-wrap justify-center"
            cols="12"
            lg="4"
            md="6"
            sm="12"
          >
          <contact-agent-form
            :forecast="forecast"
            :agent="agent"
            :user="user">
          </contact-agent-form>
          </v-col>
        </v-row>
      </v-container>

      <!-- Text if General contractors do not exist -->
      <!-- <v-container style="display: none !important" class="d-flex flex-wrap justify-center my-3" v-else>
        <p>No Contractors In Your Area</p>
      </v-container> -->

      

      <!-- Misc Heading -->
      <!-- <v-row
        class="justify-center"
      >
        <v-col
          cols="11"
        >
          <h2 class="provider-heading">
            <i
              class="mdi mdi-diversify"
              aria-hidden="true"
            />
            Misc
          </h2>
        </v-col>
      </v-row> -->

      <!-- Misc Cards -->
      <!-- <v-container>
        <v-row class="d-flex flex-wrap pa-2 justify-center">
          <v-col
            v-for="n in 3"
            :key="n"
            class="d-flex flex-wrap pa-2 justify-center"
            cols="12"
            lg="4"
            md="6"
            sm="12"
          >
            <v-card
              class="pa-1"
              elevation="10"
              color="white"
              min-height="170px"
              max-width="375px"
            >
              <v-row>
                <v-col
                  cols="6"
                >
                  <v-img
                    height="150px"
                    src="https://buildfire.com/wp-content/uploads/2016/11/realtor-advertising-strategies-1200x900.jpg"
                  />
                </v-col>
                <v-col
                  cols="6"
                  class="pa-0"
                >
                  <div class="misc-copy">
                    <v-card-title class="pt-1 pl-1">
                      Misc. business name
                    </v-card-title>
                    <v-card-subtitle class="pt-0 pl-1 pb-1">
                      Address
                    </v-card-subtitle>
                    <div class="misc-copy">
                      <v-card-text class="detail-copy pt-1 pl-1">
                        <i
                          class="mdi mdi-phone pr-1"
                          aria-hidden="true"
                        />
                        Phone
                      </v-card-text>
                      <v-card-text class="detail-copy pt-0 pl-1">
                        <i
                          class="mdi mdi-email pr-1"
                          aria-hidden="true"
                        />
                        Email
                      </v-card-text>
                      <v-card-text class="detail-copy pt-0 pl-1 pb-0">
                        <i
                          class="mdi mdi-web pr-1"
                          aria-hidden="true"
                        />
                        Website
                      </v-card-text>
                    </div>
                  </div>
                </v-col>
              </v-row>
            </v-card>
          </v-col>
        </v-row>
      </v-container> -->
    </v-container>
    <login-overlay
      ref="loginOverlay"
      style="text-align: left;"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
    />
  </div>
  <!-- eslint enable -->
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'ServiceProviders',
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      ContactAgentForm: () => import('@/components/base/ContactAgentForm'),
    },
    props: {
      user: Object,
      forecast: Object,
      serviceProviders: Object,
    },
    data: () => ({
      // test data
      testServiceProviders: {
        agents: [{
          agent_id: '1',
          agent_email: 'rrowbredsfsdfgfy1@fda.gov',
          company_name: 'Bubdgsdfdsfdsfdsfblemix',
          first_name: 'Hdgdssfsdfi',
          last_name: 'Rowbrey',
          image_url: 'https://static.photocdn.pt/images/articles/2019/08/07/images/articles/2019/07/31/linkedin_profile_picture_tips-1.jpg',
          license_number: '8470939327',
          phone_number: '422-965-0932',
          user_type: 'Buyer',
        }, {
          agent_id: '2',
          agent_email: 'rrowbsdrey1@fda.gov',
          company_name: 'Bsaubblemix',
          first_name: 'gas',
          last_name: 'Rowgsdabrey',
          image_url: 'https://images.unsplash.com/photo-1531427186611-ecfd6d936c79?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8cHJvZmlsZXxlbnwwfHwwfHw%3D&w=1000&q=80',
          license_number: '84709fsad39327',
          phone_number: '42sd2-965-0932',
          user_type: 'Seller',
        }, {
          agent_id: '3',
          agent_email: 'rrowbsdrey1@fda.gov',
          company_name: 'Bsaubblemix',
          first_name: 'Rodurke',
          last_name: 'Rowabrey',
          image_url: 'https://d2kf8ptlxcina8.cloudfront.net/YH5TFCE1QY-preview.png',
          license_number: '84709fsad39327',
          phone_number: '42sd2-965-0932',
          user_type: 'refinancer',
        }],
        general_contractors: [{
          agent_email: 'rrowbsdrey1@fda.gov',
          company_name: 'Bsaubblemix',
          first_name: 'Rodurke',
          last_name: 'Roabrey',
          image_url: 'https://vengreso.com/wp-content/uploads/2016/03/LinkedIn-Profile-Professional-Picture-Sample-Bernie-Borges.png',
          license_number: '84709fsad39327',
          phone_number: '42sd2-965-0932',
        }],
        lenders: [{
          agent_email: 'rrowbsdrey1@fda.gov',
          company_name: 'Bsaubblemix',
          first_name: 'Rodurke',
          last_name: 'Rowabrey',
          image_url: 'https://d2kf8ptlxcina8.cloudfront.net/YH5TFCE1QY-preview.png',
          license_number: '84709fsad39327',
          phone_number: '42sd2-965-0932',
        }, {
          agent_email: 'rrowbsdrey1@fda.gov',
          company_name: 'Bsaubblemix',
          first_name: 'gsadke',
          last_name: 'Rowgsdey',
          image_url: 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSPFB-br8i8s8z1q-Sg0N7dTMyEWstfOdtlFEY4VxalxKx9vxjGgGKt7VRFDN1P0qClnFA&usqp=CAU',
          license_number: '84709fsad39327',
          phone_number: '42sd2-965-0932',
        }],
      },
      // isSuccess: false,
      // isFailure: false,
      showLoginOverlay: false,
      overlayTab: 'sign-up',
      // firstName: '',
      // firstNameRules: [
      //   v => !!v || 'First Name is required',
      // ],
      // lastName: '',
      // lastNameRules: [
      //   v => !!v || 'Last Name is required',
      // ],
      // email: '',
      // emailRules: [
      //   v => !!v || 'E-mail is required',
      //   v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      // ],
      // phone: '',
      // phoneRules: [
      //   v => !!v || 'Phone number is required',
      // ],
      // serviceProvided: '',
      // serviceRules: [
      //   v => !!v || 'Please choose an option',
      // ],
    }),
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
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      goToLeadSubscriptions () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          if (this.user.user_type === 'AGENT/BROKER' || this.user.user_type === 'LENDER' || this.user.user_type === 'GENERAL CONTRACTOR') {
            window.location.href = '/leads-subscriptions'
          }
        } else {
          this.displayLoginOverlay('create-account')
        }
      },
      submitAgent () {
        const params = {
          first_name: this.firstName,
          last_name: this.lastName,
          email: this.email,
          phone_number: this.phone,
          service: this.serviceProvided,
        }
        axios.post('https://api.honely.com/lookup/agent_information', params)
          .then(() => {
            this.isSuccess = true
            setTimeout(() => {
              this.toggleDialog()
            }, 2000)
          }).catch(() => {
            this.isFailure = true
          })
      },
    },
  }
</script>

<style lang="scss" scoped>
#forecast-band-9{
  background-color: #eeeeee;
}
#service-providers {
  color: #07871c;
  border-bottom: 2px solid #07871c;
}
.provider-copy{
  font-size: 20px;
  font-weight: bold;
}
.provider-network-btn{
    font-size: 15px;
    background: #07871c;
    color: #ffffff !important;
    padding: 0 10px;
    margin-left: 10px;
    border-radius: 3px;
    border: 1px solid #07871c;
    i{
        padding-right: 5px;
        font-size: 1.5rem;
    }
}
.forecast-band-header{
  color: black;
  border-bottom: 2px solid $color-emerald;
}
.provider-modal-heading{
   color: #07871c;
}
.provider-heading{
        display: flex;
        align-items: center;
        font-weight: 400;
        color: #07871c;
        i{
            font-size: 2.5rem;
            margin-right: 5px;
        }
    }
.agent-photo{
    border-radius: 50% !important;
    border: 1px solid black;
}
.agent-details{
    display: flex;
    flex-direction: column;
    justify-content: space-around;
    .agent-detail-copy{
        display: flex;
        align-items: center;
        gap: 3px;
        color: black;
        font-weight: 400;
    }
}
.agent-detail-copy, .lender-copy, .contractor-copy, .misc-copy{
    i{
     font-size: 1.2rem;
     color: #00a650;
    }
}
.detail-copy{
    display: flex;
    align-items: center;
    gap: 3px;
}
.contractor-copy{
    height: 100%;
    margin-top: -25px;
}
@media (max-width: 768px)  {
  .provider-CTA{
    text-align: center !important;
    padding: 0 !important;
  }
  .row.apply-text-row{
    display: block !important
  }
}
@media (max-width: 500px) {
  .provider-network-btn{
    margin-left: 0;
    padding: 10px !important;
  }
}
@media only screen and (max-width: 330px){
  .provider-CTA .provider-network-btn {
    font-size: 10px;
  }
}
</style>
<style scoped>
.submit-button >>> .v-btn__content {
    color: white;
}
</style>
