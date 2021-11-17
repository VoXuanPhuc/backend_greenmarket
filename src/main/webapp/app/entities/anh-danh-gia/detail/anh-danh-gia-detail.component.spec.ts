import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnhDanhGiaDetailComponent } from './anh-danh-gia-detail.component';

describe('Component Tests', () => {
  describe('AnhDanhGia Management Detail Component', () => {
    let comp: AnhDanhGiaDetailComponent;
    let fixture: ComponentFixture<AnhDanhGiaDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AnhDanhGiaDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ anhDanhGia: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AnhDanhGiaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnhDanhGiaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load anhDanhGia on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anhDanhGia).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
