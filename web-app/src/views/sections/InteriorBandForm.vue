<template>
  <v-theme-provider light>
    <base-section
      id="interior-band-form"
      class="mt-0 pt-6 pb-15"
    >
      <v-container>
        <v-row
          justify="start"
        >
          <v-col
            sm="6"
            class="align-center text-left"
          >
            <h2
              class="mb-5 mt-3"
            >
              Leverage our technology, data, or leads for your business.
            </h2>

            <p>
              We offer out-of-the-box and custom data and analytics solutions for businesses.
            </p>
            <p>
              Real estate investors, financial institutions, and brands look to Honely to power critical real estate and business decisions with our state-of-the-art forecasting and valuation technology.
            </p>
            <p>
              Contact us today to schedule a meeting with our team to discuss leveraging our real estate forecast and valuation APIs, as well as custom data solutions we can build for your organization, and much more.
            </p>
          </v-col>
          <v-col
            sm="6"
            class="align-center text-left mt-5"
          >
            <base-contact-form
              post-url="https://api.honely.com/lookup/contact_us"
              title=""
            />
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>

</script>

<style lang="sass">
  #interior-band-form
    background-color: $color-gray-vlt

    h2
      line-height: 30px

    .v-input__slot
      background-color: $color-gray-vlt2

    .v-input.v-input--is-focused
      .v-input__slot
        background-color: white

</style>
