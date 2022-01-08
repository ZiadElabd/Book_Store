import Vue from 'vue'
import App from './App.vue'
import { store } from './store/store';
import { BootstrapVue, IconsPlugin } from 'bootstrap-vue';

import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import locale from 'element-ui/lib/locale/lang/en'
import 'bootstrap/dist/css/bootstrap.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
Vue.use(ElementUI, { locale });


import 'bootstrap/dist/css/bootstrap.min.css'
import '@/assets/css/main.css'
import router from './router'
import { library } from '@fortawesome/fontawesome-svg-core'
import { faHome,faQuestionCircle,faUserFriends,faBolt,faCog,faPlusSquare } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { FormTextareaPlugin } from 'bootstrap-vue'
library.add(faHome,faQuestionCircle,faUserFriends,faBolt,faCog,faPlusSquare )

Vue.component('font-awesome-icon', FontAwesomeIcon)
Vue.config.productionTip = false
Vue.use(FormTextareaPlugin)
Vue.use(BootstrapVue)
Vue.use(IconsPlugin)

//import mixin from './mixin.js'
//Vue.use(mixin);
export const bus = new Vue();

new Vue({
  router,
  store: store,
  beforeCreate() { this.$store.commit('initialiseStore');},
  render: h => h(App)
}).$mount('#app')
