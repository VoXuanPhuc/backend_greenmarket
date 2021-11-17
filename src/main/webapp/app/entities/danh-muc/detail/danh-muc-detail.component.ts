import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDanhMuc } from '../danh-muc.model';

@Component({
  selector: 'jhi-danh-muc-detail',
  templateUrl: './danh-muc-detail.component.html',
})
export class DanhMucDetailComponent implements OnInit {
  danhMuc: IDanhMuc | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ danhMuc }) => {
      this.danhMuc = danhMuc;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
