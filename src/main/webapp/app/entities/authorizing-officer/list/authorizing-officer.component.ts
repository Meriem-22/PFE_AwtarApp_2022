import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IAuthorizingOfficer } from '../authorizing-officer.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { AuthorizingOfficerService } from '../service/authorizing-officer.service';
import { AuthorizingOfficerDeleteDialogComponent } from '../delete/authorizing-officer-delete-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';

import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-authorizing-officer',
  templateUrl: './authorizing-officer.component.html',
})
export class AuthorizingOfficerComponent implements OnInit {
  authorizingOfficers?: any[];
  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  page?: number;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  selected: any[] = [];

  constructor(
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
    protected dataUtils: DataUtils,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.profileService.findAllAuthorizingOfficers().subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.authorizingOfficers = res.body ?? [];
        console.log(this.authorizingOfficers);
      },
      error: e => console.error(e),
    });
  }

  trackId(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }

  delete(authorizingOfficer: IAuthorizingOfficer): void {
    const modalRef = this.modalService.open(AuthorizingOfficerDeleteDialogComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.authorizingOfficer = authorizingOfficer;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }
  loadPage(): void {
    console.log(this.authorizingOfficers);
  }
  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }
}
