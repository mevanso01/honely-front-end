<template>
  <v-theme-provider light>
    <base-section
      id="interior-band-photo"
      class="white-band"
      space="40"
      style="background-color: white"
    >
      <v-container>
        <v-row
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
              {{ item.headline }}
            </h1>
            <p>
              <span
                v-html="item.copy"
              />
            </p>
            <v-row
              class="mt-7"
            >
              <v-col
                v-for="box in item.boxes"
                :key="box.id"
                cols="12"
                sm="6"
                md="6"
              >
                <v-card
                  class="ml-0 copy-box"
                  color="transparent"
                  max-width="360"
                  outlined
                  flat
                  light
                >
                  <v-list-item three-line>
                    <v-list-item-content>
                      <v-list-item-title
                        class="text-h5 mb-1 box-headline"
                      >
                        {{ box.headline }}
                        <i
                          :class="'mdi header-icon ' + box.iconClass"
                          aria-hidden="true"
                        />
                      </v-list-item-title>
                      <v-list-item-subtitle
                        class="mt-1"
                        v-html="box.copy"
                      />
                    </v-list-item-content>
                  </v-list-item>
                </v-card>
              </v-col>
            </v-row>
          </v-col>
          <v-col
            class="col mx-0 px-0 mt-15 col-image"
            cols="12"
            md="3"
          >
            <v-img
              :src="require('@/assets/' + item.img )"
            />
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'InteriorBandPhoto',
    data: () => ({
      title: '',
      items: [
        {
          id: 1,
          name: 'About Us',
          img: 'temp-about-img1.jpg',
          headline: 'We saw a need for deeper knowledge.',
          copy: 'Our one-of-a-kind, forward-looking algorithm has been years in the making, leveraging data science and machine learning capabilities to be more accurate than the competition.<br /><br />Our team has leveraged extensive experience in data science and predictive analytics to deliver forecasts for similarly unpredictable asset classes such as the stock market.',
          boxes: [
            {
              id: 1,
              headline: 'Helping you invest wisely',
              copy: 'We prioritize the future value of your property to give you the best insights, leaving others only focused on current value in the past.',
              iconClass: 'mdi-home-currency-usd',
            },
            {
              id: 2,
              headline: 'Revolutionizing home buying',
              copy: 'We are revolutionizing the home buying industry by giving average investors the tools they need to make informed decisions.<br /><br />Buyers and sellers need to know what the future will bring. For too long, stakeholders have been left in the dark over what results their purchase will yield in the long-term. Honely has changed that for good.',
              iconClass: 'mdi-home-group',
            },
          ],
        },
        {
          id: 2,
          name: 'Smart Search',
          img: 'dreamstime_s_156510213.jpg',
          headline: 'Find the true value, not the face value.',
          copy: 'Our data tools incorporate a multitude of factors to determine the value such as population growth, economic impact, location and gentrification. Start by performing a search.',
          boxes: [
            {
              id: 1,
              headline: 'Looking to buy? Find out which homes and neighborhoods are a good long-term investment.',
              copy: 'Buying a house is almost certainly the largest purchase of your lifetime. Leverage our accurate forecasting tools and neighborhood trends to give you the confidence you need to make a great investment.',
              iconClass: 'mdi-home-currency-usd',
            },
            {
              id: 2,
              headline: 'Thinking of selling? Find out if now is the right time to list.',
              copy: 'Discover the best time to sell your home. We’ll give you the current value of your home and show forecasts so you can decide whether it’s time to move on. We even let you self-list directly to our website.',
              iconClass: 'mdi-home-analytics',
            },
          ],
        },
        {
          id: 3,
          name: 'Smart Search For Property',
          img: 'dreamstime_s_156510213.jpg',
          headline: 'Find the true value, not the face value.',
          copy: 'Our data tools incorporate a multitude of factors to determine the value such as population growth, economic impact, location and gentrification. Start by performing a search.',
          boxes: [
            {
              id: 1,
              headline: 'Enim  ad minim veniam, quis nostrud exerci laboris nisi ut aliquip elaho.',
              copy: 'Maecenas purus purus, adipiscing ac lacus non, aliquet accumsan velit. Suspendisse adipiscing ante sit amet condimentum molestie. Vest bulum ante ipsum primis in faucibus orci  luctus et ultrices posuere cubilia.',
              iconClass: 'mdi-home-currency-usd',
            },
            {
              id: 2,
              headline: 'Minim veniam, quis nostrud exerci laboris nisi ut aliquip ex ea com modo.',
              copy: 'Lorem ipsom, adipiscing ac lacus. Suspendisse adipiscing ante sit amet condimentum molestie. Vest bulum ante ipsum primis in faucibus orci  luctus et ultrices posuere cubilia. Non, aliquet accumsan velit.',
              iconClass: 'mdi-home-analytics',
            },
          ],
        },
      ],
    }),

    computed: {
      item: function () {
        return this.items.find(item => item.name === this.$route.name)
      },
    },
  }
</script>

<style lang="sass">
#interior-band-photo
  .promo-header
    font-family: 'Avant Garde Gothic Md', 'lato', sans-serif
    font-weight: normal
    font-size: 30px
    text-align: center

  .intro-text
    font-family: $heading-font-family

  h1
    font-weight: normal
    font-size: 33px
    padding-right: 70px

  p
    font-size: 18px
    line-height: 26px
    padding-right: 70px

  .v-list-item
    padding: 0

    .box-headline
      overflow: visible

    .v-list-item__content
      overflow: visible

    .v-list-item__subtitle
      white-space: normal
      display: block
      font-size: 14px
      line-height: 19px
      color: $color-gray-shadowlt

    .header-icon
      color: $color-gray-lt
      font-size: 60px
      position: absolute
      right: 0
      bottom: 25px

.v-application
  #interior-band-photo
    .box-headline.text-h5.v-list-item__title
      font-family: $body-font-family !important
      font-size: 17px !important
      font-weight: 900
      line-height: 21px
      white-space: normal
      border-bottom: 4px solid $color-orange
      padding-bottom: 6px
      padding-right: 60px
      position: relative

@media (max-width: 960px)
  #interior-band-photo
    h1, p
      padding-right: 0
    .col-image
      padding: 0 50px 50px 50px !important

@media (min-width: 1440px)
  #interior-band-photo
    h1
      font-size: 50px
    p
      font-size: 24px
    .v-application
  #interior-band-photo
    .box-headline.text-h5.v-list-item__title
      font-size: 22px !important

  #interior-band-photo .v-list-item .v-list-item__subtitle
    font-size: 18px

</style>
