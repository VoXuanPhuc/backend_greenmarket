import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { AnhNongSanComponent } from '../list/anh-nong-san.component';
import { AnhNongSanDetailComponent } from '../detail/anh-nong-san-detail.component';
import { AnhNongSanUpdateComponent } from '../update/anh-nong-san-update.component';
import { AnhNongSanRoutingResolveService } from './anh-nong-san-routing-resolve.service';

const anhNongSanRoute: Routes = [
  {
    path: '',
    component: AnhNongSanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: AnhNongSanDetailComponent,
    resolve: {
      anhNongSan: AnhNongSanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: AnhNongSanUpdateComponent,
    resolve: {
      anhNongSan: AnhNongSanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: AnhNongSanUpdateComponent,
    resolve: {
      anhNongSan: AnhNongSanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(anhNongSanRoute)],
  exports: [RouterModule],
})
export class AnhNongSanRoutingModule {}
