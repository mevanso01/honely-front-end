<template>
  <div>
    <div v-if="!processComplete">
      <v-form>
        <div v-if="!requestSent">
        <p><b>Email</b></p>
        <v-text-field
          v-model="email"
          :rules="emailRules"
          class="login-input"
          placeholder="enter your email address"
          required
          outlined
        />
        </div>
        <div v-if="requestSent">
        <p><b>Confirmation Code</b></p>
        <v-text-field
          v-model="code"
          :rules="codeRules"
          class="login-input"
          placeholder="enter the confirmation code"
          required
          outlined
        />
        </div>
        <div v-if="requestSent">
          <p><b>New Password</b></p>
        <v-text-field
          class="password-col login-input"
          id="password"
            v-model="password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="passwordRules"
            :type="show1 ? 'text' : 'password'"
            placeholder="enter the new password"
            hint=""
            counter
            outlined
            required
            @click:append="show1 = !show1"
        />
        </div>
        <div>
        </div>
      </v-form>
      <br>
      <span class="error--text">{{ regError }}</span>

      <v-btn
        v-if="!requestSent && !showLoading"
        :disabled="!valid"
        block
        color="primary"
        class="login-input request-button"
        @click="validate && cognitoSendForgotPasswordCode()"
        @keyup.native.enter="valid && cognitoSendForgotPasswordCode($event)"
      >
        Request Password Reset
      </v-btn>
      <div
        id="forgotPasswordLoadingSpinner"
        v-if="showLoading"
        >
          <circle-spin :loading='true'></circle-spin>
      </div>
      <v-btn
        v-if="requestSent && !showLoading2"
        :disabled="!valid"
        block
        color="primary"
        class="login-input request-button"
        @click="validate && submit2()"
        @keyup.native.enter="valid && submit2($event)"
      >
        Reset Password
      </v-btn>
      <div
        id="forgotPasswordLoadingSpinner"
        v-if="showLoading2"
        >
          <circle-spin :loading='true'></circle-spin>
      </div>
    </div>
    <div v-if="processComplete">
      <p>Thank you. Your password has been reset. Please sign in using your new password.</p>
      <v-btn
        block
        color="primary"
        class="login-input"
        style="margin-bottom: 20px;"
        @click="closeOverlay()"
        @keyup.native.enter="closeOverlay()"
      >
        Close
      </v-btn>
    </div>
  </div>
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { authMethods } from '@/store/helpers'

  export default {
    data: () => ({
      showLoading: false,
      showLoading2: false,
      valid: true,
      requestSent: false,
      processComplete: false,
      show1: false,
      email: '',
      emailRules: [
        v => !!v || 'E-mail is required',
        v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
      ],
      username: null,
      code: null,
      codeRules: [
        v => !!v || 'Code is required',
        v => /^[0-9]*$/.test(v) || 'Code must be valid',
      ],
      password: null,
      passwordRules: [
          v => !!v || 'Required',
          v => v.length >= 8 || 'Minimum 8 characters required',
      ],
      regError: '',
    }),
    methods: {
      ...authMethods,
      validate () {
        this.$refs.form.validate()
      },
      cognitoSendForgotPasswordCode () {
        this.showLoading = true
        axios.get('https://api.honely.com/lookup/user_name_fetch?user_identifier='+this.email)
        .then(async (response)=>{
          this.username=response.data.user_name
          await this.submit()
          this.showLoading = false
        })
        .catch((error)=>{
          this.showLoading = false
          console.log('vx: user_name_fetch error', error.response.data.message)
          if(error.response.data.message === 'No user found.') {
            this.regError='No user found. Please create an account instead.'
          } else {
            this.regError=error.response.data.message
          }
        })
      },
      async submit () {
        await this.$store.dispatch('auth/cognitoSendForgotPasswordCode',{username : this.username})
        this.regError = 'A code has been sent to your inbox. Please enter the code and your new password'
        this.requestSent = true
      },
      submit2 () {
        this.showLoading2 = true
        this.$store.dispatch('auth/cognitoSendForgotPasswordNewPassword',{username : this.username, code : this.code, password : this.password})
        .then(()=>{
          this.processComplete=true
          this.showLoading2 = false
        }).catch ((error)=>{
          this.regError=error
          this.showLoading2 = false
        })
      },
      // submit () {
      //   const params = {
      //     email: this.email,
      //   }

      //   axios.post('/auth/api/v1/reset-password', params)
      //     .then(() => {
      //       this.regError = ''
      //       this.requestSent = true
      //     })
      //     .catch((err) => { this.regError = err.message })
      // },
      closeOverlay () {
        this.email = ''
        this.requestSent = false
        this.$emit('closeOverlay')
      },

    },
  }
</script>

<style lang="sass">
.request-button
  margin-top: 15px !important
  margin-bottom: 20px !important
  font-size: 17px !important
  font-family: $heading-font-family
  letter-spacing: .05em
</style>
<style scoped>
#forgotPasswordLoadingSpinner {
  min-height: 40px;
}
#forgotPasswordLoadingSpinner >>> .sk-fading-circle {
  margin: 0px auto;
  position: absolute;
  left: 40%;
}
</style>
