import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IChildStatus, ChildStatus } from '../child-status.model';
import { ChildStatusService } from '../service/child-status.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-child-status-update',
  templateUrl: './child-status-update.component.html',
})
export class ChildStatusUpdateComponent implements OnInit {
  isSaving = false;
  statusValues = Object.keys(Status);

  editForm = this.fb.group({
    id: [],
    staus: [null, [Validators.required]],
    archivated: [],
  });

  constructor(protected childStatusService: ChildStatusService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ childStatus }) => {
      this.updateForm(childStatus);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const childStatus = this.createFromForm();
    if (childStatus.id !== undefined) {
      this.subscribeToSaveResponse(this.childStatusService.update(childStatus));
    } else {
      this.subscribeToSaveResponse(this.childStatusService.create(childStatus));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChildStatus>>): void {
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

  protected updateForm(childStatus: IChildStatus): void {
    this.editForm.patchValue({
      id: childStatus.id,
      staus: childStatus.staus,
      archivated: childStatus.archivated,
    });
  }

  protected createFromForm(): IChildStatus {
    return {
      ...new ChildStatus(),
      id: this.editForm.get(['id'])!.value,
      staus: this.editForm.get(['staus'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}
