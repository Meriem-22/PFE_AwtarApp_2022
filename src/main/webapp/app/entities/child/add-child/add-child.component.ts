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
import { ChildAllDetails, IChildAllDetails } from '../child.model';
import { ChildService } from '../service/child.service';

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

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected profileService: ProfileService,
    protected childService: ChildService,
    protected cityService: CityService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected formBuilder: FormBuilder,
    protected router: Router
  ) {}

  ngOnInit(): void {
    this.ChildDetails = this.formBuilder.group({
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
    });
    this.loadRelationshipsOptionsChild();
    this.ChildFamily = history.state.childFamily;
  }
  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.ChildDetails, field, isImage).subscribe({});
  }

  clearInputImage(field: string, fieldContentType: string, idInput: string): void {
    this.ChildDetails.patchValue({
      [field]: null,
      [fieldContentType]: null,
    });
    if (idInput && this.elementRef.nativeElement.querySelector('#' + idInput)) {
      this.elementRef.nativeElement.querySelector('#' + idInput).value = null;
    }
  }

  submit(): void {
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

  protected createFromFormChild(): IChildAllDetails {
    return {
      ...new ChildAllDetails(),
      family: this.ChildFamily,
      profile: this.createFromFormChildProfile(),
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
      archivated: this.ChildDetails.get(['archivated'])!.value,
      birthPlace: this.ChildDetails.get(['birthPlace'])!.value,
      placeOfResidence: this.ChildDetails.get(['placeOfResidence'])!.value,
    };
  }
}
