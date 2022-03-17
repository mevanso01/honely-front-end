<template>
  <v-theme-provider light>
    <base-section
      id="home-insights"
      space="40"
    >
      <v-container
        id="container-insights"
      >
        <v-row
          justify="start"
          class="border-me-not"
        >
          <v-col
            class="intro-text"
            cols="12"
            sm="8"
            md="8"
          >
            <h1
              class="mb-1"
            >
              Our Latest Insights
            </h1>
            <p>
              Our curated blog has all the content you need to start investing in real estate like a pro, making lives easier for buyers, sellers and everyone in between.
            </p>
          </v-col>
        </v-row>

        <v-row
          justify="start"
          class="border-me-not"
        >
          <v-col
            cols="12"
          >
            <v-card
              v-if="firstItem"
              class="mx-auto featured-insight rounded-lg"
              max-width="1200"
              elevation="6"
              :to="'/insights' + firstItem.path"
            >
              <v-img
                :src="firstItem.image"
                :max-width="$vuetify.breakpoint.xs ? 'auto' : '455px'"
              />
              <div
                class="insight-content"
              >
                <v-card-subtitle>
                  Posted: {{ firstItem.post_date | moment("MMMM D, YYYY") }}
                </v-card-subtitle>
                <v-card-title
                  v-html="firstItem.title"
                />
                <v-card-text>
                  {{ firstItem.truncatedBody }}
                </v-card-text>
              </div>
            </v-card>
          </v-col>
        </v-row>

        <v-row
          v-if="filteredItems"
          justify="start"
          class="border-me-not"
        >
          <!-- 'v-if' temporary just to show 4 items on the home page -->
          <v-col
            v-for="item in filteredItems"
            :key="item.id"
            cols="12"
            sm="8"
            md="4"
          >
            <v-card
              class="mx-auto rounded-lg standard-insight"
              :max-width="$vuetify.breakpoint.xs ? 'auto' : '500px'"
              :min-height="$vuetify.breakpoint.xs ? 'auto' : '350px'"
              elevation="4"
              :to="'/insights' + item.path"
            >
              <v-img
                :src="item.image"
                height="200px"
              />

              <div
                class="insight-content"
              >
                <v-card-subtitle>
                  Posted: {{ item.post_date | moment("MMMM D, YYYY") }}
                </v-card-subtitle>
                <v-card-title
                  v-html="item.title"
                />
              </div>
            </v-card>
          </v-col>
        </v-row>
        <v-row
          justify="center"
          class="border-me-not centeritems"
          align-content="center"
        >
          <v-col
            cols="12"
            align-self="center"
          >
            <base-btn
              :block="$vuetify.breakpoint.smAndDown"
              :to="{ name: 'Insights' }"
              class="btn-home-insights-view-more"
            >
              <i class="fas fa-paper-plane"></i>
              <span>View More</span>
              <!--              <v-icon-->
              <!--                class="icon-left-space mdi-rotate-90  mdi-20px icon-green"-->
              <!--              >-->
              <!--                {{ 'mdi-play' }}-->
              <!--              </v-icon>-->
            </base-btn>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'SectionInsights',
    computed: {
      ...mapGetters('insights', {
        posts: 'allPosts',
      }),
      firstItem () {
        // return { title: 'Did You Know: Why New Construction Homes Are A Top Choice For Today\'s Home Buyer', path: '/2021/7/28/did-you-know--why-new-construction-homes-are-a-top-choice-for-today-s-home-buyer/', image: '/img/banner-insights.485d25f7.jpg', post_date: '2021-07-28', truncatedBody: 'Are you in the market for a new house? If so, you may have been comparing your options when it comes to open listings on the local market. One decision you will need to consider: do you want to purchase an existing home or build a new one? Let\'s ...' }
        var firstItem = this.posts.length > 0 ? this.posts[0] : null
        if (firstItem !== null) {
          var truncatedBody = firstItem.body ? firstItem.body.substring(firstItem.body.indexOf('<p>') + 3, firstItem.body.indexOf('</p>')) + '..' : ''
          if (truncatedBody.length > 250) {
            truncatedBody = truncatedBody.substring(0, 250) + '...'
          }
          firstItem.truncatedBody = truncatedBody
          firstItem.image = firstItem.image_filename
          var year = new Date(firstItem.post_date).getFullYear()
          var month = new Date(firstItem.post_date).getMonth()
          var date = new Date(firstItem.post_date).getDate()
          firstItem.path = `/${year}/${month}/${date}/${firstItem.slug}/`
        }
        return firstItem
        // return this.posts.length > 0 ? this.posts[0] : null
      },
      filteredItems () {
        // return [{ title: '3 Ways That a Reverse Mortgage Can Transform Your Retirement', path: '/2021/7/28/3-ways-that-a-reverse-mortgage-can-transform-your-retirement/', image: '/img/banner-partner.b6026520.jpg', post_date: '2021-07-28', truncatedBody: 'Are you a retired individual looking for ways to increase your financial security? If so, you may have heard of a home equity conversion mortgage, more commonly known as a reverse mortgage. Used correctly, this is one of the most effective ...' }, { title: '3 Key Ways That Driverless Cars Are Going to Reshape How We Design and Use Our Homes', path: '/2021/7/28/3-key-ways-that-driverless-cars-are-going-to-reshape-how-we-design-and-use-our-homes/', image: '/img/banner-forecast.34fcf90b.jpg', post_date: '2021-07-28', truncatedBody: 'Are you ready for the coming shift to driverless cars? While it might seem like some sort of futuristic vision, the truth is that driverless vehicles will be on the road within a few short years. Let\'s explore three key ways that driverless ...' }, { title: '5 Things To Consider When Purchasing Your First Home', path: '/2021/5/2/5-things-to-consider-when-purchasing-your-first-home/', image: '/img/blog-01.6fa1e864.jpg', post_date: '2021-07-28', truncatedBody: 'Taking the first step onto the property ladder is one of the biggest moments in anyone’s life. Buying a home is more than a purchase, it’s a major investment, almost certainly the biggest at that point in the buyer’s life. Being aware of ...' }, { title: '4 Things You Should Never, Ever Say If You Hope to Sell Your Home', path: '/2021/7/28/4-things-you-should-never-ever-say-if-you-hope-to-sell-your-home/', image: '', post_date: '2021-07-28', truncatedBody: 'Have you ever had a conversation with a total stranger where you said something that you regret? If you are placing your house or condo on the market, you\'re eventually going to end up having to chat with potential buyers. Yes, your real estate agent is likely to do most of the talking. However, you will still have to deal with buyers that want to grill you ...' }]
        // vx: EDGE CASE- what if only one post / less than 5 posts???
        var filteredItems = []
        for (let i = 1; i < this.posts.length; i++) {
          var filteredItem = this.posts[i]
          filteredItem.truncatedBody = this.posts[i].body.substring(this.posts[i].body.indexOf('<p>') + 3, this.posts[i].body.indexOf('</p>')) + '..'
          if (filteredItem.truncatedBody.length > 250) {
            filteredItem.truncatedBody = filteredItem.truncatedBody.substring(0, 250) + '...'
          }
          filteredItem.image = this.posts[i].image_filename
          var year = new Date(filteredItem.post_date).getFullYear()
          var month = new Date(filteredItem.post_date).getMonth()
          var date = new Date(filteredItem.post_date).getDate()
          filteredItem.path = `/${year}/${month}/${date}/${filteredItem.slug}/`
          filteredItems.push(filteredItem)
        }
        return filteredItems
        // return this.posts.filter((item, index) => {
        //   return index > 0 && index < 4
        // })
      },
    },
    created () {
      this.$store.dispatch('insights/getPosts', { all: 'no' })
    },
  }
</script>

<style lang="sass">
#home-cards h1
  font-family: $heading-font-family
  font-weight: normal
  font-size: 35px

#home-cards p
  font-family: $heading-font-family
  font-weight: normal
  font-size: 20px
  line-height: 25px

#home-cards .v-sheet.v-card
  box-shadow: 4px 4px 14px #666666
  border-radius: 5px
  border: 1px solid white

.camera-icon
  top: 158px
  left: 8px
  background-color: rgba(0,0,0,.5)
  color: rgba(255, 255, 255, .7) !important

.camera-icon:hover
  color: #ffffff !important

.heart-icon
  bottom: 5px
  right: 8px
  z-index:3
  color: $color-gray-lt !important

.heart-icon:hover
  background-color: transparent
  color: $color-squash !important

#homecontainer
  display: flex
  justify-content: space-between

.card-pricing
  display: block
  flex-basis: auto
  font-size: 23px
  font-weight: normal
  padding: 8px
  line-height: 21px

.card-pricing span.pricecat
  font-size: 14px
  white-space: nowrap
  letter-spacing: 0

.price2
  color: $color-emerald

.card-attributes
  display: block
  flex-basis: auto
  margin-top: 10px !important
  padding: 8px
  font-family: $body-font-family
  font-size: 13px
  text-align: right
  color: #000000 !important

