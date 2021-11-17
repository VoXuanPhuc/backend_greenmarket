import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { DanhMucComponent } from './list/danh-muc.component';
import { DanhMucDetailComponent } from './detail/danh-muc-detail.component';
import { DanhMucUpdateComponent } from './update/danh-muc-update.component';
import { DanhMucDeleteDialogComponent } from './delete/danh-muc-delete-dialog.component';
import { DanhMucRoutingModule } from './route/danh-muc-routing.module';

@NgModule({
  imports: [SharedModule, DanhMucRoutingModule],
  declarations: [DanhMucComponent, DanhMucDetailComponent, DanhMucUpdateComponent, DanhMucDeleteDialogComponent],
  entryComponents: [DanhMucDeleteDialogComponent],
})
export class DanhMucModule {}
