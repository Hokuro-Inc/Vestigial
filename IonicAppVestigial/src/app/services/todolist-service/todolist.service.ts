import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class TodolistService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ShowToDoList', data, { responseType:'text' });
  }

  public addTask(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/CreateTask', data, { responseType:'text' });
  }

  public updateTask(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/UpdateTask', data, { responseType:'text' });
  }

  public removeTask(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/RemoveTask', data, { responseType:'text' });
  }

  public addToDoList(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/CreateToDoList', data, { responseType:'text' });
  }

  public removeToDoList(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/RemoveList', data, { responseType:'text' });
  }
  
}
