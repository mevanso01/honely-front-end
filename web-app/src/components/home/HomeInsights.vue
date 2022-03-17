<template>
  <!-- eslint-disable -->
  <div class="section">
    <div class="home-insights-container">
      <p class="feature-text text-exlarge">Our Latest Insights</p>
      <p class="feature-text text-small">Our curated blog has all the content you need to start investing in real estate like a pro, making lives easier for buyers, sellers and everyone in between.</p>
      <!-- first posts -->
      <div v-if="firstPost" class="home-insights-post-featured">
        <insight-block
          :post="firstPost"
          :featured="true"
          :hideImage="false"
          :hideDescripton="false"
        />
      </div>
      <!-- /first posts -->

      <!-- first posts -->
      <div v-if="filteredPosts" class="home-insights-posts">
        <insight-block
          v-for="post in filteredPosts"
          :post="post"
          :featured="false"
          :hideImage="false"
          :hideDescripton="false"
          :key="post.id"
        />
      </div>
      <!-- /first posts -->
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'HomeInsights',
    components: {
      InsightBlock: () => import('@/components/InsightBlock'),
    },
    computed: {
      ...mapGetters('insights', {
        posts: 'allPosts',
      }),
      firstPost () {
        var firstPost = this.posts.length > 0 ? this.posts[0] : null
        if (firstPost !== null) {
          var truncatedBody = firstPost.body ? firstPost.body.substring(firstPost.body.indexOf('<p>') + 3, firstPost.body.indexOf('</p>')) + '..' : ''
          if (truncatedBody.length > 250) {
            truncatedBody = truncatedBody.substring(0, 250) + '...'
          }
          firstPost.truncatedBody = truncatedBody
          firstPost.image = firstPost.image_filename
          var year = new Date(firstPost.post_date).getFullYear()
          var month = new Date(firstPost.post_date).getMonth()
          var date = new Date(firstPost.post_date).getDate()
          firstPost.path = `/${year}/${month}/${date}/${firstPost.slug}/`
        }
        return firstPost
      },
      filteredPosts () {
        var filteredPosts = []
        for (let i = 1; i < this.posts.length; i++) {
          var filteredPost = this.posts[i]
          filteredPost.truncatedBody = this.posts[i].body.substring(this.posts[i].body.indexOf('<p>') + 3, this.posts[i].body.indexOf('</p>')) + '..'
          if (filteredPost.truncatedBody.length > 250) {
            filteredPost.truncatedBody = filteredPost.truncatedBody.substring(0, 250) + '...'
          }
          filteredPost.image = this.posts[i].image_filename
          var year = new Date(filteredPost.post_date).getFullYear()
          var month = new Date(filteredPost.post_date).getMonth()
          var date = new Date(filteredPost.post_date).getDate()
          filteredPost.path = `/${year}/${month}/${date}/${filteredPost.slug}/`
          filteredPosts.push(filteredPost)
        }
        return filteredPosts
      },
    },
    created () {
      this.$store.dispatch('insights/getPosts', { all: 'no' })
    },
  }
</script>
