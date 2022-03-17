<template>
  <div>
    <!-- app-header -->
    <div
      id="app-header"
      class="app-header"
      v-bind:class="{wSearch: !isHome}"
      v-scroll="onScroll"
    >
      <!-- header-left -->
      <div class="header-left">
        <router-link
          id="link-home"
          to="/"
        >
          <img
            class="app-logo"
            :src="'https://www.honely.com/img/logo.4bb82f35.svg'"
          />
        </router-link>
        <router-link
          v-for="(item, i) in items"
          :id="'header-menu-' + i"
          :key="i"
          :to="item.link"
          class="header-links"
        >
          {{ item.name }}
        </router-link>
      </div>
      <!-- /header-left -->

      <!-- header-center -->
      <div
        v-if="!isHome"
        class="header-center"
      >
        <v-btn
          class="mr-2 fb"
          fab
          dark
          x-small
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
          x-small
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
          x-small
          elevation="0"
          outlined
          href="https://www.instagram.com/honelyrealestate/"
          target="_blank"
        >
          <v-icon dark>
            mdi-instagram
          </v-icon>
        </v-btn>
      </div>
      <!-- /header-center -->

      <!-- header-right -->
      <div
        v-if="!isHome"
        class="header-right"
      >
        <search-suggestions-app-bar />
      </div>
      <!-- /header-right -->

      <!-- header-left-most -->
      <div class="header-right-most">
        <v-btn
          v-if="!isCognitoUserLoggedIn"
          id="btn-login"
          plain
          @click="displayLoginOverlay('sign-in')"
        >
          Log In
        </v-btn>
        <!-- <v-btn
          v-if="!isCognitoUserLoggedIn"
          id="btn-signup"
          color="primary"
          elevation="3"
          text
          @click="displayLoginOverlay('create-account')"
        >
          Sign Up
        </v-btn> -->

        <v-btn
          v-if="isCognitoUserLoggedIn && false"
          color="grey darken-2"
          fab
          icon
        >
          <v-icon x-large>
            mdi-bell-outline
          </v-icon>
        </v-btn>
        <template
          v-if="isCognitoUserLoggedIn"
        >
          <v-menu
            v-model="menu"
            :close-on-content-click="true"
            :nudge-width="200"
            offset-x
            offset-y
          >
            <template v-slot:activator="{ on: menu }">
              <v-tooltip left>
                <template v-slot:activator="{ on: tooltip }">
                  <v-btn
                    fab
                    icon
                    color="primary"
                    elevation="0"
                    v-on="{ ...tooltip, ...menu }"
                  >
                    <v-icon x-large>
                      mdi-account-circle
                    </v-icon>
                  </v-btn>
                </template>
                <span>{{ cognitoUser.attributes.email }}</span>
              </v-tooltip>
            </template>
            <v-card>
              <v-list>
                <v-list-item
                  to="/account"
                  link
                >
                  <b>Account</b>
                </v-list-item>
                <v-list-item
                  v-if="userType && (userType.includes('AGENT/BROKER') || userType.includes('LENDER') || userType.includes('GENERAL CONTRACTOR'))"
                  to="/leads-subscriptions"
                  link
                >
                  <b>Buy Leads</b>
                </v-list-item>
                <v-list-item
                  v-if="userType && (userType.includes('AGENT/BROKER') || userType.includes('LENDER') || userType.includes('GENERAL CONTRACTOR'))"
                  to="/leads-dashboard"
                  link
                >
                  <b>My Honely Leads</b>
                </v-list-item>
                <v-list-item
                  v-for="(item, i) in userMenuItems"
                  :key="i"
                  :to="item.link"
                  link
                >
                  <v-list-item-content>
                    <v-list-item-title><b>{{ item.text }}</b></v-list-item-title>
                  </v-list-item-content>
                </v-list-item>
                <v-list-item
                  to="/logout"
                  link
                >
                  <b>Log Out</b>
                </v-list-item>
              </v-list>
            </v-card>
          </v-menu>
        </template>
        <div class="mobile-menu">
          <span id="btn-mobile-menu"><i class="fas fa-bars"></i></span>
          <ul class="mobile-menu-list">
            <li id="btn-mobile-menu-close">
              <i
                class="fa fa-times-thin fa-2x"
                aria-hidden="true"
              >
              </i>
            </li>
            <li
              v-for="(item, i) in items"
              :id="'header-menu-' + i"
              :key="i"
              @click="closeMobileMenu()"
            >
              <router-link
                :to="item.link"
              >
                {{ item.name }}
              </router-link>
            </li>
          </ul>
        </div>
      </div>
      <!-- /header-left-most -->

      <!-- header-search-mobile -->
      <div
        v-if="!isHome"
        class="header-search-mobile"
      >
        <search-suggestions-app-bar />
      </div>
      <!-- /header-search-mobile -->
    </div>
    <!-- /app-header -->
    <div
      id="app-header-placeholder"
      v-bind:class="{wSearch: !isHome}"
    />

    <login-overlay
      ref="loginOverlay"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
    />

    <!-- <home-drawer
      v-model="drawer"
      :items="items"
    /> -->
  </div>
