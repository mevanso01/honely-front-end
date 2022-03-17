<template>
  <v-row
    class="cols"
    justify="center"
  >
    <v-col
      v-if="title"
      cols="12"
    >
      <h3>{{ title }}</h3>
    </v-col>

    <v-col
      v-for="(item, i) in items"
      :key="i"
      cols="12"
      md="3"
    >
      <v-card
        style="padding:10px"
        height="100%"
      >
        <v-img
          :src="item.image"
          contain
          width="96%"
          position="left center"
        />

        <v-card-title>{{ item.address }}</v-card-title>

        <v-card-text>
          {{ item.price }}
        </v-card-text>

        <v-card-actions>
          <v-row
            align="center"
            justify="end"
          >
            <v-btn
              color="green lighten-2"
              text
              :to="'/listing/'+item.id"
            >
              See More
            </v-btn>
          </v-row>
        </v-card-actions>
      </v-card>
    </v-col>
  </v-row>
</template>

<script>

  export default {
    props: {
      items: Array,
      title: String,
    },

    data () {
      return {
      }
    },
    computed: {
    },
    created () {
    },
    methods: {
      viewProperty: function (id) {
        console.log('View property: ' + id)
      },
    },
  }
</script>

<style>
  .v-card__title {
    font-size: 1.1rem;
  }
</style>
