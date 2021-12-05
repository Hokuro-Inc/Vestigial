import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { ModifyNotePage } from './modify-note.page';

const routes: Routes = [
  {
    path: '',
    component: ModifyNotePage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class ModifyNotePageRoutingModule {}
