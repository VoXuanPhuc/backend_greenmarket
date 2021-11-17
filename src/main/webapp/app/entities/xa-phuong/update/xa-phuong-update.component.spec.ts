jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { XaPhuongService } from '../service/xa-phuong.service';
import { IXaPhuong, XaPhuong } from '../xa-phuong.model';
import { IHuyenQuan } from 'app/entities/huyen-quan/huyen-quan.model';
import { HuyenQuanService } from 'app/entities/huyen-quan/service/huyen-quan.service';

import { XaPhuongUpdateComponent } from './xa-phuong-update.component';

describe('Component Tests', () => {
  describe('XaPhuong Management Update Component', () => {
    let comp: XaPhuongUpdateComponent;
    let fixture: ComponentFixture<XaPhuongUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let xaPhuongService: XaPhuongService;
    let huyenQuanService: HuyenQuanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [XaPhuongUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(XaPhuongUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(XaPhuongUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      xaPhuongService = TestBed.inject(XaPhuongService);
      huyenQuanService = TestBed.inject(HuyenQuanService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call HuyenQuan query and add missing value', () => {
        const xaPhuong: IXaPhuong = { id: 456 };
        const huyen: IHuyenQuan = { id: 18160 };
        xaPhuong.huyen = huyen;

        const huyenQuanCollection: IHuyenQuan[] = [{ id: 8791 }];
        jest.spyOn(huyenQuanService, 'query').mockReturnValue(of(new HttpResponse({ body: huyenQuanCollection })));
        const additionalHuyenQuans = [huyen];
        const expectedCollection: IHuyenQuan[] = [...additionalHuyenQuans, ...huyenQuanCollection];
        jest.spyOn(huyenQuanService, 'addHuyenQuanToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ xaPhuong });
        comp.ngOnInit();

        expect(huyenQuanService.query).toHaveBeenCalled();
        expect(huyenQuanService.addHuyenQuanToCollectionIfMissing).toHaveBeenCalledWith(huyenQuanCollection, ...additionalHuyenQuans);
        expect(comp.huyenQuansSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const xaPhuong: IXaPhuong = { id: 456 };
        const huyen: IHuyenQuan = { id: 10721 };
        xaPhuong.huyen = huyen;

        activatedRoute.data = of({ xaPhuong });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(xaPhuong));
        expect(comp.huyenQuansSharedCollection).toContain(huyen);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<XaPhuong>>();
        const xaPhuong = { id: 123 };
        jest.spyOn(xaPhuongService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ xaPhuong });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: xaPhuong }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(xaPhuongService.update).toHaveBeenCalledWith(xaPhuong);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<XaPhuong>>();
        const xaPhuong = new XaPhuong();
        jest.spyOn(xaPhuongService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ xaPhuong });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: xaPhuong }));
        saveSubject.complete();

        // THEN
        expect(xaPhuongService.create).toHaveBeenCalledWith(xaPhuong);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<XaPhuong>>();
        const xaPhuong = { id: 123 };
        jest.spyOn(xaPhuongService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ xaPhuong });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(xaPhuongService.update).toHaveBeenCalledWith(xaPhuong);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackHuyenQuanById', () => {
        it('Should return tracked HuyenQuan primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackHuyenQuanById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
