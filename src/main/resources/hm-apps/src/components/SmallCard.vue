<template>
    <v-card hover color="primary" class="white--text" style="margin: 15PX">
        <!-- TitleRow-->
        <v-layout style="margin: inherit" row>
            <v-flex xs12>
                <v-card-title primary-title>
                    <v-avatar>
                        <v-img
                                :src="logoUrl"
                                alt="test"
                                height="50px"
                                width="50px"
                        ></v-img>
                    </v-avatar>
                    <v-spacer></v-spacer>
                    <div class="headline">{{title}}</div>
                    <v-spacer></v-spacer>
                </v-card-title>
            </v-flex>
        </v-layout>
        <!-- Logo, Description, Sceenshot Row-->
        <v-layout style="margin: inherit" row>
            <v-flex xs5>
                <div v-if="description.length<200">{{description}}</div>
                <div v-if="description.length>=200">{{description.substring(0,110)+"..."}}</div>
            </v-flex>
            <v-flex xs2></v-flex>
            <v-flex xs5>
                <v-layout justify-end row>
                    <v-spacer></v-spacer>
                    <v-img
                            :src="logoUrl"
                            height="125px"
                            max-width="200px"
                            contain
                    ></v-img>
                </v-layout>
            </v-flex>
        </v-layout>

        <!-- Tags and Stars Row-->

        <v-layout style="margin: inherit" row>
            <v-flex xs5>
                <div>Tags: {{niceList(tags)}}</div>
            </v-flex>
            <v-flex xs2></v-flex>
            <v-flex xs5>
                <div style="text-align: right">
                    <v-icon>{{Math.round(rating) >= 1 ? 'star' : 'star_border'}}</v-icon>
                    <v-icon>{{Math.round(rating) >= 2 ? 'star' : 'star_border'}}</v-icon>
                    <v-icon>{{Math.round(rating) >= 3 ? 'star' : 'star_border'}}</v-icon>
                    <v-icon>{{Math.round(rating) >= 4 ? 'star' : 'star_border'}}</v-icon>
                    <v-icon>{{Math.round(rating) >= 5 ? 'star' : 'star_border'}}</v-icon>
                </div>
            </v-flex>
        </v-layout>
        <!-- Buttons Row-->

        <v-layout style="margin: inherit" row>
            <v-flex xs5>
                <v-btn color="secondary" class="black--text" round>
                    More Info
                </v-btn>
            </v-flex>
            <v-flex xs2>
            </v-flex>
            <v-flex xs5>
                <div style="text-align: right">
                    <v-btn color="secondary" class="black--text" round>
                        Go to Site
                    </v-btn>
                </div>
            </v-flex>
        </v-layout>

    </v-card>
</template>

<script>


    export default {
        name: "SmallCard",
        props: {
            title: String,
            id: Number,
            description: String,
            tags: Array,
            rating: Number,
            logoUrl: String,
            screenshotUrls: Array
        },
        methods: {
            niceList: function (array, join, finalJoin) {
                if (array.length == 0) {
                    return "";
                }
                var arr = array.slice(0), last = arr.pop();
                join = join || ', ';
                finalJoin = finalJoin || ', ';
                return arr.join(join) + finalJoin + last;
            }
        }
    }
</script>

<style scoped>

</style>