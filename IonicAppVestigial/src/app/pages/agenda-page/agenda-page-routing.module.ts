import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AgendaPagePage } from './agenda-page.page';

const routes: Routes = [
  {
    path: '',
    component: AgendaPagePage,
    children: [
      {
        path: 'account',
        loadChildren: () => import('../contacts/contacts.module').then( m => m.ContactsPageModule)


      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AgendaPagePageRoutingModule {}
