import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HealthStatusCategoryDetailComponent } from './health-status-category-detail.component';

describe('HealthStatusCategory Management Detail Component', () => {
  let comp: HealthStatusCategoryDetailComponent;
  let fixture: ComponentFixture<HealthStatusCategoryDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [HealthStatusCategoryDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ healthStatusCategory: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(HealthStatusCategoryDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(HealthStatusCategoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load healthStatusCategory on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.healthStatusCategory).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
