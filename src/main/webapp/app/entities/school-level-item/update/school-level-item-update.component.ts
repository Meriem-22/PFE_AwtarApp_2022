import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ISchoolLevelItem, SchoolLevelItem } from '../school-level-item.model';
import { SchoolLevelItemService } from '../service/school-level-item.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { ISchoolLevel } from 'app/entities/school-level/school-level.model';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';

@Component({
  selector: 'jhi-school-level-item-update',
  templateUrl: './school-level-item-update.component.html',
})
export class SchoolLevelItemUpdateComponent implements OnInit {
  isSaving = false;

  itemsSharedCollection: IItem[] = [];
  schoolLevelsSharedCollection: ISchoolLevel[] = [];

  editForm = this.fb.group({
    id: [],
    isSchoolItem: [],
    quantityNeeded: [],
    archivated: [],
    item: [null, Validators.required],
    schoolLevel: [null, Validators.required],
  });

  constructor(
    protected schoolLevelItemService: SchoolLevelItemService,
    protected itemService: ItemService,
    protected schoolLevelService: SchoolLevelService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolLevelItem }) => {
      this.updateForm(schoolLevelItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schoolLevelItem = this.createFromForm();
    if (schoolLevelItem.id !== undefined) {
      this.subscribeToSaveResponse(this.schoolLevelItemService.update(schoolLevelItem));
    } else {
      this.subscribeToSaveResponse(this.schoolLevelItemService.create(schoolLevelItem));
    }
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  trackSchoolLevelById(_index: number, item: ISchoolLevel): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolLevelItem>>): void {
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

  protected updateForm(schoolLevelItem: ISchoolLevelItem): void {
    this.editForm.patchValue({
      id: schoolLevelItem.id,
      isSchoolItem: schoolLevelItem.isSchoolItem,
      quantityNeeded: schoolLevelItem.quantityNeeded,
      archivated: schoolLevelItem.archivated,
      item: schoolLevelItem.item,
      schoolLevel: schoolLevelItem.schoolLevel,
    });

    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(this.itemsSharedCollection, schoolLevelItem.item);
    this.schoolLevelsSharedCollection = this.schoolLevelService.addSchoolLevelToCollectionIfMissing(
      this.schoolLevelsSharedCollection,
      schoolLevelItem.schoolLevel
    );
  }

  protected loadRelationshipsOptions(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));

    this.schoolLevelService
      .query()
      .pipe(map((res: HttpResponse<ISchoolLevel[]>) => res.body ?? []))
      .pipe(
        map((schoolLevels: ISchoolLevel[]) =>
          this.schoolLevelService.addSchoolLevelToCollectionIfMissing(schoolLevels, this.editForm.get('schoolLevel')!.value)
        )
      )
      .subscribe((schoolLevels: ISchoolLevel[]) => (this.schoolLevelsSharedCollection = schoolLevels));
  }

  protected createFromForm(): ISchoolLevelItem {
    return {
      ...new SchoolLevelItem(),
      id: this.editForm.get(['id'])!.value,
      isSchoolItem: this.editForm.get(['isSchoolItem'])!.value,
      quantityNeeded: this.editForm.get(['quantityNeeded'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      item: this.editForm.get(['item'])!.value,
      schoolLevel: this.editForm.get(['schoolLevel'])!.value,
    };
  }
}
