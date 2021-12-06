import { NgModule } from '@angular/core';
import { PreloadAllModules, RouterModule, Routes } from '@angular/router';

const routes: Routes = [
  {
    path: '',
    redirectTo: 'slides-page',
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
  },
  {
    path: 'contacts',
    loadChildren: () => import('./pages/contacts/contacts.module').then( m => m.ContactsPageModule)
  },
  {
    path: 'favorites',
    loadChildren: () => import('./pages/favorites/favorites.module').then( m => m.FavoritesPageModule)
  },
  {
    path: 'recents',
    loadChildren: () => import('./pages/recents/recents.module').then( m => m.RecentsPageModule)
  },
  {
    path: 'notepad',
    loadChildren: () => import('./pages/notepad/notepad.module').then( m => m.NotepadPageModule)
  },
  {
    path: 'contact-view',
    loadChildren: () => import('./pages/contact-view/contact-view.module').then( m => m.ContactViewPageModule)
  },
  {
    path: 'add-contact',
    loadChildren: () => import('./pages/add-contact/add-contact.module').then( m => m.AddContactPageModule)
  },
  {
    path: 'modify-contact',
    loadChildren: () => import('./pages/modify-contact/modify-contact.module').then( m => m.ModifyContactPageModule)
  },
  {
    path: 'lists',
    loadChildren: () => import('./pages/lists-page/lists.module').then( m => m.ListsPageModule)
  },
  {
    path: 'todolist',
    loadChildren: () => import('./pages/todolist-page/todolist.module').then( m => m.TodolistPageModule)
  },
  {
    path: 'task',
    loadChildren: () => import('./pages/task-page/task.module').then( m => m.TaskPageModule)
  },
  {
    path: 'add-task',
    loadChildren: () => import('./pages/add-task/add-task.module').then( m => m.AddTaskPageModule)
  },
  {
    path: 'add-todolist',
    loadChildren: () => import('./pages/add-todolist/add-todolist.module').then( m => m.AddTodolistPageModule)
  },
  {
    path: 'modify-task',
    loadChildren: () => import('./pages/modify-task/modify-task.module').then( m => m.ModifyTaskPageModule)
  },  {
    path: 'event-view',
    loadChildren: () => import('./pages/event-view/event-view.module').then( m => m.EventViewPageModule)
  },
  {
    path: 'notepads',
    loadChildren: () => import('./pages/notepads/notepads.module').then( m => m.NotepadsPageModule)
  },
  {
    path: 'modify-note',
    loadChildren: () => import('./pages/modify-note/modify-note.module').then( m => m.ModifyNotePageModule)
  },
  {
    path: 'add-note',
    loadChildren: () => import('./pages/add-note/add-note.module').then( m => m.AddNotePageModule)
  },
  {
    path: 'import-contact',
    loadChildren: () => import('./pages/import-contact/import-contact.module').then( m => m.ImportContactPageModule)
  }


];

@NgModule({
  imports: [
    RouterModule.forRoot(routes,  {preloadingStrategy: PreloadAllModules} )
  ],
  exports: [RouterModule]
})
export class AppRoutingModule {}