<template>
  <div>
    <div v-if="!processComplete">
      <v-form
        ref="form1"
        v-model="valid"
        :lazy-validation="true"
      >
        <div v-if="!requestSent">
          <v-text-field
            v-model="email"
            :rules="[...requiredRules, ...emailRules]"
            class="auth-modal-form-control"
            placeholder="Email"
            outlined
            shaped
            dense
            autofocus
          />
        </div>
        <div v-if="requestSent">
          <v-text-field
            v-model="code"
            :rules="[...codeRules]"
            class="auth-modal-form-control"
            placeholder="Confirmation Code"
            required
            outlined
            shaped
            dense
          />
        </div>
        <div v-if="requestSent">
          <v-text-field
            id="password"
            v-model="password"
            class="auth-modal-form-control"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[...passwordRules]"
            :type="show1 ? 'text' : 'password'"
            placeholder="New Password"
            hint=""
            outlined
            shaped
            dense
            required
            @click:append="show1 = !show1"
            @focus="() => passwordPolicy = true"
          />
          <ul
            v-if="passwordPolicy"
            class="password-policy"
            style="margin-bottom: 10px;"
          >
            <li :class="`${isPasswordValid(0) ? '' : 'invalid'}`">
              <i :class="`fa ${isPasswordValid(0) ? 'fa-check' : 'fa-times'}`" />
              <span>At least 8 characters in length</span>
            </li>
            <li :class="`${isPasswordValid([1, 2, 3, 4,]) ? '' : 'invalid'}`">
              <i :class="`fa ${isPasswordValid([1, 2, 3, 4,]) ? 'fa-check' : 'fa-times'}`" />
              <span>Should contain:</span>
              <ul>
                <li :class="`${isPasswordValid([1, 2,]) ? '' : 'invalid'}`">
                  <i :class="`fa ${isPasswordValid([1, 2,]) ? 'fa-check' : 'fa-times'}`" />
                  <span>At least 1 number and 1 special character</span>
                </li>
                <li :class="`${isPasswordValid([3, 4,]) ? '' : 'invalid'}`">
                  <i :class="`fa ${isPasswordValid([3, 4,]) ? 'fa-check' : 'fa-times'}`" />
                  <span>At least 1 uppercase and 1 lowercase character</span>
                </li>
              </ul>
            </li>
          </ul>
        </div>
      </v-form>
      <br>
      <span
        v-if="regSuccess"
        class="success--text mb-5"
      >{{ regSuccess }}</span>
      <span
        v-if="regError"
        class="error--text mb-5"
      >{{ regError }}</span>

      <v-btn
        v-if="!requestSent"
        :disabled="showLoading"
        block
        color="success"
        class="btn-submit"
        @click="cognitoSendForgotPasswordCode()"
      >
        {{ showLoading ? 'Please wait...' : 'Request Password Reset' }}
      </v-btn>
      <v-btn
        v-if="requestSent"
        :disabled="showLoading2"
        block
        color="success"
        class="btn-submit"
        @click="submit2()"
      >
        {{ showLoading2 ? 'Please wait...' : 'Reset Password' }}
      </v-btn>
    </div>
    <div v-if="processComplete">
      <p>Thank you. Your password has been reset. Please sign in using your new password.</p>
      <v-btn
        block
        color="success"
        class="btn-submit"
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
  import { REQUIREED_RULE, REQUIRED_EMAIL_RULE, REQUIRED_PASSWORD_LEN, REQUIRED_SPECIAL_CHAR, REQUIRED_ONLY_NUMBER, REQUIRED_ONLY_UPPER, REQUIRED_ONLY_LOWER } from '@/utils/validators'

  export default {
    data: () => ({
      showLoading: false,
      showLoading2: false,
      valid: false,
      requestSent: false,
      processComplete: false,
      show1: false,
      email: '',
      requiredRules: [
        REQUIREED_RULE(),
      ],
      emailRules: [
        // REQUIREED_RULE('E-mail is required'),
        REQUIRED_EMAIL_RULE('E-mail must be valid'),
      ],
      username: null,
      code: null,
      codeRules: [        
        REQUIREED_RULE('Code is required'),
        REQUIRED_ONLY_NUMBER('Code must be valid'),
      ],
      password: null,
      passwordPolicy: false,
      passwordRules: [
        REQUIRED_PASSWORD_LEN(), // Min 8 characters
        REQUIRED_SPECIAL_CHAR(), // Password should contain at least 1 special character
        REQUIRED_ONLY_NUMBER(), // Password should contain at least 1 number
        REQUIRED_ONLY_UPPER(), // Password should contain at least 1 uppercase character
        REQUIRED_ONLY_LOWER(), // Password should contain at least 1 lowercase character
      ],
      regSuccess: '',
      regError: '',
    }),
    methods: {
      isPasswordValid (ruleId) {
        let arr
        if (Array.isArray(ruleId)) {
          arr = ruleId
        } else {
          arr = [ruleId]
        }
        return arr.every(item => this.passwordRules[item](this.password))
      },
      validate () {
          console.log('-----aaa')
          console.log(this.$refs.form.validate())
        this.$refs.form.validate()
        
      },
      cognitoSendForgotPasswordCode () {
        const form = this.$refs['form1']
        if (form.validate()) {
            this.regSuccess = ''
            this.regError = ''
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
        }        
      },
      async submit () {
        const form = this.$refs['form1']
        if (form.validate()) {
            this.regSuccess = ''
            this.regError = ''
            await this.$store.dispatch('auth/cognitoSendForgotPasswordCode',{username : this.username})        
            this.regSuccess = 'A code has been sent to your inbox. Please enter the code and your new password'
            this.requestSent = true
        }
      },
      submit2 () {
        this.regSuccess = ''
        this.regError = ''
        this.showLoading2 = true
        this.$store.dispatch('auth/cognitoSendForgotPasswordNewPassword',{username : this.username, code : this.code, password : this.password})
        .then(response =>{
          this.processComplete=true
          this.showLoading2 = false
        }).catch ((error)=>{
          this.regError = (error || {}).message || 'Something went wrong! Please try again'
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

