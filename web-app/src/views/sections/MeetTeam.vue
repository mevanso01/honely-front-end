<template>
  <div class="meeting-container">
    <div class="section-wrapper">
      <div class="section-header">
        <h1 class="page-sub-title">
          Meet Our Team <br>Behind <span style="color: #24CB43;">Honely</span>
        </h1>
      </div>
      <div class="flex-wrapper">
        <div
          v-for="(item, index) in team_members"
          :key="index"
          class="flex-col4"
        >
          <div
            class="member-card"
            @click="openDialog(item)"
          >
            <img
              :src="item.photo"
              width="100%"
            >
            <h3 class="member-name">
              {{ item.name }}
            </h3>
            <h4 class="member-role">
              {{ item.role }}
            </h4>
            <!-- <div class="member-social">
              <a
                :href="item.twitter"
                _target="blank"
                style="color: #939AA7;"
              >
                <i class="mdi mdi-twitter" />
              </a>
              <a
                :href="item.linkedin"
                _target="blank"
                style="color: #939AA7;"
                class="honely-ml-3"
              >
                <i class="mdi mdi-linkedin" />
              </a>
            </div> -->
          </div>
        </div>
      </div>

      <v-dialog
        v-model="dialog"
        scrollable
        max-width= "1000px"
      >
        <div
          v-if="dialog_member"
          class="card card-body member-card"
        >
          <div
          class="btn-close"
          @click="closeDialog"
          >
            <i class="fa fa-times" />
          </div>
          <div class="card-content">
            <div class="card-left">
              <img
                :src="dialog_member.photo"
                width="100%"
              >
              <!-- <div class="text-center social-btn">
                <a
                  :href="dialog_member.twitter"
                  _target="blank"
                  icon
                  class="dialog-social-icon"
                >
                  <i class="mdi mdi-twitter" />
                </a>
                <a
                  :href="dialog_member.linkedin"
                  _target="blank"
                  class="dialog-social-icon honely-ml-4"
                >
                  <i class="mdi mdi-linkedin" />
                </a>
              </div> -->
            </div>
            <div class="card-right">
              <h2 class="dialog-member-name">
                {{ dialog_member.name }}
              </h2>
              <h5 class="dialog-member-role">
                {{ dialog_member.role }}
              </h5>
              <p class="bio-content">
                {{ dialog_member.bio }}
              </p>
            </div>
          </div>
        </div>
      </v-dialog>
    </div>
  </div>
