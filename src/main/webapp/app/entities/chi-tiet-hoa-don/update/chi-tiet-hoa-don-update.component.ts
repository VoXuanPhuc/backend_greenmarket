import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IChiTietHoaDon, ChiTietHoaDon } from '../chi-tiet-hoa-don.model';
import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';
import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';
import { HoaDonService } from 'app/entities/hoa-don/service/hoa-don.service';

@Component({
  selector: 'jhi-chi-tiet-hoa-don-update',
  templateUrl: './chi-tiet-hoa-don-update.component.html',
})
export class ChiTietHoaDonUpdateComponent implements OnInit {
  isSaving = false;

  nongSansSharedCollection: INongSan[] = [];
  hoaDonsSharedCollection: IHoaDon[] = [];

  editForm = this.fb.group({
    id: [],
    gia: [null, [Validators.required]],
    soluong: [null, [Validators.required]],
    nongsan: [],
    hoadon: [],
  });

  constructor(
    protected chiTietHoaDonService: ChiTietHoaDonService,
    protected nongSanService: NongSanService,
    protected hoaDonService: HoaDonService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ chiTietHoaDon }) => {
      this.updateForm(chiTietHoaDon);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const chiTietHoaDon = this.createFromForm();
    if (chiTietHoaDon.id !== undefined) {
      this.subscribeToSaveResponse(this.chiTietHoaDonService.update(chiTietHoaDon));
    } else {
      this.subscribeToSaveResponse(this.chiTietHoaDonService.create(chiTietHoaDon));
    }
  }

  trackNongSanById(index: number, item: INongSan): number {
    return item.id!;
  }

  trackHoaDonById(index: number, item: IHoaDon): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IChiTietHoaDon>>): void {
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

  protected updateForm(chiTietHoaDon: IChiTietHoaDon): void {
    this.editForm.patchValue({
      id: chiTietHoaDon.id,
      gia: chiTietHoaDon.gia,
      soluong: chiTietHoaDon.soluong,
      nongsan: chiTietHoaDon.nongsan,
      hoadon: chiTietHoaDon.hoadon,
    });

    this.nongSansSharedCollection = this.nongSanService.addNongSanToCollectionIfMissing(
      this.nongSansSharedCollection,
      chiTietHoaDon.nongsan
    );
    this.hoaDonsSharedCollection = this.hoaDonService.addHoaDonToCollectionIfMissing(this.hoaDonsSharedCollection, chiTietHoaDon.hoadon);
  }

  protected loadRelationshipsOptions(): void {
    this.nongSanService
      .query()
      .pipe(map((res: HttpResponse<INongSan[]>) => res.body ?? []))
      .pipe(
        map((nongSans: INongSan[]) => this.nongSanService.addNongSanToCollectionIfMissing(nongSans, this.editForm.get('nongsan')!.value))
      )
      .subscribe((nongSans: INongSan[]) => (this.nongSansSharedCollection = nongSans));

    this.hoaDonService
      .query()
      .pipe(map((res: HttpResponse<IHoaDon[]>) => res.body ?? []))
      .pipe(map((hoaDons: IHoaDon[]) => this.hoaDonService.addHoaDonToCollectionIfMissing(hoaDons, this.editForm.get('hoadon')!.value)))
      .subscribe((hoaDons: IHoaDon[]) => (this.hoaDonsSharedCollection = hoaDons));
  }

  protected createFromForm(): IChiTietHoaDon {
    return {
      ...new ChiTietHoaDon(),
      id: this.editForm.get(['id'])!.value,
      gia: this.editForm.get(['gia'])!.value,
      soluong: this.editForm.get(['soluong'])!.value,
      nongsan: this.editForm.get(['nongsan'])!.value,
      hoadon: this.editForm.get(['hoadon'])!.value,
    };
  }
}
