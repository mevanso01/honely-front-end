<template>
  <v-theme-provider :dark="dark">
    <div>
      <base-info-card
        :title="title"
        color="primary"
      >
        <slot />
      </base-info-card>

      <template v-for="({ icon, text, title: t }, i) in business">
        <base-avatar-card
          :key="i"
          :icon="icon"
          :outlined="false"
          :title="!dense ? t : undefined"
          color="transparent"
          horizontal
          space="0"
        >
          <!-- Do not use v-html for user -->
          <!-- provided values -->
          <!-- link email content only -->
          <div
            v-if="i === 1"
            v-html="'<a href=\'mailto:' + text + '\'>' + text + '</a>'"
          />
          <div
            v-else
            v-html="text"
          />
        </base-avatar-card>

        <v-divider
          v-if="i + 1 !== business.length"
          :key="`divider-${i}`"
          class="my-2"
        />
      </template>
    </div>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'BaseBusinessContact',

    props: {
      dark: Boolean,
      dense: Boolean,
      title: String,
    },

    data: () => ({
      business: [
        {
          icon: 'mdi-map-marker-outline',
          title: 'Address',
          text: '150 East Palmetto Park Road, Suite 800<br>Boca Raton, Florida 33432',
        },
        {
          icon: 'mdi-cellphone',
          title: 'Phone',
          text: '+1 (561) 607-5051',
        },
        {
          icon: 'mdi-email',
          title: 'Email',
          text: 'contact@honely.com',
        },
      ],
    }),
  }
</script>
