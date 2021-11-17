import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { HuyenQuanService } from '../service/huyen-quan.service';

import { HuyenQuanComponent } from './huyen-quan.component';

describe('Component Tests', () => {
  describe('HuyenQuan Management Component', () => {
    let comp: HuyenQuanComponent;
    let fixture: ComponentFixture<HuyenQuanComponent>;
    let service: HuyenQuanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HuyenQuanComponent],
      })
        .overrideTemplate(HuyenQuanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HuyenQuanComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(HuyenQuanService);

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
      expect(comp.huyenQuans?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
