import { Component, ElementRef, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { TranslateService } from '@ngx-translate/core';
import { SessionStorageService } from 'ngx-webstorage';

import { VERSION } from 'app/app.constants';
import { LANGUAGES } from 'app/config/language.constants';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { LoginService } from 'app/login/login.service';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { EntityNavbarItems } from 'app/entities/entity-navbar-items';
import { Gender } from 'app/entities/enumerations/gender.model';
import { IParent } from 'app/entities/parent/parent.model';
import { IChild } from 'app/entities/child/child.model';
import { AuthorizingOfficer, IAuthorizingOfficer } from '../authorizing-officer.model';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { ICity } from 'app/entities/city/city.model';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventManager } from '@angular/platform-browser';
import { CityService } from 'app/entities/city/service/city.service';
import { FormBuilder, Validators } from '@angular/forms';
import { AuthorizingOfficerService } from '../service/authorizing-officer.service';
import { IProfile, Profile } from 'app/entities/profile/profile.model';
import { HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'jhi-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css'],
})
export class AddComponent implements OnInit {
  inProduction?: boolean;
  isNavbarCollapsed = true;
  languages = LANGUAGES;
  openAPIEnabled?: boolean;
  version = '';
  account: Account | null = null;
  c: any;
  entitiesNavbarItems: any[] = [];
  isSaving = false;
  genderValues = Object.keys(Gender);

  parentsCollection: IParent[] = [];
  childrenCollection: IChild[] = [];
  authorizingOfficersCollection: IAuthorizingOfficer[] = [];
  tutorsCollection: ITutor[] = [];
  citiesSharedCollection: ICity[] = [];

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

    birthPlace: [null, Validators.required],
    placeOfResidence: [null, Validators.required],
  });

  AuthorizingOfficerForm = this.fb.group({
    id: [],
    abbreviation: [null, [Validators.required]],
    activity: [],
    manager: [],
    managerCin: [],
  });
  constructor(
    private loginService: LoginService,
    private translateService: TranslateService,
    private sessionStorageService: SessionStorageService,
    private accountService: AccountService,
    private router: Router,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected profileService: ProfileService,
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected cityService: CityService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {
    if (VERSION) {
      this.version = VERSION.toLowerCase().startsWith('v') ? VERSION : `v${VERSION}`;
    }
  }
  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.loadRelationshipsOptions();
    });
    this.loadRelationshipsOptions();
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const authorizing = this.createFromForm();
    this.subscribeToSaveResponse(this.authorizingOfficerService.add(authorizing));
    console.log(authorizing);
  }
  /*
  this.isSaving = true;
    const authorizingOfficer = this.createFromForm();
    if (authorizingOfficer.id !== undefined) {
      this.subscribeToSaveResponse(this.authorizingOfficerService.update(authorizingOfficer));
    } else {
      this.subscribeToSaveResponse(this.authorizingOfficerService.create(authorizingOfficer));
    }
*/
  trackCityById(_index: number, item: ICity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProfile>>): void {
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

  protected updateForm(profile: IProfile): void {
    this.editForm.patchValue({
      firstName: profile.firstName,
      lastName: profile.lastName,
      firstNameArabic: profile.firstNameArabic,
      lastNameArabic: profile.lastNameArabic,
      gender: profile.gender,
      dateOfBirth: profile.dateOfBirth,
      cin: profile.cin,
      urlPhoto: profile.urlPhoto,
      urlPhotoContentType: profile.urlPhotoContentType,
      address: profile.address,
      phone: profile.phone,
      email: profile.email,
      urlCinAttached: profile.urlCinAttached,
      urlCinAttachedContentType: profile.urlCinAttachedContentType,
      birthPlace: profile.birthPlace,
      placeOfResidence: profile.placeOfResidence,
    });

    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(
      this.citiesSharedCollection,
      profile.birthPlace,
      profile.placeOfResidence
    );
    console.log(this.citiesSharedCollection);
  }

  protected loadRelationshipsOptions(): void {
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

  protected createFromAuthorizingOfficerProfileForm(): IProfile {
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

  protected createFromForm(): IAuthorizingOfficer {
    return {
      ...new AuthorizingOfficer(),
      abbreviation: this.AuthorizingOfficerForm.get(['abbreviation'])!.value,
      activity: this.AuthorizingOfficerForm.get(['activity'])!.value,
      manager: this.AuthorizingOfficerForm.get(['manager'])!.value,
      managerCin: this.AuthorizingOfficerForm.get(['managerCin'])!.value,
      profile: this.createFromAuthorizingOfficerProfileForm(),
    };
  }
}
