import {Component, OnInit} from '@angular/core';
import {ServiceService} from '../../service.service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit {
  registerForm: { fullName: string; email: string; password: string } = {
    fullName: '',
    email: '',
    password: ''
  };

  constructor(private appService: ServiceService, private router: Router) {}

  ngOnInit(): void {}

  registerUser(): void {
    this.appService
      .register(this.registerForm)
      .subscribe(
        (response: any) => {
          this.router.navigate(['/login']);
        },
        (error) => {
          console.error('Error occurred during registration:', error);
        }
      );
  }
}
