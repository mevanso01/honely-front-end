/* eslint-disable */
import axios from 'axios'

export const state = () => ({
    error: null,
    all: [{
        id: 1,
        image: 'temp-home-01.jpg',
        price: '$699,000',
        pricecat: 'list',
        price2: '$734,000',
        price2cat: 'valuation',
        valuecurrentpercentage: '17.7',
        beds_count: '4',
        baths: '3',
        total_area_sq_ft: '2,164',
        propstatus: 'For Sale',
        full_address: '184 Valley Rd, Montclair, NJ 07042',
        zipcode: '07042',
        realtor: 'Weichart Realtors, Montclair',
        additional_images: null,
    }, {
        id: 2,
        image: 'temp-home-02.jpg',
        price: '$745,000',
        pricecat: 'appraisal',
        price2: '$827,500',
        price2cat: 'valuation',
        valuecurrentpercentage: '13.2',
        beds_count: '4',
        baths: '4',
        total_area_sq_ft: '4,750',
        propstatus: '',
        full_address: '13 Park St, Montclair, NJ 07042',
        zipcode: '07042',
        realtor: 'Village Square Realtors',
        additional_images: null,
    }, {
        id: 3,
        image: 'temp-home-03.jpg',
        price: '$949,000',
        pricecat: 'list',
        price2: '$902,000',
        price2cat: 'valuation',
        valuecurrentpercentage: '-4.7',
        beds_count: '4',
        baths: '3',
        total_area_sq_ft: '3,628',
        propstatus: 'For Sale',
        full_address: '56 Macopin Ave, Montclair, NJ 07043',
        zipcode: '07043',
        realtor: 'Sotheby’s International Realty',
        additional_images: null,
    }],
    totalResults: 0,
    noAddressEntries: false,
    one: [{
        id: 4,
        image: 'temp-home-01.jpg',
        price: '$699,000',
        pricecat: 'list',
        price2: '$734,000',
        price2cat: 'valuation',
        valuecurrentpercentage: '17.7',
        beds_count: '4',
        baths: '3',
        total_area_sq_ft: '2,164',
        propstatus: 'For Sale',
        full_address: '184 Valley Rd, Montclair, NJ 07042',
        zipcode: '07042',
        realtor: 'Weichart Realtors, Montclair',
        additional_images: null,
    }],
    favorites: [],
    tempImages: {
        '36061/01384-1108': ['Apt1-1.jpg', 'Apt1-2.jpg', 'Apt1-3.jpg'],
        '36061/01386-0037 01': ['Apt2-1.jpg', 'Apt2-2.jpg', 'Apt2-3.jpg'],
        '36061/01387-0011': ['Apt3-1.jpg', 'Apt3-2.jpg', 'Apt3-3.jpg'],
        '36061/01387-00279A': ['Apt4-1.jpg', 'Apt4-2.jpg', 'Apt4-3.jpg'],
        '36061/01388-1026': ['Apt5-1.jpg', 'Apt5-2.jpg', 'Apt5-3.jpg'],
        '36061/01389-0038 02': ['Apt6-1.jpg', 'Apt6-2.jpg', 'Apt6-3.jpg'],
        '36061/01390-0063': ['Apt7-1.jpg', 'Apt7-2.jpg', 'Apt7-3.jpg'],
        '36061/01407-0046': ['Apt8-1.jpg', 'Apt8-2.jpg', 'Apt8-3.jpg'],
        '36061/01408-0010': ['Apt9-1.jpg', 'Apt9-2.jpg', 'Apt9-3.jpg'],
        '36061/01408-00339C': ['Apt10-1.jpg', 'Apt10-2.jpg', 'Apt10-3.jpg'],
    },
    computed_zip: null,
    subscription: {}
})

// getters
export const getters = {
    listingsError: state => {
        return state.error
    },
    allListings: state => {
        return state.all
    },
    totalResults: state => {
        return state.totalResults
    },
    noAddressEntries: state => {
        return state.noAddressEntries
    },
    favoriteListings: state => {
        return state.favorites
    },
    tempImages: (state) => (fipsApn) => {
        return state.tempImages[fipsApn]
    },
    computedZip: state => {
        return state.computed_zip
    },
    subscriptionMode: state => {
        return state.subscription
    }
}

