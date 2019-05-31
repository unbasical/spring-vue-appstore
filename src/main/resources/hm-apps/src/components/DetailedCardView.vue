<template>
    <!-- <v-container style="margin: 15px"> -->
    <v-card color="primary" class="white--text" style="margin: 15px" min-height="25%">
        <v-layout row style="margin: inherit">
            <v-flex lg4 >
                <h1>
                    <v-card-title>
                        {{this.card.title}}
                    </v-card-title>
                </h1>

                <v-avatar :size="100" style="margin: inherit">
                    <v-img
                            :src="logoUrl"
                            alt="'no logo there'"
                            max-width="125"
                    >
                    </v-img>
                </v-avatar>
                <v-container style="margin: inherit">
                    <br>
                    Author : autor
                    <br>
                    create date: datum
                    <br>
                    last updated: datum
                    <br>
                    current Rating: {{this.card.rating}}
                    <br>
                    overall views: {{this.card.views}}
                    <br>
                    tags: {{this.card.tags}}
                    <br>

                </v-container>


            </v-flex>
            <v-flex lg4 style="margin: inherit">
                <v-card-text>
                    {{this.card.description}}
                    {{this.card.screenshots.length}}
                </v-card-text>
            </v-flex>
            <v-flex lg4 style="margin: inherit">
                <v-carousel v-if="this.card.screenshots.length>0">
                    <v-carousel-item
                            v-for="screenshot in this.card.screenshots"
                            :src="getScreenshotUrl(screenshot.id)"
                            reverse-transition="fade"
                            transition="fade"
                    ></v-carousel-item>
                </v-carousel>
            </v-flex>
        </v-layout>
    </v-card>
    <!-- </v-container> -->
    <!--
    <small-card
            v-bind:title="this.card.title"
            v-bind:id="card.id"
            v-bind:description="card.description"
            v-bind:tags="card.tags"
            v-bind:logoUrl="card.image"
            v-bind:rating="card.rating">
    </small-card>
-->
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';
    import SmallCard from "./SmallCard";

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "DetailedCardView",
        components: {SmallCard},
        data: () => ({
            card: {
                title: String,
                description: String,
                views: Number,
                screenshots: [],
                tags: [],
                rating: Number,
            }
        }),
        props: {
            id: String,
        },
        computed: {
            idNumber: function () {
                // `this` points to the vm instance
                return Number(this.id)
            },
            logoUrl: function () {
                // `this` points to the vm instance
                return process.env.VUE_APP_BASE_URL + '/api/apps/' + this.idNumber + '/logo'
            }
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
            getScreenshotUrl: function (screenID) {
                console.log(this.card.screenshots)
                console.log(screenID)
                return process.env.VUE_APP_BASE_URL + "/api/apps/" + this.idNumber + "/screenshots/" + Number(screenID);
            },
        },
        created: function () {
            console.log('create Card for ID: ' + this.idNumber)
            /* if (this.getUser().token == 'default') {
                console.error('user is not loged In correctly')
            }
            console.log('User: ' + JSON.stringify(this.getUser())) */
            axios.get("/api/apps/" + this.idNumber
                //, {headers: {'Authorization': "bearer " + this.getUser().token}}
            )
                .then(res => {
                    console.log('show app & prepare Fields of Card\n' + res)
                    console.log(...res.data.screenshots)
                    this.card = {
                        title: res.data.title,
                        description: res.data.description,
                        views: res.data.views,
                        screenshots: res.data.screenshots,
                        tags: res.data.tags,
                        rating: res.data.rating,
                    }
                })
                .catch(err => {
                        this.card = {
                            title: 'default',
                            description: 'default description',
                            views: 999,
                            screenshots: [],
                            tags: [],
                            rating: 1,
                        }
                    }
                )
        }
    }
</script>

<style scoped>

</style>
