<template>
  <!-- eslint-disable -->
  <div class="search-container simple">
    <div class="search-input-wrapper">
      <input
        type="text"
        id="search-input-simple"
        class="search-input"
        placeholder="Add another property address"
        autocomplete="off"
        @keyup="doSearch"
      >
      <span v-if="loading" class='mdi mdi-loading mdi-spin spin search-loader'></span>
      <span v-if="!loading" class='search-loader-placeholder'></span>
      <span class="mdi mdi-magnify search-icon"></span>
    </div>
    <div class="search-message">{{ message }}</div>
    <div
      v-if="results && results.length > 0"
      class="search-result-container"
    >
      <ul v-if="level === 'zip'">
        <li
          v-for="result in results"
          @click="appendSearch(result)"
        >
          <div class="search-result-col">
            <span class="mdi mdi-map-marker"></span>
            <span> {{ result }} </span>
          </div>
        </li>
      </ul>
      <ul v-else-if="level === 'street'">
        <li
          v-for="result in results"
          @click="appendSearch(result)"
        >
          <div class="search-result-col">
            <span class="mdi mdi-home"></span>
            <span class="search-result-street"> {{ (splitAddress(result)).pre }} </span>
          </div>
          <div class="search-result-col">
            <span class="search-result-state"> {{ (splitAddress(result)).state }} </span>
            <span class="search-result-zip"> {{ (splitAddress(result)).zip }} </span>
          </div>
        </li>
      </ul>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  export default {
    name: 'HonelySearchSimple',
    components: {
    },
    props: {
    },
    data: () => ({
      searchQuery: '',
      leadFormFlag: false,
      timer: null,
      level: null,
      results: [],
      loading: false,
      message: '',
      propertyForecast: null,
    }),
    watch: {
    },
    mounted () {
    },
    methods: {
      doSearch () {
        // point to root
        const self = this
        // clear timer
        clearTimeout(this.timer)
        // create new timer
        this.timer = setTimeout(function () {
          let search = document.getElementById('search-input-simple')

          if (search) {
            search = search.value
            if (search !== '') {
              search = search.trim()
            }

            // start search when search text length is bigger than 5 characters
            if (search.length > 5) {
              self.loading = true
              const url = 'https://api.honely.com/lookup/forecast_suggestions?address=' + search
              fetch(url, {
                method: 'GET',
                headers: {
                  'Content-Type': 'application/json',
                },
              }).then(function (response) {
                if (!response.ok) {
                  throw Error(response.status)
                }
                return response.json()
              }).then(function (data) {
                self.loading = false
                if (data) {
                  self.level = data.level
                  const results = data.rows
                  self.results = results
                  self.message = ''
                }
              }).catch((err) => {
                self.loading = false
                self.results = []
                console.log('[ERROR] Honely search API failed =>', err)
                self.message = 'No suggestion found'
              })
            } else if (search === '') {
              self.loading = false
              self.results = []
              self.message = ''
            } else {
              self.loading = false
              self.results = []
              self.message = 'Search must contain at least 6 characters'
            }
          }
        }, 500)
      },
      splitAddress (item) {
        var addrConstituents = item.split(' ')
        var zip = ''
        var state = ''
        var pre = ''
        var addr = {}
        if (addrConstituents.length === 1) {
          zip = addrConstituents[0]
        } else {
          zip = addrConstituents[addrConstituents.length - 1]
          state = addrConstituents[addrConstituents.length - 2]
          pre = addrConstituents.slice(0, addrConstituents.length - 2).join(' ')
        }
        addr.pre = pre
        addr.state = state
        addr.zip = zip
        return addr
      },
      appendSearch(address) {
        this.errorMessage = ''
        this.results = []
        const self = this
        if (address) {
          self.$emit('loading', true)
          const params = {
            address: address,
            user_id: '',
          }
          // get forecast data
          axios.get('https://api.honely.com/searches/short_forecast', {
            params: params,
          }).then((response) => {
            // console.log(response.data)
            document.getElementById('search-input-simple').value = ''
            self.propertyForecast = response.data
            self.$emit('appendProperties', this.propertyForecast)
            self.$emit('loading', false)
          }).catch((error) => {
            self.$emit('loading', false)
            console.log('[ERROR] Failed to fetch forecast data from buyer score')
            console.log(error)
          })
        }
      },
    },
  }
</script>
