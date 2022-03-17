<template>
  <v-theme-provider dark>
    <section id="hero-forecast">
      <base-img
        :height="$vuetify.breakpoint.mdAndUp ? 300 : 'auto'"
        gradient="to bottom, rgba(0,0,0,.2), rgba(0,0,0,.8)"
        :src="src"
        color="#45516b"
        flat
        max-width="100%"
        tile
      >
        <v-row
          v-if="title"
          align="end"
          class="ma-0 fill-height header-container"
          justify="center"
        >
          <v-col
            cols="12"
            md="6"
            class="column-1"
          >
            <base-heading
              :title="title"
              align="left"
              space="2"
              weight="500"
            />

            <!-- base-divider
              color="primary"
              dense
            / -->
            <v-subheader
              class="page-subheader mt-6 mb-12"
            >
              <p>
                {{ copy }}
              </p>
            </v-subheader>
          </v-col>
          <v-col
            cols="12"
            md="6"
            class="column-2 fill-height"
          >
            <v-row
              id="hero-search-box-cta"
              class="border-me-not"
            >
              <v-col
                class="text-center  featured-search-form-container"
                style="background-color: rgba(255, 255, 255, 0.8)"
                cols="12"
              >
                <div
                  class="text-h5 mb-0"
                >
                  Honely Property Value Forecast
                </div>
                <div
                  class="subtitle mb-6"
                >
                  Begin by searching an address or zip code
                </div>
                <div
                  class="featured-search-form-row text-center mx-auto"
                >
                  <v-combobox
                    ref="search"
                    v-model="model"
                    :items="entries"
                    :loading="isLoading"
                    :search-input.sync="search"
                    background-color="white"
                    class="suggestions-field2"
                    clearable
                    color="#000000"
                    dense
                    light
                    filled
                    hide-no-data
                    hide-selected
                    item-color="#7c7c7c"
                    item-text="name"
                    item-value="name"
                    outlined
                    placeholder="Simply enter address or zip to access"
                    append-outer-icon="mdi-magnify"
                    append-icon=""
                    return-object
                    @change="onItemChange"
                    @keyup="onKeyup"
                    @click:append-outer="onSearchClick"
                    no-filter
                    :error-messages='errorMessage'
                  >
                  <template v-slot:item="data">
                    <template>
                      <v-list-item-content>
                        <!-- <v-list-item-title v-if="searchLevel==='error'">{{ data.item.name }}</v-list-item-title> -->
                        <!-- <v-list-item-title v-if="searchLevel==='city'"><span style='float: left;'>{{ (splitAddress(data.item.name)).city }}</span>{{ (splitAddress(data.item.name)).pre }} <span style='border: 1px solid black; border-radius: 4px; padding: 1.5px; float: right'> {{ (splitAddress(data.item.name)).state }}</span></v-list-item-title> -->
                        <v-list-item-title v-if="searchLevel==='zip'"><span style='border: 1px solid green; float: right; margin-left: 4px; color: green; border-radius: 4px; padding: 1.5px;'>{{ (splitAddress(data.item.name)).zip }}</span></v-list-item-title>
                        <v-list-item-title v-if="searchLevel==='street'">{{ (splitAddress(data.item.name)).pre }}<span style='border: 1px solid green; float: right; margin-left: 4px; color: green; border-radius: 4px; padding: 1.5px;'>{{ (splitAddress(data.item.name)).zip }}</span><span style='border: 1px solid black; float: right; border-radius: 4px; padding: 1.5px;min-width:30px; text-align: center; font-family: "Lucida Console", "Courier New", monospace'><b>{{ (splitAddress(data.item.name)).state }}</b></span></v-list-item-title>
                        <!-- <v-list-item-title >{{ data.item.zip }}</v-list-item-title> -->
                      </v-list-item-content>
                    </template>
                  </template>
                  </v-combobox>
                  <p
                      v-if="error"
                      class="forecast-error"
                  >
                    {{ error }}
                  </p>
                </div>
              </v-col>
            </v-row>
          </v-col>
        </v-row>
      </base-img>
    </section>
  </v-theme-provider>
</template>

