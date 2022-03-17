<script>
  // Extensions
  import View from '@/views/View'

  // Mixins
  import LoadSections from '@/mixins/load-sections'

  export default {
    name: 'ResetPassword',

    metaInfo: { title: 'Reset Password' },

    extends: View,

    mixins: [
      LoadSections([
        'hero-alt',
        'reset-password-form',
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
