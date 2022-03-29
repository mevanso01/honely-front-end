<template>
  <div>
    <!-- eslint-disable -->
      <div class="leads-section">
        <div class="section-heading">My Honely Leads</div>
        <div v-if="!isCognitoUserLoggedIn" style="min-height: 1000px; text-align: center;padding-top: 150px;">
          <h2>Please log in to view your leads</h2>
        </div>
        <div v-else-if="!agentProfileLoaded" style="min-height: 1000px; text-align: center;padding-top: 150px;">
          <div>
            <circle-spin :loading="true" />
          </div>
        </div>
        <div v-else-if="agent_id === null" style="min-height: 1000px; text-align: center;padding-top: 150px;">
          <h2>Only Service Providers have access to this page</h2>
        </div>
        <div v-else>
        <div class="leads-container">
          <div class="leads-section-title theme4">
            <div>Leads from 
              <span class="leads-section-title-bold">Smart Search</span>
              page
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_forecast && site_leads_forecast.length > 0">Click on lead to remove from list</span>
          </div>
          <div v-if="leadsLoaded && site_leads_forecast && site_leads_forecast.length > 0" class="leads-table">
            <v-data-table
              :headers="headers1"
              :items="site_leads_forecast"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="cancelLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <!-- site-leads -->
        <div v-if="isAgent" class="leads-container">
          <div class="leads-section-title theme3">
            <div>Claimed Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (Agent/Broker)
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_agent && site_leads_agent.length > 0">Click on lead to remove from list</span>
          </div>
          <div v-if="leadsLoaded && site_leads_agent && site_leads_agent.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedA"
              :headers="headers2"
              :items="site_leads_agent"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="cancelLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <div v-if="isAgent" class="leads-container">
          <div class="leads-section-title theme1">
            <div>Unclaimed Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (Agent/Broker)
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_agent_unack && site_leads_agent_unack.length > 0">Click on lead to claim it</span>
          </div>
          <div v-if="leadsLoaded && site_leads_agent_unack && site_leads_agent_unack.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedA"
              :headers="headers2"
              :items="site_leads_agent_unack"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="claimLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <!-- /site-leads -->

        <!-- site-leads -->
        <div v-if="isLender" class="leads-container">
          <div class="leads-section-title theme3">
            <div>Claimed Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (Lender)
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_lender && site_leads_lender.length > 0">Click on lead to remove from list</span>
          </div>
          <div v-if="leadsLoaded && site_leads_lender && site_leads_lender.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedL"
              :headers="headers2"
              :items="site_leads_lender"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="cancelLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <div v-if="isLender" class="leads-container">
          <div class="leads-section-title theme1">
            <div>Unclaimed Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (Lender)
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_lender_unack && site_leads_lender_unack.length > 0">Click on lead to claim it</span>
          </div>
          <div v-if="leadsLoaded && site_leads_lender_unack && site_leads_lender_unack.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedL"
              :headers="headers2"
              :items="site_leads_lender_unack"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="claimLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <!-- /site-leads -->

        <!-- site-leads -->
        <div v-if="isGeneralContractor" class="leads-container">
          <div class="leads-section-title theme3">
            <div>Claimed Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (General Contractor)
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_gc && site_leads_gc.length > 0">Click on lead to remove from list</span>
          </div>
          <div v-if="leadsLoaded && site_leads_gc && site_leads_gc.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedGC"
              :headers="headers2"
              :items="site_leads_gc"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="cancelLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <div v-if="isGeneralContractor" class="leads-container">
          <div class="leads-section-title theme1">
            <div>Unclaimed Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (General Contractor)
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && site_leads_gc_unack && site_leads_gc_unack.length > 0">Click on lead to claim it</span>
          </div>
          <div v-if="leadsLoaded && site_leads_gc_unack && site_leads_gc_unack.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedGC"
              :headers="headers2"
              :items="site_leads_gc_unack"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="claimLead($event)"
            >
            </v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <!-- /site-leads -->

        <!-- button-leads -->
        <div class="leads-container">
          <div class="leads-section-title theme2">
            <div>Leads from
              <span class="leads-section-title-bold">Future Value</span>
            </div>
            <span style="float: right; font-size:16px;" v-if="leadsLoaded && button_leads && button_leads.length > 0">Click on lead to remove from list</span>
            <!-- <span v-if="button_leads && button_leads.length > 0" class="leads-section-title-download" title="Export to CSV file" @click="generateCsv('BUTTON')"><i class="fas fa-download"></i></span> -->
          </div>
          <!-- <div v-if="button_leads && button_leads.length > 0" class="leads-data-container">
            <div v-for="lead in button_leads" class="lead-block">
              <div class="lead-name">
                {{ lead.first_name }} {{ lead.last_name }}
              </div>
              <div class="lead-contact">
                <p><i class="fas fa-envelope-square"></i> {{ lead.user_email }}</p>
                <p><i class="fas fa-phone-square-alt"></i> {{ lead.phone_number }}</p>
              </div>
              <div class="lead-info">
                <i class="fas fa-map-marker-alt"></i> <span>{{ lead.searched_address }}</span>
              </div>
            </div>
          </div>
          <div v-else class="leads-data-container">
            <p class="leads-data-msg">There is no leads available for your account.</p>
          </div> -->
          <div v-if="leadsLoaded && button_leads && button_leads.length > 0" class="leads-table">
            <v-data-table
              :headers="headers2"
              :items="button_leads"
              item-key="lead_id"
              :items-per-page="5"
              class="datarow"
              @click:row="cancelLead($event)"
            ></v-data-table>
          </div>
          <div v-else-if="!leadsLoaded">
            <v-data-table
              item-key="lead_id"
              class="elevation-1"
              loading
              loading-text="Loading... Please wait"
            >
            </v-data-table>
          </div>
          <div v-else class="leads-table">
            <p class="leads-data-msg">There are no leads available for your account.</p>
          </div>
        </div>
        <!-- /button-leads -->
      </div>
      </div>
      <!-- eslint-enable -->
      <leads-dashboard-action-confirmation
        v-if="askConfirmation"
        :lead_id="lead_id"
        :confirmation_id="confirmation_id"
        @restorePropDefaults="restorePropDefaults"
    />
  </div>
