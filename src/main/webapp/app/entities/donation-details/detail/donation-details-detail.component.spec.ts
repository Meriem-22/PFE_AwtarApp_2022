import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DonationDetailsDetailComponent } from './donation-details-detail.component';

describe('DonationDetails Management Detail Component', () => {
  let comp: DonationDetailsDetailComponent;
  let fixture: ComponentFixture<DonationDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DonationDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ donationDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DonationDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DonationDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load donationDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.donationDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
