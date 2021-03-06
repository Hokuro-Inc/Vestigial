import { Component, OnInit } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';
import { EventViewPage } from '../event-view/event-view.page';
import { AddEventPage } from '../add-event/add-event.page';

@Component({
selector: 'app-calendar',
templateUrl: './calendar.page.html',
styleUrls: ['./calendar.page.scss'],
})
export class CalendarPage implements OnInit {

  events: Event[] = [];
  filteredEvents: Event[] = [];
  date: string;
  viewTitle: string;
  selectedDate: string;
  eventSource: any[] = [];
  calendar = {
  mode: 'month',
  currentDate: new Date(),
  };

  constructor(private calendarService: CalendarService, private modalController: ModalController) { }

  ngOnInit() {
    this.selectedDate = this.getDate(new Date().toUTCString());

    let user = {
        "user": sessionStorage.getItem("user"),
    };

    this.calendarService.getData(JSON.stringify(user)).subscribe(
      (response) => {
        console.log("Respuesta", response);
        if (response != '') {
          let data = JSON.parse(response).Calendar;
          this.events = [];

          if (data != '[]') {
            data.forEach((element: any) => {
              this.events.push(new Event(
                element.id,
                element.owner,
                element.name,
                element.description,
                element.start,
                element.end
              ));
            });

            this.filter(this.selectedDate);
            this.eventSource = this.createCalendarEvents();
          }
        }
      },
      (error) => console.log("Error", error),
      () => {
        console.log("Completed");
      }
    );
  }

  createCalendarEvents() {
    let events = [];

    for (let i = 0; i < this.events.length; i += 1) {
    events.push({
      title: this.events[i].name,
      startTime: new Date(this.getDate(this.events[i].start)),
      endTime: new Date(this.getDate(this.events[i].start)),
      allDay: true
    });
  }

  return events;
  }

  // Selected date reange and hence title changed
  onViewTitleChanged(title: string) {
    this.viewTitle = title;
  }

  async showEvent(event: Event) {
    let index = this.events.indexOf(event);
    let tmp = new Event(
      event.id,
      event.owner,
      event.name,
      event.description,
      event.start,
      event.end
    );

    const modal = await this.modalController.create({
      component: EventViewPage,
      componentProps: {
        event: event
      }
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined) {
        if (data.data.deleted) {
          let event = data.data.event;
          let index = this.events.indexOf(event);
          this.events.splice(index, 1);
        }
        else if (data.data.modified) {
          this.events[index] = data.data.event;
        }
        else {
          this.events[index] = tmp;
        }
      }
      else {
        this.events[index] = tmp;
      }

      this.eventSource = this.createCalendarEvents();
      this.filter(this.selectedDate);
    });
    return await modal.present();
  }

  async addEvent() {
    const modal = await this.modalController.create({
      component: AddEventPage,
    });
    modal.onDidDismiss().then(data => {
      if (data.data != undefined && data.data.event != undefined) {
        let event = data.data.event;
        this.events.push(new Event(
          data.data.id,
          event.owner,
          event.name,
          event.description,
          event.start,
          event.end
        ));
        this.eventSource = this.createCalendarEvents();
        this.filter(this.selectedDate);
      }
    });
    return await modal.present();
  }

  onTimeSelected(event: any) {
    this.selectedDate = this.getDate(event.selectedTime);
    this.filter(this.selectedDate);
  }

  filter(date: string) {
    this.filteredEvents = this.events.filter(item => {
      let dateStr = this.getDate(item.start);
      return date.indexOf(dateStr.substr(0, dateStr.indexOf(' '))) > -1;
    });
  }

  getDate(dateStr: string) {
    let date = new Date(String(dateStr).replace(' CET', ''));
    let day = String(date.getDate()).padStart(2, '0');
    let month = String(date.getMonth() + 1).padStart(2, '0');
    let year = date.getFullYear();
    let hour = String(date.getHours()).padStart(2, '0');
    let minute = String(date.getMinutes()).padStart(2, '0');
    return year + '-' + month + '-' + day + " " + hour + ":" + minute + ":00";
  }

}

export class Event {
  id: string;
  owner: string;
  name: string;
  description: string;
  start: string;
  end: string;

  constructor(id: string, owner: string, name: string, description: string, start: string, end: string) {
    this.id = id;
    this.owner = owner;
    this.name = name;
    this.description = description;
    this.start = start;
    this.end = end;
  }

}