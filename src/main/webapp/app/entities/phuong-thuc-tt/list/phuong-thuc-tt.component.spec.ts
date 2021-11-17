import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { PhuongThucTTService } from '../service/phuong-thuc-tt.service';

import { PhuongThucTTComponent } from './phuong-thuc-tt.component';

describe('Component Tests', () => {
  describe('PhuongThucTT Management Component', () => {
    let comp: PhuongThucTTComponent;
    let fixture: ComponentFixture<PhuongThucTTComponent>;
    let service: PhuongThucTTService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [PhuongThucTTComponent],
      })
        .overrideTemplate(PhuongThucTTComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(PhuongThucTTComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(PhuongThucTTService);

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
      expect(comp.phuongThucTTS?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
