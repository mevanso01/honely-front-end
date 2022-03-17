<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'PaymentSuccess',

    metaInfo: { title: 'Payment Success' },

    extends: View,

    mixins: [
      LoadSections([
        'hero-alt',
        'slotPaymentSuccess',
      ]),
    ],

    props: {
      id: {
        type: String,
        default: 'slotPaymentSuccess',
      },
    },
  }
</script>
