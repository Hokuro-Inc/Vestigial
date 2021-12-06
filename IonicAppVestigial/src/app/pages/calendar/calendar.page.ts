import { Component, OnInit, ViewChild } from '@angular/core';
import { ModalController } from '@ionic/angular';
import { CalendarComponent } from 'ion2-calendar';
import { CalendarService } from 'src/app/services/calendar-service/calendar.service';
import { EventViewPage } from '../event-view/event-view.page';

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
 
	calendar = {
		mode: 'month',
		currentDate: new Date(),
	};
	
	selectedDate: Date;
	
	@ViewChild(CalendarComponent) myCal: CalendarComponent;
	
	constructor(private calendarService: CalendarService, private modalController: ModalController) { }
	
	ngOnInit() {
		let curDate = new Date();
		let day = String(curDate.getDate()).padStart(2, '0');
		let month = curDate.getMonth() + 1;
		let year = curDate.getFullYear();
		let curDateStr = year + '-' + month + '-' + day;

		let user = {
			"user": sessionStorage.getItem("user"),
		};

		this.calendarService.getData(JSON.stringify(user)).subscribe(
			(response) => {
				//console.log("Respuesta", response);
				if (response != '') {
					let data = JSON.parse(response).Calendar;
					this.events = [];

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

					this.filteredEvents = this.events.filter(item => {
						return item.start == curDateStr;
					});
         		 	//this.events.forEach(e => console.log(e));
					//this.filteredEvents.forEach(e => console.log(e));
				}
			},
			(error) => console.log("Error", error),
			() => {
				console.log("Completed");
			}
		);
	}
	
	// Selected date reange and hence title changed
	onViewTitleChanged(title: string) {
		this.viewTitle = title;
	}

	async showEvent(event: Event) {
		const modal = await this.modalController.create({
			component: EventViewPage,
			componentProps: {
				event: event
			}
		});
		return await modal.present();
	}

	onTimeSelected(event: any) {
		let curDate = event.selectedTime;
		let day = String(curDate.getDate()).padStart(2, '0');
		let month = curDate.getMonth() + 1;
		let year = curDate.getFullYear();
		this.date = year + '-' + month + '-' + day;
		
		this.filteredEvents = this.events.filter(item => {
			return item.start == this.date;
		});

		//this.filteredEvents.forEach(item => console.log(item));
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
