<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'Terms',

    metaInfo: { title: 'Terms of Usage' },

    extends: View,

    mixins: [
      LoadSections([
        'hero-short',
        'terms-of-usage',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'terms',
      },
    },
  }
</script>
