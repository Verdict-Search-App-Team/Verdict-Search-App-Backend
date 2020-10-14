import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-decision',
  templateUrl: './decision.component.html',
  styleUrls: ['./decision.component.css']
})
export class DecisionComponent implements OnInit {

  @Input() 
  childDecisionText: string = "fdc";

  constructor() { }

  ngOnInit(): void {
  }

}
