<template>
  <div class="iconband-container">
    <div class="flex-wrapper">
      <div
        v-for="item in items"
        :key="item.id"
        class="col-md-3"
      >
        <div>
          <div class="about-band-icon">
            <i
              :class="'mdi ' + item.iconclass"
              aria-hidden="true"
            />
          </div>
          <h4
            class="about-band-headline"
          >
            {{ item.headline }}
          </h4>
          <p
            class="about-band-copy"
            v-html="item.copy"
          >
          </p>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'SectionContactUs',

    data: () => ({
      items: [
        { id: 1, headline: 'Growth', copy: 'Locate a home positioned for growth in just a few clicks.', iconclass: 'mdi-chart-line-variant' },
        { id: 2, headline: 'True Value', copy: 'Discover the true value of your home or potential investment.', iconclass: 'mdi-currency-usd' },
        { id: 3, headline: 'Data Science', copy: 'Leverage industry leading data science and predictive analytics.', iconclass: 'mdi-xml' },
        { id: 4, headline: 'Filter Your Search', copy: 'Filter by zip code, property specifications and forecasted growth rates', iconclass: 'mdi-magnify' },
      ],
    }),

  }
</script>
