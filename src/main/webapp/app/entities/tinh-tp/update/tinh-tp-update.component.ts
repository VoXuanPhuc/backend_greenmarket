import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize } from 'rxjs/operators';

import { ITinhTP, TinhTP } from '../tinh-tp.model';
import { TinhTPService } from '../service/tinh-tp.service';

@Component({
  selector: 'jhi-tinh-tp-update',
  templateUrl: './tinh-tp-update.component.html',
})
export class TinhTPUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    ten: [],
  });

  constructor(protected tinhTPService: TinhTPService, protected activatedRoute: ActivatedRoute, protected fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ tinhTP }) => {
      this.updateForm(tinhTP);
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const tinhTP = this.createFromForm();
    if (tinhTP.id !== undefined) {
      this.subscribeToSaveResponse(this.tinhTPService.update(tinhTP));
    } else {
      this.subscribeToSaveResponse(this.tinhTPService.create(tinhTP));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITinhTP>>): void {
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

  protected updateForm(tinhTP: ITinhTP): void {
    this.editForm.patchValue({
      id: tinhTP.id,
      ten: tinhTP.ten,
    });
  }

  protected createFromForm(): ITinhTP {
    return {
      ...new TinhTP(),
      id: this.editForm.get(['id'])!.value,
      ten: this.editForm.get(['ten'])!.value,
    };
  }
}
