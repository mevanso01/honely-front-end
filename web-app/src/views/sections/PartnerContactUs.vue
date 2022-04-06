<template>
  <!-- eslint-disable -->
  <div class="partner-contact-section" id="partner-contact-section">
    <div class="col-left">
      <p class="feature-text">
        We’d Love To
        <br>
        Hear From You
      </p>
    </div>
    <div class="col-right">
      <div class="partner-contact-form">
        <p>Contact Us</p>
        <div class="form-row">
          <label>Name</label>
          <div class="form-input-group">
            <i class="mdi mdi-account"></i>
            <input v-model="form.name" type="text" name="name" id="partner-form-name" placeholder="Your name">
          </div>
        </div>
        <div class="form-row">
          <label>Email</label>
          <div class="form-input-group">
            <i class="mdi mdi-email"></i>
            <input v-model="form.email" type="text" name="email" id="partner-form-name" placeholder="Your email">
          </div>
        </div>
        <div class="form-row">
          <label>Phone number</label>
          <div class="form-input-group">
            <i class="mdi mdi-phone"></i>
            <input v-model="form.phone" type="text" name="phone" id="partner-form-name" placeholder="Phone number">
          </div>
        </div>
        <div class="form-row">
          <label>Message</label>
          <textarea v-model="form.message" placeholder="Type your message here"></textarea>
        </div>
        <div>
          <button class="bg-primary" @click="submitForm">Submit</button>
        </div>
        <p style="margin: 10px; color: red;" >{{ errorMessage }}</p>
        <p style="margin: 10px; color: green;" >{{ successMessage }}</p>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>
<script>
  import axios from 'axios'
  export default {
    name: 'SectionPartnerContactUs',
    data: () => ({
      errorMessage: '',
      successMessage: '',
      form: {
        name: '',
        email: '',
        phone: '',
        message: '',
      },
    }),
    methods: {
      submitForm () {
        if (this.form.name === '' || this.form.email === '' || this.form.phone === '' || this.form.message === '') {
          this.errorMessage = 'Please fill out all fields'
        } else {
          axios.post('https://api.honely.com/lookup-test/contact_form', this.form)
            .then(() => {
              this.errorMessage = ''
              this.successMessage = 'Submitted Successfully!'
            })
        }
      },
    },
  }
</script>
