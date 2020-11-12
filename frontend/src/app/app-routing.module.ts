import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { AppComponent } from './app.component';
import { SearchResultsComponent } from './search-results/search-results.component';
import { DecisionComponent } from './decision/decision.component';
import { HomeContentComponent } from './home-content/home-content.component';
import { PageNotFoundComponent } from './page-not-found/page-not-found.component';

const routes: Routes = [
  { path: '', component: HomeContentComponent},
  { path: 'results', component: SearchResultsComponent},
  { path: ':id/:searchedTerm', component: DecisionComponent},
  { path: ':id', component: DecisionComponent},
  { path: 'not-found', component: PageNotFoundComponent},
  { path: '**', redirectTo: '/not-found'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
