<template
  scrollable
>
  <div>
    <v-form
      v-model="valid"
      :lazy-validation="lazy"
    >
      <p><b>Email ID</b></p>
      <v-text-field
        v-model="user_identifier"
        :rules="userIdentifierRules"
        name="login"
        class="login-input"
        type="text"
        placeholder="enter email address"
        outlined
        :readonly="confirmationCodeRequested"
        @keyup.enter="loginProcedure"
      />
      <p><b>Password</b></p>
      <v-text-field
        id="password"
        v-model="password"
        name="password"
        class="login-input"
        type="password"
        placeholder="enter password"
        :rules="passwordRules"
        outlined
        :readonly="confirmationCodeRequested"
        @keyup.enter="loginProcedure"
      />
      <v-btn
        text
        class="forgot-password"
        @click="changeTab()"
      >
        <small>Forgot your password?</small>
      </v-btn>
      <div
        v-if="confirmationCodeRequested"
      >
        <p><b>Enter Email Confirmation Code</b></p>
        <v-text-field
          v-model="confirmationCode"
          name="confirmationcode"
          class="login-input"
          type="text"
          placeholder="enter confirmation code"
          outlined
        />
      </div>
    </v-form>
    <span class="error--text">{{ authError }}</span>

    <v-btn
      v-if="!confirmationCodeRequested && !showLoading"
      :disabled="!valid || !(user_identifier && password)"
      block
      color="primary"
      class="login-button"
      @click="loginProcedure"
      @keyup.native.enter="loginProcedure"
    >
      Sign In
    </v-btn>
    <div
      v-if="showLoading"
      id="signinLoadingSpinner"
    >
      <circle-spin :loading="true" />
    </div>
    <v-btn
      v-if="confirmationCodeRequested"
      block
      color="primary"
      class="login-button"
      @click="resendVerificationCode"
      @keyup.native.enter="resendVerificationCode"
    >
      Resend Email Verification Code
    </v-btn>
    <v-btn
      v-if="confirmationCodeRequested"
      block
      color="primary"
      class="login-button"
      @click="confirmSignUp"
      @keyup.native.enter="confirmSignUp"
    >
      Confirm Sign Up
    </v-btn>
  </div>
</template>

