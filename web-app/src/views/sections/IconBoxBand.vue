<template>
  <v-theme-provider light>
    <base-section
      id="icon-box-band"
      class="mt-0 py-6"
    >
      <v-container>
        <v-row
          justify="center"
        >
          <v-col
            v-for="item in items"
            :key="item.id"
            cols="12"
            sm="2"
            md="2"
            class="align-center text-center px-8"
          >
            <v-list-item>
              <v-list-item-content>
                <v-btn
                  elevation="0"
                  outlined
                  class="py-8 mb-4"
                  @click="scrollTo('forecast-band-' + item.id)"
                  v-text="item.id"
                />
                <!-- :href=" '#forecast-band-' + item.id " -->
                <v-list-item-title
                  class="btn-headline mb-1"
                  v-html="item.headline"
                />
              </v-list-item-content>
            </v-list-item>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'SectionContactUs',

    data: () => ({
      items: [
        { id: 1, headline: 'Property<br />Value', iconclass: 'mdi-chart-line' },
        { id: 2, headline: 'Neighborhood<br />at a Glance', iconclass: 'mdi-home-currency-usd' },
        { id: 3, headline: 'Moving<br />Trends', iconclass: 'mdi-database-marker' },
        { id: 4, headline: 'Mortgage<br />Calculator', iconclass: 'mdi-map-search-outline' },
      ],
      unused: [
        { id: 4, headline: 'Rental<br />Trends', iconclass: 'mdi-map-search-outline' },
      ],
    }),
    methods: {
      scrollTo (theID) {
        var element = document.getElementById(theID)
        var headerOffset = 60
        var elementPosition = element.getBoundingClientRect().top
        var offsetPosition = elementPosition - headerOffset

        window.scrollTo({
          top: offsetPosition,
          behavior: 'smooth',
        })
      },
    },

  }
</script>

<style lang="sass">
  #icon-box-band

    .v-btn
      min-width: 30px
      border-radius: 2px
      font-size: 27px

    .btn-headline
      font-family: $heading-font-family
      font-size: 13px
      line-height: 15px
      font-weight: 400

</style>
