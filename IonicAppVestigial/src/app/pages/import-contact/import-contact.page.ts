import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { NavController } from '@ionic/angular';
import { Observable } from 'rxjs'

import { NFC, Ndef, NfcTag} from '@ionic-native/nfc/ngx';


@Component({
  selector: 'app-import-contact',
  templateUrl: './import-contact.page.html',
  styleUrls: ['./import-contact.page.scss'],
})
export class ImportContactPage implements OnInit {

  agente : String;
  readerModePage : any;

  readingTag:   boolean   = false;
  writingTag:   boolean   = false;
  isWriting:    boolean   = false;
  ndefMsg:      string    = '';


  constructor(private modalController: ModalController, private nfc: NFC, private ndef: Ndef, private contactService: ContactsService, private navController: NavController) { }

  async ngOnInit() {
/*
    if (this.agente == "iOS") {
    		try {
				    let tag = await this.nfc.scanNdef();
				    console.log(JSON.stringify(tag));
				 } catch (err) {
				     console.log('Error reading tag', err);
				 }
    }
    else {
      let flags = this.nfc.FLAG_READER_NFC_A | this.nfc.FLAG_READER_NFC_V;
      this.readerModePage = this.nfc.readerMode(flags).subscribe(
      	tag => console.log(JSON.stringify(tag)),
      	err => console.log('Error reading tag', err)
      );         
    }

  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    if (this.agente != "iOS") {
    		this.nfc.disableReaderMode().subscribe(
      	tag => console.log(JSON.stringify(tag)),
      	err => console.log('Error reading tag', err)
      );         
    }

    this.modalController.dismiss({
      'dismissed': true
    });
    */
  }

}
