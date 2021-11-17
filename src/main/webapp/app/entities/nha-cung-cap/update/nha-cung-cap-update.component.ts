import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { INhaCungCap, NhaCungCap } from '../nha-cung-cap.model';
import { NhaCungCapService } from '../service/nha-cung-cap.service';
import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';
import { XaPhuongService } from 'app/entities/xa-phuong/service/xa-phuong.service';

@Component({
  selector: 'jhi-nha-cung-cap-update',
  templateUrl: './nha-cung-cap-update.component.html',
})
export class NhaCungCapUpdateComponent implements OnInit {
  isSaving = false;

  diaChisCollection: IXaPhuong[] = [];

  editForm = this.fb.group({
    id: [],
    tenNCC: [null, [Validators.required]],
    chitietdiachi: [],
    diaChi: [],
  });

  constructor(
    protected nhaCungCapService: NhaCungCapService,
    protected xaPhuongService: XaPhuongService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ nhaCungCap }) => {
      this.updateForm(nhaCungCap);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const nhaCungCap = this.createFromForm();
    if (nhaCungCap.id !== undefined) {
      this.subscribeToSaveResponse(this.nhaCungCapService.update(nhaCungCap));
    } else {
      this.subscribeToSaveResponse(this.nhaCungCapService.create(nhaCungCap));
    }
  }

  trackXaPhuongById(index: number, item: IXaPhuong): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<INhaCungCap>>): void {
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

  protected updateForm(nhaCungCap: INhaCungCap): void {
    this.editForm.patchValue({
      id: nhaCungCap.id,
      tenNCC: nhaCungCap.tenNCC,
      chitietdiachi: nhaCungCap.chitietdiachi,
      diaChi: nhaCungCap.diaChi,
    });

    this.diaChisCollection = this.xaPhuongService.addXaPhuongToCollectionIfMissing(this.diaChisCollection, nhaCungCap.diaChi);
  }

  protected loadRelationshipsOptions(): void {
    this.xaPhuongService
      .query({ filter: 'nhacungcap-is-null' })
      .pipe(map((res: HttpResponse<IXaPhuong[]>) => res.body ?? []))
      .pipe(
        map((xaPhuongs: IXaPhuong[]) =>
          this.xaPhuongService.addXaPhuongToCollectionIfMissing(xaPhuongs, this.editForm.get('diaChi')!.value)
        )
      )
      .subscribe((xaPhuongs: IXaPhuong[]) => (this.diaChisCollection = xaPhuongs));
  }

  protected createFromForm(): INhaCungCap {
    return {
      ...new NhaCungCap(),
      id: this.editForm.get(['id'])!.value,
      tenNCC: this.editForm.get(['tenNCC'])!.value,
      chitietdiachi: this.editForm.get(['chitietdiachi'])!.value,
      diaChi: this.editForm.get(['diaChi'])!.value,
    };
  }
}
