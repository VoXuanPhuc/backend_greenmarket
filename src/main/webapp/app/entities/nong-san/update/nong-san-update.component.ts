import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INongSan, NongSan } from '../nong-san.model';
import { NongSanService } from '../service/nong-san.service';
import { IDanhMuc } from 'app/entities/danh-muc/danh-muc.model';
import { DanhMucService } from 'app/entities/danh-muc/service/danh-muc.service';
import { INhaCungCap } from 'app/entities/nha-cung-cap/nha-cung-cap.model';
import { NhaCungCapService } from 'app/entities/nha-cung-cap/service/nha-cung-cap.service';

@Component({
  selector: 'jhi-nong-san-update',
  templateUrl: './nong-san-update.component.html',
})
export class NongSanUpdateComponent implements OnInit {
  isSaving = false;

  danhMucsSharedCollection: IDanhMuc[] = [];
  nhaCungCapsSharedCollection: INhaCungCap[] = [];

  editForm = this.fb.group({
    id: [],
    tenNS: [null, [Validators.required]],
    gia: [null, [Validators.required]],
    soluongNhap: [null, [Validators.required]],
    soluongCon: [null, [Validators.required]],
    noiSanXuat: [null, [Validators.required]],
    moTaNS: [null, [Validators.required]],
    danhmuc: [],
    nhacc: [],
  });

  constructor(
    protected nongSanService: NongSanService,
    protected danhMucService: DanhMucService,
    protected nhaCungCapService: NhaCungCapService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nongSan }) => {
      this.updateForm(nongSan);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nongSan = this.createFromForm();
    if (nongSan.id !== undefined) {
      this.subscribeToSaveResponse(this.nongSanService.update(nongSan));
    } else {
      this.subscribeToSaveResponse(this.nongSanService.create(nongSan));
    }
  }

  trackDanhMucById(index: number, item: IDanhMuc): number {
    return item.id!;
  }

  trackNhaCungCapById(index: number, item: INhaCungCap): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INongSan>>): void {
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

  protected updateForm(nongSan: INongSan): void {
    this.editForm.patchValue({
      id: nongSan.id,
      tenNS: nongSan.tenNS,
      gia: nongSan.gia,
      soluongNhap: nongSan.soluongNhap,
      soluongCon: nongSan.soluongCon,
      noiSanXuat: nongSan.noiSanXuat,
      moTaNS: nongSan.moTaNS,
      danhmuc: nongSan.danhmuc,
      nhacc: nongSan.nhacc,
    });

    this.danhMucsSharedCollection = this.danhMucService.addDanhMucToCollectionIfMissing(this.danhMucsSharedCollection, nongSan.danhmuc);
    this.nhaCungCapsSharedCollection = this.nhaCungCapService.addNhaCungCapToCollectionIfMissing(
      this.nhaCungCapsSharedCollection,
      nongSan.nhacc
    );
  }

  protected loadRelationshipsOptions(): void {
    this.danhMucService
      .query()
      .pipe(map((res: HttpResponse<IDanhMuc[]>) => res.body ?? []))
      .pipe(
        map((danhMucs: IDanhMuc[]) => this.danhMucService.addDanhMucToCollectionIfMissing(danhMucs, this.editForm.get('danhmuc')!.value))
      )
      .subscribe((danhMucs: IDanhMuc[]) => (this.danhMucsSharedCollection = danhMucs));

    this.nhaCungCapService
      .query()
      .pipe(map((res: HttpResponse<INhaCungCap[]>) => res.body ?? []))
      .pipe(
        map((nhaCungCaps: INhaCungCap[]) =>
          this.nhaCungCapService.addNhaCungCapToCollectionIfMissing(nhaCungCaps, this.editForm.get('nhacc')!.value)
        )
      )
      .subscribe((nhaCungCaps: INhaCungCap[]) => (this.nhaCungCapsSharedCollection = nhaCungCaps));
  }

  protected createFromForm(): INongSan {
    return {
      ...new NongSan(),
      id: this.editForm.get(['id'])!.value,
      tenNS: this.editForm.get(['tenNS'])!.value,
      gia: this.editForm.get(['gia'])!.value,
      soluongNhap: this.editForm.get(['soluongNhap'])!.value,
      soluongCon: this.editForm.get(['soluongCon'])!.value,
      noiSanXuat: this.editForm.get(['noiSanXuat'])!.value,
      moTaNS: this.editForm.get(['moTaNS'])!.value,
      danhmuc: this.editForm.get(['danhmuc'])!.value,
      nhacc: this.editForm.get(['nhacc'])!.value,
    };
  }
}
