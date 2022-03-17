<template>
  <!-- <section id="hero">
    <v-img
      :min-height="minHeight"
      :src="require('@/assets/honely-bg1.jpg')"
      class="top-margin"
    >
      <v-container
        id="searchbox"
        class="fill-height px-4 py-12"
      >
        <div id="content-block">
          <div id="live-area">
            <v-img
              :src="require('@/assets/logo.svg')"
              contain
              :aspect-ratio="2.69"
              width="60%"
              alt="Honely.com"
            />
            <div id="below-logo">
              <base-subheading
                id="home-subhead"
                weight="regular"
                title="Accurate home value-forecasts, neighborhood stats, &amp; so much more!"
              />
              <search-suggestions
                style="margin-top: 25px; margin-left: 30px;"
              />
            </div>
          </div>
        </div>
      </v-container>
    </v-img>
  </section> -->

  <!-- eslint-disable -->
  <div 
    class="home-hero"
  >
    <div class="home-hero-container">
      <div class="home-hero-wrapper">
        <!-- <div class="home-hero-logo-container">
          <img
            :src="require('@/assets/logo_white_w_colors.png')"
            alt="Honely.com"
            class="home-hero-logo"
          />
        </div> -->
        <div class="home-hero-copy">
          <p class="home-hero-large">Find the <span style="color: #06A550 !important;">future</span> value, not the face value.</p>
          <p class="home-hero-small">Honely's industry-leading algorithms provide you with the data and analytics to identify a property or area that’s positioned for growth.</p>
          <p class="home-hero-small mobile-hide">Both the value and quality of our insights go beyond what's currently reserved for institutions and large enterprises. Never before has the public been able to access investor-grade home value forecasts, relocation trends, zip code rankings, and much more.</p>
          <div class="mobile-show">
            <div class="mobile-content">
              <p class="home-hero-small">Both the value and quality of our insights go beyond what's currently reserved for institutions and large enterprises. Never before has the public been able to access investor-grade home value forecasts, relocation trends, zip code rankings, and much more.</p>
            </div>
            <div class="mobile-btn-row">
              <button class="btn_mobile_expand"><i class="fas fa-chevron-down"></i></button>
            </div>
          </div>
        </div>
        <div class="home-hero-search">
          <!-- <input type="text" id="home-hero-search-input">
          <button id="btn_home_search"><i class="fas fa-search"></i> Search</button> -->
          <!-- <label class="home-hero-search-label">Simply enter address or zip to access</label> -->
          <div class="search-helper"><span>Example:  123 W 11th St Apt 1A New York NY 10001</span></div>
          <search-suggestions />
          <!-- <div class="search-helper"><span>Example:  123 W 11 St Apt 1A New York NY 10001</span></div> -->
        </div>
        <div class="home-hero-demo-container">
          <p>Accurate home value-forecasts, neighborhood stats, &amp; so much more!</p>
          <!-- <div class="button-row"> -->
            <!-- <img 
              :src="require('@/assets/chalk-arrow.png')"
              alt="arrow"
              class="btn-arrow"
            /> -->
            <!-- <button id="btn_home_demo" @click="doShowForecastSample"> -->
              <!-- <i class="fas fa-lock"></i> -->
              <!-- <i class="fas fa-share"></i>
              Click to view a sample smart search
            </button>
          </div> -->
        </div>
      </div>
    </div>
    <div class="home-hero-sample-container" id="home-hero-sample-container">
      <div class="home-hero-sample-wrapper">
        <div class="home-sample-title-bar">
          <span>Honely Forecast Sample</span>
          <i class="fa fa-times-thin fa-2x" aria-hidden="true" @click="doCloseSampleModal"></i>
        </div>
        <div class="home-sample-main">
          <img
            :src="require('@/assets/sample-forecast-v2.jpg')"
            alt="Honely Forecast Sample"
            class="home-hero-sample"
          />
        </div>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'SectionHero',
    data () {
      return {
        goAheadFlag: true,
      }
    },
    // updated: function () {
    //   // console.log('vx: ctrl entered updated.. goAheadFlag is', this.goAheadFlag)
    //   if (this.goAheadFlag) {
    //     // this.goAheadFlag = false
    //     setTimeout(() => {
    //       // console.log('vx: in updated.. isCognitoUserLoggedIn is', this.$store.getters['auth/isCognitoUserLoggedIn'])
    //       if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
    //         // console.log('vx: updated of Hero.vue.. A user is logged in!', this.$store.getters['auth/cognitoUser'])
    //         axios.get('https://api.honely.com/lookup/email_verification_service?email=' + this.$store.getters['auth/cognitoUser'].attributes.email)
    //           .then((response) => {
    //             // console.log('vx: email_verification_service returns..', response.data.message)
    //             if (response.data.message === 'User doesn\'t exist.') {
    //               // console.log('vx: gotta send welcome email and insert')
    //               const params = {
    //                 user_name: this.$store.getters['auth/cognitoUser'].username,
    //                 first_name: '',
    //                 last_name: '',
    //                 email: this.$store.getters['auth/cognitoUser'].attributes.email,
    //                 phone_number: '',
    //                 membership_type: 'FREE',
    //               }
    //               if (this.$store.getters['auth/cognitoUser'].username.includes('facebook')) {
    //                 params.first_name = this.$store.getters['auth/cognitoUser'].attributes.first_name
    //                 params.last_name = this.$store.getters['auth/cognitoUser'].attributes.last_name
    //               }
    //               if (this.$store.getters['auth/cognitoUser'].username.includes('google')) {
    //                 params.first_name = this.$store.getters['auth/cognitoUser'].attributes.give_name
    //                 params.last_name = this.$store.getters['auth/cognitoUser'].attributes.family_name
    //               }
    //               axios.post('https://api.honely.com/lookup/register_service', params)
    //             }
    //           })
    //       }
    //     }, 500)
    //   }
    //   this.goAheadFlag = true
    // },
    // mounted () {
    //   // console.log('vx: ctrl entered mounted.. goAheadFlag is', this.goAheadFlag)
    //   if (this.goAheadFlag) {
    //     this.goAheadFlag = false
    //     setTimeout(() => {
    //       // console.log('vx: in mounted.. isCognitoUserLoggedIn is', this.$store.getters['auth/isCognitoUserLoggedIn'])
    //       if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
    //         // console.log('vx: mounted of Hero.vue.. A user is logged in!', this.$store.getters['auth/cognitoUser'])
    //         axios.get('https://api.honely.com/lookup/email_verification_service?email=' + this.$store.getters['auth/cognitoUser'].attributes.email)
    //           .then((response) => {
    //             // console.log('vx: email_verification_service returns..', response.data.message)
    //             if (response.data.message === 'User doesn\'t exist.') {
    //               // console.log('vx: gotta send welcome email and insert')
    //               const params = {
    //                 user_name: this.$store.getters['auth/cognitoUser'].username,
    //                 first_name: '',
    //                 last_name: '',
    //                 email: this.$store.getters['auth/cognitoUser'].attributes.email,
    //                 phone_number: '',
    //                 membership_type: 'FREE',
    //               }
    //               if (this.$store.getters['auth/cognitoUser'].username.includes('facebook')) {
    //                 params.first_name = this.$store.getters['auth/cognitoUser'].attributes.first_name
    //                 params.last_name = this.$store.getters['auth/cognitoUser'].attributes.last_name
    //               }
    //               if (this.$store.getters['auth/cognitoUser'].username.includes('google')) {
    //                 params.first_name = this.$store.getters['auth/cognitoUser'].attributes.give_name
    //                 params.last_name = this.$store.getters['auth/cognitoUser'].attributes.family_name
    //               }
    //               axios.post('https://api.honely.com/lookup/register_service', params)
    //             }
    //           })
    //       }
    //     }, 500)
    //   }
    // },
    components: {
      SearchSuggestions: () => import('@/components/SearchSuggestions'),
    },

    provide: {
      theme: { isDark: true },
    },

    computed: {
      minHeight () {
        const height = this.$vuetify.breakpoint.mdAndUp ? '100vh' : '50vh'

        return `calc(${height} - ${this.$vuetify.application.top}px)`
      },
    },

    methods: {
      doShowForecastSample () {
        document.getElementById('home-hero-sample-container').classList.add('active')
      },
      doCloseSampleModal () {
        document.getElementById('home-hero-sample-container').classList.remove('active')
      },
    },
  }
