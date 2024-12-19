import {Component, OnInit} from '@angular/core';
import {ServiceService} from '../../service.service';
import {Router} from '@angular/router';
import {FormsModule} from '@angular/forms';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [
    FormsModule
  ],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent implements OnInit{
  addForm: { email?: string; password?: string } = {}; // Add appropriate type

  constructor(private appService: ServiceService, private router: Router) {}

  ngOnInit(): void {
  }

  loginUser(): void {
    this.appService
      .login(this.addForm)
      .subscribe(
        (response: any) => {
          localStorage.setItem('accessToken', response.accessToken);
          this.router.navigate(['/home']);
        },
        (error) => {
          console.error('Error occurred during login:', error);
        }
      );
  }
}

