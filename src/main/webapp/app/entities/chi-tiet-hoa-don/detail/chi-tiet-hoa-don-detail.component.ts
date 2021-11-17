import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChiTietHoaDon } from '../chi-tiet-hoa-don.model';

@Component({
  selector: 'jhi-chi-tiet-hoa-don-detail',
  templateUrl: './chi-tiet-hoa-don-detail.component.html',
})
export class ChiTietHoaDonDetailComponent implements OnInit {
  chiTietHoaDon: IChiTietHoaDon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chiTietHoaDon }) => {
      this.chiTietHoaDon = chiTietHoaDon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
