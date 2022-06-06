import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolLevelDetailComponent } from './school-level-detail.component';

describe('SchoolLevel Management Detail Component', () => {
  let comp: SchoolLevelDetailComponent;
  let fixture: ComponentFixture<SchoolLevelDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SchoolLevelDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ schoolLevel: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SchoolLevelDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SchoolLevelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load schoolLevel on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.schoolLevel).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
