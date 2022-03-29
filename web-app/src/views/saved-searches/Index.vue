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
        md="12"
      >
        <h1
          class="mb-1"
        >
          Saved Searches
        </h1>
        <!-- <p v-if="!savedSearches.length">
          You have no saved searches.
        </p> -->
        <div
        v-if="!savedSearches.length"
        style="text-align: center; padding-top: 75px; padding-bottom: 150px"
        >
          <div :style='imagePostion()'>
          <v-img
          max-height="150"
          max-width="250"
          src='https://pixabay.com/get/g4aa005369457d4442c7e3dc791e57cd5d00974fa3ecaaadce6a5115d688e9f8391d602b8fdfae23c868282188bb0b96479b60fd60c05cc969cab1a9c3634b418220f59152e023c5d495746e16b3e41c3_1280.png'
          >
          </v-img>
          </div>
          <br>
          <h2>Get Updates On Your Saved Searches</h2>
          <p>Saving your searches saves you time and we'll let you know whenever a match comes on the market.</p>
          <br>
          <div :style='searchBarPosition()'>
          <search-suggestions-app-bar />
          </div>
        </div>
        <div style="margin-bottom:2rem;margin-top:2rem;">
        <div
        v-for="savedSearch in savedSearches"
        :key="savedSearch.search"
        class="saved-search-box"
        >
        <a @click="$router.push('/listings/?address=' + savedSearch.search)">{{ savedSearch.search }}</a>
        <v-tooltip bottom>
        <template v-slot:activator="{ on, attrs }">
        <i
                  :class="'pt-1 mb-5 mdi band-header-icon mdi-delete'"
                  aria-hidden="true"
                  style="float:right;"
                  v-bind="attrs"
                  v-on="on"
                  @click="deleteSavedSearch(savedSearch.user_id, savedSearch.search)"
                />
                </template>
                <span>Delete</span>
                </v-tooltip>
        </div>
        </div>
      </v-col>
    </v-row>

    <!-- <v-row
      v-if="savedSearches.length>0"
      justify="start"
      class="border-me-not"
    >
      <v-data-table
        :headers="headers"
        :items="savedSearches"
        hide-default-footer
        disable-pagination
      >
        <template v-slot:[`item.actions`]="{ item }">
          <v-icon
            small
            class="mr-2"
            @click="$router.push('/listings/?address=' + item.search)"
          >
            mdi-arrow-top-right
          </v-icon>
        </template>
      </v-data-table>
    </v-row> -->
  </v-container>
</template>

<script>
  import { mapGetters } from 'vuex'
  import axios from 'axios'
  import { bus } from '../../main'

  export default {
    name: 'SavedSearches',

    components: {
      SearchSuggestionsAppBar: () => import('@/components/SearchSuggestionsAppBar'),
    },

    data: () => ({
      savedSearches: [],
      headers: [
        // { text: 'id', value: 'id' },
        { text: 'Query', value: 'search' },
        { sortable: false, text: 'Actions', value: 'actions' },
      ],
    }),

    computed: {
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
    },

    mounted () {
      // if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
      //   axios.get('/api/v1/saved-searches',
      //             {
      //               headers: {
      //                 Authorization: 'Bearer ' + this.currentUser,
      //               },
      //             })
      //     .then((response) => {
      //       console.log(response.data)
      //       this.savedSearches = response.data.rows
      //     })
      //     .catch(err => {
      //       console.log(err)
      //     })
      // }
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        // get current user
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.$store.getters['auth/cognitoUser'].attributes.email,
          },
        }).then((response) => {
          var user = response.data
          // console.log('vx: lalala'+ this.user)
          // this.$store.dispatch('listings/getFavorites', { user_id: user.user_id })
          axios.get('https://api.honely.com/lookup/saved_searches?user_id=' + user.user_id)
            .then((response) => {
              console.log('vx: being pushed to this.savedSearches: ', response.data)
              this.savedSearches = response.data
            })
            .catch((error) => {
              console.log('Error fetching saved searches: ', error)
            })
        }).catch((error) => {
          console.log('[ERROR] Failed to fetch user data')
          console.log(error.response.data.errorMessage)
        })
      }
    },
    methods: {
      imagePostion () {
        if (this.$vuetify.breakpoint.name === 'xs') {
          return 'padding-left: 10%'
        } else if (this.$vuetify.breakpoint.name === 'xl') {
          return 'padding-left: 43%'
        } else {
          return 'padding-left: 39%'
        }
      },
      searchBarPosition () {
        if (this.$vuetify.breakpoint.name === 'xs') {
          return 'padding-left: 0%'
        } else if (this.$vuetify.breakpoint.name === 'xl') {
          return 'padding-left: 33%'
        } else {
          return 'padding-left: 25%'
        }
      },
      deleteSavedSearch (userId, query) {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          this.removeSavedSearch(userId, query)
        } else {
          bus.$emit('showError', { message: 'Please log in or create an account to save this search' })
        }
      },
      removeSavedSearch (userId, query) {
        const params = {
          user_id: userId,
          search: query,
        }
        axios.delete('https://api.honely.com/lookup/delete_searches', { data: params })
          .then(() => {
            for (var i = 0; i < this.savedSearches.length; i++) {
              if (this.savedSearches[i].search === query) {
                this.savedSearches.splice(i, 1)
              }
            }
          })
          .catch((err) => {
            console.log('Error deleting saved search', err)
          })
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
