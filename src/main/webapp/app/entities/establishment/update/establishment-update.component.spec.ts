import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstablishmentService } from '../service/establishment.service';
import { IEstablishment, Establishment } from '../establishment.model';
import { IEstablishmentType } from 'app/entities/establishment-type/establishment-type.model';
import { EstablishmentTypeService } from 'app/entities/establishment-type/service/establishment-type.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';

import { EstablishmentUpdateComponent } from './establishment-update.component';

describe('Establishment Management Update Component', () => {
  let comp: EstablishmentUpdateComponent;
  let fixture: ComponentFixture<EstablishmentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let establishmentService: EstablishmentService;
  let establishmentTypeService: EstablishmentTypeService;
  let cityService: CityService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstablishmentUpdateComponent],
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
      .overrideTemplate(EstablishmentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstablishmentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    establishmentService = TestBed.inject(EstablishmentService);
    establishmentTypeService = TestBed.inject(EstablishmentTypeService);
    cityService = TestBed.inject(CityService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call EstablishmentType query and add missing value', () => {
      const establishment: IEstablishment = { id: 456 };
      const establishmentType: IEstablishmentType = { id: 35117 };
      establishment.establishmentType = establishmentType;

      const establishmentTypeCollection: IEstablishmentType[] = [{ id: 47382 }];
      jest.spyOn(establishmentTypeService, 'query').mockReturnValue(of(new HttpResponse({ body: establishmentTypeCollection })));
      const additionalEstablishmentTypes = [establishmentType];
      const expectedCollection: IEstablishmentType[] = [...additionalEstablishmentTypes, ...establishmentTypeCollection];
      jest.spyOn(establishmentTypeService, 'addEstablishmentTypeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ establishment });
      comp.ngOnInit();

      expect(establishmentTypeService.query).toHaveBeenCalled();
      expect(establishmentTypeService.addEstablishmentTypeToCollectionIfMissing).toHaveBeenCalledWith(
        establishmentTypeCollection,
        ...additionalEstablishmentTypes
      );
      expect(comp.establishmentTypesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call City query and add missing value', () => {
      const establishment: IEstablishment = { id: 456 };
      const city: ICity = { id: 10238 };
      establishment.city = city;

      const cityCollection: ICity[] = [{ id: 8915 }];
      jest.spyOn(cityService, 'query').mockReturnValue(of(new HttpResponse({ body: cityCollection })));
      const additionalCities = [city];
      const expectedCollection: ICity[] = [...additionalCities, ...cityCollection];
      jest.spyOn(cityService, 'addCityToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ establishment });
      comp.ngOnInit();

      expect(cityService.query).toHaveBeenCalled();
      expect(cityService.addCityToCollectionIfMissing).toHaveBeenCalledWith(cityCollection, ...additionalCities);
      expect(comp.citiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const establishment: IEstablishment = { id: 456 };
      const establishmentType: IEstablishmentType = { id: 87586 };
      establishment.establishmentType = establishmentType;
      const city: ICity = { id: 98968 };
      establishment.city = city;

      activatedRoute.data = of({ establishment });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(establishment));
      expect(comp.establishmentTypesSharedCollection).toContain(establishmentType);
      expect(comp.citiesSharedCollection).toContain(city);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Establishment>>();
      const establishment = { id: 123 };
      jest.spyOn(establishmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establishment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: establishment }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(establishmentService.update).toHaveBeenCalledWith(establishment);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Establishment>>();
      const establishment = new Establishment();
      jest.spyOn(establishmentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establishment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: establishment }));
      saveSubject.complete();

      // THEN
      expect(establishmentService.create).toHaveBeenCalledWith(establishment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Establishment>>();
      const establishment = { id: 123 };
      jest.spyOn(establishmentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establishment });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(establishmentService.update).toHaveBeenCalledWith(establishment);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEstablishmentTypeById', () => {
      it('Should return tracked EstablishmentType primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEstablishmentTypeById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackCityById', () => {
      it('Should return tracked City primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackCityById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
