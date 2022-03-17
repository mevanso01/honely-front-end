<template>
  <v-theme-provider light>
    <base-section
      id="about-us-2"
      class="black-digital"
      space="40"
    >
      <v-container>
        <v-row
          justify="start"
          class="border-me-not"
        >
          <v-col
            class="intro-text-2"
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
              <!-- {{ copy }} -->
            </p>
          </v-col>
          <v-col
            class="hidden-sm-and-down col"
            md="3"
          />
        </v-row>

        <v-row
          justify="start"
        >
          <v-col
            v-for="item in items"
            :key="item.id"
            cols="12"
            sm="4"
            md="3"
          >
            <v-card
              class="ml-auto"
              color="transparent"
              :max-width="$vuetify.breakpoint.xs ? 'auto' : '360px'"
              outlined
              flat
              light
            >
              <v-list-item three-line>
                <v-list-item-content>
                  <v-list-item-title :class="'headline-2 mb-1 ' + item.hdclass">
                    {{ item.headline }}
                    <div v-if="item.headline === 'Moving Trends'"><br></div>
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
            class="hidden-sm-and-down col"
            md="3"
          />
        </v-row>
        <!-- <v-row justify="start">
          <v-col
            v-for="item2 in items2"
            :key="item2.id"
            cols="12"
            sm="4"
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
                  <v-list-item-title :class="'headline mb-1 ' + item2.hdclass">
                    {{ item2.headline }}
                  </v-list-item-title>
                  <v-list-item-subtitle
                    class="mt-1"
                  >
                    {{ item2.copy }}
                  </v-list-item-subtitle>
                </v-list-item-content>

                <i
                  :class="'mdi header-icon ' + item2.iconclass"
                  aria-hidden="true"
                />
              </v-list-item>
            </v-card>
          </v-col>

          <v-col
            class="hidden-sm-and-down col"
            md="3"
          />
        </v-row> -->
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
/* eslint-disable */
  export default {
    name: 'AboutUs2',
    data: () => ({
      items: [
        { id: 1, headline: 'Property Value Estimates and Forecasts', copy: 'Our platform’s core technology is rooted in AI-driven property valuations and forecasts. Our models look far beyond the last sale price and perform static comparative analysis to arrive at our predictions. We not only incorporate property details such as beds/baths, but also leverage and decipher a number of macroeconomic indicators to gauge the landscape of individual real estate markets. In total, there are thousands of data points and factors we take into consideration on any single home to bring you accurate current valuation estimates and groundbreaking value forecasts (up to 36 months into the future).', hdclass: 'squash', btnclass: 'color-squash', /*iconclass: 'mdi-home-currency-usd'*/ },
        { id: 2, headline: 'Neighborhood At A Glance', copy: 'We are the first platform with the ability to rank zip codes in order of desirability. Our ‘Neighborhood At a Glance Section’ goes above and beyond in terms of depicting how an area stacks up against its state and the nation as a whole. The ‘Fastest Growing Zip Code’ section serves as a critical benchmark for predicting future hot markets. ‘Highest Average Property Value’ lets you know if you are investing in an area early. Our zip code algorithms were designed to help you get a quick, analytical understanding of any neighborhood and stay ahead of the curve.', hdclass: 'squash', btnclass: 'color-squash', /*iconclass: 'mdi-home-search'*/ },
        { id: 3, headline: 'Moving Trends', copy: 'Our forecasted moving trends delineate a zip code\'s projected migration rate at a certain period of time. We help to easily visualize whether people are moving in or out of a given area. This data is hard to come by and is critical for understanding emerging markets, knowing whether an area is a buyers or sellers market, and predicting its future population climate.', hdclass: 'squash', btnclass: 'color-squash', /*iconclass: 'mdi-home-account'*/ },
        { id: 4, headline: 'Buyer Score', copy: 'Our Buyer Score was designed to help you move your home or investment property as quickly as possible. It’s an incredibly unique product that allows you to gauge the competitiveness of your home with a given listing price by providing a score from 0-100. This model takes a multitude of factors into account such as how fast comparable properties have sold in the surrounding area, days listed, property features, geographic indicators, and more. Simply adjust your list price and receive an adjusted score in real time. This allows you to weigh the cost/benefit analysis of different price points instantly, without wasting time experimenting on the market.', hdclass: 'squash', btnclass: 'color-squash', /*iconclass: 'mdi-home-account'*/ },
      ],
      // items2: [
        // { id: 1, headline: 'Property Value Estimates and Forecasts', copy: 'Our platform’s core technology is rooted in AI-driven property valuations and forecasts. Our models look far beyond the last sale price and perform static comparative analysis to arrive at our predictions. We not only incorporate property details such as beds/baths, but also leverage and decipher a number of macroeconomic indicators to gauge the landscape of individual real estate markets. In total, there are thousands of data points and factors we take into consideration on any single home to bring you accurate current valuation estimates and groundbreaking value forecasts (up to 36 months into the future).', hdclass: '', btnclass: 'color-orange', iconclass: 'mdi-home-currency-usd' },
        // { id: 2, headline: 'Neighborhood At A Glance', copy: 'We are the first platform with the ability to rank zip codes in order of desirability. Our ‘Neighborhood At a Glance Section’ goes above and beyond in terms of depicting how an area stacks up against its state and the nation as a whole. The ‘Fastest Growing Zip Code’ section serves as a critical benchmark for predicting future hot markets. ‘Highest Average Property Value’ lets you know if you are investing in an area early. Our zip code algorithms were designed to help you get a quick, analytical understanding of any neighborhood and stay ahead of the curve.', hdclass: '', btnclass: 'color-orange', iconclass: 'mdi-home-search' },
        // { id: 1, headline: 'Moving Trends', copy: 'Our forecasted moving trends delineate a zip code\'s projected migration rate at a certain period of time. We help to easily visualize whether people are moving in or out of a given area. This data is hard to come by and is critical for understanding emerging markets, knowing whether an area is a buyers or sellers market, and predicting its future population climate.', hdclass: 'squash', btnclass: 'color-squash', /*iconclass: 'mdi-home-account'*/ },
        // { id: 2, headline: 'Honely Competitive Score', copy: 'Our Honely Competitive Score was designed to help you move your home or investment property as quickly as possible. It’s an incredibly unique product that allows you to gauge the competitiveness of your home with a given listing price by providing a score from 0-100. This model takes a multitude of factors into account such as how fast comparable properties have sold in the surrounding area, days listed, property features, geographic indicators, and more. Simply adjust your list price and receive an adjusted score in real time. This allows you to weigh the cost/benefit analysis of different price points instantly, without wasting time experimenting on the market.', hdclass: 'squash', btnclass: 'color-squash', /*iconclass: 'mdi-home-account'*/ },
      // ],
      headline: 'What value do we provide?',
    //   copy: 'Whether you’re buying or selling a home, or helping others in the process, Honely has the answers. Use our cutting-edge tools to ensure you’re making the best decisions and investing like a pro.',
    }),
  }
</script>

