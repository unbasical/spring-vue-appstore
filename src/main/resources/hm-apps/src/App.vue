<template>
    <v-app>
        <TopToolbar/>
        <v-container align-start fluid style="padding: 0px">
            <router-view></router-view>
        </v-container>
    </v-app>
</template>

<script>
    import TopToolbar from "./components/TopToolbar";
    import router from "./router"

    export default {
        name: 'App',
        router,
        components: {
            TopToolbar},
        data() {
            return {
                items: [{title: 'Menu 1'}, {title: 'Menu 2'}],
            }
        },
    }
</script>
