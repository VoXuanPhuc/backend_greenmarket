jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';
import { IChiTietHoaDon, ChiTietHoaDon } from '../chi-tiet-hoa-don.model';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';
import { IHoaDon } from 'app/entities/hoa-don/hoa-don.model';
import { HoaDonService } from 'app/entities/hoa-don/service/hoa-don.service';

import { ChiTietHoaDonUpdateComponent } from './chi-tiet-hoa-don-update.component';

describe('Component Tests', () => {
  describe('ChiTietHoaDon Management Update Component', () => {
    let comp: ChiTietHoaDonUpdateComponent;
    let fixture: ComponentFixture<ChiTietHoaDonUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let chiTietHoaDonService: ChiTietHoaDonService;
    let nongSanService: NongSanService;
    let hoaDonService: HoaDonService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChiTietHoaDonUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(ChiTietHoaDonUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(ChiTietHoaDonUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      chiTietHoaDonService = TestBed.inject(ChiTietHoaDonService);
      nongSanService = TestBed.inject(NongSanService);
      hoaDonService = TestBed.inject(HoaDonService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call NongSan query and add missing value', () => {
        const chiTietHoaDon: IChiTietHoaDon = { id: 456 };
        const nongsan: INongSan = { id: 26106 };
        chiTietHoaDon.nongsan = nongsan;

        const nongSanCollection: INongSan[] = [{ id: 16288 }];
        jest.spyOn(nongSanService, 'query').mockReturnValue(of(new HttpResponse({ body: nongSanCollection })));
        const additionalNongSans = [nongsan];
        const expectedCollection: INongSan[] = [...additionalNongSans, ...nongSanCollection];
        jest.spyOn(nongSanService, 'addNongSanToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ chiTietHoaDon });
        comp.ngOnInit();

        expect(nongSanService.query).toHaveBeenCalled();
        expect(nongSanService.addNongSanToCollectionIfMissing).toHaveBeenCalledWith(nongSanCollection, ...additionalNongSans);
        expect(comp.nongSansSharedCollection).toEqual(expectedCollection);
      });

      it('Should call HoaDon query and add missing value', () => {
        const chiTietHoaDon: IChiTietHoaDon = { id: 456 };
        const hoadon: IHoaDon = { id: 13145 };
        chiTietHoaDon.hoadon = hoadon;

        const hoaDonCollection: IHoaDon[] = [{ id: 97427 }];
        jest.spyOn(hoaDonService, 'query').mockReturnValue(of(new HttpResponse({ body: hoaDonCollection })));
        const additionalHoaDons = [hoadon];
        const expectedCollection: IHoaDon[] = [...additionalHoaDons, ...hoaDonCollection];
        jest.spyOn(hoaDonService, 'addHoaDonToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ chiTietHoaDon });
        comp.ngOnInit();

        expect(hoaDonService.query).toHaveBeenCalled();
        expect(hoaDonService.addHoaDonToCollectionIfMissing).toHaveBeenCalledWith(hoaDonCollection, ...additionalHoaDons);
        expect(comp.hoaDonsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const chiTietHoaDon: IChiTietHoaDon = { id: 456 };
        const nongsan: INongSan = { id: 5447 };
        chiTietHoaDon.nongsan = nongsan;
        const hoadon: IHoaDon = { id: 56822 };
        chiTietHoaDon.hoadon = hoadon;

        activatedRoute.data = of({ chiTietHoaDon });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(chiTietHoaDon));
        expect(comp.nongSansSharedCollection).toContain(nongsan);
        expect(comp.hoaDonsSharedCollection).toContain(hoadon);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ChiTietHoaDon>>();
        const chiTietHoaDon = { id: 123 };
        jest.spyOn(chiTietHoaDonService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ chiTietHoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: chiTietHoaDon }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(chiTietHoaDonService.update).toHaveBeenCalledWith(chiTietHoaDon);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ChiTietHoaDon>>();
        const chiTietHoaDon = new ChiTietHoaDon();
        jest.spyOn(chiTietHoaDonService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ chiTietHoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: chiTietHoaDon }));
        saveSubject.complete();

        // THEN
        expect(chiTietHoaDonService.create).toHaveBeenCalledWith(chiTietHoaDon);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<ChiTietHoaDon>>();
        const chiTietHoaDon = { id: 123 };
        jest.spyOn(chiTietHoaDonService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ chiTietHoaDon });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(chiTietHoaDonService.update).toHaveBeenCalledWith(chiTietHoaDon);
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

      describe('trackHoaDonById', () => {
        it('Should return tracked HoaDon primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHoaDonById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
