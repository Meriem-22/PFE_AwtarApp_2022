import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IProfile, Profile } from '../profile.model';
import { ProfileService } from '../service/profile.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IParent } from 'app/entities/parent/parent.model';
import { ParentService } from 'app/entities/parent/service/parent.service';
import { IChild } from 'app/entities/child/child.model';
import { ChildService } from 'app/entities/child/service/child.service';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { ITutor } from 'app/entities/tutor/tutor.model';
import { TutorService } from 'app/entities/tutor/service/tutor.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { Gender } from 'app/entities/enumerations/gender.model';

@Component({
  selector: 'jhi-profile-update',
  templateUrl: './profile-update.component.html',
})
export class ProfileUpdateComponent implements OnInit {
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
    protected parentService: ParentService,
    protected childService: ChildService,
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected tutorService: TutorService,
    protected cityService: CityService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ profile }) => {
      this.updateForm(profile);

      this.loadRelationshipsOptions();
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('awtarApp.error', { ...err, key: 'error.file.' + err.key })),
    });
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
    const profile = this.createFromForm();
    if (profile.id !== undefined) {
      this.subscribeToSaveResponse(this.profileService.update(profile));
    } else {
      this.subscribeToSaveResponse(this.profileService.create(profile));
    }
  }

  trackParentById(_index: number, item: IParent): number {
    return item.id!;
  }

  trackChildById(_index: number, item: IChild): number {
    return item.id!;
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
      id: profile.id,
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
      archivated: profile.archivated,
      parent: profile.parent,
      child: profile.child,
      authorizingOfficer: profile.authorizingOfficer,
      tutor: profile.tutor,
      birthPlace: profile.birthPlace,
      placeOfResidence: profile.placeOfResidence,
    });

    this.parentsCollection = this.parentService.addParentToCollectionIfMissing(this.parentsCollection, profile.parent);
    this.childrenCollection = this.childService.addChildToCollectionIfMissing(this.childrenCollection, profile.child);
    this.authorizingOfficersCollection = this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
      this.authorizingOfficersCollection,
      profile.authorizingOfficer
    );
    this.tutorsCollection = this.tutorService.addTutorToCollectionIfMissing(this.tutorsCollection, profile.tutor);
    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(
      this.citiesSharedCollection,
      profile.birthPlace,
      profile.placeOfResidence
    );
  }

  protected loadRelationshipsOptions(): void {
    this.parentService
      .query({ filter: 'parentprofile-is-null' })
      .pipe(map((res: HttpResponse<IParent[]>) => res.body ?? []))
      .pipe(map((parents: IParent[]) => this.parentService.addParentToCollectionIfMissing(parents, this.editForm.get('parent')!.value)))
      .subscribe((parents: IParent[]) => (this.parentsCollection = parents));

    this.childService
      .query({ filter: 'childprofile-is-null' })
      .pipe(map((res: HttpResponse<IChild[]>) => res.body ?? []))
      .pipe(map((children: IChild[]) => this.childService.addChildToCollectionIfMissing(children, this.editForm.get('child')!.value)))
      .subscribe((children: IChild[]) => (this.childrenCollection = children));

    this.authorizingOfficerService
      .query({ filter: 'authorizingofficerprofile-is-null' })
      .pipe(map((res: HttpResponse<IAuthorizingOfficer[]>) => res.body ?? []))
      .pipe(
        map((authorizingOfficers: IAuthorizingOfficer[]) =>
          this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
            authorizingOfficers,
            this.editForm.get('authorizingOfficer')!.value
          )
        )
      )
      .subscribe((authorizingOfficers: IAuthorizingOfficer[]) => (this.authorizingOfficersCollection = authorizingOfficers));

    this.tutorService
      .query({ filter: 'tutorprofile-is-null' })
      .pipe(map((res: HttpResponse<ITutor[]>) => res.body ?? []))
      .pipe(map((tutors: ITutor[]) => this.tutorService.addTutorToCollectionIfMissing(tutors, this.editForm.get('tutor')!.value)))
      .subscribe((tutors: ITutor[]) => (this.tutorsCollection = tutors));

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

  protected createFromForm(): IProfile {
    return {
      ...new Profile(),
      id: this.editForm.get(['id'])!.value,
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
      archivated: this.editForm.get(['archivated'])!.value,
      parent: this.editForm.get(['parent'])!.value,
      child: this.editForm.get(['child'])!.value,
      authorizingOfficer: this.editForm.get(['authorizingOfficer'])!.value,
      tutor: this.editForm.get(['tutor'])!.value,
      birthPlace: this.editForm.get(['birthPlace'])!.value,
      placeOfResidence: this.editForm.get(['placeOfResidence'])!.value,
    };
  }
}
