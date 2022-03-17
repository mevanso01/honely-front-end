<template>
  <!-- eslint-disable -->
  <div class="contact-form-section">
    <div class="section-subheading"> Contact us</div>
    <v-form
      v-if="!sent"
      ref="form"
      v-model="valid"
      class="form-contact"
      :lazy-validation="lazy"
    >
      <div class="form-row">
        <label>Name</label>
        <div class="form-input-group">
          <i class="mdi mdi-account"></i>
          <input
            v-model="name"
            :rules="nameRules"
            type="text"
            placeholder="e.g John Doe"
          >
        </div>
        <p
          class="character-count input-msg-default"
          v-text="(name.length)+'/25'"
        />
      </div>
      <div class="form-row">
        <label>Email</label>
        <div class="form-input-group">
          <i class="mdi mdi-email"></i>
          <input
            v-model="email"
            :rules="emailRules"
            type="email"
            placeholder="john.doe@mail.com"
          >
        </div>
        <p
          class="character-count input-msg-default"
          v-text="(email.length)+'/30'"
        />
      </div>
      <div class="form-row">
        <label>Subject</label>
        <div class="form-input-group">
          <i class="mdi mdi-book"></i>
          <input
            v-model="subject"
            :rules="subjectRules"
            type="text"
            placeholder="Subject"
          >
        </div>
        <p
          class="character-count input-msg-default"
          v-text="(subject.length)+'/75'"
        />
      </div>
      <div class="form-row">
        <label>Message</label>
        <textarea
          v-model="message"
          :rules="messageRules"
          rows="5"
          type="text"
          class="form-control message"
          placeholder="Type your message here"
        />
        <p
          class="character-count input-msg-default"
          v-text="(message.length)+'/250'"
        />
      </div>
      <div>
        <button
          class="bg-primary"
          :disabled="!valid"
          @click="sendForm()"
        >
          Submit
        </button>
      </div>
      <div v-if="sent">
        Thank you for your message!
      </div>
    </v-form>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'

  export default {
    name: 'BaseContactForm',
    // Injected from the Vuetify Themeable mixin
    inject: ['theme'],
    props: {
      subtitle: String,
      title: {
        type: String,
        default: 'MAIL US YOUR MESSAGE',
      },
      postUrl: {
        type: String,
        default: 'https://api.honely.com/lookup/contact_us',
      },
    },
    data: () => {
      return {
        lazy: true,
        valid: false,
        sent: false,
        name: '',
        email: '',
        subject: '',
        message: '',
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        nameRules: [
          v => !!v || 'Name is required',
        ],
        subjectRules: [
          v => !!v || 'Subject is required',
        ],
        messageRules: [
          v => !!v || 'Message is required',
        ],
        error: '',
      }
    },
    methods: {
      validate () {
        console.log('validate')
        this.$refs.form.validate()
      },
      sendForm () {
        this.validate()
        var me = this
        var params = {
          name: this.name,
          email: this.email,
          subject: this.subject,
          description: this.message,
        }
        setTimeout(function () {
          if (me.valid) {
            axios.post(me.postUrl, params)
              .then(() => {
                me.sent = true
              })
              .catch((err) => {
                me.error = err.message
              })
          }
        }, 500)
      },
    },
  }
</script>
