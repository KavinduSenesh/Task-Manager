import { Component } from '@angular/core';
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from '@angular/forms';
import {TaskService} from '../../../services/task.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  imports: [
    ReactiveFormsModule
  ],
  styleUrls: ['./register.component.css']
})
export class RegisterComponent {
  registerForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private taskService: TaskService,
    private router: Router
  ) {
    this.registerForm = this.fb.group({
      username: ['', Validators.required],
      password: ['', Validators.required]
    });
  }

  onRegister() {
    if (this.registerForm.valid) {
      const { username, password } = this.registerForm.value;
      this.taskService.register({ username, password }).subscribe(
        response => {
          localStorage.setItem('token', response.token)
          console.log('Registered successfully:', response);
          this.router.navigate(['/login']);
        },
        error => {
          console.error('Registration error:', error);
        }
      );
    }
  }
}
