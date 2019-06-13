<template>
    <v-container>
        <v-card xs12 lg12
                v-for="user in allUser"
                :key="user.id"
        >
            <v-layout row style="margin: 15px">
                <v-card-title>{{user.name}}</v-card-title>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-spacer></v-spacer>
                <v-select v-model="user.role" :items="roles"></v-select>
                <v-btn @click="assignRole(user.id,user.role)">
                    <v-icon>save</v-icon>
                </v-btn>
            </v-layout>
        </v-card>
    </v-container>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';

    export default {
        name: "GrantPermissions",
        data: () => ({
            allUser: [],
            roles: ['ADMIN', 'DEVELOPER', 'USER']
        }),
        mounted() {
            axios.get(this.getAllUsersUrl(), {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer ' + this.getUser().token
                },
            })
                .then(res => this.allUser = res.data)
                .catch(err => console.error(err))
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
            getAllUsersUrl: function () {
                return "/api/users"
            },
            getAssingPermissionUrl: function (userid) {
                return "/api/users/" + userid + "/role"
            },
            assignRole: function (userId, role) {
                axios.put(this.getAssingPermissionUrl(userId), role,{
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': 'Bearer ' + this.getUser().token
                    }
                })
                    .then()
                    .catch(err => console.error(err))
            }
        }
    }
</script>

<style scoped>

</style>
