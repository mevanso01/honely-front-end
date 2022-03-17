<template>
  <v-footer
    id="home-footer"
    color="grey darken-4"
    dark
    min-height="72"
  >
    <v-container>
      <v-row>
        <v-col
          cols="12"
          md="6"
        >
          <div class="d-flex flex-wrap justify-md-start justify-center justify-md-none">
            <template v-for="(link, i) in items">
              <router-link
                :key="i"
                :to="link.to"
              >
                {{ link.text }}
              </router-link>

              <v-responsive
                v-if="i < items.length - 1"
                :key="'divider-'+i"
                class="mx-4 shrink hidden-sm-and-down"
                max-height="24"
              >
                <v-divider vertical />
              </v-responsive>
            </template>
          </div>
        </v-col>
      </v-row>
      <v-row>
        <v-col
          class="text-right"
          cols="12"
        >
          Copyright &copy; 2022 Honely
        </v-col>
      </v-row>
    </v-container>
  </v-footer>
</template>

<script>
  export default {
    name: 'Footer',

    data: () => ({
    }),

    computed: {
      items () {
        return [
          {
            to: '/faq',
            text: 'FAQ',
          },
          {
            to: '/contact',
            text: 'Contact',
          },
          {
            to: '/about-us',
            text: 'About Us',
          },
        ]
      },

    },
  }
</script>

<style lang="sass">
  #home-footer a
    text-decoration: none
</style>
