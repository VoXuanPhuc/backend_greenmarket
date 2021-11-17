import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IDanhGia, DanhGia } from '../danh-gia.model';
import { DanhGiaService } from '../service/danh-gia.service';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { KhachHangService } from 'app/entities/khach-hang/service/khach-hang.service';

@Component({
  selector: 'jhi-danh-gia-update',
  templateUrl: './danh-gia-update.component.html',
})
export class DanhGiaUpdateComponent implements OnInit {
  isSaving = false;

  nongSansSharedCollection: INongSan[] = [];
  khachHangsSharedCollection: IKhachHang[] = [];

  editForm = this.fb.group({
    id: [],
    sao: [null, [Validators.required]],
    chitiet: [null, [Validators.required]],
    image: [null, [Validators.required]],
    ngaydanhgia: [null, [Validators.required]],
    nongsan: [],
    khachhang: [],
  });

  constructor(
    protected danhGiaService: DanhGiaService,
    protected nongSanService: NongSanService,
    protected khachHangService: KhachHangService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ danhGia }) => {
      if (danhGia.id === undefined) {
        const today = dayjs().startOf('day');
        danhGia.ngaydanhgia = today;
      }

      this.updateForm(danhGia);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const danhGia = this.createFromForm();
    if (danhGia.id !== undefined) {
      this.subscribeToSaveResponse(this.danhGiaService.update(danhGia));
    } else {
      this.subscribeToSaveResponse(this.danhGiaService.create(danhGia));
    }
  }

  trackNongSanById(index: number, item: INongSan): number {
    return item.id!;
  }

  trackKhachHangById(index: number, item: IKhachHang): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDanhGia>>): void {
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

  protected updateForm(danhGia: IDanhGia): void {
    this.editForm.patchValue({
      id: danhGia.id,
      sao: danhGia.sao,
      chitiet: danhGia.chitiet,
      image: danhGia.image,
      ngaydanhgia: danhGia.ngaydanhgia ? danhGia.ngaydanhgia.format(DATE_TIME_FORMAT) : null,
      nongsan: danhGia.nongsan,
      khachhang: danhGia.khachhang,
    });

    this.nongSansSharedCollection = this.nongSanService.addNongSanToCollectionIfMissing(this.nongSansSharedCollection, danhGia.nongsan);
    this.khachHangsSharedCollection = this.khachHangService.addKhachHangToCollectionIfMissing(
      this.khachHangsSharedCollection,
      danhGia.khachhang
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

  protected createFromForm(): IDanhGia {
    return {
      ...new DanhGia(),
      id: this.editForm.get(['id'])!.value,
      sao: this.editForm.get(['sao'])!.value,
      chitiet: this.editForm.get(['chitiet'])!.value,
      image: this.editForm.get(['image'])!.value,
      ngaydanhgia: this.editForm.get(['ngaydanhgia'])!.value
        ? dayjs(this.editForm.get(['ngaydanhgia'])!.value, DATE_TIME_FORMAT)
        : undefined,
      nongsan: this.editForm.get(['nongsan'])!.value,
      khachhang: this.editForm.get(['khachhang'])!.value,
    };
  }
}
