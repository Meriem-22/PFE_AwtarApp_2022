import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataUtils } from 'app/core/util/data-util.service';
import { IProfile, Profile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { IFamilyAllDetails } from '../family.model';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { ParentService } from 'app/entities/parent/service/parent.service';
import { IParentAllDetails } from 'app/entities/parent/parent.model';
import { DonationDetailsService } from 'app/entities/donation-details/service/donation-details.service';
import { ASC, DESC, ITEMS_PER_PAGE, SORT } from 'app/config/pagination.constants';
import { combineLatest, map } from 'rxjs';
import { FormBuilder } from '@angular/forms';
import { IChild } from 'app/entities/child/child.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { RemoveAuthorizingOfficerComponent } from 'app/entities/beneficiary/remove-authorizing-officer/remove-authorizing-officer.component';
import { RemoveTutorComponent } from 'app/entities/beneficiary/remove-tutor/remove-tutor.component';

@Component({
  selector: 'jhi-family-detail',
  templateUrl: './family-detail.component.html',
})
export class FamilyDetailComponent implements OnInit {
  editForm = this.fb.group({
    child: [],
  });
  Posts: any;

  isLoading = false;
  totalItems = 0;
  itemsPerPage = ITEMS_PER_PAGE;
  predicate!: string;
  ascending!: boolean;
  ngbPaginationPage = 1;
  family?: IFamilyAllDetails | null = null;
  parents?: any[] = [];
  children?: IProfile[] = [];
  t = 0;
  parent?: HttpResponse<IParentAllDetails>;
  donation?: any[];

  benef!: IBeneficiary;
  selectedDon!: any;

  childrenCollection?: IProfile[] = [];
  childProfile?: HttpResponse<IProfile>;
  childSelected?: number;
  selectedChild?: IProfile | null;

  text?: any;
  display = false;

  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  donationList?: any[];
  donationListChild?: any[];
  donationChild = 0;
  Ordonnateur!: HttpResponse<IProfile>;
  Tuteur!: HttpResponse<IProfile>;
  df = 0;
  beneficiary!: HttpResponse<IBeneficiary>;
  familyId?: number;
  o = 0;
  dc = 0;
  i = 0;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected dataUtils: DataUtils,
    protected router: Router,
    protected modalService: NgbModal,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private donationDetailsService: DonationDetailsService,
    private parentService: ParentService,

    protected fb: FormBuilder,
    protected beneficiaryService: BeneficiaryService
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.family = family;
      this.familyId = family.id;
      this.accountService.getAuthenticationState().subscribe(account => {
        this.account = account;
      });

      this.beneficiaryService.find(family.id).subscribe({
        next: (res: HttpResponse<IBeneficiary>) => {
          this.benef = res.body!;
        },
        error: e => console.error(e),
      });

      this.profileService.getAllParentsProfile(family.id).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.parents = res.body ?? [];
          for (this.t = 0; this.t < this.parents.length; this.t++) {
            this.o = this.o + +this.parents[this.t].annualRevenue;
          }
        },
        error: e => console.error(e),
      });

      this.profileService.getAllChildrenProfile(family.id).subscribe({
        next: (res: HttpResponse<IProfile[]>) => {
          this.children = res.body ?? [];
          console.log(this.children);

          for (this.t = 0; this.t < this.children.length; this.t++) {
            this.donationDetailsService.getAllDonationsIssuedOfFamily(this.children[this.t].child!.id!).subscribe({
              next: (resc: HttpResponse<any[]>) => {
                this.donationChild = this.donationChild + resc.body!.length;
              },
              error: e => console.error(e),
            });
          }
        },
        error: e => console.error(e),
      });

      this.donationDetailsService.getAllDonationsIssuedOfFamily(family.id).subscribe({
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

      this.profileService.findProfile(family.authorizingOfficer.id).subscribe({
        next: res => {
          this.Ordonnateur = res;
        },
        error: e => console.error(e),
      });

      this.profileService.findProfile(family.tutor.id).subscribe({
        next: res => {
          this.Tuteur = res;
        },
        error: e => console.error(e),
      });
    });
    this.childSelected = 0;

    this.loadRelationshipsOptions();
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    this.DonationList();
  }

  DonationList(): void {
    this.donationDetailsService.getAllDonationsIssuedOfFamily(this.family!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.donationList = res.body ?? [];
        console.log(this.donationList);
      },
      error: e => console.error(e),
    });
  }

  callFunction(): void {
    this.display = true;
    if (this.selectedChild != null) {
      this.donationDetailsService.getAllDonationsIssuedOfFamily(this.selectedChild.child!.id!).subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.donationListChild = res.body ?? [];
          console.log(this.donationListChild);
        },
        error: e => console.error(e),
      });
    }
  }

  previousState(): void {
    window.history.back();
  }

  trackId(_index: number, item: IProfile): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    return this.dataUtils.openFile(base64String, contentType);
  }

  addParent(): void {
    this.router.navigateByUrl('/parent/add', { state: { parentFamily: this.family } });
  }

  addChild(): void {
    this.router.navigateByUrl('/child/add', { state: { childFamily: this.family } });
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

  trackChildById(_index: number, profile: IProfile): number {
    return profile.id!;
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

  protected loadRelationshipsOptions(): void {
    this.profileService
      .getAllChildrenProfile(this.familyId!)
      .pipe(map((res: HttpResponse<IProfile[]>) => res.body ?? []))
      .pipe(map((children: IProfile[]) => this.profileService.addProfileToCollectionIfMissing(children, this.editForm.get('child')!.value)))
      .subscribe((children: IProfile[]) => (this.childrenCollection = children));
  }

  protected createFromForm(): IProfile {
    return {
      ...new Profile(),
      child: this.editForm.get(['child'])!.value,
    };
  }
}
