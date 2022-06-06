<template>
  <!-- eslint-disable -->
  <div class="search-container" :class="{standard : standard}">
    <div class="search-input-wrapper">
      <span class="mdi mdi-map-marker color-primary"></span>
      <input
        type="text"
        id="search-input-hero"
        class="search-input"
        placeholder="Enter Address or Zip Code"
        autocomplete="off"
        @keyup="doSearch"
        @click="() => toggleResult(true)"
        v-click-outside="() => toggleResult(false)"
      >
      <span v-if="loading" class='mdi mdi-loading mdi-spin spin search-loader'></span>
      <span v-if="!loading" class='search-loader-placeholder'></span>
      <button v-if="searchButton" class="bg-primary home-search-button">Search</button>
    </div>
    <div class="search-message">{{ message }}</div>
    <div
      v-if="results && results.length > 0 && showResult"
      class="search-result-container"
    >
      <ul v-if="level === 'zip'">
        <li
          v-for="result in results"
          @click="directSearch(result)"
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
          @click="directSearch(result)"
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
      <ul v-else-if="level === 'city'">
        <li
          v-for="result in results"
          @click="directSearch(result)"
        >
          <div class="search-result-col">
            <span class="mdi mdi-map-marker"></span>
            <span class="search-result-street"> {{ (splitAddress(result)).pre }} </span>
          </div>
          <div class="search-result-col">
            <span class="search-result-state"> {{ (splitAddress(result)).state }} </span>
          </div>
        </li>
      </ul>
    </div>
     <contact-agent-form
        :searchQuery="searchQuery"
        v-if="leadFormFlag"
    />
  </div>
  <!-- eslint-enable -->
</template>

<script>
  /* eslint-disable */
  export default {
    name: 'HonelySearch',
    components: {
      ContactAgentForm: () => import('@/components/base/ContactAgentForm'),
    },
    props: {
      listings: Boolean,
      standard: Boolean,
      searchButton: Boolean,
    },
    data: () => ({
      searchQuery: '',
      leadFormFlag: false,
      listingDirect: false,
      timer: null,
      level: null,
      results: [],
      loading: false,
      message: '',
      showResult: false,
    }),
    watch: {
    },
    mounted () {
      if (this.$route.query.address) {
      }
    },
    methods: {
      doSearch () {
        // point to root
        const self = this
        // clear timer
        clearTimeout(this.timer)
        // create new timer
        this.timer = setTimeout(function () {
          let search = document.getElementById('search-input-hero')

          if (search) {
            search = search.value
            if (search !== '') {
              search = search.trim()
            }

            // start search when search text length is bigger than 5 characters
            if (search.length >= 5) {
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
                  self.showResult = true
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
              self.message = 'Search must contain at least 5 characters'
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
        } else if (isNaN(addrConstituents[addrConstituents.length - 1])) {
          state = addrConstituents[addrConstituents.length - 1]
          pre = addrConstituents.slice(0, addrConstituents.length - 1).join(' ')
          pre = pre.slice(0, pre.length-1)
        }
        else {
          zip = addrConstituents[addrConstituents.length - 1]
          state = addrConstituents[addrConstituents.length - 2]
          pre = addrConstituents.slice(0, addrConstituents.length - 2).join(' ')
        }
        addr.pre = pre
        addr.state = state
        addr.zip = zip
        return addr
      },
      directSearch(value) {
        this.errorMessage = ''
        if (this.listings || isNaN(value.substring(value.length - 5, value.length))) {
          // console.log('search from listing page')
          this.$router.push({ name: 'Listings', query: { address: value, offset: 0 } })
            .then(() => {
              this.results = []
            })
        } else {
          if (this.leadFormNeeded(value)) {
            this.leadFormFlag = true
            this.searchQuery = value
          } else {
            if (this.listingDirect) {
              // console.log('search from none listing page')
              this.$router.push({ name: 'Listings', query: { address: value, offset: 0 } })
                .then(() => {
                  this.results = []
                })
            } else {
              // console.log('search from none listing page')
              this.$router.push({ name: 'Smart Search', query: { address: value } })
                .then(() => {
                  this.results = []
                })
            }
          }
        }
      },
      leadFormNeeded (value) {
        return false
        // if (this.$store.getters['auth/isCognitoUserLoggedIn']) {
        //   return false
        // } else {
        //   if (value.length === 5) {
        //     this.listingDirect = true
        //     return false
        //   } else {
        //     const zipInQuery = value.substring(value.length - 5, value.length)
        //     if (isNaN(zipInQuery)) {
        //       return false
        //     } else {
        //       const leadZipCodeList = this.$store.getters['auth/leadZipCodeList']
        //       if (leadZipCodeList !== null && leadZipCodeList.includes(zipInQuery)) {
        //         return false
        //       } else {
        //         return true
        //       }
        //     }
        //   }
        // }
      },
      toggleResult (value) {
        this.showResult = value;
      },
    },
  }
</script>
