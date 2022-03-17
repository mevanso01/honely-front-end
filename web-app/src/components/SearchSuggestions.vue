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
        class="suggestions-field"
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
        v-on="on"
        height="52px"
        full-width
        no-filter
        :error-messages="errorMessage"
        @change="onItemChange"
        @keyup="onKeyup"
        @click:append-outer="onSearchClick"
      >
        <template #item="data">
          <template>
            <v-list-item-content>
              <!-- <v-list-item-title v-if="searchLevel==='city'"><span style='float: left;'>{{ (splitAddress(data.item.name)).city }}</span>{{ (splitAddress(data.item.name)).pre }} <span style='border: 1px solid black; float: right; border-radius: 4px; padding: 1.5px;min-width:30px; text-align: center; font-family: "Lucida Console", "Courier New", monospace; '> <b>{{ (splitAddress(data.item.name)).state }}</b></span></v-list-item-title> -->
              <v-list-item-title v-if="searchLevel==='zip'">
                <span style="border: 1px solid green; float: right; margin-left: 4px; color: green; border-radius: 4px; padding: 1.5px;">{{ (splitAddress(data.item.name)).zip }}</span>
              </v-list-item-title>
              <v-list-item-title v-if="searchLevel==='street'">
                {{ (splitAddress(data.item.name)).pre }}<span style="border: 1px solid green; float: right; margin-left: 4px; color: green; border-radius: 4px; padding: 1.5px;">{{ (splitAddress(data.item.name)).zip }}</span><span style="border: 1px solid black; float: right; border-radius: 4px; padding: 1.5px;min-width:30px; text-align: center; font-family: &quot;Lucida Console&quot;, &quot;Courier New&quot;, monospace; "><b>{{ (splitAddress(data.item.name)).state }}</b></span>
              </v-list-item-title>
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
        if (val) {
          if (val.length >= 5) {
            this.isMinLength = true
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
          // Lazily load input items
          // axios.get('https://api.honely.com/search/suggestions', {
          // if (0) {
          //   this.isLoading = false
          //   // console.log('vx: entries being set to empty')
          //   this.entries = []
          // } else {
          this.isLoading = true
          this.errorMessage = ''
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
          })
            .catch((error) => {
              //  console.warn(error.response.status)
              console.warn(error)
              // console.log('vx: suggestions error case:', error.response.data.timestamp)
              // this.entries = []
              // this.isLoading = false
              // this.errorMessage = 'Sorry. No suggestions found.'
              if (error.response.data.timestamp > this.apiCallTs) {
                this.apiCallTs = error.response.data.timestamp
                this.entries = []
                this.isLoading = false
                if (val.length !== 0) {
                  this.errorMessage = 'Sorry. No suggestions found.'
                }
              }
              // if(this.isLoading) {
              //   this.isLoading = false
              //   if(this.entries.length === 0 && val.length !== 0) {
              //     this.errorMessage = 'Sorry. No suggestions found.'
              //   }
              // }
              // if(this.entries.length === 0 && val.length !== 0) {
              //   this.errorMessage = 'Sorry. No suggestions found.'
              // }
              return null
            })
          // }
        }, 200)
      },
    },

    mounted () {
      if (this.$route.query.address) {
        this.initialLoad = true
        this.entries = [{ name: this.$route.query.address }]
        this.model = { name: this.$route.query.address }
      }
    },

    methods: {
      splitAddress (item) {
        var addrConstituents = item.split(' ')
        var zip = ''
        var state = ''
        var pre = ''
        // var city = ''
        var addr = {}
        if (addrConstituents.length === 1) {
          zip = addrConstituents[0]
        } else {
          // if (this.searchLevel === 'city') {
          //   state = addrConstituents[addrConstituents.length - 1]
          //   city = addrConstituents.slice(0, addrConstituents.length - 1).join(' ')
          //   city = city.substring(0, city.length - 1)
          // } else {
          zip = addrConstituents[addrConstituents.length - 1]
          state = addrConstituents[addrConstituents.length - 2]
          pre = addrConstituents.slice(0, addrConstituents.length - 2).join(' ')
          // }
        }
        addr.pre = pre
        // addr.city = city
        addr.state = state
        addr.zip = zip
        return addr
      },
      onItemChange (item) {
        // console.log('onItemChange being called')
        // this.$router.push({ path: '/listings?address=' + encodeURI(item.name) })
        if (item.name.length >= 5) {
          // this.gotoListings(item.name)
          this.gotoForecast(item.name)
        } else {
          this.errorMessage = 'Search query must contain at least 5 characters'
        }
      },
      onKeyup (event) {
        // console.log('onKeyup being called')
        this.textValue = event.target.value
        if (event.keyCode === 13) {
          if (event.target.value.trim() !== '') {
            // this.$router.push({ path: '/listings?address=' + encodeURI(event.target.value) })
            if (event.target.value.length >= 5) {
              // this.gotoListings(event.target.value)
              this.gotoForecast(event.target.value)
            } else {
              this.errorMessage = 'Search query must contain at least 5 characters'
            }
          }
        }
      },
      onSearchClick (event) {
        console.log('onSearchClick being called')
        if (this.textValue !== '') {
          if (this.textValue.length >= 5) {
            // this.gotoListings(this.textValue)
            this.gotoForecast(this.textValue)
          } else {
            this.errorMessage = 'Search query must contain at least 5 characters'
          }
        }
      },
      // gotoListings (value) {
      //   // console.log('gotoListings being called')
      //   this.isLoading = true
      //   this.$router.push({ name: 'Listings', query: { address: value, offset: 0 } })
      //     .then(() => {
      //       this.isLoading = false
      //     })
      // },
      gotoForecast (value) {
        // this.isLoading = true
        this.errorMessage = ''
        this.$router.push({ name: 'Smart Search', query: { address: value } })
          .then(() => {
            // this.isLoading = false
          })
          // .catch((error) => {
          //   this.isLoading = false
          //   this.errorMessage = error
          // })
      },
    },
  }
</script>

<style lang="sass">
  .suggestions-field
    border: 1px solid $color-emerald
    box-shadow: 1px 1px 14px #666666
    width: 700px
    margin-bottom: 20px !important
    height: 54px

    .v-input__slot
      border-radius: 0px

    > .v-input__append-outer
      margin: 0px !important
      padding: 8px 8px
      padding-top: 15px
      background-color: #06a550
      height: 52px
      width: 54px

      i
        color: white

    fieldset
      border: none

</style>

<style>
.v-list-item--dense .v-list-item__title, .v-list-item--dense .v-list-item__subtitle, .v-list--dense .v-list-item .v-list-item__title, .v-list--dense .v-list-item .v-list-item__subtitle {
  font-size: 17px;
}
/* .v-messages__message {
  color: #e67e23;
} */
</style>
