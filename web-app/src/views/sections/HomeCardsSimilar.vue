<template>
  <v-theme-provider dark>
    <base-section
      id="home-cards"
      space="40"
    >
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
              Homes in your area
            </h1>
            <p>
              You can search for homes that meet your requirements but will also grow in value. Filter by location and specification but also by forecasted growth rates.
            </p>
          </v-col>
        </v-row>

        <v-row
          justify="start"
          class="border-me-not"
        >
          <v-col
            v-for="item in listings"
            :key="item.id"
            class="intro-text"
            cols="12"
            sm="8"
            md="4"
          >
            <property-listings-card :item="item" />
          </v-col>
        </v-row>
        <v-row
          justify="center"
          class="border-me-not centeritems"
          align-content="center"
        >
          <v-col
            cols="12"
            align-self="center"
          >
            <base-btn
              :block="$vuetify.breakpoint.smAndDown"
              color="white"
              href="javascript:alert('Load more homes');"
              large
              outlined
              height="40"
              width="215"
              class="btn-lightgray mt-3"
              elevation="3"
            >
              Load More
              <v-icon
                class="icon-left-space mdi-rotate-90  mdi-20px icon-green"
              >
                {{ 'mdi-play' }}
              </v-icon>
            </base-btn>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  import { mapState } from 'vuex'

  export default {
    name: 'SectionContactUs',
    components: {
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
    },
    computed: {
      ...mapState('listings', {
        listings: 'all',
      }),
    },
  }
</script>
