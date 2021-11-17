import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { INhaCungCap } from '../nha-cung-cap.model';

@Component({
  selector: 'jhi-nha-cung-cap-detail',
  templateUrl: './nha-cung-cap-detail.component.html',
})
export class NhaCungCapDetailComponent implements OnInit {
  nhaCungCap: INhaCungCap | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhaCungCap }) => {
      this.nhaCungCap = nhaCungCap;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
