import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHuyenQuan } from '../huyen-quan.model';

@Component({
  selector: 'jhi-huyen-quan-detail',
  templateUrl: './huyen-quan-detail.component.html',
})
export class HuyenQuanDetailComponent implements OnInit {
  huyenQuan: IHuyenQuan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ huyenQuan }) => {
      this.huyenQuan = huyenQuan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
