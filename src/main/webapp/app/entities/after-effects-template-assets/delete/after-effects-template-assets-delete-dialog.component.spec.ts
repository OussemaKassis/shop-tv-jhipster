jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AfterEffectsTemplateAssetsService } from '../service/after-effects-template-assets.service';

import { AfterEffectsTemplateAssetsDeleteDialogComponent } from './after-effects-template-assets-delete-dialog.component';

describe('AfterEffectsTemplateAssets Management Delete Component', () => {
  let comp: AfterEffectsTemplateAssetsDeleteDialogComponent;
  let fixture: ComponentFixture<AfterEffectsTemplateAssetsDeleteDialogComponent>;
  let service: AfterEffectsTemplateAssetsService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [AfterEffectsTemplateAssetsDeleteDialogComponent],
      providers: [NgbActiveModal],
    })
      .overrideTemplate(AfterEffectsTemplateAssetsDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AfterEffectsTemplateAssetsDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AfterEffectsTemplateAssetsService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('Should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

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
