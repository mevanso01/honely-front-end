<template>
  <!-- eslint-disable -->
  <div class='forecast-services-section section-wrapper'>
    <div class='forecast-section-title'>
      Realtors in your area
    </div>
    <p class='copy80'>If you are interested in this property or other properties in this zip code reach out to our affiliated network of realtors to have the best success with you purcahse.</p>
    <!-- service-providers-container -->
    <div v-if="agents" class="service-providers-container" :class="getAlignClass">
      <service-provider-block
        v-for="(agent, i) in agents"
        :provider="agent"
        :key="i"
      />
    </div>
    <div v-else class="service-providers-container">
      <p>No service providers available in this area</p>
    </div>
    <!-- /service-providers-container -->
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'Services',
    components: {
      ServiceProviderBlock: () => import('@/components/forecast/ServiceProviderBlock'),
    },
    props: {
      serviceProviders: Object,
    },
    data () {
      return {
        agents: null,
        lenders: null,
        generalContractors: null,
      }
    },
    computed: {
      getAlignClass () {
        if (this.agents) {
          if (this.agents.length >= 3) {
            return 'between'
          } else {
            return 'evenly'
          }
        } else {
          return 'evenly'
        }
      },
    },
    watch: {
      serviceProviders: function () {
        if (this.serviceProviders) {
          this.agents = this.serviceProviders.agents
          this.lenders = this.serviceProviders.lenders
          this.generalContractors = this.serviceProviders.general_contractors
        }
      },
    },
    mounted () {
      if (this.serviceProviders) {
        // console.log(this.serviceProviders)
        this.agents = this.serviceProviders.agents
        this.lenders = this.serviceProviders.lenders
        this.generalContractors = this.serviceProviders.general_contractors
      }
    },
  }
</script>
