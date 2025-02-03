import Vue from 'vue';
import App from './App.vue';  // Main Vue component
import router from './router'; 
import store from './store';    // Vuex store for state management (if using it)
import '../assets/global.css';

Vue.config.productionTip = false;  // Prevents the production tip in console

new Vue({
  router,  // Integrating Vue Router
  store,   // Integrating Vuex Store
  render: h => h(App),  // Rendering the main App component
}).$mount('#app');  // Mounting the Vue instance to the DOM element with id 'app'
