
import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'ngbd-navbar',
  templateUrl: './navbar.component.html'
})
export class NavbarComponent {
  collapsed = true;
  keyword: string = '';

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit() {
  }

  search(): void {
    console.log("We're in the NavbarComponent, queryparams are " + { searchedTerm: this.keyword });
    this.router.navigate(['/results'], { queryParams: { searchedTerm: this.keyword } });
  }

}