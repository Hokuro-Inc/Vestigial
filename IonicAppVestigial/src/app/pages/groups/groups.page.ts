import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { AddGroupPage } from '../add-group/add-group.page';

@Component({
  selector: 'app-groups',
  templateUrl: './groups.page.html',
  styleUrls: ['./groups.page.scss'],
})
export class GroupsPage implements OnInit {

  groups: string[];

  constructor(private modalController: ModalController) { }

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

}
