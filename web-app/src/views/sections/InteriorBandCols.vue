<template>
  <div class="page-partner">
    <v-container>
      <!-- eslint-disable -->
      <!-- section-parter-top -->
      <div class="section-partner-top">
        <div class="section-col-left">
          <h2>{{ item.headline }}</h2>
        </div>
        <div class="section-col-right">
          <button
            v-if="userTypeDetermined && (userType === null || userType === 'AGENT/BROKER' || userType === 'LENDER' || userType === 'GENERAL CONTRACTOR')"
            @click="goToLeadSubscriptions()"
            class="section-promo-btn"
          >
            Apply for Free Leads
          </button>
        </div>
      </div>
      <!-- /section-parter-top -->

      <!-- section-partner -->
      <div class="section-partner narrow">
        <!-- section-partner-heading -->
        <div class="section-partner-heading">
          <div class="section-partner-heading-left">
            <h2>Realtors, Brokerages, Lenders &amp; other Service Providers</h2>
          </div>
          <div class="section-partner-heading-right">
            <i class="mdi header-icon mdi-cash-multiple" aria-hidden="true"/>
            <i class="mdi header-icon mdi-office-building-outline" aria-hidden="true"/>
            <i class="mdi header-icon mdi-account-tie" aria-hidden="true"/>
          </div>
        </div>
        <!-- /section-partner-heading -->

        <!-- section-partner-copy -->
        <div class="section-partner-copy">
          <p>Sponsor listings and have your information and branding appear directly under properties for FREE.</p>
          <p class="text-bold">Honely has a unique advantage when it comes to the leads we procure.</p>
          <p>We differ from other real estate media sites in that we are far more than a home surfing tool. The in-depth analytics we provide for free on Honely can&#8217;t be accessed by the public anywhere else. Thus, our site visitors are aren&#8217;t just browsing, they&#8217;re seeking an analytical overview of a home as a motivated buyer, seller, or investor. We&#8217;re giving industry professionals the opportunity to reach our motivated audience for free!</p>
        </div>
        <!-- /section-partner-copy -->
      </div>
      <!-- /section-partner -->

      <!-- section-partner -->
      <div class="section-partner">
        <!-- section-partner-heading -->
        <div class="section-partner-heading">
          <div class="section-partner-heading-left">
            <h2>Other Opportunities</h2>
          </div>
          <div class="section-partner-heading-right">
            <a href="/contact" class="btn-links">Get In Touch With Our Team</a>
          </div>
        </div>
        <!-- /section-partner-heading -->

        <!-- section-partner-copy -->
        <div class="section-partner-copy">
          <ul class="section-parter-copy-list">
            <li v-for="box in item.boxes2">
              <span class="section-parter-copy-list-icon">
                <i
                  :class="'mdi header-icon mdi-numeric-' + box.id + '-circle'"
                  aria-hidden="true"
                />
              </span>
              <p>{{ box.copy }}</p>
            </li>
          </ul>
        </div>
        <!-- /section-partner-copy -->
      </div>
      <!-- /section-partner -->
      <!-- eslint-enable -->
    </v-container>
    <login-overlay
      ref="loginOverlay"
      style="text-align: left;"
      :show-overlay="showLoginOverlay"
      :display-tab="overlayTab"
      @hideLoginOverlay="hideLoginOverlay"
    />
  </div>
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'InteriorBandCols',
    components: {
      LoginOverlay: () => import('@/components/login_overlay/LoginOverlay'),
    },
    data: () => ({
      showLoginOverlay: false,
      overlayTab: 'sign-up',
      userTypeDetermined: false,
      userType: null,
      title: '',
      items: [
        {
          id: 1,
          name: 'Partner With Us',
          headline: 'Connect With Our Audience',
          // copy: 'At Honely, whether you're a real estate broker, property manager, lender or any other industry professional we can help you reach and engage with your desired audience.',
          boxes: [
            {
              id: 1,
              headline: 'Realtors, Brokerages, Lenders & other Service Providers',
              copy: '<p>Sponsor listings and have your information and branding appear directly under properties for FREE.</p><b>Honely has a unique advantage when it comes to the leads we procure. </b><br><br><p>We differ from other real estate media sites in that we are far more than a home surfing tool. The in-depth analytics we provide for free on Honely can’t be accessed by the public anywhere else. Thus, our site visitors are aren’t just browsing, they’re seeking an analytical overview of a home as a motivated buyer, seller, or investor. We’re giving industry professionals the opportunity to reach our motivated audience for free!</p>',
              // copy: 'Take advantage of Honely's industry-leading forecasting tools to aid your clients’ decision-making ability, positioning yourself at the forefront of the market.<br /><br />By sponsoring listings on Honely, you can get prominent positioning to stay in front of buyers searching for their dream homes.  Buyers and sellers will be able to find your contact details, perfect for generating leads.',
              iconClass: 'mdi-account-tie',
            },
            // {
            //   id: 2,
            //   headline: 'Brands & Service Providers',
            //   copy: 'Use Honely to form relationships with renters, home buyers, owners, sellers and real estate professionals. By advertising you can promote your products and services nationwide or focus on a specific geography.<br /><br />Using native placements across Honely you can stay in front of your target audiences and showcase your expertise with sponsored content. Sharpen your focus with segmented emails for your thought leadership or upcoming promotions.',
            //   iconClass: 'mdi-office-building-outline',
            // },
            // {
            //   id: 3,
            //   headline: 'Lenders',
            //   copy: 'Reach potential borrowers by promoting your business to home buyers and sellers. Attract new business by demonstrating your knowledge with sponsored content and follow up with qualified leads that have requested to connect with a lender to discuss their financing needs.<br /><br />Home buyers flock to Honely to ensure they’re making the right investment. Take advantage of Honely’s vast reach to target your audience with segmented emails for your thought leadership or upcoming promotions.',
            //   iconClass: 'mdi-cash-multiple',
            // },
          ],
          boxes2: [
            {
              id: 1,
              // headline: 'Realtors, Brokerages, Lenders & other Service Providers',
              copy: 'Leverage our APIs to gain an edge on your business’ website. Lenders, brokerages allow their site users to view our accurate predictive analytics in real time via our APIs. The possibilities are endless and integration is seamless.',
              // copy: 'Take advantage of Honely’s industry-leading forecasting tools to aid your clients’ decision-making ability, positioning yourself at the forefront of the market.<br /><br />By sponsoring listings on Honely, you can get prominent positioning to stay in front of buyers searching for their dream homes.  Buyers and sellers will be able to find your contact details, perfect for generating leads.',
              // iconClass: 'mdi-account-tie',
            },
            {
              id: 2,
              // headline: 'Brands & Service Providers',
              copy: 'We provide Real estate investors at any level, financial institutions, and investment service companies with customizable data solutions that make it easier than ever to make calculated investment decisions.',
              // iconClass: 'mdi-office-building-outline',
            },
            {
              id: 3,
              // headline: 'Lenders',
              copy: 'And more…Our data science team has extensive experience and is highly capable of engineering out-of-the-box data solutions. Reach out to see if there’s something we can do for you!',
              // iconClass: 'mdi-cash-multiple',
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
    watch: {
      showLoginOverlay: function () {
        if (this.showLoginOverlay) {
          document.documentElement.style.overflow = 'hidden'
          return
        }
        document.documentElement.style.overflow = 'auto'
      },
    },
    mounted () {
      if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        // get current user
        axios.get('https://api.honely.com/lookup-test/user_profile', {
          params: {
            email: this.$store.getters['auth/cognitoUser'].attributes.email,
          },
        }).then((response) => {
          this.userType = response.data.user_type
          this.userTypeDetermined = true
          // console.log('vx: userTypeDetermined', this.userTypeDetermined)
          // console.log('vx: userType', this.userType)
          // console.log(this.user)
        }).catch((error) => {
          console.log('[ERROR] Failed to fetch user data')
          console.log(error.response.data.errorMessage)
        })
      } else {
        this.userTypeDetermined = true
        // console.log('vx: userTypeDetermined', this.userTypeDetermined)
        // console.log('vx: userType', this.userType)
      }
    },
    methods: {
      displayLoginOverlay: function (displayTab) {
        this.showLoginOverlay = true
        this.$refs.loginOverlay.changeTab(displayTab)
      },
      hideLoginOverlay: function () {
        this.showLoginOverlay = false
      },
      goToLeadSubscriptions () {
        if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
          window.location.href = '/leads-subscriptions'
        } else {
          this.displayLoginOverlay('create-account')
        }
      },
    },
  }
</script>

<style>
.page-partner {
  padding: 45px 0;
}
.section-partner-top {
  max-width: 1200px;
  padding-bottom: 4rem;
  display: flex;
  flex-wrap: wrap;
  align-items: center;
  justify-content: space-between;
}
.section-partner-top h2 {
  font-family: "Avant Garde Gothic Md", lato, sans-serif;
  font-weight: normal;
  font-size: 39px;
  letter-spacing: 0.5px;
}
.section-partner-top button {
  background: #00a650;
  color: #ffffff;
  line-height: 100%;
  font-size: 24px;
  padding: 15px 25px;
  border-radius: 5px;
  -webkit-transition: all 0.30s ease-in-out;
  -moz-transition: all 0.30s ease-in-out;
  -ms-transition: all 0.30s ease-in-out;
  -o-transition: all 0.30s ease-in-out;
  -webkit-box-shadow: 2px 2px 8px 1px #cccccc;
  -moz-box-shadow: 2px 2px 8px 1px #cccccc;
  box-shadow: 2px 2px 8px 1px #cccccc;
}
.section-partner-top button:hover {
  background: #0bb326;
}
.section-partner {
  margin-bottom: 4rem;
}
.section-partner:last-of-type {
  margin-bottom: 1rem;
}
.section-partner.narrow {
  max-width: 1200px;
}
.section-partner-heading {
  display: flex;
  flex-wrap: wrap;
  justify-content: space-between;
  align-items: flex-end;
  margin-bottom: 1rem;
  border-bottom: 4px solid #e67e22;
}
.section-partner-heading h2 {
  font-size: 30px;
  font-weight: normal;
}
.section-partner-heading-right i {
  color: #cccccc;
  font-size: 50px;
  padding: 0 10px;
}
.section-partner-heading-right a.btn-links {
  display: inline-block;
  background: #e74c3c;
  line-height: 100%;
  text-decoration: none;
  color: #fff;
  padding: 10px;
  border-radius: 5px;
  margin-bottom: 7px;
  -webkit-transition: all 0.30s ease-in-out;
  -moz-transition: all 0.30s ease-in-out;
  -ms-transition: all 0.30s ease-in-out;
  -o-transition: all 0.30s ease-in-out;
  -webkit-box-shadow: 2px 2px 8px 1px #cccccc;
  -moz-box-shadow: 2px 2px 8px 1px #cccccc;
  box-shadow: 2px 2px 8px 1px #cccccc;
  cursor: pointer;
}
.section-partner-heading-right a.btn-links:hover {
  background: #ed5d4e;
}
.section-partner-copy {
  font-size: 18px;
}
ul.section-parter-copy-list {
  padding-left: 0;
  list-style: none;
  display: flex;
  flex-wrap: wrap;
}
ul.section-parter-copy-list li {
  width: 25%;
  margin-right: 2rem;
}
ul.section-parter-copy-list li:last-of-type {
  margin-right: 0;
}
ul.section-parter-copy-list li span.section-parter-copy-list-icon {
  display: block;
  color: #cccccc;
  font-size: 50px;
  line-height: 100%;
  margin-bottom: 5px;
}
ul.section-parter-copy-list li p {
  padding: 0 15px;
}
p.text-bold {
  font-weight: bold;
}
@media (max-width: 900px) {
  .section-partner-top h2 {
    font-size: 33px;
  }
  .section-partner-heading-left {
    width: 50%;
  }
  ul.section-parter-copy-list li {
    width: 30%;
  }
}
@media (max-width: 700px) {
  .page-partner {
    padding: 30px 0;
  }
  .section-partner-top {
    display: block;
  }
  .section-partner-top .section-col-left {
    text-align: center;
  }
  .section-partner-top .section-col-right {
    text-align: center;
    padding-top: 1rem;
  }
  .section-partner-heading {
    display: block;
  }
  .section-partner-heading-left {
    width: 100%;
  }
  ul.section-parter-copy-list li {
    width: 100%;
  }
  .section-partner-heading-right a.btn-links {
    margin-bottom: 10px;
  }
}
@media (max-width: 700px) {
  .section-partner-top h2, .section-partner-heading h2 {
    font-size: 23px;
  }
  .section-partner-heading-right i, ul.section-parter-copy-list li span.section-parter-copy-list-icon {
    font-size: 40px;
  }
}
</style>
