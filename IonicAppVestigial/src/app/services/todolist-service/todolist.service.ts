import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ToDoListService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/ShowToDoList', data, { responseType:'text' });
  }

}