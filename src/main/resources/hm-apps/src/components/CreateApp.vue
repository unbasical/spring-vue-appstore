<template>
    <v-stepper v-model="stage" style="margin-left: 25%;margin-right: 25%;margin-top: 15px">
        <v-stepper-header>
            <v-stepper-step :complete="stage > 1" step="1">App Beschreiben</v-stepper-step>

            <v-divider></v-divider>

            <v-stepper-step :complete="stage > 2" step="2">Tags hinzufügen</v-stepper-step>

            <v-divider></v-divider>

            <v-stepper-step step="3">Bilder hochladen</v-stepper-step>
        </v-stepper-header>

        <v-stepper-items>
            <v-stepper-content step="1">
                <v-card
                        class="mb-5"
                        color="grey lighten-3"
                        height="500px"
                        style="padding: 30px;"
                >
                    <form>
                        <v-text-field
                                v-model="title"
                                label="Titel:"
                                required
                                style="margin: 3%;"
                        ></v-text-field>
                        <v-textarea
                                v-model="description"
                                label="Beschreibung"
                                required
                                style="margin: 3%;"
                        >
                        </v-textarea>
                    </form>
                </v-card>

                <v-btn
                        color="primary"
                        @click="createApp"
                >
                    Weiter
                </v-btn>
            </v-stepper-content>

            <v-stepper-content step="2">
                <v-card
                        class="mb-5"
                        color="grey lighten-3"
                        height="500px"
                        style="padding: 30px;"
                >
                    <form>
                        <v-layout row>
                            <v-text-field
                                    no-data-text="add your own Tag"
                                    v-model="newTag"
                                    :items="allTags"
                            >
                            </v-text-field>

                            <v-btn flat @click="addTagToTags()">
                                <v-icon>add</v-icon>
                            </v-btn>
                        </v-layout>

                        <v-select
                                :items="allTags"
                                v-model="tags"
                                chips
                                multiple
                                label="Tags"
                                style="margin: 3%;"
                        ></v-select>
                    </form>
                </v-card>

                <v-btn
                        color="primary"
                        @click="addTagsToApp"
                >
                    Weiter
                </v-btn>

            </v-stepper-content>

            <v-stepper-content step="3">
                <v-card
                        class="mb-5"
                        color="grey lighten-3"
                        height="500px"
                        style="padding: 30px;"
                >
                    <form>
                        <v-layout justify-space-between row style="margin: 3%;">
                            Logo:
                            <input type="file" @change="onFileSelected">
                            <v-img :src="getScreenshotUrl(logo)" v-if="logo!=null" contain height="50px"></v-img>
                        </v-layout>
                        <v-layout justify-space-between row style="margin: 3%;">
                            Screenshots:
                            <input multiple type="file" @change="onScreenshotFilesSelected">
                            <v-layout row v-for="pic in screenshotFiles">
                                <v-img :src="getScreenshotUrl(pic)" v-if="pic!=null" contain height="50px"></v-img>
                            </v-layout>
                        </v-layout>
                    </form>
                </v-card>

                <v-btn
                        color="primary"
                        @click="uploadFiles()"
                >
                    Los gehts!
                </v-btn>
            </v-stepper-content>
        </v-stepper-items>
    </v-stepper>

</template>

<script>
    import axios from "axios";
    import router from "../router"
    import {mapGetters} from 'vuex';

    export default {
        name: "CreateApp",
        data: () => ({
            stage: 1,
            appId: null,
            title: '',
            description: '',
            screenshotFiles: [],
            logo: null,
            allTags: [],
            newTag: '',
            tags: []
        }),
        mounted() {
            axios.get(`/api/tags`)
                .then(res => {
                    this.allTags.push(...res.data);
                }).catch(error => {
                console.error("api error:" + error);
            });
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
            onScreenshotFilesSelected(event) {
                this.screenshotFiles = event.target.files
            },
            onFileSelected(event) {
                this.logo = event.target.files[0]
            },
            getCreateUrl: function () {
                return "/api/users/" + this.getUser().id + "/apps"
            },
            getUpdateUrl: function (id) {
                return "/api/users/" + this.getUser().id + "/apps/" + id
            },
            getScreenshotUrl: function (screenID) {
                return URL.createObjectURL(screenID)
            },
            createApp: function () {
                axios.post(this.getCreateUrl(),
                    {
                        'description': this.description,
                        'title': this.title,
                        'tags': this.tags
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + this.getUser().token
                        },
                    }
                ).then(res => {
                    this.appId = res.data.id;
                    this.stage = 2;
                })
                    .catch(() => Promise.reject('Die App konnte nicht erstellt werden!'))
            },
            addTagsToApp: function (){
                axios.put(this.getUpdateUrl(this.appId),
                    {
                        'description': this.description,
                        'title': this.title,
                        'tags': this.tags
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + this.getUser().token
                        },
                    }
                ).then(res => {
                    this.stage = 3;
                })
                    .catch(() => Promise.reject('Die tags konnten nicht hinzugefügt werden!'))
            },
            uploadFiles: function () {
                //Save Logo
                if (this.logo != null) {
                    const fd = new FormData();
                    fd.append('file', this.logo);
                    axios.post("/api/users/" + this.getUser().id + "/apps/" + this.appId + "/logo",
                        fd,
                        {
                            headers: {
                                'Content-Type': 'multipart/form-data',
                                'Authorization': 'Bearer ' + this.getUser().token
                            },
                        }
                    )
                        .then(() => router.push({name: 'home'}))
                        .catch(() => Promise.reject('Fehler beim Hochladen des des Logos!'))
                }


                //Save screenshots
                for (let i = 0; i < this.screenshotFiles.length; i++) {
                    const screenData = new FormData();
                    screenData.append('file', this.screenshotFiles[i]);

                    axios.post("/api/users/" + this.getUser().id + "/apps/" + this.appId + "/screenshots", screenData,
                        {
                            headers: {
                                'Authorization': 'Bearer ' + this.getUser().token
                            },
                        }
                    )
                        .catch(() => Promise.reject('Fehler beim Hochladen des Screenshots!'))
                }


            },
            addTagToTags: function (event) {
                this.tags.push(this.newTag)
                this.allTags.push(this.newTag)
                this.newTag = ''
            }
        }
    }
</script>

<style scoped>

</style>
