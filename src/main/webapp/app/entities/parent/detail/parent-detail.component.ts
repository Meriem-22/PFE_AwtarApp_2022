import { HttpResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { DataUtils } from 'app/core/util/data-util.service';
import { IProfile } from 'app/entities/profile/profile.model';
import { ProfileService } from 'app/entities/profile/service/profile.service';

import { IParent } from '../parent.model';

@Component({
  selector: 'jhi-parent-detail',
  templateUrl: './parent-detail.component.html',
})
export class ParentDetailComponent implements OnInit {
  parent: IParent | null = null;
  profile!: IProfile;
  parents?: any[] = [];
  Otherparent?: any;
  children?: IProfile[] = [];
  t = 0;

  constructor(protected activatedRoute: ActivatedRoute, protected profileService: ProfileService, protected dataUtils: DataUtils) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ parent }) => {
      this.parent = parent;
    });

    this.profileService.findProfile(this.parent!.id!).subscribe({
      next: (res: HttpResponse<IProfile>) => {
        this.profile = res.body!;
      },
      error: e => console.error(e),
    });

    this.profileService.getAllParentsProfile(this.parent!.family!.id!).subscribe({
      next: (res: HttpResponse<any[]>) => {
        this.parents = res.body ?? [];
        for (this.t = 0; this.t < this.parents.length; this.t++) {
          if (this.parents[this.t].id !== this.parent?.id) {
            this.Otherparent = this.parents[this.t];
          }
        }
        console.log(this.Otherparent.id);
      },
      error: e => console.error(e),
    });

    this.profileService.getAllChildrenProfile(this.parent!.family!.id!).subscribe({
      next: (res: HttpResponse<IProfile[]>) => {
        this.children = res.body ?? [];
      },
      error: e => console.error(e),
    });
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

  previousState(): void {
    window.history.back();
  }
}
