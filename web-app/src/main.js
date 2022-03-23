import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import vuetify from './plugins/vuetify'
import VueSimpleAlert from 'vue-simple-alert'
import './plugins'
import VueSpinners from 'vue-spinners'
import VueApexCharts from 'vue-apexcharts'

// import css styles here
import '@/assets/styles/main.css'
import '@/assets/styles/home.css'
import '@/assets/styles/about.css'
import '@/assets/styles/partner.css'
import '@/assets/styles/contact.css'
import '@/assets/styles/contact-agent.css'
import '@/assets/styles/faq.css'
import '@/assets/styles/leads.css'
import '@/assets/styles/landing.css'
import '@/assets/styles/honely-search.css'
import '@/assets/styles/property.css'
import '@/assets/styles/listings.css'
import '@/assets/styles/insights.css'
import '@/assets/styles/forecast.css'
import '@/assets/styles/forecast-form.css'
import '@/assets/styles/account.css'
import '@/assets/styles/static.css'
import '@/assets/styles/popup.css'

Vue.config.productionTip = false
export const bus = new Vue()

Vue.use(VueApexCharts)
Vue.use(require('vue-moment'))
Vue.use(VueSimpleAlert)
Vue.use(VueSpinners)

Vue.prototype.$mapsKey = 'AIzaSyClIFG-ONBwyXrn4_kaA4yMYHGpZD5EEko'
Vue.prototype.$useDevVariables = process.env.VUE_APP_USE_DEV_VARIABLES === 'true'

if (Vue.prototype.$useDevVariables === undefined) {
  Vue.prototype.$useDevVariables = false
}

Vue.component('Apexchart', VueApexCharts)

new Vue({
  router,
  store,
  vuetify,
  mounted: () => document.dispatchEvent(new Event('x-app-rendered')),
  methods: {
  },
  render: h => h(App),
}).$mount('#app')
