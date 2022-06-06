import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChildService } from '../service/child.service';
import { IChild, Child } from '../child.model';
import { IFamily } from 'app/entities/family/family.model';
import { FamilyService } from 'app/entities/family/service/family.service';

import { ChildUpdateComponent } from './child-update.component';

describe('Child Management Update Component', () => {
  let comp: ChildUpdateComponent;
  let fixture: ComponentFixture<ChildUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let childService: ChildService;
  let familyService: FamilyService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChildUpdateComponent],
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
      .overrideTemplate(ChildUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChildUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    childService = TestBed.inject(ChildService);
    familyService = TestBed.inject(FamilyService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Family query and add missing value', () => {
      const child: IChild = { id: 456 };
      const family: IFamily = { id: 38838 };
      child.family = family;

      const familyCollection: IFamily[] = [{ id: 43836 }];
      jest.spyOn(familyService, 'query').mockReturnValue(of(new HttpResponse({ body: familyCollection })));
      const additionalFamilies = [family];
      const expectedCollection: IFamily[] = [...additionalFamilies, ...familyCollection];
      jest.spyOn(familyService, 'addFamilyToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ child });
      comp.ngOnInit();

      expect(familyService.query).toHaveBeenCalled();
      expect(familyService.addFamilyToCollectionIfMissing).toHaveBeenCalledWith(familyCollection, ...additionalFamilies);
      expect(comp.familiesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const child: IChild = { id: 456 };
      const family: IFamily = { id: 77198 };
      child.family = family;

      activatedRoute.data = of({ child });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(child));
      expect(comp.familiesSharedCollection).toContain(family);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Child>>();
      const child = { id: 123 };
      jest.spyOn(childService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ child });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: child }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(childService.update).toHaveBeenCalledWith(child);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Child>>();
      const child = new Child();
      jest.spyOn(childService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ child });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: child }));
      saveSubject.complete();

      // THEN
      expect(childService.create).toHaveBeenCalledWith(child);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Child>>();
      const child = { id: 123 };
      jest.spyOn(childService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ child });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(childService.update).toHaveBeenCalledWith(child);
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
