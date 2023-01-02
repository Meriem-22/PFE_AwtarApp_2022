import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { ISchoolLevel } from '../school-level.model';

@Component({
  selector: 'jhi-school-level-detail',
  templateUrl: './school-level-detail.component.html',
})
export class SchoolLevelDetailComponent implements OnInit {
  schoolLevel: ISchoolLevel | null = null;
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    this.activatedRoute.data.subscribe(({ schoolLevel }) => {
      this.schoolLevel = schoolLevel;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