</template>

<script>
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'

  export default {
    name: 'HomeAppBar',

    components: {
      HomeDrawer: () => import('./Drawer'),
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      // SearchSuggestions: () => import('@/components/SearchSuggestions'),
      SearchSuggestionsAppBar: () => import('@/components/SearchSuggestionsAppBar'),
    },

    data: () => ({
      userType: null,
      enableLoginOutsideClick: false,
      showLoginOverlay: false,
      hasColor: false,
      drawer: null,
      menu: false,
      items: [
        {
          name: 'Smart Search',
          link: '/forecast',
        },
        {
          name: 'Listings',
          link: '/listings',
        },
        {
          name: 'Insights',
          link: '/insights',
        },
        // {
        //   name: 'Subscribe',
        //   link: '/subscribe',

        // },
        {
          name: 'About Us',
          link: '/about',
        },
        {
          name: 'Partner With Us',
          link: '/partner',
        },
      ],
      links: [
        '/about',
        'insights',
        '/forecast',
        '/partner',
        '/subscribe',
      ],
      userMenuItems: [
        // { link: '/profile', text: 'My Profile & Settings' },
        // { link: '/account', text: 'Refer A Friend' },
        { link: '/favorite-listings', text: 'Favorite Listings' },
        // { link: '#', text: 'My Messages' },
        { link: '/saved-searches', text: 'Saved Searches' },
        // { link: '/my-listings', text: 'My Listings' },
        // { link: '#', text: 'Submit a Property' },
      ],
      overlayTab: 'sign-up',
    }),

    computed: {
      // ...mapGetters('auth', ['loggedIn', 'username']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      isHome () {
        // return this.$route.name === 'Home'
        if (this.$route.name === 'Home' || this.$route.name === 'Smart Search' || this.$route.name === 'Smart Search For Property') {
          return true
        } else {
          return false
        }
      },
      color () {
        if (this.isHome) {
          return this.hasColor ? 'white' : 'white'
        } else {
          return 'white'
        }
      },
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
    updated () {
      this.$store.dispatch('auth/checkIsCognitoUserLoggedIn')
      this.fetchUserType()
    },
    mounted () {
      this.$store.dispatch('auth/checkIsCognitoUserLoggedIn')
      this.fetchUserType()
    },
    // created () {
    //   this.fetchUserType()
    // },

    methods: {
      closeMobileMenu () {
        var x = document.getElementsByClassName('mobile-menu-list')
        x[0].classList.remove('active')
      },
      fetchUserType () {
        axios.get('https://api.honely.com/lookup-test/user_profile',
                  {
                    params: {
                      email: this.cognitoUser.attributes.email,
                    },
                  })
          .then((response) => {
            this.userType = response.data.user_type
          })
      },
      onScroll () {
        this.hasColor = window.scrollY > 40
        if (window.scrollY > 40) {
          document.getElementById('app-header').classList.add('active')
          document.getElementById('app-header-placeholder').classList.add('active')
        } else {
          document.getElementById('app-header').classList.remove('active')
          document.getElementById('app-header-placeholder').classList.remove('active')
        }
      },
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      toggleVxAuth: function () {
        this.$store.dispatch('auth/toggleVxAuth')
      },
    },
  }
