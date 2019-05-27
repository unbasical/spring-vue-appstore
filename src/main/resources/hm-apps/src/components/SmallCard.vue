<template>
    <v-card hover color="primary" class="white--text" style="margin: 15Px">
        <!-- TitleRow-->
        <v-layout style="margin: inherit" row>
            <v-flex xs12>
                <v-card-title primary-title>
                    <v-avatar>
                        <v-img
                                :src="logoSrc"
                                alt="test">
                                <!--
                                height="50px"
                                width="50px"
                                -->
                        </v-img>
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
                            :src="screenshotUrls"
                            :alt="logoSrc"
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
    import axios from "axios"

    // Set base url of axios
    axios.defaults.baseURL = process.env.VUE_APP_BASE_URL;

    export default {
        name: "SmallCard",
        data:{
            logoSrc: "data:image",
        },
        props: {
            title: {type: String, default: 'Default-Title'},
            id: {type: Number, default: 999},
            description: {type: String,default: 'default description of default App'},
            tags: {type: Array, default: ['Test','default','tag']},
            rating: {type: Number, default: 0},
            logoUrl: {type:String, default:'media Type expected'},
            screenshotUrls: {type:Array, default:['screenshot1','screenshot2']}
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
        },
        created: function () {
            //TODO set logo URL
            // TODO set screenshot URL

            this.logoUrl = "/api/apps/" + this.id + "/logo"
            /*axios.get("/api/apps/"+this.id+"/logo",{
                responseType: 'blob'
            }).then(response => {
                let reader = new FileReader();
                reader.readAsDataURL(response.data);
                reader.onload = () => {
                    this.logoSrc = reader.result;
                    console.log("render logo " + this.id)
                }
            }).catch(err=>console.error(err))
*/
        }
    }
</script>

<style scoped>

</style>
