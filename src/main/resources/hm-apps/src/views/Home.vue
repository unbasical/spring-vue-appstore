<template>
  <v-layout row wrap style="height: 100%">
    <v-flex xs12 lg12>
      <Navigation style="margin-top: 64px;"/>
      <AllApps/>
    </v-flex>
  </v-layout>
</template>
<script lang="ts">
  import {Component, Vue} from "vue-property-decorator";
  import AllApps from "./../components/AllApps.vue"; // @ is an alias to /src
  import Navigation from "./../components/Navigation.vue";

  @Component({
  components: {
    AllApps,
    Navigation
  }
})
export default class Home extends Vue {}
</script>
