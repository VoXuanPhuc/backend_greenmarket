import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IHinhThucGiaoHang, HinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';
import { HinhThucGiaoHangService } from '../service/hinh-thuc-giao-hang.service';

@Component({
  selector: 'jhi-hinh-thuc-giao-hang-update',
  templateUrl: './hinh-thuc-giao-hang-update.component.html',
})
export class HinhThucGiaoHangUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    mota: [null, [Validators.required]],
  });

  constructor(
    protected hinhThucGiaoHangService: HinhThucGiaoHangService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ hinhThucGiaoHang }) => {
      this.updateForm(hinhThucGiaoHang);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const hinhThucGiaoHang = this.createFromForm();
    if (hinhThucGiaoHang.id !== undefined) {
      this.subscribeToSaveResponse(this.hinhThucGiaoHangService.update(hinhThucGiaoHang));
    } else {
      this.subscribeToSaveResponse(this.hinhThucGiaoHangService.create(hinhThucGiaoHang));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHinhThucGiaoHang>>): void {
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

  protected updateForm(hinhThucGiaoHang: IHinhThucGiaoHang): void {
    this.editForm.patchValue({
      id: hinhThucGiaoHang.id,
      mota: hinhThucGiaoHang.mota,
    });
  }

  protected createFromForm(): IHinhThucGiaoHang {
    return {
      ...new HinhThucGiaoHang(),
      id: this.editForm.get(['id'])!.value,
      mota: this.editForm.get(['mota'])!.value,
    };
  }
}
