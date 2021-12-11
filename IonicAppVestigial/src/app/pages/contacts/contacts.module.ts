import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ContactsPageRoutingModule } from './contacts-routing.module';

import { ContactsPage } from './contacts.page';

import { NFC,Ndef} from '@awesome-cordova-plugins/nfc/ngx';

import { CallNumber } from '@awesome-cordova-plugins/call-number/ngx';

import { BluetoothSerial } from '@awesome-cordova-plugins/bluetooth-serial/ngx';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ContactsPageRoutingModule
  ],
  declarations: [ContactsPage],
  providers: [NFC,Ndef,CallNumber,BluetoothSerial]
})
export class ContactsPageModule {}
