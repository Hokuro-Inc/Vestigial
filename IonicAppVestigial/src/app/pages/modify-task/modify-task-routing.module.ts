import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyTaskPage } from './modify-task.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyTaskPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyTaskPageRoutingModule {}
