<template
  scrollable
>
  <div
    id="create-account"
  >
    <v-form
      ref="form"
      v-model="valid"
      :lazy-validation="lazy"
      class="create-account-form"
    >
      <!-- User Name -->
      <v-row>
        <v-col>
          <p style="margin-top: 2px"><b>User Name</b></p>
          <v-text-field
            v-model="userName"
            :rules=[checkUserNameRule]
            name="username"
            class="login-input"
            @change="checkUserName"
            placeholder="Not case sensitive"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row>

      <!-- First name-->
      <v-row>
        <v-col>
          <p><b>First Name</b></p>
          <v-text-field
            v-model="firstName"
            :rules="firstNameRules"
            name="firstname"
            class="login-input"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row>

      <!-- Last name -->
      <v-row>
        <v-col>
          <p><b>Last Name</b></p>
          <v-text-field
            v-model="lastName"
            :rules="lastNameRules"
            name="lastname"
            class="login-input"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row>

      <!-- Small Screen First Name -->
      <!-- <v-row
        v-if="screenTypeSmall"
      >
        <v-col
          :md="screenTypeSmall ? '12' : '6'"
        >
          <p><b>First Name</b></p>
          <v-text-field
            v-model="firstName"
            :rules="firstNameRules"
            name="firstname"
            class="login-input"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row> -->

      <!-- Small Screen Last Name -->
      <!-- <v-row
        v-if="screenTypeSmall"
      >
        <v-col
          :md="screenTypeSmall ? '12' : '6'"
        >
          <p><b>Last Name</b></p>
          <v-text-field
            v-model="lastName"
            :rules="lastNameRules"
            name="lastname"
            class="login-input"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row> -->

      <!-- Small Screen Email -->
      <!-- <v-row
        v-if="screenTypeSmall"
        class="mt-0"
      >
        <v-col
          :md="screenTypeSmall ? '12' : '6'"
        >
          <p><b>Email ID</b></p>
          <v-text-field
            v-model="email"
            :rules="emailRules"
            name="email"
            class="login-input"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row> -->

      <!-- Small Screen Phone -->
      <!-- <v-row
        v-if="screenTypeSmall"
        class="mt-0"
      >
        <v-col
          md="6"
        >
          <p><b>Phone (optional)</b></p>
            <vue-country-code
              style="display: flex; float: left;"
              @onSelect="setCountryCode"
              :preferredCountries="['us']"
            >
            </vue-country-code>
            <v-text-field
              v-model="phoneNumber"
              name="phonenumber"
              :rules="phoneRules"
              @keypress="isNumber($event)"
              @keyup="checkPhoneExistence"
              :maxlength="12"
              class="login-input"
              placeholder="XXX-XXX-XXXX"
              required
              outlined
              :readonly="confirmationCodeRequested"
            />
        </v-col>
      </v-row> -->

      <!-- Big Screen Email  -->
      <v-row
        class="mt-0"
      >
        <v-col>
          <p><b>Email ID</b></p>
          <v-text-field
            v-model="email"
            :rules="emailRules"
            name="email"
            class="login-input"
            required
            outlined
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row>

      <!-- Big Screen Phone -->
      <v-row
        class="mt-0"
      >
        <v-col>
          <p><b>Phone (optional)</b></p>
            <vue-country-code
              style="display: flex; float: left;"
              @onSelect="setCountryCode"
              :preferredCountries="['us']"
            >
            </vue-country-code>
            <v-text-field
              v-model="phoneNumber"
              name="phonenumber"
              :rules="phoneRules"
              @keypress="isNumber($event)"
              @keyup="checkPhoneExistence"
              :maxlength="12"
              class="login-input"
              placeholder="XXX-XXX-XXXX"
              required
              outlined
              :readonly="confirmationCodeRequested"
            />
        </v-col>
      </v-row>

      <!-- Small Screen Password  -->
      <!-- <v-row
        v-if="screenTypeSmall"
        class="mt-0"
      >
        <v-col
          :md="screenTypeSmall ? '12' : '6'"
          class="password-col"
        >
          <p><b>Password</b></p>
          <v-tooltip
          top
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                id="password"
                v-bind="attrs"
                v-on="on"
                v-model="password"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="passwordRules"
                name="password"
                class="login-input"
                :type="show1 ? 'text' : 'password'"
                hint=""
                counter
                outlined
                @click:append="show1 = !show1"
                :readonly="confirmationCodeRequested"
              />
            </template>
            <span>Must contain minimum 8 characters<br>Must contain at least 1 number and 1 special character<br>Must contain at least 1 uppercase and 1 lowercase character</span>
          </v-tooltip>
        </v-col>
      </v-row> -->

      <!-- Small Screen Confirm Password -->
      <!-- <v-row
        v-if="screenTypeSmall"
        class="mt-0"
      >
        <v-col
          style="margin-top:5px"
          :md="$vuetify.breakpoint.xs ? '12' : '6'"
          class="password-col"
        >
          <p><b>Re-enter Password</b></p>
          <v-text-field
            id="passwordAgain"
            v-model="passwordAgain"
            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
            name="passwordAgain"
            class="login-input"
            :type="show2 ? 'text' : 'password'"
            counter
            outlined
            @click:append="show2 = !show2"
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row> -->

      <!-- Big Screen Password -->
      <v-row
        class="mt-0"
      >
        <v-col
          class="password-col"
        >
          <p><b>Password</b></p>
          <v-tooltip
          top
          >
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                id="password"
                v-bind="attrs"
                v-on="on"
                v-model="password"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="passwordRules"
                name="password"
                class="login-input"
                :type="show1 ? 'text' : 'password'"
                hint=""
                counter
                outlined
                @click:append="show1 = !show1"
                :readonly="confirmationCodeRequested"
              />
            </template>
            <span>Must contain minimum 8 characters<br>Must contain at least 1 number and 1 special character<br>Must contain at least 1 uppercase and 1 lowercase character</span>
          </v-tooltip>
        </v-col>
      </v-row>

      <!-- Big Screen Confirm Password -->
      <v-row
        class="mt-0"
      >
        <v-col
          class="password-col"
        >
          <p><b>Re-enter Password</b></p>
          <v-text-field
            id="passwordAgain"
            v-model="passwordAgain"
            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
            name="passwordAgain"
            class="login-input"
            :type="show2 ? 'text' : 'password'"
            counter
            outlined
            @click:append="show2 = !show2"
            :readonly="confirmationCodeRequested"
          />
        </v-col>
      </v-row>
      <!-- Pick options -->
      <v-row
        class="mt-0"
      >
        <v-col>
        <p><b>Pick what best describes you</b></p>
          <div
          id="user-type-dropdown"
          >
          <v-select
            v-model="userType"
            :items="['Homeowner', 'Service Provider', 'Just Browsing']"
            required
            v-on:change="resetOptions()"
            :disabled="confirmationCodeRequested"
          />
          </div>
          <!-- Choose Options Buttons -->
          <div
            v-if="submittedOptions"
            class="submittedOptionsIndicator"
          >
          <i class="fas fa-check"></i></div>
          <div
            v-else
            class="choose-options-buttons"
          >
            <button
              v-if="userType==='Homeowner'"
              @click.prevent="showForm('home-owner')"
              >
                Choose Homeowner Options
            </button>
            <button
              v-if="userType==='Service Provider'"
              @click.prevent="showForm('service-provider')"
            >
              Choose Service Provider Options
            </button>
          </div>
        </v-col>
      </v-row>

      <!-- Choose Option Button -->
      <v-row v-if="userType!='Just Browsing' && !submittedOptions">
        <v-col>
          <span
            class="register-error"
            v-if="userType==='Service Provider'"
          >
              Please choose a Service Provider option
          </span>
          <span
            class="register-error"
            v-else
          >
              Please choose Homeowner options
          </span>
        </v-col>
      </v-row>

      <!-- Confirmation Code -->
      <v-row
        class="mt-0"
      >
        <v-col
          v-if="confirmationCodeRequested"
          style="padding-bottom: 0 !important"
        >
          <p><b>Email Confirmation Code</b></p>
          <v-text-field
            v-model="confirmationCode"
            name="confirmationcode"
            class="login-input"
            required
            outlined
          />
        </v-col>
      </v-row>
      <v-row>
        <v-col
          v-if="confirmationCodeRequested"
          style="padding-top: 0 !important"
        >
          <v-btn
          :disabled="!valid"
          block
          color="primary"
          @click="validate && resendVerificationCode()"
          @keyup.native.enter="valid && resendVerificationCode($event)"
        >
          Resend Code
        </v-btn>
        </v-col>
      </v-row>
      <br
      v-if="!confirmationCodeRequested"
      >
      <div class="fixed-box">
        <br>
        <!-- Error Messages -->
        <p
          v-if="regError"
          class="register-error"
          style="margin-top:10px"
        >
          {{ regError }}
        </p>
        <p
          v-if="regError2"
          class="register-error"
          style="margin-top:10px"
        >
          {{ regError2 }}
        </p>
        <br>
        <!-- First CHeckbox -->
        <v-row>
          <v-col>
            <input
              type="checkbox"
              v-model="agreementChecked"
              :disabled="confirmationCodeRequested"
            >
            <label
              class="check-box"
            >
              &nbsp;
              I agree to the Honely
              <a
                href="https://honely.com/privacy"
                target="_blank"
              >
                Privacy Policy
              </a>, &nbsp;
              <a
                href="https://honely.com/terms"
                target="_blank"
              >
                Terms of Usage
              </a>, &nbsp;
              <a
                href="https://honely.com/disclaimer"
                target="_blank"
              >
                Disclaimer
              </a>&nbsp;and
              <a
                href="https://honely.com/licensing"
                target="_blank"
              >
                Licensing Agreement
              </a>.
            </label>
          </v-col>
        </v-row>
        <!-- 2nd Checkboxs -->
        <v-row>
          <v-col>
        <input
          type="checkbox"
          v-model="emailConsentChecked"
          :disabled="confirmationCodeRequested"
        >
        <label
          class="check-box"
        >
          &nbsp;
          I consent to receive Honely newsletters and promotional content via email. For further information, please consult the
          <a
            href="https://honely.com/privacy"
            target="_blank"
          >
            Privacy Policy
          </a>.
        </label>
          </v-col>
        </v-row>
      </div>

      <!-- Register Button -->
      <v-card-actions
        v-if="!confirmationCodeRequested"
        class="px-0 mt-2"
      >
        <v-spacer />
        <v-btn
          :disabled="validateForm()"
          block
          color="primary"
          class="register-button"
          @click="validate && signUp()"
          @keyup.native.enter="valid && signUp($event)"
        >
          Register
        </v-btn>
      </v-card-actions>

      <!-- Confirm Code -->
      <v-card-actions
        v-if="confirmationCodeRequested"
        class="px-0 mt-2"
      >
        <v-spacer />

        <v-btn
          v-if="!showLoading"
          :disabled="!(confirmationCode)"
          block
          color="primary"
          class="register-button"
          @click="validate && confirmSignUpAndLogIn()"
          @keyup.native.enter="valid && confirmSignUpAndLogIn($event)"
        >
          Confirm Sign Up
        </v-btn>
        <div
        id="signupLoadingSpinner"
        v-if="showLoading"
        >
          <circle-spin :loading='true'></circle-spin>
        </div>
      </v-card-actions>
    </v-form>
    <login-overlay
      ref="loginOverlay"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
      @optionsSubmitted="optionsSubmitted"
    />
  </div>
