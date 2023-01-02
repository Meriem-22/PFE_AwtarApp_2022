import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IFamily } from '../family.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { FamilyService } from '../service/family.service';
import { FamilyDeleteDialogComponent } from '../delete/family-delete-dialog.component';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { LoginService } from 'app/login/login.service';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';
import { AccountService } from 'app/core/auth/account.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';

import { VERSION } from 'app/app.constants';

@Component({
  selector: 'jhi-family',
  templateUrl: './family.component.html',
})
export class FamilyComponent implements OnInit {
  families?: IFamily[];
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
  selectedFamilys: any[] = [];

  constructor(
    protected familyService: FamilyService,
    protected activatedRoute: ActivatedRoute,
    protected modalService: NgbModal,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    this.familyService.findFamily().subscribe({
      next: (res: HttpResponse<IFamily[]>) => {
        this.isLoading = true;
        this.families = res.body ?? [];
        console.log(this.families);
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  trackId(_index: number, item: IFamily): number {
    return item.id!;
  }

  delete(family: IFamily): void {
    const modalRef = this.modalService.open(FamilyDeleteDialogComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.family = family;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  loadPage(): void {
    console.log(this.families);
  }
}
