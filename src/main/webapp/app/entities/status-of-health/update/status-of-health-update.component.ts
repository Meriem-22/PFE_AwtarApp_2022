import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IStatusOfHealth, StatusOfHealth } from '../status-of-health.model';
import { StatusOfHealthService } from '../service/status-of-health.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { IHealthStatusCategory } from 'app/entities/health-status-category/health-status-category.model';
import { HealthStatusCategoryService } from 'app/entities/health-status-category/service/health-status-category.service';

@Component({
  selector: 'jhi-status-of-health-update',
  templateUrl: './status-of-health-update.component.html',
})
export class StatusOfHealthUpdateComponent implements OnInit {
  isSaving = false;

  profilesSharedCollection: IProfile[] = [];
  healthStatusCategoriesSharedCollection: IHealthStatusCategory[] = [];

  editForm = this.fb.group({
    id: [],
    healthStatusDate: [null, [Validators.required]],
    urlDetailsAttached: [],
    urlDetailsAttachedContentType: [],
    archivated: [],
    person: [null, Validators.required],
    healthStatusCategory: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected statusOfHealthService: StatusOfHealthService,
    protected profileService: ProfileService,
    protected healthStatusCategoryService: HealthStatusCategoryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusOfHealth }) => {
      this.updateForm(statusOfHealth);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('awtarApp.error', { ...err, key: 'error.file.' + err.key })),
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const statusOfHealth = this.createFromForm();
    if (statusOfHealth.id !== undefined) {
      this.subscribeToSaveResponse(this.statusOfHealthService.update(statusOfHealth));
    } else {
      this.subscribeToSaveResponse(this.statusOfHealthService.create(statusOfHealth));
    }
  }

  trackProfileById(_index: number, item: IProfile): number {
    return item.id!;
  }

  trackHealthStatusCategoryById(_index: number, item: IHealthStatusCategory): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IStatusOfHealth>>): void {
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

  protected updateForm(statusOfHealth: IStatusOfHealth): void {
    this.editForm.patchValue({
      id: statusOfHealth.id,
      healthStatusDate: statusOfHealth.healthStatusDate,
      urlDetailsAttached: statusOfHealth.urlDetailsAttached,
      urlDetailsAttachedContentType: statusOfHealth.urlDetailsAttachedContentType,
      archivated: statusOfHealth.archivated,
      person: statusOfHealth.person,
      healthStatusCategory: statusOfHealth.healthStatusCategory,
    });

    this.profilesSharedCollection = this.profileService.addProfileToCollectionIfMissing(
      this.profilesSharedCollection,
      statusOfHealth.person
    );
    this.healthStatusCategoriesSharedCollection = this.healthStatusCategoryService.addHealthStatusCategoryToCollectionIfMissing(
      this.healthStatusCategoriesSharedCollection,
      statusOfHealth.healthStatusCategory
    );
  }

  protected loadRelationshipsOptions(): void {
    this.profileService
      .query()
      .pipe(map((res: HttpResponse<IProfile[]>) => res.body ?? []))
      .pipe(
        map((profiles: IProfile[]) => this.profileService.addProfileToCollectionIfMissing(profiles, this.editForm.get('person')!.value))
      )
      .subscribe((profiles: IProfile[]) => (this.profilesSharedCollection = profiles));

    this.healthStatusCategoryService
      .query()
      .pipe(map((res: HttpResponse<IHealthStatusCategory[]>) => res.body ?? []))
      .pipe(
        map((healthStatusCategories: IHealthStatusCategory[]) =>
          this.healthStatusCategoryService.addHealthStatusCategoryToCollectionIfMissing(
            healthStatusCategories,
            this.editForm.get('healthStatusCategory')!.value
          )
        )
      )
      .subscribe(
        (healthStatusCategories: IHealthStatusCategory[]) => (this.healthStatusCategoriesSharedCollection = healthStatusCategories)
      );
  }

  protected createFromForm(): IStatusOfHealth {
    return {
      ...new StatusOfHealth(),
      id: this.editForm.get(['id'])!.value,
      healthStatusDate: this.editForm.get(['healthStatusDate'])!.value,
      urlDetailsAttachedContentType: this.editForm.get(['urlDetailsAttachedContentType'])!.value,
      urlDetailsAttached: this.editForm.get(['urlDetailsAttached'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      person: this.editForm.get(['person'])!.value,
      healthStatusCategory: this.editForm.get(['healthStatusCategory'])!.value,
    };
  }
}
