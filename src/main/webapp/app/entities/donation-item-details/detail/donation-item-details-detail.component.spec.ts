import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DonationItemDetailsDetailComponent } from './donation-item-details-detail.component';

describe('DonationItemDetails Management Detail Component', () => {
  let comp: DonationItemDetailsDetailComponent;
  let fixture: ComponentFixture<DonationItemDetailsDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DonationItemDetailsDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ donationItemDetails: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DonationItemDetailsDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DonationItemDetailsDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load donationItemDetails on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.donationItemDetails).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
