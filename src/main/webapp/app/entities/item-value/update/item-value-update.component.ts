import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IItemValue, ItemValue } from '../item-value.model';
import { ItemValueService } from '../service/item-value.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

@Component({
  selector: 'jhi-item-value-update',
  templateUrl: './item-value-update.component.html',
})
export class ItemValueUpdateComponent implements OnInit {
  isSaving = false;

  itemsSharedCollection: IItem[] = [];

  editForm = this.fb.group({
    id: [],
    price: [null, [Validators.required]],
    priceDate: [null, [Validators.required]],
    availableStockQuantity: [],
    archivated: [],
    item: [null, Validators.required],
  });

  constructor(
    protected itemValueService: ItemValueService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemValue }) => {
      this.updateForm(itemValue);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const itemValue = this.createFromForm();
    if (itemValue.id !== undefined) {
      this.subscribeToSaveResponse(this.itemValueService.update(itemValue));
    } else {
      this.subscribeToSaveResponse(this.itemValueService.create(itemValue));
    }
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItemValue>>): void {
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

  protected updateForm(itemValue: IItemValue): void {
    this.editForm.patchValue({
      id: itemValue.id,
      price: itemValue.price,
      priceDate: itemValue.priceDate,
      availableStockQuantity: itemValue.availableStockQuantity,
      archivated: itemValue.archivated,
      item: itemValue.item,
    });

    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(this.itemsSharedCollection, itemValue.item);
  }

  protected loadRelationshipsOptions(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));
  }

  protected createFromForm(): IItemValue {
    return {
      ...new ItemValue(),
      id: this.editForm.get(['id'])!.value,
      price: this.editForm.get(['price'])!.value,
      priceDate: this.editForm.get(['priceDate'])!.value,
      availableStockQuantity: this.editForm.get(['availableStockQuantity'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      item: this.editForm.get(['item'])!.value,
    };
  }
}
