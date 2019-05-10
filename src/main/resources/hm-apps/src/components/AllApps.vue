<template>
    <v-container>
        <v-layout
                row wrap
        >
            <v-flex v-for="app in applist" :key="app.title" xs5>
                <v-card color="purple" class="white--text" style="margin: 10px">
                    <!-- TitleRow-->
                    <v-layout style="margin: inherit" row>
                        <v-flex xs7>
                            <v-card-title primary-title>
                                <div>
                                    <div class="headline">{{app.title}}</div>
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
                                            :src="logoOfApp(app.id)"
                                            alt="test"
                                            height="50px"
                                            width="50px"
                                    ></v-img>
                                </v-avatar>
                            </div>
                        </v-flex>
                        <v-flex xs4>
                            <div v-if="app.description.length<110">{{app.description}}</div>
                            <div v-if="app.description.length>=110">{{app.description.substring(0,110)+"..."}}</div>
                        </v-flex>
                        <v-flex xs4>
                            <div style="text-align: center">
                                <v-img
                                        :src="app.image"
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
                                <div>Tags: {{niceList(app.tags)}}</div>
                            </div>
                        </v-flex>
                        <v-flex xs4></v-flex>
                        <v-flex xs4>
                            <div style="text-align: center">
                                <v-icon>{{Math.round(app.rating) >= 1 ? 'star' : 'star_border'}}</v-icon>
                                <v-icon>{{Math.round(app.rating) >= 2 ? 'star' : 'star_border'}}</v-icon>
                                <v-icon>{{Math.round(app.rating) >= 3 ? 'star' : 'star_border'}}</v-icon>
                                <v-icon>{{Math.round(app.rating) >= 4 ? 'star' : 'star_border'}}</v-icon>
                                <v-icon>{{Math.round(app.rating) >= 5 ? 'star' : 'star_border'}}</v-icon>
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
            </v-flex>
        </v-layout>
    </v-container>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';

    export default {
        data: () => ({
            apps: [
                {
                    title: 'Donald Trump',
                    description: 'Lorem Ipsum better hope that there are no "tapes" of our conversations before he starts leaking to the press!\n' +
                        '\n' +
                        'The best taco bowls are made in Trump Tower Grill. I love Hispanics! If Trump Ipsum weren’t my own words, perhaps I’d be dating it. This placeholder text is gonna be HUGE. Lorem Ipsum\'s father was with Lee Harvey Oswald prior to Oswald\'s being, you know, shot.\n' +
                        '\n',
                    rating: 0,
                    tags: [],
                    id: 2,
                    image: 'data:image/jpeg;base64,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'
                },
                {
                    title: 'Melania Trump',
                    description: 'We are going to make placeholder text great again. Greater than ever before. This placeholder text is gonna be HUGE.\n' +
                        '\n',
                    rating: 5,
                    tags: ["Test", "Frau"],
                    id: 2,
                    image: 'data:image/jpeg;base64,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'
                },
                {
                    title: 'Ivanka Trump',
                    description: 'Does everybody know that pig named Lorem Ipsum? She\'s a disgusting pig, right? You’re disgusting. The other thing with Lorem Ipsum is that you have to take out its family. Be careful, or I will spill the beans on your placeholder text. Be careful, or I will spill the beans on your placeholder text.\n' +
                        '\n',
                    rating: 4,
                    tags: ["Tochter", ":P"],
                    id: 2,
                    image: 'data:image/jpeg;base64,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'
                },
                {
                    title: 'Donald Trump Jr.',
                    description: 'Some people have an ability to write placeholder text... It\'s an art you\'re basically born with. You either have it or you don\'t. We are going to make placeholder text great again. Greater than ever before. You’re disgusting. Lorem Ipsum is FAKE TEXT!\n' +
                        '\n',
                    rating: 2,
                    tags: ["DTJ", "Junior", "Son"],
                    id: 2,
                    image: 'data:image/jpeg;base64,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'
                },
                {
                    title: 'Halycon Days5',
                    description: 'Lorem Ipsum better hope that there are no "tapes" of our conversations before he starts leaking to the press! All of the words in Lorem Ipsum have flirted with me - consciously or unconsciously. That\'s to be expected.',
                    rating: 1,
                    tags: ["tag 1", "test"],
                    id: 2,
                    image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'
                },
            ]
        }),
        mounted() {
            axios.get(`/api/apps`)
                .then(res => {
                    this.apps.push(...res.data);
                    console.log(...res.data);
                }).catch(error => {
                console.error("api error:" + error);
            })
        },
        methods: {
            ...mapGetters([
                'getSearch',
                'getTags',
                'getMinimumRating'
            ]),
            niceList: function (array, join, finalJoin) {
                if (array.length == 0) {
                    return "";
                }
                var arr = array.slice(0), last = arr.pop();
                join = join || ', ';
                finalJoin = finalJoin || ', ';
                return arr.join(join) + finalJoin + last;
            },
            logoOfApp: function (appID){
                //TODO: Check how to receive current user
                return axios.get("/api/users/1/apps/"+appID+"/logo").catch(error => console.error(error))

            }
        },
        computed: {
            applist: function () {
                return this.apps
                    .filter(app => app.title.includes(this.getSearch()))
                    .filter(app => Math.round(app.rating) >= this.getMinimumRating())
                    .filter(app => {
                        if(this.getTags().length==0){
                            return true;
                        }
                        return this.getTags().some(oneTag => app.tags.includes(oneTag))
                    });
            }
        }
    }
</script>

<style>

</style>
