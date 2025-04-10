import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/api/v1/tasks';

  constructor(private http: HttpClient) { }

  getAllTasks(): Observable<any>{
    return this.http.get(`${this.apiUrl}/getAll`);
  }

  getTaskById(id: number): Observable<any>{
    return this.http.get(`${this.apiUrl}/get/${id}`);
  }

  deleteTask(id: number): Observable<any>{
    return this.http.delete(`${this.apiUrl}/delete/${id}`);
  }

  createTask(task: any): Observable<any>{
    return this.http.post(`${this.apiUrl}/create`,task);
  }

  updateTask(id: number, task: any): Observable<any> {
    return this.http.patch(`${this.apiUrl}/update/${id}`,task);
  }
}
