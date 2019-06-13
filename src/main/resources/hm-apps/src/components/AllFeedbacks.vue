<template>
    <v-container>
        <v-list two-line subheader>
            <v-list-tile
                    v-for="feedback in feedbacks"
                    :key="feedback.subject"
                    @click=""

            >
                <v-layout row>
                    <v-list-tile-content style="margin: 10px">
                        <v-list-tile-title>{{ feedback.subject }}</v-list-tile-title>
                        <v-list-tile-sub-title>{{ feedback.content}}</v-list-tile-sub-title>
                    </v-list-tile-content>
                    <v-list-tile-action>
                        <v-btn @click="removeFeedback(feedback)">
                            <v-icon>delete</v-icon>
                        </v-btn>
                    </v-list-tile-action>
                </v-layout>
            </v-list-tile>
        </v-list>
    </v-container>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';

    export default {
        name: "AllFeedbacks",
        data: () => ({
            feedbacks: []
        }),
        mounted() {
            axios.get("/api/feedback", {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.getUser().token
                },
            })
                .then(res => this.feedbacks = res.data)
                .catch(err => console.error(err))
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
            removeFeedback: function (feedbackobj) {
                axios.delete("/api/feedback/" + feedbackobj.id, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + this.getUser().token
                    },
                })
                    .then(res => {
                        var index = this.feedbacks.indexOf(feedbackobj);
                        if (index > -1) {
                            this.feedbacks.splice(index, 1);
                        }
                    })
                    .catch(err => console.error(err))

            }
        }
    }
</script>

<style scoped>

</style>
