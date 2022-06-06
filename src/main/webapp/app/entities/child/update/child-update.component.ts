import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IChild, Child } from '../child.model';
import { ChildService } from '../service/child.service';
import { IFamily } from 'app/entities/family/family.model';
import { FamilyService } from 'app/entities/family/service/family.service';

@Component({
  selector: 'jhi-child-update',
  templateUrl: './child-update.component.html',
})
export class ChildUpdateComponent implements OnInit {
  isSaving = false;

  familiesSharedCollection: IFamily[] = [];

  editForm = this.fb.group({
    id: [],
    family: [],
  });

  constructor(
    protected childService: ChildService,
    protected familyService: FamilyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ child }) => {
      this.updateForm(child);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const child = this.createFromForm();
    if (child.id !== undefined) {
      this.subscribeToSaveResponse(this.childService.update(child));
    } else {
      this.subscribeToSaveResponse(this.childService.create(child));
    }
  }

  trackFamilyById(_index: number, item: IFamily): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChild>>): void {
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

  protected updateForm(child: IChild): void {
    this.editForm.patchValue({
      id: child.id,
      family: child.family,
    });

    this.familiesSharedCollection = this.familyService.addFamilyToCollectionIfMissing(this.familiesSharedCollection, child.family);
  }

  protected loadRelationshipsOptions(): void {
    this.familyService
      .query()
      .pipe(map((res: HttpResponse<IFamily[]>) => res.body ?? []))
      .pipe(map((families: IFamily[]) => this.familyService.addFamilyToCollectionIfMissing(families, this.editForm.get('family')!.value)))
      .subscribe((families: IFamily[]) => (this.familiesSharedCollection = families));
  }

  protected createFromForm(): IChild {
    return {
      ...new Child(),
      id: this.editForm.get(['id'])!.value,
      family: this.editForm.get(['family'])!.value,
    };
  }
}
