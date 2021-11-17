import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IHoaDon, HoaDon } from '../hoa-don.model';
import { HoaDonService } from '../service/hoa-don.service';
import { IPhuongThucTT } from 'app/entities/phuong-thuc-tt/phuong-thuc-tt.model';
import { PhuongThucTTService } from 'app/entities/phuong-thuc-tt/service/phuong-thuc-tt.service';
import { IHinhThucGiaoHang } from 'app/entities/hinh-thuc-giao-hang/hinh-thuc-giao-hang.model';
import { HinhThucGiaoHangService } from 'app/entities/hinh-thuc-giao-hang/service/hinh-thuc-giao-hang.service';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { KhachHangService } from 'app/entities/khach-hang/service/khach-hang.service';

@Component({
  selector: 'jhi-hoa-don-update',
  templateUrl: './hoa-don-update.component.html',
})
export class HoaDonUpdateComponent implements OnInit {
  isSaving = false;

  phuongThucTTSSharedCollection: IPhuongThucTT[] = [];
  hinhThucGiaoHangsSharedCollection: IHinhThucGiaoHang[] = [];
  khachHangsSharedCollection: IKhachHang[] = [];

  editForm = this.fb.group({
    id: [],
    tongthanhtoan: [null, [Validators.required]],
    chiphivanchuyen: [null, [Validators.required]],
    ngaythanhtoan: [null, [Validators.required]],
    ngaytao: [null, [Validators.required]],
    trangthai: [],
    phuongthucTT: [],
    phuongthucGH: [],
    khachhang: [],
  });

  constructor(
    protected hoaDonService: HoaDonService,
    protected phuongThucTTService: PhuongThucTTService,
    protected hinhThucGiaoHangService: HinhThucGiaoHangService,
    protected khachHangService: KhachHangService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hoaDon }) => {
      if (hoaDon.id === undefined) {
        const today = dayjs().startOf('day');
        hoaDon.ngaythanhtoan = today;
        hoaDon.ngaytao = today;
      }

      this.updateForm(hoaDon);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hoaDon = this.createFromForm();
    if (hoaDon.id !== undefined) {
      this.subscribeToSaveResponse(this.hoaDonService.update(hoaDon));
    } else {
      this.subscribeToSaveResponse(this.hoaDonService.create(hoaDon));
    }
  }

  trackPhuongThucTTById(index: number, item: IPhuongThucTT): number {
    return item.id!;
  }

  trackHinhThucGiaoHangById(index: number, item: IHinhThucGiaoHang): number {
    return item.id!;
  }

  trackKhachHangById(index: number, item: IKhachHang): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHoaDon>>): void {
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

  protected updateForm(hoaDon: IHoaDon): void {
    this.editForm.patchValue({
      id: hoaDon.id,
      tongthanhtoan: hoaDon.tongthanhtoan,
      chiphivanchuyen: hoaDon.chiphivanchuyen,
      ngaythanhtoan: hoaDon.ngaythanhtoan ? hoaDon.ngaythanhtoan.format(DATE_TIME_FORMAT) : null,
      ngaytao: hoaDon.ngaytao ? hoaDon.ngaytao.format(DATE_TIME_FORMAT) : null,
      trangthai: hoaDon.trangthai,
      phuongthucTT: hoaDon.phuongthucTT,
      phuongthucGH: hoaDon.phuongthucGH,
      khachhang: hoaDon.khachhang,
    });

    this.phuongThucTTSSharedCollection = this.phuongThucTTService.addPhuongThucTTToCollectionIfMissing(
      this.phuongThucTTSSharedCollection,
      hoaDon.phuongthucTT
    );
    this.hinhThucGiaoHangsSharedCollection = this.hinhThucGiaoHangService.addHinhThucGiaoHangToCollectionIfMissing(
      this.hinhThucGiaoHangsSharedCollection,
      hoaDon.phuongthucGH
    );
    this.khachHangsSharedCollection = this.khachHangService.addKhachHangToCollectionIfMissing(
      this.khachHangsSharedCollection,
      hoaDon.khachhang
    );
  }

  protected loadRelationshipsOptions(): void {
    this.phuongThucTTService
      .query()
      .pipe(map((res: HttpResponse<IPhuongThucTT[]>) => res.body ?? []))
      .pipe(
        map((phuongThucTTS: IPhuongThucTT[]) =>
          this.phuongThucTTService.addPhuongThucTTToCollectionIfMissing(phuongThucTTS, this.editForm.get('phuongthucTT')!.value)
        )
      )
      .subscribe((phuongThucTTS: IPhuongThucTT[]) => (this.phuongThucTTSSharedCollection = phuongThucTTS));

    this.hinhThucGiaoHangService
      .query()
      .pipe(map((res: HttpResponse<IHinhThucGiaoHang[]>) => res.body ?? []))
      .pipe(
        map((hinhThucGiaoHangs: IHinhThucGiaoHang[]) =>
          this.hinhThucGiaoHangService.addHinhThucGiaoHangToCollectionIfMissing(hinhThucGiaoHangs, this.editForm.get('phuongthucGH')!.value)
        )
      )
      .subscribe((hinhThucGiaoHangs: IHinhThucGiaoHang[]) => (this.hinhThucGiaoHangsSharedCollection = hinhThucGiaoHangs));

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

  protected createFromForm(): IHoaDon {
    return {
      ...new HoaDon(),
      id: this.editForm.get(['id'])!.value,
      tongthanhtoan: this.editForm.get(['tongthanhtoan'])!.value,
      chiphivanchuyen: this.editForm.get(['chiphivanchuyen'])!.value,
      ngaythanhtoan: this.editForm.get(['ngaythanhtoan'])!.value
        ? dayjs(this.editForm.get(['ngaythanhtoan'])!.value, DATE_TIME_FORMAT)
        : undefined,
      ngaytao: this.editForm.get(['ngaytao'])!.value ? dayjs(this.editForm.get(['ngaytao'])!.value, DATE_TIME_FORMAT) : undefined,
      trangthai: this.editForm.get(['trangthai'])!.value,
      phuongthucTT: this.editForm.get(['phuongthucTT'])!.value,
      phuongthucGH: this.editForm.get(['phuongthucGH'])!.value,
      khachhang: this.editForm.get(['khachhang'])!.value,
    };
  }
}
