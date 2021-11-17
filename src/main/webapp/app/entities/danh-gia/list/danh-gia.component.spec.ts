import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DanhGiaService } from '../service/danh-gia.service';

import { DanhGiaComponent } from './danh-gia.component';

describe('Component Tests', () => {
  describe('DanhGia Management Component', () => {
    let comp: DanhGiaComponent;
    let fixture: ComponentFixture<DanhGiaComponent>;
    let service: DanhGiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DanhGiaComponent],
      })
        .overrideTemplate(DanhGiaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DanhGiaComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DanhGiaService);

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
      expect(comp.danhGias?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
