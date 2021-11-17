import { NgModule } from '@angular/core';
import { SharedModule } from 'app/shared/shared.module';
import { AnhNongSanComponent } from './list/anh-nong-san.component';
import { AnhNongSanDetailComponent } from './detail/anh-nong-san-detail.component';
import { AnhNongSanUpdateComponent } from './update/anh-nong-san-update.component';
import { AnhNongSanDeleteDialogComponent } from './delete/anh-nong-san-delete-dialog.component';
import { AnhNongSanRoutingModule } from './route/anh-nong-san-routing.module';

@NgModule({
  imports: [SharedModule, AnhNongSanRoutingModule],
  declarations: [AnhNongSanComponent, AnhNongSanDetailComponent, AnhNongSanUpdateComponent, AnhNongSanDeleteDialogComponent],
  entryComponents: [AnhNongSanDeleteDialogComponent],
})
export class AnhNongSanModule {}
