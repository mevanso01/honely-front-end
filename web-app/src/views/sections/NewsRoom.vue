<template>
  <!-- eslint-disable -->
  <div class="pg-static">
    <div class="pg-static-banner newsroom">
      <div class="pg-static-banner-overlay">
        <div class="banner-heading">
          <h1>Newsroom</h1>
        </div>
      </div>
    </div>
    <div class="newsroom-container">
      <div v-if="newsroomItems">
        <newsroom-block
        v-for="newsItem in newsroomItems"
        :newsItem="newsItem"
        :key="newsItem.id"
        />
    </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'NewsRoom',
    components: {
      NewsroomBlock: () => import('@/components/NewsroomBlock'),
    },
    data () {
      return {
        newsroomItems: [
          {
            id: 1,
            headline: 'Honely.com Signs Licensing Agreement for Its Property and Area Forecast APIs with Forescite, Inc.',
            link: 'https://www.prnewswire.com/news-releases/honelycom-signs-licensing-agreement-for-its-property-and-area-forecast-apis-with-forescite-inc-301512050.html',
            date: 'Mon, 28 Mar 2022',
          },
          {
            id: 2,
            headline: 'Honely Strengthens its Advisory Board with Appointment of Veteran Business Leader and Real Estate Expert Jeff Lobb',
            link: 'https://www.prnewswire.com/news-releases/honely-strengthens-its-advisory-board-with-appointment-of-veteran-business-leader-and-real-estate-expert-jeff-lobb-301437521.html',
            date: 'Mon, 6 Dec 2021',
          },
          {
            id: 3,
            headline: 'Honely.com and HousingAlerts.com Announce Strategic Partnership',
            link: 'https://www.prnewswire.com/news-releases/honelycom-and-housingalertscom-announce-strategic-partnership-301434957.html',
            date: 'Wed, 1 Dec 2021',
          },
          {
            id: 4,
            headline: 'Introducing Honely.com',
            link: 'https://www.prnewswire.com/news-releases/introducing-honelycom-301431322.html',
            date: 'Wed, 24 Nov 2021',
          },
          {
            id: 5,
            headline: 'Honely Announces Successful Closing of its Seed Round',
            link: 'https://www.prnewswire.com/news-releases/honely-announces-successful-closing-of-its-seed-round-301398709.html',
            date: 'Tue, 12 Oct 2021',
          },
        ],
      }
    },
    methods: {
    },
  }
</script>
