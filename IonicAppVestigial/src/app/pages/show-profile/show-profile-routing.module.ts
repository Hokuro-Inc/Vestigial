import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ShowProfilePage } from './show-profile.page';

const routes: Routes = [
  {
    path: '',
    component: ShowProfilePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ShowProfilePageRoutingModule {}
