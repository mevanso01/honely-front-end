<template>
  <!-- eslint-disable -->
  <div class="pg-fav-listings">
    <div class="section-wrapper">
      <div class="section-heading">Favorite Listings</div>
      <div v-if="favoriteListings && favoriteListings.length > 0" class="listings-fav">
        <property-block
          v-for="(property, i) in favoriteListings"
          :property-data="property"
          :comparable="false"
          :compact="false"
          :key="i"
        />
      </div>
      <div v-else class="home-local-property-msg">
        No favorite listings available. Whenever you find homes you like, click the <span class="mdi mdi-heart-outline"></span> to save them here.
      </div>
    </div>
  </div>
  <!-- /eslint-enable -->
</template>

<script>
  /* eslint-disable */
  import { mapGetters } from 'vuex'

  export default {
    name: 'FavoriteListings',
    components: {
      SectionLoader: () => import('@/components/SectionLoader'),
      PropertyBlock: () => import('@/components/PropertyBlock'),
    },
    data: () => ({
      loading: false,
    }),
    computed: {
      ...mapGetters('listings', ['favoriteListings']),
      ...mapGetters('auth', ['loggedIn', 'username', 'vxAuth', 'vxAuthDependent', 'isCognitoUserLoggedIn', 'cognitoUser']),
    },

    mounted () {
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        // get current user
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.$store.getters['auth/cognitoUser'].attributes.email,
          },
        }).then((response) => {
          var user = response.data
          this.$store.dispatch('listings/getFavorites', { user_id: user.user_id })
        }).catch((error) => {
          console.log('[ERROR] Failed to fetch user data')
          console.log(error)
        })
      }
    },
    methods: {
    }
  }
</script>
