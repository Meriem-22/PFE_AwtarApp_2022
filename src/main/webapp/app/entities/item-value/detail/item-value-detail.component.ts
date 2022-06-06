import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IItemValue } from '../item-value.model';

@Component({
  selector: 'jhi-item-value-detail',
  templateUrl: './item-value-detail.component.html',
})
export class ItemValueDetailComponent implements OnInit {
  itemValue: IItemValue | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ itemValue }) => {
      this.itemValue = itemValue;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
