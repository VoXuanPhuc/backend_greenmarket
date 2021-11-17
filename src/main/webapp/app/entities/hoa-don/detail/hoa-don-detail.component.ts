import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHoaDon } from '../hoa-don.model';

@Component({
  selector: 'jhi-hoa-don-detail',
  templateUrl: './hoa-don-detail.component.html',
})
export class HoaDonDetailComponent implements OnInit {
  hoaDon: IHoaDon | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoaDon }) => {
      this.hoaDon = hoaDon;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
