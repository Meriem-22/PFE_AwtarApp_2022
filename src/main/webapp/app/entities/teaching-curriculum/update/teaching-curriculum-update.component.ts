import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { ITeachingCurriculum, TeachingCurriculum } from '../teaching-curriculum.model';
import { TeachingCurriculumService } from '../service/teaching-curriculum.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { EventManager, EventWithContent } from 'app/core/util/event-manager.service';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { ISchoolLevel } from 'app/entities/school-level/school-level.model';
import { SchoolLevelService } from 'app/entities/school-level/service/school-level.service';
import { IChild } from 'app/entities/child/child.model';
import { ChildService } from 'app/entities/child/service/child.service';
import { IEducationalInstitution } from 'app/entities/educational-institution/educational-institution.model';
import { EducationalInstitutionService } from 'app/entities/educational-institution/service/educational-institution.service';
import { Status } from 'app/entities/enumerations/status.model';

@Component({
  selector: 'jhi-teaching-curriculum-update',
  templateUrl: './teaching-curriculum-update.component.html',
})
export class TeachingCurriculumUpdateComponent implements OnInit {
  isSaving = false;
  statusValues = Object.keys(Status);

  schoolLevelsSharedCollection: ISchoolLevel[] = [];
  childrenSharedCollection: IChild[] = [];
  educationalInstitutionsSharedCollection: IEducationalInstitution[] = [];

  editForm = this.fb.group({
    id: [],
    beginningYear: [null, [Validators.required]],
    endYear: [null, [Validators.required]],
    annualResult: [],
    result: [null, [Validators.required]],
    remarks: [],
    attachedFile: [],
    attachedFileContentType: [],
    archivated: [],
    schoolLevel: [null, Validators.required],
    child: [null, Validators.required],
    educationalInstitution: [null, Validators.required],
  });

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected teachingCurriculumService: TeachingCurriculumService,
    protected schoolLevelService: SchoolLevelService,
    protected childService: ChildService,
    protected educationalInstitutionService: EducationalInstitutionService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teachingCurriculum }) => {
      this.updateForm(teachingCurriculum);

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
    const teachingCurriculum = this.createFromForm();
    if (teachingCurriculum.id !== undefined) {
      this.subscribeToSaveResponse(this.teachingCurriculumService.update(teachingCurriculum));
    } else {
      this.subscribeToSaveResponse(this.teachingCurriculumService.create(teachingCurriculum));
    }
  }

  trackSchoolLevelById(_index: number, item: ISchoolLevel): number {
    return item.id!;
  }

  trackChildById(_index: number, item: IChild): number {
    return item.id!;
  }

  trackEducationalInstitutionById(_index: number, item: IEducationalInstitution): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITeachingCurriculum>>): void {
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

  protected updateForm(teachingCurriculum: ITeachingCurriculum): void {
    this.editForm.patchValue({
      id: teachingCurriculum.id,
      beginningYear: teachingCurriculum.beginningYear,
      endYear: teachingCurriculum.endYear,
      annualResult: teachingCurriculum.annualResult,
      result: teachingCurriculum.result,
      remarks: teachingCurriculum.remarks,
      attachedFile: teachingCurriculum.attachedFile,
      attachedFileContentType: teachingCurriculum.attachedFileContentType,
      archivated: teachingCurriculum.archivated,
      schoolLevel: teachingCurriculum.schoolLevel,
      child: teachingCurriculum.child,
      educationalInstitution: teachingCurriculum.educationalInstitution,
    });

    this.schoolLevelsSharedCollection = this.schoolLevelService.addSchoolLevelToCollectionIfMissing(
      this.schoolLevelsSharedCollection,
      teachingCurriculum.schoolLevel
    );
    this.childrenSharedCollection = this.childService.addChildToCollectionIfMissing(
      this.childrenSharedCollection,
      teachingCurriculum.child
    );
    this.educationalInstitutionsSharedCollection = this.educationalInstitutionService.addEducationalInstitutionToCollectionIfMissing(
      this.educationalInstitutionsSharedCollection,
      teachingCurriculum.educationalInstitution
    );
  }

  protected loadRelationshipsOptions(): void {
    this.schoolLevelService
      .query()
      .pipe(map((res: HttpResponse<ISchoolLevel[]>) => res.body ?? []))
      .pipe(
        map((schoolLevels: ISchoolLevel[]) =>
          this.schoolLevelService.addSchoolLevelToCollectionIfMissing(schoolLevels, this.editForm.get('schoolLevel')!.value)
        )
      )
      .subscribe((schoolLevels: ISchoolLevel[]) => (this.schoolLevelsSharedCollection = schoolLevels));

    this.childService
      .query()
      .pipe(map((res: HttpResponse<IChild[]>) => res.body ?? []))
      .pipe(map((children: IChild[]) => this.childService.addChildToCollectionIfMissing(children, this.editForm.get('child')!.value)))
      .subscribe((children: IChild[]) => (this.childrenSharedCollection = children));

    this.educationalInstitutionService
      .query()
      .pipe(map((res: HttpResponse<IEducationalInstitution[]>) => res.body ?? []))
      .pipe(
        map((educationalInstitutions: IEducationalInstitution[]) =>
          this.educationalInstitutionService.addEducationalInstitutionToCollectionIfMissing(
            educationalInstitutions,
            this.editForm.get('educationalInstitution')!.value
          )
        )
      )
      .subscribe(
        (educationalInstitutions: IEducationalInstitution[]) => (this.educationalInstitutionsSharedCollection = educationalInstitutions)
      );
  }

  protected createFromForm(): ITeachingCurriculum {
    return {
      ...new TeachingCurriculum(),
      id: this.editForm.get(['id'])!.value,
      beginningYear: this.editForm.get(['beginningYear'])!.value,
      endYear: this.editForm.get(['endYear'])!.value,
      annualResult: this.editForm.get(['annualResult'])!.value,
      result: this.editForm.get(['result'])!.value,
      remarks: this.editForm.get(['remarks'])!.value,
      attachedFileContentType: this.editForm.get(['attachedFileContentType'])!.value,
      attachedFile: this.editForm.get(['attachedFile'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      schoolLevel: this.editForm.get(['schoolLevel'])!.value,
      child: this.editForm.get(['child'])!.value,
      educationalInstitution: this.editForm.get(['educationalInstitution'])!.value,
    };
  }
}
