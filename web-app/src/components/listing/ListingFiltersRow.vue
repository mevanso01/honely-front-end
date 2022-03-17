<template>
  <!-- eslint-disable -->
  <div class="listings-filter-container">
    <!-- search -->
    <div class="listings-search">
      <honely-search
        :listings="true"
        :standard="true"
      />
    </div>
    <!-- /search -->
    
    <!-- filters -->
    <div class="listings-filters">
      <!-- <listing-filters @updateFilters="updateFilters" /> -->
      <div class="listings-filters-wrapper">
        <!-- filter-price -->
        <div class="listings-filter">
          <!-- filter-label -->
          <div class="filter-label" id="filter-price" @click="showFilterOptions('filter-price')">
            Price <span class="mdi mdi-chevron-down filter-label-arrow"></span>
          </div>
          <!-- /filter-label -->
          <!-- filter-options -->
          <div class="filter-options" id="option-price">
            <div class="filters">
              <div class="filter-title">Price Range</div>
              <div class="flex-input-row">
                <input type="text" name="filter-price-min" id="filter-price-min" placeholder="Min" @focus="showPriceOption('min')">
                <span class="divider"> - </span>
                <input type="text" name="filter-price-max" id="filter-price-max" placeholder="Max" @focus="showPriceOption('max')">
              </div>
              <div class="filter-flex-options-row cols2 form-row">
                <div class="filter-options-col">
                  <ul class="list-filter-options active" id="list-filter-options-price-min">
                    <li
                      v-for="(option, i) in optionPriceMin"
                      :key="i"
                      :data-value="option.value"
                      @click="applyPriceOption('min', option.value)"
                    >
                      {{ option.text }}
                    </li>
                  </ul>
                </div>
                <div class="divider"></div>
                <div class="filter-options-col">
                  <ul class="list-filter-options" id="list-filter-options-price-max">
                    <li
                      v-for="(option, i) in optionPriceMax"
                      :key="i"
                      :data-value="option.value"
                      @click="applyPriceOption('max', option.value)"
                    >
                      {{ option.text }}
                    </li>
                  </ul>
                </div>
              </div>
            </div>
            <!-- filter-action-row -->
            <div class="filter-action-row">
              <button class="text-btn" @click="resetFilters('option-price')">Clear</button>
              <button @click="applyFilters">Apply</button>
            </div>
            <!-- /filter-action-row -->
          </div>
          <!-- /filter-options -->
        </div>
        <!-- /filter-price -->

        <!-- filter-forecast -->
        <div class="listings-filter">
          <!-- filter-label -->
          <div class="filter-label" id="filter-forecast" @click="showFilterOptions('filter-forecast')">
            Forecast <span class="mdi mdi-chevron-down filter-label-arrow"></span>
          </div>
          <!-- /filter-label -->
          <!-- filter-options -->
          <div class="filter-options" id="option-forecast">
            <div class="filters">
              <!-- forecasted % -->
              <div class="filter-title">% increase in forecasted value</div>
              <div class="form-row">
                <select id="filter-forecast-percent" @change="checkForecastPercent">
                  <option
                    v-for="(option, i) in optionForecastPercent"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
              <div class="filter-flex-options-row cols2 form-row" id="filter-option-forecast-percent-custom">
                <div class="filter-options-col">
                  <input type="text" id="filter-forecast-percent-min" placeholder="Min" class="limit">%
                </div>
                <div class="divider">-</div>
                <div class="filter-options-col">
                  <input type="text" id="filter-forecast-percent-max" placeholder="Max" class="limit">%
                </div>
              </div>
              <!-- forecasted value -->
              <div class="filter-title">Forecasted value</div>
              <div class="form-row">
                <select id="filter-forecast-value">
                  <option
                    v-for="(option, i) in optionForecastValue"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
              <!-- timeframe -->
              <div class="filter-title">Timeframe</div>
              <div class="form-row">
                <select id="filter-forecast-timeframe">
                  <option
                    v-for="(option, i) in optionForecastTimeframe"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
            </div>
            <!-- filter-action-row -->
            <div class="filter-action-row">
              <button class="text-btn" @click="resetFilters('option-forecast')">Clear</button>
              <button @click="applyFilters">Apply</button>
            </div>
            <!-- /filter-action-row -->
          </div>
          <!-- /filter-options -->
        </div>
        <!-- /filter-forecast -->

        <!-- filter-hometype -->
        <div class="listings-filter">
          <!-- filter-label -->
          <div class="filter-label" id="filter-hometype" @click="showFilterOptions('filter-hometype')">
            Home Type <span class="mdi mdi-chevron-down filter-label-arrow"></span>
          </div>
          <!-- /filter-label -->
          <!-- filter-options -->
          <div class="filter-options" id="option-hometype">
            <div class="filter-title">Home Type</div>
            <div class="filters">
              <label
                class="checkbox-group"
                v-for="(option, i) in optionHomeTypes"
                :key="i"
              >
                {{ option.text }}
                <input type="checkbox" name="filter-hometypes" :value="option.value" checked @click="syncCheckboxes(false)">
                <span class="checkmark"></span>
              </label>
            </div>
            <!-- filter-action-row -->
            <div class="filter-action-row">
              <button class="text-btn" @click="resetFilters('option-hometype')">Clear</button>
              <button @click="applyFilters">Apply</button>
            </div>
            <!-- /filter-action-row -->
          </div>
          <!-- /filter-options -->
        </div>
        <!-- /filter-hometype -->

        <!-- filter-bedsbath -->
        <div class="listings-filter">
          <!-- filter-label -->
          <div class="filter-label" id="filter-bedsbath" @click="showFilterOptions('filter-bedsbath')">
            Bed &amp; Bath <span class="mdi mdi-chevron-down filter-label-arrow"></span>
          </div>
          <!-- /filter-label -->
          <!-- filter-options -->
          <div class="filter-options" id="option-bedsbath">
            <div class="filters">
              <!-- beds -->
              <div class="filter-title">Beds</div>
              <div class="flex-input-row form-row">
                <select id="filter-beds-min" @change="syncFilterOption('filter-beds-min', 'filter-beds-min-mobile')">
                  <option
                    v-for="(option, i) in optionBedsMin"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
                <span class="divider"> to </span>
                <select id="filter-beds-max" @change="syncFilterOption('filter-beds-max', 'filter-beds-max-mobile')">
                  <option
                    v-for="(option, i) in optionBedsMax"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
              <!-- baths -->
              <div class="filter-title">Baths</div>
              <div class="flex-input-row form-row">
                <select id="filter-baths-min" @change="syncFilterOption('filter-baths-min', 'filter-baths-min-mobile')">
                  <option
                    v-for="(option, i) in optionBathsMin"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
                <span class="divider"> to </span>
                <select id="filter-baths-max" @change="syncFilterOption('filter-baths-max', 'filter-baths-max-mobile')">
                  <option
                    v-for="(option, i) in optionBathsMax"
                    :key="i"
                    :value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
            </div>
            <!-- filter-action-row -->
            <div class="filter-action-row">
              <button class="text-btn" @click="resetFilters('option-bedsbath')">Clear</button>
              <button @click="applyFilters">Apply</button>
            </div>
            <!-- /filter-action-row -->
          </div>
          <!-- /filter-options -->
        </div>
        <!-- /filter-bedsbath -->

        <!-- filter-propertytype -->
        <div class="listings-filter">
          <!-- filter-label -->
          <div class="filter-label" id="filter-property" @click="showFilterOptions('filter-property')">
            Property Details <span class="mdi mdi-chevron-down filter-label-arrow"></span>
          </div>
          <!-- /filter-label -->
          <!-- filter-options -->
          <div class="filter-options right" id="option-property">
            <div class="filters">
              <div class="filter-title">Property Attributes Must Include:</div>
              <label
                class="checkbox-group"
                v-for="(option, i) in optionAttributes"
                :key="i"
              >
                {{ option.text }}
                <input type="checkbox" name="filter-attributes" :value="option.value" @click="syncCheckboxes(false)">
                <span class="checkmark"></span>
              </label>
            </div>
            <!-- filter-action-row -->
            <div class="filter-action-row">
              <button class="text-btn" @click="resetFilters('option-property')">Clear</button>
              <button @click="applyFilters">Apply</button>
            </div>
            <!-- /filter-action-row -->
          </div>
          <!-- /filter-options -->
        </div>
        <!-- /filter-propertytype -->

        <!-- filter-more -->
        <div class="listings-filter">
          <!-- filter-label -->
          <div class="filter-label" id="filter-more" @click="showFilterOptions('filter-more')">
            More <span class="mdi mdi-chevron-down filter-label-arrow"></span>
          </div>
          <!-- /filter-label -->
          <!-- filter-options -->
          <div class="filter-options right" id="option-more">
            <div class="filters">
              <!-- filter-more-hometypes -->
              <div class="filter-title">Home Type</div>
              <div class="filter-flex-options-row">
                <label
                  class="checkbox-group"
                  v-for="(option, i) in optionHomeTypes"
                  :key="i"
                >
                  {{ option.text }}
                  <input type="checkbox" name="filter-hometypes-mobile" :value="option.value" checked @click="syncCheckboxes(true)">
                  <span class="checkmark"></span>
                </label>
              </div>
              <!-- filter-more-beds -->
              <div class="filter-title">Beds</div>
              <div class="flex-input-row form-row">
                <select id="filter-beds-min-mobile" @change="syncFilterOption('filter-beds-min-mobile', 'filter-beds-min')">
                  <option
                    v-for="(option, i) in optionBedsMin"
                    :key="i"
                    :data-value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
                <span class="divider"> to </span>
                <select id="filter-beds-max-mobile" @change="syncFilterOption('filter-beds-max-mobile', 'filter-beds-max')">
                  <option
                    v-for="(option, i) in optionBedsMax"
                    :key="i"
                    :data-value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
              <!-- filter-more-baths -->
              <div class="filter-title">Baths</div>
              <div class="flex-input-row form-row">
                <select id="filter-baths-min-mobile" @change="syncFilterOption('filter-baths-min-mobile', 'filter-baths-min')">
                  <option
                    v-for="(option, i) in optionBathsMin"
                    :key="i"
                    :data-value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
                <span class="divider"> to </span>
                <select id="filter-baths-max-mobile" @change="syncFilterOption('filter-baths-max-mobile', 'filter-baths-max')">
                  <option
                    v-for="(option, i) in optionBathsMax"
                    :key="i"
                    :data-value="option.value"
                  >
                    {{ option.text }}
                  </option>
                </select>
              </div>
              <!-- filter-more-attributes -->
              <div class="filter-title">Property Attributes Must Include:</div>
              <div class="filter-flex-options-row">
                <label
                  class="checkbox-group"
                  v-for="(option, i) in optionAttributes"
                  :key="i"
                >
                  {{ option.text }}
                  <input type="checkbox" name="filter-attributes-mobile" :value="option.value" @click="syncCheckboxes(true)">
                  <span class="checkmark"></span>
                </label>
              </div>
            </div>
            <!-- filter-action-row -->
            <div class="filter-action-row">
              <button class="text-btn" @click="resetFilters('option-more')">Clear</button>
              <button @click="applyFilters">Apply</button>
            </div>
            <!-- /filter-action-row -->
          </div>
          <!-- /filter-options -->
        </div>
        <!-- /filter-more -->
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'ListingFiltersRow',
    components: {
      HonelySearch: () => import('@/components/HonelySearch'),
      // ListingFilters: () => import('@/components/listing/ListingFilters'),
    },
    props: {
    },
    data () {
      return {
        optionPriceMin: [
          {
            text: '$0+',
            value: '0',
          },
          {
            text: '$100,000+',
            value: '100000',
          },
          {
            text: '$200,000+',
            value: '200000',
          },
          {
            text: '$300,000+',
            value: '300000',
          },
          {
            text: '$400,000+',
            value: '400000',
          },
          {
            text: '$500,000+',
            value: '500000',
          },
          {
            text: '$600,000+',
            value: '600000',
          },
          {
            text: '$700,000+',
            value: '700000',
          },
          {
            text: '$800,000+',
            value: '800000',
          },
        ],
        optionPriceMax: [
          {
            text: '$500,000',
            value: '500000',
          },
          {
            text: '$600,000',
            value: '600000',
          },
          {
            text: '$700,000',
            value: '700000',
          },
          {
            text: '$800,000',
            value: '800000',
          },
          {
            text: '$900,000',
            value: '900000',
          },
          {
            text: '$1M',
            value: '1000000',
          },
          {
            text: '$1.25M',
            value: '1250000',
          },
          {
            text: '$1.5M',
            value: '1500000',
          },
          {
            text: '$1.75M',
            value: '1750000',
          },
          {
            text: 'Any Price',
            value: 'Any Price',
          },
        ],
        optionForecastPercent: [
          {
            text: 'Any',
            value: 'Any',
          },
          {
            text: '0% - 10%',
            value: '0%-10%',
          },
          {
            text: '10% - 20%',
            value: '10%-20%',
          },
          {
            text: '20% - 30%',
            value: '20%-30%',
          },
          {
            text: '30%+',
            value: '30%+',
          },
          {
            text: 'Custom',
            value: 'Custom',
          },
        ],
        optionForecastValue: [
          {
            text: 'Any',
            value: 'Any',
          },
          {
            text: '100K - 500K',
            value: '100K-500K',
          },
          {
            text: '500K - 1M',
            value: 'Any',
          },
          {
            text: '1M - 1.5M',
            value: '1M-1.5M',
          },
          {
            text: '1.5M - 2M',
            value: '1.5M-2M',
          },
          {
            text: '2M+',
            value: '2M+',
          },
        ],
        optionForecastTimeframe: [
          {
            text: '1 Year',
            value: '1',
          },
          {
            text: '2 Years',
            value: '2',
          },
          {
            text: '3 Years',
            value: '3',
          },
        ],
        optionHomeTypes: [
          {
            text: 'Single Family',
            value: 'SFR',
          },
          {
            text: 'Multi-Family',
            value: 'MFR',
          },
          {
            text: 'Apartment',
            value: 'APT',
          },
          {
            text: 'Land',
            value: 'LAND',
          },
          {
            text: 'Others',
            value: 'OTHERS',
          },
        ],
        optionBedsMin: [
          {
            text: 'No Minimum',
            value: 'No Minimum',
          },
          {
            text: '1',
            value: '1',
          },
          {
            text: '2',
            value: '2',
          },
          {
            text: '3',
            value: '3',
          },
          {
            text: '4',
            value: '4',
          },
          {
            text: '5',
            value: '5',
          },
          {
            text: '6',
            value: '6',
          },
        ],
        optionBedsMax: [
          {
            text: 'No Maximum',
            value: 'No Maximum',
          },
          {
            text: '1',
            value: '1',
          },
          {
            text: '2',
            value: '2',
          },
          {
            text: '3',
            value: '3',
          },
          {
            text: '4',
            value: '4',
          },
          {
            text: '5',
            value: '5',
          },
          {
            text: '6',
            value: '6',
          },
        ],
        optionBathsMin: [
          {
            text: 'No Minimum',
            value: 'No Minimum',
          },
          {
            text: '1',
            value: '1',
          },
          {
            text: '1.5',
            value: '1.5',
          },
          {
            text: '2',
            value: '2',
          },
          {
            text: '2.5',
            value: '2.5',
          },
          {
            text: '3',
            value: '3',
          },
          {
            text: '3.5',
            value: '3.5',
          },
          {
            text: '4',
            value: '4',
          },
          {
            text: '4.5',
            value: '4.5',
          },
          {
            text: '5',
            value: '5',
          },
        ],
        optionBathsMax: [
          {
            text: 'No Maximum',
            value: 'No Maximum',
          },
          {
            text: '1',
            value: '1',
          },
          {
            text: '1.5',
            value: '1.5',
          },
          {
            text: '2',
            value: '2',
          },
          {
            text: '2.5',
            value: '2.5',
          },
          {
            text: '3',
            value: '3',
          },
          {
            text: '3.5',
            value: '3.5',
          },
          {
            text: '4',
            value: '4',
          },
          {
            text: '4.5',
            value: '4.5',
          },
          {
            text: '5',
            value: '5',
          },
        ],
        optionAttributes: [
          {
            text: 'Parking Spot',
            value: 'Parking Spot',
          },
          {
            text: 'Pool',
            value: 'Pool',
          },
          {
            text: 'Garage',
            value: 'Garage',
          },
          {
            text: 'Basement',
            value: 'Basement',
          },
          {
            text: 'Foreclosed',
            value: 'Foreclosed',
          },
        ],
      }
    },
    computed: {
    },
    updated () {
      // default
    },
    mounted () {
    },
    created () {
      // window click event for closing filter options when clicking outside of filters
      window.onclick = function (event) {
        if (!event.target.classList.contains('listings-filter') &&
          !event.target.classList.contains('filter-label') &&
          !event.target.classList.contains('filter-label-arrow') &&
          !event.target.classList.contains('filter-options')) {
          var filterOptions = document.getElementsByClassName('filter-options')
          for (let i = 0; i < filterOptions.length; i++) {
            if (!filterOptions[i].contains(event.target)) {
              if (filterOptions[i].classList.contains('active')) {
                filterOptions[i].classList.remove('active')
              }
            }
          }
        }
      }
      // initialize filters
      // this.initializeFilters()
    },
    methods: {
      initializeFilters () {
        console.log('[INFO] initialize filters......')
      },
      showFilterOptions (filter) {
        if (filter) {
          // show selected filter options
          const dropdown = document.getElementById(filter).querySelector('.filter-label-arrow')
          const toShow = document.getElementById(filter.replace('filter-', 'option-'))
          if (!toShow.classList.contains('active')) {
            // hide other filters first
            this.hideFilterOptions()
            this.resetDropdownArrow()
            // activate options
            toShow.classList.add('active')
            dropdown.classList.add('mdi-chevron-up')
          } else {
            // hide all filters
            this.hideFilterOptions()
            this.resetDropdownArrow()
          }
        }
      },
      resetDropdownArrow () {
        const filterLabelArrows = document.getElementsByClassName('filter-label-arrow')
        for (let i = 0; i < filterLabelArrows.length; i++) {
          filterLabelArrows[i].classList.remove('mdi-chevron-up')
          filterLabelArrows[i].classList.add('mdi-chevron-down')
        }
      },
      hideFilterOptions () {
        const options = document.getElementsByClassName('filter-options')
        for (let i = 0; i < options.length; i++) {
          options[i].classList.remove('active')
        }
      },
      showPriceOption (option) {
        if (option) {
          if (option === 'min') {
            document.getElementById('list-filter-options-price-min').classList.add('active')
            document.getElementById('list-filter-options-price-max').classList.remove('active')
          } else if (option === 'max') {
            document.getElementById('list-filter-options-price-max').classList.add('active')
            document.getElementById('list-filter-options-price-min').classList.remove('active')
          }
        }
      },
      applyPriceOption (option, value) {
        // console.log(option + ' | ' + value)
        if (value) {
          if (option === 'min') {
            document.getElementById('filter-price-min').value = value
          } else if (option === 'max') {
            document.getElementById('filter-price-max').value = value
          }
        }
      },
      checkForecastPercent () {
        const select = document.getElementById('filter-forecast-percent')
        if (select) {
          if (select.value.toLowerCase() === 'custom') {
            document.getElementById('filter-option-forecast-percent-custom').classList.add('active')
          } else {
            document.getElementById('filter-option-forecast-percent-custom').classList.remove('active')
          }
        }
      },
      resetFilters (filter) {
        if (filter) {
          if (filter === 'option-price') {
            this.resetFilterPrice()
          } else if (filter === 'option-forecast') {
            this.resetFilterForecast()
          } else if (filter === 'option-hometype') {
            this.resetFilterHomeTypes()
            this.resetFilterHomeTypesMobile()
          } else if (filter === 'option-bedsbath') {
            this.resetFilterBedsBaths()
            this.resetFilterBedsBathsMobile()
          } else if (filter === 'option-property') {
            this.resetFilterAttributes()
            this.resetFilterAttributesMobile()
          } else if (filter === 'option-more') {
            this.resetFilterMore()
          } else if (filter === 'all') {
            this.resetFilterPrice()
            this.resetFilterForecast()
            this.resetFilterHomeTypes()
            this.resetFilterHomeTypesMobile()
            this.resetFilterBedsBaths()
            this.resetFilterBedsBathsMobile()
            this.resetFilterAttributes()
            this.resetFilterAttributesMobile()
          }
        }
      },
      resetFilterPrice () {
        document.getElementById('filter-price-min').value = ''
        document.getElementById('filter-price-max').value = ''
      },
      resetFilterForecast () {
        document.getElementById('filter-forecast-percent').selectedIndex = 0
        document.getElementById('filter-forecast-percent-min').value = ''
        document.getElementById('filter-forecast-percent-max').value = ''
        document.getElementById('filter-forecast-value').selectedIndex = 0
        document.getElementById('filter-forecast-timeframe').selectedIndex = 0
      },
      resetFilterHomeTypes () {
        const filterHometypes = document.getElementsByName('filter-hometypes')
        for (let i = 0; i < filterHometypes.length; i++) {
          filterHometypes[i].checked = true
        }
      },
      resetFilterHomeTypesMobile () {
        const filterHometypes = document.getElementsByName('filter-hometypes-mobile')
        for (let i = 0; i < filterHometypes.length; i++) {
          filterHometypes[i].checked = true
        }
      },
      resetFilterBedsBaths () {
        document.getElementById('filter-beds-min').selectedIndex = 0
        document.getElementById('filter-beds-max').selectedIndex = 0
        document.getElementById('filter-baths-min').selectedIndex = 0
        document.getElementById('filter-baths-max').selectedIndex = 0
      },
      resetFilterBedsBathsMobile () {
        document.getElementById('filter-beds-min-mobile').selectedIndex = 0
        document.getElementById('filter-beds-max-mobile').selectedIndex = 0
        document.getElementById('filter-baths-min-mobile').selectedIndex = 0
        document.getElementById('filter-baths-max-mobile').selectedIndex = 0
      },
      resetFilterAttributes () {
        const filterAttributes = document.getElementsByName('filter-attributes')
        for (let i = 0; i < filterAttributes.length; i++) {
          filterAttributes[i].checked = false
        }
      },
      resetFilterAttributesMobile () {
        const filterAttributes = document.getElementsByName('filter-attributes-mobile')
        for (let i = 0; i < filterAttributes.length; i++) {
          filterAttributes[i].checked = false
        }
      },
      resetFilterMore () {
        // more - home tyes
        this.resetFilterHomeTypesMobile()
        this.resetFilterHomeTypes()
        // more - attributes
        this.resetFilterAttributesMobile()
        this.resetFilterAttributes()
        // more - beds baths
        this.resetFilterBedsBathsMobile()
        this.resetFilterBedsBaths()
      },
      syncFilterOption (input, target) {
        if (input) {
          document.getElementById(target).value = document.getElementById(input).value
        }
      },
      syncCheckboxes (mobile) {
        const filterHometypes = document.getElementsByName('filter-hometypes')
        const filterHometypesMobile = document.getElementsByName('filter-hometypes-mobile')
        const filterAttributes = document.getElementsByName('filter-attributes')
        const filterAttributesMobile = document.getElementsByName('filter-attributes-mobile')

        if (mobile) {
          // home types
          for (let i = 0; i < filterHometypesMobile.length; i++) {
            if (filterHometypesMobile[i].checked) {
              filterHometypes[i].checked = true
            } else {
              filterHometypes[i].checked = false
            }
          }
          // attributes
          for (let i = 0; i < filterAttributesMobile.length; i++) {
            if (filterAttributesMobile[i].checked) {
              filterAttributes[i].checked = true
            } else {
              filterAttributes[i].checked = false
            }
          }
        } else {
          // home types
          for (let i = 0; i < filterHometypes.length; i++) {
            if (filterHometypes[i].checked) {
              filterHometypesMobile[i].checked = true
            } else {
              filterHometypesMobile[i].checked = false
            }
          }
          // attributes
          for (let i = 0; i < filterAttributes.length; i++) {
            if (filterAttributes[i].checked) {
              filterAttributesMobile[i].checked = true
            } else {
              filterAttributesMobile[i].checked = false
            }
          }
        }
      },
      applyFilters () {
        /* eslint-disable */
        // get all filter options
        let filterPriceMin = document.getElementById('filter-price-min').value
        let filterPriceMax = document.getElementById('filter-price-max').value
        let filterPriceRange = ''
        let filterForecastPercent = document.getElementById('filter-forecast-percent').value
        let filterForecastPercentMin = document.getElementById('filter-forecast-percent-min').value
        let filterForecastPercentMax = document.getElementById('filter-forecast-percent-max').value
        let filterForecastValue = document.getElementById('filter-forecast-value').value
        let filterForecastTimeframe = document.getElementById('filter-forecast-timeframe').value
        let filterBedsMin = document.getElementById('filter-beds-min').value
        let filterBedsMax = document.getElementById('filter-beds-max').value
        let filterBathsMin = document.getElementById('filter-baths-min').value
        let filterBathsMax = document.getElementById('filter-baths-max').value
        const filterHometypesChk = document.getElementsByName('filter-hometypes')
        const filterHometypes = []
        let filterHomeTypesUpdated = false
        const filterAttributesChk = document.getElementsByName('filter-attributes')
        const filterAttributes = []
        let filterAttributesUpdated = false

        // get values from checkboxes
        for (let i = 0; i < filterHometypesChk.length; i++) {
          if (!filterHometypesChk[i].checked) {
            filterHomeTypesUpdated = true
            break;
          }
        }
        for (let i = 0; i < filterAttributesChk.length; i++) {
          if (filterAttributesChk[i].checked) {
            filterAttributesUpdated = true
            break;
          }
        }
        // developer notes, comment out after test
        /* console.log('*********** [S - FILTER TEST] ***********')
        console.log('[Filter] price min: ' + filterPriceMin)
        console.log('[Filter] price max: ' + filterPriceMax)
        console.log('[Filter] price range: ' + filterPriceRange)
        console.log('[Filter] forecast percent: ' + filterForecastPercent)
        console.log('[Filter] forecast percent min: ' + filterForecastPercentMin)
        console.log('[Filter] forecast percent max: ' + filterForecastPercentMax)
        console.log('[Filter] forecast value: ' + filterForecastValue)
        console.log('[Filter] forecast timeframe: ' + filterForecastTimeframe)
        console.log('[Filter] beds min: ' + filterBedsMin)
        console.log('[Filter] beds max: ' + filterBedsMax)
        console.log('[Filter] baths min: ' + filterBathsMin)
        console.log('[Filter] baths max: ' + filterBathsMax)
        console.log('[Filter] home types: ' + filterHometypes)
        console.log('[Filter] attributes: ' + filterAttributes)
        console.log('*********** [E - FILTER TEST] ***********') */

        // validate values
        const filtersApplied = {}

        // filter - price min, max, range
        if (filterPriceMin) {
          filterPriceMin = filterPriceMin.trim()
        }
        if (filterPriceMax) {
          filterPriceMax = filterPriceMax.trim()
        }
        if (filterPriceMin !== '' && filterPriceMax !== '' && filterPriceMin.toLowerCase() !== 'any' && filterPriceMax.toLowerCase() !== 'any') {
          filterPriceRange = filterPriceMin + '-' + filterPriceMax
          filtersApplied.price_range =
          {
            filter: 'price_range',
            label: 'Valuation',
            value: filterPriceRange,
            label_value: filterPriceRange,
          }
        } else if (filterPriceMin !== '' && filterPriceMin.toLowerCase() !== 'any' && (filterPriceMax === '' || filterPriceMax.toLowerCase() === 'any')) {
          filtersApplied.price_range =
          {
            filter: 'price_range',
            label: 'Valuation',
            value: filterPriceMin,
            label_value: filterPriceMin + '+',
          }
        } else if ((filterPriceMin === '' || filterPriceMin.toLowerCase() === 'any') && filterPriceMax !== '' && filterPriceMax.toLowerCase() !== 'any') {
          filterPriceRange = 0 + '-' + filterPriceMax
          filtersApplied.price_range =
          {
            filter: 'price_range',
            label: 'Valuation',
            value: filterPriceRange,
            label_value: filterPriceRange,
          }
        }
        // filter - forecst percent
        if (filterForecastPercent) {
          if (filterForecastPercent.toLowerCase() !== 'any') {
            if (filterForecastPercent.toLowerCase() === 'custom') {
              if (filterForecastPercentMin && filterForecastPercentMax) {
                filterForecastPercent = filterForecastPercentMin + '%-' + filterForecastPercentMax + '%'
              }
            }
            filtersApplied.forecast_percent =
            {
              filter: 'forecast_percent',
              label: 'Growth %',
              value: filterForecastPercent,
              label_value: filterForecastPercent,
            }
          }
        }
        // filter - forecast value
        if (filterForecastValue) {
          if (filterForecastValue.toLowerCase() !== 'any') {
            filtersApplied.forecast_value =
            {
              filter: 'forecast_value',
              label: 'Growth value',
              value: filterForecastValue,
              label_value: filterForecastValue,
            }
          }
        }
        // filter - forecast year
        if (filterForecastTimeframe) {
          if (filterForecastPercent.toLowerCase() !== 'any' || filterForecastValue.toLowerCase() !== 'any') {
            filtersApplied.forecast_timeframe =
            {
              filter: 'forecast_timeframe',
              label: 'Forecast timeframe',
              value: filterForecastTimeframe,
              label_value: filterForecastTimeframe + 'yr',
            }
          }
        }
        // filter - home types
        if (filterHomeTypesUpdated) {
          console.log('[DEV] filter home types not default')
          for (let i = 0; i < filterHometypesChk.length; i++) {
            if (filterHometypesChk[i].checked) {
              filterHometypes.push(filterHometypesChk[i].value)
            }
          }
          filtersApplied.hometypes =
          {
            filter: 'hometypes',
            label: 'Types',
            value: filterHometypes,
            label_value: filterHometypes,
          }
        }
        // filter - beds
        if (filterBedsMin) {
          if (filterBedsMin.toLowerCase() !== 'no minimum') {
            filtersApplied.beds_min =
            {
              filter: 'beds_min',
              label: 'Beds Min',
              value: filterBedsMin,
              label_value: filterBedsMin,
            }
          }
        }
        if (filterBedsMax) {
          if (filterBedsMax.toLowerCase() !== 'no maximum') {
            filtersApplied.beds_max =
            {
              filter: 'beds_max',
              label: 'Beds Max',
              value: filterBedsMax,
              label_value: filterBedsMax,
            }
          }
        }
        // filter -baths
        if (filterBathsMin) {
          if (filterBathsMin.toLowerCase() !== 'no minimum') {
            filtersApplied.baths_min =
            {
              filter: 'baths_min',
              label: 'Baths Min',
              value: filterBathsMin,
              label_value: filterBathsMin,
            }
          }
        }
        if (filterBathsMax) {
          if (filterBathsMax.toLowerCase() !== 'no maximum') {
            filtersApplied.baths_max =
            {
              filter: 'baths_max',
              label: 'Baths Max',
              value: filterBathsMax,
              label_value: filterBathsMax,
            }
          }
        }
        // filter - attributes
        if (filterAttributesUpdated) {
          console.log('[DEV] filter attributes not default')
          for (let i = 0; i < filterAttributesChk.length; i++) {
            if (filterAttributesChk[i].checked) {
              filterAttributes.push(filterAttributesChk[i].value)
            }
          }
          filtersApplied.attributes =
          {
            filter: 'attributes',
            label: 'Attributes',
            value: filterAttributes,
            label_value: filterAttributes,
          }
        }

        // developer notes, comment out after test
        // console.log('********** [FINAL] **********')
        // console.log(filtersApplied)
        this.applyFiltersToListings(filtersApplied)
      },
      applyFiltersToListings (filtersApplied) {
        this.$emit('filterListings', filtersApplied)
      },
    },
  }
</script>
