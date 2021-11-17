import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { TinhTPComponent } from './list/tinh-tp.component';
import { TinhTPDetailComponent } from './detail/tinh-tp-detail.component';
import { TinhTPUpdateComponent } from './update/tinh-tp-update.component';
import { TinhTPDeleteDialogComponent } from './delete/tinh-tp-delete-dialog.component';
import { TinhTPRoutingModule } from './route/tinh-tp-routing.module';

@NgModule({
  imports: [SharedModule, TinhTPRoutingModule],
  declarations: [TinhTPComponent, TinhTPDetailComponent, TinhTPUpdateComponent, TinhTPDeleteDialogComponent],
  entryComponents: [TinhTPDeleteDialogComponent],
})
export class TinhTPModule {}
