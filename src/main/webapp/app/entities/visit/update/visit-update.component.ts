import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IVisit, Visit } from '../visit.model';
import { VisitService } from '../service/visit.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IBeneficiary } from 'app/entities/beneficiary/beneficiary.model';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';

@Component({
  selector: 'jhi-visit-update',
  templateUrl: './visit-update.component.html',
})
export class VisitUpdateComponent implements OnInit {
  isSaving = false;

  beneficiariesSharedCollection: IBeneficiary[] = [];

  editForm = this.fb.group({
    id: [],
    visitDate: [null, [Validators.required]],
    realizedBy: [],
    description: [null, [Validators.required]],
    attachedFile: [],
    attachedFileContentType: [],
    archivated: [],
    beneficiary: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected visitService: VisitService,
    protected beneficiaryService: BeneficiaryService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ visit }) => {
      this.updateForm(visit);

      this.loadRelationshipsOptions();
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

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const visit = this.createFromForm();
    if (visit.id !== undefined) {
      this.subscribeToSaveResponse(this.visitService.update(visit));
    } else {
      this.subscribeToSaveResponse(this.visitService.create(visit));
    }
  }

  trackBeneficiaryById(_index: number, item: IBeneficiary): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IVisit>>): void {
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

  protected updateForm(visit: IVisit): void {
    this.editForm.patchValue({
      id: visit.id,
      visitDate: visit.visitDate,
      realizedBy: visit.realizedBy,
      description: visit.description,
      attachedFile: visit.attachedFile,
      attachedFileContentType: visit.attachedFileContentType,
      archivated: visit.archivated,
      beneficiary: visit.beneficiary,
    });

    this.beneficiariesSharedCollection = this.beneficiaryService.addBeneficiaryToCollectionIfMissing(
      this.beneficiariesSharedCollection,
      visit.beneficiary
    );
  }

  protected loadRelationshipsOptions(): void {
    this.beneficiaryService
      .query()
      .pipe(map((res: HttpResponse<IBeneficiary[]>) => res.body ?? []))
      .pipe(
        map((beneficiaries: IBeneficiary[]) =>
          this.beneficiaryService.addBeneficiaryToCollectionIfMissing(beneficiaries, this.editForm.get('beneficiary')!.value)
        )
      )
      .subscribe((beneficiaries: IBeneficiary[]) => (this.beneficiariesSharedCollection = beneficiaries));
  }

  protected createFromForm(): IVisit {
    return {
      ...new Visit(),
      id: this.editForm.get(['id'])!.value,
      visitDate: this.editForm.get(['visitDate'])!.value,
      realizedBy: this.editForm.get(['realizedBy'])!.value,
      description: this.editForm.get(['description'])!.value,
      attachedFileContentType: this.editForm.get(['attachedFileContentType'])!.value,
      attachedFile: this.editForm.get(['attachedFile'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      beneficiary: this.editForm.get(['beneficiary'])!.value,
    };
  }
}
