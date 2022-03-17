<template>
  <v-theme-provider light>
    <base-section
      id="about-us-2"
      class="black-digital"
      space="40"
    >
      <v-container
        class="page-payment-message"
      >
        <!-- eslint-disable -->
        <div class="payment-message-wrapper">
          <h3 class="copy-success">Payment Successful!</h3>
          <p>The payment was processed successfully and your subscription plan was updated. Thank you.</p>
          <p>Please allow 30 seconds for changes to take effect.</p>
          <button @click="toLeadsSubscriptions()">Back to Leads Subscriptions</button>
        </div>
        <!-- eslint-disable -->
      </v-container>
  </base-section>
  </v-theme-provider>
</template>

<script>
  import { mapGetters } from 'vuex'
  import axios from 'axios'
  /* eslint-disable */ 
  export default {
    name: 'SectionPaymentSuccess',
    components: {
    },
    data () {
      return {
        referralCode: '',
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      isLogin () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          return this.$store.getters['auth/isCognitoUserLoggedIn']
        } else {
          return false
        }
      },
    },
    created () {
      this.load()
    },
    methods: {
      load () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          // vx: todo- check for stripe subscription
        }
        // TO DO
      },
      toLeadsSubscriptions () {
        window.location.href = '/leads-subscriptions'
      },
    },
  }
</script>

<style>
.page-payment-message {
  width: 100%;
}
.payment-message-wrapper {
  text-align: center;
}
.payment-message-wrapper h3 {
  font-size: 30px;
  margin-bottom: 2.5rem;
}
.payment-message-wrapper p {
  font-size: 18px;
}
.payment-message-wrapper .copy-success {
  color: #07871c;
}
.payment-message-wrapper button {
    background: #07871c;
    color: #ffffff;
    padding: 10px 15px;
    display: block;
    margin: 3rem auto 0 auto;
}
</style>
