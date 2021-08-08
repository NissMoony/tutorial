<template>
    <v-app>
        <v-app-bar app dark density="compact">
            <div class="d-flex align-center">
                <v-img
                        alt="Vuetify Logo"
                        class="shrink mr-2"
                        contain
                        src="https://cdn.vuetifyjs.com/images/logos/vuetify-logo-dark.png"
                        transition="scale-transition"
                        width="40"
                />
            </div>
            <v-app-bar-title class="font-weight-black">Niss's Test</v-app-bar-title>
            <v-spacer></v-spacer>
            <v-btn to="/">Home</v-btn>
            <v-btn text v-on:click="$router.push('/tutorial')">Tutorials</v-btn>
            <v-btn text v-on:click="$router.push('/add')">Add tutorial</v-btn>
        </v-app-bar>
    <v-main>
      <router-view />
    </v-main>
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  name: "App",

  data: () => ({
    //
  }),
});
</script>
