import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { TeachingCurriculumService } from '../service/teaching-curriculum.service';
import { ITeachingCurriculum, TeachingCurriculum } from '../teaching-curriculum.model';
import { ISchoolLevel } from 'app/entities/school-level/school-level.model';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';
import { IChild } from 'app/entities/child/child.model';
import { ChildService } from 'app/entities/child/service/child.service';
import { IEducationalInstitution } from 'app/entities/educational-institution/educational-institution.model';
import { EducationalInstitutionService } from 'app/entities/educational-institution/service/educational-institution.service';

import { TeachingCurriculumUpdateComponent } from './teaching-curriculum-update.component';

describe('TeachingCurriculum Management Update Component', () => {
  let comp: TeachingCurriculumUpdateComponent;
  let fixture: ComponentFixture<TeachingCurriculumUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let teachingCurriculumService: TeachingCurriculumService;
  let schoolLevelService: SchoolLevelService;
  let childService: ChildService;
  let educationalInstitutionService: EducationalInstitutionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [TeachingCurriculumUpdateComponent],
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
      .overrideTemplate(TeachingCurriculumUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TeachingCurriculumUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    teachingCurriculumService = TestBed.inject(TeachingCurriculumService);
    schoolLevelService = TestBed.inject(SchoolLevelService);
    childService = TestBed.inject(ChildService);
    educationalInstitutionService = TestBed.inject(EducationalInstitutionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call SchoolLevel query and add missing value', () => {
      const teachingCurriculum: ITeachingCurriculum = { id: 456 };
      const schoolLevel: ISchoolLevel = { id: 59959 };
      teachingCurriculum.schoolLevel = schoolLevel;

      const schoolLevelCollection: ISchoolLevel[] = [{ id: 89316 }];
      jest.spyOn(schoolLevelService, 'query').mockReturnValue(of(new HttpResponse({ body: schoolLevelCollection })));
      const additionalSchoolLevels = [schoolLevel];
      const expectedCollection: ISchoolLevel[] = [...additionalSchoolLevels, ...schoolLevelCollection];
      jest.spyOn(schoolLevelService, 'addSchoolLevelToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      expect(schoolLevelService.query).toHaveBeenCalled();
      expect(schoolLevelService.addSchoolLevelToCollectionIfMissing).toHaveBeenCalledWith(schoolLevelCollection, ...additionalSchoolLevels);
      expect(comp.schoolLevelsSharedCollection).toEqual(expectedCollection);
    });

    it('Should call Child query and add missing value', () => {
      const teachingCurriculum: ITeachingCurriculum = { id: 456 };
      const child: IChild = { id: 21037 };
      teachingCurriculum.child = child;

      const childCollection: IChild[] = [{ id: 42893 }];
      jest.spyOn(childService, 'query').mockReturnValue(of(new HttpResponse({ body: childCollection })));
      const additionalChildren = [child];
      const expectedCollection: IChild[] = [...additionalChildren, ...childCollection];
      jest.spyOn(childService, 'addChildToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      expect(childService.query).toHaveBeenCalled();
      expect(childService.addChildToCollectionIfMissing).toHaveBeenCalledWith(childCollection, ...additionalChildren);
      expect(comp.childrenSharedCollection).toEqual(expectedCollection);
    });

    it('Should call EducationalInstitution query and add missing value', () => {
      const teachingCurriculum: ITeachingCurriculum = { id: 456 };
      const educationalInstitution: IEducationalInstitution = { id: 85201 };
      teachingCurriculum.educationalInstitution = educationalInstitution;

      const educationalInstitutionCollection: IEducationalInstitution[] = [{ id: 39181 }];
      jest.spyOn(educationalInstitutionService, 'query').mockReturnValue(of(new HttpResponse({ body: educationalInstitutionCollection })));
      const additionalEducationalInstitutions = [educationalInstitution];
      const expectedCollection: IEducationalInstitution[] = [...additionalEducationalInstitutions, ...educationalInstitutionCollection];
      jest.spyOn(educationalInstitutionService, 'addEducationalInstitutionToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      expect(educationalInstitutionService.query).toHaveBeenCalled();
      expect(educationalInstitutionService.addEducationalInstitutionToCollectionIfMissing).toHaveBeenCalledWith(
        educationalInstitutionCollection,
        ...additionalEducationalInstitutions
      );
      expect(comp.educationalInstitutionsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const teachingCurriculum: ITeachingCurriculum = { id: 456 };
      const schoolLevel: ISchoolLevel = { id: 11415 };
      teachingCurriculum.schoolLevel = schoolLevel;
      const child: IChild = { id: 41994 };
      teachingCurriculum.child = child;
      const educationalInstitution: IEducationalInstitution = { id: 52800 };
      teachingCurriculum.educationalInstitution = educationalInstitution;

      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(teachingCurriculum));
      expect(comp.schoolLevelsSharedCollection).toContain(schoolLevel);
      expect(comp.childrenSharedCollection).toContain(child);
      expect(comp.educationalInstitutionsSharedCollection).toContain(educationalInstitution);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeachingCurriculum>>();
      const teachingCurriculum = { id: 123 };
      jest.spyOn(teachingCurriculumService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teachingCurriculum }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(teachingCurriculumService.update).toHaveBeenCalledWith(teachingCurriculum);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeachingCurriculum>>();
      const teachingCurriculum = new TeachingCurriculum();
      jest.spyOn(teachingCurriculumService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: teachingCurriculum }));
      saveSubject.complete();

      // THEN
      expect(teachingCurriculumService.create).toHaveBeenCalledWith(teachingCurriculum);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<TeachingCurriculum>>();
      const teachingCurriculum = { id: 123 };
      jest.spyOn(teachingCurriculumService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ teachingCurriculum });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(teachingCurriculumService.update).toHaveBeenCalledWith(teachingCurriculum);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackSchoolLevelById', () => {
      it('Should return tracked SchoolLevel primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackSchoolLevelById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackChildById', () => {
      it('Should return tracked Child primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackChildById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });

    describe('trackEducationalInstitutionById', () => {
      it('Should return tracked EducationalInstitution primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEducationalInstitutionById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
