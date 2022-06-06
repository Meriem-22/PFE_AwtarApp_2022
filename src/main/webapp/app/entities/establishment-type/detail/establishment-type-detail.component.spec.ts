import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EstablishmentTypeDetailComponent } from './establishment-type-detail.component';

describe('EstablishmentType Management Detail Component', () => {
  let comp: EstablishmentTypeDetailComponent;
  let fixture: ComponentFixture<EstablishmentTypeDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EstablishmentTypeDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ establishmentType: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EstablishmentTypeDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EstablishmentTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load establishmentType on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.establishmentType).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
