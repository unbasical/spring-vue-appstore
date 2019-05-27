<template>
    <v-container>
        <v-layout row wrap>

            <v-flex v-for="app in applist" :key="app.title" xs12 sm6 md6 lg4>
                <router-link :to="getDetailUrl(app.id)">
                <small-card
                        v-bind:title="app.title"
                        v-bind:id="app.id"
                        v-bind:description="app.description"
                        v-bind:tags="app.tags"
                        v-bind:logoUrl="app.image"
                        v-bind:rating="app.rating">
                </small-card>
                </router-link>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';
    import SmallCard from "./SmallCard";

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        components: {SmallCard},
        data: () => ({
            apps: [

            ]
        }),
        mounted() {
            axios.get(`/api/apps`)
                .then(res => {
                    this.apps.push(...res.data);
                }).catch(error => {
                console.error("api error:" + error);
            })
        },
        methods: {
            ...mapGetters([
                'getSearch',
                'getTags',
                'getMinimumRating'
            ]),
            getDetailUrl: function (appID) {
                return "/detail/" + appID;
            },
            niceList: function (array, join, finalJoin) {
                if (array.length == 0) {
                    return "";
                }
                var arr = array.slice(0), last = arr.pop();
                join = join || ', ';
                finalJoin = finalJoin || ', ';
                return arr.join(join) + finalJoin + last;
            },
            logoOfApp: function (appID) {
                //TODO: Check how to receive current user
                return axios.get("/api/users/1/apps/" + appID + "/logo").catch(error => console.error(error))

            }
        },
        computed: {
            applist: function () {
                return this.apps
                    .filter(app => app.title.includes(this.getSearch()))
                    .filter(app => Math.round(app.rating) >= this.getMinimumRating())
                    .filter(app => {
                        if (this.getTags().length == 0) {
                            return true;
                        }
                        return this.getTags().some(oneTag => app.tags.includes(oneTag))
                    });
            }
        }
    }
</script>

<style>

</style>
