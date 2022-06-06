import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AuthorizingOfficerService } from '../service/authorizing-officer.service';
import { IAuthorizingOfficer, AuthorizingOfficer } from '../authorizing-officer.model';

import { AuthorizingOfficerUpdateComponent } from './authorizing-officer-update.component';

describe('AuthorizingOfficer Management Update Component', () => {
  let comp: AuthorizingOfficerUpdateComponent;
  let fixture: ComponentFixture<AuthorizingOfficerUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let authorizingOfficerService: AuthorizingOfficerService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AuthorizingOfficerUpdateComponent],
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
      .overrideTemplate(AuthorizingOfficerUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AuthorizingOfficerUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    authorizingOfficerService = TestBed.inject(AuthorizingOfficerService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const authorizingOfficer: IAuthorizingOfficer = { id: 456 };

      activatedRoute.data = of({ authorizingOfficer });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(authorizingOfficer));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuthorizingOfficer>>();
      const authorizingOfficer = { id: 123 };
      jest.spyOn(authorizingOfficerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authorizingOfficer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authorizingOfficer }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(authorizingOfficerService.update).toHaveBeenCalledWith(authorizingOfficer);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuthorizingOfficer>>();
      const authorizingOfficer = new AuthorizingOfficer();
      jest.spyOn(authorizingOfficerService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authorizingOfficer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: authorizingOfficer }));
      saveSubject.complete();

      // THEN
      expect(authorizingOfficerService.create).toHaveBeenCalledWith(authorizingOfficer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<AuthorizingOfficer>>();
      const authorizingOfficer = { id: 123 };
      jest.spyOn(authorizingOfficerService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ authorizingOfficer });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(authorizingOfficerService.update).toHaveBeenCalledWith(authorizingOfficer);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
