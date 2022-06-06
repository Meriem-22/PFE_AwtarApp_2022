import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { EducationalInstitutionDetailComponent } from './educational-institution-detail.component';

describe('EducationalInstitution Management Detail Component', () => {
  let comp: EducationalInstitutionDetailComponent;
  let fixture: ComponentFixture<EducationalInstitutionDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [EducationalInstitutionDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ educationalInstitution: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(EducationalInstitutionDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EducationalInstitutionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load educationalInstitution on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.educationalInstitution).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
