import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { YeuThichComponent } from './list/yeu-thich.component';
import { YeuThichDetailComponent } from './detail/yeu-thich-detail.component';
import { YeuThichUpdateComponent } from './update/yeu-thich-update.component';
import { YeuThichDeleteDialogComponent } from './delete/yeu-thich-delete-dialog.component';
import { YeuThichRoutingModule } from './route/yeu-thich-routing.module';

@NgModule({
  imports: [SharedModule, YeuThichRoutingModule],
  declarations: [YeuThichComponent, YeuThichDetailComponent, YeuThichUpdateComponent, YeuThichDeleteDialogComponent],
  entryComponents: [YeuThichDeleteDialogComponent],
})
export class YeuThichModule {}
