import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { IDonationItemDetails } from 'app/entities/donation-item-details/donation-item-details.model';
import { DonationItemDetailsService } from 'app/entities/donation-item-details/service/donation-item-details.service';
import { IItem } from 'app/entities/item/item.model';

import { IDonationsIssued } from '../donations-issued.model';

@Component({
  selector: 'jhi-donations-issued-detail',
  templateUrl: './donations-issued-detail.component.html',
})
export class DonationsIssuedDetailComponent implements OnInit {
  donationsIssued: IDonationsIssued | null = null;
  donations?: IDonationItemDetails[] = [];
  isLoading = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected donationItemDetailsService: DonationItemDetailsService,
    protected dataUtils: DataUtils
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsIssued }) => {
      this.donationsIssued = donationsIssued;
      this.donationItemDetailsService.getAllDonationItemDetails(donationsIssued.id).subscribe({
        next: (res: HttpResponse<IDonationItemDetails[]>) => {
          this.isLoading = false;
          this.donations = res.body ?? [];
          console.log(this.donations);
        },
        error: () => {
          this.isLoading = false;
        },
      });
    });
  }

  previousState(): void {
    window.history.back();
  }

  trackId(_index: number, item: IItem): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }
}
