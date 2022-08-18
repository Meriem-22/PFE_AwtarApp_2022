import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { IEstablishment } from '../establishment.model';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { HttpResponse } from '@angular/common/http';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-establishment-detail',
  templateUrl: './establishment-detail.component.html',
})
export class EstablishmentDetailComponent implements OnInit {
  establishment: IEstablishment | null = null;
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  Ordonnateur!: HttpResponse<IProfile>;
  Tuteur!: HttpResponse<IProfile>;

  page = 1;
  count = 0;
  tableSize = 4;
  tableSizes: any = [4, 8, 16, 20];
  donationList?: any[];
  benef!: IBeneficiary;
  df = 0;
  t = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    protected donationDetailsService: DonationDetailsService,
    protected beneficiaryService: BeneficiaryService,
    protected dataUtils: DataUtils
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ establishment }) => {
      this.establishment = establishment;
    });
    this.profileService.findProfile(this.establishment!.authorizingOfficer!.id!).subscribe({
      next: res => {
        this.Ordonnateur = res;
      },
      error: e => console.error(e),
    });

    this.beneficiaryService.find(this.establishment!.id!).subscribe({
      next: (res: HttpResponse<IBeneficiary>) => {
        this.benef = res.body!;
      },
      error: e => console.error(e),
    });

    this.profileService.findProfile(this.establishment!.tutor!.id!).subscribe({
      next: res => {
        this.Tuteur = res;
      },
      error: e => console.error(e),
    });

    this.donationDetailsService.getAllDonationsIssuedOfFamily(this.establishment!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationList = res.body ?? [];
        for (this.t = 0; this.t < this.donationList.length; this.t++) {
          if (this.donationList[this.t].isValidated === true) {
            this.df = this.df + 1;
          }
        }
      },
      error: e => console.error(e),
    });

    this.DonationList();
  }

  DonationList(): void {
    this.donationDetailsService.getAllDonationsIssuedOfFamily(this.establishment!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationList = res.body ?? [];
        console.log(this.donationList);
      },
      error: e => console.error(e),
    });
  }

  onTableDataChange(event: any): void {
    this.page = event;
    this.DonationList();
  }

  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.DonationList();
  }

  previousState(): void {
    window.history.back();
  }

  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  login(): void {
    this.router.navigate(['/login']);
  }

  logout(): void {
    this.collapseNavbar();
    this.loginService.logout();
    this.router.navigate(['']);
  }

  toggleNavbar(): void {
    this.isNavbarCollapsed = !this.isNavbarCollapsed;
  }

  trackId(_index: number, item: IEstablishment): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }
}
