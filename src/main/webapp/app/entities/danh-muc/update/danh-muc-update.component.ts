import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IDanhMuc, DanhMuc } from '../danh-muc.model';
import { DanhMucService } from '../service/danh-muc.service';

@Component({
  selector: 'jhi-danh-muc-update',
  templateUrl: './danh-muc-update.component.html',
})
export class DanhMucUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenDM: [],
    anhDanhMuc: [],
  });

  constructor(protected danhMucService: DanhMucService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ danhMuc }) => {
      this.updateForm(danhMuc);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const danhMuc = this.createFromForm();
    if (danhMuc.id !== undefined) {
      this.subscribeToSaveResponse(this.danhMucService.update(danhMuc));
    } else {
      this.subscribeToSaveResponse(this.danhMucService.create(danhMuc));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IDanhMuc>>): void {
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

  protected updateForm(danhMuc: IDanhMuc): void {
    this.editForm.patchValue({
      id: danhMuc.id,
      tenDM: danhMuc.tenDM,
      anhDanhMuc: danhMuc.anhDanhMuc,
    });
  }

  protected createFromForm(): IDanhMuc {
    return {
      ...new DanhMuc(),
      id: this.editForm.get(['id'])!.value,
      tenDM: this.editForm.get(['tenDM'])!.value,
      anhDanhMuc: this.editForm.get(['anhDanhMuc'])!.value,
    };
  }
}
