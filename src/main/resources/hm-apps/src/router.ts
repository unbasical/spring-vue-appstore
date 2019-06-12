import Vue from "vue";
import Router from "vue-router";
import Home from "./views/Home.vue";
import Login from "./components/Login.vue";
import Register from "./components/Register.vue";
import DetailedCardView from "./components/DetailedCardView.vue";
import EditCardView from "./components/EditCardView.vue";
import CreateApp from "./components/CreateApp.vue";


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
            component: EditCardView,
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
