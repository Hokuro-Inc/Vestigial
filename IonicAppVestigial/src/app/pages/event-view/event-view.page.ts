import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { Event } from 'src/app/pages/calendar/calendar.page';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';

import { ModifyEventPage } from '../modify-event/modify-event.page'


@Component({
  selector: 'app-event-view',
  templateUrl: './event-view.page.html',
  styleUrls: ['./event-view.page.scss'],
})
export class EventViewPage implements OnInit {

  event: Event;

  constructor(private calendarService: CalendarService,private modalController: ModalController) { }

  ngOnInit() {
  }

  dismiss() {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true
    });
  }

  async editEvent(event: Event) {
    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ModifyEventPage,
      componentProps: {
        event: event,
      }
    });
    return await modal.present();
  }

  async deleteEvent(event: Event) {
    let datas = {
        "idEvent" : event.id
      };
      this.calendarService.removeEvent(JSON.stringify(datas)).subscribe(
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
          this.dismiss();
        }

      ); 
  }

}
