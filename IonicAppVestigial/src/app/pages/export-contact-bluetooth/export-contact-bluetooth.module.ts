import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ExportContactBluetoothPageRoutingModule } from './export-contact-bluetooth-routing.module';

import { ExportContactBluetoothPage } from './export-contact-bluetooth.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ExportContactBluetoothPageRoutingModule
  ],
  declarations: [ExportContactBluetoothPage]
})
export class ExportContactBluetoothPageModule {}
