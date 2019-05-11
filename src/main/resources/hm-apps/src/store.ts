import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        search: "",
        tags: [""],
        minimumRating: 0,
        user:{
            userid: 0,
            email : "mail",
            username: "default",
            token : "default"
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
