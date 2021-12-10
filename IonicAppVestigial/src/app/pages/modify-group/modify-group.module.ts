import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModifyGroupPageRoutingModule } from './modify-group-routing.module';

import { ModifyGroupPage } from './modify-group.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ModifyGroupPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ModifyGroupPage]
})
export class ModifyGroupPageModule {}
