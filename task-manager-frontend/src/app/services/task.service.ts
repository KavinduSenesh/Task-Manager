import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';
import {Router} from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class TaskService {
  private apiUrl = 'http://localhost:8080/api/v1/tasks';
  private authUrl = 'http://localhost:8080/api/v1/auth';

  constructor(private http: HttpClient, private router: Router) { }

  getAllTasks(): Observable<any>{
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.apiUrl}/getAll`, { headers });

  }

  getTaskById(id: number): Observable<any>{
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.get(`${this.apiUrl}/get/${id}`, { headers });
  }

  deleteTask(id: number): Observable<any>{
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.delete(`${this.apiUrl}/delete/${id}`, { headers });
  }

  createTask(task: any): Observable<any>{
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.post(`${this.apiUrl}/create`,task, { headers });
  }

  updateTask(id: number, task: any): Observable<any> {
    const token = localStorage.getItem('token');
    const headers = new HttpHeaders().set('Authorization', `Bearer ${token}`);
    return this.http.patch(`${this.apiUrl}/update/${id}`,task, { headers });
  }

  login(credentials: {username: string, password: string}): Observable<any> {
    return this.http.post(`${this.authUrl}/sign-in`, credentials);
  }

  register(user: {username: string, password: string}): Observable<any> {
    return this.http.post(`${this.authUrl}/sign-up`, user);
  }
}
