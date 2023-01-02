import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { IEstablishmentType } from '../establishment-type.model';

@Component({
  selector: 'jhi-establishment-type-detail',
  templateUrl: './establishment-type-detail.component.html',
})
export class EstablishmentTypeDetailComponent implements OnInit {
  establishmentType: IEstablishmentType | null = null;
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ establishmentType }) => {
      this.establishmentType = establishmentType;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
