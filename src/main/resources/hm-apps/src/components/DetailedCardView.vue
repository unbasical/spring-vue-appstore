<template>
    <div>
        <v-card color="cyan darken-2" class="white--text" style="margin: 50px; padding: 50px;">
            <!-- Header -->
            <v-layout row wrap>
                <!-- App Logo -->
                <v-flex xs2 align-center justify-center layout text-xs-center>
                    <v-avatar :tile="false" :size="150" color="grey lighten-4">
                        <img :src="logoUrl" alt="avatar">
                    </v-avatar>
                </v-flex>
                <!-- Title and update date -->
                <v-flex xs10>
                    <v-card-title primary-title>
                        <div>
                            <div style="font-size: 4em">{{app.title}}</div>
                            <div style="font-size: 2em">{{app.updateDate | date}}</div>
                        </div>
                    </v-card-title>
                </v-flex>
            </v-layout>
            <!-- Body -->
            <v-layout row wrap>
                <!-- Description block -->
                <v-flex xs6>
                    <v-card-title primary-title>
                        <div>
                            <div class="headline">{{app.description}}</div>
                        </div>
                    </v-card-title>
                </v-flex>
                <!-- Screenshot carousel -->
                <v-flex xs6>
                    <v-carousel height="400" hide-delimiters>
                        <v-carousel-item
                                v-for="(screenshotUrl,i) in app.screenshots"
                                :key="i">
                            <v-img height="400" contain :src="screenshotUrl"></v-img>
                        </v-carousel-item>
                    </v-carousel>
                </v-flex>
            </v-layout>
            <v-divider light></v-divider>

            <v-card-actions class="pa-3" style="padding-top: 40px;">
                <v-layout row wrap>
                    <v-flex xs3>
                        <v-rating v-model="app.rating" background-color="grey lighten-3" color="red" large/>
                    </v-flex>
                    <v-flex xs9>

                    </v-flex>
                </v-layout>
            </v-card-actions>
        </v-card>
        <Comments :appId="id"/>
    </div>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';
    import Comments from './Comments';

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "DetailedCardView",
        components: {Comments},
        props: ['id'],
        data: () => ({
            app: {}
        }),
        mounted() {
            axios.get(`/api/apps/${this.id}`)
                .then(res => {
                    this.processScreenshots(res.data);
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
            processScreenshots(app) {
                if (!app.screenshots || app.screenshots.length == 0) {
                    app.screenshots = [
                        'https://upload.wikimedia.org/wikipedia/commons/3/35/Roter_W%C3%BCrfel.jpg',
                        'https://upload.wikimedia.org/wikipedia/commons/6/64/Hochschule_Muenchen_Ansicht_Lothstrasse.jpg',
                        'https://cdn.weka-fachmedien.de/thumbs/media_uploads/images/1511264229-283-worrsfuwh.jpg.627x353.jpg',
                    ];
                } else {
                    app.screenshots = app.screenshots.map(s => `${process.env.VUE_APP_BASE_URL}/api/apps/${this.id}/screenshots/${s.id}`);
                }
            },
        }
    }
</script>

<style scoped>

</style>
