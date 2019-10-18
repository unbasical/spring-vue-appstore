<template>
    <v-toolbar>
        <v-toolbar-side-icon>
            <router-link :to="{name: 'home'}" tag="button">
                <v-icon>home</v-icon>
            </router-link>
        </v-toolbar-side-icon>

        <v-toolbar-title>
            Apps@HM
        </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn flat>
            <span class="mr-2">{{getUser().name}}</span>
        </v-btn>

        <v-menu offset-y>
            <template v-slot:activator="{ on }">
                <v-toolbar-side-icon v-on="on">
                </v-toolbar-side-icon>
            </template>
            <v-list>
                <v-list-tile>
                    <router-link :to="{name: 'home'}" tag="button">
                        <v-btn flat>
                            <v-icon left>house</v-icon>
                            all apps
                        </v-btn>
                    </router-link>
                </v-list-tile>
                <v-list-tile v-if="getUser().id==0">
                    <router-link :to="{name: 'register'}" tag="button">
                        <v-btn flat>
                            <v-icon left>add</v-icon>
                            register
                        </v-btn>
                    </router-link>
                </v-list-tile>
                <v-list-tile v-if="getUser().id!=0">
                    <router-link :to="{name: 'home'}" tag="button">
                        <v-btn flat @click="logout()">
                            <v-icon left>face</v-icon>
                            Logout
                        </v-btn>
                    </router-link>
                </v-list-tile>
                <v-list-tile v-if="getUser().id==0">
                    <router-link :to="{name: 'userlogin'}" tag="button">
                        <v-btn flat>
                            <v-icon left>face</v-icon>
                            Login
                        </v-btn>
                    </router-link>
                </v-list-tile>
                <v-list-tile>
                    <v-btn
                            flat
                            href="http://fr-11-apps.herokuapp.com/swagger-ui.html"
                            target="_blank">
                        <v-icon left>assignment</v-icon>
                        swagger UI
                    </v-btn>
                </v-list-tile>
                <v-list-tile>
                    <v-btn
                            flat
                            href="http://localhost:8080/swagger-ui.html"
                            target="_blank">
                        <v-icon left>assignment</v-icon>
                        swagger UI local
                    </v-btn>
                </v-list-tile>
                <v-list-tile>
                    <router-link :to="{name: 'showfeedback'}" tag="button">
                        <v-btn
                                flat
                                target="_blank">
                            <v-icon left>bug_report</v-icon>
                            Feedback
                        </v-btn>
                    </router-link>
                </v-list-tile>
                <v-list-tile>
                    <router-link :to="{name: 'changepermissions'}" tag="button">
                        <v-btn
                                flat
                                target="_blank">
                            <v-icon left>bug_report</v-icon>
                            Permissions
                        </v-btn>
                    </router-link>
                </v-list-tile>
            </v-list>
        </v-menu>
    </v-toolbar>
</template>
<script>
    import {mapGetters,mapMutations} from 'vuex';

    export default {
        name: 'TopToolbar',
        methods: {
            ...mapGetters([
                'getUser',
            ]),
            ...mapMutations([
                'setUser'
            ]),
            logout:function () {
                this.setUser({
                    id: 0,
                    email : "mail",
                    name: "default",
                    token: '',
                })
            }
        },
    }
</script>
