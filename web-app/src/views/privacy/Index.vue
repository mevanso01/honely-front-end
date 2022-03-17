<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'Privacy',

    metaInfo: { title: 'Privacy Policy' },

    extends: View,

    mixins: [
      LoadSections([
        'hero-short',
        'html-privacy-content',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'privacy',
      },
    },
  }
</script>
