import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { DanhMucDetailComponent } from './danh-muc-detail.component';

describe('Component Tests', () => {
  describe('DanhMuc Management Detail Component', () => {
    let comp: DanhMucDetailComponent;
    let fixture: ComponentFixture<DanhMucDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [DanhMucDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ danhMuc: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(DanhMucDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(DanhMucDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load danhMuc on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.danhMuc).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