<script>
/* eslint-disable */
  import { authMethods } from '@/store/helpers'
  import { bus } from '@/main'

  export default {
    data: () => ({
      emailConsent: null,
      valid: false,
      lazy: true,
      showLoading: false,
      user_identifier: null,
      username: null,
      password: null,
      authError: null,
      hasLoginError: false,
      confirmationCodeRequested: false,
      confirmationCode: null,
      first_name: null,
      last_name: null,
      email: null,
      phone_number: null,
      userIdentifierRules: [
        v => !!v || 'This is a mandatory field',
      ],
      passwordRules: [
        v => !!v || 'This is a mandatory field',
      ],
    }),
    methods: {
      ...authMethods,
      changeTab () {
        this.$emit('changeTab','forgot-password')
      },
      // try to log the user in
      validate () {
        this.$refs.form.validate()
      },
      tryToLogIn () {
        this.tryingToLogIn = true
        this.hasLoginError = false
        // Reset authError if it existed
        this.authError = null
        return this.logIn({
          username: this.username,
          password: this.password,
        })
          .then(() => {
            this.tryingToLogin = false

            // TODO: if this is not in an overlay, need to go to next router route
            // Should set a prop boolean of isInOverlay or similar
            // this.$router.push(this.$route.query.redirectFrom || { name: 'Home' })
            this.$emit('successfulLogin')
            bus.$emit('successfulLogin')
          })
          .catch((error) => {
            // console.log(error)
            this.tryingToLogIn = false
            this.hasLoginError = true
            // todo catch 401 not authenticated
            this.authError = 'Invalid username or password'
          })
      },
      loginProcedure () {
        if(this.user_identifier && this.password) {
          this.showLoading = true
          this.authError = null
          axios.get('https://api.honely.com/lookup/user_name_fetch?user_identifier='+this.user_identifier)
          .then(async (response)=>{
            this.username=response.data.user_name
            await this.userLogIn()
          })
          .catch((error)=>{
            this.showLoading = false
            // console.log('vx: user_name_fetch error', error.response.data.message)
            if(error.response.data.message === 'No user found.') {
              this.authError='No user found. Please create an account instead.'
            }
          })
        }
      },
      async userLogIn () {
          this.$store.dispatch('auth/cognitoSignIn',{username : this.username, password: this.password})
          .then(()=>{
            this.$store.dispatch('auth/setLeadDetails', null)
            this.$store.dispatch('auth/setLeadZipCodeList', null)
            this.$emit('successfulLogin')
            bus.$emit('successfulLogin')
            // console.log("vx: login path: " + this.$route.path)
            if (this.$route.path) {
              // if is one of the following paths, reload the page
              if (this.$route.path === '/subscribe' || this.$route.path.startsWith('/forecast')) {
                // console.log('vx: this.$route.path', this.$route.path)
                this.$router.go()
              }
              // if one of the follwing paths, replace the path
              if (this.$route.path.startsWith('/listing')) {
                this.$router.replace(this.$route.path).catch(()=>{})
              }
            }
          }).catch((error)=>{
            this.showLoading = false
            if(error.message=='Cannot read property \'username\' of null'){
              this.authError='Please enter Email ID'
            } else if (error.message=='Custom auth lambda trigger is not configured for the user pool.') {
              this.authError='Please enter password.'
            } else if (error.message== 'Password attempts exceeded') {
              this.authError='You have exceeded the limit for incorrect password atttempts. Please try again after 5 minutes.'
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
              this.authError=error.message.substring(36)
            } else if (error.message == 'User is not confirmed.') {
              this.$store.dispatch('auth/cognitoResendConfirmationCode',{username : this.username})
              .then(() => {
                this.authError='User is not confirmed. Please enter the email confirmation code sent to your inbox.'
                this.confirmationCodeRequested=true
                axios.get('https://api.honely.com/lookup/unconfirmed_user_fetch?user_name='+ this.username)
                .then((response)=>{
                  this.first_name= response.data.first_name
                  this.last_name= response.data.last_name
                  this.email= response.data.email
                  this.phone_number= response.data.phone_number
                  this.emailConsent = response.data.email_consent
                })
              })
            } else {
              this.authError=error.message
            }
              // console.log('vx: error signing in', error)
          })
      },
      async resendVerificationCode () {
        axios.get('https://api.honely.com/lookup/email_verification_service?email='+this.email)
        .then(async () => {
          this.authError='Too much time has elapsed. Please sign up again.'
        }).catch(async ()=>{
          await this.$store.dispatch('auth/cognitoResendConfirmationCode',{username : this.username})
          this.authError='Resent Email Verification Code!'
          this.confirmationCodeRequested=true
        })
      },
      async confirmSignUpAndLogIn() {
        try {
          await this.confirmSignUp()
          // .then(()=>{
          //   this.userLogIn()
          // })
          // await axios.post('https://api.honely.com/lookup/register_service', params)
        } catch (error) {
          // console.log('vx: Error while confirming and signing in', error)
          this.authError=error.message
        }
      },
      async confirmSignUp() {
        const params = {
          user_name: this.username,
          first_name: this.first_name,
          last_name: this.last_name,
          email: this.email,
          phone_number: this.phone_number,
          membership_type: 'FREE',
          email_consent: this.emailConsent,
          promo_code: '',
        }
        if(this.confirmationCode) {
          try {
            await this.$store.dispatch('auth/cognitoConfirmSignUp',{username : this.username, code: this.confirmationCode})
            this.authError=null
            this.confirmationCodeRequested=false
            await axios.post('https://api.honely.com/lookup/register_service', params)
            await this.userLogIn()
            await axios.delete('https://api.honely.com/lookup/unconfirmed_user_deletion', {data: {email: this.email}})
          } catch (error) {
            // console.log('vx: Error while confirming and signing in', error)
            this.authError=error.message
            this.showLoading = false
          }
        } else {
          this.authError='Confirmation Code cannot be empty'
        }
      },
    },
  }
</script>

<style lang="sass">
  .login-input
    height: 54px

  .login-input.v-text-field--outlined .v-input__control > .v-input__slot
    min-height: 36px

  .login-button
    margin-top: 15px !important
    margin-bottom: 20px !important
    font-size: 17px !important
    font-family: $heading-font-family
    letter-spacing: .05em
</style>
<style scoped>
#signinLoadingSpinner {
  min-height: 40px;
}
#signinLoadingSpinner >>> .sk-fading-circle {
  margin: 0px auto;
  position: absolute;
  left: 45%;
}
.forgot-password{
  float: right;
  padding-bottom: 15px;
  color: #00a650 !important;
}
.forgot-password v-btn:hover{
  cursor: pointer;
}
</style>
