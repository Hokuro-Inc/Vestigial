import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NotepadsPage } from './notepads.page';

const routes: Routes = [
  {
    path: '',
    component: NotepadsPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NotepadsPageRoutingModule {}
