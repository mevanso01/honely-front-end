import Vue from 'vue'
import Vuex from 'vuex'
import createPersistedState from 'vuex-persistedstate'

// Modules
import modules from './modules'

Vue.use(Vuex)

const store = new Vuex.Store({
  modules,
  // plugins: [createPersistedState({
  //   paths: ['auth']
  // })],
  plugins: [createPersistedState({
    storage: window.sessionStorage,
  })],
  state: {
    currentUser: null,
    vxAuth: false,
    vxAuthDependent: false,
    isCognitoUserLoggedIn: false,
    validateTimer: null,
    cognitoUser: null,
    confirmationCodeRequested: false,
    regError: null,
  },
})

export default store

dispatchActionForAllModules('init')

function dispatchActionForAllModules (
    actionName,
    { modulePrefix = '', flags = {} } = {},
) {
  // For every module...
  for (const moduleName in modules) {
    const moduleDefinition = modules[moduleName]

    // If the action is defined on the module...
    if (moduleDefinition.actions && moduleDefinition.actions[actionName]) {
      // Dispatch the action if the module is namespaced. Otherwise,
      // set a flag to dispatch the action globally at the end.
      if (moduleDefinition.namespaced) {
        store.dispatch(`${modulePrefix}${moduleName}/${actionName}`)
      } else {
        flags.dispatchGlobal = true
      }
    }

    // If there are any nested sub-modules...
    if (moduleDefinition.modules) {
      // Also dispatch the action for these sub-modules.
      dispatchActionForAllModules(actionName, {
        modules: moduleDefinition.modules,
        modulePrefix: modulePrefix + moduleName + '/',
        flags,
      })
    }
  }

  // If this is the root and at least one non-namespaced module
  // was found with the action...
  if (!modulePrefix && flags.dispatchGlobal) {
    // Dispatch the action globally.
    store.dispatch(actionName)
  }
}
//
//
// export default new Vuex.Store({
//   state: {
//   },
//   mutations: {
//   },
//   actions: {
//   },
//   modules: {
//   },
// })
