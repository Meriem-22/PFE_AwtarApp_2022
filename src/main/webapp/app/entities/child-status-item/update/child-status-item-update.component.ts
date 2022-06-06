import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IChildStatusItem, ChildStatusItem } from '../child-status-item.model';
import { ChildStatusItemService } from '../service/child-status-item.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { IChildStatus } from 'app/entities/child-status/child-status.model';
import { ChildStatusService } from 'app/entities/child-status/service/child-status.service';

@Component({
  selector: 'jhi-child-status-item-update',
  templateUrl: './child-status-item-update.component.html',
})
export class ChildStatusItemUpdateComponent implements OnInit {
  isSaving = false;

  itemsSharedCollection: IItem[] = [];
  childStatusesSharedCollection: IChildStatus[] = [];

  editForm = this.fb.group({
    id: [],
    affected: [],
    archivated: [],
    item: [null, Validators.required],
    childStatus: [null, Validators.required],
  });

  constructor(
    protected childStatusItemService: ChildStatusItemService,
    protected itemService: ItemService,
    protected childStatusService: ChildStatusService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ childStatusItem }) => {
      this.updateForm(childStatusItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const childStatusItem = this.createFromForm();
    if (childStatusItem.id !== undefined) {
      this.subscribeToSaveResponse(this.childStatusItemService.update(childStatusItem));
    } else {
      this.subscribeToSaveResponse(this.childStatusItemService.create(childStatusItem));
    }
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  trackChildStatusById(_index: number, item: IChildStatus): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChildStatusItem>>): void {
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

  protected updateForm(childStatusItem: IChildStatusItem): void {
    this.editForm.patchValue({
      id: childStatusItem.id,
      affected: childStatusItem.affected,
      archivated: childStatusItem.archivated,
      item: childStatusItem.item,
      childStatus: childStatusItem.childStatus,
    });

    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(this.itemsSharedCollection, childStatusItem.item);
    this.childStatusesSharedCollection = this.childStatusService.addChildStatusToCollectionIfMissing(
      this.childStatusesSharedCollection,
      childStatusItem.childStatus
    );
  }

  protected loadRelationshipsOptions(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));

    this.childStatusService
      .query()
      .pipe(map((res: HttpResponse<IChildStatus[]>) => res.body ?? []))
      .pipe(
        map((childStatuses: IChildStatus[]) =>
          this.childStatusService.addChildStatusToCollectionIfMissing(childStatuses, this.editForm.get('childStatus')!.value)
        )
      )
      .subscribe((childStatuses: IChildStatus[]) => (this.childStatusesSharedCollection = childStatuses));
  }

  protected createFromForm(): IChildStatusItem {
    return {
      ...new ChildStatusItem(),
      id: this.editForm.get(['id'])!.value,
      affected: this.editForm.get(['affected'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      item: this.editForm.get(['item'])!.value,
      childStatus: this.editForm.get(['childStatus'])!.value,
    };
  }
}
