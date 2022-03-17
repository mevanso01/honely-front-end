<template>
  <!-- eslint-disable -->
  <div class="provider-wrapper">
    <div
      class="provider-picture"
      v-bind:style="{ backgroundImage: 'url(' + picture + ')' }"
    >
    </div>
    <div class="provider-info">
      <p class="provider-name">{{ getName }}</p>
      <p class="provider-company">{{ getCompany }}</p>
      <p>{{ getPhone }}</p>
      <p>{{ getEmail }}</p>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'ServiceProviderBlock',
    components: {
    },
    props: {
      provider: Object,
    },
    data () {
      return {
        picture: null,
      }
    },
    computed: {
      getName () {
        let name = ''
        if (this.provider) {
          if (this.provider.first_name) {
            name += this.provider.first_name
          }
          if (this.provider.last_name) {
            name += ' ' + this.provider.last_name
          }
        }
        return name
      },
      getCompany () {
        if (this.provider && this.provider.company_name) {
          return this.provider.company_name
        } else {
          return ''
        }
      },
      getEmail () {
        if (this.provider && this.provider.agent_email) {
          return this.provider.agent_email
        } else {
          return ''
        }
      },
      getPhone () {
        if (this.provider && this.provider.phone_number) {
          return this.provider.phone_number
        } else {
          return ''
        }
      },
    },
    watch: {
    },
    mounted () {
      if (this.provider && this.provider.image_url) {
        this.picture = this.provider.image_url
      }
    },
  }
</script>
