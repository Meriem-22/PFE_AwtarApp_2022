import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { NatureService } from '../service/nature.service';
import { INature, Nature } from '../nature.model';

import { NatureUpdateComponent } from './nature-update.component';

describe('Nature Management Update Component', () => {
  let comp: NatureUpdateComponent;
  let fixture: ComponentFixture<NatureUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let natureService: NatureService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [NatureUpdateComponent],
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
      .overrideTemplate(NatureUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(NatureUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    natureService = TestBed.inject(NatureService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const nature: INature = { id: 456 };

      activatedRoute.data = of({ nature });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(nature));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nature>>();
      const nature = { id: 123 };
      jest.spyOn(natureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nature }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(natureService.update).toHaveBeenCalledWith(nature);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nature>>();
      const nature = new Nature();
      jest.spyOn(natureService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: nature }));
      saveSubject.complete();

      // THEN
      expect(natureService.create).toHaveBeenCalledWith(nature);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Nature>>();
      const nature = { id: 123 };
      jest.spyOn(natureService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ nature });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(natureService.update).toHaveBeenCalledWith(nature);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
