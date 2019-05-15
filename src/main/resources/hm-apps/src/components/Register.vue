<template>
    <v-container fluid fill-height>
        <v-layout align-center justify-center>
            <v-card elvation="12" style="margin: 15Px">
                <v-card-text>
                    <v-form
                            ref="form"
                            lazy-validation>
                        <v-text-field
                                v-model="user.name"
                                label="Name"
                                prepend-icon="person"
                                type="text"
                                required
                        ></v-text-field>
                        <v-textarea>
                            {{this.validateName}}
                        </v-textarea>

                        <v-text-field
                                v-model="user.email"
                                label="Email"
                                prepend-icon="email"
                                type="text"
                                required
                        ></v-text-field>

                        <v-text-field
                                v-model="user.password"
                                label="password"
                                type="password"
                                prepend-icon="lock"
                                required
                        ></v-text-field>
                        <v-text-field
                                v-model="user.confirmedPassword"
                                label="confirm password"
                                type="password"
                                prepend-icon="cached"
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
        name: "Register",
        data() {
            return {
                user: {
                    name: "",
                    password: "",
                    confirmedPassword: "",
                    email: ""
                }
            }
        },
        methods: {
            ...mapMutations([
                'setUser',
            ]),
            submit: function () {
                axios.post(`/api/users`, {
                    username: this.user.name,
                    email: this.user.email,
                    password: this.user.password
                }).then(res => {
                    console.log('User Created: ' + JSON.stringify(this.user));
                }).catch(error => {
                    console.error(error);
                })
            },
        }
    }
</script>

<style scoped>

</style>