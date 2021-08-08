<template>
    <div v-if="currentTutorial" class="edit-form py-3">
        <p class="headline">Edit Tutorial</p>

        <v-form ref="form" lazy-validation>
            <v-text-field
                    v-model="currentTutorial.title"
                    :rules="[(v) => !!v || 'Title is required']"
                    label="Title"
                    required
            ></v-text-field>

            <v-text-field
                    v-model="currentTutorial.description"
                    :rules="[(v) => !!v || 'Description is required']"
                    label="Description"
                    required
            ></v-text-field>

            <label><strong>Status:</strong></label>
            {{ currentTutorial.published ? "Published" : "Pending" }}

            <v-divider class="my-5"></v-divider>

            <v-btn v-if="currentTutorial.published"
                   @click="updatePublished(false)"
                   color="primary" small class="mr-2"
            >
                UnPublish
            </v-btn>

            <v-btn v-else
                   @click="updatePublished(true)"
                   color="primary" small class="mr-2"
            >
                Publish
            </v-btn>

            <v-btn color="error" small class="mr-2" @click="deleteTutorial">
                Delete
            </v-btn>

            <v-btn color="success" small @click="updateTutorial">
                Update
            </v-btn>
        </v-form>

        <p class="mt-3">{{ message }}</p>
    </div>

    <div v-else>
        <p>Please click on a Tutorial...</p>
    </div>
</template>

<script lang="ts">
import Vue from "vue";
import TutorialDataService from "@/services/TutorialDataService";
import Tutorial from "@/types/Tutorial";
import ResponseData from "@/types/ResponseData";

export default Vue.extend({
    name: "Tutorial",
    data() {
        return {
            currentTutorial: {} as Tutorial,
            message: "",
        };
    },
    methods: {
        getTutorial(id: any) {
            TutorialDataService.get(id)
            .then((response: ResponseData) => {
                this.currentTutorial = response.data;
                console.log(response.data);
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        updatePublished(status: boolean) {
            let data = {
                id: this.currentTutorial.id,
                title: this.currentTutorial.title,
                description: this.currentTutorial.description,
                published: status,
            };

            TutorialDataService.update(this.currentTutorial.id, data)
            .then((response: ResponseData) => {
                console.log(response.data);
                this.currentTutorial.published = status;
                this.message = "The status was updated successfully!";
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        updateTutorial() {
            TutorialDataService.update(this.currentTutorial.id, this.currentTutorial)
            .then((response: ResponseData) => {
                console.log(response.data);
                this.message = "The tutorial was updated successfully!";
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        deleteTutorial() {
            TutorialDataService.delete(this.currentTutorial.id)
            .then((response: ResponseData) => {
                console.log(response.data);
                this.$router.push({ name: "tutorials" });
            })
            .catch((e: Error) => {
                console.log(e);
            });
}
    },
    mounted() {
        this.message = "";
        this.getTutorial(this.$route.params.id);
    },
});
</script>

<style scoped>
.edit-form {
    max-width: 300px;
    margin: auto;
}
</style>