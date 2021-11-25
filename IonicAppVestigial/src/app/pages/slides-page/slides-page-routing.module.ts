import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { SlidesPagePage } from './slides-page.page';

const routes: Routes = [
  {
    path: '',
    component: SlidesPagePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class SlidesPagePageRoutingModule {}
