import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SchoolLevelItemDetailComponent } from './school-level-item-detail.component';

describe('SchoolLevelItem Management Detail Component', () => {
  let comp: SchoolLevelItemDetailComponent;
  let fixture: ComponentFixture<SchoolLevelItemDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [SchoolLevelItemDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ schoolLevelItem: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(SchoolLevelItemDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SchoolLevelItemDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load schoolLevelItem on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.schoolLevelItem).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
