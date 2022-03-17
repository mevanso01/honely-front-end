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
            <input type="text" name="name" id="partner-form-name" placeholder="Your name">
          </div>
        </div>
        <div class="form-row">
          <label>Email</label>
          <div class="form-input-group">
            <i class="mdi mdi-email"></i>
            <input type="text" name="name" id="partner-form-name" placeholder="Your email">
          </div>
        </div>
        <div class="form-row">
          <label>Phone number</label>
          <div class="form-input-group">
            <i class="mdi mdi-phone"></i>
            <input type="text" name="name" id="partner-form-name" placeholder="Phone nunmber">
          </div>
        </div>
        <div class="form-row">
          <label>Message</label>
          <textarea placeholder="Type your message here"></textarea>
        </div>
        <div>
          <button class="bg-primary">Submit</button>
        </div>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>
<script>
  export default {
    name: 'SectionPartnerContactUs',
    data: () => ({
      form: {
        name: '',
        email: '',
        phone_number: '',
        message: '',
      },
    }),
  }
</script>
