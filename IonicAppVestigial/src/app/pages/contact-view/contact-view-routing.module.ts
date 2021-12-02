import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ContactViewPage } from './contact-view.page';

const routes: Routes = [
  {
    path: '',
    component: ContactViewPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ContactViewPageRoutingModule {}
