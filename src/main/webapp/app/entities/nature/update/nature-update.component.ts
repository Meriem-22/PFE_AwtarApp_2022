import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { INature, Nature } from '../nature.model';
import { NatureService } from '../service/nature.service';
import { Beneficiaries } from 'app/entities/enumerations/beneficiaries.model';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-nature-update',
  templateUrl: './nature-update.component.html',
})
export class NatureUpdateComponent implements OnInit {
  isSaving = false;
  beneficiariesValues = Object.keys(Beneficiaries);
  account: Account | null = null;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    destinedTo: [null, [Validators.required]],
    necessityValue: [],
    archivated: [],
  });

  constructor(
    protected natureService: NatureService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    this.activatedRoute.data.subscribe(({ nature }) => {
      this.updateForm(nature);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nature = this.createFromForm();
    if (nature.id !== undefined) {
      this.subscribeToSaveResponse(this.natureService.update(nature));
    } else {
      this.subscribeToSaveResponse(this.natureService.create(nature));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INature>>): void {
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

  protected updateForm(nature: INature): void {
    this.editForm.patchValue({
      id: nature.id,
      name: nature.name,
      destinedTo: nature.destinedTo,
      necessityValue: nature.necessityValue,
      archivated: nature.archivated,
    });
  }

  protected createFromForm(): INature {
    return {
      ...new Nature(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      destinedTo: this.editForm.get(['destinedTo'])!.value,
      necessityValue: this.editForm.get(['necessityValue'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}
