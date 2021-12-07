import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class ContactsService {

  constructor(private httpClient: HttpClient) { }

  public getData(data: string) {
//    return this.httpClient.post('https://hokuro.xyz/Vestigial/ShowAgenda', data, { responseType:'text' });
    return this.httpClient.post('http://localhost:8080/Vestigial/ShowAgenda', data, { responseType:'text' });
  }

  public addContact(data: string) {
 //   return this.httpClient.post('https://hokuro.xyz/Vestigial/AddContact', data, { responseType:'text' });
   return this.httpClient.post('http://localhost:8080/Vestigial/AddContact', data, { responseType:'text' });
  }

  public updateContact(data: string) {
    //return this.httpClient.post('https://hokuro.xyz/Vestigial/UpdateContact', data, { responseType:'text' });
    return this.httpClient.post('http://localhost:8080/Vestigial/UpdateContact', data, { responseType:'text' });
  }

  public removeContact(data: string) {
//    return this.httpClient.post('https://hokuro.xyz/Vestigial/RemoveContact', data, { responseType:'text' });
    return this.httpClient.post('http://localhost:8080/Vestigial/RemoveContact', data, { responseType:'text' });
  }
  
}