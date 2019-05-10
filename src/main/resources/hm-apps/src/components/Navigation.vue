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
                        <v-icon>search</v-icon>
                    </v-list-tile-action>
                    <v-list-tile-content>
                        <v-select
                                v-model="selectedTags"
                                :items="allTags"

                                chips
                                label="Tags"
                                multiple
                        ></v-select>
                    </v-list-tile-content>
                </v-list-tile>
                <v-list-tile>
                    <v-list-tile-content>
                        <v-flex row>
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
                        </v-flex>
                    </v-list-tile-content>
                </v-list-tile>
            </v-list>
        </v-navigation-drawer>
    </v-container>
</template>

<script>
    import {mapMutations} from 'vuex'
    import {mapGetters} from 'vuex'
    import axios from 'axios'
    export default {
        name: "Navigation.vue",
        data() {
            return {
                filter: "",
                drawer: true,
                mini: true,
                allTags: ["test"],
                selectedTags: [],
                items: [{title: 'Menu 1'}, {title: 'Menu 2'}],
            }
        },
        mounted() {
            axios.get(`/tags`)
                .then(res => {
                    if (res.data.status == 200) {
                        this.allTags.push(...res.data);
                        console.log(...res.data);
                    }
                }).catch(error => {
                console.log("api error:" + error);
            })
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