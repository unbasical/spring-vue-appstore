<template>
    <v-card color="primary" class="white--text" style="margin: 15px" min-height="75%">
        <v-layout row style="margin: inherit">
            <v-flex lg4>
                <h1>
                    <div v-if="isEditable">
                        <!-- <input type="text" v-model="card.title" placeholder="edit me"> -->
                        <v-text-field
                                label="Outline"
                                v-model="card.title"
                                single-line
                                outline
                        ></v-text-field>
                    </div>
                    <div v-if="!isEditable">
                        <v-card-title>
                            {{this.card.title}}
                        </v-card-title>
                    </div>
                </h1>
                <v-avatar :size="100" style="margin: inherit">
                    <v-img
                            :src="logoUrl"
                            alt="'no logo there'"
                            max-width="125"
                    >
                    </v-img>
                </v-avatar>
                <div v-if="isEditable">
                    <input type="file" name="test" @change="onFileSelected">
                </div>

                <v-container style="margin: inherit">
                    <br>
                    Author : {{this.card.autor}}
                    <br>
                    create date: {{toDate(this.card.createDate)}}
                    <br>
                    last updated: {{toDate(this.card.upDate)}}
                    <br>
                    current Rating: {{this.card.rating}}
                    <br>
                    overall views: {{this.card.views}}
                    <br>
                    tags: {{this.card.tags}}
                    <br>
                </v-container>
                <div v-if="!isEditable">
                    <v-spacer></v-spacer>
                    <router-link :to="getDetailUrl(this.id)" tag="button">
                        <v-btn
                        > Edit my App
                        </v-btn>
                    </router-link>
                </div>
                <div v-if="isEditable">
                    <v-btn @click="onUpload">
                        Save Changes
                    </v-btn>
                </div>
            </v-flex>
            <v-flex lg4 style="margin: inherit">
                <v-card-text>
                    <div v-if="isEditable">
                        <v-textarea
                                outline
                                name="input-7-4"
                                label="Outline textarea"
                                value="Description"
                                v-model="card.description"
                        ></v-textarea>
                    </div>
                    <div v-if="!isEditable">
                        {{this.card.description}}
                    </div>
                </v-card-text>
            </v-flex>
            <v-flex lg4 style="margin: inherit">

                <div v-if="isEditable">
                    <input type="file" multiple @change="onScreenshotFilesSelected">
                </div>

                <div v-if="!isEditable">
                    <v-carousel v-if="this.card.screenshots.length>0">
                        <v-carousel-item
                                v-for="screenshot in this.card.screenshots"
                                :src="getScreenshotUrl(screenshot.id)"
                                reverse-transition="fade"
                                transition="fade"
                        ></v-carousel-item>
                    </v-carousel>
                </div>

            </v-flex>
        </v-layout>
    </v-card>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';
    import router from "../router"

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "DetailedCardView",
        components: {},
        data: () => ({
            selectedFileLogo: null,
            screenshotFiles: [],
            card: {
                title: String,
                description: String,
                views: Number,
                screenshots: [],
                tags: [],
                rating: Number,
                autor: String,
                createDate: Number,
                upDate: Number
            }
        }),
        props: {
            id: String,
            editMode: String,
        },
        computed: {
            isEditable: function () {
                if (this.editMode == 'edit') {
                    return true;
                }
                return false;
            },
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
            onFileSelected(event) {
                this.selectedFileLogo = event.target.files[0]
            },
            onScreenshotFilesSelected(event) {
                this.screenshotFiles = event.target.files
            },
            onUpload() {
                //Upload Image
                if (this.selectedFileLogo != null) {
                    const fd = new FormData();
                    fd.append('file', this.selectedFileLogo)
                    axios.post("/api/users/" + this.getUser().id + "/apps/" + this.idNumber + "/logo", fd,
                        {
                            headers: {
                                'Content-Type': 'multipart/form-data',
                                'Authorization': 'Bearer ' + this.getUser().token
                            },
                        }
                    )
                        .then(res => console.log(res))
                        .catch(err => console.error(err))
                }

                //for (image in this.screenshotFiles) {


                for (let i = 0; i < this.screenshotFiles.length; i++) {
                    const screenData = new FormData();
                    console.log('upload Screenshot: ' + this.screenshotFiles[i].name)
                    screenData.append('file', this.screenshotFiles[i])

                    axios.post("/api/users/" + this.getUser().id + "/apps/" + this.idNumber + "/screenshots", screenData,
                        {
                            headers: {
                                'Authorization': 'Bearer ' + this.getUser().token
                            },
                        }
                    )
                        .catch(err => console.error(err))
                }


                //save Title, Tags, Description
                axios.put("/api/users/" + this.getUser().id + "/apps/" + this.idNumber,
                    {
                        'description': this.card.description,
                        'title': this.card.title,
                        'tags': this.card.tags
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + this.getUser().token
                        },
                    }
                )
                    .then(res => console.log(res))
                    .catch(err => console.error(err))
                router.push('/detailed/view/' + this.idNumber)
            },
            getScreenshotUrl: function (screenID) {
                console.log(this.card.screenshots)
                console.log(screenID)
                return process.env.VUE_APP_BASE_URL + "/api/apps/" + this.idNumber + "/screenshots/" + Number(screenID);
            },
            toDate: function (dateAsLong) {
                return new Date(dateAsLong);
            },
            getDetailUrl: function (appID) {
                return "/detailed/edit/" + appID;
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
                        autor: res.data.author.name,
                        createDate: res.data.creationDate,
                        upDate: res.data.updateDate
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
                            autor: 'Max Mustermann',
                            createDate: Date.now(),
                            upDate: Date.now()
                        }
                    }
                )
        }
    }
</script>

<style scoped>

</style>
