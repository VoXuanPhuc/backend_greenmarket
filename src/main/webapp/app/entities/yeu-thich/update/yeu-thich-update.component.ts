import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IYeuThich, YeuThich } from '../yeu-thich.model';
import { YeuThichService } from '../service/yeu-thich.service';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { KhachHangService } from 'app/entities/khach-hang/service/khach-hang.service';

@Component({
  selector: 'jhi-yeu-thich-update',
  templateUrl: './yeu-thich-update.component.html',
})
export class YeuThichUpdateComponent implements OnInit {
  isSaving = false;

  nongSansSharedCollection: INongSan[] = [];
  khachHangsSharedCollection: IKhachHang[] = [];

  editForm = this.fb.group({
    id: [],
    nongsan: [],
    khachhang: [],
  });

  constructor(
    protected yeuThichService: YeuThichService,
    protected nongSanService: NongSanService,
    protected khachHangService: KhachHangService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ yeuThich }) => {
      this.updateForm(yeuThich);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const yeuThich = this.createFromForm();
    if (yeuThich.id !== undefined) {
      this.subscribeToSaveResponse(this.yeuThichService.update(yeuThich));
    } else {
      this.subscribeToSaveResponse(this.yeuThichService.create(yeuThich));
    }
  }

  trackNongSanById(index: number, item: INongSan): number {
    return item.id!;
  }

  trackKhachHangById(index: number, item: IKhachHang): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IYeuThich>>): void {
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

  protected updateForm(yeuThich: IYeuThich): void {
    this.editForm.patchValue({
      id: yeuThich.id,
      nongsan: yeuThich.nongsan,
      khachhang: yeuThich.khachhang,
    });

    this.nongSansSharedCollection = this.nongSanService.addNongSanToCollectionIfMissing(this.nongSansSharedCollection, yeuThich.nongsan);
    this.khachHangsSharedCollection = this.khachHangService.addKhachHangToCollectionIfMissing(
      this.khachHangsSharedCollection,
      yeuThich.khachhang
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nongSanService
      .query()
      .pipe(map((res: HttpResponse<INongSan[]>) => res.body ?? []))
      .pipe(
        map((nongSans: INongSan[]) => this.nongSanService.addNongSanToCollectionIfMissing(nongSans, this.editForm.get('nongsan')!.value))
      )
      .subscribe((nongSans: INongSan[]) => (this.nongSansSharedCollection = nongSans));

    this.khachHangService
      .query()
      .pipe(map((res: HttpResponse<IKhachHang[]>) => res.body ?? []))
      .pipe(
        map((khachHangs: IKhachHang[]) =>
          this.khachHangService.addKhachHangToCollectionIfMissing(khachHangs, this.editForm.get('khachhang')!.value)
        )
      )
      .subscribe((khachHangs: IKhachHang[]) => (this.khachHangsSharedCollection = khachHangs));
  }

  protected createFromForm(): IYeuThich {
    return {
      ...new YeuThich(),
      id: this.editForm.get(['id'])!.value,
      nongsan: this.editForm.get(['nongsan'])!.value,
      khachhang: this.editForm.get(['khachhang'])!.value,
    };
  }
}
