import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyEventPage } from './modify-event.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyEventPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyEventPageRoutingModule {}
