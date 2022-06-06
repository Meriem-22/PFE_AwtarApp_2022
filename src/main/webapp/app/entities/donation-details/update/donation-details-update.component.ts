import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDonationDetails, DonationDetails } from '../donation-details.model';
import { DonationDetailsService } from '../service/donation-details.service';
import { IDonationsIssued } from 'app/entities/donations-issued/donations-issued.model';
import { DonationsIssuedService } from 'app/entities/donations-issued/service/donations-issued.service';
import { INature } from 'app/entities/nature/nature.model';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';

@Component({
  selector: 'jhi-donation-details-update',
  templateUrl: './donation-details-update.component.html',
})
export class DonationDetailsUpdateComponent implements OnInit {
  isSaving = false;

  donationsIssuedsSharedCollection: IDonationsIssued[] = [];
  naturesSharedCollection: INature[] = [];
  beneficiariesSharedCollection: IBeneficiary[] = [];

  editForm = this.fb.group({
    id: [],
    description: [null, [Validators.required]],
    archivated: [],
    donationsIssued: [null, Validators.required],
    nature: [null, Validators.required],
    beneficiary: [null, Validators.required],
  });

  constructor(
    protected donationDetailsService: DonationDetailsService,
    protected donationsIssuedService: DonationsIssuedService,
    protected natureService: NatureService,
    protected beneficiaryService: BeneficiaryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationDetails }) => {
      this.updateForm(donationDetails);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const donationDetails = this.createFromForm();
    if (donationDetails.id !== undefined) {
      this.subscribeToSaveResponse(this.donationDetailsService.update(donationDetails));
    } else {
      this.subscribeToSaveResponse(this.donationDetailsService.create(donationDetails));
    }
  }

  trackDonationsIssuedById(_index: number, item: IDonationsIssued): number {
    return item.id!;
  }

  trackNatureById(_index: number, item: INature): number {
    return item.id!;
  }

  trackBeneficiaryById(_index: number, item: IBeneficiary): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationDetails>>): void {
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

  protected updateForm(donationDetails: IDonationDetails): void {
    this.editForm.patchValue({
      id: donationDetails.id,
      description: donationDetails.description,
      archivated: donationDetails.archivated,
      donationsIssued: donationDetails.donationsIssued,
      nature: donationDetails.nature,
      beneficiary: donationDetails.beneficiary,
    });

    this.donationsIssuedsSharedCollection = this.donationsIssuedService.addDonationsIssuedToCollectionIfMissing(
      this.donationsIssuedsSharedCollection,
      donationDetails.donationsIssued
    );
    this.naturesSharedCollection = this.natureService.addNatureToCollectionIfMissing(this.naturesSharedCollection, donationDetails.nature);
    this.beneficiariesSharedCollection = this.beneficiaryService.addBeneficiaryToCollectionIfMissing(
      this.beneficiariesSharedCollection,
      donationDetails.beneficiary
    );
  }

  protected loadRelationshipsOptions(): void {
    this.donationsIssuedService
      .query()
      .pipe(map((res: HttpResponse<IDonationsIssued[]>) => res.body ?? []))
      .pipe(
        map((donationsIssueds: IDonationsIssued[]) =>
          this.donationsIssuedService.addDonationsIssuedToCollectionIfMissing(donationsIssueds, this.editForm.get('donationsIssued')!.value)
        )
      )
      .subscribe((donationsIssueds: IDonationsIssued[]) => (this.donationsIssuedsSharedCollection = donationsIssueds));

    this.natureService
      .query()
      .pipe(map((res: HttpResponse<INature[]>) => res.body ?? []))
      .pipe(map((natures: INature[]) => this.natureService.addNatureToCollectionIfMissing(natures, this.editForm.get('nature')!.value)))
      .subscribe((natures: INature[]) => (this.naturesSharedCollection = natures));

    this.beneficiaryService
      .query()
      .pipe(map((res: HttpResponse<IBeneficiary[]>) => res.body ?? []))
      .pipe(
        map((beneficiaries: IBeneficiary[]) =>
          this.beneficiaryService.addBeneficiaryToCollectionIfMissing(beneficiaries, this.editForm.get('beneficiary')!.value)
        )
      )
      .subscribe((beneficiaries: IBeneficiary[]) => (this.beneficiariesSharedCollection = beneficiaries));
  }

  protected createFromForm(): IDonationDetails {
    return {
      ...new DonationDetails(),
      id: this.editForm.get(['id'])!.value,
      description: this.editForm.get(['description'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      donationsIssued: this.editForm.get(['donationsIssued'])!.value,
      nature: this.editForm.get(['nature'])!.value,
      beneficiary: this.editForm.get(['beneficiary'])!.value,
    };
  }
}
