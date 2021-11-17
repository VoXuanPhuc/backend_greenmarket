import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnhDanhGia } from '../anh-danh-gia.model';

@Component({
  selector: 'jhi-anh-danh-gia-detail',
  templateUrl: './anh-danh-gia-detail.component.html',
})
export class AnhDanhGiaDetailComponent implements OnInit {
  anhDanhGia: IAnhDanhGia | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anhDanhGia }) => {
      this.anhDanhGia = anhDanhGia;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
