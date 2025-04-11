import {Component, OnInit} from '@angular/core';
import {RouterLink} from '@angular/router';
import {TaskService} from '../../services/task.service';
import {CommonModule} from '@angular/common';

@Component({
  selector: 'app-task-list',
  imports: [
    RouterLink,
    CommonModule
  ],
  templateUrl: './task-list.component.html',
  styleUrl: './task-list.component.css'
})
export class TaskListComponent implements OnInit{
  tasks: any[] = [];

  constructor(private taskService: TaskService) {}

  ngOnInit() {
    this.loadTasks();
  }

  loadTasks(){
    this.taskService.getAllTasks().subscribe(data => {
      this.tasks = data;
    });
  }

  deleteTask(id: number) {
    this.taskService.deleteTask(id).subscribe(() => {
      this.loadTasks()
    });
  }
}
