<template>
    <v-toolbar>
        <v-img alt="HM-Logo"
               :src="require('../assets/hm_logo.jpeg')"
               contain
               height="100%"
        ></v-img>
        <v-toolbar-title>

            <!--:aspect-ratio="16/9"-->
            </v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn
                flat
                href="https://github.com/vuetifyjs/vuetify/releases/latest"
                target="_blank"
        >
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
                <v-list-tile>
                    <router-link :to="{name: 'register'}" tag="button">
                        <v-btn flat>
                            <v-icon left>add</v-icon>
                            register
                        </v-btn>
                    </router-link>
                </v-list-tile>
                <v-list-tile>
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

            </v-list>
        </v-menu>
    </v-toolbar>
</template>
<script>
    import {mapGetters} from 'vuex';

    export default {
        name: 'TopToolbar',
        methods: {
            ...mapGetters([
                'getUser',
            ]),
        },
    }
</script>
