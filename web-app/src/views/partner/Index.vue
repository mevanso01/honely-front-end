<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'PartnerWithUs',

    metaInfo: { title: 'Partner With Us' },

    extends: View,

    mixins: [
      LoadSections([
        'partner-banner',
        'partner-connect',
        'partner-other',
        'partner-contact-us',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'partner',
      },
    },
  }
</script>
