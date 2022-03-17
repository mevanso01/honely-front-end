<template>
  <v-tooltip
    :disabled="true"
    right
  >
    <template #activator="{ on, attrs }">
      <v-combobox
        ref="search"
        v-bind="attrs"
        v-model="model"
        :items="entries"
        :loading="isLoading"
        :search-input.sync="search"
        background-color="white"
        class="suggestions-field4"
        :style="$vuetify.breakpoint.smAndDown?'width:90% !important; margin-left:5% !important':''"
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
        placeholder="Enter zipcode or city and choose from the suggestions"
        return-object
        full-width
        v-on="on"
        no-filter
        :error-messages="errorMessage"
        auto-select-first
        @change="onItemChange"
      >
        <template #item="data">
          <template>
            <v-list-item-content>
              <v-list-item-title v-if="searchLevel==='city'">
                <span style="float: left;">{{ (splitAddress(data.item.name)).city }}</span>{{ (splitAddress(data.item.name)).pre }} <span style="border: 1px solid black; float: right; border-radius: 4px; padding: 1.5px;min-width:30px; text-align: center; font-family: &quot;Lucida Console&quot;, &quot;Courier New&quot;, monospace; "> <b>{{ (splitAddress(data.item.name)).state }}</b></span>
              </v-list-item-title>
              <v-list-item-title v-if="searchLevel==='zip'">
                <span style="border: 1px solid green; float: right; margin-left: 4px; color: green; border-radius: 4px; padding: 1.5px;">{{ (splitAddress(data.item.name)).zip }}</span>
              </v-list-item-title>
              <!-- <v-list-item-title v-if="searchLevel==='street'">{{ (splitAddress(data.item.name)).pre }}<span style='border: 1px solid green; float: right; margin-left: 4px; color: green; border-radius: 4px; padding: 1.5px;'>{{ (splitAddress(data.item.name)).zip }}</span><span style='border: 1px solid black; float: right; border-radius: 4px; padding: 1.5px;min-width:30px; text-align: center; font-family: "Lucida Console", "Courier New", monospace; '><b>{{ (splitAddress(data.item.name)).state }}</b></span></v-list-item-title> -->
              <!-- <v-list-item-title >{{ data.item.zip }}</v-list-item-title> -->
            </v-list-item-content>
          </template>
        </template>
      </v-combobox>
    </template>
  <!-- <span>Must contain minimum 5 characters</span> -->
  </v-tooltip>
</template>

<script>
  import axios from 'axios'
  export default {
    props: {
      userType: String,
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
      isMinLength: false,
      errorMessage: '',
      apiCallTs: 0,
    }),
    watch: {
      search (val) {
        if (val === null || val === '') {
          this.resetValues()
        }
        if (val) {
          if (val.length >= 5) {
            this.isMinLength = true
            this.errorMessage = ''
          } else {
            this.isMinLength = false
          }
        } else {
          this.errorMessage = ''
        }
        if (this.initialLoad) {
          this.initialLoad = false
          return
        }
        clearTimeout(this.timerId)
        // if (val === '') {
        //   this.isLoading = false
        //   // console.log('vx: entries being set to empty')
        //   this.entries = []
        //   return
        // }
        // this.isLoading = true  //vx: for the search box animation
        this.timerId = setTimeout(() => {
          this.errorMessage = ''
          this.isLoading = true
          // Lazily load input items
          // axios.get('https://api.honely.com/search/suggestions', {
          this.entries = []
          axios.get('https://api.honely.com/lookup/suggestions', {
            params: {
              address: val,
            },
          }).then((response) => {
            if (response.data.timestamp > this.apiCallTs) {
              this.apiCallTs = response.data.timestamp
              this.isLoading = false
              this.searchLevel = response.data.level
              if (this.searchLevel !== 'street') {
                this.entries = response.data.rows.map(function (item) {
                  return { name: item }
                })
              } else {
                this.errorMessage = 'Sorry. No suggestions found.'
                this.entries = []
              }
            }
          })
            .catch((error) => {
              //  console.warn(error.response.status)
              console.warn(error)
              if (error.response.data.timestamp > this.apiCallTs) {
                this.apiCallTs = error.response.data.timestamp
                this.entries = []
                this.isLoading = false
                if (val?.length !== 0) {
                  this.errorMessage = 'Sorry. No suggestions found.'
                }
              }
              // this.entries = []
              // this.isLoading = false
              // this.errorMessage = 'Sorry. No suggestions found.'
              return null
            })
        }, 200)
      },
    },
    mounted () {
      this.isLoading = false
      if (this.$route.query.address) {
        this.initialLoad = true
        this.entries = [{ name: this.$route.query.address }]
        this.model = { name: this.$route.query.address }
      }
    },
    methods: {
      resetValues () {
        this.entries = []
        this.isLoading = false
        this.model = null
        this.item = null
        this.search = null
        this.timerId = null
        this.textValue = ''
        this.initialLoad = false
        this.searchLevel = ''
        this.isMinLength = false
        this.errorMessage = ''
        this.apiCallTs = 0
      },
      splitAddress (item) {
        var addrConstituents = item.split(' ')
        var zip = ''
        var state = ''
        var pre = ''
        var city = ''
        var addr = {}
        if (addrConstituents.length === 1) {
          zip = addrConstituents[0]
        } else {
          if (this.searchLevel === 'city') {
            state = addrConstituents[addrConstituents.length - 1]
            city = addrConstituents.slice(0, addrConstituents.length - 1).join(' ')
            city = city.substring(0, city.length - 1)
          } else {
            zip = addrConstituents[addrConstituents.length - 1]
            state = addrConstituents[addrConstituents.length - 2]
            pre = addrConstituents.slice(0, addrConstituents.length - 2).join(' ')
          }
        }
        addr.pre = pre
        addr.city = city
        addr.state = state
        addr.zip = zip
        return addr
      },
      onItemChange (item) {
        console.log('onItemChange being called')
        // this.$router.push({ path: '/listings?address=' + encodeURI(item.name) })
        if (item !== null && item?.name.length >= 5) {
          this.gotoListings(item.name)
        } else {
          this.errorMessage = 'Search query must contain at least 5 characters.'
        }
      },
      onKeyup (event) {
        console.log('onKeyup being called')
        this.textValue = event.target.value
        if (event.keyCode === 13) {
          if (event.target.value.trim() !== '') {
            // this.$router.push({ path: '/listings?address=' + encodeURI(event.target.value) })
            if (event.target.value.length >= 5) {
              this.gotoListings(event.target.value)
            } else {
              this.errorMessage = 'Search query must contain at least 5 characters.'
            }
          }
        }
      },
      onSearchClick (event) {
        console.log('onSearchClick being called')
        if (this.textValue !== '') {
          if (this.textValue.length >= 5) {
            this.gotoListings(this.textValue)
          } else {
            this.errorMessage = 'Search query must contain at least 5 characters.'
          }
        }
      },
      /* gotoListings (value) {
        // console.log('gotoListings being called')
        this.errorMessage = ''
        // this.isLoading = true
        this.$router.push({ name: 'Listings', query: { address: value, offset: 0 } })
          .then(() => {
            // this.isLoading = false
          })
      }, */
      gotoListings (value) {
        var toSend = {
          searchLevel: this.searchLevel,
          searchedValue: value,
          userType: this.userType,
        }
        this.$emit('fetchSlotCount', toSend)
      },
    },
  }
</script>
