<template>
  <!-- eslint-disable -->
  <div class="pg-insights-post">
    <page-loader :loading="loading" />
    <!-- post-container -->
    <div v-if="firstPost" class="post-container">
      <div class="post-hero pg-insights-hero" v-bind:style="'background-image: url(' + getPostImg(firstPost.image) + ')'">
        <div class="pg-insights-hero-overlay"></div>
      </div>
      <div class="post-content section-wrapper">
        <div class="post-body-wrapper">
          <div class="post-body" v-html="firstPost.body">
          </div>
          <div class="post-date-wrapper">
            <p class="post-date">Posted: <span class="mdi mdi-calendar-month-outline"></span> {{ getPostDate(firstPost.post_date) }}</p>
          </div>
        </div>
        <div class="post-options">
          <span class="mdi mdi-printer" @click="printPost"></span>
          <a :href="`https://www.linkedin.com/shareArticle?mini=true&url=${pageUrl}`" target="_blank">
            <span class="mdi mdi-linkedin"></span>
          </a>
          <a :href="`https://www.facebook.com/sharer/sharer.php?u=${pageUrl}`" target="_blank">
            <span class="mdi mdi-facebook"></span>
          </a>
          <a :href="`https://wa.me/?text=Honely%20Insight%20${pageUrl}`" target="_blank">
            <span class="mdi mdi-whatsapp"></span>
          </a>
          <a :href="`mailto:?subject=Honely%20Insight&amp;body=${pageUrl}`" target="_blank">
            <span class="mdi mdi-email-outline"></span>
          </a>
        </div>
      </div>
    </div>
    <!-- /post-container -->

    <!-- post-container -->
    <div class="posts-container section-wrapper">
      <div class="section-heading">
        Recent Posts
      </div>
      <div v-if="filteredPosts && filteredPosts.length > 0" class="posts-wrapper">
        <insight-block
          v-for="post in filteredPosts"
          :post="post"
          :featured="false"
          :hideImage="false"
          :hideDescription="true"
          :key="post.id"
        />
      </div>
    </div>
    <!-- /posts-container -->
  </div>
  <!-- /eslint-enable -->
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'SectionInsights',
    components: {
      PageLoader: () => import('@/components/PageLoader'),
      InsightBlock: () => import('@/components/InsightBlock'),
    },
    metaInfo () {
      return {
        title: this.title,
      }
    },
    data: () => ({
      year: null,
      month: null,
      day: null,
      slug: null,
      posts: [],
      loading: false,
    }),
    computed: {
      /* ...mapGetters('insights', {
        posts: 'targetPosts',
      }), */
      firstPost () {
        const post = this.updatedPost(this.posts[0])
        return post
      },
      filteredPosts () {
        var filteredPosts = []
        for (let i = 1; i < this.posts.length; i++) {
          var filteredPost = this.updatedPost(this.posts[i])
          filteredPosts.push(filteredPost)
        }
        return filteredPosts
      },
    },
    watch: {
      $route (to, from) {
        this.year = this.$route.params.year
        this.month = this.$route.params.month
        this.day = this.$route.params.day
        this.slug = this.$route.params.slug
        this.load()
      },
    },
    created () {
      this.year = this.$route.params.year
      this.month = this.$route.params.month
      this.day = this.$route.params.day
      this.slug = this.$route.params.slug
      this.pageUrl = 'https://honely.com/insights/' + this.year + '/' + this.month + '/' + this.day + '/' + this.slug
      this.load()
    },
    methods: {
      load () {
        // console.log(this.posts)
        this.getPosts()
      },
      getPosts () {
        this.loading = true
        if (this.year && this.month && this.day && this.slug) {
          axios.get('https://api.honely.com/lookup/blog_posts_finder?slug=' + this.slug, {
          }).then((response) => {
            // console.log(response.data)
            this.posts = response.data
            this.loading = false
          })
            .catch((error) => {
              console.log(error)
              this.loading = false
            })
        }
        this.$store.dispatch('insights/getTargetPost', { slug: this.slug })
      },
      getPost (index) {
        let post = null
        if (this.posts.length > 0 && index < this.posts.length) {
          post = this.updatedPost(this.posts[index])
        }
        return post
      },
      updatedPost (post) {
        if (post) {
          var truncatedBody = post.body ? post.body.substring(post.body.indexOf('<p>') + 3, post.body.indexOf('</p>')) + '..' : ''
          if (truncatedBody.length > 250) {
            truncatedBody = truncatedBody.substring(0, 250) + '...'
          }
          post.truncatedBody = truncatedBody
          post.image = post.image_filename
          var year = new Date(post.post_date).getFullYear()
          var month = new Date(post.post_date).getMonth()
          var date = new Date(post.post_date).getDate()
          post.path = `/${year}/${month}/${date}/${post.slug}/`
        }
        return post
      },
      getPostImg (image) {
        if (image) {
          return 'https://honely-files-public.s3.amazonaws.com/posts' + image
        } else {
          return ''
        }
      },
      getPostDate (dateStr) {
        if (dateStr) {
          const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec']
          const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
          const postDate = new Date(dateStr)
          return days[postDate.getDay()] + ', ' + postDate.getDate() + ' ' + months[postDate.getMonth()] + ' ' + postDate.getFullYear()
        } else {
          return '--'
        }
      },
      getPostLink (path) {
        if (path) {
          return '/insights' + path
        } else {
          return '#'
        }
      },
      printPost: function (event) {
        window.print()
      },
    },
  }
</script>
