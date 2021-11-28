import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'calendar',
    pathMatch: 'full'
  },
  /*{
    path: 'folder/:id',
    loadChildren: () => import('./folder/folder.module').then( m => m.FolderPageModule)
  },*/
  {
    path: 'calendar',
    loadChildren: () => import('./pages/calendar/calendar.module').then( m => m.CalendarPageModule)
  },
  {
    path: 'register-page',
    loadChildren: () => import('./pages/register-page/register-page.module').then( m => m.RegisterPagePageModule)
  },
  {
    path: 'login-page',
    loadChildren: () => import('./pages/login-page/login-page.module').then( m => m.LoginPagePageModule)
  },
  {
    path: 'slides-page',
    loadChildren: () => import('./pages/slides-page/slides-page.module').then( m => m.SlidesPagePageModule)
  },
  {
    path: 'notepad-page',
    loadChildren: () => import('./pages/notepad-page/notepad-page.module').then( m => m.NotepadPagePageModule)
  },
  {
    path: 'agenda-page',
    loadChildren: () => import('./pages/agenda-page/agenda-page.module').then( m => m.AgendaPagePageModule)
  }

];

@NgModule({
  imports: [
    RouterModule.forRoot(routes,  {preloadingStrategy: PreloadAllModules} )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}
