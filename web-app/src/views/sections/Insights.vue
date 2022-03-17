<template>
  <!-- eslint-disable -->
  <div class="pg-insights">
    <!-- hero -->
    <div v-if="firstPost" class="pg-insights-hero" v-bind:style="'background-image: url(' + getPostImg(firstPost.image) + ')'">
      <div class="pg-insights-hero-overlay">
        <div class="section-wrapper">
          <div class="post-info">
            <p class="post-date"><span class="mdi mdi-calendar-month-outline"></span> {{ getPostDate(firstPost.post_date) }}</p>
            <p class="post-title">{{ firstPost.title }}</p>
            <p class="post-description">{{ firstPost.truncatedBody }}</p>
            <div class="post-link">
              <a :href="getPostLink(firstPost.path)" class="btn-links">Read More</a>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- /hero -->

    <!-- insights-posts-container -->
    <div class="insights-posts-container section-wrapper">
      <div class="col-left">
        <div v-if="secondPost" class="insights-feature-post">
          <insight-block
            :post="secondPost"
            :featured="false"
            :hideImage="false"
            :hideDescripton="false"
          />
        </div>
        <div class="insights-feature-posts">
          <insight-block
            :post="thirdPost"
            :featured="false"
          />
          <insight-block
            :post="fourthPost"
            :featured="false"
            :hideImage="false"
            :hideDescripton="false"
          />
        </div>
      </div>
      <div class="col-right">
        <div v-if="filteredPosts" class="page-insights-posts">
          <insight-block
            v-for="post in filteredPosts"
            :post="post"
            :featured="false"
            :hideImage="true"
            :hideDescripton="false"
            :key="post.id"
          />
        </div>
      </div>
    </div>
    <!-- /insights-posts-container -->
  </div>
  <!-- /eslint-enable -->
</template>

<script>
  import { mapGetters } from 'vuex'

  export default {
    name: 'PageInsights',
    components: {
      InsightBlock: () => import('@/components/InsightBlock'),
    },
    data () {
      return {
        page: 0,
      }
    },
    computed: {
      ...mapGetters('insights', {
        posts: 'allPosts',
        total: 'totalPosts',
      }),
      limit () {
        if (this.page === 1) {
          return 10
        } else {
          return 9
        }
      },
      offset () {
        if (this.page === 1) {
          return 0
        } else {
          return 10 + 9 * (this.page - 2)
        }
      },
      firstPost () {
        const post = this.updatedPost(this.posts[0])
        return post
      },
      secondPost () {
        const post = this.updatedPost(this.posts[1])
        return post
      },
      thirdPost () {
        const post = this.updatedPost(this.posts[2])
        return post
      },
      fourthPost () {
        const post = this.updatedPost(this.posts[3])
        return post
      },
      filteredPosts () {
        var filteredPosts = []
        for (let i = 4; i < this.posts.length; i++) {
          var filteredPost = this.updatedPost(this.posts[i])
          filteredPosts.push(filteredPost)
        }
        return filteredPosts
      },
      totalPages () {
        if (this.total - 10 > 0) {
          return Math.ceil(1 + (this.total - 10) / 9)
        } else {
          return 0
        }
      },
    },
    created () {
      this.load()
    },
    mounted () {
    },
    methods: {
      load () {
        this.$store.dispatch('insights/getPosts', { all: 'yes' })
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
      onPageChange () {
        this.load()
      },
      convertFirstLetterEachWordToUpperCase (value) {
        var words = value.split(' ')
        return words
          .map((word) => {
            return word[0].toUpperCase() + word.substring(1)
          })
          .join(' ')
      },
      expandPosts () {
        if (this.postsDisplayed >= this.posts.length) {
          document.getElementById('chevron').style.display = 'none'
        } else {
          this.postsDisplayed = this.postsDisplayed + 9
        }
      },
    },
  }
</script>
