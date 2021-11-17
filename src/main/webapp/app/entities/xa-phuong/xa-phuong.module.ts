import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { XaPhuongComponent } from './list/xa-phuong.component';
import { XaPhuongDetailComponent } from './detail/xa-phuong-detail.component';
import { XaPhuongUpdateComponent } from './update/xa-phuong-update.component';
import { XaPhuongDeleteDialogComponent } from './delete/xa-phuong-delete-dialog.component';
import { XaPhuongRoutingModule } from './route/xa-phuong-routing.module';

@NgModule({
  imports: [SharedModule, XaPhuongRoutingModule],
  declarations: [XaPhuongComponent, XaPhuongDetailComponent, XaPhuongUpdateComponent, XaPhuongDeleteDialogComponent],
  entryComponents: [XaPhuongDeleteDialogComponent],
})
export class XaPhuongModule {}
