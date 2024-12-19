import {Component, OnInit} from '@angular/core';
import {ServiceService} from '../../service.service';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [],
  templateUrl: './home.component.html',
  styleUrl: './home.component.css'
})
export class HomeComponent implements OnInit {

  home: any

  constructor(private appService : ServiceService) {
  }

  ngOnInit() {
    this.getHome();
  }

  getHome() {
    this.appService.getHome().subscribe((data: any) => {
      this.home = data.message;
    });
  }
}
