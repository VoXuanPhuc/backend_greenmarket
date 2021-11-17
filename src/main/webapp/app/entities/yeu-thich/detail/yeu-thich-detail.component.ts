import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IYeuThich } from '../yeu-thich.model';

@Component({
  selector: 'jhi-yeu-thich-detail',
  templateUrl: './yeu-thich-detail.component.html',
})
export class YeuThichDetailComponent implements OnInit {
  yeuThich: IYeuThich | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ yeuThich }) => {
      this.yeuThich = yeuThich;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
