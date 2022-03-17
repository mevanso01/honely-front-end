<template>
  <div class="main">
    <v-fade-transition mode="out-in">
      <div class="main-container">
        <router-view />
      </div>
    </v-fade-transition>
  </div>
</template>
<script>
  import { bus } from '../../main'

  export default {
    name: 'BaseView',
    components: {
      // TO DO
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
