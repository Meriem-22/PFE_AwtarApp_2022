import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChildStatusItem } from '../child-status-item.model';

@Component({
  selector: 'jhi-child-status-item-detail',
  templateUrl: './child-status-item-detail.component.html',
})
export class ChildStatusItemDetailComponent implements OnInit {
  childStatusItem: IChildStatusItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ childStatusItem }) => {
      this.childStatusItem = childStatusItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
