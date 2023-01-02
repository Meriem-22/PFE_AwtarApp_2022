import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { INature } from '../nature.model';

@Component({
  selector: 'jhi-nature-detail',
  templateUrl: './nature-detail.component.html',
})
export class NatureDetailComponent implements OnInit {
  nature: INature | null = null;
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ nature }) => {
      this.nature = nature;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
