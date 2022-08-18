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
import { ProfileService } from 'app/layouts/profiles/profile.service';
import { finalize, map, Observable } from 'rxjs';
import { IParentAllDetails, ParentAllDetails } from '../parent.model';
import { ParentService } from '../service/parent.service';

@Component({
  selector: 'jhi-add-parent',
  templateUrl: './add-parent.component.html',
  styleUrls: ['./add-parent.component.scss'],
})
export class AddParentComponent implements OnInit {
  isSaving = false;
  parentFamily: IFamily | undefined;

  genderValues = Object.keys(Gender);

  citiesSharedCollection: ICity[] = [];

  maritalStatusValues = Object.keys(MaritalStatus);

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

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected profileService: ProfileService,
    protected parentService: ParentService,
    protected cityService: CityService,
    protected elementRef: ElementRef,
    protected dataUtilsChild: DataUtils,
    protected eventManagerChild: EventManager,
    protected elementRefChild: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected formBuilder: FormBuilder,
    protected router: Router,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.parentFamily = family;
    });

    this.loadRelationshipsOptionsParent();
    console.log(history.state);
    this.parentFamily = history.state.parentFamily;
  }

  save(): void {
    this.isSaving = true;

    const parent = this.createFromFormParent();
    this.subscribeToSaveResponse(this.parentService.addParent(parent));
    console.log(parent);
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

  trackCityById(_index: number, item: ICity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IParentAllDetails>>): void {
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

  protected loadRelationshipsOptionsParent(): void {
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

  protected createFromFormParent(): IParentAllDetails {
    return {
      ...new ParentAllDetails(),
      annualRevenue: this.editForm.get(['annualRevenue'])!.value,
      cnss: this.editForm.get(['cnss'])!.value,
      maritalStatus: this.editForm.get(['maritalStatus'])!.value,
      occupation: this.editForm.get(['occupation'])!.value,
      deceased: this.editForm.get(['deceased'])!.value,
      dateOfDeath: this.editForm.get(['dateOfDeath'])!.value,
      family: this.parentFamily,
      head: this.editForm.get(['familyHead'])!.value,
      profile: this.createFromFormParentProfile(),
    };
  }

  protected createFromFormParentProfile(): IProfile {
    return {
      ...new Profile(),
      firstName: this.editForm.get(['firstName'])!.value,
      lastName: this.editForm.get(['lastName'])!.value,
      firstNameArabic: this.editForm.get(['firstNameArabic'])!.value,
      lastNameArabic: this.editForm.get(['lastNameArabic'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      dateOfBirth: this.editForm.get(['dateOfBirth'])!.value,
      cin: this.editForm.get(['cin'])!.value,
      address: this.editForm.get(['address'])!.value,
      phone: this.editForm.get(['phone'])!.value,
      email: this.editForm.get(['email'])!.value,
      urlPhotoContentType: this.editForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.editForm.get(['urlPhoto'])!.value,
      birthPlace: this.editForm.get(['birthPlace'])!.value,
      placeOfResidence: this.editForm.get(['placeOfResidence'])!.value,
    };
  }
}
