import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IDonationsIssued } from '../donations-issued.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { DonationsIssuedService } from '../service/donations-issued.service';
import { DonationsIssuedDeleteDialogComponent } from '../delete/donations-issued-delete-dialog.component';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-donations-issued',
  templateUrl: './donations-issued.component.html',
})
export class DonationsIssuedComponent implements OnInit {
  donationsIssueds?: IDonationsIssued[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  account: Account | null = null;
  selected?: any[];

  constructor(
    protected donationsIssuedService: DonationsIssuedService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    private accountService: AccountService,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.donationsIssuedService.getAll().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationsIssueds = res.body ?? [];
        console.log(this.donationsIssueds);
      },
      error: e => console.error(e),
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  trackId(_index: number, item: IDonationsIssued): number {
    return item.id!;
  }

  delete(donationsIssued: IDonationsIssued): void {
    const modalRef = this.modalService.open(DonationsIssuedDeleteDialogComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.donationsIssued = donationsIssued;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  loadPage(): void {
    console.log(this.donationsIssueds);
  }
}
