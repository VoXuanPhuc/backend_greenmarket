import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ChiTietHoaDonDetailComponent } from './chi-tiet-hoa-don-detail.component';

describe('Component Tests', () => {
  describe('ChiTietHoaDon Management Detail Component', () => {
    let comp: ChiTietHoaDonDetailComponent;
    let fixture: ComponentFixture<ChiTietHoaDonDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [ChiTietHoaDonDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ chiTietHoaDon: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(ChiTietHoaDonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChiTietHoaDonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load chiTietHoaDon on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.chiTietHoaDon).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
