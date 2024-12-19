import {RouterModule, Routes} from '@angular/router';
import {RegisterComponent} from './component/register/register.component';
import {HomeComponent} from './component/home/home.component';
import {LoginComponent} from './component/login/login.component';
import {NgModule} from '@angular/core';

export const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'home', component: HomeComponent},
  { path: '**', redirectTo: '/login' }

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
