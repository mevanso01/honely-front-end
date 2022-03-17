/* eslint-disable */
import axios from 'axios'
import VueJwtDecode from 'vue-jwt-decode'
// import store from "@/store";
import Amplify, { Auth } from 'aws-amplify'
  import awsExports from '../../aws-config'
  import config from '../../aws-exports'
  Amplify.configure(config)
  // Amplify.configure(awsExports)

export const state = {
  currentUser: getSavedState('auth.currentUser'),
  vxAuth: false,
  vxAuthDependent: false,
  isCognitoUserLoggedIn: false,
  validateTimer: null,
  // currentAuthenticatedUserReturns: null,
  cognitoUser: null, // cognitoSignUpReturns: null,
  confirmationCodeRequested: false,
  regError: null,
  selectedHomeOwnerTypes: null,
  selectedServiceProviderType: null,
  interestedZipCode: null,
  homeAddress: null,
  homeZipCode: null,
  leadDetails: null,
  leadZipCodeList: null,
  userProfile: null,
}

export const mutations = {
  TOGGLE_VX_AUTH (state) {
    state.vxAuth = !state.vxAuth
    // state.vxAuthDependent = !state.vxAuthDependent
  },
  // TOGGLE_VX_AUTH (state) {
  //   state.vxAuthDependent = state.vxAuth
  // },
  SET_IS_COGNITO_USER_LOGGED_IN (state, newValue) {
    state.isCognitoUserLoggedIn=newValue
  },
  SET_COGNITO_USER (state, newValue) { // SET_COGNITO_SIGN_UP_RETURNS (state, newValue) {
    state.cognitoUser=newValue
  },
  SET_CONFIRMATION_CODE_REQUESTED (state, newValue) {
    state.confirmationCodeRequested = newValue
  },
  SET_REG_ERROR (state, newValue) {
    state.regError = newValue
  },
  SET_CURRENT_USER (state, newValue) {
    state.currentUser = newValue
    saveState('auth.currentUser', newValue)
    setDefaultAuthHeaders(state)
  },
  SET_VALIDATE_TIMER (state, newValue) {
    if (state.validateTimer) {
      clearTimeout(state.validateTimer)
    }
    state.validateTimer = newValue
  },
  SET_SELECTED_HOME_OWNER_TYPES (state, newValue) {
    state.selectedHomeOwnerTypes = newValue
  },
  SET_SELECTED_SERVICE_PROVIDER_TYPE (state, newValue) {
    state.selectedServiceProviderType = newValue
  },
  SET_INTERESTED_ZIP_CODE (state, newValue) {
    state.interestedZipCode = newValue
  },
  SET_HOME_ADDRESS (state, newValue) {
    state.homeAddress = newValue
  },
  SET_HOME_ZIP_CODE (state, newValue) {
    state.homeZipCode = newValue
  },
  SET_LEAD_DETAILS (state, newValue) {
    state.leadDetails = newValue
  },
  SET_LEAD_ZIP_CODE_LIST (state, newValue) {
    state.leadZipCodeList = newValue
  },
  SET_USER_PROFILE (state, newValue) {
    state.userProfile = newValue
  },
}

export const getters = {
  // Whether the user is currently logged in.
  loggedIn (state) {
    return !!state.currentUser
  },
  currentUser (state) {
    return state.currentUser
  },
  user (state) {
    return state.currentUser ? VueJwtDecode.decode(state.currentUser) : null
  },
  username (state) {
    return state.currentUser ? VueJwtDecode.decode(state.currentUser).email : null
  },
  firstName (state) {
    return state.currentUser ? VueJwtDecode.decode(state.currentUser).firstName : null
  },
  lastName (state) {
    return state.currentUser ? VueJwtDecode.decode(state.currentUser).lastName : null
  },
  permissions (state) {
    return state.currentUser ? VueJwtDecode.decode(state.currentUser).permissions : null
  },
  vxAuth (state) {
    console.log('vx: ctrl coming to vxAuth getter..')
    console.log('vx: state.vxAuth', state.vxAuth)
    return state.vxAuth
  },
  vxAuthDependent (state) {
    console.log('vx: ctrl coming to vxAuthDependent getter..')
    console.log('vx: state.vxAuth', state.vxAuth)
    state.vxAuthDependent=state.vxAuth
    console.log('vx: state.vxAuthDependent', state.vxAuthDependent)
    return state.vxAuthDependent
  },
  isCognitoUserLoggedIn (state) {
    return state.isCognitoUserLoggedIn
  //   var thatOneReturns=null
  //   try {
  //     thatOneReturns=await Auth.currentAuthenticatedUser();
  //     console.log('vx: thatOneReturns:', thatOneReturns)
  //     console.log("vx: user is logged in");
  //     state.isCognitoUserLoggedIn=true
  //     console.log('vx: lolz, ctrl coming here, u lose')
  //     return state.isCognitoUserLoggedIn
  // } catch {
  //     console.log('vx: thatOneReturns:', thatOneReturns)
  //     console.log("vx: user is logged out");
  //     state.isCognitoUserLoggedIn=false
  //     console.log('vx: state.isCognitoUserLoggedIn:', state.isCognitoUserLoggedIn)
  //     return state.isCognitoUserLoggedIn
  // }
  },
  cognitoUser (state) {  // cognitoSignUpReturns (state) {
    return state.cognitoUser
  },
  confirmationCodeRequested (state) {
    return state.confirmationCodeRequested
  },
  regError (state) {
    return state.regError
  },
  selectedHomeOwnerTypes (state) {
    return state.selectedHomeOwnerTypes
  },
  selectedServiceProviderType (state) {
    return state.selectedServiceProviderType
  },
  interestedZipCode (state) {
    return state.interestedZipCode
  },
  homeAddress (state) {
    return state.homeAddress
  },
  homeZipCode (state) {
    return state.homeZipCode
  },
  leadDetails (state) {
    return state.leadDetails
  },
  leadZipCodeList (state) {
    return state.leadZipCodeList
  },
  userProfile (state) {
    return state.userProfile
  },
}

