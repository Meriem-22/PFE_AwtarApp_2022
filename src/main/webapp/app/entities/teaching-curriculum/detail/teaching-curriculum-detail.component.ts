import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITeachingCurriculum } from '../teaching-curriculum.model';
import { DataUtils } from 'app/core/util/data-util.service';

@Component({
  selector: 'jhi-teaching-curriculum-detail',
  templateUrl: './teaching-curriculum-detail.component.html',
})
export class TeachingCurriculumDetailComponent implements OnInit {
  teachingCurriculum: ITeachingCurriculum | null = null;

  constructor(protected dataUtils: DataUtils, protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ teachingCurriculum }) => {
      this.teachingCurriculum = teachingCurriculum;
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
