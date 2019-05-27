<template>
    <v-layout>
        <v-card color="primary" class="white--text" style="margin: 15Px" min-width="100%" min-height="25%">
            <v-layout row>
                <v-flex lg4>
                    <v-card-title>
                        {{card.title}}
                    </v-card-title>
                </v-flex>
                <v-flex lg4>
                    <v-card-text>
                        {{card.description}}
                    </v-card-text>
                </v-flex>
                <v-flex lg4>
                    <v-img
                            v-bind:alt="card.screenshots[0]"
                    >
                    </v-img>
                </v-flex>
            </v-layout>
        </v-card>
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

    </v-layout>
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
            }
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
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
