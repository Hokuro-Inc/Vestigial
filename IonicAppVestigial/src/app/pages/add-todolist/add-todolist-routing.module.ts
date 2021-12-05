import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AddTodolistPage } from './add-todolist.page';

const routes: Routes = [
  {
    path: '',
    component: AddTodolistPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AddTodolistPageRoutingModule {}
