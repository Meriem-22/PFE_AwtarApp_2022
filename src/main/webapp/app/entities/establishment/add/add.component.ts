import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { IEstablishmentType } from 'app/entities/establishment-type/establishment-type.model';
import { ICity } from 'app/entities/city/city.model';
import { FormBuilder, Validators } from '@angular/forms';
import { EstablishmentService } from '../service/establishment.service';
import { EstablishmentTypeService } from 'app/entities/establishment-type/service/establishment-type.service';
import { CityService } from 'app/entities/city/service/city.service';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { TutorService } from 'app/entities/tutor/service/tutor.service';
import { HttpResponse } from '@angular/common/http';
import { map, Observable, finalize } from 'rxjs';
import { Establishment, IEstablishment } from '../establishment.model';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

@Component({
  selector: 'jhi-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css'],
})
export class AddComponent implements OnInit {
  isSaving = false;

  establishmentTypesSharedCollection: IEstablishmentType[] = [];
  citiesSharedCollection: ICity[] = [];
  authorizingOfficersSharedCollection: IProfile[] = [];
  tutorsSharedCollection: IProfile[] = [];

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    activity: [],
    manager: [],
    managerCin: [],
    contact: [],
    adress: [],
    cp: [],
    region: [],
    phone: [],
    fax: [],
    email: [],
    site: [],
    remark: [],
    establishmentType: [null, Validators.required],
    city: [null, Validators.required],
    authorizingOfficer: [],
    tutor: [],
  });

  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  c?: any;

  constructor(
    protected establishmentService: EstablishmentService,
    protected establishmentTypeService: EstablishmentTypeService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private profileService: ProfileService,
    private router: Router,
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected tutorService: TutorService
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }
  ngOnInit(): void {
    this.loadRelationshipsOptions();
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const establishment = this.createFromForm();
    this.subscribeToSaveResponse(this.establishmentService.add(establishment));
    console.log(establishment);
  }
  changeLanguage(languageKey: string): void {
    this.sessionStorageService.store('locale', languageKey);
    this.translateService.use(languageKey);
  }

  collapseNavbar(): void {
    this.isNavbarCollapsed = true;
  }

  trackEstablishmentTypeById(_index: number, item: IEstablishmentType): number {
    return item.id!;
  }

  trackCityById(_index: number, item: ICity): number {
    return item.id!;
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

  trackAuthorizingOfficerById(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }

  trackTutorById(_index: number, item: ITutor): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstablishment>>): void {
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

  protected loadRelationshipsOptions(): void {
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

    this.establishmentTypeService
      .query()
      .pipe(map((res: HttpResponse<IEstablishmentType[]>) => res.body ?? []))
      .pipe(
        map((establishmentTypes: IEstablishmentType[]) =>
          this.establishmentTypeService.addEstablishmentTypeToCollectionIfMissing(
            establishmentTypes,
            this.editForm.get('establishmentType')!.value
          )
        )
      )
      .subscribe((establishmentTypes: IEstablishmentType[]) => (this.establishmentTypesSharedCollection = establishmentTypes));

    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(map((cities: ICity[]) => this.cityService.addCityToCollectionIfMissing(cities, this.editForm.get('city')!.value)))
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));
  }

  protected createFromForm(): IEstablishment {
    return {
      ...new Establishment(),
      name: this.editForm.get(['name'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      manager: this.editForm.get(['manager'])!.value,
      managerCin: this.editForm.get(['managerCin'])!.value,
      contact: this.editForm.get(['contact'])!.value,
      adress: this.editForm.get(['adress'])!.value,
      cp: this.editForm.get(['cp'])!.value,
      region: this.editForm.get(['region'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      fax: this.editForm.get(['fax'])!.value,
      email: this.editForm.get(['email'])!.value,
      site: this.editForm.get(['site'])!.value,
      remark: this.editForm.get(['remark'])!.value,
      establishmentType: this.editForm.get(['establishmentType'])!.value,
      city: this.editForm.get(['city'])!.value,
      authorizingOfficer: this.editForm.get(['authorizingOfficer'])!.value,
      tutor: this.editForm.get(['tutor'])!.value,
    };
  }
}
