import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IStatusOfHealth } from '../status-of-health.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-status-of-health-detail',
  templateUrl: './status-of-health-detail.component.html',
})
export class StatusOfHealthDetailComponent implements OnInit {
  statusOfHealth: IStatusOfHealth | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ statusOfHealth }) => {
      this.statusOfHealth = statusOfHealth;
    });
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
