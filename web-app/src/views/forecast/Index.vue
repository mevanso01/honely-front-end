<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'SmartSearch',

    metaInfo: { title: 'Smart Search' },

    extends: View,

    mixins: [
      LoadSections([
        'Forecast',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'forecast',
      },
    },
  }
</script>
