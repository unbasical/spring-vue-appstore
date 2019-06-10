<template>
    <v-card>
        <!-- Header -->
        <v-card-title><h2>Kommentare</h2></v-card-title>
        <!-- Body -->
        <v-layout row align-content-center v-if="isLoggedIn()">
            <!-- Description block -->
            <v-flex xs9 offset-xs1>
                <v-form v-model="valid">
                    <v-textarea
                            outline
                            v-model="newComment"
                            :rules="newCommentRules"
                            :counter="300"
                            :label="getUser().name + ':'"
                            required>
                    </v-textarea>
                </v-form>
            </v-flex>
            <v-flex xs1 offset-xs1>
                <v-btn flat icon @click="sendComment">
                    <v-icon :size="90">send</v-icon>
                </v-btn>
            </v-flex>
        </v-layout>
        <v-divider light></v-divider>
        <!-- Existing comments -->
        <v-layout row wrap>
            <v-flex xs8 offset-xs2>
                <v-list three-line>
                    <template v-for="(comment, index) in comments">
                        <v-divider v-if="index != 0" inset></v-divider>
                        <v-list-tile :key="comment.id" avatar>
                            <v-layout row wrap>
                                <v-flex xs1>
                                    <v-avatar color="red">
                                        <span class="white--text headline">{{toAcronym(comment.author.name)}}</span>
                                    </v-avatar>
                                </v-flex>
                                <v-flex xs10>
                                    <v-list-tile-content>
                                        <v-list-tile-title v-html="comment.author.name"></v-list-tile-title>
                                        <v-list-tile-sub-title v-html="comment.text"></v-list-tile-sub-title>
                                    </v-list-tile-content>
                                </v-flex>
                            </v-layout>
                        </v-list-tile>
                    </template>
                </v-list>
            </v-flex>
        </v-layout>
    </v-card>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "Comments",
        props: ['appId'],
        data: () => ({
            valid: false,
            newComment: '',
            newCommentRules: [
                v => !!v || 'Sie können kein leeres Kommentar senden!',
                v => v.length <= 300 || 'Das Kommentar darf nicht mehr als 300 Zeichen haben'
            ],
            comments: [],
        }),
        mounted() {
            axios.get(`/api/apps/${this.appId}/comments`)
                .then(res => {
                    this.comments.push(...res.data);
                }).catch(() => {
                Promise.reject('Unable to load Comments');
            });
        },
        computed: {},
        methods: {
            ...mapGetters([
                'getUser',
                'isLoggedIn',
                'userAcronym'
            ]),
            sendComment() {
                if (!this.valid)
                    return;

                axios.post(`/api/apps/${this.appId}/comments`, {
                        author: this.getUser(),
                        text: this.newComment
                    },
                    {
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': 'Bearer ' + this.getUser().token
                        },
                    })
                    .then((res) => {
                        this.comments.unshift(res.data);
                        this.newComment = '';
                    })
                    .catch(err => Promise.reject("Fehler beim Senden des Kommentars!"))

            },
            getAvatar(comment) {
                return 'https://www.winhelponline.com/blog/wp-content/uploads/2017/12/user.png';
            },
            toAcronym(username) {
                return username
                    .split(' ')
                    .reduce((a, b) => a + b.charAt(0), '')
                    .substr(0, 2).toUpperCase();
            }
        }
    }
</script>

<style scoped>

</style>
