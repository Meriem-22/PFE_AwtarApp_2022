import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITutor, Tutor } from '../tutor.model';
import { TutorService } from '../service/tutor.service';

@Component({
  selector: 'jhi-tutor-update',
  templateUrl: './tutor-update.component.html',
})
export class TutorUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    activity: [],
    manager: [],
    managerCin: [],
  });

  constructor(protected tutorService: TutorService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tutor }) => {
      this.updateForm(tutor);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tutor = this.createFromForm();
    if (tutor.id !== undefined) {
      this.subscribeToSaveResponse(this.tutorService.update(tutor));
    } else {
      this.subscribeToSaveResponse(this.tutorService.create(tutor));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITutor>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(tutor: ITutor): void {
    this.editForm.patchValue({
      id: tutor.id,
      activity: tutor.activity,
      manager: tutor.manager,
      managerCin: tutor.managerCin,
    });
  }

  protected createFromForm(): ITutor {
    return {
      ...new Tutor(),
      id: this.editForm.get(['id'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      manager: this.editForm.get(['manager'])!.value,
      managerCin: this.editForm.get(['managerCin'])!.value,
    };
  }
}
