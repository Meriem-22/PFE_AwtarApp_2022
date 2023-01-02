import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { ICity } from '../city.model';

@Component({
  selector: 'jhi-city-detail',
  templateUrl: './city-detail.component.html',
})
export class CityDetailComponent implements OnInit {
  city: ICity | null = null;
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ city }) => {
      this.city = city;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
