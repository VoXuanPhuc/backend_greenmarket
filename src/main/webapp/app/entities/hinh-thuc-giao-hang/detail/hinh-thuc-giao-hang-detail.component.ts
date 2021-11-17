import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';

@Component({
  selector: 'jhi-hinh-thuc-giao-hang-detail',
  templateUrl: './hinh-thuc-giao-hang-detail.component.html',
})
export class HinhThucGiaoHangDetailComponent implements OnInit {
  hinhThucGiaoHang: IHinhThucGiaoHang | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hinhThucGiaoHang }) => {
      this.hinhThucGiaoHang = hinhThucGiaoHang;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
