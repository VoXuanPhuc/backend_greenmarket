import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AnhDanhGiaService } from '../service/anh-danh-gia.service';

import { AnhDanhGiaComponent } from './anh-danh-gia.component';

describe('Component Tests', () => {
  describe('AnhDanhGia Management Component', () => {
    let comp: AnhDanhGiaComponent;
    let fixture: ComponentFixture<AnhDanhGiaComponent>;
    let service: AnhDanhGiaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnhDanhGiaComponent],
      })
        .overrideTemplate(AnhDanhGiaComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnhDanhGiaComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AnhDanhGiaService);

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
      expect(comp.anhDanhGias?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
