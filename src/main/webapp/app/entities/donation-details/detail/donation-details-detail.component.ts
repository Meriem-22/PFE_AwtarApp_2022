import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationDetails } from '../donation-details.model';

@Component({
  selector: 'jhi-donation-details-detail',
  templateUrl: './donation-details-detail.component.html',
})
export class DonationDetailsDetailComponent implements OnInit {
  donationDetails: IDonationDetails | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationDetails }) => {
      this.donationDetails = donationDetails;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
