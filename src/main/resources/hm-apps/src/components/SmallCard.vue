<template>
    <v-card color="purple" class="white--text" style="margin: 10px">
        <!-- TitleRow-->
        <v-layout style="margin: inherit" row>
            <v-flex xs7>
                <v-card-title primary-title>
                    <div>
                        <div class="headline">{{id}}   {{title}}</div>
                        what the fuck
                    </div>
                </v-card-title>
            </v-flex>
        </v-layout>
        <!-- Logo, Description, Sceenshot Row-->

        <v-layout style="margin: inherit" row>
            <v-flex xs4>
                <div style="text-align: center">
                    <v-avatar>
                        <v-img
                                :src="logoUrl"
                                alt="test"
                                height="50px"
                                width="50px"
                        ></v-img>
                    </v-avatar>
                </div>
            </v-flex>
            <v-flex xs4>
                <div v-if="description.length<110">{{description}}</div>
                <div v-if="description.length>=110">{{description.substring(0,110)+"..."}}</div>
            </v-flex>
            <v-flex xs4>
                <div style="text-align: center">
                    <v-img
                            :src="logoUrl"
                            height="125px"
                            contain
                    ></v-img>
                </div>
            </v-flex>
        </v-layout>

        <!-- Tags and Stars Row-->

        <v-layout style="margin: inherit" row>
            <v-flex xs4>
                <div style="text-align: center">
                    <div>Tags: {{niceList(tags)}}</div>
                </div>
            </v-flex>
            <v-flex xs4></v-flex>
            <v-flex xs4>
                <div style="text-align: center">
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
            <v-flex xs4>
                <div style="text-align: center">

                    <v-btn color="deep-purple" class="white--text" round>
                        More Info
                    </v-btn>

                </div>
            </v-flex>
            <v-flex xs4>
            </v-flex>
            <v-flex xs4>
                <div style="text-align: center">
                    <v-btn color="deep-purple" class="white--text" round>
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