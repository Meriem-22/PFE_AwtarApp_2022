import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICompositeItem } from '../composite-item.model';

@Component({
  selector: 'jhi-composite-item-detail',
  templateUrl: './composite-item-detail.component.html',
})
export class CompositeItemDetailComponent implements OnInit {
  compositeItem: ICompositeItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ compositeItem }) => {
      this.compositeItem = compositeItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
