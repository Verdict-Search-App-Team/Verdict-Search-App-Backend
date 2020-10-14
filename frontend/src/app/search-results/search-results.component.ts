import { Component, OnInit, Input } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-search-results',
  templateUrl: './search-results.component.html',
  styleUrls: ['./search-results.component.css']
})
export class SearchResultsComponent implements OnInit  {

  decisionText = "asfdcas";

  @Input() 
  childContextList: Array<Object> = [];

  @Input() 
  childResultsList: Array<Object> = [];

  constructor() { }

  ngOnInit(): void {
  }

  getDecision(context: string): void {
    let decisionNumber = this.childContextList.indexOf(context);
    this.decisionText = this.childResultsList[decisionNumber]['decisionDto']['hatarozatszoveg'];
  }

}
