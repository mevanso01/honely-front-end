<script>
  // Extensions
  import View from '@/views/View'
  // Mixins
  import LoadSections from '@/mixins/load-sections'
  export default {
    name: 'Subscribe',
    metaInfo: { title: 'Subscribe' },
    extends: View,
    mixins: [
      LoadSections([
        'hero-alt',
        'subscribe',
      ]),
    ],
    props: {
      id: {
        type: String,
        default: 'subscribe',
      },
    },
  }
</script>
