jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { YeuThichService } from '../service/yeu-thich.service';
import { IYeuThich, YeuThich } from '../yeu-thich.model';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { KhachHangService } from 'app/entities/khach-hang/service/khach-hang.service';

import { YeuThichUpdateComponent } from './yeu-thich-update.component';

describe('Component Tests', () => {
  describe('YeuThich Management Update Component', () => {
    let comp: YeuThichUpdateComponent;
    let fixture: ComponentFixture<YeuThichUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let yeuThichService: YeuThichService;
    let nongSanService: NongSanService;
    let khachHangService: KhachHangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [YeuThichUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(YeuThichUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(YeuThichUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      yeuThichService = TestBed.inject(YeuThichService);
      nongSanService = TestBed.inject(NongSanService);
      khachHangService = TestBed.inject(KhachHangService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call NongSan query and add missing value', () => {
        const yeuThich: IYeuThich = { id: 456 };
        const nongsan: INongSan = { id: 14463 };
        yeuThich.nongsan = nongsan;

        const nongSanCollection: INongSan[] = [{ id: 85757 }];
        jest.spyOn(nongSanService, 'query').mockReturnValue(of(new HttpResponse({ body: nongSanCollection })));
        const additionalNongSans = [nongsan];
        const expectedCollection: INongSan[] = [...additionalNongSans, ...nongSanCollection];
        jest.spyOn(nongSanService, 'addNongSanToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ yeuThich });
        comp.ngOnInit();

        expect(nongSanService.query).toHaveBeenCalled();
        expect(nongSanService.addNongSanToCollectionIfMissing).toHaveBeenCalledWith(nongSanCollection, ...additionalNongSans);
        expect(comp.nongSansSharedCollection).toEqual(expectedCollection);
      });

      it('Should call KhachHang query and add missing value', () => {
        const yeuThich: IYeuThich = { id: 456 };
        const khachhang: IKhachHang = { id: 69253 };
        yeuThich.khachhang = khachhang;

        const khachHangCollection: IKhachHang[] = [{ id: 44814 }];
        jest.spyOn(khachHangService, 'query').mockReturnValue(of(new HttpResponse({ body: khachHangCollection })));
        const additionalKhachHangs = [khachhang];
        const expectedCollection: IKhachHang[] = [...additionalKhachHangs, ...khachHangCollection];
        jest.spyOn(khachHangService, 'addKhachHangToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ yeuThich });
        comp.ngOnInit();

        expect(khachHangService.query).toHaveBeenCalled();
        expect(khachHangService.addKhachHangToCollectionIfMissing).toHaveBeenCalledWith(khachHangCollection, ...additionalKhachHangs);
        expect(comp.khachHangsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const yeuThich: IYeuThich = { id: 456 };
        const nongsan: INongSan = { id: 99501 };
        yeuThich.nongsan = nongsan;
        const khachhang: IKhachHang = { id: 42576 };
        yeuThich.khachhang = khachhang;

        activatedRoute.data = of({ yeuThich });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(yeuThich));
        expect(comp.nongSansSharedCollection).toContain(nongsan);
        expect(comp.khachHangsSharedCollection).toContain(khachhang);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<YeuThich>>();
        const yeuThich = { id: 123 };
        jest.spyOn(yeuThichService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ yeuThich });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: yeuThich }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(yeuThichService.update).toHaveBeenCalledWith(yeuThich);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<YeuThich>>();
        const yeuThich = new YeuThich();
        jest.spyOn(yeuThichService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ yeuThich });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: yeuThich }));
        saveSubject.complete();

        // THEN
        expect(yeuThichService.create).toHaveBeenCalledWith(yeuThich);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<YeuThich>>();
        const yeuThich = { id: 123 };
        jest.spyOn(yeuThichService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ yeuThich });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(yeuThichService.update).toHaveBeenCalledWith(yeuThich);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackNongSanById', () => {
        it('Should return tracked NongSan primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackNongSanById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackKhachHangById', () => {
        it('Should return tracked KhachHang primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackKhachHangById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
