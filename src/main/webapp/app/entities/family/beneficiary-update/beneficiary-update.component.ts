import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { Beneficiary, IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { Beneficiaries } from 'app/entities/enumerations/beneficiaries.model';
import { TutorService } from 'app/entities/tutor/service/tutor.service';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { map, Observable, finalize } from 'rxjs';

@Component({
  selector: 'jhi-beneficiary-update',
  templateUrl: './beneficiary-update.component.html',
  styleUrls: ['./beneficiary-update.component.scss'],
})
export class BeneficiaryUpdateComponent implements OnInit {
  isSaving = false;
  beneficiariesValues = Object.keys(Beneficiaries);

  authorizingOfficersSharedCollection: IAuthorizingOfficer[] = [];
  tutorsSharedCollection: ITutor[] = [];

  editForm = this.fb.group({
    id: [],
    beneficiaryReference: [null, []],
    beneficiaryType: [null, [Validators.required]],
    archivated: [],
    authorizingOfficer: [],
    tutor: [],
  });

  constructor(
    protected beneficiaryService: BeneficiaryService,
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected tutorService: TutorService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      console.log(beneficiary);

      this.updateForm(beneficiary);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const beneficiary = this.createFromForm();
    if (beneficiary.id !== undefined) {
      this.subscribeToSaveResponse(this.beneficiaryService.update(beneficiary));
    } else {
      this.subscribeToSaveResponse(this.beneficiaryService.create(beneficiary));
    }
  }

  trackAuthorizingOfficerById(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }

  trackTutorById(_index: number, item: ITutor): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiary>>): void {
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

  protected updateForm(beneficiary: IBeneficiary): void {
    this.editForm.patchValue({
      id: beneficiary.id,
      beneficiaryReference: beneficiary.beneficiaryReference,
      beneficiaryType: beneficiary.beneficiaryType,
      archivated: beneficiary.archivated,
      authorizingOfficer: beneficiary.authorizingOfficer,
      tutor: beneficiary.tutor,
    });

    this.authorizingOfficersSharedCollection = this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
      this.authorizingOfficersSharedCollection,
      beneficiary.authorizingOfficer
    );
    this.tutorsSharedCollection = this.tutorService.addTutorToCollectionIfMissing(this.tutorsSharedCollection, beneficiary.tutor);
  }

  protected loadRelationshipsOptions(): void {
    this.authorizingOfficerService
      .query()
      .pipe(map((res: HttpResponse<IAuthorizingOfficer[]>) => res.body ?? []))
      .pipe(
        map((authorizingOfficers: IAuthorizingOfficer[]) =>
          this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
            authorizingOfficers,
            this.editForm.get('authorizingOfficer')!.value
          )
        )
      )
      .subscribe((authorizingOfficers: IAuthorizingOfficer[]) => (this.authorizingOfficersSharedCollection = authorizingOfficers));

    this.tutorService
      .query()
      .pipe(map((res: HttpResponse<ITutor[]>) => res.body ?? []))
      .pipe(map((tutors: ITutor[]) => this.tutorService.addTutorToCollectionIfMissing(tutors, this.editForm.get('tutor')!.value)))
      .subscribe((tutors: ITutor[]) => (this.tutorsSharedCollection = tutors));
  }

  protected createFromForm(): IBeneficiary {
    return {
      ...new Beneficiary(),
      id: this.editForm.get(['id'])!.value,
      beneficiaryReference: this.editForm.get(['beneficiaryReference'])!.value,
      beneficiaryType: this.editForm.get(['beneficiaryType'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      authorizingOfficer: this.editForm.get(['authorizingOfficer'])!.value,
      tutor: this.editForm.get(['tutor'])!.value,
    };
  }
}
