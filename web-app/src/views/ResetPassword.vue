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
              <v-toolbar-title>Reset Password</v-toolbar-title>
            </v-app-bar>
            <span class="error--text">{{ regError }}</span>
            <v-form
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

              <v-text-field
                id="password"
                v-model="password"
                :append-icon="show1 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[passwordRules.required, passwordRules.min]"
                label="Password"
                name="password"
                prepend-inner-icon="mdi-key"
                :type="show1 ? 'text' : 'password'"
                hint="At least 12 characters"
                counter
                @click:append="show1 = !show1"
              />

              <v-text-field
                id="passwordAgain"
                v-model="passwordAgain"
                :append-icon="show2 ? 'mdi-eye' : 'mdi-eye-off'"
                :rules="[matchRule]"
                label="Password Again"
                name="passwordAgain"
                prepend-inner-icon="mdi-key"
                :type="show2 ? 'text' : 'password'"
                counter
                @click:append="show2 = !show2"
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
                  Set new password
                </v-btn>
              </v-card-actions>
            </v-form>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </section>
</template>

<script>
  // import axios from 'axios';
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
        console.log(params)
        // axios.post("http://localhost:4000/auth/api/v1/set-password", params)
        //   .then(() => {
        //     console.log('password set');
        //
        //   })
        //   .catch(err => this.regError = err.message)
      },
    },
  }
</script>
