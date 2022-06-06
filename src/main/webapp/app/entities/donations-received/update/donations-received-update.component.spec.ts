import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonationsReceivedService } from '../service/donations-received.service';
import { IDonationsReceived, DonationsReceived } from '../donations-received.model';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';

import { DonationsReceivedUpdateComponent } from './donations-received-update.component';

describe('DonationsReceived Management Update Component', () => {
  let comp: DonationsReceivedUpdateComponent;
  let fixture: ComponentFixture<DonationsReceivedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donationsReceivedService: DonationsReceivedService;
  let authorizingOfficerService: AuthorizingOfficerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonationsReceivedUpdateComponent],
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
      .overrideTemplate(DonationsReceivedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonationsReceivedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donationsReceivedService = TestBed.inject(DonationsReceivedService);
    authorizingOfficerService = TestBed.inject(AuthorizingOfficerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call AuthorizingOfficer query and add missing value', () => {
      const donationsReceived: IDonationsReceived = { id: 456 };
      const authorizingOfficer: IAuthorizingOfficer = { id: 94099 };
      donationsReceived.authorizingOfficer = authorizingOfficer;

      const authorizingOfficerCollection: IAuthorizingOfficer[] = [{ id: 74678 }];
      jest.spyOn(authorizingOfficerService, 'query').mockReturnValue(of(new HttpResponse({ body: authorizingOfficerCollection })));
      const additionalAuthorizingOfficers = [authorizingOfficer];
      const expectedCollection: IAuthorizingOfficer[] = [...additionalAuthorizingOfficers, ...authorizingOfficerCollection];
      jest.spyOn(authorizingOfficerService, 'addAuthorizingOfficerToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationsReceived });
      comp.ngOnInit();

      expect(authorizingOfficerService.query).toHaveBeenCalled();
      expect(authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing).toHaveBeenCalledWith(
        authorizingOfficerCollection,
        ...additionalAuthorizingOfficers
      );
      expect(comp.authorizingOfficersSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const donationsReceived: IDonationsReceived = { id: 456 };
      const authorizingOfficer: IAuthorizingOfficer = { id: 80732 };
      donationsReceived.authorizingOfficer = authorizingOfficer;

      activatedRoute.data = of({ donationsReceived });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(donationsReceived));
      expect(comp.authorizingOfficersSharedCollection).toContain(authorizingOfficer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsReceived>>();
      const donationsReceived = { id: 123 };
      jest.spyOn(donationsReceivedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsReceived });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationsReceived }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(donationsReceivedService.update).toHaveBeenCalledWith(donationsReceived);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsReceived>>();
      const donationsReceived = new DonationsReceived();
      jest.spyOn(donationsReceivedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsReceived });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationsReceived }));
      saveSubject.complete();

      // THEN
      expect(donationsReceivedService.create).toHaveBeenCalledWith(donationsReceived);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsReceived>>();
      const donationsReceived = { id: 123 };
      jest.spyOn(donationsReceivedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsReceived });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donationsReceivedService.update).toHaveBeenCalledWith(donationsReceived);
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
  });
});
