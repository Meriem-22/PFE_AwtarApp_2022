import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ItemValueService } from '../service/item-value.service';
import { IItemValue, ItemValue } from '../item-value.model';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

import { ItemValueUpdateComponent } from './item-value-update.component';

describe('ItemValue Management Update Component', () => {
  let comp: ItemValueUpdateComponent;
  let fixture: ComponentFixture<ItemValueUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let itemValueService: ItemValueService;
  let itemService: ItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ItemValueUpdateComponent],
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
      .overrideTemplate(ItemValueUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ItemValueUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    itemValueService = TestBed.inject(ItemValueService);
    itemService = TestBed.inject(ItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Item query and add missing value', () => {
      const itemValue: IItemValue = { id: 456 };
      const item: IItem = { id: 34363 };
      itemValue.item = item;

      const itemCollection: IItem[] = [{ id: 24884 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [item];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ itemValue });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const itemValue: IItemValue = { id: 456 };
      const item: IItem = { id: 28505 };
      itemValue.item = item;

      activatedRoute.data = of({ itemValue });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(itemValue));
      expect(comp.itemsSharedCollection).toContain(item);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ItemValue>>();
      const itemValue = { id: 123 };
      jest.spyOn(itemValueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemValue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemValue }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(itemValueService.update).toHaveBeenCalledWith(itemValue);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ItemValue>>();
      const itemValue = new ItemValue();
      jest.spyOn(itemValueService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemValue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: itemValue }));
      saveSubject.complete();

      // THEN
      expect(itemValueService.create).toHaveBeenCalledWith(itemValue);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ItemValue>>();
      const itemValue = { id: 123 };
      jest.spyOn(itemValueService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ itemValue });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(itemValueService.update).toHaveBeenCalledWith(itemValue);
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
  });
});
