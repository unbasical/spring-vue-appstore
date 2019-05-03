<template>
    <v-app>
      <v-toolbar>
        <v-img
            alt="HM-Logo"
            :src="require('./assets/hm_logo.jpeg')"
            style="background-position: left center !important;"
            height="100%"
            width="10px"
          ></v-img>
        <v-spacer></v-spacer>
        <v-btn
          flat
          href="https://github.com/vuetifyjs/vuetify/releases/latest"
          target="_blank"
        >
        
          <span class="mr-2">Donaldus Maximus</span>
        </v-btn>
        <v-toolbar-side-icon></v-toolbar-side-icon>
      </v-toolbar>
      <v-container align-start fluid style="padding: 0px">
        <v-layout row wrap>
          <v-flex xs2>
            <v-navigation-drawer permanent>
      <v-toolbar flat>
        <v-list>
          <v-list-tile>
            <v-list-tile-title class="title headline text-uppercase">
              <span>HM-App</span>
              <span class="font-weight-light">store</span>
            </v-list-tile-title>
          </v-list-tile>
        </v-list>
      </v-toolbar>
  
      <v-divider></v-divider>
  
      <v-list dense class="pt-0">
        <v-list-tile
          v-for="item in items"
          :key="item.title"
          @click=""
        >
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
  
          <v-list-tile-content>
            <v-list-tile-title>{{ item.title }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </v-list>
    </v-navigation-drawer>
          </v-flex>
          <v-flex xs10>
            <v-content>
              <AllApps/>
              </v-content>
          </v-flex>
        </v-layout>
      </v-container>
    </v-app>
  </template>

<script>
import AllApps from './components/AllApps'

export default {
  name: 'App',
  components: {
    AllApps
  },
  data () {
    return {
      items : [{title: 'Test'}],
    }
  }
}
</script>
