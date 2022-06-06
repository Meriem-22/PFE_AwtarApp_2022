import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IParent, Parent } from '../parent.model';
import { ParentService } from '../service/parent.service';
import { IFamily } from 'app/entities/family/family.model';
import { FamilyService } from 'app/entities/family/service/family.service';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';

@Component({
  selector: 'jhi-parent-update',
  templateUrl: './parent-update.component.html',
})
export class ParentUpdateComponent implements OnInit {
  isSaving = false;
  maritalStatusValues = Object.keys(MaritalStatus);

  familiesSharedCollection: IFamily[] = [];
  familyHeadsCollection: IFamily[] = [];

  editForm = this.fb.group({
    id: [],
    annualRevenue: [null, [Validators.required]],
    cnss: [],
    maritalStatus: [null, [Validators.required]],
    occupation: [null, [Validators.required]],
    deceased: [],
    dateOfDeath: [],
    familyHead: [],
    family: [null, Validators.required],
  });

  constructor(
    protected parentService: ParentService,
    protected familyService: FamilyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parent }) => {
      this.updateForm(parent);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const parent = this.createFromForm();
    if (parent.id !== undefined) {
      this.subscribeToSaveResponse(this.parentService.update(parent));
    } else {
      this.subscribeToSaveResponse(this.parentService.create(parent));
    }
  }

  trackFamilyById(_index: number, item: IFamily): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParent>>): void {
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

  protected updateForm(parent: IParent): void {
    this.editForm.patchValue({
      id: parent.id,
      annualRevenue: parent.annualRevenue,
      cnss: parent.cnss,
      maritalStatus: parent.maritalStatus,
      occupation: parent.occupation,
      deceased: parent.deceased,
      dateOfDeath: parent.dateOfDeath,
      familyHead: parent.familyHead,
      family: parent.family,
    });

    this.familiesSharedCollection = this.familyService.addFamilyToCollectionIfMissing(this.familiesSharedCollection, parent.family);
    this.familyHeadsCollection = this.familyService.addFamilyToCollectionIfMissing(this.familyHeadsCollection, parent.familyHead);
  }

  protected loadRelationshipsOptions(): void {
    this.familyService
      .query()
      .pipe(map((res: HttpResponse<IFamily[]>) => res.body ?? []))
      .pipe(map((families: IFamily[]) => this.familyService.addFamilyToCollectionIfMissing(families, this.editForm.get('family')!.value)))
      .subscribe((families: IFamily[]) => (this.familiesSharedCollection = families));

    this.familyService
      .query({ filter: 'head-is-null' })
      .pipe(map((res: HttpResponse<IFamily[]>) => res.body ?? []))
      .pipe(
        map((families: IFamily[]) => this.familyService.addFamilyToCollectionIfMissing(families, this.editForm.get('familyHead')!.value))
      )
      .subscribe((families: IFamily[]) => (this.familyHeadsCollection = families));
  }

  protected createFromForm(): IParent {
    return {
      ...new Parent(),
      id: this.editForm.get(['id'])!.value,
      annualRevenue: this.editForm.get(['annualRevenue'])!.value,
      cnss: this.editForm.get(['cnss'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      occupation: this.editForm.get(['occupation'])!.value,
      deceased: this.editForm.get(['deceased'])!.value,
      dateOfDeath: this.editForm.get(['dateOfDeath'])!.value,
      familyHead: this.editForm.get(['familyHead'])!.value,
      family: this.editForm.get(['family'])!.value,
    };
  }
}
