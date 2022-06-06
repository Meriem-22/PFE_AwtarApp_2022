import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ChildStatusService } from '../service/child-status.service';
import { IChildStatus, ChildStatus } from '../child-status.model';

import { ChildStatusUpdateComponent } from './child-status-update.component';

describe('ChildStatus Management Update Component', () => {
  let comp: ChildStatusUpdateComponent;
  let fixture: ComponentFixture<ChildStatusUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let childStatusService: ChildStatusService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ChildStatusUpdateComponent],
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
      .overrideTemplate(ChildStatusUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ChildStatusUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    childStatusService = TestBed.inject(ChildStatusService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const childStatus: IChildStatus = { id: 456 };

      activatedRoute.data = of({ childStatus });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(childStatus));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChildStatus>>();
      const childStatus = { id: 123 };
      jest.spyOn(childStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ childStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: childStatus }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(childStatusService.update).toHaveBeenCalledWith(childStatus);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChildStatus>>();
      const childStatus = new ChildStatus();
      jest.spyOn(childStatusService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ childStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: childStatus }));
      saveSubject.complete();

      // THEN
      expect(childStatusService.create).toHaveBeenCalledWith(childStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ChildStatus>>();
      const childStatus = { id: 123 };
      jest.spyOn(childStatusService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ childStatus });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(childStatusService.update).toHaveBeenCalledWith(childStatus);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
