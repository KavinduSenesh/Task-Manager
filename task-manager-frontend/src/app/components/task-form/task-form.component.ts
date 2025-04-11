import {Component, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {NgIf} from '@angular/common';
import {ActivatedRoute, Router, RouterLink} from '@angular/router';
import {TaskService} from '../../services/task.service';

@Component({
  selector: 'app-task-form',
  imports: [
    ReactiveFormsModule,
    NgIf,
    RouterLink
  ],
  templateUrl: './task-form.component.html',
  styleUrl: './task-form.component.css'
})
export class TaskFormComponent implements OnInit {
  taskForm!: FormGroup;
  id!: number;
  isEdit: boolean = false;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit() {
    this.taskForm = this.fb.group({
      title: ['', [Validators.required, Validators.maxLength(100)]],
      description: ['', Validators.maxLength(200)],
      status: ['TO_DO', Validators.required],
    });

    this.id = this.route.snapshot.params['id'];

    if (this.id){
      this.isEdit = true;
      this.taskService.getTaskById(this.id).subscribe(task => {
        this.taskForm.patchValue({
          title: task.title,
          description: task.description,
          status: task.status
        });
      })
    }
  }

  protected readonly onsubmit = onsubmit;

  onSubmit() {
    if (this.taskForm.invalid) {
      return;
    }

    const taskData = this.taskForm.value;

    if (this.isEdit){
      this.taskService.updateTask(this.id, taskData).subscribe(
        (response) => {
          this.router.navigate(['/task-list']);
        },
        (error) => {
          console.error('Error updating task:', error);
        }
      );
    }else {
      this.taskService.createTask(taskData).subscribe(
        (response) => {
          this.router.navigate(['/task-list']);
        },
        (error) => {
          console.error('Error creating task:', error);
        }
      )
    }
  }
}
