<template>
  <v-theme-provider light>
    <base-section
      id="search-box-cta"
      class="white-band"
      space="5"
    >
      <v-container>
        <v-row
          class="border-me-not"
        >
          <v-col
            class="text-center"
            cols="12"
          >
            <div
              class="text-h5 mb-0"
            >
              {{ heading }}
            </div>
            <div
              class="subtitle mb-6"
            >
              {{ subheading }}
            </div>
            <!-- form
              class="featured-search-form"
              action="javascript:alert('Perform Search On: '+document.querySelectorAll('input')[0].value)"
            >
              <div
                class="featured-search-form-row text-center mx-auto"
              >
                <span>
                  <input
                    id="searchstring"
                    name="searchstring"
                    type="'text"
                    value=""
                    placeholder="enter address or zip code"
                  >
                </span>
                <button
                  class="search-btn"
                >
                  <i
                    class="mdi mdi-magnify"
                    aria-hidden="true"
                  />
                </button>
              </div>
            </form -->
            <div
              class="divider-with-icon"
            >
              <v-divider
                class="mt-8"
              />
              <i
                class="mdi mdi-chevron-down"
                aria-hidden="true"
              />
            </div>
          </v-col>
        </v-row>
      </v-container>
    </base-section>
  </v-theme-provider>
</template>

<script>
  export default {
    name: 'SearchBoxCta',

    data: () => ({
      heading: 'Property Value Forecast Results',
      subheading: 'for: [ address searched ]',
    }),

  }
</script>

<style lang="sass">
#search-box-cta
  .divider-with-icon
    font-size: 30px
    position: relative

    i
      background-color: #fafafa
      color: $color-gray-lt
      position: absolute
      width: 34px
      height: 20px
      top:-7px
      left: calc(50% - 17px)
      line-height: .6

  .text-h5
    font-weight: 400

  .subtitle
    font-size: 20px
    font-weight: 200

  .featured-search-form

    .featured-search-form-row
      border: 1px solid $color-emerald
      background-color: white
      width: 438px
      box-shadow: 1px 1px 14px #666666
      height: 35px
      overflow: hidden
      position: relative

      span
        display: inline-block
        color: black

      .search-btn
        width: 35px
        height: 35px
        background-color: $color-emerald
        display: block
        position: absolute
        top: 0
        right: 0
        color: white
        font-size: 30px
        padding: 0
        line-height: 35px !important

        i
          display: block
          width: 100%
          text-align: right
          font-size: 25px
          padding-right: 3px

      #searchstring
        padding: 3px 10px
        width: 420px
        color: $color-gray-mddk

  ::placeholder
    color: $color-gray-lt

  :-ms-input-placeholder  /* Internet Explorer 10-11 */
    color: $color-gray-lt

  input:focus, textarea:focus, select:focus, button:focus
    outline: none !important
</style>
