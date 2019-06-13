<template>
    <v-card :color="background.LightVibrant" style="padding: 50px">
        <v-card :color="background.DarkVibrant" class="white--text" style="padding: 50px;">
            <!-- Header -->
            <v-layout row wrap>
                <!-- App Logo -->
                <v-flex xs2 align-center justify-center layout text-xs-center>
                    <v-badge right>
                        <template v-slot:badge>
                            <v-btn fab color="success" @click="$refs.logoUpload.click()">
                                <v-icon dark>edit</v-icon>
                            </v-btn>
                            <input v-show="false" ref="logoUpload" type="file"
                                   @change="logo = $event.target.files[0]; setBackground()">
                        </template>
                        <v-avatar :tile="false" :size="150" :color="background.LightVibrant">
                            <img :src="logoUrl" alt="avatar" style="background-color: white;">
                        </v-avatar>
                    </v-badge>
                </v-flex>
                <!-- Title and update date -->
                <v-flex xs8>
                    <v-card-title primary-title>
                        <div>
                            <v-text-field style="min-width: 500px;"
                                          :background-color="background.LightVibrant"
                                          v-model="app.title"
                                          label="Solo textarea"
                                          single-line
                                          @click="app.dirty = true"
                            ></v-text-field>
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
                            <v-textarea style="min-width: 700px;"
                                        full-width auto-grow
                                        :background-color="background.LightVibrant"
                                        name="input-7-1"
                                        label="Solo textarea"
                                        v-model="app.description"
                                        @click="app.dirty = true"
                            ></v-textarea>
                        </div>
                    </v-card-title>
                </v-flex>
                <!-- Screenshot carousel -->
                <v-flex xs6>
                    <v-container grid-list-sm fluid>
                        <v-layout row wrap>
                            <v-flex
                                    v-for="(screenshotUrl,i) in screenshotUrls"
                                    :key="i"
                                    xs4
                                    d-flex
                            >
                                <v-card flat tile class="d-flex" style="margin: 10px">
                                    <v-btn
                                            fab
                                            small
                                            color="red accent-2"
                                            top
                                            right
                                            absolute
                                            @click="deleteScreenshot(i)"
                                    >
                                        <v-icon>delete</v-icon>
                                    </v-btn>
                                    <v-img contain
                                           :src="screenshotUrl"
                                           aspect-ratio="1"
                                           class="grey lighten-2"
                                    >
                                    </v-img>
                                </v-card>
                            </v-flex>
                            <v-flex xs4 d-flex>
                                <div>
                                    <v-btn fab color="success" @click="$refs.screenshotUpload.click()" class="d-flex">
                                        <v-icon dark>add</v-icon>
                                    </v-btn>
                                    <input multiple v-show="false" ref="screenshotUpload" type="file"
                                           @change="addScreenshot">
                                </div>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-flex>
            </v-layout>
            <v-divider light style="margin-top: 20px;"></v-divider>

            <v-card-actions class="pa-3" style="padding-top: 40px;">
                <v-layout row wrap>
                    <v-flex xs1 offset-xs10>
                        <div v-if="userRole() === 'ADMIN' || userRole() === 'DEVELOPER'">
                            <v-btn color="success"
                                   class="white--text"
                                   style="margin-bottom: 30px"
                                   @click="commit()"
                            >
                                Fertig
                                <v-icon right dark>edit</v-icon>
                            </v-btn>
                        </div>
                    </v-flex>
                </v-layout>
            </v-card-actions>
        </v-card>
    </v-card>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';
    import Comments from './Comments';
    import * as Vibrant from 'node-vibrant';
    import router from "../router"
    import {EMPTY, forkJoin, from} from 'rxjs';
    import {filter, flatMap, tap} from 'rxjs/operators';

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "DetailedCardView",
        components: {Comments},
        props: ['id'],
        data: () => ({
            ratingChanged: false,
            app: {},
            logo: null,
            screenshots: [],
            screenshotIdsToDelete: [],
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
                    this.app = res.data;
                    this.setBackground();
                }).catch(() => {
                Promise.reject('Unable to load app');
            });
        },
        computed: {
            logoUrl: function () {
                if (!this.logo) {
                    return process.env.VUE_APP_BASE_URL + '/api/apps/' + this.id + '/logo';
                } else {
                    return URL.createObjectURL(this.logo);
                }
            },
            screenshotUrls: function () {
                const urls = [];
                if (this.app.screenshots && this.app.screenshots.length > 0) {
                    urls.push(...this.app.screenshots.map(s => `${process.env.VUE_APP_BASE_URL}/api/apps/${this.id}/screenshots/${s.id}`));
                }

                // Add local images
                urls.push(...this.screenshots.map(s => URL.createObjectURL(s)));
                return urls;
            },
        },
        methods: {
            ...mapGetters([
                'getUser',
                'userAcronym',
                'isLoggedIn',
                'userRole',
                'getRequestHeader'
            ]),
            setBackground() {
                Vibrant.from(this.logoUrl).getPalette()
                    .then((palette) => {
                        Object.keys(palette).forEach(key => this.background[key] = palette[key].hex);
                    });
            },
            addScreenshot(event) {
                this.screenshots.push(...event.target.files);
            },
            deleteScreenshot(index) {
                // Delete local Screenshot
                const border = this.app.screenshots.length;
                if (this.app.screenshots && index >= border) {
                    this.screenshots.splice(index - border, 1);
                }
                // Delete remote screenshot
                else {
                    // Mark for deletion
                    this.screenshotIdsToDelete.push(this.app.screenshots[index].id);
                    // Delete local
                    this.app.screenshots.splice(index, 1);
                }
            },
            commit() {
                forkJoin(this.uploadApp(), this.uploadLogo(), this.deleteMarkedScreenshots(), this.uploadNewScreenshots())
                    .subscribe(res => {
                    }, err => Promise.reject('Fehler beim Aktualisieren der App!'), () => router.push({name: 'home'}));
            },
            uploadLogo() {
                if (this.logo != null) {
                    const fd = new FormData();
                    fd.append('file', this.logo);
                    return from(axios.post("/api/users/" + this.getUser().id + "/apps/" + this.id + "/logo", fd, this.getRequestHeader('multipart/form-data')))
                        .pipe(tap(() => this.setBackground()))
                }
                return EMPTY;
            },
            uploadApp() {
                if (this.app.dirty) {
                    return from(axios.put("/api/users/" + this.getUser().id + "/apps/" + this.id, {
                        title: this.app.title,
                        description: this.app.description,
                    }, this.getRequestHeader()));
                }
                return EMPTY;
            },
            deleteMarkedScreenshots() {
                return from(this.screenshotIdsToDelete)
                    .pipe(
                        flatMap(screenshotId => from(axios.delete(`/api/users/${this.getUser().id}/apps/${this.id}/screenshots/${screenshotId}`, this.getRequestHeader())))
                    );
            },
            uploadNewScreenshots() {
                return from(this.screenshots).pipe(
                    filter(s => !('id' in s)),
                    flatMap(s => {
                        const screenData = new FormData();
                        screenData.append('file', s);
                        return from(axios.post(`/api/users/${this.getUser().id}/apps/${this.id}/screenshots`, screenData, this.getRequestHeader('multipart/form-data')));
                    })
                );
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
                }, this.getRequestHeader())
                    .then((res) => {
                        this.app.rating = res.data;
                    })
                    .catch(err => Promise.reject("Fehler beim Senden des Ratings!"))
            },
            getRequestHeader(contentType) {
                const type = contentType ? contentType : 'application/json';
                return {
                    headers: {
                        'Content-Type': type,
                        'Authorization': 'Bearer ' + this.getUser().token
                    },
                }
            },
        }
    }
</script>

<style scoped>
</style>
