import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { ReportService } from '../service/report.service';
import { IReport, Report } from '../report.model';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';

import { ReportUpdateComponent } from './report-update.component';

describe('Report Management Update Component', () => {
  let comp: ReportUpdateComponent;
  let fixture: ComponentFixture<ReportUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let reportService: ReportService;
  let establishmentService: EstablishmentService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [ReportUpdateComponent],
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
      .overrideTemplate(ReportUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ReportUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    reportService = TestBed.inject(ReportService);
    establishmentService = TestBed.inject(EstablishmentService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Establishment query and add missing value', () => {
      const report: IReport = { id: 456 };
      const establishment: IEstablishment = { id: 42828 };
      report.establishment = establishment;

      const establishmentCollection: IEstablishment[] = [{ id: 30151 }];
      jest.spyOn(establishmentService, 'query').mockReturnValue(of(new HttpResponse({ body: establishmentCollection })));
      const additionalEstablishments = [establishment];
      const expectedCollection: IEstablishment[] = [...additionalEstablishments, ...establishmentCollection];
      jest.spyOn(establishmentService, 'addEstablishmentToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ report });
      comp.ngOnInit();

      expect(establishmentService.query).toHaveBeenCalled();
      expect(establishmentService.addEstablishmentToCollectionIfMissing).toHaveBeenCalledWith(
        establishmentCollection,
        ...additionalEstablishments
      );
      expect(comp.establishmentsSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const report: IReport = { id: 456 };
      const establishment: IEstablishment = { id: 39205 };
      report.establishment = establishment;

      activatedRoute.data = of({ report });
      comp.ngOnInit();

      expect(comp.editForm.value).toEqual(expect.objectContaining(report));
      expect(comp.establishmentsSharedCollection).toContain(establishment);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Report>>();
      const report = { id: 123 };
      jest.spyOn(reportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ report });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: report }));
      saveSubject.complete();

      // THEN
      expect(comp.previousState).toHaveBeenCalled();
      expect(reportService.update).toHaveBeenCalledWith(report);
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Report>>();
      const report = new Report();
      jest.spyOn(reportService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ report });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: report }));
      saveSubject.complete();

      // THEN
      expect(reportService.create).toHaveBeenCalledWith(report);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<Report>>();
      const report = { id: 123 };
      jest.spyOn(reportService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ report });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(reportService.update).toHaveBeenCalledWith(report);
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Tracking relationships identifiers', () => {
    describe('trackEstablishmentById', () => {
      it('Should return tracked Establishment primary key', () => {
        const entity = { id: 123 };
        const trackResult = comp.trackEstablishmentById(0, entity);
        expect(trackResult).toEqual(entity.id);
      });
    });
  });
});
