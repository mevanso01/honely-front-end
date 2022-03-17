<template>
    <!-- eslint-disable -->
    <!-- Service Provider Card -->
    <div class="service-provider-card">
      <!-- Service Provider Image -->
      <div class="service-provider-image">
        <img v-if="agent.image_url && agent.image_url !== ''" :src="agent.image_url">
        <img v-else src="https://honely-files-public.s3.amazonaws.com/images/avatar/avatar_user_01.png">
      </div>
  
      <!-- Service Provider Description -->
      <div class="service-provider-description">
        <!-- Service Provider Name -->
        <div class="service-provider-full-name">
          <i class="fas fa-user"></i>
          <p v-if="agent.first_name && agent.first_name !== ''">{{agent.first_name}}</p>
          <p v-if="agent.last_name && agent.last_name !== ''">{{agent.last_name}}</p>
        </div>
        <!-- Service Provider Phone Number -->
        <div v-if="agent.phone_number && agent.phone_number !== ''" class="service-provider-phone">
          <i class="fas fa-phone"></i>
          <p>{{agent.phone_number}}</p>
        </div>
        <!-- Service Provider Company -->
        <div class="service-provider-company">
          <i class="fas fa-building"></i>
          <p v-if="agent.company_name && agent.company_name !== ''">{{agent.company_name}}</p>
        </div>
        <!-- Service Provider Email -->
        <div class="service-provider-email">
          <i class="fas fa-envelope"></i>
          <p v-if="agent.agent_email && agent.agent_email !== ''">{{agent.agent_email}}</p>
        </div>
      </div>
    </div>
    <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'AgentCard',
    props: {
      agent: Object,
    },

  }
</script>
