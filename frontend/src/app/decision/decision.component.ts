import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-decision',
  templateUrl: './decision.component.html',
  styleUrls: ['./decision.component.css']
})
export class DecisionComponent implements OnInit {

  decision: { id: any; };
  decisionObject: Object;
  decisionString: string[];
  mySearch;

  constructor(private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit(): void {
    this.decision = {
      id: this.route.snapshot.params['id']
    };
    this.mySearch = this.route.snapshot.queryParams;
    console.log('GET: http://localhost:8080/' + this.decision.id);
    this.http.
      get('http://localhost:8080/' + this.decision.id)
      .subscribe(response => {
        this.decisionString = response['decisionText'].split("\n");
        this.decisionObject = response;
      });
  }

}


