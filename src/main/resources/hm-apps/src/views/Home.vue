<template>
  <v-layout row wrap>
    <v-flex xs2 lg2>
      <Navigation/>
    </v-flex>
    <v-flex xs10 lg10>
      <AllApps/>
    </v-flex>
  </v-layout>
</template>
<script lang="ts">
import { Component, Vue } from "vue-property-decorator";
import AllApps from "@/components/AllApps.vue"; // @ is an alias to /src
import Navigation from "@/components/Navigation.vue";
@Component({
  components: {
    AllApps,
    Navigation
  }
})
export default class Home extends Vue {}
</script>
