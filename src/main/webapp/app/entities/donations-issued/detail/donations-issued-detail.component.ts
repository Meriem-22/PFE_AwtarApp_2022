import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationsIssued } from '../donations-issued.model';

@Component({
  selector: 'jhi-donations-issued-detail',
  templateUrl: './donations-issued-detail.component.html',
})
export class DonationsIssuedDetailComponent implements OnInit {
  donationsIssued: IDonationsIssued | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsIssued }) => {
      this.donationsIssued = donationsIssued;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
