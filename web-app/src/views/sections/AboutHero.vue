<template>
  <div class="about-container">
    <div class="section-wrapper flex-wrapper">
      <div class="section-col-left">
        <h1 class="page-sub-title py-md-5">
          We Saw A Need <br> For Deeper Knowledge.
        </h1>
        <p class="sub-content mt-4">
          Honely is a Boca Raton-based company that harnesses the transformative power of AI/ML and predictive analytics to deliver real estate data analysis instantaneously to investors and home buyers alike.
        </p>
        <p
          class="sub-content mt-3"
          v-show="expand"
        >
          Our team consists of a unique blend of dedicated data scientists, machine learning experts, engineers, and real estate professionals. This blend has allowed us to bring a fresh approach to real estate data and provide solutions that were previously unavailable.
        </p>
        <a
          href="javascript:;"
          class="btn-read-more mt-2"
          @click="expand = !expand"
        >
          {{ expand ? 'Read less' : 'Read more' }}
        </a>
      </div>
      <div class="section-col-right">
        <iframe
          width="100%"
          height="293"
          src="https://www.youtube.com/embed/tDcUUcSmgIo?autoplay=0"
          title="YouTube video player"
          frameborder="0"
          allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture"
          autoplay="0"
          allowfullscreen
        />
      </div>
    </div>
  </div>
</template>

<script>
  export default {
    name: 'SectionAboutHero',

    provide: {
      heading: { align: 'center' },
    },

    data: () => ({
      expand: false,
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
