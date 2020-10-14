import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';


import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { DecisionComponent } from './decision/decision.component';

@NgModule({
  declarations: [
    AppComponent,
    SearchResultsComponent,
    DecisionComponent
  ],
  imports: [BrowserModule, AppRoutingModule, FormsModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
