<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'PaymentFailed',

    metaInfo: { title: 'Payment Failed' },

    extends: View,

    mixins: [
      LoadSections([
        'hero-alt',
        'paymentFailed',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'paymentFailed',
      },
    },
  }
</script>
