<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'FAQ',
    metaInfo: { title: 'FAQ' },
    extends: View,
    mixins: [
      LoadSections([
        'html-faq-content',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'FAQ',
      },
    },
  }
</script>
