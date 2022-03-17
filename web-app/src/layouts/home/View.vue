<template>
  <v-main>
    <error-overlay
      :error-message="errorMessage"
      :show-error="showError"
      @hideError="hideError"
    />
    <v-fade-transition mode="out-in">
      <router-view />
    </v-fade-transition>
  </v-main>
</template>
<script>
  import { bus } from '../../main'

  export default {
    name: 'BaseView',

    components: {
      ErrorOverlay: () => import('@/components/ErrorOverlay'),
    },

    data: () => ({
      showError: false,
      errorMessage: '',
    }),

    created () {
      bus.$on('showError', (data) => {
        this.onError(data)
      })
    },

    methods: {
      onError (data) {
        if (data.message) {
          this.errorMessage = data.message
          this.showError = true
        }
      },
      hideError () {
        this.showError = false
        this.errorMessage = ''
      },
    },
  }
</script>
