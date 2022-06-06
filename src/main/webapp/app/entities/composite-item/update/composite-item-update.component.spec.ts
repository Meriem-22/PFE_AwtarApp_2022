import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { CompositeItemService } from '../service/composite-item.service';
import { ICompositeItem, CompositeItem } from '../composite-item.model';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

import { CompositeItemUpdateComponent } from './composite-item-update.component';

describe('CompositeItem Management Update Component', () => {
  let comp: CompositeItemUpdateComponent;
  let fixture: ComponentFixture<CompositeItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let compositeItemService: CompositeItemService;
  let itemService: ItemService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [CompositeItemUpdateComponent],
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
      .overrideTemplate(CompositeItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(CompositeItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    compositeItemService = TestBed.inject(CompositeItemService);
    itemService = TestBed.inject(ItemService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Item query and add missing value', () => {
      const compositeItem: ICompositeItem = { id: 456 };
      const composant: IItem = { id: 4349 };
      compositeItem.composant = composant;
      const composer: IItem = { id: 99860 };
      compositeItem.composer = composer;

      const itemCollection: IItem[] = [{ id: 7587 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [composant, composer];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ compositeItem });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const compositeItem: ICompositeItem = { id: 456 };
      const composant: IItem = { id: 79323 };
      compositeItem.composant = composant;
      const composer: IItem = { id: 59262 };
      compositeItem.composer = composer;

      activatedRoute.data = of({ compositeItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(compositeItem));
      expect(comp.itemsSharedCollection).toContain(composant);
      expect(comp.itemsSharedCollection).toContain(composer);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CompositeItem>>();
      const compositeItem = { id: 123 };
      jest.spyOn(compositeItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ compositeItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: compositeItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(compositeItemService.update).toHaveBeenCalledWith(compositeItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CompositeItem>>();
      const compositeItem = new CompositeItem();
      jest.spyOn(compositeItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ compositeItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: compositeItem }));
      saveSubject.complete();

      // THEN
      expect(compositeItemService.create).toHaveBeenCalledWith(compositeItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<CompositeItem>>();
      const compositeItem = { id: 123 };
      jest.spyOn(compositeItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ compositeItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(compositeItemService.update).toHaveBeenCalledWith(compositeItem);
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
