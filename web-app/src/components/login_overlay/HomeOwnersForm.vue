<template>
  <!-- eslint-disable -->
  <div>
    <!-- Questionnaire to come after use selects options -->
    <div class="questionnaire" v-if="submitted">
      <!-- If user chose buying -->
      <div class="buyer-question" v-if="buying">
        <h3 class="question">Are you interested in any specific zip code?</h3>
        <input class="seller-question-input" type="textbox" v-model="interestedArea" placeholder="Enter Zip Code You Are Interested In">
        <!-- <search-suggestions-lead-slots
          class="search-suggestions-lead-slots"
          @fetchSlotCount="fetchSlotCount"
        /> -->
      </div>
      <!-- If user chose selling -->
      <div class="seller-question" v-if="selling || refinancing">
        <h3 class="question">Enter Your Home Address</h3>
        <input class="seller-question-input" type="textbox" v-model="addrLine1" placeholder="Address Line 1">
        <input class="seller-question-input" type="textbox" v-model="addrLine2" placeholder="Address Line 2">
        <input class="seller-question-input" type="textbox" v-model="city" placeholder="City">
        <input class="seller-question-input" type="textbox" v-model="state" placeholder="State">
        <input class="seller-question-input" type="textbox" v-model="zipcode" placeholder="Zip Code">
      </div>
      <div class="bottom-buttons">
        <button class="submitZipcodeButton" @click="submitExtraInfo()">Submit</button>
        <button class="skip-button" @click="skipAndExit()">Skip</button>
      </div>
    </div>
    <!-- Options for Home owners -->
    <div v-else>
      <div class="home-owners-form">
        <!-- Selling -->
        <div class="home-owners-form-container">
          <i class="fas fa-home"></i>
          <label for="selling">Selling</label>
          <input v-model="selling" type="checkbox" id="selling">
        </div>
        <!-- Buying -->
        <div class="home-owners-form-container">
          <i class="fas fa-house-user"></i>
          <label for="buying">Buying</label>
          <input v-model="buying" type="checkbox" id="buying">
        </div>
        <!-- Refinancing -->
        <div class="home-owners-form-container">
          <i class="fas fa-comment-dollar"></i>
          <label for="refinancing">Refinancing</label>
          <input v-model="refinancing" type="checkbox" id="refinancing">
        </div>
        <!-- Other -->
        <div class="home-owners-form-container">
          <i class="fas fa-strikethrough"></i>
          <label for="other">Other</label>
          <input v-model="other" type="checkbox" id="other">
        </div>
      </div>
      <span v-if="errorMessage" class="error-message">{{ errorMessage }}</span>
      <button class="submit-home-options-button" @click="submitHomeOptions()">Submit</button>
      <login-overlay
        ref="loginOverlay"
        :show-overlay="showLoginOverlay"
        :display-tab="overlayTab"
        @hideLoginOverlay="hideLoginOverlay"
      />
  </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'HomeOwnersForm',
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },
    data: () => {
      return {
        selling: false,
        buying: false,
        refinancing: false,
        other: false,
        overlayTab: 'sign-up',
        showLoginOverlay: false,
        submitted: false,
        errorMessage: null,
        interestedArea: null,
        homeAddress: null,
        addrLine1: '',
        addrLine2: '',
        city: '',
        state: '',
        zipcode: '',
      }
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
    methods: {
      submitHomeOptions () {
        var res = []
        if (!this.selling && !this.buying && !this.refinancing && !this.other) {
          this.errorMessage = 'Please select at least one option'
          setTimeout(() => {
            this.errorMessage = null
          }, 1500)
          return
        }
        if (this.selling) res.push('Seller')
        if (this.buying) res.push('Buyer')
        if (this.refinancing) res.push('Refinancer')
        if (this.other) res.push('Other')
        this.$store.dispatch('auth/setSelectedHomeOwnerTypes', res)
        this.submitted = true
        this.$emit('optionsSubmitted', true)
        if (this.other && !this.selling && !this.buying && !this.refinancing) this.$emit('closeOverlay') // if other is  the only option chosen, close overlay
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      submitExtraInfo () {
        this.$store.dispatch('auth/setInterestedZipCode', this.interestedArea)
        this.$store.dispatch('auth/setHomeAddress', this.addrLine1 + ' ' + this.addrLine2 + ' ' + this.city + ' ' + this.state + ' ' + this.zipcode)
        this.$store.dispatch('auth/setHomeZipCode', this.zipcode)
        this.$emit('closeOverlay')
      },
      skipAndExit () {
        this.$emit('closeOverlay')
      },
    },
  }
</script>

<style scoped>
  .home-owners-form {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .home-owners-form i{
    font-size: 35px;
    color: #00a650;
  }

  .home-owners-form input, label{
    display: block;
    margin: auto;
    text-align: center;
    margin-bottom: 5px;
  }

  .home-owners-form-container{
    padding: 15px;
    height: 120px;
    width: 150px;
    border: solid;
    border-width: 3px;
    border-color: #00a650;
    border-radius: 5px;
    text-align: center;
    margin: 25px;
  }

  .submit-home-options-button{
    background: #00a650;
    color: white;
    border-radius: 5px;
    padding: 10px;
    margin-left: 18rem;
  }

  .questionnaire{
    text-align: center;
  }

  .questionnaire .buyer-question .question{
    margin-bottom: 10px;
  }

  .submitZipcodeButton{
    background: #00a650;
    color: white;
    border-radius: 5px;
    padding: 10px;
  }

  .search-suggestions-lead-slots{
    margin: 15px;
  }

  .seller-question-input{
    border: 1px solid #00a650;
    box-shadow: 1px 1px 14px #666666;
    padding: 10px;
    width: 75%;
    border-radius: 5px;
    margin: auto;
    margin-top: 5px;
    margin-bottom: 25px;
    height: 42px;
  }

  .seller-question .question{
    margin-bottom: 10px;
  }

  .skip-button{
    float: right;
    color: #00a650;
    text-decoration: underline;
    margin-top: 10px;
  }

  .error-message{
    float: right;
    color: red;
    margin-top: 5px;
  }

  @media only screen and (max-width: 500px){
    .home-owners-form{
      display: block;
    }
    .submit-home-options-button{
      margin-left: 4rem;
    }
    .questionnaire{
      width: 350px;
    }
    .seller-question-input{
      width: 90%;
    }
  }
</style>
<style>
  .questionnaire .buyer-question .suggestions-field4{
    width: 75% !important;
    margin-left: 12% !important;
  }
</style>
