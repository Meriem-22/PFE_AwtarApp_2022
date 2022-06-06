import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationsReceivedItem } from '../donations-received-item.model';

@Component({
  selector: 'jhi-donations-received-item-detail',
  templateUrl: './donations-received-item-detail.component.html',
})
export class DonationsReceivedItemDetailComponent implements OnInit {
  donationsReceivedItem: IDonationsReceivedItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsReceivedItem }) => {
      this.donationsReceivedItem = donationsReceivedItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
