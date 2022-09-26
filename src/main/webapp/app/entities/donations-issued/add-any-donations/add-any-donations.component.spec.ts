import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddAnyDonationsComponent } from './add-any-donations.component';

describe('AddAnyDonationsComponent', () => {
  let component: AddAnyDonationsComponent;
  let fixture: ComponentFixture<AddAnyDonationsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [AddAnyDonationsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AddAnyDonationsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
