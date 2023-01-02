import { Component, OnInit } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import { combineLatest } from 'rxjs';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IEstablishment } from '../establishment.model';

import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { EstablishmentService } from '../service/establishment.service';
import { EstablishmentDeleteDialogComponent } from '../delete/establishment-delete-dialog.component';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';

@Component({
  selector: 'jhi-establishment',
  templateUrl: './establishment.component.html',
})
export class EstablishmentComponent implements OnInit {
  establishments?: IEstablishment[];
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
  selectedEstablishments: any[] = [];

  constructor(
    protected establishmentService: EstablishmentService,
    protected activatedRoute: ActivatedRoute,
    protected router: Router,
    protected modalService: NgbModal,
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
    this.establishmentService.findEstablishments().subscribe({
      next: (res: HttpResponse<IEstablishment[]>) => {
        this.isLoading = true;
        this.establishments = res.body ?? [];
        console.log(this.establishments);
      },
      error: () => {
        this.isLoading = false;
      },
    });
  }

  trackId(_index: number, item: IEstablishment): number {
    return item.id!;
  }

  delete(establishment: IEstablishment): void {
    const modalRef = this.modalService.open(EstablishmentDeleteDialogComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.establishment = establishment;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        this.loadPage();
      }
    });
  }

  loadPage(): void {
    console.log('fnghn');
  }
}
