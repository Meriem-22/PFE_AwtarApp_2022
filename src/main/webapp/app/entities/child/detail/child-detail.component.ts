import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';

import { IChild } from '../child.model';
import { HttpResponse } from '@angular/common/http';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { RemoveAuthorizingOfficerComponent } from 'app/entities/beneficiary/remove-authorizing-officer/remove-authorizing-officer.component';
import { RemoveTutorComponent } from 'app/entities/beneficiary/remove-tutor/remove-tutor.component';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

@Component({
  selector: 'jhi-child-detail',
  templateUrl: './child-detail.component.html',
})
export class ChildDetailComponent implements OnInit {
  child: IChild | null = null;
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  profile!: IProfile;
  parents?: any[] = [];
  Otherchild?: IProfile[] = [];
  children?: IProfile[] = [];
  restOfChildren?: IProfile[] = [];
  selectedDon!: any;

  t = 0;
  r = 0;
  donationListChild?: any[];
  df = 0;
  Ordonnateur!: HttpResponse<IProfile>;
  Tuteur!: HttpResponse<IProfile>;
  Beneficiary!: IBeneficiary;
  x!: number;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    protected dataUtils: DataUtils,
    private donationDetailsService: DonationDetailsService,
    private beneficiaryService: BeneficiaryService,
    protected modalService: NgbModal
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }
  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ child }) => {
      this.child = child;
      this.x = child.id;
    });

    this.profileService.findProfile(this.child!.id!).subscribe({
      next: (res: HttpResponse<IProfile>) => {
        this.profile = res.body!;
        console.log(this.profile);
      },
      error: e => console.error(e),
    });

    this.donationDetailsService.getAllDonationsIssuedOfFamily(this.child!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationListChild = res.body ?? [];
        for (this.t = 0; this.t < this.donationListChild.length; this.t++) {
          if (this.donationListChild[this.t].isValidated === true) {
            this.df = this.df + 1;
          }
        }
      },
      error: e => console.error(e),
    });

    if (this.child!.family) {
      this.profileService.getAllParentsProfile(this.child!.family.id!).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.parents = res.body ?? [];
        },
        error: e => console.error(e),
      });

      this.profileService.getAllChildrenProfile(this.child!.family.id!).subscribe({
        next: (res: HttpResponse<IProfile[]>) => {
          this.children = res.body ?? [];
          for (this.t = 0; this.t < this.children.length; this.t++) {
            if (this.children[this.t].child!.id! !== this.child!.id!) {
              this.restOfChildren![this.r] = this.children[this.t];
              this.r++;
            }
          }
          console.log(this.restOfChildren);
        },
        error: e => console.error(e),
      });
    }

    this.beneficiaryService.find(this.child!.id!).subscribe({
      next: (res: HttpResponse<IBeneficiary>) => {
        this.Beneficiary = res.body!;
        console.log(this.Beneficiary);

        this.profileService.findProfile(this.Beneficiary.tutor!.id!).subscribe({
          next: res3 => {
            this.Tuteur = res3;
            console.log(this.Tuteur);
          },
          error: e => console.error(e),
        });
        this.profileService.findProfile(this.Beneficiary.authorizingOfficer!.id!).subscribe({
          next: res2 => {
            this.Ordonnateur = res2;
            console.log(this.Ordonnateur);
          },
          error: e => console.error(e),
        });
      },
      error: e => console.error(e),
    });

    this.DonationList();
  }

  DonationList(): void {
    this.donationDetailsService.getAllDonationsIssuedOfFamily(this.child!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationListChild = res.body ?? [];
        console.log(this.donationListChild);
      },
      error: e => console.error(e),
    });
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

  trackId(_index: number, item: IProfile): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  removeAuthorizingOfficer(beneficiary: IBeneficiary): void {
    const modalRef = this.modalService.open(RemoveAuthorizingOfficerComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.beneficiary = beneficiary;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'deleted') {
        //this.loadPage();
      }
    });
  }

  removeTutor(beneficiary: IBeneficiary): void {
    const modalRef = this.modalService.open(RemoveTutorComponent, { size: 'lg', backdrop: false, keyboard: false });
    modalRef.componentInstance.beneficiary = beneficiary;
    // unsubscribe not needed because closed completes on modal close
    modalRef.closed.subscribe(reason => {
      if (reason === 'removed') {
        //this.loadPage();
      }
    });
  }
}
