<template>
    <v-app>
        <TopToolbar/>
        <v-container align-start fluid style="padding: 0px">
            <Error/>
            <router-view></router-view>
        </v-container>
        <Feedback/>
    </v-app>
</template>

<script>
    import TopToolbar from "./components/TopToolbar";
    import router from "./router"
    import Error from './components/Error';
    import Feedback from './components/Feedback'

    export default {
        name: 'App',
        router,
        components: {
            TopToolbar,
            Error,
            Feedback
        },
        data() {
            return {
                items: [{title: 'Menu 1'}, {title: 'Menu 2'}],
            }
        },
    }
</script>
