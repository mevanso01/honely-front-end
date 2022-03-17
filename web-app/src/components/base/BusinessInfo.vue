<template>
  <!-- eslint-disable -->
  <div class="section-contact-info">
    <div class="section-heading">Keep In Touch With Us</div>
    <p>Please complete the form or reach out to us using the contact information below and one of our team members will be in touch.</p>
    <div class="contact-info-container">
      <ul class="list-contact">
        <li
          v-for="(item, i) in business"
          :key="i"
        >
          <i class="mdi" :class="item.icon"></i>
          <p v-html="item.text" />
        </li>
      </ul>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'BaseBusinessInfo',
    props: { dark: Boolean },
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
