<template>
  <!-- eslint-disable -->
  <div class="header">
    <div class="header-container" id="header-container">
      <!-- header-wrapper -->
      <div class="header-wrapper" :class="{ wide : isWidePage }">
        <!-- header-logo -->
        <div class="header-logo-container">
          <a href="/"><img src="/site_images/logo_honely.png" class="header-logo" alt="Honely"></a>
        </div> 
        <!-- /header-logo -->

        <!-- header-menu-container -->
        <div :class="isCognitoUserLoggedIn?'header-menu-container-logged-in':'header-menu-container'">
          <ul class="header-menu" id="header-menu">
            <li id="btn-mobile-menu-close">
              <span class="mdi mdi-close" @click="toggleMobileMenu"></span>
            </li>
            <li
              v-for="(item, i) in headerMenu"
            >
              <router-link
                v-if="item.text !== 'Realtors'"
                :id="'header-menu-' + i"
                :key="i"
                :to="item.link"
                :class="{ active : getActiveMenuLink == item.label }"
              >
                {{ item.text }}
              </router-link>
              <a v-if="item.text === 'Realtors'" href="https://realtors.honely.com">Realtors</a>
            </li>
          </ul>
        </div>
        <!-- /header-menu-container -->
        
        <!-- header-action-menu -->
        <div :class="isCognitoUserLoggedIn?'header-action-menu-container-logged-in':'header-action-menu-container'">
          <div class="header-welcome-message-container" v-if="isCognitoUserLoggedIn && userProfile">
            <span>Welcome, {{ userProfile.first_name }} {{ userProfile.last_name }}</span>
          </div>
          <div v-if="!($route.name === 'Buying Landing Page' || $route.name === 'Selling Landing Page' || $route.name === 'Refinancing Landing Page' || $route.name === 'Investing Landing Page')">
            <button
              v-if="!isCognitoUserLoggedIn"
              id="btn_login"
              @click="showLoginPopup()"
            >
              Sign in
            </button>
          </div> 
          <div>    
          <span v-if="isCognitoUserLoggedIn" id="btn_user_menu" class="mdi mdi-account-circle" @click="toggleUserMenu"></span></div>
          <div v-if="isCognitoUserLoggedIn" class="menu-user" id="menu-user">
            <ul class="list-menu-user">
              <li
                v-for="(item, i) in actionMenuItems"
              >
                <router-link
                  :id="'header-action-menu-' + i"
                  :key="i"
                  :to="item.link"
                  :class="{ active : getActiveMenuLink == item.label }"
                >
                  {{ item.text }}
                </router-link>
              </li>
              <li
                v-if="isServiceProvider"
                v-for="(item, i) in serviceProviderMenuItems"
              >
                <router-link
                  :id="'header-action-menu-' + (getActionMenuItemCount + i)"
                  :key="i"
                  :to="item.link"
                  :class="{ active : getActiveMenuLink == item.label }"
                >
                  {{ item.text }}
                </router-link>
              </li>
              <li>
                <router-link
                  :id="'header-action-menu-99'"
                  :to="logoutLink.link"
                >
                  {{ logoutLink.text }}
                </router-link>
              </li>
            </ul>
          </div>
          <span class="mdi mdi-menu" id="btn-mobile-menu" @click="toggleMobileMenu"></span>
        </div>
        <!-- /header-action-menu -->
      </div>
      <!-- /header-wrapper -->
    </div>
    <!-- header-placeholder -->
    <div class="header-placeholder"></div>
    <login-popup
      :show="showLogin"
      @toggleShow="toggleLoginPopupShow"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'

  export default {
    name: 'Header',
    components: {
      LoginPopup: () => import('@/components/login_popup/Index'),
    },
    data: () => ({
      headerMenu: [
        /* {
          text: 'Smart Search',
          link: '/forecast',
          label: 'forecast',
        }, */
        {
          text: 'Listings',
          link: '/listings',
          label: 'listings',
        },
        // {
        //   text: 'Insights',
        //   link: '/insights',
        //   label: 'insights',
        // },
        {
          text: 'Realtors',
          link: '',
          label: 'realtors',
        },
        // {
        //   text: 'Newsroom',
        //   link: '/newsroom',
        //   label: 'newsroom',
        // },
        // {
        //   text: 'Subscribe',
        //   link: '/subscribe',
        //   label: 'subscribe',
        // },
        {
          text: 'About Us',
          link: '/about',
          label: 'about',
        },
        {
          text: 'Partner With Us',
          link: '/partner',
          label: 'partner',
        },
        {
          text: 'Subscribe',
          link: '/subscription',
          label: 'subscription',
        },
        // {
        //   text: 'API',
        //   link: '',
        //   label: 'api',
        // },
      ],
      actionMenuItems: [
        /* {
          text: 'My Profile & Settings',
          link: '/profile',
        }, */
        {
          text: 'Account',
          link: '/account',
          label: 'account',
        },
        {
          link: '/favorite-listings',
          text: 'Favorite Listings',
          label: 'favorites',
        },
        /* {
          link: '#',
          text: 'My Messages',
          label: 'messages',
        }, */
        {
          link: '/saved-searches',
          text: 'Saved Searches',
          label: 'saved-searches',
        },
        {
          link: '/smart-data-subscription',
          text: 'Honely Subscription',
          label: 'smart-data-subscription',
        },
        /* {
          link: '/my-listings',
          text: 'My Listings',
          label: 'my-listings',
        },
        {
          link: '#',
          text: 'Submit a Property',
        }, */
      ],
      serviceProviderMenuItems: [
        {
          link: '/leads-subscriptions',
          text: 'Buy Leads',
          label: 'leads-subscriptions',
        },
        {
          link: '/leads-dashboard',
          text: 'My Honely Leads',
          label: 'leads-dashboard',
        },
      ],
      logoutLink: {
        link: '/logout',
        text: 'Log Out',
        label: 'logout',
      },
      showLogin: false,
      // userProfile: null,
    }),
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser', 'userProfile']),
      isWidePage () {
        if (this.$route.name === 'Listings') {
          return true
        } else {
          return false
        }
      },
      getActionMenuItemCount () {
        if (this.actionMenuItems) {
          return this.actionMenuItems.length
        } else {
          return 0
        }
      },
      isServiceProvider () {
        // console.log(this.userProfile)
        let flag = false
        if (this.userProfile && this.userProfile.user_type) {
          if (this.userProfile.user_type.toLowerCase().includes('agent/broker') || this.userProfile.user_type.toLowerCase().includes('lender') || this.userProfile.user_type.toLowerCase().includes('general contractor')) {
            flag = true
          }
        }
        return flag
      },
      getActiveMenuLink () {
        let active = ''
        if (this.$route.name === 'About Us') {
          active = 'about'
        } else if (this.$route.name === 'Listings') {
          active = 'listings'
        } else if (this.$route.name === 'Smart Search') {
          active = 'forecast'
        } else if (this.$route.name === 'Insights') {
          active = 'insights'
        } else if (this.$route.name === 'Newsroom') {
          active = 'newsroom'
        } else if (this.$route.name === 'Partner With Us') {
          active = 'partner'
        } else if (this.$route.name === 'Account') {
          active = 'account'
        } else if (this.$route.name === 'Leads Dashboard') {
          active = 'leads-dashboard'
        } else if (this.$route.name === 'Leads Subscriptions') {
          active = 'leads-subscriptions'
        } else if (this.$route.name === 'Favorite Listings') {
          active = 'favorites'
        } else if (this.$route.name === 'SavedSearches') {
          active = 'saved-searches'
        }
        return active
      },
    },
    watch: {
      '$route' () {
        this.closeMobileMenu()
      },
    },
    created () {
      // window click event for closing user menu
      window.onclick = function (event) {
        if (event.target.id !== 'btn_user_menu' &&
          !event.target.classList.contains('menu-user')) {
          const userMenu = document.getElementById('menu-user')
          const userMenuBtn = document.getElementById('btn_user_menu')
          if (userMenu && userMenuBtn) {
            if (!userMenu.contains(event.target)) {
              if (userMenu.classList.contains('active')) {
                userMenu.classList.remove('active')
                userMenuBtn.classList.remove('active')
              }
            }
          }
        }
      }
    },
    updated () {
      this.$store.dispatch('auth/checkIsCognitoUserLoggedIn')
      // get user profile when login is true but user profile is null
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        if (this.userProfile == null) {
          this.getUserProfile()
        }
      }
    },
    mounted () {
      this.$store.dispatch('auth/checkIsCognitoUserLoggedIn')
      // header function for active and inactive
      let scrollPos = window.scrollY
      const header = document.getElementById('header-container')
      const headerHeight = header.offsetHeight
      const activateHeader = () => header.classList.add('active')
      const deactivateHeader = () => header.classList.remove('active')

      // add event to window
      window.addEventListener('scroll', function () {
        scrollPos = window.scrollY
        if (scrollPos >= headerHeight) {
          activateHeader()
        } else {
          deactivateHeader()
        }
      })
      // get user profile
      if (this.$store.getters['auth/isCognitoUserLoggedIn'] && this.userProfile == null) {
        this.getUserProfile()
      }
    },
    methods: {
      getUserProfile () {
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.cognitoUser.attributes.email,
          },
        }).then((response) => {
          // console.log(response.data)
          // this.userProfile = response.data
          this.$store.dispatch('auth/setUserProfile', response.data)
        })
      },
      toggleUserMenu () {
        const menu = document.getElementById('menu-user')
        const menuBtn = document.getElementById('btn_user_menu')
        if (menu.classList.contains('active')) {
          menu.classList.remove('active')
          menuBtn.classList.remove('active')
        } else {
          menu.classList.add('active')
          menuBtn.classList.add('active')
        }
      },
      toggleMobileMenu () {
        const menu = document.getElementById('header-menu')
        if (menu.classList.contains('active')) {
          menu.classList.remove('active')
        } else {
          menu.classList.add('active')
        }
      },
      closeMobileMenu () {
        const menu = document.getElementById('header-menu')
        if (menu.classList.contains('active')) {
          menu.classList.remove('active')
        }
      },
      showLoginPopup () {
        this.showLogin = true
      },
      toggleLoginPopupShow (value) {
        this.showLogin = value
      },
    },
  }
</script>
