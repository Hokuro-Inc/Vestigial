import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TodolistPage } from './todolist.page';

const routes: Routes = [
  {
    path: '',
    component: TodolistPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TodolistPageRoutingModule {}