</template>
<script>
  export default {
    name: 'SectionMeetTeam',

    provide: {
      heading: { align: 'center' },
    },

    data: () => ({
      team_members: [
        {
          id: '',
          name: 'Jonathan Shah',
          role: 'CoFounder – CEO',
          twitter: '',
          linkedin: '',
          photo: require('@/assets/site_images/graphics/team_member1.png'),
          bio: 'Born in Manhattan, Jonathan Shah was immersed in the worlds of financial markets and equities trading at a young age. He continued to develop his passion and interests while attending the esteemed Pine Crest School in South Florida. It was there that he also began exploring alternate asset classes that are not easily traded, such as real estate. He played influential roles in developing models which revolutionized day trading by reading the market and delivering forecasts in real time on any publicly traded stock or crypto-currency.\n\n Subsequently, Jonathan enrolled in Babson College’s Entrepreneurship program, the world’s #1 college for entrepreneurship, which focuses intensely on educating entrepreneurial leaders. He soon furthered his passion for the real estate industry after meeting Babson classmate Maxwell Schwarz. Together they founded Honely, LLC, a property technology company, which Jonathan personally funded through equity trading and investment. Now, Jonathan is the CEO of Honely.com, and is spearheading its mission to revolutionize the journey to home and real estate ownership by leveraging data science and predictive analytics algorithms to give average investors the tools they need to make fully informed real estate investment decisions at fair valuations.',
        },
        {
          id: '',
          name: 'Maxwell Schwarz',
          role: 'CoFounder – President',
          twitter: '',
          linkedin: '',
          photo: require('@/assets/site_images/graphics/team_member2.png'),
          bio: 'Born in Plantation, Florida, Maxwell Schwarz has always been the hardest worker in the room, excelling in his studies and driving him towards many extracurricular feats including a state championship for hockey, mentoring and teaching special needs children, and getting his real estate license at a young age. \n\n Following his enrollment in Babson College’s Entrepreneurship program, the world’s #1 college for entrepreneurship, he met classmate Jonathan Shah, and together co-founded Honely. His extensive knowledge and passion for real estate combined with Jonathan’s skills allowed them to find pain points in the real estate space and solve them with unique solutions. \n\n Now, Maxwell is President of Honely.com, and is primarily focused on helping real estate investors and homeowners realize their home ownership goals. He also runs the company’s daily activities and coordinates all legal and financial matters.',
        },
        {
          id: '',
          name: 'Tigran Tovmasayan',
          role: 'Head Of Creative',
          full_role: '',
          twitter: '',
          linkedin: '',
          photo: require('@/assets/site_images/graphics/team_member4.png'),
          bio: 'Tigran is a passionate entrepreneur with vast experience in Media, Tech and Fashion. He has a bachelor\'s degree in Biological Sciences from UC Irvine but focused his career in the creative industries of Los Angeles. He\'s led multiple production companies as well as co-founded one of the earliest co-working spaces in Los Angeles. He is Head of Creative at Allocate Rite, a fintech company in NYC. \n\n His ability to tap into nascent technologies and creative problem solving has resulted in a wide range of projects including global fashion brands, fortune 500 companies such as Tesla and Sony and Apple. Tigran\'s cinematography and production projects with international artists and celebrities have amassed hundreds of millions of views.',
        },
        {
          id: '',
          name: 'Jeff Lobb',
          role: 'Advisor',
          full_role: '',
          twitter: '',
          linkedin: '',
          photo: require('@/assets/site_images/graphics/team_member3.png'),
          bio: 'Jeff Lobb is the Founder and CEO of SparkTank Media - A Speaking , Training and Coaching company that focuses on Sales, Marketing and Technology in the Real Estate, Mortgage and Title Industries. Jeff Lobb is an International Speaker, Innovator and Consultant has been a REALTOR® for over 26 years. He has more than 10 years experience in the Internet Technology and Sales Management fields both with Fortune 500® companies and startup ventures. Jeff’s Sales, Data, and Wireless experience has held Management and Executive level positions with Companies such as IDT, LEVEL 3 , W2W, Bell Atlantic and Verizon Wireless.\n\n He is a Sales and Marketing Specialist coming from the perspective of a REALTOR! Over 26 years of Real Estate, he has been a top producing agent, has run Real Estate brokerages and has developed training and coaching products to serve the Real Estate Industry. \n\n Jeff has presented at Internet Conferences, Re/Max , Coldwell Banker, ERA, EXIT Realty, Inman Agent Reboot, Inman Connect, XPLODE Real Estate Technology Conferences, RIS Media, Retso, Midwest tech fair, WCR, and many Real Estate boards and MLS’s . Jeff was the Co-Author of the Swanepoel Technology Report in 2013. Recently the Swanepoel Power 200 ranked Jeff in the TOP 20 most powerful in Social Media . Jeff has also recently been named Inman’s Top 100 Most Influential Leaders in Real Estate for two consecutive years. \n\n He has a very accurate pulse on the industry and the many brokerage models. His diverse sales, marketing and technology background gives Jeff the vision, the passion and experience he refers to as “the perfect storm” - Growth Strategies for those who chose to execute the plan. \n\n Jeff is Fluent in the three languages....English, Real Estate, & Technology',
        },
      ],
      dialog_member: null,
      dialog: false,
      open_dialog: false,
    }),

    computed: {
      title () {
        return this.$route.name
      },
      src () {
        return this.$route.meta.src
      },
      copy () {
        return this.$route.meta.copy
      },
    },

    methods: {
      openDialog (item) {
        this.dialog_member = item
        this.dialog = true
      },
      closeDialog () {
        this.dialog_member = null
        this.dialog = false
      },
    },
  }
</script>
