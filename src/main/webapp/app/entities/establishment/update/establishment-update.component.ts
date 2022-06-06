import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEstablishment, Establishment } from '../establishment.model';
import { EstablishmentService } from '../service/establishment.service';
import { IEstablishmentType } from 'app/entities/establishment-type/establishment-type.model';
import { EstablishmentTypeService } from 'app/entities/establishment-type/service/establishment-type.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';

@Component({
  selector: 'jhi-establishment-update',
  templateUrl: './establishment-update.component.html',
})
export class EstablishmentUpdateComponent implements OnInit {
  isSaving = false;

  establishmentTypesSharedCollection: IEstablishmentType[] = [];
  citiesSharedCollection: ICity[] = [];

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
  });

  constructor(
    protected establishmentService: EstablishmentService,
    protected establishmentTypeService: EstablishmentTypeService,
    protected cityService: CityService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ establishment }) => {
      this.updateForm(establishment);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const establishment = this.createFromForm();
    if (establishment.id !== undefined) {
      this.subscribeToSaveResponse(this.establishmentService.update(establishment));
    } else {
      this.subscribeToSaveResponse(this.establishmentService.create(establishment));
    }
  }

  trackEstablishmentTypeById(_index: number, item: IEstablishmentType): number {
    return item.id!;
  }

  trackCityById(_index: number, item: ICity): number {
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

  protected updateForm(establishment: IEstablishment): void {
    this.editForm.patchValue({
      id: establishment.id,
      name: establishment.name,
      activity: establishment.activity,
      manager: establishment.manager,
      managerCin: establishment.managerCin,
      contact: establishment.contact,
      adress: establishment.adress,
      cp: establishment.cp,
      region: establishment.region,
      phone: establishment.phone,
      fax: establishment.fax,
      email: establishment.email,
      site: establishment.site,
      remark: establishment.remark,
      establishmentType: establishment.establishmentType,
      city: establishment.city,
    });

    this.establishmentTypesSharedCollection = this.establishmentTypeService.addEstablishmentTypeToCollectionIfMissing(
      this.establishmentTypesSharedCollection,
      establishment.establishmentType
    );
    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(this.citiesSharedCollection, establishment.city);
  }

  protected loadRelationshipsOptions(): void {
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
      id: this.editForm.get(['id'])!.value,
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
    };
  }
}
