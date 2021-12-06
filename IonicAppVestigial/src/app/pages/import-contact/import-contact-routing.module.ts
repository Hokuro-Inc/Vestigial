import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ImportContactPage } from './import-contact.page';

const routes: Routes = [
  {
    path: '',
    component: ImportContactPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ImportContactPageRoutingModule {}
