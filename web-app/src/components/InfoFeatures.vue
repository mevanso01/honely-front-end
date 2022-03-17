<template>
  <div>
    <div id="insightSignUpSection">
    <base-info-card
      title="Sign Up For Our Insights"
      class="footerhdr"
    />

    <v-form
      v-if="!sent"
      ref="form"
      v-model="valid"
      :lazy-validation="lazy"
    >
    <p><span style="color: rgb(189, 189, 189); font-family: lato, verdana, sans-serif; font-size: 15px; font-style: normal; font-variant-ligatures: normal; font-variant-caps: normal; font-weight: 400; letter-spacing: 0.46875px; orphans: 2; text-align: left; text-indent: 0px; text-transform: none; white-space: normal; widows: 2; word-spacing: 0px; -webkit-text-stroke-width: 0px; caret-color: rgb(189, 189, 189); background-color: rgb(82, 82, 82); text-decoration-thickness: initial; text-decoration-style: initial; text-decoration-color: initial; display: inline !important; float: none;">Please provide your email in the form below and you will be notified when a new insight gets posted.</span></p>
      <v-card
        class="d-flex justify-start rounded form-flex-container"
        flat
        tile
      >
        <v-icon
          class="form-email-icon"
        >
          {{ 'mdi-email' }}
        </v-icon>
        <v-text-field
          v-model="email"
          :rules="emailRules"
          class="form-email-input"
          placeholder="Enter your email"
          required
        />
        <v-btn
          class="email-form-submit"
          elevation="0"
          @click="sendForm()"
        >
          Subscribe
        </v-btn>
      </v-card>
    </v-form>
    </div>
    <h3 v-if="sent">
      Thank you for signing up for our insights! You will be notified when a new insight gets posted.
    </h3>

    <base-info-card
      title="Follow Us"
      class="footerhdr mt-10"
    />
    <div class="social-btn-container">
      <v-btn
        class="mr-2 fb"
        fab
        dark
        small
        elevation="0"
        outlined
        href="https://www.facebook.com/HonelyRealEstate/"
        target="_blank"
      >
        <v-icon dark>
          mdi-facebook
        </v-icon>
      </v-btn>
      <v-btn
        class="mr-2 youtube"
        fab
        dark
        small
        elevation="0"
        outlined
        href="https://www.youtube.com/channel/UC00wpLkRR7IbCDbNbuFX10A"
        target="_blank"
      >
        <v-icon dark>
          mdi-youtube
        </v-icon>
      </v-btn>
      <v-btn
        class="mr-2 insta"
        fab
        dark
        small
        elevation="0"
        outlined
        href="https://www.instagram.com/honelypredictiveanalytics/?hl=en"
        target="_blank"
      >
        <v-icon dark>
          mdi-instagram
        </v-icon>
      </v-btn>
    </div>
  </div>
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'InfoFeatures',

    data: () => {
      return {
        lazy: true,
        sent: false,
        valid: false,
        email: '',
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        error: '',
      }
    },
    methods: {
      validate () {
        this.$refs.form.validate()
      },
      sendForm () {
        this.validate()

        var me = this

        var params = {
          email: this.email,
        }

        setTimeout(function () {
          if (me.valid) {
            axios.post('https://api.honely.com/lookup/user_insights', params)
              .then(() => {
                me.sent = true
              })
              .catch((err) => {
                me.error = err.message
              })
          }
        }, 500)
      },
    },
  }
</script>

<style lang="sass">
  #home-footer a
    text-decoration: none

  .footerhdr h3.text-h6
    text-transform: none !important
    color: $color-gray-lt !important
    font-weight: normal !important
    font-size: 24px !important

  .quickfix
    background-position-y: 0

  .text-body-1 div
    font-size: 15px
    line-height: 20px

  .form-flex-container
    background-color: transparent !important

  .email-form-submit
    background-color: #06a550 !important
    height: 45px !important
    border-top-left-radius: 0 !important
    border-bottom-left-radius: 0 !important

  .form-email-icon
    background-color: $color-gray-dk !important
    color: $color-gray-med !important
    height: 45px
    width: 45px
    border-bottom-left-radius: 5px !important
    border-top-right-radius: 0 !important
    border-bottom-right-radius: 0 !important
    font-size: 21px !important

  .form-email-input
    margin: 0
    padding: 5px 10px
    height: 45px
    background-color: $color-gray-shadowlt

  .form-email-input .v-input__slot:before, .form-email-input .v-input__slot:after
    display: none

  .form-email-input input
    border: none

  .social-btn-container .v-btn
    border: 1px solid $color-gray-med
    color: $color-gray-med
    border-radius: 6px

  .social-btn-container .v-btn.fb:hover
    background-color: #3b5998
    color: #ffffff
    border-color: $color-gray-lt

  .social-btn-container .v-btn.youtube:hover
    background-color: #FF0000
    color: #ffffff
    border-color: $color-gray-lt

  .social-btn-container .v-btn.insta:hover
    background: linear-gradient(to bottom left, #8943bd, #f30208 50%, #fae100)
    color: #ffffff
    border-color: $color-gray-lt
</style>
<style scoped>
#insightSignUpSection >>> .v-messages__message {
  margin-top: 5px;
}
</style>
