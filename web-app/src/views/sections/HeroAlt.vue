<template>
  <div>
    <!-- /INSIGHTS HEADING -->
  </div>
</template>

<script>
  /* eslint-disable */
  export default {
    name: 'SectionHeroAlt',

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

<style lang="sass">
#hero-alt
  height: 300px
  margin-top: 6px
  font-family: $heading-font-family

  .page-subheader
    padding: 0
    font-family: $body-font-family
    font-size: 20px
    color: white
    max-width: 800px
    height: auto

  .header-container
    max-width: 1185px

</style>

<style lang="css">
.aboutPageSection {
  height: 425 !important;
}
.aboutPage {
  height: 300px !important;
}
@media (max-width: 1264px) {
  #hero-alt .header-container {
    max-width: 900px;
  }
}
@media (max-width: 960px) {
  #hero-alt {
    height: auto;
  }
}
@media (max-width: 600px) {
  #hero-alt .page-subheader {
    font-size: 18px;
    line-height: 25px;
    text-shadow: 1px 1px 3px #000000;
  }
  .aboutPage {
  height: auto !important;
}
}
</style>
