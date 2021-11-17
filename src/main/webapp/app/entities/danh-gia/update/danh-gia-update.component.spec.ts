jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { DanhGiaService } from '../service/danh-gia.service';
import { IDanhGia, DanhGia } from '../danh-gia.model';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';
import { IKhachHang } from 'app/entities/khach-hang/khach-hang.model';
import { KhachHangService } from 'app/entities/khach-hang/service/khach-hang.service';

import { DanhGiaUpdateComponent } from './danh-gia-update.component';

describe('Component Tests', () => {
  describe('DanhGia Management Update Component', () => {
    let comp: DanhGiaUpdateComponent;
    let fixture: ComponentFixture<DanhGiaUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let danhGiaService: DanhGiaService;
    let nongSanService: NongSanService;
    let khachHangService: KhachHangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [DanhGiaUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(DanhGiaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(DanhGiaUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      danhGiaService = TestBed.inject(DanhGiaService);
      nongSanService = TestBed.inject(NongSanService);
      khachHangService = TestBed.inject(KhachHangService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call NongSan query and add missing value', () => {
        const danhGia: IDanhGia = { id: 456 };
        const nongsan: INongSan = { id: 37646 };
        danhGia.nongsan = nongsan;

        const nongSanCollection: INongSan[] = [{ id: 32420 }];
        jest.spyOn(nongSanService, 'query').mockReturnValue(of(new HttpResponse({ body: nongSanCollection })));
        const additionalNongSans = [nongsan];
        const expectedCollection: INongSan[] = [...additionalNongSans, ...nongSanCollection];
        jest.spyOn(nongSanService, 'addNongSanToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ danhGia });
        comp.ngOnInit();

        expect(nongSanService.query).toHaveBeenCalled();
        expect(nongSanService.addNongSanToCollectionIfMissing).toHaveBeenCalledWith(nongSanCollection, ...additionalNongSans);
        expect(comp.nongSansSharedCollection).toEqual(expectedCollection);
      });

      it('Should call KhachHang query and add missing value', () => {
        const danhGia: IDanhGia = { id: 456 };
        const khachhang: IKhachHang = { id: 46520 };
        danhGia.khachhang = khachhang;

        const khachHangCollection: IKhachHang[] = [{ id: 26269 }];
        jest.spyOn(khachHangService, 'query').mockReturnValue(of(new HttpResponse({ body: khachHangCollection })));
        const additionalKhachHangs = [khachhang];
        const expectedCollection: IKhachHang[] = [...additionalKhachHangs, ...khachHangCollection];
        jest.spyOn(khachHangService, 'addKhachHangToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ danhGia });
        comp.ngOnInit();

        expect(khachHangService.query).toHaveBeenCalled();
        expect(khachHangService.addKhachHangToCollectionIfMissing).toHaveBeenCalledWith(khachHangCollection, ...additionalKhachHangs);
        expect(comp.khachHangsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const danhGia: IDanhGia = { id: 456 };
        const nongsan: INongSan = { id: 20577 };
        danhGia.nongsan = nongsan;
        const khachhang: IKhachHang = { id: 21261 };
        danhGia.khachhang = khachhang;

        activatedRoute.data = of({ danhGia });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(danhGia));
        expect(comp.nongSansSharedCollection).toContain(nongsan);
        expect(comp.khachHangsSharedCollection).toContain(khachhang);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DanhGia>>();
        const danhGia = { id: 123 };
        jest.spyOn(danhGiaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ danhGia });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: danhGia }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(danhGiaService.update).toHaveBeenCalledWith(danhGia);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DanhGia>>();
        const danhGia = new DanhGia();
        jest.spyOn(danhGiaService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ danhGia });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: danhGia }));
        saveSubject.complete();

        // THEN
        expect(danhGiaService.create).toHaveBeenCalledWith(danhGia);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<DanhGia>>();
        const danhGia = { id: 123 };
        jest.spyOn(danhGiaService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ danhGia });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(danhGiaService.update).toHaveBeenCalledWith(danhGia);
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
