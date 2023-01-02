import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

import { IEducationalInstitution } from '../educational-institution.model';

@Component({
  selector: 'jhi-educational-institution-detail',
  templateUrl: './educational-institution-detail.component.html',
})
export class EducationalInstitutionDetailComponent implements OnInit {
  educationalInstitution: IEducationalInstitution | null = null;
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    this.activatedRoute.data.subscribe(({ educationalInstitution }) => {
      this.educationalInstitution = educationalInstitution;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
