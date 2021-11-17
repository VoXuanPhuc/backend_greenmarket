import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { IPhuongThucTT, PhuongThucTT } from '../phuong-thuc-tt.model';
import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';

@Component({
  selector: 'jhi-phuong-thuc-tt-update',
  templateUrl: './phuong-thuc-tt-update.component.html',
})
export class PhuongThucTTUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    tenPTTT: [null, [Validators.required]],
  });

  constructor(protected phuongThucTTService: PhuongThucTTService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ phuongThucTT }) => {
      this.updateForm(phuongThucTT);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const phuongThucTT = this.createFromForm();
    if (phuongThucTT.id !== undefined) {
      this.subscribeToSaveResponse(this.phuongThucTTService.update(phuongThucTT));
    } else {
      this.subscribeToSaveResponse(this.phuongThucTTService.create(phuongThucTT));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPhuongThucTT>>): void {
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

  protected updateForm(phuongThucTT: IPhuongThucTT): void {
    this.editForm.patchValue({
      id: phuongThucTT.id,
      tenPTTT: phuongThucTT.tenPTTT,
    });
  }

  protected createFromForm(): IPhuongThucTT {
    return {
      ...new PhuongThucTT(),
      id: this.editForm.get(['id'])!.value,
      tenPTTT: this.editForm.get(['tenPTTT'])!.value,
    };
  }
}
