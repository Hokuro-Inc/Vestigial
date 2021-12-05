import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { EventViewPageRoutingModule } from './event-view-routing.module';

import { EventViewPage } from './event-view.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    EventViewPageRoutingModule
  ],
  declarations: [EventViewPage]
})
export class EventViewPageModule {}
