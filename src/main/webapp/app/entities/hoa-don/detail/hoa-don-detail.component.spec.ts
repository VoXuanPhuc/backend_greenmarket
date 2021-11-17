import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HoaDonDetailComponent } from './hoa-don-detail.component';

describe('Component Tests', () => {
  describe('HoaDon Management Detail Component', () => {
    let comp: HoaDonDetailComponent;
    let fixture: ComponentFixture<HoaDonDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HoaDonDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ hoaDon: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HoaDonDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HoaDonDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load hoaDon on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.hoaDon).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
