<template>
  <v-theme-provider dark>
    <div class="faq-banner">
      <base-img
        :height="$vuetify.breakpoint.mdAndUp ? 225 : 225"
        gradient="to bottom, rgba(0,0,0,.2), rgba(0,0,0,.8)"
        :src="src"
        color="#45516b"
        flat
        max-width="100%"
        tile
      >
        <div
          v-if="title"
          class="flex-wrapper"
        >
          <base-heading
            :title="title"
            align="left"
            space="2"
            weight="500"
            class="mb-5"
          />
        </div>
      </base-img>
    </div>
  </v-theme-provider>
</template>

<script>

  export default {
    name: 'SectionHeroShort',

    provide: {
      heading: { align: 'center' },
    },

    data: () => ({
    }),

    computed: {
      title () {
        return this.$route.name
      },
      src () {
        return this.$route.meta.src
      },
      copy () {
        return this.$route.meta.copy
      },
    },
  }
</script>
