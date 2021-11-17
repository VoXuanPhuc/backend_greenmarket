import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { XaPhuongDetailComponent } from './xa-phuong-detail.component';

describe('Component Tests', () => {
  describe('XaPhuong Management Detail Component', () => {
    let comp: XaPhuongDetailComponent;
    let fixture: ComponentFixture<XaPhuongDetailComponent>;

    beforeEach(() => {
      TestBed.configureTestingModule({
        declarations: [XaPhuongDetailComponent],
        providers: [
          {
            provide: ActivatedRoute,
            useValue: { data: of({ xaPhuong: { id: 123 } }) },
          },
        ],
      })
        .overrideTemplate(XaPhuongDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(XaPhuongDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should load xaPhuong on init', () => {
        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.xaPhuong).toEqual(expect.objectContaining({ id: 123 }));
      });
    });
  });
});
