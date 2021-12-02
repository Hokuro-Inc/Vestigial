import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { RecentsPageRoutingModule } from './recents-routing.module';

import { RecentsPage } from './recents.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    RecentsPageRoutingModule
  ],
  declarations: [RecentsPage]
})
export class RecentsPageModule {}
