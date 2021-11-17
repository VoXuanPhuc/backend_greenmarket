import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { NhaCungCapDetailComponent } from './nha-cung-cap-detail.component';

describe('Component Tests', () => {
  describe('NhaCungCap Management Detail Component', () => {
    let comp: NhaCungCapDetailComponent;
    let fixture: ComponentFixture<NhaCungCapDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [NhaCungCapDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ nhaCungCap: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(NhaCungCapDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(NhaCungCapDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load nhaCungCap on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.nhaCungCap).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
