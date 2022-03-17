<script>
  import View from '@/views/View'
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'Insights',
    metaInfo: { title: 'Insights' },
    extends: View,
    mixins: [
      LoadSections([
        'insights',
      ]),
    ],
    props: {
      id: {
        type: String,
        default: 'insights',
      },
    },
  }
</script>
