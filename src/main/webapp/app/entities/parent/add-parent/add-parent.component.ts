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
  ParentDetails!: FormGroup;
  isSaving = false;
  parentFamily: IFamily | undefined;

  genderValues = Object.keys(Gender);

  citiesSharedCollection: ICity[] = [];

  maritalStatusValues = Object.keys(MaritalStatus);

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
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.parentFamily = family;
    });
    this.ParentDetails = this.formBuilder.group({
      firstName: [null, [Validators.required]],
      lastName: [null, [Validators.required]],
      firstNameArabic: [],
      lastNameArabic: [],
      gender: [null, [Validators.required]],
      dateOfBirth: [null, [Validators.required]],
      cin: [],
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
    this.loadRelationshipsOptionsParent();
    console.log(history.state);
    this.parentFamily = history.state.parentFamily;
  }

  submit(): void {
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
            this.ParentDetails.get('birthPlace')!.value,
            this.ParentDetails.get('placeOfResidence')!.value
          )
        )
      )
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));
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
      family: this.parentFamily,
      head: this.ParentDetails.get(['familyHead'])!.value,
      profile: this.createFromFormParentProfile(),
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
      address: this.ParentDetails.get(['address'])!.value,
      phone: this.ParentDetails.get(['phone'])!.value,
      email: this.ParentDetails.get(['email'])!.value,
      urlPhotoContentType: this.ParentDetails.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.ParentDetails.get(['urlPhoto'])!.value,
      archivated: this.ParentDetails.get(['archivated'])!.value,
      birthPlace: this.ParentDetails.get(['birthPlace'])!.value,
      placeOfResidence: this.ParentDetails.get(['placeOfResidence'])!.value,
    };
  }
}
