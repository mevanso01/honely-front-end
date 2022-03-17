<template>
  <v-app class="content">
    <site-header />
    <site-view />
    <site-footer />
  </v-app>
</template>

<script>
  export default {
    name: 'MainLayout',
    components: {
      SiteHeader: () => import('@/layouts/main/Header'),
      SiteFooter: () => import('@/layouts/main/Footer'),
      SiteView: () => import('@/layouts/main/View'),
    },
  }
</script>
