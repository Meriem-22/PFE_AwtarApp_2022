import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IReport, Report } from '../report.model';
import { ReportService } from '../service/report.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { IEstablishment } from 'app/entities/establishment/establishment.model';
import { EstablishmentService } from 'app/entities/establishment/service/establishment.service';

@Component({
  selector: 'jhi-report-update',
  templateUrl: './report-update.component.html',
})
export class ReportUpdateComponent implements OnInit {
  isSaving = false;

  establishmentsSharedCollection: IEstablishment[] = [];

  editForm = this.fb.group({
    id: [],
    nature: [null, [Validators.required]],
    date: [null, [Validators.required]],
    urlEnclosed: [null, [Validators.required]],
    urlEnclosedContentType: [],
    archivated: [],
    establishment: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected reportService: ReportService,
    protected establishmentService: EstablishmentService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ report }) => {
      this.updateForm(report);

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
    const report = this.createFromForm();
    if (report.id !== undefined) {
      this.subscribeToSaveResponse(this.reportService.update(report));
    } else {
      this.subscribeToSaveResponse(this.reportService.create(report));
    }
  }

  trackEstablishmentById(_index: number, item: IEstablishment): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IReport>>): void {
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

  protected updateForm(report: IReport): void {
    this.editForm.patchValue({
      id: report.id,
      nature: report.nature,
      date: report.date,
      urlEnclosed: report.urlEnclosed,
      urlEnclosedContentType: report.urlEnclosedContentType,
      archivated: report.archivated,
      establishment: report.establishment,
    });

    this.establishmentsSharedCollection = this.establishmentService.addEstablishmentToCollectionIfMissing(
      this.establishmentsSharedCollection,
      report.establishment
    );
  }

  protected loadRelationshipsOptions(): void {
    this.establishmentService
      .query()
      .pipe(map((res: HttpResponse<IEstablishment[]>) => res.body ?? []))
      .pipe(
        map((establishments: IEstablishment[]) =>
          this.establishmentService.addEstablishmentToCollectionIfMissing(establishments, this.editForm.get('establishment')!.value)
        )
      )
      .subscribe((establishments: IEstablishment[]) => (this.establishmentsSharedCollection = establishments));
  }

  protected createFromForm(): IReport {
    return {
      ...new Report(),
      id: this.editForm.get(['id'])!.value,
      nature: this.editForm.get(['nature'])!.value,
      date: this.editForm.get(['date'])!.value,
      urlEnclosedContentType: this.editForm.get(['urlEnclosedContentType'])!.value,
      urlEnclosed: this.editForm.get(['urlEnclosed'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      establishment: this.editForm.get(['establishment'])!.value,
    };
  }
}
