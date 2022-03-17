<template>
  <!-- eslint-disable -->
  <div class="page-banner-section partner">
    <div class="page-banner-copy">
      <h1 class="page-title">Partner With Us</h1>
        <p class="page-hero-text">We Provide solutions for the entire real estate ecosystem. Whether its through predictive analytics, APIs, or leads, partnering with Honely can help elevate your business</p>
        <a
          href="#partner_contact_us"
          class="btn-links bg-primary"
          @click="scrollToContact"
        >
          Contact Us
        </a>
    </div>
  </div>
  <!-- eslint-enable -->
</template>
<script>
  export default {
    name: 'SectionPartnerBanner',
    data: () => ({}),
    methods: {
      scrollToContact (e) {
        e.preventDefault()
        const offsetTop = document.querySelector('#partner-contact-section').offsetTop
        scroll({
          top: offsetTop,
          behavior: 'smooth',
        })
      },
    },
  }
</script>
