import { set, toggle } from '@/utils/vuex'

export default {
  namespaced: true,
  state: {
    links: [
      ['/logout', 'log out'],
    ],
    drawer: null,
  },
  mutations: {
    setDrawer: set('drawer'),
    toggleDrawer: toggle('drawer'),
  },
}
