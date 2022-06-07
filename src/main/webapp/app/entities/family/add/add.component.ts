import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef } from '@angular/core';
import { FormGroup, Validators, FormBuilder } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { Beneficiary, IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { ChildAllDetails, IChildAllDetails } from 'app/entities/child/child.model';
import { ChildService } from 'app/entities/child/service/child.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { Beneficiaries } from 'app/entities/enumerations/beneficiaries.model';
import { Gender } from 'app/entities/enumerations/gender.model';
import { MaritalStatus } from 'app/entities/enumerations/marital-status.model';
import { IParentAllDetails, ParentAllDetails } from 'app/entities/parent/parent.model';
import { ParentService } from 'app/entities/parent/service/parent.service';
import { IProfile, Profile } from 'app/entities/profile/profile.model';
import { TutorService } from 'app/entities/tutor/service/tutor.service';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { finalize, map, Observable } from 'rxjs';
import { FamilyAllDetails, IFamilyAllDetails } from '../family.model';
import { FamilyService } from '../service/family.service';

@Component({
  selector: 'jhi-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css'],
})
export class AddComponent {
  isSavingParent = false;

  authorizingOfficersSharedCollection: IAuthorizingOfficer[] = [];
  tutorsSharedCollection: ITutor[] = [];
  ParentsCollection: IParentAllDetails[] = [];
  ChildrenCollection: IChildAllDetails[] = [];
  isSaving = false;

  genderValues = Object.keys(Gender);

  citiesSharedCollection: ICity[] = [];

  maritalStatusValues = Object.keys(MaritalStatus);
  FamilyDetails!: FormGroup;
  ParentDetails!: FormGroup;
  ChildDetails!: FormGroup;
  family_step = false;
  parent_step = false;
  child_step = false;
  step = 1;
  i = 0;
  c = 0;

