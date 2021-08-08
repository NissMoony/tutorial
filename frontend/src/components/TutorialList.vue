<template>
    <v-container>
        <v-row align="center" class="list px-3 mx-auto">
            <v-col cols="12" md="8">
                <v-text-field v-model="title" label="Search by Title"></v-text-field>
            </v-col>

            <v-col cols="12" md="4">
                <v-btn small @click="searchTitle">
                    Search
                </v-btn>
            </v-col>

            <v-col cols="12" sm="12">
                <v-card class="mx-auto" tile>
                    <v-card-title>Tutorials</v-card-title>

                    <v-data-table
                            :headers="headers"
                            :items="tutorials"
                            disable-pagination
                            :hide-default-footer="true">
                        <template v-slot:[`item.actions`]="{ item }">
                            <v-icon small class="mr-2" @click="editTutorial(item.id)">mdi-pencil</v-icon>
                            <v-icon small @click="deleteTutorial(item.id)">mdi-delete</v-icon>
                        </template>
                    </v-data-table>

                    <v-card-actions v-if="tutorials.length > 0">
                        <v-btn small color="error" @click="removeAllTutorials">
                            Remove All
                        </v-btn>
                    </v-card-actions>
                </v-card>
            </v-col>
        </v-row>
    </v-container>
</template>

<script lang="ts">
import Vue from "vue";
import Tutorial from "@/types/Tutorial";
import TutorialDataService from "@/services/TutorialDataService";
import ResponseData from "@/types/ResponseData";

export default Vue.extend({
    name: "TutorialList",

    data() {
        return {
            tutorials: [],
            title: "",
            headers: [
                { text: "Title", value: "title", sortable: false, align: "start" },
                { text: "Description", value: "description", sortable: false },
                { text: "Status", value: "status", sortable: false },
                { text: "Actions", value: "actions", sortable: false },
            ]
        };
    },
    methods: {
        retrieveTutorials() {
            TutorialDataService.getAll()
            .then((response: ResponseData) => {
                if (response.data) {
                    this.tutorials = response.data.map((t: Tutorial) => this.getDisplayTutorial(t));
                } else {
                    this.tutorials = [];
                }
                console.log(response.data);
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        refreshList() {
            this.retrieveTutorials();
        },
        removeAllTutorials() {
            TutorialDataService.deleteAll()
            .then((response: ResponseData) => {
                console.log(response.data);
                this.refreshList();
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        searchTitle() {
            TutorialDataService.findByTitle(this.title)
            .then((response: ResponseData) => {
                if (response.data) {
                    this.tutorials = response.data.map((t: Tutorial) => this.getDisplayTutorial(t));
                } else {
                    this.tutorials = [];
                }
                console.log(response.data);
            })
            .catch((e: Error) => {
                console.log(e);
            });
        },
        editTutorial(id: any) {
            this.$router.push({ name: "tutorial-details", params: { id: id } });
        },
        deleteTutorial(id: any) {
            TutorialDataService.delete(id)
            .then(() => {
                this.refreshList();
            })
            .catch((e) => {
                console.log(e);
            });
        },
        getDisplayTutorial(tutorial: Tutorial) {
            return {
                id: tutorial.id,
                title: tutorial.title.length > 30
                        ? tutorial.title.substr(0, 30) + "..."
                        : tutorial.title,
                description: tutorial.description.length > 30
                        ? tutorial.description.substr(0, 30) + "..."
                        : tutorial.description,
                status: tutorial.published ? "Published" : "Pending",
            };
        },
    },

    mounted() {
        this.retrieveTutorials();
    }
});
</script>

<style scoped>
.list {
    max-width: 750px;
}
</style>
