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
  modified: boolean = false;

  constructor(private calendarService: CalendarService,private modalController: ModalController) { }

  ngOnInit() { }

  dismiss(event: Event, deleted: boolean = false) {
    // using the injected ModalController this page
    // can "dismiss" itself and optionally pass back data
    this.modalController.dismiss({
      'dismissed': true,
      'event': event,
      'deleted': deleted,
      'modified': this.modified
    });
  }

  getDateFormatted(dateStr: string) {
    let date = new Date(String(dateStr).replace(' CET', ''));
		let day = String(date.getDate()).padStart(2, '0');
		let month = String(date.getMonth() + 1).padStart(2, '0');
		let year = date.getFullYear();
		let hour = String(date.getHours()).padStart(2, '0');
		let minute = String(date.getMinutes()).padStart(2, '0');
		return day + '-' + month + '-' + year + " " + hour + ":" + minute;
  }

  async editEvent(event: Event) {
    let tmp = new Event(
      event.id,
      event.owner,
      event.name,
      event.description,
      event.start,
      event.end
    );

    const modal = await this.modalController.create({
      // Data passed in by componentProps
      component: ModifyEventPage,
      componentProps: {
        event: this.event,
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        if (!data.data.modified) {
          this.event = tmp;
        }
        else {
          this.modified = true;
        }
      }
      else {
        this.event = tmp;
      }
    });
    return await modal.present();
  }

  async deleteEvent(event: Event) {
    let data = {
      "idEvent" : event.id
    };
    this.calendarService.removeEvent(JSON.stringify(data)).subscribe(
      (response) => { 
        console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Mensaje
          console.log("Mensaje",data)
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
        this.dismiss(event, true);
      }
    );
  }

}
