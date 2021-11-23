jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { KhachHangService } from '../service/khach-hang.service';
import { IKhachHang, KhachHang } from '../khach-hang.model';
import { IXaPhuong } from 'app/entities/xa-phuong/xa-phuong.model';
import { XaPhuongService } from 'app/entities/xa-phuong/service/xa-phuong.service';

import { KhachHangUpdateComponent } from './khach-hang-update.component';

describe('Component Tests', () => {
  describe('KhachHang Management Update Component', () => {
    let comp: KhachHangUpdateComponent;
    let fixture: ComponentFixture<KhachHangUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let khachHangService: KhachHangService;
    let xaPhuongService: XaPhuongService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [KhachHangUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(KhachHangUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(KhachHangUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      khachHangService = TestBed.inject(KhachHangService);
      xaPhuongService = TestBed.inject(XaPhuongService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call XaPhuong query and add missing value', () => {
        const khachHang: IKhachHang = { id: 456 };
        const xa: IXaPhuong = { id: 86981 };
        khachHang.xa = xa;

        const xaPhuongCollection: IXaPhuong[] = [{ id: 97218 }];
        jest.spyOn(xaPhuongService, 'query').mockReturnValue(of(new HttpResponse({ body: xaPhuongCollection })));
        const additionalXaPhuongs = [xa];
        const expectedCollection: IXaPhuong[] = [...additionalXaPhuongs, ...xaPhuongCollection];
        jest.spyOn(xaPhuongService, 'addXaPhuongToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ khachHang });
        comp.ngOnInit();

        expect(xaPhuongService.query).toHaveBeenCalled();
        expect(xaPhuongService.addXaPhuongToCollectionIfMissing).toHaveBeenCalledWith(xaPhuongCollection, ...additionalXaPhuongs);
        expect(comp.xaPhuongsSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const khachHang: IKhachHang = { id: 456 };
        const xa: IXaPhuong = { id: 92207 };
        khachHang.xa = xa;

        activatedRoute.data = of({ khachHang });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(khachHang));
        expect(comp.xaPhuongsSharedCollection).toContain(xa);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<KhachHang>>();
        const khachHang = { id: 123 };
        jest.spyOn(khachHangService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ khachHang });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: khachHang }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(khachHangService.update).toHaveBeenCalledWith(khachHang);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<KhachHang>>();
        const khachHang = new KhachHang();
        jest.spyOn(khachHangService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ khachHang });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: khachHang }));
        saveSubject.complete();

        // THEN
        expect(khachHangService.create).toHaveBeenCalledWith(khachHang);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<KhachHang>>();
        const khachHang = { id: 123 };
        jest.spyOn(khachHangService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ khachHang });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(khachHangService.update).toHaveBeenCalledWith(khachHang);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackXaPhuongById', () => {
        it('Should return tracked XaPhuong primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackXaPhuongById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
