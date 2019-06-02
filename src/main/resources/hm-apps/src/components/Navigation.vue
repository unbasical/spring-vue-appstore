<template>
    <v-navigation-drawer
            v-model="drawer"
            :mini-variant.sync="mini"
            hide-overlay
            stateless

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

        <v-list class="pt-0">
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
            <v-list-tile>

                <v-list-tile-action>
                    <v-icon>list</v-icon>
                </v-list-tile-action>
                <v-list-tile-content>
                    <v-select

                            v-model="selectedTags"
                            v-on:input="setTags(selectedTags)"
                            :items="allTags"
                            chips
                            label="Tags"
                            multiple
                    ></v-select>
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile>
                <v-list-tile-content>
                    <v-layout row>
                        <div style="text-align: center">
                            <v-btn icon small depressed
                                   @click="setMinimumRating(1)">
                                <v-icon x-small>{{getMinimumRating() >= 1 ? 'star' : 'star_border'}}</v-icon>
                            </v-btn>
                            <v-btn icon small depressed
                                   @click="setMinimumRating(2)">
                                <v-icon x-small>{{getMinimumRating() >= 2 ? 'star' : 'star_border'}}</v-icon>
                            </v-btn>
                            <v-btn icon small depressed
                                   @click="setMinimumRating(3)">
                                <v-icon x-small>{{getMinimumRating() >= 3 ? 'star' : 'star_border'}}</v-icon>
                            </v-btn>
                            <v-btn icon small depressed
                                   @click="setMinimumRating(4)">
                                <v-icon x-small>{{getMinimumRating() >= 4 ? 'star' : 'star_border'}}</v-icon>
                            </v-btn>
                            <v-btn icon small depressed
                                   @click="setMinimumRating(5)">
                                <v-icon x-small>{{getMinimumRating() >= 5 ? 'star' : 'star_border'}}</v-icon>
                            </v-btn>
                        </div>
                    </v-layout>
                </v-list-tile-content>
            </v-list-tile>
            <v-list-tile>
                <v-list-tile-content >
                    <v-btn large round>
                        <v-icon>add_circle</v-icon>
                        Share your App
                    </v-btn>
                </v-list-tile-content>
            </v-list-tile>
        </v-list>
    </v-navigation-drawer>
</template>

<script>
    import {mapGetters, mapMutations} from 'vuex'
    import axios from 'axios'

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "Navigation.vue",
        data() {
            return {
                filter: "",
                drawer: true,
                mini: true,
                allTags: ["test", "ball", "trump"],
                selectedTags: [],
                items: [{title: 'Menu 1'}, {title: 'Menu 2'}],
            }
        },
        mounted() {
            axios.get(`/api/tags`)
                .then(res => {
                    this.allTags.push(...res.data);
                }).catch(error => {
                console.error("api error:" + error);
            })
            this.setTags([])
        },
        methods: {
            ...mapMutations([
                'setSearch',
                'setTags',
                'setMinimumRating'
            ]),
            ...mapGetters([
                'getSearch',
                'getTags',
                'getMinimumRating'
            ])
        }
    }
</script>

<style scoped>

</style>
