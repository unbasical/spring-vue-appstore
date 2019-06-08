<template>
    <v-container>
        <v-layout row wrap>
            <v-flex v-for="app in applist" :key="app.title" xs12 sm6 md6 lg4>
                <router-link :to="getDetailUrl(app.id)">
                    <small-card :app="app"></small-card>
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
                }).catch(() => Promise.reject('Fehler beim laden aller Apps!'))
        },
        methods: {
            ...mapGetters([
                'getSearch',
                'getTags',
                'getMinimumRating'
            ]),
            getDetailUrl: function (appID) {
                return "/view/" + appID;
            },
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
