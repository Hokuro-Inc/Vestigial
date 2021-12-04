import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyContactPage } from './modify-contact.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyContactPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyContactPageRoutingModule {}
