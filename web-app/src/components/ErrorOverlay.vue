<template>
  <v-overlay :value="showError">
    <v-container
      v-click-outside="hideError"
      fluid
    >
      <v-row
        align="center"
        justify="center"
      >
        <v-col
          md="12"
        >
          <v-card
            class="elevation-3 rounded-lg login-form"
            light
            :min-width="$vuetify.breakpoint.xs ? 'auto' : '600px'"
            :min-height="$vuetify.breakpoint.xs ? 'auto' : '100px'"
          >
            <!-- <v-row
              justify="center"
              class="error-title align-center"
              no-gutters
            >
              <v-col
                md="4"
                offset-md="4"
                class="align-center"
              >
                <div
                  class="text-h5 modal-header text-center"
                >
                  Alert!
                </div>
              </v-col>
              <v-col
                md="2"
                offset-md="2"
                class="text-right"
              >
                <v-btn
                  color="grey darken-2"
                  fab
                  icon
                  @click="hideError"
                >
                  <v-icon>
                    mdi-close-circle
                  </v-icon>
                </v-btn>
              </v-col>
            </v-row> -->
            <v-row
              justify="center"
            >
              <v-col
                md="10"
                class="pb-0"
              >
                <v-container
                  class="text-center"
                >
                <v-btn
                  color="grey darken-2"
                  fab
                  icon
                  right="true"
                  :absolute="true"
                  @click="hideError"
                >
                  <v-icon>
                    mdi-close-circle
                  </v-icon>
                </v-btn>
                  <h3>{{ errorMessage }}</h3>
                </v-container>
              </v-col>
            </v-row>
          </v-card>
        </v-col>
      </v-row>
    </v-container>
  </v-overlay>
</template>

<script>
  export default {
    theme: {
      options: { light: true },
    },

    props: {
      errorMessage: String,
      showError: Boolean,
    },

    methods: {
      hideError: function () {
        this.$emit('hideError')
      },
    },
  }
</script>

<style lang="sass">
  .login-form
    .error-title
      padding: 5px 0
      border-bottom: 5px solid orange

    .modal-header
      font-weight: normal
      color: $color-gray-mddk
      padding-top: 15px
      letter-spacing: normal

    p
      margin-bottom: 5px

    .form-links
      padding: 0 14px

    .form-link-col
      padding-top: 0
      margin-bottom: 20px

    .btn-google
      background: url('~@/assets/Google__G__Logo.svg') no-repeat 17px 8px
      background-size: 20px 20px

    .btn-facebook
      background: #3b5998 url('~@/assets/icon-facebook-circle-white.svg') no-repeat 15px 7px
      background-color: #3b5998 !important
      background-size: 23px 23px
      color: #ffffff !important
</style>
