jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HinhThucGiaoHangService } from '../service/hinh-thuc-giao-hang.service';
import { IHinhThucGiaoHang, HinhThucGiaoHang } from '../hinh-thuc-giao-hang.model';

import { HinhThucGiaoHangUpdateComponent } from './hinh-thuc-giao-hang-update.component';

describe('Component Tests', () => {
  describe('HinhThucGiaoHang Management Update Component', () => {
    let comp: HinhThucGiaoHangUpdateComponent;
    let fixture: ComponentFixture<HinhThucGiaoHangUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let hinhThucGiaoHangService: HinhThucGiaoHangService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HinhThucGiaoHangUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HinhThucGiaoHangUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HinhThucGiaoHangUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      hinhThucGiaoHangService = TestBed.inject(HinhThucGiaoHangService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should update editForm', () => {
        const hinhThucGiaoHang: IHinhThucGiaoHang = { id: 456 };

        activatedRoute.data = of({ hinhThucGiaoHang });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(hinhThucGiaoHang));
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HinhThucGiaoHang>>();
        const hinhThucGiaoHang = { id: 123 };
        jest.spyOn(hinhThucGiaoHangService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ hinhThucGiaoHang });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hinhThucGiaoHang }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(hinhThucGiaoHangService.update).toHaveBeenCalledWith(hinhThucGiaoHang);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HinhThucGiaoHang>>();
        const hinhThucGiaoHang = new HinhThucGiaoHang();
        jest.spyOn(hinhThucGiaoHangService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ hinhThucGiaoHang });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: hinhThucGiaoHang }));
        saveSubject.complete();

        // THEN
        expect(hinhThucGiaoHangService.create).toHaveBeenCalledWith(hinhThucGiaoHang);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HinhThucGiaoHang>>();
        const hinhThucGiaoHang = { id: 123 };
        jest.spyOn(hinhThucGiaoHangService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ hinhThucGiaoHang });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(hinhThucGiaoHangService.update).toHaveBeenCalledWith(hinhThucGiaoHang);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });
  });
});
