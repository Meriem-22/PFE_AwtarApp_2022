import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChildStatus } from '../child-status.model';

@Component({
  selector: 'jhi-child-status-detail',
  templateUrl: './child-status-detail.component.html',
})
export class ChildStatusDetailComponent implements OnInit {
  childStatus: IChildStatus | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ childStatus }) => {
      this.childStatus = childStatus;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
