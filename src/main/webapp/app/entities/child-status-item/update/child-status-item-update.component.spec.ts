import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChildStatusItemService } from '../service/child-status-item.service';
import { IChildStatusItem, ChildStatusItem } from '../child-status-item.model';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { IChildStatus } from 'app/entities/child-status/child-status.model';
import { ChildStatusService } from 'app/entities/child-status/service/child-status.service';

import { ChildStatusItemUpdateComponent } from './child-status-item-update.component';

describe('ChildStatusItem Management Update Component', () => {
  let comp: ChildStatusItemUpdateComponent;
  let fixture: ComponentFixture<ChildStatusItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let childStatusItemService: ChildStatusItemService;
  let itemService: ItemService;
  let childStatusService: ChildStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChildStatusItemUpdateComponent],
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
      .overrideTemplate(ChildStatusItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChildStatusItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    childStatusItemService = TestBed.inject(ChildStatusItemService);
    itemService = TestBed.inject(ItemService);
    childStatusService = TestBed.inject(ChildStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Item query and add missing value', () => {
      const childStatusItem: IChildStatusItem = { id: 456 };
      const item: IItem = { id: 65608 };
      childStatusItem.item = item;

      const itemCollection: IItem[] = [{ id: 94385 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [item];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ childStatusItem });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call ChildStatus query and add missing value', () => {
      const childStatusItem: IChildStatusItem = { id: 456 };
      const childStatus: IChildStatus = { id: 61173 };
      childStatusItem.childStatus = childStatus;

      const childStatusCollection: IChildStatus[] = [{ id: 38381 }];
      jest.spyOn(childStatusService, 'query').mockReturnValue(of(new HttpResponse({ body: childStatusCollection })));
      const additionalChildStatuses = [childStatus];
      const expectedCollection: IChildStatus[] = [...additionalChildStatuses, ...childStatusCollection];
      jest.spyOn(childStatusService, 'addChildStatusToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ childStatusItem });
      comp.ngOnInit();

      expect(childStatusService.query).toHaveBeenCalled();
      expect(childStatusService.addChildStatusToCollectionIfMissing).toHaveBeenCalledWith(
        childStatusCollection,
        ...additionalChildStatuses
      );
      expect(comp.childStatusesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const childStatusItem: IChildStatusItem = { id: 456 };
      const item: IItem = { id: 44008 };
      childStatusItem.item = item;
      const childStatus: IChildStatus = { id: 48043 };
      childStatusItem.childStatus = childStatus;

      activatedRoute.data = of({ childStatusItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(childStatusItem));
      expect(comp.itemsSharedCollection).toContain(item);
      expect(comp.childStatusesSharedCollection).toContain(childStatus);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChildStatusItem>>();
      const childStatusItem = { id: 123 };
      jest.spyOn(childStatusItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ childStatusItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: childStatusItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(childStatusItemService.update).toHaveBeenCalledWith(childStatusItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChildStatusItem>>();
      const childStatusItem = new ChildStatusItem();
      jest.spyOn(childStatusItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ childStatusItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: childStatusItem }));
      saveSubject.complete();

      // THEN
      expect(childStatusItemService.create).toHaveBeenCalledWith(childStatusItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChildStatusItem>>();
      const childStatusItem = { id: 123 };
      jest.spyOn(childStatusItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ childStatusItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(childStatusItemService.update).toHaveBeenCalledWith(childStatusItem);
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

    describe('trackChildStatusById', () => {
      it('Should return tracked ChildStatus primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackChildStatusById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
