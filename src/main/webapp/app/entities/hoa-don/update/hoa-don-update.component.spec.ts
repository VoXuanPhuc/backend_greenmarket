jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HoaDonService } from '../service/hoa-don.service';
import { IHoaDon, HoaDon } from '../hoa-don.model';
import { IPhuongThucTT } from 'app/entities/phuong-thuc-tt/phuong-thuc-tt.model';
import { PhuongThucTTService } from 'app/entities/phuong-thuc-tt/service/phuong-thuc-tt.service';
import { IHinhThucGiaoHang } from 'app/entities/hinh-thuc-giao-hang/hinh-thuc-giao-hang.model';
import { HinhThucGiaoHangService } from 'app/entities/hinh-thuc-giao-hang/service/hinh-thuc-giao-hang.service';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { KhachHangService } from 'app/entities/khach-hang/service/khach-hang.service';

import { HoaDonUpdateComponent } from './hoa-don-update.component';

describe('Component Tests', () => {
  describe('HoaDon Management Update Component', () => {
    let comp: HoaDonUpdateComponent;
    let fixture: ComponentFixture<HoaDonUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hoaDonService: HoaDonService;
    let phuongThucTTService: PhuongThucTTService;
    let hinhThucGiaoHangService: HinhThucGiaoHangService;
    let khachHangService: KhachHangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HoaDonUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HoaDonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HoaDonUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hoaDonService = TestBed.inject(HoaDonService);
      phuongThucTTService = TestBed.inject(PhuongThucTTService);
      hinhThucGiaoHangService = TestBed.inject(HinhThucGiaoHangService);
      khachHangService = TestBed.inject(KhachHangService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call PhuongThucTT query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const phuongthucTT: IPhuongThucTT = { id: 60277 };
        hoaDon.phuongthucTT = phuongthucTT;

        const phuongThucTTCollection: IPhuongThucTT[] = [{ id: 32056 }];
        jest.spyOn(phuongThucTTService, 'query').mockReturnValue(of(new HttpResponse({ body: phuongThucTTCollection })));
        const additionalPhuongThucTTS = [phuongthucTT];
        const expectedCollection: IPhuongThucTT[] = [...additionalPhuongThucTTS, ...phuongThucTTCollection];
        jest.spyOn(phuongThucTTService, 'addPhuongThucTTToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(phuongThucTTService.query).toHaveBeenCalled();
        expect(phuongThucTTService.addPhuongThucTTToCollectionIfMissing).toHaveBeenCalledWith(
          phuongThucTTCollection,
          ...additionalPhuongThucTTS
        );
        expect(comp.phuongThucTTSSharedCollection).toEqual(expectedCollection);
      });

      it('Should call HinhThucGiaoHang query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const phuongthucGH: IHinhThucGiaoHang = { id: 17849 };
        hoaDon.phuongthucGH = phuongthucGH;

        const hinhThucGiaoHangCollection: IHinhThucGiaoHang[] = [{ id: 43322 }];
        jest.spyOn(hinhThucGiaoHangService, 'query').mockReturnValue(of(new HttpResponse({ body: hinhThucGiaoHangCollection })));
        const additionalHinhThucGiaoHangs = [phuongthucGH];
        const expectedCollection: IHinhThucGiaoHang[] = [...additionalHinhThucGiaoHangs, ...hinhThucGiaoHangCollection];
        jest.spyOn(hinhThucGiaoHangService, 'addHinhThucGiaoHangToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(hinhThucGiaoHangService.query).toHaveBeenCalled();
        expect(hinhThucGiaoHangService.addHinhThucGiaoHangToCollectionIfMissing).toHaveBeenCalledWith(
          hinhThucGiaoHangCollection,
          ...additionalHinhThucGiaoHangs
        );
        expect(comp.hinhThucGiaoHangsSharedCollection).toEqual(expectedCollection);
      });

      it('Should call KhachHang query and add missing value', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const khachhang: IKhachHang = { id: 99401 };
        hoaDon.khachhang = khachhang;

        const khachHangCollection: IKhachHang[] = [{ id: 97819 }];
        jest.spyOn(khachHangService, 'query').mockReturnValue(of(new HttpResponse({ body: khachHangCollection })));
        const additionalKhachHangs = [khachhang];
        const expectedCollection: IKhachHang[] = [...additionalKhachHangs, ...khachHangCollection];
        jest.spyOn(khachHangService, 'addKhachHangToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(khachHangService.query).toHaveBeenCalled();
        expect(khachHangService.addKhachHangToCollectionIfMissing).toHaveBeenCalledWith(khachHangCollection, ...additionalKhachHangs);
        expect(comp.khachHangsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const hoaDon: IHoaDon = { id: 456 };
        const phuongthucTT: IPhuongThucTT = { id: 49107 };
        hoaDon.phuongthucTT = phuongthucTT;
        const phuongthucGH: IHinhThucGiaoHang = { id: 74318 };
        hoaDon.phuongthucGH = phuongthucGH;
        const khachhang: IKhachHang = { id: 20314 };
        hoaDon.khachhang = khachhang;

        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hoaDon));
        expect(comp.phuongThucTTSSharedCollection).toContain(phuongthucTT);
        expect(comp.hinhThucGiaoHangsSharedCollection).toContain(phuongthucGH);
        expect(comp.khachHangsSharedCollection).toContain(khachhang);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HoaDon>>();
        const hoaDon = { id: 123 };
        jest.spyOn(hoaDonService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hoaDon }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hoaDonService.update).toHaveBeenCalledWith(hoaDon);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HoaDon>>();
        const hoaDon = new HoaDon();
        jest.spyOn(hoaDonService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hoaDon }));
        saveSubject.complete();

        // THEN
        expect(hoaDonService.create).toHaveBeenCalledWith(hoaDon);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HoaDon>>();
        const hoaDon = { id: 123 };
        jest.spyOn(hoaDonService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ hoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hoaDonService.update).toHaveBeenCalledWith(hoaDon);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackPhuongThucTTById', () => {
        it('Should return tracked PhuongThucTT primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackPhuongThucTTById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });

      describe('trackHinhThucGiaoHangById', () => {
        it('Should return tracked HinhThucGiaoHang primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHinhThucGiaoHangById(0, entity);
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
