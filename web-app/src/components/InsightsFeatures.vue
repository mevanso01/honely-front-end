<template>
  <v-theme-provider :dark="dark">
    <ul class="insights-features mt-20">
      <li
        class="feature"
        v-for="(item) in filteredItems"
        :key="item.id"
      >
        <router-link :to="'/insights' + item.path">
          {{ convertFirstLetterEachWordToUpperCase(item.title) }}
        </router-link>
        <span class="blogdate">{{ item.post_date | moment("M/D/YY") }}</span>
      </li>
    </ul>
  </v-theme-provider>
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'SectionInsightsFeatures',

    data: () => ({
      dark: true,
    }),
    computed: {
      ...mapGetters('insights', {
        posts: 'footerPosts',
      }),
      filteredItems () {
        var filteredItems = []
        for (let i = 0; i < this.posts.length; i++) {
          var filteredItem = this.posts[i]
          filteredItem.truncatedBody = this.posts[i].body.substring(this.posts[i].body.indexOf('<p>') + 3, this.posts[i].body.indexOf('</p>')) + '..'
          filteredItem.image = this.posts[i].image_filename
          var year = new Date(filteredItem.post_date).getFullYear()
          var month = new Date(filteredItem.post_date).getMonth()
          var date = new Date(filteredItem.post_date).getDate()
          filteredItem.path = `/${year}/${month}/${date}/${filteredItem.slug}/`
          filteredItems.push(filteredItem)
        }
        return filteredItems
      },
    },
    created () {
      this.$store.dispatch('insights/getFooterPosts', { limit: 5, offset: 0 })
    },
    methods: {
      convertFirstLetterEachWordToUpperCase (value) {
        var words = value.split(' ')
        return (words.map((word) => { return word[0].toUpperCase() + word.substring(1) }).join(' '))
      },
    },
  }
</script>
<style>
  ul.insights-features {
      list-style: none;
      overflow: visible;
      margin-left:-22px;
  }
  ul.insights-features li {
      overflow: visible;
      position: relative;
      padding-bottom: 20px;
      font-size: 14px;
      line-height: 17px;
      padding-right: 30px;
  }
  ul.insights-features li a {
    color: darkgrey;
  }
  ul.insights-features li a:hover {
    color: #ffffff;
  }
  .feature{
    display: flex;
    flex-direction: column;

  }
  .blogdate {
    margin-top: 5px;
    width: 65px;
    color: #777777 !important;
  }

</style>
