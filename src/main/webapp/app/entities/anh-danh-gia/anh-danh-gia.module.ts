import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnhDanhGiaComponent } from './list/anh-danh-gia.component';
import { AnhDanhGiaDetailComponent } from './detail/anh-danh-gia-detail.component';
import { AnhDanhGiaUpdateComponent } from './update/anh-danh-gia-update.component';
import { AnhDanhGiaDeleteDialogComponent } from './delete/anh-danh-gia-delete-dialog.component';
import { AnhDanhGiaRoutingModule } from './route/anh-danh-gia-routing.module';

@NgModule({
  imports: [SharedModule, AnhDanhGiaRoutingModule],
  declarations: [AnhDanhGiaComponent, AnhDanhGiaDetailComponent, AnhDanhGiaUpdateComponent, AnhDanhGiaDeleteDialogComponent],
  entryComponents: [AnhDanhGiaDeleteDialogComponent],
})
export class AnhDanhGiaModule {}
