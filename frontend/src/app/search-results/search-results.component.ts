import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit {


  resultsList;
  contextList;

  decisionText;
  decisionToBeShown = -1;
  isDecisionShown = false;

  mySearch;


  constructor(private route: ActivatedRoute, private http: HttpClient) { }


  httpHeaders: HttpHeaders = new HttpHeaders();


  ngOnInit(): void {
    this.http
      .get('http://localhost:8080/results', { params: this.route.snapshot.queryParams, headers: this.httpHeaders })
      .subscribe(response => {
        this.mySearch = this.route.snapshot.queryParams;
        this.resultsList = response;
        this.resultsList.forEach(element => {
          this.contextList.push(element['searchContextString'])
        });
      });
  }

}




