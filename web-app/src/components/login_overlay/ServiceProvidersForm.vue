<template>
  <!-- eslint-disable -->
  <div>
    <!-- Service provider options -->
    <div class="service-providers-form">
      <!-- Realtor -->
      <div class="service-providers-form-container">
        <i class="fas fa-user-check"></i>
        <label for="realtor">Realtor</label>
        <input type="radio" id="realtor" v-model="serviceProviderType" value="AGENT/BROKER">
      </div>
      <!-- Lender -->
      <div class="service-providers-form-container">
        <i class="fas fa-hand-holding-usd"></i>
        <label for="lender">Lender</label>
        <input type="radio" id="lender"  v-model="serviceProviderType" value="Lender">
      </div>
      <div class="service-providers-form-container">
        <i class="fas fa-file-signature"></i>
        <label class="gc-label" for="general-contractor">General Contractor</label>
        <input type="radio" id="general-contractor"  v-model="serviceProviderType" value="General Contractor">
      </div>
      <!-- Other -->
      <!-- <div class="service-providers-form-container">
        <i class="fas fa-strikethrough"></i>
        <label for="other">Other</label>
        <input type="radio" id="other" v-model="serviceProviderType" value="">
      </div> -->
    </div>
    <span v-if="errorMessage" class="error-message">{{ errorMessage }}</span>
    <button class="submit-service-provider-button" @click="submitServiceProviderOptions()">Submit</button>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'ServiceProvidersForm',
    data: () => {
      return {
        serviceProviderType: null,
        errorMessage: null,
      }
    },
    methods: {
      submitServiceProviderOptions () {
        var res = []
        if (!this.serviceProviderType) {
          this.errorMessage = 'Please select an option'
          setTimeout(() => {
            this.errorMessage = null
          }, 1500)
          return
        }
        this.$store.dispatch('auth/setSelectedServiceProviderType', this.serviceProviderType)
        this.$emit('optionsSubmitted', true)
        this.$emit('closeOverlay')
      },
    },
  }
</script>

<style scoped>
  .service-providers-form {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .service-providers-form i{
    font-size: 35px;
    color: #00a650;
  }

  .service-providers-form input, label{
    display: block;
    margin: auto;
    text-align: center;
    margin-bottom: 5px;
  }

  .service-providers-form-container{
    padding: 15px;
    height: 120px;
    width: 150px;
    border: solid;
    border-width: 3px;
    border-color: #00a650;
    border-radius: 5px;
    text-align: center;
    margin: 25px;
  }

  .selling-container{
    display: block;
  }
  .gc-label{
    font-size: 10px;
  }
  .submit-service-provider-button{
    background: #00a650;
    color: white;
    border-radius: 5px;
    padding: 10px;
    margin-left: 13rem;
  }

  .error-message{
    float: right;
    color: red;
    margin-top: 5px;
  }

  @media only screen and (max-width: 500px){
  .service-providers-form{
    display: block;
  }
  .submit-service-provider-button{
    margin-left: 4rem;
  }
}
</style>
