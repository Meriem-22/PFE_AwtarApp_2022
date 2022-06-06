import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationItemDetails } from '../donation-item-details.model';

@Component({
  selector: 'jhi-donation-item-details-detail',
  templateUrl: './donation-item-details-detail.component.html',
})
export class DonationItemDetailsDetailComponent implements OnInit {
  donationItemDetails: IDonationItemDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationItemDetails }) => {
      this.donationItemDetails = donationItemDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
