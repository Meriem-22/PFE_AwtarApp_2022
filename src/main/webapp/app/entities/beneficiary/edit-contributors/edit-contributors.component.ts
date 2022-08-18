import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-edit-contributors',
  templateUrl: './edit-contributors.component.html',
  styleUrls: ['./edit-contributors.component.css'],
})
export class EditContributorsComponent implements OnInit {
  account: Account | null = null;

  constructor(protected activatedRoute: ActivatedRoute, private accountService: AccountService) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      console.log(beneficiary);
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }
}
