<template>
  <v-theme-provider :dark="dark">
    <div
      class="mt-5"
    >
      <base-img
        :src="'https://www.honely.com/img/logo-gray.91fd000e.svg'"
        contain
        height="78"
        class="footer-logo"
      />

      <v-card
        class="mx-auto ml-4 mt-5 mb-15 footernav"
        max-width="400"
        elevation="0"
        tile
      >
        <v-list-item
          v-for="(name, i) in items"
          :key="i"
          class="navitem"
        >
          <v-list-item-content
            class="navitemcontent"
          >
            <router-link :to="{ name }">
              {{ name }}
            </router-link>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          :key="9"
          class="navitem"
        >
          <!-- <v-list-item-content
            class="navitemcontent"
          >
            ---
          </v-list-item-content> -->
        </v-list-item>
        <v-list-item
          v-for="(name, i) in moreItems"
          :key="i+10"
          class="navitem"
        >
          <v-list-item-content
            class="navitemcontent"
          >
            <router-link :to="{ name }">
              {{ name }}
            </router-link>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          v-for="(name, i) in smallItems"
          :key="i+20"
          class="navsubitem"
        >
          <v-list-item-content
            class="navsubitemcontent"
            :class="{ 'navsubitem-margin' : i == 0 }"
          >
            <router-link :to="{ name }">
              {{ name }}
            </router-link>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          class="navsubitem"
        >
          <v-list-item-content
            class="navsubitemcontent"
            :class="{ 'navsubitem-margin' : 4 == 0 }"
          >
            <a href="/aup.pdf">AUP</a>
          </v-list-item-content>
        </v-list-item>
        <v-list-item
          :key="18"
          class="navsubitem"
        >
          <v-list-item-content
            class="navsubitemcontent extramargin"
          >
            ©2021 Honely, LLC.
          </v-list-item-content>
        </v-list-item>
      </v-card>
    </div>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'BaseInfo',

    props: {
      title: String,
      dark: Boolean,
    },

    data: () => ({
      items: [
        'Home',
        'About Us',
        'Insights',
        'Smart Search',
        'Partner With Us',
        'Subscribe',
      ],
      moreItems: [
        'FAQ',
        'Contact Us',
      ],
      smallItems: [
        'Privacy Policy',
        'Terms of Usage',
        'Disclaimer',
        'Licensing Agreement',
        'Cookie Policy',
      ],
    }),
  }
</script>

<style lang="sass">
  .footernav.theme--dark.v-card
    background-color: transparent
    text-transform: uppercase

    .navitem
      padding: 0
      margin: 0
      min-height: 10px

    .navitemcontent
      padding: 4px 0

    .navsubitem
      padding: 0
      margin: 0
      min-height: 10px
      text-transform: none

    .navsubitem-margin
      margin-top: 15px

    .navsubitemcontent
      padding: 0

    .navsubitemcontent a
      font-size: 17px
      color: #fafafa
      line-height: 21px

    .navsubitemcontent a:hover
      color: black

  .v-application
    .footernav a
      color: #ffffff

    .footernav a:hover
      color: #000000

  .extramargin
    display: block
    margin-top: 15px
    color: $color-gray-med !important

  .footer-logo
    opacity: .8
    background-position: center left
    .v-image__image--contain
      background-position: center left !important

  @media only screen and (max-width: 600px)
    #icon-band
      .icon-headline
        font-size: 30px !important
        line-height: 30px !important
      .v-list-item__subtitle
        max-width: 350px
        margin: 0 auto
</style>
