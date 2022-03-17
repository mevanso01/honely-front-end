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
          <h3 class="copy-failed">Payment Failed</h3>
          <p>The payment was not successfully processed. Please contact our customer support.</p>
          <button @click="toHomePg()">Back to Honely</button>
        </div>
        <!-- eslint-disable -->
      </v-container>
  </base-section>
  </v-theme-provider>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'SectionPaymentFailed',
    components: {
    },
    data () {
      return {
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
        // TO DO
      },
      toHomePg () {
        window.location.href = '/'
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
.payment-message-wrapper .copy-failed {
  color: red;
}
.payment-message-wrapper button {
    background: #07871c;
    color: #ffffff;
    padding: 10px 15px;
    display: block;
    margin: 3rem auto 0 auto;
}
</style>
