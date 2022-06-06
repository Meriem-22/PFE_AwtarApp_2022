jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { SchoolLevelItemService } from '../service/school-level-item.service';

import { SchoolLevelItemDeleteDialogComponent } from './school-level-item-delete-dialog.component';

describe('SchoolLevelItem Management Delete Component', () => {
  let comp: SchoolLevelItemDeleteDialogComponent;
  let fixture: ComponentFixture<SchoolLevelItemDeleteDialogComponent>;
  let service: SchoolLevelItemService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [SchoolLevelItemDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(SchoolLevelItemDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(SchoolLevelItemDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(SchoolLevelItemService);
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
