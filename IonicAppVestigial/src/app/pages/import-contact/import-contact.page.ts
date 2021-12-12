import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { Contact } from '../contacts/contacts.page'

import { NFC } from '@awesome-cordova-plugins/nfc/ngx';


@Component({
  selector: 'app-import-contact',
  templateUrl: './import-contact.page.html',
  styleUrls: ['./import-contact.page.scss'],
})
export class ImportContactPage implements OnInit {

  agente : String;
  readerModePage : any;
  contact : Contact = new Contact("","","","","","","","","");

  constructor(private modalController: ModalController, private nfc: NFC, private contactService: ContactsService) { }
  
  async ngOnInit() {
    let flags = this.nfc.FLAG_READER_NFC_A | this.nfc.FLAG_READER_NFC_V;
    this.readerModePage = this.nfc.readerMode(flags).subscribe(
      tag => {
        console.log(JSON.stringify(tag));
        let dataNFC = tag.ndefMessage;
    
        let aux = this.nfc.bytesToString(dataNFC[0].payload);

        console.log("MENSAJE TARJETA " , aux);

        aux = aux.split("{")[1];

        console.log("Spliteado " , aux);

        aux = "{"+aux;

        let data = JSON.parse(aux);

        console.log(data.owner);
        let contact = {
          "owner": sessionStorage.getItem("user"),
          "name": data.name,
          "surname": data.surname,
          "prefix": data.prefix,
          "phone": data.phone,
          "address": data.address,
          "email": data.email,
          "description": data.description,
          "alias": data.alias,
          "groups" : [""]
        };
        this.contact = new Contact(data.address,data.alias,data.description,data.email,data.name,sessionStorage.getItem("user"),data.phone,data.prefix,data.surname);
        this.contactService.importContact(JSON.stringify(contact)).subscribe(
          (response) => console.log("Respuesta", response),
          (error) => console.log("Error", error),
          () => {
            this.dismiss(this.contact);
            //alert("Funciona!!!!");
          }
        );
      },
      err => console.log('Error reading tag', err)
    );

    // Read NFC Tag - iOS
    // On iOS, a NFC reader session takes control from your app while scanning tags then returns a tag
    try {
      let tag = await this.nfc.scanNdef();
      console.log(JSON.stringify(tag));
      let dataNFC = tag.ndefMessage;

      let aux = this.nfc.bytesToString(dataNFC[0].payload);

      console.log("MENSAJE TARJETA " , aux);
    } 
    catch (err) {
      console.log('Error reading tag', err);
    }
  }

  dismiss(contact: Contact) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'contact': contact
    });
    
  }

}
