import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IHuyenQuan, HuyenQuan } from '../huyen-quan.model';
import { HuyenQuanService } from '../service/huyen-quan.service';
import { ITinhTP } from 'app/entities/tinh-tp/tinh-tp.model';
import { TinhTPService } from 'app/entities/tinh-tp/service/tinh-tp.service';

@Component({
  selector: 'jhi-huyen-quan-update',
  templateUrl: './huyen-quan-update.component.html',
})
export class HuyenQuanUpdateComponent implements OnInit {
  isSaving = false;

  tinhTPSSharedCollection: ITinhTP[] = [];

  editForm = this.fb.group({
    id: [],
    ten: [],
    tinh: [],
  });

  constructor(
    protected huyenQuanService: HuyenQuanService,
    protected tinhTPService: TinhTPService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ huyenQuan }) => {
      this.updateForm(huyenQuan);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const huyenQuan = this.createFromForm();
    if (huyenQuan.id !== undefined) {
      this.subscribeToSaveResponse(this.huyenQuanService.update(huyenQuan));
    } else {
      this.subscribeToSaveResponse(this.huyenQuanService.create(huyenQuan));
    }
  }

  trackTinhTPById(index: number, item: ITinhTP): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IHuyenQuan>>): void {
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

  protected updateForm(huyenQuan: IHuyenQuan): void {
    this.editForm.patchValue({
      id: huyenQuan.id,
      ten: huyenQuan.ten,
      tinh: huyenQuan.tinh,
    });

    this.tinhTPSSharedCollection = this.tinhTPService.addTinhTPToCollectionIfMissing(this.tinhTPSSharedCollection, huyenQuan.tinh);
  }

  protected loadRelationshipsOptions(): void {
    this.tinhTPService
      .query()
      .pipe(map((res: HttpResponse<ITinhTP[]>) => res.body ?? []))
      .pipe(map((tinhTPS: ITinhTP[]) => this.tinhTPService.addTinhTPToCollectionIfMissing(tinhTPS, this.editForm.get('tinh')!.value)))
      .subscribe((tinhTPS: ITinhTP[]) => (this.tinhTPSSharedCollection = tinhTPS));
  }

  protected createFromForm(): IHuyenQuan {
    return {
      ...new HuyenQuan(),
      id: this.editForm.get(['id'])!.value,
      ten: this.editForm.get(['ten'])!.value,
      tinh: this.editForm.get(['tinh'])!.value,
    };
  }
}
