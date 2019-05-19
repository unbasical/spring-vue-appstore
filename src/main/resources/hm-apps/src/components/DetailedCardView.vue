<template>
    <v-layout>
        <h4>This is a detailed View of ID</h4>
        <h1>{{idNumber}}</h1>
        <p>{{card}}</p>
    </v-layout>
</template>

<script>
    import axios from "axios";
    import {mapGetters} from 'vuex';

    export default {
        name: "DetailedCardView",
        data: () => ({
            card: {
                title: String,
                description: String,
                views: Number,
                screenshots: [],
                tags: [],
                rating: Number,
            }
        }),
        props: {
            id: String,
        },
        computed: {
            idNumber: function () {
                // `this` points to the vm instance
                return Number(this.id)
            }
        },
        methods: {
            ...mapGetters([
                'getUser'
            ]),
        },
        created() {
            console.log('create Card for ID: ' + this.idNumber)
            if (this.getUser().token == 'default') {
                console.error('user is not loged In correctly')
            }
            console.log('User: ' + JSON.stringify(this.getUser()))
            axios.get("/api/apps/" + this.idNumber
                //, {headers: {'Authorization': "bearer " + this.getUser().token}}
                )
                .then(res => console.log('show app & prepare Fields of Card\n' + res))
                .catch(
                    this.card = {
                        title: 'default',
                        description: 'default description',
                        views: 999,
                        screenshots: [],
                        tags: [],
                        rating: 1,
                    }
                )
        }
    }
</script>

<style scoped>

</style>