<script>

  import axios from 'axios'

  export default {
    name: 'HeroForecast',
    props: {
      forecast: Object,
      error: String,
    },

    provide: {
      heading: { align: 'center' },
    },

    data: () => ({
      entries: [],
      isLoading: false,
      model: null,
      item: null,
      search: null,
      timerId: null,
      textValue: '',
      initialLoad: false,
      searchLevel: '',
      errorMessage: '',
      apiCallTs: 0,
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

    watch: {
      search (val) {
        if (val) {
          if (val.length >= 5) {
            this.isMinLength = true
          } else {
            this.isMinLength = false
            // return
          }
        } else {
          this.errorMessage = ''
          return
        }
        if (this.initialLoad) {
          this.initialLoad = false
          return
        }

        clearTimeout(this.timerId)

        // if (val === '') {
        //   this.isLoading = false
        //   this.entries = []
        //   return
        // }

        // this.isLoading = true  //vx: for the search box animation

        this.timerId = setTimeout(() => {
          this.errorMessage = ''
          this.isLoading = true
          // Lazily load input items
          // axios.get('https://api.honely.com/search/forecast_suggestions', {
          this.entries = []
          axios.get('https://api.honely.com/lookup/forecast_suggestions', {
            params: {
              address: val,
            },
          }).then((response) => {
            if (response.data.timestamp > this.apiCallTs) {
              this.apiCallTs = response.data.timestamp
              this.isLoading = false
              this.searchLevel = response.data.level
              this.entries = response.data.rows.map(function (item) {
                return { name: item }
              })
            }
            // this.isLoading = false
            // this.searchLevel = response.data.level
            // this.entries = response.data.rows.map(function (item) {
            //   return { name: item }
            // })
            // console.log('vx: in happy case this.entries is', this.entries)
          })
            .catch((error) => {
              //  console.warn(error.response.status)
              // console.log('vx: error.response.data.message is', error.response.data.message)
              console.warn(error)
              if (error.response.data.timestamp > this.apiCallTs) {
                this.apiCallTs = error.response.data.timestamp
                this.entries = []
                this.isLoading = false
                if (val.length !== 0) {
                  this.errorMessage = 'Sorry. No suggestions found.'
                }
              }
              // this.entries = []
              // this.searchLevel = 'error'
              // this.entries = [{name: error.response.data.message}]
              // console.log('vx: in sad case this.entries is', this.entries)
              // this.isLoading = false
              // this.errorMessage = 'Sorry. No suggestions found.'
              return null
            })
        }, 200)
      },
    },

    methods: {
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
      onItemChange (item) {
        // this.$router.push({ path: '/listings?address=' + encodeURI(item.name) })
        if (item && item.name) {
          if (item.name.length >= 5) {
            this.gotoForecast(item.name)
          } else {
            this.errorMessage = 'Search query must contain at least 5 characters'
          }
        }
      },
      onKeyup (event) {
        this.textValue = event.target.value
        if (event.keyCode === 13) {
          if (event.target.value.trim() !== '') {
            // this.$router.push({ path: '/listings?address=' + encodeURI(event.target.value) })
            if (event.target.value.length >= 5) {
              this.gotoForecast(event.target.value)
            } else {
              this.errorMessage = 'Search query must contain at least 5 characters'
            }
          }
        }
      },
      onSearchClick (event) {
        if (this.textValue !== '') {
          if (this.textValue.length >= 5) {
            this.gotoForecast(this.textValue)
          } else {
            this.errorMessage = 'Search query must contain at least 5 characters'
          }
        }
      },
      gotoForecast (value) {
        // this.isLoading = true
        this.errorMessage = ''
        this.$router.push({ name: 'Smart Search', query: { address: value } })
          .then(() => {
            // this.isLoading = false
            if (this.$vuetify.breakpoint.smAndDown) {
              scrollTo({
                top: 1390,
                behavior: 'smooth',
              })
            } else {
              scrollTo({
                top: 884,
                behavior: 'smooth',
              })
            }
          })
          // .catch((error) => {
          //   this.isLoading = false
          //   this.errorMessage = error
          // })
      },
    },
  }
</script>
