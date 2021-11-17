import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TinhTPDetailComponent } from './tinh-tp-detail.component';

describe('Component Tests', () => {
  describe('TinhTP Management Detail Component', () => {
    let comp: TinhTPDetailComponent;
    let fixture: ComponentFixture<TinhTPDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [TinhTPDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ tinhTP: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(TinhTPDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TinhTPDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load tinhTP on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tinhTP).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
