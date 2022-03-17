<template>
  <!-- eslint-disable -->
  <div class="pg-static">
    <div class="pg-static-banner faq">
      <div class="pg-static-banner-overlay">
        <div class="banner-heading">
          <h1>Frequently Asked Questions</h1>
        </div>
      </div>
    </div>
    <div class="html-faq-container">
      <div class="flex-wrapper">
        <div class="flex-col12">
          <v-expansion-panels
            v-model="panel"
            multiple
          >
            <v-expansion-panel
              v-for="(item,i) in faqs"
              :key="i"
              elevation="0"
            >
              <v-expansion-panel-header>
                <h4>
                  {{ item.question }}
                </h4>
                <template v-slot:actions>
                  <v-icon
                    color="#24cb43"
                    size="35"
                  >
                    {{ panel.includes(i) ? 'mdi-minus-circle-outline' : 'mdi-plus-circle-outline' }}
                  </v-icon>
                </template>
              </v-expansion-panel-header>
              <v-expansion-panel-content
                v-html="item.answer"
              />
            </v-expansion-panel>
          </v-expansion-panels>
        </div>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'FaqContent',

    data () {
      return {
        panel: [],
        items: 7,
        faqs: [
          {
            id: 1,
            question: 'What is Honely?',
            answer: '<p>Honely is a Boca Raton-based, real estate analytics provider. Our team consists of real estate experts, data scientists, machine learning engineers, and developers who strive to bring powerful insights to the real estate world. Our data solutions are accurate, accessible, and designed to improve investment decisions.</p>',
          },
          {
            id: 2,
            question: 'How accurate are Honely’s property value forecasts? ',
            answer: '<p>Please visit <a href=\'https://www.honely.com/about\'>About Us</a></p>',
          },
          {
            id: 3,
            question: 'I can’t find my property on the site, how can I add it?',
            answer: '<p>We’re sorry that you’re having trouble! Please reach out regarding your issue and we’ll work to get it resolved as quickly as possible.</p><a href=\'https://www.honely.com/contact\'>Contact Us</a>',
          },
          {
            id: 4,
            question: 'My property information is outdated or incorrect, how can I update my property details?',
            answer: '<p>We’re sorry that you’re having trouble! Please reach out regarding your issue and we’ll work to get it resolved as quickly as possible.</p><a href=\'https://www.honely.com/contact\'>Contact Us</a>',
          },
          {
            id: 5,
            question: 'My property is for sale. Can I post my listing on Honely?',
            answer: '<p>We have MLS coverage on the vast majority of on-market homes, but if we missed you we would love to host your listing on <a href=\'https://www.honely.com\'>Honely.com</a>. An added plus to updating your properties information is that we could provide you the most accurate statistics as well!</p><p>Please look up your properties address, select ‘Update/Add Property Info’ and we’ll have your updated listing up in no time!<p>',
          },
          {
            id: 6,
            question: 'Do you offer advertising opportunities for real estate agents?',
            answer: '<p>We partner with real estate professionals to help them gain exposure and get closer to buyers and sellers and offer a range of advertising&nbsp;services.</p><p>Visit the “partner with us” page to learn more about advertising opportunities or click here to get in touch with our&nbsp;team.</p>',
          },
          {
            id: 7,
            question: 'Can my business use your forecast technology?',
            answer: '<p>Real estate investors, financial institutions, and brands look to Honely to power critical real estate and business decisions with our state-of-the-art forecasting and valuation&nbsp;technology.</p><p><a href="https://www.honely.com/contact">Click here</a> to schedule a meeting with our team to discuss leveraging our real estate forecast and valuation APIs, as well as custom data solutions we can build for your&nbsp;organization.</p>',
          },
        ],
      }
    },
    methods: {
      // Create an array the length of our items
      // with all values as true
      all () {
        this.panel = [...Array(this.items).keys()].map((k, i) => i)
      },
      // Reset the panel
      none () {
        this.panel = []
      },
    },
  }
</script>
