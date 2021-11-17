import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IXaPhuong, XaPhuong } from '../xa-phuong.model';
import { XaPhuongService } from '../service/xa-phuong.service';
import { IHuyenQuan } from 'app/entities/huyen-quan/huyen-quan.model';
import { HuyenQuanService } from 'app/entities/huyen-quan/service/huyen-quan.service';

@Component({
  selector: 'jhi-xa-phuong-update',
  templateUrl: './xa-phuong-update.component.html',
})
export class XaPhuongUpdateComponent implements OnInit {
  isSaving = false;

  huyenQuansSharedCollection: IHuyenQuan[] = [];

  editForm = this.fb.group({
    id: [],
    ten: [],
    huyen: [],
  });

  constructor(
    protected xaPhuongService: XaPhuongService,
    protected huyenQuanService: HuyenQuanService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ xaPhuong }) => {
      this.updateForm(xaPhuong);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const xaPhuong = this.createFromForm();
    if (xaPhuong.id !== undefined) {
      this.subscribeToSaveResponse(this.xaPhuongService.update(xaPhuong));
    } else {
      this.subscribeToSaveResponse(this.xaPhuongService.create(xaPhuong));
    }
  }

  trackHuyenQuanById(index: number, item: IHuyenQuan): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IXaPhuong>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(xaPhuong: IXaPhuong): void {
    this.editForm.patchValue({
      id: xaPhuong.id,
      ten: xaPhuong.ten,
      huyen: xaPhuong.huyen,
    });

    this.huyenQuansSharedCollection = this.huyenQuanService.addHuyenQuanToCollectionIfMissing(
      this.huyenQuansSharedCollection,
      xaPhuong.huyen
    );
  }

  protected loadRelationshipsOptions(): void {
    this.huyenQuanService
      .query()
      .pipe(map((res: HttpResponse<IHuyenQuan[]>) => res.body ?? []))
      .pipe(
        map((huyenQuans: IHuyenQuan[]) =>
          this.huyenQuanService.addHuyenQuanToCollectionIfMissing(huyenQuans, this.editForm.get('huyen')!.value)
        )
      )
      .subscribe((huyenQuans: IHuyenQuan[]) => (this.huyenQuansSharedCollection = huyenQuans));
  }

  protected createFromForm(): IXaPhuong {
    return {
      ...new XaPhuong(),
      id: this.editForm.get(['id'])!.value,
      ten: this.editForm.get(['ten'])!.value,
      huyen: this.editForm.get(['huyen'])!.value,
    };
  }
}
