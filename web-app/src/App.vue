<template>
  <v-fade-transition mode="out-in">
    <div>
      <router-view />
    </div>
  </v-fade-transition>
</template>

<script>
  import CookieLaw from 'vue-cookie-law'
  export default {
    name: 'App',
    components: { CookieLaw },
    metaInfo: {
      title: 'App',
      titleTemplate: '%s | Honely',
      htmlAttrs: { lang: 'en' },
      meta: [
        { charset: 'utf-8' },
        { name: 'viewport', content: 'width=device-width, initial-scale=1' },
      ],
    },
  }
</script>
