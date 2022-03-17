<template>
  <!-- eslint-disable -->
  <div v-if="post" class="post-container" :class="{feature : featured}">
    <div v-if="!hideImage" class="post-image" :style="{'background-image': 'url(' + getPostImg + ')'}"></div>
    <div class="post-info">
      <p class="post-date"><span class="mdi mdi-calendar-month-outline"></span> {{ getPostDate }}</p>
      <p class="post-title">{{ post.title }}</p>
      <p v-if="!hideDescription" class="post-description">{{ post.truncatedBody }}</p>
      <div class="post-link">
        <a :href="getPostLink" class="btn-links">Read More</a>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'InsightBlock',
    props: {
      post: Object,
      featured: Boolean,
      hideImage: Boolean,
      hideDescription: Boolean,
    },
    data () {
      return {
      }
    },
    computed: {
      getPostImg () {
        if (this.post) {
          if (this.post.image) {
            return 'https://honely-files-public.s3.amazonaws.com/posts' + this.post.image
          } else {
            return ''
          }
        } else {
          return ''
        }
      },
      getPostDate () {
        if (this.post) {
          if (this.post.post_date) {
            const months = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'June', 'July', 'Aug', 'Sept', 'Oct', 'Nov', 'Dec']
            const days = ['Mon', 'Tue', 'Wed', 'Thu', 'Fri', 'Sat', 'Sun']
            const postDate = new Date(this.post.post_date)
            return days[postDate.getDay()] + ', ' + postDate.getDate() + ' ' + months[postDate.getMonth()] + ' ' + postDate.getFullYear()
          } else {
            return '--'
          }
        } else {
          return '--'
        }
      },
      getPostLink () {
        if (this.post) {
          if (this.post.path) {
            return '/insights' + this.post.path
          } else {
            return '#'
          }
        } else {
          return '#'
        }
      },
    },
    updated () {
    },
    mounted () {
    },
    methods: {
    },
  }
</script>
