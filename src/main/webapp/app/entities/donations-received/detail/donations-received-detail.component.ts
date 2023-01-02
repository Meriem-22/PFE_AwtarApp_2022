import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDonationsReceived } from '../donations-received.model';
import { DataUtils } from 'app/core/util/data-util.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-donations-received-detail',
  templateUrl: './donations-received-detail.component.html',
})
export class DonationsReceivedDetailComponent implements OnInit {
  donationsReceived: IDonationsReceived | null = null;
  account: Account | null = null;

  constructor(protected dataUtils: DataUtils, private accountService: AccountService, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsReceived }) => {
      this.donationsReceived = donationsReceived;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  previousState(): void {
    window.history.back();
  }
}
