import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { AgendaPagePage } from './agenda-page.page';

const routes: Routes = [
  {
    path: '',
    component: AgendaPagePage,
    children: [
      {
        path: 'contacts',
        loadChildren: () => import('../contacts/contacts.module').then( m => m.ContactsPageModule)
      },
      {
        path: 'favorites',
        loadChildren: () => import('../favorites/favorites.module').then( m => m.FavoritesPageModule)
      },

      {
        path: 'recents',
        loadChildren: () => import('../recents/recents.module').then( m => m.RecentsPageModule)
      }
      
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class AgendaPagePageRoutingModule {}
