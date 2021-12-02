import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ContactViewPageRoutingModule } from './contact-view-routing.module';

import { ContactViewPage } from './contact-view.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ContactViewPageRoutingModule
  ],
  declarations: [ContactViewPage]
})
export class ContactViewPageModule {}
