import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { RecentsPage } from './recents.page';

const routes: Routes = [
  {
    path: '',
    component: RecentsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class RecentsPageRoutingModule {}
