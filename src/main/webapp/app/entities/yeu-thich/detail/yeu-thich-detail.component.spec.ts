import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YeuThichDetailComponent } from './yeu-thich-detail.component';

describe('Component Tests', () => {
  describe('YeuThich Management Detail Component', () => {
    let comp: YeuThichDetailComponent;
    let fixture: ComponentFixture<YeuThichDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [YeuThichDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ yeuThich: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(YeuThichDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(YeuThichDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load yeuThich on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.yeuThich).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