  constructor(
    protected beneficiaryService: BeneficiaryService,
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected tutorService: TutorService,
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected profileService: ProfileService,
    protected parentService: ParentService,
    protected childService: ChildService,
    protected cityService: CityService,
    protected elementRef: ElementRef,
    protected dataUtilsChild: DataUtils,
    protected eventManagerChild: EventManager,
    protected elementRefChild: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected familyService: FamilyService,
    protected formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.FamilyDetails = this.formBuilder.group({
      familyName: [null, [Validators.required]],
      dwelling: [null, [Validators.required]],
      area: [null, [Validators.required]],
      authorizingOfficer: [],
      tutor: [],
      notebookOfPoverty: [],
      notebookOfHandicap: [],
      archivated: [],
    });

    this.ParentDetails = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      firstNameArabic: [],
      lastNameArabic: [],
      gender: [null, [Validators.required]],
      dateOfBirth: [null, [Validators.required]],
      cin: [],
      urlCinAttached: [],
      urlCinAttachedContentType: [],
      address: [],
      phone: [],
      email: [],
      urlPhoto: [],
      urlPhotoContentType: [],
      archivated: [],
      parent: [],
      child: [],
      birthPlace: [null, Validators.required],
      placeOfResidence: [null, Validators.required],
      annualRevenue: [null, [Validators.required]],
      cnss: [],
      maritalStatus: [null, [Validators.required]],
      occupation: [null, [Validators.required]],
      deceased: [],
      dateOfDeath: [],
      familyHead: [],
    });

    this.ChildDetails = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      firstNameArabic: [],
      lastNameArabic: [],
      gender: [null, [Validators.required]],
      dateOfBirth: [null, [Validators.required]],
      cin: [],
      urlCinAttached: [],
      urlCinAttachedContentType: [],
      address: [],
      phone: [],
      email: [],
      urlPhoto: [],
      urlPhotoContentType: [],
      archivated: [],
      parent: [],
      child: [],
      birthPlace: [null, Validators.required],
      placeOfResidence: [null, Validators.required],
    });

    this.loadRelationshipsOptionsFamily();
    this.loadRelationshipsOptionsParent();
    this.loadRelationshipsOptionsChild();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    if (this.step === 2) {
      this.dataUtils.openFile(base64String, contentType);
    } else if (this.step === 3) {
      this.dataUtilsChild.openFile(base64String, contentType);
    }
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.ParentDetails, field, isImage).subscribe({});
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.ParentDetails.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  byteSizeChild(base64String: string): string {
    return this.dataUtilsChild.byteSize(base64String);
  }

  setFileDataChild(event: Event, field: string, isImage: boolean): void {
    this.dataUtilsChild.loadFileToForm(event, this.ChildDetails, field, isImage).subscribe({});
  }

  clearInputImageChild(field: string, fieldContentType: string, idInput: string): void {
    this.ChildDetails.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRefChild.nativeElement.querySelector('#' + idInput)) {
      this.elementRefChild.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  next(): void {
    if (this.step === 1) {
      this.family_step = true;
      if (this.FamilyDetails.invalid) {
        return;
      }
      this.step++;
    } else if (this.step === 2) {
      this.parent_step = true;
      this.step++;
    }
  }

  add(): void {
    if (this.step === 2) {
      this.parent_step = true;
      if (this.ParentDetails.invalid) {
        return;
      }
      this.ParentsCollection.push(this.createFromFormParent());

      this.ParentDetails = this.formBuilder.group({
        firstName: [null, [Validators.required]],
        lastName: [null, [Validators.required]],
        firstNameArabic: [],
        lastNameArabic: [],
        gender: [null, [Validators.required]],
        dateOfBirth: [null, [Validators.required]],
        cin: [],
        urlCinAttached: [],
        urlCinAttachedContentType: [],
        address: [],
        phone: [],
        email: [],
        urlPhoto: [],
        urlPhotoContentType: [],
        archivated: [],
        parent: [],
        child: [],
        birthPlace: [null, Validators.required],
        placeOfResidence: [null, Validators.required],
        annualRevenue: [null, [Validators.required]],
        cnss: [],
        maritalStatus: [null, [Validators.required]],
        occupation: [null, [Validators.required]],
        deceased: [],
        dateOfDeath: [],
        familyHead: [],
      });
    } else if (this.step === 3) {
      this.parent_step = true;
      if (this.ChildDetails.invalid) {
        return;
      }
      this.ChildrenCollection.push(this.createFromFormChild());

      this.ChildDetails = this.formBuilder.group({
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
    }
  }

  previous(): void {
    this.step--;

    if (this.step === 1) {
      this.parent_step = false;
    }
    if (this.step === 2) {
      this.child_step = false;
    }
  }

  submit(): void {
    this.isSaving = true;
    if (this.step === 3) {
      this.child_step = true;
      const family = this.createFromFormFamily();
      this.subscribeToSaveResponse(this.familyService.createFromFormFamily(family));
      console.log(family);
    }
  }

  previousState(): void {
    window.history.back();
  }

  trackAuthorizingOfficerById(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }

  trackTutorById(_index: number, item: ITutor): number {
    return item.id!;
  }

  trackCityById(_index: number, item: ICity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFamilyAllDetails>>): void {
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

  protected loadRelationshipsOptionsFamily(): void {
    this.authorizingOfficerService
      .query()
      .pipe(map((res: HttpResponse<IAuthorizingOfficer[]>) => res.body ?? []))
      .pipe(
        map((authorizingOfficers: IAuthorizingOfficer[]) =>
          this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
            authorizingOfficers,
            this.FamilyDetails.get('authorizingOfficer')!.value
          )
        )
      )
      .subscribe((authorizingOfficers: IAuthorizingOfficer[]) => (this.authorizingOfficersSharedCollection = authorizingOfficers));

    this.tutorService
      .query()
      .pipe(map((res: HttpResponse<ITutor[]>) => res.body ?? []))
      .pipe(map((tutors: ITutor[]) => this.tutorService.addTutorToCollectionIfMissing(tutors, this.FamilyDetails.get('tutor')!.value)))
      .subscribe((tutors: ITutor[]) => (this.tutorsSharedCollection = tutors));
  }

  protected loadRelationshipsOptionsParent(): void {
    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(
        map((cities: ICity[]) =>
          this.cityService.addCityToCollectionIfMissing(
            cities,
            this.ParentDetails.get('birthPlace')!.value,
            this.ParentDetails.get('placeOfResidence')!.value
          )
        )
      )
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));
  }

  protected loadRelationshipsOptionsChild(): void {
    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(
        map((cities: ICity[]) =>
          this.cityService.addCityToCollectionIfMissing(
            cities,
            this.ChildDetails.get('birthPlace')!.value,
            this.ChildDetails.get('placeOfResidence')!.value
          )
        )
      )
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));
  }

  protected createFromFormFamily(): IFamilyAllDetails {
    return {
      ...new FamilyAllDetails(),
      familyName: this.FamilyDetails.get(['familyName'])!.value,
      dwelling: this.FamilyDetails.get(['dwelling'])!.value,
      area: this.FamilyDetails.get(['area'])!.value,
      notebookOfPoverty: this.FamilyDetails.get(['notebookOfPoverty'])!.value,
      notebookOfHandicap: this.FamilyDetails.get(['notebookOfHandicap'])!.value,
      archivated: this.FamilyDetails.get(['archivated'])!.value,
      beneficiary: this.createFromFormBeneficiary(),
      parentsDetails: this.ParentsCollection.slice(),
      childrenDetails: this.ChildrenCollection.slice(),
    };
  }

  protected createFromFormParent(): IParentAllDetails {
    return {
      ...new ParentAllDetails(),
      annualRevenue: this.ParentDetails.get(['annualRevenue'])!.value,
      cnss: this.ParentDetails.get(['cnss'])!.value,
      maritalStatus: this.ParentDetails.get(['maritalStatus'])!.value,
      occupation: this.ParentDetails.get(['occupation'])!.value,
      deceased: this.ParentDetails.get(['deceased'])!.value,
      dateOfDeath: this.ParentDetails.get(['dateOfDeath'])!.value,
      head: this.ParentDetails.get(['familyHead'])!.value,
      profile: this.createFromFormParentProfile(),
    };
  }

  protected createFromFormChild(): IChildAllDetails {
    return {
      ...new ChildAllDetails(),
      profile: this.createFromFormChildProfile(),
    };
  }

  protected createFromFormBeneficiary(): IBeneficiary {
    return {
      ...new Beneficiary(),
      archivated: this.FamilyDetails.get(['archivated'])!.value,
      authorizingOfficer: this.FamilyDetails.get(['authorizingOfficer'])!.value,
      tutor: this.FamilyDetails.get(['tutor'])!.value,
    };
  }

  protected createFromFormChildProfile(): IProfile {
    return {
      ...new Profile(),
      firstName: this.ChildDetails.get(['firstName'])!.value,
      lastName: this.ChildDetails.get(['lastName'])!.value,
      firstNameArabic: this.ChildDetails.get(['firstNameArabic'])!.value,
      lastNameArabic: this.ChildDetails.get(['lastNameArabic'])!.value,
      gender: this.ChildDetails.get(['gender'])!.value,
      dateOfBirth: this.ChildDetails.get(['dateOfBirth'])!.value,
      cin: this.ChildDetails.get(['cin'])!.value,
      address: this.ChildDetails.get(['address'])!.value,
      phone: this.ChildDetails.get(['phone'])!.value,
      email: this.ChildDetails.get(['email'])!.value,
      urlPhotoContentType: this.ChildDetails.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.ChildDetails.get(['urlPhoto'])!.value,
      urlCinAttachedContentType: this.ChildDetails.get(['urlCinAttachedContentType'])!.value,
      urlCinAttached: this.ChildDetails.get(['urlCinAttached'])!.value,
      archivated: this.ChildDetails.get(['archivated'])!.value,
      birthPlace: this.ChildDetails.get(['birthPlace'])!.value,
      placeOfResidence: this.ChildDetails.get(['placeOfResidence'])!.value,
    };
  }

  protected createFromFormParentProfile(): IProfile {
    return {
      ...new Profile(),
      firstName: this.ParentDetails.get(['firstName'])!.value,
      lastName: this.ParentDetails.get(['lastName'])!.value,
      firstNameArabic: this.ParentDetails.get(['firstNameArabic'])!.value,
      lastNameArabic: this.ParentDetails.get(['lastNameArabic'])!.value,
      gender: this.ParentDetails.get(['gender'])!.value,
      dateOfBirth: this.ParentDetails.get(['dateOfBirth'])!.value,
      cin: this.ParentDetails.get(['cin'])!.value,
      urlPhotoContentType: this.ParentDetails.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.ParentDetails.get(['urlPhoto'])!.value,
      address: this.ParentDetails.get(['address'])!.value,
      phone: this.ParentDetails.get(['phone'])!.value,
      email: this.ParentDetails.get(['email'])!.value,
      urlCinAttachedContentType: this.ParentDetails.get(['urlCinAttachedContentType'])!.value,
      urlCinAttached: this.ParentDetails.get(['urlCinAttached'])!.value,
      archivated: this.ParentDetails.get(['archivated'])!.value,
      birthPlace: this.ParentDetails.get(['birthPlace'])!.value,
      placeOfResidence: this.ParentDetails.get(['placeOfResidence'])!.value,
    };
  }
}
