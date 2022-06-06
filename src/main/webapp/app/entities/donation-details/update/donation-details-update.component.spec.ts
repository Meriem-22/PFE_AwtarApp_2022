import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonationDetailsService } from '../service/donation-details.service';
import { IDonationDetails, DonationDetails } from '../donation-details.model';
import { IDonationsIssued } from 'app/entities/donations-issued/donations-issued.model';
import { DonationsIssuedService } from 'app/entities/donations-issued/service/donations-issued.service';
import { INature } from 'app/entities/nature/nature.model';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';

import { DonationDetailsUpdateComponent } from './donation-details-update.component';

describe('DonationDetails Management Update Component', () => {
  let comp: DonationDetailsUpdateComponent;
  let fixture: ComponentFixture<DonationDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donationDetailsService: DonationDetailsService;
  let donationsIssuedService: DonationsIssuedService;
  let natureService: NatureService;
  let beneficiaryService: BeneficiaryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonationDetailsUpdateComponent],
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
      .overrideTemplate(DonationDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonationDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donationDetailsService = TestBed.inject(DonationDetailsService);
    donationsIssuedService = TestBed.inject(DonationsIssuedService);
    natureService = TestBed.inject(NatureService);
    beneficiaryService = TestBed.inject(BeneficiaryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DonationsIssued query and add missing value', () => {
      const donationDetails: IDonationDetails = { id: 456 };
      const donationsIssued: IDonationsIssued = { id: 14574 };
      donationDetails.donationsIssued = donationsIssued;

      const donationsIssuedCollection: IDonationsIssued[] = [{ id: 56697 }];
      jest.spyOn(donationsIssuedService, 'query').mockReturnValue(of(new HttpResponse({ body: donationsIssuedCollection })));
      const additionalDonationsIssueds = [donationsIssued];
      const expectedCollection: IDonationsIssued[] = [...additionalDonationsIssueds, ...donationsIssuedCollection];
      jest.spyOn(donationsIssuedService, 'addDonationsIssuedToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      expect(donationsIssuedService.query).toHaveBeenCalled();
      expect(donationsIssuedService.addDonationsIssuedToCollectionIfMissing).toHaveBeenCalledWith(
        donationsIssuedCollection,
        ...additionalDonationsIssueds
      );
      expect(comp.donationsIssuedsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Nature query and add missing value', () => {
      const donationDetails: IDonationDetails = { id: 456 };
      const nature: INature = { id: 36992 };
      donationDetails.nature = nature;

      const natureCollection: INature[] = [{ id: 2298 }];
      jest.spyOn(natureService, 'query').mockReturnValue(of(new HttpResponse({ body: natureCollection })));
      const additionalNatures = [nature];
      const expectedCollection: INature[] = [...additionalNatures, ...natureCollection];
      jest.spyOn(natureService, 'addNatureToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      expect(natureService.query).toHaveBeenCalled();
      expect(natureService.addNatureToCollectionIfMissing).toHaveBeenCalledWith(natureCollection, ...additionalNatures);
      expect(comp.naturesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Beneficiary query and add missing value', () => {
      const donationDetails: IDonationDetails = { id: 456 };
      const beneficiary: IBeneficiary = { id: 80876 };
      donationDetails.beneficiary = beneficiary;

      const beneficiaryCollection: IBeneficiary[] = [{ id: 89488 }];
      jest.spyOn(beneficiaryService, 'query').mockReturnValue(of(new HttpResponse({ body: beneficiaryCollection })));
      const additionalBeneficiaries = [beneficiary];
      const expectedCollection: IBeneficiary[] = [...additionalBeneficiaries, ...beneficiaryCollection];
      jest.spyOn(beneficiaryService, 'addBeneficiaryToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      expect(beneficiaryService.query).toHaveBeenCalled();
      expect(beneficiaryService.addBeneficiaryToCollectionIfMissing).toHaveBeenCalledWith(
        beneficiaryCollection,
        ...additionalBeneficiaries
      );
      expect(comp.beneficiariesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const donationDetails: IDonationDetails = { id: 456 };
      const donationsIssued: IDonationsIssued = { id: 47044 };
      donationDetails.donationsIssued = donationsIssued;
      const nature: INature = { id: 77213 };
      donationDetails.nature = nature;
      const beneficiary: IBeneficiary = { id: 62006 };
      donationDetails.beneficiary = beneficiary;

      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(donationDetails));
      expect(comp.donationsIssuedsSharedCollection).toContain(donationsIssued);
      expect(comp.naturesSharedCollection).toContain(nature);
      expect(comp.beneficiariesSharedCollection).toContain(beneficiary);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationDetails>>();
      const donationDetails = { id: 123 };
      jest.spyOn(donationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(donationDetailsService.update).toHaveBeenCalledWith(donationDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationDetails>>();
      const donationDetails = new DonationDetails();
      jest.spyOn(donationDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationDetails }));
      saveSubject.complete();

      // THEN
      expect(donationDetailsService.create).toHaveBeenCalledWith(donationDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationDetails>>();
      const donationDetails = { id: 123 };
      jest.spyOn(donationDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donationDetailsService.update).toHaveBeenCalledWith(donationDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDonationsIssuedById', () => {
      it('Should return tracked DonationsIssued primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDonationsIssuedById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackNatureById', () => {
      it('Should return tracked Nature primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackNatureById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackBeneficiaryById', () => {
      it('Should return tracked Beneficiary primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackBeneficiaryById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
