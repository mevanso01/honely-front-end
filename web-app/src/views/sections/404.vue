<template>
  <!-- eslint-disable -->
  <div class="pg-404" id="404">
    <div class="section-wrapper">
      <h1>404</h1>
      <p>The page you are looking for was not found.</p>
      <div>
        <router-link
          :to="{ name: 'Home'}"
          class="btn-links bg-primary"
        >
          Return Home
        </router-link>
      </div>
    </div>
  </div>
  <!-- eslint-enable -->
</template>

<script>
  export default {
    name: 'FourOhFour',
  }
</script>
