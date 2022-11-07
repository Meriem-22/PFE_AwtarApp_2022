import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DonationIssuedDetailsComponent } from './donation-issued-details.component';

describe('DonationIssuedDetailsComponent', () => {
  let component: DonationIssuedDetailsComponent;
  let fixture: ComponentFixture<DonationIssuedDetailsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [DonationIssuedDetailsComponent],
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DonationIssuedDetailsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
