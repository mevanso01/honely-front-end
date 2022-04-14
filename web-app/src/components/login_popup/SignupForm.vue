<template>
  <!-- eslint-disable -->
  <div>
    <v-form v-if="step === 1" v-model="valid1" :lazy-validation="true" ref="form1">
      <!-- <label>User name</label> -->
      <v-text-field
        outlined
        shaped
        dense
        label=""
        placeholder="User name"
        class="auth-modal-form-control"
        :maxlength="30"
        :rules="[checkUserNameRule/*...requiredRules, ...userNameRules*/]"
        @change="checkUserName"
        v-model="userName"
        @keypress="isNumberOrCharacter($event)"
      ></v-text-field>
      <!-- <label>Email</label> -->
      <v-text-field
        outlined
        shaped
        dense
        label=""
        placeholder="Email"
        class="auth-modal-form-control"
        :rules="[...requiredRules, ...emailRules]"
        :maxlength="50"
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
        :append-icon="showPassword ? 'mdi-eye' : 'mdi-eye-off'"
        @click:append="showPassword = !showPassword"
        class="auth-modal-form-control"
        :rules="[...passwordRules]"
        v-model="password"
        @focus="() => passwordPolicy = true"
      ></v-text-field>      
      <ul v-if="passwordPolicy" class="password-policy" style="margin-bottom: 10px;">
        <li :class="`${isPasswordValid(0, '') ? '' : 'invalid'}`">
          <i :class="`fa ${isPasswordValid(0, '') ? 'fa-check' : 'fa-times'}`"/>
          <span>At least 8 characters in length</span>
        </li>
        <li :class="`${isPasswordValid([1, 2, 3, 4,], '') ? '' : 'invalid'}`">
          <i :class="`fa ${isPasswordValid([1, 2, 3, 4,], '') ? 'fa-check' : 'fa-times'}`"/>
          <span>Should contain:</span>
          <ul>
            <li :class="`${isPasswordValid([1, 2,], '') ? '' : 'invalid'}`">
              <i :class="`fa ${isPasswordValid([1, 2,], '') ? 'fa-check' : 'fa-times'}`"/>
              <span>At least 1 number and 1 special character</span>
            </li>
            <li :class="`${isPasswordValid([3, 4,], '') ? '' : 'invalid'}`">
              <i :class="`fa ${isPasswordValid([3, 4,], '') ? 'fa-check' : 'fa-times'}`"/>
              <span>At least 1 uppercase and 1 lowercase character</span>
            </li>
          </ul>
        </li>
      </ul>
      <div style="display: flex; justify-content: between; margin-bottom: 4px;">
        <v-checkbox
          v-model="agreementChecked"
          label=""
          class="auth-modal-form-control"
        ></v-checkbox>
        <p style="margin-bottom: 0;">
          I agree to the <a href="/" class="primary-link">Honely Privacy Policy</a>, <a href="/" class="primary-link">Terms of Usage</a>, <a href="/" class="primary-link">Disclaimer and Licensing Agreement</a>.
        </p>
      </div>
      <div style="display: flex; justify-content: between;">
        <v-checkbox
          v-model="emailConsentChecked"
          label=""
          class="auth-modal-form-control"
        ></v-checkbox>
        <p style="margin-bottom: 0;">
          I consent to receive Honely newsletters and promotional content via email. For further information, please consult the Privacy Policy.
        </p>
      </div>
    </v-form>
    <v-form v-else-if="step === 2" v-model="valid2" :lazy-validation="true" ref="form2">
      <div class="auth-signup-step-title" style="margin-top: -10px; margin-bottom: 20px;">Tell us about yourself</div>
      <div class="d-flex justify-between">
        <div style="margin-right: 5px; flex-grow: 1;">
          <!-- <label>User name</label> -->
          <v-text-field
            outlined
            shaped
            dense
            label="First name"
            class="auth-modal-form-control"
            :maxlength="30"
            :rules="[...requiredRules, ...nameRules]"
            v-model="firstName"
          ></v-text-field>
        </div>
        <div style="margin-left: 5px; flex-grow: 1;">
          <!-- <label>User name</label> -->
          <v-text-field
            outlined
            shaped
            dense
            label="Last name"
            class="auth-modal-form-control"
            :maxlength="30"
            :rules="[...requiredRules, ...nameRules]"
            v-model="lastName"
          ></v-text-field>
        </div>
      </div>
      <!-- <label>Phone number</label> -->
      <v-text-field
        outlined
        shaped
        dense
        label="Phone number"
        class="auth-modal-form-control"
        @keypress="isNumber($event)"
        :rules="phoneRules"
        :maxlength="10"
        v-model="phoneNumber"
      ></v-text-field>
      <!-- <label>Pick What best describes you?</label> -->
      <v-select
        dense
        outlined
        label="Pick What best describes you?"
        class="auth-modal-form-control"
        :items="bestDescribeItems"
        :rules="requiredRules"
        v-model="userType"
      ></v-select>
      <!-- <label>Pick How will you use Honely?</label> -->
      <v-combobox
        v-if="userType === 'Homeowner'"
        clearable
        multiple
        dense
        outlined
        label="Pick How will you use Honely?"
        class="auth-modal-form-control"
        :items="homeOwnerTypeList"
        :rules="requiredArrayRules"
        v-model="homeOwnerType"
      ></v-combobox>
      <v-combobox
        v-if="userType === 'Service Provider'"
        clearable
        multiple
        dense
        outlined
        label="Pick How will you use Honely?"
        class="auth-modal-form-control"
        :items="serviceProviderTypeList"
        :rules="requiredArrayRules"
        v-model="serviceProviderType"
      ></v-combobox>
    </v-form>
    <v-form v-else-if="step === 3" v-model="valid3" :lazy-validation="true" ref="form3">
      <div class="auth-signup-step-title" style="margin-top: -10px; margin-bottom: 20px;">Find out your home's<br/>price estimate</div>
      <div style="margin-top: 25px;" />
      <v-text-field
        v-if="isBuying"
        outlined
        shaped
        dense
        label="Zip Code You Are Interested In"
        placeholder=""
        class="auth-modal-form-control"
        @keypress="isNumber($event)"
        :maxlength="10"
        v-model="interestedZipcode"
      ></v-text-field>
      <v-text-field
        v-if="isSellingOrRefinancing"
        outlined
        shaped
        dense
        label="Address"
        placeholder=""
        class="auth-modal-form-control"
        :maxlength="100"
        v-model="homeAddress1"
      ></v-text-field>
      <v-text-field
        v-if="isSellingOrRefinancing"
        outlined
        shaped
        dense
        label="Address 2"
        placeholder=""
        class="auth-modal-form-control"
        :maxlength="100"
        v-model="homeAddress2"
      ></v-text-field>
      <v-text-field
        v-if="isSellingOrRefinancing"
        outlined
        shaped
        dense
        label="City"
        placeholder=""
        class="auth-modal-form-control"
        :maxlength="30"
        v-model="homeCity"
      ></v-text-field>
      <div
        v-if="isSellingOrRefinancing"
        class="d-flex justify-between"
      >
        <div style="margin-right: 5px; flex-grow: 1;">
          <v-combobox
            clearable
            dense
            outlined
            label="State"
            class="auth-modal-form-control"
            :items="stateList"
            v-model="homeState"
          ></v-combobox>
        </div>
        <div style="margin-left: 5px; flex-grow: 1;">
          <v-text-field
            outlined
            shaped
            dense
            label="Zip Code"
            placeholder=""
            class="auth-modal-form-control"
            @keypress="isNumber($event)"
            :maxlength="10"
            v-model="homeZipCode"
          ></v-text-field>
        </div>
      </div>
    </v-form>
    <v-form v-else v-model="valid4" :lazy-validation="true" ref="form4">
      <div class="auth-signup-step-title" style="margin-top: -10px; margin-bottom: 20px;">Confirm SignUp</div>
      <div style="margin-top: 25px;" />
      <div class="d-flex justify-content-between align-baseline">
        <v-text-field
          outlined
          shaped
          dense
          label="Email Confirmation Code"
          placeholder=""
          class="auth-modal-form-control"
          :rules="requiredRules"
          v-model="confirmationCode"
        ></v-text-field>
        <v-btn
          color="success"
          class="btn-submit"
          style="padding-left: 10px !important; padding-right: 10px !important;"
          :disabled="spinner1"
          @click="onClickResendCode()"
        >
          {{ spinner1 ? 'Please wait...' : 'Resend Code' }}
        </v-btn>
      </div>
    </v-form>
    <span v-if="successTxt" class="success--text d-flex justify-center" style="margin-top: 10px">{{ successTxt }}</span>
    <span v-if="errTxt" class="error--text d-flex justify-center" style="margin-top: 10px">{{ errTxt }}</span>
    <div class="d-flex justify-center mt-5">
      <v-btn
        rounded
        color="success"
        class="btn-submit"
        :disabled="spinner"
        @click="onSubmitForm()"
      >
        {{ spinner ? 'Please wait...' : 'Create an Account' }}
      </v-btn>
    </div>
    <div class="d-flex justify-center mt-5">
      <step-indicator
        :steps="stepSize"
        :value="step"
        size="sm"
      />
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import { mapState, mapGetters } from 'vuex'
  import { Auth } from 'aws-amplify'
  import { bus } from '@/main'
  import { STATE_LIST } from '@/utils/constant'
  import { REQUIREED_RULE, ARRAY_RULES, REQUIRED_CHAR_NUMBER_RULE, REQUIRED_CHAR_RULE, REQUIRED_EMAIL_RULE, LEN_LESS_RULE, LEN_MORE_RULE, REQUIRED_PHONE_RULE, REQUIRED_PASSWORD_LEN, REQUIRED_SPECIAL_CHAR, REQUIRED_ONLY_NUMBER, REQUIRED_ONLY_UPPER, REQUIRED_ONLY_LOWER } from '@/utils/validators'

  export default {
    components: {
      StepIndicator: () => import('@/components/base/StepIndicator'),
    },

    props: {
      step: Number,
    },

    data: () => ({
      valid1: false,
      valid2: false,
      valid3: false,
      valid4: false,
      userName: '',
      firstName: '',
      lastName: '',
      email: '',
      countryCode: '+1',
      phoneNumber: '',
      password: '',
      passwordPolicy: false,
      userType: '',
      homeOwnerType: [],
      serviceProviderType: [],
      homeAddress1: '',
      homeAddress2: '',
      homeCity: '',
      homeState: '',
      homeZipCode: '',
      interestedZipcode: '',
      bestDescribeItems: [{
        text: 'Personal',
        value: 'Homeowner',
      }, {
        text: 'Business',
        value: 'Service Provider',
      }],
      homeOwnerTypeList: [{
        text: 'Buying a Home',
        value: 'Buyer',
      }, {
        text: 'Selling a Home',
        value: 'Seller',
      }, {
        text: 'Refinancing',
        value: 'Refinancer',
      }, {
        text: 'Just Browsing',
        value: 'Other',
      }],
      serviceProviderTypeList: [{
        text: 'Realtor',
        value: 'Agent/Broker',
      }, {
        text: 'Lender',
        value: 'Lender',
      }, {
        text: 'Contractor',
        value: 'General Contractor',
      }],
      stateList: STATE_LIST,
      agreementChecked: false,
      emailConsentChecked: false,
      checkUserNameExists: false,
      showPassword: false,
      spinner: false,
      spinner1: false,
      successTxt: '',
      errTxt: '',
      confirmationCodeRequested: false,
      confirmationCode: '',
      isInvalidPromo: false,
      promoCode: '',
      requiredRules: [
        REQUIREED_RULE(),
      ],
      requiredArrayRules: [
        ARRAY_RULES(),
      ],
      userNameRules: [
        // v => /^[A-Za-z][A-Za-z0-9]*$/.test(v) || 'Username cannot contain spaces',
        REQUIRED_CHAR_NUMBER_RULE('User name cannot contain spaces or special characters'),
        LEN_LESS_RULE(5, 'Username has to be at least 5 characters'),
        LEN_MORE_RULE(15, 'Username cannot be more than 15 characters'),
      ],
      nameRules: [
        REQUIRED_CHAR_RULE('Name cannot contain numbers or special characters'),
      ],
      emailRules: [
        REQUIRED_EMAIL_RULE(),
      ],
      phoneRules: [
        REQUIRED_PHONE_RULE(),
      ],
      passwordRules: [
        REQUIRED_PASSWORD_LEN(), // Min 8 characters
        REQUIRED_SPECIAL_CHAR(), // Password should contain at least 1 special character
        REQUIRED_ONLY_NUMBER(), // Password should contain at least 1 number
        REQUIRED_ONLY_UPPER(), // Password should contain at least 1 uppercase character
        REQUIRED_ONLY_LOWER(), // Password should contain at least 1 lowercase character
      ],
    }),

    computed: {
      ...mapState('auth', ['currentUser']),
      ...mapGetters('auth', ['cognitoSignUpReturns']),
      stepSize () {
        if (this.isBuying || this.isSellingOrRefinancing) {
          return 4
        }
        return 3
      },
      isBuying () {
        return this.userType === 'Homeowner' && this.homeOwnerTypeFlattened.includes('Buyer')
      },
      isSellingOrRefinancing () {
        return this.userType === 'Homeowner' && (this.homeOwnerTypeFlattened.includes('Seller') || this.homeOwnerTypeFlattened.includes('Refinancer'))
      },
      fullAddress () {
        const arr = [
          this.homeAddress1,
          this.homeAddress2,
          this.homeCity,
          this.homeState?.text,
          this.homeZipCode,
        ].filter(item => !!item)
        return arr.join(', ')
      },
      homeOwnerTypeFlattened () {
        return this.homeOwnerType.map(item => item.value)
      },
      serviceProviderTypeFlattened () {
        return this.serviceProviderType.map(item => item.value)
      },
      checkUserNameRule () {
        // v => !!v || 'Username is required',
        //   // v => /^[A-Za-z][A-Za-z0-9]*$/.test(v) || 'Username cannot contain spaces',
        //   v => /^[A-Za-z0-9]*$/.test(v) || 'User name cannot contain spaces or special characters.',
        //   v => v.length >= 5 || 'Username has to be atleast 5 characters',
        //   v => v.length <= 15 || 'Username cannot be more than 15 characters',
        const usernameToCheck = this.userName
        // console.log('vx: usernameToCheck.length', usernameToCheck.length)
        if (usernameToCheck.length > 15) {
          return 'Username cannot be more than 15 characters'
        }
        if (usernameToCheck.length < 5) {
          return 'Username has to be at least 5 characters'
        }
        if (!/^[A-Za-z0-9]*$/.test(usernameToCheck)) {
          return 'Username cannot contain spaces or special characters'
        }
        if (this.checkUserNameExists === true) {
          return 'Username exists. Please use a different one'
        }
        return true
      },
    },

    watch: {
      userType: function (newVal, oldVal) {
        this.homeOwnerType = []
        this.serviceProviderType = []
      },
    },

    methods: {
      isNumberOrCharacter: function (evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if (charCode < 48 || (charCode > 57 && charCode < 65) || (charCode > 90 && charCode < 97) || charCode > 122) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      isNumber: function (evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      isPasswordValid (ruleId, errorMessage) {
        let arr
        if (Array.isArray(ruleId)) {
          arr = ruleId
        } else {
          arr = [ruleId]
        }
        return arr.every(item => {
          if (errorMessage !== '' && this.passwordRules[item](this.password) === '') {
            this.errTxt = 'Invalid Password'
          }
          return this.passwordRules[item](this.password)
        },
        )
      },
      checkUserName () {
        const usernameToCheck = this.userName
        this.checkUserNameExists = false
        // console.log('vx: usernmae',this.userName)
        Auth.signIn(usernameToCheck.toLowerCase(), '123').then(res => {
          return 'Username already exists'
        }).catch(error => {
          const code = error.code
          console.log(error)
          switch (code) {
            case 'UserNotFoundException':
              // console.log("vx: UserNotFoundException")
              this.checkUserNameExists = true
              // return !this.redirectToRegister(email);
              return true
            case 'UserLambdaValidationException':
              console.log('UserLambdaValidationException')
              this.checkUserNameExists = false
              return true
            case 'NotAuthorizedException':
              // console.log("vx: NotAuthorizedException")
              this.checkUserNameExists = true
              return true
            case 'PasswordResetRequiredException':
              // console.log("vx: PasswordResetRequiredException")
              this.checkUserNameExists = true
              return true
            case 'UserNotConfirmedException':
              // console.log("vx: UserNotConfirmedException")
              this.checkUserNameExists = true
              return true
            default:
              this.checkUserNameExists = false
              return false
          }
        })
      },
      onSubmitForm: function () {
        this.isPasswordValid([0, 1, 2, 3, 4], 'Invalid Password')
        const form = this.$refs[`form${this.step}`]
        if (form.validate() && this.agreementChecked) {
          this.errTxt = ''
          if (this.step < 3) {
            this.$emit('stepNext')
          } else if (this.step === 3) {
            this.signUp()
          } else {
            this.confirmSignUpAndLogIn()
          }
        }
        if (!this.agreementChecked) {
          this.errTxt = 'Please agree to the Honely Privacy Policy, Terms of Usage, Disclaimer and Licensing Agreement.'
        }
      },
      onClickResendCode: function () {
        this.errTxt = ''
        this.successTxt = ''
        this.spinner1 = true
        axios.get('https://api.honely.com/lookup-test/email_verification_service?email=' + this.email)
          .then(async () => {
            this.spinner1 = false
            this.errTxt = 'Too much time has elapsed. Please sign up again.'
            // this.errTxt='Too much time has elapsed. Please click register again.'
            // this.confirmationCode = null
            // this.confirmationCodeRequested = false
          }).catch(async () => {
            await this.$store.dispatch('auth/cognitoResendConfirmationCode', { username: this.userName })
            this.successTxt = 'Resent Email Verification Code!'
            this.spinner1 = false
            this.confirmationCodeRequested = true
          })
      },
      /* eslint-disable */
      signUp () {
        let fullPhone = ""
        if (this.phoneNumber == ""){
          fullPhone = ""
        }
        else{
          fullPhone = this.countryCode + this.phoneNumber
          fullPhone = fullPhone.replace(/-/g, "");
        }
        let emailConsent = this.emailConsentChecked?'TRUE':'FALSE'
        // vx: getting subtypes addiotinal info
        let siteLeads = "FALSE"
        let userType = ''
        if (this.userType === 'Service Provider') {
          siteLeads = "TRUE"
          // userType = this.serviceProviderTypeFlattened
          userType = (this.serviceProviderTypeFlattened || []).join(',')
        }
        if (this.userType === 'Homeowner') {
          userType = (this.homeOwnerTypeFlattened || []).join(',')
        }
        const params = {
          first_name: this.firstName,
          last_name: this.lastName,
          email: this.email,
          membership_type: 'FREE',
          phone_number: fullPhone,
          user_name: this.userName,
          email_consent: emailConsent,
          user_type: userType,
          site_leads: siteLeads,
          button_leads: "FALSE",
          // home_url: null,
          interested_zip_codes: this.interestedZipcode,
          home_address: this.fullAddress,
          home_zip_code: this.homeZipCode,
        }
        this.errTxt = ''
        this.successTxt = ''
        this.spinner = true
        axios.get('https://api.honely.com/lookup-test/email_verification_service?email='+this.email)
          .then(async ()=>{
            try{
              await this.$store.dispatch('auth/cognitoSignUp',{username : this.userName, password: this.password, email: this.email, phone_number: fullPhone, first_name: this.firstName, last_name: this.lastName})
              this.successTxt='Email Confirmation Code sent to your inbox! (If you haven\'t received it, please check your spam folder.)'
              this.confirmationCodeRequested=true
              this.$emit('stepNext')
              // axios.post('https://api.honely.com/lookup-test/unconfirmed_user_addition', params)
              axios.post('https://api.honely.com/lookup/unconfirmed_user_addition', params)
              this.spinner = false
            } catch (error) {
              this.spinner = false
              if (error.message=='User already exists') {
                this.errTxt='Username already exists. Please use a different username or sign in to existing account'
              } else {
                this.errTxt=error.message
              }
            }
          }).catch((error)=>{
            this.spinner = false
            if(this.email==''){
              this.errTxt='Please enter a valid email.'
            } else {
              this.errTxt='This email is already in use. Please use a different email address or sign in to existing account'
            }
          })
      },
      async confirmSignUpAndLogIn() {
        let fullPhone = ""
        if (this.phoneNumber == ""){
          fullPhone = ""
        }
        else{
          fullPhone = this.countryCode + this.phoneNumber
          fullPhone = fullPhone.replace(/-/g, "");
        }
        var emailConsent = this.emailConsentChecked?'TRUE':'FALSE'
        var siteLeads = "FALSE"
        let userType = ''
        // if (this.userType === 'Agent/Broker' || this.userType === 'Lender' || this.userType === 'General Contractor') {
        if (this.userType === 'Service Provider') {
          siteLeads = "TRUE"
          this.userType = (this.serviceProviderTypeFlattened || []).join(',')
        }
        if (this.userType === 'Homeowner') {
          this.userType = (this.homeOwnerTypeFlattened || []).join(',')
        }
        const params = {
          user_name: this.userName,
          first_name: this.firstName,
          last_name: this.lastName,
          email: this.email,
          phone_number: fullPhone,
          membership_type: 'FREE',
          email_consent: emailConsent,
          promo_code: this.promoCode,
          user_type: this.userType,
          site_leads: siteLeads,
          button_leads: "FALSE",
          home_url: null,
          interested_zip_codes: this.interestedZipcode,
          home_address: this.fullAddress,
          home_zip_code: this.homeZipCode,
        }
        try {
          this.errTxt = ''
          this.successTxt = ''
          this.spinner = true
          await this.confirmSignUp()
          await axios.delete('https://api.honely.com/lookup-test/unconfirmed_user_deletion', {data: {email: this.email}})
          // vx: add zipcode to leadZipCodeList and send tier 2 lead
          this.$store.dispatch('auth/setLeadDetails', null)
          this.$store.dispatch('auth/setLeadZipCodeList', null)
          var leadZipCodeList = []
          if (this.interestedZipcode) {
            leadZipCodeList.push(this.interestedZipcode)
            const paramsTier2 = {
              name: this.firstName,
              email: this.email,
              phone_number: fullPhone,
              search_address: this.interestedZipcode,
              zip_code: this.interestedZipcode,
              lead_type: 'BUYING,,,',
              lead_category: 'AGENT/BROKER,LENDER,',
              came_from: "SITE"
            }
            axios.post('https://api.honely.com/lookup-test/lead', paramsTier2)
          }
          if (this.homeZipCode) {
            if (!leadZipCodeList.includes(this.homeZipCode)) {
              leadZipCodeList.push(this.homeZipCode)
            }
            var tier2Targets = []
            var messageParam = ''
            var endUserTypes = []
            if (this.homeOwnerType.includes('Seller')) {
              // tier2Targets.push('AGENT/BROKER')
              // tier2Targets.push('GENERAL CONTRACTOR')
              endUserTypes.push('SELLING')
            } else {
              endUserTypes.push('')
            }
            if (this.homeOwnerType.includes('Buyer') && !this.homeOwnerType.includes('Seller')) {
              // tier2Targets.push('AGENT/BROKER')
              endUserTypes.push('BUYING')
            } else {
              endUserTypes.push('')
            }
            if (this.homeOwnerType.includes('Refinancer')) {
              // tier2Targets.push('LENDER')
              endUserTypes.push('REFINANCING')
            } else {
              endUserTypes.push('')
            }
            if (this.homeOwnerType.includes('Buyer') || this.homeOwnerType.includes('Seller') || this.userType === 'Just Browsing') {
              tier2Targets.push('AGENT/BROKER')
            } else {
              tier2Targets.push('')
            }
            if (this.homeOwnerType.includes('Buyer') || this.homeOwnerType.includes('Refinancer') || this.userType === 'Just Browsing') {
              tier2Targets.push('LENDER')
            } else {
              tier2Targets.push('')
            }
            tier2Targets.push('')
            messageParam = endUserTypes.join(',')
            const lead_category = tier2Targets.join(',')
            const paramsTier22 = {
              name: this.firstName,
              email: this.email,
              phone_number: fullPhone,
              search_address: this.homeZipCode,
              zip_code: this.homeZipCode,
              lead_type: messageParam,
              lead_category: lead_category,
              came_from: "SITE"
            }
            axios.post('https://api.honely.com/lookup-test/lead', paramsTier22)
          }
          if (leadZipCodeList.length > 0) {
            this.$store.dispatch('auth/setLeadZipCodeList', leadZipCodeList)
          }
          await this.signIn()
          this.$emit('successfulLogin')
          bus.$emit('successfulLogin')
          // await axios.post('https://api.honely.com/lookup-test/register_service', params)
          await axios.post('https://api.honely.com/lookup/register_service', params)
          if (this.$route.path) {
              // if is one of the following paths, reload the page
              if (this.$route.path.startsWith('/forecast') || this.$route.path === '/subscribe' || this.$route.path === '/leads-dashboard' || this.$route.path === '/smart-data-subscription') {
                // console.log('vx: this.$route.path', this.$route.path)
                // location.reload()
                this.$router.go()
              }
              /* if (this.$route.path.startsWith('/listing')) {
                this.$router.push({ name: 'Home'})
              }*/
              if (this.$route.path.startsWith('/listing')) {
                this.$router.replace(this.$route.path).catch(()=>{})
              }
            }
        } catch (error) {
          // console.log('vx: Error while confirming and signing in', error)
          this.errTxt=error.message
          this.spinner = false
        }
      },
      async confirmSignUp() {
        // try {
          await this.$store.dispatch('auth/cognitoConfirmSignUp',{ username : this.userName, code: this.confirmationCode })
          this.errTxt=null
          this.confirmationCodeRequested=true
        // } catch (error) {
        //   this.regError=error
        //   console.log('error confirming sign up', error);
        // }
      },
      async signIn() {
        try{
          await this.$store.dispatch('auth/cognitoSignIn',{username : this.userName, password: this.password})
        } catch (error) {
          this.errTxt=error
          // console.log('vx: error signing in', error)
        }
          // try {
          //     var username=this.firstName
          //     var password=this.password
          //     const user = await Auth.signIn(username, password);
          //     console.log(user)
          // } catch (error) {
          //     console.log('error signing in', error);
          // }
      },
      /* eslint-enable */
    },
  }
</script>

<style lang="scss">
</style>

<style scoped>
</style>
