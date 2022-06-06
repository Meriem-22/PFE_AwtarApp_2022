import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { HealthStatusCategoryService } from '../service/health-status-category.service';
import { IHealthStatusCategory, HealthStatusCategory } from '../health-status-category.model';

import { HealthStatusCategoryUpdateComponent } from './health-status-category-update.component';

describe('HealthStatusCategory Management Update Component', () => {
  let comp: HealthStatusCategoryUpdateComponent;
  let fixture: ComponentFixture<HealthStatusCategoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let healthStatusCategoryService: HealthStatusCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [HealthStatusCategoryUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(HealthStatusCategoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(HealthStatusCategoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    healthStatusCategoryService = TestBed.inject(HealthStatusCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const healthStatusCategory: IHealthStatusCategory = { id: 456 };

      activatedRoute.data = of({ healthStatusCategory });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(healthStatusCategory));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HealthStatusCategory>>();
      const healthStatusCategory = { id: 123 };
      jest.spyOn(healthStatusCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ healthStatusCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: healthStatusCategory }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(healthStatusCategoryService.update).toHaveBeenCalledWith(healthStatusCategory);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HealthStatusCategory>>();
      const healthStatusCategory = new HealthStatusCategory();
      jest.spyOn(healthStatusCategoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ healthStatusCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: healthStatusCategory }));
      saveSubject.complete();

      // THEN
      expect(healthStatusCategoryService.create).toHaveBeenCalledWith(healthStatusCategory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<HealthStatusCategory>>();
      const healthStatusCategory = { id: 123 };
      jest.spyOn(healthStatusCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ healthStatusCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(healthStatusCategoryService.update).toHaveBeenCalledWith(healthStatusCategory);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
