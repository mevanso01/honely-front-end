<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'CookiePolicy',

    metaInfo: { title: 'Cookie Policy' },

    extends: View,

    mixins: [
      LoadSections([
        'cookiepolicy',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'cookiepolicy',
      },
    },
  }
</script>
