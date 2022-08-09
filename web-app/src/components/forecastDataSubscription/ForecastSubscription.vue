<template>
  <!-- eslint-disable -->
  <div class="forecast-subscription-container">
    <div class="paymethods-container">
      <p>Payment methods</p>
      <template v-if="paymethodsLoading">
        <div
          v-for="n in 3"
          class="stripe-paymethod-wrapper"
        >
          <vue-skeleton-loader
            type="circle"
            :width="16"
            :height="16"
          />
          <div class="stripe-logo-wrapper">
            <vue-skeleton-loader
              type="rect"
              radius="8"
              :width="80"
              :height="16"
            />
          </div>
          <vue-skeleton-loader
            type="rect"
            radius="8"
            :width="200"
            :height="20"
          />
        </div>
      </template>
      <template v-else>
        <div
          v-for="paymethod in paymethods"
          :key="paymethod.id"
          class="stripe-paymethod-wrapper"
          @click="handleSelectPaymethod(paymethod.id)"
        >
          <input type="radio" v-model="selectedPaymethodId" :value="paymethod.id">
          <div class="stripe-logo-wrapper">
            <img :src ="'/site_images/icons/' + paymethod.brand + '.png'" alt="" />
          </div>
          <span>●●●● ●●●● ●●●● {{ paymethod.last4 }}</span>
        </div>
      </template>
      <button @click="toggleStripeCardFormShow(true)" class="stripe-card-add-btn">Add another card</button>
      <div class="paymethod-default-checkbox">
        <input type="checkbox" id="defaultCheckBox" v-model="paymethodDefaultChecked">
        <label for="defaultCheckBox">Save this card as default.</label>
      </div>
      <div v-if="promoCodeFlag" class="promocode-container">
        <label>Enter Promo Code (if applicable)</label>
        <input
          type="text"
          :value="promoCode.toUpperCase()"
          @input="promoCode = $event.target.value.toUpperCase()"
        />
        <p v-if="promoCodeError" class="promocode-error">{{ promoCodeError }}</p>
      </div>
      <button v-if="!promoCodeFlag" class="bg-primary" @click="doSubscription">
        <span v-if="isSubscribing">Loading...</span>
        <span v-else>
          <span v-if="subscriptionPrice === 1499">Subscribe </span>
          <span v-else>Purchase </span>
           for ${{ (subscriptionPrice / 100).toFixed(2) }}
        </span>
      </button>
      <button v-else class="bg-primary" @click="handleCreateSubscription">
        Continue
      </button>
      <p v-if="subScriptionError" class="subscription-error">{{ subScriptionError }}</p>
    </div>
    <stripe-card-form
      :show="showStripeCardForm"
      @toggleShow="toggleStripeCardFormShow"
      @successAdded="handlePaymethodAdded"
    />
  </div>
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapState, mapGetters } from 'vuex'
  import VueSkeletonLoader from 'skeleton-loader-vue'

  const promocodeList = ['HONELY20', 'HONELYBA1']

  export default {
    components: {
      VueSkeletonLoader,
      StripeCardForm: () => import("@/components/forecastDataSubscription/StripeCardForm"),
    },
    data () {
      return {
        showStripeCardForm: false,
        paymethodsLoading: true,
        paymethods: [],
        selectedPaymethodId: null,
        paymethodDefaultChecked: false,
        subscriptionPrice: 1499,
        subScriptionError: null,
        isSubscribing: false,
        promoCode: "",
        promoCodeFlag: false,
        promoCodeError: null
      }
    },
    computed: {
      ...mapState('auth', ['cognitoUser']),
      ...mapGetters('listings', ['subscriptionMode'])
    },
    mounted () {
      if (this.subscriptionMode?.price) {
        this.subscriptionPrice = this.subscriptionMode.price
      }
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        this.getPaymethods()
      }
    },
    beforeDestroy () {
      this.$store.dispatch('listings/resetSubscription')
    },
    methods: {
      toggleStripeCardFormShow (value) {
        this.showStripeCardForm = value
      },
      getPaymethods () {
        this.paymethodsLoading = true
        axios.get('https://api.honely.com/dev/payments/payment-methods', {
          headers: {
            Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
          }
        })
        .then(response => {
          this.paymethods = response.data.data
          this.selectedPaymethodId = response.data.data.find(paymethod => paymethod.default)?.id
          this.paymethodDefaultChecked = this.selectedPaymethodId ? true : false
          this.paymethodsLoading = false
        }).catch(error => {
          this.paymethodsLoading = false
          console.log(error)
        })
      },
      handleSelectPaymethod (paymethodId) {
        this.selectedPaymethodId = paymethodId
      },
      handlePaymethodAdded () {
        this.getPaymethods()
      },
      doSubscription () {
        if (!this.selectedPaymethodId) {
          this.subScriptionError = 'Please select a payment method'
          return
        }
        this.subScriptionError = null
        this.isSubscribing = true
        if (this.subscriptionPrice === 1499) {
          this.promoCodeFlag = true
          this.promoCodeError = null
        } else this.handleCreatePayment()
      },
      handleCreateSubscription () {
        if (this.promoCode && !promocodeList.includes(this.promoCode)) {
          this.promoCodeError = 'Invalid Promo Code'
          return
        } else {
          this.promoCodeError = ''
        }
        axios.post('https://api.honely.com/dev/payments/create-subscription',
          {
            "payment-method": this.selectedPaymethodId,
            "default-pm": this.paymethodDefaultChecked,
            "promo-code": this.promoCode
          },
          {
            headers: {
              Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
            }
          }
        )
        .then(response => {
          if (response.data.data.message === 'Subscription Successful') {
            this.isSubscribing = false
            const redirectUrl = this.subscriptionMode.successURL
            this.$store.dispatch('listings/resetSubscription')
            if (redirectUrl) {
              window.location.href = redirectUrl
            } else {
              window.location.reload()
            }
          } else {
            this.isSubscribing = false
            this.subScriptionError = response.data.data.error
          }
        })
        .catch(error => {
          this.isSubscribing = false
          this.subScriptionError = error
        })
      },
      handleCreatePayment () {
        let params = null
        if (this.subscriptionMode?.propertyId) {
          params = {
            amount: this.subscriptionPrice,
            "payment-method": this.selectedPaymethodId,
            "property-id": this.subscriptionMode.propertyId,
            "default-pm": this.paymethodDefaultChecked
          }
        } else if (this.subscriptionMode.zipCode) {
          params = {
            amount: this.subscriptionPrice,
            "payment-method": this.selectedPaymethodId,
            "zip-code": this.subscriptionMode.zipCode,
            "default-pm": this.paymethodDefaultChecked
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
            const redirectUrl = this.subscriptionMode.successURL
            this.$store.dispatch('listings/resetSubscription')
            window.location.href = redirectUrl
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
  .forecast-subscription-container {
    margin: 30px 0;
    border-top: 1px solid #0000001A;
    border-bottom: 1px solid #0000001A;
    padding: 20px 0;
  }
  .stripe-card-add-btn {
    padding: 0;
    border: none;
    outline: none;
    background: transparent;
    color: #000;
    font-size: 18px;
    font-weight: 700;
    margin: 20px 0;
  }
  .stripe-card-add-btn:hover {
    color: #24cb43;
    background: transparent;
  }
  .stripe-paymethod-wrapper {
    display: flex;
    align-items: center;
    margin-bottom: 15px;
    cursor: pointer;
  }
  .stripe-paymethod-wrapper span {
    font-size: 18px;
  }
  .stripe-logo-wrapper {
    border: 1px solid #0000001A;
    border-radius: 3px;
    width: 54px;
    height: 25px;
    padding: 5px;
    box-sizing: border-box;
    display: flex;
    align-items: center;
    justify-content: center;
    margin: 0 15px;
  }
  .stripe-logo-wrapper img {
    width: 100%;
    height: 100%;
    object-fit: contain;
    box-sizing: border-box;
  }
  .paymethod-default-checkbox {
    display: flex;
    align-items: center;
    margin: 20px 0;
  }
  .paymethod-default-checkbox label {
    margin-left: 10px;
    font-size: 18px;
  }
  p.subscription-error {
    color: #df1b41;
    font-size: 16px;
    margin-top: 16px;
    margin-bottom: 0;
  }
  .promocode-container {
    display: flex;
    flex-direction: column;
    margin: 30px 0;
  }
  .promocode-container label {
    font-size: 16px;
    font-weight: 600;
    color: #000;
    margin-bottom: 10px;
  }
  .promocode-container input {
    max-width: 300px;
  }
  .promocode-container p.promocode-error {
    color: #df1b41;
    font-size: 16px;
    margin-top: 4px;
    margin-bottom: 0;
  }
</style>
