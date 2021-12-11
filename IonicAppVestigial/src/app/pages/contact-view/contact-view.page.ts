import { Component, OnInit } from '@angular/core';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { ModalController } from '@ionic/angular';
import { Contact } from '../contacts/contacts.page'
import { ModifyContactPage } from '../modify-contact/modify-contact.page'

import { NFC, Ndef, NfcTag} from '@awesome-cordova-plugins/nfc/ngx';
import { CallNumber } from '@awesome-cordova-plugins/call-number/ngx';

import { BluetoothSerial } from '@awesome-cordova-plugins/bluetooth-serial/ngx';


@Component({
  selector: 'app-contact-view',
  templateUrl: './contact-view.page.html',
  styleUrls: ['./contact-view.page.scss'],
})
export class ContactViewPage implements OnInit {

	contact: Contact;
  listener;

  constructor(private contactsService: ContactsService, public modalController: ModalController,
              private nfc: NFC, private ndef: Ndef,
              private callNumber: CallNumber,private bluetoothSerial: BluetoothSerial) { 
  }

  ngOnInit() {
    //console.log(this.contact);
  }

   dismiss(contact: Contact, deleted: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'contact': contact,
      'deleted': deleted
    });
  }

  async editContact(contact: Contact) {
    //console.log(contact);
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ModifyContactPage,
      componentProps: {
        contact: contact,
      }
    });
    return await modal.present();
  }

  async deleteContact(contact: Contact) {
    //console.log(contact);
    let data = {
      "user": sessionStorage.getItem("user"),
      "phone" : contact.phone,
      "prefix" : contact.prefix
    };
    this.contactsService.removeContact(JSON.stringify(data)).subscribe(
      (response) => { 
        //console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Mensaje
          console.log("Mensaje",data)
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(contact, true);
      }
    ); 
  }

  async llamarContacto (contact: Contact) {
    let numeroTelefono = "+" + contact.prefix + contact.phone;
    console.log("Vamos a llamar al contacto",numeroTelefono);
    this.callNumber.callNumber(numeroTelefono, true)
      .then(res => console.log('Launched dialer!', res))
      .catch(err => console.log('Error launching dialer', err));

  }  

/*
  async exportContactBluetooth (contact: Contact) {
    
        this.nfc.enabled().then(value => console.log(value), reason => console.log(reason))

    this.nfc.addNdefFormatableListener(
      () => {
        console.log('successfully attached ndef listener BLUE');
      }, 
      (err) => {
        console.log('error attaching ndef listener BLUIE', err);
      }).subscribe((event) => {
        console.log('received ndef message. the tag contains BLUIE: ', event.tag);
        console.log('decoded tag id BLUE', this.nfc.bytesToHexString(event.tag.id));
        let message = this.ndef.textRecord('Hello world');
        this.nfc.write([message]).then(() => {console.log("EXITO BLUETOH")}).catch((error) => {console.log("ERROR", error)});
    });

  }
*/
  async exportContactNFC() {
     this.listener = this.nfc.addNdefListener().subscribe(this.onNdefTagScanned.bind(this));
  }
  
  onNdefTagScanned(nfcEvent: any) {

    // Create an NDEF text record
    const record = this.ndef.textRecord(JSON.stringify(this.contact), "en", null);
    // an NDEF message is an array of NDEF records    
    const message = [record];

    // write to the tag
    this.nfc.write(message).then(
      _ => {
        console.log('Wrote message to tag LISTENER')
        this.listener.unsubscribe();       
      },
      error => console.log('Write failed LISTENER', error)
    )


  }

}