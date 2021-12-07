import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AboutusPage } from './aboutus.page';

const routes: Routes = [
  {
    path: '',
    component: AboutusPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AboutusPageRoutingModule {}
