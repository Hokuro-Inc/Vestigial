import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ExportContactBluetoothPage } from './export-contact-bluetooth.page';

const routes: Routes = [
  {
    path: '',
    component: ExportContactBluetoothPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ExportContactBluetoothPageRoutingModule {}
