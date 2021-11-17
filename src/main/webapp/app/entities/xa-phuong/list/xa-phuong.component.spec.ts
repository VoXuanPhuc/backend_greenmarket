import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { XaPhuongService } from '../service/xa-phuong.service';

import { XaPhuongComponent } from './xa-phuong.component';

describe('Component Tests', () => {
  describe('XaPhuong Management Component', () => {
    let comp: XaPhuongComponent;
    let fixture: ComponentFixture<XaPhuongComponent>;
    let service: XaPhuongService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [XaPhuongComponent],
      })
        .overrideTemplate(XaPhuongComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(XaPhuongComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(XaPhuongService);

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
      expect(comp.xaPhuongs?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
