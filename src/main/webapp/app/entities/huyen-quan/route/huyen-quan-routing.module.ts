import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { HuyenQuanComponent } from '../list/huyen-quan.component';
import { HuyenQuanDetailComponent } from '../detail/huyen-quan-detail.component';
import { HuyenQuanUpdateComponent } from '../update/huyen-quan-update.component';
import { HuyenQuanRoutingResolveService } from './huyen-quan-routing-resolve.service';

const huyenQuanRoute: Routes = [
  {
    path: '',
    component: HuyenQuanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: HuyenQuanDetailComponent,
    resolve: {
      huyenQuan: HuyenQuanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: HuyenQuanUpdateComponent,
    resolve: {
      huyenQuan: HuyenQuanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: HuyenQuanUpdateComponent,
    resolve: {
      huyenQuan: HuyenQuanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(huyenQuanRoute)],
  exports: [RouterModule],
})
export class HuyenQuanRoutingModule {}
