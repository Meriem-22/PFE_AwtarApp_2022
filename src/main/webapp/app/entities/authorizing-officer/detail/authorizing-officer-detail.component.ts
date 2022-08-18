import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { BeneficiaryService } from 'app/entities/beneficiary/service/beneficiary.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

import { IAuthorizingOfficer } from '../authorizing-officer.model';

@Component({
  selector: 'jhi-authorizing-officer-detail',
  templateUrl: './authorizing-officer-detail.component.html',
})
export class AuthorizingOfficerDetailComponent implements OnInit {
  authorizingOfficer: IAuthorizingOfficer | null = null;
  profile!: IProfile;
  families?: any[] | null;
  establishments?: any[] | null;
  children?: any[] | null;
  profiles?: any[] | null;

  page = 1;
  count = 0;
  tableSize = 4;
  tableSizes: any = [4, 8, 16, 20];

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected profileService: ProfileService,
    protected dataUtils: DataUtils,
    protected beneficiaryService: BeneficiaryService
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ authorizingOfficer }) => {
      this.authorizingOfficer = authorizingOfficer;
    });

    this.profileService.findProfile(this.authorizingOfficer!.id!).subscribe({
      next: (res: HttpResponse<IProfile>) => {
        this.profile = res.body!;
        console.log(this.profile);
      },
      error: e => console.error(e),
    });

    this.profileService.findOthersAuthaurizingOfficersProfiles(this.authorizingOfficer!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.profiles = res.body ?? [];
      },
      error: e => console.error(e),
    });

    this.ChildrenList();
    this.EstablishmentsList();
    this.FamiliesList();
  }

  EstablishmentsList(): void {
    this.beneficiaryService.findAllEstablishments(this.authorizingOfficer!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.establishments = res.body ?? [];
        console.log(this.establishments);
      },
      error: e => console.error(e),
    });
  }

  onTableEDataChange(event: any): void {
    this.page = event;
    this.EstablishmentsList();
  }

  onTableESizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.EstablishmentsList();
  }

  ChildrenList(): void {
    this.beneficiaryService.findAllChildren(this.authorizingOfficer!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.children = res.body ?? [];
        console.log(this.children);
      },
      error: e => console.error(e),
    });
  }

  onTableCDataChange(event: any): void {
    this.page = event;
    this.ChildrenList();
  }

  onTableCSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.ChildrenList();
  }

  FamiliesList(): void {
    this.beneficiaryService.findAllFamilies(this.authorizingOfficer!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.families = res.body ?? [];
        console.log(this.families);
      },
      error: e => console.error(e),
    });
  }

  onTableFDataChange(event: any): void {
    this.page = event;
    this.FamiliesList();
  }

  onTableFSizeChange(event: any): void {
    this.tableSize = event.target.value;
    this.page = 1;
    this.FamiliesList();
  }

  previousState(): void {
    window.history.back();
  }

  byteSize(base64String: string): string {
    return this.dataUtils.byteSize(base64String);
  }

  openFile(base64String: string, contentType: string | null | undefined): void {
    this.dataUtils.openFile(base64String, contentType);
  }

  trackId(_index: number, item: IAuthorizingOfficer): number {
    return item.id!;
  }
}
