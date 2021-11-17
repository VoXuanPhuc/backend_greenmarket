import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IPhuongThucTT } from '../phuong-thuc-tt.model';

@Component({
  selector: 'jhi-phuong-thuc-tt-detail',
  templateUrl: './phuong-thuc-tt-detail.component.html',
})
export class PhuongThucTTDetailComponent implements OnInit {
  phuongThucTT: IPhuongThucTT | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phuongThucTT }) => {
      this.phuongThucTT = phuongThucTT;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