.card-attributes span
  font-size: 10px
  color: $color-gray-lt
  letter-spacing: 0

.card-attributes b
  color: $color-squash

.addressinfo
  padding: 0 0 0 12px
  color: black !important
  font-size: 14px
  line-height: 16px

.addressinfo span
  font-family: $body-font-family
  color: $color-gray-mddk
  font-size: 12px

.cardbtns
  padding:4px

.centeritems
  text-align: center

.icon-left-space
  margin-left: 5px
  margin-right: -5px

.icon-rotate
  transform: rotate(90deg)

.icon-green
  color: $color-emerald !important
  font-size: 22px !important
  line-height: 22px !important

.btn-lightgray
  border: 1px solid white !important
  border-radius: 5px !important
  margin-top: 10px
  background-color: $color-gray-mdlt !important
  height: 40px !important
  width: 215px !important

.btn-lightgray span.v-btn__content
  color: #000000 !important
  font-size: 18px
  font-weight: 600

.btn-lightgray:hover span.v-btn__content
  color: $color-emerald !important

.btn-lightgray:hover
  background-color: $color-gray-lt !important

.theme--light .intro-text h1, .theme--light .intro-text p
  color: $color-gray-shadow

#container-insights
  max-width: 1185px !important

.insight-content
  overflow: hidden

.insight-content .v-card__subtitle
  color: $color-gray-med
  font-size: 15px
  line-height: 17px
  padding-bottom: 8px

.insight-content .v-card__title
  font-family: $heading-font-family
  font-weight: normal
  font-size: 18px
  line-height: 25px
  word-break: normal
  padding-top: 0

.standard-insight
  height: 100%

.standard-insight .v-image
  border-radius: 5px 5px 0 0

.featured-insight, .standard-insight
  border: 1px solid white

.featured-insight
  display: flex

.featured-insight .v-image
  border-radius: 5px 0 0 5px !important
  flex-grow: 1
  flex-shrink: 0

.featured-insight .insight-content
  padding: 20px 40px
  flex-grow: 2
  overflow: hidden

.featured-insight .insight-content .v-card__subtitle
  border-bottom: 4px solid $color-emerald
  padding: 0 0 6px 0

.featured-insight .insight-content .v-card__title
  font-size: 22px
  line-height: 28px
  margin: 10px 0 !important
  padding: 0

.featured-insight .insight-content .v-card__text
  padding: 0
  margin: 0
  font-size: 18px
  line-height: 25px

a.no-underline
  text-decoration: none

@media only screen and (max-width: 600px)
  .featured-insight
    display: block
    .v-image
      border-radius: 5px 5px 0 0 !important

</style>

<style>
.v-application .primary.btn-home-insights-view-more {
  background-color: #ffffff !important;
  color: #00a650 !important;
  font-weight: normal !important;
  border: 1px solid #00a650 !important;
  border-radius: 3px;
}
.v-application .primary.btn-home-insights-view-more:hover {
  background: #e67e22 !important;
  border-color: #e67e22 !important;
  color: #ffffff !important;
}
.v-application .primary.btn-home-insights-view-more i {
  margin-right: 5px;
}
@media only screen and (max-width: 900px) {
  .col-sm-8 {
    flex: 0 0 100%;
    max-width: 100%;
  }
}
</style>
