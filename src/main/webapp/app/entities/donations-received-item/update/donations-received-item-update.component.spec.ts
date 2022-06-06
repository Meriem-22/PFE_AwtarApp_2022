import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonationsReceivedItemService } from '../service/donations-received-item.service';
import { IDonationsReceivedItem, DonationsReceivedItem } from '../donations-received-item.model';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { IDonationsReceived } from 'app/entities/donations-received/donations-received.model';
import { DonationsReceivedService } from 'app/entities/donations-received/service/donations-received.service';

import { DonationsReceivedItemUpdateComponent } from './donations-received-item-update.component';

describe('DonationsReceivedItem Management Update Component', () => {
  let comp: DonationsReceivedItemUpdateComponent;
  let fixture: ComponentFixture<DonationsReceivedItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donationsReceivedItemService: DonationsReceivedItemService;
  let itemService: ItemService;
  let donationsReceivedService: DonationsReceivedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonationsReceivedItemUpdateComponent],
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
      .overrideTemplate(DonationsReceivedItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonationsReceivedItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donationsReceivedItemService = TestBed.inject(DonationsReceivedItemService);
    itemService = TestBed.inject(ItemService);
    donationsReceivedService = TestBed.inject(DonationsReceivedService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Item query and add missing value', () => {
      const donationsReceivedItem: IDonationsReceivedItem = { id: 456 };
      const item: IItem = { id: 39737 };
      donationsReceivedItem.item = item;

      const itemCollection: IItem[] = [{ id: 14884 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [item];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationsReceivedItem });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call DonationsReceived query and add missing value', () => {
      const donationsReceivedItem: IDonationsReceivedItem = { id: 456 };
      const donationsReceived: IDonationsReceived = { id: 65867 };
      donationsReceivedItem.donationsReceived = donationsReceived;

      const donationsReceivedCollection: IDonationsReceived[] = [{ id: 33828 }];
      jest.spyOn(donationsReceivedService, 'query').mockReturnValue(of(new HttpResponse({ body: donationsReceivedCollection })));
      const additionalDonationsReceiveds = [donationsReceived];
      const expectedCollection: IDonationsReceived[] = [...additionalDonationsReceiveds, ...donationsReceivedCollection];
      jest.spyOn(donationsReceivedService, 'addDonationsReceivedToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ donationsReceivedItem });
      comp.ngOnInit();

      expect(donationsReceivedService.query).toHaveBeenCalled();
      expect(donationsReceivedService.addDonationsReceivedToCollectionIfMissing).toHaveBeenCalledWith(
        donationsReceivedCollection,
        ...additionalDonationsReceiveds
      );
      expect(comp.donationsReceivedsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const donationsReceivedItem: IDonationsReceivedItem = { id: 456 };
      const item: IItem = { id: 98729 };
      donationsReceivedItem.item = item;
      const donationsReceived: IDonationsReceived = { id: 77345 };
      donationsReceivedItem.donationsReceived = donationsReceived;

      activatedRoute.data = of({ donationsReceivedItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(donationsReceivedItem));
      expect(comp.itemsSharedCollection).toContain(item);
      expect(comp.donationsReceivedsSharedCollection).toContain(donationsReceived);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsReceivedItem>>();
      const donationsReceivedItem = { id: 123 };
      jest.spyOn(donationsReceivedItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsReceivedItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationsReceivedItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(donationsReceivedItemService.update).toHaveBeenCalledWith(donationsReceivedItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsReceivedItem>>();
      const donationsReceivedItem = new DonationsReceivedItem();
      jest.spyOn(donationsReceivedItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsReceivedItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationsReceivedItem }));
      saveSubject.complete();

      // THEN
      expect(donationsReceivedItemService.create).toHaveBeenCalledWith(donationsReceivedItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsReceivedItem>>();
      const donationsReceivedItem = { id: 123 };
      jest.spyOn(donationsReceivedItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsReceivedItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donationsReceivedItemService.update).toHaveBeenCalledWith(donationsReceivedItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackItemById', () => {
      it('Should return tracked Item primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackItemById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackDonationsReceivedById', () => {
      it('Should return tracked DonationsReceived primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackDonationsReceivedById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