</script>

<style lang="sass">
  #home-app-bar
    .v-tabs-slider
      max-width: 24px
      margin: 0 auto

    .v-tab
      text-transform: none
      font-weight: bold
      font-size: 20px
      color: $color-gray-shadow
      letter-spacing: 0.01em
      &::before
        display: none

    .v-tab:hover
      color: $color-emerald

    .v-tab.v-tab--active
      color: $color-squash
      // border-bottom: 2px solid $color-emerald

    .v-btn
      text-transform: none
      font-weight: bold
      font-size: 20px

  #btn-signup
    background-color: white
    border-radius: 10px

    .v-btn__content
      color: $color-emerald

  #btn-login
    .v-btn__content
      color: $color-gray-shadow
      opacity: 1

  #btn-login
    .v-btn__content:hover
      color: $color-emerald

  #btn-signup, #btn-login
    letter-spacing: 0.01em

  #home-link
    width: 120px
    margin-right: 15px

  #navitem-1
    margin-left: 10px

  .social-btn-container
    .v-btn
      border: 1px solid $color-gray-med
      color: $color-gray-med
      border-radius: 6px

    .v-btn:hover
      color: #ffffff
      border-color: $color-gray-lt

    .v-btn.fb:hover
      background-color: #3b5998

    .v-btn.youtube:hover
      background-color: #FF0000

    .v-btn.insta:hover
      background: linear-gradient(to bottom left, #8943bd, #f30208 50%, #fae100)

  .nav-search-container
    width: 410px

  @media only screen and (max-width: 600px)
    #home-app-bar .v-btn
      font-size: 15px
    .nav-search-container
      width: 150px
</style>

<style>
.app-header {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 76px;
  padding: 10px 0;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
  background: #ffffff;
  z-index: 999;
  -webkit-transition: all 0.50s ease-in-out;
  -moz-transition: all 0.50s ease-in-out;
  -ms-transition: all 0.50s ease-in-out;
  -o-transition: all 0.50s ease-in-out;
}
.app-header.active {
  -webkit-box-shadow: 3px 0px 5px 3px #aaaaaa;
  -moz-box-shadow: 3px 0px 5px 3px #aaaaaa;
  box-shadow: 3px 0px 5px 3px #aaaaaa;
}
#app-header-placeholder {
  height: 76px;
}
.app-header .header-left {
  display: flex;
  align-items: center;
}
.app-header .header-center {
}
.app-header .header-right {
}
.app-header .header-right-most {
  padding-right: 1.5rem;
  display: flex;
  align-items: center;
}
.app-header .header-right-most #btn-signup,
.app-header .header-right-most #btn-login {
  font-size: 18px;
  font-weight: bold;
}
.app-header .header-right .suggestions-field3 {
  margin-bottom: 0 !important;
}
.app-header .header-left img.app-logo {
  width: 135px;
  margin-left: 1.5rem;
  margin-right: 1rem;
}
.app-header .header-left a {
  display: block;
  text-decoration: none;
  color: #222222;
  font-size: 20px;
  font-weight: bold;
  line-height: 100%;
  margin-right: 2rem;
}
.app-header .header-left a:first-of-type {
  margin-right: 1rem;
}
.app-header .header-left a:hover {
  color: #00a650;
}
.app-header .header-links.active, .app-header a.router-link-active {
  color: #00a650;
}
.app-header .theme--dark.v-btn {
   color: #bbbbbb;
}
.mobile-menu {
  display: none;
  margin-left: 1.5rem;
}
#btn-mobile-menu {
  font-size: 28px;
  cursor: pointer;
}
.mobile-menu-list {
  position: fixed;
  bottom: 0;
  right: -280px;
  height: 100%;
  width: 280px;
  background: #fefefe;
  box-shadow: 3px 0px 5px 3px #aaaaaa;
  list-style: none;
  font-size: 20px;
  padding: 0 !important;
  -webkit-transition: all 0.50s ease-in-out;
  -moz-transition: all 0.50s ease-in-out;
  -ms-transition: all 0.50s ease-in-out;
  -o-transition: all 0.50s ease-in-out;
  z-index: 9999;
  overflow-y: scroll;
  -ms-overflow-style: none;  /* IE and Edge */
  scrollbar-width: none;  /* Firefox */
}
.mobile-menu-list::-webkit-scrollbar{
    display: none;
}
.mobile-menu-list.active {
  right: 0;
}
.mobile-menu-list li {
}
.mobile-menu-list li:last-of-type {
}
.mobile-menu-list li#btn-mobile-menu-close {
  cursor: pointer;
  font-size: 25px;
  padding: 5px 20px;
}
.fa-times-thin:before {
  content: '\00d7';
}
.mobile-menu-list li a {
  display: block;
  line-height: 100%;
  padding: 10px 20px;
  text-decoration: none;
  color: #222222;
  border-top: 1px solid #fefefe;
  border-bottom: 1px solid #fefefe;
}
.mobile-menu-list li a:hover,
.mobile-menu-list li a.router-link-active {
  color: #00a650;
  background: #eeeeee;
  border-color: #dddddd;
}
.header-search-mobile {
  display :none;
  width: 100%;
  padding: 10px 10px 0 10px;
}
.header-search-mobile .suggestions-field3 {
  margin-bottom: 0 !important;
  margin-left: auto;
  margin-right: auto;
}
.v-text-field--outlined.v-input--is-focused fieldset, .v-text-field--outlined.v-input--has-state fieldset {
  border: 1px solid #aaaaaa;
}
#navitem-0 {
  display: none;
}
.v-text-field.v-text-field--enclosed:not(.v-text-field--rounded) > .v-input__control > .v-input__slot, .v-text-field.v-text-field--enclosed .v-text-field__details {
  border-radius: 3px !important;
  border-color: transparent;
}
@media only screen and (max-width: 1780px) {
  .header-center {
    display: none;
  }
  .header-right {
    max-width: 465px;
  }
}
@media only screen and (max-width: 1600px) {
  .app-header .header-left img.app-logo {
    width: 115px;
  }
  .app-header .header-left a {
    font-size: 18px;
    margin-right: 1.5rem;
  }
}
@media only screen and (max-width: 1430px) {
  .header-left {
    width: 150px;
  }
  .app-header a.header-links {
    display: none;
  }
  .mobile-menu {
    display: block;
  }
}
@media only screen and (max-width: 1000px) {
  .header-right {
    display: none;
  }
  .header-search-mobile {
    display: block;
  }
  .app-header.wSearch, #app-header-placeholder.wSearch {
    height: 125px;
  }
}
@media only screen and (max-width: 700px) {
  .app-header .header-left img.app-logo {
    width: 90px;
  }
}
@media (max-width: 500px) {
  .header-left {
    width: 115px;
  }
  .app-header .header-left a {
    margin-right: 1rem;
  }
  .app-header .header-left img.app-logo {
    margin-left: 10px;
  }
  .app-header .header-right-most {
    padding-right: 10px;
  }
  .app-header .header-right-most #btn-signup, .app-header .header-right-most #btn-login {
    font-size: 15px;
  }
  .v-btn:not(.v-btn--round).v-size--default {
    padding: 10px;
  }
  .mobile-menu {
    margin-left: 1rem;
  }
}
</style>
