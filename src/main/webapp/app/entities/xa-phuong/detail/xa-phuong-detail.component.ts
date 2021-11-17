import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IXaPhuong } from '../xa-phuong.model';

@Component({
  selector: 'jhi-xa-phuong-detail',
  templateUrl: './xa-phuong-detail.component.html',
})
export class XaPhuongDetailComponent implements OnInit {
  xaPhuong: IXaPhuong | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ xaPhuong }) => {
      this.xaPhuong = xaPhuong;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
