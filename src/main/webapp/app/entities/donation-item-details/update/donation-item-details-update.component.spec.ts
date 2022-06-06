import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonationItemDetailsService } from '../service/donation-item-details.service';
import { IDonationItemDetails, DonationItemDetails } from '../donation-item-details.model';
import { IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

import { DonationItemDetailsUpdateComponent } from './donation-item-details-update.component';

describe('DonationItemDetails Management Update Component', () => {
  let comp: DonationItemDetailsUpdateComponent;
  let fixture: ComponentFixture<DonationItemDetailsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donationItemDetailsService: DonationItemDetailsService;
  let donationDetailsService: DonationDetailsService;
  let itemService: ItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonationItemDetailsUpdateComponent],
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
      .overrideTemplate(DonationItemDetailsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonationItemDetailsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donationItemDetailsService = TestBed.inject(DonationItemDetailsService);
    donationDetailsService = TestBed.inject(DonationDetailsService);
    itemService = TestBed.inject(ItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call DonationDetails query and add missing value', () => {
      const donationItemDetails: IDonationItemDetails = { id: 456 };
      const donationDetails: IDonationDetails = { id: 82475 };
      donationItemDetails.donationDetails = donationDetails;

      const donationDetailsCollection: IDonationDetails[] = [{ id: 53758 }];
      jest.spyOn(donationDetailsService, 'query').mockReturnValue(of(new HttpResponse({ body: donationDetailsCollection })));
      const additionalDonationDetails = [donationDetails];
      const expectedCollection: IDonationDetails[] = [...additionalDonationDetails, ...donationDetailsCollection];
      jest.spyOn(donationDetailsService, 'addDonationDetailsToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationItemDetails });
      comp.ngOnInit();

      expect(donationDetailsService.query).toHaveBeenCalled();
      expect(donationDetailsService.addDonationDetailsToCollectionIfMissing).toHaveBeenCalledWith(
        donationDetailsCollection,
        ...additionalDonationDetails
      );
      expect(comp.donationDetailsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Item query and add missing value', () => {
      const donationItemDetails: IDonationItemDetails = { id: 456 };
      const item: IItem = { id: 31661 };
      donationItemDetails.item = item;

      const itemCollection: IItem[] = [{ id: 28009 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [item];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationItemDetails });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const donationItemDetails: IDonationItemDetails = { id: 456 };
      const donationDetails: IDonationDetails = { id: 984 };
      donationItemDetails.donationDetails = donationDetails;
      const item: IItem = { id: 21702 };
      donationItemDetails.item = item;

      activatedRoute.data = of({ donationItemDetails });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(donationItemDetails));
      expect(comp.donationDetailsSharedCollection).toContain(donationDetails);
      expect(comp.itemsSharedCollection).toContain(item);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationItemDetails>>();
      const donationItemDetails = { id: 123 };
      jest.spyOn(donationItemDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationItemDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationItemDetails }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(donationItemDetailsService.update).toHaveBeenCalledWith(donationItemDetails);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationItemDetails>>();
      const donationItemDetails = new DonationItemDetails();
      jest.spyOn(donationItemDetailsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationItemDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationItemDetails }));
      saveSubject.complete();

      // THEN
      expect(donationItemDetailsService.create).toHaveBeenCalledWith(donationItemDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationItemDetails>>();
      const donationItemDetails = { id: 123 };
      jest.spyOn(donationItemDetailsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationItemDetails });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donationItemDetailsService.update).toHaveBeenCalledWith(donationItemDetails);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackDonationDetailsById', () => {
      it('Should return tracked DonationDetails primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDonationDetailsById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackItemById', () => {
      it('Should return tracked Item primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackItemById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
