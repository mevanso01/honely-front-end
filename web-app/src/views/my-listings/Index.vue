<template>
  <v-container>
    <v-row
      justify="start"
      class="border-me-not"
    >
      <v-col
        class="intro-text"
        cols="12"
        sm="10"
        md="8"
      >
        <h1
          class="mb-1"
        >
          My Listings
        </h1>
        <p v-if="!myListings.length">
          You have no listings.
        </p>
      </v-col>
    </v-row>

    <v-row
      justify="start"
      class="border-me-not"
    >
      <v-col
        v-for="item in myListings"
        :key="item.id"
        class="intro-text"
        cols="12"
        sm="8"
        md="4"
      >
        <property-listings-card
          :item="item"
          :show-edit="true"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import axios from 'axios'
  import { mapState } from 'vuex'

  export default {
    name: 'MyListings',

    components: {
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
    },

    computed: {
      ...mapState('auth', ['currentUser']),
    },

    data () {
      return {
        myListings: [],
      }
    },

    mounted () {
      if (this.$store.getters['auth/loggedIn']) {
        this.load()
      }
    },

    methods: {
      load () {
        axios
          .get('/api/v1/my-sponsored-listings', {
            headers: {
              Authorization: 'Bearer ' + this.currentUser,
            },
            params: {
              fullData: true,
            },
          })
          .then((response) => {
            const data = response.data

            this.myListings = data.rows
          })
      },
    },
  }
</script>
