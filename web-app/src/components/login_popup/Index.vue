<template>
  <!-- eslint-disable -->
  <v-dialog
    v-model="dialog"
    scrollable
    persistent
    max-width="500px"
    content-class="auth-dialog-wrapper"
  >
    <template #default>
      <div class="auth-dialog">
        <div class="auth-dialog-header">
          <!-- header-logo -->
          <div class="header-logo-container">
            <a href="/">
              <img
                src="site_images/logo_honely.png"
                class="header-logo"
                alt="Honely"
              />
            </a>
            <button class="btn-close" @click="hideDialog()">
              <i class="fa fa-times" style="color: black;"/>
            </button>
          </div>
          <!-- header-logo -->
        </div>
        <div class="auth-dialog-content">
          <btn-switch
            v-if="form_type !== 'forgot-password' && (form_type === 'login' || signup_step === 1)"
            :options="[{
              value: 'login',
              label: 'Log In',
            }, {
              value: 'signup',
              label: 'Sign Up',
            }]"
            size="sm"
            :value="form_type"
            :change="onChangeFormType"
          />
          <div style="margin-top: 25px;" />
          <login-form
            v-if="form_type === 'login'"
            :bus="busLoginForm"            
            @stepForgot="onChangeFormType('forgot-password')"
          />
          <signup-form
            v-if="form_type === 'signup'"
            :step="signup_step"
            @stepNext="signupStepNext()"
          />
          <forgot-password-form 
            v-if="form_type === 'forgot-password'"
            @closeOverlay="onChangeFormType('login')" 
          />
        </div>
      </div>
    </template>
  </v-dialog>
  <!-- eslint-enable -->
</template>

<script>
  import Vue from 'vue'
  import { bus } from '@/main'
  export default {
    components: {
      BtnSwitch: () => import('@/components/base/BtnSwitch'),
      LoginForm: () => import('@/components/login_popup/LoginForm'),
      SignupForm: () => import('@/components/login_popup/SignupForm'),
      ForgotPasswordForm: () => import('@/components/login_popup/ForgotPasswordForm'),
    },

    props: {
      show: Boolean,
    },

    data: () => ({
      form_type: 'login',
      signup_step: 1,
      busLoginForm: new Vue(),
    }),

    computed: {
      dialog: {
        get () {
          return this.show
        },
        set (value) {
          this.$emit('toggleShow', value)
        },
      },
    },

    mounted () {
      bus.$on('successfulLogin', this.onSuccessfulLogin)
    },

    beforeDestroy () {
      bus.$off('successfulLogin', this.onSuccessfulLogin)
    },

    methods: {
      isNumber: function (evt) {
        var charCode = (evt.which) ? evt.which : evt.keyCode
        if ((charCode > 31 && (charCode < 48 || charCode > 57))) {
          evt.preventDefault()
        } else {
          return true
        }
      },
      onChangeFormType (value) {
        this.form_type = value
      },
      hideDialog () {
        this.dialog = false
      },
      signupStepNext () {
        this.signup_step++
      },
      onSuccessfulLogin (data) {
        this.hideDialog()
      },
    },

    watch: {
      show: function (newVal, oldVal) {
        if (newVal && !oldVal) {
          this.form_type = 'login'
          this.signup_step = 1
          this.busLoginForm.$emit('reset')
        }
      },
      form_type: function (newVal, oldVal) {
        if (newVal === 'signup' && newVal !== oldVal) {
          this.signup_step = 1
        }
      },
    },
  }
</script>

<style scoped>
</style>
