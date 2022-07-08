<template>
  <!-- eslint-disable -->
  <v-dialog
    v-model="dialog"
    scrollable
    persistent
    max-width="500px"
    content-class="singlie-subscription-dialog-wrapper"
  >
    <div class="single-subscription-dialog">
      <h2>You'll be charged $1 for this property.</h2>
      <p>Are you sure you want to continue?</p>
      <div class="btn-group">
        <button class="bg-primary" @click="continueSubscription()">
          <span v-if="isSubscribing">Loading...</span>
          <span v-else>Continue</span>
        </button>
        <button class="bg-primary" @click="hideDialog()">No</button>
      </div>
      <p v-if="subScriptionError">{{ subScriptionError }}</p>
    </div>
  </v-dialog>
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapState } from 'vuex'

  export default {
    props: {
      show: Boolean,
      propertyId: String || Number,
      defaultPaymethod: Object,
      forecastAccess: Boolean,
      zipCode: String || Number
    },
    data () {
      return {
        isSubscribing: false,
        subScriptionError: null
      }
    },
    computed: {
      ...mapState('auth', ['cognitoUser']),
      dialog: {
        get () {
          return this.show
        },
        set (value) {
          this.$emit('toggleShow', value)
        }
      }
    },
    methods: {
      hideDialog () {
        this.dialog = false
      },
      goToSubscriptionPage () {
        this.$store.dispatch('listings/setSubscriptionMode', {
          propertyId: this.propertyId,
          zipCode: this.zipCode,
          price: 100,
          successURL: window.location.href,
          forecastAccess: this.forecastAccess
        })
        this.$router.push('/smart-data-subscription')
      },
      continueSubscription () {
        if (this.defaultPaymethod?.id) {
          this.handleCreatePayment()
        } else {
          this.goToSubscriptionPage()
        }
      },
      handleCreatePayment () {
        this.isSubscribing = true
        this.subScriptionError = null
        let params
        if (this.propertyId) {
          params = {
            amount: 100,
            "payment-method": this.defaultPaymethod.id,
            "property-id": this.propertyId,
            "default-pm": true
          }
        } else if (this.zipCode) {
          params = {
            amount: 100,
            "payment-method": this.defaultPaymethod.id,
            "zip-code": this.zipCode,
            "default-pm": true
          }
        }
        axios.post('https://api.honely.com/dev/payments/create-payment',
          params,
          {
            headers: {
              Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
            }
          }
        )
        .then(response => {
          if (response.data.data.message === 'Payment Successful') {
            this.isSubscribing = false
            this.hideDialog()
            window.location.reload()
          } else {
            this.isSubscribing = false
            this.subScriptionError = response.data.data.error
          }
        })
        .catch(error => {
          this.isSubscribing = false
          this.subScriptionError = error
        })
      }
    }
  }
</script>

<style>
  .singlie-subscription-dialog-wrapper {
    background: #FFF;
  }
  .single-subscription-dialog {
    padding: 40px 20px 30px 20px;
    position: relative;
  }
  .single-subscription-dialog h2 {
    font-size: 20px;
  }
  .single-subscription-dialog p {
    font-size: 18px;
    margin: 20px 0;
  }
  .single-subscription-dialog .btn-group{
    width: 100%;
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 40px;
  }
  .single-subscription-dialog .btn-group button {
    min-width: calc(50% - 15px);
  }
</style>