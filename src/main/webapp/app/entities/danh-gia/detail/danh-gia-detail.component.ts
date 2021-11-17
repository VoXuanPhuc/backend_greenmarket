import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IDanhGia } from '../danh-gia.model';

@Component({
  selector: 'jhi-danh-gia-detail',
  templateUrl: './danh-gia-detail.component.html',
})
export class DanhGiaDetailComponent implements OnInit {
  danhGia: IDanhGia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ danhGia }) => {
      this.danhGia = danhGia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
