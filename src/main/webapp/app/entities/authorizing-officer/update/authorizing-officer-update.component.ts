import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IAuthorizingOfficer, AuthorizingOfficer } from '../authorizing-officer.model';
import { AuthorizingOfficerService } from '../service/authorizing-officer.service';

@Component({
  selector: 'jhi-authorizing-officer-update',
  templateUrl: './authorizing-officer-update.component.html',
})
export class AuthorizingOfficerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    abbreviation: [null, [Validators.required]],
    activity: [],
    manager: [],
    managerCin: [],
  });

  constructor(
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authorizingOfficer }) => {
      this.updateForm(authorizingOfficer);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const authorizingOfficer = this.createFromForm();
    if (authorizingOfficer.id !== undefined) {
      this.subscribeToSaveResponse(this.authorizingOfficerService.update(authorizingOfficer));
    } else {
      this.subscribeToSaveResponse(this.authorizingOfficerService.create(authorizingOfficer));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAuthorizingOfficer>>): void {
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

  protected updateForm(authorizingOfficer: IAuthorizingOfficer): void {
    this.editForm.patchValue({
      id: authorizingOfficer.id,
      abbreviation: authorizingOfficer.abbreviation,
      activity: authorizingOfficer.activity,
      manager: authorizingOfficer.manager,
      managerCin: authorizingOfficer.managerCin,
    });
  }

  protected createFromForm(): IAuthorizingOfficer {
    return {
      ...new AuthorizingOfficer(),
      id: this.editForm.get(['id'])!.value,
      abbreviation: this.editForm.get(['abbreviation'])!.value,
      activity: this.editForm.get(['activity'])!.value,
      manager: this.editForm.get(['manager'])!.value,
      managerCin: this.editForm.get(['managerCin'])!.value,
    };
  }
}
