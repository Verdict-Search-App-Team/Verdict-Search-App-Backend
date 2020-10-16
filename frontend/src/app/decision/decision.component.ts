import { Component, OnInit, Input } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-decision',
  templateUrl: './decision.component.html',
  styleUrls: ['./decision.component.css']
})
export class DecisionComponent implements OnInit {

  decision: { id: string }
  decisionString: string[];

  constructor(private route: ActivatedRoute, private http: HttpClient) { }

  ngOnInit(): void {
    this.decision = {
      id: this.route.snapshot.params['id']
    };
    this.http.
      get('http://localhost:8080/' + this.decision.id)
      .subscribe(response => {
        this.decisionString = response[0]['decisionString'].split("\n");
      });
  }

}

