import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { EstablishmentTypeService } from '../service/establishment-type.service';
import { IEstablishmentType, EstablishmentType } from '../establishment-type.model';

import { EstablishmentTypeUpdateComponent } from './establishment-type-update.component';

describe('EstablishmentType Management Update Component', () => {
  let comp: EstablishmentTypeUpdateComponent;
  let fixture: ComponentFixture<EstablishmentTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let establishmentTypeService: EstablishmentTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [EstablishmentTypeUpdateComponent],
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
      .overrideTemplate(EstablishmentTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(EstablishmentTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    establishmentTypeService = TestBed.inject(EstablishmentTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const establishmentType: IEstablishmentType = { id: 456 };

      activatedRoute.data = of({ establishmentType });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(establishmentType));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstablishmentType>>();
      const establishmentType = { id: 123 };
      jest.spyOn(establishmentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establishmentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: establishmentType }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(establishmentTypeService.update).toHaveBeenCalledWith(establishmentType);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstablishmentType>>();
      const establishmentType = new EstablishmentType();
      jest.spyOn(establishmentTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establishmentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: establishmentType }));
      saveSubject.complete();

      // THEN
      expect(establishmentTypeService.create).toHaveBeenCalledWith(establishmentType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<EstablishmentType>>();
      const establishmentType = { id: 123 };
      jest.spyOn(establishmentTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ establishmentType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(establishmentTypeService.update).toHaveBeenCalledWith(establishmentType);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
