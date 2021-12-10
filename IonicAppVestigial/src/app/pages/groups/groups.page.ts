import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { ContactsService } from 'src/app/services/contacts-service/contacts.service';
import { AddGroupPage } from '../add-group/add-group.page';
import { ModifyGroupPage } from '../modify-group/modify-group.page';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.page.html',
  styleUrls: ['./groups.page.scss'],
})
export class GroupsPage implements OnInit {

  groups: string[];

  constructor(private modalController: ModalController, private contactService: ContactsService) { }

  ngOnInit() {
    this.sort();
  }

  dismiss() {
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  sort() {
    if (this.groups.length == 0) return;

    this.groups.sort((a, b) => {
      if (a.toLowerCase() < b.toLowerCase()) {
        return -1;
      }
      if (a.toLowerCase() > b.toLowerCase()) {
        return 1;
      }
      return 0;
    });
  }

  async addGroup() {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: AddGroupPage
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        let group = data.data.group;
        this.groups.push(group);
        this.sort();
      }
    });
    return await modal.present();
  }

  async editGroup(group: string) {
    const modal = await this.modalController.create({
      component: ModifyGroupPage,
      componentProps: {
        oldGroup: group
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        let index = this.groups.indexOf(group);
        this.groups[index] = data.data.group;
        this.sort();
      }
    });
    return await modal.present();
  }

  async deleteGroup(group: string) {
    console.log("delete");
    let data = {
      'user': sessionStorage.getItem("user"),
      'group': group
    };

    this.contactService.removeGroup(JSON.stringify(data)).subscribe(
      (response) => {
        //console.log(response);
        if (response != '') {
          let data = JSON.parse(response).Mensaje;
          console.log("Mensaje", data);
          let index = this.groups.indexOf(group);
          this.groups.splice(index, 1);
        }
      },
      (error) => console.log(error),
      () => {
        console.log("Completed");
      }
    );
  }

}
