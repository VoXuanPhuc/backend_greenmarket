import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HinhThucGiaoHangDetailComponent } from './hinh-thuc-giao-hang-detail.component';

describe('Component Tests', () => {
  describe('HinhThucGiaoHang Management Detail Component', () => {
    let comp: HinhThucGiaoHangDetailComponent;
    let fixture: ComponentFixture<HinhThucGiaoHangDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HinhThucGiaoHangDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ hinhThucGiaoHang: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HinhThucGiaoHangDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HinhThucGiaoHangDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hinhThucGiaoHang on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hinhThucGiaoHang).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
