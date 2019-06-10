import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        search: "",
        tags: [""],
        minimumRating: 0,
        user:{
            id: 0,
            email : "mail",
            name: "default",
            token: null,
        }
    },
    getters: {
        getUser: (state) =>{
            return state.user;
        },
        getSearch: (state) => {
            return state.search;
        },
        getTags: (state) => {
            return state.tags;
        },
        getMinimumRating: (state) => {
            return state.minimumRating;
        },
        isLoggedIn: (state) => {
            return state.user.token;
        },
        userAcronym: (state) => {
            return state.user.name
                .split(' ')
                .reduce((a, b) => a + b.charAt(0), '')
                .substr(0, 2).toUpperCase();
        }

    },
    mutations: {
        setUser(state, user){
            state.user = user;
        },
        setSearch(state, searchString) {
            state.search = searchString
        },
        setTags(state, tagStrings) {
            state.tags = tagStrings
        },
        setMinimumRating(state, minRating) {
            state.minimumRating = minRating
        },
    },
    actions: {}
});
