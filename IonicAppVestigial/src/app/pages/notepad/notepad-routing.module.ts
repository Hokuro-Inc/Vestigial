import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { NotepadPage } from './notepad.page';

const routes: Routes = [
  {
    path: '',
    component: NotepadPage
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class NotepadPageRoutingModule {}
