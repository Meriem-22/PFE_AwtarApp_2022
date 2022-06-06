import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEstablishmentType } from '../establishment-type.model';

@Component({
  selector: 'jhi-establishment-type-detail',
  templateUrl: './establishment-type-detail.component.html',
})
export class EstablishmentTypeDetailComponent implements OnInit {
  establishmentType: IEstablishmentType | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ establishmentType }) => {
      this.establishmentType = establishmentType;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
