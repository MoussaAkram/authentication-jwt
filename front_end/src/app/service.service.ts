import { Injectable } from '@angular/core';
import {Router} from '@angular/router';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {Observable} from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ServiceService {
  private apiUrl = "http://localhost:8080";

  constructor(private http : HttpClient , public router: Router) {
  }


  register(user: any) : Observable<any> {
    const url = `${this.apiUrl}/auth/register`;
    return this.http.post(url, user)
  }

  login(user: any): Observable<any> {
    const url = `${this.apiUrl}/auth/authenticate`;
    return this.http.post(url, user);
  }

  doLogout() {
    let removeToken = localStorage.removeItem('accessToken');
    if (removeToken == null) {
      this.router.navigate(['/login']);
    }
  }

  getHome(): Observable<any> {
    const accessToken = localStorage.getItem('accessToken');
    const headers = new HttpHeaders({
      'Authorization': `Bearer ${accessToken}`,
    });
    const url = `${this.apiUrl}/home`;
    return this.http.get(url, { headers: headers });
  }

}
