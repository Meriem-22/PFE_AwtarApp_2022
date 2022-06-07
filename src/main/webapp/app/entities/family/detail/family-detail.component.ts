import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';
import { DataUtils } from 'app/core/util/data-util.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

import { IFamilyAllDetails } from '../family.model';

@Component({
  selector: 'jhi-family-detail',
  templateUrl: './family-detail.component.html',
})
export class FamilyDetailComponent implements OnInit {
  family?: IFamilyAllDetails | null = null;
  profiles?: IProfile[] = [];
  children?: IProfile[] = [];
  isLoading = false;

  constructor(
    protected activatedRoute: ActivatedRoute,
    protected profileService: ProfileService,
    protected dataUtils: DataUtils,
    protected router: Router,
    protected modalService: NgbModal
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ family }) => {
      this.family = family;
      this.profileService.getAllParentsProfile(family.id).subscribe({
        next: (res: HttpResponse<IProfile[]>) => {
          this.isLoading = false;
          this.profiles = res.body ?? [];
          console.log(this.profiles);
        },
        error: () => {
          this.isLoading = false;
        },
      });

      this.profileService.getAllChildrenProfile(family.id).subscribe({
        next: (res: HttpResponse<IProfile[]>) => {
          this.isLoading = false;
          this.children = res.body ?? [];
          console.log(this.children);
        },
        error: () => {
          this.isLoading = false;
        },
      });
    });
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
    return this.dataUtils.openFile(base64String, contentType);
  }

  addParent(): void {
    this.router.navigateByUrl('/parent/add', { state: { parentFamily: this.family } });
  }

  addChild(): void {
    this.router.navigateByUrl('/child/add', { state: { childFamily: this.family } });
  }
}
