<template>
  <!-- eslint-disable -->
  <div class="pg-static">
    <div class="section section-wrapper placeholder">
        <!-- if jonathan or venkat, show page -->
        <div v-if="isAuthorized">
        <h1 style="font-size: 20px;">Lead Moderation Page</h1><br><br>
        <p style="color: red;">{{ errorMessage }}</p>
        <!-- show section if get moderation api loaded -->
        <div v-if="getModerationApiLoaded">
        <div class="static-body feature-text text-exsmall">
            <div v-if="moderation_leads">
            <h1>Leads With Moderation Pending</h1><br>
        <v-data-table
            v-model="leadsToDelete"
            :headers="headers"
            :items="moderation_leads"
            item-key="lead_id"
            show-select
        ></v-data-table>
        <button v-if="!patchModerationInProgress" @click="sendOutLeads">Delete Selected Items And Send Out Leads</button>
            <div v-else id="patchModerationInProgressSpinner">
                  <circle-spin :loading='true'></circle-spin>
              </div>
            </div>
            <div v-else>
                <p>No Leads With Moderation Pending</p>
            </div>
        </div>
        </div>
        <!-- if get moderation api still loading, show loading animation -->
        <div v-else>
            <div>
                  <circle-spin :loading='true'></circle-spin>
              </div>
        </div>
        </div>
        <!-- if not jonathan or venkat, hide page -->
        <div v-else>
            <p>Not authorized to view this page.</p>
        </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'
  export default {
    name: 'ClaimLead',
    data () {
      return {
        isAuthorized: false,
        patchModerationInProgress: false,
        getModerationApiLoaded: false,
        moderation_leads: [],
        leadsToDelete: [],
        approvedLeadIds: [],
        rejectedLeadIds: [],
        headers: [
          { text: 'Name', value: 'name' },
          { text: 'Email', value: 'email' },
          { text: 'Phone Number', value: 'phone_number' },
          { text: 'Zip Code', value: 'zip_code' },
          { text: 'Searched Address', value: 'searched_address' },
          { text: 'Purpose', value: 'lead_type' },
          { text: 'Lead Price', value: 'lead_price' },
          { text: 'Average Home Price', value: 'average_home_price' },
        ],
        errorMessage: null,
      }
    },
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser'], 'userProfile'),
    },
    mounted () {
      this.$store.dispatch('auth/checkIsCognitoUserLoggedIn')
        .then(() => {
          if (this.cognitoUser.attributes.email === 'venkat@honely.com' || this.cognitoUser.attributes.email === 'jonathan@honely.com') {
            this.isAuthorized = true
            const config = {
              headers: {
                Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
              },
            }
            axios.get('https://api.honely.com/dev/lead/moderation', config)
              .then((response) => {
                this.errorMessage = null
                this.moderation_leads = response.data.data.moderation_leads
                this.getModerationApiLoaded = true
              })
              .catch((error) => {
                this.getModerationApiLoaded = true
                this.errorMessage = error
              })
          }
        })
      // if (this.isCognitoUserLoggedIn) {
      //   if (this.cognitoUser.attributes.email === 'venkat@honely.com' || this.cognitoUser.attributes.email === 'jonathan@honely.com') {
      //     this.isAuthorized = true
      //   }
      //   console.log('vx: cognitoUser email', this.cognitoUser.attributes.email)
      // }
      // axios.get('https://api.honely.com/lookup-test/leads/moderation')
      //   .then((response) => {
      //     this.errorMessage = null
      //     this.moderation_leads = [...response.data.leads.site, ...response.data.leads.button, ...response.data.leads.forecast]
      //     this.getModerationApiLoaded = true
      //   })
      //   .catch((error) => {
      //     this.getModerationApiLoaded = true
      //     this.errorMessage = error
      //   })
    },
    methods: {
      sendOutLeads () {
        this.patchModerationInProgress = true
        this.errorMessage = null
        this.rejectedLeadIds = []
        this.approvedLeadIds = []
        for (let i = 0; i < this.moderation_leads.length; i++) {
          if (this.leadsToDelete.includes(this.moderation_leads[i])) {
            this.rejectedLeadIds.push(this.moderation_leads[i].lead_id)
          } else {
            this.approvedLeadIds.push(this.moderation_leads[i].lead_id)
          }
        }
        //   console.log('vx: rejectedLeadIds', this.rejectedLeadIds)
        //   console.log('vx: approvedLeadIds', this.approvedLeadIds)
        const params = {
          approved: this.approvedLeadIds,
          rejected: this.rejectedLeadIds,
        }
        // delete selected leads and send out rest
        this.$store.dispatch('auth/checkIsCognitoUserLoggedIn')
          .then(() => {
            const config = {
              headers: {
                Authorization: 'Bearer ' + this.cognitoUser.signInUserSession.idToken.jwtToken,
              },
            }
            axios.patch('https://api.honely.com/dev/lead/moderation', params, config)
              .then(() => {
                location.reload()
              })
              .catch((error) => {
                this.errorMessage = error
              })
          })
      },
    },
  }
</script>
<style scoped>
#patchModerationInProgressSpinner {
  min-height: 40px;
}
#patchModerationInProgressSpinner >>> .sk-fading-circle {
  margin: 0px auto;
  position: absolute;
  left: 46%;
}
</style>
