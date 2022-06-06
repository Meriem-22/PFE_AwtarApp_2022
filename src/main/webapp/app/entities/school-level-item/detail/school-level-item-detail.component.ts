import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolLevelItem } from '../school-level-item.model';

@Component({
  selector: 'jhi-school-level-item-detail',
  templateUrl: './school-level-item-detail.component.html',
})
export class SchoolLevelItemDetailComponent implements OnInit {
  schoolLevelItem: ISchoolLevelItem | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolLevelItem }) => {
      this.schoolLevelItem = schoolLevelItem;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
