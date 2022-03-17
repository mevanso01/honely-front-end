<template>
  <v-theme-provider light>
    <base-section
      id="home-promo2"
      class="white-digital"
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
            sm="12"
            md="9"
          >
            <h1
              class="mb-3"
            >
              {{ headline }}
            </h1>
            <p>
              {{ copy }}
            </p>
          </v-col>
          <v-col
            class="hidden-sm-and-down col"
            md="3"
          />
        </v-row>

        <v-row justify="start">
          <v-col
            v-for="item in items"
            :key="item.id"
            cols="12"
            sm="3"
            md="3"
          >
            <v-card
              class="mx-auto"
              color="transparent"
              :max-width="$vuetify.breakpoint.xs ? 'auto' : '360px'"
              outlined
              flat
              light
            >
              <v-list-item three-line>
                <v-list-item-content>
                  <v-list-item-title :class="'headline mb-1 ' + item.hdclass">
                    {{ item.headline }}
                  </v-list-item-title>
                  <v-list-item-subtitle
                    class="mt-1"
                  >
                    {{ item.copy }}
                  </v-list-item-subtitle>
                </v-list-item-content>

                <i
                  :class="'mdi header-icon ' + item.iconclass"
                  aria-hidden="true"
                />
              </v-list-item>

              <!--  v-card-actions>
                <v-btn
                  outlined
                  text
                  absolute
                  right
                >
                  Learn more
                  <i
                    :class="'mdi mdi-play ' + item.btnclass"
                    aria-hidden="true"
                  />
                </v-btn>
              </!-->
            </v-card>
          </v-col>

          <v-col
            md="3"
          >
          <!-- <img :src="require('@/assets/background_honely.png')"> -->
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
        { id: 1, headline: 'Thinking of selling? Find out your home’s true worth.', copy: 'Discover the best time to sell your home. We’ll give you the most accurate current and future value estimates on the market, in addition to other valuable tools such as the Honely Competitive Score. Listing your home with Honely is a great way to reach motivated buyers and real estate professionals.', hdclass: '', btnclass: 'color-orange', iconclass: 'mdi-home-lightbulb' },
        { id: 2, headline: 'Looking to buy? Find out which home’s value will grow.', copy: 'Buying a house is almost certainly the largest purchase of your lifetime. Use the power of data and our accurate forecasting tools to give you the confidence to make a great investment.', hdclass: '', btnclass: 'color-orange', iconclass: 'mdi-home-search' },
        { id: 3, headline: 'We can connect you with motivated buyers and sellers.', copy: 'If you’re a real estate professional, we help you reach motivated buyers and sellers. Unlike other sites, we also help you identify real estate investors. Learn how you can advertise with us.', hdclass: 'squash', btnclass: 'color-squash', iconclass: 'mdi-home-account' },
      ],
      headline: 'Buyers, Sellers, & Real Estate Professionals',
      copy: 'Whether you’re buying or selling a home, or helping others in the process, Honely has the answers. Use our cutting-edge tools to ensure you’re making the best decisions and investing like a pro.',
    }),
  }
</script>

<style lang="sass">
#home-promo2
  background-color: #eeeeee
  background-image: url('~@/assets/background_honely.png')
  background-size: 450px
  background-position: center right
  background-repeat: no-repeat

.white-digital
  background-size: cover
  background-color: #eeeeee
  color: $color-gray-shadowlt

  .promo-copy
    color: black
    font-size: 20px
    line-height: 27px
    text-align:  center
    margin: 0 50px

  .promo-header
    font-family: 'Avant Garde Gothic Md', 'lato', sans-serif
    font-weight: normal
    font-size: 30px
    text-align: center

  .intro-text
    font-family: $heading-font-family

  .intro-text h1
    font-weight: normal
    font-size: 33px

  .intro-text h1, .intro-text p
    padding-right: 0px

  .intro-text p
    font-size: 18px
    line-height: 26px

  .v-list-item
    padding: 0

  .v-list-item .headline
    font-family: $body-font-family !important
    font-size: 17px !important
    font-weight: 900 !important
    line-height: 19px
    white-space: normal
    border-bottom: 4px solid $color-orange
    padding-bottom: 6px
    padding-right: 60px

  .v-list-item .v-list-item__subtitle
    white-space: normal !important
    display: block
    font-size: 16px
    line-height: 19px
    color: $color-gray-shadowlt !important

  .header-icon
    color: #e67e22
    font-size: 60px
    position: absolute
    right: 0
    top: -20px

  .v-btn i.color-orange
    color: $color-orange

  .v-btn i.color-squash
    color: $color-squash

  .v-btn
    letter-spacing: .01em
    font-weight: 700
    border: none
    border-radius: 6px

</style>

<style>
/* MOBILE */
@media only screen and (max-width: 900px) {
  .col-sm-4 {
    width: 47%;
    flex: 0 0 47%;
    max-width: 47%;
  }
}
@media only screen and (max-width: 600px) {
  .col-sm-4 {
    width: 100%;
    flex: 0 0 100%;
    max-width: 100%;
  }
  #home-promo2 .white-digital h1 {
    padding-right: 0;
    border: 1px solid red;
  }
}
@media (max-width: 500px) {
  .white-digital .intro-text h1 {
    font-size: 25px;
  }
}
</style>
