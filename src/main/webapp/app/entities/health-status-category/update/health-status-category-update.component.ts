import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IHealthStatusCategory, HealthStatusCategory } from '../health-status-category.model';
import { HealthStatusCategoryService } from '../service/health-status-category.service';

@Component({
  selector: 'jhi-health-status-category-update',
  templateUrl: './health-status-category-update.component.html',
})
export class HealthStatusCategoryUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    archivated: [],
  });

  constructor(
    protected healthStatusCategoryService: HealthStatusCategoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ healthStatusCategory }) => {
      this.updateForm(healthStatusCategory);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const healthStatusCategory = this.createFromForm();
    if (healthStatusCategory.id !== undefined) {
      this.subscribeToSaveResponse(this.healthStatusCategoryService.update(healthStatusCategory));
    } else {
      this.subscribeToSaveResponse(this.healthStatusCategoryService.create(healthStatusCategory));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHealthStatusCategory>>): void {
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

  protected updateForm(healthStatusCategory: IHealthStatusCategory): void {
    this.editForm.patchValue({
      id: healthStatusCategory.id,
      name: healthStatusCategory.name,
      archivated: healthStatusCategory.archivated,
    });
  }

  protected createFromForm(): IHealthStatusCategory {
    return {
      ...new HealthStatusCategory(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}
