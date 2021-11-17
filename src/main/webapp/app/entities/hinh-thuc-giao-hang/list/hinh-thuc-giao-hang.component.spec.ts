import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HinhThucGiaoHangService } from '../service/hinh-thuc-giao-hang.service';

import { HinhThucGiaoHangComponent } from './hinh-thuc-giao-hang.component';

describe('Component Tests', () => {
  describe('HinhThucGiaoHang Management Component', () => {
    let comp: HinhThucGiaoHangComponent;
    let fixture: ComponentFixture<HinhThucGiaoHangComponent>;
    let service: HinhThucGiaoHangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HinhThucGiaoHangComponent],
      })
        .overrideTemplate(HinhThucGiaoHangComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HinhThucGiaoHangComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(HinhThucGiaoHangService);

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
      expect(comp.hinhThucGiaoHangs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
