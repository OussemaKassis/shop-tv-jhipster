import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../after-effects-template-assets.test-samples';

import { AfterEffectsTemplateAssetsFormService } from './after-effects-template-assets-form.service';

describe('AfterEffectsTemplateAssets Form Service', () => {
  let service: AfterEffectsTemplateAssetsFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AfterEffectsTemplateAssetsFormService);
  });

  describe('Service methods', () => {
    describe('createAfterEffectsTemplateAssetsFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetName: expect.any(Object),
            assetType: expect.any(Object),
          })
        );
      });

      it('passing IAfterEffectsTemplateAssets should create a new form with FormGroup', () => {
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetName: expect.any(Object),
            assetType: expect.any(Object),
          })
        );
      });
    });

    describe('getAfterEffectsTemplateAssets', () => {
      it('should return NewAfterEffectsTemplateAssets for default AfterEffectsTemplateAssets initial value', () => {
        // eslint-disable-next-line @typescript-eslint/no-unused-vars
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup(sampleWithNewData);

        const afterEffectsTemplateAssets = service.getAfterEffectsTemplateAssets(formGroup) as any;

        expect(afterEffectsTemplateAssets).toMatchObject(sampleWithNewData);
      });

      it('should return NewAfterEffectsTemplateAssets for empty AfterEffectsTemplateAssets initial value', () => {
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup();

        const afterEffectsTemplateAssets = service.getAfterEffectsTemplateAssets(formGroup) as any;

        expect(afterEffectsTemplateAssets).toMatchObject({});
      });

      it('should return IAfterEffectsTemplateAssets', () => {
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup(sampleWithRequiredData);

        const afterEffectsTemplateAssets = service.getAfterEffectsTemplateAssets(formGroup) as any;

        expect(afterEffectsTemplateAssets).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAfterEffectsTemplateAssets should not enable id FormControl', () => {
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAfterEffectsTemplateAssets should disable id FormControl', () => {
        const formGroup = service.createAfterEffectsTemplateAssetsFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
