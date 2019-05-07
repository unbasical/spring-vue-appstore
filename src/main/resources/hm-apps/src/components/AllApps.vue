<template>
  <v-container>
    <v-layout
      row wrap
    >
      <v-flex v-for="app in apps" :key="app.title" xs4>
        <v-card color="purple" class="white--text" style="margin: 10px">
            <v-container>
              <v-layout row>
                <v-flex xs7>
                  <v-card-title primary-title>
                    <div>
                      <div class="headline">{{app.title}}</div>
                      <div>{{app.description}}</div>
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
                <v-icon>{{app.rating <= 1 ? 'star' : 'star_border'}}</v-icon>
                <v-icon>{{app.rating <= 2 ? 'star' : 'star_border'}}</v-icon>
                <v-icon>{{app.rating <= 3 ? 'star' : 'star_border'}}</v-icon>
                <v-icon>{{app.rating <= 4 ? 'star' : 'star_border'}}</v-icon>
                <v-icon>{{app.rating <= 5 ? 'star' : 'star_border'}}</v-icon>
              </v-card-actions>
            </v-card>
      </v-flex>
    </v-layout>
  </v-container>
</template>

<script>
  export default {
    data: () => ({
      apps: [
        {title: 'Halycon Days', description: 'Ellie Goulding\n(2013)', rating: 5, image: 'https://cdn.vuetifyjs.com/images/cards/foster.jpg'}, 
        {title: 'Halycon Days2', description: 'Ellie Goulding\n(2013)', rating: 4, image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'},
        {title: 'Halycon Days3', description: 'Ellie Goulding\n(2013)', rating: 3, image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'},
        {title: 'Halycon Days4', description: 'Ellie Goulding\n(2013)', rating: 3, image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'},
        {title: 'Halycon Days5', description: 'Ellie Goulding\n(2013)', rating: 1, image: 'https://cdn.vuetifyjs.com/images/cards/halcyon.png'},
      ]
    })
  }
</script>

<style>

</style>