// actions
export const actions = {
    getListings ({ commit }, payload) {
        var params = {
            address: null,
        }
        if (!payload.query && payload.computed_zip !== null) {
            commit('SET_COMPUTED_ZIP', payload.computed_zip)
            params.address = payload.computed_zip
        } else {
            commit('SET_COMPUTED_ZIP', null)
            params.address = payload.query
        }
        if (payload.offset) {
            params.offset = payload.offset
        }
        if (!params.address) {
            params.ipConfig = 'yes'
        }
        if (payload.lat) {
            params.lat = payload.lat
        }
        if (payload.long) {
            params.long = payload.long
        }
        if (payload.sortBy) {
            params.sort_by = payload.sortBy
        }

        if (payload.sortOrder) {
            params.order_dir = payload.sortOrder
        }

        if (payload.numBedroomsMin && payload.numBedroomsMin.indexOf('No') === -1) {
            params.num_bedrooms_min = payload.numBedroomsMin
        }

        if (payload.numBedroomsMax && payload.numBedroomsMax.indexOf('No') === -1) {
            params.num_bedrooms_max = payload.numBedroomsMax
        }

        if (payload.numBathroomsMin && payload.numBathroomsMin.indexOf('No') === -1) {
            params.num_bathrooms_min = payload.numBathroomsMin
        }

        if (payload.numBathroomsMax && payload.numBathroomsMax.indexOf('No') === -1) {
            params.num_bathrooms_max = payload.numBathroomsMax
        }

        if (payload.forecastedGrowthPercent && payload.forecastedGrowthPercent.indexOf('Any') === -1) {
            params.forecasted_growth_percentage_range = payload.forecastedGrowthPercent
        }

        if (payload.forecastedGrowthDollar && payload.forecastedGrowthDollar.indexOf('Any') === -1) {
            params.forecasted_dollar_amount_range = payload.forecastedGrowthDollar
        }

        if (payload.forecastedYear) {
            params.forecasted_year = payload.forecastedYear
        }

        if (payload.homeTypes && payload.homeTypes.length > 0) {
            params.home_types = payload.homeTypes.join(',')
        }

        if (payload.propertyDetails && payload.propertyDetails.length > 0) {
            params.property_details = payload.propertyDetails.join(',')
        }

        if (payload.priceRange) {
            params.property_price = payload.priceRange
        }

        return new Promise((resolve, reject) => {
            axios.get('https://listhub.honely.com/locate/listhub_listings', {
                params: params,
            })
                .then((response) => {
                    // console.log(response.data)
                    if (response.data.error) {
                        commit('SET_ALL_LISTINGS', [])
                        commit('SET_TOTAL_RESULTS', 0)
                        commit('SET_NO_ADDRESS_ENTRIES', true)
                        commit('SET_LISTINGS_ERROR', response.data.errorMessage)
                        resolve()
                    } else {
                        const listings = []
                        const listhubListings = response.data.rows
                        const faListings = response.data.first_american

                        if (listhubListings && listhubListings.length > 0) {
                            for (let i = 0; i < listhubListings.length; i++) {
                                listings.push(listhubListings[i])
                            }
                        }
                        if (faListings && faListings.length) {
                            for (let i = 0; i < faListings.length; i++) {
                                listings.push(faListings[i])
                            }
                        }

                        if (listings) {
                            listings.forEach(function (listing) {
                                const tempImages = this.getters['listings/tempImages'](listing.fips + '/' + listing.apn)
                                if (tempImages) {
                                    listing.tempImages = tempImages
                                }
                            }, this)
                        }
                        commit('SET_LISTINGS_ERROR', null)
                        commit('SET_ALL_LISTINGS', [...listings])
                        if (response.data && response.data.computed_zip) {
                            commit('SET_COMPUTED_ZIP', response.data.computed_zip)
                        }
                        // vx: count of search results set here
                        const total = response.data.total + response.data.fa_total
                        commit('SET_TOTAL_RESULTS', total)
                        if (response.data.total == 0) {
                            console.log
                            commit('SET_NO_ADDRESS_ENTRIES', true)
                        } else {
                            commit('SET_NO_ADDRESS_ENTRIES', false)
                        }
                    }
                    resolve()
                })
                .catch((error) => {
                    commit('SET_ALL_LISTINGS', [])
                    commit('SET_TOTAL_RESULTS', 0)
                    commit('SET_NO_ADDRESS_ENTRIES', true)
                    commit('SET_LISTINGS_ERROR', error.response.data.errorMessage)
                    resolve()
                })
        })
    },
    getFavorites ({ commit }, payload) {
        // console.log('vx: getFavorites in store called.. payload is', payload)
        axios
            .get('https://api.honely.com/lookup/fetch_favorites?user_id='+payload.user_id)
            .then((response) => {
                // set the state
                const data = response.data
                // console.log('vx: fetch_favorites successful. data being pushed to state', data)
                // commit('SET_ALL_FAVORITES', data.rows)
                for(var i=0;i<data.length;i++) {
                    data[i].appraisal=data[i].current_value
                    data[i].beds_count=data[i].bedrooms
                    data[i].baths=data[i].bathrooms
                    data[i].total_area_sq_ft=data[i].homesize
                    // data[i].propstatus=data[i].status
                    data[i].property_type=data[i].propertytype
                    data[i].for_sale=data[i].status
                    data[i].latitude=data[i].situslatitude
                    data[i].longitude=data[i].situslongitude
                    data[i].property_id=data[i].propertyid
                }
                commit('SET_ALL_FAVORITES', data)
            })
    },
    toggleFavorite ({ commit }, payload) {
        // console.log('vx: toggleFavorite in store called.. payload', payload)
        const obj = { address: payload.address, fips: payload.fips, apn: payload.apn }
        console.log('vx: obj is ', obj)
        console.log(this.getters['listings/favoriteListings'])
        const index = this.getters['listings/favoriteListings'].findIndex(function (x) {    // find the index of that property in favoriteListings
            // console.log('vx: inside findIndex.. x.fips '+ x.fips + ' obj.fips '+ obj.fips+ ' x.apn ' + x.apn + ' obj.apn '+ obj.apn)
            return x.fips === obj.fips && x.apn === obj.apn
        })
        console.log(index)
        // console.log('index from togglefav', index)
        // if property is not found, add property
        if (index === -1) {
            const params = {
                user_id: payload.user_id,
                property_id: payload.property_id,
              }
              // console.log('vx: about to call add_favorites.. params', params)
            axios
                .post('https://api.honely.com/lookup/add_favorites', // add property
                    params)
                .then((response) => {
                    // console.log('vx: add_favorites successful.. obj before commit', obj)
                    commit('ADD_FAVORITE', obj) //add in state; kyle adding only address, fips and apn
                })
                .catch((error) => {
                    console.warn(error)
                    return null
                })
        } else {
            // if property is found, remove property
            const params = {
                user_id: payload.user_id,
                property_id: payload.property_id,
              }
              // console.log('vx: about to call delete_favorites.. params', params)
            axios
                .delete('https://api.honely.com/lookup/delete_favorites',    // remove that property
                        { data: params }
                    )
                .then((response) => {
                    // console.log('vx: delete_favorites successful.. removing from state, index', index)
                    commit('REMOVE_FAVORITE', index)    //remove in state
                })
                .catch((error) => {
                    console.warn(error)
                    return null
                })
        }
    },
    setSubscriptionMode ({ commit }, payload) {
        commit('SET_SUBSCRIPTION_MODE', payload)
    }
}

// mutations
export const mutations = {
    SET_LISTINGS_ERROR (state, error) {
        state.error = error
    },
    SET_ALL_LISTINGS (state, listings) {
      state.all = listings
    },
    SET_NO_ADDRESS_ENTRIES (state, noAddressEntries) {
        state.noAddressEntries = noAddressEntries
    },
    SET_TOTAL_RESULTS (state, totalResults) {
        state.totalResults = totalResults
    },

    ADD_FAVORITE (state, obj) {
        state.favorites.push(obj)
    },
    REMOVE_FAVORITE (state, index) {
        state.favorites.splice(index, 1)
    },
    SET_ALL_FAVORITES (state, favorites) {
        state.favorites = favorites
    },
    SET_COMPUTED_ZIP (state, computedZip) {
        state.computed_zip = computedZip
    },
    SET_SUBSCRIPTION_MODE (state, obj) {
        state.subscription = obj
    },
    // SET_MY_SPONSORED_LISTINGS (state, sponsoredListings) {
    //     state.mySponsoredListings = sponsoredListings
    // },
}
