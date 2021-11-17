import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { YeuThichService } from '../service/yeu-thich.service';

import { YeuThichComponent } from './yeu-thich.component';

describe('Component Tests', () => {
  describe('YeuThich Management Component', () => {
    let comp: YeuThichComponent;
    let fixture: ComponentFixture<YeuThichComponent>;
    let service: YeuThichService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [YeuThichComponent],
      })
        .overrideTemplate(YeuThichComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(YeuThichComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(YeuThichService);

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
      expect(comp.yeuThiches?.[0]).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
