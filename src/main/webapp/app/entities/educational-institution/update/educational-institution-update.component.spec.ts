import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EducationalInstitutionService } from '../service/educational-institution.service';
import { IEducationalInstitution, EducationalInstitution } from '../educational-institution.model';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';

import { EducationalInstitutionUpdateComponent } from './educational-institution-update.component';

describe('EducationalInstitution Management Update Component', () => {
  let comp: EducationalInstitutionUpdateComponent;
  let fixture: ComponentFixture<EducationalInstitutionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let educationalInstitutionService: EducationalInstitutionService;
  let cityService: CityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EducationalInstitutionUpdateComponent],
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
      .overrideTemplate(EducationalInstitutionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EducationalInstitutionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    educationalInstitutionService = TestBed.inject(EducationalInstitutionService);
    cityService = TestBed.inject(CityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call City query and add missing value', () => {
      const educationalInstitution: IEducationalInstitution = { id: 456 };
      const city: ICity = { id: 35371 };
      educationalInstitution.city = city;

      const cityCollection: ICity[] = [{ id: 38455 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCities = [city];
      const expectedCollection: ICity[] = [...additionalCities, ...cityCollection];
      jest.spyOn(cityService, 'addCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ educationalInstitution });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCityToCollectionIfMissing).toHaveBeenCalledWith(cityCollection, ...additionalCities);
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const educationalInstitution: IEducationalInstitution = { id: 456 };
      const city: ICity = { id: 89667 };
      educationalInstitution.city = city;

      activatedRoute.data = of({ educationalInstitution });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(educationalInstitution));
      expect(comp.citiesSharedCollection).toContain(city);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EducationalInstitution>>();
      const educationalInstitution = { id: 123 };
      jest.spyOn(educationalInstitutionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationalInstitution });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: educationalInstitution }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(educationalInstitutionService.update).toHaveBeenCalledWith(educationalInstitution);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EducationalInstitution>>();
      const educationalInstitution = new EducationalInstitution();
      jest.spyOn(educationalInstitutionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationalInstitution });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: educationalInstitution }));
      saveSubject.complete();

      // THEN
      expect(educationalInstitutionService.create).toHaveBeenCalledWith(educationalInstitution);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EducationalInstitution>>();
      const educationalInstitution = { id: 123 };
      jest.spyOn(educationalInstitutionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ educationalInstitution });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(educationalInstitutionService.update).toHaveBeenCalledWith(educationalInstitution);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackCityById', () => {
      it('Should return tracked City primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
