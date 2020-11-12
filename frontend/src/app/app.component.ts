import { Component, OnInit } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { LocationStrategy } from '@angular/common';


@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'frontend';
  numberOfRulings = 0;
  searchedTerm: string = '';
  resultsList;
  contextList = [];

  constructor(private http: HttpClient,private location: LocationStrategy) { }

  fullPath = (<any>this.location)._platformLocation.location.href;


  ngOnInit() {
    this.fetchNumberOfRulings();
  }

  private fetchNumberOfRulings(): any {
  this.http.get('http://localhost:8080/home')
    .subscribe(response => {
      console.log(response);
      this.numberOfRulings = response[0];
    });
}


/*
search(): void {
  this.http
    .get('http://localhost:8080/results?searchedTerm=' + this.searchedTerm)
    .subscribe(response => {
      console.log(response);
      this.resultsList = response;
      this.showResults();
    });
}
*/
}
