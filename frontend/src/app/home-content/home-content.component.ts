import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-home-content',
  templateUrl: './home-content.component.html',
  styleUrls: ['./home-content.component.css']
})
export class HomeContentComponent implements OnInit {

  totalDecisionCount;
  popularDecisionsList;

  collapsed = true;
  keyword: string = '';

  constructor(private router: Router, private route: ActivatedRoute, private http: HttpClient) { }


  httpHeaders: HttpHeaders = new HttpHeaders();


  ngOnInit(): void {
    this.http
      .get('http://localhost:8080/home')
      .subscribe(response => {
        this.totalDecisionCount = response[0];
        this.popularDecisionsList = response[1];
      });
      
  }

  search(): void {
    console.log("We're in the NavbarComponent, queryparams are " + { searchedTerm: this.keyword });
    this.router.navigate(['/results'], { queryParams: { searchedTerm: this.keyword } });
  }

  
  

}
