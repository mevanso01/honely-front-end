<template>
  <v-text-field
    v-model="searchValue"
    placeholder="Search Listings"
    rounded
    filled
    prepend-inner-icon="mdi-magnify"
    @keydown.enter="doSearch"
  />
</template>

<script>

  export default {
    data () {
      return {
        searchValue: '',
      }
    },
    created () {
    },
    methods: {
      doSearch: function () {
        if (this.searchValue.trim() !== '') {
          console.log('lets search! ' + this.searchValue)
        }
      },
    },
  }
</script>
