<template>
  <v-theme-provider light>
    <base-section
      id="interior-band-video"
      class="white-band"
      space="40"
    >
      <v-container
        class="container-about"
      >
        <v-row
          class="border-me-not"
        >
          <v-col
            class="intro-text"
            cols="12"
            sm="12"
            md="8"
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
            class="col mx-0 px-0"
            cols="12"
            sm="12"
            md="4"
          >
            <div class="video-container">
              <iframe
                width="495"
                height="280"
                src="https://www.youtube.com/embed/tDcUUcSmgIo?autoplay=0"
                title="YouTube video player"
                frameborder="0"
                allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
                autoplay="1"
                allowfullscreen
              />
            </div>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'InteriorBandVideo',

    data: () => ({
      title: '',
      items: [
        {
          id: 1,
          name: 'About Us',
          img: 'temp-about-img1.jpg',
          headline: 'We Saw A Need For Deeper Knowledge.',
          copy: 'Our team has leveraged extensive experience in data science and predictive analytics to deliver forecasts for similarly unpredictable asset classes such as the stock market.<br /><br />We noticed the real estate space was far behind other industries in terms of power, accuracy, and creativity of data solutions. Therefore, we put some of the best analytical minds together to develop data products that deliver true transparency to the real estate marketplace.',
          boxes: [
            {
              id: 1,
              headline: 'Helping you invest wisely',
              copy: 'Other digital resources are designed simply for home browsing. We’re here to bring transparency and aid in decision-making.<br /><br />We don’t just focus on a property’s current value. Honely aims to give you a comprehensive view, providing insights on the future value, the zip code, the moving trends, and more. Our forecasting models are the most accurate on the market, which is crucial given that a few percentage points can translate to large sums of money when it comes to real estate.',
              iconClass: 'mdi-home-lightbulb',
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
          img: 'temp-forecast-img1.jpg',
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
#interior-band-video
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
      font-size: 17px
      line-height: 19px
      color: $color-gray-shadowlt

    .header-icon
      color: $color-gray-lt
      font-size: 60px
      position: absolute
      right: 0
      bottom: 25px

.v-application
  #interior-band-video
    .box-headline.text-h5.v-list-item__title
      font-family: $body-font-family !important
      font-size: 19px !important
      font-weight: 900
      line-height: 21px
      white-space: normal
      border-bottom: 4px solid $color-orange
      padding-bottom: 6px
      padding-right: 60px
      position: relative

@media only screen and (max-width: 960px)
  iframe
    display: block
    margin: 0 auto

@media only screen and (max-width: 600px)
  #interior-band-video
    h1
      padding-right: 0
    p
      padding-right: 0

</style>

<style>
.container-about .copy-box {
  max-width: 500px;
}
.video-container {
  position: relative;
  overflow: hidden;
  width: 100%;
}
.video-container::after {
  display: block;
  content: "";
  padding-top: 56.25%;
}
.video-container iframe {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
}
@media only screen and (max-width: 1250px) {
  .container-about .row {
    justify-content: center;
  }
  .container-about .col-md-8 {
    flex: 0 0 100%;
    max-width: 100%;
  }
  .container-about .col-md-4 {
    flex: 0 0 70%;
    max-width: 70%;
  }
}
@media only screen and (max-width: 700px) {
  #interior-band-video h1 {
    font-size: 28px;
    padding-right: 0;
  }
  #interior-band-video p {
    padding-right: 0;
  }
  .container-about .col-md-4 {
    flex: 0 0 95%;
    max-width: 95%;
  }
  .container-about .col-sm-6 {
    flex: 0 0 100%;
    max-width: 100%;
  }
  .container-about .copy-box {
    max-width: initial;
  }
}
</style>
