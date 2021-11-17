import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DanhGiaDetailComponent } from './danh-gia-detail.component';

describe('Component Tests', () => {
  describe('DanhGia Management Detail Component', () => {
    let comp: DanhGiaDetailComponent;
    let fixture: ComponentFixture<DanhGiaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DanhGiaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ danhGia: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DanhGiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DanhGiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load danhGia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.danhGia).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
