import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IFamily, Family, IFamilyAllDetails } from '../family.model';
import { FamilyService } from '../service/family.service';
import { Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';

@Component({
  selector: 'jhi-family-update',
  templateUrl: './family-update.component.html',
})
export class FamilyUpdateComponent implements OnInit {
  isSaving = false;
  authorizingOfficersSharedCollection: IProfile[] = [];
  tutorsSharedCollection: IProfile[] = [];
  familyDetails?: any;

  editForm = this.fb.group({
    id: [],
    familyName: [null, [Validators.required]],
    dwelling: [null, [Validators.required]],
    area: [],
    authorizingOfficer: [],
    tutor: [],
    notebookOfPoverty: [],
    notebookOfHandicap: [],
    archivated: [],
    Oid: [],
    Tid: [],
  });

  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  Ordonnateur!: HttpResponse<IProfile>;
  Tuteur!: HttpResponse<IProfile>;
  op?: IProfile | null;
  ot?: IProfile | null;

  constructor(
    protected familyService: FamilyService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    protected beneficiaryService: BeneficiaryService,
    private router: Router
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.familyDetails = family;
      this.profileService.findProfile(this.familyDetails.authorizingOfficer.id).subscribe({
        next: res => {
          this.op = res.body;
        },
        error: e => console.error(e),
      });

      this.profileService.findProfile(this.familyDetails.tutor.id).subscribe({
        next: res => {
          this.ot = res.body;
        },
        error: e => console.error(e),
      });
    });

    this.updateForm(this.familyDetails);

    this.loadRelationshipsOptionsFamily();
  }

  previousState(): void {
    window.location.reload();
  }

  save(): void {
    this.isSaving = true;
    const family = this.createFromForm();
    if (family.id !== undefined) {
      this.subscribeToSaveResponse(this.familyService.update(family));
    } else {
      this.subscribeToSaveResponse(this.familyService.create(family));
    }
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

  trackAuthorizingOfficerById(_index: number, item: IProfile): number {
    return item.id!;
  }

  trackTutorById(_index: number, item: IProfile): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamily>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(family: IFamily): void {
    this.editForm.patchValue({
      id: family.id,
      familyName: family.familyName,
      dwelling: family.dwelling,
      area: family.area,
      authorizingOfficer: this.op,
      tutor: this.ot,
      notebookOfPoverty: family.notebookOfPoverty,
      notebookOfHandicap: family.notebookOfHandicap,
      archivated: family.archivated,
    });
  }

  protected createFromForm(): IFamily {
    return {
      ...new Family(),
      id: this.editForm.get(['id'])!.value,
      familyName: this.editForm.get(['familyName'])!.value,
      dwelling: this.editForm.get(['dwelling'])!.value,
      area: this.editForm.get(['area'])!.value,
      notebookOfPoverty: this.editForm.get(['notebookOfPoverty'])!.value,
      notebookOfHandicap: this.editForm.get(['notebookOfHandicap'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }

  protected loadRelationshipsOptionsFamily(): void {
    this.profileService.findAuthorizingOfficerProfiles().subscribe({
      next: (res: HttpResponse<IProfile[]>) => {
        this.authorizingOfficersSharedCollection = res.body ?? [];
        console.log(this.authorizingOfficersSharedCollection);
      },
      error: () => {
        console.log(this.authorizingOfficersSharedCollection);
      },
    });

    this.profileService.findTutorsProfiles().subscribe({
      next: (res: HttpResponse<IProfile[]>) => {
        this.tutorsSharedCollection = res.body ?? [];
        console.log(this.tutorsSharedCollection);
      },
      error: () => {
        console.log(this.tutorsSharedCollection);
      },
    });
  }
}
