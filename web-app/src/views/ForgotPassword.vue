<template>
  <section
    id="register"
    ref="register"
  >
    <v-container fluid>
      <v-row
        class="text-center"
        align="center"
      >
        <v-col class="align-content-start">
          <v-card
            outlined
            max-width="600"
            class="mx-auto"
          >
            <v-app-bar
              dark
              color="primary"
            >
              <v-icon>mdi-account</v-icon>
              <v-toolbar-title>Forgot Password</v-toolbar-title>
            </v-app-bar>
            <span class="error--text">{{ regError }}</span>
            <v-form
              v-if="!requestSent"
              ref="form"
              v-model="valid"
              class="pa-6"
              :lazy-validation="lazy"
            >
              <v-text-field
                v-model="email"
                :rules="emailRules"
                prepend-inner-icon="mdi-email"
                label="E-mail"
                required
              />

              <v-card-actions>
                <v-spacer />

                <v-btn
                  :disabled="!valid"
                  color="primary"
                  class="mr-4"
                  @click="validate && submit()"
                  @keyup.native.enter="valid && submit($event)"
                >
                  Request Password Reset
                </v-btn>
              </v-card-actions>
            </v-form>
            <v-container v-if="requestSent">
              Your reset password request has been received, please check your email for further instructions.
            </v-container>
          </v-card>
        </v-col>
      </v-row>
      <v-row
        align="center"
        justify="center"
      >
        <router-link
          to="/login"
          style="font-weight: bold"
        >
          Back to sign in
        </router-link>
      </v-row>
    </v-container>
  </section>
</template>

<script>
  // import axios from 'axios';
  import { authMethods } from '@/store/helpers'
  export default {
    name: 'ForgotPassword',
    metaInfo: {
      title: 'Forgot Password',
    },
    data: () => {
      return {
        lazy: true,
        requestSent: false,
        show1: false,
        show2: false,
        valid: true,
        email: '',
        emailRules: [
          v => !!v || 'E-mail is required',
          v => /.+@.+\..+/.test(v) || 'E-mail must be valid',
        ],
        regError: '',
      }
    },
    methods: {
      ...authMethods,
      validate () {
        this.$refs.form.validate()
      },
      submit () {
        const params = {
          email: this.email,
        }
        console.log(params)
        // axios.post("http://localhost:4000/auth/api/v1/reset-password", params)
        //   .then(() => {
        //     this.regError = "";
        //     this.requestSent = true;
        //
        //   })
        //   .catch(err => this.regError = err.message)
      },

    },
  }
</script>
