import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IEstablishmentType, EstablishmentType } from '../establishment-type.model';
import { EstablishmentTypeService } from '../service/establishment-type.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-establishment-type-update',
  templateUrl: './establishment-type-update.component.html',
})
export class EstablishmentTypeUpdateComponent implements OnInit {
  isSaving = false;
  account: Account | null = null;

  editForm = this.fb.group({
    id: [],
    typeName: [null, [Validators.required]],
    archivated: [],
  });

  constructor(
    protected establishmentTypeService: EstablishmentTypeService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder,
    private accountService: AccountService
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ establishmentType }) => {
      this.updateForm(establishmentType);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const establishmentType = this.createFromForm();
    if (establishmentType.id !== undefined) {
      this.subscribeToSaveResponse(this.establishmentTypeService.update(establishmentType));
    } else {
      this.subscribeToSaveResponse(this.establishmentTypeService.create(establishmentType));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEstablishmentType>>): void {
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

  protected updateForm(establishmentType: IEstablishmentType): void {
    this.editForm.patchValue({
      id: establishmentType.id,
      typeName: establishmentType.typeName,
      archivated: establishmentType.archivated,
    });
  }

  protected createFromForm(): IEstablishmentType {
    return {
      ...new EstablishmentType(),
      id: this.editForm.get(['id'])!.value,
      typeName: this.editForm.get(['typeName'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}
