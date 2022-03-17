<template>
<!-- eslint-disable -->
<div v-if="!($route.name === 'Buying Landing Page' || $route.name === 'Selling Landing Page' || $route.name === 'Refinancing Landing Page' || $route.name === 'Investing Landing Page')">
  <div class="footer">
    <div class="footer-container">
      <div class="footer-col-links">
        <ul class="footer-links">
          <li
              v-for="(item, i) in siteLinks"
            >
              <router-link
                :id="'footer-link-' + i"
                :key="i"
                :to="item.link"
              >
                {{ item.text }}
              </router-link>
            </li>
        </ul>
        <ul class="footer-links">
          <li
              v-for="(item, i) in policyLinks"
            >
              <router-link
                :id="'footer-link-' + i"
                :key="i"
                :to="item.link"
              >
                {{ item.text }}
              </router-link>
            </li>
        </ul>
      </div>
      <div class="footer-col-social">
        <!-- <p class="footer-feature-text">Sign Up For Our Insights</p>
        <p>Please provide your email in the form below and you will be notified when a new insight gets posted.</p> -->
        <!-- <div class="footer-subscribe-container">
          <span class="mdi mdi-email"></span>
          <input type="text" id="footer-subscribe-email">
          <button class="bg-primary">Subscribe</button>
        </div> -->
        <p class="footer-feature-text">Follow Us</p>
        <ul class="footer-social-links">
          <li><a href="https://www.facebook.com/HonelyRealEstate/"><span class="mdi mdi-facebook"></span></a></li>
          <li><a href="https://www.youtube.com/channel/UC00wpLkRR7IbCDbNbuFX10A"><span class="mdi mdi-youtube"></span></a></li>
          <li><a href="https://www.instagram.com/honelyrealestate/"><span class="mdi mdi-instagram"></span></a></li>
        </ul>
      </div>
      <div class="copyright">
        &copy; {{ getCurrentYear }} Honely, LLC.
      </div>
    </div>
  </div>
</div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  import {
    mapGetters,
  } from 'vuex'

  export default {
    name: 'Footer',
    components: {
    },
    data: () => ({
      siteLinks: [
        {
          text: 'About Us',
          link: '/about',
        },
        {
          text: 'Insights',
          link: '/insights',
        },
        /* {
          text: 'Smart Search',
          link: '/forecast',
        }, */
        {
          text: 'Partner With Us',
          link: '/partner',
        },
        /* {
          text: 'Subscribe',
          link: '/subscribe',
        }, */
        {
          text: 'FAQ',
          link: '/faq',
        },
        {
          text: 'Contact Us',
          link: '/contact',
        },
      ],
      policyLinks: [
        {
          text: 'Privacy Policy',
          link: '/privacy',
        },
        {
          text: 'Terms of Usage',
          link: '/terms',
        },
        {
          text: 'Disclaimer',
          link: '/disclaimer',
        },
        {
          text: 'Licensing Agreement',
          link: '/licensing',
        },
        {
          text: 'Cookie Policy',
          link: '/cookiepolicy',
        },
        {
          text: 'AUP',
          link: '/aup.pdf',
        },
      ],
    }),
    computed: {
      getCurrentYear () {
        return new Date().getFullYear()
      },
    },
    mounted () {
    },
    methods: {
    },
  }
</script>
