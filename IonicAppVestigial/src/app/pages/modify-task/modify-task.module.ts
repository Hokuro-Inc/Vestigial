import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule  } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModifyTaskPageRoutingModule } from './modify-task-routing.module';

import { ModifyTaskPage } from './modify-task.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ModifyTaskPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ModifyTaskPage]
})
export class ModifyTaskPageModule {}
