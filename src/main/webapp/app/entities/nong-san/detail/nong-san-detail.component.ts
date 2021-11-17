import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INongSan } from '../nong-san.model';

@Component({
  selector: 'jhi-nong-san-detail',
  templateUrl: './nong-san-detail.component.html',
})
export class NongSanDetailComponent implements OnInit {
  nongSan: INongSan | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nongSan }) => {
      this.nongSan = nongSan;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
