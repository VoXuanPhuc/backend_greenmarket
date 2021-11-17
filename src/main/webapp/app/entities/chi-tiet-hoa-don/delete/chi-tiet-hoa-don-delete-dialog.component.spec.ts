jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { ChiTietHoaDonService } from '../service/chi-tiet-hoa-don.service';

import { ChiTietHoaDonDeleteDialogComponent } from './chi-tiet-hoa-don-delete-dialog.component';

describe('Component Tests', () => {
  describe('ChiTietHoaDon Management Delete Component', () => {
    let comp: ChiTietHoaDonDeleteDialogComponent;
    let fixture: ComponentFixture<ChiTietHoaDonDeleteDialogComponent>;
    let service: ChiTietHoaDonService;
    let mockActiveModal: NgbActiveModal;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [HttpClientTestingModule],
        declarations: [ChiTietHoaDonDeleteDialogComponent],
        providers: [NgbActiveModal],
      })
        .overrideTemplate(ChiTietHoaDonDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(ChiTietHoaDonDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = TestBed.inject(ChiTietHoaDonService);
      mockActiveModal = TestBed.inject(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({})));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
        })
      ));

      it('Should not call delete service on clear', () => {
        // GIVEN
        jest.spyOn(service, 'delete');

        // WHEN
        comp.cancel();

        // THEN
        expect(service.delete).not.toHaveBeenCalled();
        expect(mockActiveModal.close).not.toHaveBeenCalled();
        expect(mockActiveModal.dismiss).toHaveBeenCalled();
      });
    });
  });
});
