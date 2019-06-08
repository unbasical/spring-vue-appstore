import Vue from "vue";
import './plugins/vuetify'
import App from "./App.vue";
import router from "./router";
import store from "./store";
import moment from 'moment'

Vue.config.productionTip = false;
new Vue({
    router,
    store,
    render: h => h(App)
}).$mount("#app");

Vue.filter('date', function(value: any) {
    if (value) {
        console.log(value);
        return moment(value).format('DD.MM.YYYY hh:mm')
    }
});


