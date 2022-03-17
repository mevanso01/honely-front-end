<template>
  <v-container>
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
        <!-- site-leads -->
        <div v-if="isAgent" class="leads-container">
          <div class="leads-section-title theme1">
            <div>Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (Agent/Broker)
            </div>
            <span v-if="site_leads_agent && selectedA.length > 0 && $vuetify.breakpoint.smAndDown && !acknowledgedA" class="leads-section-title-download2" @click="acknowledgeLeads(agent)">Claim leads</span>
            <span v-if="$vuetify.breakpoint.smAndDown && acknowledgedA" class="acknowledged-tick">Claimed<i class="fas fa-check"></i></span>
            <span v-if="site_leads_agent && selectedA.length > 0 && $vuetify.breakpoint.mdAndUp && !acknowledgedA" class="leads-section-title-download2" @click="acknowledgeLeads(agent)">Claim Leads</span>
            <span v-if="$vuetify.breakpoint.mdAndUp && acknowledgedA" class="acknowledged-tick">Claimed<i class="fas fa-check"></i></span>
            <!-- <span v-if="site_leads_agent && site_leads_agent.length > 0" class="leads-section-title-download" title="Export to CSV file" @click="generateCsv('SITE')"><i class="fas fa-download"></i></span> -->
          </div>
          <!-- <div v-if="site_leads && site_leads.length > 0" class="leads-data-container">
            <div v-for="lead in site_leads" class="lead-block">
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
          <div v-if="leadsLoaded && site_leads_agent && site_leads_agent.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedA"
              :headers="headers2"
              :items="unacknowledgedTier2Tier3LeadsA"
              item-key="lead_id"
              :items-per-page="5"
              class="elevation-1"
            >
            <!-- <template v-slot:top>
              <v-switch
                v-model="singleSelectA"
                label="Single select"
                class="pa-3"
              ></v-switch>
            </template> -->
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
          <div class="leads-section-title theme1">
            <div>Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (Lender)
            </div>
            <span v-if="site_leads_lender && selectedL.length > 0 && $vuetify.breakpoint.smAndDown && !acknowledgedL" class="leads-section-title-download2" @click="acknowledgeLeads(lender)">Claim leads</span>
            <span v-if="$vuetify.breakpoint.smAndDown && acknowledgedL" class="acknowledged-tick">Claimed<i class="fas fa-check"></i></span>
            <span v-if="site_leads_lender && selectedL.length > 0 && $vuetify.breakpoint.mdAndUp && !acknowledgedL" class="leads-section-title-download2" @click="acknowledgeLeads(lender)">Claim Leads</span>
            <span v-if="$vuetify.breakpoint.mdAndUp && acknowledgedL" class="acknowledged-tick">Claimed<i class="fas fa-check"></i></span>
            <!-- <span v-if="site_leads_lender && site_leads_lender.length > 0" class="leads-section-title-download" title="Export to CSV file" @click="generateCsv('SITE')"><i class="fas fa-download"></i></span> -->
          </div>
          <!-- <div v-if="site_leads && site_leads.length > 0" class="leads-data-container">
            <div v-for="lead in site_leads" class="lead-block">
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
          <div v-if="leadsLoaded && site_leads_lender && site_leads_lender.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedL"
              :headers="headers2"
              :items="unacknowledgedTier2Tier3LeadsL"
              item-key="lead_id"
              :items-per-page="5"
              class="elevation-1"
            >
            <!-- <template v-slot:top>
              <v-switch
                v-model="singleSelectL"
                label="Single select"
                class="pa-3"
              ></v-switch>
            </template> -->
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
          <div class="leads-section-title theme1">
            <div>Leads from 
              <span class="leads-section-title-bold">Honely</span>
              (General Contractor)
            </div>
            <span v-if="site_leads_gc && selectedGC.length > 0 && $vuetify.breakpoint.smAndDown && !acknowledgedGC" class="leads-section-title-download2" @click="acknowledgeLeads(gc)">Claim leads</span>
            <span v-if="$vuetify.breakpoint.smAndDown && acknowledgedGC" class="acknowledged-tick">Claimed<i class="fas fa-check"></i></span>
            <span v-if="site_leads_gc && selectedGC.length > 0 && $vuetify.breakpoint.mdAndUp && !acknowledgedGC" class="leads-section-title-download2" @click="acknowledgeLeads(gc)">Claim Leads</span>
            <span v-if="$vuetify.breakpoint.mdAndUp && acknowledgedGC" class="acknowledged-tick">Claimed<i class="fas fa-check"></i></span>
            <!-- <span v-if="site_leads_gc && site_leads_gc.length > 0" class="leads-section-title-download" title="Export to CSV file" @click="generateCsv('SITE')"><i class="fas fa-download"></i></span> -->
          </div>
          <!-- <div v-if="site_leads && site_leads.length > 0" class="leads-data-container">
            <div v-for="lead in site_leads" class="lead-block">
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
          <div v-if="leadsLoaded && site_leads_gc && site_leads_gc.length > 0" class="leads-table">
            <v-data-table
              v-model="selectedGC"
              :headers="headers2"
              :items="unacknowledgedTier2Tier3LeadsGC"
              item-key="lead_id"
              :items-per-page="5"
              class="elevation-1"
            >
            <!-- <template v-slot:top>
              <v-switch
                v-model="singleSelectGC"
                label="Single select"
                class="pa-3"
              ></v-switch>
            </template> -->
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
              :headers="headers"
              :items="button_leads"
              :items-per-page="5"
              class="elevation-1"
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
  </v-container>
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
    },
    data () {
      return {
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
        zipSlotRecords: [
          {
            zipcode: '00000',
            slotsAvailable: 3,
          },
          {
            zipcode: '00001',
            slotsAvailable: 3,
          },
          {
            zipcode: '00002',
            slotsAvailable: 3,
          },
          {
            zipcode: '00003',
            slotsAvailable: 3,
          },
          {
            zipcode: '00004',
            slotsAvailable: 3,
          },
          {
            zipcode: '00005',
            slotsAvailable: 3,
          },
        ],
        zipCart: [],
        agentOwnedZipcodes: [],
        zipPurchasePower: 2,
        urlPrompt: '',
        headers: [
          { text: 'Name', value: 'first_name' },
          // { text: 'Last Name', value: 'last_name' },
          { text: 'Email', value: 'user_email' },
          { text: 'Phone Number', value: 'phone_number' },
          { text: 'Searched Address', value: 'searched_address' },
        ],
        headers2: [
          { text: 'First Name', value: 'first_name' },
          { text: 'Last Name', value: 'last_name' },
          { text: 'Email', value: 'user_email' },
          { text: 'Phone Number', value: 'phone_number' },
          { text: 'Searched Address', value: 'searched_address' },
          { text: 'Received at', value: 'time_stamp' },
        ],
        site_leads_agent: [],
        site_leads_lender: [],
        site_leads_gc: [],
        button_leads: [],
        agent_id: null,
        loading: true,
        screenSize: this.$vuetify.breakpoint.name,
        publishableKey: 'pk_test_51Jf6iUKnYAv7HjWU4iktr1OvlsX9aSTiUJAF16nsG2lJIwEkS2k6yOorjuFk7VHQ5bjExBa0V7WP9fVCuvGnora100CiipkNxY',
        successURL: 'https://d3vysvze1cydzh.cloudfront.net/paymentSuccess',
        cancelURL: 'https://d3vysvze1cydzh.cloudfront.net/',
        tab: 'tab-3',
        showButtonSubscribeSection: false,
        showButtonSubscriptionDetails: false,
        showButtonUnsubscribeBtn: false,
        btnSubscriptionStartDate: null,
        btnSubscriptionEndDate: null,
        btnSubscriptionRenewalDate: null,
        btnSubscriptionId: null,
        homeUrl: null,
        enteredHomeUrl: '',
        trialUsed: false,
        userType: null,
        leadSlotSearchInput: '',
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
      unacknowledgedTier2Tier3LeadsA () {
        return this.site_leads_agent.map(x => ({ ...x, isSelectable: ((x.tier === 'Honely Green' || x.tier === 'Honely Red') && !x.acknowledged) }))
      },
      unacknowledgedTier2Tier3LeadsL () {
        return this.site_leads_lender.map(x => ({ ...x, isSelectable: ((x.tier === 'Honely Green' || x.tier === 'Honely Red') && !x.acknowledged) }))
      },
      unacknowledgedTier2Tier3LeadsGC () {
        return this.site_leads_gc.map(x => ({ ...x, isSelectable: ((x.tier === 'Honely Green' || x.tier === 'Honely Red') && !x.acknowledged) }))
      },
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
        axios.get('https://api.honely.com/lookup-test/fetch_leads?agent_id=' + this.agent_id)
          .then((response) => {
            // console.log(response.data)
            this.loading = false
            this.site_leads_agent = response.data.site_leads.agent
            if (this.site_leads_agent) {
              for (let x = 0; x < this.site_leads_agent.length; x++) {
                this.site_leads_agent[x].time_stamp = this.site_leads_agent[x].time_stamp.substring(0, 19)
                if (this.site_leads_agent[x].tier === '1') {
                  this.site_leads_agent[x].tier = 'Honely Orange'
                }
                if (this.site_leads_agent[x].tier === '2') {
                  this.site_leads_agent[x].tier = 'Honely Green'
                }
                if (this.site_leads_agent[x].tier === '3') {
                  this.site_leads_agent[x].tier = 'Honely Red'
                }
              }
            }
            this.site_leads_lender = response.data.site_leads.lender
            if (this.site_leads_lender) {
              for (let x = 0; x < this.site_leads_lender.length; x++) {
                this.site_leads_lender[x].time_stamp = this.site_leads_lender[x].time_stamp.substring(0, 19)
                if (this.site_leads_lender[x].tier === '1') {
                  this.site_leads_lender[x].tier = 'Honely Orange'
                }
                if (this.site_leads_lender[x].tier === '2') {
                  this.site_leads_lender[x].tier = 'Honely Green'
                }
                if (this.site_leads_lender[x].tier === '3') {
                  this.site_leads_lender[x].tier = 'Honely Red'
                }
              }
            }
            this.site_leads_gc = response.data.site_leads.gc
            if (this.site_leads_gc) {
              for (let x = 0; x < this.site_leads_gc.length; x++) {
                this.site_leads_gc[x].time_stamp = this.site_leads_gc[x].time_stamp.substring(0, 19)
                if (this.site_leads_gc[x].tier === '1') {
                  this.site_leads_gc[x].tier = 'Honely Orange'
                }
                if (this.site_leads_gc[x].tier === '2') {
                  this.site_leads_gc[x].tier = 'Honely Green'
                }
                if (this.site_leads_gc[x].tier === '3') {
                  this.site_leads_gc[x].tier = 'Honely Red'
                }
              }
            }
            this.button_leads = response.data.button_leads
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
