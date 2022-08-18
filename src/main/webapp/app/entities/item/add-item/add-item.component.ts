import { HttpResponse } from '@angular/common/http';
import { Component, ElementRef, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { EventManager } from '@angular/platform-browser';
import { ActivatedRoute } from '@angular/router';
import { DataUtils, FileLoadError } from 'app/core/util/data-util.service';
import { EventWithContent } from 'app/core/util/event-manager.service';
import { ItemGender } from 'app/entities/enumerations/item-gender.model';
import { INature } from 'app/entities/nature/nature.model';
import { NatureService } from 'app/entities/nature/service/nature.service';
import { AlertError } from 'app/shared/alert/alert-error.model';
import { map, Observable, finalize } from 'rxjs';
import { IItem, Item } from '../item.model';
import { ItemService } from '../service/item.service';

@Component({
  selector: 'jhi-add-item',
  templateUrl: './add-item.component.html',
  styleUrls: ['./add-item.component.css'],
})
export class AddItemComponent implements OnInit {
  isSaving = false;
  itemGenderValues = Object.keys(ItemGender);

  naturesSharedCollection: INature[] = [];

  DonationsIssued!: FormGroup;
  DonationsDetails!: FormGroup;
  DonationsDetails2!: FormGroup;
  DonationsDetails3!: FormGroup;
  DonationsDetails4!: FormGroup;

  DonationsDetailsItem!: FormGroup;
  DonationsIssued_step = false;
  DonationsDetails_step = false;
  step = 1;

  editForm = this.fb.group({
    id: [],
    name: [null, [Validators.required]],
    urlPhoto: [],
    urlPhotoContentType: [],
    gender: [null, [Validators.required]],
    composed: [],
    archivated: [],
    nature: [null, Validators.required],
  });
  i = 0;

  constructor(
    protected dataUtils: DataUtils,
    protected eventManager: EventManager,
    protected itemService: ItemService,
    protected natureService: NatureService,
    protected elementRef: ElementRef,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.i++;
    this.loadRelationshipsOptions();
  }
  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  setFileData(event: Event, field: string, isImage: boolean): void {
    this.dataUtils.loadFileToForm(event, this.editForm, field, isImage).subscribe({});
  }

  next(): void {
    this.step++;
  }

  previous(): void {
    this.step--;
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
    const item = this.createFromForm();
    if (item.id !== undefined) {
      this.subscribeToSaveResponse(this.itemService.update(item));
    } else {
      this.subscribeToSaveResponse(this.itemService.create(item));
    }
  }

  trackNatureById(_index: number, item: INature): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IItem>>): void {
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

  protected updateForm(item: IItem): void {
    this.editForm.patchValue({
      id: item.id,
      name: item.name,
      urlPhoto: item.urlPhoto,
      urlPhotoContentType: item.urlPhotoContentType,
      gender: item.gender,
      composed: item.composed,
      archivated: item.archivated,
      nature: item.nature,
    });

    this.naturesSharedCollection = this.natureService.addNatureToCollectionIfMissing(this.naturesSharedCollection, item.nature);
  }

  protected loadRelationshipsOptions(): void {
    this.natureService
      .query()
      .pipe(map((res: HttpResponse<INature[]>) => res.body ?? []))
      .pipe(map((natures: INature[]) => this.natureService.addNatureToCollectionIfMissing(natures, this.editForm.get('nature')!.value)))
      .subscribe((natures: INature[]) => (this.naturesSharedCollection = natures));
  }

  protected createFromForm(): IItem {
    return {
      ...new Item(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      urlPhotoContentType: this.editForm.get(['urlPhotoContentType'])!.value,
      urlPhoto: this.editForm.get(['urlPhoto'])!.value,
      gender: this.editForm.get(['gender'])!.value,
      composed: this.editForm.get(['composed'])!.value,
      archivated: this.editForm.get(['archivated'])!.value,
      nature: this.editForm.get(['nature'])!.value,
    };
  }
}
