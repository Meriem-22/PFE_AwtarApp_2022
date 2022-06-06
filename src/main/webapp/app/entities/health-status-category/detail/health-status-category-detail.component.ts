import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHealthStatusCategory } from '../health-status-category.model';

@Component({
  selector: 'jhi-health-status-category-detail',
  templateUrl: './health-status-category-detail.component.html',
})
export class HealthStatusCategoryDetailComponent implements OnInit {
  healthStatusCategory: IHealthStatusCategory | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ healthStatusCategory }) => {
      this.healthStatusCategory = healthStatusCategory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
