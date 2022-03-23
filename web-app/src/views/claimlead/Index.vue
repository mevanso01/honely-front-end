<template>
  <!-- eslint-disable -->
  <!-- vx: If the contents of this file are modified, the pdf version in s3 bucket needs to be updated too.. -->
  <div class="section section-wrapper placeholder">
    <div class="static-body feature-text text-exsmall">
      <p id="claim-lead-message">Please wait while the lead is being claimed...</p>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  import axios from 'axios'
  export default {
    name: 'ClaimLead',
    mounted () {
      const urlSearchParams = new URLSearchParams(window.location.search)
      const params = Object.fromEntries(urlSearchParams.entries())
      if (params.confirmation_id) {
        axios.patch('https://api.honely.com/lookup-test/lead/confirm/' + params.confirmation_id)
          .then(() => {
            document.getElementById('claim-lead-message').innerHTML = 'Claimed! Redirecting to Leads Dashboard..'
            setTimeout(() => {
              window.location.href = '/leads-dashboard'
            }, 5000)
          })
          .catch(() => {
            document.getElementById('claim-lead-message').innerHTML = 'Sorry. Failed to claim lead. Redirecting to Leads Dashboard..'
            setTimeout(() => {
              window.location.href = '/leads-dashboard'
            }, 5000)
          })
      }
    },
  }
</script>
