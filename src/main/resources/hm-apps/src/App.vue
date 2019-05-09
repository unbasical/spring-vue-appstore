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
            <v-layout row wrap>

                <v-navigation-drawer
                        v-model="drawer"
                        :mini-variant.sync="mini"
                        hide-overlay
                        stateless
                        absolute
                >
                    <v-toolbar flat class="transparent">
                        <v-list class="pa-0">
                            <v-list-tile>
                                    <v-list-tile-action>
                                        <div v-if="mini">

                                        <v-btn
                                                icon
                                                @click.stop="mini = !mini"
                                        >
                                            <v-icon>chevron_right</v-icon>
                                        </v-btn>
                                        </div>
                                    </v-list-tile-action>
                                <v-list-tile-content>
                                    <v-list-tile-title>HM APPS</v-list-tile-title>
                                </v-list-tile-content>
                                <v-list-tile-action>
                                    <v-btn
                                            icon
                                            @click.stop="mini = !mini"
                                    >
                                        <v-icon>chevron_left</v-icon>
                                    </v-btn>
                                </v-list-tile-action>
                            </v-list-tile>
                        </v-list>
                    </v-toolbar>

                    <v-list class="pt-0" dense>
                        <v-divider></v-divider>

                        <v-list-tile
                                v-for="item in items"
                                :key="item.title"
                        >
                            <!--
                            Add Icon if you want to
                            <v-list-tile-action>
                                <v-icon>{{ item.icon }}</v-icon>
                            </v-list-tile-action>
                             -->
                            <v-list-tile-content>
                                <v-list-tile-title>{{ item.title }}</v-list-tile-title>
                            </v-list-tile-content>
                        </v-list-tile>
                    </v-list>
                </v-navigation-drawer>
                <v-content>
                    <AllApps/>
                </v-content>
            </v-layout>
        </v-container>
    </v-app>
</template>

<script>
    import AllApps from './components/AllApps'

    export default {
        name: 'App',
        components: {
            AllApps
        },
        data() {
            return {
                drawer: true,
                mini: true,
                items: [{title: 'Menu 1'},{title: 'Menu 2'}],
            }
        }
    }
</script>
