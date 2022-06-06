import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { VisitService } from '../service/visit.service';
import { IVisit, Visit } from '../visit.model';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';

import { VisitUpdateComponent } from './visit-update.component';

describe('Visit Management Update Component', () => {
  let comp: VisitUpdateComponent;
  let fixture: ComponentFixture<VisitUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let visitService: VisitService;
  let beneficiaryService: BeneficiaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [VisitUpdateComponent],
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
      .overrideTemplate(VisitUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(VisitUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    visitService = TestBed.inject(VisitService);
    beneficiaryService = TestBed.inject(BeneficiaryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Beneficiary query and add missing value', () => {
      const visit: IVisit = { id: 456 };
      const beneficiary: IBeneficiary = { id: 78198 };
      visit.beneficiary = beneficiary;

      const beneficiaryCollection: IBeneficiary[] = [{ id: 82613 }];
      jest.spyOn(beneficiaryService, 'query').mockReturnValue(of(new HttpResponse({ body: beneficiaryCollection })));
      const additionalBeneficiaries = [beneficiary];
      const expectedCollection: IBeneficiary[] = [...additionalBeneficiaries, ...beneficiaryCollection];
      jest.spyOn(beneficiaryService, 'addBeneficiaryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ visit });
      comp.ngOnInit();

      expect(beneficiaryService.query).toHaveBeenCalled();
      expect(beneficiaryService.addBeneficiaryToCollectionIfMissing).toHaveBeenCalledWith(
        beneficiaryCollection,
        ...additionalBeneficiaries
      );
      expect(comp.beneficiariesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const visit: IVisit = { id: 456 };
      const beneficiary: IBeneficiary = { id: 45613 };
      visit.beneficiary = beneficiary;

      activatedRoute.data = of({ visit });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(visit));
      expect(comp.beneficiariesSharedCollection).toContain(beneficiary);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Visit>>();
      const visit = { id: 123 };
      jest.spyOn(visitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ visit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: visit }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(visitService.update).toHaveBeenCalledWith(visit);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Visit>>();
      const visit = new Visit();
      jest.spyOn(visitService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ visit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: visit }));
      saveSubject.complete();

      // THEN
      expect(visitService.create).toHaveBeenCalledWith(visit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Visit>>();
      const visit = { id: 123 };
      jest.spyOn(visitService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ visit });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(visitService.update).toHaveBeenCalledWith(visit);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackBeneficiaryById', () => {
      it('Should return tracked Beneficiary primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBeneficiaryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
