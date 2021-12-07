import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http'

@Injectable({
  providedIn: 'root'
})
export class NotepadService {

  constructor(private httpClient: HttpClient) { }

  public getNotePads(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ShowBloc', data, { responseType:'text' });
  }

  public addNotePad(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/AddBloc', data, { responseType:'text' });
  }

  public updateNotePad(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/ModifyBloc', data, { responseType:'text' });
  }

  public removeNotePad(data: string) {
    return this.httpClient.post('https://proxyhokuro.herokuapp.com/https://hokuro.xyz/Vestigial/RemoveBloc', data, { responseType:'text' });
  }

}
