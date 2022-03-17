<template>
  <div class="section section-wrapper">
    <h1 class="page-title">
      Sales Dashboard
    </h1>
    <div class="align-center">
      <v-tabs
        show-arrows
      >
        <v-tab>
          Search by zipcode
        </v-tab>
        <v-tab>
          Search by service provider
        </v-tab>
        <v-tab-item>
          <div>
            <v-text-field
              v-model="zipCode"
              outlined
              shaped
              label="Enter Zip Code"
              width="80%"
              prepend-inner-icon="mdi-home"
              style="width: 80%;margin-top: 20px;margin-left: 10%"
              @v-on:keyup.enter="fetchZipCodeSubscriptionDetails()"
            />
            <button
              color="primary"
              style="margin-bottom: 20px;"
              @click="fetchZipCodeSubscriptionDetails()"
            >
              Submit
            </button>
            <p>{{ errorMessage }}</p>
            <div v-if="subbers">
              <div>
                <h2>REALTORS</h2>
                <v-data-table
                  :headers="headers"
                  :items="subbers.Agent"
                  item-key="agent_id"
                  :items-per-page="5"
                  class="elevation-1"
                />
              </div>
              <br>
              <div>
                <h2>LENDERS</h2>
                <v-data-table
                  :headers="headers"
                  :items="subbers.Lender"
                  item-key="agent_id"
                  :items-per-page="5"
                  class="elevation-1"
                />
              </div>
              <br>
              <div>
                <h2>GENERAL CONTRACTORS</h2>
                <v-data-table
                  :headers="headers"
                  :items="subbers.Gc"
                  item-key="agent_id"
                  :items-per-page="5"
                  class="elevation-1"
                />
              </div>
            </div>
            <!-- <div>
            </div> -->
          </div>
        </v-tab-item>
        <v-tab-item>
          <div>
            <v-text-field
              v-model="sPEmail"
              outlined
              shaped
              label="Service Provider's Email ID"
              width="80%"
              prepend-inner-icon="mdi-email"
              style="width: 80%;margin-top: 20px;margin-left: 10%"
              @v-on:keyup.enter="fetchSPSubscriptionDetails()"
            />
            <button
              color="primary"
              style="margin-bottom: 20px;"
              @click="fetchSPSubscriptionDetails()"
            >
              Submit
            </button>
            <p>{{ errorMessage2 }}</p>
            <div v-if="sPProfile">
              <div style="border: 1px solid green; margin: 30px; padding: 30px; border-radius:10px;">
                <h1>Service Provider Summary:</h1>
                <br>
                <p><b>Name:</b> {{ sPProfile.first_name }} {{ sPProfile.last_name }}</p>
                <p><b>Email:</b> {{ sPProfile.agent_email }}</p>
                <p><b>Phone:</b> {{ sPProfile.phone_number }}</p>
                <p><b>Service Provider Type:</b> {{ sPProfile.user_type.replace(/,(?=[^\s])/g, ", ") }}</p>
              </div>
              <div style="border: 1px solid green; margin: 30px; padding: 30px; border-radius:10px;">
                <h1>Service Provider's Subscription Summary:</h1><br>
                <!-- sptype check -->
                <div v-if="sPProfile.user_type.toUpperCase().includes('AGENT/BROKER')">
                  <p><b>Zipcodes subscribed to as Agent</b></p>
                  <!-- zipcodecount check -->
                  <div v-if="sPProfile.subscribed_zip_codes_new.agent[0] === ''">
                    <p>None!</p>
                  </div>
                  <div v-else>
                    <p
                      v-for="item in sPProfile.subscribed_zip_codes_new.agent"
                      :key="item"
                    >
                      {{ item }}
                    </p>
                  </div>
                </div>
                <div v-if="sPProfile.user_type.toUpperCase().includes('LENDER')">
                  <p><b>Zipcodes subscribed to as Lender</b></p>
                  <div v-if="sPProfile.subscribed_zip_codes_new.lender[0] === ''">
                    <p>None!</p>
                  </div>
                  <div v-else>
                    <p
                      v-for="item in sPProfile.subscribed_zip_codes_new.lender"
                      :key="item"
                    >
                      {{ item }}
                    </p>
                  </div>
                </div>
                <div v-if="sPProfile.user_type.toUpperCase().includes('GENERAL CONTRACTOR')">
                  <p><b>Zipcodes subscribed to as Contractor</b></p>
                  <div v-if="sPProfile.subscribed_zip_codes_new.gc[0] === ''">
                    <p>None!</p>
                  </div>
                  <div v-else>
                    <p
                      v-for="item in sPProfile.subscribed_zip_codes_new.gc"
                      :key="item"
                    >
                      {{ item }}
                    </p>
                  </div>
                </div>
              </div>
              <v-btn
                color="red"
                style="margin-bottom: 20px;"
                @click="resetSPProfile()"
              >
                Clear
              </v-btn>
            </div>
          </div>
        </v-tab-item>
      </v-tabs>
    </div>
  </div>
</template>

<script>
  import { mapGetters } from 'vuex'
  import axios from 'axios'
  export default {
    name: 'SavedSearches',
    data: () => ({
      sPZipCode: null,
      errorMessage2: '',
      errorMessage: '',
      sPEmail: null,
      sPProfile: null,
      zipCode: null,
      subbers: null,
      headers: [
        { text: 'First name', value: 'first_name' },
        { text: 'Last Name', value: 'last_name' },
        { text: 'Email', value: 'agent_email' },
        { text: 'Phone Number', value: 'phone_number' },
      ],
    }),
    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
    },
    methods: {
      fetchZipCodeSubscriptionDetails () {
        this.errorMessage = ''
        this.subbers = null
        axios.get('https://api.honely.com/searches/agents?zip_code=' + this.zipCode)
          .then((response) => {
            if (response.data.result === 'Success') {
              this.subbers = response.data
            } else {
              this.errorMessage = 'Error. Please check the zipcode you have entered.'
            }
          })
          .catch(() => {
            this.errorMessage = 'Error. Please check the zipcode you have entered.'
          })
      },
      fetchSPSubscriptionDetails () {
        this.errorMessage2 = ''
        this.sPProfile = null
        axios.get('https://api.honely.com/lookup-test/agent_profile?agent_email=' + this.sPEmail)
          .then((response) => {
            this.sPProfile = response.data
          })
          .catch(() => {
            this.errorMessage2 = 'Error. Please check the email ID you have entered.'
          })
      },
      resetSPProfile () {
        this.sPProfile = null
      },
    },
  }
</script>
<style scoped>
.saved-search-box {
  margin-top: 1rem;
  border-radius: 5px;
  padding: 1rem;
  max-width: 90%;
  border: 1px solid #aaaaaa;
}
</style>
