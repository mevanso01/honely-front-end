<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'About',
    metaInfo: { title: 'About Us' },
    extends: View,
    mixins: [
      LoadSections([
        'about-hero',
        'meet-team',
        'about-info',
        'icon-band',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'about',
      },
    },
  }
</script>
