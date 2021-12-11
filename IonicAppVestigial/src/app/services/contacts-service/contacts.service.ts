import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ContactsService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ShowAgenda', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/ShowAgenda', data, { responseType:'text' });
  }

  public addContact(data: string) {
   return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/AddContact', data, { responseType:'text' });
   //return this.httpClient.post('http://localhost:8080/Vestigial/AddContact', data, { responseType:'text' });
  }

  public updateContact(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/UpdateContact', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/UpdateContact', data, { responseType:'text' });
  }

  public removeContact(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/RemoveContact', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/RemoveContact', data, { responseType:'text' });
  }

  public getGroups(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/GetGroups', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/GetGroups', data, { responseType:'text' });
  }

  public addGroup(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/AddGroup', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/AddGroup', data, { responseType:'text' });
  }

  public updateGroup(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/UpdateGroup', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/UpdateGroup', data, { responseType:'text' });
  }

  public removeGroup(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/RemoveGroup', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/RemoveGroup', data, { responseType:'text' });
  }

  public getProfile(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ShowProfile', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/ShowProfile', data, { responseType:'text' });

  }  

  public importContact(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ImportContact', data, { responseType:'text' });
    //return this.httpClient.post('http://localhost:8080/Vestigial/ImportContact', data, { responseType:'text' });
  }

}