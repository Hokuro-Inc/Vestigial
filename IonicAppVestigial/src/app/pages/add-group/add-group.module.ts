import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddGroupPageRoutingModule } from './add-group-routing.module';

import { AddGroupPage } from './add-group.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddGroupPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [AddGroupPage]
})
export class AddGroupPageModule {}
