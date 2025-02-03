// client/store/index.js
import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

// Mock user database
const MOCK_USERS = [
  {
    id: 1,
    email: 'test@habitforge.com',
    password: 'password123',
    name: 'admin'
  }
]

export default new Vuex.Store({
  state: {
    user: null,
    isAuthenticated: false,
    authError: null
  },
  mutations: {
    setUser(state, user) {
      state.user = user
      state.isAuthenticated = !!user
    },
    setAuthError(state, error) {
      state.authError = error
    }
  },
  actions: {
    login({ commit }, { email, password }) {
      // Simulate async login
      return new Promise((resolve, reject) => {
        // Mock authentication
        const user = MOCK_USERS.find(
          u => u.email === email && u.password === password
        )

        if (user) {
          // Successful login
          const { password: _, ...userWithoutPassword } = user
          commit('setUser', userWithoutPassword)
          commit('setAuthError', null)
          resolve(userWithoutPassword)
        } else {
          // Failed login
          commit('setUser', null)
          commit('setAuthError', 'Invalid email or password')
          reject(new Error('Invalid credentials'))
        }
      })
    },
    logout({ commit }) {
      commit('setUser', null)
    }
  },
  getters: {
    isAuthenticated: state => state.isAuthenticated,
    currentUser: state => state.user,
    authError: state => state.authError
  }
})
// Automatically resolves to './router/index.js', hence why we named this file index.js