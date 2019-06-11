<template>
    <v-card :color="background.LightVibrant" style="padding: 50px">
        <v-card :color="background.DarkVibrant" class="white--text" style="padding: 50px;">
            <!-- Header -->
            <v-layout row wrap>
                <!-- App Logo -->
                <v-flex xs2 align-center justify-center layout text-xs-center>
                    <v-badge right>
                        <template v-slot:badge>
                            <span>{{app.views}} Views</span>
                        </template>
                        <v-avatar :tile="false" :size="150" :color="background.LightVibrant">
                            <img :src="logoUrl" alt="avatar">
                        </v-avatar>
                    </v-badge>
                </v-flex>
                <!-- Title and update date -->
                <v-flex xs8>
                    <v-card-title primary-title>
                        <div>
                            <div style="font-size: 4em">{{app.title}}</div>
                            <div style="font-size: 2em">{{app.updateDate | date}}</div>
                        </div>
                    </v-card-title>
                </v-flex>
                <v-flex xs2>
                    <v-layout v-if="app.author">
                        <v-flex xs3>
                            <v-avatar :color="background.LightMuted">
                                <span class="white--text headline">{{toAcronym(app.author.name)}}</span>
                            </v-avatar>
                        </v-flex>
                        <v-flex xs9 align-self-end>
                            <h2>{{app.author.name}}</h2>
                            <h4>{{app.author.email}}</h4>
                        </v-flex>
                    </v-layout>
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
                    <v-carousel height="400" hide-delimiters hide-controls>
                        <v-carousel-item
                                v-for="(screenshotUrl,i) in app.screenshots"
                                :key="i">
                            <v-img height="400" contain :src="screenshotUrl"></v-img>
                        </v-carousel-item>
                    </v-carousel>
                </v-flex>
            </v-layout>
            <v-divider light style="margin-top: 20px;"></v-divider>

            <v-card-actions class="pa-3" style="padding-top: 40px;">
                <v-layout row wrap>
                    <v-flex xs3>
                        <div @click="ratingChanged = true">
                            <v-rating v-model="app.rating" :background-color="background.LightMuted"
                                      :color="background.Vibrant" large/>
                        </div>
                        <v-btn v-if="ratingChanged"
                               :disabled="!isLoggedIn()"
                               color="blue-grey"
                               class="white--text"
                               @click="rate"
                        >
                            Bewerten
                            <v-icon right dark>send</v-icon>
                        </v-btn>
                    </v-flex>
                    <v-flex xs1 offset-xs8>
                        <div v-if="userRole() === 'ADMIN' || userRole() === 'DEVELOPER'">
                            <v-btn color="blue-grey"
                                   class="white--text"
                                   :to="`/edit/${id}`"
                                   style="margin-bottom: 30px"
                            >
                                Bearbeiten
                                <v-icon right dark>edit</v-icon>
                            </v-btn>
                            <v-btn color="blue-grey"
                                   class="white--text"
                                   @click="deleteApp"
                            >
                                Löschen
                                <v-icon right dark>delete</v-icon>
                            </v-btn>
                        </div>
                    </v-flex>
                </v-layout>
            </v-card-actions>
        </v-card>
        <Comments :appId="id" :background="background"/>
    </v-card>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';
    import Comments from './Comments';
    import * as Vibrant from 'node-vibrant'

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "DetailedCardView",
        components: {Comments},
        props: ['id'],
        data: () => ({
            ratingChanged: false,
            app: {},
            background: {
                Vibrant: 'white',
                Muted: 'white',
                LightVibrant: 'white',
                DarkVibrant: 'white',
                DarkMuted: 'white',
                LightMuted: 'white'
            },
        }),
        mounted() {
            axios.get(`/api/apps/${this.id}`)
                .then(res => {
                    this.processScreenshots(res.data);
                    this.app = res.data;
                    this.setBackground();
                }).catch(() => {
                Promise.reject('Unable to load app');
            });
        },
        computed: {
            logoUrl: function () {
                return process.env.VUE_APP_BASE_URL + '/api/apps/' + this.id + '/logo'
            },
        },
        methods: {
            ...mapGetters([
                'getUser',
                'userAcronym',
                'isLoggedIn',
                'userRole'
            ]),
            setBackground() {
                Vibrant.from(this.logoUrl).getPalette()
                    .then((palette) => {
                        Object.keys(palette).forEach(key => this.background[key] = palette[key].hex);
                    });
            },
            deleteApp() {
                axios.delete(`/api/users/${this.app.author.id}/apps/${this.id}`,
                    {
                        headers: {
                            'Authorization': 'Bearer ' + this.getUser().token
                        },
                    })
                    .then((res) => {
                        router.push({name: 'home'});
                    })
                    .catch(err => Promise.reject("Fehler beim Löschen der App!"))
            },
            processScreenshots(app) {
                if (!app.screenshots || app.screenshots.length === 0) {
                    app.screenshots = [
                        'https://upload.wikimedia.org/wikipedia/commons/3/35/Roter_W%C3%BCrfel.jpg',
                        'https://upload.wikimedia.org/wikipedia/commons/6/64/Hochschule_Muenchen_Ansicht_Lothstrasse.jpg',
                        'https://cdn.weka-fachmedien.de/thumbs/media_uploads/images/1511264229-283-worrsfuwh.jpg.627x353.jpg',
                    ];
                } else {
                    app.screenshots = app.screenshots.map(s => `${process.env.VUE_APP_BASE_URL}/api/apps/${this.id}/screenshots/${s.id}`);
                }
            },
            toAcronym(username) {
                return username
                    .split(' ')
                    .reduce((a, b) => a + b.charAt(0), '')
                    .substr(0, 2).toUpperCase();
            },
            rate() {
                this.ratingChanged = false;
                axios.post(`/api/apps/${this.id}/ratings`, {
                        author: this.getUser(),
                        stars: this.app.rating
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + this.getUser().token
                        },
                    })
                    .then((res) => {
                        this.app.rating = res.data;
                    })
                    .catch(err => Promise.reject("Fehler beim Senden des Ratings!"))
            }
        }
    }
</script>

<style scoped>

</style>
