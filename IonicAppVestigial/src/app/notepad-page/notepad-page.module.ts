import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NotepadPagePageRoutingModule } from './notepad-page-routing.module';

import { NotepadPagePage } from './notepad-page.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NotepadPagePageRoutingModule
  ],
  declarations: [NotepadPagePage]
})
export class NotepadPagePageModule {}
