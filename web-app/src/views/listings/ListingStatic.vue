<template>
  <v-container
    id="listing"
    class="mt-3"
    fluid
  >
    <v-row>
      <v-col
        cols="12"
      >
        <v-container
          id="listing-div"
        >
          <v-row>
            <v-col
              cols="10"
              class="white-buttons"
            >
              <v-btn
                text
                href="/listings?address=9%20Norwood"
              >
                <v-icon>
                  {{ 'mdi-keyboard-backspace' }}
                </v-icon>
                Search
              </v-btn>
              <v-btn
                text
                outlined
              >
                Overview
              </v-btn>
              <v-btn
                text
                outlined
              >
                Public Info
              </v-btn>
              <v-btn
                text
                outlined
              >
                Property Details
              </v-btn>
              <v-btn
                text
                outlined
              >
                Sale & Tax History
              </v-btn>
              <v-btn
                text
                outlined
              >
                Schools
              </v-btn>
              <h2
                class="mt-3"
              >
                {{ listingForDev.address.street_number }}
                {{ listingForDev.address.street_name }}
                {{ listingForDev.address.street_suffix }},
                {{ listingForDev.address.city }},
                {{ listingForDev.address.state }}
                {{ listingForDev.address.zip_code }}
              </h2>
            </v-col>
            <v-spacer />
            <v-col
              cols="2"
              class="squash-buttons text-center"
            >
              <v-btn
                text
                elevation="3"
              >
                Add to Favorites
                <v-icon>
                  {{ 'mdi-heart-outline' }}
                </v-icon>
              </v-btn>
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="6"
              class="mt-0 pt-0"
            >
              <v-img
                src="https://maps.googleapis.com/maps/api/streetview?channel=mb-pdp-publicrecord&location=9+Norwood+Ln%2C+Bella+Vista%2C+AR+72714&size=665x441&source=outdoor&client=gme-redfin&signature=DmbXXt8x0qAxovjDZXjUkSxLIKE="
                aspect-ratio="1.3"
              />
            </v-col>
            <v-col
              cols="6"
              class="mt-0 pt-0"
            >
              <forecast-card
                :displayaddress="listingForDev.address.street_number + ' ' + listingForDev.address.street_name"
              />
            </v-col>
          </v-row>
          <v-row>
            <v-col
              cols="7"
              class="white-buttons"
            >
              <v-card
                class="mx-auto listing-overview pa-4"
                light
                outlined
              >
                <div class="homecontainer">
                  <v-btn
                    x-large
                    elevation="6"
                    class="btn-forecast"
                  >
                    <v-icon left>
                      mdi-home-currency-usd
                    </v-icon>
                    Full Forecast Tool &gt;
                  </v-btn>

                  <v-card-title
                    class="card-pricing pa-0"
                  >
                    $175,000
                    <span class="pricecat">
                      list
                    </span>
                    <br>
                    <span class="price2">
                      $192,500
                      <span class="pricecat">
                        valuation
                      </span>
                    </span>
                  </v-card-title>
                  <h4
                    class="card-address mt-4"
                  >
                    {{ listingForDev.address.street_number }}
                    {{ listingForDev.address.street_name }}
                    {{ listingForDev.address.street_suffix }},
                    {{ listingForDev.address.city }},
                    {{ listingForDev.address.state }}
                    {{ listingForDev.address.zip_code }}
                  </h4>
                  <h3>
                    {{ listingForDev.structure.beds_count }}  Beds
                    <span>&bull;</span>
                    {{ listingForDev.structure.baths }}  Baths
                    <span>&bull;</span>
                    {{ listingForDev.structure.total_area_sq_ft }} Sq. Ft.
                  </h3>
                  <div
                    class="tool-buttons mt-6"
                  >
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          class="mr-2"
                          small
                          v-bind="attrs"
                          v-on="on"
                        >
                          <v-icon>
                            mdi-camera
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>View Photos</span>
                    </v-tooltip>
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          class="mr-2"
                          small
                          v-bind="attrs"
                          v-on="on"
                        >
                          <v-icon>
                            mdi-directions
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>Get Directions</span>
                    </v-tooltip>
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          class="mr-2"
                          small
                          v-bind="attrs"
                          v-on="on"
                        >
                          <v-icon>
                            mdi-share
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>Share</span>
                    </v-tooltip>
                    <v-tooltip bottom>
                      <template v-slot:activator="{ on, attrs }">
                        <v-btn
                          class="mr-2"
                          small
                          v-bind="attrs"
                          v-on="on"
                        >
                          <v-icon>
                            mdi-heart
                          </v-icon>
                        </v-btn>
                      </template>
                      <span>Add To Favorites</span>
                    </v-tooltip>
                    <!-- v-btn
                      x-large
                      elevation="6"
                      class="btn-forecast btn-squash"
                    >
                      <v-icon left>
                        mdi-pencil
                      </v-icon>
                      Edit Property Info
                    </! -->
                  </div>
                </div>
              </v-card>
            </v-col>
            <v-col
              cols="5"
              class="map-container"
            >
              <iframe
                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d5339.296173438166!2d-94.24682974108948!3d36.48303284491339!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x87c90324c1364aed%3A0xc4158662c004968f!2s9%20Norwood%20Ln%2C%20Bella%20Vista%2C%20AR%2072714!5e0!3m2!1sen!2sus!4v1619155941062!5m2!1sen!2sus"
                width="100%"
                height="250px"
                style="border:0;"
                allowfullscreen=""
                loading="lazy"
              />
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        class="background-gray"
      >
        <v-container
          id="listing-div"
          class="pb-8"
        >
          <v-row>
            <v-col
              cols="8"
              class="white-buttons"
            >
              <base-heading
                class="mt-0 band-header pb-1"
                title=""
                weight="medium"
                size="text-h5"
                space="1"
              >
                Public Info
                <v-btn
                  x-large
                  elevation="6"
                  class="btn-forecast btn-squash header-btn"
                >
                  <v-icon left>
                    mdi-pencil
                  </v-icon>
                  Edit Property Info
                </v-btn>
              </base-heading>
              <v-row>
                <v-col
                  cols="6"
                >
                  <table
                    class="mt-3 lined-table"
                  >
                    <tr>
                      <td>
                        Beds
                      </td>
                      <td>
                        {{ listingForDev.structure.beds_count }}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        Baths
                      </td>
                      <td>
                        {{ listingForDev.structure.baths }}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        Sq. Ft.
                      </td>
                      <td>
                        {{ listingForDev.structure.total_area_sq_ft }}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        Stories
                      </td>
                      <td>
                        {{ listingForDev.structure.stories }}
                      </td>
                    </tr>
                  </table>
                </v-col>
                <v-col
                  cols="6"
                >
                  <table
                    class="mt-3 lined-table"
                  >
                    <tr>
                      <td>
                        Style
                      </td>
                      <td>
                        {{ listingForDev.structure.architecture_type }}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        Year Built
                      </td>
                      <td>
                        {{ listingForDev.structure.year_built }}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        County
                      </td>
                      <td>
                        {{ listingForDev.structure.total_area_sq_ft }}
                      </td>
                    </tr>
                    <tr>
                      <td>
                        APN
                      </td>
                      <td>
                        {{ listingForDev.address.apn }}
                      </td>
                    </tr>
                  </table>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        class="background-white"
      >
        <v-container
          id="listing-div"
          class="pb-8"
        >
          <v-row>
            <v-col
              cols="8"
              class="white-buttons"
            >
              <base-heading
                class="mt-0 band-header pb-1"
                title=""
                weight="medium"
                size="text-h5"
                space="1"
              >
                Property Details for
                {{ listingForDev.address.street_number }}
                {{ listingForDev.address.street_name }}
              </base-heading>
              <v-row>
                <v-col
                  cols="12"
                >
                  <table
                    class="mt-3"
                  >
                    <tr>
                      <th>
                        Interior Features
                      </th>
                    </tr>
                    <tr>
                      <td>
                        <v-row>
                          <v-col
                            cols="6"
                          >
                            <table
                              class="mt-3 lined-table"
                            >
                              <tr>
                                <th
                                  colspan="2"
                                >
                                  Bed &amp; Bath
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  # of Bedrooms
                                </td>
                                <td>
                                  {{ listingForDev.structure.beds_count }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  # of Full Baths
                                </td>
                                <td>
                                  {{ listingForDev.structure.baths }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  # of Partial Baths
                                </td>
                                <td>
                                  {{ listingForDev.structure.partial_baths_count }}
                                </td>
                              </tr>
                              <tr>
                                <th
                                  colspan="2"
                                  class="table-subspacer"
                                >
                                  Room Information
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Room Count
                                </td>
                                <td>
                                  {{ listingForDev.structure.rooms_count }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Other Rooms
                                </td>
                                <td>
                                  {{ listingForDev.structure.other_rooms }}
                                </td>
                              </tr>
                              <tr>
                                <th
                                  colspan="2"
                                  class="table-subspacer"
                                >
                                  Heating &amp; Cooling
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Air Conditioning Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.air_conditioning_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Heating Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.heating_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Heating Fuel Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.heating_fuel_type }}
                                </td>
                              </tr>
                            </table>
                          </v-col>
                          <v-col
                            cols="6"
                          >
                            <table
                              class="mt-3 lined-table"
                            >
                              <tr>
                                <th
                                  colspan="2"
                                >
                                  Additional Details
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Stories
                                </td>
                                <td>
                                  {{ listingForDev.structure.stories }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Amenities
                                </td>
                                <td>
                                  {{ listingForDev.structure.amenities }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Total Area Square Feet
                                </td>
                                <td>
                                  {{ listingForDev.structure.total_area_sq_ft }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Parking Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.parking_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Parking Spaces Count
                                </td>
                                <td>
                                  {{ listingForDev.structure.parking_spaces_count }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Plumbing Fxtures Count
                                </td>
                                <td>
                                  {{ listingForDev.structure.plumbing_fixtures_count }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Pool
                                </td>
                                <td>
                                  {{ listingForDev.structure.pool_type }}
                                </td>
                              </tr>
                            </table>
                          </v-col>
                        </v-row>
                      </td>
                    </tr>
                  </table>
                  <table
                    class="mt-3"
                  >
                    <tr>
                      <th>
                        Construction &amp; Exterior Features
                      </th>
                    </tr>
                    <tr>
                      <td>
                        <v-row>
                          <v-col
                            cols="6"
                          >
                            <table
                              class="mt-3 lined-table"
                            >
                              <tr>
                                <th
                                  colspan="2"
                                >
                                  Building Information
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Year Built
                                </td>
                                <td>
                                  {{ listingForDev.structure.year_built }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Units Count
                                </td>
                                <td>
                                  {{ listingForDev.structure.units_count }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Architecture Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.architecture_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Condition
                                </td>
                                <td>
                                  {{ listingForDev.structure.condition }}
                                </td>
                              </tr>
                              <tr>
                                <th
                                  colspan="2"
                                  class="table-subspacer"
                                >
                                  Walls &amp; Flooring
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Exterior Walls
                                </td>
                                <td>
                                  {{ listingForDev.structure.exterior_wall_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Interior Walls
                                </td>
                                <td>
                                  {{ listingForDev.structure.interior_wall_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Flooring Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.flooring_types }}
                                </td>
                              </tr>
                            </table>
                          </v-col>
                          <v-col
                            cols="6"
                          >
                            <table
                              class="mt-3 lined-table"
                            >
                              <tr>
                                <th
                                  colspan="2"
                                >
                                  Construction
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Basement Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.basement_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Construction Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.construction_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Foundation Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.foundation_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Roof Style
                                </td>
                                <td>
                                  {{ listingForDev.structure.roof_style_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Roof Material
                                </td>
                                <td>
                                  {{ listingForDev.structure.roof_material_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Quality
                                </td>
                                <td>
                                  {{ listingForDev.structure.quality }}
                                </td>
                              </tr>
                              <tr>
                                <th
                                  colspan="2"
                                  class="table-subspacer"
                                >
                                  Utilities
                                </th>
                              </tr>
                              <tr>
                                <td>
                                  Sewer Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.sewer_type }}
                                </td>
                              </tr>
                              <tr>
                                <td>
                                  Water Type
                                </td>
                                <td>
                                  {{ listingForDev.structure.water_type }}
                                </td>
                              </tr>
                            </table>
                          </v-col>
                        </v-row>
                      </td>
                    </tr>
                  </table>
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        class="background-gray"
      >
        <v-container
          id="listing-div"
          class="pb-8"
        >
          <v-row>
            <v-col
              cols="8"
              class="white-buttons"
            >
              <tax-history />
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        class="background-white"
      >
        <v-container
          id="listing-div"
          class="pb-8"
        >
          <v-row>
            <v-col
              cols="8"
              class="white-buttons"
            >
              <base-heading
                class="mt-0 band-header pb-1"
                title=""
                weight="medium"
                size="text-h5"
                space="1"
              >
                Schools
              </base-heading>
              <v-list
                subheader
                two-line
                class="white-background"
              >
                <v-subheader
                  inset
                >
                  GreatSchools Rating
                </v-subheader>

                <v-list-item
                  v-for="school in schoolsForDev"
                  :key="school.id"
                >
                  <v-list-item-avatar>
                    <v-icon
                      class="grey lighten-1 school-rating"
                      dark
                    >
                      {{ school.rating }}
                    </v-icon>
                  </v-list-item-avatar>

                  <v-list-item-content>
                    <v-list-item-title v-text="school.name" />
                    <v-list-item-subtitle v-text="school.desc" />
                  </v-list-item-content>

                  <v-list-item-content>
                    <v-list-item-title v-text="school.distance" />
                    <v-list-item-subtitle v-text="school.distanceText" />
                  </v-list-item-content>
                </v-list-item>
              </v-list>
              <p
                class="small-type"
              >
                School data provided by
                <a href="http://www.greatschools.org/">
                  GreatSchools
                </a>
                . School service boundaries are intended to be used as reference only. To verify enrollment eligibility for a property, contact the school directly.
              </p>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <v-row>
      <v-col
        class="background-gray"
      >
        <v-container
          id="listing-div"
          class="pb-8"
        >
          <v-row>
            <v-col
              cols="12"
              class="white-buttons"
            >
              <v-row
                justify="start"
                class="border-me-not"
              >
                <v-col
                  class="intro-text"
                  cols="12"
                  sm="10"
                  md="8"
                >
                  <h1
                    class="mb-1"
                  >
                    Similar homes in the area
                  </h1>
                  <!-- p>
                    You can search for homes that meet your requirements but will also grow in value. Filter by location and specification but also by forecasted growth rates.
                  </! -->
                </v-col>
              </v-row>

              <v-row
                justify="start"
                class="border-me-not"
              >
                <v-col
                  v-for="item in listings"
                  :key="item.id"
                  class="intro-text"
                  cols="12"
                  sm="8"
                  md="4"
                >
                  <property-listings-card :item="item" />
                </v-col>
              </v-row>
              <v-row
                justify="center"
                class="border-me-not centeritems"
                align-content="center"
              >
                <v-col
                  cols="12"
                  align-self="center"
                >
                  <!-- base-btn
                    :block="$vuetify.breakpoint.smAndDown"
                    color="white"
                    href="javascript:alert('Load more homes');"
                    large
                    outlined
                    height="40"
                    width="215"
                    class="btn-lightgray mt-3"
                    elevation="3"
                  >
                    Load More
                    <v-icon
                      class="icon-left-space mdi-rotate-90  mdi-20px icon-green"
                    >
                      {{ 'mdi-play' }}
                    </v-icon>
                  </!-->
                </v-col>
              </v-row>
            </v-col>
          </v-row>
        </v-container>
      </v-col>
    </v-row>
    <!-- h1>Listing</h1>
    <div
      v-for="(value, name) in listingForDev"
      :key="value"
    >
      {{ name }}:
      <div
        v-for="(innervalue, innername) in value"
        :key="innervalue"
        style="margin-left: 20px"
      >
        {{ innername }}: {{ innervalue }}
      </div>
    </div -->
  </v-container>
</template>

<script>

  import axios from 'axios'
  import { mapState } from 'vuex'

  export default {
    components: {
      ForecastCard: () => import('@/components/ForecastCard'),
      TaxHistory: () => import('@/components/TaxHistory'),
      PropertyListingsCard: () => import('@/components/PropertyListingsCard'),
    },
    data () {
      return {
        fips: null,
        apn: null,
        listing: {},
        // eslint-disable-next-line
        listingForDev: {"address":{"fips":"05007","apn":"16-39780-000","street_number":"9","street_pre_direction":"null","street_name":"NORWOOD","street_suffix":"LN","street_post_direction":"null","unit_type":"null","unit_number":"null","formatted_street_address":"9 NORWOOD LN","city":"BELLA VISTA","state":"AR","zip_code":"72714","zip_plus_four_code":"1849","latitude":"36.481077","longitude":"-94.247209","geocoding_accuracy":"PARCEL CENTROID","census_tract":"50070207.012014","carrier_code":"R048","full_address":"9 NORWOOD LN BELLA VISTA AR 72714 1849"},"tax":{"year":"2020","amount":"1263","exemptions":"HOMESTEAD","rate_code_area":"CBV6"},"valuation":{"year":"149000","amount":"125160","exemptions":"172840","forecast_standard_deviation":"16","date":"2021-03-03"},"structure":{"year_built":"null","effective_year_built":"null","stories":"1","rooms_count":"null","beds_count":"3","baths":"2","partial_baths_count":"null","units_count":"null","total_area_sq_ft":"1296.0","air_conditioning_type":"CENTRAL","amenities":"null","architecture_type":"null","basement_type":"null","condition":"null","construction_type":"FRAME","exterior_wall_type":"WOOD","flooring_types":"CARPET|TILE","foundation_type":"PIERS","heating_type":"CENTRAL","heating_fuel_type":"null","interior_wall_type":"null","other_rooms":"null","parking_type":"CARPORT","parking_spaces_count":"1.0","plumbing_fixtures_count":"null","pool_type":"null","quality":"C+","roof_material_type":"ASPHALT","roof_style_type":"null","sewer_type":"null","water_type":"null"}},
        schoolsForDev: [
          {
            id: 1,
            rating: '9/10',
            name: 'Cooper Elementary School',
            desc: 'Public | K to 4 | Serves this home',
            distance: '3.2 mi',
            distanceText: 'distance',
          },
          {
            id: 2,
            rating: '6/10',
            name: 'Old High Middle School',
            desc: 'Public | 5 to 6 | Serves this home',
            distance: '7.6 mi',
            distanceText: 'distance',
          },
          {
            id: 3,
            rating: '8/10',
            name: 'Washington Junior High School',
            desc: 'Public | 7 to 8 | Serves this home',
            distance: '8.1 mi',
            distanceText: 'distance',
          },
          {
            id: 4,
            rating: '8/10',
            name: 'Bentonville High School',
            desc: 'Public | 9 to 12 | Serves this home',
            distance: '9.4 mi',
            distanceText: 'distance',
          },
        ],
      }
    },
    computed: {
      ...mapState('listings', {
        listings: 'all',
      }),
    },
    created () {
      this.fips = this.$route.params.fips
      this.apn = this.$route.params.apn
      this.load()
    },
    methods: {
      load () {
        if (this.fips && this.apn) {
          axios.get('https://api.honely.com/search/listing', {
            params: {
              apn: this.apn,
              fips: this.fips,
            },
          }).then((response) => {
            this.listing = response.data
            console.log('zz response')
          })
        }
      },
    },
  }
</script>

<style lang="sass">
  #listing
    border-top: 4px solid $color-orange

    #listing-div

      .v-btn
        margin-right: 6px
        font-weight: 600

        .v-icon
          font-size: 1rem

      .white-buttons
        .v-icon
          padding-right: 6px

      .squash-buttons

        .v-btn
          color: white
          background-color: $color-squash

        .v-icon
          padding-left: 6px

      .map-container

      iframe
        border: 1px solid $color-gray-lt !important

      .listing-overview
        border-color: $color-emerald
        height: 250px

        .homecontainer

          .card-pricing
            display: block
            font-size: 27px
            font-family: $body-font-family
            font-weight: 600
            line-height: 27px

            .pricecat
              font-size: 15px

            .price2
              color: $color-emerald

          .v-btn
            background-color: $color-emerald
            color: white
            font-weight: 600
            letter-spacing: 0.04em

            .v-icon
              font-size: 1.5rem

          .btn-forecast
            float: right

          h3
            span
              font-size: 1rem
              color: $color-gray-med
              padding: 0 6px

          .tool-buttons

            .v-btn
              background-color: $color-gray-shadow
              height: 40px

              .v-icon
                padding: 0

    .background-gray
      background-color: $color-gray-vlt

    .band-header
      border-bottom: 2px solid $color-emerald

    table
      width: 100%
      border-collapse: collapse

      tr
        margin: 0

        th
          background-color: $color-gray-shadow
          color: white
          padding: 4px 20px
          text-align: left

        td
          padding: 4px 0 10px 0
          margin: 0
          background-color: white

    table.lined-table

      tr:nth-child(odd)
        background-color: white
        margin: 0

      tr:nth-child(even)
        background-color: $color-gray-vlt !important
        margin: 0

      th
        background-color: transparent
        color: $color-squash
        border-bottom: 2px solid $color-gray-med

      th.table-subspacer
        padding-top: 15px
        background-color: white

      td
        padding: 4px 20px
        margin: 0
        border-bottom: 1px solid $color-gray-lt
        background-color: inherit

      td:first-child
        border-left: 1px solid $color-gray-lt

      td:last-child
        text-align: right
        font-weight: 600

    .school-rating
      font-size: 16px
      padding: 0 !important

    .white-background
      background-color: white

    .small-type
      font-size: 12px
      line-height: 14px
      color: $color-gray-med

    .btn-squash.v-btn
      color: white
      background-color: $color-squash !important

    .header-btn
      height: 30px
      float: right
      font-family: $body-font-family
      font-size: 14px

</style>
