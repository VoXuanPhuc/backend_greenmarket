jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { HuyenQuanService } from '../service/huyen-quan.service';
import { IHuyenQuan, HuyenQuan } from '../huyen-quan.model';
import { ITinhTP } from 'app/entities/tinh-tp/tinh-tp.model';
import { TinhTPService } from 'app/entities/tinh-tp/service/tinh-tp.service';

import { HuyenQuanUpdateComponent } from './huyen-quan-update.component';

describe('Component Tests', () => {
  describe('HuyenQuan Management Update Component', () => {
    let comp: HuyenQuanUpdateComponent;
    let fixture: ComponentFixture<HuyenQuanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let huyenQuanService: HuyenQuanService;
    let tinhTPService: TinhTPService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [HuyenQuanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(HuyenQuanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(HuyenQuanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      huyenQuanService = TestBed.inject(HuyenQuanService);
      tinhTPService = TestBed.inject(TinhTPService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call TinhTP query and add missing value', () => {
        const huyenQuan: IHuyenQuan = { id: 456 };
        const tinh: ITinhTP = { id: 24993 };
        huyenQuan.tinh = tinh;

        const tinhTPCollection: ITinhTP[] = [{ id: 8665 }];
        jest.spyOn(tinhTPService, 'query').mockReturnValue(of(new HttpResponse({ body: tinhTPCollection })));
        const additionalTinhTPS = [tinh];
        const expectedCollection: ITinhTP[] = [...additionalTinhTPS, ...tinhTPCollection];
        jest.spyOn(tinhTPService, 'addTinhTPToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ huyenQuan });
        comp.ngOnInit();

        expect(tinhTPService.query).toHaveBeenCalled();
        expect(tinhTPService.addTinhTPToCollectionIfMissing).toHaveBeenCalledWith(tinhTPCollection, ...additionalTinhTPS);
        expect(comp.tinhTPSSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const huyenQuan: IHuyenQuan = { id: 456 };
        const tinh: ITinhTP = { id: 38817 };
        huyenQuan.tinh = tinh;

        activatedRoute.data = of({ huyenQuan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(huyenQuan));
        expect(comp.tinhTPSSharedCollection).toContain(tinh);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HuyenQuan>>();
        const huyenQuan = { id: 123 };
        jest.spyOn(huyenQuanService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ huyenQuan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: huyenQuan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(huyenQuanService.update).toHaveBeenCalledWith(huyenQuan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HuyenQuan>>();
        const huyenQuan = new HuyenQuan();
        jest.spyOn(huyenQuanService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ huyenQuan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: huyenQuan }));
        saveSubject.complete();

        // THEN
        expect(huyenQuanService.create).toHaveBeenCalledWith(huyenQuan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<HuyenQuan>>();
        const huyenQuan = { id: 123 };
        jest.spyOn(huyenQuanService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ huyenQuan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(huyenQuanService.update).toHaveBeenCalledWith(huyenQuan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).not.toHaveBeenCalled();
      });
    });

    describe('Tracking relationships identifiers', () => {
      describe('trackTinhTPById', () => {
        it('Should return tracked TinhTP primary key', () => {
          const entity = { id: 123 };
          const trackResult = comp.trackTinhTPById(0, entity);
          expect(trackResult).toEqual(entity.id);
        });
      });
    });
  });
});
