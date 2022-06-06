import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEducationalInstitution } from '../educational-institution.model';

@Component({
  selector: 'jhi-educational-institution-detail',
  templateUrl: './educational-institution-detail.component.html',
})
export class EducationalInstitutionDetailComponent implements OnInit {
  educationalInstitution: IEducationalInstitution | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ educationalInstitution }) => {
      this.educationalInstitution = educationalInstitution;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
