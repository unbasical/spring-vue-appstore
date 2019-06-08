<template>
    <v-card color="cyan darken-2" class="white--text" style="margin: 50px; padding: 50px;">
        <v-layout row wrap>
            <v-flex
            xs2
            align-center
            justify-center
            layout
            text-xs-center
            >
            <v-avatar :tile="false" :size="150" color="grey lighten-4">
                <img :src="logoUrl" alt="avatar">
            </v-avatar>
            </v-flex>
            <v-flex xs10>
                <v-card-title primary-title>
                    <div>
                        <div style="font-size: 4em">{{app.title}}</div>
                        <div style="font-size: 2em">{{app.updateDate | date}}</div>
                    </div>
                </v-card-title>
            </v-flex>
            <v-flex xs7>
                <v-card-title primary-title>
                    <div>
                        <div class="headline">{{app.description}}</div>
                    </div>
                </v-card-title>
            </v-flex>
            <v-flex xs5>
                <v-carousel>
                    <v-carousel-item
                            v-for="(item,i) in app.screenshots"
                            :key="i"
                            :src="screenshotUrlFromId(item.id)"
                    ></v-carousel-item>
                </v-carousel>
            </v-flex>
        </v-layout>
        <v-divider light></v-divider>
        <v-card-actions class="pa-3" style="padding-top: 40px;">
            <v-rating
                    v-model="app.rating"
                    background-color="grey lighten-3"
                    color="red"
                    large
            ></v-rating>
        </v-card-actions>
    </v-card>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "DetailedCardView",
        props: ['id'],
        data: () => ({
            app: {}
        }),
        mounted() {
            axios.get(`/api/apps/${this.id}`)
                .then(res => {
                    this.app = res.data;
                }).catch(() => {
                Promise.reject('Unable to load app');
            });
        },
        computed: {
            logoUrl: function () {
                return process.env.VUE_APP_BASE_URL + '/api/apps/' + this.id + '/logo'
        }
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
            screenshotUrlFromId(screenshotId) {
                return `${process.env.VUE_APP_BASE_URL}/api/apps/${this.id}/screenshots/${screenshotId}`
            }
        }
    }
</script>

<style scoped>

</style>
