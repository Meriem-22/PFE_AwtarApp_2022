import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddDonationsIssuedComponent } from './add-donations-issued.component';

describe('AddDonationsIssuedComponent', () => {
  let component: AddDonationsIssuedComponent;
  let fixture: ComponentFixture<AddDonationsIssuedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddDonationsIssuedComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddDonationsIssuedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
