import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ShowCalendar', data, { responseType:'text' });
  }
 
  public addEvent(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/CreateEvent', data, { responseType:'text' });
  }

  public updateEvent(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/UpdateEvent', data, { responseType:'text' });
  }
  
  public removeEvent(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/RemoveEvent', data, { responseType:'text' });
  }

}
