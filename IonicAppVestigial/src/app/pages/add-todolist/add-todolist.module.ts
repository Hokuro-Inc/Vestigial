import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AddTodolistPageRoutingModule } from './add-todolist-routing.module';

import { AddTodolistPage } from './add-todolist.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AddTodolistPageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [AddTodolistPage]
})
export class AddTodolistPageModule {}
