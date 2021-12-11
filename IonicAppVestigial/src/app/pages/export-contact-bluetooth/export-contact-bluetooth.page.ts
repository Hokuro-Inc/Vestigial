import { Component, OnInit } from '@angular/core';
import { ModalController,ToastController } from '@ionic/angular';
import { NavController } from '@ionic/angular';
import { BluetoothSerial } from '@awesome-cordova-plugins/bluetooth-serial/ngx';
import { Contact } from '../contacts/contacts.page'


@Component({
  selector: 'app-export-contact-bluetooth',
  templateUrl: './export-contact-bluetooth.page.html',
  styleUrls: ['./export-contact-bluetooth.page.scss'],
})
export class ExportContactBluetoothPage implements OnInit {

  contact: Contact;
  Dispositivos;

  constructor(private modalController: ModalController, private navController: NavController,private bluetoothSerial: BluetoothSerial, private toastController: ToastController) { }

  ngOnInit() {
  }

  ActivarBluetooth() {
    console.log("ACTIVAR BLUETTOH");
    this.bluetoothSerial.isEnabled().then(
      response => {this.ListarDispositivos()},
      error => {this.isEnabled("IsOff")})
  }

  ListarDispositivos () {
        console.log("LISTAR");
      this.bluetoothSerial.list().then(
//      this.bluetoothSerial.discoverUnpaired().then(
      response => {this.Dispositivos = response},
      error => {console.log("No se encuentran Dispositivos")})
  }

  ConectarBluetooth(direccion) {
        console.log("CONECTAR");
    this.bluetoothSerial.connect(direccion).subscribe(
      success => {this.deviceConnected()},
      error => {console.log("No se encuentran Dispositivos")})
  }

  deviceConnected () {
        console.log("DISPOSITIVO CONECTADO");
    this.bluetoothSerial.subscribe("/n").subscribe(
      //FUNCION 
       success => {console.log("Conectado con exito",success);this.setData()},
      )
  }

  setData() {
      this.bluetoothSerial.write (JSON.stringify(this.contact)).then (
        response => {console.log("OK")},
        error => {console.log("Ha surgido un problema al escribir")}
        )
  } 

  Desconectar () {
    this.bluetoothSerial.disconnect();

  }
  async isEnabled(msg) {
    const aler = await this.toastController.create (
    {
      header: "Alerta Bluetooth", message: msg
    }
    )
  }
}
