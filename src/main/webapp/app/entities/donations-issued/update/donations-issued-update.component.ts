import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDonationsIssued, DonationsIssued } from '../donations-issued.model';
import { DonationsIssuedService } from '../service/donations-issued.service';
import { Period } from 'app/entities/enumerations/period.model';

@Component({
  selector: 'jhi-donations-issued-update',
  templateUrl: './donations-issued-update.component.html',
})
export class DonationsIssuedUpdateComponent implements OnInit {
  isSaving = false;
  periodValues = Object.keys(Period);

  editForm = this.fb.group({
    id: [],
    model: [null, [Validators.required]],
    isValidated: [],
    validationDate: [],
    validationUser: [],
    donationsDate: [],
    canceledDonations: [],
    canceledOn: [],
    canceledBy: [],
    cancellationReason: [],
    recurringDonations: [],
    periodicity: [],
    recurrence: [],
    archivated: [],
  });

  constructor(
    protected donationsIssuedService: DonationsIssuedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsIssued }) => {
      this.updateForm(donationsIssued);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donationsIssued = this.createFromForm();
    if (donationsIssued.id !== undefined) {
      this.subscribeToSaveResponse(this.donationsIssuedService.update(donationsIssued));
    } else {
      this.subscribeToSaveResponse(this.donationsIssuedService.create(donationsIssued));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationsIssued>>): void {
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

  protected updateForm(donationsIssued: IDonationsIssued): void {
    this.editForm.patchValue({
      id: donationsIssued.id,
      model: donationsIssued.model,
      isValidated: donationsIssued.isValidated,
      validationDate: donationsIssued.validationDate,
      validationUser: donationsIssued.validationUser,
      donationsDate: donationsIssued.donationsDate,
      canceledDonations: donationsIssued.canceledDonations,
      canceledOn: donationsIssued.canceledOn,
      canceledBy: donationsIssued.canceledBy,
      cancellationReason: donationsIssued.cancellationReason,
      recurringDonations: donationsIssued.recurringDonations,
      periodicity: donationsIssued.periodicity,
      recurrence: donationsIssued.recurrence,
      archivated: donationsIssued.archivated,
    });
  }

  protected createFromForm(): IDonationsIssued {
    return {
      ...new DonationsIssued(),
      id: this.editForm.get(['id'])!.value,
      model: this.editForm.get(['model'])!.value,
      isValidated: this.editForm.get(['isValidated'])!.value,
      validationDate: this.editForm.get(['validationDate'])!.value,
      validationUser: this.editForm.get(['validationUser'])!.value,
      donationsDate: this.editForm.get(['donationsDate'])!.value,
      canceledDonations: this.editForm.get(['canceledDonations'])!.value,
      canceledOn: this.editForm.get(['canceledOn'])!.value,
      canceledBy: this.editForm.get(['canceledBy'])!.value,
      cancellationReason: this.editForm.get(['cancellationReason'])!.value,
      recurringDonations: this.editForm.get(['recurringDonations'])!.value,
      periodicity: this.editForm.get(['periodicity'])!.value,
      recurrence: this.editForm.get(['recurrence'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}
