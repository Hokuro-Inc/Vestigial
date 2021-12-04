import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TaskViewPageRoutingModule } from './task-view-routing.module';

import { TaskViewPage } from './task-view.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TaskViewPageRoutingModule
  ],
  declarations: [TaskViewPage]
})
export class TaskViewPageModule {}
