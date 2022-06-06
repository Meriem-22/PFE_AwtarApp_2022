import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAuthorizingOfficer } from '../authorizing-officer.model';

@Component({
  selector: 'jhi-authorizing-officer-detail',
  templateUrl: './authorizing-officer-detail.component.html',
})
export class AuthorizingOfficerDetailComponent implements OnInit {
  authorizingOfficer: IAuthorizingOfficer | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authorizingOfficer }) => {
      this.authorizingOfficer = authorizingOfficer;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
