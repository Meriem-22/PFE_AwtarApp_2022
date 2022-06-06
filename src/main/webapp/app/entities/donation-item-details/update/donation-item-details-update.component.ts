import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDonationItemDetails, DonationItemDetails } from '../donation-item-details.model';
import { DonationItemDetailsService } from '../service/donation-item-details.service';
import { IDonationDetails } from 'app/entities/donation-details/donation-details.model';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { IItem } from 'app/entities/item/item.model';
import { ItemService } from 'app/entities/item/service/item.service';

@Component({
  selector: 'jhi-donation-item-details-update',
  templateUrl: './donation-item-details-update.component.html',
})
export class DonationItemDetailsUpdateComponent implements OnInit {
  isSaving = false;

  donationDetailsSharedCollection: IDonationDetails[] = [];
  itemsSharedCollection: IItem[] = [];

  editForm = this.fb.group({
    id: [],
    quantity: [null, [Validators.required]],
    date: [null, [Validators.required]],
    archivated: [],
    donationDetails: [null, Validators.required],
    item: [null, Validators.required],
  });

  constructor(
    protected donationItemDetailsService: DonationItemDetailsService,
    protected donationDetailsService: DonationDetailsService,
    protected itemService: ItemService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationItemDetails }) => {
      this.updateForm(donationItemDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donationItemDetails = this.createFromForm();
    if (donationItemDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.donationItemDetailsService.update(donationItemDetails));
    } else {
      this.subscribeToSaveResponse(this.donationItemDetailsService.create(donationItemDetails));
    }
  }

  trackDonationDetailsById(_index: number, item: IDonationDetails): number {
    return item.id!;
  }

  trackItemById(_index: number, item: IItem): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationItemDetails>>): void {
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

  protected updateForm(donationItemDetails: IDonationItemDetails): void {
    this.editForm.patchValue({
      id: donationItemDetails.id,
      quantity: donationItemDetails.quantity,
      date: donationItemDetails.date,
      archivated: donationItemDetails.archivated,
      donationDetails: donationItemDetails.donationDetails,
      item: donationItemDetails.item,
    });

    this.donationDetailsSharedCollection = this.donationDetailsService.addDonationDetailsToCollectionIfMissing(
      this.donationDetailsSharedCollection,
      donationItemDetails.donationDetails
    );
    this.itemsSharedCollection = this.itemService.addItemToCollectionIfMissing(this.itemsSharedCollection, donationItemDetails.item);
  }

  protected loadRelationshipsOptions(): void {
    this.donationDetailsService
      .query()
      .pipe(map((res: HttpResponse<IDonationDetails[]>) => res.body ?? []))
      .pipe(
        map((donationDetails: IDonationDetails[]) =>
          this.donationDetailsService.addDonationDetailsToCollectionIfMissing(donationDetails, this.editForm.get('donationDetails')!.value)
        )
      )
      .subscribe((donationDetails: IDonationDetails[]) => (this.donationDetailsSharedCollection = donationDetails));

    this.itemService
      .query()
      .pipe(map((res: HttpResponse<IItem[]>) => res.body ?? []))
      .pipe(map((items: IItem[]) => this.itemService.addItemToCollectionIfMissing(items, this.editForm.get('item')!.value)))
      .subscribe((items: IItem[]) => (this.itemsSharedCollection = items));
  }

  protected createFromForm(): IDonationItemDetails {
    return {
      ...new DonationItemDetails(),
      id: this.editForm.get(['id'])!.value,
      quantity: this.editForm.get(['quantity'])!.value,
      date: this.editForm.get(['date'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      donationDetails: this.editForm.get(['donationDetails'])!.value,
      item: this.editForm.get(['item'])!.value,
    };
  }
}
