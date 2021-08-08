<template>
    <div class="submit-form mt-3 mx-auto">
        <p class="headline">Add Tutorial</p>

        <div v-if="!submitted">
            <v-form ref="form" lazy-validation>
                <v-text-field
                        v-model="tutorial.title"
                        :rules="[(v) => !!v || 'Title is required']"
                        label="Title"
                        required
                ></v-text-field>

                <v-text-field
                        v-model="tutorial.description"
                        :rules="[(v) => !!v || 'Description is required']"
                        label="Description"
                        required
                ></v-text-field>
            </v-form>

            <v-btn color="primary" class="mt-3" @click="saveTutorial">Submit</v-btn>
        </div>

        <div v-else>
            <v-card class="mx-auto">
                <v-card-title>
                    Submitted successfully!
                </v-card-title>

                <v-card-subtitle>
                    Click the button to add new Tutorial.
                </v-card-subtitle>

                <v-card-actions>
                    <v-btn color="success" @click="newTutorial">Add</v-btn>
                </v-card-actions>
            </v-card>
        </div>
    </div>
</template>

<script lang="ts">
import Vue from "vue";
import Tutorial from "@/types/Tutorial";
import TutorialDataService from "@/services/TutorialDataService";
import ResponseData from "@/types/ResponseData";

export default Vue.extend({
    name: "AddTutorial",

    data() {
        return {
            tutorial: {
                id: null,
                title: "",
                description: "",
                published: false
            } as Tutorial,
            submitted: false
        };
    },
    methods: {
        saveTutorial() {
            let data = {
                title: this.tutorial.title,
                description: this.tutorial.description
            };

            TutorialDataService.create(data)
            .then((response: ResponseData) => {
                this.tutorial.id = response.data.id;
                console.log(response.data);
                this.submitted = true;
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        newTutorial() {
            this.submitted = false;
            this.tutorial = {} as Tutorial;
        }
    }
});
</script>

<style scoped>
.submit-form {
    max-width: 300px;
}
</style>