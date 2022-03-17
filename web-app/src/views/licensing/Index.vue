<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'Terms',

    metaInfo: { title: 'Licensing Agreement' },

    extends: View,

    mixins: [
      LoadSections([
        'hero-short',
        'licensing',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'licensing',
      },
    },
  }
</script>
