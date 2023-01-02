import { Component, OnInit, ElementRef } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IDonationsReceived, DonationsReceived } from '../donations-received.model';
import { DonationsReceivedService } from '../service/donations-received.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';

@Component({
  selector: 'jhi-donations-received-update',
  templateUrl: './donations-received-update.component.html',
})
export class DonationsReceivedUpdateComponent implements OnInit {
  isSaving = false;

  authorizingOfficersSharedCollection: IAuthorizingOfficer[] = [];
  account: Account | null = null;

  editForm = this.fb.group({
    id: [],
    urlPhoto: [],
    urlPhotoContentType: [],
    archivated: [],
    authorizingOfficer: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected donationsReceivedService: DonationsReceivedService,
    protected authorizingOfficerService: AuthorizingOfficerService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ donationsReceived }) => {
      this.updateForm(donationsReceived);

      this.loadRelationshipsOptions();
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({
      error: (err: FileLoadError) =>
        this.eventManager.broadcast(new EventWithContent<AlertError>('awtarApp.error', { ...err, key: 'error.file.' + err.key })),
    });
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
    const donationsReceived = this.createFromForm();
    if (donationsReceived.id !== undefined) {
      this.subscribeToSaveResponse(this.donationsReceivedService.update(donationsReceived));
    } else {
      this.subscribeToSaveResponse(this.donationsReceivedService.create(donationsReceived));
    }
  }

  trackAuthorizingOfficerById(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDonationsReceived>>): void {
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

  protected updateForm(donationsReceived: IDonationsReceived): void {
    this.editForm.patchValue({
      id: donationsReceived.id,
      urlPhoto: donationsReceived.urlPhoto,
      urlPhotoContentType: donationsReceived.urlPhotoContentType,
      archivated: donationsReceived.archivated,
      authorizingOfficer: donationsReceived.authorizingOfficer,
    });

    this.authorizingOfficersSharedCollection = this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
      this.authorizingOfficersSharedCollection,
      donationsReceived.authorizingOfficer
    );
  }

  protected loadRelationshipsOptions(): void {
    this.authorizingOfficerService
      .query()
      .pipe(map((res: HttpResponse<IAuthorizingOfficer[]>) => res.body ?? []))
      .pipe(
        map((authorizingOfficers: IAuthorizingOfficer[]) =>
          this.authorizingOfficerService.addAuthorizingOfficerToCollectionIfMissing(
            authorizingOfficers,
            this.editForm.get('authorizingOfficer')!.value
          )
        )
      )
      .subscribe((authorizingOfficers: IAuthorizingOfficer[]) => (this.authorizingOfficersSharedCollection = authorizingOfficers));
  }

  protected createFromForm(): IDonationsReceived {
    return {
      ...new DonationsReceived(),
      id: this.editForm.get(['id'])!.value,
      urlPhotoContentType: this.editForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.editForm.get(['urlPhoto'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      authorizingOfficer: this.editForm.get(['authorizingOfficer'])!.value,
    };
  }
}
