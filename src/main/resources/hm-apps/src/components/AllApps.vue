<template>
  <v-container>
    <v-layout
      row wrap
    >
      <v-flex v-for="app in apps" :key="app.title" xs-4>
        <v-card color="purple" class="white--text" style="margin: 10px">
            <v-container>
              <v-layout row>
                <v-flex xs7>
                  <v-card-title primary-title>
                    <div>
                      <div class="headline">Halycon Days</div>
                      <div>Ellie Goulding</div>
                      <div>(2013)</div>
                    </div>
                  </v-card-title>
                </v-flex>
                <v-flex xs5>
                  <v-img
                    :src="app.image"
                    height="125px"
                    contain
                  ></v-img>
                </v-flex>
              </v-layout>
              </v-container>
              <v-divider light></v-divider>
              <v-card-actions class="pa-3">
                Rate this album
                <v-spacer></v-spacer>
                <v-icon>star_border</v-icon>
                <v-icon>star_border</v-icon>
                <v-icon>star_border</v-icon>
                <v-icon>star_border</v-icon>
                <v-icon>star_border</v-icon>
              </v-card-actions>
            </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    data: () => ({
      apps: [{title: 'test', image: 'https://cdn.vuetifyjs.com/images/cards/foster.jpg'}, {title: 'test2', image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'}]
    })
  }
</script>

<style>

</style>
