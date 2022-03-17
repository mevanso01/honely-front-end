<template>
  <v-overlay
    light
    opacity="0"
    :value="showExampleFilter"
    class="example-filter"
  >
    <v-card
      v-click-outside="hideOverlay"
      width="400"
      height="200"
      light
    >
      <v-card-text>
        <v-row
          align="center"
          justify="center"
        >
          <v-col cols="12">
            <p class="text-center">
              Example
            </p>
          </v-col>
          <v-btn-toggle
            v-model="exampleValue"
            mandatory
          >
            <v-btn
              v-for="item in exampleItems"
              :key="item"
              text
            >
              {{ item }}
            </v-btn>
          </v-btn-toggle>
          <v-col
            cols="12"
            class="text-center"
          >
            Index: {{ exampleValue }}
          </v-col>
        </v-row>
      </v-card-text>
    </v-card>
  </v-overlay>
</template>

<script>

  export default {
    name: 'ExampleFilter',
    props: {
      showExampleFilter: Boolean,
      // exampleValue: Number,
    },

    data: () => ({
      exampleItems: [1, 2, 3, 4, 5, 6],
      exampleValue: 0,
    }),

    methods: {
      hideOverlay () {
        console.log('hideOverlay')
        this.$emit('hideExampleFilterOverlay')
      },
    },
  }
</script>

<style lang="sass">
  .example-filter
    .v-overlay__content
      width: 100%
      height: 100%

      .v-card
        position: absolute
        top: 140px
        left: 250px

@media (max-width: 960px)
    .example-filter
      .v-overlay__content
        .v-card
          left: 15px
</style>
