<template>
    <v-container>
        <v-list two-line subheader>
            <v-list-tile
                    v-for="item in feedback"
                    :key="item.subject"
                    @click=""
            >
                <v-list-tile-content style="margin: 10px">
                    <v-list-tile-title>{{ item.subject }}</v-list-tile-title>
                    <v-list-tile-sub-title>{{ item.content}}</v-list-tile-sub-title>
                </v-list-tile-content>
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
            feedback: []
        }),
        mounted() {
            axios.get("/api/feedback", {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.getUser().token
                },
            })
                .then(res => this.feedback = res.data)
                .catch(err => console.error(err))
        },
        methods: {
            ...mapGetters([
                'getUser'
            ])
        }
    }
</script>

<style scoped>

</style>