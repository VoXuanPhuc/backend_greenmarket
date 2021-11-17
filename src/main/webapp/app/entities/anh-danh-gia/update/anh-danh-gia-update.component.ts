import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAnhDanhGia, AnhDanhGia } from '../anh-danh-gia.model';
import { AnhDanhGiaService } from '../service/anh-danh-gia.service';
import { IDanhGia } from 'app/entities/danh-gia/danh-gia.model';
import { DanhGiaService } from 'app/entities/danh-gia/service/danh-gia.service';

@Component({
  selector: 'jhi-anh-danh-gia-update',
  templateUrl: './anh-danh-gia-update.component.html',
})
export class AnhDanhGiaUpdateComponent implements OnInit {
  isSaving = false;

  danhGiasSharedCollection: IDanhGia[] = [];

  editForm = this.fb.group({
    id: [],
    ten: [null, [Validators.required]],
    danhgia: [],
  });

  constructor(
    protected anhDanhGiaService: AnhDanhGiaService,
    protected danhGiaService: DanhGiaService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anhDanhGia }) => {
      this.updateForm(anhDanhGia);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anhDanhGia = this.createFromForm();
    if (anhDanhGia.id !== undefined) {
      this.subscribeToSaveResponse(this.anhDanhGiaService.update(anhDanhGia));
    } else {
      this.subscribeToSaveResponse(this.anhDanhGiaService.create(anhDanhGia));
    }
  }

  trackDanhGiaById(index: number, item: IDanhGia): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnhDanhGia>>): void {
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

  protected updateForm(anhDanhGia: IAnhDanhGia): void {
    this.editForm.patchValue({
      id: anhDanhGia.id,
      ten: anhDanhGia.ten,
      danhgia: anhDanhGia.danhgia,
    });

    this.danhGiasSharedCollection = this.danhGiaService.addDanhGiaToCollectionIfMissing(this.danhGiasSharedCollection, anhDanhGia.danhgia);
  }

  protected loadRelationshipsOptions(): void {
    this.danhGiaService
      .query()
      .pipe(map((res: HttpResponse<IDanhGia[]>) => res.body ?? []))
      .pipe(
        map((danhGias: IDanhGia[]) => this.danhGiaService.addDanhGiaToCollectionIfMissing(danhGias, this.editForm.get('danhgia')!.value))
      )
      .subscribe((danhGias: IDanhGia[]) => (this.danhGiasSharedCollection = danhGias));
  }

  protected createFromForm(): IAnhDanhGia {
    return {
      ...new AnhDanhGia(),
      id: this.editForm.get(['id'])!.value,
      ten: this.editForm.get(['ten'])!.value,
      danhgia: this.editForm.get(['danhgia'])!.value,
    };
  }
}
