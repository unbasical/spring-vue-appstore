<template>
    <v-app>
        <v-toolbar>
            <v-img
                    alt="HM-Logo"
                    :src="require('./assets/hm_logo.jpeg')"
                    style="background-position: left center !important;"
                    height="100%"
                    width="10px"
            ></v-img>
            <v-spacer></v-spacer>
            <v-btn
                    flat
                    href="https://github.com/vuetifyjs/vuetify/releases/latest"
                    target="_blank"
            >
                <span class="mr-2">Donaldus Maximus</span>
            </v-btn>

            <v-menu offset-y>
                <template v-slot:activator="{ on }">
                    <v-toolbar-side-icon v-on="on">
                    </v-toolbar-side-icon>
                </template>
                <v-list>
                    <v-list-tile>
                        <v-btn
                                flat
                                :to="{name: 'login'}"
                                target="_blank">
                            <v-icon left>face</v-icon>
                            Login
                        </v-btn>
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
        <v-container align-start fluid style="padding: 0px">
            <router-view></router-view>
        </v-container>
    </v-app>
</template>

<script>
    import AllApps from './components/AllApps'
    import Navigation from './components/Navigation'

    export default {
        name: 'App',
        components: {
            AllApps,
            Navigation
        },
        data() {
            return {
                items: [{title: 'Menu 1'}, {title: 'Menu 2'}],
            }
        },
    }
</script>
