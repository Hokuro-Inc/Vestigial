import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyGroupPage } from './modify-group.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyGroupPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyGroupPageRoutingModule {}
