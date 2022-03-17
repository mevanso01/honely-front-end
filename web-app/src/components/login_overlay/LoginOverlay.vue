<template>
  <v-overlay
    :value="showOverlay"
  >
    <v-container
      class="login-form-container"
      style="overflow-y:scroll; position:relative; height:90vh; display: flex;"
    >
      <v-row
        :style="screenTypeSmall ? 'margin-top:5%' : 'margin-top:40px'"
        align="center"
        justify="center"
      >
        <v-col
          md="12"
        >
          <v-card
            class="elevation-3 login-form"
            light
            :min-width="$vuetify.breakpoint.xs ? '250px' : '600px'"
          >
          <button
            class="btn"
            style="position: absolute;
                  top: -15px;
                  right: -5px;
                  border: none;
                  outline: none;
                  padding: 20px 20px;
                  color: white;"
            @click="onCloseOverlay()"
          >
            <i
              class="fa fa-times-thin fa-2x"
              style="color: black;"
            >
            </i>
          </button>
          <v-row
            justify="center"
            class="login-title
            align-center
            d-none
            d-sm-flex"
            no-gutters
          >
            <!-- <v-col
              v-if="tab == tabs.indexOf('create-account')"
              sm="4"
              class="align-center"
            >
              <div
                class="text-h5 modal-header"
              >
                Sign Up
              </div>
            </v-col>
            <v-col
              v-if="tab == tabs.indexOf('home-owner')"
              sm="4"
              class="align-center"
            >
              <div
                class="text-h5 modal-header"
              >
                Homeowner Options
              </div>
            </v-col>
            <v-col
              v-if="tab == tabs.indexOf('service-provider')"
              sm="4"
              class="align-center"
            >
              <div
                class="text-h5 modal-header"
              >
                Service Provider Options
              </div>
            </v-col>
            <v-col
              v-if="tab == tabs.indexOf('forgot-password')"
              sm="4"
              class="align-center"
            >
              <div
                class="text-h5 modal-header"
              >
                Forgot Password
              </div>
            </v-col>
            <v-col
              v-if="tab == tabs.indexOf('sign-in')"
              sm="4"
              class="align-center"
            >
              <div
                class="text-h5 modal-header"
              >
                Account Login
              </div>
            </v-col> -->
            <!-- <v-col
              class='text-right'
              sm="3"
            >
              <button
                class="btn"
                @click="onCloseOverlay()"
              >
                <i
                  class="fa fa-times-circle fa-lg"
                >
                </i>
              </button>

            </v-col> -->
          </v-row>
          <v-row
            class="sign-in-tabs-row"
            v-if="tab == tabs.indexOf('sign-in') || tab == tabs.indexOf('create-account')"
          >
            <div class="sign-in-tabs">
              <button
                :class="tab == tabs.indexOf('sign-in') ? 'tabs-btn selected' : 'tabs-btn' "
                @click="changeTab('sign-in')"
              >
                  Sign In
              </button>
              <button
                :class="tab == tabs.indexOf('create-account') ? 'tabs-btn selected' : 'tabs-btn' "
                @click="changeTab('create-account')"
              >
                  Sign Up
              </button>
            </div>
          </v-row>

            <!-- <v-row
              justify="center"
            >
              <v-col
                md="10"
                class="form-link-col"
              >
                <v-row
                  no-gutters
                  justify="space-between"
                  class="form-links"
                >
                  <v-col
                    v-if="tab != tabs.indexOf('sign-in') && tab != tabs.indexOf('home-owner') && tab != tabs.indexOf('service-provider')"
                    md="6"
                  >
                    <a @click="changeTab('sign-in')"> Sign in instead &gt; </a>
                  </v-col>
                  <v-col
                    v-if="tab != tabs.indexOf('forgot-password') && tab != tabs.indexOf('home-owner') && tab != tabs.indexOf('service-provider')"
                    md="6"
                    :class="{
                      'text-right': tab == tabs.indexOf('create-account'),
                      'text-left': tab != tabs.indexOf('create-account'),
                    }"
                  >
                    <a @click="changeTab('forgot-password')">
                      Forgot your password &gt;
                    </a>
                  </v-col>
                  <v-col
                    v-if="tab != tabs.indexOf('create-account') && tab != tabs.indexOf('home-owner') && tab != tabs.indexOf('service-provider')"
                    md="6"
                    class="text-right"
                  >
                    <a @click="changeTab('create-account')">
                      Create an account instead &gt;
                    </a>
                  </v-col>
                </v-row>
              </v-col>
            </v-row> -->
            <v-row
              justify="center"
            >
              <v-col
                md="10"
                class="py-0"
              >
                <v-container>
                  <v-tabs-items
                    v-model="tab"
                  >
                    <v-tab-item>
                      <login-form
                        @successfulLogin="onLogin"
                        @changeTab="changeTab"
                      />
                    </v-tab-item>
                    <v-tab-item>
                      <create-account-form @successfulLogin="onLogin" />
                    </v-tab-item>
                    <v-tab-item>
                      <forgot-password-form @closeOverlay="hideOverlay" />
                    </v-tab-item>
                    <v-tab-item>
                      <home-owners-form
                        @closeOverlay="hideOverlay"
                        @optionsSubmitted="optionsSubmitted"
                      />
                    </v-tab-item>
                    <v-tab-item>
                      <service-providers-form
                        @closeOverlay="hideOverlay"
                        @optionsSubmitted="optionsSubmitted"
                      />
                    </v-tab-item>
                  </v-tabs-items>
                </v-container>
              </v-col>
            </v-row>

            <!-- <v-container
              :class="{'grey lighten-2 pb-2': $vuetify.breakpoint.smAndDown, 'grey lighten-2 pb-2': $vuetify.breakpoint.mdAndUp}"
            >
              <v-row
              no-gutters
              >
                <v-col
                  md="12"
                >
                  <p class="text-center social-auth">
                    Or continue with <a @click="ggSignIn">Google or Facebook</a>
                  </p>
                </v-col>
              </v-row> -->
              <!-- <v-row
                justify="center"
                no-gutters
              >
                <v-col
                  md="5"
                  class="col-google"
                >
                  <v-btn
                    class="lighten-2 text--darken-3 btn-google"
                    block
                    @click="ggSignIn"
                  >
                  </v-btn>
                </v-col>
                <v-col
                  md="5"
                  class="col-facebook"
                >
                  <v-btn
                    class="lighten-2 btn-facebook"
                    block
                    @click="ggSignIn"
                  >
                  </v-btn>
                </v-col>
              </v-row> -->
            <!-- </v-container> -->
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-overlay>
</template>

