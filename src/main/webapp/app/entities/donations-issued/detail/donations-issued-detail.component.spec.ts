import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DonationsIssuedDetailComponent } from './donations-issued-detail.component';

describe('DonationsIssued Management Detail Component', () => {
  let comp: DonationsIssuedDetailComponent;
  let fixture: ComponentFixture<DonationsIssuedDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [DonationsIssuedDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ donationsIssued: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(DonationsIssuedDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(DonationsIssuedDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load donationsIssued on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.donationsIssued).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
