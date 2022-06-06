import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ParentService } from '../service/parent.service';
import { IParent, Parent } from '../parent.model';
import { IFamily } from 'app/entities/family/family.model';
import { FamilyService } from 'app/entities/family/service/family.service';

import { ParentUpdateComponent } from './parent-update.component';

describe('Parent Management Update Component', () => {
  let comp: ParentUpdateComponent;
  let fixture: ComponentFixture<ParentUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let parentService: ParentService;
  let familyService: FamilyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ParentUpdateComponent],
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
      .overrideTemplate(ParentUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ParentUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    parentService = TestBed.inject(ParentService);
    familyService = TestBed.inject(FamilyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Family query and add missing value', () => {
      const parent: IParent = { id: 456 };
      const family: IFamily = { id: 391 };
      parent.family = family;

      const familyCollection: IFamily[] = [{ id: 12804 }];
      jest.spyOn(familyService, 'query').mockReturnValue(of(new HttpResponse({ body: familyCollection })));
      const additionalFamilies = [family];
      const expectedCollection: IFamily[] = [...additionalFamilies, ...familyCollection];
      jest.spyOn(familyService, 'addFamilyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parent });
      comp.ngOnInit();

      expect(familyService.query).toHaveBeenCalled();
      expect(familyService.addFamilyToCollectionIfMissing).toHaveBeenCalledWith(familyCollection, ...additionalFamilies);
      expect(comp.familiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should call familyHead query and add missing value', () => {
      const parent: IParent = { id: 456 };
      const familyHead: IFamily = { id: 78023 };
      parent.familyHead = familyHead;

      const familyHeadCollection: IFamily[] = [{ id: 86440 }];
      jest.spyOn(familyService, 'query').mockReturnValue(of(new HttpResponse({ body: familyHeadCollection })));
      const expectedCollection: IFamily[] = [familyHead, ...familyHeadCollection];
      jest.spyOn(familyService, 'addFamilyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ parent });
      comp.ngOnInit();

      expect(familyService.query).toHaveBeenCalled();
      expect(familyService.addFamilyToCollectionIfMissing).toHaveBeenCalledWith(familyHeadCollection, familyHead);
      expect(comp.familyHeadsCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const parent: IParent = { id: 456 };
      const familyHead: IFamily = { id: 48366 };
      parent.familyHead = familyHead;
      const family: IFamily = { id: 12742 };
      parent.family = family;

      activatedRoute.data = of({ parent });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(parent));
      expect(comp.familyHeadsCollection).toContain(familyHead);
      expect(comp.familiesSharedCollection).toContain(family);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Parent>>();
      const parent = { id: 123 };
      jest.spyOn(parentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parent }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(parentService.update).toHaveBeenCalledWith(parent);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Parent>>();
      const parent = new Parent();
      jest.spyOn(parentService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: parent }));
      saveSubject.complete();

      // THEN
      expect(parentService.create).toHaveBeenCalledWith(parent);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Parent>>();
      const parent = { id: 123 };
      jest.spyOn(parentService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ parent });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(parentService.update).toHaveBeenCalledWith(parent);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackFamilyById', () => {
      it('Should return tracked Family primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackFamilyById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
