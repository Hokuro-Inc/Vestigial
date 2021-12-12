import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ImportContactPageRoutingModule } from './import-contact-routing.module';

import { ImportContactPage } from './import-contact.page';

import { NFC } from '@awesome-cordova-plugins/nfc/ngx';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ImportContactPageRoutingModule
  ],
  declarations: [ImportContactPage],
  providers: [NFC]
})
export class ImportContactPageModule {}
