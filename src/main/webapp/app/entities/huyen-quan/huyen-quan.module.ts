import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { HuyenQuanComponent } from './list/huyen-quan.component';
import { HuyenQuanDetailComponent } from './detail/huyen-quan-detail.component';
import { HuyenQuanUpdateComponent } from './update/huyen-quan-update.component';
import { HuyenQuanDeleteDialogComponent } from './delete/huyen-quan-delete-dialog.component';
import { HuyenQuanRoutingModule } from './route/huyen-quan-routing.module';

@NgModule({
  imports: [SharedModule, HuyenQuanRoutingModule],
  declarations: [HuyenQuanComponent, HuyenQuanDetailComponent, HuyenQuanUpdateComponent, HuyenQuanDeleteDialogComponent],
  entryComponents: [HuyenQuanDeleteDialogComponent],
})
export class HuyenQuanModule {}
