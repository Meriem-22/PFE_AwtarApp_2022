jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { EducationalInstitutionService } from '../service/educational-institution.service';

import { EducationalInstitutionDeleteDialogComponent } from './educational-institution-delete-dialog.component';

describe('EducationalInstitution Management Delete Component', () => {
  let comp: EducationalInstitutionDeleteDialogComponent;
  let fixture: ComponentFixture<EducationalInstitutionDeleteDialogComponent>;
  let service: EducationalInstitutionService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [EducationalInstitutionDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(EducationalInstitutionDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(EducationalInstitutionDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(EducationalInstitutionService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      })
    ));

    it('Should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
