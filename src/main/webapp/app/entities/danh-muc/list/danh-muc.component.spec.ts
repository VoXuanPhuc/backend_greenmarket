import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { DanhMucService } from '../service/danh-muc.service';

import { DanhMucComponent } from './danh-muc.component';

describe('Component Tests', () => {
  describe('DanhMuc Management Component', () => {
    let comp: DanhMucComponent;
    let fixture: ComponentFixture<DanhMucComponent>;
    let service: DanhMucService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DanhMucComponent],
      })
        .overrideTemplate(DanhMucComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DanhMucComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(DanhMucService);

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
      expect(comp.danhMucs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
