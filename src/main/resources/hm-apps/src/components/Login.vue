<template>
    <v-container fluid fill-height>
        <v-layout align-center justify-center>
            <v-card elvation="12" style="margin: 15Px">
                <v-card-text>
                    <v-form
                            ref="form"
                            lazy-validation>
                        <v-text-field
                                v-model="name"
                                label="Name"
                                prepend-icon="person"
                                type="text"
                                required
                        ></v-text-field>

                        <v-text-field
                                @keydown.enter="submit"
                                v-model="password"
                                label="password"
                                type="password"
                                prepend-icon="lock"
                                required
                        ></v-text-field>
                        <v-card-actions>
                            <v-spacer></v-spacer>
                                <v-btn
                                        color="success"
                                        @click="submit"
                                > submit
                                </v-btn>
                            <v-spacer></v-spacer>
                        </v-card-actions>
                    </v-form>
                </v-card-text>
            </v-card>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from "axios"
    import {mapMutations} from 'vuex'
    import router from "../router"

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "Login",
        data() {
            return {
                name: "",
                password: "",
            }
        },

        methods: {
            ...mapMutations([
                'setUser',
            ]),
            submit: function () {
                axios.post(`/api/login`, {
                    username: this.name,
                    password: this.password
                }).then(res => {
                    this.setUser(res.data)
                    console.log(res.data)
                    router.push({name: 'home'})
                }).catch(() => Promise.reject('Fehler beim Einloggen!'))
            }
        }
    }
</script>

<style scoped>

</style>
