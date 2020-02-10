<template>
    <div>
        <v-alert
                v-model="show"
                dismissible
                type="error"
                transition="scale-transition"
        >
            {{error}}
        </v-alert>
    </div>
</template>

<script>
    import axios from "axios"

    // Set base url of axios from index.html property
    const node = document.querySelector(`meta[property=BACKEND_URL]`)
    axios.defaults.baseURL = node.content;

    export default {
        name: "Error",
        data: () => ({
            show: false,
            error: ''
        }),
        methods: {
            showError: function (event) {
                this.error = event.reason;
                this.show = true;
                window.setTimeout(this.hideError, 4000);
            },
            hideError: function (event) {
                this.show = false;
            }
        },
        mounted() {
            window.addEventListener('unhandledrejection', this.showError);
        },
    }
</script>

<style scoped>

</style>
