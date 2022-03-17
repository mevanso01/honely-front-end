<template>
  <v-container
    class="tax-history-container ma-0 pa-0"
    fluid
  >
    <base-heading
      class="mt-0 band-header pb-1"
      title=""
      weight="medium"
      size="text-h5"
      space="1"
    >
      Sales &amp; Tax History
    </base-heading>
    <v-tabs
      color="#07871c"
      left
    >
      <v-tab>
        Sales History
      </v-tab>
      <v-tab>
        Tax History
      </v-tab>

      <v-tab-item>
        <v-container
          fluid
        >
          <v-row>
            <v-col
              class="mt-3"
            >
              <v-card-text class="py-0">
                <v-timeline
                  align-top
                  dense
                >
                  <v-timeline-item
                    color="orange"
                    small
                  >
                    <v-row
                      v-for="(item, i) in listing.sale_history"
                      :key="i"
                      class="pt-1"
                    >
                      <v-col
                        cols="4"
                      >
                        <strong>{{ item.date | moment("MMM. D, YYYY") }}</strong>
                        <div class="text-caption">
                          Date
                        </div>
                      </v-col>
                      <v-col
                        cols="4"
                      >
                        <strong>${{ formatPrice(item.price) }}</strong>
                        <div class="text-caption">
                          Price
                        </div>
                      </v-col>
                    </v-row>
                  </v-timeline-item>
                </v-timeline>
              </v-card-text>
            </v-col>
          </v-row>
        </v-container>
      </v-tab-item>

      <v-tab-item
        v-if="hasTaxData"
      >
        <v-container
          fluid
        >
          <v-row>
            <v-col
              class="mt-3"
            >
              <v-card
                elevation="0"
                class="forecast-card-content mx-auto text-left pa-0 ma-0 "
              >
                <v-simple-table
                  class="forecast-big-number-table"
                >
                  <tbody>
                    <tr>
                      <th>
                        Year
                      </th>
                      <th>
                        Property Tax
                      </th>
                      <th>
                        Land
                      </th>
                      <th>
                        + Additions
                      </th>
                      <th
                        class="text-right"
                      >
                        = Assessed Value
                      </th>
                    </tr>
                    <tr
                      v-for="(item, i) in listing.tax"
                      :key="i"
                    >
                      <td>
                        {{ item.year }}
                      </td>
                      <td>
                        ${{ formatPrice(item.property_tax) }}
                      </td>
                      <td>
                        ${{ formatPrice(item.land) }}
                      </td>
                      <td>
                        ${{ formatPrice(item.additions) }}
                      </td>
                      <td>
                        ${{ formatPrice(item.assessed_value) }}
                      </td>
                    </tr>
                  </tbody>
                </v-simple-table>
              </v-card>
            </v-col>
          </v-row>
        </v-container>
      </v-tab-item>
      <v-spacer />
    </v-tabs>
  </v-container>
</template>

<script>

  export default {
    name: 'TaxHistory',

    props: {
      listing: Object,
    },

    data: () => ({
      tickLabels: [
        '1 year',
        '2 years',
        '3 years',
      ],
    }),

    computed: {
      hasTaxData () {
        return this.listing && this.listing.tax && this.listing.tax.length > 0
      },
      hasSalesData () {
        return this.listing && this.listing.sale_history && this.listing.sale_history.length > 0
      },
      hasTaxAndSalesData () {
        return this.hasTaxData && this.hasSalesData
      },
    },

    methods: {
      formatPrice (value) {
        // let val = (value/1).toFixed(2).replace('.', ',')
        if (value) {
          value = value.toString().split('.')[0]
          return value.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ',')
        } else {
          return '--'
        }
      },
    },

  }
</script>

<style lang="sass">
  .tax-history-container
    font-family: inherit
</style>
