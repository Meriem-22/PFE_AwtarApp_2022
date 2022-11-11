import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { TutorService } from 'app/entities/tutor/service/tutor.service';
import { Beneficiary, IBeneficiary } from '../beneficiary.model';
import { finalize } from 'rxjs/operators';
import { BeneficiaryService } from '../service/beneficiary.service';
import { Observable } from 'rxjs';
import { ITutor } from 'app/entities/tutor/tutor.model';

@Component({
  selector: 'jhi-edit-tutor',
  templateUrl: './edit-tutor.component.html',
  styleUrls: ['./edit-tutor.component.css'],
})
export class EditTutorComponent implements OnInit {
  account: Account | null = null;
  Tutor!: IProfile;
  beneficiary?: IBeneficiary;
  activity?: string;
  profiles?: any[] | null;

  page = 1;
  count = 0;
  tableSize = 4;
  tableSizes: any = [3, 6, 12, 20];
  id!: number;
  isSaving = false;
  tutor?: ITutor;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private profileService: ProfileService,
    private tutorService: TutorService,
    protected dataUtils: DataUtils,
    protected beneficiaryService: BeneficiaryService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      this.beneficiary = beneficiary;
      console.log(beneficiary);
    });
    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    if (this.beneficiary!.tutor) {
      this.tutorService.find(this.beneficiary!.tutor.id!).subscribe({
        next: res => {
          this.activity = res.body!.activity!;
        },
        error: e => console.error(e),
      });

      this.profileService.findProfile(this.beneficiary!.tutor.id!).subscribe({
        next: (res: HttpResponse<IProfile>) => {
          this.Tutor = res.body!;
        },
        error: e => console.error(e),
      });

      this.TutorsList();
    }
    if (!this.beneficiary!.tutor) {
      this.tutorService.findTutorsDetails().subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.profiles = res.body ?? [];
          console.log(this.profiles);
        },
        error: e => console.error(e),
      });
    }
  }

  TutorsList(): void {
    this.profileService.findOthersTutorsProfiles(this.beneficiary!.tutor!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.profiles = res.body ?? [];
        console.log(this.profiles);
      },
      error: e => console.error(e),
    });
  }

  onTableDataChange(event: any): void {
    this.page = event;
    this.TutorsList();
  }

  onTableSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.TutorsList();
  }

  previousState(): void {
    window.history.back();
  }

  trackId(_index: number, item: IProfile): number {
    return item.id!;
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  edit(identity: IProfile): void {
    this.id = identity.id!;
    console.log(this.id);
    this.beneficiary!.idContributor = this.id;
    console.log(this.beneficiary!);

    this.subscribeToSaveResponse(this.beneficiaryService.updateTutor(this.beneficiary!));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiary>>): void {
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
}
