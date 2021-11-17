import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { NongSanComponent } from '../list/nong-san.component';
import { NongSanDetailComponent } from '../detail/nong-san-detail.component';
import { NongSanUpdateComponent } from '../update/nong-san-update.component';
import { NongSanRoutingResolveService } from './nong-san-routing-resolve.service';

const nongSanRoute: Routes = [
  {
    path: '',
    component: NongSanComponent,
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: NongSanDetailComponent,
    resolve: {
      nongSan: NongSanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: NongSanUpdateComponent,
    resolve: {
      nongSan: NongSanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: NongSanUpdateComponent,
    resolve: {
      nongSan: NongSanRoutingResolveService,
    },
    canActivate: [UserRouteAccessService],
  },
];

@NgModule({
  imports: [RouterModule.forChild(nongSanRoute)],
  exports: [RouterModule],
})
export class NongSanRoutingModule {}
