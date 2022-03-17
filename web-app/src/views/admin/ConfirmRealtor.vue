<template>
  <v-container
    fluid
  >
    <v-row
      justify="center"
    >
      <v-col
        cols="4"
      >
        <v-card>
          <v-card-title>Realtor Confirmation</v-card-title>
          <v-card-text
            v-if="confirmed"
          >
            This realtor has been confirmed
          </v-card-text>
          <v-card-text
            v-if="!confirmed"
          >
            <p><label>Name:</label> {{ realtor.first_name }} {{ realtor.last_name }}</p>
            <p><label>Email: </label> {{ realtor.email }}</p>
            <p><label>Relator ID: </label> {{ realtor.realtor_id }}</p>
          </v-card-text>

          <v-card-actions
            v-if="!confirmed"
          >
            <v-btn
              color="primary"
              @click="authorizeRealtor"
            >
              Authorize Realtor
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import axios from 'axios'
  import { mapState } from 'vuex'

  export default {
    name: 'ConfirmRealtor',

    data: () => {
      return {
        realtor: {},
      }
    },

    computed: {
      ...mapState('auth', ['currentUser']),
      confirmed () {
        return this.realtor.realtor_status === 'confirmed'
      },
    },

    mounted () {
      if (this.$store.getters['auth/loggedIn']) {
        if (this.$store.getters['auth/permissions'].indexOf('can-authorize-realtor') !== -1) {
          this.load()
        } else {
          this.$router.push({ name: 'FourOhFour' })
        }
      }
    },

    methods: {
      load () {
        axios.get('/api/v1/get-realtor',
                  {
                    headers: {
                      Authorization: 'Bearer ' + this.currentUser,
                    },
                    params: {
                      uuid: this.$route.params.uuid,
                    },
                  })
          .then((response) => {
            this.realtor = response.data
          })
      },

      authorizeRealtor () {
        axios.put('/api/v1/authorize-realtor',
                  {
                    id: this.realtor.id,
                  },
                  {
                    headers: {
                      Authorization: 'Bearer ' + this.currentUser,
                    },
                  })
          .then(() => {
            this.realtor.realtor_status = 'confirmed'
          })
      },
    },
  }
</script>
