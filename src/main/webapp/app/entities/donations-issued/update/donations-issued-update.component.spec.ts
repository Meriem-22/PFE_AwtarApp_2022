import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { DonationsIssuedService } from '../service/donations-issued.service';
import { IDonationsIssued, DonationsIssued } from '../donations-issued.model';

import { DonationsIssuedUpdateComponent } from './donations-issued-update.component';

describe('DonationsIssued Management Update Component', () => {
  let comp: DonationsIssuedUpdateComponent;
  let fixture: ComponentFixture<DonationsIssuedUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let donationsIssuedService: DonationsIssuedService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [DonationsIssuedUpdateComponent],
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
      .overrideTemplate(DonationsIssuedUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(DonationsIssuedUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    donationsIssuedService = TestBed.inject(DonationsIssuedService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const donationsIssued: IDonationsIssued = { id: 456 };

      activatedRoute.data = of({ donationsIssued });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(donationsIssued));
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsIssued>>();
      const donationsIssued = { id: 123 };
      jest.spyOn(donationsIssuedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsIssued });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationsIssued }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(donationsIssuedService.update).toHaveBeenCalledWith(donationsIssued);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsIssued>>();
      const donationsIssued = new DonationsIssued();
      jest.spyOn(donationsIssuedService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsIssued });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: donationsIssued }));
      saveSubject.complete();

      // THEN
      expect(donationsIssuedService.create).toHaveBeenCalledWith(donationsIssued);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<DonationsIssued>>();
      const donationsIssued = { id: 123 };
      jest.spyOn(donationsIssuedService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ donationsIssued });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(donationsIssuedService.update).toHaveBeenCalledWith(donationsIssued);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
