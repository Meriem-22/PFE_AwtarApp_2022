import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ICompositeItem, CompositeItem } from '../composite-item.model';
import { CompositeItemService } from '../service/composite-item.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

@Component({
  selector: 'jhi-composite-item-update',
  templateUrl: './composite-item-update.component.html',
})
export class CompositeItemUpdateComponent implements OnInit {
  isSaving = false;

  itemsSharedCollection: IItem[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    archivated: [],
    composant: [null, Validators.required],
    composer: [null, Validators.required],
  });

  constructor(
    protected compositeItemService: CompositeItemService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compositeItem }) => {
      this.updateForm(compositeItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const compositeItem = this.createFromForm();
    if (compositeItem.id !== undefined) {
      this.subscribeToSaveResponse(this.compositeItemService.update(compositeItem));
    } else {
      this.subscribeToSaveResponse(this.compositeItemService.create(compositeItem));
    }
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICompositeItem>>): void {
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

  protected updateForm(compositeItem: ICompositeItem): void {
    this.editForm.patchValue({
      id: compositeItem.id,
      quantity: compositeItem.quantity,
      archivated: compositeItem.archivated,
      composant: compositeItem.composant,
      composer: compositeItem.composer,
    });

    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(
      this.itemsSharedCollection,
      compositeItem.composant,
      compositeItem.composer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(
        map((items: IItem[]) =>
          this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('composant')!.value, this.editForm.get('composer')!.value)
        )
      )
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));
  }

  protected createFromForm(): ICompositeItem {
    return {
      ...new CompositeItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      composant: this.editForm.get(['composant'])!.value,
      composer: this.editForm.get(['composer'])!.value,
    };
  }
}
