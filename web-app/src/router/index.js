/* eslint-disable */
import Vue from 'vue'
import Router from 'vue-router'
import store from '@/store'

Vue.use(Router)

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  scrollBehavior: (to, from, savedPosition) => {
    if (to.hash) return { selector: to.hash }
    if (savedPosition) return savedPosition
    return { x: 0, y: 0 }
  },
  routes: [
    {
      path: '/',
      component: () => import('@/layouts/main/Index.vue'),
      children: [
        {
          path: '',
          name: 'Home',
          component: () => import('@/views/home/Index.vue'),
          // meta: { requiresAuth: true },
        },
        {
          path: '/claimlead',
          name: 'Claim Lead',
          component: () => import('@/views/claimlead/Index.vue'),         
        },
        {
          path: '/newsroom',
          name: 'Newsroom',
          component: () => import('@/views/newsroom/Index.vue'),         
        },
        {
          path: '/leadmoderation',
          name: 'Lead Moderation',
          component: () => import('@/views/leadmoderation/Index.vue'),         
        },
        {
          path: '/buying',
          name: 'Buying Landing Page',
          component: () => import('@/views/landing-pages/BuyingLandingPage.vue'),         
        },
        {
          path: '/selling',
          name: 'Selling Landing Page',
          component: () => import('@/views/landing-pages/SellingLandingPage.vue'),
        },
        {
          path: '/refinancing',
          name: 'Refinancing Landing Page',
          component: () => import('@/views/landing-pages/RefinancingLandingPage.vue'),
        },
        {
          path: '/investing',
          name: 'Investing Landing Page',
          component: () => import('@/views/landing-pages/InvestingLandingPage.vue'),
        },
        {
          path: 'admin/confirm-realtor/:uuid',
          name: 'Confirm Relator',
          component: () => import('@/views/admin/ConfirmRealtor.vue'),
          meta: { authRequired: true },
        },
        {
          path: 'about',
          name: 'About Us',
          component: () => import('@/views/about/Index.vue'),
          meta: {
            copy: 'About us copy'
          },
        },
        {
          path: 'account',
          name: 'Account',
          component: () => import('@/views/account/Index.vue'),
        },
        {
          path: 'smart-data-subscription',
          name: 'Forecast Data Subscription',
          component: () => import('@/views/forecast-data-subscription/Index.vue'),
        },
        {
          path: 'leads-subscriptions',
          name: 'Leads Subscriptions',
          component: () => import('@/views/leads-subscriptions/Index.vue'),
        },
        {
          path: 'leads-dashboard',
          name: 'Leads Dashboard',
          component: () => import('@/views/leads-dashboard/Index.vue'),
        },
        {
          path: 'sales-dashboard',
          name: 'Sales Dashboard',
          component: () => import('@/views/sales-dashboard/Index.vue'),
        },
        {
          path: 'contact',
          name: 'Contact Us',
          component: () => import('@/views/contact-us/Index.vue'),
        },
        {
          path: 'disclaimer',
          name: 'Disclaimer',
          component: () => import('@/views/disclaimer/Index.vue'),
        },
        {
          path: 'faq',
          name: 'FAQ',
          component: () => import('@/views/faq/Index.vue'),
          meta: {
            copy: '',
            title: 'FAQ',
          },
        },
        {
          path: '/favorite-listings',
          name: 'Favorite Listings',
          component: () => import('@/views/favorite-listings/Index.vue'),
          // meta: { authRequired: true },
        },
        {
          path: 'forecast',
          name: 'Smart Search',
          component: () => import('@/views/forecast/Index.vue'),
        },
        {
          path: 'forecast/:fips/:apn',
          name: 'Smart Search For Property by APN',
          component: () => import('@/views/forecast/Index.vue'),
        },
        {
          // path: 'forecast/:propertyId',
          path: 'forecast/:property_id',
          name: 'Smart Search For Property',
          component: () => import('@/views/forecast/Index.vue'),
        },
        {
          path: 'insights',
          name: 'Insights',
          component: () => import('@/views/insights/Index.vue'),
        },
        {
          path: 'insights/:year/:month/:day/:slug',
          name: 'Insights Post',
          component: () => import('@/views/insights/Post.vue'),
        },
        {
          path: 'listing/:search_id',
          name: 'ListingId',
          component: () => import('@/views/listings/Listing.vue'),
        },
        {
          path: 'listing/:fips/:apn',
          name: 'Listing',
          component: () => import('@/views/listings/Listing.vue'),
        },
        {
          path: 'listing-static/:fips/:apn',
          name: 'ListingStatic',
          component: () => import('@/views/listings/ListingStatic.vue'),
        },
        {
          path: 'listings',
          name: 'Listings',
          component: () => import('@/views/listings/Index.vue'),
        },
        {
          path: 'listings-static',
          name: 'ListingsStatic',
          component: () => import('@/views/listings/IndexStatic.vue'),
        },
        {
          path: 'logout',
          name: 'Logout',
        },
        {
          path: '/my-listings',
          name: 'MyListings',
          component: () => import('@/views/my-listings/Index.vue'),
          meta: { authRequired: true },
        },
        {
          path: '/saved-searches',
          name: 'SavedSearches',
          component: () => import('@/views/saved-searches/Index.vue'),
          // meta: { authRequired: true },
        },
        {
          path: '/my-listings/edit/:fips/:apn',
          name: 'EditListing',
          component: () => import('@/views/my-listings/Edit.vue'),
          meta: { authRequired: true },
        },
        {
          path: 'partner',
          name: 'Partner With Us',
          component: () => import('@/views/partner/Index.vue'),
        },
        {
          path: 'pricing',
          name: 'Pricing',
          component: () => import('@/views/pricing/Index.vue'),
        },
        {
          path: 'post/:year/:month/:day/:slug',
          name: 'Post',
          component: () => import('@/views/insights/Post.vue'),
        },
        {
          path: 'privacy',
          name: 'Privacy Policy',
          component: () => import('@/views/privacy/Index.vue'),
        },
        {
          path: 'terms',
          name: 'Terms of Usage',
          component: () => import('@/views/terms/Index.vue'),
        },
        {
          path: 'licensing',
          name: 'Licensing Agreement',
          component: () => import('@/views/licensing/Index.vue'),
        },
        {
          path: 'cookiepolicy',
          name: 'Cookie Policy',
          component: () => import('@/views/cookiepolicy/Index.vue'),
        },
        {
          path: '/profile',
          name: 'Profile',
          component: () => import('@/views/profile/Index.vue'),
          meta: { authRequired: true },
        },
        {
          path: 'reset-password/:token',
          name: 'ResetPassword',
          component: () => import('@/views/reset-password/Index.vue'),
        },
        /* {
          path: '/subscribe',
          name: 'Subscribe',
          component: () => import('@/views/subscribe/Index.vue'),
        }, */
        {
          path: '/paymentSuccess',
          name: 'Payment Success',
          component: () => import('@/views/paymentSuccess/Index.vue'),
        },
        {
          path: '/slotPaymentSuccess',
          name: 'Payment Success',
          component: () => import('@/views/slotPaymentSuccess/Index.vue'),
        },
        {
          path: '/paymentFailed',
          name: 'Payment Failed',
          component: () => import('@/views/paymentFailed/Index.vue'),
        },
        {
          path: '/login',
          name: 'Login',
          component: () => import('@/views/login/Index.vue'),
        },
        {
          path: '*',
          name: 'FourOhFour',
          component: () => import('@/views/404/Index.vue'),
        },
      ],
    },
  ],
})

router.beforeResolve((routeTo, routeFrom, next) => {
  if (routeTo.name === 'Logout') {
    store.dispatch('auth/logOut')
    store.dispatch('auth/setLeadDetails', null)
    store.dispatch('auth/setLeadZipCodeList', null)
    sessionStorage.clear()
    // redirect to login page
    next({ name: 'Home' })
  } else {
    next()
  }
})

router.beforeEach((routeTo, routeFrom, next) => {
  if (routeTo.query.token) {
    const token = routeTo.query.token
    store.dispatch('auth/setToken', { token: token })

    const query = Object.assign({}, routeTo.query)
    delete query.token
    router.replace({ query })
  }

  const authRequired = routeTo.matched.some((route) => route.meta.authRequired)

  if (store.getters['auth/loggedIn']) {
    store.dispatch('auth/validate').then((validUser) => {
      // Then continue if the token still represents a valid user,
      // otherwise redirect to login.
      validUser != null ? next() : redirectToHome()
    })
  } else {
    if (authRequired) {
      redirectToHome()
    } else {
      next()
    }
  }

  function redirectToHome () {
    // Pass the original route to the login component
    router.push({ name: 'Home', query: { redirectFrom: routeTo.fullPath } })
  }
})

export default router
