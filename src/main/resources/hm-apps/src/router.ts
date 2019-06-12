import Vue from "vue";
import Router, {Route} from "vue-router";
import Home from "./views/Home.vue";
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import DetailedCardView from "./components/DetailedCardView.vue";
import CreateApp from "./components/CreateApp.vue";
import AllFeedback from "./components/AllFeedbacks.vue";
import GrantPermissions from "./components/GrantPermissions.vue";
import Test from "./components/Test.vue";

Vue.use(Router);

export default new Router({
    mode: "history",
    routes: [
        {
            path: "/userlogin",
            name: "userlogin",
            component: Login
        },
        {
            path: "/register",
            name: "register",
            component: Register
        },
        {
            path: "/",
            name: "home",
            component: Home,
        },
        {
            path: "/edit/:id",
            name: "editapp",
            component: DetailedCardView,
            props: true
        },
        {
            path: "/view/:id",
            name: "viewapp",
            component: DetailedCardView,
            props: true
        },
        {
            path: "/create",
            name: "createapp",
            component: CreateApp,
        },
        {
            path: "/feedbacks",
            name: "showfeedback",
            component: AllFeedback,
        },
        {
            path: "/permissions",
            name: "changepermissions",
            component: GrantPermissions,
        },
        {
            path: "/about",
            name: "about",
            // route level code-splitting
            // this generates a separate chunk (about.[hash].js) for this route
            // which is lazy-loaded when the route is visited.
            component: () =>
                import(/* webpackChunkName: "about" */ "./views/About.vue")
        }
    ]
});
