import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DonationsReceivedItemDetailComponent } from './donations-received-item-detail.component';

describe('DonationsReceivedItem Management Detail Component', () => {
  let comp: DonationsReceivedItemDetailComponent;
  let fixture: ComponentFixture<DonationsReceivedItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DonationsReceivedItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ donationsReceivedItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DonationsReceivedItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DonationsReceivedItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load donationsReceivedItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.donationsReceivedItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
