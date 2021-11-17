import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { TinhTPComponent } from '../list/tinh-tp.component';
import { TinhTPDetailComponent } from '../detail/tinh-tp-detail.component';
import { TinhTPUpdateComponent } from '../update/tinh-tp-update.component';
import { TinhTPRoutingResolveService } from './tinh-tp-routing-resolve.service';

const tinhTPRoute: Routes = [
  {
    path: '',
    component: TinhTPComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: TinhTPDetailComponent,
    resolve: {
      tinhTP: TinhTPRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: TinhTPUpdateComponent,
    resolve: {
      tinhTP: TinhTPRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: TinhTPUpdateComponent,
    resolve: {
      tinhTP: TinhTPRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(tinhTPRoute)],
  exports: [RouterModule],
})
export class TinhTPRoutingModule {}
