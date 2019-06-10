<template>
    <form>
        <v-card style="margin-left: 25%;margin-right: 25%;margin-top: 15px">
            <v-text-field
                    v-model="title"
                    label="Title:"
                    required
                    style="margin: 3%;"
            ></v-text-field>
            <v-textarea
                    v-model="description"
                    label="App description"
                    required
                    style="margin: 3%;"
            >
            </v-textarea>
            <v-select
                    :items="allTags"
                    v-model="tags"
                    chips
                    multiple
                    label="Tags"
                    style="margin: 3%;"
            ></v-select>
            <v-layout justify-space-between row style="margin: 3%;">
                Screenshots:
                <input multiple type="file" @change="onScreenshotFilesSelected">
                <v-layout row v-for="pic in screenshotFiles">
                    <v-img :src="getScreenshotUrl(pic)" v-if="pic!=null" contain height="50px"></v-img>
                </v-layout>
            </v-layout>
            <v-layout justify-space-between row style="margin: 3%;">
                Logo:
                <input type="file" @change="onFileSelected">
                <v-img :src="getScreenshotUrl(logo)" v-if="logo!=null" contain height="50px"></v-img>
            </v-layout>
            <v-btn
                    @click="createApp"
                    style="margin: 3%;"
            > Create My APP
            </v-btn>
        </v-card>
    </form>
</template>

<script>
    import axios from "axios";
    import router from "../router"
    import {mapGetters} from 'vuex';

    export default {
        name: "CreateApp",
        data: () => ({
            title: '',
            description: '',
            screenshotFiles: [],
            logo: null,
            allTags: [],
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
            getScreenshotUrl: function (screenID) {
                console.log(screenID)
                return URL.createObjectURL(screenID)
            },
            createApp: function (event) {
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
                        let idofAPP = res.data.id
                        //Save Logo
                        if (this.logo != null) {
                            const fd = new FormData();
                            fd.append('file', this.logo)
                            axios.post("/api/users/" + this.getUser().id + "/apps/" + idofAPP + "/logo",
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
                            console.log('upload Screenshot: ' + this.screenshotFiles[i].name)
                            screenData.append('file', this.screenshotFiles[i])

                            axios.post("/api/users/" + this.getUser().id + "/apps/" + idofAPP + "/screenshots", screenData,
                                {
                                    headers: {
                                        'Authorization': 'Bearer ' + this.getUser().token
                                    },
                                }
                            )
                                .catch(() => Promise.reject('Fehler beim Hochladen des Screenshots!'))
                        }
                    }
                ).catch(err => console.error(err)
                )


            }


        }
    }
</script>

<style scoped>

</style>
