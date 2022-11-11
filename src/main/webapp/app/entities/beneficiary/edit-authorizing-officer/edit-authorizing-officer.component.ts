import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Account } from 'app/core/auth/account.model';
import { AccountService } from 'app/core/auth/account.service';
import { DataUtils } from 'app/core/util/data-util.service';
import { IAuthorizingOfficer } from 'app/entities/authorizing-officer/authorizing-officer.model';
import { AuthorizingOfficerService } from 'app/entities/authorizing-officer/service/authorizing-officer.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';
import { Observable } from 'rxjs';
import { Beneficiary, IBeneficiary } from '../beneficiary.model';
import { BeneficiaryService } from '../service/beneficiary.service';
import { finalize } from 'rxjs/operators';

@Component({
  selector: 'jhi-edit-authorizing-officer',
  templateUrl: './edit-authorizing-officer.component.html',
  styleUrls: ['./edit-authorizing-officer.component.css'],
})
export class EditAuthorizingOfficerComponent implements OnInit {
  account: Account | null = null;
  Ordonnateur!: IProfile;
  beneficiary?: IBeneficiary;
  activity?: string;
  profiles?: any[] | null;
  id!: number;
  isSaving = false;
  authorizingOfficer?: IAuthorizingOfficer;

  constructor(
    protected activatedRoute: ActivatedRoute,
    private accountService: AccountService,
    private profileService: ProfileService,
    private authorizingOfficerService: AuthorizingOfficerService,
    protected dataUtils: DataUtils,
    protected beneficiaryService: BeneficiaryService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ beneficiary }) => {
      this.beneficiary = beneficiary;
    });

    this.accountService.getAuthenticationState().subscribe(account => {
      this.account = account;
    });

    if (this.beneficiary!.authorizingOfficer) {
      this.authorizingOfficerService.find(this.beneficiary!.authorizingOfficer.id!).subscribe({
        next: res => {
          this.activity = res.body!.activity!;
        },
        error: e => console.error(e),
      });

      this.profileService.findProfile(this.beneficiary!.authorizingOfficer.id!).subscribe({
        next: (res: HttpResponse<IProfile>) => {
          this.Ordonnateur = res.body!;
        },
        error: e => console.error(e),
      });

      this.AuthaurizingOfficersList();
    }
    if (!this.beneficiary!.authorizingOfficer) {
      this.authorizingOfficerService.findAuthorizingOfficerDetails().subscribe({
        next: (res: HttpResponse<any[]>) => {
          this.profiles = res.body ?? [];
          console.log(this.profiles);
        },
        error: e => console.error(e),
      });
    }
  }

  AuthaurizingOfficersList(): void {
    this.profileService.findOthersAuthaurizingOfficersProfiles(this.beneficiary!.authorizingOfficer!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.profiles = res.body ?? [];
        console.log(this.profiles);
      },
      error: e => console.error(e),
    });
  }

  previousState(): void {
    window.history.back();
  }

  trackId(_index: number, item: IAuthorizingOfficer): number {
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

    this.subscribeToSaveResponse(this.beneficiaryService.updateAuthorizingOfficer(this.beneficiary!));
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IBeneficiary>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
    console.log(result);
  }

  protected onSaveSuccess(): void {
    window.history.back();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }
}
