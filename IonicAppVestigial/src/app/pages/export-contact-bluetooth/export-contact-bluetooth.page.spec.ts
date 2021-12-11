import { ComponentFixture, TestBed, waitForAsync } from '@angular/core/testing';
import { IonicModule } from '@ionic/angular';

import { ExportContactBluetoothPage } from './export-contact-bluetooth.page';

describe('ExportContactBluetoothPage', () => {
  let component: ExportContactBluetoothPage;
  let fixture: ComponentFixture<ExportContactBluetoothPage>;

  beforeEach(waitForAsync(() => {
    TestBed.configureTestingModule({
      declarations: [ ExportContactBluetoothPage ],
      imports: [IonicModule.forRoot()]
    }).compileComponents();

    fixture = TestBed.createComponent(ExportContactBluetoothPage);
    component = fixture.componentInstance;
    fixture.detectChanges();
  }));

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
