import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { HuyenQuanDetailComponent } from './huyen-quan-detail.component';

describe('Component Tests', () => {
  describe('HuyenQuan Management Detail Component', () => {
    let comp: HuyenQuanDetailComponent;
    let fixture: ComponentFixture<HuyenQuanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [HuyenQuanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ huyenQuan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(HuyenQuanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(HuyenQuanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load huyenQuan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.huyenQuan).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
