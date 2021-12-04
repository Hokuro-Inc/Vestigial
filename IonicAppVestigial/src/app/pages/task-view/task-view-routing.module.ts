import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { TaskViewPage } from './task-view.page';

const routes: Routes = [
  {
    path: '',
    component: TaskViewPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class TaskViewPageRoutingModule {}
