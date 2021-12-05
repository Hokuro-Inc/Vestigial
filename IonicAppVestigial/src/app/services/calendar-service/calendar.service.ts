import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class CalendarService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/ShowCalendar', data, { responseType:'text' });
  }

}
