import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISchoolLevel } from '../school-level.model';

@Component({
  selector: 'jhi-school-level-detail',
  templateUrl: './school-level-detail.component.html',
})
export class SchoolLevelDetailComponent implements OnInit {
  schoolLevel: ISchoolLevel | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ schoolLevel }) => {
      this.schoolLevel = schoolLevel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
