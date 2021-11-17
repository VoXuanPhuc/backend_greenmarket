import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NhaCungCapService } from '../service/nha-cung-cap.service';

import { NhaCungCapComponent } from './nha-cung-cap.component';

describe('Component Tests', () => {
  describe('NhaCungCap Management Component', () => {
    let comp: NhaCungCapComponent;
    let fixture: ComponentFixture<NhaCungCapComponent>;
    let service: NhaCungCapService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NhaCungCapComponent],
      })
        .overrideTemplate(NhaCungCapComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NhaCungCapComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NhaCungCapService);

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
      expect(comp.nhaCungCaps?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
