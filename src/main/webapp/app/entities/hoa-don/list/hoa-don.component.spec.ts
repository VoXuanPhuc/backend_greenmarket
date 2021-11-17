import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HoaDonService } from '../service/hoa-don.service';

import { HoaDonComponent } from './hoa-don.component';

describe('Component Tests', () => {
  describe('HoaDon Management Component', () => {
    let comp: HoaDonComponent;
    let fixture: ComponentFixture<HoaDonComponent>;
    let service: HoaDonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HoaDonComponent],
      })
        .overrideTemplate(HoaDonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HoaDonComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(HoaDonService);

      const headers = new HttpHeaders().append('link', 'link;link');
      jest.spyOn(service, 'query').mockReturnValue(
        of(
          new HttpResponse({
            body: [{ id: 123 }],
            headers,
          })
        )
      );
    });

    it('Should call load all on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(service.query).toHaveBeenCalled();
      expect(comp.hoaDons?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
