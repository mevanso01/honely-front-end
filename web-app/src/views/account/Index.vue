<template>
  <!-- eslint-disable -->
  <!-- profile-container -->
  <div class="profile-container">
    <!-- profile-col-left -->
    <div class="profile-col-left">
      <div class="profile-picture">
        <img :src="fetchAgentImage()" />
      </div>
      <div class="profile-info">
        <div class="profile-info-basic">
          <p class="profile-info-name">{{ userProfile.first_name }} {{ userProfile.last_name }}</p>
          <p class="profile-info-type"><span>{{ userType(userProfile.user_type) }}</span></p>
        </div>
        <div class="profile-info-additional">
          <p><i class="fas fa-envelope-square"></i> {{ userProfile.email }}</p>
          <p><i class="fas fa-phone-square-alt"></i> {{ userProfile.phone_number }}</p>
        </div>
        <div class="profile-options-container">
          <ul class="profile-options">
            <li class="profile-option" id="panel-password" @click="profileOptionSelect('panel-password')"><i class="fas fa-shield-alt"></i> Change Password</li>

            <li
              v-if="isServiceProvider(userProfile.user_type)"
              class="profile-option"
              id="panel-agent"
              @click="profileOptionSelect('panel-agent')">
                <i class="fas fa-building"></i> Edit Agent Info
            </li>
          </ul>
        </div>
      </div>
    </div>
    <!-- /profile-col-left -->

    <!-- profile-col-right -->
    <div class="profile-col-right">
      <!-- form-default -->
      <div class="profile-form active" id="form-profile">
        <p class="form-title">Account Information</p>
        <div class="flex-2cols">
          <p>
            <label>First Name</label>
            <input type="text" name="first-name" id="user-first-name" v-bind:value="userProfile.first_name">
          </p>
          <p>
            <label>Last Name</label>
            <input type="text" name="last-name" id="user-last-name" v-bind:value="userProfile.last_name">
          </p>
        </div>
        <p>
          <label>Email</label>
          <input type="text" name="email" v-bind:value="userProfile.email" readonly>
        </p>
        <p>
          <label>Phone</label>
          <input type="text" name="phone" id="user-phone" v-bind:value="userProfile.phone_number">
        </p>
        <p>
          <label>Sign up for our newsletter?</label>
          <input v-if="checkEmailConsent(userProfile.email_consent)" type="checkbox" name="newletter-setting" id="user-newsletter" checked>
          <input v-else name="newletter-setting" id="user-newsletter" type="checkbox">
        </p>
        <p class="sys-msg" id='sys-msg-user'></p>
        <div class="profile-form-btn-row">
          <button class="bg-primary" @click="updateUserProfile()">Update</button>
        </div>
        <!-- loader -->
        <div class="form-loader-overlay" id="loader-user-profile">
            <div class="form-loader-container">
              <i class="fas fa-spinner fa-spin"></i>
              <span>Updating</span>
            </div>
        </div>
        <!-- /loader -->
      </div>
      <!-- /form-default -->

      <!-- form-password -->
      <div class="profile-form" id="form-password">
        <p class="form-title">Change Password</p>
        <p>
          <label>Old Password</label>
          <input type="password" name="old-password" id="old-password" :disabled="passwordChanged">
        </p>
        <p>
          <label>New Password</label>
          <input type="password" name="new-password" id="new-password" :disabled="passwordChanged">
        </p>
        <p>
          <label>Confirm Password</label>
          <input type="password" name="confirm-password" id="confirm-password" :disabled="passwordChanged">
        </p>
        <p class="sys-msg" v-bind:class="sysMsgColorPwd" id='sys-msg-pwd'>{{sysMsgPwd}}</p>
        <div class="profile-form-btn-row">
          <button @click="backToProfile()"><i class="fas fa-chevron-left"></i> Back to profile</button>
          <button v-if="!passwordChanged" class="bg-primary" @click="updatePassword()">Update</button>
          <button v-if="passwordChanged" class="bg-primary" @click="resetPasswordForm()">Change Password Again</button>
        </div>
      </div>
      <!-- /form-password -->

      <!-- form-profile-->
      <div class="profile-form" id="form-agent">
        <p class="form-title">Edit Agent Information</p>
        <div class="flex-2cols">
          <p>
            <label>First Name</label>
            <input type="text" name="first-name" id="agent-first-name" v-bind:value="agentProfile.first_name">
          </p>
          <p>
            <label>Last Name</label>
            <input type="text" name="last-name" id="agent-last-name" v-bind:value="agentProfile.last_name">
          </p>
        </div>
        <div class="profile-picture-file">
          <label>Profile Picture</label>
          <div class="profile-picture-preset">
            <ul class="profile-picture-preset-list">
              <li v-for="image in presetAvatars" :id="image" class="preset-avatar" v-bind:class="{active : checkAgentImageWithPreset(image)}" @click="presetAvatarSelec(image)">
                <img :src="getPresetAvatarImg(image)">
              </li>
            </ul>
          </div>
          <label>Upload Custom Picture</label>
          <div class="profile-file-input-container">
            <input type="file" name="profile-img-file" id="profile-img-file" accept="image/*" @change="readFileData()">
          </div>
        </div>
        <p>
          <label>Company</label>
          <input type="text" name="company" id="agent-company" v-bind:value="agentProfile.company_name">
        </p>
        <p>
          <label>Company Website</label>
          <input type="text" name="company-url" id="agent-company-url" v-bind:value="agentProfile.home_url">
        </p>
        <p>
          <label>Company Phone</label>
          <input type="text" name="company-phone" id="agent-company-phone" v-bind:value="agentProfile.phone_number">
        </p>
        <p>
          <label>Agent License #</label>
          <input type="text" name="company-license" id="agent-license" v-bind:value="agentProfile.license_number">
        </p>
        <p class="sys-msg" id='sys-msg-agent'></p>
        <div class="profile-form-btn-row">
          <button @click="backToProfile()"><i class="fas fa-chevron-left"></i> Back to profile</button>
          <button class="bg-primary" @click="updateAgentProfile()">Update</button>
        </div>
        <!-- loader -->
        <div class="form-loader-overlay" id="loader-agent-profile">
            <div class="form-loader-container">
              <i class="fas fa-spinner fa-spin"></i>
              <span>Updating</span>
            </div>
        </div>
        <!-- /loader -->
      </div>
      <!-- /form-profile -->
    </div>
    <!-- /profile-col-right -->
  </div>
  <!-- /profile-container -->

  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import Amplify, { Auth } from 'aws-amplify'
  import awsExports from '../../aws-config'
  import config from '../../aws-exports'
  Amplify.configure(config)
  // Amplify.configure(awsExports)
  import axios from 'axios'
  import {
    mapGetters, mapState,
  } from 'vuex'

  export default {
    data: () => ({
      passwordChanged: false,
      email_consent: false,
      promotionalConsentLoading: false,
      toggleError: false,
      user: {},
      userFirstName: '',
      userLastName: '',
      currentPassword: '',
      oldPassword: '',
      newPassword: '',
      referral_user: '',
      referring_user: '',
      passwordformOpen: false,
      show1: false,
      show2: false,
      passwordRules: [
           v => !!v || 'Required.',
           v => v.length >= 8 || 'Min 8 characters',
          //  v => !/^[A-Za-z0-9]*$/.test(v) || 'Password should contain atleast 1 special characters',
          //  v => !/[0-9~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(v) || 'Password should contain atleast 1 letter',
          //  v => !/[A-Za-z~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(v) || 'Password should contain atleast 1 number',
          //  v => !/[~`!#$%\^&*+=\-\[\]\\';,/{}|\\":<>\?]/.test(v) || 'Password should contain special characters',
        ],
      emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
      email: '',
      startDate: 'No Subscription Found',
      endDate: 'No Subscription Found',
      newPassword: '',
      newPasswordConfirmation: '',
      valid: false,
      renews: "",
      isHiddenSubscription: true,
      subscriptionData: {},
      referralLink: null,
      regError: '',
      refError: '',
      changeError: '',
      sysMsgPwd: '',
      sysMsgColorPwd: 'status-ok',
      firstNameRules: [
        v => !!v || 'First name is required',
      ],
      lastNameRules: [
        v => !!v || 'Last name is required',
      ],
      userProfile: {},
      agentProfile: {},
      presetAvatars: ['avatar_user_01', 'avatar_user_02', 'avatar_user_03', 'avatar_user_04', 'avatar_user_05'],
      imagePreset: null,
      imageFileData: null,
      imageFileExt: null,
    }),
    computed: {
    //   ...mapState('auth', ['currentUser']),
    ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
    //   ...mapGetters('auth', ['loggedIn', 'username', 'firstName', 'lastName']),
    },

    created () {
    //   this.load()
      this.fetchSubscription()
      this.email = this.cognitoUser.attributes.email
      this.fetchPromotionalConsentStatus()
    },
    methods: {
      userType (data) {
        if (data) {
          if (data.toLowerCase() == 'agent/broker') {
            return 'Agent/Broker'
          } else if (data.toLowerCase() == 'lender') {
            return 'Lender'
          } else if (data.toLowerCase() == 'general contractor') {
            return 'General Contractor'
          } else {
            return 'User'
          }
        } else {
          return 'User'
        }
      },
      isServiceProvider (data) {
        if (data) {
          if (data.toLowerCase() == 'agent/broker' || data.toLowerCase() == 'lender' || data.toLowerCase() == 'general contractor') {
            return true
          } else {
            return false
          }
        } else {
          return false
        }
      },
      checkEmailConsent (data) {
        if (data) {
          if (data == true) {
            return true
          } else {
            if (data == false) {
              return false
            } else {
              if (data.toLowerCase() == 'true') {
                return true
              } else {
                return false
              }
            }
          }
        } else {
          return false
        }
      },
      readFileData () {
        console.log('[INFO] Reading file...')
        const fileInput = document.getElementById('profile-img-file')
        if (fileInput) {
          let reader = new FileReader()
          reader.onload = () => {
            // console.log(reader.result)
            this.imageFileData = reader.result.split(',')[1]
            this.imageFileExt = this.getFileExtension(fileInput.files[0].type)
          }
          reader.readAsDataURL(fileInput.files[0])
        }
      },
      getFileExtension (fileType) {
        if (fileType) {
          return fileType.replace('image/', '')
        }
      },
      fetchPromotionalConsentStatus () {
        axios.get('https://api.honely.com/lookup-test/user_profile',
          {
              params: {
                email: this.cognitoUser.attributes.email,
              },
          })
          .then((response) => {
            // console.log(response.data)
            this.userProfile = response.data
            this.email_consent = response.data.email_consent === 'True' ? true : false

            // get agent profile data if user is agent type
            if (this.userProfile) {
              if (this.isServiceProvider(this.userProfile.user_type)) {
                this.fetchAgentProfile()
              }
            }
          })
      },
      fetchAgentProfile () {
        axios.get('https://api.honely.com/lookup-test/agent_profile',
        {
            params: {
              agent_email: this.cognitoUser.attributes.email,
            },
        }).then((response) => {
          // console.log(response.data)
          this.agentProfile = response.data
        }).catch((error) => {
          console.log('error')
        })
      },
      fetchAgentImage () {
        const defaultImg = 'https://honely-files-public.s3.amazonaws.com/images/avatar/avatar_user_01.png'
        if (this.userProfile) {
          if (this.userProfile.image_url) {
            return this.userProfile.image_url
          } else {
            return defaultImg
          }
        } else {
          return defaultImg
        }
      },
      checkAgentImageWithPreset (id) {
        if (this.userProfile) {
          if (this.userProfile.image_url) {
            let imgName = this.userProfile.image_url.replace('https://honely-files-public.s3.amazonaws.com/images/avatar/', '').replace('.png', '')
            if (imgName == id) {
              return true
            } else {
              return false
            }
          } else {
            return false
          }
        } else {
          return false
        }
      },
      togglePromotionalConsentStatus () {
        this.promotionalConsentLoading = true
        var consent= this.email_consent?'TRUE':'FALSE'
        var params = {
          email: this.cognitoUser.attributes.email,
          consent: consent,
        }
        axios.post('https://api.honely.com/lookup/email_consent', params)
        .then(() => {
          this.promotionalConsentLoading = false
        })
        .catch(()=>{
          this.toggleError = true
        })
      },
      openChangeForm (){
        // console.log('this.passwordformOpen',this.passwordformOpen)
        if (this.passwordformOpen === true){
          this.passwordformOpen = false
        }
        else{
        this.passwordformOpen = true
        }
      },
      changePassword (){
        // console.log('oldPassword',this.oldPassword)
        // console.log('newPassword',this.newPassword)
        Auth.currentAuthenticatedUser()
        .then(user => {
            return Auth.changePassword(user, this.oldPassword, this.newPassword);
        })
        .then(data => 
        // console.log(data),
        this.changeError = "Password Changed Succesfully")
        .catch(err => {
        if (err.code == "LimitExceededException"){
          this.changeError = "Password Limit Change Exceeded. Please try again later.";
        }
        else if (err.code == "NotAuthorizedException"){
          this.changeError = "Incorrect Current password provided";
        }
        else if (err.code == "InvalidPasswordException"){
          this.changeError = err.message;
        }
        else{
          this.changeError = "Password Change Failed. Please check your network connection and try again";
        }
        })
      },
      fetchSubscription (){
            // console.log('email',this.cognitoUser.attributes.email)
            axios.get('https://api.honely.com/lookup/fetch-subscription',
                  {
                      params: {
                      email: this.cognitoUser.attributes.email,
                    },
                    // headers: {
                    //   Authorization: 'Bearer ' + this.currentUser,
                    // },
                  })
                   .then((response) => {
            // console.log(response.data)
            // console.log(response.data.referral_link)
            this.referralLink = response.data.referral_link
            this.referring_user = response.data.referral_link_name
            // console.log(response.data.data.end_date)
            // console.log(response.data.data.start_date)
            this.subscriptionData = response.data
            this.startDate = "No Subscription Found"
            this.renews = "No Subscription Found"
            if (response.data == null) {
                this.renews = "No Subscription Found"
            }
            else{
                this.startDate = "Started on " + response.data.data.start_date
                if (response.data.data.renews ==1 ){
                this.isHiddenSubscription = false
                this.renews = "Renews on " + response.data.data.end_date
                }
                else{
                    this.startDate = "Started on " + response.data.data.start_date
                    this.renews = "Expires on " + response.data.data.end_date
                }
            }
          })
      },
      refer () {
        // console.log(this.referralLink)
        // console.log(this.referring_user)
        // console.log(this.email)
        const referralLink = 'https://honely.com/subscribe?code='+this.referralLink + "&name=" + this.referring_user
        const emailTo = this.referral_user
        const emailFrom = this.email

        const params = {
        emailTo: emailTo,
        referralLink: referralLink,
        referring_user: this.referring_user,
        emailFrom: emailFrom,
        subject: "Honely Referral Link"
      }
      console.log('emailTo', emailTo)
      if (emailTo == "" || emailTo == null){
        this.refError = "Email address cannot be empty"
      }
      else{
        console.log('sending email')
        axios.post('https://api.honely.com/lookup/refer_email', params)
          .then((response) => {
              // console.log('email sent', response)
              this.refError = "Email sent to " + emailTo
              // commit('ADD_FAVORITE', obj) //add in state; kyle adding only address, fips and apn
          })
          .catch((error) => {
              console.warn(error)
              this.refError = "Email sending failed. Please check your network connedtion and try again."
              return null
          })
      }

      },
      unsubscribe_stripe () {
        this.$confirm(
          'Are you sure? This cannot be undone.'
        ).then(() => {
          // user click OK
          console.log('[INFO] Unsubscribe comfirmed')
          // console.log(this.subscriptionData.data.subscription_id)
          axios.get('https://api.honely.com/lookup/cancel-subscription',
          {
              params: {
                id: this.subscriptionData.data.subscription_id,
              },
              // headers: {
              //    Authorization: 'Bearer ' + this.currentUser,
              // },
          })
          .then(() => {
            console.log("Unsubscribe")
            location.reload()
          })
          .catch(err => {
            console.log(err)
            this.regError = err.response.data
          })
        }).catch(() => {
          // user click cancel
          console.log('[INFO] Unsubscribe canceled')
        })
      },
      validatePasswordForm () {
        // this.$refs.form.validate()
        const oldPwd = document.getElementById('old-password')
        const newPwd = document.getElementById('new-password')
        const confirmPwd = document.getElementById('confirm-password')
        const sysMsg = document.getElementById('sys-msg-pwd')

        // console.log('new password: ' + newPwd.value)
        // console.log('confirm password: ' + confirmPwd.value)

        if (oldPwd && newPwd && confirmPwd) {
          if (oldPwd.value.trim() == '' || newPwd.value.trim() == '' || confirmPwd.value.trim() == '') {
            if (oldPwd.value.trim() == '') {
              oldPwd.classList.add('error-input')
              this.sysMsgPwd = 'Enter the current password'
              this.sysMsgColorPwd = 'status-error'
              return
            } else {
              oldPwd.classList.remove('error-input')
              this.sysMsgPwd = ''
              this.sysMsgColorPwd = 'status-ok'
            }
            if (newPwd.value.trim() == '') {
              newPwd.classList.add('error-input')
              this.sysMsgPwd = 'Enter the new password'
              this.sysMsgColorPwd = 'status-error'
              return
            } else {
              newPwd.classList.remove('error-input')
              this.sysMsgPwd = ''
              this.sysMsgColorPwd = 'status-ok'
            }
            if (confirmPwd.value.trim() == '') {
              confirmPwd.classList.add('error-input')
              this.sysMsgPwd = 'Enter the confirm password'
              this.sysMsgColorPwd = 'status-error'
              return
            } else {
              confirmPwd.classList.remove('error-input')
              this.sysMsgPwd = ''
              this.sysMsgColorPwd = 'status-ok'
            }
          } else {
            if (newPwd.value !== confirmPwd.value) {
              newPwd.classList.add('error-input')
              confirmPwd.classList.add('error-input')
              this.sysMsgPwd = 'New password and confirm password does not match'
              this.sysMsgColorPwd = 'status-error'
              return false
            } else {
              oldPwd.classList.remove('error-input')
              newPwd.classList.remove('error-input')
              confirmPwd.classList.remove('error-input')
              this.sysMsgPwd = ''
              this.sysMsgColorPwd = 'status-ok'
              this.oldPassword = oldPwd.value
              this.newPassword = newPwd.value
              return true
            }
          }
        } else {
          this.sysMsgPwd = 'Invalid values from inputs'
          this.sysMsgColorPwd = 'status-error'
          return false
        }  
      },
      resetPasswordForm () {
        document.getElementById('old-password').value = ''
        document.getElementById('new-password').value = ''
        document.getElementById('confirm-password').value = ''
        this.sysMsgPwd = ''
        this.sysMsgColorPwd = 'status-ok'
        this.oldPassword = ''
        this.newPassword = ''
        this.passwordChanged = false
      },
      profileOptionSelect (id) {
        const panelToShow = id.replace('panel-', 'form-')
        const optionsToDisable = document.getElementsByClassName('profile-option')
        const panelsToDisable = document.getElementsByClassName('profile-form')
        
        // disable options for none-active ones
        if (optionsToDisable) {
          for (let i = 0; i < optionsToDisable.length; i++) {
            optionsToDisable[i].classList.remove('active')
          }
        }
        // disable panels for none-active ones
        if (panelsToDisable) {
          for (let i = 0; i < panelsToDisable.length; i++) {
            panelsToDisable[i].classList.remove('active')
          }
        }
        
        // make selection active
        if (id != 'panel-profile') {
          document.getElementById(id).classList.add('active')
        }
        document.getElementById(panelToShow).classList.add('active')
      },
      presetAvatarSelec (id) {
        const presetItems = document.getElementsByClassName('preset-avatar')

        if (presetItems) {
          for (let i = 0; i < presetItems.length; i++) {
            presetItems[i].classList.remove('active')
          }
        }
        document.getElementById(id).classList.add('active')
        // update image preset
        this.imagePreset = id + '.png'
      },
      backToProfile () {
        this.profileOptionSelect('panel-profile')
      },
      getPresetAvatarImg (img) {
        if (img) {
          return 'https://honely-files-public.s3.amazonaws.com/images/avatar/' + img + '.png'
        } else {
          return ''
        }
      },
      validateFormFields (fields) {
        let errorField = null
        if (fields) {
          for (let i = 0; i < fields.length; i++) {
            const field = document.getElementById(fields[i])
            if (field) {
              if (field.value.trim() == '') {
                errorField = fields[i]
                field.classList.add('error-input')
                break
              } else {
                field.classList.remove('error-input')
              }
            }
          }
        }

        return errorField
      },
      toggleLoader (id, show) {
        if (show) {
          document.getElementById(id).classList.add('active')
        } else {
          document.getElementById(id).classList.remove('active')
        }
      },
      updateUserProfile () {
        console.log('[INFO] Updating user profile')
        const sysMsg = document.getElementById('sys-msg-user')
        let firstName = document.getElementById('user-first-name')
        let lastName = document.getElementById('user-last-name')
        let phone = document.getElementById('user-phone')
        let newsletter = document.getElementById('user-newsletter')

        if (newsletter) {
          if (newsletter.checked == true) {
            newsletter = true
          } else {
            newsletter = false
          }
        }

        // prepare required fields
        const fields = ['user-first-name', 'user-last-name', 'user-phone']
        const errorChk = this.validateFormFields(fields)

        if (errorChk != null) {
          sysMsg.classList.add('status-error')
          sysMsg.innerHTML = 'Please fill out the field'
          return              
        } else {
          sysMsg.classList.add('status-ok')
          sysMsg.innerHTML = ''
        }

        let body = {
          user_name: this.userProfile.user_name,
          information: ['first_name', 'last_name', 'phone_number', 'email_consent'],
          first_name_value: firstName.value,
          last_name_value: lastName.value,
          phone_number_value: phone.value,
          email_consent_value: newsletter,
        }
        // console.log(JSON.stringify(body))

        //show loader
        this.toggleLoader('loader-user-profile', true)

        // update requst
        const apiURL = 'https://api.honely.com/lookup-test/user_profile_modification'
        const self = this

        fetch(apiURL, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(body),
        }).then(function (response) {
          // console.log(response)
          return response.json()
        }).then(function (data) {
          // hide loader
          self.toggleLoader('loader-user-profile', false)
          console.log(data)
          if (data) {
            if (data.result.toLowerCase() == 'success') {
              self.userProfile.first_name = firstName.value
              self.userProfile.last_name = lastName.value
              self.userProfile.phone_number = phone.value
              self.userProfile.email_consent = newsletter
              // update system message
              sysMsg.classList.add('status-ok')
              sysMsg.innerHTML = 'Profile updated'
            }
          }
        }).catch((err) => {
          // hide loader
          self.toggleLoader('loader-user-profile', false)
          // update system message
          sysMsg.classList.add('status-error')
          sysMsg.innerHTML = 'Something went wrong, please try again later'
          console.log('[ERROR] User profile update API failed => ', err)
        })
      },
      updateAgentProfile () {
        console.log('[INFO] Updating agent profile')

        const sysMsg = document.getElementById('sys-msg-agent')
        let firstName = document.getElementById('agent-first-name')
        let lastName = document.getElementById('agent-last-name')
        let company = document.getElementById('agent-company')
        let companyUrl = document.getElementById('agent-company-url')
        let companyPhone = document.getElementById('agent-company-phone')
        let agentLicense = document.getElementById('agent-license')

        // prepare required fields
        const fields = ['agent-first-name', 'agent-last-name', 'agent-company-phone']
        const errorChk = this.validateFormFields(fields)

        if (errorChk != null) {
          sysMsg.classList.add('status-error')
          sysMsg.innerHTML = 'Please fill out the field'
          return
        } else {
          sysMsg.classList.add('status-ok')
          sysMsg.innerHTML = ''
        }

        // if other fields are empty, make them null
        if (company) {
          if (company.value.trim() == '') {
            company = null
          } else {
            company = company.value
          }
        } else {
          company = null
        }
        if (companyUrl) {
          if (companyUrl.value.trim() == '') {
            companyUrl = null
          } else {
            companyUrl = companyUrl.value
          }
        } else {
          companyUrl = null
        }
        if (agentLicense) {
          if (agentLicense.value.trim() == '') {
            agentLicense = ''
          } else {
            agentLicense = agentLicense.value
          }
        } else {
          agentLicense = ''
        }

        const infoList = ['first_name', 'last_name', 'phone_number', 'company_name', 'home_url']

        if (agentLicense != null) {
          infoList.push('license_number')
        }

        if (this.imageFileData != null) {
          infoList.push('image')
          // null image preset
          this.imagePreset = null
        }

        if (this.imagePreset != null) {
          infoList.push('avatar')
          // null image data
          this.imageFileData = null
        }

        let body = {
          agent_id: this.agentProfile.agent_id,
          information: infoList,
          first_name_value: firstName.value,
          last_name_value: lastName.value,
          phone_number_value: companyPhone.value,
          company_name_value: company,
          home_url_value: companyUrl,
        }

        if (agentLicense != null) {
          body.license_number_value = agentLicense
        }

        if (this.imageFileData != null) {
          body.user_name = this.userProfile.user_name
          body.base64 = this.imageFileData
          body.extension = this.imageFileExt
        }

        if (this.imagePreset != null) {
          body.user_name = this.userProfile.user_name
          body.avatar_value = this.imagePreset
        }

        // console.log(JSON.stringify(body))

        //show loader
        this.toggleLoader('loader-agent-profile', true)

        // update requst
        const apiURL = 'https://api.honely.com/lookup-test/agent_profile_modification'
        const self = this

        fetch(apiURL, {
          method: 'POST',
          headers: {
            'Content-Type': 'application/json',
          },
          body: JSON.stringify(body),
        }).then(function (response) {
          // console.log(response)
          return response.json()
        }).then(function (data) {
          // hide loader
          self.toggleLoader('loader-agent-profile', false)
          // console.log(data)
          if (data) {
            if (data.result.toLowerCase() == 'success') {
              self.agentProfile.first_name = firstName.value
              self.agentProfile.last_name = lastName.value
              self.agentProfile.phone_number = companyPhone.value
              self.agentProfile.company = company
              self.agentProfile.home_url = companyUrl
              self.agentProfile.license_number = agentLicense

              if (self.imagePreset != null) {
                self.userProfile.image_url = 'https://honely-files-public.s3.amazonaws.com/images/avatar/' + self.imagePreset
              } 
              if (self.imageFileData != null) {
                self.userProfile.image_url = 'https://honely-files-public.s3.amazonaws.com/images/' + self.userProfile.user_name + '.' + self.imageFileExt
              }
              // update system message
              sysMsg.classList.add('status-ok')
              sysMsg.innerHTML = 'Profile updated'
            }
          }
        }).catch((err) => {
          // hide loader
          self.toggleLoader('loader-agent-profile', false)
          // update system message
          sysMsg.classList.add('status-error')
          sysMsg.innerHTML = 'Something went wrong, please try again later'
          console.log('[ERROR] Agent profile update API failed => ', err)
        })
      },
      updatePassword () {
        if (this.validatePasswordForm()) {
          // console.log('oldPassword',this.oldPassword)
          // console.log('newPassword',this.newPassword)

          Auth.currentAuthenticatedUser()
          .then(user => {
              return Auth.changePassword(user, this.oldPassword, this.newPassword);
          }).then(data => 
            // console.log(data),
            // this.changeError = "Password Changed Succesfully")
            this.sysMsgPwd = 'Password Changed Succesfully', this.sysMsgColorPwd = 'status-ok', this.passwordChanged = true )
            .catch(err => {
            if (err.code == "LimitExceededException") {
              // this.changeError = "Password Limit Change Exceeded. Please try again later.";
              this.sysMsgPwd = 'Password Limit Change Exceeded. Please try again later.'
              this.sysMsgColorPwd = 'status-error'
            } else if (err.code == "NotAuthorizedException") {
              // this.changeError = "Incorrect Current password provided";
              this.sysMsgPwd = 'Incorrect Current password provided'
              this.sysMsgColorPwd = 'status-error'
            } else if (err.code == "InvalidPasswordException") {
              // this.changeError = err.message;
              this.sysMsgPwd = err.message
              this.sysMsgColorPwd = 'status-error'
            } else {
              // this.changeError = "Password Change Failed. Please check your network connection and try again"
              this.sysMsgPwd = 'Password Change Failed. Please check your network connection and try again'
              this.sysMsgColorPwd = 'status-error'
            }
          })
        }
      },
    },
  }
  
</script>
