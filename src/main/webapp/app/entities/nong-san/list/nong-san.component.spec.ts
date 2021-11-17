import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { NongSanService } from '../service/nong-san.service';

import { NongSanComponent } from './nong-san.component';

describe('Component Tests', () => {
  describe('NongSan Management Component', () => {
    let comp: NongSanComponent;
    let fixture: ComponentFixture<NongSanComponent>;
    let service: NongSanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [NongSanComponent],
      })
        .overrideTemplate(NongSanComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(NongSanComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(NongSanService);

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
      expect(comp.nongSans?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
