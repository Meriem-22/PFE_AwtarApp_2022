import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ISchoolLevel, SchoolLevel } from '../school-level.model';
import { SchoolLevelService } from '../service/school-level.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-school-level-update',
  templateUrl: './school-level-update.component.html',
})
export class SchoolLevelUpdateComponent implements OnInit {
  isSaving = false;
  account: Account | null = null;

  editForm = this.fb.group({
    id: [],
    schoolLevel: [null, [Validators.required]],
    archivated: [],
  });

  constructor(
    protected schoolLevelService: SchoolLevelService,
    private accountService: AccountService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
    this.activatedRoute.data.subscribe(({ schoolLevel }) => {
      this.updateForm(schoolLevel);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const schoolLevel = this.createFromForm();
    if (schoolLevel.id !== undefined) {
      this.subscribeToSaveResponse(this.schoolLevelService.update(schoolLevel));
    } else {
      this.subscribeToSaveResponse(this.schoolLevelService.create(schoolLevel));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISchoolLevel>>): void {
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

  protected updateForm(schoolLevel: ISchoolLevel): void {
    this.editForm.patchValue({
      id: schoolLevel.id,
      schoolLevel: schoolLevel.schoolLevel,
      archivated: schoolLevel.archivated,
    });
  }

  protected createFromForm(): ISchoolLevel {
    return {
      ...new SchoolLevel(),
      id: this.editForm.get(['id'])!.value,
      schoolLevel: this.editForm.get(['schoolLevel'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
    };
  }
}
