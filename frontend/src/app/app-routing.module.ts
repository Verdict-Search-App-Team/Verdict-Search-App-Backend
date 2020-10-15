import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { DecisionComponent } from './decision/decision.component';
import { HomeContentComponent } from './home-content/home-content.component';

const routes: Routes = [
  { path: '', component: HomeContentComponent},
  { path: 'results', component: SearchResultsComponent},
  { path: ':id', component: DecisionComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
