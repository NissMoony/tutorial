import http from "@/http-common";

class TutorialDataService {
    getAll(): Promise<any> {
        return http.get("/tutorial");
    }

    get(id: any): Promise<any> {
        return http.get(`/tutorial/${id}`);
    }

    create(data: any): Promise<any> {
        return http.post("/tutorial", data);
    }

    update(id: any, data: any): Promise<any> {
        return http.put(`/tutorial/${id}`, data);
    }

    deleteAll(): Promise<any> {
        return http.delete("/tutorial");
    }

    delete(id: any): Promise<any> {
        return http.delete(`/tutorial/${id}`);
    }

    findByTitle(title: string): Promise<any> {
        return http.get(`/tutorial?title=${title}`);
    }
}

export default new TutorialDataService();
