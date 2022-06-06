import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { StatusOfHealthService } from '../service/status-of-health.service';
import { IStatusOfHealth, StatusOfHealth } from '../status-of-health.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { IHealthStatusCategory } from 'app/entities/health-status-category/health-status-category.model';
import { HealthStatusCategoryService } from 'app/entities/health-status-category/service/health-status-category.service';

import { StatusOfHealthUpdateComponent } from './status-of-health-update.component';

describe('StatusOfHealth Management Update Component', () => {
  let comp: StatusOfHealthUpdateComponent;
  let fixture: ComponentFixture<StatusOfHealthUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let statusOfHealthService: StatusOfHealthService;
  let profileService: ProfileService;
  let healthStatusCategoryService: HealthStatusCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [StatusOfHealthUpdateComponent],
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
      .overrideTemplate(StatusOfHealthUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(StatusOfHealthUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    statusOfHealthService = TestBed.inject(StatusOfHealthService);
    profileService = TestBed.inject(ProfileService);
    healthStatusCategoryService = TestBed.inject(HealthStatusCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Profile query and add missing value', () => {
      const statusOfHealth: IStatusOfHealth = { id: 456 };
      const person: IProfile = { id: 1887 };
      statusOfHealth.person = person;

      const profileCollection: IProfile[] = [{ id: 53610 }];
      jest.spyOn(profileService, 'query').mockReturnValue(of(new HttpResponse({ body: profileCollection })));
      const additionalProfiles = [person];
      const expectedCollection: IProfile[] = [...additionalProfiles, ...profileCollection];
      jest.spyOn(profileService, 'addProfileToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statusOfHealth });
      comp.ngOnInit();

      expect(profileService.query).toHaveBeenCalled();
      expect(profileService.addProfileToCollectionIfMissing).toHaveBeenCalledWith(profileCollection, ...additionalProfiles);
      expect(comp.profilesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call HealthStatusCategory query and add missing value', () => {
      const statusOfHealth: IStatusOfHealth = { id: 456 };
      const healthStatusCategory: IHealthStatusCategory = { id: 5070 };
      statusOfHealth.healthStatusCategory = healthStatusCategory;

      const healthStatusCategoryCollection: IHealthStatusCategory[] = [{ id: 47666 }];
      jest.spyOn(healthStatusCategoryService, 'query').mockReturnValue(of(new HttpResponse({ body: healthStatusCategoryCollection })));
      const additionalHealthStatusCategories = [healthStatusCategory];
      const expectedCollection: IHealthStatusCategory[] = [...additionalHealthStatusCategories, ...healthStatusCategoryCollection];
      jest.spyOn(healthStatusCategoryService, 'addHealthStatusCategoryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ statusOfHealth });
      comp.ngOnInit();

      expect(healthStatusCategoryService.query).toHaveBeenCalled();
      expect(healthStatusCategoryService.addHealthStatusCategoryToCollectionIfMissing).toHaveBeenCalledWith(
        healthStatusCategoryCollection,
        ...additionalHealthStatusCategories
      );
      expect(comp.healthStatusCategoriesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const statusOfHealth: IStatusOfHealth = { id: 456 };
      const person: IProfile = { id: 39201 };
      statusOfHealth.person = person;
      const healthStatusCategory: IHealthStatusCategory = { id: 74081 };
      statusOfHealth.healthStatusCategory = healthStatusCategory;

      activatedRoute.data = of({ statusOfHealth });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(statusOfHealth));
      expect(comp.profilesSharedCollection).toContain(person);
      expect(comp.healthStatusCategoriesSharedCollection).toContain(healthStatusCategory);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StatusOfHealth>>();
      const statusOfHealth = { id: 123 };
      jest.spyOn(statusOfHealthService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusOfHealth });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statusOfHealth }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(statusOfHealthService.update).toHaveBeenCalledWith(statusOfHealth);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StatusOfHealth>>();
      const statusOfHealth = new StatusOfHealth();
      jest.spyOn(statusOfHealthService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusOfHealth });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: statusOfHealth }));
      saveSubject.complete();

      // THEN
      expect(statusOfHealthService.create).toHaveBeenCalledWith(statusOfHealth);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<StatusOfHealth>>();
      const statusOfHealth = { id: 123 };
      jest.spyOn(statusOfHealthService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ statusOfHealth });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(statusOfHealthService.update).toHaveBeenCalledWith(statusOfHealth);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackProfileById', () => {
      it('Should return tracked Profile primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackProfileById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackHealthStatusCategoryById', () => {
      it('Should return tracked HealthStatusCategory primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackHealthStatusCategoryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
