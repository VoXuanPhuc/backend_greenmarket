import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { AnhNongSanService } from '../service/anh-nong-san.service';

import { AnhNongSanComponent } from './anh-nong-san.component';

describe('Component Tests', () => {
  describe('AnhNongSan Management Component', () => {
    let comp: AnhNongSanComponent;
    let fixture: ComponentFixture<AnhNongSanComponent>;
    let service: AnhNongSanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnhNongSanComponent],
      })
        .overrideTemplate(AnhNongSanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnhNongSanComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(AnhNongSanService);

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
      expect(comp.anhNongSans?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
