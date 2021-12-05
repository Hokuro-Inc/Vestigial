import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NotepadsPageRoutingModule } from './notepads-routing.module';

import { NotepadsPage } from './notepads.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NotepadsPageRoutingModule
  ],
  declarations: [NotepadsPage]
})
export class NotepadsPageModule {}
