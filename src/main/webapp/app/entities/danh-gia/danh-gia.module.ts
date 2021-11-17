import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DanhGiaComponent } from './list/danh-gia.component';
import { DanhGiaDetailComponent } from './detail/danh-gia-detail.component';
import { DanhGiaUpdateComponent } from './update/danh-gia-update.component';
import { DanhGiaDeleteDialogComponent } from './delete/danh-gia-delete-dialog.component';
import { DanhGiaRoutingModule } from './route/danh-gia-routing.module';

@NgModule({
  imports: [SharedModule, DanhGiaRoutingModule],
  declarations: [DanhGiaComponent, DanhGiaDetailComponent, DanhGiaUpdateComponent, DanhGiaDeleteDialogComponent],
  entryComponents: [DanhGiaDeleteDialogComponent],
})
export class DanhGiaModule {}