</script>

<style lang="sass">

  #content-block
    padding-right: 45%
    padding-bottom: 75px
    text-align: left
    position: relative

  #live-area
    width: 1100px

  #home-logo
    width: 80%

  #below-logo
    padding-left: 20px

  .top-margin
    margin-top: -80px

  #home-form-row
    border: 1px solid $color-emerald
    background-color: white
    width: 438px
    box-shadow: 1px 1px 14px #666666
    height: 35px
    overflow: hidden
    position: relative

  #home-form-row span
    display: inline-block
    color: black

  #home-form-row #searchstring
    padding: 3px 10px
    width: 420px
    color: $color-gray-mddk

  #home-subhead
    margin-left:30px
    margin-bottom: 25px
    color: $color-gray-mddk !important
    font-size: 20px !important

  #searchbox
    margin-left:70px
    #home-subhead
      .v-input__control
        .v-input__slot
          border-radius: 3px !important

  #search-btn
    width: 35px
    height: 35px
    background-color: $color-emerald
    display: block
    position: absolute
    top: 0
    right: 0
    color: white
    font-size: 30px
    padding: 0
    line-height: 35px !important

  #search-btn i
    display: block
    width: 100%
    text-align: right
    font-size: 25px
    padding-right: 3px

  ::placeholder
    color: $color-gray-lt

  :-ms-input-placeholder  /* Internet Explorer 10-11 */
    color: $color-gray-lt

  input:focus, textarea:focus, select:focus, button:focus
    outline: none !important

  @media only screen and (max-width: 600px)
    #home-logo
      width: 50%
      margin-top: 75px
    #home-subhead
      font-size: 20px !important
      color: black !important
    #below-logo
      padding-left: 15px
      .suggestions-field
        width: 400px
        margin-top: -10px

  @media only screen and (max-width: 600px)
    #below-logo
      .suggestions-field
        width: 275px
        margin-top: -10px

