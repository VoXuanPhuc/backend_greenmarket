import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { PhuongThucTTDetailComponent } from './phuong-thuc-tt-detail.component';

describe('Component Tests', () => {
  describe('PhuongThucTT Management Detail Component', () => {
    let comp: PhuongThucTTDetailComponent;
    let fixture: ComponentFixture<PhuongThucTTDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [PhuongThucTTDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ phuongThucTT: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(PhuongThucTTDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(PhuongThucTTDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load phuongThucTT on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.phuongThucTT).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
