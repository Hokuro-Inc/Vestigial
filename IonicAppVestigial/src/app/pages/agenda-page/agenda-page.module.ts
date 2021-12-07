import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { IonicModule } from '@ionic/angular';

import { AgendaPagePageRoutingModule } from './agenda-page-routing.module';

import { AgendaPagePage } from './agenda-page.page';
import { ContactsPage } from '../contacts/contacts.page';

@NgModule({
  imports: [
    CommonModule,
    FormsModule,
    IonicModule,
    AgendaPagePageRoutingModule,
  ],
  declarations: [AgendaPagePage],
  providers: [ContactsPage]
})
export class AgendaPagePageModule {}
