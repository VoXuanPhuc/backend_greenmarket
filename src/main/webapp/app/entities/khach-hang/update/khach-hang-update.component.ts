import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import * as dayjs from 'dayjs';
import { DATE_TIME_FORMAT } from 'app/config/input.constants';

import { IKhachHang, KhachHang } from '../khach-hang.model';
import { KhachHangService } from '../service/khach-hang.service';
import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';
import { XaPhuongService } from 'app/entities/xa-phuong/service/xa-phuong.service';

@Component({
  selector: 'jhi-khach-hang-update',
  templateUrl: './khach-hang-update.component.html',
})
export class KhachHangUpdateComponent implements OnInit {
  isSaving = false;

  xaPhuongsSharedCollection: IXaPhuong[] = [];

  editForm = this.fb.group({
    id: [],
    hoTenKH: [],
    tenDangNhap: [null, []],
    matkhau: [null, [Validators.required]],
    email: [null, [Validators.required]],
    sdt: [null, [Validators.required]],
    ngaySinh: [null, [Validators.required]],
    gioitinh: [],
    chitietdiachi: [],
    xa: [],
  });

  constructor(
    protected khachHangService: KhachHangService,
    protected xaPhuongService: XaPhuongService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ khachHang }) => {
      if (khachHang.id === undefined) {
        const today = dayjs().startOf('day');
        khachHang.ngaySinh = today;
      }

      this.updateForm(khachHang);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const khachHang = this.createFromForm();
    if (khachHang.id !== undefined) {
      this.subscribeToSaveResponse(this.khachHangService.update(khachHang));
    } else {
      this.subscribeToSaveResponse(this.khachHangService.create(khachHang));
    }
  }

  trackXaPhuongById(index: number, item: IXaPhuong): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IKhachHang>>): void {
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

  protected updateForm(khachHang: IKhachHang): void {
    this.editForm.patchValue({
      id: khachHang.id,
      hoTenKH: khachHang.hoTenKH,
      tenDangNhap: khachHang.tenDangNhap,
      matkhau: khachHang.matkhau,
      email: khachHang.email,
      sdt: khachHang.sdt,
      ngaySinh: khachHang.ngaySinh ? khachHang.ngaySinh.format(DATE_TIME_FORMAT) : null,
      gioitinh: khachHang.gioitinh,
      chitietdiachi: khachHang.chitietdiachi,
      xa: khachHang.xa,
    });

    this.xaPhuongsSharedCollection = this.xaPhuongService.addXaPhuongToCollectionIfMissing(this.xaPhuongsSharedCollection, khachHang.xa);
  }

  protected loadRelationshipsOptions(): void {
    this.xaPhuongService
      .query()
      .pipe(map((res: HttpResponse<IXaPhuong[]>) => res.body ?? []))
      .pipe(
        map((xaPhuongs: IXaPhuong[]) => this.xaPhuongService.addXaPhuongToCollectionIfMissing(xaPhuongs, this.editForm.get('xa')!.value))
      )
      .subscribe((xaPhuongs: IXaPhuong[]) => (this.xaPhuongsSharedCollection = xaPhuongs));
  }

  protected createFromForm(): IKhachHang {
    return {
      ...new KhachHang(),
      id: this.editForm.get(['id'])!.value,
      hoTenKH: this.editForm.get(['hoTenKH'])!.value,
      tenDangNhap: this.editForm.get(['tenDangNhap'])!.value,
      matkhau: this.editForm.get(['matkhau'])!.value,
      email: this.editForm.get(['email'])!.value,
      sdt: this.editForm.get(['sdt'])!.value,
      ngaySinh: this.editForm.get(['ngaySinh'])!.value ? dayjs(this.editForm.get(['ngaySinh'])!.value, DATE_TIME_FORMAT) : undefined,
      gioitinh: this.editForm.get(['gioitinh'])!.value,
      chitietdiachi: this.editForm.get(['chitietdiachi'])!.value,
      xa: this.editForm.get(['xa'])!.value,
    };
  }
}
