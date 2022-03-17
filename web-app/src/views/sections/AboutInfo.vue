<template>
  <div class="aboutinfo-container">
    <div class="flex-wrapper">
      <div class="col-5">
        <img
          src="@/assets/site_images/icons/icon-dollar.png"
          class="about-img"
        />
        <h4 class="page-sub-title-2">Helping You Invest Wisely</h4>
        <p class="page-sub-content">
          Other digital resources are designed simply for home browsing. We’re here to bring transparency and aid in decision-making.
        </p>
        <p
          class="page-sub-content"
          v-show="expand_help"
        >
          We don’t just focus on a property’s current value. Honely aims to give you a comprehensive view, providing insights on the future value, the zip code, the moving trends, and more. Our forecasting models are the most accurate on the market, which is crucial given that a few percentage points can translate to large sums of money when it comes to real estate
        </p>
        <a
          href="javascript:;"
          class="btn-read-more"
          @click="expand_help = !expand_help"
        >
          {{ expand_help ? 'Read less' : 'Read more' }}
        </a>
      </div>
      <div class="col-5">
        <img
          src="@/assets/site_images/icons/icon-heart.png"
          class="about-img"
        />
        <h4 class="page-sub-title-2 ">Revolutionizing Home Buying</h4>
        <p class="page-sub-content">
          We are revolutionizing the home buying industry by giving average investors the tools they need to make informed decisions.
        </p>
        <p
          class="page-sub-content"
          v-show="expand_revolution"
        >
          Buyers and sellers need to know what the future will bring. For too long, stakeholders have been left in the dark over what results their purchase will yield in the long-term. Honely has changed that for good.
        </p>
        <a
          href="javascript:;"
          class="btn-read-more"
          @click="expand_revolution = !expand_revolution"
        >
          {{ expand_revolution ? 'Read less' : 'Read more' }}
        </a>
      </div>
    </div>
  </div>
</template>
<script>
  export default {
    data: () => ({
      expand_help: false,
      expand_revolution: false,
    }),
  }
</script>
