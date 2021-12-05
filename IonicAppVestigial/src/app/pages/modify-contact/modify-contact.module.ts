import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModifyContactPageRoutingModule } from './modify-contact-routing.module';

import { ModifyContactPage } from './modify-contact.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ModifyContactPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ModifyContactPage]
})
export class ModifyContactPageModule {}
