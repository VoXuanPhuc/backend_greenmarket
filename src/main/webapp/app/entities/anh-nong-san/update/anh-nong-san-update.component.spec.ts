jest.mock('@angular/router');

import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { of, Subject } from 'rxjs';

import { AnhNongSanService } from '../service/anh-nong-san.service';
import { IAnhNongSan, AnhNongSan } from '../anh-nong-san.model';
import { INongSan } from 'app/entities/nong-san/nong-san.model';
import { NongSanService } from 'app/entities/nong-san/service/nong-san.service';

import { AnhNongSanUpdateComponent } from './anh-nong-san-update.component';

describe('Component Tests', () => {
  describe('AnhNongSan Management Update Component', () => {
    let comp: AnhNongSanUpdateComponent;
    let fixture: ComponentFixture<AnhNongSanUpdateComponent>;
    let activatedRoute: ActivatedRoute;
    let anhNongSanService: AnhNongSanService;
    let nongSanService: NongSanService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [AnhNongSanUpdateComponent],
        providers: [FormBuilder, ActivatedRoute],
      })
        .overrideTemplate(AnhNongSanUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(AnhNongSanUpdateComponent);
      activatedRoute = TestBed.inject(ActivatedRoute);
      anhNongSanService = TestBed.inject(AnhNongSanService);
      nongSanService = TestBed.inject(NongSanService);

      comp = fixture.componentInstance;
    });

    describe('ngOnInit', () => {
      it('Should call NongSan query and add missing value', () => {
        const anhNongSan: IAnhNongSan = { id: 456 };
        const anhnongsan: INongSan = { id: 26623 };
        anhNongSan.anhnongsan = anhnongsan;

        const nongSanCollection: INongSan[] = [{ id: 22818 }];
        jest.spyOn(nongSanService, 'query').mockReturnValue(of(new HttpResponse({ body: nongSanCollection })));
        const additionalNongSans = [anhnongsan];
        const expectedCollection: INongSan[] = [...additionalNongSans, ...nongSanCollection];
        jest.spyOn(nongSanService, 'addNongSanToCollectionIfMissing').mockReturnValue(expectedCollection);

        activatedRoute.data = of({ anhNongSan });
        comp.ngOnInit();

        expect(nongSanService.query).toHaveBeenCalled();
        expect(nongSanService.addNongSanToCollectionIfMissing).toHaveBeenCalledWith(nongSanCollection, ...additionalNongSans);
        expect(comp.nongSansSharedCollection).toEqual(expectedCollection);
      });

      it('Should update editForm', () => {
        const anhNongSan: IAnhNongSan = { id: 456 };
        const anhnongsan: INongSan = { id: 31151 };
        anhNongSan.anhnongsan = anhnongsan;

        activatedRoute.data = of({ anhNongSan });
        comp.ngOnInit();

        expect(comp.editForm.value).toEqual(expect.objectContaining(anhNongSan));
        expect(comp.nongSansSharedCollection).toContain(anhnongsan);
      });
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnhNongSan>>();
        const anhNongSan = { id: 123 };
        jest.spyOn(anhNongSanService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anhNongSan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anhNongSan }));
        saveSubject.complete();

        // THEN
        expect(comp.previousState).toHaveBeenCalled();
        expect(anhNongSanService.update).toHaveBeenCalledWith(anhNongSan);
        expect(comp.isSaving).toEqual(false);
      });

      it('Should call create service on save for new entity', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnhNongSan>>();
        const anhNongSan = new AnhNongSan();
        jest.spyOn(anhNongSanService, 'create').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anhNongSan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.next(new HttpResponse({ body: anhNongSan }));
        saveSubject.complete();

        // THEN
        expect(anhNongSanService.create).toHaveBeenCalledWith(anhNongSan);
        expect(comp.isSaving).toEqual(false);
        expect(comp.previousState).toHaveBeenCalled();
      });

      it('Should set isSaving to false on error', () => {
        // GIVEN
        const saveSubject = new Subject<HttpResponse<AnhNongSan>>();
        const anhNongSan = { id: 123 };
        jest.spyOn(anhNongSanService, 'update').mockReturnValue(saveSubject);
        jest.spyOn(comp, 'previousState');
        activatedRoute.data = of({ anhNongSan });
        comp.ngOnInit();

        // WHEN
        comp.save();
        expect(comp.isSaving).toEqual(true);
        saveSubject.error('This is an error!');

        // THEN
        expect(anhNongSanService.update).toHaveBeenCalledWith(anhNongSan);
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
    });
  });
});
