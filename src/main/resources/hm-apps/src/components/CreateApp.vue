<template>
    <form>
        <v-card style="margin-left: 25%;margin-right: 25%">
            <v-text-field
                    v-model="title"
                    label="Title:"
                    required
            ></v-text-field>
            <v-textarea
                    v-model="description"
                    label="App description"
                    required
            >
            </v-textarea>
            <v-select
                    :items="tags"
                    label="Tags"
            ></v-select>
            <v-layout justify-space-between row>
                Screenshots:
                <input type="file" @change="onScreenshotFilesSelected">
                <!--
                <v-list v-for="pic in screenshotFiles">
                    <v-list-tile>
                        <v-img :src="getScreenshotUrl(pic)" v-if="pic!=null" contain height="50px"></v-img>
                    </v-list-tile>
                </v-list>
                -->
            </v-layout>
            <v-layout justify-space-between row>
                Logo:
                <input type="file" @change="onFileSelected">
                <!--
                <v-img :src="getScreenshotUrl(logo)" v-if="logo!=null" contain height="50px"></v-img>
                -->
            </v-layout>
            <v-btn
                    @click="createApp"
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
            items: [],
            screenshotFiles: [],
            logo: null,
            tags: []
        }),
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
                return this.getUser().id + "/apps"
            },
            getScreenshotUrl: function (screenID) {
                console.log(screenID)
                return URL.createObjectURL(screenID)
            }, createApp: function (event) {
                //create APP code
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
                )
            }


        }
    }
</script>

<style scoped>

</style>