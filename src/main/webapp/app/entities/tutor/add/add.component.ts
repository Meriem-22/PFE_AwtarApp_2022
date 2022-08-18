import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { EventWithContent } from 'app/core/util/event-manager.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { Gender } from 'app/entities/enumerations/gender.model';
import { IProfile, Profile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { map, Observable, finalize } from 'rxjs';
import { TutorService } from '../service/tutor.service';
import { ITutor, Tutor } from '../tutor.model';

@Component({
  selector: 'jhi-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.scss'],
})
export class AddComponent implements OnInit {
  isSaving = false;
  genderValues = Object.keys(Gender);
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

  tutorForm = this.fb.group({
    id: [],
    activity: [],
    manager: [],
    managerCin: [],
  });
  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected profileService: ProfileService,
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
    this.loadRelationshipsOptions();
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
    const tutor = this.createFromForm();
    this.subscribeToSaveResponse(this.tutorService.add(tutor));
    console.log(tutor);
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

  protected updateProfileForm(profile: IProfile): void {
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
      archivated: profile.archivated,
      birthPlace: profile.birthPlace,
      placeOfResidence: profile.placeOfResidence,
    });

    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(
      this.citiesSharedCollection,
      profile.birthPlace,
      profile.placeOfResidence
    );
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

  protected createProfileForm(): IProfile {
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
  protected updateForm(tutor: ITutor): void {
    this.editForm.patchValue({
      activity: tutor.activity,
      manager: tutor.manager,
      managerCin: tutor.managerCin,
    });
  }

  protected createFromForm(): ITutor {
    return {
      ...new Tutor(),
      activity: this.tutorForm.get(['activity'])!.value,
      manager: this.tutorForm.get(['manager'])!.value,
      managerCin: this.tutorForm.get(['managerCin'])!.value,
      profile: this.createProfileForm(),
    };
  }
}
