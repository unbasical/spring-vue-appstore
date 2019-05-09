<template>
    <v-container>
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
                <v-list-tile>
                    <v-list-tile-action>
                        <v-icon>search</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                        <v-text-field
                                label="search App"
                                single-line
                                v-model="filter"
                                @keyup="setSearch(filter)"
                        >
                        </v-text-field>
                    </v-list-tile-content>
                </v-list-tile>
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
    </v-container>
</template>

<script>
    import { mapMutations } from 'vuex'

    export default {
        name: "Navigation.vue",
        data() {
            return {
                filter:"",
                drawer: true,
                mini: true,
                items: [{title: 'Menu 1'}, {title: 'Menu 2'}],
            }
        },
        methods:{
            ...mapMutations([
                'setSearch',
                'setTags',
                'setMinimumRating'
            ])
        }
    }
</script>

<style scoped>

</style>