<template>
  <!-- eslint-disable -->
  <v-dialog v-model="dialog" persistent max-width="500px">
    <!-- Pop up -->
    <template #default="dialog">
      <div class="popup-card" >        
        <div class="popup-header-wrapper">
          <div class="popup-title-wrapper">
            <p class="popup-heading">Confirmation</p>
          </div>
          <div class="btn-close" @click="toggleDialog">
            <i class="fa fa-times"/>
          </div>
        </div>
        <div class="popup-message">
        <p v-if="lead_id">Remove this lead?</p>
        <p v-if="confirmation_id">Claim this lead?</p>
        </div>
        <div class="popup-btn-row">
        <button class="bg-primary" @click="completeAction">Yes</button>
        <button @click="toggleDialog">Cancel</button>
        </div>
      </div>
    </template>    
  </v-dialog>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import axios from 'axios'
  import { mapGetters } from 'vuex'
  export default {
    name: 'LeadsDashboardActionConfirmation',
    props: {
      lead_id: String,
      confirmation_id: String,
    },
    mounted () {
      
    },
    data: () => ({
      dialog: true,
    }),
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser', 'leadDetails', 'leadZipCodeList']),
    },
    methods: {
      completeAction () {
          console.log('vx: this.lead_id', this.lead_id)
          console.log('vx: this.confirmation_id', this.confirmation_id)
        if (this.lead_id) {
          axios.patch('https://api.honely.com/lookup-test/lead/cancel/' + this.lead_id)
          .then(() => {
            location.reload()
          })
        }
        if (this.confirmation_id) {
          axios.patch('https://api.honely.com/lookup-test/lead/confirm/' + this.confirmation_id)
          .then(() => {
            location.reload()
          })
        }
      },
      toggleDialog () {
        this.$emit('restorePropDefaults')
        this.dialog = !this.dialog
      },
    },
  }
</script>