export const actions = {
  async cognitoSignUp ({commit}, payload) {
    var username = payload.username
    var password = payload.password
    var email = payload.email
    var phone_number = payload.phone_number
    var first_name=payload.first_name
    var last_name=payload.last_name

      const { user } = await Auth.signUp({
          username,
          password,
          attributes: {
              email,          // optional
              phone_number,   // optional - E.164 number convention
              // other custom attributes 
          }
      });
      console.log(user);
      commit('SET_CONFIRMATION_CODE_REQUESTED', true)
      commit('SET_COGNITO_USER', user)

  },
  async cognitoResendConfirmationCode ({commit}, payload) {
    var username = payload.username
    try {
      await Auth.resendSignUp(username);
      console.log('code resent successfully');
    } catch (err) {
      console.log('error resending code: ', err);
    }
  },
  async cognitoSendForgotPasswordCode ({commit}, payload) {
    var username = payload.username
    Auth.forgotPassword(username)
    .then(data => console.log(data))
    .catch(err => console.log(err));
  },
  async cognitoSendForgotPasswordNewPassword ({commit}, payload) {
    var username = payload.username
    var code = payload.code
    var new_password = payload.password
    try {
      return await Auth.forgotPasswordSubmit(username, code, new_password)
    } catch (err) {
      return Promise.reject(err)
    }
  },
  toggleVxAuth ({commit}) {
    console.log('vx: ctrl coming here')
    commit('TOGGLE_VX_AUTH')
  },
  async checkIsCognitoUserLoggedIn ({commit}) {
    var thatOneReturns=null
    try {
      thatOneReturns=await Auth.currentAuthenticatedUser();
      // console.log('vx: from checkIsCognitoUserLoggedIn action thatOneReturns:', thatOneReturns)
      // console.log("vx: from checkIsCognitoUserLoggedIn action user is logged in");
      commit('SET_IS_COGNITO_USER_LOGGED_IN',true)
      commit('SET_COGNITO_USER', thatOneReturns)
  } catch {
      // console.log('vx: from checkIsCognitoUserLoggedIn action thatOneReturns:', thatOneReturns)
      // console.log("vx: from checkIsCognitoUserLoggedIn action user is logged out");
      commit('SET_IS_COGNITO_USER_LOGGED_IN',false)
  }
},
async cognitoConfirmSignUp ({commit}, payload) {
  // try {
  //   var username = payload.username
  //   var code = payload.code
  //   await Auth.confirmSignUp(username, code);
  //   commit('SET_REG_ERROR', null)
  //   await dispatch cog
  // } catch (error) {
  //     console.log('error confirming sign up', error);
  //     commit('SET_REG_ERROR', error)
  // }
  var username = payload.username
    var code = payload.code
    await Auth.confirmSignUp(username, code)
    commit('SET_CONFIRMATION_CODE_REQUESTED', false)
},
setSelectedHomeOwnerTypes ({commit}, payload) {
  commit('SET_SELECTED_HOME_OWNER_TYPES', payload)
},
setSelectedServiceProviderType ({commit}, payload) {
  commit('SET_SELECTED_SERVICE_PROVIDER_TYPE', payload)
},
setInterestedZipCode ({commit}, payload) {
  commit('SET_INTERESTED_ZIP_CODE', payload)
},
setHomeAddress ({commit}, payload) {
  commit('SET_HOME_ADDRESS', payload)
},
setHomeZipCode ({commit}, payload) {
  commit('SET_HOME_ZIP_CODE', payload)
},
setLeadDetails ({commit}, payload) {
  commit('SET_LEAD_DETAILS', payload)
},
setLeadZipCodeList ({commit}, payload) {
  commit('SET_LEAD_ZIP_CODE_LIST', payload)
},
setUserProfile ({commit}, payload) {
  commit('SET_USER_PROFILE', payload)
},
async checkUserProfile (context) {
  context.commit('SET_USER_PROFILE', context.state.userProfile)
},
async cognitoSignIn ({commit}, payload) {
  const username=payload.username
  const password=payload.password
  const user = await Auth.signIn(username, password);
  // console.log('vx: user from cognitoSignIn method', user)
  commit('SET_COGNITO_USER', user)
  commit('SET_IS_COGNITO_USER_LOGGED_IN',true)
},
  // This is automatically run in `src/state/store.js` when the app
  // starts, along with any other actions named `init` in other modules.
  // init({ state, dispatch }) {
  init ({ commit, state, dispatch }) {
    setDefaultAuthHeaders(state)
    dispatch('validate', { commit, state })
  },

  // Logs in the current user.
  /* es-lint-disable */
  logIn ({ commit }, { username, password } = {}) {
      console.log('auth - logIn')
  // logIn({ commit, dispatch, getters }, { username, password } = {}) {
    // if (getters.loggedIn) return dispatch('validate')
    return axios
      .post('/auth/api/v1/token', { username: username, password: password },
          {
            // withCredentials: true
          })
      .then((response) => {
        // const user = response.data;
        const token = response.data
        const decoded = VueJwtDecode.decode(token)
        const exp = decoded.exp
        const expDate = new Date(0)
        expDate.setUTCSeconds(exp)

        const maxTimeout = Math.ceil((expDate - new Date()) / (1000 * 60))
        const me = this

        commit('SET_VALIDATE_TIMER', setTimeout(function () { me.dispatch('auth/validate') }, (maxTimeout - 1) * 60 * 1000))
        commit('SET_CURRENT_USER', token)

        this.dispatch('auth/setLastLoggedIn')

        return token
      })
  },

  setLastLoggedIn ({ commit, state }) {
    axios.post('/api/v1/set-last-logged-in',
        {
        },
        {
            // withCredentials: true,
            headers: {
                Authorization: 'Bearer ' + state.currentUser,
            },
        })
  },

  /* es-lint-enable */

  setToken ({ commit }, { token } = {}) {
      console.log('auth - setToken')
    console.log(VueJwtDecode.decode(token))
    commit('SET_CURRENT_USER', token)
    return token
  },

  // Logs out the current user.
  // logOut ({ commit }) {
  //     console.log('auth - logOut')
  //   commit('SET_CURRENT_USER', null)
  // },
  async logOut ({ commit }) {
    try {
      await Auth.signOut();
      commit('SET_IS_COGNITO_USER_LOGGED_IN',false)
      commit('SET_COGNITO_USER',null)
      commit('SET_CONFIRMATION_CODE_REQUESTED',false)
      commit('SET_USER_PROFILE', null)
  } catch (error) {
      console.log('error signing out: ', error);
  }
  },

  // Validates the current user's token and refreshes it
  // with new data from the API.
  validate ({ commit, state }) {
    // todo force refresh token when backend changes are made (like permissions)
    if (!state.currentUser) return Promise.resolve(null)
    return axios
        .post('/auth/api/v1/validate-token',
            {
            },
            {
                // withCredentials: true,
                headers: {
                    Authorization: 'Bearer ' + state.currentUser,
                },
            })
        .then((response) => {
            const token = response.data

            const decoded = VueJwtDecode.decode(token)
            const exp = decoded.exp
            const expDate = new Date(0)
            expDate.setUTCSeconds(exp)

            const maxTimeout = Math.ceil((expDate - new Date()) / (1000 * 60))
            const me = this

            commit('SET_VALIDATE_TIMER', setTimeout(function () { me.dispatch('auth/validate') }, (maxTimeout - 1) * 60 * 1000))
            commit('SET_CURRENT_USER', token)

            return state.currentUser ? state.currentUser : null
        })
        .catch((error) => {
            if (error.response && (error.response.status === 401 || error.response.status === 400 || error.response.status === 500)) {
                commit('SET_CURRENT_USER', null)
                // saveState
            } else {
                console.warn(error)
            }
            return null
        })
  },
}

// ===
// Private helpers
// ===

function getSavedState (key) {
  return JSON.parse(window.localStorage.getItem(key))
}

function saveState (key, state) {
  window.localStorage.setItem(key, JSON.stringify(state))
}

function setDefaultAuthHeaders (state) {
  axios.defaults.headers.common.Authorization = state.currentUser
    ? state.currentUser
    : ''
}
