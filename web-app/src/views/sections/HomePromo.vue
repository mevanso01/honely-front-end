<template>
  <v-theme-provider dark>
    <base-section
      id="home-promo"
      class="primary green-gradient"
      space="40"
    >
      <v-container
        class="background-shapes"
      >
        <v-row
          justify="center"
        >
          <v-col
            cols="11"
            class="align-center"
          >
            <!-- base-title
              :space="$vuetify.breakpoint.mdAndUp ? 0 : 4"
              title="Let Us Show You The Future"
              class="mb-5"
              max-width="580px"
            / -->
            <div class="promo2-container">
              <h2 class="promo-header mb-3">
                Find the right value, not the face value.
              </h2>

              <p class="promo-copy">
                Honely’s industry-leading algorithms provide you with the data and analytics to identify a property or area that’s positioned for growth.
                <br>
                <br>
                Both the value and quality of our insights go beyond what’s currently reserved for institutions and large enterprises. Never before has the public been able to access investor-grade home value forecasts, relocation trends, zip code rankings, and much more.
              </p>
              <base-btn
                :block="$vuetify.breakpoint.smAndDown"
                :to="{ name: 'Smart Search' }"
                color="white"
                class="txtonly-btn"
                large
                plain
                outlined
                target="_self"
              >
                Search an address or zip code
                <i
                  class="mdi mdi-play"
                  aria-hidden="true"
                />
              </base-btn>
            </div>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'SectionContactUs',
  }
</script>

<style lang="sass">
#home-promo
  border-top: 3px solid white !important

  .green-gradient
    background: linear-gradient(to right, #018f45 , #3bc950)

  .background-shapes
    background-color: transparent

  .promo-copy
    color: black
    font-size: 20px
    line-height: 27px
    text-align:  center
    margin: 0

  .promo-header
    font-family: 'Avant Garde Gothic Md', 'lato', sans-serif
    font-weight: normal
    font-size: 30px
    text-align: center

  .promo2-container
    text-align: center

  .txtonly-btn
    border: none

  .txtonly-btn .v-btn__content
    opacity: 1 !important

  .txtonly-btn:hover .v-btn__content
    color: $color-yellow

  .mdi-play
    color: $color-yellow

  .nowidow
    white-space: nowrap
</style>
