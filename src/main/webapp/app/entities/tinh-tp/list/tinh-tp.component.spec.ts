import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TinhTPService } from '../service/tinh-tp.service';

import { TinhTPComponent } from './tinh-tp.component';

describe('Component Tests', () => {
  describe('TinhTP Management Component', () => {
    let comp: TinhTPComponent;
    let fixture: ComponentFixture<TinhTPComponent>;
    let service: TinhTPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [TinhTPComponent],
      })
        .overrideTemplate(TinhTPComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TinhTPComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(TinhTPService);

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
      expect(comp.tinhTPS?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
