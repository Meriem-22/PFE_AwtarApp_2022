import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { BeneficiaryService } from '../service/beneficiary.service';
import { IBeneficiary, Beneficiary } from '../beneficiary.model';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { TutorService } from 'app/entities/tutor/service/tutor.service';

import { BeneficiaryUpdateComponent } from './beneficiary-update.component';

describe('Beneficiary Management Update Component', () => {
  let comp: BeneficiaryUpdateComponent;
  let fixture: ComponentFixture<BeneficiaryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let beneficiaryService: BeneficiaryService;
  let authorizingOfficerService: AuthorizingOfficerService;
  let tutorService: TutorService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [BeneficiaryUpdateComponent],
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
      .overrideTemplate(BeneficiaryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(BeneficiaryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    beneficiaryService = TestBed.inject(BeneficiaryService);
    authorizingOfficerService = TestBed.inject(AuthorizingOfficerService);
    tutorService = TestBed.inject(TutorService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AuthorizingOfficer query and add missing value', () => {
      const beneficiary: IBeneficiary = { id: 456 };
      const authorizingOfficer: IAuthorizingOfficer = { id: 82698 };
      beneficiary.authorizingOfficer = authorizingOfficer;

      const authorizingOfficerCollection: IAuthorizingOfficer[] = [{ id: 54795 }];
      jest.spyOn(authorizingOfficerService, 'query').mockReturnValue(of(new HttpResponse({ body: authorizingOfficerCollection })));
      const additionalAuthorizingOfficers = [authorizingOfficer];
      const expectedCollection: IAuthorizingOfficer[] = [...additionalAuthorizingOfficers, ...authorizingOfficerCollection];
      jest.spyOn(authorizingOfficerService, 'addAuthorizingOfficerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ beneficiary });
      comp.ngOnInit();

      expect(authorizingOfficerService.query).toHaveBeenCalled();
      expect(authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing).toHaveBeenCalledWith(
        authorizingOfficerCollection,
        ...additionalAuthorizingOfficers
      );
      expect(comp.authorizingOfficersSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Tutor query and add missing value', () => {
      const beneficiary: IBeneficiary = { id: 456 };
      const tutor: ITutor = { id: 21786 };
      beneficiary.tutor = tutor;

      const tutorCollection: ITutor[] = [{ id: 93770 }];
      jest.spyOn(tutorService, 'query').mockReturnValue(of(new HttpResponse({ body: tutorCollection })));
      const additionalTutors = [tutor];
      const expectedCollection: ITutor[] = [...additionalTutors, ...tutorCollection];
      jest.spyOn(tutorService, 'addTutorToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ beneficiary });
      comp.ngOnInit();

      expect(tutorService.query).toHaveBeenCalled();
      expect(tutorService.addTutorToCollectionIfMissing).toHaveBeenCalledWith(tutorCollection, ...additionalTutors);
      expect(comp.tutorsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const beneficiary: IBeneficiary = { id: 456 };
      const authorizingOfficer: IAuthorizingOfficer = { id: 41070 };
      beneficiary.authorizingOfficer = authorizingOfficer;
      const tutor: ITutor = { id: 58870 };
      beneficiary.tutor = tutor;

      activatedRoute.data = of({ beneficiary });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(beneficiary));
      expect(comp.authorizingOfficersSharedCollection).toContain(authorizingOfficer);
      expect(comp.tutorsSharedCollection).toContain(tutor);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Beneficiary>>();
      const beneficiary = { id: 123 };
      jest.spyOn(beneficiaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ beneficiary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: beneficiary }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(beneficiaryService.update).toHaveBeenCalledWith(beneficiary);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Beneficiary>>();
      const beneficiary = new Beneficiary();
      jest.spyOn(beneficiaryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ beneficiary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: beneficiary }));
      saveSubject.complete();

      // THEN
      expect(beneficiaryService.create).toHaveBeenCalledWith(beneficiary);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Beneficiary>>();
      const beneficiary = { id: 123 };
      jest.spyOn(beneficiaryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ beneficiary });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(beneficiaryService.update).toHaveBeenCalledWith(beneficiary);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackAuthorizingOfficerById', () => {
      it('Should return tracked AuthorizingOfficer primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackAuthorizingOfficerById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackTutorById', () => {
      it('Should return tracked Tutor primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackTutorById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
