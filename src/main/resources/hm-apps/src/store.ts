import Vue from "vue";
import Vuex from "vuex";

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        search: "",
        tags: [""],
        minimumRating: 0,
    },
    getters: {
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
