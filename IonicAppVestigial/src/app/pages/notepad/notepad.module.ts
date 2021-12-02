import { Component, NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { NotepadPageRoutingModule } from './notepad-routing.module';

import { NotepadPage } from './notepad.page';


@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    NotepadPageRoutingModule,

  ],
  declarations: [NotepadPage]
})
export class NotepadPageModule {}
