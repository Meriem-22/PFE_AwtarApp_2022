import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IEducationalInstitution, EducationalInstitution } from '../educational-institution.model';
import { EducationalInstitutionService } from '../service/educational-institution.service';
import { ICity } from 'app/entities/city/city.model';
import { CityService } from 'app/entities/city/service/city.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-educational-institution-update',
  templateUrl: './educational-institution-update.component.html',
})
export class EducationalInstitutionUpdateComponent implements OnInit {
  isSaving = false;

  citiesSharedCollection: ICity[] = [];
  account: Account | null = null;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    adress: [],
    archivated: [],
    city: [],
  });

  constructor(
    protected educationalInstitutionService: EducationalInstitutionService,
    protected cityService: CityService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    this.activatedRoute.data.subscribe(({ educationalInstitution }) => {
      this.updateForm(educationalInstitution);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const educationalInstitution = this.createFromForm();
    if (educationalInstitution.id !== undefined) {
      this.subscribeToSaveResponse(this.educationalInstitutionService.update(educationalInstitution));
    } else {
      this.subscribeToSaveResponse(this.educationalInstitutionService.create(educationalInstitution));
    }
  }

  trackCityById(_index: number, item: ICity): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEducationalInstitution>>): void {
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

  protected updateForm(educationalInstitution: IEducationalInstitution): void {
    this.editForm.patchValue({
      id: educationalInstitution.id,
      name: educationalInstitution.name,
      adress: educationalInstitution.adress,
      archivated: educationalInstitution.archivated,
      city: educationalInstitution.city,
    });

    this.citiesSharedCollection = this.cityService.addCityToCollectionIfMissing(this.citiesSharedCollection, educationalInstitution.city);
  }

  protected loadRelationshipsOptions(): void {
    this.cityService
      .query()
      .pipe(map((res: HttpResponse<ICity[]>) => res.body ?? []))
      .pipe(map((cities: ICity[]) => this.cityService.addCityToCollectionIfMissing(cities, this.editForm.get('city')!.value)))
      .subscribe((cities: ICity[]) => (this.citiesSharedCollection = cities));
  }

  protected createFromForm(): IEducationalInstitution {
    return {
      ...new EducationalInstitution(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      adress: this.editForm.get(['adress'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      city: this.editForm.get(['city'])!.value,
    };
  }
}
