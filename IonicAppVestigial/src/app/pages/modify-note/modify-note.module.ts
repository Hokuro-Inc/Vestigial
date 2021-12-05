import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule,ReactiveFormsModule  } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { ModifyNotePageRoutingModule } from './modify-note-routing.module';

import { ModifyNotePage } from './modify-note.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    ModifyNotePageRoutingModule,
    ReactiveFormsModule
  ],
  declarations: [ModifyNotePage]
})
export class ModifyNotePageModule {}