</template>
<script>
  // import { StripeCheckout } from '@vue-stripe/vue-stripe'
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'

  export default {
    components: {
      // LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
      // StripeCheckout,
      LeadsDashboardActionConfirmation: () => import('@/components/base/LeadsDashboardActionConfirmation'),
    },
    data () {
      return {
        confirmation_id: null,
        lead_id: null,
        askConfirmation: false,
        isAgent: false,
        isLender: false,
        isGeneralContractor: false,
        agent: 'AGENT',
        lender: 'LENDER',
        gc: 'CONTRACTOR',
        agentProfileLoaded: false,
        acknowledgedA: false,
        acknowledgedL: false,
        acknowledgedGC: false,
        leadsLoaded: false,
        // singleSelectA: true,
        // singleSelectL: true,
        // singleSelectGC: true,
        selectedA: [],
        selectedL: [],
        selectedGC: [],
        customerId: 'cus_Kpcon4obPmqUC8',
        headers1: [
          { text: 'Name', value: 'name' },
          { text: 'Email', value: 'email' },
          { text: 'Phone Number', value: 'phone_number' },
          { text: 'Searched Address', value: 'searched_address' },
          { text: 'Message', value: 'message' },
        ],
        headers2: [
          { text: 'Name', value: 'name' },
          { text: 'Email', value: 'email' },
          { text: 'Phone Number', value: 'phone_number' },
          { text: 'Searched Address', value: 'searched_address' },
        ],
        site_leads_forecast: [],
        site_leads_agent: [],
        site_leads_lender: [],
        site_leads_gc: [],
        site_leads_agent_unack: [],
        site_leads_lender_unack: [],
        site_leads_gc_unack: [],
        button_leads: [],
        agent_id: null,
        loading: true,
        screenSize: this.$vuetify.breakpoint.name,
        publishableKey: 'pk_test_51Jf6iUKnYAv7HjWU4iktr1OvlsX9aSTiUJAF16nsG2lJIwEkS2k6yOorjuFk7VHQ5bjExBa0V7WP9fVCuvGnora100CiipkNxY',
        successURL: 'https://d3vysvze1cydzh.cloudfront.net/paymentSuccess',
        cancelURL: 'https://d3vysvze1cydzh.cloudfront.net/',
        tab: 'tab-3',
        userType: null,
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
    },
    mounted () {
      axios.get('https://api.honely.com/lookup-test/agent_profile?agent_email=' + this.cognitoUser.attributes.email)
        .then((response) => {
          this.agent_id = response.data.agent_id
          this.homeUrl = response.data.home_url
          this.userType = response.data.user_type
          this.fetchLeadsList()
          this.loading = false
          this.agentProfileLoaded = true
          if (response.data.user_type.includes('AGENT/BROKER')) {
            this.isAgent = true
          }
          if (response.data.user_type.includes('GENERAL CONTRACTOR')) {
            this.isGeneralContractor = true
          }
          if (response.data.user_type.includes('LENDER')) {
            this.isLender = true
          }
        })
        .catch(() => {
          this.loading = false
          this.agentProfileLoaded = true
        })
    },
    methods: {
      restorePropDefaults () {
        this.askConfirmation = false
        this.confirmation_id = null
        this.lead_id = null
      },
      claimLead (item) {
        this.confirmation_id = item.confirmation_id
        this.askConfirmation = true
      },
      cancelLead (item) {
        this.lead_id = item.lead_id
        this.askConfirmation = true
      },
      acknowledgeLeads (spType) {
        var tier2 = []
        var tier3 = []
        if (spType === this.agent) {
          for (let x = 0; x < this.selectedA.length; x++) {
            if (this.selectedA[x].tier === 'Honely Green') {
              tier2.push(this.selectedA[x].lead_id)
            }
            if (this.selectedA[x].tier === 'Honely Red') {
              tier3.push(this.selectedA[x].lead_id)
            }
          }
        }
        if (spType === this.lender) {
          for (let x = 0; x < this.selectedL.length; x++) {
            if (this.selectedL[x].tier === 'Honely Green') {
              tier2.push(this.selectedL[x].lead_id)
            }
            if (this.selectedL[x].tier === 'Honely Red') {
              tier3.push(this.selectedL[x].lead_id)
            }
          }
        }
        if (spType === this.gc) {
          for (let x = 0; x < this.selectedGC.length; x++) {
            if (this.selectedGC[x].tier === 'Honely Green') {
              tier2.push(this.selectedGC[x].lead_id)
            }
            if (this.selectedGC[x].tier === 'Honely Red') {
              tier3.push(this.selectedGC[x].lead_id)
            }
          }
        }
        var tier2Str = null
        var tier3Str = null
        if (tier2.length > 0) {
          tier2Str = String(tier2[0])
        }
        if (tier3.length > 0) {
          tier3Str = String(tier3[0])
        }
        for (let y = 1; y < tier2.length; y++) {
          tier2Str = tier2Str + ',' + String(tier2[y])
        }
        for (let z = 1; z < tier3.length; z++) {
          tier3Str = tier3Str + ',' + String(tier3[z])
        }
        const ackParams = {
          tier2: tier2Str,
          tier3: tier3Str,
        }
        axios.post('https://api.honely.com/lookup-test/acknowledge_leads', ackParams)
          .then(() => {
            if (spType === this.agent) {
              this.acknowledgedA = true
            }
            if (spType === this.lender) {
              this.acknowledgedL = true
            }
            if (spType === this.gc) {
              this.acknowledgedGC = true
            }
            setTimeout(() => {
              location.reload()
            }, 200)
          })
      },
      fetchLeadsList () {
        // https://api.honely.com/lookup-test/leads/HYQL5126
        axios.get('https://api.honely.com/lookup-test/leads/' + this.agent_id)
          .then((response) => {
            // console.log(response.data)
            this.loading = false
            this.site_leads_forecast = [...response.data.leads.forecast.realtors, ...response.data.leads.forecast.lenders, ...response.data.leads.forecast.gcs]
            this.site_leads_agent = response.data.leads.site.realtors.confirmed
            this.site_leads_lender = response.data.leads.site.lenders.confirmed
            this.site_leads_gc = response.data.leads.site.gcs.confirmed
            this.site_leads_agent_unack = response.data.leads.site.realtors.progress
            this.site_leads_lender_unack = response.data.leads.site.lenders.progress
            this.site_leads_gc_unack = response.data.leads.site.gcs.progress
            this.button_leads = [...response.data.leads.button.realtors, ...response.data.leads.button.lenders, ...response.data.leads.button.gcs]
            this.leadsLoaded = true
          })
          .catch(() => {
            this.loading = false
            this.leadsLoaded = true
          })
      },
      generateCsv (type) {
        axios.get('https://api.honely.com/lookup-test/generate_leads_csv?&agent_id=' + this.agent_id + '&lead_type=' + type)
          .then(() => {
            // https://honely-leads-reports.s3.amazonaws.com/AGHT3476.csv
            window.location.href = 'https://honely-leads-reports.s3.amazonaws.com/' + this.agent_id + '.csv'
          })
      },
    },
  }
</script>
<style>
.datarow tbody:hover{
  cursor: pointer;
}
</style>
