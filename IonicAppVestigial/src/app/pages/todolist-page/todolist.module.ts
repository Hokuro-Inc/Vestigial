import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { TodolistPageRoutingModule } from './todolist-routing.module';

import { TodolistPage } from './todolist.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    TodolistPageRoutingModule
  ],
  declarations: [TodolistPage]
})
export class TodolistPageModule {}
