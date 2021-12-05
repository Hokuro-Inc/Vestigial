import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class TodolistService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/ShowToDoList', data, { responseType:'text' });
  }

  public addTask(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/CreateTask', data, { responseType:'text' });
  }

  public updateTask(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/UpdateTask', data, { responseType:'text' });
  }

  public removeTask(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/RemoveTask', data, { responseType:'text' });
  }

  public addToDoList(data: string) {
    console.log(data)
    return this.httpClient.post('http://localhost:8080/Vestigial/CreateToDoList', data, { responseType:'text' });
  }

  public removeToDoList(data: string) {
    return this.httpClient.post('http://localhost:8080/Vestigial/RemoveList', data, { responseType:'text' });
  }
  
}
