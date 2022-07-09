<template>
  <!-- eslint-disable -->
  <v-form v-model="valid" :lazy-validation="true" ref="form">
    <!-- <label>Email</label> -->
    <v-text-field
      outlined
      shaped
      dense
      label=""
      placeholder="Email"
      class="auth-modal-form-control"
      @keyup.enter="onSubmit"
      :rules="requiredRules"
      :maxlength="50"
      :readonly="confirmationCodeRequested"
      v-model="email"
    ></v-text-field>
    <!-- <label>Password</label> -->
    <v-text-field
      outlined
      shaped
      dense
      :type="showPassword ? 'text' : 'password'"
      label=""
      placeholder="Password"
      class="auth-modal-form-control"
      :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
      @click:append="showPassword = !showPassword"
      @keyup.enter="onSubmit"
      :rules="requiredRules"
      v-model="password"
      :readonly="confirmationCodeRequested"
    ></v-text-field>
    <span v-if="errTxt" class="error--text" style="margin-bottom: 15px;">{{ errTxt }}</span>
    <div class="d-flex justify-end" @click="changeTab()">
      <a class="primary-link">Forgot your password?</a>
    </div>
    <div v-if="!confirmationCodeRequested" class="d-flex justify-center mt-5">
      <v-btn
        rounded
        color="success"
        class="btn-submit"
        :disabled="spinner"
        @click="onSubmit()"
      >
        {{ spinner ? 'Please wait...' : 'Login' }}
      </v-btn>
      <!-- <circle-spin :loading='true'></circle-spin> -->
    </div>
    <br v-if="confirmationCodeRequested">
    <v-text-field
      v-if="confirmationCodeRequested"
      outlined
      shaped
      dense
      label=""
      placeholder="Email Confirmation Code"
      class="auth-modal-form-control"
      @keyup.enter="onSubmit"
      :rules="requiredRules"
      :maxlength="50"
      v-model="confirmationCode"
    ></v-text-field>
    <div v-if="confirmationCodeRequested" class="d-flex justify-end" @click="resendVerificationCode()">
      <a class="primary-link">Resend email verification code</a>
    </div>
    <div v-if="confirmationCodeRequested" class="d-flex justify-center mt-5">
      <v-btn
        rounded
        color="success"
        class="btn-submit"
        :disabled="spinner"
        @click="onSubmit()"
      >
        {{ spinner ? 'Please wait...' : 'Login' }}
      </v-btn>
      <!-- <circle-spin :loading='true'></circle-spin> -->
    </div>
  </v-form>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import { bus } from '@/main'
  import { REQUIREED_RULE } from '@/utils/validators'

  export default {
    components: {
    },

    props: ['bus', 'redirectPath'],

    data: () => ({
      emailConsent: null,
      valid: false,
      email: '',
      password: '',
      showPassword: false,
      requiredRules: [
        REQUIREED_RULE(),
      ],
      username: null,
      spinner: false,
      errTxt: '',
      confirmationCode: null,
      confirmationCodeRequested: false,
      userType: null,
    }),

    mounted () {
      this.bus.$on('reset', this.reset)
    },
    beforeDestroy () {
      this.bus.$off('reset', this.reset)
    },

    methods: {
      changeTab () {
        this.$emit('stepForgot')
      },
      reset () {
        this.$refs.form.reset()
        this.spinner = false
        this.errTxt = ''
      },
      onSubmit () {
        if (this.$refs.form.validate()) {
          this.spinner = true
          this.errTxt = null
          axios.get('https://api.honely.com/lookup/user_name_fetch?user_identifier=' + this.email)
            .then(async (response) => {
              this.username = response.data.user_name
              if (!this.confirmationCodeRequested) {
                await this.userLogIn()
              } else {
                await this.confirmSignUpAndLogIn()
              }
            })
            .catch((error) => {
              this.spinner = false
              // console.log('vx: user_name_fetch error', error.response.data.message)
              if (error.response.data.message === 'No user found.') {
                this.errTxt = 'No user found. Please create an account instead.'
              }
            })
        }
      },
      /* eslint-disable */
      async userLogIn () {
        this.$store.dispatch('auth/cognitoSignIn',{username : this.username, password: this.password})
        .then(()=>{
          // store user profile data
          this.getUserProfile ()
          this.spinner = false
          this.$store.dispatch('auth/setLeadDetails', null)
          this.$store.dispatch('auth/setLeadZipCodeList', null)
          this.$emit('successfulLogin')
          bus.$emit('successfulLogin')
          // console.log("vx: login path: " + this.$route.path)
          if (this.$route.path) {
            // if is one of the following paths, reload the page
            if (this.$route.path === '/subscribe' || this.$route.path === '/leadmoderation'  || this.$route.path.startsWith('/forecast') || this.$route.path === '/smart-data-subscription') {
              // console.log('vx: this.$route.path', this.$route.path)
              if (this.redirectPath) {
                this.$router.push(this.redirectPath)
              } else {
                this.$router.go()
              }
            }
            // if one of the follwing paths, replace the path
            if (this.$route.path.startsWith('/listing')) {
              this.$router.replace(this.$route.path).catch(()=>{})
            }
          }
        }).catch((error)=>{
          this.spinner = false
          if(error.message=='Cannot read property \'username\' of null'){
            this.errTxt='Please enter Email ID'
          } else if (error.message=='Custom auth lambda trigger is not configured for the user pool.') {
            this.errTxt='Please enter password.'
          } else if (error.message== 'Password attempts exceeded') {
            this.errTxt='You have exceeded the limit for incorrect password atttempts. Please try again after 5 minutes.'
            var y = ((new Date()).getFullYear()).toString()
            var m = ((new Date()).getMonth()+1).toString()
            m=(m.length==1)?('0'+m):m
            var d = ((new Date()).getDate()).toString()
            d=(d.length==1)?('0'+d):d
            var h = ((new Date()).getHours()).toString()
            h=(h.length==1)?('0'+h):h
            var min = ((new Date()).getMinutes()).toString()
            min=(min.length==1)?('0'+min):min
            var s = ((new Date()).getSeconds()).toString()
            s=(s.length==1)?('0'+s):s
            var ans = y+'-'+m+'-'+d+' '+h+':'+min+':'+s+'.00000'
            axios.post('https://api.honely.com/lookup/invalid-password', {
              email: this.username,
              timestamp: ans,
            })
          } else if (error.message.includes('PreAuthentication failed with error You had exceeded the limit for incorrect password attempts.')) {
            this.errTxt=error.message.substring(36)
          } else if (error.message == 'User is not confirmed.') {
            this.$store.dispatch('auth/cognitoResendConfirmationCode',{username : this.username})
            .then(() => {
              this.errTxt='User is not confirmed. Please enter the email confirmation code sent to your inbox.'
              this.confirmationCodeRequested=true
              axios.get('https://api.honely.com/lookup/unconfirmed_user_fetch?user_name='+ this.username)
              .then((response)=>{
                this.first_name= response.data.first_name
                this.last_name= response.data.last_name
                this.email= response.data.email
                this.phone_number= response.data.phone_number
                this.emailConsent = response.data.email_consent
                this.userType = response.data.user_type
              })
            })
          } else {
            this.errTxt=error.message
          }
            // console.log('vx: error signing in', error)
        })
      },
      async confirmSignUp() {
        var siteLeads = "FALSE"
        if (this.userType.includes('AGENT/BROKER') || this.userType.includes('LENDER') || this.userType.includes('GENERAL CONTRACTOR')) {
          siteLeads = "TRUE"
        }
        const params = {
          user_name: this.username,
          first_name: this.first_name,
          last_name: this.last_name,
          email: this.email,
          phone_number: this.phone_number,
          membership_type: 'FREE',
          email_consent: this.emailConsent,
          promo_code: '',
          user_type: this.userType,
          site_leads: siteLeads,
          button_leads: "FALSE",
          home_url: null,
          interested_zip_codes: '',
          home_address: '',
          home_zip_code: '',
          company_name: '',
        }
        if(this.confirmationCode) {
          try {
            await this.$store.dispatch('auth/cognitoConfirmSignUp',{username : this.username, code: this.confirmationCode})
            this.errTxt=null
            await axios.post('https://api.honely.com/lookup/register_service', params)
            await this.userLogIn()
            await axios.delete('https://api.honely.com/lookup/unconfirmed_user_deletion', {data: {email: this.email}})
            this.confirmationCodeRequested=false
          } catch (error) {
            // console.log('vx: Error while confirming and signing in', error)
            this.errTxt=error.message
            this.showLoading = false
          }
        } else {
          this.errTxt='Confirmation Code cannot be empty'
        }
      },
      async confirmSignUpAndLogIn() {
        try {
          await this.confirmSignUp()
          .then(()=>{
            // this.userLogIn()
          })
          // await axios.post('https://api.honely.com/lookup/register_service', params)
        } catch (error) {
          // console.log('vx: Error while confirming and signing in', error)
          this.errTxt=error.message
        }
      },
      async resendVerificationCode () {
        axios.get('https://api.honely.com/lookup-test/email_verification_service?email='+this.email)
        .then(async () => {
          this.errTxt='Too much time has elapsed. Please sign up again.'
        }).catch(async ()=>{
          await this.$store.dispatch('auth/cognitoResendConfirmationCode',{username : this.username})
          this.errTxt='Resent Email Verification Code!'
          this.confirmationCodeRequested=true
        })
      },
      getUserProfile () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          axios.get('https://api.honely.com/lookup-test/user_profile', {
            params: {
              email: this.$store.getters['auth/cognitoUser'].attributes.email,
            },
          }).then((response) => {
            // console.log(response.data)
            this.$store.dispatch('auth/setUserProfile', response.data)
          }).catch((error) => {
            console.log('[ERROR] Failed to fetch user data')
            console.log(error.response.data.errorMessage)
          })
        }
      },
      /* eslint-enable */
    },
  }
</script>
