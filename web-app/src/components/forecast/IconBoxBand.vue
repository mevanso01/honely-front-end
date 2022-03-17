<template>
  <!-- eslint-disable -->
  <div class="forecast-navigation-row">
    <div class="forecast-navigation">
      <div
        v-for="(item, i) in filteredItems"
        :key="item.id"
        class="forecast-nav-item"
      >
        <button @click="scrollTo('forecast-band-' + item.id)">
          <i
            class="mdi"
            :class="item.iconclass"
            aria-hidden="true"
          />
          {{ item.headline }}
        </button>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'IconBoxBand',
    props: {
      forecast: Object,
    },

    data: () => ({
      items: [
        { id: 1, headline: 'Property Value', iconclass: 'mdi-home' },
        { id: 2, headline: 'Neighborhood', iconclass: 'mdi-home-group' },
        { id: 3, headline: 'Moving Trends', iconclass: 'mdi-home-map-marker' },
        { id: 5, headline: 'Buyer Score', iconclass: 'mdi-crown-circle' },
        { id: 7, headline: 'Rental Trends', iconclass: 'mdi-home-city' },
        { id: 6, headline: 'Comparable Properties', iconclass: 'mdi-home-switch' },
        // { id: 8, headline: 'Improvement Calculator', iconclass: 'mdi-chart-box' },
        { id: 4, headline: 'Investment Calculator', iconclass: 'mdi-calculator' },
        { id: 9, headline: 'Service Providers', iconclass: 'mdi-account-wrench' },
      ],
      unused: [
        { id: 4, headline: 'Rental Trends', iconclass: 'mdi-home-city' },
      ],
    }),

    computed: {
      filteredItems: function () {
        if (this.forecast && !this.forecast.property_forecast) {
          return this.items.filter(function (x) { return x.headline !== 'Property Value' && x.headline !== 'Buyer Score' && x.headline !== 'Comparable Properties' && x.headline !== 'Improvement Calculator' && x.headline !== 'Rental Trends' && x.headline !== 'Investment Calculator' })
        } else {
          return this.items
        }
      },
    },
    methods: {
      scrollTo (theID) {
        var element = document.getElementById(theID)
        // header offset should match with the current header height
        var headerOffset = 75
        var elementPosition = element.getBoundingClientRect().top
        var offsetPosition = elementPosition - headerOffset

        window.scrollTo({
          top: offsetPosition,
          behavior: 'smooth',
        })
      },
    },

  }
</script>

<style lang="sass">
  #icon-box-band

    .v-btn
      min-width: 30px
      border-radius: 2px
      font-size: 27px

    .btn-headline
      font-family: $heading-font-family
      font-size: 13px
      line-height: 15px
      font-weight: 400

</style>

<style>
.forecast-navigation-row {
  background: #fafafa;
}
.forecast-navigation {
  max-width: 1785px;
  margin: 0 auto;
  padding: 1rem;
  display: flex;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
}
.forecast-nav-item {
  padding: 1rem;
}
.forecast-nav-item button {
  font-size: 17px;
  width: 230px;
  padding: 15px 0;
  font-weight: 600;
  line-height: 100%;
  background: #f9f9f9;
  border: 1px solid #555555;
  border-radius: 3px;
}
.forecast-nav-item button:hover {
  color: #07871c;
  border-color: #07871c;
}
.forecast-nav-item button i {
  font-size: 30px;
  vertical-align: bottom;
}

@media only screen and (max-width: 1240px) {
  .forecast-navigation-row .col-sm-2 {
    flex: 0 0 30%;
    max-width: 30%;
    padding-right: 10px !important;
    padding-left: 10px !important;
  }
  #icon-box-band .v-btn {
    font-size: 20px;
    padding-top: 22px !important;
    padding-bottom: 22px !important;
  }
}
@media only screen and (max-width: 600px) {
  .forecast-navigation-row .v-list-item {
    padding: 0 10px;
  }
  .forecast-navigation-row .col-sm-2 {
    flex: 0 0 50%;
    max-width: 50%;
  }
  #icon-box-band .btn-headline {
    font-size: 14px;
  }
}
@media only screen and (max-width: 500px) {
  .forecast-navigation {
    padding: 10px 0;
  }
  .forecast-nav-item {
    width: 50%;
    padding: 10px;
  }
  .forecast-nav-item button {
    width: 100%;
    padding: 15px 5px;
    height: 85px;
  }
  .forecast-nav-item button i {
    display: block;
    padding-bottom: 8px;
  }
}
</style>
