import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AnhNongSanDetailComponent } from './anh-nong-san-detail.component';

describe('Component Tests', () => {
  describe('AnhNongSan Management Detail Component', () => {
    let comp: AnhNongSanDetailComponent;
    let fixture: ComponentFixture<AnhNongSanDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [AnhNongSanDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ anhNongSan: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(AnhNongSanDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(AnhNongSanDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load anhNongSan on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.anhNongSan).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
