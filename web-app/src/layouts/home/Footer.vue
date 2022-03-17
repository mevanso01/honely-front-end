<template>
  <v-footer
    id="home-footer"
    color="#525252"
    dark
    min-height="72"
  >
    <v-container>
      <v-row>
        <v-col
          cols="12"
          md="4"
        >
          <base-info />
        </v-col>

        <v-col
          cols="12"
          md="4"
          class="mt-11"
        >
          <base-info-card
            title="Latest Insights"
            class="footerhdr"
          />
          <insights-features />
        </v-col>

        <v-col
          cols="12"
          md="4"
          class="mt-11"
        >
          <info-features />
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script>
  export default {
    name: 'HomeFooter',
    components: {
      InsightsFeatures: () => import('@/components/InsightsFeatures'),
      InfoFeatures: () => import('@/components/InfoFeatures'),
    },
  }
</script>

<style lang="sass">
  #home-footer a
    text-decoration: none

  .footerhdr h3.text-h6
    text-transform: none !important
    color: $color-gray-lt !important
    font-weight: normal !important
    font-size: 24px !important
</style>
