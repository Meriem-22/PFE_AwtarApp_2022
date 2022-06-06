jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { TeachingCurriculumService } from '../service/teaching-curriculum.service';

import { TeachingCurriculumDeleteDialogComponent } from './teaching-curriculum-delete-dialog.component';

describe('TeachingCurriculum Management Delete Component', () => {
  let comp: TeachingCurriculumDeleteDialogComponent;
  let fixture: ComponentFixture<TeachingCurriculumDeleteDialogComponent>;
  let service: TeachingCurriculumService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TeachingCurriculumDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(TeachingCurriculumDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TeachingCurriculumDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TeachingCurriculumService);
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
