import { Routes } from '@angular/router';
import {TaskFormComponent} from './components/task-form/task-form.component';
import {LoginComponent} from './components/auth/login/login.component';
import {RegisterComponent} from './components/auth/register/register.component';
import {TaskListComponent} from './components/task-list/task-list.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'task-form', component: TaskFormComponent },
  { path: 'task-form/:id', component: TaskFormComponent },
  { path: 'task-list', component: TaskListComponent }
];