</style>

<style>
  .v-text-field.v-text-field--enclosed:not(.v-text-field--rounded) > .v-input__control > .v-input__slot, .v-text-field.v-text-field--enclosed .v-text-field__details
  { border-radius: 3px; }
</style>

<style>
.mobile-show, .mobile-content {
  display: none;
}
.mobile-btn-row {
  text-align: center;
  margin-bottom: 10px;
}
.mobile-btn-row button {
  background: transparent;
  color: #e67e22;
  font-size: 25px;
}
.mobile-btn-row button:hover {
  color: #ffffff;
}
.home-hero {
  background-image: url('~@/assets/home-bg-1.jpg');
  background-size: cover;
  background-position: center;
  height: 93vh;
  min-height: 700px;
}
.home-hero-container {
  align-items: center;
  width: 100%;
  height: 100%;
  padding-top: 30px;
}
.home-hero-wrapper {
  width: 40%;
  max-width: 760px;
  background: rgba(0,0,0,0.5);
  color: #ffffff;
  padding: 3rem 0 0 0;
}
.home-hero-logo {
  display: block;
  max-width: 125px;
  margin: 0 2rem;
}
.home-hero-large {
  font-size: 40px;
  font-weight: bold;
}
.home-hero-small {
  font-size: 18px;
}
.home-hero-copy {
  padding: 0 2rem;
}
.home-hero-search {
  position: relative;
  margin: 0;
  padding: 1rem 2rem 0 2rem;
  background: rgba(0,0,0,0.6)
}
.search-helper {
  font-size: 12px;
}
.search-helper span {

}
.home-hero-search .suggestions-field {
  box-shadow: none;
}
.home-hero-search .v-icon.v-icon.v-icon--link {
  color: #ffffff;
}
.home-hero-search fieldset {
  border: none !important;
}
.home-hero-search .v-text-field--outlined, .home-hero-search .v-text-field--solo {
  border: none;
  border-radius: 0;
  margin-bottom: 0 !important;
}
.home-hero-search .v-text-field.v-text-field--enclosed:not(.v-text-field--rounded) > .v-input__control > .v-input__slot, .v-text-field.v-text-field--enclosed .v-text-field__details {
  border-radius: 0 !important;
}
#btn_home_search i {
  margin-right: 5px;
}
.home-hero-search-label {
  display: block;
  padding-bottom: 5px;
  font-size: 18px;
}
.home-hero-demo-container {
  background: rgba(0,0,0,0.6);
  padding-top: 1rem;
}
.home-hero-demo-container p {
  display: block;
  margin-bottom: 0 !important;
  padding: 1rem 1.5rem;
  text-align: center;
}
.button-row {
}
#btn_home_demo {
  display: block;
  width: 100%;
  background: #e67e22;
  padding: 1rem 1.5rem;
  -webkit-transition: all 0.30s ease-in-out;
  -moz-transition: all 0.30s ease-in-out;
  -ms-transition: all 0.30s ease-in-out;
  -o-transition: all 0.30s ease-in-out;
}
#btn_home_demo:hover {
  background: #07871c;
}
/* #btn_home_demo:hover i:before {
  content: '\f09c';
} */
#btn_home_demo i {
  margin-right: 5px;
}
img.btn-arrow {
  width: 35px;
  vertical-align: bottom;
}
.home-hero-sample-container {
  display: none;
  position: fixed;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  background: rgba(10, 10, 10, 0.5);
  border-radius: 4px;
  z-index: 999;
}
.home-hero-sample-container.active {
  display: block;
}
.home-hero-sample-wrapper {
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%,-50%);
    width: 80%;
    max-width: 1000px;
    min-width: 300px;
    min-height: 300px;
    background: #fff;
    border-radius: 5px;
    z-index: 99;
}
.home-sample-title-bar {
  display: flex;
  align-items: center;
  background: #07871c;
  padding: 10px;
  justify-content: space-between;
  border-top-left-radius: 5px;
  border-top-right-radius: 5px;
}
.home-sample-title-bar span {
  color: #ffffff;
}
.fa-times-thin:before {
  content: '\00d7';
}
.home-sample-title-bar i {
  cursor: pointer;
  color: #ffffff;
}
.home-sample-title-bar i:hover {
  color: #222222;
}
.home-sample-main {
  max-height: 80vh;
  padding-bottom: 1rem;
  overflow-y: scroll;
  border-bottom-left-radius: 5px;
  border-bottom-right-radius: 5px;
}
.home-sample-main img {
  width: 100%;
  height: auto;
}
@media (max-width: 1800px) {
  .home-hero-wrapper {
    width: 50%;
  }
  .home-hero-large {
    font-size: 38px;
  }
}
@media (max-width: 1440px) {
  .home-hero-wrapper {
    width: 60%;
  }
}
@media (max-width: 1200px) {
  .home-hero-wrapper {
    width: 65%;
  }
  #home-hero-search-input {
    width: 80%;
  }
}
@media (max-width: 1000px) {
  .home-hero-wrapper {
    width: 90%;
    margin: 0 auto;
  }
}
@media (max-width: 700px) {
  #home-hero-search-input {
    width: 70%;
  }
  .mobile-hide {
    display: none;
  }
  .mobile-show {
    display: block;
  }
}
@media (max-width: 500px) {
  .home-hero-container {
    padding-top: 0;
  }
  .home-hero {
    height: auto;
    min-height: auto;;
  }
  .home-hero-wrapper {
    width: 100%;
    height: 100%;
  }
  .home-hero-copy, .home-hero-search {
    padding: 1rem;
  }
  #home-hero-search-input {
    width: 74%;
  }
}
</style>
