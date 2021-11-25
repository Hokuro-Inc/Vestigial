import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { SlidesPagePageRoutingModule } from './slides-page-routing.module';

import { SlidesPagePage } from './slides-page.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    SlidesPagePageRoutingModule
  ],
  declarations: [SlidesPagePage]
})
export class SlidesPagePageModule {}
