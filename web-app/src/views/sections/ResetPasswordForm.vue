<template>
  <v-container>
    <v-row
      justify="center"
    >
      <v-col
        cols="12"
        lg="4"
        md="6"
      >
        <v-form
          v-if="!resetComplete"
          ref="form"
          v-model="valid"
          class="pa-2"
          :lazy-validation="lazy"
        >
          <p>Email</p>
          <v-text-field
            v-model="email"
            :rules="emailRules"
            placeholder="enter email address"
            outlined
            required
          />
          <p>New Password</p>
          <v-text-field
            id="password"
            v-model="password"
            :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[passwordRules.required, passwordRules.min]"
            placeholder="enter new password"
            name="password"
            prepend-inner-icon="mdi-key"
            :type="show1 ? 'text' : 'password'"
            hint="At least 12 characters"
            outlined
            counter
            @click:append="show1 = !show1"
          />
          <p>New Password Confirmation</p>
          <v-text-field
            id="passwordAgain"
            v-model="passwordAgain"
            :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
            :rules="[matchRule]"
            placeholder="enter new password confirmation"
            name="passwordAgain"
            prepend-inner-icon="mdi-key"
            :type="show2 ? 'text' : 'password'"
            outlined
            counter
            @click:append="show2 = !show2"
          />

          <v-card-actions>
            <v-spacer />

            <v-btn
              :disabled="!valid"
              block
              color="primary"
              @click="validate && submit()"
              @keyup.native.enter="valid && submit($event)"
            >
              Set new password
            </v-btn>
          </v-card-actions>
        </v-form>
        <div v-if="resetComplete">
          <p>Thank you. Your password has been reset.</p>
        </div>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import axios from 'axios'
  import { authMethods } from '@/store/helpers'

  export default {
    name: 'ResetPassword',
    metaInfo: {
      title: 'Reset Password',
    },
    data: () => {
      return {
        lazy: true,
        show1: false,
        show2: false,
        valid: true,
        email: '',
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        password: '',
        passwordAgain: '',
        passwordRules: {
          required: value => !!value || 'Required.',
          min: v => v.length >= 12 || 'Min 12 characters',
        },
        regError: '',
        resetComplete: false,
      }
    },
    computed: {
      matchRule () {
        return v => (!!v && v) === this.password || 'The passwords you entered don\'t match'
      },
    },
    methods: {
      ...authMethods,
      validate () {
        this.$refs.form.validate()
      },
      submit () {
        const params = {
          email: this.email,
          password: this.password,
          resetToken: this.$route.params.token,
        }
        console.log('here')
        axios.post('/auth/api/v1/set-password', params)
          .then(() => {
            console.log('password set')
            this.resetComplete = true
          })
          .catch((err) => { this.regError = err.message })
      },

    },
  }
</script>
