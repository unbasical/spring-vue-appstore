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
                axios.post(`/login`, {
                    username: this.name,
                    password: this.password
                }).then(res => {
                    console.log(res)
                    this.setUser(res)
                }).catch(error => {
                    console.error(error);
                })
            }
        }
    }
</script>

<style scoped>

</style>