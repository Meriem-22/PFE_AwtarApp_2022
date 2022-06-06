import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDonationsReceivedItem, DonationsReceivedItem } from '../donations-received-item.model';
import { DonationsReceivedItemService } from '../service/donations-received-item.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';
import { IDonationsReceived } from 'app/entities/donations-received/donations-received.model';
import { DonationsReceivedService } from 'app/entities/donations-received/service/donations-received.service';

@Component({
  selector: 'jhi-donations-received-item-update',
  templateUrl: './donations-received-item-update.component.html',
})
export class DonationsReceivedItemUpdateComponent implements OnInit {
  isSaving = false;

  itemsSharedCollection: IItem[] = [];
  donationsReceivedsSharedCollection: IDonationsReceived[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    date: [null, [Validators.required]],
    archivated: [],
    item: [null, Validators.required],
    donationsReceived: [null, Validators.required],
  });

  constructor(
    protected donationsReceivedItemService: DonationsReceivedItemService,
    protected itemService: ItemService,
    protected donationsReceivedService: DonationsReceivedService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsReceivedItem }) => {
      this.updateForm(donationsReceivedItem);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donationsReceivedItem = this.createFromForm();
    if (donationsReceivedItem.id !== undefined) {
      this.subscribeToSaveResponse(this.donationsReceivedItemService.update(donationsReceivedItem));
    } else {
      this.subscribeToSaveResponse(this.donationsReceivedItemService.create(donationsReceivedItem));
    }
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  trackDonationsReceivedById(_index: number, item: IDonationsReceived): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationsReceivedItem>>): void {
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

  protected updateForm(donationsReceivedItem: IDonationsReceivedItem): void {
    this.editForm.patchValue({
      id: donationsReceivedItem.id,
      quantity: donationsReceivedItem.quantity,
      date: donationsReceivedItem.date,
      archivated: donationsReceivedItem.archivated,
      item: donationsReceivedItem.item,
      donationsReceived: donationsReceivedItem.donationsReceived,
    });

    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(this.itemsSharedCollection, donationsReceivedItem.item);
    this.donationsReceivedsSharedCollection = this.donationsReceivedService.addDonationsReceivedToCollectionIfMissing(
      this.donationsReceivedsSharedCollection,
      donationsReceivedItem.donationsReceived
    );
  }

  protected loadRelationshipsOptions(): void {
    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));

    this.donationsReceivedService
      .query()
      .pipe(map((res: HttpResponse<IDonationsReceived[]>) => res.body ?? []))
      .pipe(
        map((donationsReceiveds: IDonationsReceived[]) =>
          this.donationsReceivedService.addDonationsReceivedToCollectionIfMissing(
            donationsReceiveds,
            this.editForm.get('donationsReceived')!.value
          )
        )
      )
      .subscribe((donationsReceiveds: IDonationsReceived[]) => (this.donationsReceivedsSharedCollection = donationsReceiveds));
  }

  protected createFromForm(): IDonationsReceivedItem {
    return {
      ...new DonationsReceivedItem(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      date: this.editForm.get(['date'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      item: this.editForm.get(['item'])!.value,
      donationsReceived: this.editForm.get(['donationsReceived'])!.value,
    };
  }
}
