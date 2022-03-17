<script>
  // Extensions
  import View from '@/views/View'
  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'Disclaimer',
    metaInfo: { title: 'Disclaimer' },
    extends: View,
    mixins: [
      LoadSections([
        'html-disclaimer-content',
      ]),
    ],
    props: {
      id: {
        type: String,
        default: 'disclaimer',
      },
    },
  }
</script>
