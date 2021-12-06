import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddContactPage } from '../add-contact/add-contact.page'
import { ImportContactPage } from '../import-contact/import-contact.page'

@Component({
  selector: 'app-agenda-page',
  templateUrl: './agenda-page.page.html',
  styleUrls: ['./agenda-page.page.scss'],
})
export class AgendaPagePage implements OnInit {

  	constructor(private modalController: ModalController) { }

  	ngOnInit() {
  	}

  	getMobileOperatingSystem() {
    var userAgent = navigator.userAgent || navigator.vendor;

    // Windows Phone must come first because its UA also contains "Android"
    if (/windows phone/i.test(userAgent)) {
      return "Windows Phone";
    }

    if (/android/i.test(userAgent)) {
       return "Android";
    }

    // iOS detection from: http://stackoverflow.com/a/9039885/177710
    if (/iPad|iPhone|iPod/.test(userAgent)) {
       return "iOS";
    }

    return "unknown";
  }

  	async addContact() {
	    const modal = await this.modalController.create({
	      // Data passed in by componentProps
	      component: AddContactPage,
	    });
	    return await modal.present();
  	}

  	async importContact() {
	    const modal = await this.modalController.create({
	      // Data passed in by componentProps
	      component: ImportContactPage,
	      componentProps: {
	      	agente: this.getMobileOperatingSystem()
	      }
	    });
	    return await modal.present();
  	}
}
