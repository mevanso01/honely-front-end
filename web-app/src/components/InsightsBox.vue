<template>
  <v-container>
    <v-row>
      <v-col
        v-for="(item, i) in items"
        :key="i"
        :cols="4"
      >
        <div class="text-body-2 font-weight-regular mb-1 text-left grey--text">
          {{ item.date }}
        </div>

        <h3>{{ item.title }}</h3>

        <v-divider />

        <p>{{ item.synopsis }}</p>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
  export default {
    props: {
      items: Array,
    },
  }
</script>
