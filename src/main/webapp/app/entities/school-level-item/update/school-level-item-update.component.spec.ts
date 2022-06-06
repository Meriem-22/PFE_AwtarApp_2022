import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SchoolLevelItemService } from '../service/school-level-item.service';
import { ISchoolLevelItem, SchoolLevelItem } from '../school-level-item.model';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { ISchoolLevel } from 'app/entities/school-level/school-level.model';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';

import { SchoolLevelItemUpdateComponent } from './school-level-item-update.component';

describe('SchoolLevelItem Management Update Component', () => {
  let comp: SchoolLevelItemUpdateComponent;
  let fixture: ComponentFixture<SchoolLevelItemUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let schoolLevelItemService: SchoolLevelItemService;
  let itemService: ItemService;
  let schoolLevelService: SchoolLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SchoolLevelItemUpdateComponent],
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
      .overrideTemplate(SchoolLevelItemUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SchoolLevelItemUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    schoolLevelItemService = TestBed.inject(SchoolLevelItemService);
    itemService = TestBed.inject(ItemService);
    schoolLevelService = TestBed.inject(SchoolLevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Item query and add missing value', () => {
      const schoolLevelItem: ISchoolLevelItem = { id: 456 };
      const item: IItem = { id: 69081 };
      schoolLevelItem.item = item;

      const itemCollection: IItem[] = [{ id: 92658 }];
      jest.spyOn(itemService, 'query').mockReturnValue(of(new HttpResponse({ body: itemCollection })));
      const additionalItems = [item];
      const expectedCollection: IItem[] = [...additionalItems, ...itemCollection];
      jest.spyOn(itemService, 'addItemToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schoolLevelItem });
      comp.ngOnInit();

      expect(itemService.query).toHaveBeenCalled();
      expect(itemService.addItemToCollectionIfMissing).toHaveBeenCalledWith(itemCollection, ...additionalItems);
      expect(comp.itemsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call SchoolLevel query and add missing value', () => {
      const schoolLevelItem: ISchoolLevelItem = { id: 456 };
      const schoolLevel: ISchoolLevel = { id: 8320 };
      schoolLevelItem.schoolLevel = schoolLevel;

      const schoolLevelCollection: ISchoolLevel[] = [{ id: 27206 }];
      jest.spyOn(schoolLevelService, 'query').mockReturnValue(of(new HttpResponse({ body: schoolLevelCollection })));
      const additionalSchoolLevels = [schoolLevel];
      const expectedCollection: ISchoolLevel[] = [...additionalSchoolLevels, ...schoolLevelCollection];
      jest.spyOn(schoolLevelService, 'addSchoolLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ schoolLevelItem });
      comp.ngOnInit();

      expect(schoolLevelService.query).toHaveBeenCalled();
      expect(schoolLevelService.addSchoolLevelToCollectionIfMissing).toHaveBeenCalledWith(schoolLevelCollection, ...additionalSchoolLevels);
      expect(comp.schoolLevelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const schoolLevelItem: ISchoolLevelItem = { id: 456 };
      const item: IItem = { id: 18223 };
      schoolLevelItem.item = item;
      const schoolLevel: ISchoolLevel = { id: 2223 };
      schoolLevelItem.schoolLevel = schoolLevel;

      activatedRoute.data = of({ schoolLevelItem });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(schoolLevelItem));
      expect(comp.itemsSharedCollection).toContain(item);
      expect(comp.schoolLevelsSharedCollection).toContain(schoolLevel);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SchoolLevelItem>>();
      const schoolLevelItem = { id: 123 };
      jest.spyOn(schoolLevelItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schoolLevelItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schoolLevelItem }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(schoolLevelItemService.update).toHaveBeenCalledWith(schoolLevelItem);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SchoolLevelItem>>();
      const schoolLevelItem = new SchoolLevelItem();
      jest.spyOn(schoolLevelItemService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schoolLevelItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schoolLevelItem }));
      saveSubject.complete();

      // THEN
      expect(schoolLevelItemService.create).toHaveBeenCalledWith(schoolLevelItem);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SchoolLevelItem>>();
      const schoolLevelItem = { id: 123 };
      jest.spyOn(schoolLevelItemService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schoolLevelItem });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(schoolLevelItemService.update).toHaveBeenCalledWith(schoolLevelItem);
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

    describe('trackSchoolLevelById', () => {
      it('Should return tracked SchoolLevel primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSchoolLevelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
