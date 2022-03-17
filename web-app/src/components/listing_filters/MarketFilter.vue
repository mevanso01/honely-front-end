<template>
  <v-overlay
    light
    opacity="0"
    :value="showMarketFilter"
    class="market-filter"
  >
    <v-card
      v-click-outside="hideOverlay"
      width="250"
      height="235"
      light
    >
      <v-card-text>
        <v-row
          align="center"
          justify="center"
        >
          <v-col
            cols="12"
            class="pb-1"
          >
            <h4>
              Market Attractiveness
            </h4>
            <v-row>
              <v-col
                cols="12"
                class="pt-5"
              >
                <v-btn-toggle
                  v-model="attractivenessValue"
                  mandatory
                >
                  <v-btn
                    v-for="item in attractivenessItems"
                    :key="item"
                    text
                    class="black-text"
                  >
                    {{ item }}
                  </v-btn>
                </v-btn-toggle>
              </v-col>
            </v-row>
            <h4
              class="pt-6"
            >
              Market Competitiveness:
              <span>
                list price vs. sale price
              </span>
            </h4>
            <v-row>
              <v-col
                cols="12"
                class="pt-5"
              >
                <v-btn-toggle
                  v-model="competitivenessValue"
                  mandatory
                >
                  <v-btn
                    v-for="item in competitivenessItems"
                    :key="item"
                    text
                  >
                    {{ item }}
                  </v-btn>
                </v-btn-toggle>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>

  export default {
    name: 'MarketFilter',
    props: {
      showMarketFilter: Boolean,
      // MarketValue: Number,
    },

    data: () => ({
      attractivenessItems: ['Hot', 'Medium', 'Cold'],
      attractivenessValue: 0,
      competitivenessItems: ['High', 'Medium', 'Low'],
      competitivenessValue: 0,

      item: {},
      tickLabels: [
        '1 year',
        '2 years',
        '3 years',
      ],
      growthIndex: 0,
      dollarIndex: 0,
    }),

    methods: {
      hideOverlay () {
        console.log('hideOverlay')
        this.$emit('hideMarketFilterOverlay')
      },
    },
  }
</script>

<style lang="sass">
  .market-filter
    .v-overlay__content
      width: 100%
      height: 100%

      .v-card
        position: absolute
        top: 140px
        left:132px
        border: 1px solid green

        h4
          color: black
          font-family: $heading-font-family
          font-weight: 500
          line-height: 16px

          span
            display: block
            font-size: .8rem
            color: $color-gray-mddk

        .v-input
          .v-label
            font-size: 12px

        .v-input--is-label-active
          .v-label
            /* color: black */
            font-weight: 600

        .v-btn-toggle:not(.v-btn-toggle--group)
          background: white
          color: rgba(0, 0, 0, 0.5)

        .theme--light.v-btn-toggle .v-btn.v-btn--active
          background-color: $color-emerald

          span
            color: white

        .theme--light.v-btn-toggle:not(.v-btn-toggle--group) .v-btn.v-btn
          border-color: $color-emerald

  #search-listings
    .market-filter
      .v-btn
        color: black

  @media (max-width: 960px)
    .market-filter
      .v-overlay__content
        .v-card
          left: 15px

</style>
