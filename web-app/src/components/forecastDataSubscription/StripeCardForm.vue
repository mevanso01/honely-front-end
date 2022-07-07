<template>
  <!-- eslint-disable -->
  <v-dialog
    v-model="dialog"
    scrollable
    persistent
    max-width="500px"
    content-class="stripe-form-dialog-wrapper"
  >
    <div class="stripe-form-dialog">
      <button class="btn-close" @click="hideDialog()">
        <i class="fa fa-times" style="color: black;"/>
      </button>
      <div v-if="loading" class="loading-wrapper">
        <span class='mdi mdi-loading mdi-spin spin'></span>
      </div>
      <div id="stripe-payment-element">
        <!-- Elements will create form elements here -->
      </div>
      <p class="error-message">{{ stripeError }}</p>
      <button class="btn-confirm" @click="onConfirm()">
        <span v-if="isConfirmLoading">loading...</span>
        <span v-else>Confirm</span>
      </button>
    </div>
  </v-dialog>
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapState } from 'vuex'

  export default {
    props: {
      show: Boolean
    },
    data () {
      return {
        loading: true,
        stripe: null,
        elements: null,
        stripeError: null,
        stripePublishableKey: null,
        clientSecret: null,
        isConfirmLoading: false
      }
    },
    watch: {
      show: function () {
        if (this.show && this.loading) {
          setTimeout(() => {
            this.initStripe();
          }, 100);
        }
      }
    },
    computed: {
      ...mapState('auth', ['cognitoUser', 'currentUser']),
      dialog: {
        get () {
          return this.show
        },
        set (value) {
          this.$emit('toggleShow', value)
        }
      }
    },
    mounted () {
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        this.getPaymentIntent();
      }
    },
    methods: {
      hideDialog () {
        this.dialog = false
      },
      getPaymentIntent () {
        axios.get('https://api.honely.com/dev/payments/setup-intent', {
          headers: {
            Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
          }
        })
        .then(response => {
          this.clientSecret = response.data.data['client-secret']
          this.stripePublishableKey = response.data.data['publishable-key']
        }).catch(error => {
          console.log(error)
        })
      },
      initStripe () {
        this.stripe = Stripe(this.stripePublishableKey);
        const options = {
          clientSecret: this.clientSecret,
          appearance: {
            theme: 'stripe',
          },
        };
        this.elements = this.stripe.elements(options);
        const paymentElement = this.elements.create('payment');
        paymentElement.mount('#stripe-payment-element');
        this.loading = false
      },
      onConfirm () {
        this.isConfirmLoading = true
        this.stripe.confirmSetup({
          elements: this.elements,
          redirect: "if_required",
          confirmParams: {
            // return_url: 'https://example.com/account/payments/setup-complete',
          }
        }).then(response => {
          if (response.error) {
            this.stripeError = response.error.message
            this.isConfirmLoading = false
          } else {
            this.isConfirmLoading = false
            this.$emit('successAdded')
            this.hideDialog()
          }
        })
        .catch(error => console.log(error))
      }
    }
  };
</script>

<style>
  .stripe-form-dialog-wrapper {
    background-color: #FFF;
  }
  .stripe-form-dialog {
    min-height: 415px;
    box-sizing: border-box;
    display: flex;
    flex-direction: column;
    justify-content: space-between;
  }
  .stripe-form-dialog .loading-wrapper {
    flex: 1;
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .stripe-form-dialog .loading-wrapper .mdi-spin {
    font-size: 80px;
    color: #24cb43;
  }
  .stripe-form-dialog-wrapper .btn-close {
    position: absolute;
    top: 10px;
    right: 10px;
    background-color: transparent;
    border: none;
    padding: 10px;
    min-width: initial;
  }
  .stripe-form-dialog-wrapper .btn-confirm {
    border-radius: 4px;
    width: 100%;
    margin-top: 20px;
    border: 1px solid #24cb43;
    background-color: #24cb43;
    color: white;
  }
  .stripe-form-dialog-wrapper .error-message {
    margin-top: 15px;
    margin-bottom: 0;
    font-size: 14px;
    color: #df1b41;
  }
  .stripe-form-dialog {
    padding: 40px 20px 30px 20px;
    position: relative;
  }
</style>
