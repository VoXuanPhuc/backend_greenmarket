import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITinhTP } from '../tinh-tp.model';

@Component({
  selector: 'jhi-tinh-tp-detail',
  templateUrl: './tinh-tp-detail.component.html',
})
export class TinhTPDetailComponent implements OnInit {
  tinhTP: ITinhTP | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tinhTP }) => {
      this.tinhTP = tinhTP;
    });
  }

  previousState(): void {
    window.history.back();
  }
}
