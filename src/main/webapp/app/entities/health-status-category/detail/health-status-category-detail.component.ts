import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { IHealthStatusCategory } from '../health-status-category.model';

@Component({
  selector: 'jhi-health-status-category-detail',
  templateUrl: './health-status-category-detail.component.html',
})
export class HealthStatusCategoryDetailComponent implements OnInit {
  healthStatusCategory: IHealthStatusCategory | null = null;
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ healthStatusCategory }) => {
      this.healthStatusCategory = healthStatusCategory;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
