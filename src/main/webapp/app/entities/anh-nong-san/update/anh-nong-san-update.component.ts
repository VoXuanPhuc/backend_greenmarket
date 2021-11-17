import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import { IAnhNongSan, AnhNongSan } from '../anh-nong-san.model';
import { AnhNongSanService } from '../service/anh-nong-san.service';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';

@Component({
  selector: 'jhi-anh-nong-san-update',
  templateUrl: './anh-nong-san-update.component.html',
})
export class AnhNongSanUpdateComponent implements OnInit {
  isSaving = false;

  nongSansSharedCollection: INongSan[] = [];

  editForm = this.fb.group({
    id: [],
    ten: [null, [Validators.required]],
    anhnongsan: [],
  });

  constructor(
    protected anhNongSanService: AnhNongSanService,
    protected nongSanService: NongSanService,
    protected activatedRoute: ActivatedRoute,
    protected fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ anhNongSan }) => {
      this.updateForm(anhNongSan);

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const anhNongSan = this.createFromForm();
    if (anhNongSan.id !== undefined) {
      this.subscribeToSaveResponse(this.anhNongSanService.update(anhNongSan));
    } else {
      this.subscribeToSaveResponse(this.anhNongSanService.create(anhNongSan));
    }
  }

  trackNongSanById(index: number, item: INongSan): number {
    return item.id!;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAnhNongSan>>): void {
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

  protected updateForm(anhNongSan: IAnhNongSan): void {
    this.editForm.patchValue({
      id: anhNongSan.id,
      ten: anhNongSan.ten,
      anhnongsan: anhNongSan.anhnongsan,
    });

    this.nongSansSharedCollection = this.nongSanService.addNongSanToCollectionIfMissing(
      this.nongSansSharedCollection,
      anhNongSan.anhnongsan
    );
  }

  protected loadRelationshipsOptions(): void {
    this.nongSanService
      .query()
      .pipe(map((res: HttpResponse<INongSan[]>) => res.body ?? []))
      .pipe(
        map((nongSans: INongSan[]) => this.nongSanService.addNongSanToCollectionIfMissing(nongSans, this.editForm.get('anhnongsan')!.value))
      )
      .subscribe((nongSans: INongSan[]) => (this.nongSansSharedCollection = nongSans));
  }

  protected createFromForm(): IAnhNongSan {
    return {
      ...new AnhNongSan(),
      id: this.editForm.get(['id'])!.value,
      ten: this.editForm.get(['ten'])!.value,
      anhnongsan: this.editForm.get(['anhnongsan'])!.value,
    };
  }
}
