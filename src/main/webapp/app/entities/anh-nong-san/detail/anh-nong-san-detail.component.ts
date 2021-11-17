import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAnhNongSan } from '../anh-nong-san.model';

@Component({
  selector: 'jhi-anh-nong-san-detail',
  templateUrl: './anh-nong-san-detail.component.html',
})
export class AnhNongSanDetailComponent implements OnInit {
  anhNongSan: IAnhNongSan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anhNongSan }) => {
      this.anhNongSan = anhNongSan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
