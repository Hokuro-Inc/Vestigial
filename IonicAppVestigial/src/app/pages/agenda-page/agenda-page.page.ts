import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddContactPage } from '../add-contact/add-contact.page'

@Component({
  selector: 'app-agenda-page',
  templateUrl: './agenda-page.page.html',
  styleUrls: ['./agenda-page.page.scss'],
})
export class AgendaPagePage implements OnInit {

  	constructor(private modalController: ModalController) { }

  	ngOnInit() {
  	}

  	async addContact() {
	    const modal = await this.modalController.create({
	      // Data passed in by componentProps
	      component: AddContactPage,
	    });
	    return await modal.present();
  	}

}
