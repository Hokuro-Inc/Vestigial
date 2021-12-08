import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddGroupPage } from './add-group.page';

const routes: Routes = [
  {
    path: '',
    component: AddGroupPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddGroupPageRoutingModule {}
