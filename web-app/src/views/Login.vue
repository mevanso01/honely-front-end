<template>
  <v-main>
    <v-container
      class="fill-height"
      fluid
    >
      <v-row
        align="center"
        justify="center"
      >
        <v-col
          cols="12"
          sm="8"
          md="4"
        >
          <v-card class="elevation-3">
            <v-toolbar
              color="primary"
              dark
              flat
            >
              <v-icon>mdi-account</v-icon>
              <v-toolbar-title>Log In</v-toolbar-title>
            </v-toolbar>
            <v-card-text>
              <v-form>
                <v-text-field
                  v-model="username"
                  label="E-mail"
                  name="login"
                  prepend-icon="mdi-email"
                  type="text"
                />

                <v-text-field
                  id="password"
                  v-model="password"
                  label="Password"
                  name="password"
                  prepend-icon="mdi-key"
                  type="password"
                />
              </v-form>
              <span class="error--text">{{ authError }}</span>
            </v-card-text>
            <v-card-actions>
              <v-spacer />
              <v-btn
                color="primary"
                @click="tryToLogIn"
                @keyup.native.enter="tryToLogIn"
              >
                Login
              </v-btn>
            </v-card-actions>
            <p style="text-align: center">
              <router-link to="/forgot-password">
                Forgot password?
              </router-link>
            </p>
          </v-card>
        </v-col>
      </v-row>
      <v-row
        align="center"
        justify="center"
      >
        <p>
          Don't have an account? <router-link
            to="/register"
            style="font-weight: bold"
          >
            Create one here
          </router-link>
        </p>
      </v-row>
    </v-container>
  </v-main>
</template>

<script>
  // import { mapActions } from 'vuex'
  import { authMethods } from '@/store/helpers'

  export default {
    name: 'Home',
    metaInfo: {
      title: 'Home',
    },
    data: () => ({
      username: '',
      password: '',
      authError: null,
      tryingToLogIn: false,
    }),
    methods: {
      ...authMethods,
      // ...mapActions('users', ['fetchUser']),
      // try to log the user in
      tryToLogIn () {
        console.log('username: ' + this.username)
        console.log('password: ' + this.password)
        this.tryingToLogIn = true
        // Reset authError if it existed
        this.authError = null
        // return this.logIn({
        //   username: this.username,
        //   password: this.password,
        // })
        //   .then((user) => {
        //     console.log('got user: ');
        //     console.dir(user);
        //     this.tryingToLogin = false
        //     //this.fetchUser({ username: this.username}).then((user) => {
        //     //  console.log('got user: ' + user);
        //     //})
        //     //.catch((error) => {
        //     //  console.log("fetch error: " + error);
        //     //});
        //
        //     // Redirect to the originally requested page, or to the home page
        //     this.$oldRouter.push(this.$route.query.redirectFrom || { name: 'Home' })
        //   })
        //   .catch((error) => {
        //     this.tryingToLogIn = false
        //     //todo catch 401 not authenticated
        //     this.authError = error
        //   })
      },
    },
  }
</script>
