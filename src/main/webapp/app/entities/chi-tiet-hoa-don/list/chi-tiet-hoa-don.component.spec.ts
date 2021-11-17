import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';

import { ChiTietHoaDonComponent } from './chi-tiet-hoa-don.component';

describe('Component Tests', () => {
  describe('ChiTietHoaDon Management Component', () => {
    let comp: ChiTietHoaDonComponent;
    let fixture: ComponentFixture<ChiTietHoaDonComponent>;
    let service: ChiTietHoaDonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChiTietHoaDonComponent],
      })
        .overrideTemplate(ChiTietHoaDonComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChiTietHoaDonComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ChiTietHoaDonService);

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
      expect(comp.chiTietHoaDons?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
