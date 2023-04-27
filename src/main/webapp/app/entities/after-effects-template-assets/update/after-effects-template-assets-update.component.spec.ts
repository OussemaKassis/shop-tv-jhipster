import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { AfterEffectsTemplateAssetsFormService } from './after-effects-template-assets-form.service';
import { AfterEffectsTemplateAssetsService } from '../service/after-effects-template-assets.service';
import { IAfterEffectsTemplateAssets } from '../after-effects-template-assets.model';

import { AfterEffectsTemplateAssetsUpdateComponent } from './after-effects-template-assets-update.component';

describe('AfterEffectsTemplateAssets Management Update Component', () => {
  let comp: AfterEffectsTemplateAssetsUpdateComponent;
  let fixture: ComponentFixture<AfterEffectsTemplateAssetsUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let afterEffectsTemplateAssetsFormService: AfterEffectsTemplateAssetsFormService;
  let afterEffectsTemplateAssetsService: AfterEffectsTemplateAssetsService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([])],
      declarations: [AfterEffectsTemplateAssetsUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AfterEffectsTemplateAssetsUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AfterEffectsTemplateAssetsUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    afterEffectsTemplateAssetsFormService = TestBed.inject(AfterEffectsTemplateAssetsFormService);
    afterEffectsTemplateAssetsService = TestBed.inject(AfterEffectsTemplateAssetsService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should update editForm', () => {
      const afterEffectsTemplateAssets: IAfterEffectsTemplateAssets = { id: 456 };

      activatedRoute.data = of({ afterEffectsTemplateAssets });
      comp.ngOnInit();

      expect(comp.afterEffectsTemplateAssets).toEqual(afterEffectsTemplateAssets);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAfterEffectsTemplateAssets>>();
      const afterEffectsTemplateAssets = { id: 123 };
      jest.spyOn(afterEffectsTemplateAssetsFormService, 'getAfterEffectsTemplateAssets').mockReturnValue(afterEffectsTemplateAssets);
      jest.spyOn(afterEffectsTemplateAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ afterEffectsTemplateAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: afterEffectsTemplateAssets }));
      saveSubject.complete();

      // THEN
      expect(afterEffectsTemplateAssetsFormService.getAfterEffectsTemplateAssets).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(afterEffectsTemplateAssetsService.update).toHaveBeenCalledWith(expect.objectContaining(afterEffectsTemplateAssets));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAfterEffectsTemplateAssets>>();
      const afterEffectsTemplateAssets = { id: 123 };
      jest.spyOn(afterEffectsTemplateAssetsFormService, 'getAfterEffectsTemplateAssets').mockReturnValue({ id: null });
      jest.spyOn(afterEffectsTemplateAssetsService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ afterEffectsTemplateAssets: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: afterEffectsTemplateAssets }));
      saveSubject.complete();

      // THEN
      expect(afterEffectsTemplateAssetsFormService.getAfterEffectsTemplateAssets).toHaveBeenCalled();
      expect(afterEffectsTemplateAssetsService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAfterEffectsTemplateAssets>>();
      const afterEffectsTemplateAssets = { id: 123 };
      jest.spyOn(afterEffectsTemplateAssetsService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ afterEffectsTemplateAssets });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(afterEffectsTemplateAssetsService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
