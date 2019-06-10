<template>
    <v-card hover :color="background.DarkVibrant" class="white--text" style="margin: 15px; padding-bottom: 10px">
        <!-- TitleRow-->
        <v-layout style="margin: inherit" row>
            <v-flex xs12>
                <v-card-title primary-title>
                    <v-avatar>
                        <v-img
                            :src="logoUrl"
                            alt="'no logo there'">
                        </v-img>
                    </v-avatar>
                    <v-spacer></v-spacer>
                    <div class="headline">{{app.title}}</div>
                    <v-spacer></v-spacer>
                </v-card-title>
            </v-flex>
        </v-layout>
        <!-- Logo, Description, Sceenshot Row-->
        <v-layout style="margin: inherit" row>
            <v-flex xs5>
                <div v-if="app.description.length<200">{{app.description}}</div>
                <div v-if="app.description.length>=200">{{app.description.substring(0,110)+"..."}}</div>
            </v-flex>
            <v-flex xs2></v-flex>
            <v-flex xs5>
                <v-layout justify-end row>
                    <v-spacer></v-spacer>
                    <v-img
                            :src="logoUrl"
                            :alt="'no logo there'"
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
                <div>Tags: {{niceList(app.tags)}}</div>
            </v-flex>
            <v-flex xs1></v-flex>
            <v-flex xs6>
                <div style="text-align: right">
                    <v-rating v-model="app.rating" :background-color="background.LightMuted"
                              :color="background.Vibrant" small/>
                </div>
            </v-flex>
        </v-layout>
    </v-card>
</template>

<script>
    import * as Vibrant from 'node-vibrant'

    export default {
        name: "SmallCard",
        props: {
            app: {type: Object, default: {}},
        },
        data: () => ({
            background: {
                Vibrant: 'white',
                Muted: 'white',
                LightVibrant: 'white',
                DarkVibrant: 'white',
                DarkMuted: 'white',
                LightMuted: 'white'
            },
        }),
        mounted() {
            this.setBackground();
        },
        methods: {
            niceList: function (array, join, finalJoin) {
                if (!array || array.length == 0) {
                    return "";
                }
                var arr = array.slice(0), last = arr.pop();
                join = join || ', ';
                finalJoin = finalJoin || ', ';
                return arr.join(join) + finalJoin + last;
            },
            setBackground() {
                Vibrant.from(this.logoUrl).getPalette()
                    .then((palette) => {
                        Object.keys(palette).forEach(key => this.background[key] = palette[key].hex);
                    });
            },
        },
        computed: {
            // a computed getter
            logoUrl: function () {
                // `this` points to the vm instance
                return process.env.VUE_APP_BASE_URL + '/api/apps/' + this.app.id + '/logo'
            }
        },
    }
</script>

<style scoped>

</style>
