import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { SchoolLevelService } from '../service/school-level.service';
import { ISchoolLevel, SchoolLevel } from '../school-level.model';

import { SchoolLevelUpdateComponent } from './school-level-update.component';

describe('SchoolLevel Management Update Component', () => {
  let comp: SchoolLevelUpdateComponent;
  let fixture: ComponentFixture<SchoolLevelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let schoolLevelService: SchoolLevelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [SchoolLevelUpdateComponent],
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
      .overrideTemplate(SchoolLevelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SchoolLevelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    schoolLevelService = TestBed.inject(SchoolLevelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const schoolLevel: ISchoolLevel = { id: 456 };

      activatedRoute.data = of({ schoolLevel });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(schoolLevel));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SchoolLevel>>();
      const schoolLevel = { id: 123 };
      jest.spyOn(schoolLevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schoolLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schoolLevel }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(schoolLevelService.update).toHaveBeenCalledWith(schoolLevel);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SchoolLevel>>();
      const schoolLevel = new SchoolLevel();
      jest.spyOn(schoolLevelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schoolLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: schoolLevel }));
      saveSubject.complete();

      // THEN
      expect(schoolLevelService.create).toHaveBeenCalledWith(schoolLevel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<SchoolLevel>>();
      const schoolLevel = { id: 123 };
      jest.spyOn(schoolLevelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ schoolLevel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(schoolLevelService.update).toHaveBeenCalledWith(schoolLevel);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
