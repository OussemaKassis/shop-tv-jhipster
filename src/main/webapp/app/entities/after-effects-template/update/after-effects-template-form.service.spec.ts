import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../after-effects-template.test-samples';

import { AfterEffectsTemplateFormService } from './after-effects-template-form.service';

describe('AfterEffectsTemplate Form Service', () => {
  let service: AfterEffectsTemplateFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AfterEffectsTemplateFormService);
  });

  describe('Service methods', () => {
    describe('createAfterEffectsTemplateFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAfterEffectsTemplateFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            templateName: expect.any(Object),
            templateDuration: expect.any(Object),
            templateDescription: expect.any(Object),
            templateRating: expect.any(Object),
            templateActive: expect.any(Object),
            templateType: expect.any(Object),
            templateExpectedSize: expect.any(Object),
            templateCount: expect.any(Object),
            templateVisibleFor: expect.any(Object),
            ratio: expect.any(Object),
            previewUrl: expect.any(Object),
            company: expect.any(Object),
            category: expect.any(Object),
            afterEffectsTemplateAssets: expect.any(Object),
          })
        );
      });

      it('passing IAfterEffectsTemplate should create a new form with FormGroup', () => {
        const formGroup = service.createAfterEffectsTemplateFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            templateName: expect.any(Object),
            templateDuration: expect.any(Object),
            templateDescription: expect.any(Object),
            templateRating: expect.any(Object),
            templateActive: expect.any(Object),
            templateType: expect.any(Object),
            templateExpectedSize: expect.any(Object),
            templateCount: expect.any(Object),
            templateVisibleFor: expect.any(Object),
            ratio: expect.any(Object),
            previewUrl: expect.any(Object),
            company: expect.any(Object),
            category: expect.any(Object),
            afterEffectsTemplateAssets: expect.any(Object),
          })
        );
      });
    });

    describe('getAfterEffectsTemplate', () => {
      it('should return NewAfterEffectsTemplate for default AfterEffectsTemplate initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAfterEffectsTemplateFormGroup(sampleWithNewData);

        const afterEffectsTemplate = service.getAfterEffectsTemplate(formGroup) as any;

        expect(afterEffectsTemplate).toMatchObject(sampleWithNewData);
      });

      it('should return NewAfterEffectsTemplate for empty AfterEffectsTemplate initial value', () => {
        const formGroup = service.createAfterEffectsTemplateFormGroup();

        const afterEffectsTemplate = service.getAfterEffectsTemplate(formGroup) as any;

        expect(afterEffectsTemplate).toMatchObject({});
      });

      it('should return IAfterEffectsTemplate', () => {
        const formGroup = service.createAfterEffectsTemplateFormGroup(sampleWithRequiredData);

        const afterEffectsTemplate = service.getAfterEffectsTemplate(formGroup) as any;

        expect(afterEffectsTemplate).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAfterEffectsTemplate should not enable id FormControl', () => {
        const formGroup = service.createAfterEffectsTemplateFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAfterEffectsTemplate should disable id FormControl', () => {
        const formGroup = service.createAfterEffectsTemplateFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
