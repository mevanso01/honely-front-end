<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'Newsroom',

    metaInfo: { title: 'Newsroom' },

    extends: View,

    mixins: [
      LoadSections([
        'newsRoom',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'newsroom',
      },
    },
  }
</script>
