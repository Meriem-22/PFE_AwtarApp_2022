import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFamily, Family } from '../family.model';
import { FamilyService } from '../service/family.service';

@Component({
  selector: 'jhi-family-update',
  templateUrl: './family-update.component.html',
})
export class FamilyUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    familyName: [null, [Validators.required]],
    dwelling: [null, [Validators.required]],
    area: [],
    notebookOfPoverty: [],
    notebookOfHandicap: [],
    archivated: [],
  });

  constructor(protected familyService: FamilyService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.updateForm(family);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const family = this.createFromForm();
    if (family.id !== undefined) {
      this.subscribeToSaveResponse(this.familyService.update(family));
    } else {
      this.subscribeToSaveResponse(this.familyService.create(family));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>): void {
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

  protected updateForm(family: IFamily): void {
    this.editForm.patchValue({
      id: family.id,
      familyName: family.familyName,
      dwelling: family.dwelling,
      area: family.area,
      notebookOfPoverty: family.notebookOfPoverty,
      notebookOfHandicap: family.notebookOfHandicap,
      archivated: family.archivated,
    });
  }

  protected createFromForm(): IFamily {
    return {
      ...new Family(),
      id: this.editForm.get(['id'])!.value,
      familyName: this.editForm.get(['familyName'])!.value,
      dwelling: this.editForm.get(['dwelling'])!.value,
      area: this.editForm.get(['area'])!.value,
      notebookOfPoverty: this.editForm.get(['notebookOfPoverty'])!.value,
      notebookOfHandicap: this.editForm.get(['notebookOfHandicap'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}