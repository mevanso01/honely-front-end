<template>
  <v-app-bar
    v-scroll="onScroll"
    :color="!isScrolling ? 'transparent' : 'rgba(66, 66, 66, .9)'"
    :hide-on-scroll="$vuetify.breakpoint.smAndDown"
    app
    elevate-on-scroll
  >
    <router-link to="/">
      <v-img
        :src="require('@/assets/honely-logo.svg')"
        contain
        height="64"
        width="170"
        position="left center"
      />
    </router-link>
    <v-spacer />

    <v-toolbar-items v-if="$vuetify.breakpoint.mdAndUp">
      <v-btn
        v-for="(item, i) in items"
        :key="i"
        :active-class="!isScrolling ? 'primary--text' : undefined"
        :to="item.to"
        text
      >
        <span v-text="item.text" />
      </v-btn>
    </v-toolbar-items>
  </v-app-bar>
</template>

<script>
  // Utilities
  // import { mapMutations } from 'vuex'

  export default {
    data: () => ({
      isScrolling: false,
    }),

    computed: {
      items () {
        return [
          {
            to: '/listings',
            text: 'Listings',
          },
          {
            to: '/insights',
            text: 'Insights',
          },
          {
            to: '/about-us',
            text: 'About Us',
          },
          {
            to: '/sign-in',
            text: 'Sign In',
          },
        ]
      },
    },

    methods: {
      // ...mapMutations(['toggleDrawer']),
      onScroll () {
        this.isScrolling = (window.pageYOffset ||
          document.documentElement.scrollTop || 0) > 25
      },
    },
  }
</script>
