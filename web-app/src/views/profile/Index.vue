<template>
  <v-container>
    <v-row
      justify="start"
      class="border-me-not"
    >
      <v-col
        cols="12"
      >
        <v-card>
          <span class="error--text">{{ regError }}</span>
          <v-card-title>User Information</v-card-title>
          <v-card-text>
            <v-form
              ref="form"
              v-model="valid"
            >
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.first_name"
                    label="First Name"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.last_name"
                    label="Last Name"
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.cell_phone"
                    label="Cell Phone"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.work_phone"
                    label="Work Phone"
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.address_1"
                    label="Address 1"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.address_2"
                    label="Address 2"
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.city"
                    label="City"
                  />
                </v-col>

                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.state"
                    label="State"
                  />
                </v-col>
              </v-row>
              <v-row>
                <v-col
                  cols="12"
                  md="6"
                >
                  <v-text-field
                    v-model="user.zip"
                    label="Zip"
                  />
                </v-col>
              </v-row>

              <div class="d-flex">
                <v-spacer />
                <v-btn
                  color="primary"
                  :disabled="!valid"
                  @click="submitInformationUpdate()"
                >
                  Save
                </v-btn>
              </div>
            </v-form>
          </v-card-text>
        </v-card>
        <v-card>
          <v-card-title>Change Password</v-card-title>
          <v-card-text>
            <v-row>
              <v-col
                cols="12"
                md="6"
              >
                <v-text-field
                  v-model="currentPassword"
                  label="Current Password"
                  type="password"
                />
              </v-col>
            </v-row>
            <v-row>
              <v-col
                cols="12"
                md="6"
              >
                <v-text-field
                  v-model="newPassword"
                  label="New Password"
                  type="password"
                />
              </v-col>
              <v-col
                cols="12"
                md="6"
              >
                <v-text-field
                  v-model="newPasswordConfirmation"
                  label="New Password Confirmation"
                  type="password"
                />
              </v-col>
            </v-row>

            <div class="d-flex">
              <v-spacer />
              <v-btn
                color="primary"
                @click="submitPasswordChange()"
              >
                Save
              </v-btn>
            </div>
          </v-card-text>
        </v-card>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import axios from 'axios'
  import {
    mapGetters, mapState,
  } from 'vuex'

  export default {
    data: () => ({
      user: {},
      userFirstName: '',
      userLastName: '',
      currentPassword: '',
      newPassword: '',
      newPasswordConfirmation: '',
      valid: false,
      regError: '',
      firstNameRules: [
        v => !!v || 'First name is required',
      ],
      lastNameRules: [
        v => !!v || 'Last name is required',
      ],
    }),
    computed: {
      ...mapState('auth', ['currentUser']),
      ...mapGetters('auth', ['loggedIn', 'username', 'firstName', 'lastName']),
    },

    created () {
      this.load()
    },
    methods: {
      load () {
        axios.get('/api/v1/user',
                  {
                    headers: {
                      Authorization: 'Bearer ' + this.currentUser,
                    },
                  })
          .then((response) => {
            console.log(response)
            this.user = response.data
            this.validate()
          })
          .catch(err => {
            console.log(err)
            // this.regError = err.response.data
          })
      },
      submitInformationUpdate () {
        // const params = {
        //   firstName: this.userFirstName,
        //   lastName: this.userLastName,
        // }

        axios.put('/api/v1/user', this.user,
                  {
                    headers: {
                      Authorization: 'Bearer ' + this.currentUser,
                    },
                  })
          .then(() => {

          })
          .catch(err => {
            console.log(err)
            this.regError = err.response.data
          })
      },
      submitPasswordChange () {
        console.log('password change')
      },
      validate () {
        this.$refs.form.validate()
      },
    },
  }
</script>
