import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { NongSanComponent } from './list/nong-san.component';
import { NongSanDetailComponent } from './detail/nong-san-detail.component';
import { NongSanUpdateComponent } from './update/nong-san-update.component';
import { NongSanDeleteDialogComponent } from './delete/nong-san-delete-dialog.component';
import { NongSanRoutingModule } from './route/nong-san-routing.module';

@NgModule({
  imports: [SharedModule, NongSanRoutingModule],
  declarations: [NongSanComponent, NongSanDetailComponent, NongSanUpdateComponent, NongSanDeleteDialogComponent],
  entryComponents: [NongSanDeleteDialogComponent],
})
export class NongSanModule {}
