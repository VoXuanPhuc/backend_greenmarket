import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NongSanDetailComponent } from './nong-san-detail.component';

describe('Component Tests', () => {
  describe('NongSan Management Detail Component', () => {
    let comp: NongSanDetailComponent;
    let fixture: ComponentFixture<NongSanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NongSanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nongSan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NongSanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NongSanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nongSan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nongSan).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
