import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ImportContactPageRoutingModule } from './import-contact-routing.module';

import { ImportContactPage } from './import-contact.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ImportContactPageRoutingModule
  ],
  declarations: [ImportContactPage]
})
export class ImportContactPageModule {}
