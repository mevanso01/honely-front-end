<template>
  <v-container>
    <v-row
      justify="center"
      class="border-me-not"
    >
      <v-col
        class="intro-text"
        cols="12"
        sm="8"
        md="6"
      >
        <h1
          class="mb-1"
        >
          Edit Listing
        </h1>
      </v-col>
    </v-row>

    <v-row
      justify="center"
      class="border-me-not"
    >
      <v-col
        cols="10"
        sm="8"
        md="6"
      >
        <v-card
          class="pa-4"
        >
          <v-form>
            <p>Description</p>
            <v-textarea
              v-model="sponsoredListing.description"
              row="5"
              outlined
            />

            <p>Upload Files</p>
            <v-file-input
              multiple
              accept="image/*"
              v-model="new_files"
            />
          </v-form>
          <v-row
            justify="right"
            class="border-me-not"
          >
            <v-col
              cols="2"
            >
              <v-btn
                color="primary"
                @click="saveSponsoredListing"
              >
                Save
              </v-btn>
            </v-col>
          </v-row>
        </v-card>
      </v-col>
    </v-row>

    <v-row
      justify="center"
      class="border-me-not"
      v-if="sponsoredListing.images"
    >
      <v-col
        v-for="(item, i) in sponsoredListing.images"
        :key="i"
        cols="2"
      >
        <v-img
          :src="'/api/v1/sponsored-listing-image/'+sponsoredListing.id+'/'+item"
        />
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  import { mapState } from 'vuex'
  import axios from 'axios'

  export default {
    components: {
    },
    theme: {
      options: { light: true },
    },

    data: () => ({
      sponsoredListing: {
        images: [],
      },
      new_files: [],
      fips: null,
      apn: null,
    }),

    computed: {
      ...mapState('auth', ['currentUser']),
    },

    mounted () {
      this.fips = this.$route.params.fips
      this.apn = this.$route.params.apn
      this.getSponsoredListing()
    },

    methods: {
      getSponsoredListing () {
        if (this.fips && this.apn) {
          axios.get('/api/v1/my-sponsored-listing',
                    {
                      headers: {
                        Authorization: 'Bearer ' + this.currentUser,
                      },
                      params: {
                        fips: this.fips,
                        apn: this.apn,
                      },
                    })
            .then((response) => {
              this.sponsoredListing = response.data
            })
            .catch(() => {
              this.$router.push('/listing/' + this.fips + '/' + this.apn)
            })
        }
      },
      saveSponsoredListing: function () {
        var formData = new FormData()

        if (this.new_files.length) {
          this.new_files.forEach(function (x) {
            formData.append('file', x)
          })
        }
        formData.append('new_files[]', this.new_files)
        formData.append('description', this.sponsoredListing.description)
        formData.append('fips', this.fips)
        formData.append('apn', this.apn)
        formData.append('id', this.sponsoredListing.id)

        axios
          .put('/api/v1/my-sponsored-listing',
               formData,
               {
                 headers: {
                   Authorization: 'Bearer ' + this.currentUser,

                   'Content-Type': 'multipart/form-data',
                 },
               })
          .then(response => {
            console.log(response)
            this.getSponsoredListing()
            this.new_files = []
          })
          .catch(error => {
            console.log(error)
          })
      },
    },
  }
</script>

<style lang="sass">
</style>
