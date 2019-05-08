<template>
    <v-container>
        <v-layout
                row wrap
        >
            <v-flex v-for="app in apps" :key="app.title" xs4>
                <v-card color="purple" class="white--text" style="margin: 10px">
                    <v-layout row>
                        <v-flex xs7>
                            <v-card-title primary-title>
                                <div>
                                    <div class="headline">{{app.title}}</div>
                                </div>
                            </v-card-title>
                        </v-flex>
                    </v-layout>
                    <v-layout row>
                        <v-flex xs4>
                            <v-img
                                    :src="app.image"
                                    height="50px"
                                    contain
                            ></v-img>
                        </v-flex>
                        <v-flex xs3>
                            <div>{{app.description}}</div>
                        </v-flex>
                        <v-flex xs5>
                            <v-img
                                    :src="app.image"
                                    height="125px"
                                    contain
                            ></v-img>
                        </v-flex>
                    </v-layout>
                    <v-layout justify-space-between row style="margin-left: 10px">
                        <v-flex xs3>
                            <div>Tags: tag1, tag2</div>
                        </v-flex>
                        <v-flex xs5></v-flex>
                        <v-flex xs3>
                            <v-icon>{{app.rating &lt; 1 ? 'star' : 'star_border'}}</v-icon>
                            <v-icon>{{app.rating &lt; 2 ? 'star' : 'star_border'}}</v-icon>
                            <v-icon>{{app.rating &lt; 3 ? 'star' : 'star_border'}}</v-icon>
                            <v-icon>{{app.rating &lt; 4 ? 'star' : 'star_border'}}</v-icon>
                            <v-icon>{{app.rating &lt; 5 ? 'star' : 'star_border'}}</v-icon>
                        </v-flex>
                    </v-layout>
                    <v-layout justify-space-between row>
                        <v-flex xs3>
                            <v-card-actions>
                                <v-btn>More Info</v-btn>
                            </v-card-actions>
                        </v-flex>
                        <v-flex xs5>
                        </v-flex>
                        <v-flex xs3>
                            <v-btn>Go to Site</v-btn>
                        </v-flex>

                    </v-layout>

                </v-card>
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    export default {
        data: () => ({
            apps: [
                {
                    title: 'Halycon Days',
                    description: 'Ellie Goulding\n(2013)',
                    rating: 5,
                    image: 'https://cdn.vuetifyjs.com/images/cards/foster.jpg'
                },
                {
                    title: 'Halycon Days2',
                    description: 'Ellie Goulding\n(2013)',
                    rating: 4,
                    image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'
                },
                {
                    title: 'Halycon Days3',
                    description: 'Ellie Goulding\n(2013)',
                    rating: 3,
                    image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'
                },
                {
                    title: 'Halycon Days4',
                    description: 'Ellie Goulding\n(2013)',
                    rating: 3,
                    image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'
                },
                {
                    title: 'Halycon Days5',
                    description: 'Ellie Goulding\n(2013)',
                    rating: 1,
                    image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'
                },
            ]
        })
    }
</script>

<style>

</style>
