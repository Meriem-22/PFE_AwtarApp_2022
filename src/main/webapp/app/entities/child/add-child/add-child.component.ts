import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute, Router } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { IFamily } from 'app/entities/family/family.model';
import { IProfile, Profile } from 'app/entities/profile/profile.model';
import { finalize, map, Observable } from 'rxjs';
import { ChildAllDetails, IChildAllDetails } from '../child.model';
import { ChildService } from '../service/child.service';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

@Component({
  selector: 'jhi-add-child',
  templateUrl: './add-child.component.html',
  styleUrls: ['./add-child.component.scss'],
})
export class AddChildComponent implements OnInit {
  isSaving = false;

  genderValues = Object.keys(Gender);
  ChildFamily: IFamily | undefined;
  citiesSharedCollection: ICity[] = [];

  maritalStatusValues = Object.keys(MaritalStatus);
  ChildDetails!: FormGroup;
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  entitiesNavbarItems: any[] = [];
  authorizingOfficersSharedCollection: IProfile[] = [];
  tutorsSharedCollection: IProfile[] = [];

  editForm = this.fb.group({
    id: [],
    firstName: [null, [Validators.required]],
    lastName: [null, [Validators.required]],
    firstNameArabic: [],
    lastNameArabic: [],
    gender: [null, [Validators.required]],
    dateOfBirth: [null, [Validators.required]],
    cin: [],
    urlPhoto: [],
    urlPhotoContentType: [],
    address: [],
    phone: [],
    email: [],
    urlCinAttached: [],
    urlCinAttachedContentType: [],
    archivated: [],
    parent: [],
    child: [],
    authorizingOfficer: [],
    tutor: [],
    birthPlace: [null, Validators.required],
    placeOfResidence: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected profileService: ProfileService,
    protected childService: ChildService,
    protected cityService: CityService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected formBuilder: FormBuilder,
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private router: Router,
    protected fb: FormBuilder
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }
  ngOnInit(): void {
    this.loadRelationshipsOptionsChild();
    this.ChildFamily = history.state.childFamily;

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

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({});
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.editForm.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  trackAuthorizingOfficerById(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }

  trackTutorById(_index: number, item: ITutor): number {
    return item.id!;
  }

  save(): void {
    this.isSaving = true;

    const child = this.createFromFormChild();
    this.subscribeToSaveResponse(this.childService.addchild(child));
    console.log(child);
  }

  previousState(): void {
    window.history.back();
  }

  trackCityById(_index: number, item: ICity): number {
    return item.id!;
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

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChildAllDetails>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
    console.log(result);
  }

  protected onSaveSuccess(): void {
    this.previousState();
    // this.router.navigate(['family',  , 'view']
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected loadRelationshipsOptionsChild(): void {
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
    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(
        map((cities: ICity[]) =>
          this.cityService.addCityToCollectionIfMissing(
            cities,
            this.editForm.get('birthPlace')!.value,
            this.editForm.get('placeOfResidence')!.value
          )
        )
      )
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));
  }

  protected createFromFormChild(): IChildAllDetails {
    return {
      ...new ChildAllDetails(),
      family: this.ChildFamily,
      authorizingOfficer: this.editForm.get(['authorizingOfficer'])!.value,
      tutor: this.editForm.get(['tutor'])!.value,
      profile: this.createFromFormChildProfile(),
    };
  }

  protected createFromFormChildProfile(): IProfile {
    return {
      ...new Profile(),
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      firstNameArabic: this.editForm.get(['firstNameArabic'])!.value,
      lastNameArabic: this.editForm.get(['lastNameArabic'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      urlPhotoContentType: this.editForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.editForm.get(['urlPhoto'])!.value,
      address: this.editForm.get(['address'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      urlCinAttachedContentType: this.editForm.get(['urlCinAttachedContentType'])!.value,
      urlCinAttached: this.editForm.get(['urlCinAttached'])!.value,
      birthPlace: this.editForm.get(['birthPlace'])!.value,
      placeOfResidence: this.editForm.get(['placeOfResidence'])!.value,
    };
  }
}