<script>
  import { Auth } from 'aws-amplify'
  export default {
    components: {
      LoginForm: () => import('@/components/login_overlay/LoginForm'),
      ForgotPasswordForm: () => import('@/components/login_overlay/ForgotPasswordForm'),
      CreateAccountForm: () => import('@/components/login_overlay/CreateAccountForm'),
      HomeOwnersForm: () => import('@/components/login_overlay/HomeOwnersForm'),
      ServiceProvidersForm: () => import('@/components/login_overlay/ServiceProvidersForm'),
    },
    theme: {
      options: { light: true },
    },
    props: {
      showOverlay: Boolean,
    },

    data: () => ({
      tab: 0,
      screenTypeSmall: false,
      tabs: ['sign-in', 'create-account', 'forgot-password', 'home-owner', 'service-provider'],
      userType: null,
    }),
    created () {
      // console.log('small screen', this.$vuetify.breakpoint.name)
      if (this.$vuetify.breakpoint.name === 'xs') {
        // console.log('small screen', this.$vuetify.breakpoint.name)
        this.screenTypeSmall = true
      }
    },
    methods: {
      optionsSubmitted (value) {
        this.$emit('optionsSubmitted', value)
      },
      ggSignIn: function () {
        Auth.federatedSignIn()
      },
      hideOverlay: function () {
        this.$emit('hideLoginOverlay')
      },
      changeTab: function (tabId) {
        this.tab = this.tabs.indexOf(tabId)
      },
      onLogin: function () {
        // refresh page?
        this.hideOverlay()
      },
      onCloseOverlay: function () {
        // refresh page?
        this.hideOverlay()
      },
      signInProvider (provider) {
        window.location.href = '/auth/api/v1/authorize-' + provider
      },
    },
  }
</script>
