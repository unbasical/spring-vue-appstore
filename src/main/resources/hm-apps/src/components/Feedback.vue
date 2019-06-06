<template>
    <v-dialog
            v-model="dialog"
            width="500"
    >
        <template v-slot:activator="{ on }">
            <v-btn class="feedback-button"
                   color="green lighten-2"
                   dark
                   v-on="on"
            >
                Feedback
            </v-btn>
        </template>

        <v-card>
            <v-card-title
                    class="headline grey lighten-2"
                    primary-title
            >
                Tragen Sie hier Ihr Feedback ein:
            </v-card-title>

            <v-card-text>
                <v-form v-model="valid">
                    <v-container>
                        <v-layout row wrap>
                            <v-flex lg6>
                                <v-text-field
                                        v-model="subject"
                                        :rules="subjectRules"
                                        :counter="100"
                                        label="Subject"
                                        required
                                ></v-text-field>
                            </v-flex>
                            <v-flex lg12>
                                <v-textarea
                                        v-model="content"
                                        :rules="contentRules"
                                        :counter="300"
                                        label="Content"
                                        required
                                ></v-textarea>
                            </v-flex>
                        </v-layout>
                    </v-container>
                </v-form>
            </v-card-text>

            <v-divider></v-divider>

            <v-card-actions>
                <v-spacer></v-spacer>
                <v-btn
                        color="primary"
                        flat
                        @click="sendFeedback()"
                >
                    Send
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
    import axios from "axios"

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "Feedback",
        data: () => ({
            valid: false,
            dialog: false,
            subject: '',
            content: '',
            subjectRules: [
                v => !!v || 'Subject is required',
                v => v.length <= 100 || 'Subject must be less than 100 characters'
            ],
            contentRules: [
                v => !!v || 'Content is required',
                v => v.length <= 300 || 'Content must be less than 300 characters'
            ]
        }),

        methods: {
            sendFeedback: function () {
                if (!this.valid)
                    return;

                axios.post(`/api/feedback`, {
                    subject: this.subject,
                    content: this.content
                })
                    .then(() => {
                        this.dialog = false;
                        this.subject = '';
                        this.content = '';
                    })
                    .catch(err => Promise.reject("Fehler beim Senden des Feedbacks!"))
            }
        }
    }
</script>

<style scoped>
    .feedback-button {
        height: 40px;
        border: solid 3px #CCCCCC;
        background: #333;
        width: 100px;
        line-height: 32px;
        -webkit-transform: rotate(-90deg);
        font-weight: 600;
        color: white;
        transform: rotate(-90deg);
        -ms-transform: rotate(-90deg);
        -moz-transform: rotate(-90deg);
        text-align: center;
        font-size: 17px;
        position: fixed;
        right: -40px;
        top: 45%;
        font-family: "Roboto", helvetica, arial, sans-serif;
        z-index: 999;
    }
</style>