</template>
<script src="https://cdnjs.cloudflare.com/ajax/libs/vue/2.5.17/vue.js"></script>
<script>
/* eslint-disable */
  import axios from 'axios'
  import { authMethods } from '@/store/helpers'
  import { mapState, mapGetters } from 'vuex'
  import { bus } from '@/main'
  import Amplify, { Auth } from 'aws-amplify'
  import awsExports from '../../aws-config'
  import Vue from "vue";
  import VueCountryCode from "vue-country-code";
  Vue.use(VueCountryCode);
  import config from '../../aws-exports'
  Amplify.configure(config)
  // Amplify.configure(awsExports)
  async function signOut() {
    try {
        await Auth.signOut();
        // console.log('vx: user signed out')
    } catch (error) {
        // console.log('vx: error signing out: ', error);
    }
}
// signOut()
  var thatOneReturns=null
  async function isUserLoggedIn() {
    try {
        thatOneReturns=await Auth.currentAuthenticatedUser();
        // console.log('vx: from the form thatOneReturns:', thatOneReturns)
        // console.log("vx: from the form user is logged in");
    } catch {
        // console.log('vx: from the form thatOneReturns:', thatOneReturns)
        // console.log("vx: from the form user is logged out");
    }
}
// isUserLoggedIn()
  export default {
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },
    data: () => {
      return {
        homeOwnerType: null,
        homeAddress: null,
        homeZipCode: null,
        interestedZipcode: null,
        serviceProviderType: null,
        disableForPhoneCheck: false,
        phoneExists: null,
        userType: 'Just Browsing',
        isInvalidPromo: false,
        promoCode: '',
        emailConsentChecked: true,
        showLoading: false,
        agreementChecked: false,
        privacyPolicyChecked: false,
        termsChecked: false,
        disclaimerChecked: false,
        licensingChecked: false,
        lazy: true,
        screenTypeSmall: false,
        show1: false,
        show2: false,
        valid: false,
        countryCode: '+1',
        userName: '',
        firstName: '',
        checkUserNameExists: false,
        userNameRules: [
          v => !!v || 'Username is required',
          // v => /^[A-Za-z][A-Za-z0-9]*$/.test(v) || 'Username cannot contain spaces',
          v => /^[A-Za-z0-9]*$/.test(v) || 'User name cannot contain spaces or special characters.',
          v => v.length >= 5 || 'Username has to be at least 5 characters',
          v => v.length <= 15 || 'Username cannot be more than 15 characters',
        ],
        firstNameRules: [
          v => !!v || 'First name is required',
          v => /^[A-Za-z][A-Za-z\s]*$/.test(v) || 'Name cannot contain numbers or special characters.',
        ],
        lastName: '',
        lastNameRules: [
          v => !!v || 'Last name is required',
          v => /^[A-Za-z][A-Za-z\s]*$/.test(v) || 'Name cannot contain numbers or special characters.',
        ],
        phoneNumber: '', // vx: newly added.. Need to handle in other places..
        /* phoneRules: [
          // v => v.length >= 10 || 'Atleast 10 characters',
          v => /^$|^((?!(0))(?!(1))[0-9A-Za-z-]{9,12})$/.test(v) || 'Invalid Phone Number',
          v => /^$|\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/.test(v) || 'Invalid Phone Number',
          // v => !!v || 'Phone number is required for Agents, Lenders and Contractors',
          // v => /^$^|[+1]$|^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{5,7}$/.test(v) || 'Phone Number must be valid',
        ], */
        email: '',
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        password: '',
        passwordAgain: '',
        passwordRules: [
           v => !!v || 'Required.',
           v => v.length >= 8 || 'Min 8 characters',
          //  v => !/^[A-Za-z0-9]*$/.test(v) || 'Password should contain atleast 1 special characters',
          //  v => !/[0-9~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(v) || 'Password should contain atleast 1 letter',
          //  v => !/[A-Za-z~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(v) || 'Password should contain atleast 1 number',
          //  v => !/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(v) || 'Password should contain special characters',
        ],
        confirmationCode: null,
        isRealtor: false,
        realtorID: '',
        regError: null,
        confirmationCodeRequested: false,
        showLoginOverlay: false,
        overlayTab: 'sign-up',
        submittedOptions: false,
      }
    },
    computed: {
      ...mapState('auth', ['currentUser']),
      ...mapGetters('auth', ['cognitoSignUpReturns', 'selectedHomeOwnerTypes', 'homeAddress', 'homeZipCode', 'interestedZipCode', 'selectedServiceProviderType']),
      phoneRules () {
        // if (this.userType === 'Agent/Broker' || this.userType === 'Lender' || this.userType === 'General Contractor') {
        if (this.userType === 'Service Provider') {
          if (this.phoneExists) {
            return ['Phone number exists']
          } else {
            return [
              // v => v.length >= 10 || 'Atleast 10 characters',
              v => /^$|^((?!(0))(?!(1))[0-9A-Za-z-]{9,12})$/.test(v) || 'Invalid Phone Number',
              v => /^$|\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/.test(v) || 'Invalid Phone Number',
              v => !!v || 'Phone number is required for ' + this.userType +'s.',
              // v => /^$^|[+1]$|^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{5,7}$/.test(v) || 'Phone Number must be valid',
            ]
          }
        } else {
          if (this.phoneExists) {
            return ['Phone number exists']
          } else {
            return [
              // v => v.length >= 10 || 'Atleast 10 characters',
              v => /^$|^((?!(0))(?!(1))[0-9A-Za-z-]{9,12})$/.test(v) || 'Invalid Phone Number',
              v => /^$|\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/.test(v) || 'Invalid Phone Number',
              // v => /^$^|[+1]$|^[+]?[(]?[0-9]{3}[)]?[-\s.]?[0-9]{3}[-\s.]?[0-9]{5,7}$/.test(v) || 'Phone Number must be valid',
            ]
          }
        }
      },
      regError2 () {
        // if ((this.userType === 'Agent/Broker' || this.userType === 'Lender' || this.userType === 'General Contractor') && (this.phoneNumber === '')) {
        if ((this.userType === 'Service Provider') && (this.phoneNumber === '')) {
          return 'Phone number is required for ' + this.userType +'s.'
        } else {
          return ''
        }
      },
      checkUserNameRule () {
        // v => !!v || 'Username is required',
        //   // v => /^[A-Za-z][A-Za-z0-9]*$/.test(v) || 'Username cannot contain spaces',
        //   v => /^[A-Za-z0-9]*$/.test(v) || 'User name cannot contain spaces or special characters.',
        //   v => v.length >= 5 || 'Username has to be atleast 5 characters',
        //   v => v.length <= 15 || 'Username cannot be more than 15 characters',
        var user_name_to_check = this.userName
        // console.log('vx: user_name_to_check.length', user_name_to_check.length)
        if (this.checkUserNameExists === true) {
          return 'Username exists. Please use a different username.'
        }
        if (user_name_to_check.length >15) {
          return 'Username cannot be more than 15 characters'
        }
        if (user_name_to_check.length <5) {
          return 'Username has to be at least 5 characters'
        }
        if (!/^[A-Za-z0-9]*$/.test(user_name_to_check)) {
          return 'User name cannot contain spaces or special characters.'
        }
      },
      matchRule () {
        return v => (!!v && v) === this.password || 'The passwords you entered don\'t match'
      },
      matchRule2 () {
        return v => (!!v && v) === this.passwordAgain || 'The passwords you entered don\'t match'
      },
      realtorIsRequired () {
        return v => (!!this.isRealtor && !!v) || 'Realtor ID Required'
      },
    },
    watch: {
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },
    mounted: function () {
      this.$store.dispatch('auth/setSelectedHomeOwnerTypes', null)
      this.$store.dispatch('auth/setSelectedServiceProviderType', null)
      this.$store.dispatch('auth/setInterestedZipCode', null)
      this.$store.dispatch('auth/setHomeAddress', null)
      this.$store.dispatch('auth/setHomeZipCode', null)
      var x = document.getElementsByClassName("v-icon")
      for(var i=0;i<x.length;i++){
        // console.log('vx: x[i][&lsquo;classList&rsquo;][4]', x[i]['classList'][4])
        if (x[i]['classList'][4]==='mdi-eye-off') {
          x[i].style.marginBottom="14px"
        }
      }
      console.log(this.homeOwnerOptions)
    },
    methods: {
      ...authMethods,
      // Reset $store for user info
      resetOptions () {
        this.submittedOptions = false
        this.$store.dispatch('auth/setSelectedHomeOwnerTypes', null)
        this.$store.dispatch('auth/setSelectedServiceProviderType', null)
        this.$store.dispatch('auth/setInterestedZipCode', null)
        this.$store.dispatch('auth/setHomeAddress', null)
        this.$store.dispatch('auth/setHomeZipCode', null)
      },
      validateForm () {
        return !this.valid || !this.agreementChecked || !(this.userName && this.firstName && this.lastName && this.email && this.password && this.passwordAgain) ||
               ((this.userType === 'Service Provider') && (this.phoneNumber === '') || this.disableForPhoneCheck) ||
               ((this.userType != 'Just Browsing') && !this.submittedOptions)
      },
      optionsSubmitted (value) {
        this.submittedOptions = value
        // if (value) {
        //   // $store.getters['auth/selectedHomeOwnerTypes']
        //   this.homeOwnerType = this.$store.getters['auth/selectedHomeOwnerTypes']
        //   this.homeAddress = this.$store.getters['auth/homeAddress']
        //   this.homeZipCode = this.$store.getters['auth/homeZipCode']
        //   this.interestedZipcode = this.$store.getters['auth/interestedZipCode']
        //   this.serviceProviderType = this.$store.getters['auth/selectedServiceProviderType']
        //   this.$store.dispatch('auth/setSelectedHomeOwnerTypes', null)
        //   this.$store.dispatch('auth/setSelectedServiceProviderType', null)
        //   this.$store.dispatch('auth/setInterestedZipCode', null)
        //   this.$store.dispatch('auth/setHomeAddress', null)
        //   this.$store.dispatch('auth/setHomeZipCode', null)
        // }
      },
      checkPhoneExistence: function () {
        var fullPhone = ""
        if (this.phoneNumber == ""){
          fullPhone = ""
        }
        else{
          fullPhone = this.countryCode + this.phoneNumber
          fullPhone = fullPhone.replace(/-/g, "");
        }
        fullPhone = fullPhone.substring(1)
        if (fullPhone.length >= 11) {
          this.disableForPhoneCheck = true
          axios.get('https://api.honely.com/lookup-test/phone_verification_service?phone_number=' + fullPhone)
        .then(() => {
          this.phoneExists = false
          this.disableForPhoneCheck = false
        })
        .catch((error) => {
          if (error.response.data.message === 'Phone number already exists.') {
            this.phoneExists = true
            this.disableForPhoneCheck = false
          } else {
            this.regError = 'Error while checking for presence of Phone number in the DB. Please refresh the page and sign up again.'
          }
        })
        } else {
          this.phoneExists = false
        }
      },
      optionsDidSubmit (value) {
        console.log('submitted',value)
      },
      isPromoCodeValid: function () {
        if (this.promoCode.length > 0) {
          axios.get('https://api.honely.com/lookup-test/promo_code_check', {
            params: {
              promo_code: this.promoCode,
            },
          })
          .then((response)=>{
            if(!response.data.check){
              this.isInvalidPromo = true
              setTimeout(() => {
                this.isInvalidPromo = false
              }, 2000)
            }
          })
        }
      },
      isNumber: function(evt) {
        evt = (evt) ? evt : window.event;
        var charCode = (evt.which) ? evt.which : evt.keyCode;
        if ((charCode > 31 && (charCode < 48 || charCode > 57)) && charCode !== 45) {
          evt.preventDefault();;
        } else {
          return true;
        }
      },
      checkUserName: function () {
        var user_name_to_check = this.userName
        // console.log('vx: usernmae',this.userName)
        Auth.signIn(user_name_to_check.toLowerCase(), '123').then(res => {
          return 'Username already exists';
      }).catch(error => {
        const code = error.code;
        console.log(error);
        switch (code) {
            case 'UserNotFoundException':
              // console.log("vx: UserNotFoundException")
              this.checkUserNameExists = true
              return true
              //  return !this.redirectToRegister(email);
            case 'UserLambdaValidationException':
              console.log("UserLambdaValidationException")
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
      // watch: {
      // phoneNumber: function(newValue) {
      //   const result = newValue.replace(/\D/g, "")
      //     .replace(/\B(?=(\d{3})+(?!\d))/g, ",");
      //   Vue.nextTick(() => this.phoneNumber = result);
      // }
      // },
      acceptNumber() {
        var x = this.value.replace(/\D/g, '').match(/(\d{0,3})(\d{0,3})(\d{0,4})/);
        this.value = !x[2] ? x[1] : '(' + x[1] + ') ' + x[2] + (x[3] ? '-' + x[3] : '');
      },
      setPrivacyChecked () {
        this.privacyPolicyChecked = true
      },
      setTermsChecked () {
        this.termsChecked = true
      },
      setDisclaimerChecked () {
        this.disclaimerChecked = true
      },
      setLicensingChecked () {
        this.licensingChecked = true
      },
      setCountryCode({name, iso2, dialCode}) {
       if (this.$vuetify.breakpoint.name === 'xs'){
         this.screenTypeSmall = true
       }
     },
      validate () {
        this.$refs.form.validate()
      },
      async resendVerificationCode () {
        axios.get('https://api.honely.com/lookup-test/email_verification_service?email='+this.email)
        .then(async () => {
          this.regError='Too much time has elapsed. Please sign up again.'
          // this.regError='Too much time has elapsed. Please click register again.'
          // this.confirmationCode = null
          // this.confirmationCodeRequested = false
        }).catch(async ()=>{
          await this.$store.dispatch('auth/cognitoResendConfirmationCode',{username : this.userName})
          this.regError='Resent Email Verification Code!'
          this.confirmationCodeRequested=true
        })
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      showForm: function (displayTab) {
        this.displayLoginOverlay(displayTab)
      },
      signUp () {
        var fullPhone = ""
        if (this.phoneNumber == ""){
          fullPhone = ""
        }
        else{
          fullPhone = this.countryCode + this.phoneNumber
          fullPhone = fullPhone.replace(/-/g, "");
        }
        var emailConsent = this.emailConsentChecked?'TRUE':'FALSE'
        // vx: getting subtypes addiotinal info
        this.homeOwnerType = this.$store.getters['auth/selectedHomeOwnerTypes']
        this.homeAddress = this.$store.getters['auth/homeAddress']
        this.homeZipCode = this.$store.getters['auth/homeZipCode']
        this.interestedZipcode = this.$store.getters['auth/interestedZipCode']
        this.serviceProviderType = this.$store.getters['auth/selectedServiceProviderType']
        this.$store.dispatch('auth/setSelectedHomeOwnerTypes', null)
        this.$store.dispatch('auth/setSelectedServiceProviderType', null)
        this.$store.dispatch('auth/setInterestedZipCode', null)
        this.$store.dispatch('auth/setHomeAddress', null)
        this.$store.dispatch('auth/setHomeZipCode', null)
        var siteLeads = "FALSE"
        if (this.userType === 'Service Provider') {
          siteLeads = "TRUE"
          this.userType = this.serviceProviderType
        }
        if (this.userType === 'Homeowner') {
          this.userType = ''
          if (this.homeOwnerType) {
            this.userType = this.homeOwnerType[0]
          }
          for (let x = 1; x < this.homeOwnerType.length; x++) {
            this.userType = this.userType + ',' + this.homeOwnerType[x]
          }
        }
        const params = {
          first_name: this.firstName,
          last_name: this.lastName,
          email: this.email,
          membership_type: 'FREE',
          phone_number: fullPhone,
          user_name: this.userName,
          email_consent: emailConsent,
          user_type: this.userType,
          site_leads: siteLeads,
          button_leads: "FALSE",
          // home_url: null,
          interested_zip_codes: this.interestedZipcode,
          home_address: this.homeAddress,
          home_zip_code: this.homeZipCode,
        }
        if(this.passwordAgain != this.password){
          this.regError='Passwords must match.'
        }
        else{
        axios.get('https://api.honely.com/lookup-test/email_verification_service?email='+this.email)
        .then(async ()=>{
          try{
        await this.$store.dispatch('auth/cognitoSignUp',{username : this.userName, password: this.password, email: this.email, phone_number: fullPhone, first_name: this.firstName, last_name: this.lastName})
        this.regError='Email Confirmation Code sent to your inbox! (If you haven\'t received it, please check your spam folder.)'
        this.confirmationCodeRequested=true
        // axios.post('https://api.honely.com/lookup-test/unconfirmed_user_addition', params)
        axios.post('https://api.honely.com/lookup/unconfirmed_user_addition', params)
        } catch (error) {
          if (error.message=='User already exists') {
            this.regError='Username already exists. Please use a different username or sign in to existing account'
          } else {
            this.regError=error.message
          }
        }
        }).catch((error)=>{
          if(this.email==''){
            this.regError='Please enter a valid email.'
          } else {
            this.regError='This email is already in use. Please use a different email address or sign in to existing account'
          }
        })
        }
      },
      async confirmSignUpAndLogIn() {
        var fullPhone = ""
        if (this.phoneNumber == ""){
          fullPhone = ""
        }
        else{
          fullPhone = this.countryCode + this.phoneNumber
          fullPhone = fullPhone.replace(/-/g, "")
        }
        var emailConsent = this.emailConsentChecked?'TRUE':'FALSE'
        var siteLeads = "FALSE"
        // if (this.userType === 'Agent/Broker' || this.userType === 'Lender' || this.userType === 'General Contractor') {
        if (this.userType === 'Service Provider') {
          siteLeads = "TRUE"
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
          home_address: this.homeAddress,
          home_zip_code: this.homeZipCode,
        }
        try {
          this.showLoading = true
          await this.confirmSignUp()
          await axios.delete('https://api.honely.com/lookup-test/unconfirmed_user_deletion', {data: {email: this.email}})
          // vx: add zipcode to leadZipCodeList and send tier 2 lead
          this.$store.dispatch('auth/setLeadDetails', null)
          this.$store.dispatch('auth/setLeadZipCodeList', null)
          var leadZipCodeList = []
          if (this.interestedZipcode) {
            leadZipCodeList.push(this.interestedZipcode)
            const paramsTier2 = {
              first_name: this.firstName,
              last_name: this.lastName,
              user_email: this.email,
              phone_number: fullPhone,
              address: this.interestedZipcode,
              zip_code: this.interestedZipcode,
              message: '',
              user_types: ["AGENT/BROKER"],
              tier: '2.1',
            }
            axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier2)
          }
          if (this.homeZipCode) {
            if (!leadZipCodeList.includes(this.homeZipCode)) {
              leadZipCodeList.push(this.homeZipCode)
            }
            var tier2Targets = []
            if (this.homeOwnerType.includes('Seller')) {
              tier2Targets.push('AGENT/BROKER')
              tier2Targets.push('GENERAL CONTRACTOR')
            }
            if (this.homeOwnerType.includes('Refinancer')) {
              tier2Targets.push('LENDER')
            }
            const paramsTier22 = {
              first_name: this.firstName,
              last_name: this.lastName,
              user_email: this.email,
              phone_number: fullPhone,
              address: this.homeZipCode,
              zip_code: this.homeZipCode,
              message: '',
              user_types: tier2Targets,
              tier: '2.1',
            }
            axios.post('https://api.honely.com/lookup-test/leads_tier_notification', paramsTier22)
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
              if (this.$route.path.startsWith('/forecast') || this.$route.path === '/subscribe') {
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
          this.regError=error.message
          this.showLoading = false
        }
        //   await this.confirmSignUp()
        // .then(async ()=>{
        //   await this.signIn()
        //   .then(()=>{
        //     this.$emit('successfulLogin')
        //     bus.$emit('successfulLogin')
        //     axios.post('https://api.honely.com/lookup/register_service', params)
        //     })}).catch((error)=>{
        //       console.log('Error while confirming and signing in', error)
        //       this.regError=error
        //       })
        // await this.$store.dispatch('auth/cognitoConfirmSignUp',{username : this.firstName, code: this.confirmationCode})
        // await this.$store.dispatch('auth/cognitoSignIn',{username : this.firstName, password: this.password})
    // try {
    //   var username=this.firstName
    //   var code=this.confirmationCode
    //   await Auth.confirmSignUp(username, code);
    //   this.regError=null
    //   await this.signIn()
    //   this.$emit('successfulLogin')
    //   bus.$emit('successfulLogin')
    //   console.log('user confirmed')
    // } catch (error) {
    //     this.regError=error
    //     console.log('error confirming sign up', error);
    // }
},
async confirmSignUp() {
  // try {
    await this.$store.dispatch('auth/cognitoConfirmSignUp',{username : this.userName, code: this.confirmationCode})
    this.regError=null
    this.confirmationCodeRequested=true
  // } catch (error) {
  //   this.regError=error
  //   console.log('error confirming sign up', error);
  // }
},
async userExist(username) {
  // console.log("vx: UserExists check for: ",username)
  return Auth.signIn(username.toLowerCase(), '123').then(res => {
    return false;
  }).catch(error => {
    const code = error.code;
    // console.log('vx: in userExists- error', error);
    switch (code) {
        // case 'UserNotFoundException':
        //    return !this.redirectToRegister(email);
        case 'NotAuthorizedException':
          return true
        case 'PasswordResetRequiredException':
          return true
        case 'UserNotConfirmedException':
          return true
        default:
          return false;
    }
  });
},
async signIn() {
  try{
    await this.$store.dispatch('auth/cognitoSignIn',{username : this.userName, password: this.password})
  } catch (error) {
    this.regError=error
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
// async isUserLoggedIn() {
//     try {
//         await Auth.currentAuthenticatedUser();
//         console.log("user is logged in");
//     } catch {
//         console.log("user is logged out");
//     }
// },
      submit () {
        var fullPhone = ""
        if (this.phoneNumber == ""){
          fullPhone = ""
        }
        else{
          fullPhone = this.countryCode + this.phoneNumber
          fullPhone = fullPhone.replace(/-/g, "");
        }
        const params = {
          firstName: this.firstName,
          lastName: this.lastName,
          email: this.email,
          password: this.password,
          phoneNumber: fullPhone,
        }
        // console.log('vx: submit')
        axios.post('/auth/api/v1/register', params)
          .then(() => {
            // console.log('vx: registered')
            this.logIn({
              username: this.email,
              password: this.password,
            })
              .then(() => {
                this.regError = null
                // console.log('vx: loggedIn ' + this.isRealtor)
                if (this.isRealtor) {
                  this.setIsRealtor()
                } else {
                  // TODO: if this is not in an overlay, need to go to next router route
                  // Should set a prop boolean of isInOverlay or similar
                  // this.$router.push({ name: 'Home' })
                  this.$emit('successfulLogin')
                  bus.$emit('successfulLogin')
                }
              })
              .catch((error) => {
                // console.log('vx: error')
                // console.log(error)
                this.tryingToLogIn = false
                // todo catch 401 not authenticated
                this.regError = error
              })
          })
          .catch(err => {
            // console.log(err)
            if (err.response.status === 409) {
              this.regError = 'This email address has already been registered.'
            } else {
              this.regError = err.response.data
            }
          })
      },
      setIsRealtor () {
        axios.post('/api/v1/set-realtor',
                   {
                     realtorId: this.realtorID,
                   },
                   {
                     headers: {
                       Authorization: 'Bearer ' + this.currentUser,
                     },
                   })
          .then(() => {
            this.$emit('successfulLogin')
          })
          .catch(err => {
            // console.log(err)
          })
      },
    }
  }
</script>

<style lang="sass" scoped>
.v-text-field--outlined > .v-input__control > .v-input__slot
  height: 30px
.v-text-field--enclosed .v-input__append-inner
  margin-top: 0px
.register-button
  margin-top: 5px !important
  font-size: 17px !important
  font-family: $heading-font-family
  letter-spacing: .05em
.register-error
  color: $color-orange
#create-account
  .row
      .col
        padding: 5px 12px !important
  .password-col
    .v-text-field--enclosed.v-text-field--outlined
      .v-input__prepend-inner
        border: 1px solid green
        margin-top: 5px !important
</style>
<style scoped>
.check-box{
  margin-right:30px;
}
.choose-options-buttons button{
  background: #00a650;
  color: white;
  border-radius: 5px;
  padding: 10px;
  margin: 15px;
}
</style>
<style scoped>
#signupLoadingSpinner {
  min-height: 40px;
}
#signupLoadingSpinner >>> .sk-fading-circle {
  margin: 0px auto;
  position: absolute;
  left: 50%;
}
#user-type-dropdown >>> .v-input {
  margin-top: 0px !important;
  padding-top: 0px !important;
}
#service-provider-dropdown >>> .v-input {
  margin-top: 0px !important;
  padding-top: 0px !important;
}
.submittedOptionsIndicator{
  color: #00a650;
  font-size: 30px;
  text-align: center;
}

.fixed-box{
  max-width: 500px;
}
</style>
